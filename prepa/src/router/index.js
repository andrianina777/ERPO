import { createRouter,createWebHashHistory } from "vue-router"
import preparation from "./preparation"
import adv from "./adv"
import transfert from "./transfert"
import home from "./home"
import admin from "./admin"
import facture from "./facture"
import etatcommande from "./etatcommande"
import facturio from "./facturio"
import capa from "./capa"
import suivianomalie from "./suivianomalie"
import appelentrant from "./appelentrant"
import appelsortant from "./appelsortant"
import useCheckSessionID from '@/composables/useCheckSessionID';

const { checkSessionID } = useCheckSessionID();

const routes = [
  ...home,
  ...preparation,
  ...adv,
  ...transfert,
  ...admin,
  ...facture,
  ...etatcommande,
  ...facturio,
  ...appelentrant,
  ...appelsortant,
  ...capa,
  ...suivianomalie,

]
const router = createRouter({
  history: createWebHashHistory(),
  routes,
});
router.beforeEach((to, from, next) => {
  checkSessionID(to, from, next);
});
export default router
