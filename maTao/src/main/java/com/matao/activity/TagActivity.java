package com.matao.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.matao.adapter.HomeTypeAdapter;
import com.matao.bean.ArticleList;
import com.matao.bean.Bean;
import com.matao.bean.DaTePager;
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
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-27 下午4:08:52
 * @Description:标签页界面
 */

public class TagActivity extends BaseActivity implements
		OnRefreshListener2<ListView>, topBarClickListener {
	private AnimationDrawable aniDraw;
	@ViewInject(R.id.anim)
	private TextView aini;
	@ViewInject(R.id.favorable_tip)
	TextView tip_doData;
	@ViewInject(R.id.favorable_bg)
	TextView bg;
	@ViewInject(R.id.tag_topbar_text)
	TextView title;
	@ViewInject(R.id.tag_top_text)
	private TextView top_text;
	@ViewInject(R.id.tag_top_img)
	private ImageView top_img;
	private View foot;
	// private TextView footer;
	private PullToRefreshListView listView;// 列表
	private List<ArticleList> list = new ArrayList<ArticleList>();// 列表数据集合
	private Bean bean;// 数据bean
	private HomeTypeAdapter adapter;// 列表适配器
	private String tagid = "0";//
	private int pageIndex = 1;// 列表页码
	private String querytime = "2015-05-6";// 时间戳
	// private int totalCount;// 列表总条数
	// private int pagerCount;// 列表总页数
	private TopBar bar;
	private ListView lv;
	private View header;

	@OnClick({ R.id.favorable_bg, R.id.favorable_top })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.favorable_top:
			Logger.i("topClick", "返回顶部");
			listView.getRefreshableView().setSelection(0);
			break;
		case R.id.favorable_bg:
			animaostart();
			loadData();
			break;
		}
	}

	// 初始化列表
	private void initListView() {
		lv = listView.getRefreshableView();
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(this);
		adapter = new HomeTypeAdapter(this, list);
		// 设置在listview滑动时不加载图片，停止滑动开始加载图片
		listView.setOnScrollListener(new PauseOnScrollListener(
				MTApplication.bmu, false, true));
		listView.setAdapter(adapter);
		// 增加头部视图
		header = LayoutInflater.from(TagActivity.this).inflate(
				R.layout.activity_tag_top, null);
		top_text = (TextView) header.findViewById(R.id.tag_top_text);
		top_img = (ImageView) header.findViewById(R.id.tag_top_img);
		lv.addHeaderView(header);
	}

	// 开始请求
	@Override
	public void onStart(ServiceAction service, Object action) {
		super.onStart(service, action);
	}

	private boolean isCanLoad = true;

	@Override
	public void onFinish(ServiceAction service, Object action) {
		super.onFinish(service, action);
		listView.getRefreshableView().removeFooterView(foot);
		isCanLoad = true;
	}

	// 发送请求
	public void loadData() {
		if (MTUtils.isNetworkConnected(this)) {
			if (isCanLoad) {
				isCanLoad = false;
				String url = null;
				try {
					String querytime1 = URLEncoder.encode(querytime, "utf-8");
					url = MTUtils.getMTParams(URLs.TAG, URLs.TAG_ID, tagid,
							URLs.PAGE_INDEX, pageIndex, URLs.QUERY_TIME,
							querytime1);
					Logger.i("url-----homeFragment------>>>", url);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// 发送get请求
				SendActtionTool.get(url, ServiceAction.Action_Comment, null,
						this);
			}
		} else {
			animstop();
			tip_doData.setVisibility(View.GONE);
			// footer.setVisibility(View.GONE);
			reset();
			listView.onRefreshComplete();
			listView.setVisibility(View.INVISIBLE);
			bg.setVisibility(View.VISIBLE);
			MTUtils.netTip(this);
		}
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		bean = JSON.parseObject(value.toString(), Bean.class);// 获得数据实体，使用alibaba的fastJson解析
		List<ArticleList> tlist = bean.getData().getTagLists();
		if (!MTUtils.isEmpty(bean.getData().getTagInfo().getTagName())) {
			title.setText(bean.getData().getTagInfo().getTagName());
		}
		if (!MTUtils.isEmpty(bean.getData().getTagInfo().getTagImgUrl())) {
			MTApplication.bmu.display(top_img, bean.getData().getTagInfo()
					.getTagImgUrl());
			LayoutParams lp = top_img.getLayoutParams();
			lp.height = (int) (MTUtils.getScreenWidth(this) * 0.4694444);
			top_img.setLayoutParams(lp);
		} else {
			top_img.setVisibility(View.GONE);
		}

		if (!MTUtils.isEmpty(bean.getData().getTagInfo().getContent())) {
			top_text.setText(bean.getData().getTagInfo().getContent());
			// 根据屏幕分辨率设置文字大小
			if (MTUtils.getScreenWidth(this) > 720) {
				top_text.setTextSize(14);
			}
		} else {
			top_text.setVisibility(View.GONE);
		}
		if (tlist != null && tlist.size() != 0) {
			Logger.i("成功--tlist", tlist.size());
			list.addAll(tlist);
			tip_doData.setVisibility(View.GONE);
		} else if (list.size() == 0) {
			tip_doData.setVisibility(View.VISIBLE);
		}

		DaTePager dp = bean.getData().getDaTePager();
		// if (pageIndex == 1) {// 当列表为第一页的时候初始化下列属性
		// totalCount = dp.getTotalCount();
		// pagerCount = dp.getPageCount();
		// }

		// if (pagerCount != 0 && pageIndex >= pagerCount) {
		// footer.setVisibility(View.VISIBLE);
		// } else {
		// footer.setVisibility(View.GONE);
		// }
		querytime = dp.getQueryTime();
		animstop();
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
		animstop();
		reset();
		lv.removeHeaderView(header);
		bg.setVisibility(View.VISIBLE);
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		animstop();
		reset();
		lv.removeHeaderView(header);
		bg.setVisibility(View.VISIBLE);
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		reset();
		loadData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		listView.getRefreshableView().addFooterView(foot);
		pageIndex += 1;
		// footer.setVisibility(View.GONE);
		loadData();
	}

	/**
	 *  重置属性值
	 * 
	 * 20150420:ZhouYang
	 * 
	 * 2015-5-12下午5:47:39
	 * 
	 */
	private void reset() {
		// pagerCount = 0;
		pageIndex = 0;
		list.clear();
		pageIndex = 1;
		adapter.notifyDataSetChanged();
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
	}

	@Override
	public void leftClick() {
		finish();
	}

	@Override
	public void rightClick() {
		Intent in = new Intent(this, MyHouseActivity.class);
		startActivity(in);
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

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_tag);
		ViewUtils.inject(this);
	}

	@Override
	public void findViewById() {
		Intent i = getIntent();
		tagid = i.getStringExtra("tagid");
		foot = View.inflate(this, R.layout.fragment_bottom, null);
		// footer = (TextView) foot.findViewById(R.id.fragment_bottom_footer);
		// footer.startAnimation(AnimationUtils.loadAnimation(this,
		// R.anim.slide_bottom_to_top));
		title.setTypeface(MTApplication.jtz);
		aniDraw = (AnimationDrawable) aini.getBackground();
		listView = (PullToRefreshListView) findViewById(R.id.tag_listview);
		// listView.getRefreshableView().addFooterView(foot);

		bar = (TopBar) findViewById(R.id.tag_topbar);
		bar.setOnTopBarClickListener(this);
		initListView();
		animaostart();
		loadData();
	}
}
