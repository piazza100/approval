export const msg = {
	data() {
		return {
			MSG: {
				MESG_PROGRESS : "처리중입니다."
				,MESG_SERVER_ERROR : "서버의 응답이 없습니다. 다시 시도해 주세요."
				,MESG_TOKEN_TIME_OUT : "세션시간이 만료됐습니다."

				,MESG_INPUT_TITLE_ERROR : "제목를 입력해주세요."
				,MESG_INPUT_CONTENTS_ERROR : "내용을 입력해주세요."
				,MESG_INPUT_ADMIN_USER_ERROR : "결재자를 선택해주세요."
				,MESG_INPUT_USER_ID_ERROR : "아이디를 입력해주세요."
				,MESG_INPUT_PASSWORD_ERROR : "비밀번호를 입력해주세요."
				,MESG_CHECK_USER_ID_OR_PASSWORD_ERROR : "아이디 또는 비밀번호를 확인하세요."

				,MESG_ADD_APPROVAL_SUCCESS : "결재가 등록 되었습니다."
				,MESG_UPDATE_APPROVAL_SUCCESS : "결재가 수정 되었습니다."
				,MESG_DELETE_APPROVAL_SUCCESS : "결재가 삭제 되었습니다."
				,MESG_REJECT_APPROVAL_SUCCESS : "결재가 반려 되었습니다."
				,MESG_CONFIRM_APPROVAL_SUCCESS : "결재가 승인 되었습니다."
			},
			STATE_CODE: {
				REQUEST : { "CODE" : "REQUEST", "MESSAGE" : "요청" }
				,CONFIRM : { "CODE" : "CONFIRM", "MESSAGE" : "승인" }
				,REJECT : { "CODE" : "REJECT", "MESSAGE" : "반려" }
				,DELETE : { "CODE" : "DELETE", "MESSAGE" : "삭제" }
			},
		};
	},
}