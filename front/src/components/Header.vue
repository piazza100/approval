<template>
	<header>
		<a href="/list"> | 결재 목록</a>
		<a href="/write" v-if="$store.getters.role === 'ROLE_USER'"> | 결재 문서 등록</a>
		<a href="/login" v-show="isLogin !== false"><span>{{loginMessage}}</span></a></span>
		<a href="" @click.prevent="logout" v-show="isLogin === false"><span> | {{loginMessage}}</span></a>
		({{roleMessage}})
	</header>
</template>

<script>

export default {
  data() {
    return {
    	isLogin : this.$store.getters.isExpire
    };
  },
  created() {
  },
  methods: {
		logout() {
			this.$store.dispatch('LOGOUT')
			.then(() => this.goLogoutPages())
		},
		goLogoutPages () {
			window.location.href='/'
		},
	},
  mounted: function() {

  },
	computed: {
		loginMessage: function () {
			if(this.isLogin === false) {
				return '로그아웃'
			} else {
				return '로그인'
			}	
		},
		roleMessage: function () {
			if(this.$store.getters.role === 'ROLE_USER') {
				return 'USER'
			} else {
				return 'ADMIN'
			}	
		},
	}
};
</script>