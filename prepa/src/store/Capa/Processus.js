import { defineStore } from 'pinia';
import Config from '@/utils/config.js';
import axios from '@/utils/axiosConfig';

export const useProcStore = defineStore('Proc', {
  state: () => ({
    Procs: [], 
    loading: false,
    params: {
        enumLibelle: '',
    },
  }),
  actions: {
    async fetchProc() {
      this.loading = true;
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/processus');
        if (Array.isArray(response.data)) {
          this.Procs = response.data; 
        } else {
          this.Procs = [];
        }
      } catch (error) {
        console.error(error);
      } finally {
        this.loading = false;
      }
    },
  },
});
