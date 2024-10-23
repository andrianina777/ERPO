import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useActionStore = defineStore('Action', {
  state: () => ({
    Actions: [],
    loading: false,
    params: { 
      actionCauseseq: ''
    },
  }),
  actions: {
    async fetchAction(actionCauseseq) { 
      this.loading = true;
      console.log(`Fetching Actions with actionCauseseq: ${actionCauseseq}`);  
      try {

        const url = Config.SERVER_URL + `api/action?actionCauseseq=${actionCauseseq}`;
        
        const response = await axios.get(url);
        
        if (response.status === 404) {
          this.Actions = [];  
        }
        
        if (Array.isArray(response.data)) {
          this.Actions = response.data;
        } else {
          this.Actions = [];
          console.log('No valid data received. Actions set to empty array.');
        }
      } catch (error) {
        console.error('Error fetching Actions:', error);  
      } finally {
        this.loading = false;
        console.log('Loading finished. Current loading state:', this.loading); 
      }
    },
  },
});
