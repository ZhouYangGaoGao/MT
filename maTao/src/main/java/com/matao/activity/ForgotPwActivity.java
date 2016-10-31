package com.matao.activity;

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
import com.matao.bean.Data;
import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.MD5Util;
import com.matao.utils.MTUtils;
import com.matao.utils.SendActtionTool;
import com.matao.utils.ServiceAction;
import com.matao.utils.URLs;
import com.matao.utils.UserAction;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

/**
 * @author: ZhangYaWei
 * @time:2015-4-22 下午4:30:11
 * @Description:忘记密码获取验证码
 */
public class ForgotPwActivity extends BaseActivity {

	private TextView getCode, verifybtn;
	private TopBar topBar;
	private MyCountDownTimer mc;
	private EditText telephone, verifyCode;
	private String code;// 接受自服务端的验证码
	private String token;
	private int forgotuserId;

	@Override
	public void onClick(View v) {
		String phone = telephone.getText().toString().trim();
		switch (v.getId()) {
		case R.id.get_code:
			if (TextUtils.isEmpty(phone)) {
				MTUtils.Toast(this, "手机号码不能为空");
			} else if (MTUtils.isPhone(phone)) {
				getTelephoneCode(phone);
				mc = new MyCountDownTimer(60001, 1000);
				mc.start();
				getCode.setTextColor(0xffffffff);
				getCode.setBackgroundResource(R.drawable.btn_validation_2);
				getCode.setClickable(false);

			} else {
				MTUtils.Toast(this, "手机号格式不正确");
			}
			break;
		case R.id.forgotpw_verify:
			// error 验证码 三十分钟
			String xCode = verifyCode.getText().toString().trim();
			if (TextUtils.isEmpty(phone)) {
				MTUtils.Toast(this, "手机号码不能为空");
			} else if (TextUtils.isEmpty(xCode)) {
				MTUtils.Toast(this, "验证码不能为空");
			} else if (xCode.equals(code)) {
				if (mc != null) {
					mc.cancel();
					mc.onFinish();
				}
				Intent intent = new Intent(ForgotPwActivity.this,
						ResetPwActivity.class);
				Logger.i("验userId码>>>", forgotuserId);
				intent.putExtra("forgotuserId", forgotuserId);// 是否三方
				intent.putExtra("token", token);// 三方媒体 ID
				startActivity(intent);
				this.finish();
			} else {
				MTUtils.Toast(this, "验证码错误");
			}
			break;
		}
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		Logger.i("收到服务器验证码===》》", value.toString());
		switch ((UserAction) action) {
		case Action_Get_Code:
			Bean bean = JSON.parseObject(value.toString(), Bean.class);
			code = bean.getData().getVerificationCode();
			Logger.i("验证码>>>", code + "a");
			Data ben = bean.getData();
			forgotuserId = ben.getUserId();
			token = ben.getToken();
			Logger.i("验userId码>>>", forgotuserId + "a" + token);
			break;
		}
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		Logger.i("接收验证码失败===》》", value.toString());
		switch ((UserAction) action) {
		case Action_Get_Code:
			org.json.JSONObject object;
			try {
				object = new org.json.JSONObject(value.toString());
				String str = object.optString("Msg", "");
				MTUtils.Toast(this, str);
				if (mc != null) {
					mc.cancel();
					mc.onFinish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
		if (mc != null) {
			mc.cancel();
			mc.onFinish();
		}
	}

	@Override
	public void onStart(ServiceAction service, Object action) {
		super.onStart(service, action);
	}

	@Override
	public void onFinish(ServiceAction service, Object action) {
		super.onFinish(service, action);
	}

	// 忘记密码 找回密码
	private void getTelephoneCode(String phone) {
		String MobileNumber = phone;
		String TimeStamp = MTUtils.getTimeStamp();
		org.json.JSONObject json = new org.json.JSONObject();

		try {
			json.put("Sign", MD5Util.getLowerCaseMD5(MobileNumber + TimeStamp
					+ URLs.KEY));
			json.put(URLs.MOBILE_NUMBER, MobileNumber);
			json.put(URLs.TIME_STAMP, TimeStamp);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Logger.i("请求》》", json.toString());
		Logger.i("请求1》》", TimeStamp);
		// 发送请求
		RequestParams params = new RequestParams();
		params.addBodyParameter(URLs.JSON_INFO, json.toString());
		SendActtionTool.post(URLs.FORGOTPW, ServiceAction.Action_User,
				UserAction.Action_Get_Code, this, params);
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_forgotpw);
	}

	@Override
	public void findViewById() {
		getCode = (TextView) findViewById(R.id.get_code);
		verifybtn = (TextView) findViewById(R.id.forgotpw_verify);
		telephone = (EditText) findViewById(R.id.telephone_number);
		verifyCode = (EditText) findViewById(R.id.forgotpw_verfiycode);

		topBar = (TopBar) findViewById(R.id.forgotpw_topbar);
		topBar.setOnTopBarClickListener(new topBarClickListener() {

			@Override
			public void rightClick() {

			}

			@Override
			public void leftClick() {
				if (mc != null) {
					mc.cancel();
					mc.onFinish();
				}
				finish();
			}
		});

		getCode.setOnClickListener(this);
		verifybtn.setOnClickListener(this);

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

	/**
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
			getCode.setTextColor(0xff666666);
			getCode.setBackgroundResource(R.drawable.btn_validation_1);
			getCode.setText("重新发送");
			getCode.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			getCode.setText("重新发送(" + millisUntilFinished / 1000 + ")");
		}
	}
}
