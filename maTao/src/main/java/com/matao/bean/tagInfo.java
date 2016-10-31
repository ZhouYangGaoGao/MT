package com.matao.bean;

import java.io.Serializable;

public class tagInfo implements Serializable{
	private String TagName;
	private String TagImgUrl;
	private String Content;
	
	public String getTagName() {
		return TagName;
	}

	public void setTagName(String tagName) {
		TagName = tagName;
	}

	public String getTagImgUrl() {
		return TagImgUrl;
	}

	public void setTagImgUrl(String tagImgUrl) {
		TagImgUrl = tagImgUrl;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	@Override
	public String toString() {
		return "tagInfo [TagName=" + TagName + ", TagImgUrl=" + TagImgUrl
				+ ", Content=" + Content + "]";
	}

}
