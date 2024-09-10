package com.neusoft.elmboot.service;

import java.util.List;

import com.neusoft.elmboot.po.Food;

public interface FoodService {

	public List<Food> listFoodByBusinessId(Integer businessId);
	
	public int saveFood(String foodName, String foodExplain, String foodIng,Double foodPrice, Integer businessId, Integer quantity);
	
	public int removeFood(Integer foodId);
	
	public int updateFood(Food food);
}
