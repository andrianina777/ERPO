import AdvHome from '@/views/adv/PageAdvHome.vue'
import useCheckSessionID from '@/composables/useCheckSessionID';

const { checkSessionID } = useCheckSessionID();

export default [
    {
        path: '/AdvHome',
        component: AdvHome,
        name: 'advhome',
        beforeEnter: checkSessionID 
    },
]