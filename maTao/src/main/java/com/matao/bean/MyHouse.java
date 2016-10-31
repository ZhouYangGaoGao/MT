package com.matao.bean;

import java.io.Serializable;

public class MyHouse implements Serializable{
	private int Code;
	private String Msg;
	private BabystateData Data;
	public int getCode() {
		return Code;
	}
	public void setCode(int code) {
		Code = code;
	}
	public String getMsg() {
		return Msg;
	}
	public void setMsg(String msg) {
		Msg = msg;
	}

	public BabystateData getData() {
		return Data;
	}
	public void setData(BabystateData data) {
		Data = data;
	}
	@Override
	public String toString() {
		return "MyHouse [Code=" + Code + ", Msg=" + Msg + ", Data=" + Data
				+ "]";
	}

}
