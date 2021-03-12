package com.approval.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.approval.demo.domain.ApprovalLineVO;
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
		List<ApprovalVO> list = this.approvalService.getApprovalList(approvalVO);
		resultMap.put("result", list);
		return ResponseEntity.ok(resultMap);
	}

	@GetMapping(value = "/admin/list")
	public ResponseEntity<?> adminList(ApprovalVO approvalVO) throws Exception {
		Map resultMap = new HashMap();
		List<ApprovalVO> list = this.approvalService.getApprovalRequestList(approvalVO);
		resultMap.put("result", list);
		return ResponseEntity.ok(resultMap);
	}

	@GetMapping(value = "/view")
	public ResponseEntity<?> view(ApprovalVO approvalVO) throws Exception {
		Map resultMap = new HashMap();
		ApprovalVO view = this.approvalService.getApproval(approvalVO);
		resultMap.put("result", view);
		return ResponseEntity.ok(resultMap);
	}

	@PostMapping(value = "/add")
	public ResponseEntity<?> add(@RequestBody ApprovalVO approvalVO) throws Exception {
		Map resultMap = new HashMap();
		this.approvalService.addApproval(approvalVO);
		resultMap.put("result", "Y");
		return ResponseEntity.ok(resultMap);
	}

	@PostMapping(value = "/update")
	public ResponseEntity<?> update(@RequestBody @Valid ApprovalVO approvalVO) throws Exception {
		Map resultMap = new HashMap();
		this.approvalService.isValidApprovalState(approvalVO);
		this.approvalService.updateApproval(approvalVO);
		resultMap.put("result", "Y");
		return ResponseEntity.ok(resultMap);
	}

	@PostMapping(value = "/admin/update/state")
	public ResponseEntity<?> adminUpdate(@RequestBody @Valid ApprovalLineVO approvalLineVO) throws Exception {
		Map resultMap = new HashMap();
		this.approvalService.updateApprovalLine(approvalLineVO);
		resultMap.put("result", "Y");
		return ResponseEntity.ok(resultMap);
	}

}
