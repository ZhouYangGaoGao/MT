package com.matao.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.matao.adapter.HomeTypeAdapter;
import com.matao.bean.ArticleList;
import com.matao.bean.BabystateData;
import com.matao.bean.Bean;
import com.matao.bean.DaTePager;
import com.matao.bean.MyHouse;
import com.matao.matao.R;
import com.matao.pulltozoomview.PullToZoomListViewEx;
import com.matao.utils.Logger;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.utils.SendActtionTool;
import com.matao.utils.ServiceAction;
import com.matao.utils.URLs;
import com.matao.utils.UserAction;
import com.matao.view.RoundAngleImageView;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-27 下午4:08:52
 * @Description:他的小窝
 */

public class TaHouseActivity extends BaseActivity {
	@ViewInject(R.id.scroll_view)
	private PullToZoomListViewEx listView;
	@ViewInject(R.id.favorable_tip)
	TextView tip_doData;
	@ViewInject(R.id.favorable_bg)
	TextView bg;
	private TopBar topBar;
	private View foot;
//	private TextView footer;
	private ImageView icon, sex;
	private TextView name, babyname, fans;
	private TextView t1, t2, t3, t11, t22, t33, t1t, t2t, t3t, t11t, t22t,
			t33t;
	private ImageView i1, i2, i3, i1t, i2t, i3t;
	private List<ImageView> is = new ArrayList<ImageView>();
	private List<TextView> ts = new ArrayList<TextView>();
	private List<TextView> tss = new ArrayList<TextView>();
	// private int totalCount = 0;// 列表总条数
	// private int pagerCount = 0;// 列表总页数
	private String querytime = "2015-05-8";
	private int pageIndex = 1;
	private Bean bean;
	private List<ArticleList> list = new ArrayList<ArticleList>();
	private HomeTypeAdapter adapter;
	private int type = 1;
	private int Channel = 3;

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

	@Override
	public void onClick(View v) {
		reset();
		switch (v.getId()) {
		case R.id.ta_click2:
			checkTab(1);
			type = 1;
			Channel = 2;
			break;
		case R.id.ta_click1:
			checkTab(0);
			type = 1;
			Channel = 3;
			break;
		case R.id.ta_click3:
			checkTab(2);
			type = 2;
			Channel = 3;
			break;
		}
		loadList();
	}

	int OwnerUserId = 0;

	public void loadList() {
		String url = null;
		try {
			int CurrentUserId = MTApplication.getInt("UserId");
			if (OwnerUserId == 0) {
				OwnerUserId = CurrentUserId;
			}
			String time = URLEncoder.encode(querytime, "utf-8");
			url = MTUtils.getMTParams(URLs.SENDORLIKE, URLs.QUERY_TIME, time,
					"TypeId", type, "Channel", Channel, URLs.PAGE_INDEX,
					pageIndex, "LoginUserId",
					MTApplication.getInt(URLs.USER_ID), "OwnerUserId",
					OwnerUserId);
			Logger.i("经验晒单_url", url);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		isLoad = false;
		SendActtionTool.get(url, ServiceAction.Action_Comment,
				UserAction.Action_Favorable_list, this);
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		switch ((UserAction) action) {
		case Action_Myhouse:
			MyHouse house = JSON.parseObject(value.toString(), MyHouse.class);
			BabystateData data = house.getData();
			BitmapUtils utils = new BitmapUtils(this);
			utils.display(icon, house.getData().getAtaver());
			int sexid = house.getData().getBabyGender();
			if (sexid == 0) {
				sex.setImageResource(R.drawable.xw_ico_male);
			} else if (sexid == 1) {
				sex.setImageResource(R.drawable.xw_ico_female);
			} else {
				sex.setVisibility(View.GONE);
			}
			name.setText(house.getData().getNickName());

			if (!MTUtils.isEmpty(data.getBabyNickName())) {
				babyname.setText(data.getBabyNickName() + ","
						+ data.getBabyInfo() + "   " + data.getProvince() + " "
						+ data.getCity());
			} else {
				babyname.setText(data.getBabyInfo() + "   "
						+ data.getProvince() + " " + data.getCity());
			}
			fans.setText("粉丝：" + data.getFans() + "  /  关注："
					+ data.getFollows());
			t3.setText("" + data.getLikes());
			t3t.setText("" + data.getLikes());
			t1.setText("" + data.getSdanCounts());
			t1t.setText("" + data.getSdanCounts());
			t2.setText("" + data.getJyanCounts());
			t2t.setText("" + data.getJyanCounts());
			break;
		case Action_Favorable_list:
			bean = JSON.parseObject(value.toString(), Bean.class);
			if (bean != null) {
				List<ArticleList> tlist = new ArrayList<ArticleList>();
				tlist = bean.getData().getArticleList();
				switch ((UserAction) action) {
				case Action_Favorable_list:
					Logger.i("Action_Favorable_list", value.toString());
					if (tlist != null && tlist.size() != 0) {
						tip_doData.setVisibility(View.GONE);
						bg.setVisibility(View.GONE);
						list.addAll(tlist);
						isLoad = true;
					} else {
						isLoad = false;
						listView.getPullRootView().removeFooterView(foot);
					}
					break;
				}
				if (list.size() != 0) {
					adapter.notifyDataSetChanged();
				} else {
					bg.setVisibility(View.GONE);
					tip_doData.setVisibility(View.VISIBLE);
					if (pageIndex == 1) {
						adapter.notifyDataSetChanged();
					}
				}
				DaTePager dp = bean.getData().getDaTePager();
				// if (pageIndex == 1) {// 当列表为第一页的时候初始化下列属性
				// totalCount = dp.getTotalCount();
				// pagerCount = dp.getPageCount();
				// }
				querytime = dp.getQueryTime();
			}
			break;
		}
	}

	private void reset() {
		// pagerCount = 0;
		pageIndex = 1;
		list.clear();
		adapter.notifyDataSetChanged();
		// listView.getPullRootView().addFooterView(foot);
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_tahouse);
		ViewUtils.inject(this);
	}

	private void checkTab(int i) {
		for (int j = 0; j < 3; j++) {
			is.get(j).setImageBitmap(null);
			ts.get(j).setTextColor(0xff999999);
			tss.get(j).setTextColor(0xff999999);
			is.get(j + 3).setImageBitmap(null);
			ts.get(j + 3).setTextColor(0xff999999);
			tss.get(j + 3).setTextColor(0xff999999);
		}
		is.get(i).setImageResource(R.drawable.line_2_selected);
		ts.get(i).setTextColor(0xffff74bf);
		tss.get(i).setTextColor(0xffff74bf);
		is.get(i + 3).setImageResource(R.drawable.line_2_selected);
		ts.get(i + 3).setTextColor(0xffff74bf);
		tss.get(i + 3).setTextColor(0xffff74bf);
	}

	@Override
	public void findViewById() {

		OwnerUserId = getIntent().getIntExtra("OwnerUserId", 0);
		adapter = new HomeTypeAdapter(this, list);
		listView.setAdapter(adapter);
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
		// int mScreenHeight = localDisplayMetrics.heightPixels;
		int mScreenWidth = localDisplayMetrics.widthPixels;
		AbsListView.LayoutParams localObject = new AbsListView.LayoutParams(
				mScreenWidth, (int) (8.0F * (mScreenWidth / 16.0F)));
		final View heard = View.inflate(this, R.layout.ta_include2, null);
		ListView lv = listView.getPullRootView();
		lv.addHeaderView(heard);
		listView.setHeaderLayoutParams(localObject);
		final View top = findViewById(R.id.ta_top);
		// final int[] m = new int[2];
		// final int[] t = new int[2];
		bg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				loadList();
			}
		});
		icon = (RoundAngleImageView) findViewById(R.id.wdxw_icon_set);
		sex = (ImageView) findViewById(R.id.wdxw_sex);
		name = (TextView) findViewById(R.id.wdxw_name);
		babyname = (TextView) findViewById(R.id.wdxw_babyname);
		fans = (TextView) findViewById(R.id.wdxw_guanzhu);
		icon.setOnClickListener(this);
		foot = View.inflate(this, R.layout.fragment_bottom, null);
//		footer = (TextView) foot.findViewById(R.id.fragment_bottom_footer);
		lv.addFooterView(foot);
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
		t1 = (TextView) top.findViewById(R.id.t1);
		t2 = (TextView) top.findViewById(R.id.t2);
		t3 = (TextView) top.findViewById(R.id.t3);
		t11 = (TextView) top.findViewById(R.id.t11);
		t22 = (TextView) top.findViewById(R.id.t22);
		t33 = (TextView) top.findViewById(R.id.t33);
		i1 = (ImageView) top.findViewById(R.id.favorable_page1_icon);
		i2 = (ImageView) top.findViewById(R.id.favorable_page2_icon);
		i3 = (ImageView) top.findViewById(R.id.favorable_page3_icon);
		t1t = (TextView) heard.findViewById(R.id.t1);
		t2t = (TextView) heard.findViewById(R.id.t2);
		t3t = (TextView) heard.findViewById(R.id.t3);
		t11t = (TextView) heard.findViewById(R.id.t11);
		t22t = (TextView) heard.findViewById(R.id.t22);
		t33t = (TextView) heard.findViewById(R.id.t33);
		i1t = (ImageView) heard.findViewById(R.id.favorable_page1_icon);
		i2t = (ImageView) heard.findViewById(R.id.favorable_page2_icon);
		i3t = (ImageView) heard.findViewById(R.id.favorable_page3_icon);
		is.add(i1);
		is.add(i2);
		is.add(i3);
		ts.add(t1);
		ts.add(t2);
		ts.add(t3);
		tss.add(t11);
		tss.add(t22);
		tss.add(t33);
		is.add(i1t);
		is.add(i2t);
		is.add(i3t);
		ts.add(t1t);
		ts.add(t2t);
		ts.add(t3t);
		tss.add(t11t);
		tss.add(t22t);
		tss.add(t33t);
		listView.getPullRootView().setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem >= 1) {
					top.setVisibility(View.VISIBLE);
				} else {
					// Logger.i("top", "top");
					top.setVisibility(View.INVISIBLE);
				}
				if ((firstVisibleItem + visibleItemCount) == totalItemCount
						&& isLoad) {
					listView.getPullRootView().addFooterView(foot);
					Logger.i("foot", foot.getVisibility() == View.VISIBLE);
					if (foot.getVisibility() == View.VISIBLE) {

						pageIndex++;
						loadList();
					}
				}

			}
		});
		// listView.setonScrollListener(new onScrollListener1() {
		// // int x = 0;
		//
		// @Override
		// public void onScroll(boolean isLast) {
		// top.getLocationOnScreen(t);
		// heard.getLocationOnScreen(m);
		// Logger.i("m---t", m[1] + "---" + t[1]);
		// if (m[1] <= t[1]) {
		// if (top.getVisibility() != View.VISIBLE) {
		// top.setVisibility(View.VISIBLE);
		// }
		// } else {
		// if (top.getVisibility() != View.INVISIBLE) {
		// top.setVisibility(View.INVISIBLE);
		// }
		// }
		// // if (m[1] == x) {
		// // top.setVisibility(View.VISIBLE);
		// // }
		// // x = m[1];
		// if (isLast && isLoad) {
		// isLoad = false;
		// pageIndex++;
		// loadList();
		// }
		// }
		//
		// });
		loadData();
		loadList();
	}

	private boolean isLoad = false;

	private void loadData() {
		String url = null;
		String TimeStamp = MTUtils.getTimeStamp();
		int CurrentUserId = MTApplication.getInt("UserId");
		if (OwnerUserId == 0) {
			OwnerUserId = CurrentUserId;
		}
		url = MTUtils.getMTParams(URLs.MYHOUSE, URLs.CurrentUserId,
				CurrentUserId, URLs.OwnerUserId, OwnerUserId, "k", TimeStamp);
		Logger.i("Ta小窝_url", url);
		SendActtionTool.get(url, ServiceAction.Action_Comment,
				UserAction.Action_Myhouse, this);
	}

	@Override
	public void onFinish(ServiceAction service, Object action) {
		super.onFinish(service, action);
		if ((UserAction) action == UserAction.Action_Favorable_list) {
			listView.getPullRootView().removeFooterView(foot);
		}
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
		if ((UserAction) action == UserAction.Action_Favorable_list) {
			reset();
			tip_doData.setVisibility(View.GONE);
			bg.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		if ((UserAction) action == UserAction.Action_Favorable_list) {
			reset();
			tip_doData.setVisibility(View.GONE);
			bg.setVisibility(View.VISIBLE);
		}
	}
}
