import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useUpdateActionStore = defineStore('UpdateAction', {
  state: () => ({
    UpdateActions: [],
    loading: false,
    params: { 
        actionSeq: '',
        actionResponsable: '',
        actionDateprev: '',
    },
  }),
  actions: {
    async updateAction() { 
      this.loading = true;
      try {
        const url = Config.SERVER_URL + `api/updateaction?actionSeq=${this.params.actionSeq}&actionResponsable=${this.params.actionResponsable}&actionDateprev=${this.params.actionDateprev}`;
        
        const response = await axios.put(url);
        
        if (response.status === 200) {
          this.UpdateActions = response.data; 
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
