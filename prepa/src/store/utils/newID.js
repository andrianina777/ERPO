import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useNewID = defineStore("getID", {
  state: () => ({
    myId: '',
    loading: false

  }),
  actions: {
    async getId() {
      this.loading = true
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/new-id')
        this.myId = response.data
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
