package com.matao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.matao.utils.CommonListener;
import com.matao.utils.ServiceAction;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-27 上午10:26:10
 * @Description:界面 fragment base
 */
public abstract class BaseFragment extends Fragment implements CommonListener,
		OnClickListener {
	public static boolean isJump = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {  
		View v = setContentView(inflater);
		initView(v);
		return v;
	}

	public abstract View setContentView(LayoutInflater inflater);

	public abstract void initView(View v);

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
	public void onFinish(ServiceAction service, Object action) {

	}

	@Override
	public void onStart(ServiceAction service, Object action) {

	}

}
