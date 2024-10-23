import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useIncompletStore = defineStore("incomplet", {
  state: () => ({
    incomplets0: [],
    incomplets1: [],
    loading: false,
    params: {
      depotOrg: '',
      depotDest: '',
      alle: '',
    },
  }),
  actions: {
    async fetchIncomplet(depotOrg,depotDest,alle) {
      this.loading = true

      try {
        const response = await axios.get(Config.SERVER_URL + 'api/colisIncomplet', {
          params: {
            depotOrg,
            depotDest,
            alle,
          }
        })
        const data = response.data
        if (Array.isArray(data) && data.length === 2) {
          this.incomplets0 = data[0]
          this.incomplets1 = data[1]
        } else {
          
          console.error('Unexpected data structure in fetchRotation response:', data)
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