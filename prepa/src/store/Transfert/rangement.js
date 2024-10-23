import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useRangementStore = defineStore("rangement", {
  state: () => ({
    rangements0: [],
    rangements1: [],
    loading: false,
    params: {
      depot: '',

    },
  }),
  actions: {
    async fetchRangement(depot) {
      this.loading = true

      try {
        const response = await axios.get(Config.SERVER_URL + 'api/rangement', {
          params: {
            depot,
          }
        })
        const data = response.data
        if (Array.isArray(data) && data.length === 2) {
          this.rangements0 = data[0]
          this.rangements1 = data[1]
        } else {
          
          console.error('Unexpected data structure in fetchRangement response:', data)
        }
      } catch (error) {
        alert(error);
        localStorage.clear();
        console.error(error)
      } finally {
        this.loading = false
      }
    },
  },

})