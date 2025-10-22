import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('../views/Login/Login.vue')
    },
    {
      path: '/',
      name: 'Home',
      component: () => import('../views/Home/Home.vue')
    }
  ],
})

export default router
