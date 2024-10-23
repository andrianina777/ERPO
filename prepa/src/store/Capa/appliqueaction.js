import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useApplActionStore = defineStore('ApplAction', {
  state: () => ({
    ApplActions: [],
    loading: false,
    params: { 
        actionSeq: '',
        actionDateanaleffic: '',
        actionDaterelleeffic: '',
    },
  }),
  actions: {
    async applAction() { 
      this.loading = true;
      try {
        const url = Config.SERVER_URL + `api/updateaction2?actionSeq=${this.params.actionSeq}&actionDateanaleffic=${this.params.actionDateanaleffic}&actionDaterelleeffic=${this.params.actionDaterelleeffic}`;
        
        const response = await axios.put(url);
        
        if (response.status === 200) {
          this.ApplActions = response.data; 
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
