package com.approval.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.approval.demo.domain.UserVO;
import com.approval.demo.mapper.UserMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO userVO = new UserVO();
		userVO.setUserId(username);

		UserVO resultUserVO = null;
		try {
			resultUserVO = userMapper.getUser(userVO);
		} catch (Exception e) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}

		if (resultUserVO == null || resultUserVO.getUserId() == null || resultUserVO.getPassword() == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return resultUserVO;
	}
}
