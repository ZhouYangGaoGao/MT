/** *TODO * * */package com.matao.bean;import java.io.Serializable;import java.util.Arrays;import java.util.List;/** * @author: ZhouYang * @time:2015-5-25 下午7:14:08 * @Description:TODO */public class ReplyList implements Serializable {	private int Id;	private int ArticleId;	private int ReplyId;	private int Yes;	private int Floor;	private int UserId;	private int ImgNum;	private int BabyGender;	private String Content;	private String ReplyDate;	private String NickName;	private String Avatar;	private String BabyInfo;	private String BabyNickName;	private String[] ImgList;	private List<String> BigImgList;	private boolean IsDelete;	private boolean IsYes;	private ReplyList ReferencedReply;	public List<String> getBigImgList() {		return BigImgList;	}	public void setBigImgList(List<String> bigImgList) {		BigImgList = bigImgList;	}	public boolean isIsYes() {		return IsYes;	}	public void setIsYes(boolean isYes) {		IsYes = isYes;	}	@Override	public String toString() {		return "ReplyList [Id=" + Id + ", ArticleId=" + ArticleId				+ ", ReplyId=" + ReplyId + ", Yes=" + Yes + ", Floor=" + Floor				+ ", UserId=" + UserId + ", ImgNum=" + ImgNum + ", BabyGender="				+ BabyGender + ", Content=" + Content + ", ReplyDate="				+ ReplyDate + ", NickName=" + NickName + ", Avatar=" + Avatar				+ ", BabyInfo=" + BabyInfo + ", BabyNickName=" + BabyNickName				+ ", ImgList=" + Arrays.toString(ImgList) + ", BigImgList="				+ BigImgList + ", IsDelete=" + IsDelete + ", IsYes=" + IsYes				+ ", ReferencedReply=" + ReferencedReply + "]";	}	public int getId() {		return Id;	}	public void setId(int id) {		Id = id;	}	public int getArticleId() {		return ArticleId;	}	public void setArticleId(int articleId) {		ArticleId = articleId;	}	public int getReplyId() {		return ReplyId;	}	public void setReplyId(int replyId) {		ReplyId = replyId;	}	public int getYes() {		return Yes;	}	public void setYes(int yes) {		Yes = yes;	}	public int getFloor() {		return Floor;	}	public void setFloor(int floor) {		Floor = floor;	}	public int getUserId() {		return UserId;	}	public void setUserId(int userId) {		UserId = userId;	}	public int getImgNum() {		return ImgNum;	}	public void setImgNum(int imgNum) {		ImgNum = imgNum;	}	public int getBabyGender() {		return BabyGender;	}	public void setBabyGender(int babyGender) {		BabyGender = babyGender;	}	public String getContent() {		return Content;	}	public void setContent(String content) {		Content = content;	}	public String getReplyDate() {		return ReplyDate;	}	public void setReplyDate(String replyDate) {		ReplyDate = replyDate;	}	public String getNickName() {		return NickName;	}	public void setNickName(String nickName) {		NickName = nickName;	}	public String getAvatar() {		return Avatar;	}	public void setAvatar(String avatar) {		Avatar = avatar;	}	public String getBabyInfo() {		return BabyInfo;	}	public void setBabyInfo(String babyInfo) {		BabyInfo = babyInfo;	}	public String getBabyNickName() {		return BabyNickName;	}	public void setBabyNickName(String babyNickName) {		BabyNickName = babyNickName;	}	public String[] getImgList() {		return ImgList;	}	public void setImgList(String[] imgList) {		ImgList = imgList;	}	public boolean isIsDelete() {		return IsDelete;	}	public void setIsDelete(boolean isDelete) {		IsDelete = isDelete;	}	public ReplyList getReferencedReply() {		return ReferencedReply;	}	public void setReferencedReply(ReplyList referencedReply) {		ReferencedReply = referencedReply;	}}