package com.matao.activity;

import java.io.File;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.http.RequestParams;
import com.matao.bean.BabystateData;
import com.matao.bean.Bean;
import com.matao.bean.MyHouse;
import com.matao.matao.R;
import com.matao.pulltozoomview.PullToZoomScrollViewEx;
import com.matao.utils.Logger;
import com.matao.utils.MD5Util;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.utils.SendActtionTool;
import com.matao.utils.ServiceAction;
import com.matao.utils.URLs;
import com.matao.utils.UserAction;
import com.matao.view.RoundAngleImageView;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-27 下午4:08:52
 * @Description:我的小窝
 */

public class MyHouseActivity extends BaseActivity {
	private TopBar topBar;
	private ImageView icon, sex;
	private TextView name, babyname, fans, gz, TB;
	private int flag, Msgnum,Tbnum;
	private TextView tv_msgnum;
	/*
	 * 图片上传处理部分
	 */
	
	private static final int PHOTO_REQUEST_CAMERA = 401;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 501;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 601;// 结果
	public static final int CODE_HOUSE_ZILIAO = 100;// 头像

	/* 头像名称 */
	private String PHOTO_FILE_NAME;
	private File tempFile;
	// private Bitmap bitmap;
	private Dialog eduation_dialog;

	private String MobieNum;
	private static MyHouseActivity me;

	// private ImageView mBackgroundImageView = null;
	// private MyScrollView mScrollView = null;

	public static MyHouseActivity getInstence() {
		return me;
	}

	// 请求接口加载页面数据
	private void loadData() {
		String url = null;
		String TimeStamp = MTUtils.getTimeStamp();
		int CurrentUserId = MTApplication.getInt("UserId");
		url = MTUtils.getMTParams(URLs.MYHOUSE, URLs.CurrentUserId,
				CurrentUserId, URLs.OwnerUserId, CurrentUserId, "k", TimeStamp);
		Logger.i("小窝_url", url);

		SendActtionTool.get(url, ServiceAction.Action_Comment,
				UserAction.Action_Myhouse, this);
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
			// tempFile = null;
		}
		SendActtionTool.post(URLs.UPICON, ServiceAction.Action_User,
				UserAction.Action_Upicon, this, p);
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		switch ((UserAction) action) {
		// 加载小窝数据
		case Action_Myhouse:
			MyHouse house = JSON.parseObject(value.toString(), MyHouse.class);
			BabystateData data = house.getData();
			MTApplication.bmu.display(icon, house.getData().getAtaver());
			int sexid = house.getData().getBabyGender();
			if (sexid == 0) {
				sex.setImageResource(R.drawable.xw_ico_male);
			} else if (sexid == 1) {
				sex.setImageResource(R.drawable.xw_ico_female);
			} else {
				sex.setVisibility(View.GONE);
			}
			name.setText(house.getData().getNickName());
			if (!MTUtils.isEmpty(data.getBabyNickName())) {
				babyname.setText(data.getBabyNickName() + ","
						+ data.getBabyInfo() + "   " + data.getProvince() + " "
						+ data.getCity());
			} else {
				babyname.setText(data.getBabyInfo() + "  " + data.getProvince()
						+ " " + data.getCity());
			}

			fans.setText(house.getData().getFans() + "");
			gz.setText(house.getData().getFollows() + "");
			TB.setText(house.getData().getPoint() + "");
			Tbnum = house.getData().getPoint() ;
			MobieNum = house.getData().getMobileNumber();
			Msgnum = house.getData().getUnReadMegCount();
			Logger.e("msgnum-------------------", Msgnum);
			if (Msgnum != 0) {
				tv_msgnum.setVisibility(View.VISIBLE);
				tv_msgnum.setText(Msgnum + "");
				if (Msgnum >= 99) {
					tv_msgnum.setText("99");
				}
			} else {
				tv_msgnum.setVisibility(View.GONE);
			}
			break;
		// 上传头像
		case Action_Upicon:
			loadData();
			Bean bean = JSON.parseObject(value.toString(), Bean.class);
			String str = bean.getMsg();
			if (bean.getData().getPoint()>0) {
				MTUtils.JiFenToast(this, str);
			}else {
				MTUtils.Toast(this, str);
			}
			break;
		}
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
	}

	@Override
	public void onFinish(ServiceAction service, Object action) {
		super.onFinish(service, action);
	}

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
				// getimage(path);
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
					String path = MTUtils.getAbsoluteImagePath(this, uri);
					Logger.i("PHOTO_REQUEST_CUT", path);
					if (flag == 1) {
						// background.setImageURI(uri);
						MTApplication.mEditor.putString("00000", path);
						MTApplication.mEditor.commit();
//						String tr = MTApplication.getString("00000");
						tempFile = null;
					} else {
						// icon.setImageURI(uri);
						path = MTUtils.getAbsoluteImagePath(this, uri);
						// 将头像上传
						tempFile = new File(path);
						uploadImg();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		loadData();
	}

	// 弹出屏幕底部选择弹窗
	private void choiseImgUpdate() {
		View eatsView = View.inflate(this, R.layout.complete_choise_img, null);
		eduation_dialog = new Dialog(this, R.style.FullHeightDialog);
		TextView img_isee = (TextView) eatsView
				.findViewById(R.id.choise_img_see);
		img_isee.setVisibility(View.GONE);
		OnClickListener choiseListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.choise_img_phone:
					camera();
					eduation_dialog.dismiss();
					break;
				case R.id.choise_img_pic:
					gallery();
					eduation_dialog.dismiss();
					break;
				case R.id.choise_img_cancle:
					eduation_dialog.dismiss();
					break;
				}
			}
		};
		TextView title = (TextView) eatsView
				.findViewById(R.id.choise_img_title);
		if (flag == 1) {
			title.setText("更换背景图片");
		} else {
			title.setText("更换头像");
		}
		eatsView.findViewById(R.id.choise_img_phone).setOnClickListener(
				choiseListener);
		eatsView.findViewById(R.id.choise_img_pic).setOnClickListener(
				choiseListener);
		eatsView.findViewById(R.id.choise_img_cancle).setOnClickListener(
				choiseListener);

		eduation_dialog.setContentView(eatsView);
		eduation_dialog.setCanceledOnTouchOutside(true);
		Window dialogWindow = eduation_dialog.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);
		dialogWindow.getDecorView().setPadding(0, 0, 0, 0);

		WindowManager.LayoutParams p = eduation_dialog.getWindow()
				.getAttributes(); // 获取对话框当前的参数值
		p.width = MTUtils.getScreenWidth(this);// 宽度设置为屏幕的0.95
		dialogWindow.setAttributes(p);
		eduation_dialog.show();
	}

	/*
	 * 从相册获取
	 */
	public void gallery() {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		// 将图片传递到onActivityResult方法中的：PHOTO_REQUEST_GALLERY
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
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
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.click1:
			MTUtils.Toast(this, "正在开发中，敬请期待！");
			// Intent i = new Intent(this, TaHouseActivity.class);
			// startActivity(i);
			break;
		case R.id.click2:
			MTUtils.Toast(this, "正在开发中，敬请期待！");
			break;
		case R.id.click3://积分兑换中心
			Intent i = new Intent(this, ExchangeActivity.class);
			i.putExtra("Tbnum", Tbnum);
			startActivity(i);
			break;
		case R.id.wdxw_icon_set:
			flag = 2;
			choiseImgUpdate();
			break;
		case R.id.top:// 跳转资料页面
			Intent intent = new Intent(this, ZiLiaoActivity.class);
			startActivityForResult(intent, CODE_HOUSE_ZILIAO);
			break;
		case R.id.wdxw_1_click:// 跳转消息页面
			Intent intent2 = new Intent(this, MyMsgActivity.class);
			Msgnum = 0;
			startActivity(intent2);
			break;
		case R.id.wdxw_2_click:// 跳转晒单页面
			Intent i1 = new Intent(this, MyLikeActivity.class);
			i1.putExtra("Channel", 3);
			startActivity(i1);
			break;
		case R.id.wdxw_3_click:// 跳转经验页面
			Intent i2 = new Intent(this, MyLikeActivity.class);
			i2.putExtra("Channel", 2);
			startActivity(i2);
			break;
		case R.id.wdxw_4_click:// 跳转喜欢页面
			Intent i3 = new Intent(this, MyLikeActivity.class);
			startActivity(i3);
			break;
		}
	}

	@Override
	public void setContentView() {
		me = this;
		setContentView(R.layout.myhouse);
		PHOTO_FILE_NAME = MTUtils.getTimeStamp() + ".jpg";
	}

	@Override
	public void findViewById() {
		loadViewForCode();
		scrollView = (PullToZoomScrollViewEx) findViewById(R.id.scroll_view);
		// scrollView.getPullRootView().findViewById(R.id.tv_test1)
		// .setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// }
		// });

		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
//		int mScreenHeight = localDisplayMetrics.heightPixels;
		int mScreenWidth = localDisplayMetrics.widthPixels;
		LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(
				mScreenWidth, (int) (5.0F * (mScreenWidth / 16.0F)));
		scrollView.setHeaderLayoutParams(localObject);

		icon = (RoundAngleImageView) findViewById(R.id.wdxw_icon_set);
		sex = (ImageView) findViewById(R.id.wdxw_sex);
		name = (TextView) findViewById(R.id.wdxw_name);
		babyname = (TextView) findViewById(R.id.wdxw_babyname);
		fans = (TextView) findViewById(R.id.fans_num);
		gz = (TextView) findViewById(R.id.gz_num);
		TB = (TextView) findViewById(R.id.TB_num);
		icon.setOnClickListener(this);
		tv_msgnum = (TextView) findViewById(R.id.msgnum);
		topBar = (TopBar) findViewById(R.id.wdxw_topbar);
		topBar.setOnTopBarClickListener(new topBarClickListener() {

			@Override
			public void leftClick() {
				finish();
			}

			@Override
			public void rightClick() {
				Intent intent = new Intent(MyHouseActivity.this,
						SetActivity.class);
				intent.putExtra("telephone", MobieNum);
				// 跳转设置页面
				startActivity(intent);
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_left_out);

			}
		});
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}

	private PullToZoomScrollViewEx scrollView;

	private void loadViewForCode() {
		PullToZoomScrollViewEx scrollView = (PullToZoomScrollViewEx) findViewById(R.id.scroll_view);
		View headView = LayoutInflater.from(this).inflate(
				R.layout.my_head_view, null, false);
		View zoomView = LayoutInflater.from(this).inflate(
				R.layout.my_zoom_view, null, false);
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.my_content_view, null, false);
		scrollView.setHeaderView(headView);
		scrollView.setZoomView(zoomView);
		scrollView.setScrollContentView(contentView);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
