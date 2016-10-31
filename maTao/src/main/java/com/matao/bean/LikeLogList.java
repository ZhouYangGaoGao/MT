package com.matao.bean;

import java.io.Serializable;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-7-14 下午2:31:43
 * @Description:
 */
public class LikeLogList implements Serializable {
	private String Avatar;
	private String ImgUrl;
	private String Content;
	private String Url;
	private int UserId;

	public String getAvatar() {
		return Avatar;
	}

	public void setAvatar(String avatar) {
		Avatar = avatar;
	}

	public String getImgUrl() {
		return ImgUrl;
	}

	public void setImgUrl(String imgUrl) {
		ImgUrl = imgUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	@Override
	public String toString() {
		return "LikeLogList [Avatar=" + Avatar + ", ImgUrl=" + ImgUrl
				+ ", Content=" + Content + ", Url=" + Url + ", UserId="
				+ UserId + "]";
	}

}
