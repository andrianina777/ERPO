import { defineStore } from 'pinia'
import Config from '@/utils/config.js'
import axios from "@/utils/axiosConfig"

export const prepCommandStore = defineStore('prepCommandStore', {
  state: () => ({
    depotCmd: [],
    articleCmd: [],
    listeCmd: [],
    rayonCmd: [],
    loading: false,
    params: {
      code: '',
    },
  }),

  getters: {
    getDepotCmd(state) {
      return state.depotCmd
    },
  },

  actions: {
    async fetchPreparerCmd() {
      this.loading = true

      try {
        const response = await axios.get(Config.SERVER_URL + 'api/genererBP', { params: this.params })

        const data = response.data
        if (Array.isArray(data) && data.length === 4) {
          this.depotCmd = data[0]
          this.articleCmd = data[1]
          this.listeCmd = data[2]
          this.rayonCmd = data[3]
        } else {
          console.error('Unexpected data structure in fetchPreparerCmd response:', data)
        }
      } catch (error) {
        alert(error);
        localStorage.clear();
        console.error(error)
      } finally {
        this.loading = false
      }
    },

    setCode(code) {
      this.params.code = code
    },
  },
})
