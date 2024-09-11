package com.neusoft.elmboot.controller;

import com.neusoft.elmboot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class) // 只加载 UserController
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; // 用于模拟HTTP请求

    @MockBean
    private UserService userService; // 模拟 UserService

    @BeforeEach
    public void init() {
        System.out.println("初始化方法");
    }

    @Test
    public void testSaveUser() throws Exception {
        // 模拟当调用 userService 的 saveUser 方法时，返回 1
        Mockito.when(userService.saveUser("testUser", "testPass", "TestName", 1))
               .thenReturn(1);

        // 模拟一个 HTTP POST 请求，发送数据，并验证返回值
        mockMvc.perform(post("/User")
                .param("userId", "testUser")
                .param("password", "testPass")
                .param("userName", "TestName")
                .param("userSex", "1")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())  // 验证返回的状态码是200 OK
                .andExpect(content().string("1"));  // 验证返回的内容是1
    }

    @Test
    public void testUpdateUserImg() throws Exception {
        // 模拟当调用 userService 的 updateUserImg 方法时，返回 1
        Mockito.when(userService.updateUserImg("image123", "testUser")).thenReturn(1);

        // 模拟一个 HTTP POST 请求，发送 JSON 数据，并验证返回值
        mockMvc.perform(post("/UserImg")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userImg\":\"image123\", \"userId\":\"testUser\"}"))
                .andExpect(status().isOk())  // 验证返回的状态码是200 OK
                .andExpect(content().string("1"));  // 验证返回的内容是1
    }
}
