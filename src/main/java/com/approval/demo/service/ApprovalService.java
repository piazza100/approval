package com.approval.demo.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.approval.demo.domain.ApprovalLineVO;
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

	public List<ApprovalVO> getApprovalList(ApprovalVO approvalVO) throws Exception {
		return approvalMapper.getApprovalList(approvalVO);
	}
	
	public List<ApprovalVO> getApprovalRequestList(ApprovalVO approvalVO) throws Exception {
		return approvalMapper.getApprovalRequestList(approvalVO);
	}

//	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
//	public void addApproval(ApprovalVO approvalVO) throws Exception {
//		UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		approvalVO.setUserNo(userVO.getUserNo());
//
//		// addApproval
//		approvalMapper.addApproval(approvalVO);
//
//		// addApprovalLine
//		for (int i = 0; i < approvalVO.getApprovalLineVOList().size(); i++) {
////			ApprovalLineVO approvalLineVO = approvalVO.getApprovalLineVOList().get(i);
//			ApprovalLineVO param = new ApprovalLineVO();
//			param.setApprovalNo(approvalVO.getApprovalNo());
//			if (i == 0) {
//				param.setSeq(i);
//				param.setUserNo(approvalVO.getUserNo());// 요청자
//				param.setState(ApprovalLineVO.STATE.REQUEST);
//				approvalMapper.addApprovalLine(param);
//			} else {
//				param.setState(ApprovalLineVO.STATE.READY);
//			}
//			param.setSeq(i+1);
//			param.setUserNo(approvalVO.getApprovalLineVOList().get(i).getUserNo());// 결재자
//			approvalMapper.addApprovalLine(param);
//		}
//	}
	

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public void addApproval(ApprovalVO approvalVO) throws Exception {
		UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		approvalVO.setUserNo(userVO.getUserNo());

		// addApproval
		approvalMapper.addApproval(approvalVO);

		// addApprovalLine
		for (int i = 0; i < approvalVO.getApprovalLineVOList().size(); i++) {
			ApprovalLineVO param = new ApprovalLineVO();
			param.setApprovalNo(approvalVO.getApprovalNo());
			param.setState(ApprovalLineVO.STATE.REQUEST);
			if (i > 0) {
				param.setState(ApprovalLineVO.STATE.READY);
			}
			param.setSeq(i+1);
			param.setUserNo(approvalVO.getApprovalLineVOList().get(i).getUserNo());// 결재자
			approvalMapper.addApprovalLine(param);
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public void updateApproval(ApprovalVO approvalVO) throws Exception {
		approvalMapper.updateApproval(approvalVO);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public void updateApprovalLine(ApprovalLineVO approvalLineVO) throws Exception {
		UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		approvalLineVO.setUserNo(userVO.getUserNo());

		approvalMapper.updateApprovalLine(approvalLineVO);
		
		ApprovalVO approvalVO = new ApprovalVO();
		approvalVO.setApprovalNo(approvalLineVO.getApprovalNo());
		Integer nextSeq = approvalMapper.getNextSeq(approvalVO);
		
		if (nextSeq != null) {
			// 완료 시
			this.closeApproval(approvalVO);
		} else {
			// 다음 결재 존재시
			approvalLineVO.setUserNo(null);
			approvalLineVO.setSeq(nextSeq);
			approvalMapper.updateApprovalLine(approvalLineVO);
		}
	}
	
	public void closeApproval(ApprovalVO approvalVO) throws Exception {
		approvalMapper.closeApproval(approvalVO);
	}

	public void isValidApprovalState(ApprovalVO approvalVO) throws Exception {
		UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if ((UserVO.USER_ROLE.USER).equals(userVO.getRole())
//				&& !((ApprovalLineVO.STATE.REQUEST).equals(approvalVO.getState())
//						|| (ApprovalLineVO.STATE.REJECT).equals(approvalVO.getState()))) {
//			throw new ApprovalException(ApprovalException.Code.ACCESS_DENIED_EXCEPTION);
//		}
	}

}
