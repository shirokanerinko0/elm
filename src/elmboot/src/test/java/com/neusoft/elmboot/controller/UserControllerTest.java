package com.neusoft.elmboot.controller;


import com.neusoft.elmboot.po.UserDTO;
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
import static org.hamcrest.CoreMatchers.is;
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
    
    // 测试用户登录
    @Test
    public void testGetUserByIdByPass() throws Exception {
        // 模拟返回的用户数据
        UserDTO mockUserDTO = new UserDTO(null, null, null, null, null, null);
        mockUserDTO.setUserId("testUser");
        mockUserDTO.setUserName("TestName");
        mockUserDTO.setUserSex(1);
        mockUserDTO.setUserImg("testImg");

        // 当调用 userService.getUserByIdByPass 时，返回模拟的 UserDTO 对象
        Mockito.when(userService.getUserByIdByPass("testUser", "testPass")).thenReturn(mockUserDTO);

        // 模拟一个 HTTP GET 请求，验证返回的 JSON 数据
        mockMvc.perform(get("/UserLogin")
                .param("userId", "testUser")
                .param("password", "testPass"))
                .andExpect(status().isOk())  // 验证状态码
                .andExpect(jsonPath("$.userId", is("testUser")))  // 验证返回 JSON 中 userId 字段
                .andExpect(jsonPath("$.userName", is("TestName")))  // 验证 userName 字段
                .andExpect(jsonPath("$.userSex", is(1)))  // 验证 userSex 字段
                .andExpect(jsonPath("$.userImg", is("testImg")));  // 验证 userImg 字段
    }

    // 测试根据用户ID获取用户
    @Test
    public void testGetUserById() throws Exception {
        // 模拟 userService 返回的用户ID
        Mockito.when(userService.getUserById("testUser")).thenReturn(1);

        // 模拟一个 HTTP GET 请求，验证返回值
        mockMvc.perform(get("/User")
                .param("userId", "testUser"))
                .andExpect(status().isOk())  // 验证状态码
                .andExpect(content().string("1"));  // 验证返回的内容
    }

    @Test
    public void testSaveUser() throws Exception {
    	//模拟服务层行为
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
