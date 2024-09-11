package com.neusoft.elmboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.neusoft.elmboot.po.Food;

@Mapper
public interface FoodMapper {

	@Select("select * from food where businessId=#{businessId} order by foodId")
	public List<Food> listFoodByBusinessId(Integer businessId);
	
	@Select("select * from food where foodId=#{foodId}")
	public Food getFoodById(Integer foodId);
	
	@Update("update food set quantity = quantity - #{odQuantity} where foodId = #{foodId}")
	public void orderFood(Integer foodId, Integer odQuantity);
	
	@Insert("Insert into food (foodName, foodExplain, foodImg, foodPrice, businessId, quantity) values (#{foodName}, #{foodExplain}, #{foodImg}, #{foodPrice}, #{businessId}, #{quantity})")
	public int saveFood(String foodName, String foodExplain, String foodImg,Double foodPrice, Integer businessId, Integer quantity);
	
	@Update("update food set businessId = 0 where foodId = #{foodId}")
	public int removeFood(Integer foodId);
	
	@Update("update food set foodName = #{foodName}, foodExplain = #{foodExplain}, foodImg = #{foodImg}, foodPrice = #{foodPrice}, businessId = #{businessId}, quantity = #{quantity} where foodId = #{foodId}")
	public int updateFood(Food food);
}
