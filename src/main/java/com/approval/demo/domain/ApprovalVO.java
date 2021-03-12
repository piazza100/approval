package com.approval.demo.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApprovalVO {
	private Integer approvalNo;
	private Integer userNo;

	@NotEmpty
	private String title;
	@NotEmpty
	private String content;

	private Date regTime;
	private Date modTime;
	private Date endTime;

	private List<ApprovalLineVO> approvalLineVOList;
}