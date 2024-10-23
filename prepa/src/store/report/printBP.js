import { defineStore } from 'pinia';
import axios from "@/utils/axiosConfig"
import Config from "@/utils/config.js";
export const useReportBPStore = defineStore('report', {
  state: () => ({
    reportBytes: null,
  }),
  actions: {
    async generateReport(codeBP, isDouble) {
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/printPrepa', {
          params: {
            codeBP,
            isDouble,
          },
          responseType: 'arraybuffer',
        });
        return response;
        // this.reportBytes = response.data;
      } catch (error) {
        alert(error);
        localStorage.clear();
        console.error(error);
        throw new Error('Failed to generate report.');
      }
    },
    async generateReportTD(td, org, dest, isDouble, users) {
      try {
        const response = await axios.get(Config.SERVER_URL + 'api/printTransfert', {
          params: {
            td,
            org,
            dest,
            isDouble,
            users
          },
          responseType: 'arraybuffer',
        });
        return response;
        // this.reportBytes = response.data;
      } catch (error) {
        alert(error);
       // localStorage.clear();
        console.error(error);
        throw new Error('Failed to generate report.');
      }
    },
  },
});
