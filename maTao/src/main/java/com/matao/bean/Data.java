package com.matao.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-27 下午3:16:08
 * @Description:TODO
 */

public class Data implements Serializable {
	private static final long serialVersionUID = 1L;
	private int UserId;
	private int Point;
	private String Token;
	private String Recommend;
	private tagInfo TagInfo;
	private DaTePager DaTePager;
	private LoginUserInfo LoginUserInfo;
	private BabyInfo BabyInfo;
	private String VerificationCode;
	private boolean OperateResult;
	private VersionInfo VersionInfo;
	private List<ArticleList> YouhuiJsons;
	private List<HeadAdList> HeadAdList;
	private List<HeadAdList> MiddleAdList;
	private List<ArticleList> ArticleList;
	private List<ArticleList> TagLists;
	private List<ArticleList> MataoJingYanLists;
	private List<HeadAdList> RecommendsList;
	private List<ReplyList> ReplyLists;
	private List<HeadAdList> SystemNotices;
	private List<ReceiveCommentList> ReceiveCommentList;
	private List<LikeLogList> LikeLogList;
	private String Channel;
	private String Title;
	private String Summary;
	private String Url;
	private boolean IsRecommend;
	private boolean IsLike;
	private int Recommends;
	private int Likes;
	private int Replys;
	private List<String> ImgList;
	// 获取淘币任务列表
	private List<TaskList> TaskList;
	// 获取收货地址
	private String Name;
	private String Email;
	private String Mobile;
	private String Province;
	private String City;
	private String Area;
	private int AreaId;
	private String QQ;
	private String Street;
	private int ProvinceId;
	private int CityId;
	// 获取兑换产品列表
	private List<HeadAdList> AdList;
	private List<HeadAdList> ActivityList;
	public List<HeadAdList> getActivityList() {
		return ActivityList;
	}

	public void setActivityList(List<HeadAdList> activityList) {
		ActivityList = activityList;
	}

	private List<GoodsList> GoodsList;

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public int getAreaId() {
		return AreaId;
	}

	public void setAreaId(int areaId) {
		AreaId = areaId;
	}

	public List<HeadAdList> getAdList() {
		return AdList;
	}

	public void setAdList(List<HeadAdList> adList) {
		AdList = adList;
	}

	public List<GoodsList> getGoodsList() {
		return GoodsList;
	}

	public void setGoodsList(List<GoodsList> goodsList) {
		GoodsList = goodsList;
	}

	public List<TaskList> getTaskList() {
		return TaskList;
	}

	public void setTaskList(List<TaskList> taskList) {
		TaskList = taskList;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
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

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getStreet() {
		return Street;
	}

	public void setStreet(String street) {
		Street = street;
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

	public String getChannel() {
		return Channel;
	}

	public void setChannel(String channel) {
		Channel = channel;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getSummary() {
		return Summary;
	}

	public void setSummary(String summary) {
		Summary = summary;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public boolean isIsRecommend() {
		return IsRecommend;
	}

	public void setIsRecommend(boolean isRecommend) {
		IsRecommend = isRecommend;
	}

	public boolean isIsLike() {
		return IsLike;
	}

	public void setIsLike(boolean isLike) {
		IsLike = isLike;
	}

	public int getRecommends() {
		return Recommends;
	}

	public void setRecommends(int recommends) {
		Recommends = recommends;
	}

	public int getLikes() {
		return Likes;
	}

	public void setLikes(int likes) {
		Likes = likes;
	}

	public int getReplys() {
		return Replys;
	}

	public void setReplys(int replys) {
		Replys = replys;
	}

	public List<String> getImgList() {
		return ImgList;
	}

	public void setImgList(List<String> imgList) {
		ImgList = imgList;
	}

	public tagInfo getTagInfo() {
		return TagInfo;
	}

	public void setTagInfo(tagInfo tagInfo) {
		TagInfo = tagInfo;
	}

	public List<LikeLogList> getLikeLogList() {
		return LikeLogList;
	}

	public void setLikeLogList(List<LikeLogList> likeLogList) {
		LikeLogList = likeLogList;
	}

	public List<ReceiveCommentList> getReceiveCommentList() {
		return ReceiveCommentList;
	}

	public void setReceiveCommentList(
			List<ReceiveCommentList> receiveCommentList) {
		ReceiveCommentList = receiveCommentList;
	}

	public VersionInfo getVersionInfo() {
		return VersionInfo;
	}

	public void setVersionInfo(VersionInfo versionInfo) {
		VersionInfo = versionInfo;
	}

	public String getRecommend() {
		return Recommend;
	}

	public void setRecommend(String recommend) {
		Recommend = recommend;
	}

	public List<ReplyList> getReplyLists() {
		return ReplyLists;
	}

	public void setReplyLists(List<ReplyList> replyLists) {
		ReplyLists = replyLists;
	}

	public boolean isOperateResult() {
		return OperateResult;
	}

	public void setOperateResult(boolean operateResult) {
		OperateResult = operateResult;
	}

	public List<ArticleList> getTagLists() {
		return TagLists;
	}

	public void setTagLists(List<ArticleList> tagLists) {
		TagLists = tagLists;
	}

	public List<ArticleList> getMataoJingYanLists() {
		return MataoJingYanLists;
	}

	public void setMataoJingYanLists(List<ArticleList> mataoJingYanLists) {
		MataoJingYanLists = mataoJingYanLists;
	}

	public List<HeadAdList> getRecommendsList() {
		return RecommendsList;
	}

	public void setRecommendsList(List<HeadAdList> recommendsList) {
		RecommendsList = recommendsList;
	}

	public String getVerificationCode() {
		return VerificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		VerificationCode = verificationCode;
	}

	public LoginUserInfo getLoginUserInfo() {
		return LoginUserInfo;
	}

	public void setLoginUserInfo(LoginUserInfo loginUserInfo) {
		LoginUserInfo = loginUserInfo;
	}

	public BabyInfo getBabyInfo() {
		return BabyInfo;
	}

	public void setBabyInfo(BabyInfo babyInfo) {
		BabyInfo = babyInfo;
	}

	public List<HeadAdList> getHeadAdList() {
		return HeadAdList;
	}

	public void setHeadAdList(List<HeadAdList> headAdList) {
		HeadAdList = headAdList;
	}

	public List<HeadAdList> getMiddleAdList() {
		return MiddleAdList;
	}

	public void setMiddleAdList(List<HeadAdList> middleAdList) {
		MiddleAdList = middleAdList;
	}

	public List<ArticleList> getArticleList() {
		return ArticleList;
	}

	public void setArticleList(List<ArticleList> articleList) {
		ArticleList = articleList;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int getPoint() {
		return Point;
	}

	public void setPoint(int point) {
		Point = point;
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}

	public List<ArticleList> getYouhuiJsons() {
		return YouhuiJsons;
	}

	public void setYouhuiJsons(List<ArticleList> youhuiJsons) {
		YouhuiJsons = youhuiJsons;
	}

	public DaTePager getDaTePager() {
		return DaTePager;
	}

	public void setDaTePager(DaTePager daTePager) {
		DaTePager = daTePager;
	}

	public List<HeadAdList> getSystemNotices() {
		return SystemNotices;
	}

	public void setSystemNotices(List<HeadAdList> systemNotices) {
		SystemNotices = systemNotices;
	}

	@Override
	public String toString() {
		return "Data [UserId=" + UserId + ", Point=" + Point + ", Token="
				+ Token + ", Recommend=" + Recommend + ", TagInfo=" + TagInfo
				+ ", DaTePager=" + DaTePager + ", LoginUserInfo="
				+ LoginUserInfo + ", BabyInfo=" + BabyInfo
				+ ", VerificationCode=" + VerificationCode + ", OperateResult="
				+ OperateResult + ", VersionInfo=" + VersionInfo
				+ ", YouhuiJsons=" + YouhuiJsons + ", HeadAdList=" + HeadAdList
				+ ", MiddleAdList=" + MiddleAdList + ", ArticleList="
				+ ArticleList + ", TagLists=" + TagLists
				+ ", MataoJingYanLists=" + MataoJingYanLists
				+ ", RecommendsList=" + RecommendsList + ", ReplyLists="
				+ ReplyLists + ", SystemNotices=" + SystemNotices
				+ ", ReceiveCommentList=" + ReceiveCommentList
				+ ", LikeLogList=" + LikeLogList + ", Channel=" + Channel
				+ ", Title=" + Title + ", Summary=" + Summary + ", Url=" + Url
				+ ", IsRecommend=" + IsRecommend + ", IsLike=" + IsLike
				+ ", Recommends=" + Recommends + ", Likes=" + Likes
				+ ", Replys=" + Replys + ", ImgList=" + ImgList + ", TaskList="
				+ TaskList + ", Name=" + Name + ", Email=" + Email
				+ ", Mobile=" + Mobile + ", Province=" + Province + ", City="
				+ City + ", Area=" + Area + ", AreaId=" + AreaId + ", QQ=" + QQ
				+ ", Street=" + Street + ", ProvinceId=" + ProvinceId
				+ ", CityId=" + CityId + ", AdList=" + AdList + ", GoodsList="
				+ GoodsList + "]";
	}

}
