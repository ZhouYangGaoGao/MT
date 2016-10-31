package com.matao.activity;

import org.json.JSONException;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;

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

/**
 * @author: ZhangYaWei
 * @time:2015-4-22 下午4:30:11
 * @Description:用户反馈
 */
public class FeedBackActivity extends BaseActivity {

	// 反馈内容控件
	private EditText feedBackContent;

	// 弹出加载对象
	private PopupWindow pro;

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.feedBack_send) {// 反馈发送点击按钮
			if (MTUtils.isEmpty(feedBackContent.getText().toString()
							.trim())) {
				MTUtils.Toast(this, "请填写反馈意见");
			} else if (MTUtils.getLength(feedBackContent.getText().toString().trim()) > 1000) {
				MTUtils.Toast(this, "最多只能输入五百个汉字");
			} else {
				sendFeedBack();
			}
		}
	}

	/**
	 * @author: ZhangYaWei
	 * @time:2015-4-22 下午4:30:11
	 * @Description:发送用户反馈
	 */
	private void sendFeedBack() {
		String content = feedBackContent.getText().toString().trim();
		String TimeStamp = MTUtils.getTimeStamp();
		org.json.JSONObject json = new org.json.JSONObject();

		try {
			int userId = MTApplication.getInt("UserId");
			json.put("Sign",
					MD5Util.getLowerCaseMD5(userId + TimeStamp + URLs.KEY));
			json.put("UserId", userId);
			json.put("Content", content);
			json.put("UserToken",
					MTApplication.getString("Token"));
			json.put(URLs.TIME_STAMP, TimeStamp);

			RequestParams p = new RequestParams();
			p.addBodyParameter(URLs.JSON_INFO, json.toString());
			SendActtionTool.post(URLs.FEEDBACK, ServiceAction.Action_User,
					UserAction.Action_FeedBack, this, p);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		Logger.i("feedback==============>>>>>", json.toString());

	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		if (action.toString() == "Action_FeedBack") {
			Bean bean = JSON.parseObject(value.toString(), Bean.class);// 获得数据实体
			String str = bean.getMsg();
			MTUtils.Toast(this, str);
			finish();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
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
	public void onFinish(ServiceAction service, Object action) {
		super.onFinish(service, action);
		ClosePop();
	}

	@Override
	public void setContentView() {

		if (!MTApplication.isLogin) {// 没有登录跳转首页
			startActivity(new Intent(FeedBackActivity.this, LoginActivity.class));
			finish();
		}
		setContentView(R.layout.activity_feedback);
	}

	@Override
	public void findViewById() {
		feedBackContent = (EditText) findViewById(R.id.feedBack_content);
		 findViewById(R.id.feedBack_send).setOnClickListener(this);
		TopBar topBar = (TopBar) findViewById(R.id.feed_topbar);
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
