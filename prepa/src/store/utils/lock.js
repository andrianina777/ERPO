import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
export const useLockStore = defineStore("lock", {
    state: () => ({
        locks: [],
        loading: false,
        // params: {
        //     xTable:'',
        //     xCode:'',
        //     isClose:'',
        // },
    }),

    actions: {
        async fetchLock(params) {
            this.loading = true
            try {
                const response = await axios.post(Config.SERVER_URL + 'api/lock', null, {
                    headers: { 'Content-Type': 'application/json' },
                    params: params  // Pass params as query parameters
                  });
                  this.locks = response.data
                  this.loading = false
            }
            catch (error) {
                alert(error);
                localStorage.clear();
                console.log(error)
            } finally {
                this.loading = false
            }
        },
    },

})