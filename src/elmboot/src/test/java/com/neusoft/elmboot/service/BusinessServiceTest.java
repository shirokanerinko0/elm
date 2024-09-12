package com.neusoft.elmboot.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.neusoft.elmboot.mapper.BusinessMapper;
import com.neusoft.elmboot.mapper.UserMapper;
import com.neusoft.elmboot.po.User;
import com.neusoft.elmboot.service.impl.BusinessServiceImpl;
import com.neusoft.elmboot.po.Business;
import com.neusoft.elmboot.util.PasswordEncoderUtil;

class BusinessServiceTest {

    @Mock
    private BusinessMapper businessMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private BusinessServiceImpl businessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    

    @Test
    void testSaveBusinessFailure() {
        // 模拟未找到用户
        when(userMapper.getUserByIdByPass("user123")).thenReturn(null);

        // 执行保存业务
        Integer result = businessService.saveBusiness("user123", "plainPassword", "New Business");

        // 验证没有保存业务，也没有更新用户类型
        verify(businessMapper, never()).saveBusiness(anyString());
        verify(userMapper, never()).updateUserTypeByUserId(anyString(), anyInt());

        // 断言返回 null
        assertNull(result);
    }

    @Test
    void testUpdateBusiness() {
        // 模拟业务更新返回成功
        Business business = new Business();
        business.setBusinessId(1);
        when(businessMapper.updateBusiness(any(Business.class))).thenReturn(1);

        // 执行业务更新
        int result = businessService.updateBusiness(business);

        // 验证方法是否被正确调用并检查返回的更新结果
        verify(businessMapper, times(1)).updateBusiness(business);
        assertEquals(1, result);
    }

    @Test
    void testUpdateBusinessImg() {
        // 模拟业务图片更新返回成功
        when(businessMapper.updateBusinessImg("newImage.jpg", "1")).thenReturn(1);

        // 执行业务图片更新
        int result = businessService.updateBusinessImg("newImage.jpg", "1");

        // 验证方法是否被正确调用并检查返回的更新结果
        verify(businessMapper, times(1)).updateBusinessImg("newImage.jpg", "1");
        assertEquals(1, result);
    }
}
