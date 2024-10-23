import { defineStore } from 'pinia'
import Config  from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useEtapeStore = defineStore("etape", {
  state: () => ({
    etapes: [],
    loading: false,
    params:{
      code:'',
    },
  }),

  actions: {
    async fetchEtape() {
      this.loading = true
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/etape')
        this.etapes = response.data
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