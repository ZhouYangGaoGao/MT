package com.matao.activity;

import java.util.List;

import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.matao.adapter.HqtbAdapter;
import com.matao.bean.Bean;
import com.matao.bean.TaskList;
import com.matao.matao.R;
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
 * @version: 创建时间：2015-8-3 下午3:01:12
 * @Description:积分获取规则列表
 */
public class GetTBActivity extends BaseActivity{
	@ViewInject(R.id.hqtb_listview)
	private ListView lv;
	private List<TaskList> list;
	private HqtbAdapter adapter;// 列表适配器

	@Override
	public void onClick(View arg0) {

	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_hqtb);
		ViewUtils.inject(this);
		loadData();
	}

	@Override
	public void findViewById() {
		TopBar topBar = (TopBar) findViewById(R.id.hqtb_topbar);
		topBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
			}
			@Override
			public void leftClick() {
				GetTBActivity.this.finish();
				// overridePendingTransition(R.anim.slide_left_in,
				// R.anim.slide_left_out);
				finish();
			}
		});
	}

	// 发送请求
	public void loadData() {
		String url = null;
		int UserId = MTApplication.getInt("UserId");
		url = MTUtils.getMTParams(URLs.JfList, "UserId", UserId);
		Logger.e("JfList-------------------------", url);
		// 发送get请求
		SendActtionTool.get(url, ServiceAction.Action_Comment, null, this);
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		// TODO Auto-generated method stub
		super.onSuccess(service, action, value);
		Bean bean = JSON.parseObject(value.toString(), Bean.class);
		list = bean.getData().getTaskList();
		adapter = new HqtbAdapter(list,this);
		lv.setAdapter(adapter);
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		Bean bean = JSON.parseObject(value.toString(), Bean.class);
		String Msg = bean.getMsg();
		MTUtils.Toast(this, Msg);
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
