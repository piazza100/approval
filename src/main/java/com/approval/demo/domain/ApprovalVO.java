package com.approval.demo.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class ApprovalVO {
	private Integer approvaNo;
	private Integer userNo;
//	private Integer adminUserNo;

	@NotEmpty
	private String title;
	@NotEmpty
	private String content;
//	@NotEmpty
//	@Pattern(regexp = "^R$|^D$|^C$|^J$")
//	private String state;

	private Date regTime;
	private Date modTime;
	private Date endTime;
	
	private List<ApprovalLineVO> approvalLineVOList;

	/*public static final class STATE {
		public static final String REQUEST = "R";
		public static final String CONFIRM = "C";
		public static final String REJECT = "J";
		public static final String DELETE = "D";
	}*/

}