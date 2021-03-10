package com.approval.demo.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.Data;

@Data
public class UserVO {
	@JsonIgnore
	private Integer userNo;

	private String userId;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	private Date regTime;
	private String role;

	public static final class USER_ROLE {
		public static final String USER = "USER";
		public static final String ADMIN = "ADMIN";
	}

}
