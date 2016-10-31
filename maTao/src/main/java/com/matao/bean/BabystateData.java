package com.matao.bean;

import java.io.Serializable;

public class BabystateData implements Serializable {
	private String NickName;
	private String Ataver;
	private String BabyInfo;
	private String BabyNickName;
	private String Province;
	private String City;
	private String MobileNumber;
	private int BabyGender;
	private int Fans;
	private int Follows;
	private int Likes;
	private int FollowState;
	private int Point;
	private int UnReadMegCount;
	private int BabyAgeScope;
	private int SdanCounts;
	private int JyanCounts;

	public int getSdanCounts() {
		return SdanCounts;
	}

	public void setSdanCounts(int sdanCounts) {
		SdanCounts = sdanCounts;
	}

	public int getJyanCounts() {
		return JyanCounts;
	}

	public void setJyanCounts(int jyanCounts) {
		JyanCounts = jyanCounts;
	}

	public int getBabyAgeScope() {
		return BabyAgeScope;
	}

	public void setBabyAgeScope(int babyAgeScope) {
		BabyAgeScope = babyAgeScope;
	}

	public String getMobileNumber() {
		return MobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		MobileNumber = mobileNumber;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public String getAtaver() {
		return Ataver;
	}

	public void setAtaver(String ataver) {
		Ataver = ataver;
	}

	public String getBabyInfo() {
		return BabyInfo;
	}

	public void setBabyInfo(String babyInfo) {
		BabyInfo = babyInfo;
	}

	public String getBabyNickName() {
		return BabyNickName;
	}

	public void setBabyNickName(String babyNickName) {
		BabyNickName = babyNickName;
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

	public int getBabyGender() {
		return BabyGender;
	}

	public void setBabyGender(int babyGender) {
		BabyGender = babyGender;
	}

	public int getFans() {
		return Fans;
	}

	public void setFans(int fans) {
		Fans = fans;
	}

	public int getFollows() {
		return Follows;
	}

	public void setFollows(int follows) {
		Follows = follows;
	}

	public int getLikes() {
		return Likes;
	}

	public void setLikes(int likes) {
		Likes = likes;
	}

	public int getFollowState() {
		return FollowState;
	}

	public void setFollowState(int followState) {
		FollowState = followState;
	}

	public int getPoint() {
		return Point;
	}

	public void setPoint(int point) {
		Point = point;
	}

	public int getUnReadMegCount() {
		return UnReadMegCount;
	}

	public void setUnReadMegCount(int unReadMegCount) {
		UnReadMegCount = unReadMegCount;
	}

	@Override
	public String toString() {
		return "hData [NickName=" + NickName + ", Ataver=" + Ataver
				+ ", BabyInfo=" + BabyInfo + ", BabyNickName=" + BabyNickName
				+ ", Province=" + Province + ", City=" + City
				+ ", MobileNumber=" + MobileNumber + ", BabyGender="
				+ BabyGender + ", Fans=" + Fans + ", Follows=" + Follows
				+ ", Likes=" + Likes + ", FollowState=" + FollowState
				+ ", Point=" + Point + ", UnReadMegCount=" + UnReadMegCount
				+ ", BabyAgeScope=" + BabyAgeScope + ", SdanCounts="
				+ SdanCounts + ", JyanCounts=" + JyanCounts + "]";
	}
}
