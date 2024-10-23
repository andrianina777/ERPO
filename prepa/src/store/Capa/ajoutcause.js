import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useAjoutCauseStore = defineStore('AjoutCause', {
  state: () => ({
    AjoutCauses: [],
    loading: false,
    params: { 
        causeRacCode: '',
        causeRacDescription: '',
        causeRacDate: ''
    },
  }),
  actions: {
    async fetchAjoutCause(causeRacCode, causeRacDescription, causeRacDate) { 
      this.loading = true;
      console.log(`CauseRac avec code: ${causeRacCode}`); 
      console.log(`CauseRac avec description: ${causeRacDescription}`);
      console.log(`CauseRac avec date: ${causeRacDate}`);
      
      try {
        this.params.causeRacCode = causeRacCode;
        this.params.causeRacDescription = causeRacDescription;
        this.params.causeRacDate = causeRacDate;
        console.log('code :'+ this.params.causeRacCode);
        console.log('desc :'+ this.params.causeRacDescription);
        console.log('date :'+ this.params.causeRacDate);

        const url = Config.SERVER_URL + `api/insertcauserac?causeRacCode=${this.params.causeRacCode}&causeRacDescription=${this.params.causeRacDescription}&causeRacDate=${this.params.causeRacDate}`;
         
        //const url = `http://192.168.130.156:12800/api/insertcauserac?causeRacCode=${causeRacCode}&causeRacDescription=${causeRacDescription}&causeRacDate=${causeRacDate}`;

        const response = await axios.post(url); 

        console.log('Response data:', response.data);
        
        if (response.status === 200) {
            this.Inserts = response.data; 
            console.log("Ajout réussi", response.data);
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
