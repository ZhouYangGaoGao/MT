package com.matao.bean; 

import java.io.Serializable;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com  
 * @version: 创建时间：2015-7-13 上午9:51:01 
 * @Description: 
 */
public class MsgBean implements Serializable{
	private String Msg;
	private int Code;
	private MsgData Data;
	public String getMsg() {
		return Msg;
	}
	public void setMsg(String msg) {
		Msg = msg;
	}
	public int getCode() {
		return Code;
	}
	public void setCode(int code) {
		Code = code;
	}
	public MsgData getData() {
		return Data;
	}
	public void setData(MsgData data) {
		Data = data;
	}
	@Override
	public String toString() {
		return "MsgBean [Msg=" + Msg + ", Code=" + Code + "]";
	}
	
}
 