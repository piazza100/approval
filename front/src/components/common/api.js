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
		// selectCoinTicket: function(coinTicket) {
		// 	this.init('ALL')

		// 	if(typeof coinTicket !== 'undefined'){
		// 		this.coinTicket = coinTicket
		// 	}

		// 	if(this.$store.getters.encUserKey){
		// 		if(!localStorage.token){
		// 			alert(this.MSG.MESG_TOKEN_TIME_OUT)
		// 			location.reload()
		// 			return
		// 		}
		// 		if(this.coinTicket === 'FREE_1'){
		// 			alert(this.MSG.MESG_USER_KEY_ERROR)
		// 		}else{
		// 			this.payment()
		// 		}
		// 	}else{
		// 		let siteCode = this.$route.query.siteCode
		// 		if('MOBILE' === this.joinType){
		// 			$('#ly_infowrap').show()
		// 		}else if('WEB' === this.joinType){
		// 			layer_open('join')
		// 		}
		// 		if(this.coinTicket === 'FREE_1'){
		// 			this.isPaymentPopup = false
		// 		}else{
		// 			this.isPaymentPopup = true
		// 		}
		// 	}
		// 	return false
		// },
		// payment: function() {
		// 	let _this = this
		// 	let param = {'encUserKey' : this.$store.getters.encUserKey, 'siteCode' : this.siteCode, 'rParams' : this.rParams}
		// 	$.get('/api/payment/checkPayment', param, function(data){
		// 		if(data.result === 'Y'){
		// 			window.open("/api/payment/danal/ready?itemCode=" + _this.coinTicket + '&encUserKey=' + _this.$store.getters.encUserKey + '&siteCode=' + _this.siteCode + '&rParams=' + _this.rParams, "ready", "width=550, height=700");
		// 		}
		// 	},'json')
		// 	.fail(function(data) {
		// 		if(typeof data.responseJSON !== 'undefined'){
		// 			alert(data.responseJSON.message);
		// 		}else{
		// 			alert(_this.MSG.MESG_SERVER_ERROR);
		// 		}
		// 	})
		// 	.always(function(data) {
		// 	});
		// },
		// checkValidation : function(){
		// 	let isValidate = true
		// 	if (this.onProgress) {
		// 		alert(this.MSG.MESG_PROGRESS);
		// 		isValidate = false
		// 	}
		// 	return isValidate
		// },
		addApproval: function() {
			let _this = this

			// this.onProgress = true
			// this.isLoading = true
			let param = {'content' : this.content, 'title' : this.title, 'approvalLineVOList' : [{'state' : 'R', 'approvalLineNo' : '111'}]}
			// let param = {'content' : this.content, 'title' : this.title, 'approvalLineVOList.state' : 'R', 'approvalLineVOList.approvalLineNo' : '111'}


		axios.post('/api/approval/add', JSON.stringify(param), {
          headers: {
            "Content-Type": `application/json`,
          },
        })
        .then(({data}) => {
        	alert(data)
        })
			// $.get('/api/approval/add', param, function(data){
			// 	alert(1)
			// 	if(data.result < 1){
			// 		// if(existType === 'MDN'){
			// 		// 	_this.mdnMsg = _this.MSG.SUCCESS + '사용 가능한 휴대폰번호입니다.'
			// 		// 	_this.onExistMdnCount = true

			// 		// }else if(existType === 'EMAIL'){
			// 		// 	_this.emailMsg = _this.MSG.SUCCESS + '사용 가능한 이메일입니다.'
			// 		// 	_this.onExistEmailCount = true
			// 		// }

			// 	}else{
			// 		// if(existType === 'MDN'){
			// 		// 	_this.mdn2 = ''
			// 		// 	_this.mdnMsg = _this.MSG.FAIL + '이미 사용중인 휴대폰번호입니다.'
			// 		// }else if(existType === 'EMAIL'){
			// 		// 	_this.email = ''
			// 		// 	_this.emailMsg = _this.MSG.FAIL + '이미 사용중인 이메일입니다.'
			// 		// }
			// 	}
			// },'json')
			// .fail(function(data) {
			// 	// if(typeof data.responseJSON !== 'undefined'){
			// 	// 	if(existType === 'MDN'){
			// 	// 		_this.mdnMsg = _this.MSG.FAIL + data.responseJSON.message
			// 	// 	}else if(existType === 'EMAIL'){
			// 	// 		_this.emailMsg = _this.MSG.FAIL + data.responseJSON.message
			// 	// 	}
			// 	// }else{
			// 	// 	if(existType === 'MDN'){
			// 	// 		_this.mdnMsg = _this.MSG.FAIL + _this.MSG.MESG_SERVER_ERROR
			// 	// 	}else if(existType === 'EMAIL'){
			// 	// 		_this.emailMsg = _this.MSG.FAIL + _this.MSG.MESG_SERVER_ERROR
			// 	// 	}
			// 	// }
			// })
			// .always(function(data) {
			// 	// _this.onProgress = false
			// 	// _this.isLoading = false
			// });
		},
		
		// submitRegist: function(type) {
		// 	if(!this.checkValidation()){
		// 		return false
		// 	}

		// 	if(this.email.length === 0) {
		// 		this.emailMsg = this.MSG.FAIL + this.MSG.MESG_INPUT_EMAIL_ERROR
		// 		return false
		// 	}
		// 	if(this.onExistEmailCount === false) {
		// 		this.emailMsg = this.MSG.FAIL + this.MSG.MESG_CHECK_EMAIL_ERROR
		// 		return false
		// 	}
		// 	// 중간에 번호가 변경됐는지 확인.. mdn select 박스가.. v-model 처리가 안됨..
		// 	if(this.mdn2.length === 0){
		// 		this.mdnMsg = this.MSG.FAIL + this.MSG.MESG_INPUT_MDN_ERROR
		// 		return false
		// 	}
		// 	if(this.mdn1 !== ($('#hp-num option:selected').val() + this.mdn2)){
		// 		this.mdn1 = ''
		// 		this.mdn2 = ''
		// 		this.mdnMsg = this.MSG.FAIL + this.MSG.MESG_CHECK_MDN_ERROR
		// 		return false
		// 	}
		// 	if(this.onExistMdnCount === false) {
		// 		this.mdnMsg = this.MSG.FAIL + this.MSG.MESG_CHECK_MDN_ERROR
		// 		return false
		// 	}
		// 	if(this.password.length === 0) {
		// 		this.passwordMsg = this.MSG.FAIL + this.MSG.MESG_INPUT_PASSWORD_ERROR
		// 		return false
		// 	}
		// 	if(this.password === '' || this.checkPw(this.password) !== '0') {
		// 		this.passwordMsg = this.MSG.FAIL + this.MSG.MESG_CHECK_PASSWORD_ERROR
		// 		return false
		// 	}else{
		// 		this.passwordMsg = ''
		// 	}
		// 	if(this.password2.length === 0) {
		// 		this.password2Msg = this.MSG.FAIL + this.MSG.MESG_INPUT_PASSWORD_ERROR
		// 		return false
		// 	}
		// 	if(this.password2 === '' || this.checkPw(this.password2) !== '0') {
		// 		this.password2Msg = this.MSG.FAIL + this.MSG.MESG_CHECK_PASSWORD_ERROR
		// 		return false
		// 	}else{
		// 		this.password2Msg = ''
		// 	}
		// 	if(this.password !== this.password2) {
		// 		this.password2Msg = this.MSG.FAIL + this.MSG.MESG_MATCH_PASSWORD_ERROR
		// 		return false
		// 	}else{
		// 		this.password2Msg = ''
		// 	}
		// 	if(!this.checkAgreeAll()){
		// 		return false
		// 	}
		// 	this.onProgress = true
		// 	this.isLoading = true
		// 	let _this = this
		// 	let param = {'email' : this.email, 'password' : this.password, 'mdn' : this.mdn1, 'itemCode' : this.coinTicket, 'siteCode' : this.siteCode, 'ctgCode' : this.ctgCode}
		// 	$.post('/api/user/submitRegist', param, function(data){
		// 		if(data.result === 'Y'){
		// 			alert('회원가입이 완료되었습니다.')
		// 			if('WEB' === type){
		// 				layer_close()
		// 			}else if ('MOBILE' === this.joinType){
		// 				$('#ly_infowrap').hide()
		// 			}
		// 			_this.login(_this.email, _this.password)//자동 로그인
		// 		}
		// 	},'json')
		// 	.fail(function(data) {
		// 		if(typeof data.responseJSON !== 'undefined'){
		// 			alert(data.responseJSON.message);
		// 		}else{
		// 			alert(_this.MSG.MESG_SERVER_ERROR);
		// 		}
		// 	})
		// 	.always(function(data) {
		// 		_this.onProgress = false
		// 		_this.isLoading = false
		// 	});
		// },
		// changeInput: function(type) {
		// 	if('ID' === type) {
		// 		this.onExistIdCount = false
		// 	}
		// 	if('EMAIL' === type) {
		// 		this.onExistEmailCount = false
		// 	}
		// },
		// setStep2 :function(){
		// 	//인증번호 입력 영역
		// 	alert(this.MSG.MESG_SMS_SEND_COMPLETE)

		// 	this.auth_timer = this.MSG.MESG_SMS_VALIDATION_TIME_THREE
		// 	let _this = this
		// 	this.authTimer = setTimeout(function() {
		// 		_this.displayTimer();
		// 	}, 1000);
		// 	this.isReadOnly(true);
		// },
		// checkPhone: function() {
		// 	if(!this.checkValidation()){
		// 		return false;
		// 	}
		// 	if ($('#hp-num option:selected').val() == '' || this.mdn2 == '' || this.checkMdn($('#hp-num option:selected').val() + this.mdn2) !== '0') {
		// 		alert(this.MSG.MESG_INPUT_CORRECT_MDN);
		// 		return false;
		// 	}
		// 	if (this.authTimer != null) {
		// 		clearTimeout(this.authTimer);
		// 		this.auth_timer = this.MSG.MESG_RECEIVE_AUTH_INPUT
		// 	}
		// 	this.isLoading = true
		// 	this.onProgress = true
		// 	let _this = this
		// 	let param = {'mdn' : $('#hp-num option:selected').val() + this.mdn2, 'itemCode' : this.coinTicket, 'checkPhoneType' : this.checkPhoneType, 'siteCode' : this.siteCode, 'ctgCode' : this.ctgCode, 'rParams' : this.rParams}
		// 	$.post('/api/user/checkPhone', param, function(data){
		// 		if(data.result){
		// 			_this.onAuthCode = true
		// 			_this.setStep2()
		// 		}
		// 	},'json')
		// 	.fail(function(data) {
		// 		if(typeof data.responseJSON !== 'undefined'){
		// 			alert(data.responseJSON.message);
		// 		}else{
		// 			alert(_this.MSG.MESG_SERVER_ERROR);
		// 		}
		// 	})
		// 	.always(function(data) {
		// 		_this.onProgress = false
		// 		_this.isLoading = false
		// 	});
		// 	return false
		// },
		// checkAuthcode: function() {
		// 	if(!this.checkValidation()){
		// 		return false;
		// 	}
		// 	if (this.authCode == '') {
		// 		alert(this.MSG.MESG_INPUT_AUTH_CODE);
		// 		return false;
		// 	}
		// 	this.isLoading = true
		// 	this.onProgress = true
		// 	let _this = this
		// 	let param = {'mdn' : $('#hp-num option:selected').val() + this.mdn2, 'itemCode' : this.coinTicket, 'authCode' : this.authCode, 'siteCode' : this.siteCode, 'ctgCode' : this.ctgCode, 'checkPhoneType' : this.checkPhoneType}
  // 			$.ajax({
		// 		type: 'POST',
		// 		dataType : 'json',
		// 		url: '/api/user/checkAuthcode',
		// 		data: param,
		// 		async: false,
		// 		success: function(data) {
		// 			if(data.result == 'Y'){
		// 				_this.isAuthResult = true
		// 				alert(_this.MSG.MESG_AUTH_MDN_SUCCESS)
		// 				if('SEARCH_ID' === _this.checkPhoneType){
		// 					_this.encUserKey = data.encUserKey
		// 					_this.searchButton()
		// 				}
		// 			}else{
		// 				alert(_this.MSG.MESG_SMS_AUTH_ERROR)
		// 			}
		// 		},
		// 		error: function(data) {
		// 			if(typeof data.responseJSON !== 'undefined'){
		// 				alert(data.responseJSON.message);
		// 			}else{
		// 				alert(_this.MSG.MESG_SERVER_ERROR);
		// 			}
		// 		},
		// 		complete: function() {
		// 			_this.isLoading = false
		// 			_this.onProgress = false
		// 		}
  //         	});
		// },
		// displayTimer: function() {
		// 	var html = this.auth_timer
		// 	if(html == null){
		// 		return
		// 	}
		// 	var min = html.substr(6, 2);
		// 	var sec = html.substr(9, 2);
			
		// 	if (sec == "00") {
		// 		min = "0" + (parseInt(min) - 1);
		// 		sec = "59";
		// 	} else {
		// 		if (html.substr(9, 1) == "0") {
		// 			sec = html.substr(10, 1);
		// 		}
		// 		var tmp = parseInt(sec) - 1;
		// 		if (tmp < 10) {
		// 			sec = "0" + tmp;
		// 		} else {
		// 			sec = tmp;
		// 		}
		// 	}
		// 	if (min == 0 && sec == 0) {
		// 		this.auth_timer = this.MSG.MESG_SMS_VALIDATION_TIME_EXP
		// 	} else {
		// 		this.auth_timer = "("+this.MSG.MESG_SMS_VALIDATION_TIME+" " + min + ":" + sec+")"
		// 		let _this = this
		// 		this.authTimer = setTimeout(function() {
		// 			_this.displayTimer(_this.auth_timer);
		// 		}, 1000);
		// 	}
		// },
		// isReadOnly : function(isReadOnly){
		// 	if(isReadOnly){
		// 		$("#hp-num").not(":selected").attr("disabled", "disabled");
		// 		$("#hp").attr("readOnly", true);
		// 		try{
		// 			$("select").selectmenu("refresh", true);
		// 		}catch(e) {
		// 			console.log(e)
		// 		}
		// 	}else{
		// 		$("#hp-num").not(":selected").removeAttr("disabled");
		// 		$("#hp").removeAttr("readOnly");
		// 		try{
		// 			$("select").selectmenu("refresh", false);
		// 		}catch(e) {
		// 			console.log(e)
		// 		}
		// 	}
		// },
		// checkAgreeAll : function(){
		// 	var allCheck = false;

		// 	if (!this.agreeService || !this.agreePrivate || !this.agreePrivate2 || !this.agreeConsign) {
		// 		if(confirm(this.MSG.MESG_AGREEMENT_IS_REQUIRED)){
		// 			this.agreeAll = true
		// 			this.agreeService = true
		// 			this.agreePrivate = true
		// 			this.agreePrivate2 = true
		// 			this.agreeConsign = true
					
		// 			allCheck = true
		// 		}
		// 	}else{
		// 		allCheck = true
		// 	}
		// 	if(!allCheck){
		// 		return false
		// 	}
		// 	return true
		// },
		// checkAgree : function(e){
		// 	if(typeof e.target.attributes.class !== 'undefined' && e.target.attributes.class.value == 'on'){
		// 		if(e.target.attributes.for.value === 'all'){
		// 			this.agreeAll = false
		// 			this.agreeService = false
		// 			this.agreePrivate = false
		// 			this.agreePrivate2 = false
		// 			this.agreeConsign = false
					
		// 		}else{
		// 			if(e.target.attributes.for.value === 'service'){
		// 				this.agreeService = false
		// 			}else if(e.target.attributes.for.value === 'private'){
		// 				this.agreePrivate = false
		// 			}else if(e.target.attributes.for.value === 'private2'){
		// 				this.agreePrivate2 = false
		// 			}else if(e.target.attributes.for.value === 'consign'){
		// 				this.agreeConsign = false
		// 			}
		// 		}
				
		// 		if (!this.agreeService || !this.agreePrivate || !this.agreePrivate2 || !this.agreeConsign) {
		// 			this.agreeAll = false
		// 		}
		// 	}else{
		// 		if(e.target.attributes.for.value === 'all'){
		// 			this.agreeAll = true
		// 			this.agreeService = true
		// 			this.agreePrivate = true
		// 			this.agreePrivate2 = true
		// 			this.agreeConsign = true
		// 		}else{
		// 			if(e.target.attributes.for.value === 'service'){
		// 				this.agreeService = true
		// 			}else if(e.target.attributes.for.value === 'private'){
		// 				this.agreePrivate = true
		// 			}else if(e.target.attributes.for.value === 'private2'){
		// 				this.agreePrivate2 = true
		// 			}else if(e.target.attributes.for.value === 'consign'){
		// 				this.agreeConsign = true
		// 			}
		// 		}
				
		// 		if (this.agreeService && this.agreePrivate && this.agreePrivate2 && this.agreeConsign) {
		// 			this.agreeAll = true
		// 		}
		// 	}
		// },
		// agreePopup : function(agreeType){
		// 	layer_open('agree_popup_' + agreeType)
		// 	if(agreeType === 'service'){
		// 		this.agreeService = true
		// 	}else if(agreeType === 'private'){
		// 		this.agreePrivate = true
		// 	}else if(agreeType === 'private2'){
		// 		this.agreePrivate2 = true
		// 	}else if(agreeType === 'consign'){
		// 		this.agreeConsign = true
		// 	}

		// 	if (this.agreeService && this.agreePrivate && this.agreePrivate2 && this.agreeConsign) {
		// 		this.agreeAll = true
		// 	}
		// },
	// 	setRParams () {
	// 	  let rParams = '';
	// 	  let _tempUrl = window.location.search.substring(1);
	// 	  let _tempArray = _tempUrl.split('&');
	// 	  for(let i = 0; i < _tempArray.length; i++) {
	// 	    let _keyValuePair = _tempArray[i].split('=');
	// 	    if(!(_keyValuePair[0] == 'siteCode' || _keyValuePair[0] == 'ctgCode' || _keyValuePair[0] == 'cList' || _keyValuePair[0] == 'headImg' || _keyValuePair[0] == 'isFree')){
	// 	      rParams += _keyValuePair[0].replace(/\s/gi, '+');
	// 	      rParams += ':';
	// 	      rParams += _keyValuePair[1];
	// 	      rParams += ',';
	// 	    }
	// 	  }
	// 	  this.rParams = rParams
	// 	},
	// 	init (type) {
	// 		this.mdn1= ''
	// 		this.mdn2= ''
	// 		this.authCode= ''
	// 		this.auth_timer = null
	// 		this.authTimer = null
	// 		this.onAuthCode= false
	// 		this.isAuthResult= false
	// 		this.emailMsg= ''
	// 		this.mdnMsg= ''
	// 		this.passwordMsg= ''
	// 		this.password2Msg= ''

	// 		if(type === 'ALL'){
	// 			this.onExistIdCount = false
	// 			this.onExistEmailCount = false
	// 			this.email = ''
	// 			this.userId = ''
	// 			this.password = ''
	// 			this.password2 = ''
	// 			this.agreeAll = false
	// 			this.agreeService = false
	// 			this.agreePrivate = false
	// 			this.agreePrivate2 = false
	// 			this.agreeConsign = false
	// 		}
	// 	},
	},
	computed: {},
}