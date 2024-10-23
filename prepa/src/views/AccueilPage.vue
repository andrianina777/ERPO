<template>
  <v-app id="inspire">
    <v-app-bar>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>Accueil</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-divider vertical></v-divider>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <div>
        <u>Login</u> :&nbsp;<b><span style="color: #388E3C;">{{ user }}</span></b>
      </div>
      <v-btn @click="logout">
        <v-img :width="30" :src="require('@/assets/logout.png')">
        </v-img>
      </v-btn>
      <!-- <v-tab @click="logout" class="text-red-lighten-1">Déconnecter</v-tab> -->
    </v-app-bar>

    <v-navigation-drawer v-model="drawer" temporary>
      <!-- class="bg-deep-purple-lighten-5" -->

      <v-list color="transparent" density="compact">
        <v-list-subheader><b>CATEGORIE</b></v-list-subheader>
        <v-list-item v-for="menu in filteredMenus" :key="menu.title" color="primary" variant="plain">
          <v-list-item-title
            class="lighten-1 v-list-item v-list-item--link v-list-item--nav v-theme--dark v-list-item--density-compact v-list-item--rounded v-list-item--variant-text"
            :style="{ fontSize: '14px' }" @click="menuClick(menu)" color="primary">{{
              menu.title }} </v-list-item-title>
          <v-divider class="border-opacity-100" color="warning"></v-divider>
        </v-list-item>
      </v-list>

    </v-navigation-drawer>

    <v-main class="bg-grey-lighten-2">
      <v-container>
        <v-row>
          <v-col>
            <strong><u>Catégorie</u></strong>
          </v-col>
        </v-row>

        <v-row justify="start">
          <v-col cols="6" md="2" v-for="(menu, i) in filteredMenus" :key="i">
            <v-hover v-slot="{ isHovering, props }">
              <v-card class="mx-auto" max-width="159" @click="menuClick(menu)" v-if="menu.visible" v-bind="props"
                :color="isHovering ? 'deep-purple-lighten-1' : undefined">
                <v-img max-height="100" class="mx-4" :src="menu.img"></v-img>
                <v-card-item class="mt-n3">
                  <v-card-title class="text-center lighten-1" style="font-size: 14px; ">{{ menu.title }}</v-card-title>
                </v-card-item>
              </v-card>
            </v-hover>
          </v-col>
        </v-row>

      </v-container>

    </v-main>
    <v-card>
      <v-row>
        <v-col class="text-right">
          <v-sheet class="custom-bg">
            Version {{ version }}
          </v-sheet>
        </v-col>
      </v-row>
    </v-card>
  </v-app>
</template>
  
<script>
import { ref, defineComponent, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import swal from 'sweetalert2';
import { useAuthStore } from '@/store/utils/login';
import { fetchdroits } from "@/composables/useDroit";
import useSessionTimeout from '@/utils/useSessionTimeout';
import Config from "@/utils/config.js";
import Version from "@/utils/version.js";

export default defineComponent({
  name: 'accueilpage',

  methods: {
    // fiche() {
    //   router.push('/fiches')
    // }
  },

  setup() {
    const drawer = ref(null);
    const router = useRouter();
    const useLogoutStore = useAuthStore();
    const showMagsin = ref(false);
    const showPreparation = ref(false);
    const showArticle = ref(false);
    const showADV = ref(false);
    const showExpedition = ref(false);
    const showPriseCmd = ref(false);
    const showSuiviCmd = ref(false);
    const showAdmin = ref(false);
    const showSuiviFact = ref(false);
    const showCapa = ref(false);
    const user = localStorage.getItem('username');

    const version = ref(Version.APP_VERSION);

    useSessionTimeout(Config.DURRE_SESSION, router);

    const admin = () => {
      const tabDefautAdmin = getAdminDroit();
      router.push('/' + tabDefautAdmin);
    }

    const fiche = () => {
      //router.push('/fiches')
      window.location = 'http://192.168.130.6/fiches';
    }

    const transfert = () => {
      const tabDefautTransfert = getTransfertDroit();
      router.push('/' + tabDefautTransfert);
    };

    const prepa = () => {
      const tabDefautPrepa = getPrepaDroit();
      router.push('/' + tabDefautPrepa);
    };

    const adv = () => {
      router.push('/AdvHome');
    };

    const facture = () => {
      // console.log('clicked')
      router.push('/facture');
    };

    const suivi = () => {
      // console.log('clicked suivi')
      router.push('/EtatCommande');
    };

    const facturio = () => {
      //  console.log('clicked facturio')
      router.push('/appelentrant');
    };
    const capaio = () => {
      //  console.log('clicked capa')
      router.push('/capa_view');
    };

    const menuClick = (menu) => {
      if (menu.action === 'transfert') {
        transfert();
      } else if (menu.action === 'prepa') {
        prepa();
      } else if (menu.action === 'fiche') {
        fiche();
      } else if (menu.action === 'admin') {
        admin();
      }
       else if (menu.action === 'adv') {
        adv();
      }
      else if (menu.action === 'facture') {
        facture();
      } 
      else if (menu.action === 'suivi') {
        suivi();
      } 
      else if (menu.action === 'facturio') {
        facturio();
      } 
      else if (menu.action === 'capaio'){
        capaio();
      }
    };

    const getPrepaDroit = () => {
      const lstDroit = localStorage.getItem('listeDroits');
      if (lstDroit) {
        const listePages = ['PageAttePrepa', 'PageEnCoursPrepa', 'PageAtteCtrl', 'PageEnCoursCtrl', 'PageEmballage', 'PageEnCoursPrepBP', 'PageAtteCtrlBP', 'PageEnCoursCtrlBP'];
        const droits = JSON.parse(lstDroit);
        const element = listePages.find(page => droits.some(item => item.descAcces.trim() === page));
        if (element) {
          return element;
        } else {
          return 'tabPrepa';
        }
      }
    };

    const getTransfertDroit = () => {
      const lstDroit = localStorage.getItem('listeDroits');
      if (lstDroit) {
        const listePages = ['Rotation', 'ColisIncomplet', 'PetitColis', 'Rangement', 'ConseilReappro', 'SuiviTD'];
        const droits = JSON.parse(lstDroit);
        const element = listePages.find(page => droits.some(item => item.descAcces.trim() === page));
        if (element) {
          return element;
        } else {
          return 'tabPrepa';
        }
      }
    };

    const getAdminDroit = () => {
      const lstDroit = localStorage.getItem('listeDroits');
      if (lstDroit) {
        const listePages = ['GestionAcces'];
        const droits = JSON.parse(lstDroit);
        const element = listePages.find(page => droits.some(item => item.descAcces.trim() === page));
        if (element) {
          return element;
        } else {
          return 'tabPrepa';
        }
      }
    };

    onMounted(async () => {
      showMagsin.value = await fetchdroits('MAGASIN');
      showPreparation.value = await fetchdroits('PREPARATION');
      showArticle.value = await fetchdroits('FICHE ARTICLE');
      showADV.value = await fetchdroits('ADV');
      showExpedition.value = await fetchdroits('EXPEDITION');
      showPriseCmd.value = await fetchdroits('PRISE DE COMMANDE')
      showSuiviCmd.value = await fetchdroits('SUIVI DE COMMANDE')
      showAdmin.value = await fetchdroits('Administration')
      showSuiviFact.value = await fetchdroits('FACTURIO')
      showCapa.value = await fetchdroits('FACTURIO')
    })

    const logout = () => {
      swal
        .fire({
          title: 'Voulez-vous vraiment Déconnecter ?',
          showDenyButton: true,
          showCancelButton: false,
          allowOutsideClick: false,
          confirmButtonText: 'Oui',
          denyButtonText: `Non`,
        })
        .then(async (result) => {
          if (result.isConfirmed) {
            localStorage.clear();
            await useLogoutStore.logout();
            router.push({ name: 'login' });
          } else if (result.isDenied) {
            // swal.fire('Transfert annuler', '', 'info');
          }
        });
    };

    const menus = ref([
      {
        img: require('@/assets/admin.png'),
        title: "Administration",
        visible: showAdmin,
        action: "admin",

      },
      {
        img: require('@/assets/transfert.png'),
        title: "Magasin",
        visible: showMagsin,
        action: "transfert",

      },
      {
        img: require('@/assets/prepa.png'),
        title: "Préparation",
        visible: showPreparation,
        action: "prepa",
      },
      {
        img: require('@/assets/fiche.png'),
        title: "Fiche Article",
        visible: showArticle,
        action: "fiche",
      },
      {
        img: require('@/assets/adv.png'),
        title: "Adv",
        visible: showADV,
        action: "adv",
      },
      {
        img: require('@/assets/exped.png'),
        title: "Expédition",
        visible: showExpedition,
        action: "expedition",
      },
      {
        img: require('@/assets/priseCom.png'),
        title: "Prise de Commande",
        visible: showPriseCmd,
        action: "commande",
      },
      {
        img: require('@/assets/suivicom.png'),
        title: "Suivi de Commande",
        visible: showSuiviCmd,
        action: "suivi",
      },
      {
        img: require('@/assets/facture.png'),
        title: "Facture",
        visible: showSuiviCmd,
        action: "facture",
      },
      {
        img: require('@/assets/facturio.png'),
        title: "Suivi Facturation",
        visible: showSuiviFact,
        action: "facturio",
      },
      {
        img: require('@/assets/841606.svg'),  
        title: "CAPA",
        visible: showCapa,
        action: "capaio",
      },
    ])

    const filteredMenus = computed(() => menus.value.filter(menu => menu.visible));

    return {
      drawer, showArticle, showADV, showExpedition, showPriseCmd,showCapa, showSuiviCmd, user, showAdmin,
      transfert,
      prepa,
      adv,
      suivi,
      facturio,
      capaio,
      logout,
      admin,
      showMagsin,
      showPreparation,
      version, fiche, menus, menuClick, filteredMenus,
      facture

    }
  }
});
</script>
<style>
.custom-bg {
  background-color: #ffffff;
  color: grey;
}

.text-right {
  text-align: right;
}
</style>
  