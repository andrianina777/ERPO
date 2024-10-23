import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useUserStore = defineStore("user", {
  state: () => ({
    users: [],
    loading: false,
  }),
  actions: {
    async fetchUser() {
      this.loading = true
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/user_capa')
       
          this.users = response.data

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