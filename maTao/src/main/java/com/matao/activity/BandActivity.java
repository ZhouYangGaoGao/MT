package com.matao.activity;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-8-3 下午3:01:12
 * @Description:手机绑定
 */
//手机绑定
import org.json.JSONException;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.http.RequestParams;
import com.matao.bean.Bean;
import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.MD5Util;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.utils.SendActtionTool;
import com.matao.utils.ServiceAction;
import com.matao.utils.URLs;
import com.matao.utils.UserAction;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

public class BandActivity extends BaseActivity {

	private EditText phone_number;// 手机号码
	private TopBar topBar;
	private TextView send_number;// 发送验证码
	private MyCountDownTimer mc;
	private EditText editCode;// 输入验证码

	private TextView verify;// 验证
	private String code;// 接受自服务端的验证码
	private String phone;

	@Override
	public void onClick(View v) {
		phone = phone_number.getText().toString().trim();
		switch (v.getId()) {
		// 发送按钮
		case R.id.send_number:
			if (TextUtils.isEmpty(phone)) {
				MTUtils.Toast(this, "手机号码不能为空");
			} else if (MTUtils.isPhone(phone)) {
				getCode(phone);
				mc = new MyCountDownTimer(60001, 1000);
				mc.start();
				send_number.setTextColor(0xff999999);
				send_number.setClickable(false);
			} else {
				MTUtils.Toast(this, "请输入正确的手机号码");
			}

			break;
		// 验证按钮
		case R.id.verify:
			// int str = MTApplication.getInt("VerificationCode");
			String codex = editCode.getText().toString().trim();
			// System.out.println("我是验证码：----------" + str);
			if (TextUtils.isEmpty(phone)) {
				MTUtils.Toast(this, "手机号码不能为空");
			} else if (!MTUtils.isPhone(phone)) {
				MTUtils.Toast(this, "手机号码格式不正确");
			}
			// 如果验证码为空的话就提示不跳转如果不为空的话判断后跳转
			else if (!TextUtils.isEmpty(codex)) {
				// 判断验证码是否相等。如果相等的话就返回我的资料页面
				if (code.equals(codex)) {
					band();
					mc.cancel();
					mc.onFinish();
				} else {
					MTUtils.Toast(this, "您输入的验证码有误");
				}
			} else {
				MTUtils.Toast(this, "验证码不能为空");
			}
			break;
		}

	}

	// 获取验证码
	private void getCode(String phone) {
		int userId = MTApplication.getInt("UserId");
		String token = MTApplication.getString("Token");
		String TimeStamp = MTUtils.getTimeStamp();
		String MobileNumber = phone;
		org.json.JSONObject json = new org.json.JSONObject();
		try {
			json.put(
					"Sign",
					MD5Util.getLowerCaseMD5(MobileNumber + "" + userId + token
							+ TimeStamp + URLs.KEY));
			json.put(URLs.MOBILE_NUMBER, MobileNumber);
			json.put(URLs.TIME_STAMP, TimeStamp);
			json.put("UserToken", token);
			json.put("UserId", userId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// 发送请求
		RequestParams params = new RequestParams();
		params.addBodyParameter(URLs.JSON_INFO, json.toString());
		SendActtionTool.post(URLs.GETCODE, ServiceAction.Action_User,
				UserAction.Action_GetCode, this, params);
		Logger.i("json----------->", json.toString());
	}

	// 点击验证请求服务器
	private void band() {
		int userId = MTApplication.getInt("UserId");
		String token = MTApplication.getString("Token");
		String MobileNumber = phone;
		String TimeStamp = MTUtils.getTimeStamp();
		org.json.JSONObject json = new org.json.JSONObject();

		try {
			json.put(
					"Sign",
					MD5Util.getLowerCaseMD5(MobileNumber + "" + userId + token
							+ TimeStamp + URLs.KEY));
			json.put(URLs.MOBILE_NUMBER, MobileNumber);
			json.put("PassWord", "");
			json.put(URLs.TIME_STAMP, TimeStamp);
			json.put("UserToken", token);
			json.put("UserId", userId);
			json.put("Type", 0);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// 发送请求
		RequestParams params = new RequestParams();
		params.addBodyParameter(URLs.JSON_INFO, json.toString());
		SendActtionTool.post(URLs.BAND, ServiceAction.Action_User,
				UserAction.Action_Band, this, params);
		Logger.i("json", json.toString());
	}

	// 成功
	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);

		switch ((UserAction) action) {
		case Action_GetCode:
			Logger.i("Action_GetCode", value.toString());
			Bean bean = JSON.parseObject(value.toString(), Bean.class);
			code = bean.getData().getVerificationCode();
			String str = bean.getMsg();
			MTUtils.Toast(this, str);
			break;
		case Action_Band:
			Logger.i("Action_Band", value.toString());
			Bean bean2 = JSON.parseObject(value.toString(), Bean.class);
			String str2 = bean2.getMsg();
			boolean operate = bean2.getData().isOperateResult();
			if (MTApplication.getThreeLogin() && operate == true) {
				Intent intent = new Intent(this, SetPsdActivity.class);
				intent.putExtra("phone", phone);
				startActivity(intent);
				finish();
			} else {
				if (operate == true) {
					startActivity(new Intent(this, ZiLiaoActivity.class));
					MTUtils.Toast(this, str2);
				} else {
					MTUtils.Toast(this, str2);
				}
			}
		}
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_band);
		init();
	}

	@Override
	public void findViewById() {
	}

	private void init() {
		topBar = (TopBar) findViewById(R.id.band_topbar);
		phone_number = (EditText) findViewById(R.id.phone_number);
		send_number = (TextView) findViewById(R.id.send_number);
		editCode = (EditText) findViewById(R.id.number);
		verify = (TextView) findViewById(R.id.verify);
		phone_number.setOnClickListener(this);
		send_number.setOnClickListener(this);
		editCode.setOnClickListener(this);
		verify.setOnClickListener(this);
		topBar.setOnTopBarClickListener(new topBarClickListener() {

			@Override
			public void rightClick() {

			}

			@Override
			public void leftClick() {
				finish();
			}
		});
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	// 开始
	@Override
	public void onStart(ServiceAction service, Object action) {
		super.onStart(service, action);
	}

	// 失败
	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		Logger.i("onFaile===》》", value.toString());
		switch ((UserAction) action) {
		case Action_GetCode:
			org.json.JSONObject object1;
			try {
				object1 = new org.json.JSONObject(value.toString());
				String str1 = object1.optString("Msg", "");
				MTUtils.Toast(this, str1);
				mc.cancel();
				mc.onFinish();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			break;
		case Action_Band:
			org.json.JSONObject object;
			try {
				object = new org.json.JSONObject(value.toString());
				String str = object.optString("Msg", "");
				MTUtils.Toast(this, str);
				mc.cancel();
				mc.onFinish();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	// 完成，结束
	@Override
	public void onFinish(ServiceAction service, Object action) {
		super.onFinish(service, action);
	}

	// 异常
	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
		mc.cancel();
		mc.onFinish();
		// MTUtils.Toast(this, "请求出现异常，请稍后再试");
	}

	/**
	 * 
	 * @author: ZhouYang
	 * @time:2015-5-15 下午4:21:18
	 * @Description:发送验证码倒计时
	 */
	class MyCountDownTimer extends CountDownTimer {
		/**
		 * * * @param millisInFuture * 表示以毫秒为单位 倒计时的总数 * * 例如
		 * millisInFuture=1000 表示1秒 * * @param countDownInterval * 表示 间隔 多少微秒
		 * 调用一次 onTick 方法 * * 例如: countDownInterval =1000 ;
		 * 表示每1000毫秒调用一次onTick() *
		 */
		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			send_number.setTextColor(0xff666666);
			send_number.setText("重新发送");
			send_number.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			send_number.setText("重新发送(" + millisUntilFinished / 1000 + ")");
		}
	}
}
