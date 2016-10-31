package com.matao.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.matao.activity.MyHouseActivity;
import com.matao.activity.SearchActivity;
import com.matao.adapter.FPAdapter;
import com.matao.adapter.SeasGridAdapter;
import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.view.MyGridView;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;
import com.umeng.analytics.MobclickAgent;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-24 下午1:54:35
 * @Description:优惠fragment
 */

public class FavorableFragment extends BaseFragment implements
		OnDismissListener, OnPageChangeListener, topBarClickListener {
	private TextView t1, t2, t3, t4, t5;
	private ImageView i1, i2, i3, i4, i5;
	private List<ImageView> is = new ArrayList<ImageView>();
	private List<TextView> ts = new ArrayList<TextView>();
	private TopBar topBar;
	public ViewPager pager;
	private FPAdapter myPagerAdapter;
	private List<Fragment> fragemList = new ArrayList<Fragment>();
	private MyGridView g1, g2, g3;
	private PopupWindow popupWindow;// 筛选浮层
	private int priceType = 0;
	public static int topTabIndex = 0;

	// private boolean[] isClickeds = { false, false, false, false };

	private void tabClick(int i) {
		SearchPagerFragment fragment = (SearchPagerFragment) fragemList
				.get(topTabIndex);
		fragment.search();
		// if (isClickeds[i]) {
		// isClickeds[i] = false;
		// } else {
		// isClickeds[i] = true;
		// }
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.favorable_shaixuan:
			tabChecked(4);
			break;
		case R.id.favorable_page3:
			tabClick(3);
			pager.setCurrentItem(3);
			break;
		case R.id.favorable_page22:
			tabClick(2);
			pager.setCurrentItem(2);
			break;
		case R.id.favorable_page2:
			tabClick(1);
			pager.setCurrentItem(1);
			break;
		case R.id.favorable_page1:
			tabClick(0);
			pager.setCurrentItem(0);
			break;
		case R.id.seas_screen_Y:
			doYesSelect();
			isSelect = true;
			popupWindow.dismiss();
			break;
		case R.id.seas_screen_N:
			popupWindow.dismiss();
			break;

		}
	}

	public static int sortid = 0;
	public static int ageid = 0;
	public static int sourceid = 0;
	private ImageView dot;
	private ImageView ico;

	private void doYesSelect() {
		if (sortid == 0 && ageid == 0 && sourceid == 0) {
			dot.setVisibility(View.INVISIBLE);
		} else {
			dot.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		Logger.i("favora--setUserVisibleHint", isVisibleToUser);
		if (isVisibleToUser) {
			// 相当于Fragment的onResume
		} else {
			// 相当于Fragment的onPause
		}
	}

	public static boolean isJump = true;

	@Override
	public void onResume() {
		super.onResume();
		Logger.i("favorable", "onResume()");
		if (isJump) {
			isJump = false;
			doYesSelect();
			pager.setCurrentItem(topTabIndex);
		}
		MobclickAgent.onPageStart("MainScreen"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

	// 切换tab改变priceType值重新加载数据
	public void tabChecked(int i) {
		switch (i) {
		case 0:
			topTabIndex = 0;
			priceType = 0;
			doSelect();
			break;
		case 1:
			topTabIndex = 1;
			priceType = 11;
			doSelect();
			break;
		case 2:
			topTabIndex = 2;
			priceType = 8;
			doSelect();
			break;
		case 3:
			topTabIndex = 3;
			priceType = 10;
			doSelect();
			break;
		case 4:
			showPop();
			break;
		}
		checkTab(i);
	}

	/**
	 * 切换tab
	 * 
	 * ZhouYang
	 * 
	 * 2015-5-14上午11:52:48
	 * 
	 */
	private void checkTab(int i) {
		TextView t;
		ImageView im;
		for (int j = 0; j < 5; j++) {
			t = ts.get(j);
			im = is.get(j);
			im.setImageBitmap(null);
			t.setTextColor(0xff999999);
		}
		if (i == 4) {
			is.get(i).setImageResource(R.drawable.ico_arrow_4);
		} else {
			is.get(i).setImageResource(R.drawable.line_2_selected);
		}
		ts.get(i).setTextColor(0xffff74bf);
	}

	// 根据当前选择重新加载数据
	private void doSelect() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
		}
	}

	// 初始化控件
	private void init(View v) {
		topBar = (TopBar) v.findViewById(R.id.favorable_topbar);
		topBar.setOnTopBarClickListener(this);
		ico = (ImageView) v.findViewById(R.id.favorable_page4_ico);
		dot = (ImageView) v.findViewById(R.id.favorable_page4_dot);
		pager = (ViewPager) v.findViewById(R.id.favorable_viewPager);
		t1 = (TextView) v.findViewById(R.id.favorable_page1);
		t2 = (TextView) v.findViewById(R.id.favorable_page2);
		t3 = (TextView) v.findViewById(R.id.favorable_page22);
		t4 = (TextView) v.findViewById(R.id.favorable_page3);
		t5 = (TextView) v.findViewById(R.id.favorable_page4);
		i1 = (ImageView) v.findViewById(R.id.favorable_page1_icon);
		i2 = (ImageView) v.findViewById(R.id.favorable_page2_icon);
		i3 = (ImageView) v.findViewById(R.id.favorable_page22_icon);
		i4 = (ImageView) v.findViewById(R.id.favorable_page3_icon);
		i5 = (ImageView) v.findViewById(R.id.favorable_page4_icon);
		fragemList.add(new SearchPagerFragment(1, 0));
		fragemList.add(new SearchPagerFragment(1, 11));
		fragemList.add(new SearchPagerFragment(1, 8));
		fragemList.add(new SearchPagerFragment(1, 10));
		t1.setOnClickListener(this);
		t2.setOnClickListener(this);
		t3.setOnClickListener(this);
		t4.setOnClickListener(this);
		v.findViewById(R.id.favorable_shaixuan).setOnClickListener(this);
		is.add(i1);
		is.add(i2);
		is.add(i3);
		is.add(i4);
		is.add(i5);
		ts.add(t1);
		ts.add(t2);
		ts.add(t3);
		ts.add(t4);
		ts.add(t5);

		myPagerAdapter = new FPAdapter(getChildFragmentManager(), fragemList);
		pager.setAdapter(myPagerAdapter);
		pager.setOnPageChangeListener(this);
		pager.setOffscreenPageLimit(0);
	}

	@Override
	public View setContentView(LayoutInflater inflater) {
		View v = inflater.inflate(R.layout.fragment_favorable_and_seas, null);
		return v;
	}

	@Override
	public void initView(View v) {
		init(v);
	}

	SeasGridAdapter gridAdapter1;
	SeasGridAdapter gridAdapter2;
	SeasGridAdapter gridAdapter3;

	// 浮层window
	private void showPop() {
		if (popupWindow == null) {
			final View v = LayoutInflater.from(getActivity()).inflate(
					R.layout.seas_screen, null);
			popupWindow = new PopupWindow(v, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT, true);
			v.findViewById(R.id.seas_top).setVisibility(View.GONE);
			popupWindow.setAnimationStyle(R.style.popwin_anim_style);
			g1 = (MyGridView) v.findViewById(R.id.seas_screen_grid1);
			g2 = (MyGridView) v.findViewById(R.id.seas_screen_grid2);
			g3 = (MyGridView) v.findViewById(R.id.seas_screen_grid0);
			g1.setFocusable(false);
			g2.setFocusable(false);
			g3.setFocusable(false);
			gridAdapter1 = new SeasGridAdapter(getActivity(),
					MTApplication.fenlei, 0, 1, sortid);
			gridAdapter2 = new SeasGridAdapter(getActivity(),
					MTApplication.ages, 1, 1, ageid);
			gridAdapter3 = new SeasGridAdapter(getActivity(),
					MTApplication.source, 2, 1, sourceid);
			g1.setAdapter(gridAdapter1);
			g2.setAdapter(gridAdapter2);
			g3.setAdapter(gridAdapter3);
			popupWindow.setFocusable(true); // 设置不允许在外点击消失
			popupWindow.setOutsideTouchable(true);
			popupWindow.setOnDismissListener(this);
			// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
			popupWindow.setBackgroundDrawable(new BitmapDrawable());
			v.findViewById(R.id.seas_screen_N).setOnClickListener(this);
			v.findViewById(R.id.seas_screen_Y).setOnClickListener(this);

		}
		if (popupWindow != null && !popupWindow.isShowing()) {
			// 设置好参数之后再show
			ico.setImageResource(R.drawable.ico_filter_selected);
			popupWindow.showAsDropDown(i5, 0, 0);
		}

	}

	// 用户是否选点击分类的确定
	private boolean isSelect = false;

	@Override
	public void onDismiss() {

		switch (priceType) {
		case 0:
			tabChecked(0);
			break;
		case 11:
			tabChecked(1);
			break;
		case 8:
			tabChecked(2);
			break;
		case 10:
			tabChecked(3);
			break;
		}
		ico.setImageResource(R.drawable.ico_filter_default);
		if (isSelect) {// 如果用户点击了确定按钮 则重新加载数据
			isSelect = false;
			SearchPagerFragment fragment = (SearchPagerFragment) fragemList
					.get(topTabIndex);
			fragment.search();
		}
		popupWindow = null;
	}

	@Override
	public void leftClick() {
		Intent intent = new Intent(getActivity(), SearchActivity.class);
		intent.putExtra("indext", 1);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);
	}

	@Override
	public void rightClick() {
		if (!MTApplication.isLogin) {// 没有登录跳转首页
			MTUtils.showLoginDialog(getActivity());
		} else {
			Intent in = new Intent(getActivity(), MyHouseActivity.class);
			startActivity(in);
			getActivity().overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int p) {
		tabChecked(p);
	}
}
