import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useActionTypeStore = defineStore("ActionType", {
  state: () => ({
    ActionTypes: [],
    loading: false,
  }),
  actions: {
    async fetchActionType() {
      this.loading = true
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/actiontype')
       
          this.ActionTypes = response.data

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