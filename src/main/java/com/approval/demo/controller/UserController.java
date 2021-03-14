package com.approval.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	/**
	 * 로그인
	 * 
	 * @param userVO
	 * @return
	 * @throws AuthenticationException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
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

	/**
	 * 결재자 목록
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/admin/list")
	public ResponseEntity<?> adminList() throws Exception {
		List<UserVO> list = this.userService.getAdminList();
		return ResponseEntity.ok(list);
	}

}
