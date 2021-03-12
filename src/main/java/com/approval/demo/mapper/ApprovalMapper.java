package com.approval.demo.mapper;

import java.sql.SQLException;
import java.util.List;

import com.approval.demo.domain.ApprovalLineVO;
import com.approval.demo.domain.ApprovalVO;

public interface ApprovalMapper {

	public ApprovalVO getApproval(ApprovalVO approvalVO) throws SQLException;

	public List<ApprovalVO> getApprovalList(ApprovalVO approvalVO) throws SQLException;

	public List<ApprovalVO> getApprovalRequestList(ApprovalVO approvalVO) throws SQLException;
	
	public int addApproval(ApprovalVO approvalVO) throws SQLException;
	
	public void addApprovalLine(ApprovalLineVO approvalLineVO) throws SQLException;
	
	public void updateApproval(ApprovalVO approvalVO) throws SQLException;

	public void updateApprovalLine(ApprovalLineVO approvalLineVO) throws SQLException;
	
	public void closeApproval(ApprovalVO approvalVO) throws SQLException;
	
	public Integer getNextSeq(ApprovalVO approvalVO) throws SQLException;
	
}
