package com.matao.activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-27 下午4:08:52
 * @Description:预览大图界面
 */
@ContentView(R.layout.activity_preview)
public class PreviewActivity extends BaseActivity {
	@ViewInject(R.id.bigimag_topbar)
	private TopBar topBar;
	@ViewInject(R.id.bigimag)
	private ImageView img;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialog_Y:
			Intent data = new Intent();
			data.putExtra("isDelete", true);
			setResult(1, data);
			d.dismiss();
			PreviewActivity.this.finish();
			break;
		case R.id.dialog_N:
			d.dismiss();
			break;
		}
	}

	public static String uri = null;

	@Override
	public void setContentView() {
		ViewUtils.inject(this);
		Logger.i("uri", uri);
		MTApplication.bmu.display(img, uri);
		// img.setImageURI(uri);
		topBar = (TopBar) findViewById(R.id.bigimag_topbar);
		topBar.setOnTopBarClickListener(new topBarClickListener() {

			@Override
			public void leftClick() {
				finish();
			}

			@Override
			public void rightClick() {
				d = MTUtils.showDialog(PreviewActivity.this,
						PreviewActivity.this, "取消", "确认", "您确定要删除这张图吗?");
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

	Dialog d;

	@Override
	public void findViewById() {

	}

}
