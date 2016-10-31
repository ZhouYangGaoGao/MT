package com.matao.bean;

import java.io.Serializable;

public class ZiliaoData implements Serializable{
	private String NickName;
	private String UserHead;
	private String BabyName;
	private String BabyBirthday;
	private String DueDate;
	private String DueDateInfo;
	private boolean IsModified;
	private String Hospital;
	private String Province;
	private String City;
	private String Area;
	private String MobileNumber;
	private int BabyId;
	private int Gender;
	private int BabyState;
	private int BabySex;
	private int ProvinceId;
	private int CityId;
	private int AreaId;
	
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public String getUserHead() {
		return UserHead;
	}
	public void setUserHead(String userHead) {
		UserHead = userHead;
	}
	public String getBabyName() {
		return BabyName;
	}
	public void setBabyName(String babyName) {
		BabyName = babyName;
	}
	public String getBabyBirthday() {
		return BabyBirthday;
	}
	public void setBabyBirthday(String babyBirthday) {
		BabyBirthday = babyBirthday;
	}
	public String getDueDate() {
		return DueDate;
	}
	public void setDueDate(String dueDate) {
		DueDate = dueDate;
	}
	public String getDueDateInfo() {
		return DueDateInfo;
	}
	public void setDueDateInfo(String dueDateInfo) {
		DueDateInfo = dueDateInfo;
	}
	public boolean isIsModified() {
		return IsModified;
	}
	public void setIsModified(boolean isModified) {
		IsModified = isModified;
	}
	public String getHospital() {
		return Hospital;
	}
	public void setHospital(String hospital) {
		Hospital = hospital;
	}
	public String getProvince() {
		return Province;
	}
	public void setProvince(String province) {
		Province = province;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	public String getMobileNumber() {
		return MobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		MobileNumber = mobileNumber;
	}
	public int getGender() {
		return Gender;
	}
	public void setGender(int gender) {
		Gender = gender;
	}
	public int getBabyState() {
		return BabyState;
	}
	public void setBabyState(int babyState) {
		BabyState = babyState;
	}
	public int getBabySex() {
		return BabySex;
	}
	public void setBabySex(int babySex) {
		BabySex = babySex;
	}
	public int getProvinceId() {
		return ProvinceId;
	}
	public void setProvinceId(int provinceId) {
		ProvinceId = provinceId;
	}
	public int getCityId() {
		return CityId;
	}
	public void setCityId(int cityId) {
		CityId = cityId;
	}
	public int getAreaId() {
		return AreaId;
	}
	public void setAreaId(int areaId) {
		AreaId = areaId;
	}
	public int getBabyId() {
		return BabyId;
	}
	public void setBabyId(int babyId) {
		BabyId = babyId;
	}
	@Override
	public String toString() {
		return "ZiliaoData [NickName=" + NickName + ", UserHead=" + UserHead
				+ ", BabyName=" + BabyName + ", BabyBirthday=" + BabyBirthday
				+ ", DueDate=" + DueDate + ", DueDateInfo=" + DueDateInfo
				+ ", IsModified=" + IsModified + ", Hospital=" + Hospital
				+ ", Province=" + Province + ", City=" + City + ", Area="
				+ Area + ", MobileNumber=" + MobileNumber + ", BabyId="
				+ BabyId + ", Gender=" + Gender + ", BabyState=" + BabyState
				+ ", BabySex=" + BabySex + ", ProvinceId=" + ProvinceId
				+ ", CityId=" + CityId + ", AreaId=" + AreaId + "]";
	}
}
