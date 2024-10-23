import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useStoreDroit = defineStore("droitAccess", {
    state: () => ({
        droits: [],
        loading: false,
        params: {
            xUser: '',
            access: '',
        },
    }),

    actions: {
        async fetchDroit(xUser, access) {
            this.loading = true
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/droit', {
                    params: {
                        xUser,
                        access,
                    }
                })
                this.droits = response.data;
                this.loading = false;
            }
            catch (error) {
                alert(error);
                localStorage.clear();
                console.log(error)
            } finally {
                this.loading = false
            }
        },
        async listeDroit(xUser) {
            this.loading = true
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/listDroit', {
                    params: {
                        xUser,
                    }
                })
                this.droits = response.data;
                this.loading = false;
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
