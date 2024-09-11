package com.neusoft.elmboot.service;

import java.util.List;

import com.neusoft.elmboot.po.Business;

public interface BusinessService {

	public List<Business> listBusinessByOrderTypeId(Integer orderTypeId);
	public Business getBusinessById(Integer businessId);
	public List<Business> listBusinessByKeyWord(String keyWord);
	public Integer saveBusiness(String userId, String password, String businessName);
	public int updateBusiness(Business business);
}
