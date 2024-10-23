import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useAtteValStore = defineStore('AtteVal', {
  state: () => ({
    AtteVals: [], 
    loading: false,
    params: {},
  }),
  actions: {
    async fetchAtteVal() {
      this.loading = true;
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/attentevalid');
        if (Array.isArray(response.data)) {
          this.AtteVals = response.data; 
        } else {
          this.AtteVals = [];
        }
      } catch (error) {
        console.error(error);
      } finally {
        this.loading = false;
      }
    },
  },
});
