package com.neusoft.elmboot.po;

public class UserDTO {

    private String userId;
    private String userName;
    private Integer userSex;
    private String userImg;
	private Integer delTag;

    public UserDTO(String userId, String userName, Integer userSex, String userImg,Integer delTag) {
        this.userId = userId;
        this.userName = userName;
        this.userSex = userSex;
        this.userImg = userImg;
        this.delTag = delTag;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }
}
