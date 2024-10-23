// /Users/anjaniainaandrianina/Desktop/Project/prepa/ERPO/frontend_prepa/prepa/src/store/Transfert

import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useRotationStore = defineStore("rotation", {
  state: () => ({
    rotations0: [],
    rotations1: [],
    loading: false,
    params: {
      depot: '',
      alle: '',
    },
  }),
  actions: {
    async fetchRotation(depot, alle) {
      this.loading = true

      try {
        const response = await axios.get(Config.SERVER_URL + 'api/rotation', {
          params: {
            depot,
            alle,
          }
        })
        const data = response.data
        if (Array.isArray(data) && data.length === 2) {
          this.rotations0 = data[0]
          this.rotations1 = data[1]
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
