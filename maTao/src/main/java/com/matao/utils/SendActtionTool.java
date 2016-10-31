package com.matao.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-27 上午10:26:10
 * @Description:网络请求
 */
public class SendActtionTool {
	/**
	 * 
	 * 2015年4月1日
	 * 
	 * @param url
	 *            带参数字段的 url xx.com?name=a&psd=chuagn123
	 * @param service
	 *            业务分类
	 * @param action
	 *            业务指令信号
	 * @param listener
	 *            回调业务
	 */
	public static void post(final String url, final ServiceAction service,
			final Object action, final CommonListener listener) {
		RequestParams params = new RequestParams();
		post(url, service, action, listener, params);

	}

	/**
	 * 
	 * @param url
	 *            网络地址
	 * @param service
	 *            业务
	 * @param action
	 *            业务指令信号
	 * @param listener
	 *            回调
	 * @param params
	 *            参数字段
	 */
	public static void post(final String url, final ServiceAction service,
			final Object action, final CommonListener listener,
			RequestParams params) {
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onStart() {
						listener.onStart(service, action);
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						super.onLoading(total, current, isUploading);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						listener.onException(service, action, "请查看手机网络设置");
						listener.onFinish(service, action);
						MTUtils.Toast(MTApplication.getApplication(),
								"您的网络不给力哦");
					}

					@Override
					public void onSuccess(ResponseInfo<String> response) {
						analyDatas(listener, response, service, action);
						listener.onFinish(service, action);
					}
				});
	}

	/**
	 * 
	 * 2015年4月1日
	 * 
	 * @param url
	 *            带参数字段的 url xx.com?name=a&psd=chuagn123
	 * @param service
	 *            业务分类
	 * @param action
	 *            业务指令信号
	 * @param listener
	 *            回调业务
	 */
	public static void get(final String url, final ServiceAction service,
			final Object action, final CommonListener listener) {
		RequestParams params = new RequestParams();
		get(url, service, action, listener, params);
	}

	/**
	 * 
	 * @param url
	 *            请请地址
	 * @param service
	 * @param action
	 * @param listener
	 * @param params
	 *            请求参数
	 */
	public static void get(final String url, final ServiceAction service,
			final Object action, final CommonListener listener,
			final RequestParams params) {
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onStart() {
						listener.onStart(service, action);
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						super.onLoading(total, current, isUploading);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						listener.onException(service, action, "请查看手机网络设置");
						listener.onFinish(service, action);
						Logger.e("请求失败", msg);
					}

					@Override
					public void onSuccess(ResponseInfo<String> response) {
						analyDatas(listener, response, service, action);
						listener.onFinish(service, action);
					}
				});
	}

	private static void analyDatas(CommonListener listener,
			ResponseInfo<String> response, ServiceAction service, Object action) {
		try {
			JSONObject object = new JSONObject(response.result);
			String value = object.optString("Code", "");
			JSONObject data = object.optJSONObject("Data");
			// 请求成功
			if (data != null && value.equals("0")) {
				listener.onSuccess(service, action, object);
			} else {
				// 请求失败
				listener.onFaile(service, action, object);
			}
		} catch (JSONException e) {
			// 服务器出现异常
			listener.onException(service, action, "服务器出现异常");
			e.printStackTrace();
		}
	}

}
