import { defineStore } from 'pinia'
import Config  from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useAlerteStore = defineStore("alerte", {
  state: () => ({
    alertes: [],
    loading: false,
    params:{
      code:'',
    },
  }),
  // getters: {
  //   getCommande(state) {
  //     return state.commandes
  //   }
  // },
  actions: {
    async fetchAlerte() {
      this.loading = true
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/timing',{ params: this.params })
        this.alertes = response.data
        this.loading = false
      }
      catch (error) {
        alert(error)
        console.log(error)
      } finally {
        this.loading = false
      }
    },
    setCode(code) {
      this.params.code = code;
    },
  },
 
})