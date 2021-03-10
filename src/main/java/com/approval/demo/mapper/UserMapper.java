package com.approval.demo.mapper;

import java.sql.SQLException;

import com.approval.demo.domain.UserVO;

public interface UserMapper {

	public UserVO getUser(UserVO userVO) throws SQLException;

	public UserVO getUserByNo(Integer userNo) throws SQLException;

}
