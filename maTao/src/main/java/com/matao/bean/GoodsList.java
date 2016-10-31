package com.matao.bean;

import java.io.Serializable;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-8-7 上午10:52:12
 * @Description:
 */
public class GoodsList implements Serializable {
	private String Title;
	private String ImgUrl;
	private int Id;
	private int Coins;
	private int Stock;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getImgUrl() {
		return ImgUrl;
	}

	public void setImgUrl(String imgUrl) {
		ImgUrl = imgUrl;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getCoins() {
		return Coins;
	}

	public void setCoins(int coins) {
		Coins = coins;
	}

	public int getStock() {
		return Stock;
	}

	public void setStock(int stock) {
		Stock = stock;
	}

	@Override
	public String toString() {
		return "GoodsList [Title=" + Title + ", ImgUrl=" + ImgUrl + ", Id="
				+ Id + ", Coins=" + Coins + ", Stock=" + Stock + "]";
	}

}
