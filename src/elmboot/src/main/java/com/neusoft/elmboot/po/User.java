package com.neusoft.elmboot.po;

public class User {

	private String userId;
	private String password;
	private String userName;
	private Integer userSex;
	private String userImg;
	private Integer userType;
	
	private Integer delTag;
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public Integer getDelTag() {
		return delTag;
	}
	public void setDelTag(Integer delTag) {
		this.delTag = delTag;
	}
}
