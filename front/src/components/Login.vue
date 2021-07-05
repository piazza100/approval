<template>
<div>

	<Header/>
	<div>
			<h3>결재 서비스 로그인</h3>

			<div>아이디<input name="login" id="id" type="text" value="" maxlength="" v-model.trim="userId"></div>
      <div>비밀번호<input name="login" id="pw" type="password" value="" v-model.trim="password" @keyup.enter="login(userId, password)"></div>

      <div><button type="button" onclick="" @click="login(userId, password)">로그인 1</button></div>
	</div>
	<Footer/>



</div>
</template>

<script>
import Footer from './Footer.vue'
import {common} from './common/common.js'

export default {
  mixins: [common],
  components: {
    Footer
  },
  data() {
    return {
    };
  },
  created() {},
  mounted: function() {},
  methods: {
  }
};
</script>

