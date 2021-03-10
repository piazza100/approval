<template>
<div>

	<Header/>
	<div>
			<h3>결재 서비스 로그인</h3>

				<div>아이디<input name="login" id="id" type="text" value="" maxlength="" v-model.trim="userId"></div>
        <div>비밀번호<input name="login" id="pw" type="password" value="" v-model.trim="password" @keyup.enter="login(userId, password)"></div>

        <div><button type="button" onclick="" @click="login(userId, password)">로그인</button></div>
	</div>
	<Footer/>



</div>
</template>

<script>
import Footer from './common/Footer.vue'

export default {
  components: {
    Footer
  },
  data() {
    return {
      userId: 'piazza100',
      password: '123qwe!@#',
      msg: '',
    };
  },
  created() {},
  mounted: function() {},
  methods: {
    login : function(userId, password){
  	  if(this.userId === '') {
		alert('아이디를 입력해주세요.')
		return false
      }
  	  if(this.password === '') {
		alert('비밀번호를 입력해주세요.')
		return false
      }

      this.$store.dispatch('LOGIN', {userId, password})
        .then(() => this.goToPages())
        .catch(({message}) => alert('아이디 또는 비밀번호를 확인하세요.'))
    },
    goToPages () {
    	if(this.$route.query.redirect) {
			window.location.href=this.$route.query.redirect    	
    	}else {
			window.location.href='/main'    	
    	}
    }
  }
};
</script>

