package com.approval.demo.mapper;

import java.sql.SQLException;
import java.util.List;

import com.approval.demo.domain.ApprovalVO;

public interface ApprovalMapper {

	public ApprovalVO getApproval(ApprovalVO approvalVO) throws SQLException;

	public List<ApprovalVO> getApprovalListByUserNo(ApprovalVO approvalVO) throws SQLException;

	public void addApproval(ApprovalVO approvalVO) throws SQLException;
	
	public void updateApproval(ApprovalVO approvalVO) throws SQLException;

}
