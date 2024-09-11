package com.neusoft.elmboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.elmboot.mapper.BusinessMapper;
import com.neusoft.elmboot.mapper.UserMapper;
import com.neusoft.elmboot.po.Business;
import com.neusoft.elmboot.po.User;
import com.neusoft.elmboot.service.BusinessService;
import com.neusoft.elmboot.util.PasswordEncoderUtil;

@Service
public class BusinessServiceImpl implements BusinessService{
	
	@Autowired
	private BusinessMapper businessMapper;
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<Business> listBusinessByOrderTypeId(Integer orderTypeId) {
		return businessMapper.listBusinessByOrderTypeId(orderTypeId);
	}
	
	@Override
	public Business getBusinessById(Integer businessId) {
		return businessMapper.getBusinessById(businessId);
	}
	
	@Override
	public List<Business> listBusinessByKeyWord(String keyWord) {
		System.out.println(keyWord);
		return businessMapper.listBusinessByKeyWord(keyWord);
	}
	
	@Override
	public Integer saveBusiness(String userId, String password, String businessName) {
		User foundUser = userMapper.getUserByIdByPass(userId);
		if (foundUser != null && PasswordEncoderUtil.matches(password, foundUser.getPassword()) == true) {
			businessMapper.saveBusiness(businessName);
			Integer businessId = 10000 + businessMapper.getBusinessesNumber();
			userMapper.updateUserTypeByUserId(userId, businessId);
			return businessId;
		}
		return null;
	}
	
	@Override
	public int updateBusiness(Business business) {
		return businessMapper.updateBusiness(business);
	}
}
