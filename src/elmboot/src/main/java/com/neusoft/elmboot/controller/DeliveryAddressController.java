package com.neusoft.elmboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.neusoft.elmboot.po.DeliveryAddress;
import com.neusoft.elmboot.service.DeliveryAddressService;

@RestController
public class DeliveryAddressController {

	@Autowired
	private DeliveryAddressService deliveryAddressService;
	
	@RequestMapping("/DeliveryAddresses")
	public List<DeliveryAddress> listDeliveryAddressByUserId(DeliveryAddress deliveryAddress) throws Exception{
		return deliveryAddressService.listDeliveryAddressByUserId(deliveryAddress.getUserId());
	} 
	
	@GetMapping("/DeliveryAddress")
	public DeliveryAddress getDeliveryAddressById(@RequestParam Integer daId) throws Exception{
		return deliveryAddressService.getDeliveryAddressById(daId);
	} 
	
	@PostMapping("/DeliveryAddress")
	public int saveDeliveryAddress(DeliveryAddress deliveryAddress) throws Exception{
		return deliveryAddressService.saveDeliveryAddress(deliveryAddress);
	} 
	
	@PutMapping("/DeliveryAddress")
	public int updateDeliveryAddress(DeliveryAddress deliveryAddress) throws Exception{
		return deliveryAddressService.updateDeliveryAddress(deliveryAddress);
	} 
	
	@DeleteMapping("/DeliveryAddress")
	public int removeDeliveryAddress(@RequestParam Integer daId) throws Exception{
		return deliveryAddressService.removeDeliveryAddress(daId);
	} 
}
