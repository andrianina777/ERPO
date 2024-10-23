import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useListEnumStore = defineStore('ListEnum', {
  state: () => ({
    ListEnums: [], 
    loading: false,
    params: {
    },
  }),
  actions: {
    async fetchListEnum() {
      this.loading = true;
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/listenumerationcapa');
        this.ListEnums = response.data
        this.loading = false
      } catch (error) {
        console.error(error);
      } finally {
        this.loading = false;
      }
    },
  },
});
