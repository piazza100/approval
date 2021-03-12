import axios from 'axios'
export const api = {
  	methods: {
	    login : function(userId, password){
	  	  if(this.userId === '') {
			alert(this.MSG.MESG_INPUT_USER_ID_ERROR)
			return false
	      }
	  	  if(this.password === '') {
			alert(this.MSG.MESG_INPUT_PASSWORD_ERROR)
			return false
	      }

	      this.$store.dispatch('LOGIN', {userId, password})
	        .then(() => this.goToPages())
	        .catch(({message}) => alert(this.MSG.MESG_CHECK_USER_ID_OR_PASSWORD_ERROR))
	    },
	    goToPages () {
	    	if(this.$route.query.redirect) {
				window.location.href=this.$route.query.redirect    	
	    	}else {
				window.location.href='/main'    	
	    	}
	    },
		addApproval: function() {
			let _this = this
			this.onProgress = true

			if(this.title === ''){
				alert(this.MSG.MESG_INPUT_TITLE_ERROR)
				return
			}
			if(this.content === ''){
				alert(this.MSG.MESG_INPUT_CONTENTS_ERROR)
				return
			}
			if(this.adminUserNo === ''){
				alert(this.MSG.MESG_INPUT_ADMIN_USER_ERROR)
				return
			}

			let param = {'content' : this.content, 'title' : this.title, 'approvalLineVOList' : [{'state' : this.STATE_CODE.REQUEST, 'userNo' : this.adminUserNo}]}

			axios.post('/api/approval/add', param)
		        .then(({data}) => {
		        	alert(_this.MSG.MESG_ADD_APPROVAL_SUCCESS)
		        	location.reload()
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
		
	},
	computed: {},
}