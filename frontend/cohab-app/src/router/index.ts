import { createRouter, createWebHistory } from 'vue-router'
import { authGuard } from './guards/authGuard';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/Login/Login.vue'),
      meta: { requiresAuth: false }
    },
    {
      path: '/registration',
      name: 'Registration',
      component: () => import('@/views/Registration/Registration.vue'),
      meta: { requiresAuth: false }
    },
    {
      path: '/',
      name: 'Home',
      component: () => import('@/views/Home/Home.vue'),
      meta: { requiresAuth: true }
    }
  ],
})

router.beforeEach(authGuard);

export default router