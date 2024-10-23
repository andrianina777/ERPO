import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useConseilReapproStore = defineStore("conseilReappro", {
    state: () => ({
        conseilReappro0: [],
        conseilReappro1: [],
        loading: false,
        params: {
            article: '',
            depotOrg: '',
            depotDest: '',
        },
    }),
    actions: {
        async fetchConseilReappro(article, depotOrg, depotDest) {
            this.loading = true

            try {
                const response = await axios.get(Config.SERVER_URL + 'api/conseilReappro', {
                    params: {
                        article,
                        depotOrg,
                        depotDest,
                    }
                })
                const data = response.data
                if (Array.isArray(data) && data.length === 2) {
                    this.conseilReappro0 = data[0]
                    this.conseilReappro1 = data[1]
                } else {

                    console.error('Unexpected data structure in fetchConseilReappro response:', data)
                }
            } catch (error) {
                alert(error);
                localStorage.clear();
                console.error(error)
            } finally {
                this.loading = false
            }
        },
    },

})