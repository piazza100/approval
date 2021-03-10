package com.approval.demo.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ApprovalVO {
	private Integer approvaNo;
	private Integer userNo;
	private Integer adminUserNo;

	private String title;
	private String content;
	private String state;

	private Date regTime;
	private Date modTime;

	public static final class STATE {
		public static final String REQUEST = "REQUEST";
		public static final String CONFIRM = "CONFIRM";
		public static final String REJECT = "REJECT";
		public static final String DELETE = "DELETE";
	}

}
