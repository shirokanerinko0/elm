package com.neusoft.elmboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.neusoft.elmboot.po.Business;

@Mapper
public interface BusinessMapper {

	@Select("select * from business where orderTypeId=#{orderTypeId} order by businessId")
	public List<Business> listBusinessByOrderTypeId(Integer orderTypeId);

	@Select("select * from business where businessId=#{businessId}")
	public Business getBusinessById(Integer businessId);

	@Select("select * from business where businessName like CONCAT('%', #{keyWord}, '%')")
	public List<Business> listBusinessByKeyWord(String keyWord);

	@Insert("insert into business (businessName) values (#{businessName})")
	public void saveBusiness(String businessName);

	@Select("select count(*) from business")
	public Integer getBusinessesNumber();

	@Update("update business set businessName = #{businessName}, businessAddress = #{businessAddress}, businessExplain = #{businessExplain}, orderTypeId = #{orderTypeId}, starPrice = #{starPrice}, deliveryPrice = #{deliveryPrice} where businessId = #{businessId}")
	public int updateBusiness(Business business);

	@Update("update business set businessImg = #{businessImg} where businessId = #{businessId}")
	public int updateBusinessImg(String businessImg, String businessId);
}
