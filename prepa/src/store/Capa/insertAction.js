import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useAjoutActionStore = defineStore('AjoutAction', {
  state: () => ({
    AjoutActions: [],
    loading: false,
    params: { 
        actionNcpscode: '',
        actionDescription: '',
        actionType: '',
        actionCausesseq: '',
    },
  }),
  actions: {
    async fetchAjoutAction() { 
      this.loading = true;
      
      try {

        const url = Config.SERVER_URL + `api/insertaction?actionNcpscode=${this.params.actionNcpscode}&actionDescription=${this.params.actionDescription}&actionType=${this.params.actionType}&actionCausesseq=${this.params.actionCausesseq}`;
         
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
