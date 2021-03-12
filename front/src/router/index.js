import Vue from 'vue'
import Router from 'vue-router'
import store from '../store'
import Login from '@/components/Login'
import Main from '@/components/Main'
import List from '@/components/List'
import View from '@/components/View'
import Write from '@/components/Write'



Vue.use(Router)

store.getters.isAuthenticated

const requireAuth = () => (to, from, next) => {
  if (!store.getters.isExpire) {
	return next()
  }
  next({
    path: '/login',
    query: { redirect: to.fullPath }
  })
}

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    // {
    //   path: '/main',
    //   name: 'Main',
    //   component: Main,
    //   beforeEnter: requireAuth()
    // },
    {
      path: '/list',
      name: 'List',
      component: List,
      beforeEnter: requireAuth()
    },
    {
      path: '/write',
      name: 'Write',
      component: Write,
      beforeEnter: requireAuth()
    },
    {
      path: '/view',
      name: 'View',
      component: View,
      beforeEnter: requireAuth()
    },
    {
      path: '/view/:id',
      name: 'View',
      component: View,
      beforeEnter: requireAuth()
    },
	{
	  path: '*',
	  component: Main,
      beforeEnter: requireAuth()
	}
  ]
})
