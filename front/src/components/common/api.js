import axios from 'axios'
export const api = {
  	methods: {
  		getApprovalList : function(){
  			let url = '/api/approval/list'
  			if(this.$store.getters.role === 'ROLE_ADMIN'){
  				url = '/api/approval/admin/list'
  			}
		    axios.get(url)
		        .then(({data}) => {
		          this.approvalVoList = data
		        })
		        .catch((error) => {
					this.commonError(error)
		        })
		        .finally(() => {
		          this.onProgress = false
		        });
  		},
  		getAdminList : function(approvalNo){
		    axios.get('/api/user/admin/list')
		        .then(({data}) => {
		          this.adminUserList = data
		        })
		        .catch((error) => {
					this.commonError(error)
		        })
		        .finally(() => {
		          this.onProgress = false
		        });
  		},
  		getApproval : function(approvalNo){
		    axios.get('/api/approval/getApproval?approvalNo=' + approvalNo)
		        .then(({data}) => {
		          let approvalVo = data
		          this.title = approvalVo.title
		          this.content = approvalVo.content
		          this.adminUserNo = approvalVo.approvalLineVOList[0].userNo
		          this.endTime = approvalVo.endTime
		        })
		        .catch((error) => {
					this.commonError(error)
		        })
		        .finally(() => {
		          this.onProgress = false
		        });
  		},
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
			.catch((error) => {
				if(typeof error.response.data.code !== 'undefined'){
					alert(this.MSG.MESG_CHECK_USER_ID_OR_PASSWORD_ERROR);
				}else{
					alert(this.MSG.MESG_SERVER_ERROR);
				}
			})
	    },
	    goToPages () {
	    	if(this.$route.query.redirect) {
				window.location.href=this.$route.query.redirect    	
	    	}else {
				window.location.href='/list'
	    	}
	    },
		addApproval: function(approvalNo) {
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

			let param = {'content' : this.content, 'title' : this.title, 'approvalLineVOList' : [{"state" : this.STATE_CODE.REQUEST.CODE,'userNo' : this.adminUserNo}]}
			let url = '/api/approval/add'
			if(typeof approvalNo !== 'undefined'){
				param.approvalNo = this.approvalNo
				url = '/api/approval/update'
			}
			axios.post(url, param)
		        .then(({data}) => {
		        	if (typeof approvalNo === 'undefined') {
			        	alert(this.MSG.MESG_ADD_APPROVAL_SUCCESS)
		        	} else {
			        	alert(this.MSG.MESG_UPDATE_APPROVAL_SUCCESS)
		        	}
		        	window.location.href='/list'
		        })
				.catch((error) => {
					this.commonError(error)
				})
				.finally(() => {
					this.onProgress = false
				});
		},
		updateApprovalState: function(approvalNo, state) {
			this.onProgress = true

			let param = {'state' : state,'approvalNo' : this.approvalNo}
			let url = '/api/approval/admin/update'

			axios.post(url, param)
				.then(({data}) => {
					if(state === 'CONFIRM'){
						alert(this.MSG.MESG_CONFIRM_APPROVAL_SUCCESS)
					}else{
						alert(this.MSG.MESG_REJECT_APPROVAL_SUCCESS)
					}
		        	window.location.href='/list'
				})
				.catch((error) => {
					this.commonError(error)
				})
				.finally(() => {
					this.onProgress = false
				});
		},
		deleteApproval: function(approvalNo) {
			this.onProgress = true

			let param = {'approvalNo' : this.approvalNo}
			let url = '/api/approval/delete'

			axios.post(url, param)
				.then(({data}) => {
					alert(this.MSG.MESG_DELETE_APPROVAL_SUCCESS)
					window.location.href='/list'
				})
				.catch((error) => {
					this.commonError(error)
				})
				.finally(() => {
					this.onProgress = false
				});
		},
		commonError: function(error) {
			if(typeof error.response.data.code !== 'undefined'){
				alert(error.response.data.message);
				if(error.response.data.code === 'E1006'){
		        	window.location.href='/list'
				}
			}else{
				alert(this.MSG.MESG_SERVER_ERROR);
			}
		},
	},
	computed: {},
}