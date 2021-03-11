<template>
<div>

	<Header/>
	<div>
			<h3>결재 서비스 메인</h3>
	</div>
	<Footer/>



</div>
</template>

<script>
import Header from './Header.vue'
import Footer from './Footer.vue'

export default {
  components: {
    Header, Footer
  },
  data() {
    return {
      userId: '',
      password: '',
      msg: '',
    };
  },
  created() {},
  mounted: function() {},
  methods: {

  }
};
</script>

