import Config from '@/utils/config.js';
import { defineStore } from 'pinia';
import axios from '@/utils/axiosConfig';

export const useDeleteCauseStore = defineStore('DeleteCause', {
  state: () => ({
    DeleteCauses: [],
    loading: false,
    params: { 
      causeracseq: '',
    },
  }),
  actions: {
    async deleteCause() { 
      this.loading = true;
      try {
        const url = Config.SERVER_URL + `api/deletecauserac?causeracseq=${this.params.causeracseq}`;
        
        const response = await axios.delete(url);
        
        if (response.status === 200) {
          this.DeleteCauses = response.data; 
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
