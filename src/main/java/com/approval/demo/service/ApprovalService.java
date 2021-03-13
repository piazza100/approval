package com.approval.demo.service;

import java.util.Date;
import java.util.List;

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
//		UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		approvalVO.setUserNo(this.getUserNo());
		return approvalMapper.getApproval(approvalVO);
	}

	public List<ApprovalVO> getApprovalList(ApprovalVO approvalVO) throws Exception {
//		UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		approvalVO.setUserNo(this.getUserNo());
		return approvalMapper.getApprovalList(approvalVO);
	}

	public List<ApprovalVO> getApprovalRequestList(ApprovalVO approvalVO) throws Exception {
//		UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		approvalVO.setUserNo(this.getUserNo());
		return approvalMapper.getApprovalRequestList(approvalVO);
	}

	/**
	 * 결재 등록
	 * 
	 * @param approvalVO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public void addApproval(ApprovalVO approvalVO) throws Exception {
//		UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		approvalVO.setUserNo(this.getUserNo());

		// 결재 등록
		approvalMapper.addApproval(approvalVO);

		// 결재선 등록
		this.addApprovalLine(approvalVO);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public void deleteApproval(ApprovalVO approvalVO) throws Exception {
//		UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		approvalVO.setUserNo(this.getUserNo());

		// 결재 수정 가능한지 확인
		if (this.isValidApproval(approvalVO) == false)
			throw new ApprovalException(ApprovalException.Code.DO_NOT_UPDATE_EXCEPTION);

		// 결재 삭제
		approvalVO.setEndTime(new Date());
		approvalMapper.updateApproval(approvalVO);
	}

	/**
	 * 결재선 등록
	 * 
	 * @param approvalVO
	 * @throws Exception
	 */
	public void addApprovalLine(ApprovalVO approvalVO) throws Exception {
		for (int i = 0; i < approvalVO.getApprovalLineVOList().size(); i++) {
			ApprovalLineVO param = new ApprovalLineVO();
			param.setApprovalNo(approvalVO.getApprovalNo());
			param.setState(ApprovalLineVO.STATE.REQUEST);
			if (i > 0) {
				param.setState(ApprovalLineVO.STATE.READY);
			}
			param.setSeq(i + 1);
			param.setUserNo(approvalVO.getApprovalLineVOList().get(i).getUserNo());
			approvalMapper.addApprovalLine(param);
		}
	}

	/**
	 * 결재선 삭제
	 * 
	 * @param approvalVO
	 * @throws Exception
	 */
	public void deleteApprovalLine(ApprovalVO approvalVO) throws Exception {
		approvalMapper.deleteApprovalLine(approvalVO);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public void updateApproval(ApprovalVO approvalVO) throws Exception {
//		UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		approvalVO.setUserNo(this.getUserNo());

		// 결재 수정 가능한지 확인
		if (this.isValidApproval(approvalVO) == false)
			throw new ApprovalException(ApprovalException.Code.DO_NOT_UPDATE_EXCEPTION);

		// 결재 수정 처리
		approvalMapper.updateApproval(approvalVO);

		// 결재선 수정 처리
		this.deleteApprovalLine(approvalVO);
		this.addApprovalLine(approvalVO);
	}

	/**
	 * 결재 상태 변경
	 * 
	 * @param approvalLineVO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class })
	public void updateApprovalLine(ApprovalLineVO approvalLineVO) throws Exception {
		Integer userNo = this.getUserNo();
		approvalLineVO.setUserNo(userNo);

		ApprovalVO approvalVO = new ApprovalVO();
		approvalVO.setApprovalNo(approvalLineVO.getApprovalNo());
		approvalVO.setUserNo(userNo);

		// 결재선 수정 가능한지 확인
		if (this.isValidApproval(approvalVO) == false)
			throw new ApprovalException(ApprovalException.Code.DO_NOT_UPDATE_EXCEPTION);

		// 결재 상태 업데이트
		if (approvalMapper.updateApprovalLine(approvalLineVO) == 0)
			throw new ApprovalException(ApprovalException.Code.NULL_DATA_EXCEPTION);

		Integer nextSeq = approvalMapper.getNextSeq(approvalVO);

		if (nextSeq == null || ApprovalLineVO.STATE.REJECT.equals(approvalLineVO.getState())) {
			// 완료 또는 반려 시 종료처리
			this.closeApproval(approvalVO);
		} else {
			// 다음 결재 존재시
			approvalLineVO.setSeq(nextSeq);
			approvalLineVO.setState(ApprovalLineVO.STATE.REQUEST);
			approvalMapper.updateApprovalLineReadyToRequest(approvalLineVO);
		}
	}

	/**
	 * 결재 종료
	 * 
	 * @param approvalVO
	 * @throws Exception
	 */
	public void closeApproval(ApprovalVO approvalVO) throws Exception {
		approvalMapper.closeApproval(approvalVO);
	}

	/**
	 * 결재|결재선 수정 가능한지 확인
	 * 
	 * @param approvalVO
	 * @return
	 * @throws Exception
	 */
	public boolean isValidApproval(ApprovalVO approvalVO) throws Exception {
		if (approvalMapper.getValidApprovalCount(approvalVO) > 0) {
			return false;
		} else {
			return true;
		}
	}

	private Integer getUserNo() {
		UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userVO.getUserNo(); 
	}
}
