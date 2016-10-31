package com.matao.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.matao.activity.DetailsActivity;
import com.matao.activity.HomeActivity;
import com.matao.activity.MyHouseActivity;
import com.matao.activity.UpdateManager;
import com.matao.activity.XActivity;
import com.matao.activity.ZiLiaoActivity;
import com.matao.adapter.HomeTypeAdapter;
import com.matao.adapter.ViewPagerAdapter;
import com.matao.bean.ArticleList;
import com.matao.bean.BabyInfo;
import com.matao.bean.Bean;
import com.matao.bean.DaTePager;
import com.matao.bean.Data;
import com.matao.bean.HeadAdList;
import com.matao.bean.LoginUserInfo;
import com.matao.bean.WarningBean;
import com.matao.bean.WarningData;
import com.matao.matao.R;
import com.matao.pulltorefresh.library.PullToRefreshBase;
import com.matao.pulltorefresh.library.PullToRefreshListView;
import com.matao.pulltorefresh.library.PullToRefreshBase.Mode;
import com.matao.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.matao.utils.Logger;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.utils.SendActtionTool;
import com.matao.utils.ServiceAction;
import com.matao.utils.URLs;
import com.matao.utils.UserAction;
import com.matao.view.AutoScrollViewPager;
import com.matao.view.Gif;
import com.matao.view.RoundAngleImageView;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;
import com.umeng.analytics.MobclickAgent;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-24 下午1:54:35
 * @Description:首页fragment fragment全部继承BaseFragment
 */

public class HomeFragment extends BaseFragment implements OnPageChangeListener,
		OnRefreshListener2<ListView>, topBarClickListener, OnItemClickListener {
	private HomeActivity ha;
	@ViewInject(R.id.favorable_tip)
	private TextView tip_doData;
	@ViewInject(R.id.favorable_bg)
	private TextView bg;
	private TextView hot_recommend;
	private RelativeLayout pageLayout;
	@ViewInject(R.id.favorable_top)
	private ImageView top;
	private View foot;
	private AnimationDrawable aniDraw;
	@ViewInject(R.id.anim)
	private TextView aini;
	private PullToRefreshListView listView;// 列表
	private AutoScrollViewPager pager;
	private LinearLayout dotlayout;// 广告位置提示标布局
	private Bean bean;// 数据bean
	private BabyInfo baby;
	private List<ArticleList> list = new ArrayList<ArticleList>();// 列表数据集合
	private List<HeadAdList> adLists = new ArrayList<HeadAdList>();// 广告数据集合
	private List<ImageView> imgs = new ArrayList<ImageView>();// 广告展示内容
	private List<HeadAdList> mids = new ArrayList<HeadAdList>();
	private ViewPagerAdapter pagerAdapter;// 广告展示适配器
	private LinearLayout dengluLayout, infolayout, buqulayout;// 登录状态广告下方布局 n部曲
	private HomeTypeAdapter adapter;// 列表适配器
	private int pageIndex = 1;// 列表页码
	private String querytime = "2015-05-6";// 时间戳
	private TopBar bar;
	private ImageView ad0, ad1, ad2, ad3, ad4, ad5, ad6, ad7;
	private RoundAngleImageView icon;
	private TextView tip1, tip2, tip3, tip4;
	// private Dialog dialog;
	public static int age = 0;

	// 点击事件
	@OnClick({ R.id.favorable_bg, R.id.favorable_top })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tip2_layout:
			// 登录情况下
			if (MTApplication.isLogin == true) {
				if (baby.getBabyAgeScope() == 7) {
					Intent intent = new Intent(ha, DetailsActivity.class);
					intent.putExtra("url",
							"http://m.matao.com/app/detail-30390/");
					startActivity(intent);
				} else if (baby.getBabyAgeScope() == 0) {
					Intent intent = new Intent(ha, DetailsActivity.class);
					intent.putExtra("url",
							"http://m.matao.com/app/detail-3250172/");
					startActivity(intent);
				} else {
					Intent intent = new Intent(ha, XActivity.class);
					intent.putExtra("age", baby.getBabyAgeScope());
					startActivity(intent);
				}
				ha.overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_left_out);
			} else {// 未登录情况下
				if (BabyAgeScope == 7) {
					Intent intent = new Intent(ha, DetailsActivity.class);
					intent.putExtra("url",
							"http://m.matao.com/app/detail-30390/");
					startActivity(intent);
				} else if (BabyAgeScope == 0) {
					Intent intent = new Intent(ha, DetailsActivity.class);
					intent.putExtra("url",
							"http://m.matao.com/app/detail-3250172/");
					startActivity(intent);
				} else {
					Intent intent = new Intent(ha, XActivity.class);
					intent.putExtra("age", BabyAgeScope);
					startActivity(intent);
				}
				ha.overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_left_out);
			}
			break;
		// 点击进去小窝
		case R.id.home_icon:
			if (MTApplication.isLogin) {
				Intent in = new Intent(getActivity(), MyHouseActivity.class);
				startActivity(in);
				ha.overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_left_out);
			} else {
				MTUtils.showLoginDialog(getActivity());
			}
			break;
		case R.id.tip1_layout:
			if (MTApplication.isLogin) {
				Intent in = new Intent(getActivity(), MyHouseActivity.class);
				startActivity(in);
				ha.overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_left_out);
			} else {
				MTUtils.showLoginDialog(getActivity());
			}
			break;
		// 点击重新加载背景重新加载数据
		case R.id.favorable_bg:
			animaostart();
			loadData();
			break;
		// 点击返回顶部
		case R.id.favorable_top:
			Logger.i("topClick", "返回顶部");
			top.setVisibility(View.GONE);
			listView.getRefreshableView().setSelection(0);
			break;
		}
		// 根据获得的不同的类型ID中部添加不同的内容
		if (mids != null && mids.size() >= 8) {
			switch (v.getId()) {
			case R.id.home_middle_0:
				linkTo(mids.get(0));
				break;
			case R.id.home_middle_1:
				linkTo(mids.get(1));
				break;
			case R.id.home_middle_2:
				linkTo(mids.get(2));
				break;
			case R.id.home_middle_3:
				linkTo(mids.get(3));
				break;
			case R.id.home_middle_4:
				linkTo(mids.get(4));
				break;
			case R.id.home_middle_5:
				linkTo(mids.get(5));
				break;
			case R.id.home_middle_6:
				linkTo(mids.get(6));
				break;
			case R.id.home_middle_7:
				linkTo(mids.get(7));
				break;
			}
		}
	}

	// 初始化中间提示区域
	private void initMiddle(Data data) {
		LoginUserInfo info = data.getLoginUserInfo();
		baby = data.getBabyInfo();
		age = baby.getBabyAgeScope();
		// 当登陆成功后将登录时获得的宝宝信息替换存贮在手机中
		MTApplication.mEditor.putString("BabyBirth", baby.getBirthday());
		switch (baby.getGender()) {
		case 0:
			MTApplication.mEditor.putString("BabySex", "男宝宝");
			break;
		case 1:
			MTApplication.mEditor.putString("BabySex", "女宝宝");
			break;
		}
		MTApplication.mEditor.putString("BabyAgeD", baby.getBabyInfo());
		MTApplication.mEditor.putInt("BabyAgeScope", age);
		if (age == 0) {
			MTApplication.mEditor.putInt("BabyState", 2);
		} else if (age == 1) {
			MTApplication.mEditor.putInt("BabyState", 1);
		} else {
			MTApplication.mEditor.putInt("BabyState", 0);
		}
		MTApplication.mEditor.putString("BabyState2", "yes");
		MTApplication.mEditor.commit();
		MTApplication.bmu.display(icon, info.getAvatar());
		icon.setOnClickListener(this);
		switch (baby.getBabyAgeScope()) {
		case 0:
			tip1.setText(info.getNickName());
			tip2.setText("努力造人中...");
			tip3.setText("准备造人");
			tip4.setText("成功好“孕”必备神器");
			hot_recommend.setText("备孕热点推荐");
			break;
		case 1:
			tip1.setText(info.getNickName());
			tip2.setText(baby.getBabyInfo() + "咯");
			tip3.setText("孕期ing");
			tip4.setText("宝妈囤货十步曲");
			hot_recommend.setText("孕期热点推荐");
			break;
		case 2:
			tip1.setText(info.getNickName());
			tip2.setText("宝宝" + baby.getBabyInfo() + "咯");
			tip3.setText("0-3个月");
			tip4.setText("宝宝囤货九步曲");
			hot_recommend.setText("0-3个月热点推荐");
			break;
		case 3:
			tip1.setText(info.getNickName());
			tip2.setText("宝宝" + baby.getBabyInfo() + "咯");
			tip3.setText("3-6个月");
			tip4.setText("宝宝囤货五步曲");
			hot_recommend.setText("3-6个月热点推荐");
			break;
		case 4:
			tip1.setText(info.getNickName());
			tip2.setText("宝宝" + baby.getBabyInfo() + "咯");
			tip3.setText("6-12个月");
			tip4.setText("宝宝囤货七步曲");
			hot_recommend.setText("6-12个月热点推荐");
			break;
		case 5:
			tip1.setText(info.getNickName());
			tip2.setText("宝宝" + baby.getBabyInfo() + "咯");
			tip3.setText("1-3岁");
			tip4.setText("宝宝囤货七步曲");
			hot_recommend.setText("1-3岁热点推荐");
			break;
		case 6:
			tip1.setText(info.getNickName());
			tip2.setText("宝宝" + baby.getBabyInfo() + "咯");
			tip3.setText("3-6岁");
			tip4.setText("宝宝囤货四步曲");
			hot_recommend.setText("3-6岁热点推荐");
			break;
		case 7:
			tip1.setText(info.getNickName());
			tip2.setText("宝宝" + baby.getBabyInfo() + "咯");
			tip3.setText("6岁以上");
			tip4.setText("儿童必备学习用品");
			hot_recommend.setText("6岁以上热点推荐");
			break;
		}
	}

	// 初始化控件
	private void init(View v) {
		ha = (HomeActivity) getActivity();
		aniDraw = (AnimationDrawable) aini.getBackground();
		listView = (PullToRefreshListView) v.findViewById(R.id.home_listview);
		bar = (TopBar) v.findViewById(R.id.home_topbar);
		bar.setOnTopBarClickListener(this);
		foot = View.inflate(getActivity(), R.layout.fragment_bottom, null);
		// footer = (TextView) foot.findViewById(R.id.fragment_bottom_footer);
		// listView.getRefreshableView().addFooterView(foot);
	}

	// 添加广告图片
	private void initImgs() {
		imgs.clear();
		ImageView img;
		for (int i = 0; i < 5 * adLists.size(); i++) {
			img = new ImageView(ha);
			MTApplication.bmu.display(img, adLists.get(i % adLists.size())
					.getImgUrl());
			img.setScaleType(ScaleType.FIT_XY);
			final int x = i;
			img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					linkTo(adLists.get(x % adLists.size()));
				}
			});
			imgs.add(img);
		}
		pager.startAutoScroll();
	}

	private void linkTo(HeadAdList h) {
		HomeActivity home = (HomeActivity) getActivity();
		home.linkTo(h);
	}

	// 初始化广告幻灯片
	private void initViewPager() {
		pagerAdapter = new ViewPagerAdapter(imgs);
		pager.setAdapter(pagerAdapter);
		pager.setOffscreenPageLimit(5);
		pager.setOnPageChangeListener(this);
		pager.setInterval(4000);// 设置播放间隔时间
		pager.setAutoScrollDurationFactor(10);
		pager.setCycle(false);// 设置是否循环

	}

	// 初始化pager标记原点
	private void initDot() {
		if (imgs.size() / 5 < 2) {
			pager.stopAutoScroll();
			dotlayout.setVisibility(View.GONE);
		} else {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					(int) (MTUtils.getScreenWidth(ha) * 0.021),
					(int) (MTUtils.getScreenWidth(ha) * 0.021));
			lp.setMargins(10, 0, 0, 0);
			ImageView imgdot;
			for (int i = 0; i < imgs.size() / 5; i++) {
				imgdot = new ImageView(ha);
				imgdot.setImageResource(R.drawable.ico_float);
				imgdot.setLayoutParams(lp);
				dotlayout.addView(imgdot);
			}
			imgdot = (ImageView) dotlayout.getChildAt(0);
			imgdot.setImageResource(R.drawable.ico_float_red);
		}
	}

	// 改变pager点的指示位置，
	private void dot(int n) {
		ImageView imag;
		for (int i = 0; i < imgs.size() / 5; i++) {
			imag = (ImageView) dotlayout.getChildAt(i);
			if (imag != null) {
				imag.setImageResource(R.drawable.ico_float);
			}
		}
		imag = (ImageView) dotlayout.getChildAt(n);
		if (imag != null) {
			imag.setImageResource(R.drawable.ico_float_red);
		}
	}

	View header;

	// 初始化列表
	private void initListView() {
		ListView lv = listView.getRefreshableView();
		header = LayoutInflater.from(ha).inflate(R.layout.fragmengt_home_top,
				null);
		hot_recommend = (TextView) header.findViewById(R.id.home_hot_recommend);
		ad0 = (ImageView) header.findViewById(R.id.home_middle_0);
		ad1 = (ImageView) header.findViewById(R.id.home_middle_1);
		ad2 = (ImageView) header.findViewById(R.id.home_middle_2);
		ad3 = (ImageView) header.findViewById(R.id.home_middle_3);
		ad4 = (ImageView) header.findViewById(R.id.home_middle_4);
		ad5 = (ImageView) header.findViewById(R.id.home_middle_5);
		ad6 = (ImageView) header.findViewById(R.id.home_middle_6);
		ad7 = (ImageView) header.findViewById(R.id.home_middle_7);
		ad0.setOnClickListener(this);
		ad1.setOnClickListener(this);
		ad2.setOnClickListener(this);
		ad3.setOnClickListener(this);
		ad4.setOnClickListener(this);
		ad5.setOnClickListener(this);
		ad6.setOnClickListener(this);
		ad7.setOnClickListener(this);
		dengluLayout = (LinearLayout) header
				.findViewById(R.id.home_linearLayout_tip);
		dengluLayout.setOnClickListener(this);
		infolayout = (LinearLayout) header.findViewById(R.id.tip1_layout);
		infolayout.setOnClickListener(this);
		buqulayout = (LinearLayout) header.findViewById(R.id.tip2_layout);
		buqulayout.setOnClickListener(this);
		icon = (RoundAngleImageView) dengluLayout.findViewById(R.id.home_icon);
		tip1 = (TextView) dengluLayout.findViewById(R.id.home_tip1);
		tip2 = (TextView) dengluLayout.findViewById(R.id.home_tip2);
		tip3 = (TextView) dengluLayout.findViewById(R.id.home_tip3);
		tip4 = (TextView) dengluLayout.findViewById(R.id.home_tip4);
		pager = new AutoScrollViewPager(ha);
		pager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				(int) (MTUtils.getScreenWidth(ha) * 0.31)));
		pageLayout = (RelativeLayout) header.findViewById(R.id.home_pagelayout);
		pageLayout.addView(pager);
		lv.addHeaderView(header);
		dotlayout = (LinearLayout) header.findViewById(R.id.home_pagerDot);
		initViewPager();
		listView.setMode(Mode.BOTH);
		listView.setOnItemClickListener(this);
		listView.setOnRefreshListener(this);
		adapter = new HomeTypeAdapter(ha, list);
		// 设置在listview滑动时不加载图片，停止滑动开始加载图片
		listView.setOnScrollListener(new PauseOnScrollListener(
				MTApplication.bmu, false, true));
		listView.setAdapter(adapter);
	}

	// 屏幕适配
	private void lp() {
		Gif gif = (Gif) header.findViewById(R.id.hometop_imgs_layoutbg);
		gif.setMovieResource(R.drawable.bggif);
		LinearLayout layout = (LinearLayout) header
				.findViewById(R.id.hometop_imgs_layout);
		// 设置中间图片广告的高度 自动根据屏幕分辨率适配
		RelativeLayout.LayoutParams lp1 = (android.widget.RelativeLayout.LayoutParams) layout
				.getLayoutParams();
		lp1.height = (int) (MTUtils.getScreenWidth(ha) * 0.812);
		layout.setLayoutParams(lp1);
		gif.setLayoutParams(lp1);
		LinearLayout layout2 = (LinearLayout) header
				.findViewById(R.id.hometop_imgs_layout2);
		RelativeLayout.LayoutParams lp2 = (android.widget.RelativeLayout.LayoutParams) layout2
				.getLayoutParams();
		lp2.height = (int) (MTUtils.getScreenWidth(ha) * 0.2223);
		layout2.setLayoutParams(lp2);
	}

	// 添加中间图片广告
	private void initMiddleAd(List<HeadAdList> ad) {
		MTApplication.bmu.display(ad0, ad.get(0).getImgUrl());
		MTApplication.bmu.display(ad1, ad.get(1).getImgUrl());
		MTApplication.bmu.display(ad2, ad.get(2).getImgUrl());
		MTApplication.bmu.display(ad3, ad.get(3).getImgUrl());
		MTApplication.bmu.display(ad4, ad.get(4).getImgUrl());
		MTApplication.bmu.display(ad5, ad.get(5).getImgUrl());
		MTApplication.bmu.display(ad6, ad.get(6).getImgUrl());
		MTApplication.bmu.display(ad7, ad.get(7).getImgUrl());
		lp();

	}

	// 发送请求加载数据
	public void loadData() {
		if (MTUtils.isNetworkConnected(getActivity())) {
			if (isCanLoad) {
				isCanLoad = false;
				String url = null;
				try {
					String querytime1 = URLEncoder.encode(querytime, "utf-8");
					url = MTUtils.getMTParams(URLs.base_url, URLs.USER_ID,
							MTApplication.getInt("UserId"), URLs.PAGE_INDEX,
							pageIndex, "BabyAgeScope", BabyAgeScope,
							URLs.QUERY_TIME, querytime1);
					Logger.i("url--home", url);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// 发送get请求
				SendActtionTool.get(url, ServiceAction.Action_Comment,
						UserAction.Action_HOME, this);
			}
		} else {
			animstop();
			reset();
			tip_doData.setVisibility(View.GONE);
			// footer.setVisibility(View.GONE);
			listView.onRefreshComplete();
			listView.setVisibility(View.INVISIBLE);
			bg.setVisibility(View.VISIBLE);
			MTUtils.netTip(getActivity());
		}
	}

	private boolean isInit = false;

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		switch ((UserAction) action) {
		// 加载首页
		case Action_HOME:
			if (value != null) {
				bean = JSON.parseObject(value.toString(), Bean.class);// 获得数据实体，使用alibaba的fastJson解析
				List<ArticleList> tlist = bean.getData().getArticleList();
				// 中间广告图片数据
				mids = bean.getData().getMiddleAdList();
				dotlayout.removeAllViews();
				adLists = bean.getData().getHeadAdList();
				if (mids != null && mids.size() != 0 && adLists != null
						&& adLists.size() != 0) {
					initImgs();
					initDot();
					if (!isInit) {
						initMiddleAd(mids);
						isInit = true;
					}
					Logger.i("中间广告图片", mids);
				}
				if (MTApplication.isLogin) {// 登录情况下展示十步曲提示
					initMiddle(bean.getData());
				} else {// 未登录情况下根据逛一逛填写信息展示十步曲提示
					initMiddle2();
				}
				if (tlist != null && tlist.size() != 0) {
					list.addAll(tlist);
				}
				pagerAdapter.notifyDataSetChanged();
				adapter.notifyDataSetChanged();
				DaTePager dp = bean.getData().getDaTePager();
				// if (pageIndex == 1) {// 当列表为第一页的时候初始化下列属性
				// totalCount = dp.getTotalCount();
				// pagerCount = dp.getPageCount();
				// }
				querytime = dp.getQueryTime();
			}
			animstop();
			break;
		case Action_UpdaeVersion:
			Bean bean = JSON.parseObject(value.toString(), Bean.class);
			Data data = bean.getData();
			if (data.getVersionInfo().isIsLastVersion()) {
				// MTUtils.Toast(ha, "你当前已是最新版本");
			} else {
				new UpdateManager(ha, data.getVersionInfo().getUpgradeUrl(),
						data.getVersionInfo().isIsForceUpgrade(), data
								.getVersionInfo().getVersionLog());
			}
			break;
		case Action_WARNING:
			WarningBean wbean = JSON.parseObject(value.toString(),
					WarningBean.class);
			WarningData wdata = wbean.getData();
			Logger.i("warning---------------------->", value.toString());
			// 取出时间戳
			String time1 = MTApplication.getString("isWarning");
			Logger.i("time-=-====================", time1);
			if (MTApplication.isLogin == true) {
				if (!MTUtils.isEmpty(time1)) {
					String time2 = time1.substring(0, 7);
					String time3 = MTUtils.getTimeStamp();
					String time4 = time3.substring(0, 7);
					if (!time2.equals(time4)) {
						if (wdata.isBabyBorn() == true) {
							waringDialog();
						}
					}
				} else {
					if (wdata.isBabyBorn() == true) {
						waringDialog();
					}
				}
			}
			break;
		}
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
		switch ((UserAction) action) {
		case Action_HOME:
			Logger.e("onException", "Action_HOME");
			animstop();
			reset();
			// bg.setVisibility(View.VISIBLE);
			break;
		case Action_WARNING:

			break;
		}
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		switch ((UserAction) action) {
		case Action_HOME:
			Logger.e("onFaile", "Action_HOME");
			animstop();
			reset();
			bg.setVisibility(View.VISIBLE);
			break;
		case Action_UpdaeVersion:
			org.json.JSONObject object;
			try {
				object = new org.json.JSONObject(value.toString());
				String str = object.optString("Msg", "");
				MTUtils.Toast(getActivity(), str);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	// 最先调用 之后调用initView(View v)
	@Override
	public View setContentView(LayoutInflater inflater) {
		View v = inflater.inflate(R.layout.fragment_home, null);
		ViewUtils.inject(this, v);
		return v;
	}

	// 初始化
	@Override
	public void initView(View v) {
		init(v);
		warning();
		initListView();
		animaostart();
		getVersion();
		loadData();
	}

	// 获取版本名称
	private void getVersion() {
		String versionNumber = MTApplication.mVersionName;
		SendActtionTool
				.get(URLs.GETVERSION + versionNumber,
						ServiceAction.Action_User,
						UserAction.Action_UpdaeVersion, this);
	}

	// 预产期提醒请求
	public void warning() {
		String url = MTUtils.getMTParams(URLs.WARING, URLs.USER_ID,
				MTApplication.getInt("UserId"));
		// 发送get请求
		SendActtionTool.get(url, ServiceAction.Action_Comment,
				UserAction.Action_WARNING, this);
	}

	// 此界面可见时广告位滚动，不可见时停止滚动
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		Logger.i("HomePager---onHiddenChanged", "isHidden?" + hidden);
		if (hidden) {
			pager.stopAutoScroll();
		} else {
			pager.startAutoScroll();
		}
	}

	@Override
	public void onResume() {
		Logger.i("homeonResume", "homeonResume");
		if (isJump) {
			isJump = false;
			hot_recommend.setText("热点推荐");
			loadData();
		}
		super.onResume();
		MobclickAgent.onPageStart("MainScreen"); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

	private boolean isCanLoad = true;

	@Override
	public void onFinish(ServiceAction service, Object action) {
		super.onFinish(service, action);
		listView.getRefreshableView().removeFooterView(foot);
		isCanLoad = true;
	}

	/**
	 * 重置属性值
	 */
	private void reset() {
		// pagerCount = 0;
		pageIndex = 0;
		list.clear();
		pageIndex = 1;
		adapter.notifyDataSetChanged();
	}

	// 轮播广告位滚动事件
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int i) {
		dot(i % adLists.size());// 切换指示图标
		if (i == imgs.size() - 1) {
			pager.setCurrentItem(0, false);
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		reset();
		loadData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		pageIndex += 1;
		listView.getRefreshableView().addFooterView(foot);
		// footer.setVisibility(View.GONE);
		loadData();
	}

	// 启动动画
	private void animaostart() {
		bg.setVisibility(View.GONE);
		tip_doData.setVisibility(View.GONE);
		if (aniDraw != null) {
			aniDraw.start();
			aini.setVisibility(View.VISIBLE);
			listView.setVisibility(View.INVISIBLE);
		}
	}

	// 暂停动画
	private void animstop() {
		adapter.notifyDataSetChanged();
		bg.setVisibility(View.GONE);
		listView.onRefreshComplete();
		listView.setVisibility(View.VISIBLE);
		if (aniDraw != null) {
			aniDraw.stop();
			aini.setVisibility(View.INVISIBLE);
		}
		// adapter.isAnimtion = true;
	}

	// 预产期弹窗
	private void waringDialog() {
		final Dialog d = new Dialog(ha, R.style.MmsDialogTheme);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 存入时间戳
		MTApplication.mEditor.putString("isWarning", MTUtils.getTimeStamp());
		MTApplication.mEditor.commit();

		View v = LayoutInflater.from(ha)
				.inflate(R.layout.dialog_yuchanqi, null);
		v.findViewById(R.id.btn_close).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						d.dismiss();
					}
				});
		v.findViewById(R.id.btn_go).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ZiLiaoActivity.class);
				startActivity(intent);
				d.dismiss();
			}
		});
		d.setContentView(v);
		WindowManager.LayoutParams lp = d.getWindow().getAttributes();
		Display dsp = ha.getWindowManager().getDefaultDisplay(); // 获取屏幕宽、高用
		lp.width = (int) (dsp.getWidth() * 0.75); // 高度设置为屏幕的0.7
		d.getWindow().setAttributes(lp);
		d.show();

	}

	@Override
	public void leftClick() {

	}

	@Override
	public void rightClick() {
		if (!MTApplication.isLogin) {// 没有登录跳转登录页
			MTUtils.showLoginDialog(getActivity());
		} else {// 已经登录跳转我的小窝
			Intent in = new Intent(getActivity(), MyHouseActivity.class);
			startActivity(in);
			getActivity().overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);

		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	// private boolean scrollFlag = false;// 标记是否滑动
	// private int lastVisibleItemPosition;// 标记上次滑动位置

	// @Override
	// public void onScrollStateChanged(AbsListView view, int scrollState) {
	// if (scrollState == OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
	// scrollFlag = true;
	// } else {
	// scrollFlag = false;
	// }
	// }

	// @Override
	// public void onScroll(AbsListView view, int firstVisibleItem,
	// int visibleItemCount, int totalItemCount) {
	// Logger.i("onScroll", "firstVisibleItem:" + firstVisibleItem
	// + "--visibleItemCount:" + visibleItemCount
	// + "--totalItemCount:" + totalItemCount);
	// if (scrollFlag) {
	// if (firstVisibleItem > lastVisibleItemPosition) {
	// Logger.i("dc", "上滑");
	// top.setVisibility(View.GONE);
	// }
	// if (firstVisibleItem < lastVisibleItemPosition) {
	// Logger.i("dc", "下滑");
	// top.setVisibility(View.VISIBLE);
	//
	// }
	// if (firstVisibleItem == lastVisibleItemPosition) {
	// return;
	// }
	// lastVisibleItemPosition = firstVisibleItem;
	// }
	// }
	// 未登录情况下初始化中间区域
	private String BabyAgeD;
	private int BabyAgeScope;

	private void initMiddle2() {
		BabyAgeD = MTApplication.getString("BabyAgeD");
		BabyAgeScope = MTApplication.getInt("BabyAgeScope");
		icon.setImageResource(R.drawable.pic_14);
		icon.setOnClickListener(this);
		switch (BabyAgeScope) {
		case 0:
			tip1.setText("未登录");
			tip2.setText("努力造人中...");
			tip3.setText("准备造人");
			tip4.setText("成功好“孕”必备神器");
			hot_recommend.setText("备孕热点推荐");
			break;
		case 1:
			tip1.setText("未登录");
			tip2.setText(BabyAgeD + "咯");
			tip3.setText("孕期ing");
			tip4.setText("宝妈囤货十步曲");
			hot_recommend.setText("孕期热点推荐");
			break;
		case 2:
			tip1.setText("未登录");
			tip2.setText("宝宝" + BabyAgeD + "咯");
			tip3.setText("0-3个月");
			tip4.setText("宝宝囤货九步曲");
			hot_recommend.setText("0-3个月热点推荐");
			break;
		case 3:
			tip1.setText("未登录");
			tip2.setText("宝宝" + BabyAgeD + "咯");
			tip3.setText("3-6个月");
			tip4.setText("宝宝囤货五步曲");
			hot_recommend.setText("3-6个月热点推荐");
			break;
		case 4:
			tip1.setText("未登录");
			tip2.setText("宝宝" + BabyAgeD + "咯");
			tip3.setText("6-12个月");
			tip4.setText("宝宝囤货七步曲");
			hot_recommend.setText("6-12个月热点推荐");
			break;
		case 5:
			tip1.setText("未登录");
			tip2.setText("宝宝" + BabyAgeD + "咯");
			tip3.setText("1-3岁");
			tip4.setText("宝宝囤货七步曲");
			hot_recommend.setText("1-3岁热点推荐");
			break;
		case 6:
			tip1.setText("未登录");
			tip2.setText("宝宝" + BabyAgeD + "咯");
			tip3.setText("3-6岁");
			tip4.setText("宝宝囤货四步曲");
			hot_recommend.setText("3-6岁热点推荐");
			break;
		case 7:
			tip1.setText("未登录");
			tip2.setText("宝宝" + BabyAgeD + "咯");
			tip3.setText("6岁以上");
			tip4.setText("儿童必备学习用品");
			hot_recommend.setText("6岁以上热点推荐");
			break;
		}
	}
}
