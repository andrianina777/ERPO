import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useDpt2Store = defineStore("dpt2", {
  state: () => ({
    dpts2: [],
    loading: false,
  }),
  actions: {
    async fetchDpt2() {
      this.loading = true
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/dpt_capa2')
       
          this.dpts2 = response.data

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