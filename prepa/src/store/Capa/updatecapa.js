import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useUpdateCapaStore = defineStore('UpdateCapa', {
  state: () => ({
    UpdateCapas: [],
    loading: false,
    params: { 
      ncpsSeq: '',
      ncpsCode: '',
      ncpsDate: '',
      ncpsDescription: '', 
      ncpsDepartement: '',
      ncpsType: '',  
      ncpsModeDetect: '',
      ncpsProcessusDetect: '',
    },
  }),
  actions: {
    async updateCapa() { 
      this.loading = true;
      try {
        const url = Config.SERVER_URL + `api/updatecapa?ncpsSeq=${this.params.ncpsSeq}&ncpsCode=${this.params.ncpsCode}&ncpsDate=${this.params.ncpsDate}&ncpsDescription=${this.params.ncpsDescription}&ncpsDepartement=${this.params.ncpsDepartement}&ncpsType=${this.params.ncpsType}&ncpsModeDetect=${this.params.ncpsModeDetect}&ncpsProcessusDetect=${this.params.ncpsProcessusDetect}`;
        
        const response = await axios.put(url);
        
        if (response.status === 200) {
          this.UpdateCapas = response.data; 
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
