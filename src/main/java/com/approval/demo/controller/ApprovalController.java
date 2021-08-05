package com.approval.demo.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.approval.demo.domain.ApprovalLineVO;
import com.approval.demo.domain.ApprovalVO;
import com.approval.demo.exception.ApprovalException;
import com.approval.demo.service.ApprovalService;

@RestController
@RequestMapping("/api/approval")
public class ApprovalController {

	@Autowired
	ApprovalService approvalService;

	/**
	 * 사용자 > 결재 목록
	 * 
	 * @param approvalVO
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/list")
	public ResponseEntity<?> list(ApprovalVO approvalVO) throws Exception {
		List<ApprovalVO> list = this.approvalService.getApprovalList(approvalVO);
		return ResponseEntity.ok(list);
	}

	/**
	 * 관리자 > 결재 요청 목록
	 * 
	 * @param approvalVO
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/admin/list")
	public ResponseEntity<?> adminList(ApprovalVO approvalVO) throws Exception {
		List<ApprovalVO> list = this.approvalService.getApprovalRequestList(approvalVO);
		return ResponseEntity.ok(list);
	}

	/**
	 * 결재 상세 조회
	 * 
	 * @param approvalVO
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/getApproval")
	public ResponseEntity<?> getApproval(ApprovalVO approvalVO) throws Exception {
		ApprovalVO view = this.approvalService.getApproval(approvalVO);
		return ResponseEntity.ok(view);
	}

	/**
	 * 결재 등록
	 * 
	 * @param approvalVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/add")
	public ResponseEntity<?> add(@RequestBody @Valid ApprovalVO approvalVO) throws Exception {
		this.approvalService.addApproval(approvalVO);
		return ResponseEntity.ok(new HashMap<String,String>(){{put("result", "Y");}});
	}

	/**
	 * 결재 수정
	 * 
	 * @param approvalVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/update")
	public ResponseEntity<?> update(@RequestBody @Valid ApprovalVO approvalVO) throws Exception {
		if (approvalVO.getApprovalNo() == null) {
			throw new ApprovalException(ApprovalException.Code.NULL_PARAM_EXCEPTION);
		}
		this.approvalService.updateApproval(approvalVO);
		return ResponseEntity.ok(new HashMap<String,String>(){{put("result", "Y");}});
	}

	/**
	 * 관리자 > 결재 상태(승인/반려) 변경
	 * 
	 * @param approvalLineVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/admin/update")
	public ResponseEntity<?> adminUpdate(@RequestBody ApprovalLineVO approvalLineVO) throws Exception {
		if (StringUtils.isBlank(approvalLineVO.getState()) || approvalLineVO.getApprovalNo() == null) {
			throw new ApprovalException(ApprovalException.Code.NULL_PARAM_EXCEPTION);
		}
		this.approvalService.updateApprovalLine(approvalLineVO);
		return ResponseEntity.ok(new HashMap<String,String>(){{put("result", "Y");}});
	}

	/**
	 * 결재 삭제
	 * 
	 * @param approvalVO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/delete")
	public ResponseEntity<?> delete(@RequestBody ApprovalVO approvalVO) throws Exception {
		if (approvalVO.getApprovalNo() == null) {
			throw new ApprovalException(ApprovalException.Code.NULL_PARAM_EXCEPTION);
		}
		this.approvalService.deleteApproval(approvalVO);
		return ResponseEntity.ok(new HashMap<String,String>(){{put("result", "Y");}});
	}

}
