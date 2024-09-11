package com.neusoft.elmboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.elmboot.mapper.FoodMapper;
import com.neusoft.elmboot.po.Food;
import com.neusoft.elmboot.service.FoodService;

@Service
public class FoodServiceImpl implements FoodService{
	
	@Autowired
	private FoodMapper foodMapper;

	@Override
	public List<Food> listFoodByBusinessId(Integer businessId) {
		return foodMapper.listFoodByBusinessId(businessId);
	}
	
	@Override
	public int saveFood(String foodName, String foodExplain, String foodImg,Double foodPrice, Integer businessId, Integer quantity) {
		return foodMapper.saveFood(foodName, foodExplain, foodImg, foodPrice, businessId, quantity);
	}
	
	@Override
	public int removeFood(Integer foodId) {
		foodMapper.deleteCart(foodId);
		return foodMapper.removeFood(foodId);
	}
	
	@Override
	public int updateFood(Food food) {
		 if (food.getFoodName() == null || food.getFoodName().isEmpty()) {
		        throw new IllegalArgumentException("食品名称不能为空");
		 }
		Integer foodId = food.getFoodId();
		foodMapper.deleteCart(foodId);
		foodMapper.saveFood(food.getFoodName(), food.getFoodExplain(), food.getFoodImg(), food.getFoodPrice(), food.getBusinessId(), food.getQuantity());
		return foodMapper.removeFood(foodId);
	}
	
	@Override
	public Food getFoodById(Integer foodId) {
		return foodMapper.getFoodById(foodId);
	}
}
