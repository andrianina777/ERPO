<template >
    <v-data-table class="blue-header" :headers="headers" :items="items" :fixed-header="true" v-if="!loading"
        :loading="loading" density="compact" hide-default-footer :etape="etape" hover="true"
        no-data-text="Pas données disponibles" :items-per-page="-1">

        <template v-slot:[`item.actions`]="{ item }">
            <template v-if="showAction1">
                <v-btn size="x-small"   block variant="flat" color="#FFECB3"  @click="action1PItem(item)" style="margin-top:2px;margin-bottom:2px" >
                    {{ action1 }}
                </v-btn>
                <v-spacer />
            </template>

            <template v-if="showAction3" >
                <v-btn  variant="flat"   block size="x-small" color="#FFF8E1"  @click="action3PItem(item)" >
                    {{ action3 }}
                </v-btn>
            </template>

            <v-spacer />
            <v-btn   variant="flat"   block size="x-small" color="#FFF8E1"   @click="action2PItem(item)" style="margin-top:2px;margin-bottom:2px"  >
                {{ action2 }}
            </v-btn>

        </template>

        <template v-slot:[`item.date_com`]="{ item }">
            <!-- <v-chip label :color="getColor(item.valeur)" text-color="#000000">
                {{ item.date_com }}
                {{ formatDate(item.date_com) }}
            </v-chip> -->
            <v-btn size="small" variant="flat" :color="getColor(item.valeur)" class="custom-button">
                {{ formatDate(item.date_com) }}
            </v-btn>
        </template>

        <template v-slot:[`item.debut_prep_rayon`]="{ item }">
            <v-btn size="small" variant="flat" class="custom-button">
                {{ formatDateTime(item.debut_prep_rayon) }}
            </v-btn>
        </template>

        <template v-slot:[`item.fin_prep_rayon`]="{ item }">
            <v-btn size="small" variant="flat" class="custom-button">
                {{ formatDateTime(item.fin_prep_rayon) }}
            </v-btn>
        </template>

        <template v-slot:[`item.debut_ctrl`]="{ item }">
            <v-btn size="small" variant="flat" class="custom-button">
                {{ formatDateTime(item.debut_ctrl) }}
            </v-btn>
        </template>

        <template v-slot:[`item.fin_ctrl`]="{ item }">
            <v-btn size="small" variant="flat" class="custom-button">
                {{ formatDateTime(item.fin_ctrl) }}
            </v-btn>
        </template>


        <template v-slot:[`item.codeCC_traite`]="{ item }">
            <v-btn size="small" variant="flat" :color="getColor(item.valeur)" class="custom-button">
                {{ (item.codeCC_traite) }}
            </v-btn>
        </template>

        <template v-slot:[`item.resume`]="{ item }">
            <b>
                <span style="color: red;">
                    {{ item.resume }}
                </span>
            </b>
        </template>

        <template v-slot:[`item.priori`]="{ item }">
            <v-avatar :color=getUrgent(item) rounded="0" size="14"></v-avatar>
        </template>

        <template v-slot:[`item.val_client`]="{ item }">
            <v-avatar :color="getColor(item.valeur)" rounded="10" size="16"></v-avatar>
        </template>

        <template v-slot:[`item.code`]="{ item }">
            <!-- <span :style="{ color: getUrgent(item) }"> {{ item.code }}</span> -->
            <!-- <v-btn size="small" :color= getUrgent(item) class="custom-button"> -->
            {{ item.code }}
            <!-- </v-btn> -->

        </template>

        <template v-slot:[`item.situation_mag`]="{ item }">
            <!-- <span :style="{ color: getUrgent(item) }"> {{ item.situation_mag }}</span> -->
            <v-btn size="small" variant="flat" :color=getUrgent(item) class="custom-button">
                {{ item.situation_mag }}
            </v-btn>
        </template>

        <template v-slot:[`item.situation_Mag`]="{ item }">
            <v-btn size="small" variant="flat" :color=getUrgent(item) class="custom-button">
                {{ item.situation_Mag }}
            </v-btn>
        </template>

        <template v-slot:[`item.situation_exp`]="{ item }">
            <!-- <span :style="{ color: getUrgent(item) }"> {{ item.situation_exp }}</span> -->
            <v-btn size="small" variant="flat" :color=getUrgent(item) class="custom-button">
                {{ item.situation_exp }}
            </v-btn>

        </template>


        <template v-slot:[`item.etat`]="{ item }">
            <v-checkbox-btn v-model="item.etat" color="primary" hide-details
                :disabled="item.transfert === 'Transfert' || item.transfert === 'Sans Empl' || item.transfert === 'Porte'"
                @click="handleCheckboxClick(item)"></v-checkbox-btn>
        </template>

        <!-- <template v-slot:[`header.etat`]="{ headers }">
            <span v-if="headers">{{ headers.text }}</span>
            <v-checkbox v-model="selectAll" :indeterminate="indeterminate" :label="headers.text"
                :disabled="items.length == 0" @click.native.stop @change="toggleAll"></v-checkbox>
        </template> -->


        <template #footer>
            <div class="text-center">
                This is the footer text.
            </div>
        </template>


        <template v-slot:[`item.code_BP`]="{ item }">
            <b>
                <span style="color: #01579B;">
                    {{ item.code_BP }}
                </span>
            </b>
        </template>

        <template v-slot:[`item.coms`]="{ item }">
            <b>
                <span style="color: red;">
                    {{ item.coms }}
                </span>
            </b>
        </template>


        <!-- <template v-slot:item.date_prevue_liv="{ item }">
            {{ formatDate(item.date_prevue_liv) }}
        </template> -->
        <template v-slot:[`item.date_prevue_liv`]="{ item }">
            {{ formatDateTime(item.date_prevue_liv) }}
        </template>
        <template v-slot:[`item.datecre_BP`]="{ item }">
            {{ formatDateTime(item.datecre_BP) }}
        </template>

        <template v-slot:[`item.date_entree`]="{ item }">
            {{ formatDateTime(item.date_entree) }}
        </template>
        <!-- <template v-slot:[`item.date_com`]="{ item }">
            {{ formatDate(item.date_com) }}
        </template> -->
        <!-- <span>{{ useDateTime(value).dateTime.value.slice(1, -1) }}</span> -->


        <!-- <template #headerCell="{ header }">
            <v-checkbox v-if="header.value === 'select'" :input-value="selectAll" hide-details @click.stop></v-checkbox>
            <span v-else>{{ header.text }}</span>
        </template> -->

        <!-- <template #item="{ item }">
            <tr :style="{color : getUrgent(item)}">
                <td>
                    {{ item.code }}
                </td>
                <td>
                    {{ item.date_com }}
                </td>
                <td>{{ item.date_prevue_liv }}</td>
                <td>{{ item.nom_client }}</td>
                <td>{{ item.commentaire }}</td>
                <td>{{ item.axe }}</td>
                <td>{{ item.duree_atte_prep }}</td>
                <td>{{ item.resume }}</td>
                <td>{{ item.nbLigne }}</td>
                <td>{{ item.situationmag }}</td>
                <td>{{ item.actions }}</td>
            </tr>
        </template> -->
        <!-- <span :style="{ color: item.nbLigne > 3 ? 'green' : 'red' }">{{ item.date_com }}</span> -->

    </v-data-table>
    <v-card density="compact" v-if="showNbLignes">
        <v-card-text density="compact" class="pa-1">
            <b density="compact"> {{ totalArticles }}</b> Lignes Trouvées </v-card-text>
    </v-card>
</template>
<script>
import { defineComponent } from 'vue';
import {
    VDataTableVirtual,
    VDataTable,
    VDataTableServer,
} from "vuetify/lib/components";
import { useAlerteStore } from "@/store/utils/alerte.js"
import { ref, onMounted, onBeforeMount, computed } from 'vue';
import dayjs from 'dayjs';

export default defineComponent({
    components: {
        VDataTable,
        VDataTableServer,
        VDataTableVirtual,
    },
    emits: ['action1Item', 'action2Item', 'handleCheckboxClick'],

    props: {
        headers: Object,
        items: Array,
        headerColor: {
            String,
            default: 'blue'
        },

        loading: {
            type: Boolean,
            default: false
        },
        action1: String,
        action2: String,
        action3: String,
        titre_list: String,
        itemslist: Object,
        header_text: String,
        alerte: String,
        etape: String,

        showAction1: {
            type: Boolean,
            default: false
        },

        showAction3: {
            type: Boolean,
            default: false
        },
        showNbLigne: {
            type: Boolean,
            default: true
        }
    },
    defaults: {
        VDataTable: {
            fixedHeader: true,
            noDataText: 'Pas de resultats',
        },
    },

    setup(props, { emit }) {
        // const m_date = ref(null);
        // const useformatDate = useformatDate();
        const showNbLignes = ref(true);
        showNbLignes.value = props.showNbLigne
        const storeAlert = useAlerteStore();
        const loading = ref(false);
        const alerte = ref('');
        const action1PItem = (item) => {
            emit('action1Item', item);
        };
        const action2PItem = (item) => {
            emit('action2Item', item);
        };

        const action3PItem = (item) => {
            emit('action3Item', item);
        };

        const handleCheckboxClick = (item) => {
            emit('handleCheckboxClick', item);
        };
        const emitEvent = () => {
            emit('my-alert', alerte);
            emit('my-val', storeAlert.alertes.val)
        };

        const fetchAlerte = async () => {
            storeAlert.params.code = props.etape;
            if (props.etape) {
                await storeAlert.fetchAlerte();
                alerte.value = storeAlert.alertes.alerte;
            }
        }


        const formatDateTime = (date) => {
            if (date !== null && date !== '') {
                return dayjs(date).format('DD/MM/YYYY HH:mm');
            } else {
                return '';
            }

        };

        const formatDate = (date) => {
            if (date !== null && date !== '') {
                return dayjs(date).format('DD/MM/YYYY');
            } else {
                return '';
            }

        };


        const getColor = (valeur) => {
            if (valeur.trim() == "OR") return '#ff66ff';
            else if (valeur.trim() == "ARGENT") return '#ffff00';
            else if (valeur.trim() == "BRONZE") return '#99ffff';
            else if (valeur.trim() == "FER") return '#c0c0c0';
        };
        onMounted(async () => {
            await fetchAlerte();
            emitEvent();

        })

        const totalArticles = computed(() => {
            return props.items.length;
        });

        const getUrgent = (item) => {

            if (item.prioritaire == 1 && item.province == 1 && item.duree_atte_prep >= alerte.value) {
                item.urgent = 1

            }
            if ((item.urgent + item.aprendre) == 2) {
                return '#FF0000';
            }
            else if (item.urgent == 0 && item.aprendre == 0 && item.garde == 1) {
                return '#ff9933';
            }
            else if (item.urgent == 1 && item.aprendre == 0) {
                return '#00cc00';
            }
            else if (item.urgent == 0 && item.aprendre == 1) {
                return '#0099ff';
            }

        };

        return { action1PItem, action2PItem, action3PItem, getColor, getUrgent, fetchAlerte, emitEvent, handleCheckboxClick, formatDate, formatDateTime, totalArticles, showNbLignes };
    }

});
</script>
  

<style>
.v-data-table.blue-header .v-table__wrapper>table>thead>tr th,
.v-data-table.blue-header .v-table__wrapper>table tbody>tr th {
    background-color: #b8b3cc !important;
    white-space: nowrap;
    color: #000000 !important;
}



/* .headerColor {
    background-color: #b6b5b6;
} */


/** Manisy barre vertical am tableau */
.v-data-table tr>td:first-child {
    border-left: 4px solid #969696;
    /* border-right: 1px solid #dddddd; */
    /* border-bottom: 2px solid #dddddd; */

}

/* .v-data-table tr >td:nth-child(n+3) {
border-left: 1px solid #dddddd;
  border-right: 1px solid #dddddd;
} */

.v-data-table tr>td:last-child {
    /* border-left: 1px solid #dddddd; */
    border-right: 4px solid #969696;
    /* border-bottom: 1px solid #dddddd; */
   
}


/* .v-data-table tr >td:nth-child(n+3) {
    border-bottom: 1px solid #dddddd;
    background: lightgray
} */

.custom-button {
    pointer-events: none;
    /* Désactive les interactions avec le bouton */
    opacity: 1;
    /* Garde l'opacité du bouton à 100% */
    cursor: not-allowed;
    /* Curseur de souris indiquant que le bouton est désactivé */
    color: black;
    border: none;
    border-radius: 2px;
    width: 120px;

}


/* .v-table > .v-table__wrapper > table > tbody > tr:nth-child(-n+2) td{
      background: #e1eed7;
    } */
</style>
