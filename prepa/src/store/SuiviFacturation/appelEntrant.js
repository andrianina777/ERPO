import { defineStore } from 'pinia';
import Config from "@/utils/config.js";
import axios from "@/utils/axiosConfig";

export const useAppelEntrantStore = defineStore("AppelEntrant", {
  state: () => ({
    AppelEntrants: [],
    loading: false,
    params: {
      date_deb: null,
      date_fin: null,
    },
    seq: null, etat: null, dateRappel: null
  }),
  actions: {
    async fetchAppelEntrant(date_deb, date_fin) {
      this.loading = true;
      try {
        //API APPEL ENTRANT 
        const response = await axios.get(Config.SERVER_URL + 'api/appelentrant', {
          params: {
            date_deb, date_fin
          }
        });
        if (Array.isArray(response.data)) {
          this.AppelEntrants = response.data;
        } else {
          this.AppelEntrants = [];
        }
      } catch (error) {
        console.error(error);
        console.log(date_deb)
      } finally {
        this.loading = false;
      }
    },



    async updateEtat(params) {
      try {
        this.loading = true
        const response = await axios.put(Config.SERVER_URL + 'api/updateEtat', null, {
          headers: { 'Content-Type': 'application/json' },
          params: params  // Pass params as query parameters
        });
        this.loading = false
      }
      catch (error) {
        alert(error)
        console.log(error)
      } finally {
        this.loading = false
      }
    },

    async updateEtat1(params) {
      try {
        this.loading = true
        const response = await axios.put(Config.SERVER_URL + 'api/updateEtat1', null, {
          headers: { 'Content-Type': 'application/json' },
          params: params  // Pass params as query parameters
        });
        this.loading = false
      }
      catch (error) {
        alert(error)
        console.log(error)
      } finally {
        this.loading = false
      }
    },


  },
});