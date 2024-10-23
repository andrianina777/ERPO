import { defineStore } from 'pinia';
import Config from "@/utils/config.js";
import axios from "@/utils/axiosConfig";

export const useValidationCommandeStore = defineStore("validations", {
  state: () => ({
    validations: [],
    loading: false,
    // params: {
    //   groupe: '',
    //   dateliv: null,
    // },
  }),
  actions: {
    async fetchCommandeValidation() {
      this.loading = true;
      try {
        //API a changer avec la vrai valeur de l'API du ADV
        const response = await axios.get(Config.SERVER_URL + 'api/prep');
        if(Array.isArray(response.data)){
          this.validations = response.data;
        } else {
          this.validations = [];
        }
      } catch (error) {
        console.error(error);
        // Handle the error gracefully, e.g., display an error message.
      } finally {
        this.loading = false;
      }
    },
  },
});