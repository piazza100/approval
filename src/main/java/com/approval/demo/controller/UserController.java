package com.approval.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.approval.demo.config.JwtTokenUtil;
import com.approval.demo.domain.AuthTokenVO;
import com.approval.demo.domain.UserVO;
import com.approval.demo.exception.ApprovalException;
import com.approval.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@RequestMapping(value = "/a", method = RequestMethod.GET)
	public ResponseEntity<?> a(UserVO userVO) throws Exception {
		Map resultMap = new HashMap();
		resultMap.put("result", "/a");
		return ResponseEntity.ok(resultMap);
	}
	
	@RequestMapping(value = "/user/a", method = RequestMethod.GET)
	public ResponseEntity<?> prvA(UserVO userVO) throws Exception {
		Map resultMap = new HashMap();
		resultMap.put("result", "/user/a");
		return ResponseEntity.ok(resultMap);
	}
	
	@RequestMapping(value = "/user/request", method = RequestMethod.GET)
	public ResponseEntity<?> request(UserVO userVO) throws Exception {
		Map resultMap = new HashMap();
		resultMap.put("result", "/user/request");
		return ResponseEntity.ok(resultMap);
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ResponseEntity<?> prvAdminA(UserVO userVO) throws Exception {
		Map resultMap = new HashMap();
		resultMap.put("result", "/admin");
		return ResponseEntity.ok(resultMap);
	}
	
	@RequestMapping(value = "/admin/confirm", method = RequestMethod.GET)
	public ResponseEntity<?> confirm(UserVO userVO) throws Exception {
		Map resultMap = new HashMap();
		resultMap.put("result", "/admin/confirm");
		return ResponseEntity.ok(resultMap);
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody UserVO userVO) throws AuthenticationException {
		final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userVO.getUserId(), userVO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserVO param = new UserVO();
		param.setUserId(userVO.getUserId());
		UserVO user = null;
		try {
			user = this.userService.getUser(param);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApprovalException(ApprovalException.Code.NULL_USER_EXCEPTION);
		}

		String token = "";
		try {
			token = jwtTokenUtil.generateToken(user);
		} catch (Exception e) {
			throw new ApprovalException(ApprovalException.Code.TOKEN_CREATE_EXCEPTION);
		}
		return ResponseEntity.ok(new AuthTokenVO(token));
	}
}
