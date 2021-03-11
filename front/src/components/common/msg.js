export const msg = {
	data() {
		return {
			MSG: {
				MESG_PROGRESS : "처리중입니다."
				,MESG_SERVER_ERROR : "서버의 응답이 없습니다. 다시 시도해 주세요."
				,MESG_TOKEN_TIME_OUT : "세션시간이 만료됐습니다."

				,MESG_INPUT_USER_ID_ERROR : "아이디를 입력해주세요."
				,MESG_INPUT_PASSWORD_ERROR : "비밀번호를 입력해주세요."
				,MESG_CHECK_USER_ID_OR_PASSWORD_ERROR : "아이디 또는 비밀번호를 확인하세요."
				
			},
			STATE_CODE: {
				REQUEST : "R"
				,CONFIRM : "C"
				,REJECT : "J"
				,DELETE : "D"
			},
		};
	},
}