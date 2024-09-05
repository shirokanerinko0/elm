package com.neusoft.elmboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.elmboot.mapper.UserMapper;
import com.neusoft.elmboot.po.User;
import com.neusoft.elmboot.po.UserDTO;
import com.neusoft.elmboot.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;

	//只返回必要用户信息
	@Override
	public UserDTO getUserByIdByPass(User user) {
		  User foundUser = userMapper.getUserByIdByPass(user);
	        if (foundUser != null) {
	            return new UserDTO(
	                foundUser.getUserId(),
	                foundUser.getUserName(),
	                foundUser.getUserSex(),
	                foundUser.getUserImg(),
	                foundUser.getDelTag()
	            );
	        }
	        return null;
	}
	
	@Override
	public int getUserById(String userId) {
		return userMapper.getUserById(userId);
	}
	
	@Override
	public int saveUser(User user) {
		return userMapper.saveUser(user);
	}
}
