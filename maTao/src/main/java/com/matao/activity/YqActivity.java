package com.matao.activity;

import java.util.Calendar;

import android.app.Dialog;
import android.content.Intent;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.matao.bean.BabystateBean;
import com.matao.bean.BabystateData;
import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.utils.SendActtionTool;
import com.matao.utils.ServiceAction;
import com.matao.utils.URLs;
import com.matao.utils.UserAction;
import com.matao.view.MyDatePicker;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-7-7 下午2:14:34
 * @Description:选择预产期页面
 */
public class YqActivity extends BaseActivity {
	private Dialog ddate;// 选择日期弹窗
	public final int DATE = 1001;
	public final int BIRTHDAY = 1002;
	private String duetime;

	@ViewInject(R.id.gyg_yq_date)
	TextView yuchanqi;// 选择预产期
	@ViewInject(R.id.dialog_date_N)
	TextView date_N;// 日期选择器-取消
	@ViewInject(R.id.dialog_date_Y)
	TextView date_Y;// 日期选择器-确定
	@ViewInject(R.id.dialog_date_Title)
	TextView date_Title;// 日期选择器-标题

	@OnClick({ R.id.gyg_yq_back, R.id.gyg_yq_date, R.id.gyg_yq_yes,
			R.id.dialog_date_N, R.id.dialog_date_Y, })
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.gyg_yq_back:
			startActivity(new Intent(this, GygActivity.class));
			finish();
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
			break;
		case R.id.gyg_yq_date:
			ddate = datePickerShow(ddate, DATE, R.layout.dialog_time_birday);
			break;
		case R.id.dialog_date_Y:
			if (month < 10 && day < 10) {
				yuchanqi.setText(year + "年" + month + "月" + day + "日");
				duetime = year + "-" + month + "-" + day;
			} else if (month >= 10 && day < 10) {
				yuchanqi.setText(year + "年" + month + "月" + day + "日");
				duetime = year + "-" + month + "-" + day;
			} else if (month < 10 && day >= 10) {
				yuchanqi.setText(year + "年" + month + "月" + day + "日");
				duetime = year + "-" + month + "-" + day;
			} else {
				yuchanqi.setText(year + "年" + month + "月" + day + "日");
				duetime = year + "-" + month + "-" + day;
			}
			ddate.dismiss();
			break;
		case R.id.dialog_date_N:
			ddate.dismiss();
			break;
		case R.id.gyg_yq_yes:
			check();
			break;
		}
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_gyg_yq);
	}

	@Override
	public void findViewById() {
		ViewUtils.inject(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(this, GygActivity.class));
		finish();
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}

	/**
	 * 日期选择
	 * 
	 * @param textView
	 * @return
	 */
	private int year;
	private int month;
	private int day;

	protected Dialog datePickerShow(Dialog dialog, int dialogCode, int layoutId) {
		dialog = new Dialog(YqActivity.this, R.style.FullHeightDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View v = View.inflate(YqActivity.this, layoutId, null);
		ViewUtils.inject(this, v);
		dialog.setContentView(v);
		switch (dialogCode) {
		case DATE:
			date_Title.setText("选择预产期");
			break;
		case BIRTHDAY:
			date_Title.setText("选择宝宝生日");
			break;
		}
		dialog.setCanceledOnTouchOutside(true);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);
		WindowManager m = YqActivity.this.getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
		p.width = (int) (d.getWidth() * 1); // 宽度设置为屏幕的0.95
		dialogWindow.setAttributes(p);
		dialogWindow.getDecorView().setPadding(0, 0, 0, 0);

		MyDatePicker dpicker = (MyDatePicker) dialog
				.findViewById(R.id.datepicker_layout);
		dpicker.setOnChangeListener(new MyDatePicker.OnChangeListener() {
			@Override
			public void onChange(int year2, int month2, int day2,
					int day_of_week2) {
				year = year2;
				Calendar calendar = Calendar.getInstance();
				calendar.get(Calendar.YEAR);
				if (calendar.get(Calendar.YEAR) == year) {
					year = calendar.get(Calendar.YEAR);
				}
				month = month2;
				day = day2;

				if (month < 10 && day < 10) {
				} else if (month >= 10 && day < 10) {
				} else if (month < 10 && day >= 10) {
				}
			}
		});
		dialog.show();
		return dialog;
	}

	// 请求接口上传逛一逛填写数据
	private void upData() {
		String url = null;
		url = MTUtils.getMTParams(URLs.BabyInfo, "babystate", 1, "birthday",
				null, "dueTime", duetime);
		SendActtionTool.get(url, ServiceAction.Action_Comment,
				UserAction.Action_BabyInfo, this);
		Logger.i("url-----------------------------", url);
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		switch ((UserAction) action) {
		case Action_BabyInfo:
			BabystateBean bean = JSON.parseObject(value.toString(), BabystateBean.class);
			BabystateData data = bean.getData();
			String BabyAgeD = data.getBabyInfo();
			int BabyAgeScope = data.getBabyAgeScope();
			Logger.i("info+aged===========================", BabyAgeD
					+ BabyAgeScope);
			MTApplication.mEditor.putString("BabyDue", duetime);
			MTApplication.mEditor.putString("BabyAgeD", BabyAgeD);
			MTApplication.mEditor.putInt("BabyState", 1);
			MTApplication.mEditor.putString("BabyState2", "yes");
			MTApplication.mEditor.putInt("BabyAgeScope", BabyAgeScope);
			MTApplication.mEditor.commit();
			WelcomeActivity.getInstence().finish();
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			finish();
			break;
		}
	}

	public void check() {
		// 当前时间
		long times = System.currentTimeMillis();
		Logger.i("times", times);
		// 用户选取预产期时间
		long chose_yctime = MTUtils.getMillTime(year + "-" + month + "-" + day);
		Logger.i("chose_yctime", chose_yctime);
		if (MTUtils.isEmpty(yuchanqi.getText().toString().trim())
				|| yuchanqi.getText().toString().trim().equals("请选择预产期")) {
			MTUtils.Toast(this, "请选择预产期");
		} else if (!MTUtils.isEmpty(yuchanqi.getText().toString().trim())
				&& chose_yctime < times) {
			MTUtils.Toast(this, "亲，预产期只能选择今天之后哦~");
		} else if (!MTUtils.isEmpty(yuchanqi.getText().toString().trim())
				&& chose_yctime > (times + 280l * 24 * 3600 * 1000)) {
			Logger.i("times+280", times + 280l * 24 * 3600 * 1000);
			MTUtils.Toast(this, "亲，预产期可能不会超过280天 哦~");
		} else {
			upData();
		}
	}
}
