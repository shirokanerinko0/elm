package com.neusoft.elmboot.po;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testGetUserType() {
        user.setUserType(2);
        assertEquals(2, user.getUserType());
    }

    @Test
    void testSetUserType() {
        user.setUserType(3);
        assertEquals(3, user.getUserType());
    }

    @Test
    void testGetUserId() {
        user.setUserId("testUserId");
        assertEquals("testUserId", user.getUserId());
    }

    @Test
    void testSetUserId() {
        user.setUserId("newUserId");
        assertEquals("newUserId", user.getUserId());
    }

    @Test
    void testGetPassword() {
        user.setPassword("testPass");
        assertEquals("testPass", user.getPassword());
    }

    @Test
    void testSetPassword() {
        user.setPassword("newPass");
        assertEquals("newPass", user.getPassword());
    }

    @Test
    void testGetUserName() {
        user.setUserName("testName");
        assertEquals("testName", user.getUserName());
    }

    @Test
    void testSetUserName() {
        user.setUserName("newName");
        assertEquals("newName", user.getUserName());
    }

    @Test
    void testGetUserSex() {
        user.setUserSex(1);
        assertEquals(1, user.getUserSex());
    }

    @Test
    void testSetUserSex() {
        user.setUserSex(0);
        assertEquals(0, user.getUserSex());
    }

    @Test
    void testGetUserImg() {
        user.setUserImg("testImg");
        assertEquals("testImg", user.getUserImg());
    }

    @Test
    void testSetUserImg() {
        user.setUserImg("newImg");
        assertEquals("newImg", user.getUserImg());
    }

    @Test
    void testGetDelTag() {
        user.setDelTag(1);
        assertEquals(1, user.getDelTag());
    }

    @Test
    void testSetDelTag() {
        user.setDelTag(0);
        assertEquals(0, user.getDelTag());
    }
}
