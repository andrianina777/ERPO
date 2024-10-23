import { defineStore } from 'pinia'
import Config  from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useEnumerationStore = defineStore("enumeration", {
  state: () => ({
    enumerations: [],
    // selectedEnum: null,
    loading: false,
    params:{
      code:'',
    },
  }),
  // getters: {
  //   getCommande(state) {
  //     return state.commandes
  //   }
  // },
  actions: {
    async fetchEnums() {
      this.loading = true
      try {
        const response = await axios.get(Config.SERVER_URL +'api/enumeration',{ params: this.params })
        this.enumerations = response.data
      //  this.enumerations = response.data.map(({ libelle }) => ({ libelle }))
        this.loading = false
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
    // setSelectedEnum(enumeration) {
    //   this.selectedEnum = enumeration
    // }
  },
 
})
