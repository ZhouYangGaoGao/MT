package com.matao.bean;

import java.io.Serializable;

public class WarningBean implements Serializable{
	private int Code;
	private String Msg;
	private WarningData Data;
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
	public WarningData getData() {
		return Data;
	}
	public void setData(WarningData data) {
		Data = data;
	}
	@Override
	public String toString() {
		return "WarningBean [Code=" + Code + ", Msg=" + Msg + "]";
	}
	
}
