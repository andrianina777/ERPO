import { defineStore } from 'pinia';
import Config from "@/utils/config.js";
import axios from "@/utils/axiosConfig";

export const useAppelSortantStore = defineStore("AppelSortant", {
    state: () => ({
        AppelSortants: [],
        ListClients:[],
        loading: false,
        params: {
            date_sortant: null,
            numero: null,
            date_sortant_second: null,
        }
    }),
    actions: {
        async fetchAppelSortant(date_sortant, numero) {
            this.loading = true;
            try {
                //API APPEL SORTANT
                const response = await axios.get(Config.SERVER_URL + 'api/appelsortant', {
                    params: {
                        date_sortant,
                        numero,
                    }
                });
                if (Array.isArray(response.data)) {
                    this.AppelSortants = response.data;
                } else {
                    this.AppelSortants = [];
                }
            } catch (error) {
                console.error(error);
            } finally {
                this.loading = false;
            }
        },



        async updateEtatSortie(seq, etat, seqSortie,rappel,src_sortant) {
            this.loading = true
            try {
                const response = await axios.post(Config.SERVER_URL + 'api/updateEtatSortie', null, {
                    params: {
                        seq, etat, seqSortie,rappel,src_sortant,
                    }
                    
                },
                );
                this.loading = false
            }
            catch (error) {
                // alert(error)
                console.log(error)
            } finally {
                this.loading = false
            }
        },


        async fetchlListClient() {
            this.loading = true;
            try {
                //API LIST CLIENT
                const response = await axios.get(Config.SERVER_URL + 'api/listClient', {
                });
                if (Array.isArray(response.data)) {
                    this.ListClients = response.data;
                } else {
                    this.ListClients = [];
                }
            } catch (error) {
                console.error(error);
            } finally {
                this.loading = false;
            }
        },



        // async updateEtatSortie(seq, etat, seqSortant) {
        //     try {
        //         this.loading = true
        //         const response = await axios.put(Config.SERVER_URL + 'api/updateEtatSortie', null, {
        //             headers: { 'Content-Type': 'application/json' },
        //             seq, etat, seqSortant
        //         });
        //         this.loading = false
        //     }
        //     catch (error) {
        //         alert(error)
        //         console.log(error)
        //     } finally {
        //         this.loading = false
        //     }
        // },
    },
});