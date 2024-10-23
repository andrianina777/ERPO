<template>
    <v-card>
        <v-app>
            <v-app-bar :elevation="1" density="compact" color="#d7ead5" app>
                <v-card density="compact">
                    <TabsTrans></TabsTrans>
                </v-card>
                <v-spacer></v-spacer>
                <div>
                    <u>Login</u> :&nbsp;<b><span style="color: #388E3C;">{{ user }}</span></b>
                </div>&nbsp;&nbsp;
                <div>
                    <span
                        :style="{ backgroundColor: isOnline ? 'green' : 'red', width: '10px', height: '10px', borderRadius: '50%', display: 'inline-block' }"></span>
                </div>

                <div>
                    <v-btn @click="onHome">
                        <v-img :width="20" :src="require('@/assets/home.png')"></v-img>
                    </v-btn>
                </div>
            </v-app-bar>
            <v-main style="--v-layout-top: 45px" app>
                <v-card-actions density="compact">
                    <v-card-title density="compact">
                        <MyTextField name_lab_text="Numéro TD/TS/TE" :rules="[rules.customFormat]" style="width: 130px"
                            hide-details="auto" v-model="codeTD" @input="convertToUpperCase" hint="ex:TD23082565" placeholder="TD23082565">
                        </MyTextField>
                    </v-card-title>
                    <v-card-title>
                        <v-text-field v-model="dateDeb" type="date" label="Date Deb" hide-no-data hide-details variant="outlined"
                            color="deep-purple-accent-4" density="compact" clearable></v-text-field>
                    </v-card-title>
    
                    <v-card-title>
                        <v-text-field v-model="dateFin" type="date" label="Date fin"  hide-no-data hide-details variant="outlined"
                            color="deep-purple-accent-4" density="compact" clearable></v-text-field>
                    </v-card-title>
                    <v-card-title density="compact">
                        <MySelect comb_lab="Dépôt Orig" :items="depotCodes" v-model="depOrg" :disabled="loading"
                            style="width: 145px; height: 35px;">
                        </MySelect>
                    </v-card-title>
                    <v-card-title density="compact">
                        <MySelect comb_lab="Dépôt Dest" :items="depotCodes" v-model="depDest" :disabled="loading"
                            style="width: 145px; height: 35px;">
                        </MySelect>
                    </v-card-title>
                    <v-card-title density="compact">
                        <Autocomplete :isUpdating="isUpdating" :items="items" v-model:search="search" v-model="select"
                            :loading="loading" @updateSelect="handleSelectChange" />
                    </v-card-title>
                    <v-card-title density="compact">
                        <small class="text-subtitle-1">{{ libelle }}</small>
                    </v-card-title>
                    <searchArticle v-if="isSearch" :dialog="isSearch" @close="closeDialog" @getArticle="getArticleSearch">
                    </searchArticle>
                    <v-alert v-if="isDepOrg" type="error" @click="isDepOrg = false, dialog = false" colored-border
                        elevation="1" density="compact">Dépôt d'origine est vide
                    </v-alert>
                    <v-alert v-if="isDepDest" type="error" @click="isDepDest = false, dialog = false" colored-border
                        elevation="1" density="compact">Dépôt destination est vide
                    </v-alert>
                    <v-alert v-if="isDatedeb" type="error" @click="isDatedeb = false, dialog = false" colored-border
                        elevation="1" density="compact">Date début obligatoire
                    </v-alert>
                    <v-alert v-if="isDatefin" type="error" @click="isDatefin = false, dialog = false" colored-border
                        elevation="1" density="compact">Date fin obligatoire
                    </v-alert>
                    <v-spacer />
                    <v-btn variant="tonal" color="primary" @click="searchEvent" :disabled="loading"
                        density="compact">Rechercher
                    </v-btn>
                </v-card-actions>
                <v-progress-linear v-if="loadings" color="#6200ee"  rounded indeterminate height="5" density="compact">
                </v-progress-linear>
                <MyLoading :loading="chagmt" @close="chagmt = false" :articles="articles"></MyLoading>
                <router-view></router-view>
                <div class="pa-2" style="max-height: 600px; overflow-y: auto;">
                    <MyTable :headers="headers" :items="list" action2="Reimprimer" @action2Item="reimprimer"
                        height="600">
                    </MyTable>
                </div>

            </v-main>
        </v-app>
    </v-card>
</template>
<script>
import { ref, computed, onMounted, watch, onBeforeMount, defineComponent } from 'vue';
import { useOnline } from '@vueuse/core';
import TabsTrans from "@/components/transfert/TabsTrans.vue";
import { useRouter } from 'vue-router'
import useSessionTimeout from '@/utils/useSessionTimeout';
import Config from "@/utils/config.js"
import MyTextField from '@/components/MyTextField.vue';
import Autocomplete from "@/components/MyAutocomplete.vue"
import MySelect from '@/components/MySelect.vue';
import dayjs from 'dayjs'
import MyTable from '@/components/MyTable.vue';
import { useDepotStore } from "@/store/utils/depot.js";
import { useSuiviTDStore } from "@/store/Transfert/suiviTD";
import searchArticle from "@/components/searchArticle.vue";
import useArticleSelection from "@/composables/useArticleSelection"
import { useReportBPStore } from "@/store/report/printBP";
import MyLoading from "@/components/Loading";
export default defineComponent({
    components: {
        TabsTrans,
        MyTextField,
        Autocomplete,
        MySelect,
        searchArticle,
        MyTable,
        MyLoading
    },

    methods: {
        onHome() {
            this.$router.push("/AccueilPage");
        },
    },
    setup() {
        const isOnline = useOnline();
        const user = localStorage.getItem('username');
        const codeTD = ref('');
        const dateDeb = ref('');
        const dateFin = ref('');
        const storeDepot = useDepotStore();
        const depOrg = ref('');
        const depDest = ref('');
        const isSearch = ref(false);
        const dialog = ref(false);
        const store = useSuiviTDStore();
        const loadings = computed(() => store.loading);
        const isDepOrg = ref(false);
        const isDepDest = ref(false);
        const isDatedeb = ref(false);
        const isDatefin = ref(false);
        const storePrint = useReportBPStore();
        const chagmt = ref(false);
        const articles = ref('');
        const router = useRouter();
        useSessionTimeout(Config.DURRE_SESSION, router);
        const list = ref([]);

        const convertToUpperCase = () => {
            codeTD.value = codeTD.value.toUpperCase();
        };

        onMounted(async () => {
            const today = dayjs().format('YYYY-MM-DD');
            dateDeb.value = today;
            dateFin.value = today;
            await storeDepot.fetchDepots(`isnull(DPLOC,0)<>0 and rtrim(ADNOMUSER)='${user}'`);
            list.value=[];
        });

        const rules = {
            customFormat: (value) => {
                if (!value) {
                    return true;
                }
                const regex = /^(TE|TS|TD)\d{8}$/;
                if (!value.match(regex)) {
                    return 'code invalid';
                }
                return true;
            },
        };

        const depotCodes = computed(() => {
            return storeDepot.depots.map(depot => depot.code);
        });
        const { isUpdating, items, search, select, loading, handleSelectChange, libelle } = useArticleSelection(isSearch);

        function closeDialog() {
            isSearch.value = false;
            dialog.value = false;
        }

        function getArticleSearch(item) {
            select.value = item.article;
            libelle.value = item.libelle;
            closeDialog();
        }

        const totalArticles = computed(() => {
            return listConsRea0.value.length;
        });

        const searchEvent = async () => {
            console.log(dateDeb.value)
            if (depOrg.value.trim().length === 0) {
                isDepOrg.value = true;
            } else if (depDest.value.trim().length === 0) {
                isDepDest.value = true;
            } else if (dateDeb.value === null) {
                isDatedeb.value = true;
            } else if (dateFin.value === null) {
                isDatefin.value = true;
            }
            else {
                listDT();
            }

        }
        const headers = [
            { title: 'CODE', align: 'center', key: 'code' },
            { title: 'DATE', align: 'center', key: 'date', },
            { title: 'EMPL ORG', align: 'denter', key: 'empl_org' },
            { title: 'LIBELLE', align: 'start', key: 'libelle',minWidth: '300px' },
            { title: 'QUANTITE', align: 'end', key: 'qte' },
            { title: 'EMPL DEST', align: 'center', key: 'empl_dest' },
            { title: 'COMMENTAIRE', align: 'start', key: 'commentaire' },
            { title: 'MOTIF', align: 'start', key: 'depot_dest' },
            { title: "DATE D'ENTREE", align: 'start', key: 'date_entree' },
            { title: "USER", align: 'start', key: 'users' },
            { title: 'ACTION', align: 'center', key: 'actions' },
        ];


        const listDT = async () => {
            await store.fetchSuiviTD(codeTD.value, depOrg.value, depDest.value, dateDeb.value, dateFin.value, select.value)
            if(store.suiviTDs.length > 0){
                list.value = store.suiviTDs;
            }

        }

        const reimprimer = async (item) => {
            chagmt.value = true;
            articles.value = 'Impression en cours ' + item.code;
            const response = await storePrint.generateReportTD(item.code, depOrg.value, depDest.value, 1, localStorage.getItem('username'));
            const blob = new Blob([response.data], { type: 'application/pdf' });
            const url = URL.createObjectURL(blob);
            window.open(url, '_blank');
            URL.revokeObjectURL(url);
            chagmt.value = false;
        }

        return {
            isOnline, user, codeTD, convertToUpperCase, dateDeb, dateFin, depotCodes, depOrg, depDest,
            isUpdating, items, search, select, loading, libelle, handleSelectChange, isSearch, dialog, totalArticles,
            getArticleSearch, closeDialog, searchEvent, store, headers, listDT, loadings, isDepOrg, isDepDest, isDatedeb, isDatefin,
            reimprimer, chagmt, articles, rules,list
        };
    },
});
</script>
