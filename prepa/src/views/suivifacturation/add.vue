<template>
    <v-card>
        <v-card-title class="custom-card-dm">
            <div class="d-flex justify-space-between align-center">
                <span class="text-h5"> <u>Ajouter nom du client </u> :{{ Tnumero }} </span>
                <v-icon @click="closeDetail">mdi-close</v-icon>
            </div>

        </v-card-title>
        <div class="pa-2">
            <!-- <v-progress-linear 
                    v-if="loading" 
                    color="primary" 
                    rounded indeterminate 
                    height="5"
                    density="compact">
                  </v-progress-linear> -->

            <v-card>

                <!-- <MyTableFirst :headers="headers" :items="list" v-model="selected" return-object :justTwo="1">
                </MyTableFirst> -->

                <MyTableFirst :headers="headers" :items="list">
                </MyTableFirst>

            </v-card>
        </div>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="#B71C1C" variant="outlined" @click="annuler">
                Annuler
            </v-btn>
            <v-btn color="#26A69A" variant="outlined" @click="save">
                Enregistrer
            </v-btn>
        </v-card-actions>
    </v-card>
</template>
<script>
import { defineComponent, ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAppelSortantStore } from "@/store/SuiviFacturation/appelSortant";
import MyTableFirst from '@/components/facturio/MyTableFirst.vue';
export default defineComponent({
    components: {
        MyTableFirst,
    },

    props: {
        numero: String,
        seqEntrant: Number,
    },

    setup(props, { emit }) {
        const dialogVisible = ref(false);
        const list = ref([]);
        const store = useAppelSortantStore();

        onMounted(() => {
            listClient();
        });

        const listClient = async () => {
            await store.fetchlListClient()
            list.value = store.ListClients
        }

        const annuler = () => {
            dialogVisible.value = false;
            emit('annuler');
        };

        const closeDetail = () => {
            dialogVisible.value = false;
            emit('annuler');
        };

        const headers = [
            // { title: 'SEQ', align: 'center', key: 'seq', sortable: true },
            { title: 'CODE CLIENT', align: 'center', key: 'code_client', sortable: true },
            { title: 'CLIENT', align: 'start', key: 'nom_client', sortable: true },
        ];

        return {
            dialogVisible,
            headers,
            annuler, closeDetail,
            listClient, list,
        };
    }
})
</script>