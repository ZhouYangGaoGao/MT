package com.matao.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.matao.adapter.CommonAdapter;
import com.matao.bean.Bean;
import com.matao.bean.DaTePager;
import com.matao.bean.HeadAdList;
import com.matao.matao.R;
import com.matao.pulltorefresh.library.PullToRefreshBase;
import com.matao.pulltorefresh.library.PullToRefreshBase.Mode;
import com.matao.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.matao.pulltorefresh.library.PullToRefreshListView;
import com.matao.utils.Logger;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.utils.SendActtionTool;
import com.matao.utils.ServiceAction;
import com.matao.utils.URLs;
import com.matao.utils.ViewHolder;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-27 下午4:08:52
 * @Description:标签页界面
 */

public class ZhuanTiActivity extends BaseActivity implements
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
	private List<HeadAdList> list = new ArrayList<HeadAdList>();// 列表数据集合
	private Bean bean;// 数据bean
	private CommonAdapter<HeadAdList> adapter;// 列表适配器
	private int pageIndex = 1;// 列表页码
	private String querytime = "2015-05-6";// 时间戳
	private TopBar bar;

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
		listView.setMode(Mode.BOTH);
		listView.setOnRefreshListener(this);
		adapter = new CommonAdapter<HeadAdList>(this, list,
				R.layout.item_zhuan_ti) {

			@Override
			public void convert(ViewHolder helper, HeadAdList item, int position) {
				helper.setImageByUrl2(R.id.item_zhuan_ti_img, item.getImgUrl(),
						R.drawable.maotao_675_382);
				helper.setText(R.id.item_zhuan_ti_text1, item.getTitle());
				helper.setText(R.id.item_zhuan_ti_text2, item.getSummary());
			}
		};
		// 设置在listview滑动时不加载图片，停止滑动开始加载图片
		listView.setOnScrollListener(new PauseOnScrollListener(
				MTApplication.bmu, false, true));
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int p,
					long arg3) {
				HomeActivity.getHomeAvtivity().linkTo(list.get(p));
			}
		});
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
					url = MTUtils.getMTParams(URLs.ZHUANTI, URLs.PAGE_INDEX,
							pageIndex, URLs.QUERY_TIME, querytime1);
					Logger.i("专题列表-url", url);
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
		List<HeadAdList> tlist = bean.getData().getActivityList();
		if (tlist != null && tlist.size() != 0) {
			Logger.i("成功--tlist", tlist.size());
			list.addAll(tlist);
			tip_doData.setVisibility(View.GONE);
		} else if (list.size() == 0) {
			tip_doData.setVisibility(View.VISIBLE);
		}

		DaTePager dp = bean.getData().getDaTePager();
		querytime = dp.getQueryTime();
		animstop();
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
		animstop();
		reset();
		bg.setVisibility(View.VISIBLE);

	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		animstop();
		reset();
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
	 * 重置属性值
	 * 
	 * 20150420:ZhouYang
	 * 
	 * 2015-5-12下午5:47:39
	 * 
	 */
	private void reset() {
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
		foot = View.inflate(this, R.layout.fragment_bottom, null);
		// footer = (TextView) foot.findViewById(R.id.fragment_bottom_footer);
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
