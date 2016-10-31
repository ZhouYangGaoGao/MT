package com.matao.activity;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.matao.adapter.FPAdapter;
import com.matao.fragment.SearchPagerFragment;
import com.matao.matao.R;
import com.matao.utils.MTApplication;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-24 下午1:54:35
 * @Description:优惠fragment
 */

public class MyLikeActivity extends BaseActivity implements
		OnPageChangeListener, topBarClickListener {
	private TextView t1, t2;
	private ImageView i1, i2;
	private List<ImageView> is = new ArrayList<ImageView>();
	private List<TextView> ts = new ArrayList<TextView>();
	private TopBar topBar;
	public ViewPager pager;
	private FPAdapter myPagerAdapter;
	private List<Fragment> fragemList = new ArrayList<Fragment>();
	// private int priceType = 0;
	public static int topTabIndex = 0;

	private void tabClick(int i) {
		SearchPagerFragment fragment = (SearchPagerFragment) fragemList
				.get(topTabIndex);
		fragment.search();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.favorable_page2:
			tabClick(1);
			pager.setCurrentItem(1);
			break;
		case R.id.favorable_page1:
			tabClick(0);
			pager.setCurrentItem(0);
			break;
		}
	}

	// 切换tab改变priceType值重新加载数据
	public void tabChecked(int i) {
		switch (i) {
		case 0:
			topTabIndex = 0;
			// priceType = 0;
			break;
		case 1:
			topTabIndex = 1;
			// priceType = 11;
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
		for (int j = 0; j < 2; j++) {
			t = ts.get(j);
			im = is.get(j);
			im.setImageBitmap(null);
			t.setTextColor(0xff999999);
		}
		is.get(i).setImageResource(R.drawable.line_2_selected);
		ts.get(i).setTextColor(0xffff74bf);
	}

	// 初始化控件
	private void init() {
		topBar = (TopBar) findViewById(R.id.favorable_topbar);
		int i = getIntent().getIntExtra("Channel", 0);
		if (i == 0) {
			fragemList.add(new SearchPagerFragment(2, 2, MTApplication
					.getInt("UserId")));
			fragemList.add(new SearchPagerFragment(2, 3, MTApplication
					.getInt("UserId")));
		} else {
			if (i == 2) {
				topBar.setTitle("我的经验");
			} else {
				topBar.setTitle("我的晒单");
			}
			findViewById(R.id.isLike).setVisibility(View.GONE);
			fragemList.add(new SearchPagerFragment(1, i, MTApplication
					.getInt("UserId")));
		}
		topBar.setOnTopBarClickListener(this);
		pager = (ViewPager) findViewById(R.id.favorable_viewPager);
		t1 = (TextView) findViewById(R.id.favorable_page1);
		t2 = (TextView) findViewById(R.id.favorable_page2);
		i1 = (ImageView) findViewById(R.id.favorable_page1_icon);
		i2 = (ImageView) findViewById(R.id.favorable_page2_icon);

		t1.setOnClickListener(this);
		t2.setOnClickListener(this);
		is.add(i1);
		is.add(i2);
		ts.add(t1);
		ts.add(t2);
		myPagerAdapter = new FPAdapter(getSupportFragmentManager(), fragemList);
		pager.setAdapter(myPagerAdapter);
		pager.setOnPageChangeListener(this);
		pager.setOffscreenPageLimit(0);
	}

	@Override
	public void leftClick() {
		finish();
		this.overridePendingTransition(R.anim.slide_right_in,
				R.anim.slide_right_out);
	}

	@Override
	public void rightClick() {
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

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_my_like);
	}

	@Override
	public void findViewById() {
		init();
	}
}
