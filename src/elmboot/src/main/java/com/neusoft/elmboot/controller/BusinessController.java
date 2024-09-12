package com.neusoft.elmboot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.elmboot.po.Business;
import com.neusoft.elmboot.service.BusinessService;

@RestController
public class BusinessController {
	
	@Autowired
	private BusinessService businessService;
	
	@RequestMapping("/Businesses")
	public List<Business> listBusinessByOrderTypeId(Business business) throws Exception{
		return businessService.listBusinessByOrderTypeId(business.getOrderTypeId());
	}
	
	@RequestMapping("/Business")
	public Business getBusinessById(@RequestParam Integer businessId) throws Exception{
		return businessService.getBusinessById(businessId);
	}
	
	@RequestMapping("/BusinessesByKeyWord")
	public List<Business> listBusinessByKeyWord(@RequestParam String keyWord) throws Exception{
		return businessService.listBusinessByKeyWord(keyWord);
	}
	
	//
	@PostMapping("/BusinessRegistration")
	public Integer saveBusiness(@RequestParam String userId, @RequestParam String password, @RequestParam String businessName) {
		return businessService.saveBusiness(userId, password, businessName);
	}
	//
	@PutMapping("/Business")
	public int updateBusiness(Business business) throws Exception{
		return businessService.updateBusiness(business);
	}
	//
	@PostMapping("/BusinessImg")
	public int updateBusinessImg(@RequestBody Map<String, String> params) throws Exception {
		String businessImg = params.get("businessImg");
		String businessId = params.get("businessId");
		return businessService.updateBusinessImg(businessImg, businessId);
	}
}
