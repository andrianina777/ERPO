<template>
    <v-card>
        <v-app>
            <v-app-bar :elevation="1" density="compact" color="#d7ead5" app>
                <v-card density="compact">
                    <MyTabsAdv :StoreAdv="useEtapeStoreAdv"></MyTabsAdv>
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
                <div>
                    <v-card-text>
                        <!-- <u>Code Client</u>:--> 
                        <div class="spaceCde"><b> {{ 'DM139' }}</b></div>
                        <v-spacer />
                        <!-- <u>Client</u>:--> 
                        <div class="spaceCde">{{ 'DEPOT RABEMANANJARA MAHAFENO' }}</div>
                        <v-spacer />
                        <!--<u>Date</u> :-->
                        <div class="spaceCde">{{ '29/12/2023' }}</div>
                        <v-spacer />
                        <!-- <u>TOTAL HT</u>: -->
                        <div class="spaceCde">{{'409 900 Ar'}}</div>
                    </v-card-text>
                </div>
                    <v-spacer/>
                    <v-row>
                        <v-col>
                            <!-- <MyList titre_list="Valeur Client " :items_list="list_valeur"></MyList> -->

                            <div class='space0'><b><u>Valeurs Clients</u></b><br></div>

                            <v-card color="#D7CCC8" max-width="190">
                                <div>
                                    <v-btn size="x-small" class="or--text text--or" variant="tonal" @click="OnOR">OR
                                    <!-- <v-badge :content="totalOr" color="#37474F"  inline class="large">
                                    </v-badge>  -->
                                    </v-btn>
                                    <v-btn size="x-small" class="bronze--text text--bronze" variant="tonal" @click="OnBronze">Bronze
                                    <!-- <v-badge :content="totalBronze" color="#37474F"  inline class="large">
                                    </v-badge>  -->
                                    </v-btn>
                                </div>
                                <div>
                                    <v-btn size="x-small" class="argent--text text--argent" variant="tonal" @click="OnArgent">Argent
                                    <!-- <v-badge :content="totalArgent" color="#37474F"  inline class="large">
                                    </v-badge>  -->
                                    </v-btn>
                                    <v-btn size="x-small" class="fer--text text--fer" variant="tonal" @click="OnFer">Fer
                                    <!-- <v-badge :content="totalFer" color="#37474F"  inline class="large">
                                    </v-badge>  -->
                                    </v-btn>
                                </div>
                            </v-card>
                        </v-col>

                        <v-col>
                            <div class="space1"><b><u>ETAT DE PAIEMENTS</u></b><br></div>
                            
                                <v-card color="#D7CCC8" width="510">
                                    <div>
                                        <v-btn size="x-small" class="plafond--text text--plafond" variant="tonal" @click="onUrgApre">PLAFOND DEPASSE + RETARD DE PAIEMENT
                                            <!-- <v-badge :content="totalUP" color="#5D4037"  inline class="large">
                                            </v-badge> -->
                                        </v-btn>
                                        <v-btn size="x-small" class="retard--text text--retard" variant="tonal" @click="onApre">RETARD DE PAIEMENT
                                            
                                            <!-- <v-badge :content="totalP" color="#5D4037"  inline class="large">
                                            </v-badge> -->
                                        </v-btn>
                                    </div>
                                    <div>
                                        <v-btn size="x-small" class="debloq--text text--debloq" variant="tonal" @click="onUrge">A DEBLOQUER SI AU COMPTANT
                                            
                                            <!-- <v-badge :content="totalURG" color="#5D4037"  inline class="large">
                                            </v-badge> -->
                                        </v-btn>
                                        <v-btn size="x-small" class="plafondep--text text--plafondep" variant="tonal" @click="onGar">PLAFOND DEPASSE
                                            
                                            <!-- <v-badge :content="totalGAR" color="#5D4037"  inline class="large">
                                            </v-badge> -->
                                        </v-btn>
                                        <!-- <v-btn size="x-small" class="all--text text--all" variant="tonal" @click="onAll">Tous</v-btn> -->
                                    </div>
                                </v-card>
                                    <!-- <MyList titre_list="Priorité" :items_list="list_priorite"></MyList> -->
                            </v-col>
                    </v-row>
                    <v-spacer/>
                    <v-row>
                        <v-col cols="auto">
                            <v-btn 
                            variant="tonal" 
                            color="primary" 
                            @click="searchEvent" 
                            :disabled="loading"
                            size="large"
                            density="compact">Rechercher
                            </v-btn>
                        </v-col>
                    </v-row>
                </v-card-actions>
                <div class="pa-2">
                  <!-- <v-progress-linear 
                    v-if="loading" 
                    color="primary" 
                    rounded indeterminate 
                    height="5"
                    density="compact">
                  </v-progress-linear> -->

                  <pageImpaye :dialogVisible="dialogVisible" @closeDialog="dialogVisible = false"></pageImpaye>
                  
                <v-progress-linear v-if="loading" color="primary" rounded indeterminate height="5" density="compact"></v-progress-linear>

                <MyTableAdv v-if="!loading" :headers="headers" :items="store.validations" height="590"
                action1="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;impayés&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"  @action1Item="showImpayé" :showAction1="true"  
                action2="Règlement fait" :showAction2="true"
                action3="&nbsp;&nbsp;&nbsp;valider&nbsp;&nbsp;&nbsp;" :showAction3="true"
                action4="En attente" :showAction4="true"
                >
                </MyTableAdv>

               

                <v-card>
                    <v-row>
                        <v-col>
                            <v-sheet class="pl-2">
                            <b> {{ 0 }}</b> Lignes Trouvées
                            </v-sheet>
                        </v-col>
                    </v-row>
                </v-card>
                </div>
                <v-row>
                    <!-- <v-col cols="10" md="3">
                        <MyTextarea name_lab_area="Commentaires Client"></MyTextarea>
                    </v-col>
                    <v-col cols="10" md="3">
                        <MyTextarea name_lab_area="Commentaires Magasin"></MyTextarea>
                    </v-col>
                    <v-col cols="10" md="3">
                        <v-btn>Valider Commentaires</v-btn>
                    </v-col> -->
                    <!-- <v-col>
                        <v-card-actions class="d-flex justify-center">
                            <v-btn variant="outlined" color="" @click="searchEvent" :disabled="loading" density="compact" >
                                Impayés
                            </v-btn>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <v-btn variant="outlined" color="green-darken-3" @click="searchEvent" :disabled="loading" density="compact">
                                Valider
                            </v-btn>
                            </v-card-actions> 
                            <v-card-actions class="d-flex justify-center">
                            <v-btn variant="outlined" color="" @click="searchEvent" :disabled="loading" density="compact" >
                                Réglement fait
                            </v-btn>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <v-btn variant="outlined" color="green-darken-3" @click="searchEvent" :disabled="loading" density="compact">
                                En attente
                            </v-btn>
                        </v-card-actions> 
                    </v-col>  -->
                </v-row> 
            </v-main>
        </v-app>
    </v-card>
</template>
<script>
  import { defineComponent , ref , computed ,onMounted} from "vue";
  import { useRouter } from 'vue-router'
  import { useOnline } from '@vueuse/core';
  import MyTabsAdv from '@/components/adv/MyTabsAdv.vue';
  import MyTableAdv from '@/components/adv/MyTableAdv.vue'
  import { useEtapeStoreAdv } from "@/store/utils/etapeadv.js";
  import MyTextarea from '@/components/MyTextarea.vue';
  import { useValidationCommandeStore } from "@/store/Adv/adv.js";
  

  import pageImpaye from '@/views/adv/PageImpaye.vue';

  
export default defineComponent({
    components: {
        MyTabsAdv,
        MyTableAdv,
        MyTextarea,
        pageImpaye,
    
    },

    methods: {
      onHome() {
        this.$router.push("/AccueilPage");
      },
    },

    setup(props) {

        const isOnline = useOnline();
        const router = useRouter();
        const user = localStorage.getItem('username');
        const loading = computed(() => store.loading)
        const StoreAdv = useEtapeStoreAdv();
        const store = useValidationCommandeStore();
        const dialogVisible = ref(false);


  
        const headers = [
                { title: 'Etat', align: 'start', key: 'code_traite', sortable: true },
                { title: 'Client', align: 'center', key: 'date_com', sortable: true },
                { title: 'Condition client', align: 'center', key: 'date_prevue_liv' },
                { title: 'Condition cde', align: 'start', key: 'nom_client' },
                { title: 'Date', align: 'start', key: 'type_tana'},
                { title: 'Total HT', align: 'center', key: 'axe' },
                { title: 'Plafond', align: 'end', key: 'depuis_adv' },
                { title: 'Total Impayé', align: 'center', key: 'depuis_prepa' },
                { title: 'Total Retard', align: 'end', key: 'duree_atte_prep' },
                { title: 'Type', align: 'center', key: 'mag_restant' },
                { title: 'LIVRAISON', align: 'start', key: 'liv', value: 'liv' },
                { title: 'Adresse LIV', align: 'start', key: 'adresse_liv', value: 'adresse_liv' },
                { title: 'Valeurs Clients', align: 'start', key: 'valeur', value: 'action' },
                { title: 'Actions', align: 'start', key: 'action1', value: 'action1' },
                { title: '', align: 'start', key: 'actions', value: 'actions' },
            ]


            onMounted(async () => {
                await run_list_adv();
            })

            const searchEvent = async () => {
                run_list_adv();
            }

            const showImpayé = async (item) => {
                console.log('tonga eto impayé')
                dialogVisible.value = true;
            }

            const run_list_adv = async () => {
                // console.log('tonga eto ty ')
                await store.fetchCommandeValidation();
            } 
    
        
        return {
            StoreAdv,headers,isOnline,user,
            searchEvent,run_list_adv,
            store,loading,
            showImpayé,dialogVisible,

        };
    },
})
</script>

<style>

.plafond--text.text--plafond{
    background-color:#FF0000;
    color: rgb(255, 255, 255) !important;
    width: 250px;
}

.retard--text.text--retard{
    background-color:rgb(255, 204,153);
    color: rgb(7, 7, 7) !important;
    width: 250px;
}

.debloq--text.text--debloq{
    background-color:rgb(0, 187,0);
    color: rgb(7, 7, 7) !important;
    width: 250px;
}

.plafondep--text.text--plafondep{
    background-color:rgb(255, 153,204);
    color: rgb(7, 7, 7) !important;
    width: 250px;
}


.space0 {
    width: 100px;
    margin-left: 50px;
}

.space1 {
    width: 150px;
    margin-left: 150px;
}

.spaceCde {
    margin-left: 2em;
}

</style>