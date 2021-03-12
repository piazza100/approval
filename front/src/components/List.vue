<template>
<div>

  <Header/>
  <div>
      <h3>결재 목록</h3>

      <table border="1">
        <tr>
          <td width="200px;">제목</td>
          <td width="500px;">내용</td>
          <td width="200px;">상태</td>
        </tr>
        <tr v-for="approval in approvalList">
          <td>{{ approval.title }}</td>
          <td>{{ approval.content }}</td>
          <td>{{ approval.approvalLineVOList[0].state }}</td>
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
    let _this = this

    axios.get('/api/approval/list')
        .then(({data}) => {
          _this.approvalList = data.result
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

