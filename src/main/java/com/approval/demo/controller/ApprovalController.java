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
	public ResponseEntity<?> update(ApprovalVO approvalVO) throws Exception {
		Map resultMap = new HashMap();
		this.approvalService.checkByRole(approvalVO);
		this.approvalService.updateApproval(approvalVO);
		resultMap.put("result", "Y");
		return ResponseEntity.ok(resultMap);
	}

	@PostMapping(value = "/admin/update")
	public ResponseEntity<?> adminUpdate(ApprovalVO approvalVO) throws Exception {
		Map resultMap = new HashMap();
		this.approvalService.updateApproval(approvalVO);
		resultMap.put("result", "Y");
		return ResponseEntity.ok(resultMap);
	}

}
