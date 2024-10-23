<template>

    <v-card>
        <v-app>
            <v-app-bar :elevation="1" density="compact" color="#d7ead5" app>
                <v-card density="compact">
                     <!-- <MyTabsAdv :StoreAdv="useEtapeStoreAdv"></MyTabsAdv> -->
                    <MyTabsSuivi :StoreAdv="useEtapeStoreAdv"></MyTabsSuivi>
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

                <div>
                    <v-btn @click="onHome">
                        <v-img :width="20" :src="require('@/assets/home.png')"></v-img>
                    </v-btn>
                </div>
            </v-app-bar>

            <v-main style="--v-layout-top: 40px" app>
                <v-card-actions density="compact">
                    <v-container class="light-background">
                        <!-- <v-row justify="center">
                            <v-col cols="12" class="text-center">
                                <h1>Etats commandes</h1>
                            </v-col>
                        </v-row> -->
                        <v-row>
                            <v-card-title density="compact">
                                <MyTextField name_lab_text="Code Commande" style="width: 130px"
                                v-model="codeCC" @input="convertToUpperCase" hint="ex:CC23108829"
                                    placeholder="CC">
                                </MyTextField>
                            </v-card-title>

                            <v-card-title density="compact">
                                <MyTextField name_lab_text="Code Client" style="width: 130px" hint=""
                                v-model="CodeCLT" @input="convertToUpperCase" placeholder="Code client">
                                </MyTextField>
                            </v-card-title>

                            <v-card-title density="compact">
                                <MyTextField name_lab_text="Nom Client" style="width: 145px" @input="convertToUpperCase"
                                    v-model="nomClient" hint=" ex:PHARMACIE" placeholder="Nom client">
                                </MyTextField>
                            </v-card-title>

                            <v-card-title density="compact" >
                                <MySelect label="Axe"
                                    :items="storeAxe.axes"
                                    variant="underlined" v-model="selectedAxe"  style="width: 200px;" ></MySelect>
                            </v-card-title>



                            <v-card-title density="compact"></v-card-title>
                            <div class="date-container">
                                <span class="date-label" density="compact"><b>Date Début</b></span>

                                <v-card-title class="date-input">
                                    <v-text-field type="date" label="Date Livraison" v-model="dateDeb" single-line
                                        hide-details color="deep-purple-accent-4" density="compact"
                                        clearable></v-text-field>
                                </v-card-title>
                            </div>


                            <div class="date-container">
                                <span class="date-label" density="compact"><b>Date Fin</b></span>

                                <v-card-title class="date-input">
                                    <v-text-field type="date" label="Date Livraison" v-model="dateFin" single-line
                                        hide-details color="deep-purple-accent-4" density="compact"
                                        clearable></v-text-field>
                                </v-card-title>
                            </div>

                            <v-spacer />

                            <v-row>
                                <v-col cols="auto">
                                    <v-btn variant="tonal" color="primary" @click="searchEvent" :disabled="loading"
                                        size="large" density="compact">Rechercher
                                    </v-btn>
                                </v-col>
                            </v-row>
                        </v-row>
                    </v-container>
                </v-card-actions>

                <div>
                    <v-card>
                        <v-progress-linear v-if="loading" color="primary" rounded indeterminate height="5" density="compact"></v-progress-linear>

                        <MyTableSuivi :headers="headers" :items="list" height="600" :search="nomClient" class="dezoomed-table-container">
                        </MyTableSuivi>

                         <!-- <v-card style="width: 60%;">
                            <v-row>
                                <v-col>
                                    <v-sheet class="pl-2">
                                        <b> {{ 0 }}</b> Lignes Trouvées
                                    </v-sheet>
                                </v-col>
                            </v-row>
                        </v-card>  -->
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
import dayjs from 'dayjs'
import MyTextField from '@/components/MyTextField.vue';
import MyTabsSuivi from '@/components/etatcommande/MyTabsSuivi.vue';
import { useEtapeStoreAdv } from "@/store/utils/etapeadv.js";
import MyTableSuivi from '@/components/etatcommande/MyTableSuivi.vue';
import { useEtatCommandeStore } from "@/store/SuiviCommande/etatCommande.js";
import { useAxeStore } from "@/store/utils/axe.js";

import MySelect from '@/components/MySelect.vue';

export default defineComponent({
    components: {
        MyTabsSuivi,
        MyTextField,
        MyTableSuivi,
        MySelect,
    },

    methods: {
        onHome() {
            this.$router.push("/AccueilPage");
        },
    },

    setup(props) {

        const isOnline = useOnline();
        const user = localStorage.getItem('username');
        const StoreAdv = useEtapeStoreAdv();
        const store = useEtatCommandeStore();
        const codeCC = ref('');
        const CodeCLT = ref('');
        const nomClient = ref('');
        const selectedAxe = ref('');
        const dateDeb = ref('');
        const dateFin = ref('');
        const list = ref([]);
        const loading = computed(() => store.loading);
        const storeAxe = useAxeStore();


        const convertToUpperCase = () => {
            codeCC.value = codeCC.value.toUpperCase();
            CodeCLT.value = CodeCLT.value.toUpperCase();
        };

        onMounted(async () => {
            const today = dayjs().format('YYYY-MM-DD');
            dateDeb.value = today;
            dateFin.value = today;
            list.value=[];
            await storeAxe.fetchAxe();
        });


        const searchEvent = async () => {
            // console.log(codeCC.value)
            // console.log(CodeCLT.value)
            // console.log(nomClient.value)
            // console.log(selectedAxe.value)
            // console.log(dateDeb.value)
            // console.log(dateFin.value)

            listCC();

            // console.log(listCC())

        }

        const headers = [
            { title: 'CODE', align: 'start', key: 'code', sortable: true },
            { title: 'CODE CLIENT', align: 'center', key: 'code_client', sortable: true },
            { title: 'NOM CLIENT', align: 'start', key: 'nom_client' },
            { title: 'AXE', align: 'start', key: 'axe' },
            { title: 'COMMANDE', align: 'start', key: 'commande' },
            { title: 'VALID_FACT', align: 'center', key: 'validefact' },
            { title: 'MODE', align: 'start', key: 'mode', value: 'mode' },
            { title: 'VALID_ADV', align: 'start', key: 'valid_adv', value: 'valid_adv' },
            { title: 'DEB_PREP', align: 'start', key: 'deb_prep', value: 'deb_prep' },
            { title: 'FIN_PREP', align: 'start', key: 'fin_prep', value: 'fin_prep' },
            { title: 'DEB_CTRL', align: 'start', key: 'deb_ctrl', value: 'deb_ctrl' },
            { title: 'FIN_CTRL', align: 'start', key: 'fin_ctrl', value: 'fin_ctrl' },
            { title: 'FIN_EMB', align: 'start', key: 'fin_emb', value: 'fin_emb' },
            { title: 'SORTIE_FACT', align: 'start', key: 'sortie_fact', value: 'sortie_fact' },
            { title: 'COMMENTAIRE', align: 'start', key: 'commentaire', value: 'commentaire' },
            { title: 'COMMENTAIREMAG', align: 'start', key: 'commentaire_mag', value: 'commentaire_mag' },
            { title: 'SORTIE_OPHAM', align: 'start', key: 'sortie_opham', value: 'sortieopham' },
            { title: 'LIVRÉE PAR', align: 'start', key: 'livree_par', value: 'livree_par' },
            { title: 'VOITURE LIV', align: 'start', key: 'voiture_liv', value: 'voiture_liv' },
            { title: 'RETOUR LIV', align: 'start', key: 'retour_liv', value: 'retour_liv' },
        ];

        //Prend la valeur du variable d'entrée

        const listCC = async () => {
            await store.fetchEtatCommande(codeCC.value, CodeCLT.value, dateDeb.value, dateFin.value, selectedAxe.value)
            if(store.suiviCommandes.length > 0){
                list.value = store.suiviCommandes;
            }
        }


        return {
            isOnline, user,
            StoreAdv,
            headers,convertToUpperCase,searchEvent,
            codeCC,CodeCLT,nomClient,selectedAxe,dateDeb,dateFin,listCC,list,loading,storeAxe,
        };

    }

})

</script>

<style>
.light-background {
    background-color: #eeeeee;
    /* Une couleur de fond claire */
    padding: 20px;
    /* Pour ajouter un peu de padding autour du contenu */
    border-radius: 10px;
    /* Pour arrondir les coins si désiré */
}

.date-container {
    display: flex;
    align-items: center;
}

.date-label {
    margin-right: 10px;
    /* Ajustez cette valeur selon vos besoins */
}

.date-input {
    flex: 1;
}

/* .dezoomed-table-container {
  width: 100%;
  overflow: auto;
}

.dezoomed-table-container > * {
  transform: scale(0.68);
  transform-origin: top left;
  width: 150%;
} */

/* .dezoomed-table-container tr:nth-child(even) {
  background-color: #ffffff;
}

.dezoomed-table-container tr:nth-child(odd) {
  background-color: #4088b8;
}  */

</style>