package com.matao.bean; 

import java.io.Serializable;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com  
 * @version: 创建时间：2015-7-8 下午12:11:52 
 * @Description: 
 */
public class BabystateBean implements Serializable{
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
		return "Bean2 [Code=" + Code + ", Msg=" + Msg + ", Data=" + Data + "]";
	}
}
 