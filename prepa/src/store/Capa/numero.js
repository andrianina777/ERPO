import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useNumStore = defineStore('Num', {
  state: () => ({
    Nums: [],
    loading: false,
    params: { 
      seqk: ''
    },
  }),
  actions: {
    async fetchLatestKtable() {
        this.loading = true;
        try {
          const response = await axios.get(Config.SERVER_URL + `api/ktable`);
          this.Nums = response.data;
        } catch (error) {
          console.error('Erreur lors de la récupération des données de KTABLE', error);
        } finally {
          this.loading = false;
        }
      },
      async updateKtable() {
        this.loading = true;
        try {
            const url = Config.SERVER_URL + `api/updatektable?seqk=${this.params.seqk}`;
            await axios.put(url);
            await this.fetchLatestKtable();
        } catch (error) {
            console.error('Erreur lors de la mise à jour de KTABLE', error);
        } finally {
            this.loading = false;
        }
        },
      async insertKtable() {
        this.loading = true;
        try {
          await axios.post(Config.SERVER_URL + `api/insertktable`);
          await this.fetchLatestKtable();
        } catch (error) {
          console.error('Erreur lors de l\'insertion dans KTABLE', error);
        } finally {
          this.loading = false;
        }
      },
  },
});