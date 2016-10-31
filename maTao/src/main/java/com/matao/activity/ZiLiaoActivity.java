package com.matao.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.matao.bean.Bean;
import com.matao.bean.ZiliaoBean;
import com.matao.bean.ZiliaoData;
import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.MD5Util;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.utils.SendActtionTool;
import com.matao.utils.ServiceAction;
import com.matao.utils.URLs;
import com.matao.utils.UserAction;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-27 下午4:08:52
 * @Description:我的资料页面
 */

public class ZiLiaoActivity extends BaseActivity {
	@ViewInject(R.id.choise_img_pic)
	private TextView textView;
	@ViewInject(R.id.choise_img_phone)
	private TextView textView2;
	@ViewInject(R.id.choise_img_cancle)
	private TextView textView3;
	@ViewInject(R.id.btn_baba)
	private LinearLayout layout;
	@ViewInject(R.id.btn_mama)
	private LinearLayout layout2;
	@ViewInject(R.id.btn_cancle)
	private TextView layout3;

	private Dialog dialog, dialog2;
	public final int SEX = 102;
	public final int ICON = 101;
	private RelativeLayout icon, name, sex, place, band, state;
	private ImageView zl_icon, zl_bang;
	private TextView zl_name, zl_sex, zl_place, zl_state;
	private TopBar topBar;
	private File tempFile;
	private String bandNum, babydue, babysex, hospital, babyname, yuchanqi,
			birthday, nickname, stateStr;
	private int fg_state, babyId, stateid, sexid;
	private String provinceName = "", cityName = "", areaName;
	private int provinceId = 0, cityId = 0, areaId;
	private boolean isAmend;
	// 图片上传处理部分
	private static final int PHOTO_REQUEST_CAMERA = 701;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 801;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 901;// 结果
	// 我的资料相关
	public static final int CODE_AMEND_ICON = 1000;
	public static final int CODE_AMEND_NAME = 1001;
	public static final int CODE_SELECT_AREA = 1002;
	public final static int CODE_AMEND_STATE = 1003;
	// 头像名称
	private String PHOTO_FILE_NAME;

	public void init() {
		setContentView(R.layout.activity_ziliao);
		topBar = (TopBar) findViewById(R.id.wdxw_topbar);
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

	@OnClick({ R.id.choise_img_phone, R.id.choise_img_pic,
			R.id.choise_img_cancle, R.id.btn_baba, R.id.btn_mama,
			R.id.btn_cancle })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.zl_layouy_icon:
			dialog = showDialog(dialog2, ICON, R.layout.dialog_chose_img);
			break;
		case R.id.zl_layout_name:
			Intent intent = new Intent(ZiLiaoActivity.this,
					AmendNameActivity.class);
			intent.putExtra("name", zl_name.getText().toString());
			startActivityForResult(intent, CODE_AMEND_NAME);
			break;
		case R.id.zl_layout_sex:
			dialog2 = showDialog(dialog, SEX, R.layout.dialog_choose_sex);
			break;
		case R.id.zl_layout_place:
			Intent intent2 = new Intent(this, SelectAreaActivity.class);
			startActivityForResult(intent2, CODE_SELECT_AREA);
			break;
		case R.id.zl_layout_band:
			// 判断是否进行过绑定
			if (!MTUtils.isEmpty(bandNum)) {
				showDialog();
			} else {
				Intent intent3 = new Intent(this, BandActivity.class);
				// intent3.putExtra("bandnum", bandNum);
				startActivity(intent3);
			}
			break;
		case R.id.zl_layout_state:
			// 根据不用状态进入不同的修改状态界面
			String flag = null;
			if (fg_state == 0) {
				// 已有宝宝;
				flag = "a";
			} else if (fg_state == 1) {
				// 已经怀孕（babydue）
				flag = "b";
			} else {
				flag = "c";
				// 准备怀孕
			}
			Intent intent4 = new Intent(this, StateActivity.class);
			intent4.putExtra("isamend", isAmend);
			intent4.putExtra("flag", flag);
			intent4.putExtra("babyid", babyId);
			intent4.putExtra("duedate", babydue);
			intent4.putExtra("babyname", babyname);
			intent4.putExtra("babysex", babysex);
			intent4.putExtra("birthday", birthday);
			intent4.putExtra("hospital", hospital);
			intent4.putExtra("yuchanqi", yuchanqi);
			startActivityForResult(intent4, CODE_AMEND_STATE);
			break;
		case R.id.choise_img_phone:
			camera();
			dialog.dismiss();
			break;
		case R.id.choise_img_pic:
			gallery();
			dialog.dismiss();
			break;
		case R.id.choise_img_cancle:
			dialog.dismiss();
			break;
		case R.id.btn_baba:
			zl_sex.setText("爸爸");
			// 请求修改性别的接口
			upSex(0);
			dialog2.dismiss();
			break;
		case R.id.btn_mama:
			zl_sex.setText("妈妈");
			upSex(1);
			dialog2.dismiss();
			break;
		case R.id.btn_cancle:
			dialog2.dismiss();
			break;
		}
	}

	// ----------------------------------回调方法------------------------------------------------------
	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int ResultCode, Intent data) {
		super.onActivityResult(requestCode, ResultCode, data);
		switch (requestCode) {
		case PHOTO_REQUEST_GALLERY:
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				String path = MTUtils.getAbsoluteImagePath(this, uri);
				tempFile = new File(path);
				crop(uri);
			}
			break;
		case PHOTO_REQUEST_CAMERA:
			if (MTUtils.hasSdcard()) {
				tempFile = new File(Environment.getExternalStorageDirectory(),
						PHOTO_FILE_NAME);
				if (tempFile.length() != 0) {
					Uri uri = Uri.fromFile(tempFile);
					crop(uri);
				}
			} else {
				MTUtils.Toast(this, "未找到存储卡，无法存储照片！");
			}
			break;
		case PHOTO_REQUEST_CUT:
			try {
				if (data != null) {
					Uri uri = data.getData();
					// 获取头像存储路径
					String path = MTUtils.getAbsoluteImagePath(this, uri);
					// 将头像的存储路径存入本地缓存
					MTApplication.mEditor.putString("iconpath", path);
					Logger.i("PHOTO_REQUEST_CUT", path);
					// 根据选取的图片先将头像替换为最新
					zl_icon.setImageURI(uri);
					// 将新选取的头像图片进行上传
					tempFile = new File(path);
					uploadImg();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		// 接受最新修改昵称的回调方法
		case CODE_AMEND_NAME:
			if (data != null) {
				String name = data.getStringExtra("NickName");
				zl_name.setText(name);
			}
			break;
		// 接受最新修改地址的回调
		case CODE_SELECT_AREA:
			if (data != null) {
				provinceName = data.getStringExtra("provinceName");
				provinceId = data.getIntExtra("provinceId", 110000);
				cityName = data.getStringExtra("cityName");
				cityId = data.getIntExtra("cityId", 110000);
				areaName = data.getStringExtra("areaName");
				areaId = data.getIntExtra("areaId", 110000);
				if (!MTUtils.isEmpty(provinceName)) {
					zl_place.setText(provinceName + " " + cityName);
					upPlace();
				}
			}
			break;
		// 接受最新修改宝宝状态的回调
		case CODE_AMEND_STATE:
			if (data != null) {
				stateid = data.getIntExtra("babyState", 1);
				fg_state = stateid;
				if (fg_state == 0) {
					zl_state.setText("已有宝宝");
					stateStr = "已有宝宝";
					babysex = data.getStringExtra("babysex");
					if (babysex.equals("男宝宝")) {
						sexid = 0;
					} else {
						sexid = 1;
					}
				} else if (fg_state == 1) {
					zl_state.setText("已经怀孕");
					stateStr = "已经怀孕";
				} else {
					zl_state.setText("准备怀孕");
					stateStr = "准备怀孕";
				}
			}
			loadData();
		}
	}

	/**
	 * 获取最新资料页数据返回到我的小窝
	 */
	// private void setRst() {
	// Intent data = new Intent();
	// data.putExtra("headicon", MTApplication.getString("iconpath"));
	// data.putExtra("nickname", zl_name.getText().toString().trim());
	// data.putExtra("place", zl_place.getText().toString().trim());
	// data.putExtra("babystate", stateStr);
	// data.putExtra("babysex", sexid);
	// // data.putExtra("babyage", );
	// setResult(MyHouseActivity.CODE_HOUSE_ZILIAO, data);
	// // overridePendingTransition(R.anim.slide_left_in,
	// // R.anim.slide_left_out);
	// ZiLiaoActivity.this.finish();
	// }

	// ----------------------------接口请求----------------------------------------------------------------
	// 请求接口加载页面数据
	private void loadData() {
		String url = null;
		String TimeStamp = MTUtils.getTimeStamp();
		int UserId = MTApplication.getInt("UserId");
		String token = MTApplication.getString("Token");
		url = MTUtils.getMTParams(URLs.ZILIAO, URLs.USER_ID, UserId,
				"userToken", token, "k", TimeStamp);
		SendActtionTool.get(url, ServiceAction.Action_Comment,
				UserAction.Action_Ziliao, this);
	}

	// 请求接口上传头像
	private void uploadImg() {
		String TimeStamp = MTUtils.getTimeStamp();
		int userId = MTApplication.getInt("UserId");
		String token = MTApplication.getString("Token");
		org.json.JSONObject json = new org.json.JSONObject();
		try {
			json.put("Sign",
					MD5Util.getLowerCaseMD5(userId + TimeStamp + URLs.KEY));
			json.put(URLs.TIME_STAMP, TimeStamp);
			json.put("UserId", userId);
			json.put("UserToken", token);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		RequestParams p = new RequestParams();
		p.addBodyParameter(URLs.JSON_INFO, json.toString());
		Logger.i("json.toString()", json.toString());
		if (tempFile != null) {
			p.addBodyParameter(PHOTO_FILE_NAME, tempFile);
		}
		SendActtionTool.post(URLs.UPICON, ServiceAction.Action_User,
				UserAction.Action_Upicon, this, p);
	}

	// 请求接口修改性别
	private void upSex(int sexid) {
		String TimeStamp = MTUtils.getTimeStamp();
		int userId = MTApplication.getInt("UserId");
		String token = MTApplication.getString("Token");
		org.json.JSONObject json = new org.json.JSONObject();
		try {
			json.put(
					"Sign",
					MD5Util.getLowerCaseMD5(userId + "" + sexid + ""
							+ TimeStamp + URLs.KEY));
			json.put(URLs.TIME_STAMP, TimeStamp);
			json.put("UserId", userId);
			json.put("UserToken", token);
			json.put("TypeId", 2);
			json.put("Gender", sexid);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		RequestParams p = new RequestParams();
		p.addBodyParameter(URLs.JSON_INFO, json.toString());
		Logger.i("xiugaixingbie--------------------->", json.toString());
		SendActtionTool.post(URLs.AMEND, ServiceAction.Action_User,
				UserAction.Action_AmSex, this, p);
	}

	// 请求接口修改地址
	private void upPlace() {
		int userId = MTApplication.getInt("UserId");
		String token = MTApplication.getString("Token");
		String TimeStamp = MTUtils.getTimeStamp();
		org.json.JSONObject json = new org.json.JSONObject();
		try {
			json.put(
					"Sign",
					MD5Util.getLowerCaseMD5(userId + "" + provinceId + ""
							+ cityId + "" + areaId + "" + TimeStamp + URLs.KEY));
			json.put("ProvinceId", provinceId);
			json.put("Province", provinceName);
			json.put("CityId", cityId);
			json.put("City", cityName);
			json.put("AreaId", areaId);
			json.put("Area", areaName);
			json.put("TypeId", 1);
			json.put("UserToken", token);
			json.put(URLs.TIME_STAMP, TimeStamp);
			json.put("UserId", userId);
			Logger.i("json_register==============>>>>>", json.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		RequestParams p = new RequestParams();
		p.addBodyParameter(URLs.JSON_INFO, json.toString());
		SendActtionTool.post(URLs.AMEND, ServiceAction.Action_User,
				UserAction.Action_AmPlace, this, p);
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		Logger.i("babaysex------------------------?", value.toString());
		switch ((UserAction) action) {
		case Action_Ziliao:
			// 加载我的资料
			ZiliaoBean zlbean = JSON.parseObject(value.toString(),
					ZiliaoBean.class);
			ZiliaoData data = zlbean.getData();
			Logger.i("huodeshuju------->", value.toString());
			BitmapUtils utils = new BitmapUtils(this);
			utils.display(zl_icon, data.getUserHead());
			nickname = data.getNickName();
			babyId = data.getBabyId();
			zl_name.setText(nickname);
			// 获取性别
			int sexid = data.getGender();
			if (sexid == 2) {
				zl_sex.setText("请选择性别");
			} else if (sexid == 0) {
				zl_sex.setText("爸爸");
			} else {
				zl_sex.setText("妈妈");
			}
			zl_place.setText(data.getProvince() + " " + data.getCity());
			// 判断是否绑定
			bandNum = data.getMobileNumber();
			if (!MTUtils.isEmpty(bandNum)) {
				zl_bang.setImageResource(R.drawable.ico_phone_red);
			}
			// 判断状态
			fg_state = data.getBabyState();
			// 获取怀孕状态
			babydue = data.getDueDateInfo();
			if (fg_state == 0) {
				zl_state.setText("已有宝宝");
			} else if (fg_state == 1) {
				zl_state.setText(babydue);
			} else {
				zl_state.setText("准备怀孕");
			}

			babyname = data.getBabyName();
			birthday = data.getBabyBirthday();
			// 获取宝宝性别
			int babyse = data.getBabySex();
			if (babyse == 2) {
				babysex = "请选择性别";
			} else if (babyse == 0) {
				babysex = "男宝宝";
			} else {
				babysex = "女宝宝";
			}
			hospital = data.getHospital();
			// 预产期
			yuchanqi = data.getDueDate();
			// 判断宝宝状态是否被修改过
			isAmend = data.isIsModified();
			// if (isAmend == true) {
			// state.setClickable(false);
			// }
			break;

		case Action_Upicon:
			// 上传头像
			Bean bean = JSON.parseObject(value.toString(), Bean.class);
			String str = bean.getMsg();
			if (bean.getData().getPoint()>0) {
				MTUtils.JiFenToast(this, str);
			}else {
				MTUtils.Toast(this, str);
			}
			loadData();
			break;
		case Action_AmSex:
			// 修改性别
			Bean bean2 = JSON.parseObject(value.toString(), Bean.class);
			String str2 = bean2.getMsg();
			Logger.i("xiugaixingbie", bean2.getMsg());
			MTUtils.Toast(this, str2);
			loadData();
			break;
		case Action_AmPlace:
			// 修改地区
			Bean bean3 = JSON.parseObject(value.toString(), Bean.class);
			String str3 = bean3.getMsg();
			MTUtils.Toast(this, str3);
			loadData();
			break;
		}
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		Logger.i("shibai", value.toString());
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
		Logger.i("cuowu", value.toString());
	}

	@Override
	public void onFinish(ServiceAction service, Object action) {
		super.onFinish(service, action);
	}

	// ------------------------------上传头像相关-----------------------------------------------------------
	/*
	 * 从相册获取
	 */
	public void gallery() {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		// 将图片传递到onActivityResult方法中的：PHOTO_REQUEST_GALLERY
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
	}

	/*
	 * 从相机获取
	 */
	public void camera() {
		Intent intentc = new Intent("android.media.action.IMAGE_CAPTURE");
		// 判断存储卡是否可以用，可用进行存储
		if (MTUtils.hasSdcard()) {
			intentc.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), PHOTO_FILE_NAME)));
		}
		// 将图片传递到onActivityResult方法中的：PHOTO_REQUEST_CAMERA
		startActivityForResult(intentc, PHOTO_REQUEST_CAMERA);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
	}

	// 按质量压缩
	public void compressBmpToFile(Bitmap bmp) {
		tempFile = new File(Environment.getExternalStorageDirectory(),
				"upPhoto.jpg");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;// 压缩比例
		bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		Logger.i("baos大小", baos.toByteArray().length + "");
		while (baos.toByteArray().length / 1024 > 1024) {
			baos.reset();
			options -= 1;
			Logger.i("options大小", options + "");
			bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
			Logger.i("baos大小", baos.toByteArray().length + "");
		}
		try {
			FileOutputStream fos = new FileOutputStream(tempFile);
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
			bmp.recycle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 图片裁剪
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
	}

	// --------------------------------------------------------------------------------------------------
	@Override
	public void setContentView() {
		init();
		ViewUtils.inject(this);
		PHOTO_FILE_NAME = MTUtils.getTimeStamp() + ".jpg";
	}

	@Override
	public void findViewById() {
		icon = (RelativeLayout) findViewById(R.id.zl_layouy_icon);
		name = (RelativeLayout) findViewById(R.id.zl_layout_name);
		sex = (RelativeLayout) findViewById(R.id.zl_layout_sex);
		place = (RelativeLayout) findViewById(R.id.zl_layout_place);
		band = (RelativeLayout) findViewById(R.id.zl_layout_band);
		state = (RelativeLayout) findViewById(R.id.zl_layout_state);
		zl_icon = (ImageView) findViewById(R.id.zl_icon);
		zl_name = (TextView) findViewById(R.id.zl_nickname);
		zl_sex = (TextView) findViewById(R.id.zl_sex);
		zl_place = (TextView) findViewById(R.id.zl_province);
		zl_bang = (ImageView) findViewById(R.id.zl_band);
		zl_state = (TextView) findViewById(R.id.zl_state);
		icon.setOnClickListener(this);
		name.setOnClickListener(this);
		sex.setOnClickListener(this);
		place.setOnClickListener(this);
		band.setOnClickListener(this);
		state.setOnClickListener(this);
		// 加载本页数据
		loadData();
	}

	/**
	 * 屏幕底部弹窗
	 * 
	 * @param dialog
	 *            要显示的弹窗对象
	 * @param layoutId
	 *            布局Id
	 */
	public Dialog showDialog(Dialog dialog, int dialogCode, int layoutId) {

		dialog = new Dialog(this, R.style.FullHeightDialog);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View v = View.inflate(this, layoutId, null);
		ViewUtils.inject(ZiLiaoActivity.this, v);
		dialog.setContentView(v);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);
		dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
		WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
		p.width = MTUtils.getScreenWidth(this);// 宽度设置为屏幕的0.95
		dialogWindow.setAttributes(p);
		dialog.show();
		return dialog;
	}

	// 提示框
	private Dialog isLoadDialog;

	private void showDialog() {
		// 构造软件下载对话框
		isLoadDialog = new Dialog(ZiLiaoActivity.this, R.style.DialogLoginTip);
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater
				.from(ZiLiaoActivity.this);
		View v = inflater.inflate(R.layout.dialog, null);
		TextView textView = (TextView) v.findViewById(R.id.dialog_N);
		TextView textView2 = (TextView) v.findViewById(R.id.dialog_T);
		v.findViewById(R.id.dialog_Y).setVisibility(View.GONE);
		v.findViewById(R.id.dialog_L).setVisibility(View.GONE);
		textView.setText("我知道了");
		textView2.setText("嗨，淘友，您已绑定的号码是" + " " + bandNum + " "
				+ "您可以通过PC端进行修改！");
		OnClickListener isup = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.dialog_N:
					isLoadDialog.dismiss();
					break;
				}
			}
		};
		v.findViewById(R.id.dialog_N).setOnClickListener(isup);
		isLoadDialog.setContentView(v);
		WindowManager.LayoutParams lp = isLoadDialog.getWindow()
				.getAttributes();
		lp.width = MTUtils.getScreenWidth(ZiLiaoActivity.this) - 136;
		lp.height = -2;
		isLoadDialog.getWindow().setAttributes(lp);
		isLoadDialog.show();
	}
}
