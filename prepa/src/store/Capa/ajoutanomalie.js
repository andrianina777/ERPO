import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useInsertStore = defineStore('Insert', {
    state: () => ({
      Inserts: [],
      loading: false,
      params: { 
        ncpsEmetteur: '',
        ncpsDescription: '', 
        ncpsDepartement: '',
        ncpsDate: '',
      },
    }),
    actions: {
      async insert(ncpsEmetteur, ncpsDescription, ncpsDepartement, ncpsDate) { 
        this.loading = true;
        try {
          this.params.ncpsEmetteur = ncpsEmetteur;
          this.params.ncpsDescription = ncpsDescription;
          this.params.ncpsDepartement = ncpsDepartement;
          this.params.ncpsDate = ncpsDate;
  
          const url = Config.SERVER_URL + `api/insertcapa?ncpsEmetteur=${this.params.ncpsEmetteur}&ncpsDate=${this.params.ncpsDate}&ncpsDescription=${this.params.ncpsDescription}&ncpsDepartement=${this.params.ncpsDepartement}`;
          
          const response = await axios.post(url); 
          
          if (response.status === 200) {
            this.Inserts = response.data; 
            console.log("Ajout réussi", response.data);
          }
        } catch (error) {
          console.error("Erreur lors de l'ajout :", error);
        } finally {
          this.loading = false;
        }
      },
    },
  });
  