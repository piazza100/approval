import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import jwt_decode from 'jwt-decode'

Vue.use(Vuex)

// const resourceHost = '/api'

const enhanceAccessToeken = () => {
  const {token} = localStorage
  if (!token) return
  axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
}
enhanceAccessToeken()

export default new Vuex.Store({
  state: {
	  token: null
  },
  getters: {
    isAuthenticated(state) {
      state.token = state.token || localStorage.token
      return state.token
    },
    isExpire(state) {
      let isExpire = true;
      const token = state.token;
      if (token) {
        const decoded = jwt_decode(token);
        const exp = decoded.exp
        if (exp > (Date.now() / 1000)) {
          isExpire = false;
        } else {
          isExpire = true;
          delete localStorage.token
        }
      }

      return isExpire;
    },
    role(state) {
      let role = '';
      const token = state.token;
      if (token) {
        const decoded = jwt_decode(token);
        role = decoded.scopes[0].authority;
      }
      return role;
    },
    // encUserKey(state) {
    //   let encUserKey = '';
    //   const token = state.token;
    //   if (token) {
    //     const decoded = jwt_decode(token);
    //     encUserKey = decoded.encUserKey;
    //   }
    //   return encUserKey;
    // },

  },
  mutations: {
    LOGIN (state, {token}) {
      // 스토어에 액세스 토큰 저장
      state.token = token
      // 토큰을 로컬 스토리지에 저장
      localStorage.token = token
      

    },
    LOGOUT (state) {
      state.token = null
      delete localStorage.token
    }
  },
  actions: {
    LOGIN ({commit}, {userId, password}) {
      return axios.post('/api/user/login', {userId, password})
        .then(({data}) => {
          // LOGIN 변이 실행
          commit('LOGIN', data)
          // 모든 HTTP 요청 헤더에 Authorization 을 추가한다.
          axios.defaults.headers.common['Authorization'] = `Bearer ${data.token}`;
        })
    },
    LOGOUT ({commit}) {
      axios.defaults.headers.common['Authorization'] = undefined
      commit('LOGOUT')
    }
  }
})
