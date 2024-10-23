import { defineStore } from 'pinia';
import Config from "@/utils/config.js";
import axios from "@/utils/axiosConfig";

export const useCommandeStore = defineStore("commandes", {
  state: () => ({
    commandes: [],
    PP: '',
    loading: false,
    params: {
      groupe: '',
      dateliv: null,
    },
  }),
  actions: {
    async fetchCommande() {
      this.loading = true;
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/prep', { params: this.params });
        if(Array.isArray(response.data)){
          this.commandes = response.data;
        } else {
          this.commandes = [];
        }
      } catch (error) {
        console.error(error);
        // Handle the error gracefully, e.g., display an error message.
      } finally {
        this.loading = false;
      }
    },


    async fetchPP() {
      this.loading = true;
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/check_PP');


        // console.log('fetchPP API response:', response);
        // console.log('fetchPP API response data:', response.data);
        
        if (typeof response.data === 'number') {
          this.PP = response.data;
        } else {
          this.PP = 0; // Si le retour n'est pas un nombre, on initialise PP à 0
        }
        // console.log('PP value:', this.PP);
      } finally {
        this.loading = false;
      }
    },









  },
});
