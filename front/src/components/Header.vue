<template>
	<header>

		<a href="/use">서비스 이용</a>
		<a href="/login" v-show="this.$store.getters.isExpire !== false"><span>{{loginMessage}}</span></a>
		<a href="" @click.prevent="logout" v-show="this.$store.getters.isExpire === false"><span>{{loginMessage}}</span></a>

	</header>
</template>

<script>
import store from '../store'

export default {
  data() {
    return {};
  },
  created() {
  },
  methods: {},
  mounted: function() {
  },
	computed: {
		loginMessage: function () {
			if(this.$store.getters.isExpire === false) {
				return '로그아웃'
			} else {
				return '로그인'
			}	
		}
	}
};
</script>