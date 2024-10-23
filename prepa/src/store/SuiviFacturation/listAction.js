import { defineStore } from 'pinia';
import Config from "@/utils/config.js";
import axios from "@/utils/axiosConfig";

export const uselistAction = defineStore("listaction", {
    state: () => ({
      listactions: [],
      loading: false,

    }),
    actions: {
        async fetchListAction() {
            this.loading = true
            try {
              const response = await axios.get(Config.SERVER_URL + 'api/listAction')
              if (Array.isArray(response.data)) {
                this.listactions = response.data
              } else {
                this.listactions = []
              }
    
              this.loading = false
            }
            catch (error) {
              alert(error)
              console.log(error)
            } finally {
              this.loading = false
            }
          },
        
    }
})