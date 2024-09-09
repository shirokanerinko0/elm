package com.neusoft.elmboot.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neusoft.elmboot.mapper.CartMapper;
import com.neusoft.elmboot.mapper.FoodMapper;
import com.neusoft.elmboot.mapper.OrderDetailetMapper;
import com.neusoft.elmboot.mapper.OrdersMapper;
import com.neusoft.elmboot.po.Cart;
import com.neusoft.elmboot.po.Food;
import com.neusoft.elmboot.po.OrderDetailet;
import com.neusoft.elmboot.po.Orders;
import com.neusoft.elmboot.service.OrdersService;
import com.neusoft.elmboot.util.CommonUtil;

@Service
public class OrdersServiceImpl implements OrdersService{
	
	@Autowired
	private CartMapper cartMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrderDetailetMapper orderDetailetMapper;
	@Autowired
	private FoodMapper foodMapper;
	
	private final Lock lock = new ReentrantLock();

	@Override
	@Transactional
	public int createOrders(Orders orders) {
		//1、查询当前用户购物车中当前商家的所有食品
		Cart cart = new Cart();
		cart.setUserId(orders.getUserId());
		cart.setBusinessId(orders.getBusinessId());
		List<Cart> cartList = cartMapper.listCart(cart);
		
		//2、创建订单（返回生成的订单编号）
		orders.setOrderDate(CommonUtil.getCurrentDate());
		ordersMapper.saveOrders(orders);
		int orderId = orders.getOrderId();
		
		//3、批量添加订单明细
		List<OrderDetailet> list = new ArrayList<>();
		for(Cart c : cartList) {
			OrderDetailet od = new OrderDetailet();
			od.setOrderId(orderId);
			od.setFoodId(c.getFoodId());
			od.setQuantity(c.getQuantity());
			list.add(od);
		}
		orderDetailetMapper.saveOrderDetailetBatch(list);
		
		//4、从购物车表中删除相关食品信息
		cartMapper.removeCart(cart);
		
		return orderId;
	}
	
	@Override
	public Orders getOrdersById(Integer orderId) {
		return ordersMapper.getOrdersById(orderId);
	}
	
	@Override
	public List<Orders> listOrdersByUserId(String userId){
		return ordersMapper.listOrdersByUserId(userId);
	}
	
	@SuppressWarnings("finally")
	@Override
	public List<String> finishOrders(Integer orderId) {
		lock.lock();
		try {
			List<String> show = new ArrayList<>();
			List<OrderDetailet> orderDetailets = orderDetailetMapper.listOrderDetailetByOrderId(orderId);
			for (OrderDetailet od : orderDetailets) {
				Integer foodId = od.getFoodId();
				Food food = foodMapper.getFoodById(foodId);
				Integer foodQuantity = food.getQuantity();
				Integer odQuantity = od.getQuantity();
				if (foodQuantity < odQuantity) {
					show.add(food.getFoodName() + "数量不足");
				}
			}
			
			if (show.isEmpty() == false) {
				lock.unlock();
				return show;
			}
			for (OrderDetailet od : orderDetailets) {
				Integer foodId = od.getFoodId();
				Integer odQuantity = od.getQuantity();
				foodMapper.orderFood(foodId, odQuantity);
			}
			ordersMapper.finishOrders(orderId);
        } finally {
            lock.unlock();
            return null;
        }
	}
}
