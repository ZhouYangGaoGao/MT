package com.matao.utils;

import android.util.Log;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-3 上午10:26:10
 * @Description:log工具类
 */
public class Logger {

	static int i = 2;

	public static void i(String tag, Object msg) {
		if (i > 0) {
			Log.i("--MaTao--", "【" + tag + "】" + (msg == null ? "" : msg));
		}
	}

	public static void e(String tag, Object msg) {
		if (i > 1) {
			Log.e("--MaTao--", "【" + tag + "】" + (msg == null ? "" : msg));
		}
	}
}