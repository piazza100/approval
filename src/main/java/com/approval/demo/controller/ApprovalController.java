package com.approval.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

import com.approval.demo.domain.ApprovalVO;
import com.approval.demo.service.ApprovalService;

@RestController
@RequestMapping("/api/approval")
public class ApprovalController {

	@Autowired
	ApprovalService approvalService;

	@GetMapping(value = "/list")
	public ResponseEntity<?> list(ApprovalVO approvalVO) throws Exception {
		Map resultMap = new HashMap();
		List<ApprovalVO> list = this.approvalService.getApprovalListByUserNo(approvalVO);
		resultMap.put("result", list);
		return ResponseEntity.ok(resultMap);
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> add(ApprovalVO approvalVO) throws Exception {
		Map resultMap = new HashMap();
		this.approvalService.addApproval(approvalVO);
		resultMap.put("result", "Y");
		return ResponseEntity.ok(resultMap);
	}

	@PostMapping(value = "/update")
	public ResponseEntity<?> update(@Valid ApprovalVO approvalVO) throws Exception {
		Map resultMap = new HashMap();
		if(StringUtils.isEmpty(approvalVO.getTitle()) || StringUtils.isEmpty(approvalVO.getContent())) {
			throw new ApprovalException(ApprovalException.Code.NULL_PARAM_EXCEPTION);
		}
		this.approvalService.checkByRole(approvalVO);
		this.approvalService.updateApproval(approvalVO);
		resultMap.put("result", "Y");
		return ResponseEntity.ok(resultMap);
	}

	@PostMapping(value = "/admin/update")
	public ResponseEntity<?> adminUpdate(@Valid ApprovalVO approvalVO) throws Exception {
		Map resultMap = new HashMap();
		UserVO user = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.approvalService.updateApproval(approvalVO);
		resultMap.put("result", "Y");
		return ResponseEntity.ok(resultMap);
	}

}
