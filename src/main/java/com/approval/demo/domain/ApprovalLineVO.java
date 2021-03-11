package com.approval.demo.domain;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class ApprovalLineVO {
	private Integer approvalLineNo;
	private Integer approvaNo;
	private Integer userNo;

//	private String title;
//	private String content;

	@NotEmpty
	@Pattern(regexp = "^R$|^D$|^C$|^J$")
	private String state;

	private Date regTime;
	private Date modTime;

	public static final class STATE {
		public static final String REQUEST = "R";
		public static final String CONFIRM = "C";
		public static final String REJECT = "J";
		public static final String DELETE = "D";
	}

}
