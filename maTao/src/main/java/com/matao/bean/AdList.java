package com.matao.bean;

import java.io.Serializable;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-8-7 上午10:52:05
 * @Description:
 */
public class AdList implements Serializable {
	private String Title;
	private String NativeName;
	private String NativeParam;
	private String ImgUrl;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getNativeName() {
		return NativeName;
	}

	public void setNativeName(String nativeName) {
		NativeName = nativeName;
	}

	public String getNativeParam() {
		return NativeParam;
	}

	public void setNativeParam(String nativeParam) {
		NativeParam = nativeParam;
	}

	public String getImgUrl() {
		return ImgUrl;
	}

	public void setImgUrl(String imgUrl) {
		ImgUrl = imgUrl;
	}

	@Override
	public String toString() {
		return "AdList [Title=" + Title + ", NativeName=" + NativeName
				+ ", NativeParam=" + NativeParam + ", ImgUrl=" + ImgUrl + "]";
	}

}
