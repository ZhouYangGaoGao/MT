package com.matao.utils;

/**
 * 
 * @author rendy 网络参数数据封装
 */
public class UrlParams {
	private static final String IP = "119.254.11.178";
	public static final String BASE_URL = "http://" + IP + "/Port/App.aspx?";

	public static final String METHOD = "Method";
	public static final String CODE = "Code";
	public static final String RESULT = "result";

	public class UserClientParams extends UrlParams {
		public static final String Mobile = "Mobile";
		public static final String Password = "Password";
		public static final String User = "user";
	}
}
