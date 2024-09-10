package com.neusoft.elmboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.elmboot.po.Food;
import com.neusoft.elmboot.service.FoodService;

@RestController
public class FoodController {
	
	@Autowired
	private FoodService foodService;

	@RequestMapping("/Food")
	public List<Food> listFoodByBusinessId(Food food) throws Exception{
		return foodService.listFoodByBusinessId(food.getBusinessId());
	}
	
	@PostMapping("/OneFood")
	public int saveFood(@RequestParam String foodName, @RequestParam String foodExplain, @RequestParam String foodImg, @RequestParam Double foodPrice, @RequestParam Integer businessId, @RequestParam Integer quantity) throws Exception {
		return foodService.saveFood(foodName, foodExplain, foodImg, foodPrice, businessId, quantity);
	}
	
	@PutMapping("/Food")
	public int updateFood(Food food) throws Exception {
		return foodService.updateFood(food);
	}
	
	@DeleteMapping("/Food")
	public int removeFood(@RequestParam Integer foodId) throws Exception {
		return foodService.removeFood(foodId);
	}
}
