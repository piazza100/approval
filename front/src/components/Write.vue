<template>
<div>

  <Header/>
  <div>
      <h3>결재 문서 등록</h3>

      <div>제목<input id="title" type="text" value="" maxlength="30" v-model.trim="title"></div>
      <div>내용<textarea id="content" value="" v-model.trim="content"/></div>

      <div><button type="button" onclick="" @click="addApproval">등록</button></div>

  </div>
  <Footer/>



</div>
</template>

<script>
import Header from './Header.vue'
import Footer from './Footer.vue'

import {common} from './common/common.js'

export default {
  mixins: [common],
  components: {
    Header, Footer
  },
  data() {
    return {
      // userId: '',
      // password: '',
      // msg: '',
    };
  },
  created() {},
  mounted: function() {},
  methods: {

  }
};
</script>

