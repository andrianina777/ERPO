import { defineStore } from 'pinia'
import Config  from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useAlleeStore = defineStore("allee", {
  state: () => ({
    allees: [],
    loading: false,
    params:{
        critere:'',
        depot:'',
      },
  }),
//   getters: {
//     getDepots(state) {
//       return state.depots
//     }
//   },
  actions: {
    async fetchAllees(depot,critere) {
      this.loading = true
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/listAlle',{
                    params: {
                        depot,
                        critere,
                    }
                })
        this.allees = response.data
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