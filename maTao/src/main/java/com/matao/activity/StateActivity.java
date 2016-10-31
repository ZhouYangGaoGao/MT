package com.matao.activity;
/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-8-3 下午3:01:12
 * @Description:修改状态页面
 */
import java.util.Calendar;

import org.json.JSONException;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.matao.bean.BabystateBean;
import com.matao.bean.BabystateData;
import com.matao.bean.Bean;
import com.matao.bean.Data;
import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.MD5Util;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.utils.SendActtionTool;
import com.matao.utils.ServiceAction;
import com.matao.utils.URLs;
import com.matao.utils.UserAction;
import com.matao.view.MyDatePicker;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

public class StateActivity extends BaseActivity {
	// 初始化控件
	@ViewInject(R.id.perfect_topbar)
	TopBar topBar;// 顶部
	@ViewInject(R.id.perfect_Zhuangtai)
	TextView zhuangtai;// 当前状态
	@ViewInject(R.id.perfect_select_yuchanqi)
	TextView yuchanqi;// 选择预产期
	@ViewInject(R.id.perfect_babyNickName)
	EditText babyNickName;// 宝宝昵称
	@ViewInject(R.id.perfect_birthDay)
	TextView birthDay;// 宝宝生日
	@ViewInject(R.id.perfect_babySex)
	TextView babySex;// 宝宝性别
	@ViewInject(R.id.perfect_babyHospital)
	TextView babyHospital;// 宝宝出生医院
	@ViewInject(R.id.zt_arrow)
	ImageView imageView;
	@ViewInject(R.id.birth_arrow)
	ImageView imageView2;
	@ViewInject(R.id.sex_arrow)
	ImageView imageView3;
	@ViewInject(R.id.perfect_completRgst)
	Button complet;// 保存
	@ViewInject(R.id.babyname_layout)
	LinearLayout babyname_layout;
	@ViewInject(R.id.babystate_layout)
	LinearLayout layout_babystate;
	@ViewInject(R.id.babysex_layout)
	LinearLayout layout_babysex;
	@ViewInject(R.id.birthday_layout)
	LinearLayout layout_birthday;
	@ViewInject(R.id.perfect_haveBaby)
	LinearLayout haveBaby_layout;// 宝宝信息区域
	@ViewInject(R.id.perfect_yuchanqi_layout)
	LinearLayout yuchanqi_layout;// 预产期区域
	@ViewInject(R.id.dialog_zhuangtai_zhunbei)
	LinearLayout zhuangtai_zhunbei;// 状态-准备
	@ViewInject(R.id.dialog_zhuangtai_huaiyun)
	LinearLayout zhuangtai_huaiyun;// 状态-怀孕
	@ViewInject(R.id.dialog_zhuangtai_havebaby)
	LinearLayout zhuangtai_havebaby;// 状态-已有宝宝
	@ViewInject(R.id.dialog_date_datePicker)
	DatePicker date;// 日期选择器
	@ViewInject(R.id.dialog_date_Title)
	TextView date_Title;// 日期选择器-标题
	@ViewInject(R.id.dialog_date_N)
	TextView date_N;// 日期选择器-取消
	@ViewInject(R.id.dialog_date_Y)
	TextView date_Y;// 日期选择器-确定
	@ViewInject(R.id.dialog_sex_nan)
	LinearLayout nan;// 选择性别-男
	@ViewInject(R.id.dialog_sex_nv)
	LinearLayout nv;// 选择性别-女
	@ViewInject(R.id.dialog_sex_N)
	TextView sex_N;// 选择性别-取消

	public final int DATE = 1001;
	public final int BIRTHDAY = 1002;
	public final int GENDER = 1003;
	public final int ZHUANGTAI = 1004;
	private int dialogCode = DATE, babyState = -1, babyid;
	private boolean babyGender, isAmend;
	public static String mobileNumber = "";
	private String nick_Name, babyRealName, babysex, babyBirthday, dueDate,
			Hospital;
	private String flag = "", duedata = "", name = "", sex = "", shengri = "",
			hospital = "", yudate = "";
	// 记录当前的日期
	private int year;
	private int month;
	private int day;
	private Dialog d;
	private Dialog dzt;// 选择状态弹窗
	private Dialog dbbsex;// 选择宝宝性别弹窗
	private Dialog ddate;// 选择日期弹窗
	// 当前时间
	private long times = System.currentTimeMillis();

	// 点击事件 初始化和结果处理
	@OnClick({ R.id.birthday_layout, R.id.babystate_layout,
			R.id.babyname_layout, R.id.babysex_layout,
			R.id.dialog_zhuangtai_havebaby, R.id.dialog_zhuangtai_huaiyun,
			R.id.dialog_zhuangtai_zhunbei, R.id.perfect_select_yuchanqi,
			R.id.dialog_zhuangtai_N, R.id.dialog_date_N, R.id.dialog_date_Y,
			R.id.perfect_completRgst, R.id.dialog_sex_N, R.id.dialog_sex_nv,
			R.id.dialog_sex_nan, R.id.perfect_userProtocol })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.perfect_select_yuchanqi:// 选择预产期
			ddate = datePickerShow(ddate, DATE, R.layout.dialog_time_birday);
			break;
		case R.id.babyname_layout:// 选择宝宝名字
			if (isAmend == true && flag.equals("a")) {
				MTUtils.Toast(this, "宝宝信息已提交，不能再次修改");
			}
			break;
		case R.id.babysex_layout:// 选择宝宝性别
			if (isAmend == true && flag.equals("a")) {
				MTUtils.Toast(this, "宝宝信息已提交，不能再次修改");
			} else {
				dbbsex = showDialog(ddate, GENDER, R.layout.dialog_perfect_sex);
			}
			break;
		case R.id.dialog_sex_nv:
			babySex.setText("女宝宝");
			babyGender = true;
			dbbsex.dismiss();
			break;
		case R.id.dialog_sex_nan:
			babySex.setText("男宝宝");
			babyGender = false;
			dbbsex.dismiss();
			break;
		case R.id.dialog_sex_N:
			dbbsex.dismiss();
			break;
		case R.id.perfect_completRgst:
			if (babyState == 0) {
				if (isAmend == true) {
					if (!MTUtils.isEmpty(Hospital)
							&& MTUtils.getLength(Hospital) < 8) {
						MTUtils.Toast(this, " 医院内容不得少于 8 个字符哦~");
					} else if (!MTUtils.isEmpty(Hospital)
							&& MTUtils.getLength(Hospital) > 40) {
						MTUtils.Toast(this, " 医院内容不得多于 20 个字符哦~");
					}
					complete();
				} else {
					check();
				}
			} else {
				complete();
			}
			break;
		case R.id.babystate_layout:// 选择当前状态
			if (isAmend == true && flag.equals("a")) {
				MTUtils.Toast(this, "宝宝信息已提交，不能再次修改");
			} else {
				dzt = showDialog(dzt, ZHUANGTAI,
						R.layout.dialog_perfect_zhuangtai);
			}
			break;
		case R.id.dialog_zhuangtai_N:
			dzt.dismiss();
			break;
		case R.id.birthday_layout:// 选择宝宝生日
			if (isAmend == true && flag.equals("a")) {
				MTUtils.Toast(this, "宝宝信息已提交，不能再次修改");
			} else {
				ddate = datePickerShow(ddate, BIRTHDAY,
						R.layout.dialog_time_birday);
			}
			break;
		case R.id.dialog_date_N:
			ddate.dismiss();
			break;
		case R.id.dialog_date_Y:
			switch (dialogCode) {
			case DATE:
				if (month < 10 && day < 10) {
					yuchanqi.setText(year + "-0" + month + "-0" + day);
				} else if (month >= 10 && day < 10) {
					yuchanqi.setText(year + "-" + month + "-0" + day);
				} else if (month < 10 && day >= 10) {
					yuchanqi.setText(year + "-0" + month + "-" + day);
				} else {
					yuchanqi.setText(year + "-" + month + "-" + day);
				}
				break;
			case BIRTHDAY:
				if (month < 10 && day < 10) {
					birthDay.setText(year + "-0" + month + "-0" + day);
				} else if (month >= 10 && day < 10) {
					birthDay.setText(year + "-" + month + "-0" + day);
				} else if (month < 10 && day >= 10) {
					birthDay.setText(year + "-0" + month + "-" + day);
				} else {
					birthDay.setText(year + "-" + month + "-" + day);
				}
				break;
			}
			ddate.dismiss();
			break;
		case R.id.dialog_zhuangtai_havebaby:
			initZhuanT("已有宝宝", View.GONE, View.VISIBLE);
			yuchanqi.setText(null);
			// birthDay.setText("选择宝宝出生日期");
			babyState = 0;
			break;
		case R.id.dialog_zhuangtai_huaiyun:
			initZhuanT("已经怀孕", View.VISIBLE, View.GONE);
			yuchanqi.setText("选择预产期");
			birthDay.setText(null);
			babyState = 1;
			babyBirthday = null;
			break;
		case R.id.dialog_zhuangtai_zhunbei:
			initZhuanT("准备怀孕", View.GONE, View.GONE);
			yuchanqi.setText(null);
			birthDay.setText(null);
			babyState = 2;
			babyBirthday = null;
			break;
		}
	}

	// 保存逻辑判断方法
	private void complete() {
		// 判断用户是否修改，若未做修改直接返回上级页面，不上传
		String zt = zhuangtai.getText().toString();
		String yq = yuchanqi.getText().toString();
		String bn = babyNickName.getText().toString();
		String bs = babySex.getText().toString();
		String bb = birthDay.getText().toString();
		String bh = babyHospital.getText().toString();
		if (flag.equals("c") && zt.equals("准备怀孕")) {
			finish();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
		} else if (flag.equals("b") && yq.equals(yudate)) {
			finish();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
		} else if (flag.equals("a") && zt.equals("已有宝宝") && bn.equals(name)
				&& bs.equals(sex) && bb.equals(shengri) && bh.equals(hospital)) {
			finish();
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
		} else {
			upBabyState();
		}
	}

	// 上传宝宝信息
	private void upBabyState() {
		// 用户选取预产期时间
		long chose_yctime = MTUtils.getMillTime(year + "-" + month + "-" + day);
		// 用户选取生日时间
		// long chose_birtime = MTUtils
		// .getMillTime(year + "-" + month + "-" + day);
		babyRealName = babyNickName.getText().toString().trim();
		babysex = babySex.getText().toString().trim();
		if (babysex.equals("选择宝宝性别")) {
			babysex = "";
		}
		babyBirthday = birthDay.getText().toString().trim();
		if (babyBirthday.equals("选择宝宝生日")) {
			babyBirthday = "";
		}
		Hospital = babyHospital.getText().toString().trim();
		nick_Name = babyNickName.getText().toString().trim();
		switch (babyState) {
		// 已有宝宝
		case 0:
			dueDate = null;
			break;
		// 已经怀孕
		case 1:
			dueDate = yuchanqi.getText().toString().trim();
			nick_Name = null;
			babysex = null;
			babyBirthday = null;
			break;
		// 准备怀孕
		case 2:
			dueDate = null;
			nick_Name = null;
			babysex = null;
			babyBirthday = null;
			break;
		}
		// 判断所填信息是否为空
		if (babyState == -1) {
			MTUtils.Toast(this, "请选择当前状态");
		} else if (babyState == 0) {
			up();
		} else if (babyState == 1) {
			if (TextUtils.isEmpty(dueDate) || dueDate == null
					|| dueDate.equals("选择预产期")) {
				MTUtils.Toast(this, "请选择预产期");
			} else if (!TextUtils.isEmpty(dueDate) && chose_yctime < times) {
				MTUtils.Toast(this, "亲，预产期只能选择今天之后哦~");
			} else if (!TextUtils.isEmpty(dueDate)
					&& chose_yctime > (times + 280l * 24 * 3600 * 1000)) {
				Logger.i("times+280", times + 280l * 24 * 3600 * 1000);
				MTUtils.Toast(this, "亲，预产期可能不会超过280天 哦~");
			} else {
				up();
			}
		} else {
			up();
		}
	}

	// 请求方法
	public void up() {
		int userId = MTApplication.getInt("UserId");
		String token = MTApplication.getString("Token");
		String TimeStamp = MTUtils.getTimeStamp();

		org.json.JSONObject json = new org.json.JSONObject();
		try {
			json.put(
					"Sign",
					MD5Util.getLowerCaseMD5(userId + "" + babyid + ""
							+ TimeStamp + URLs.KEY));
			json.put("NickName", nick_Name);
			json.put("Gender", babyGender);
			json.put("Birthday", babyBirthday);
			json.put("DueDate", dueDate);
			json.put("BabyState", babyState);
			json.put("Hospital", Hospital);
			// 宝宝id
			json.put("Id", babyid);
			json.put("UserId", userId);
			json.put("UserToken", token);
			json.put("TimeStamp", TimeStamp);
			Logger.i("json_register==============>>>>>", json.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		RequestParams p = new RequestParams();
		p.addBodyParameter(URLs.JSON_INFO, json.toString());
		SendActtionTool.post(URLs.STATE, ServiceAction.Action_User,
				UserAction.Action_AmState, this, p);
	}

	// 请求接口上传修改成功后的宝宝信息
	private void upData() {
		String url = null;
		url = MTUtils.getMTParams(URLs.BabyInfo, "babystate", babyState,
				"birthday", babyBirthday, "dueTime", dueDate);
		SendActtionTool.get(url, ServiceAction.Action_Comment,
				UserAction.Action_BabyInfo, this);
		Logger.i("url-----------------------------", url);
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		switch ((UserAction) action) {
		case Action_AmState:
			Bean bean = JSON.parseObject(value.toString(), Bean.class);
			Data mdata = bean.getData();
			MTApplication.mEditor.putInt("BabyState", babyState);
			MTApplication.mEditor.putString("BabyState2", "yes");
			MTApplication.mEditor.putString("BabyDue", dueDate);
			MTApplication.mEditor.putString("BabyBirth", babyBirthday);
			MTApplication.mEditor.putString("BabySex", babySex.getText()
					.toString().trim());
			MTApplication.mEditor.commit();
			String str = bean.getMsg();
			boolean OperateResult = mdata.isOperateResult();
			if (OperateResult == true) {
				MTUtils.Toast(this, str);
				upData();
				setRst();
			} else {
				MTUtils.Toast(this, str);
			}
		case Action_BabyInfo:
			BabystateBean bean2 = JSON.parseObject(value.toString(), BabystateBean.class);
			BabystateData data = bean2.getData();
			String BabyAgeD = data.getBabyInfo();
			int BabyAgeScope = data.getBabyAgeScope();
			Logger.i("info+aged===========================", BabyAgeD
					+ BabyAgeScope);
			MTApplication.mEditor.putString("BabyDue", yuchanqi.getText()
					.toString().trim());
			MTApplication.mEditor.putString("BabyAgeD", BabyAgeD);
			MTApplication.mEditor.putInt("BabyAgeScope", BabyAgeScope);
			MTApplication.mEditor.commit();
			Intent intent = new Intent(this, HomeActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			finish();
			break;
		}
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		MTApplication.setLogin(false);
		Logger.i("----------------onFaile-------------", value.toString());
		org.json.JSONObject object;
		try {
			object = new org.json.JSONObject(value.toString());
			String str = object.optString("Msg", "");
			MTUtils.Toast(this, str);
			// showDialog(str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// 获取宝宝信息回传资料页面
	private void setRst() {
		Intent data = new Intent(this, ZiLiaoActivity.class);
		data.putExtra("babystate", babyState);
		data.putExtra("babysex", babySex.getText().toString());
		setResult(ZiLiaoActivity.CODE_AMEND_STATE, data);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
		finish();
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_state);
	}

	@Override
	public void findViewById() {
		ViewUtils.inject(this);
		topBar.setTitle("修改宝宝资料");
		complet.setText("保存");
		// 判断用户是否第一次修改信息，若不是将控件置为不可编辑状态
		Intent intent = getIntent();
		isAmend = intent.getBooleanExtra("isamend", false);
		// 根据资料页面宝宝状态填充相应状态页面信息
		babyid = intent.getIntExtra("babyid", 1);
		flag = intent.getStringExtra("flag");
		duedata = intent.getStringExtra("duedate");
		name = intent.getStringExtra("babyname");
		sex = intent.getStringExtra("babysex");
		shengri = intent.getStringExtra("birthday");
		hospital = intent.getStringExtra("hospital");
		yudate = intent.getStringExtra("yuchanqi");
		if (flag.equals("a")) {
			babyState = 0;
			haveBaby_layout.setVisibility(View.VISIBLE);
			zhuangtai.setText("已有宝宝");
			babyNickName.setText(name);
			babySex.setText(sex);
			birthDay.setText(shengri);
			babyHospital.setText(hospital);
			zhuangtai.setTextColor(this.getResources().getColor(
					R.color.pink_word));
			babyNickName.setTextColor(this.getResources().getColor(
					R.color.pink_word));
			babySex.setTextColor(this.getResources()
					.getColor(R.color.pink_word));
			birthDay.setTextColor(this.getResources().getColor(
					R.color.pink_word));
			babyHospital.setTextColor(this.getResources().getColor(
					R.color.pink_word));
		} else if (flag.equals("b")) {
			babyState = 1;
			yuchanqi_layout.setVisibility(View.VISIBLE);
			zhuangtai.setText(duedata);
			zhuangtai.setTextColor(this.getResources().getColor(
					R.color.pink_word));
			yuchanqi.setText(yudate);
			yuchanqi.setTextColor(this.getResources().getColor(
					R.color.pink_word));
		} else {
			babyState = 2;
			zhuangtai.setText("准备怀孕");
		}
		if (isAmend == true && flag.equals("a")) {
			babyNickName.setCursorVisible(false);
			babyNickName.setEnabled(false);
			zhuangtai.setTextColor(this.getResources().getColor(
					R.color.unchose_word));
			imageView.setVisibility(View.GONE);
			babyNickName.setTextColor(this.getResources().getColor(
					R.color.unchose_word));
			babySex.setTextColor(this.getResources().getColor(
					R.color.unchose_word));
			imageView2.setVisibility(View.GONE);
			birthDay.setTextColor(this.getResources().getColor(
					R.color.unchose_word));
			imageView3.setVisibility(View.GONE);
		}

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

	/**
	 * 用户选择当前状态后设置界面
	 * 
	 * @param zt
	 *            状态名称
	 * @param vsbYcq
	 *            选择预产期布局可见性
	 * @param vsbBbInfo
	 *            宝宝信息布局可见性
	 */
	private void initZhuanT(String zt, int vsbYcq, int vsbBbInfo) {
		zhuangtai.setText(zt);
		dzt.dismiss();
		yuchanqi_layout.setVisibility(vsbYcq);
		haveBaby_layout.setVisibility(vsbBbInfo);
	}

	/**
	 * 屏幕底部弹窗
	 */
	public Dialog showDialog(Dialog dialog, int dialogCode, int layoutId) {

		this.dialogCode = dialogCode;
		dialog = new Dialog(this, R.style.MmsDialogTheme);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View v = View.inflate(this, layoutId, null);
		ViewUtils.inject(this, v);
		dialog.setContentView(v);
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		Display dsp = getWindowManager().getDefaultDisplay();
		lp.width = (int) dsp.getWidth();
		lp.y = (int) dsp.getHeight();
		dialog.getWindow().setAttributes(lp);
		dialog.show();
		return dialog;
	}

	/**
	 * 日期选择
	 * 
	 * @param textView
	 * @return
	 */
	protected Dialog datePickerShow(Dialog dialog, int dialogCode, int layoutId) {
		this.dialogCode = dialogCode;
		dialog = new Dialog(StateActivity.this, R.style.FullHeightDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View v = View.inflate(StateActivity.this, layoutId, null);
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
		WindowManager m = StateActivity.this.getWindowManager();
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

	// 检查已有宝宝状态信息是否填写完整
	public void check() {
		// 用户选取生日时间
		long chose_birtime = MTUtils
				.getMillTime(year + "-" + month + "-" + day);
		babyRealName = babyNickName.getText().toString().trim();
		babysex = babySex.getText().toString().trim();
		if (babysex.equals("选择宝宝性别")) {
			babysex = "";
		}
		babyBirthday = birthDay.getText().toString().trim();
		if (babyBirthday.equals("选择宝宝生日")) {
			babyBirthday = "";
		}
		Hospital = babyHospital.getText().toString().trim();
		nick_Name = babyNickName.getText().toString().trim();
		// 判断所填信息是否为空
		if (babyState == -1) {
			MTUtils.Toast(this, "请选择当前状态");
		} else if (babyState == 0) {
			if (TextUtils.isEmpty(babyRealName.toString())
					|| babyNickName.toString() == "输入宝宝名字") {
				MTUtils.Toast(this, "请填写宝宝昵称");
			} else if (!MTUtils.checkBabyNickName(babyRealName.toString())) {
				MTUtils.Toast(this, "宝宝名字为字母和汉字");
			} else if (MTUtils.getLength(babyRealName) < 4) {
				MTUtils.Toast(this, "宝宝乳名不能小于4个字符");
			} else if (MTUtils.getLength(babyRealName) > 10) {
				MTUtils.Toast(this, "宝宝乳名不能超过10个字符");
			} else if (TextUtils.isEmpty(babyBirthday) || babyBirthday == null
					|| babyBirthday.equals("选择宝宝生日")) {
				MTUtils.Toast(this, "请选择出生日期");
			} else if (!TextUtils.isEmpty(babyBirthday)
					&& chose_birtime > times) {
				MTUtils.Toast(this, "亲，生日不能选择今天之后哦~");
			} else if (MTUtils.isEmpty(babysex)) {
				Logger.i("cc------------------------------", babysex);
				MTUtils.Toast(this, "请选择宝宝性别");
			} else if (!MTUtils.isEmpty(Hospital)
					&& MTUtils.getLength(Hospital) < 8) {
				MTUtils.Toast(this, " 医院内容不得少于 8 个字符哦~");
			} else if (!MTUtils.isEmpty(Hospital)
					&& MTUtils.getLength(Hospital) > 40) {
				MTUtils.Toast(this, " 医院内容不得多于 20 个字符哦~");
			} else if (isAmend == false) {
				d = MTUtils.showDialog(StateActivity.this,
						new OnClickListener() {
							@Override
							public void onClick(View v) {
								switch (v.getId()) {
								case R.id.dialog_N:
									d.dismiss();
									break;
								case R.id.dialog_Y:
									d.dismiss();
									complete();
									break;
								}
							}
						}, "取消", "确定", "请您仔细核对宝宝资料，提交后就不能再更改咯~");
			}
		}
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
}
