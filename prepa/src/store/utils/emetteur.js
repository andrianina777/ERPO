import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useEmetteurStore = defineStore("emetteur", {
  state: () => ({
    emetteurs: [],
    loading: false,
  }),
  actions: {
    async fetchEmetteur() {
      this.loading = true
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/emetteur_capa')
       
          this.emetteurs = response.data

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

})