import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useCauseRacStore = defineStore('CauseRac', {
  state: () => ({
    CauseRacs: [],
    loading: false,
    params: { 
      code_causerac: ''
    },
  }),
  actions: {
    async fetchCauseRac(code_causerac) { 
      this.loading = true;
      console.log(`Fetching CauseRac with code_causerac: ${code_causerac}`);  
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/causeRac', {
          params: {
            code_causerac
          }
        });
        
        if (Array.isArray(response.data)) {
          this.CauseRacs = response.data;

        } else {
          this.CauseRacs = [];
          console.log('No valid data received. CauseRacs set to empty array.');
        }
      } catch (error) {
        console.error('Error fetching CauseRac:', error);  
      } finally {
        this.loading = false;
        console.log('Loading finished. Current loading state:', this.loading); 
      }
    },
  },
});
