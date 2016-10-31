package com.matao.activity;

import org.json.JSONException;

import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
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
 * @time:2015-5-22 下午4:30:11
 * @Description:忘记密码 重置密码
 */
public class ResetPwActivity extends BaseActivity {

	// 密码控件
	private EditText newPw, confirmPw;
	private TextView sumbitbtn;
	public String token;
	public int userId;
	// 弹出加载对象
	private PopupWindow pro;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String newPwStr = newPw.getText().toString().trim();
		String confirmPwStr = confirmPw.getText().toString().trim();

		if (TextUtils.isEmpty(newPwStr) || newPwStr == "请输入新密码") {
			MTUtils.Toast(this, "新密码不能为空");

		} else if (newPwStr.getBytes().length < 6
				|| newPwStr.getBytes().length > 16) {
			MTUtils.Toast(this, "密码长度6-16位");
		} else if (MTUtils.checkPassWord(newPwStr)) {
			MTUtils.Toast(this, "密码不能带有特殊字符~");
		} else if (TextUtils.isEmpty(confirmPwStr) || confirmPwStr == "再次输入新密码") {
			MTUtils.Toast(this, "确认密码不能为空");
		} else if (!newPwStr.equals(confirmPwStr)) {
			MTUtils.Toast(this, "两次输入密码不一致");
		} else {

			String TimeStamp = MTUtils.getTimeStamp();
			org.json.JSONObject json = new org.json.JSONObject();

			try {
				json.put(
						"Sign",
						MD5Util.getLowerCaseMD5(userId + ""
								+ MD5Util.getLowerCaseMD5(newPwStr) + token
								+ TimeStamp + URLs.KEY));
				json.put("UserId", userId);
				json.put("PassWord", MD5Util.getLowerCaseMD5(newPwStr));
				json.put("UserToken", token);
				json.put(URLs.TIME_STAMP, TimeStamp);

				RequestParams p = new RequestParams();
				p.addBodyParameter(URLs.JSON_INFO, json.toString());

				SendActtionTool.post(URLs.RESETPW, ServiceAction.Action_User,
						UserAction.Action_ResetPw, this, p);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Logger.i("updatePw==============>>>>>", json.toString());
		}
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		Logger.i("msg--------------", action.toString());
		if (action.toString() == "Action_ResetPw") {
			Bean bean = JSON.parseObject(value.toString(), Bean.class);// 获得数据实体
																		// //
																		// 解析数据

			Data data = bean.getData();
			Logger.i("msg--------------", bean.getMsg());
			MTUtils.Toast(this, bean.getMsg());
			Logger.i("password--------------------", data.isOperateResult());
			if (data.isOperateResult() == true) {// 执行成功跳转
				// 跳转首页
				startActivity(new Intent(ResetPwActivity.this,
						LoginActivity.class));
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_left_out);
				finish();
			}

			ClosePop();
		}
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		ClosePop();
		org.json.JSONObject object;
		try {
			object = new org.json.JSONObject(value.toString());
			String str = object.optString("Msg", "");
			MTUtils.Toast(this, str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
		ClosePop();
	}

	@Override
	public void onStart(ServiceAction service, Object action) {
		super.onStart(service, action);
		View v = LayoutInflater.from(this).inflate(R.layout.progressdialog,
				null);
		pro = new PopupWindow(v, 200, 200);
		pro.setFocusable(true); // 设置不允许在外点击消失
		pro.setOutsideTouchable(false);
		MTUtils.KeyBoardCancle(this);
		pro.showAtLocation(View.inflate(this, R.layout.activity_login, null),
				Gravity.CENTER, 0, 0);
	}

	@Override
	public void onFinish(ServiceAction service, Object action) {
		super.onFinish(service, action);
		ClosePop();
	}

	/**
	 * ZhangYaWei 2015.5.14 关闭加载体提示
	 * */
	public void ClosePop() {
		if (pro != null && pro.isShowing()) {
			pro.dismiss();
			pro = null;
		}
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_resetpw);
	}

	@Override
	public void findViewById() {

		newPw = (EditText) findViewById(R.id.resetpw_pw);
		confirmPw = (EditText) findViewById(R.id.resetpwconfrim_pw);

		sumbitbtn = (TextView) findViewById(R.id.resetpw_sumbit);
		sumbitbtn.setOnClickListener(this);
		Intent i = getIntent();
		userId = i.getIntExtra("forgotuserId", 0);
		Logger.i("mg------------------", userId);
		token = i.getStringExtra("token");

		TopBar topBar = (TopBar) findViewById(R.id.resetpw_topbar);
		// 头部返回键
		topBar.setOnTopBarClickListener(new topBarClickListener() {

			@Override
			public void rightClick() {
				// TODO Auto-generated method stub
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
}
