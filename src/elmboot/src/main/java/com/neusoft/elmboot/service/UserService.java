package com.neusoft.elmboot.service;

import com.neusoft.elmboot.po.User;
import com.neusoft.elmboot.po.UserDTO;

public interface UserService {

	public UserDTO getUserByIdByPass(String userId, String password);
	public int getUserById(String userId);
	public int saveUser(String userId, String password, String userName, Integer userSex);
}
