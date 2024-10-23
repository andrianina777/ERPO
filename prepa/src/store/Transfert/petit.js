import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const usePetitStore = defineStore("petitColis", {
  state: () => ({
    petits0: [],
    petits1: [],
    loading: false,
    params: {
      depotOrg: '',
      depotDest: '',
      alle: '',
    },
  }),
  actions: {
    async fetchPetit(depotOrg,depotDest,alle) {
      this.loading = true

      try {
        const response = await axios.get(Config.SERVER_URL + 'api/petitColis', {
          params: {
            depotOrg,
            depotDest,
            alle,
          }
        })
        const data = response.data
        if (Array.isArray(data) && data.length === 2) {
          this.petits0 = data[0]
          this.petits1 = data[1]
        } else {
          
          console.error('Unexpected data structure in fetchPetit response:', data)
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