// /Users/anjaniainaandrianina/Desktop/Project/prepa/ERPO/frontend_prepa/prepa/src/store/Transfert

import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useTransfertStore = defineStore("transfert", {
  state: () => ({
    loading: false,
    codeTD: '',
    problemStock: [],
    stockNonVide:0,
    params: {
      article: '',
      qte: '',
      depot: '',
      lettre: '',
      empl: ''
      
    },
  }),
  actions: {
    async insertL6_transfert(listeCmd) {
      this.loading = true
      try {
        const response = await axios.post(Config.SERVER_URL + 'api/insertL6_transfert', listeCmd);
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
    async insertFSIL(params) {
      this.loading = true
      try {
        const response = await axios.post(Config.SERVER_URL + 'api/insertFSIL', null, {
          headers: { 'Content-Type': 'application/json' },
          params: params  // Pass params as query parameters
        });
        this.codeTD = response.data;
        this.loading = false
      }
      catch (error) {
        alert(error)
        console.log(error)
      } finally {
        this.loading = false
      }
    },

    async listProblemStock(article, qte, depot, lettre, empl) {
      this.loading = true;
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/testStock', {
          params: {
            article,
            qte,
            depot,
            lettre,
            empl,
          },
        });
        this.problemStock = response.data;

        this.loading = false;
      } catch (error) {
        alert(error)
        console.log(error)
      } finally {
        this.loading = false;
      }
    },

    async stockPasVide(  depot,  empl) {
      this.loading = true;
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/stockPasVide', {
          params: {
            depot,
            empl,
          },
        });
        this.stockNonVide = response.data;

        this.loading = false;
      } catch (error) {
        alert(error)
        console.log(error)
      } finally {
        this.loading = false;
      }
    },
    async insertL6_transfertConseil(listeTransfert) {
      this.loading = true
      try {
        const response = await axios.post(Config.SERVER_URL + 'api/insertL6_transfertConseil', listeTransfert);
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

  },

})
