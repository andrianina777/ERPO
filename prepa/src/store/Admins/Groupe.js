import { defineStore } from 'pinia';
import Config from "@/utils/config.js";
import axios from "@/utils/axiosConfig";

export const useGroupeStore = defineStore("groupes", {
    state: () => ({
        groupes: [],
        users: [],
        membres: [],
        acces: [],
        sesDroits: [],
        loading: false,
        params: {
            groupe: '',
            users: '',
            droit: '',
            read: true,
            write: true,
        },
    }),
    actions: {
        async fetchGroupe() {
            this.loading = true;
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/listGroupe',);
                if (Array.isArray(response.data)) {
                    this.groupes = response.data;
                } else {
                    this.groupes = [];
                }
            } catch (error) {
                console.error(error);
                // Handle the error gracefully, e.g., display an error message.
            } finally {
                this.loading = false;
            }
        },


        async fetchUsers(groupe) {
            this.loading = true;
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/listUsers', {
                    params: {
                        groupe,
                    }
                });
                if (Array.isArray(response.data)) {
                    this.users = response.data;
                } else {
                    this.users = [];
                }
            } catch (error) {
                console.error(error);
                // Handle the error gracefully, e.g., display an error message.
            } finally {
                this.loading = false;
            }
        },
        async fetchMembres(groupe) {
            this.loading = true;
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/listMembres', {
                    params: {
                        groupe,
                    }
                });
                if (Array.isArray(response.data)) {
                    this.membres = response.data;
                } else {
                    this.membres = [];
                }
            } catch (error) {
                console.error(error);
                // Handle the error gracefully, e.g., display an error message.
            } finally {
                this.loading = false;
            }
        },
        async fetchAcces(groupe) {
            this.loading = true;
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/listAcces', {
                    params: {
                        groupe,
                    }
                });
                if (Array.isArray(response.data)) {
                    this.acces = response.data;
                } else {
                    this.acces = [];
                }
            } catch (error) {
                console.error(error);
                // Handle the error gracefully, e.g., display an error message.
            } finally {
                this.loading = false;
            }
        },
        async fetchSesDroits(groupe) {
            this.loading = true;
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/sesDroits', {
                    params: {
                        groupe,
                    }
                });
                if (Array.isArray(response.data)) {
                    this.sesDroits = response.data;
                } else {
                    this.sesDroits = [];
                }
            } catch (error) {
                console.error(error);
                // Handle the error gracefully, e.g., display an error message.
            } finally {
                this.loading = false;
            }
        },

        async insert_droit(accec) {
            this.loading = true
            try {
                const response = await axios.post(Config.SERVER_URL + 'api/insert_droit', accec);
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

        async insert_Membres(groupe, users) {
            this.loading = true
            try {
                const response = await axios.post(Config.SERVER_URL + 'api/insert_Membres', null, {
                    headers: { 'Content-Type': 'application/json' },
                    params: {
                        groupe,
                        users,
                    },
                });
                this.loading = false
            }
            catch (error) {
                alert(error)
                console.log(error)
            } finally {
                this.loading = false
            }
        },

        async delete_Membres(groupe, users) {
            this.loading = true
            try {
                const response = await axios.delete(Config.SERVER_URL + 'api/delete_Membres', {
                    headers: { 'Content-Type': 'application/json' },
                    params: {
                        groupe,
                        users,
                    },
                });
                this.loading = false
            }
            catch (error) {
                alert(error)
                console.log(error)
            } finally {
                this.loading = false
            }
        },
        async delete_Droit(groupe, droit) {
            this.loading = true
            try {
                const response = await axios.delete(Config.SERVER_URL + 'api/delete_Droit', {
                    headers: { 'Content-Type': 'application/json' },
                    params: {
                        groupe,
                        droit,
                    },
                });
                this.loading = false
            }
            catch (error) {
                alert(error)
                console.log(error)
            } finally {
                this.loading = false
            }
        },
        async update_Droit(read, write, groupe, droit) {
            try {
                this.loading = true
                const response = await axios.put(Config.SERVER_URL + 'api/update_Droit', null, {
                    headers: { 'Content-Type': 'application/json' },
                    params: {
                        read,
                        write,
                        groupe,
                        droit
                    }
                });
                this.loading = false
            }
            catch (error) {
                alert(error)
                console.log(error)
            } finally {
                this.loading = false
            }
        },

        async insert_groupe(group) {
            this.loading = true
            try {
                const response = await axios.post(Config.SERVER_URL + 'api/insert_groupe', group);
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
    }

});
