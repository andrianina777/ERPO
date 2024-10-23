import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useInfoCmdStore = defineStore("infoCmd", {
  state: () => ({
    infoCmds: [],
    loading: false,
    params: {
      code: '',
    },
  }),
  // getters: {
  //   getCommande(state) {
  //     return state.commandes
  //   }
  // },
  actions: {
    async fetchInfoCommande() {
      this.loading = true
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/infoCmd', { params: this.params })
        this.infoCmds = response.data
        this.loading = false
      }
      catch (error) {
        alert(error)
        localStorage.clear();
        console.log(error)
      } finally {
        this.loading = false
      }
    },
    async updateInfoCommande(infCmd) {
      try {
        const response = await axios.patch(Config.SERVER_URL + 'api/updateInfoCmd', infCmd);
        this.data = response.data;
      }
      catch (error) {
        alert(error)
        console.log(error)
      } finally {
        this.loading = false
      }
    },
    setCode(code) {
      this.params.code = code;
    },
  },

})
//export const piniaPlugin = createPiniaPlugin()