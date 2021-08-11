
import Vue from 'vue'

import Router from 'vue-router'
import store from '../store'
import Login from '@/components/Login'
import List from '@/components/List'
import View from '@/components/View'
import Write from '@/components/Write'




//sss

//kkkk222

// test 1

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
      path: '/write/:approvalNo',
      name: 'Write',
      component: Write,
      beforeEnter: requireAuth()
    },
    {
      path: '/view/:approvalNo',
      name: 'View',
      component: View,
      beforeEnter: requireAuth()
    },
  	{
  	  path: '*',
  	  component: List,
        beforeEnter: requireAuth()
  	}
  ]
})
