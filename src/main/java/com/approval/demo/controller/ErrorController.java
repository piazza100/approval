package com.approval.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.approval.demo.exception.ApprovalException;

@RestController("/error")
public class ErrorController {
	@RequestMapping(value = "/403")
	public ResponseEntity<Map<String, String>> error403() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", ApprovalException.Code.FORBIDDEN.getKey());
		map.put("message", ApprovalException.Code.FORBIDDEN.getValue());
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.FORBIDDEN);
	}

	@RequestMapping(value = "/404")
	public ResponseEntity<Map<String, String>> error404() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", ApprovalException.Code.NOT_FOUND.getKey());
		map.put("message", ApprovalException.Code.NOT_FOUND.getValue());
		return new ResponseEntity<Map<String, String>>(map, HttpStatus.NOT_FOUND);
	}
}
