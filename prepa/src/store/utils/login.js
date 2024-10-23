
import { defineStore } from 'pinia';
import Config from "@/utils/config.js";
import axios from "axios";

export const useAuthStore = defineStore('login', {
    state: () => ({
        username: '',
        password: '',
        sessionId: '', // New state to store the session ID
        servers: [],
        name_server: '',
        ip_local: '',
    }),
    actions: {
        async login() {
            const credentials = {
                username: this.username,
                password: this.password,
                name_server: this.name_server,
            };

            try {
                const response = await axios.post(Config.SERVER_URL + 'api/login', credentials, { withCredentials: true });
                this.sessionId = response.data;
                localStorage.setItem('sessionId', this.sessionId);

                return response;
            } catch (error) {
                // alert(error);
                throw error;
            }
        },

        async logout() {
            try {
                await axios.get(Config.SERVER_URL + 'api/logout', {
                    headers: {
                        Authorization: this.sessionId // Send the session ID as a header in the GET request
                    },
                    withCredentials: true
                });
            } catch (error) {

                // console.error("Logout failed: ", error);

            } finally {
                // Clear the session data in the store and localStorage regardless of the server response
                this.sessionId = '';
                localStorage.removeItem('sessionId');
            }
        },

        async getlisteServer() {
            this.loading = true
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/listServer')
                this.servers = response.data
                this.loading = false
            }
            catch (error) {
                alert(error)
                console.log(error)
            } finally {
                this.loading = false
            }
        },

        isAuthenticated() {
            return !!this.sessionId; // Return true if the session ID exists, otherwise false
        },

        async fetchLocalIp() {
            this.loading = true
            try {
                const response = await axios.get(Config.SERVER_URL + 'api/local-ip')
                this.ip_local = response.data
                this.loading = false
            } catch (error) {
                console.error('Erreur lors de la récupération de l\'adresse IP locale privée:', error);
            }
        }
    }
});
