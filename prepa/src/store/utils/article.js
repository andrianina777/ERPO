import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useArticleStore = defineStore("articles", {
    state: () => ({
        articles: [],
        labos: [],
        familles: [],
        loading: false,
        params: {
            critere: '',
        },
    }),

    actions: {
        async fetchArticle(critere) {
            this.loading = true
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/listArticle', {
                    params: {
                        critere,
                    }
                })
                this.articles = response.data
                this.loading = false
            }
            catch (error) {
                alert(error)
                console.log(error)
            } finally {
                this.loading = false
            }
        },
        async fetchFamille() {
            this.loading = true
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/listFamille', {

                })
                this.familles = response.data
                this.loading = false
            }
            catch (error) {
                alert(error)
                console.log(error)
            } finally {
                this.loading = false
            }
        },
        async fetchLabo(critere) {
            this.loading = true
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/listLabo', {
                    params: {
                        critere,
                    }
                })
                this.labos = response.data
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