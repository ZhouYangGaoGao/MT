package com.matao.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.matao.adapter.WelcomePagerAdapter;
import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-8-3 下午3:01:12
 * @Description:欢迎页
 */
public class WelcomeActivity extends BaseActivity implements OnClickListener,
		OnPageChangeListener {
	// 定义ViewPager对象
	private ViewPager viewPager;
	// 定义ViewPager适配器
	private WelcomePagerAdapter vpAdapter;
	// 定义一个ArrayList来存放View
	private ArrayList<View> views;
	// 引导图片资源
	private static final int[] pics = { R.drawable.wel_1, R.drawable.wel_2,
			R.drawable.wel_3, R.drawable.wel_4, R.drawable.wel_5 };
	// 引导图片资源2
	private static final int[] pics2 = { R.drawable.wel_1, R.drawable.wel_2,
			R.drawable.wel_3, R.drawable.wel_4, R.drawable.wel_5 };
	// 底部小点的图片
//	private ImageView[] points;
	// 记录当前选中位置
//	private int currentIndex;
	private final int SPLASH_DISPLAY_LENGHT = 3000; // 延迟三秒
	private static WelcomeActivity me;

	public static WelcomeActivity getInstence() {
		return me;
	}

	@Override
	public void setContentView() {
		// 发送策略定义了用户由统计分析SDK产生的数据发送回友盟服务器的频率。
		MobclickAgent.updateOnlineConfig(this);
		// 设置是否对日志信息进行加密, 默认false(不加密).
		AnalyticsConfig.enableEncrypt(true);
		me = this;
		// 获取宝宝状态信息
		String babyState = MTApplication.getString("BabyState2");
		if (!babyState.equals("yes")) {// 没保存
			setContentView(R.layout.welcome_pager);
			initView();
			initData();
		} else {// 保存
			if (MTApplication.isFlag == false) {// 第一次展示欢迎页
				setContentView(R.layout.welcome_pager);
				initView();
				initData2();
				MTApplication.setFlag(true);
			} else {// 展示过欢迎页之后
				setContentView(R.layout.splash);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent(WelcomeActivity.this,
								HomeActivity.class);
						startActivity(intent);
						WelcomeActivity.this.finish();
					}

				}, SPLASH_DISPLAY_LENGHT);
			}
		}
	}

	@Override
	public void findViewById() {

	}

	// 获取版本号
	// String Version = MTApplication.getString("mVersionName");
	// String Version2 = MTApplication.mVersionName;
	// Logger.i("Version--Version2", Version + " - " + Version2);
	// if (MTUtils.isEmpty(Version) || (!Version.equals(Version2))) {
	// int code = MTApplication.getInt("fristload");
	// if (code != 0) {
	// setContentView(R.layout.splash);
	// new Handler().postDelayed(new Runnable() {
	// @Override
	// public void run() {
	// Intent intent = new Intent(WelcomeActivity.this,
	// HomeActivity.class);
	// startActivity(intent);
	// WelcomeActivity.this.finish();
	// }
	//
	// }, SPLASH_DISPLAY_LENGHT);
	// } else {
	// MTApplication.mEditor.putInt("fristload", 1);
	// MTApplication.mEditor.putString("mVersionName", "1.2");
	// MTApplication.mEditor.commit();
	// }
	// }

	/**
	 * 初始化组件
	 */
	private void initView() {
		// 实例化ArrayList对象
		views = new ArrayList<View>();

		// 实例化ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);

		// 实例化ViewPager适配器
		vpAdapter = new WelcomePagerAdapter(views);
		getDeviceInfo(this);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 定义一个布局并设置参数
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);

		// 初始化引导图片列表
		for (int i = 0; i < pics.length; i++) {
			RelativeLayout iv = (RelativeLayout) LayoutInflater.from(this)
					.inflate(R.layout.wel_1, null);
			iv.setLayoutParams(mParams);
			iv.setBackgroundResource(pics[i]);
			ImageButton imb = (ImageButton) iv.findViewById(R.id.wel_login);
			ImageButton imb2 = (ImageButton) iv.findViewById(R.id.wel_gyg);
			imb.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(WelcomeActivity.this, LoginActivity.class);
					i.putExtra("flag", "yes");
					i.putExtra("isOther", false);
					WelcomeActivity.this.startActivity(i);
					((Activity) WelcomeActivity.this).overridePendingTransition(
							R.anim.slide_in_from_bottom, R.anim.slide);
				}
			});
			imb2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					MTUtils.showGygDialog(WelcomeActivity.this);
				}
			});
			views.add(iv);
		}

		// 设置数据
		viewPager.setAdapter(vpAdapter);
		// 设置监听

		// 初始化底部小点
		// initPoint();
	}

	private void initData2() {
		// 定义一个布局并设置参数
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);

		// 初始化引导图片列表
		for (int i = 0; i < pics2.length; i++) {
			RelativeLayout iv = (RelativeLayout) LayoutInflater.from(this)
					.inflate(R.layout.wel_01, null);
			iv.setLayoutParams(mParams);
			iv.setBackgroundResource(pics2[i]);
			ImageButton imb = (ImageButton) iv.findViewById(R.id.wel_tg);
			imb.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					startActivity(new Intent(WelcomeActivity.this,
							HomeActivity.class));
					finish();
				}
			});
			views.add(iv);
		}

		// 设置数据
		viewPager.setAdapter(vpAdapter);
		// 初始化底部小点
		// initPoint();
	}

	// /**
	// * 初始化底部小点
	// */
	// private void initPoint() {
	// LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);
	//
	// points = new ImageView[pics.length];
	//
	// // 循环取得小点图片
	// for (int i = 0; i < pics.length; i++) {
	// // 得到一个LinearLayout下面的每一个子元素
	// points[i] = (ImageView) linearLayout.getChildAt(i);
	// // 默认都设为灰色
	// points[i].setEnabled(true);
	// // 给每个小点设置监听
	// points[i].setOnClickListener(this);
	// // 设置位置tag，方便取出与当前位置对应
	// points[i].setTag(i);
	// }
	//
	// // 设置当面默认的位置
	// currentIndex = 0;
	// // 设置为白色，即选中状态
	// points[currentIndex].setEnabled(false);
	// }

	/**
	 * 当滑动状态改变时调用
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	/**
	 * 当当前页面被滑动时调用
	 */

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	/**
	 * 当新的页面被选中时调用
	 */

	@Override
	public void onPageSelected(int position) {
		// 设置底部小点选中状态
		// setCurDot(position);

	}

	/**
	 * 通过点击事件来切换当前的页面
	 */
	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		// setCurDot(position);
	}

	/**
	 * 设置当前页面的位置
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}
		viewPager.setCurrentItem(position);
	}

	// /**
	// * 设置当前的小点的位置
	// */
	// private void setCurDot(int positon) {
	// if (positon < 0 || positon > pics.length - 1 || currentIndex == positon)
	// {
	// return;
	// }
	// points[positon].setEnabled(false);
	// points[currentIndex].setEnabled(true);
	//
	// currentIndex = positon;
	// }
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public static String getDeviceInfo(Context context) {
		try {
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			String device_id = tm.getDeviceId();

			android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);

			String mac = wifi.getConnectionInfo().getMacAddress();
			json.put("mac", mac);

			if (TextUtils.isEmpty(device_id)) {
				device_id = mac;
			}

			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
			}

			json.put("device_id", device_id);

			// return json.toString();
			Logger.i("devicemsg---------------------->", json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
