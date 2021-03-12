package com.approval.demo.domain;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class ApprovalLineVO {
	private Integer approvalNo;
	private Integer userNo;
	private Integer seq;

	@NotEmpty
	@Pattern(regexp = "^DELETE$|^REQUEST$|^CONFIRM$|^REJECT$")
	private String state;

	private Date regTime;
	private Date modTime;

	public static final class STATE {
		public static final String READY = "READY";
		public static final String REQUEST = "REQUEST";
		public static final String CONFIRM = "CONFIRM";
		public static final String REJECT = "REJECT";
		public static final String DELETE = "DELETE";
	}

}
