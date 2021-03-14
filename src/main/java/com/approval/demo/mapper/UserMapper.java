package com.approval.demo.mapper;

import java.sql.SQLException;
import java.util.List;

import com.approval.demo.domain.UserVO;

public interface UserMapper {

	public UserVO getUser(UserVO userVO) throws SQLException;

	public List<UserVO> getAdminList() throws SQLException;
	
}
