package com.matao.activity;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-8-3 下午3:01:12
 * @Description:修改用户昵称
 */
import org.json.JSONException;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
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

public class AmendNameActivity extends BaseActivity {
	private EditText ed_name;
	private TextView tv_save;
	private String old_name, name;
	private TopBar topBar;

	public void init() {
		setContentView(R.layout.activity_amend_name);

		topBar = (TopBar) findViewById(R.id.wdxw_topbar);
		topBar.setOnTopBarClickListener(new topBarClickListener() {

			@Override
			public void leftClick() {
				finish();
			}

			@Override
			public void rightClick() {
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save:
			name = ed_name.getText().toString().trim();
			if (!MTUtils.isEmpty(name) && !name.equals(old_name)) {
				Logger.i("===", MTUtils.getLength(name));
				if (MTUtils.getLength(name) < 4) {
					MTUtils.Toast(this, "昵称不得少于4个字符哦~");
				} else if (MTUtils.getLength(name) > 16) {
					MTUtils.Toast(this, "昵称不得大于16个字符哦~");
				} else if (!MTUtils.checkNickName(name)) {
					MTUtils.Toast(this, "昵称不能使用特殊符号");
				}
				upName();
			} else {
				name = old_name;
				finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_left_out);
			}
			break;
		}
	}

	// 返回数据
	private void setRst() {
		Intent data = new Intent();
		data.putExtra("NickName", name);
		setResult(ZiLiaoActivity.CODE_AMEND_NAME, data);
		AmendNameActivity.this.finish();
	}

	@Override
	public void setContentView() {
		init();
		ViewUtils.inject(this);
	}

	@Override
	public void findViewById() {
		ed_name = (EditText) findViewById(R.id.edit_name);
		tv_save = (TextView) findViewById(R.id.save);
		tv_save.setOnClickListener(this);
		Intent intent = getIntent();
		old_name = intent.getStringExtra("name");
		if (!MTUtils.isEmpty(old_name)) {
			ed_name.setHint(old_name);
		}
	}

	// 请求接口上传昵称
	private void upName() {
		name = ed_name.getText().toString().trim();
		String TimeStamp = MTUtils.getTimeStamp();
		int userId = MTApplication.getInt("UserId");
		String token = MTApplication.getString("Token");
		org.json.JSONObject json = new org.json.JSONObject();
		try {
			json.put("Sign",
					MD5Util.getLowerCaseMD5(userId + TimeStamp + URLs.KEY));
			json.put(URLs.TIME_STAMP, TimeStamp);
			json.put("UserId", userId);
			json.put("UserToken", token);
			json.put("TypeId", 0);
			json.put("NickName", name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		RequestParams p = new RequestParams();
		p.addBodyParameter(URLs.JSON_INFO, json.toString());
		SendActtionTool.post(URLs.AMEND, ServiceAction.Action_User,
				UserAction.Action_AmName, this, p);
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		// TODO Auto-generated method stub
		super.onSuccess(service, action, value);
		Bean bean = JSON.parseObject(value.toString(), Bean.class);
		String str = bean.getMsg();
		boolean operate = bean.getData().isOperateResult();
		if (operate == false) {
			MTUtils.Toast(this, str);
		} else {
			setRst();
			MTUtils.Toast(this, str);
		}
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		// TODO Auto-generated method stub
		super.onFaile(service, action, value);
		Bean bean = JSON.parseObject(value.toString(), Bean.class);
		String str = bean.getMsg();
		MTUtils.Toast(this, str);
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		// TODO Auto-generated method stub
		super.onException(service, action, value);
		// MTUtils.Toast(this, value.toString());
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
