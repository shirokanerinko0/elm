package com.neusoft.elmboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


import com.neusoft.elmboot.po.User;
import com.neusoft.elmboot.po.UserDTO;
import com.neusoft.elmboot.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	//只返回必要信息
	@RequestMapping("/UserLogin")
	public UserDTO getUserByIdByPass(@RequestParam("userId") String userId, @RequestParam("password") String password) throws Exception{
		return userService.getUserByIdByPass(userId, password);
	}
	
	@GetMapping("/User")
	public int getUserById(@RequestParam String userId) throws Exception{
		return userService.getUserById(userId);
	}
	
	@PostMapping("/User")
	public int saveUser(@RequestParam("userId") String userId, @RequestParam("password") String password, @RequestParam("userName") String userName, @RequestParam("userSex") Integer userSex,  @RequestParam("userType") Integer userType) throws Exception{
		return userService.saveUser(userId, password, userName, userSex, userType);
	}
	
}
