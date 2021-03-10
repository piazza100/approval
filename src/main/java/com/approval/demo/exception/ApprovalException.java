package com.approval.demo.exception;

import java.util.Map;

import lombok.Data;

@Data
public class ApprovalException extends RuntimeException {
	private static final long serialVersionUID = -4428883185317881260L;

	private ApprovalException.Code code;
    private Map<String, Object> errorVO;

	public ApprovalException(ApprovalException.Code code) {
		this.code = code;
	}

	public ApprovalException(ApprovalException.Code code, Map<String, Object> errorVO) {
		this.code = code;
		this.errorVO = errorVO;
	}
	
	public enum Code {
		SUCCESS("E0000", "성공"),
		EXCEPTION("E1000", "에러"),
		UNAUTHORIZED("E0401", "인증이 필요합니다."),
		FORBIDDEN("E0403", "권한이 충분하지 않습니다."),
		NOT_FOUND("E0404", "페이지를 찾을 수 없습니다."),
		TOKEN_EXCEPTION("E1001", "토큰 에러"),
		TOKEN_CREATE_EXCEPTION("E1017", "토큰 생성 에러"),
		NULL_USER_EXCEPTION("E1002", "회원이 존재하지 않습니다."),
		ACCESS_DENIED_EXCEPTION("E1003", "권한이 충분하지 않습니다."),
		NULL_USER_INFO_EXCEPTION("E1004", "회원정보 등록이 필요합니다."),
		NULL_PARAM_EXCEPTION("E1009", "입력값이 부족합니다."),
		INVALID_ACCESS_EXCEPTION("E0034", "유효하지 않은 접속입니다."),
		EMAIL_USER_EXCEPTION("E1002", "일치하는 회원 정보가 없습니다."),
		;

		private String key;
		private String value;

		Code(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	public static void main(String[] args) {
		ApprovalException.Code success = ApprovalException.Code.NULL_USER_EXCEPTION;
		System.out.println(success.getKey());
		System.out.println(success.getValue());
	}

}
