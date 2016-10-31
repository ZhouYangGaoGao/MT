package com.matao.activity;

import org.json.JSONException;

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
import com.matao.utils.MD5Util;
import com.matao.utils.MTApplication;
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
 * @Description:设置 修改密码
 */
public class UpdatePwActivity extends BaseActivity {

	// 密码控件
	private EditText oldPw, newPw, confirmPw;
	private TextView sumbitbtn;
	// 弹出加载对象
	private PopupWindow pro;

	@Override
	public void onClick(View v) {

		String oldPwStr = oldPw.getText().toString().trim();
		String newPwStr = newPw.getText().toString().trim();
		String confirmPwStr = confirmPw.getText().toString().trim();

		if (TextUtils.isEmpty(oldPwStr) || oldPwStr == "请输入旧密码") {
			MTUtils.Toast(this, "旧密码不能为空");
		} else if (TextUtils.isEmpty(newPwStr) || newPwStr == "请输入新密码") {
			MTUtils.Toast(this, "新密码不能为空");

		} else if (newPwStr.getBytes().length < 6
				|| newPwStr.getBytes().length > 16) {
			MTUtils.Toast(this, "密码长度6-16位");
		} else if (MTUtils.checkPassWord(newPwStr)) {
			MTUtils.Toast(this, "密码不能带有特殊字符~");
		} else if (TextUtils.isEmpty(confirmPwStr) || confirmPwStr == "再次输入密码") {
			MTUtils.Toast(this, "确认密码不能为空");
		} else if (!newPwStr.equals(confirmPwStr)) {
			MTUtils.Toast(this, "两次输入密码不一致");
		} else if (newPwStr.equals(oldPwStr)) {
			MTUtils.Toast(this, "旧密码和新密码一致~");
		} else {

			String TimeStamp = MTUtils.getTimeStamp();
			org.json.JSONObject json = new org.json.JSONObject();

			try {
				json.put(
						"Sign",
						MD5Util.getLowerCaseMD5(MTApplication.getInt("UserId") + TimeStamp + URLs.KEY));
				json.put("UserId",
						MTApplication.getInt("UserId"));
				json.put("OldPassword", MD5Util.getLowerCaseMD5(oldPwStr));
				json.put("NewPassword", MD5Util.getLowerCaseMD5(newPwStr));
				json.put("UserToken",
						MTApplication.getString("Token"));
				json.put(URLs.TIME_STAMP, TimeStamp);

				RequestParams p = new RequestParams();
				p.addBodyParameter(URLs.JSON_INFO, json.toString());

				SendActtionTool.post(URLs.UPDATEPW, ServiceAction.Action_User,
						UserAction.Action_UpdaePw, this, p);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);

		if (action.toString() == "Action_UpdaePw") {
			Bean bean = JSON.parseObject(value.toString(), Bean.class);// 获得数据实体
			Data data = bean.getData();
			String Msg = bean.getMsg();
			if (bean.getCode() == 0 && data.isOperateResult() == true) {// 执行成功跳转
				MTUtils.Toast(this, "修改密码成功");
				finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_left_out);
			} else {
				MTUtils.Toast(this, Msg);
			}

		}
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		ClosePop();
		if (action.toString() == "Action_UpdaePw") {
			org.json.JSONObject object;
			try {
				object = new org.json.JSONObject(value.toString());
				String str = object.optString("Msg", "");
				MTUtils.Toast(this, str);

			} catch (JSONException e) {
				e.printStackTrace();
			}
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
		setContentView(R.layout.activity_updatepw);
	}

	@Override
	public void findViewById() {
		oldPw = (EditText) findViewById(R.id.updatepw_oldpw);
		newPw = (EditText) findViewById(R.id.updatepw_newpw);
		confirmPw = (EditText) findViewById(R.id.updatepw_confrimpw);

		sumbitbtn = (TextView) findViewById(R.id.updatepw_sumbit);
		sumbitbtn.setOnClickListener(this);

		TopBar topBar = (TopBar) findViewById(R.id.updatepw_topbar);
		// 头部返回键
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
}
