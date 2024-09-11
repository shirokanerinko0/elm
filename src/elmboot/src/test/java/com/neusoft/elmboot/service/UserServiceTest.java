package com.neusoft.elmboot.service;


import com.neusoft.elmboot.mapper.UserMapper;
import com.neusoft.elmboot.po.User;
import com.neusoft.elmboot.po.UserDTO;
import com.neusoft.elmboot.service.UserService;
import com.neusoft.elmboot.service.impl.UserServiceImpl;
import com.neusoft.elmboot.util.PasswordEncoderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserMapper userMapper; // 模拟 UserMapper

    @InjectMocks
    private UserServiceImpl userService; // 注入模拟的 UserMapper 到 UserServiceImpl

    @BeforeEach
    public void setUp() {
        // 初始化 Mockito 注解
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserByIdByPass_Success() {
        // 模拟数据库中的用户数据
        User mockUser = new User();
        mockUser.setUserId("testUser");
        mockUser.setPassword(PasswordEncoderUtil.encode("testPass"));
        mockUser.setUserName("TestName");
        mockUser.setUserSex(1);
        mockUser.setUserImg("testImg");
        mockUser.setDelTag(0);
        mockUser.setUserType(1);

        // 模拟 userMapper 返回的用户数据
        when(userMapper.getUserByIdByPass("testUser")).thenReturn(mockUser);

        // 调用 service 方法
        UserDTO userDTO = userService.getUserByIdByPass("testUser", "testPass");

        // 断言返回的 userDTO 不为 null，且各字段正确
        assertNotNull(userDTO);
        assertEquals("testUser", userDTO.getUserId());
        assertEquals("TestName", userDTO.getUserName());
        assertEquals(1, userDTO.getUserSex());
        assertEquals("testImg", userDTO.getUserImg());
    }

    @Test
    public void testGetUserByIdByPass_InvalidPassword() {
        // 模拟数据库中的用户数据
        User mockUser = new User();
        mockUser.setUserId("testUser");
        mockUser.setPassword(PasswordEncoderUtil.encode("testPass"));

        // 模拟 userMapper 返回的用户数据
        when(userMapper.getUserByIdByPass("testUser")).thenReturn(mockUser);

        // 调用 service 方法，使用错误的密码
        UserDTO userDTO = userService.getUserByIdByPass("testUser", "wrongPass");

        // 断言返回结果应为 null
        assertNull(userDTO);
    }

    @Test
    public void testSaveUser() {
        // 模拟 userMapper 保存用户时返回 1 表示成功
        when(userMapper.saveUser(anyString(), anyString(), anyString(), anyInt())).thenReturn(1);

        // 调用 service 方法
        int result = userService.saveUser("testUser", "testPass", "TestName", 1);

        // 验证保存成功，并返回结果为 1
        assertEquals(1, result);

        // 验证 userMapper 的 saveUser 方法被调用一次
        verify(userMapper, times(1)).saveUser(anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    public void testUpdateUserImg() {
        // 模拟 userMapper 更新用户头像时返回 1 表示成功
        when(userMapper.updateUserImg("image123", "testUser")).thenReturn(1);

        // 调用 service 方法
        int result = userService.updateUserImg("image123", "testUser");

        // 断言返回结果为 1
        assertEquals(1, result);

        // 验证 userMapper 的 updateUserImg 方法被调用一次
        verify(userMapper, times(1)).updateUserImg("image123", "testUser");
    }
}
