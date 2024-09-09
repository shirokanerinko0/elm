package com.neusoft.elmboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public Business getBusinessById(Business business) throws Exception{
		return businessService.getBusinessById(business.getBusinessId());
	}
	
	@RequestMapping("/BusinessesByKeyWord")
	public List<Business> listBusinessByKeyWord(@RequestParam String keyWord) throws Exception{
		return businessService.listBusinessByKeyWord(keyWord);
	}
}
