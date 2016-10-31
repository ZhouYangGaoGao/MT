package com.matao.bean;

import java.io.Serializable;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-8-5 上午10:43:56
 * @Description:
 */
public class TaskList implements Serializable {
	private int Point;
	private String Type;
	private String ImgUrl;
	private String Name;

	public int getPoint() {
		return Point;
	}

	public void setPoint(int point) {
		Point = point;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getImgUrl() {
		return ImgUrl;
	}

	public void setImgUrl(String imgUrl) {
		ImgUrl = imgUrl;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	@Override
	public String toString() {
		return "Hqtblist [Point=" + Point + ", Type=" + Type + ", ImgUrl="
				+ ImgUrl + ", Name=" + Name + "]";
	}

}
