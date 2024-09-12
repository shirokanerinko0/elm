package com.neusoft.elmboot.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.neusoft.elmboot.po.User;

class UserMapperTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserMapperTest userMapperTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserByIdByPass() {
        // 准备模拟返回值
        User mockUser = new User();
        mockUser.setUserId("testUser");
        mockUser.setPassword("testPass");

        // 模拟行为
        when(userMapper.getUserByIdByPass("testUser")).thenReturn(mockUser);

        // 执行测试
        User result = userMapper.getUserByIdByPass("testUser");

        // 验证结果
        assertNotNull(result);
        assertEquals("testUser", result.getUserId());
        assertEquals("testPass", result.getPassword());

        // 验证交互
        verify(userMapper, times(1)).getUserByIdByPass("testUser");
    }

    @Test
    void testGetUserById() {
        // 模拟行为
        when(userMapper.getUserById("testUser")).thenReturn(1);

        // 执行测试
        int result = userMapper.getUserById("testUser");

        // 验证结果
        assertEquals(1, result);

        // 验证交互
        verify(userMapper, times(1)).getUserById("testUser");
    }

    @Test
    void testSaveUser() {
        // 模拟行为
        when(userMapper.saveUser("testUser", "testPass", "testName", 1)).thenReturn(1);

        // 执行测试
        int result = userMapper.saveUser("testUser", "testPass", "testName", 1);

        // 验证结果
        assertEquals(1, result);

        // 验证交互
        verify(userMapper, times(1)).saveUser("testUser", "testPass", "testName", 1);
    }

    @Test
    void testUpdateUserTypeByUserId() {
        // 模拟无返回值的方法
        doNothing().when(userMapper).updateUserTypeByUserId("testUser", 2);

        // 执行测试
        userMapper.updateUserTypeByUserId("testUser", 2);

        // 验证交互
        verify(userMapper, times(1)).updateUserTypeByUserId("testUser", 2);
    }

    @Test
    void testUpdateUserImg() {
        // 模拟行为
        when(userMapper.updateUserImg("image123", "testUser")).thenReturn(1);

        // 执行测试
        int result = userMapper.updateUserImg("image123", "testUser");

        // 验证结果
        assertEquals(1, result);

        // 验证交互
        verify(userMapper, times(1)).updateUserImg("image123", "testUser");
    }
}
