import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"

export const useDepotStore = defineStore("depot", {
  state: () => ({
    depots: [],
    loading: false,
    params: {
      critere: '',
    },
  }),
  actions: {
    async fetchDepots(critere) {
      this.loading = true
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/listDepot', {
          params: {
            critere,
          }
        })
        if (response.data.length > 0) {
          this.depots = response.data;
        } else {
          this.depots = []
        }
        this.loading = false
      } catch (error) {
        alert(error)
        console.log(error)
      } finally {
        this.loading = false
      }
    },
  },
})


