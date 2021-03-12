<template>
	<header>

		<!-- <a href="/main">결재 메인</a> -->
		<a href="/list"> | 결재 목록</a>
		<!-- <a href="/view"> | 결재 문서</a> -->
		<a href="/write"> | 결재 문서 등록</a>
		<a href="/login" v-show="this.$store.getters.isExpire !== false"><span>{{loginMessage}}</span></a></span>
		<a href="" @click.prevent="logout" v-show="this.$store.getters.isExpire === false"><span> | {{loginMessage}}</span></a>

		({{roleMessage}})

	</header>
</template>

<script>

export default {
  data() {
    return {};
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
			if(this.$store.getters.isExpire === false) {
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