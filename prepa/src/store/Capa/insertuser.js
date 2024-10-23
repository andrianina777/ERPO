import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useAjoutUserStore = defineStore('AjoutUser', {
  state: () => ({
    AjoutUsers: [],
    loading: false,
    Inserts: [],
  }),
  actions: {
    async fetchAjoutUser(ncpsUserUserCode, ncpsUserNcpsCode) { 
      this.loading = true;   
      try {
        const url = Config.SERVER_URL + `api/insertuser?ncpsUserUserCode=${ncpsUserUserCode}&ncpsUserNcpsCode=${ncpsUserNcpsCode}`;
         
        const response = await axios.post(url); 

        console.log('Response data:', response.data);
        
        if (response.status === 200) {
            this.Inserts = response.data; 
            console.log("Ajout réussi", response.data);
        }
      } catch (error) {
        console.error('Error fetching AjoutUsers:', error);  
      } finally {
        this.loading = false;
        console.log('Loading finished. Current loading state:', this.loading); 
      }
    },
  },
});

