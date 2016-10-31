package com.matao.activity;
/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-8-3 下午3:01:12
 * @Description:设置密码页面
 */
import org.json.JSONException;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.http.RequestParams;
import com.matao.bean.Bean;
import com.matao.matao.R;
import com.matao.utils.MD5Util;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.utils.SendActtionTool;
import com.matao.utils.ServiceAction;
import com.matao.utils.URLs;
import com.matao.utils.UserAction;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

public class SetPsdActivity extends BaseActivity {
	private EditText ed_psd, ed_agpsd;
	private TextView tv_Y;
	private String phone;
	private TopBar topbar;

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.setpasd_login:
			String psd = ed_psd.getText().toString().trim();
			String psd2 = ed_agpsd.getText().toString().trim();
			if (!psd.equals(psd2)) {
				MTUtils.Toast(this, "两次密码输入不一致");
			}
			int userId = MTApplication.getInt("UserId");
			String token = MTApplication.getString("Token");
			String TimeStamp = MTUtils.getTimeStamp();
			org.json.JSONObject json = new org.json.JSONObject();
			try {
				json.put(
						"Sign",
						MD5Util.getLowerCaseMD5(phone + userId
								+ MD5Util.getLowerCaseMD5(psd) + token
								+ TimeStamp + URLs.KEY));
				json.put(URLs.MOBILE_NUMBER, phone);
				json.put("PassWord", MD5Util.getLowerCaseMD5(psd));
				json.put(URLs.TIME_STAMP, TimeStamp);
				json.put("UserToken", token);
				json.put("UserId", userId);
				json.put("Type", 1);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			// 发送请求
			RequestParams params = new RequestParams();
			params.addBodyParameter(URLs.JSON_INFO, json.toString());
			SendActtionTool.post(URLs.BAND, ServiceAction.Action_User,
					UserAction.Action_Band, this, params);
			break;
		}

	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		switch ((UserAction) action) {
		case Action_Band:
			Bean bean = JSON.parseObject(value.toString(), Bean.class);
			String str = bean.getMsg();

			boolean Operate = bean.getData().isOperateResult();
			if (Operate == true) {
				Intent intent = new Intent(this, ZiLiaoActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_left_out);
				finish();
				MTUtils.Toast(this, str);
			} else {
				MTUtils.Toast(this, str);
			}
			break;
		}
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		Bean bean = JSON.parseObject(value.toString(), Bean.class);
		String str = bean.getMsg();
		MTUtils.Toast(this, str);
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
	}

	@Override
	public void onFinish(ServiceAction service, Object action) {
		super.onFinish(service, action);
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_setpasd);
	}

	@Override
	public void findViewById() {
		ed_agpsd = (EditText) findViewById(R.id.setpasd_password);
		ed_psd = (EditText) findViewById(R.id.setpasd_username);
		tv_Y = (TextView) findViewById(R.id.setpasd_login);
		tv_Y.setOnClickListener(this);
		topbar = (TopBar) findViewById(R.id.setpasd_topbar);
		topbar.setOnTopBarClickListener(new topBarClickListener() {

			@Override
			public void rightClick() {

			}

			@Override
			public void leftClick() {
				finish();
			}
		});
		Intent intent = getIntent();
		phone = intent.getStringExtra("phone");
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
}
