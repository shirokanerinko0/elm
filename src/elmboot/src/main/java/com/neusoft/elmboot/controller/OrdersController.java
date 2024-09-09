package com.neusoft.elmboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.neusoft.elmboot.po.Orders;
import com.neusoft.elmboot.service.OrdersService;

@RestController
public class OrdersController {

	@Autowired
	private OrdersService ordersService;
	
	@PostMapping("/Orders")
	public int createOrders(Orders orders) throws Exception{
		return ordersService.createOrders(orders);
	}
	
	@GetMapping("/Orders")
	public Orders getOrdersById(@RequestParam Integer orderId) throws Exception{
		return ordersService.getOrdersById(orderId);
	}
	
	@RequestMapping("/OrdersList")
	public List<Orders> listOrdersByUserId(Orders orders) throws Exception{
		return ordersService.listOrdersByUserId(orders.getUserId());
	}
	
	@PutMapping("/Orders")
	public List<String> finishOrders(@RequestParam Integer orderId) throws Exception {
		return ordersService.finishOrders(orderId);
	}
}
