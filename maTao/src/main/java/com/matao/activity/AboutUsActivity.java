package com.matao.activity;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-8-3 下午3:01:12
 * @Description:关于我们
 */

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matao.matao.R;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

public class AboutUsActivity extends BaseActivity {
	static final int FOLLOW_MSG_SHOW_COMPLETE = 4;
	static final int FOLLOW_MSG_SHOW_ERROR = 5;
	static final int FOLLOW_MSG_SHOW_CANCEL = 6;

	private RelativeLayout wxbtn, qqbtn, wbbtn;
	private TextView currentappversion;

	@SuppressLint("NewApi")
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.auboutus_qq:
			// 获取剪切板服务
			ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			clipboardManager.setText("192647149");
			MTUtils.Toast(this, "复制成功");
			break;
		case R.id.auboutus_wx:
			// 获取剪切板服务
			ClipboardManager clip2 = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			clip2.setText("mataoclub");
			MTUtils.Toast(this, "复制成功");
			break;
		case R.id.auboutus_wibo:
			// 点击关注mataowang
			Intent intent = new Intent(this, WebViewActivity.class);
			intent.putExtra("url", "http://m.matao.com");
			startActivity(intent);
			AboutUsActivity.this.finish();
		}
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_aboutus);
		// 初始化SDK
		// ShareSDK.initSDK(this);

	}

	@Override
	public void findViewById() {
		wbbtn = (RelativeLayout) findViewById(R.id.auboutus_wibo);
		wxbtn = (RelativeLayout) findViewById(R.id.auboutus_wx);
		qqbtn = (RelativeLayout) findViewById(R.id.auboutus_qq);
		currentappversion = (TextView) findViewById(R.id.aboutus_appversion);
		currentappversion.setText("V" + MTApplication.mVersionName);
		wbbtn.setOnClickListener(this);
		wxbtn.setOnClickListener(this);
		qqbtn.setOnClickListener(this);

		TopBar topBar = (TopBar) findViewById(R.id.aboutus_topbar);
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
	// //关注某个好友
	// public void follow_frinends(View v){
	// System.out.println("点击关注好友");
	// Platform weibo=ShareSDK.getPlatform(SinaWeibo.NAME);
	// // weibo.SSOSetting(true);//禁用SSO授权
	// weibo.setPlatformActionListener(new PlatformActionListener() {
	//
	// public void onError(Platform arg0, int arg1, Throwable arg2) {
	// System.out.println("关注好友失败,请查看微博名是否存在或为空！");
	// Message msg=new Message();
	// msg.what=FOLLOW_MSG_SHOW_ERROR;
	// handler.sendMessage(msg);
	// }
	// public void onComplete(Platform arg0, int arg1, HashMap<String, Object>
	// arg2) {
	// System.out.println("关注好友成功");
	// Message msg=new Message();
	// msg.what=FOLLOW_MSG_SHOW_COMPLETE;
	// handler.sendMessage(msg);
	// }
	// public void onCancel(Platform arg0, int arg1) {
	// System.out.println("取消关注好友");
	// Message msg=new Message();
	// msg.what=FOLLOW_MSG_SHOW_CANCEL;
	// handler.sendMessage(msg);
	// }
	// });
	// //需要关注好友的微博名字
	// weibo.followFriend("妈淘网");
	// }
	// //消息处理机制
	// Handler handler=new Handler(){
	// public void handleMessage(Message msg) {
	// System.out.println("进入消息处理");
	// switch(msg.what){
	// case FOLLOW_MSG_SHOW_ERROR: {
	// Toast.makeText(AboutUsActivity.this, "关注好友失败",
	// Toast.LENGTH_SHORT).show();
	// }break;
	//
	// case FOLLOW_MSG_SHOW_COMPLETE:{
	// Toast.makeText(AboutUsActivity.this, "关注好友“"+"妈淘网"+"”成功",
	// Toast.LENGTH_SHORT).show();
	// }break;
	//
	// case FOLLOW_MSG_SHOW_CANCEL:{
	// Toast.makeText(AboutUsActivity.this, "取消关注好友",
	// Toast.LENGTH_SHORT).show();
	// }break;
	// }
	// }
	// };

}
