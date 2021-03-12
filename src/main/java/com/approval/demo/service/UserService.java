package com.approval.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.approval.demo.domain.UserVO;
import com.approval.demo.mapper.UserMapper;

@Transactional
@Service
public class UserService {

	@Autowired
	UserMapper userMapper;

	public UserVO getUser(UserVO userVO) throws Exception {
		UserVO result = userMapper.getUser(userVO);
		return result;
	}

	public List<UserVO> getAdminList() throws Exception {
		return userMapper.getAdminList();
	}

}
