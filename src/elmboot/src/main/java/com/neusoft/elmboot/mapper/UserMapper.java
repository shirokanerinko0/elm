package com.neusoft.elmboot.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.neusoft.elmboot.po.User;

@Mapper
public interface UserMapper {

	@Select("select * from user where userId=#{userId}")
	public User getUserByIdByPass(String userId);
	
	@Select("select count(*) from user where userId=#{userId}")
	public int getUserById(String userId); 
	
	@Insert("insert into user values(#{userId},#{password},#{userName},#{userSex},null,1,#{userType)")
	public int saveUser(String userId, String password, String userName, Integer userSex, Integer userType);
}
