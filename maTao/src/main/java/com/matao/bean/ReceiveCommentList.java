package com.matao.bean; 

import java.io.Serializable;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com  
 * @version: 创建时间：2015-7-13 下午3:19:43 
 * @Description: 
 */
public class ReceiveCommentList implements Serializable{
	private int Id;
	private int CommentUserId;
	private int Type;
	private int ArticleId;
	private int ReplyId;
	private Boolean IsReply;
	private Boolean IsRead;
	private String CommentNickName;
	private String Avatar;
	private String Title;
	private String Content;
	private String AddDate;
	private String Url;
	
	
	public int getReplyId() {
		return ReplyId;
	}
	public void setReplyId(int replyId) {
		ReplyId = replyId;
	}
	public int getArticleId() {
		return ArticleId;
	}
	public void setArticleId(int articleId) {
		ArticleId = articleId;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getCommentUserId() {
		return CommentUserId;
	}
	public void setCommentUserId(int commentUserId) {
		CommentUserId = commentUserId;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	public Boolean getIsReply() {
		return IsReply;
	}
	public void setIsReply(Boolean isReply) {
		IsReply = isReply;
	}
	public Boolean getIsRead() {
		return IsRead;
	}
	public void setIsRead(Boolean isRead) {
		IsRead = isRead;
	}
	public String getCommentNickName() {
		return CommentNickName;
	}
	public void setCommentNickName(String commentNickName) {
		CommentNickName = commentNickName;
	}
	public String getAvatar() {
		return Avatar;
	}
	public void setAvatar(String avatar) {
		Avatar = avatar;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getAddDate() {
		return AddDate;
	}
	public void setAddDate(String addDate) {
		AddDate = addDate;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	@Override
	public String toString() {
		return "ReceiveCommentList [Id=" + Id + ", CommentUserId="
				+ CommentUserId + ", Type=" + Type + ", ArticleId=" + ArticleId
				+ ", ReplyId=" + ReplyId + ", IsReply=" + IsReply + ", IsRead="
				+ IsRead + ", CommentNickName=" + CommentNickName + ", Avatar="
				+ Avatar + ", Title=" + Title + ", Content=" + Content
				+ ", AddDate=" + AddDate + ", Url=" + Url + "]";
	}
	
}
 