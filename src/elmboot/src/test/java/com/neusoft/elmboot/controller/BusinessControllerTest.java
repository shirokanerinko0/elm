package com.neusoft.elmboot.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.neusoft.elmboot.po.Business;
import com.neusoft.elmboot.service.BusinessService;

class BusinessControllerTest {

    @Mock
    private BusinessService businessService;

    @InjectMocks
    private BusinessController businessController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(businessController).build();
    }

    @Test
    void testSaveBusiness() throws Exception {
        // 模拟业务逻辑层的返回值
        when(businessService.saveBusiness("user1", "pass1", "Business1")).thenReturn(1);

        // 调用控制器方法
        Integer result = businessController.saveBusiness("user1", "pass1", "Business1");

        // 验证结果
        assertEquals(1, result);

        // 验证业务层调用
        verify(businessService, times(1)).saveBusiness("user1", "pass1", "Business1");
    }

    @Test
    void testUpdateBusiness() throws Exception {
        Business business = new Business();
        business.setBusinessId(1);
        business.setBusinessName("Business1");

        // 模拟业务逻辑层的返回值
        when(businessService.updateBusiness(business)).thenReturn(1);

        // 调用控制器方法
        int result = businessController.updateBusiness(business);

        // 验证结果
        assertEquals(1, result);

        // 验证业务层调用
        verify(businessService, times(1)).updateBusiness(business);
    }

    @Test
    void testUpdateBusinessImg() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("businessImg", "newImg.png");
        params.put("businessId", "1");

        // 模拟业务逻辑层的返回值
        when(businessService.updateBusinessImg("newImg.png", "1")).thenReturn(1);

        // 调用控制器方法
        int result = businessController.updateBusinessImg(params);

        // 验证结果
        assertEquals(1, result);

        // 验证业务层调用
        verify(businessService, times(1)).updateBusinessImg("newImg.png", "1");
    }
}
