package com.matao.bean;

import java.io.Serializable;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-27 下午2:38:55
 * @Description:TODO
 */

public class Bean implements Serializable {
	private int Code;
	private String Msg;
	private Data Data;
	private int VerificationCode;

	public int getVerificationCode() {
		return VerificationCode;
	}

	public void setVerificationCode(int verificationCode) {
		VerificationCode = verificationCode;
	}

	public int getCode() {
		return Code;
	}

	public void setCode(int Code) {
		this.Code = Code;
	}

	public String getMsg() {
		return Msg;
	}

	public void setMsg(String Msg) {
		this.Msg = Msg;
	}

	public Data getData() {
		return Data;
	}

	public void setData(Data Data) {
		this.Data = Data;
	}

	@Override
	public String toString() {
		return "Bean [Code=" + Code + ", Msg=" + Msg + ", Data=" + Data
				+ ", VerificationCode=" + VerificationCode + "]";
	}

}
