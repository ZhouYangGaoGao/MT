package com.matao.bean;

import java.io.Serializable;

public class ZiliaoBean implements Serializable{
	private int Code;
	private String Msg;
	private ZiliaoData Data;
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
	public ZiliaoData getData() {
		return Data;
	}
	public void setData(ZiliaoData data) {
		Data = data;
	}
	@Override
	public String toString() {
		return "ZiliaoBean [Code=" + Code + ", Msg=" + Msg + ", Data=" + Data
				+ "]";
	}
}
