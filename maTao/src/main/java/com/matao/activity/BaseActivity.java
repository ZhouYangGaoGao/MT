package com.matao.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View.OnClickListener;

import com.matao.utils.CommonListener;
import com.matao.utils.ServiceAction;
import com.umeng.analytics.MobclickAgent;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-27 上午10:26:10
 * @Description:界面基类
 */
public abstract class BaseActivity extends FragmentActivity implements
		CommonListener, OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView();
		findViewById();
	}

	public abstract void setContentView();

	public abstract void findViewById();

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {

	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {

	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {

	}

	@Override
	public void onStart(ServiceAction service, Object action) {

	}

	@Override
	public void onFinish(ServiceAction service, Object action) {

	}
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		}
		public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		}
}
