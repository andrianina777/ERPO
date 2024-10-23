import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useCreateBP = defineStore("useCreateBP", {
  state: () => ({
    myId: '',
    code_BP: '',
    loading: false,
    code_CC: null,
    article: null
    // params:{
    //   ids:'',
    //   code_CC:'',
    //   depot:''
    // },
  }),
  // getters: {
  //   getCommande(state) {
  //     return state.commandes
  //   }
  // },
  actions: {
    async getId() {
      this.loading = true
      try {
        const data = await axios.get(Config.SERVER_URL + 'api/new-id')
        this.myId = data.data
        this.loading = false
      }
      catch (error) {
        alert(error)
        console.log(error)
      } finally {
        this.loading = false
      }
    },
    async insertL6(listeCmd) {
      this.loading = true
      try {
        const response = await axios.post(Config.SERVER_URL + 'api/insertL6', listeCmd);
        this.data = response.data;
        this.loading = false
      }
      catch (error) {
        alert(error)
        console.log(error)
      } finally {
        this.loading = false
      }
    },
    async insertBP(params) {
      this.loading = true
      try {
        const response = await axios.post(Config.SERVER_URL + 'api/insertBP', null, {
          headers: { 'Content-Type': 'application/json' },
          params: params  // Pass params as query parameters
        });
        this.code_BP = response.data;
        this.loading = false
      }
      catch (error) {
        alert(error)
        console.log(error)
      } finally {
        this.loading = false
      }
    },
    async atteTransfert(code_CC, article) {
      this.loading = true
      try {
        const response = await axios.post(Config.SERVER_URL + 'api/atteTransfert', null, {
          params: {
            code_CC, article
          }
        },
        );
        this.loading = false
      }
      catch (error) {
       // alert(error)
        //console.log(error)
      } finally {
        this.loading = false
      }
    },
    async updateResume(params) {
      try {
        this.loading = true
        const response = await axios.put(Config.SERVER_URL + 'api/resumeT', null, {
          headers: { 'Content-Type': 'application/json' },
          params: params  // Pass params as query parameters
        });
        this.loading = false
      }
      catch (error) {
        alert(error)
        console.log(error)
      } finally {
        this.loading = false
      }
    },

    async updateFrais(params) {
      try {
        this.loading = true
        const response = await axios.put(Config.SERVER_URL + 'api/updateFrais', null, {
          headers: { 'Content-Type': 'application/json' },
          params: params  // Pass params as query parameters
        });
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
