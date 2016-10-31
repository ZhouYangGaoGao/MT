package com.matao.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.matao.adapter.FragmentTabAdapter;
import com.matao.adapter.FragmentTabAdapter.OnRgsExtraCheckedChangedListener;
import com.matao.bean.HeadAdList;
import com.matao.bean.MsgBean;
import com.matao.bean.MsgData;
import com.matao.fragment.ExperienceFragment;
import com.matao.fragment.FavorableFragment;
import com.matao.fragment.HomeFragment;
import com.matao.fragment.ShowFragment;
import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.utils.SendActtionTool;
import com.matao.utils.ServiceAction;
import com.matao.utils.URLs;
import com.matao.utils.UserAction;
import com.umeng.analytics.MobclickAgent;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-23 下午4:13:33
 * @Description:主界面
 */

public class HomeActivity extends BaseActivity implements
		OnRgsExtraCheckedChangedListener {
	// private long exitTime = 0;
	private FragmentTabAdapter tabAdapter;
	private static RadioGroup rgs;
	private HomeFragment home;
	private static FavorableFragment favorable;
	// private static SeasFragment seas;
	private ExperienceFragment experience;
	private ShowFragment show;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private int msg_num;
	private static HomeActivity ha;
	private Dialog isLoadDialog;
	private TextView tv_msgnum;
	public static HomeActivity getHomeAvtivity() {
		return ha;
	}

	private void initTabAdapter() {
		tabAdapter = new FragmentTabAdapter(getSupportFragmentManager(),
				fragments, R.id.home_fragment, rgs);
		tabAdapter.setOnRgsExtraCheckedChangedListener(this);
	}

	private void initFragments() {
		fragments.add(home);
		fragments.add(favorable);
		// fragments.add(seas);
		fragments.add(experience);
		fragments.add(show);
	}

	private void initView() {
		rgs = (RadioGroup) findViewById(R.id.home_bottom);
		tv_msgnum = (TextView) findViewById(R.id.home_msgnum);
		// 底部导航高度 屏幕适配
		LinearLayout.LayoutParams lp = (LayoutParams) rgs.getLayoutParams();
		lp.height = (int) (MTUtils.getScreenWidth(this) * 0.1528);
		rgs.setLayoutParams(lp);

		home = (home == null) ? new HomeFragment() : home;
		favorable = (favorable == null) ? new FavorableFragment() : favorable;
		// seas = (seas == null) ? new SeasFragment() : seas;
		experience = (experience == null) ? new ExperienceFragment()
				: experience;
		show = (show == null) ? new ShowFragment() : show;
	}

	@Override
	public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId,
			int index) {
		if (tv_msgnum.getVisibility() == View.VISIBLE) {
			Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
			alphaAnimation.setDuration(500);
			alphaAnimation.setStartOffset(500l);
			tv_msgnum.startAnimation(alphaAnimation);
		}
		getMsgnum();
	}

	@Override
	public void onClick(View v) {

	}

	public static void setRadioButtonId(int idx, int tabIdx, int sortid) {
		switch (idx) {
		case 1:
			FavorableFragment.topTabIndex = tabIdx;
			FavorableFragment.sortid = sortid;
			if (!FavorableFragment.isJump) {
				FavorableFragment.isJump = true;
				favorable.onResume();
			}
			break;
		case 2:
			ExperienceFragment.isJump = true;
			ExperienceFragment.sortid = sortid;
			break;
		case 3:
			ShowFragment.isJump = true;
			ShowFragment.sortid = sortid;
			break;
		}
		((RadioButton) rgs.getChildAt(idx)).setChecked(true);
	}

	public void linkTo(HeadAdList HeadAdList) {
		Logger.i("linkTo", HeadAdList);
		Intent i = null;
		switch (HeadAdList.getNativeName()) {
		// 优惠/海淘内嵌页面 详情页地址|文章ID webView加载
		case "YouhuiDetail":
			i = new Intent(this, DetailsActivity.class);
			i.putExtra("url", HeadAdList.getNativeParam());
			startActivity(i);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			i = null;
			break;

		case "JingyanDetail":// 经验/晒单内嵌页面
			i = new Intent(this, DetailsActivity.class);
			i.putExtra("url", HeadAdList.getNativeParam());
			startActivity(i);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			i = null;
			break;
		case "MataoTopic":
			WebViewActivity.isTopic = true;
			i = new Intent(this, WebViewActivity.class);
			i.putExtra("url", HeadAdList.getNativeParam());
			startActivity(i);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			i = null;
			break;
		case "SpecialTopic":// 所有第三方的页面 内嵌浏览器打开
			i = new Intent(this, WebViewActivity.class);
			i.putExtra("url", HeadAdList.getNativeParam());
			startActivity(i);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			i = null;
			break;
		case "Tag":// 6015 标签ID
			i = new Intent(this, TagActivity.class);
			i.putExtra("tagid", HeadAdList.getNativeParam());
			startActivity(i);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			i = null;
			break;
		case "SuperLowPrice":// 进入优惠超级低价列表
			// FavorableFragment.sourceid = 1;
			setRadioButtonId(1, 3, 0);
			break;
		case "Nine":// 进入优惠折扣列表
			// FavorableFragment.sourceid = 1;
			startActivity(new Intent(this, HomeActivity.class));
			setRadioButtonId(1, 2, 0);
			break;
		case "Discount":// 进入优惠折扣列表
			// FavorableFragment.sourceid = 1;
			setRadioButtonId(1, 1, 0);
			break;
		// case "HaiTaoDiscount":// 进入海淘折扣列表
		// // FavorableFragment.sourceid = 2;
		// setRadioButtonId(1, 1, 0);
		// break;
		// case "HaiTaoSuperLowPrice":// 进入海淘超级低价列表
		// // FavorableFragment.sourceid = 2;
		// setRadioButtonId(1, 3, 0);
		// break;
		}
	}

	// 获取未读消息数量
	public void getMsgnum() {
		String url = null;
		String TimeStamp = MTUtils.getTimeStamp();
		if (MTApplication.isLogin == true) {
			int UserId = MTApplication.getInt("UserId");
			url = MTUtils.getMTParams(URLs.MSgNum, URLs.USER_ID, UserId, "p",
					TimeStamp);
			SendActtionTool.get(url, ServiceAction.Action_Comment,
					UserAction.Action_GetMasgNum, this);
			Logger.e("success", "getmsgsuccess");
		}
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		switch ((UserAction) action) {
		case Action_GetMasgNum:
			MsgBean mbean = JSON.parseObject(value.toString(), MsgBean.class);
			MsgData mdata = mbean.getData();
			msg_num = mdata.getTotal();
			if (msg_num != 0) {
				tv_msgnum.setVisibility(View.VISIBLE);
				if (msg_num >= 99) {
					tv_msgnum.setText("99");
				} else {
					tv_msgnum.setText(msg_num + "");
				}
			} else {
				tv_msgnum.setVisibility(View.GONE);
			}
			break;
		}
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_home);
		ha = this;
	}

	@Override
	public void findViewById() {
		initView();
		initFragments();
		initTabAdapter();
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this); // 统计时长
		getMsgnum();
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void finish() {
		super.finish();
		FavorableFragment.sortid = 0;
		FavorableFragment.sourceid = 0;
		FavorableFragment.ageid = 0;
		ShowFragment.sortid = 0;
		ShowFragment.sourceid = 0;
		ShowFragment.ageid = 0;
		ExperienceFragment.sortid = 0;
		ExperienceFragment.sourceid = 0;
		ExperienceFragment.ageid = 0;
	}

	// 点击返回按钮
	@Override
	public void onBackPressed() {
		if (isLoadDialog != null) {
			isLoadDialog.show();
		} else {
			isLoadDialog = MTUtils.showDialog(HomeActivity.this,
					new OnClickListener() {
						public void onClick(View v) {
							switch (v.getId()) {
							case R.id.dialog_N:
								isLoadDialog.dismiss();
								break;
							case R.id.dialog_Y:
								finish();
								isLoadDialog.dismiss();
								break;
							}
						}
					}, "取消", "确定", "亲，确定要退出妈淘吗？");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.gc();
	}
}
