import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useCapaDetailStore = defineStore('capaDetail', {
  state: () => ({
    capaDetails: [],
    loading: false,
    params: { 
      code: '',
      description: '',
      emetteur: '',
      libelleDep: '',
      type: '',
      date: null, 
      causevalid: '',
      causeatte: '',
      attevalid: '',
      attemiseappli: '',
      appli: ''
    },
  }),
  actions: {
    async fetchCapaDetails(code, description, emetteur, libelleDep, type, date, causevalid, causeatte, attevalid, attemiseappli, appli) { 
      this.loading = true;
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/capa', {
          params: {
            code,
            description,
            emetteur,
            libelleDep,
            type,
            date,
            causevalid,
            causeatte,
            attevalid,
            attemiseappli,
            appli
          }
        });


        if (Array.isArray(response.data)) {
          this.capaDetails = response.data;
        } else {
          this.capaDetails = [];
        }
      } catch (error) {
        this.capaDetails = [];
        if (error.response && error.response.status === 404) {
          console.log('Aucune correspondance trouvée (404 Not Found)');
          this.capaDetails = [];
        } else {
          console.error('Une erreur s\'est produite lors de la récupération des données', error);
        }
      } finally {
        this.loading = false;
      }
    },
  },
});
