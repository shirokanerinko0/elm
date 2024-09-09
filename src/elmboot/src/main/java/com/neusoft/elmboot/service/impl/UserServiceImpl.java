package com.neusoft.elmboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.elmboot.mapper.UserMapper;
import com.neusoft.elmboot.po.User;
import com.neusoft.elmboot.po.UserDTO;
import com.neusoft.elmboot.service.UserService;
import com.neusoft.elmboot.util.PasswordEncoderUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	// 只返回必要用户信息
	@Override
	public UserDTO getUserByIdByPass(String userId, String password) {
		String encodedPassword = PasswordEncoderUtil.encode(password);
		User foundUser = userMapper.getUserByIdByPass(userId);
		if (foundUser != null && PasswordEncoderUtil.matches(password, foundUser.getPassword()) == true) {
			return new UserDTO(foundUser.getUserId(), foundUser.getUserName(), foundUser.getUserSex(),
					foundUser.getUserImg(), foundUser.getDelTag(),foundUser.getUserType());
		}
		return null;
	}

	@Override
	public int getUserById(String userId) {
		return userMapper.getUserById(userId);
	}

	@Override
	public int saveUser(String userId, String password, String userName, Integer userSex, Integer userType) {
		String encodedPassword = PasswordEncoderUtil.encode(password);
		return userMapper.saveUser(userId, encodedPassword, userName, userSex, userType);
	}
}
