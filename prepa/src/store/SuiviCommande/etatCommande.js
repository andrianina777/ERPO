import { defineStore } from 'pinia';
import Config from "@/utils/config.js";
import axios from "@/utils/axiosConfig";

export const useEtatCommandeStore = defineStore("SuiviCommande", {
    state: () => ({
        suiviCommandes: [],
        loading: false,
        params: {
            CodeCommande: '',
            CodeClient: '',
            Axe:'',
            DateDeb: null,
            DateFin: null,
            
        },
    }),
    actions: {
        async fetchEtatCommande(CodeCommande, CodeClient, DateDeb, DateFin, Axe) {
            this.loading = true;
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/etatcommande', {
                    params: {
                        CodeCommande,
                        CodeClient, DateDeb, DateFin, Axe
                    }
                });
                if (Array.isArray(response.data)) {
                    this.suiviCommandes = response.data;
                } else {
                    this.suiviCommandes = [];
                }
            } catch (error) {
                console.error(error);
            } finally {
                this.loading = false;
            }
        },
    },
});