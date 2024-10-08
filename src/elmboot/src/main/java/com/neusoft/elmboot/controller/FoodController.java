package com.neusoft.elmboot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.elmboot.po.Food;
import com.neusoft.elmboot.service.FoodService;

@RestController
public class FoodController {
	
	@Autowired
	private FoodService foodService;

	@PostMapping("/Food")
	public List<Food> listFoodByBusinessId(Food food) throws Exception{
		return foodService.listFoodByBusinessId(food.getBusinessId());
	}
	
	//
	@PostMapping("/OneFood")
	public int saveFood(@RequestBody Map<String, Object> map) throws Exception {
	    // 从 map 中获取参数
	    String foodName = (String) map.get("foodName");
	    String foodExplain = (String) map.get("foodExplain");
	    String foodImg = (String) map.get("foodImg");
	    // 这里使用 Number 来接收可能是 Integer 或 Double 的值
	    Number foodPriceNumber = (Number) map.get("foodPrice");
	    Double foodPrice = foodPriceNumber.doubleValue();  // 将其转换为 Double
	    Integer businessId = (Integer) map.get("businessId");
	    Integer quantity = (Integer) map.get("quantity");
	    // 将参数传递给 service
	    return foodService.saveFood(foodName, foodExplain, foodImg, foodPrice, businessId, quantity);
	}
	//
	@PutMapping("/Food")
	public int updateFood(@RequestBody Food food) throws Exception {
		if (food.getFoodName() == null || food.getFoodName().isEmpty()) {
	        throw new IllegalArgumentException("食品名称不能为空");
		}
		return foodService.updateFood(food);
	}
	//
	@DeleteMapping("/Food")
	public int removeFood(@RequestParam Integer foodId) throws Exception {
		return foodService.removeFood(foodId);
	}
	//
	@GetMapping("/GetOneFood")
	public Food getFoodById(@RequestParam Integer foodId) throws Exception {
		return foodService.getFoodById(foodId);
	}
}
