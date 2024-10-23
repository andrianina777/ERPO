import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useUpdateCauseStore = defineStore('UpdateCause', {
  state: () => ({
    UpdateCauses: [],
    loading: false,
    params: { 
      causeRacSeq: '',
      causeRacCode: '',
      causeRacDate: '',
      causeRacDescription: '',
      causeRacProcImput: '', 
      causeRacValide: '',
    },
  }),
  actions: {
    async updateCause() { 
      this.loading = true;
      try {
        const url = Config.SERVER_URL + `api/updatecuserac?causeRacSeq=${this.params.causeRacSeq}&causeRacCode=${this.params.causeRacCode}&causeRacDate=${this.params.causeRacDate}&causeRacDescription=${this.params.causeRacDescription}&causeRacProcImput=${this.params.causeRacProcImput}&causeRacValide=${this.params.causeRacValide}`;
        
        const response = await axios.put(url);
        
        if (response.status === 200) {
          this.UpdateCauses = response.data; 
          console.log("Mise à jour réussie", response.data);
        }
      } catch (error) {
        console.error("Erreur lors de la mise à jour :", error);
      } finally {
        this.loading = false;
      }
    },
  },
});
