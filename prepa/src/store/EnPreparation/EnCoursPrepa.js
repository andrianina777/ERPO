import { defineStore } from 'pinia'
import Config from "@/utils/config.js"
import axios from "@/utils/axiosConfig"
import { ref,  } from "vue";
export const useEnCoursCmdStore = defineStore("encoursCmd", {
    state: () => ({
        encoursCmds: ref([]),
        encoursBP:ref([]),
        loading: false,
        params: {
            prepspecif: '',
            isVisible: '',
            dateliv: '',
            etat: 1,
        },
    }),
    actions: {
        async fetchEnCoursCmd(prepspecif, isVisible, dateliv) {
            this.loading = true
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/encoursprep', {
                    params: {
                        prepspecif,
                        isVisible,
                        dateliv
                    }
                })
                if(response.data.length>0)
                {
                    this.encoursCmds = response.data;
                } else {
                    this.encoursCmds = [];
                }
                
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
        async fetchEnCoursPrepBP(etat) {
            this.loading = true
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/listEnCoursBP', {
                    params: {
                        etat,
                    }
                })
                if(response.data.length>0){
                    this.encoursBP = response.data;
                } else {
                    this.encoursBP = [];
                }
               
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
