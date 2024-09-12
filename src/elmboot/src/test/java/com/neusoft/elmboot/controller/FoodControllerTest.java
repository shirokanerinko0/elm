package com.neusoft.elmboot.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.neusoft.elmboot.po.Food;
import com.neusoft.elmboot.service.FoodService;

import java.util.HashMap;
import java.util.Map;

class FoodControllerTest {

    @Mock
    private FoodService foodService; // 模拟 FoodService

    @InjectMocks
    private FoodController foodController; // 注入模拟的 FoodService 到 FoodController

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // 初始化 Mockito 注解
    }

    @Test
    void testSaveFood() throws Exception {
        // 创建一个示例 Food 对象
        Map<String, Object> map = new HashMap<>();
        map.put("foodName", "Pizza");
        map.put("foodExplain", "Delicious cheese pizza");
        map.put("foodImg", "pizza.png");
        map.put("foodPrice", 9.99);
        map.put("businessId", 1);
        map.put("quantity", 100);

        // 模拟 foodService.saveFood 返回的结果
        when(foodService.saveFood("Pizza", "Delicious cheese pizza", "pizza.png", 9.99, 1, 100)).thenReturn(1);

        // 调用 controller 方法
        int result = foodController.saveFood(map);

        // 断言返回结果为 1
        assertEquals(1, result);
    }

    @Test
    void testUpdateFood() throws Exception {
        // 创建一个示例 Food 对象
        Food food = new Food();
        food.setFoodId(1);
        food.setFoodName("Updated Pizza");

        // 模拟 foodService.updateFood 返回的结果
        when(foodService.updateFood(food)).thenReturn(1);

        // 调用 controller 方法
        int result = foodController.updateFood(food);

        // 断言返回结果为 1
        assertEquals(1, result);
    }

    @Test
    void testRemoveFood() throws Exception {
        // 模拟 foodService.removeFood 返回的结果
        when(foodService.removeFood(1)).thenReturn(1);

        // 调用 controller 方法
        int result = foodController.removeFood(1);

        // 断言返回结果为 1
        assertEquals(1, result);
    }

    @Test
    void testGetFoodById() throws Exception {
        // 创建一个示例 Food 对象
        Food food = new Food();
        food.setFoodId(1);
        food.setFoodName("Pizza");

        // 模拟 foodService.getFoodById 返回的结果
        when(foodService.getFoodById(1)).thenReturn(food);

        // 调用 controller 方法
        Food result = foodController.getFoodById(1);

        // 断言返回的 Food 对象不为空，并且字段正确
        assertNotNull(result);
        assertEquals("Pizza", result.getFoodName());
    }
}
