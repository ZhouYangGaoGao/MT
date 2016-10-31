package com.matao.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.matao.adapter.CommonAdapter;
import com.matao.adapter.ViewPagerAdapter;
import com.matao.bean.Bean;
import com.matao.bean.Data;
import com.matao.bean.GoodsList;
import com.matao.bean.HeadAdList;
import com.matao.matao.R;
import com.matao.pulltorefresh.library.PullToRefreshBase;
import com.matao.pulltorefresh.library.PullToRefreshScrollView;
import com.matao.pulltorefresh.library.PullToRefreshBase.Mode;
import com.matao.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.matao.utils.MD5Util;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.utils.SendActtionTool;
import com.matao.utils.ServiceAction;
import com.matao.utils.URLs;
import com.matao.utils.UserAction;
import com.matao.utils.ViewHolder;
import com.matao.view.AutoScrollViewPager;
import com.matao.view.NoSorceGridView;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-8-3 上午11:59:01
 * @Description: 积分兑换页面
 * 
 */
public class ExchangeActivity extends BaseActivity implements
		OnRefreshListener2<ScrollView>, OnPageChangeListener {
	@ViewInject(R.id.exchange_scrollview)
	private PullToRefreshScrollView scrollView;
	@ViewInject(R.id.exchange_grideview)
	private NoSorceGridView gridView;
	@ViewInject(R.id.exchange_pagelayout)
	private AutoScrollViewPager viewPager;
	@ViewInject(R.id.exchange_pagerDot)
	private LinearLayout dot_layout;// 广告位置提示标布局
	@ViewInject(R.id.TB_num)
	private TextView tv_Tb;
	private TextView foot;

	private ViewPagerAdapter pagerAdapter;// 广告展示适配器
	private ExchangeAdapter adapter;// 列表适配器
	private List<ImageView> imgs = new ArrayList<ImageView>();// 广告展示内容
	private List<HeadAdList> adLists = new ArrayList<HeadAdList>();// 广告数据集合
	private List<GoodsList> goodsLists = new ArrayList<GoodsList>();// 广告数据集合
	private int pageIndex = 1;// 列表页码
	private int goodsId, coins;
	private String querytime = "2015-05-6";// 时间戳
	private boolean flag = true;

	@OnClick({ R.id.TB_layout, R.id.btn_hqtb })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.TB_layout:

			break;
		case R.id.btn_hqtb:
			Intent intent = new Intent(this, GetTBActivity.class);
			startActivity(intent);
			break;
		}
	}

	// 设置标志位防止重复加载数据
	private boolean isCanLoad = true;

	// 请求接口加载数据
	public void loadData() {
//		if (isCanLoad) {
//			isCanLoad = false;
			String url = null;
			try {
				int UserId = MTApplication.getInt("UserId");
				String querytime1 = URLEncoder.encode(querytime, "utf-8");
				url = MTUtils.getMTParams(URLs.GetGoodsList, "UserId", UserId,
						"Querytime", querytime1, URLs.PAGE_INDEX, pageIndex);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Log.i("exchangeurl-----------------", url);
			// 发送get请求
			SendActtionTool.get(url, ServiceAction.Action_Comment,
					UserAction.Action_GetGoodsList, this);
//		} else {
//			reset();
//			scrollView.onRefreshComplete();
//		}
	}

	// 判断是否符合兑换条件
	private void IsBought(int GoodsId,int Coin) {
		int UserId = MTApplication.getInt("UserId");
		String UserToken = MTApplication.getString("Token");
		String TimeStamp = MTUtils.getTimeStamp();
		org.json.JSONObject json = new org.json.JSONObject();
		try {
			json.put(
					"Sign",
					MD5Util.getLowerCaseMD5(goodsId + "" + coins + ""
							+ UserToken + TimeStamp + URLs.KEY));
			json.put("UserId", UserId);
			json.put("GoodsId", goodsId);
			json.put("Coin", coins);
			json.put("UserToken", UserToken);
			json.put("TimeStamp", TimeStamp);
		} catch (org.json.JSONException e) {
			e.printStackTrace();
		}
		RequestParams p = new RequestParams();
		p.addBodyParameter(URLs.JSON_INFO, json.toString());
		Log.i("goodid---------", json.toString());
		SendActtionTool.post(URLs.IsBought, ServiceAction.Action_User,
				UserAction.Action_IsBought, ExchangeActivity.this, p);
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		switch ((UserAction) action) {
		// 获取已有用户收货地址
		case Action_GetGoodsList:
			// 获取数据成功停止刷新
			scrollView.onRefreshComplete();
			Log.i("exchangedata-----------------", value.toString());
			Bean bean = JSON.parseObject(value.toString(), Bean.class);
			Data data = bean.getData();
			// 加载头部广告数据
			dot_layout.removeAllViews();
			adLists = bean.getData().getAdList();
			List<GoodsList> Tlist = data.getGoodsList();
			if (Tlist != null && Tlist.size() != 0) {
				goodsLists.addAll(Tlist);
			}
			initImgs();
			initDot();
			pagerAdapter.notifyDataSetChanged();
			// 加载商品列表数据
			// 因为Scroll中嵌套grideview 不能开始初始化grideView（初始化高度导致不能展示数据）
			// 因此只能每次生成新的adapter，需要在刷新和加载后使adapter=null
			adapter = new ExchangeAdapter(this, goodsLists,
					R.layout.item_exchange);
			gridView.setAdapter(adapter);
			Log.i("goodsLists", goodsLists.toString());
			// 解决刷新后列表没有回到顶部
			if (flag) {
				scrollView.scrollTo(0, 0);
			}
			break;
		case Action_IsBought:
			Bean bean2 = JSON.parseObject(value.toString(), Bean.class);
			Data data2 = bean2.getData();
			String Msg = bean2.getMsg();
			if (bean2.getCode() == 0 && data2.isOperateResult() == true) {// 执行成功跳转
				Intent intent = new Intent(ExchangeActivity.this,
						UserAddressActivity.class);
				intent.putExtra("GoodsId", goodsId);
				intent.putExtra("Coin", coins);
				startActivity(intent);
			} else {
				MTUtils.Toast(this, Msg);
			}
			break;
		}
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);

		switch ((UserAction) action) {
		case Action_GetGoodsList:
			 reset();
			break;

		case Action_IsBought:
			Bean bean = JSON.parseObject(value.toString(), Bean.class);
			String Msg = bean.getMsg();
			MTUtils.Toast(this, Msg);
			break;
		}
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
		switch ((UserAction) action) {
		case Action_GetGoodsList:
			 reset();
			break;

		case Action_IsBought:
			Bean bean = JSON.parseObject(value.toString(), Bean.class);
			String Msg = bean.getMsg();
			MTUtils.Toast(this, Msg);
			break;
		}
	}

	@Override
	public void onFinish(ServiceAction service, Object action) {
		super.onFinish(service, action);
		foot.setVisibility(View.GONE);
		isCanLoad = true;
	}

	/**
	 * 重置属性值
	 */
	private void reset() {
		pageIndex = 0;
		goodsLists.clear();
		pageIndex = 1;
		adapter.notifyDataSetChanged();
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_exchange);
		ViewUtils.inject(this);

	}

	// 头部广告数据添加
	private HomeActivity home;

	// 添加广告图片
	private void initImgs() {
		imgs.clear();
		ImageView img;
		home = HomeActivity.getHomeAvtivity();
		for (int i = 0; i < 5 * adLists.size(); i++) {
			img = new ImageView(this);
			MTApplication.bmu.display(img, adLists.get(i % adLists.size())
					.getImgUrl());
			img.setScaleType(ScaleType.FIT_XY);
			final int x = i;
			// 点击头部广告页
			img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					home.linkTo(adLists.get(x % adLists.size()));
				}
			});
			imgs.add(img);
		}
		viewPager.startAutoScroll();
	}

	// 初始化pager标记原点
	private void initDot() {
		if (imgs.size() / 5 < 2) {
			viewPager.stopAutoScroll();
			dot_layout.setVisibility(View.GONE);
		} else {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					(int) (MTUtils.getScreenWidth(this) * 0.021),
					(int) (MTUtils.getScreenWidth(this) * 0.021));
			lp.setMargins(10, 0, 0, 0);
			ImageView imgdot;
			for (int i = 0; i < imgs.size() / 5; i++) {
				imgdot = new ImageView(this);
				imgdot.setImageResource(R.drawable.ico_float);
				imgdot.setLayoutParams(lp);
				dot_layout.addView(imgdot);
			}
			imgdot = (ImageView) dot_layout.getChildAt(0);
			imgdot.setImageResource(R.drawable.ico_float_red);
		}
	}

	// 初始化广告幻灯片
	private void initViewPager() {
		pagerAdapter = new ViewPagerAdapter(imgs);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOffscreenPageLimit(5);
		viewPager.setOnPageChangeListener(this);
		viewPager.setInterval(4000);// 设置播放间隔时间
		viewPager.setAutoScrollDurationFactor(10);
		viewPager.setCycle(false);// 设置是否循环

	}

	// 改变pager点的指示位置，
	private void dot(int n) {
		ImageView imag;
		for (int i = 0; i < imgs.size() / 5; i++) {
			imag = (ImageView) dot_layout.getChildAt(i);
			if (imag != null) {
				imag.setImageResource(R.drawable.ico_float);
			}
		}
		imag = (ImageView) dot_layout.getChildAt(n);
		if (imag != null) {
			imag.setImageResource(R.drawable.ico_float_red);
		}
	}

	// 头部广告滑动事件
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
			viewPager.setCurrentItem(0, false);
		}
	}

	// 列表刷新加载
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
		reset();
		adapter = null;
		flag = true;
		loadData();

	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
		foot.setVisibility(View.VISIBLE);
		pageIndex += 1;
		adapter = null;
		flag = false;
		loadData();
	}

	@Override
	public void findViewById() {
		Intent intent = getIntent();
		int tbnum = intent.getIntExtra("Tbnum", 0);
		initViewPager();
		tv_Tb.setText(tbnum + "");
		foot = (TextView) findViewById(R.id.exchange_footer);
		scrollView.setMode(Mode.BOTH);
		scrollView.setOnRefreshListener(this);
		TopBar topBar = (TopBar) findViewById(R.id.exchange_topbar);
		topBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
			}

			@Override
			public void leftClick() {
				ExchangeActivity.this.finish();
				finish();
			}
		});
		loadData();

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	// 兑换中心适配器
	public class ExchangeAdapter extends CommonAdapter<GoodsList> {
		public ExchangeAdapter(Context context, List<GoodsList> mDatas,
				int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
			this.mContext = context;
		}

		@Override
		public void convert(final ViewHolder helper, final GoodsList item,
				int position) {
			helper.setImageByUrl(R.id.jiangpin_img, item.getImgUrl());
			helper.setText(R.id.jiangpin_name, item.getTitle());
			helper.setText(R.id.taobi_num, item.getCoins() + "");
			helper.setText(R.id.jiangpin_num, item.getStock() + "");
			OnClickListener clic = new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					goodsId = item.getId();
					coins = item.getCoins();
					// 点击马上兑换
					IsBought(goodsId,coins);
				}
			};
			helper.getView(R.id.btn_exchange).setOnClickListener(clic);
		}
	}
}
