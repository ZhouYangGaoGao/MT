 package com.matao.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.matao.matao.R;
import com.matao.utils.MTApplication;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-7-7 上午11:09:16
 * @Description:逛一逛页面
 */
public class GygActivity extends BaseActivity {
	private ImageView imageView, imageView2, imageView3, imageView4;
	private int BabyState;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.gyg_zt_close:
			finish();
			break;
		case R.id.gyg_zt_by:
			BabyState = 2;
			MTApplication.mEditor.putInt("BabyState", BabyState);
			MTApplication.mEditor.putString("BabyState2", "yes");
			MTApplication.mEditor.putInt("BabyAgeScope", 0);
			MTApplication.mEditor.commit();
			WelcomeActivity.getInstence().finish();
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			finish();
			break;
		case R.id.gyg_zt_yq:
			BabyState = 1;
			Intent intent2 = new Intent(this, YqActivity.class);
			startActivity(intent2);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			finish();
			break;
		case R.id.gyg_zt_yybb:
			BabyState = 0;
			Intent intent3 = new Intent(this, YybbActivity.class);
			startActivity(intent3);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			finish();
			break;
		}
	}

	@Override
	public void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_gyg_zt);
	}

	@Override
	public void findViewById() {
		// TODO Auto-generated method stub
		imageView = (ImageView) findViewById(R.id.gyg_zt_close);
		imageView.setOnClickListener(GygActivity.this);
		imageView2 = (ImageView) findViewById(R.id.gyg_zt_by);
		imageView2.setOnClickListener(GygActivity.this);
		imageView3 = (ImageView) findViewById(R.id.gyg_zt_yq);
		imageView3.setOnClickListener(GygActivity.this);
		imageView4 = (ImageView) findViewById(R.id.gyg_zt_yybb);
		imageView4.setOnClickListener(GygActivity.this);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.slide,
				R.anim.slide_out_to_bottom);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
	
}
