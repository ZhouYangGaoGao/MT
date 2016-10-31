package com.matao.activity;

import java.util.Map;
import java.util.Set;

import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
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
import com.matao.fragment.BaseFragment;
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
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SocializeClientListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-22 下午4:30:11
 * @Description:登陆界面
 */

public class LoginActivity extends BaseActivity {
	private TopBar topbar;
	private TextView login;
	private EditText username, password;
	private PopupWindow pro;
	private int MediaCate;
	private UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.login");
	private SHARE_MEDIA platformType;
	private String MediaUserID, MediaNickName = "呵呵呵", UserToken, UserToken2;
	private String Expires_in, name;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_stroll:
			Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			this.finish();
			break;
		case R.id.login_forgetpass:
			Intent intent3 = new Intent(LoginActivity.this,
					ForgotPwActivity.class);
			startActivity(intent3);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			break;
		case R.id.login_quikRegs:
			Intent quikRegsIntent = new Intent(this, RegisterActivity.class);
			startActivity(quikRegsIntent);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			break;
		case R.id.login_login:
			name = username.getText().toString().trim();
			String pass = password.getText().toString().trim();
			if (TextUtils.isEmpty(name)) {
				MTUtils.Toast(this, "账号不能为空");
			} else if (TextUtils.isEmpty(pass)) {
				MTUtils.Toast(this, "密码不能为空");
			} else if (!MTUtils.isPhone(name) && !MTUtils.isEmail(name)) {
				MTUtils.Toast(this, "请输入正确的手机号或邮箱地址");
			} else {

				// Resources resource = getResources();
				// ColorStateList w = (ColorStateList) resource
				// .getColorStateList(R.color.black_button_nor);

				// login.setClickable(false);
				// login.setBackgroundResource(R.drawable.shape_login);
				login(name, pass);
			}

			break;
		case R.id.login_qq:
			// LoginProgress();
			MediaCate = 0;
			platformType = SHARE_MEDIA.QQ;
			initUm(SHARE_MEDIA.QQ);
			break;
		case R.id.login_wx:
			// LoginProgress();
			MediaCate = 2;
			platformType = SHARE_MEDIA.WEIXIN;
			initUm(SHARE_MEDIA.WEIXIN);
			break;
		case R.id.login_xl:
			// LoginProgress();
			MediaCate = 1;
			platformType = SHARE_MEDIA.SINA;
			initUm(SHARE_MEDIA.SINA);
			break;
		case R.id.dialog_N:

			break;
		case R.id.dialog_Y:

			break;
		case R.id.login_username:
			username.setCursorVisible(true);
			username.setFocusable(true);
			username.setFocusableInTouchMode(true);
			username.requestFocus();

			login.setText("登录");
			login.setBackgroundResource(R.drawable.btn_4);
			login.setClickable(true);
			break;
		case R.id.login_password:
			login.setText("登录");
			login.setClickable(true);
			login.setBackgroundResource(R.drawable.btn_4);
			break;
		}
	}

	// ---------------------------------三方登录----------------------------------------------

	// 授权接口
	private void initUm(final SHARE_MEDIA platformType) {

		mController.doOauthVerify(LoginActivity.this, platformType,
				new UMAuthListener() {
					@Override
					public void onError(SocializeException e,
							SHARE_MEDIA platform) {
						// CloseLoginProgress();
					}

					@Override
					public void onComplete(Bundle value, SHARE_MEDIA platform) {
						// CloseLoginProgress();
						if (value != null) {
							MediaUserID = platform.name().equals(
									SHARE_MEDIA.QQ.name()) ? value
									.getString("uid") : platform.name().equals(
									SHARE_MEDIA.WEIXIN.name()) ? value
									.getString("openid") : value
									.getString("uid");
							Logger.i("bundle----------------->",
									value.toString());
							UserToken = value.getString("access_token");
							Logger.i("UserToken----------------->", UserToken);
							UserToken2 = value.getString("access_key");
							Logger.i("UserToken2----------------->", UserToken2);
							Expires_in = value.getString("expires_in");
							Logger.i("Expires_in----------------->", Expires_in);
							if (!TextUtils.isEmpty(MediaUserID)) {
								// 授权将相应字段上传服务器验证
								String TimeStamp = MTUtils.getTimeStamp();
								org.json.JSONObject json2 = new org.json.JSONObject();
								try {
									json2.put(
											"Sign",
											MD5Util.getLowerCaseMD5(MediaUserID
													+ TimeStamp + URLs.KEY));
									json2.put(URLs.TIME_STAMP, TimeStamp);
									json2.put("MediaUserID", MediaUserID);
									json2.put("MediaCate", MediaCate);
								} catch (JSONException e) {
									e.printStackTrace();
								}
								RequestParams p2 = new RequestParams();
								p2.addBodyParameter(URLs.JSON_INFO,
										json2.toString());
								SendActtionTool.post(URLs.ThirdPartyLogon,
										ServiceAction.Action_User,
										UserAction.Action_Threelogin,
										LoginActivity.this, p2);
							} else {
								// MTUtils.Toast(LoginActivity.this, "授权失败.");
							}
						}
					}

					@Override
					public void onCancel(SHARE_MEDIA platform) {
						// CloseLoginProgress();
						// MTUtils.Toast(LoginActivity.this, "授权取消.");
					}

					@Override
					public void onStart(SHARE_MEDIA platform) {
						// CloseLoginProgress();
						// MTUtils.Toast(LoginActivity.this, "授权开始.");
					}
				});
	}

	// 获取用户资料
	private void getUserInfo(SHARE_MEDIA platform) {
		mController.getPlatformInfo(LoginActivity.this, platform,
				new UMDataListener() {
					@Override
					public void onStart() {
						// CloseLoginProgress();
					}

					@Override
					public void onComplete(int status, Map<String, Object> info) {
						// CloseLoginProgress();
						if (status == 200 && info != null) {
							// 获得第三方平台中该用户的 头像昵称等各项信息，如需要可获取传递给服务器
							StringBuilder sb = new StringBuilder();
							Set<String> keys = info.keySet();
							for (String key : keys) {
								sb.append(key + "=" + info.get(key).toString()
										+ "\r\n");
								// if (info.get("screen_name").toString()
								// .equals("")) {
								MediaNickName = "";
								// } else {
								// MediaNickName = info.get("screen_name")
								// .toString();
								// }

							}
							Logger.i("MediaNickName", MediaNickName);
							fuck();
							Logger.i("TestData", sb.toString());
						} else {
							Logger.i("TestData", "发生错误：" + status);
						}
					}
				});
	}

	// 配置三方SSO(免登录)开关
	private void ssoOK() {
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(LoginActivity.this,
				URLs.WXAppID, URLs.WXAppSecret);
		wxHandler.addToSocialSDK();
		// 添加微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(LoginActivity.this,
				URLs.WXAppID, URLs.WXAppSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		// 添加QQ
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(LoginActivity.this,
				URLs.QQAppID, URLs.QQAppSecret);
		qqSsoHandler.addToSocialSDK();
		// 添加QQ空间
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
				LoginActivity.this, URLs.QQAppID, URLs.QQAppSecret);
		qZoneSsoHandler.addToSocialSDK();
		// 设置新浪SSO handler
		// SinaSsoHandler sinaSsoHandler = new
		// SinaSsoHandler(LoginActivity.this);
		// sinaSsoHandler.addToSocialSDK();

		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		mController.getConfig().setSinaCallbackUrl("http://www.matao.com/");
	}

	// 添加SSO相关回调
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	// 注销登录的方法
	private void exit(SHARE_MEDIA platform) {
		mController.deleteOauth(LoginActivity.this, platform,
				new SocializeClientListener() {
					@Override
					public void onStart() {
					}

					@Override
					public void onComplete(int status, SocializeEntity entity) {
						if (status == 200) {
							MTUtils.Toast(LoginActivity.this, "删除成功.");
						} else {
							MTUtils.Toast(LoginActivity.this, "删除失败.");
						}
					}
				});
	}

	// -------------------------------------------------------------------------------------------------

	// 普通登录
	private void login(String name, String pass) {
		String Account = name;
		String PassWord = pass;
		String TimeStamp = MTUtils.getTimeStamp();
		org.json.JSONObject json = new org.json.JSONObject();
		try {
			json.put(
					"Sign",
					MD5Util.getLowerCaseMD5(Account
							+ MD5Util.getLowerCaseMD5(PassWord) + TimeStamp
							+ URLs.KEY));
			json.put(URLs.TIME_STAMP, TimeStamp);
			json.put(URLs.PASSWORD, MD5Util.getLowerCaseMD5(PassWord));
			json.put(URLs.ACCOUNT, Account);
			Logger.i("jsonLogin==============>>>>>", json.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		RequestParams p = new RequestParams();
		p.addBodyParameter(URLs.JSON_INFO, json.toString());
		SendActtionTool.post(URLs.LOGIN, ServiceAction.Action_User,
				UserAction.Action_login, this, p);

	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		Logger.i("----------------onSuccess-------------", value.toString());
		switch ((UserAction) action) {
		// 普通登陆
		case Action_login:
			Bean bean = JSON.parseObject(value.toString(), Bean.class);// 获得数据实体，使用alibaba的fastJson解析
			Data data = bean.getData();
			if (data.getPoint() > 0) {
				MTUtils.JiFenToast(this, bean.getMsg());
			} else {
				MTUtils.Toast(this, bean.getMsg());
			}
			MTApplication.mEditor.putInt("UserId", data.getUserId());
			MTApplication.mEditor.putInt("Point", data.getPoint());
			MTApplication.mEditor.putString("Token", data.getToken());
			MTApplication.mEditor.putString("Username", name);
			MTApplication.mEditor.commit();
			if (WelcomeActivity.getInstence() != null) {
				WelcomeActivity.getInstence().finish();
			}
			MTApplication.setLogin(true);
			MTUtils.Toast(this, "登录成功");
			if (isOther == false) {
				startActivity(new Intent(LoginActivity.this, HomeActivity.class));
				finish();
			} else {
				LoginActivity.this.finish();
			}
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			CloseLoginProgress();
			break;
		// 三方登录
		case Action_Threelogin:
			// 将服务器验证后返回的结果进行解析
			Bean bean2 = JSON.parseObject(value.toString(), Bean.class);// 获得数据实体，使用alibaba的fastJson解析
			Data data2 = bean2.getData();
			if (data2.getPoint() > 0) {
				MTUtils.JiFenToast(this, bean2.getMsg());
			} else {
				MTUtils.Toast(this, bean2.getMsg());
			}
			MTApplication.mEditor.putInt("UserId", data2.getUserId());
			MTApplication.mEditor.putInt("Point", data2.getPoint());
			MTApplication.mEditor.putString("Token", data2.getToken());
			MTApplication.mEditor.commit();
			WelcomeActivity.getInstence().finish();
			MTApplication.setThreeLogin(true);
			MTApplication.setLogin(true);
			// 判断该用户是否之前授权登录过
			int code = bean2.getCode();
			if (code == 0 && bean2.getData() != null) {
				// 授权登录成功
				BaseFragment.isJump = true;
				if (isOther == false) {
					startActivity(new Intent(LoginActivity.this,
							HomeActivity.class));
					finish();
				} else {
					LoginActivity.this.finish();
				}
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_left_out);
			}
			break;
		}

	}

	@Override
	public void onStart(ServiceAction service, Object action) {
		super.onStart(service, action);
		Logger.i("----------------onStart-------------", action.toString());

		LoginProgress();

	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		MTApplication.setLogin(false);
		Logger.i("----------------onFaile-------------", value.toString());
		switch ((UserAction) action) {
		case Action_login:
			org.json.JSONObject object;
			try {
				object = new org.json.JSONObject(value.toString());
				String str = object.optString("Msg", "");
				MTUtils.Toast(this, str);
				// showDialog(str);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case Action_Threelogin:
			// 之前未三方登陆过跳转至完善信息页，再次调用获取单方平台用户信息的方法可获取三方平台用户信息（可将信息传递给需要的界面）
			getUserInfo(platformType);
			break;
		}
	}

	private void fuck() {
		if (MTUtils.isEmpty(UserToken)) {
			UserToken = UserToken2;
		}
		Intent intent = new Intent(this, PerfectActivity.class);
		intent.putExtra("isMidier", true);// 是否三方
		intent.putExtra("MediaUserID", MediaUserID);// 三方媒体 ID
		intent.putExtra("MediaKey", UserToken);// 三方媒体 token
												// 值第三方用户信息返回的token

		intent.putExtra("MediaNickName", MediaNickName);// 三方媒体昵称
		intent.putExtra("ProfileImageUrl", "");// 第三方用户头像地址
												// 取像素比例最大的提交没有的时候传字符串””
		intent.putExtra("MediaCate", MediaCate);// 三方媒体类型（0、腾讯 1、新浪 2 微信）
		intent.putExtra("ExpireDay", Expires_in);// 三方媒体签名有效天数
		startActivity(intent);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
	}

	@Override
	public void onFinish(ServiceAction service, Object action) {
		super.onFinish(service, action);
		CloseLoginProgress();
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
		CloseLoginProgress();
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_login);
		// SharedPreferences mspf = getSharedPreferences("matao", MODE_PRIVATE);
	}

	@Override
	public void findViewById() {
		init();
		String user = MTApplication.getString("Username");
		if (!MTUtils.isEmpty(user)) {
			// 默认填充上次成功登录的用户名
			username.setText(user);
			// 密码自动获取焦点
			password.requestFocus();
		} else {
			username.setText("");
		}

		ssoOK();
	}

	private boolean isOther;

	private void init() {
		Intent intent = getIntent();
		isOther = intent.getBooleanExtra("isOther", false);
		// flag_wel = intent.getStringExtra("flag");
		topbar = (TopBar) findViewById(R.id.login_topbar);
		BaseFragment.isJump = true;
		topbar.getRightImageView().setVisibility(View.VISIBLE);
		topbar.setOnTopBarClickListener(new topBarClickListener() {

			@Override
			public void rightClick() {
				// if (MTUtils.isEmpty(flag_wel)) {
				finish();
				overridePendingTransition(R.anim.slide,
						R.anim.slide_out_to_bottom);
				// } else {
				// finish();
				// }
			}

			@Override
			public void leftClick() {

			}
		});
		username = (EditText) findViewById(R.id.login_username);
		password = (EditText) findViewById(R.id.login_password);
		login = (TextView) findViewById(R.id.login_login);
		login.setOnClickListener(this);
		username.setOnClickListener(this);
		password.setOnClickListener(this);
		findViewById(R.id.login_forgetpass).setOnClickListener(this);
		findViewById(R.id.login_quikRegs).setOnClickListener(this);
		findViewById(R.id.login_qq).setOnClickListener(this);
		findViewById(R.id.login_wx).setOnClickListener(this);
		findViewById(R.id.login_xl).setOnClickListener(this);
		findViewById(R.id.login_stroll).setOnClickListener(this);
	}

	// 开启登录进度条
	private void LoginProgress() {
		View v = LayoutInflater.from(this).inflate(R.layout.progressdialog,
				null);
		pro = new PopupWindow(v, 200, 200);
		pro.setFocusable(true); // 设置不允许在外点击消失
		pro.setOutsideTouchable(false);
		MTUtils.KeyBoardCancle(this);
		pro.showAtLocation(View.inflate(this, R.layout.activity_login, null),
				Gravity.CENTER, 0, 0);
	}

	// 关闭登录进度条
	private void CloseLoginProgress() {
		if (pro != null && pro.isShowing()) {
			pro.dismiss();
			pro = null;
		}
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.slide, R.anim.slide_out_to_bottom);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

}
