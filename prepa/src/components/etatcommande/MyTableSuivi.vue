<template>
    <!-- <v-card-title class="d-flex align-center pe-2">

<v-spacer></v-spacer>

<v-text-field v-model="searchTerm" prepend-inner-icon="mdi-magnify" density="compact" label="Rechercher" flat
  hide-details variant="solo-filled"></v-text-field>
</v-card-title> -->

    <v-data-table class="custom-table" :headers="headers" :fixed-header="true" v-if="!loading" :loading="loading"
        density="compact" :items="items" :items-per-page="-1" hover="true" no-data-text="Pas données disponibles"
        v-model:search="search" hide-default-footer>



        <template v-slot:[`item.validefact`]="{ item }">
            {{ formatDateTime(item.validefact) }}
        </template>

        <template v-slot:[`item.valid_adv`]="{ item }">
            {{ formatDateTime(item.valid_adv) }}
        </template>

        <template v-slot:[`item.deb_prep`]="{ item }">
            {{ formatDateTime(item.deb_prep) }}
        </template>

        <template v-slot:[`item.fin_prep`]="{ item }">
            {{ formatDateTime(item.fin_prep) }}
        </template>

        <template v-slot:[`item.deb_ctrl`]="{ item }">
            {{ formatDateTime(item.deb_ctrl) }}
        </template>

        <template v-slot:[`item.fin_ctrl`]="{ item }">
            {{ formatDateTime(item.fin_ctrl) }}
        </template>

        <template v-slot:[`item.fin_emb`]="{ item }">
            {{ formatDateTime(item.fin_emb) }}
        </template>

        <template v-slot:[`item.sortie_fact`]="{ item }">
            {{ formatDateTime(item.sortie_fact) }}
        </template>

        <template v-slot:[`item.sortie_opham`]="{ item }">
            {{ formatDateTime(item.sortie_opham) }}
        </template>

        <template v-slot:[`item.retour_liv`]="{ item }">
            {{ formatDateTime(item.retour_liv) }}
        </template>


    </v-data-table>

</template>

<script>
import { defineComponent, ref } from 'vue';
import { VDataTable } from "vuetify/lib/components";
import dayjs from 'dayjs';

export default defineComponent({
    components: {
        VDataTable,
    },
    props: {
        headers: Object,
        items: Object,
        search: String,
        loading: {
            type: Boolean,
            default: false
        },
    },
    setup() {
        const formatDateTime = (date) => {
            if (date !== null && date !== '') {
                return dayjs(date).format('DD/MM/YYYY HH:mm');
            } else {
                return '';
            }
        };

        return { formatDateTime };
    }
});
</script>

<style scoped>
/* Styles spécifiques à ce composant */
.custom-table .v-data-table__wrapper>table>thead>tr th,
.custom-table .v-data-table__wrapper>table tbody>tr th {
    background-color: #b8b3cc;
    white-space: nowrap;
}

/* Autres styles pour ce composant uniquement */
.custom-table tbody tr:nth-of-type(even) {
    background-color: #f0f0f0;
}

.custom-table tbody tr:nth-of-type(odd) {
    background-color: rgba(0, 0, 0, 0.05);
}
</style>
