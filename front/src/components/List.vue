<template>
<div>

  <Header/>
  <div>
      <h3>결재 목록</h3>

      <table border="1">
        <tr>
          <td width="200px;">결재 번호</td>
          <td width="200px;">제목</td>
          <td width="500px;">내용</td>
          <td width="200px;">결재 상태</td>
          <td width="200px;">종료일시</td>
        </tr>
        <tr v-for="approval in approvalVoList">
          <td>{{ approval.approvalNo }}</td>
          <td>{{ approval.title }}</td>
          <td v-if="$store.getters.role === 'ROLE_USER'"><a :href="`/write/${approval.approvalNo}`">{{ approval.content }}</a></td>
          <td v-else><a :href="`/view/${approval.approvalNo}`">{{ approval.content }}</a></td>
          <td>
            <span v-for="approvalLine in approval.approvalLineVOList">
              <span v-if="approval.endTime !== null && approvalLine.state === STATE_CODE.REQUEST.CODE">
              {{ STATE_CODE.DELETE.MESSAGE }}
              </span>
              <span v-else>
              {{ approvalLine.state | getStateCodeMessage(approvalLine.state) }}
              </span>
            </span>
          </td>
          <td>{{ approval.endTime | formatDate('YYYY/MM/DD HH:mm') }}</td>
        </tr>
      </table>

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
    };
  },
  created() {
    this.getApprovalList()
  },
  mounted: function() {},
  methods: {

  },
  filters: {
    getStateCodeMessage: function (value) {
      let messge
      if ('REQUEST' === value){
        messge = '요청'
      } else if ('CONFIRM' === value) {
        messge = '승인'
      } else if ('REJECT' === value) {
        messge = '반려'
      } else if ('DELETE' === value) {
        messge = '삭제'
      }
      return messge
    },
  },
};
</script>

