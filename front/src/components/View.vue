<template>
<div>

  <Header/>
  <div>
      <h3>결재 내용</h3>

      <div>제목<input id="title" type="text" value="" maxlength="30" v-model.trim="title"></div>
      <div>내용<textarea id="content" value="" v-model.trim="content"/></div>

      <select v-model="adminUserNo">
        <option disabled value="">결재선 지정</option>
        <option v-for="adminUser in adminUserList" v-bind:value="adminUser.userNo">
          {{ adminUser.userId }}
        </option>
      </select>
      <span>선택함: {{ adminUserNo }}</span>

      <!-- ADMIN -->
      <div v-if="$store.getters.role === 'ROLE_ADMIN'">
        <button type="button" onclick="" @click="updateApprovalState(approvalNo, STATE_CODE.REJECT.CODE)">반려</button>
        <button type="button" onclick="" @click="updateApprovalState(approvalNo, STATE_CODE.CONFIRM.CODE)">승인</button>
      </div>

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
      approvalNo: this.$route.params.approvalNo,
      adminUserList : '',
      endTime : ''
    };
  },
  created() {
    if(typeof this.approvalNo !== 'undefined') 
      this.getApproval(this.approvalNo)

    this.getAdminList()
  },
  mounted: function() {
  },
  computed: {
    writeMessage: function () {
      if(typeof this.approvalNo !== 'undefined') {
        return '수정'
      } else {
        return '등록'
      } 
    },
  }
};
</script>

