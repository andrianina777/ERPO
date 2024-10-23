import Config from '@/utils/config.js';
import { defineStore } from 'pinia';
import axios from '@/utils/axiosConfig';

export const useDeleteActionStore = defineStore('DeleteAction', {
  state: () => ({
    DeleteActions: [],
    loading: false,
    params: { 
      actionSeq: '',
    },
  }),
  actions: {
    async deleteAction() { 
      this.loading = true;
      try {
        const url = Config.SERVER_URL + `api/deleteactionc?actionSeq=${this.params.actionSeq}`;
        
        const response = await axios.delete(url);
        
        if (response.status === 200) {
          this.DeleteActions = response.data; 
          console.log("Suppression réussie", response.data);
        }
      } catch (error) {
        console.error("Erreur lors de la mise à jour :", error);
      } finally {
        this.loading = false;
      }
    },
  },
});
