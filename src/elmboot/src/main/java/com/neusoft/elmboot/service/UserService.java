package com.neusoft.elmboot.service;

import com.neusoft.elmboot.po.User;
import com.neusoft.elmboot.po.UserDTO;

public interface UserService {

	public UserDTO getUserByIdByPass(User user);
	public int getUserById(String userId);
	public int saveUser(User user);
}
