import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useAxeStore = defineStore("axe", {
  state: () => ({
    axes: [],
    loading: false,
  }),
  actions: {
    async fetchAxe() {
      this.loading = true
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/axe')
       
          this.axes = response.data
     


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
//export const piniaPlugin = createPiniaPlugin()