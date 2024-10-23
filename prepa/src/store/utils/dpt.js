import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useDptStore = defineStore("dpt", {
  state: () => ({
    dpts: [],
    loading: false,
  }),
  actions: {
    async fetchDpt() {
      this.loading = true
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/dpt_capa')
       
          this.dpts = response.data

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