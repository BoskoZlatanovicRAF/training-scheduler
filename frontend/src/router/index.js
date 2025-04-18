import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import login from "@/views/Login.vue";

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path:'/login',
    name:'login',
    component:login
  },
  {
    path: '/userRegister',
    name: 'userRegister',
    component: () => import('../views/RegisterKorisnik.vue')
  },
  {
    path: '/managerRegister',
    name: 'managerRegister',
    component: () => import('../views/RegisterManager.vue')
  },
  {
    path: '/users',
    name: 'users',
    component: () => import('../views/UserList.vue')
  },
  {
    path: '/editUser',
    name: 'editUser',
    component: () => import('../views/EditUser.vue')
  },
  {
    path: '/dodajTermin',
    name: 'dodajTermin',
    component: () => import('../views/DodajTermin.vue')
  },
  {
    path: '/zakaziTermin',
    name: 'zakaziTermin',
    component: () => import('../views/ZakaziTermin.vue')
  },
  {
    path: '/listaTreninga',
    name: 'listaTreninga',
    component: () => import('../views/ListaTreninga.vue')
  },
  {
    path : '/racuni',
    name : 'racuni',
    component: () => import('../views/RacunList.vue')
  },
  {
    path: '/notifikacije',
    name: 'notifikacije',
    component: () => import('../views/NotifikacijeList.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
