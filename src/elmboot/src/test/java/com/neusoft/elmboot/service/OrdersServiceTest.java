package com.neusoft.elmboot.service;

import com.neusoft.elmboot.mapper.CartMapper;
import com.neusoft.elmboot.mapper.FoodMapper;
import com.neusoft.elmboot.mapper.OrderDetailetMapper;
import com.neusoft.elmboot.mapper.OrdersMapper;
import com.neusoft.elmboot.po.Cart;
import com.neusoft.elmboot.po.Food;
import com.neusoft.elmboot.po.OrderDetailet;
import com.neusoft.elmboot.po.Orders;
import com.neusoft.elmboot.service.impl.OrdersServiceImpl;
import com.neusoft.elmboot.util.CommonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrdersServiceTest {

    @Mock
    private CartMapper cartMapper;
    @Mock
    private OrdersMapper ordersMapper;
    @Mock
    private OrderDetailetMapper orderDetailetMapper;
    @Mock
    private FoodMapper foodMapper;

    @InjectMocks
    private OrdersServiceImpl ordersService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrders_Success() {
        // 模拟购物车数据
        Cart mockCart = new Cart();
        mockCart.setFoodId(1);
        mockCart.setQuantity(2);
        List<Cart> cartList = new ArrayList<>();
        cartList.add(mockCart);

        when(cartMapper.listCart(any(Cart.class))).thenReturn(cartList);

        // 模拟保存订单
        Orders mockOrder = new Orders();
        mockOrder.setOrderId(1001);
        when(ordersMapper.saveOrders(any(Orders.class))).thenAnswer(invocation -> {
            Orders order = invocation.getArgument(0);
            order.setOrderId(1001);
            return 1;
        });

        // 模拟订单明细保存
        when(orderDetailetMapper.saveOrderDetailetBatch(anyList())).thenReturn(1);

        // 模拟删除购物车信息
        when(cartMapper.removeCart(any(Cart.class))).thenReturn(1);

        // 测试创建订单
        int result = ordersService.createOrders(mockOrder);
        assertEquals(1001, result);  // 断言返回的订单号正确

        // 验证各方法调用次数
        verify(cartMapper, times(1)).listCart(any(Cart.class));
        verify(ordersMapper, times(1)).saveOrders(any(Orders.class));
        verify(orderDetailetMapper, times(1)).saveOrderDetailetBatch(anyList());
        verify(cartMapper, times(1)).removeCart(any(Cart.class));
    }

    @Test
    public void testListOrdersByUserId() {
        // 模拟订单列表
        List<Orders> mockOrdersList = new ArrayList<>();
        Orders mockOrder = new Orders();
        mockOrder.setOrderId(1001);
        mockOrdersList.add(mockOrder);

        when(ordersMapper.listOrdersByUserId("testUser")).thenReturn(mockOrdersList);

        // 调用服务方法
        List<Orders> result = ordersService.listOrdersByUserId("testUser");

        // 断言结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1001, result.get(0).getOrderId());

        // 验证方法调用
        verify(ordersMapper, times(1)).listOrdersByUserId("testUser");
    }

    @Test
    public void testFinishOrders_Success() {
        // 模拟订单明细
        OrderDetailet mockOrderDetailet = new OrderDetailet();
        mockOrderDetailet.setFoodId(1);
        mockOrderDetailet.setQuantity(2);
        List<OrderDetailet> mockOrderDetailets = new ArrayList<>();
        mockOrderDetailets.add(mockOrderDetailet);

        when(orderDetailetMapper.listOrderDetailetByOrderId(1001)).thenReturn(mockOrderDetailets);

        // 模拟食品库存足够
        Food mockFood = new Food();
        mockFood.setQuantity(5);
        mockFood.setFoodName("TestFood");
        when(foodMapper.getFoodById(1)).thenReturn(mockFood);

        // 调用完成订单方法
        List<String> result = ordersService.finishOrders(1001);

        // 断言没有库存不足的情况
        assertNull(result);

        // 验证调用次数
        verify(orderDetailetMapper, times(1)).listOrderDetailetByOrderId(1001);
        verify(foodMapper, times(1)).getFoodById(1);
        verify(ordersMapper, times(1)).finishOrders(1001);
    }

}
