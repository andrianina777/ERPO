<template>

    <v-card>
        <v-app>
            <v-app-bar :elevation="1" density="compact" color="#d7ead5" app>
                <v-card density="compact">

                    <MyTabsFacturio :StoreAdv="useEtapeStoreAdv"></MyTabsFacturio>


                </v-card>
                <v-spacer></v-spacer>

                <div>
                    <u>Login</u> :&nbsp;<b><span style="color: #388E3C;">{{ user }}</span></b>
                </div>&nbsp;&nbsp;
                <div>
                    <span
                    :style="{ backgroundColor: isOnline ? 'green' : 'red', width: '10px', height: '10px', borderRadius: '50%', display: 'inline-block' }">
                    </span>
                </div>
                <!-- test deploiement -->

                <div>
                    <v-btn @click="onHome">
                        <v-img :width="20" :src="require('@/assets/home.png')"></v-img>
                    </v-btn>
                </div>
            </v-app-bar>

            <v-main style="--v-layout-top: 45px" app>
                <v-card-actions density="compact" style="background-color: #FAFAFA;">
                    <v-row align="center">
                        <v-col cols="auto">
                            <v-card-title class="date-input">
                                <v-text-field type="date" label="Date" v-model="date_deb" single-line hide-details
                                    color="deep-purple-accent-4" density="compact" clearable>
                                </v-text-field>
                            </v-card-title>
                        </v-col>
                        <b>AU</b>
                        <v-col cols="auto">
                            <v-card-title class="date-input">
                                <v-text-field type="date" label="Date" v-model="date_fin" single-line hide-details
                                    color="deep-purple-accent-4" density="compact" clearable>
                                </v-text-field>
                            </v-card-title>
                        </v-col>

                        <v-col cols="auto">
                            <v-btn variant="tonal" color="primary" @click="searchEvent" :disabled="loading" size="large">
                                Actualiser
                            </v-btn>
                        </v-col>
                    </v-row>
                </v-card-actions>

                <div class="pa-2">
                    <v-progress-linear v-if="loading" color="primary" rounded indeterminate height="5"
                        density="compact">
                    </v-progress-linear>

                    <v-dialog v-model="dialogVisible" :persistent="true" width="1500px" height="500px">
                        <firstCall :numero="numero" :calldate=calldate :nomclient=nomclient :seqEntrant=seq
                            @annuler="onAnnuler" @save="onSave" />
                    </v-dialog>

                    <v-dialog v-model="dialogVisibleSecondCall" :persistent="true" width="1500px" height="500px">
                        <secondCall :numero="numero" :daterappel_1=daterappel_1 :nomclient=nomclient :seqEntrant=seq
                        @annuler="onAnnulerSecond" @save="onSaveSecond"/>
                    </v-dialog>

                    <v-dialog v-model="dialogVisibleAdd" :persistent="true" width="1500px" height="500px">
                        <add :numero="numero" :seqEntrant=seq
                        @annuler="onAnnulerAdd" @save="onSaveSecond"/>
                    </v-dialog>

                    <v-card>
                        <MyTableFact :headers="headers" :items="list" v-model:search="search" action1="A Rappeler"
                            @action1Item="showarappeler" action4="1er Rappel" @action4Item="premierrappel"
                            action5="2eme Rappel" @action5Item="deuxmrappel" @action3Item='add'>
                        </MyTableFact>

                    </v-card>
                </div>
            </v-main>
        </v-app>
    </v-card>
</template>

<script>
import { defineComponent, ref, computed, onMounted } from "vue";
import { useRouter } from 'vue-router'
import { useOnline } from '@vueuse/core';
import { useEtapeStoreAdv } from "@/store/utils/etapeadv.js";
import { useAppelEntrantStore } from "@/store/SuiviFacturation/appelEntrant";
import MyTabsFacturio from '@/components/facturio/MyTabsFacturio.vue';
import swal from 'sweetalert2';

import firstCall from '@/views/suivifacturation/firstCall.vue'
import secondCall from '@/views/suivifacturation/secondCall.vue'
import add from '@/views/suivifacturation/add.vue'

import dayjs from 'dayjs';
import MyTableFact from '@/components/facturio/MyTableFact.vue';


export default defineComponent({
    components: {
        MyTabsFacturio,
        MyTableFact,
        firstCall,
        secondCall,
        add,
    },

    methods: {
        onHome() {
            this.$router.push("/AccueilPage");
        },
    },

    setup() {

        const isOnline = useOnline();
        const user = localStorage.getItem('username');
        const StoreAdv = useEtapeStoreAdv();
        const router = useRouter();
        const dialog = ref(false);
        const dialogVisible = ref(false);
        const dialogVisibleSecondCall = ref(false);
        const dialogVisibleAdd = ref(false);

        const date_deb = ref('');
        const date_fin = ref('');

        const loading = computed(() => store.loading)

        const isCallOne = ref(false);

        const store = useAppelEntrantStore();
        const list = ref([]);

        const numero = ref();
        const client = ref();
        const calldate = ref();
        const nomclient = ref();
        const seq = ref();
        const daterappel_1 =ref();

        onMounted(async () => {
            const today = dayjs().format('YYYY-MM-DD');
            date_deb.value = today;
            date_fin.value = today;


            list.value = [];
            listAppelEntrant();

            // console.log(list.value)
        })

        // const headers = [
        //     { title: 'DATE', align: 'center', key: 'calldate', sortable: true },
        //     { title: 'CODE CLIENT', align: 'center', key: 'code', sortable: true },
        //     { title: 'CONTACT', align: 'center', key: 'numero', sortable: true },
        //     { title: 'NOM CLIENTS', align: 'start', key: 'nomclient' },
        //     { title: 'LOCALISATION', align: 'start', key: 'localisation' },
        //     { title: 'APPEL', align: 'start', key: 'dst' },
        //     { title: '1ER RAPPEL', align: 'center', key: 'rappel1' },
        //     { title: '2EME RAPPEL', align: 'start', key: 'rappel2' },
        //     { title: 'COMMENTAIRE', align: 'start', key: 'coms'},
        //     { title: 'TEMPS DE RAPPEL', align: 'start', key: ''},
        //     { title: 'ACTION', align: 'start', key: 'action', value: 'action' },

        // ];

        const headers = [
            { title: 'DATE', align: 'center', key: 'calldate', sortable: true },
            { title: 'SOURCE', align: 'start', key: 'numero', sortable: true},
            { title: '', align: 'center', key: 'add', sortable: true },
            { title: 'CLIENT', align: 'start', key: 'client', sortable: true },
            // { title: 'CONTACT', align: 'center', key: 'numero', sortable: true },
            // { title: 'NUMERO INDICATIF', align: 'start', key: 'numero_indicatif' },
            // { title: 'LOCALISATION', align: 'start', key: 'dcontext' },
            // { title: 'APPEL', align: 'start', key: 'dst' },
            // { title: 'CHANNEL', align: 'start', key: 'dstchannel' },
            { title: 'POSTE INTERNE', align: 'center', key: 'poste_interne' },
            // { title: 'DUREE(s)', align: 'end', key: 'duration' },
            { title: 'DUREÉ APPEL(s)', align: 'center', key: 'billsec' },
            // { title: 'DISPOSITION', align: 'start', key: 'disposition' },
            { title: 'STATUS', align: 'start', key: 'statut' },
            { title: '1er Rappel', align: 'start', key: 'rappel1' },
            { title: '2eme Rappel', align: 'start', key: 'rappel2' },
            { title: 'DEPUIS(mn)', align: 'start', key: 'depuis' },
            { title: 'ACTION', align: 'start', key: 'action', value: 'action' },

        ];

        const listAppelEntrant = async () => {
            await store.fetchAppelEntrant(date_deb.value, date_fin.value)
            list.value = store.AppelEntrants
        }

        const searchEvent = async () => {
            listAppelEntrant();
        }


        const onAnnuler = async () => {
            dialogVisible.value = false;
            isCallOne.value = false;
            listAppelEntrant();
        };

        const onAnnulerSecond = async () => {
            dialogVisibleSecondCall.value = false;
            listAppelEntrant();
        };

        const onAnnulerAdd = async () => {
            dialogVisibleAdd.value = false;
            listAppelEntrant();
        };




        const showarappeler = async (item) => {
            numero.value = item.numero;
            client.value = item.client;

            const displayName = client.value ? client.value : numero.value;

            swal
                .fire({
                    title: `Voulez-vous Rappeler : <br><span style="color: #43A047;">${displayName}</span> ?`,
                    showDenyButton: true,
                    showCancelButton: false,
                    allowOutsideClick: false,
                    confirmButtonText: 'Oui',
                    denyButtonText: `Non`,
                })
                .then(async (result) => {
                    if (result.isConfirmed) {
                        const id = item.seq
                        const params = {
                            AppelId: id,
                            userFact: user,
                        }
                        await store.updateEtat(params)
                        listAppelEntrant();

                    } else if (result.isDenied) {
                        // swal.fire('Transfert annuler', '', 'info');
                    }
                });

        }
        const premierrappel = async (item) => {
            numero.value = item.numero;
            calldate.value = item.calldate;
            nomclient.value = item.client;
            seq.value = item.seq;

            dialogVisible.value = true;

            // console.log(numero)
            // console.log('nom client :'+nomclient.value)


            // router.push({ name: 'firstCall', params: { numero } });

        }

        const deuxmrappel = async (item) => {
            numero.value = item.numero;
            nomclient.value = item.client;
            seq.value = item.seq;
            daterappel_1.value = item.daterappel_1;
            dialogVisibleSecondCall.value = true;
        }

        const add = async (item) => {
            // console.log('kaka')
            numero.value = item.numero;
            seq.value = item.seq;
            dialogVisibleAdd.value = true;
        }


        return {
            isOnline, user, StoreAdv, dialogVisible, dialogVisibleSecondCall,dialogVisibleAdd,dialog, headers, date_deb, date_fin,
            list, listAppelEntrant, searchEvent, showarappeler, premierrappel, isCallOne,onAnnulerSecond, onAnnuler,onAnnulerAdd, numero, calldate, nomclient, seq,client,
            loading,deuxmrappel,daterappel_1,add

        };

    }

})

</script>