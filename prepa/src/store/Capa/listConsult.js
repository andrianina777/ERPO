import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useConsultStore = defineStore('Consult', {
  state: () => ({
    Consults: [],
    loading: false,
    params: { 
        ncpsUserNcpsCode: ''
    },
  }),
  actions: {
    async fetchConsult(ncpsUserNcpsCode) { 
      this.loading = true;
      try {

        const url = Config.SERVER_URL + `api/listuserconsult?ncpsUserNcpsCode=${ncpsUserNcpsCode}`;
        
        const response = await axios.get(url);
        
        if (response.status === 404) {
          this.Consults = [];  
        }
        
        if (Array.isArray(response.data)) {
          this.Consults = response.data;
        } else {
          this.Consults = [];
          console.log('No valid data received. Consults set to empty array.');
        }
      } catch (error) {
        console.error('Error fetching Consults:', error);  
      } finally {
        this.loading = false;
        console.log('Loading finished. Current loading state:', this.loading); 
      }
    },
  },
});
