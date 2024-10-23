import { defineStore } from 'pinia';
import Config from "@/utils/config.js";
import axios from "@/utils/axiosConfig";

export const useSuiviTDStore = defineStore("suiviTD", {
    state: () => ({
        suiviTDs: [],
        loading: false,
        params: {
            code: '',
            depOrg: '',
            depDest: '',
            datedeb: null,
            datefin: null,
            article: ''
        },
    }),
    actions: {
        async fetchSuiviTD(code, depOrg, depDest, datedeb, datefin, article) {
            this.loading = true;
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/suiviTD', {
                    params: {
                        code,
                        depOrg,
                        depDest,
                        datedeb,
                        datefin,
                        article
                    }
                });
                if (Array.isArray(response.data)) {
                    this.suiviTDs = response.data;
                } else {
                    this.suiviTDs = [];
                }
            } catch (error) {
                console.error(error);
            } finally {
                this.loading = false;
            }
        },
    },
});
