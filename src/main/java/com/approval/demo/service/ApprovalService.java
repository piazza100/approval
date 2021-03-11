package com.approval.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.approval.demo.domain.ApprovalVO;
import com.approval.demo.domain.UserVO;
import com.approval.demo.exception.ApprovalException;
import com.approval.demo.mapper.ApprovalMapper;
import com.approval.demo.mapper.UserMapper;

@Service
public class ApprovalService {
	@Autowired
	UserMapper userMapper;

	@Autowired
	ApprovalMapper approvalMapper;

	public ApprovalVO getApproval(ApprovalVO approvalVO) throws Exception {
		return approvalMapper.getApproval(approvalVO);
	}

	public List<ApprovalVO> getApprovalListByUserNo(ApprovalVO approvalVO) throws Exception {
		return approvalMapper.getApprovalListByUserNo(approvalVO);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public void addApproval(ApprovalVO approvalVO) throws Exception {
		approvalMapper.addApproval(approvalVO);

	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public void updateApproval(ApprovalVO approvalVO) throws Exception {
		approvalMapper.updateApproval(approvalVO);
	}

	public void checkByRole(ApprovalVO approvalVO) throws Exception {
		UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if ((UserVO.USER_ROLE.USER).equals(userVO.getRole())
				&& !((ApprovalVO.STATE.REQUEST).equals(approvalVO.getState())
						|| (ApprovalVO.STATE.REJECT).equals(approvalVO.getState()))) {
			throw new ApprovalException(ApprovalException.Code.ACCESS_DENIED_EXCEPTION);
		}
	}

}
