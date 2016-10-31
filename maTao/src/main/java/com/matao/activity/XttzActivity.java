package com.matao.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.matao.adapter.XttzAdapter;
import com.matao.bean.Bean;
import com.matao.bean.HeadAdList;
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
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-7-10 上午10:56:03
 * @Description:系统通知列表页面
 */
public class XttzActivity extends BaseActivity implements
		OnRefreshListener2<ListView> {
	private TopBar topBar;
	private AnimationDrawable aniDraw;
	@ViewInject(R.id.anim)
	private TextView aini;
	@ViewInject(R.id.favorable_tip)
	TextView tip_doData;
	@ViewInject(R.id.xttz_listview)
	private PullToRefreshListView listView;
	private XttzAdapter adapter;// 列表适配器
	private List<HeadAdList> list = new ArrayList<HeadAdList>();// 列表数据集合
	// private String querytime = "2015-05-6";// 时间戳
	// private int pagerCount;// 列表总页数
	// private int totalCount;// 列表总条数
	private int pageIndex = 1;// 列表页码
	private View foot;
	private int msg_num;

	// 初始化列表
	private void initListView() {
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(this);
		listView.getRefreshableView();
		adapter = new XttzAdapter(this, list, R.layout.item_xttz, msg_num);
		// 设置在listview滑动时不加载图片，停止滑动开始加载图片
		listView.setOnScrollListener(new PauseOnScrollListener(
				MTApplication.bmu, false, true));
		listView.setAdapter(adapter);
	}

	// 开始请求
	@Override
	public void onStart(ServiceAction service, Object action) {
		super.onStart(service, action);
	}

	private boolean isCanLoad = true;

	// 发送请求
	public void loadData() {
		if (MTUtils.isNetworkConnected(this)) {
			if (isCanLoad) {
				isCanLoad = false;
				String url = null;
				int UserId = MTApplication.getInt("UserId");
				url = MTUtils.getMTParams(URLs.XttzList, URLs.PAGE_INDEX,
						pageIndex, "userId", UserId);
				// 发送get请求
				Logger.e("xttzurl--------------------", url);
				SendActtionTool.get(url, ServiceAction.Action_Comment, null,
						this);
			}
		} else {
			tip_doData.setVisibility(View.GONE);
			reset();
			// footer.setVisibility(View.GONE);
			listView.onRefreshComplete();
			listView.setVisibility(View.INVISIBLE);
			MTUtils.netTip(this);
		}
	}

	private void reset() {
		// pagerCount = 0;
		pageIndex = 0;
		list.clear();
		pageIndex = 1;
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);

		// 获取数据
		Bean bean = JSON.parseObject(value.toString(), Bean.class);
		List<HeadAdList> tlist = bean.getData().getSystemNotices();
		Logger.i("SHUJU---------------", tlist);
		if (tlist != null && tlist.size() != 0) {
			Logger.i("成功--tlist", tlist.size());
			list.addAll(tlist);
			Logger.i("list-------------", list);
			tip_doData.setVisibility(View.GONE);
		} else if (list.size() == 0) {
			tip_doData.setVisibility(View.VISIBLE);
		}
		// DaTePager dp = bean.getData().getDaTePager();
		// if (pageIndex == 1) {// 当列表为第一页的时候初始化下列属性
		// totalCount = dp.getTotalCount();
		// pagerCount = dp.getPageCount();
		// }
		// querytime = dp.getQueryTime();
		animstop();
	}

	@Override
	public void onFinish(ServiceAction service, Object action) {
		super.onFinish(service, action);
		Logger.i("onFinish", "onFinish");
		listView.getRefreshableView().removeFooterView(foot);
		isCanLoad = true;
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
		listView.getRefreshableView().removeFooterView(foot);
		animstop();
		reset();
		tip_doData.setVisibility(View.VISIBLE);
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		animstop();
		reset();
		tip_doData.setVisibility(View.VISIBLE);
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_xttz);
		ViewUtils.inject(this);
	}

	@Override
	public void findViewById() {
		Intent i = getIntent();
		msg_num = i.getIntExtra("Msgnum", 0);
		initListView();
		loadData();
		aniDraw = (AnimationDrawable) aini.getBackground();
		animaostart();
		foot = View.inflate(this, R.layout.fragment_bottom, null);
		topBar = (TopBar) findViewById(R.id.xttz_topbar);
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

	// 上拉加载，下拉刷新
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		reset();
		loadData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		listView.getRefreshableView().addFooterView(foot);
		pageIndex += 1;
		loadData();
	}

	@Override
	public void onClick(View arg0) {
	}

	// 启动动画
	private void animaostart() {
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
		listView.onRefreshComplete();
		listView.setVisibility(View.VISIBLE);
		if (aniDraw != null) {
			aniDraw.stop();
			aini.setVisibility(View.INVISIBLE);
		}
	}
}
