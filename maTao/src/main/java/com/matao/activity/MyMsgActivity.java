package com.matao.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.matao.bean.MsgBean;
import com.matao.bean.MsgData;
import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.utils.SendActtionTool;
import com.matao.utils.ServiceAction;
import com.matao.utils.URLs;
import com.matao.utils.UserAction;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-7-10 上午10:23:15
 * @Description:我的消息页面
 */
public class MyMsgActivity extends BaseActivity {
	private TopBar topBar;
	@ViewInject(R.id.prompt_hf)
	private TextView tv_hf;
	@ViewInject(R.id.prompt_xh)
	private TextView tv_xh;
	@ViewInject(R.id.prompt_xttz)
	private TextView tv_xttz;
	private int hf_num, xh_num, xttz_num;
	public static final int CODE_MYMSG = 100;
	public static boolean isRead1 = false;
	public static boolean isRead2 = false;
	public static boolean isRead3 = false;

	@OnClick({ R.id.wdxx_hf, R.id.wdxx_xh, R.id.wdxx_xttz })
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.wdxx_hf:
			Intent intent = new Intent(this, HfActivity.class);
			intent.putExtra("Msgnum", hf_num);
			tv_hf.setVisibility(View.GONE);
			startActivity(intent);
			break;
		case R.id.wdxx_xh:
			Intent intent2 = new Intent(this, XhActivity.class);
			intent2.putExtra("Msgnum", xh_num);
			startActivity(intent2);
			tv_xh.setVisibility(View.GONE);
			break;
		case R.id.wdxx_xttz:
			Intent intent3 = new Intent(this, XttzActivity.class);
			intent3.putExtra("Msgnum", xttz_num);
			tv_xttz.setVisibility(View.GONE);
			startActivity(intent3);
			break;
		}
	}

	// @Override
	// protected void onActivityResult(int requestCode, int ResultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, ResultCode, data);
	// switch (requestCode) {
	// case CODE_MYMSG:
	// loadData();
	// }
	// }

	// 请求接口加载页面数据
	private void loadData() {
		String url = null;
		String TimeStamp = MTUtils.getTimeStamp();
		int UserId = MTApplication.getInt("UserId");
		url = MTUtils.getMTParams(URLs.MSgNum, URLs.USER_ID, UserId, "p",
				TimeStamp);
		SendActtionTool.get(url, ServiceAction.Action_Comment,
				UserAction.Action_GetMasgNum, this);
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		switch ((UserAction) action) {
		case Action_GetMasgNum:
			MsgBean bean = JSON.parseObject(value.toString(), MsgBean.class);
			MsgData data = bean.getData();
			hf_num = data.getReplys();
			if (hf_num != 0) {
				tv_hf.setVisibility(View.VISIBLE);
				tv_hf.setText(hf_num + "");
				if (hf_num >= 99) {
					tv_hf.setText("99");
				}
			} else {
				tv_hf.setVisibility(View.GONE);
			}
			xh_num = data.getLikes();
			if (xh_num != 0) {
				tv_xh.setVisibility(View.VISIBLE);
				tv_xh.setText(xh_num + "");
				if (xh_num >= 99) {
					tv_xh.setText("99");
				}
			} else {
				tv_xh.setVisibility(View.GONE);
			}
			xttz_num = data.getNotices();
			if (xttz_num != 0) {
				tv_xttz.setVisibility(View.VISIBLE);
				tv_xttz.setText(xttz_num + "");
				if (xttz_num >= 99) {
					tv_xttz.setText("99");
				}
			} else {
				tv_xttz.setVisibility(View.GONE);
			}
			Logger.e("hf_num===================", hf_num);
			Logger.e("xh_num===================", xh_num);
			Logger.e("xttz_num===================", xttz_num);
			break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		loadData();
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_msg);
		ViewUtils.inject(this);
		loadData();
	}

	@Override
	public void findViewById() {
		topBar = (TopBar) findViewById(R.id.wdxx_topbar);
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
}
