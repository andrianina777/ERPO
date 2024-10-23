<template>
  <v-card flat>
    <v-card-title class="d-flex align-center pe-2">

      <v-spacer></v-spacer>

      <v-text-field v-model="searchTerm" prepend-inner-icon="mdi-magnify" density="compact" label="Rechercher" flat
        hide-details variant="solo-filled"></v-text-field>
    </v-card-title>

    <v-divider></v-divider>

    <v-data-table class="blue-header" :headers="headers" :items="filteredItems" density="compact" hover="true"
      no-data-text="Pas de données disponibles" :fixed-header="true" :items-per-page="-1" :height="600"
      hide-default-footer>



      <template v-slot:item.depuis="{ item }">
        <template v-if="item.depuis === 0">
        </template>
        <template v-else>
          {{ item.depuis }}
        </template>
      </template>

      <template v-slot:item.numero="{ item }">
        <span>{{ formatNumero(item.numero) }}</span>
      </template>

      <template v-slot:item.action="{ item }">

        <template v-if="item.etat === 1 || item.etat === 2 || item.etat === 3">
          <!-- <v-btn size="x-small" disabled @click="action1PItem(item)">
            {{ action1 }}
          </v-btn> -->
        </template>

        <template v-else>
          <v-btn size="x-small" color="#FFE0B2" @click="action1PItem(item)">
            {{ action1 }}
          </v-btn>
        </template>
      </template>

      <template v-slot:[`item.actions`]="{ item }">
        <v-row align="left" class="d-flex justify-start" density="compact">
          <v-col cols="auto">
            <v-btn size="x-small" color="#FFE0B2" @click="action2PItem(item)">
              {{ action2 }}
            </v-btn>
          </v-col>
        </v-row>
      </template>

      <template v-slot:[`item.calldate`]="{ item }">
        {{ formatDateTime(item.calldate) }}
      </template>

      <template v-slot:[`item.calldate_sortant`]="{ item }">
        {{ formatDateTime(item.calldate_sortant) }}
      </template>


      <template v-slot:item.add="{ item }">
        <template v-if="item.client === null">

          <v-btn size="large" dark variant="text" icon @click="action3PItem(item)">
            <v-icon color="blue">mdi-plus-circle</v-icon>
          </v-btn>

        </template>
        <template v-else>
        </template>
      </template>

      <!-- Action 1er rappel -->
      <template v-slot:item.rappel1="{ item }">
        <template v-if="item.etat === 1">
          <v-btn size="x-small" color="#E0F2F1" @click="action4PItem(item)">
            {{ action4 }}
          </v-btn>
        </template>
        <template v-else>
          <div v-if="item.daterappel_1">
            {{ formatDateTime(item.daterappel_1) }}
            <div v-if="item.poste_rap1"><b>({{ item.poste_rap1 }})</b></div>
          </div>
        </template>
      </template>
      <!--  -->

      <!-- Action 2eme rappel -->
      <template v-slot:item.rappel2="{ item }">
        <template v-if="item.etat === 2">
          <v-btn size="x-small" color="#FFE0B2" @click="action5PItem(item)">
            {{ action5 }}
          </v-btn>
        </template>
        <template v-else-if="item.etat === 3">
          {{ formatDateTime(item.daterappel_2) }}
          <div v-if="item.poste_rap2">({{ item.poste_rap2 }})</div>
        </template>
        <template v-else>
        </template>
      </template>
      <!--  -->
      <!-- {{ formatDateTime(item.daterappel_2) }} -->

      <template v-slot:[`item.action_first`]="{ item }">
        <v-radio></v-radio>
      </template>

      <template v-slot:[`item.select_sortant`]="{ item }">
        <v-select></v-select>
      </template>

    </v-data-table>
  </v-card>
</template>
<script>
import { defineComponent, ref, computed } from 'vue';
import { VDataTable } from "vuetify/lib/components";;
import dayjs from 'dayjs';

export default defineComponent({
  components: {
    VDataTable,
  },

  emits: ['action1Item', 'action2Item', 'action3Item','action4Item', 'action5Item'],

  props: {
    headers: Object,
    items: Array,
    action1: String,
    action2: String,
    action4: String,
    action5: String,

    search: {
      type: String,
      default: '',
    },

    headerColor: {
      String,
      default: 'blue'
    },
  },

  setup(props, { emit }) {

    const searchTerm = ref(props.search.toLowerCase());

    const filteredItems = computed(() => {
      const searchRegex = new RegExp(searchTerm.value, 'i'); // 'i' pour ignorer la casse
      return props.items.filter(item => {
        return Object.values(item).some(value => {
          // Check for null or undefined before applying the regular expression test
          return value !== null && value !== undefined && searchRegex.test(value.toString());
        });
      });
    });


    const action1PItem = (item) => {
      emit('action1Item', item);
    };

    const action2PItem = (item) => {
      emit('action2Item', item);
    };

    const action3PItem = (item) => {
      emit('action3Item', item);
    };

    const action4PItem = (item) => {
      emit('action4Item', item);
    };

    const action5PItem = (item) => {
      emit('action5Item', item);
    };

    const formatDateTime = (date) => {
      if (date !== null && date !== '') {
        return dayjs(date).format('DD/MM/YYYY HH:mm:ss');
      } else {
        return '';
      }

    };

    const formatNumero = (numero) => {
      if (numero && numero.length === 10) {
        return numero.replace(/(\d{3})(\d{2})(\d{3})(\d{2})/, '$1 $2 $3 $4');
      }
      return numero;
    };

    return {
      action1PItem, action2PItem, action3PItem, action4PItem, action5PItem,
      filteredItems, searchTerm, formatDateTime,formatNumero,
    }
  }
})
</script>
<style>
.v-data-table.blue-header .v-table__wrapper>table>thead>tr th,
.v-data-table.blue-header .v-table__wrapper>table tbody>tr th {
  background-color: #b8b3cc;
  white-space: nowrap;
}
</style>
