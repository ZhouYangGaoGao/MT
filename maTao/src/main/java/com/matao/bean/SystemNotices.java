package com.matao.bean; 

import java.io.Serializable;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com  
 * @version: 创建时间：2015-7-13 上午10:56:43 
 * @Description: 
 */
public class SystemNotices implements Serializable{
	private int Id;
	private String NickName;
	private String NoticeContent;
	private String NoticeContentNoticeContent;
	private String NativeParam;
	private String HeadImage;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public String getNoticeContent() {
		return NoticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		NoticeContent = noticeContent;
	}
	public String getNoticeContentNoticeContent() {
		return NoticeContentNoticeContent;
	}
	public void setNoticeContentNoticeContent(String noticeContentNoticeContent) {
		NoticeContentNoticeContent = noticeContentNoticeContent;
	}
	public String getNativeParam() {
		return NativeParam;
	}
	public void setNativeParam(String nativeParam) {
		NativeParam = nativeParam;
	}
	public String getHeadImage() {
		return HeadImage;
	}
	public void setHeadImage(String headImage) {
		HeadImage = headImage;
	}
	@Override
	public String toString() {
		return "SystemNotices [Id=" + Id + ", NickName=" + NickName
				+ ", NoticeContent=" + NoticeContent
				+ ", NoticeContentNoticeContent=" + NoticeContentNoticeContent
				+ ", NativeParam=" + NativeParam + ", HeadImage=" + HeadImage
				+ "]";
	}
}
 