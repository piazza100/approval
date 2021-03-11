export const validate = {
  	methods: {
		checkId: function(param){
			let inputStr = param
			if (inputStr == '')
				return "1";
			if (inputStr.length < 6 || inputStr.length > 20)
				return "2";

			let isAlphabetNum = /^[a-zA-Z0-9]*$/.test(inputStr)

			if (isAlphabetNum) {
				return "0";
			} else {
				return "3";
			}
		},
		checkPw: function(param){
			let inputStr = param
			if (inputStr == '')
				return "1";
			if (inputStr.length < 8 || inputStr.length > 15)
				return "2";

			let isAlphabet = /[a-zA-Z]/.test(inputStr)
			let isNumber = /[0-9]/.test(inputStr)
			let isSpecialChar = /[~!@#$%^&*()_+|<>?:{}]/.test(inputStr)

			// 3가지 이상 조합인가?
			if (isAlphabet && isNumber && isSpecialChar) {
				return "0";
			} else {
				return "3";
			}
		},
	}
}