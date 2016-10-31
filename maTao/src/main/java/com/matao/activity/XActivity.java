package com.matao.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.matao.fragment.HomeFragment;
import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.URLs;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-27 下午4:08:52
 * @Description:N部曲界面
 */
public class XActivity extends BaseActivity {
	public static int age;
	@ViewInject(R.id.x_topbar)
	TopBar topBar;
	@ViewInject(R.id.x_close)
	ImageView imageView;

	@OnClick({ R.id.x_close })
	public void onClick(View v) {
		switch (v.getId()) {
		// 点击关闭
		case R.id.x_close:
			this.finish();
			break;
		// 孕期
		case R.id.yq_img1:
			toDesc(URLs.mhost + "-28276/");
			break;
		case R.id.yq_img2:
			toDesc(URLs.mhost + "-28292/");
			break;
		case R.id.yq_img3:
			toDesc(URLs.mhost + "-28310/");
			break;
		case R.id.yq_img4:
			toDesc(URLs.mhost + "-28423/");
			break;
		case R.id.yq_img5:
			toDesc(URLs.mhost + "-28433/");
			break;
		case R.id.yq_img6:
			toDesc(URLs.mhost + "-28449/");
			break;
		case R.id.yq_img7:
			toDesc(URLs.mhost + "-28460/");
			break;
		case R.id.yq_img8:
			toDesc(URLs.mhost + "-28474/");
			break;
		case R.id.yq_img9:
			toDesc(URLs.mhost + "-28438/");
			break;
		case R.id.yq_img10:
			toDesc(URLs.mhost + "-28476/");
			break;

		// 0-3月
		case R.id.x_0_3_img1:
			toDesc(URLs.mhost + "-28560/");
			break;
		case R.id.x_0_3_img2:
			toDesc(URLs.mhost + "-28618/");
			break;
		case R.id.x_0_3_img3:
			toDesc(URLs.mhost + "-28697/");
			break;
		case R.id.x_0_3_img4:
			toDesc(URLs.mhost + "-28722/");
			break;
		case R.id.x_0_3_img5:
			toDesc(URLs.mhost + "-28744/");
			break;
		case R.id.x_0_3_img6:
			toDesc(URLs.mhost + "-28754/");
			break;
		case R.id.x_0_3_img7:
			toDesc(URLs.mhost + "-28770/");
			break;
		case R.id.x_0_3_img8:
			toDesc(URLs.mhost + "-28892/");
			break;
		case R.id.x_0_3_img9:
			toDesc(URLs.mhost + "-28925/");
			break;
		case R.id.x_0_3_img10:
			toDesc(URLs.mhost + "-28756/");
			break;

		// 3-6月
		case R.id.x_3_6m1:
			toDesc(URLs.mhost + "-29384/");
			break;
		case R.id.x_3_6m2:
			toDesc(URLs.mhost + "-28588/");
			break;
		case R.id.x_3_6m3:
			toDesc(URLs.mhost + "-28593/");
			break;
		case R.id.x_3_6m4:
			toDesc(URLs.mhost + "-29454/");
			break;
		case R.id.x_3_6m5:
			toDesc(URLs.mhost + "-29461/");
			break;

		// 6-12月
		case R.id.x_6_12m1:
			toDesc(URLs.mhost + "-29469/");
			break;
		case R.id.x_6_12m2:
			toDesc(URLs.mhost + "-29530/");
			break;
		case R.id.x_6_12m3:
			toDesc(URLs.mhost + "-29542/");
			break;
		case R.id.x_6_12m4:
			toDesc(URLs.mhost + "-29549/");
			break;
		case R.id.x_6_12m5:
			toDesc(URLs.mhost + "-29557/");
			break;
		case R.id.x_6_12m6:
			toDesc(URLs.mhost + "-29567/");
			break;
		case R.id.x_6_12m7:
			toDesc(URLs.mhost + "-29580/");
			break;

		// 1-3岁
		case R.id.x_1_3_img1:
			toDesc(URLs.mhost + "-29586/");
			break;
		case R.id.x_1_3_img2:
			toDesc(URLs.mhost + "-29590/");
			break;
		case R.id.x_1_3_img3:
			toDesc(URLs.mhost + "-29596/");
			break;
		case R.id.x_1_3_img4:
			toDesc(URLs.mhost + "-29613/");
			break;
		case R.id.x_1_3_img5:
			toDesc(URLs.mhost + "-29662/");
			break;
		case R.id.x_1_3_img6:
			toDesc(URLs.mhost + "-29667/");
			break;
		case R.id.x_1_3_img7:
			toDesc(URLs.mhost + "-29673/");
			break;

		// 3-6岁
		case R.id.x_3_6y1:
			toDesc(URLs.mhost + "-29686/");
			break;
		case R.id.x_3_6y2:
			toDesc(URLs.mhost + "-29705/");
			break;
		case R.id.x_3_6y3:
			toDesc(URLs.mhost + "-29714/");
			break;
		case R.id.x_3_6y4:
			// Intent intent43 = new Intent(this, DetailsActivity.class);
			toDesc(URLs.mhost + "-29731/");
			break;
		}
	}

	// 点击跳转详情
	private void toDesc(String url) {
		Intent intent = new Intent(this, DetailsActivity.class);
		intent.putExtra("url", url);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
	}

	// 根据宝宝年龄阶段展示不同的view
	@Override
	public void setContentView() {
		age = getIntent().getIntExtra("age", 0);
		Logger.i("age", age);
		if (age == 0) {
			this.finish();
		}
		switch (age) {
		// 孕期
		case 1:
			setContentView(R.layout.activity_0_0_);
			break;
		case 2:
			setContentView(R.layout.activity_0_3month);
			break;
		case 3:
			setContentView(R.layout.activity_3_6month);
			break;
		case 4:
			setContentView(R.layout.activity_6_12month);
			break;
		case 5:
			setContentView(R.layout.activity_1_3year);
			break;
		case 6:
			setContentView(R.layout.activity_3_6year);
			break;
		}
	}

	// 初始化
	@Override
	public void findViewById() {
		ViewUtils.inject(this);
		topBar.setOnTopBarClickListener(new topBarClickListener() {

			@Override
			public void leftClick() {
				if (age == HomeFragment.age) {
					finish();
				} else {
					finish();
					Intent intent = new Intent(XActivity.this, XActivity.class);
					intent.putExtra("age", XActivity.age - 1);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_right_in,
							R.anim.slide_right_out);
				}
			}

			@Override
			public void rightClick() {
				finish();
				Intent intent = new Intent(XActivity.this, XActivity.class);
				intent.putExtra("age", XActivity.age + 1);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_left_out);
			}
		});
		if (age == 6) {
			topBar.getRightImageView().setVisibility(View.GONE);
		}
	}

	// 定义关闭返回方法
	@Override
	public void finish() {
		super.finish();
		// overridePendingTransition(R.anim.slide_right_in,
		// R.anim.slide_right_out);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
