package com.matao.activity;

import android.view.View;

import com.matao.matao.R;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-27 下午4:08:52
 * @Description:注册协议界面
 */

public class RegistRreatyActivity extends BaseActivity {
	private TopBar topBar;

	public void init() {
		setContentView(R.layout.activity_registrreaty);
		topBar = (TopBar) findViewById(R.id.registtreaty_topbar);
		topBar.setOnTopBarClickListener(new topBarClickListener() {

			@Override
			public void leftClick() {
				finish();
			}

			@Override
			public void rightClick() {

			}
		});
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
	public void onClick(View v) {

	}

	@Override
	public void setContentView() {
		init();

	}

	@Override
	public void findViewById() {

	}

}
