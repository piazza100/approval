package com.approval.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.approval.demo.domain.UserVO;
import com.approval.demo.mapper.UserMapper;

/**
 * Created by fan.jin on 2016-10-31.
 */

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
//		return new org.springframework.security.core.userdetails.User(resultUserVO.getUserId(), resultUserVO.getPassword(), this.getAuthority(resultUserVO));
		return resultUserVO;
	}

	/*private List<SimpleGrantedAuthority> getAuthority(UserVO user) {
		if ("USER".equals(user.getRole())) {
			return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		} else {
			return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
	}*/

	private void aa() {
		String password = "123qwe!@#";
		PasswordEncoder p = new MessageDigestPasswordEncoder("SHA-256");
		// System.out.println("encPassword = " + p.encode(password));
		System.out.println("encPassword = " + p.matches(password,
				"{yDuffW3B8hH1QvgOF2MhzViZT28QFJ9w1dnJyyEa+9w=}ea9cfb195f43865ffd6b2fa4dc80879f2def46aa200c19b0a1f950e67d7c12c9"));
		// System.out.println("encPassword = " + p.matches(password,
		// p.encode(password)));
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	public static void main(String[] args) {
		CustomUserDetailsService c = new CustomUserDetailsService();
		c.aa();
	}
}
