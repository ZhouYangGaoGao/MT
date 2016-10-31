package com.matao.activity;

import java.util.Calendar;

import android.app.Dialog;
import android.content.Intent;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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
 * @version: 创建时间：2015-7-7 下午2:16:48
 * @Description:已有宝宝状态选择页面
 */
public class YybbActivity extends BaseActivity {
	private Dialog ddate;// 选择日期弹窗
	public final int DATE = 1001;
	public final int BIRTHDAY = 1002;
	private String babySex = "",birthtime;
	
	
	@ViewInject(R.id.gyg_yybb_date)
	TextView birthDay;// 宝宝生日
	@ViewInject(R.id.dialog_date_N)
	TextView date_N;// 日期选择器-取消
	@ViewInject(R.id.dialog_date_Y)
	TextView date_Y;// 日期选择器-确定
	@ViewInject(R.id.dialog_date_Title)
	TextView date_Title;// 日期选择器-标题
	@ViewInject(R.id.gyg_yybb_flmale)
	private ImageView imageView;
	@ViewInject(R.id.gyg_yybb_male)
	private ImageView imageView2;

	@OnClick({ R.id.gyg_yybb_back, R.id.gyg_yybb_date, R.id.gyg_yybb_flmale,
			R.id.gyg_yybb_male, R.id.gyg_yybb_yes, R.id.dialog_date_N,
			R.id.dialog_date_Y, })
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.gyg_yybb_back:
			startActivity(new Intent(this,GygActivity.class));
			finish();
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
			break;
		case R.id.gyg_yybb_date:
			ddate = datePickerShow(ddate, BIRTHDAY, R.layout.dialog_time_birday);
			break;
		case R.id.gyg_yybb_flmale:
			babySex = "女宝宝";
			MTApplication.mEditor.putString("BabySex", "女宝宝");
			MTApplication.mEditor.commit();
			break;
		case R.id.gyg_yybb_male:
			babySex = "男宝宝";
			MTApplication.mEditor.putString("BabySex", "男宝宝");
			MTApplication.mEditor.commit();
			break;
		case R.id.dialog_date_Y:
			if (month < 10 && day < 10) {
				birthDay.setText(year + "年" + month + "月" + day + "日");
				birthtime = year + "-" + month + "-" + day;
			} else if (month >= 10 && day < 10) {
				birthDay.setText(year + "年" + month + "月" + day + "日");
				birthtime = year + "-" + month + "-" + day;
			} else if (month < 10 && day >= 10) {
				birthDay.setText(year + "年" + month + "月" + day + "日");
				birthtime = year + "-" + month + "-" + day;
			} else {
				birthDay.setText(year + "年" + month + "月" + day + "日");
				birthtime = year + "-" + month + "-" + day;
			}
			ddate.dismiss();
			break;
		case R.id.dialog_date_N:
			ddate.dismiss();
			break;
		case R.id.gyg_yybb_yes:
			check();
			break;
		}
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_gyg_yybb);
	}

	@Override
	public void findViewById() {
		ViewUtils.inject(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(this,GygActivity.class));
		finish();
		overridePendingTransition(R.anim.slide_right_in,
				R.anim.slide_right_out);
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
		dialog = new Dialog(YybbActivity.this, R.style.FullHeightDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View v = View.inflate(YybbActivity.this, layoutId, null);
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
		WindowManager m = YybbActivity.this.getWindowManager();
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
		url = MTUtils.getMTParams(URLs.BabyInfo, "babystate", 0, "birthday",
				birthtime, "dueTime", null);
		SendActtionTool.get(url, ServiceAction.Action_Comment,
				UserAction.Action_BabyInfo, this);
		Logger.e("url----------------------------", url);
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
			MTApplication.mEditor.putString("BabyBirth", birthtime);
			MTApplication.mEditor.putString("BabyAgeD", BabyAgeD);
			MTApplication.mEditor.putInt("BabyAgeScope", BabyAgeScope);
			MTApplication.mEditor.putInt("BabyState", 0);
			MTApplication.mEditor.putString("BabyState2", "yes");
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
		// 用户选取生日时间
		long chose_birtime = MTUtils
				.getMillTime(year + "-" + month + "-" + day);
		if (MTUtils.isEmpty(birthDay.getText().toString().trim())
				|| birthDay.getText().toString().trim().equals("请选择出生日期")) {
			MTUtils.Toast(this, "请选择出生日期");
		} else if (chose_birtime > times) {
			MTUtils.Toast(this, "亲，生日不能选择今天之后哦~");
		} else if (!babySex.equals("男宝宝") && !babySex.equals("女宝宝")) {
			MTUtils.Toast(this, "请选择宝宝性别");
		} else {
			upData();
		}
	}
}
