package com.neusoft.elmboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;


import com.neusoft.elmboot.po.User;
import com.neusoft.elmboot.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping("/UserLogin")
	public User getUserByIdByPass(User user) throws Exception{
		return userService.getUserByIdByPass(user);
	}
	
	@GetMapping("/User")
	public int getUserById(@RequestParam String userId) throws Exception{
		return userService.getUserById(userId);
	}
	
	@PostMapping("/User")
	public int saveUser(User user) throws Exception{
		return userService.saveUser(user);
	}
}
