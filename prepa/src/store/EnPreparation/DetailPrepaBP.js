import { defineStore } from 'pinia'
import Config  from "@/utils/config.js"
import axios from "@/utils/axiosConfig"

export const useDetailPrepStoreBP = defineStore("detailPrepBP", {
  state: () => ({
    detailPrepsBP: [],
    detailBP: [],
    detailCTRL:[],
    infoBP:[],
    loading: false,
    params:{
      BP:'',
    },
  }),
  actions: {
    async fetchDetailPrepBP(BP) {
            this.loading = true
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/listLigneBP', {
                    params: {
                        BP,
                    }
                })
                this.detailPrepsBP = response.data
                this.loading = false
            }
            catch (error) {
                alert(error);
                localStorage.clear();
                console.log(error)
            } finally {
                this.loading = false
            }
    },
    async fetchDetailBP(BP) {
      this.loading = true
        try {
            const response = await axios.get(Config.SERVER_URL + 'api/detailBP', {
                params: {
                    BP,
                }
            })
            this.detailBP = response.data
            this.loading = false
        }
        catch (error) {
            alert(error);
            localStorage.clear();
            console.log(error)
        } finally {
            this.loading = false
        }
    },

    async fetchDetailCTRL(BP) {
        this.loading = true
          try {
              const response = await axios.get(Config.SERVER_URL + 'api/detailBPCTRL', {
                  params: {
                      BP,
                  }
              })
              this.detailCTRL = response.data
              this.loading = false
          }
          catch (error) {
              alert(error);
              localStorage.clear();
              console.log(error)
          } finally {
              this.loading = false
          }
      },

      async fetchinfoBP(BP) {
        this.loading = true
          try {
              const response = await axios.get(Config.SERVER_URL + 'api/infoEncoursBP', {
                  params: {
                      BP,
                  }
              })
              this.infoBP = response.data
              this.loading = false
          }
          catch (error) {
              alert(error);
              localStorage.clear();
              console.log(error)
          } finally {
              this.loading = false
          }
      },
  },
 
})


//api/detailBP