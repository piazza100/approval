<template>
<div>

  <Header/>
  <div>
      <h3>결재 문서 등록</h3>

      <div>제목<input id="title" type="text" value="" maxlength="30" v-model.trim="title"></div>
      <div>내용<textarea id="content" value="" v-model.trim="content"/></div>

      <select v-model="adminUserNo">
        <option disabled value="">결재선 지정</option>
        <option v-for="adminUser in adminUserList" v-bind:value="adminUser.userNo">
          {{ adminUser.userId }}
        </option>
      </select>
      <span>선택함: {{ adminUserNo }}</span>

      <div><button type="button" onclick="" @click="addApproval">등록</button></div>

  </div>
  <Footer/>



</div>
</template>

<script>
import Header from './Header.vue'
import Footer from './Footer.vue'
import axios from 'axios'

import {common} from './common/common.js'

export default {
  mixins: [common],
  components: {
    Header, Footer
  },
  data() {
    return {
      adminUserList : ''
    };
  },
  created() {
    let _this = this

    axios.get('/api/user/admin/list')
        .then(({data}) => {
          _this.adminUserList = data.result
        })
        .catch((error) => {
          if(typeof error.response.data.code !== 'undefined'){
            alert(error.response.data.message);
          }else{
            alert(_this.MSG.MESG_SERVER_ERROR);
          }
        })
        .finally(() => {
          _this.onProgress = false
        });
  },
  mounted: function() {},
  methods: {

  }
};
</script>

