import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useAnaActionStore = defineStore('AnaAction', {
  state: () => ({
    AnaActions: [],
    loading: false,
    params: { 
        actionSeq: '',
        actionDaterelleeffic: '',
        actionCommentaire: '',
    },
  }),
  actions: {
    async anaAction() { 
      this.loading = true;
      try {
        const url = Config.SERVER_URL + `api/updateaction3?actionSeq=${this.params.actionSeq}&actionDaterelleeffic=${this.params.actionDaterelleeffic}&actionCommentaire=${this.params.actionCommentaire}`;
        
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
