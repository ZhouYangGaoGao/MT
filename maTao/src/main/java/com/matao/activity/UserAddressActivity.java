package com.matao.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.matao.bean.Bean;
import com.matao.bean.Data;
import com.matao.matao.R;
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
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-8-3 下午3:00:40
 * @Description:用户收货地址
 */
public class UserAddressActivity extends BaseActivity {
	@ViewInject(R.id.sh_name)
	private EditText editText;
	@ViewInject(R.id.sh_phone)
	private EditText editText2;
	@ViewInject(R.id.sh_detilplace)
	private EditText editText3;
	@ViewInject(R.id.sh_qq)
	private EditText editText4;
	@ViewInject(R.id.sh_email)
	private EditText editText5;
	@ViewInject(R.id.sh_place)
	private EditText editText6;
	@ViewInject(R.id.sh_yes)
	private TextView textView;
	@ViewInject(R.id.sh_searcharea)
	private LinearLayout layout;
	public static final int CODE_SELECT_AREA = 1000;
	private String Place, Detailplace, RealName, Address, Telephone, Qq, Email;
	private String provinceName, cityName, areaName;
	private int provinceId = 0, cityId = 0, areaId = 0;
	private int GoodsId, Coin;

	@OnClick({ R.id.sh_searcharea, R.id.sh_yes })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sh_searcharea:
			startActivityForResult(new Intent(this, SelectAreaActivity.class),
					CODE_SELECT_AREA);
			overridePendingTransition(R.anim.slide_left_in,
					R.anim.slide_left_out);
			break;
		case R.id.sh_yes:
			getUserinfo();
			break;
		}
	}

	@Override
	protected void onActivityResult(int rq, int rs, Intent it) {
		super.onActivityResult(rq, rs, it);
		if (it != null) {
			switch (rq) {
			case CODE_SELECT_AREA:
				provinceName = it.getStringExtra("provinceName");
				provinceId = it.getIntExtra("provinceId", 110000);
				cityName = it.getStringExtra("cityName");
				cityId = it.getIntExtra("cityId", 110000);
				areaName = it.getStringExtra("areaName");
				areaId = it.getIntExtra("areaId", 110000);
				editText6.setText(provinceName + cityName + areaName);
				break;
			}
		}
	}

	// 获取并检查收货地址信息
	public void getUserinfo() {
		RealName = editText.getText().toString().trim();
		Telephone = editText2.getText().toString().trim();
		Place = editText6.getText().toString().trim();
		Detailplace = editText3.getText().toString().trim();
		Qq = editText4.getText().toString().trim();
		Email = editText5.getText().toString().trim();
		if (MTUtils.isEmpty(RealName)) {
			MTUtils.Toast(this, "请填写收货人姓名");
		} else if (MTUtils.isEmpty(Telephone)) {
			MTUtils.Toast(this, "请填写收货人联系电话");
		} else if (MTUtils.isEmpty(Place)) {
			MTUtils.Toast(this, "请填写收货地址");
		} else if (MTUtils.isEmpty(Detailplace)) {
			MTUtils.Toast(this, "请填写收货详细地址");
		} else if (MTUtils.isEmpty(Qq)) {
			MTUtils.Toast(this, "请填写收货人QQ");
		} else if (MTUtils.isEmpty(Email)) {
			MTUtils.Toast(this, "请填写收货人Email");
		} else {
			wancheng();
		}
	}

	// 发送请求获取收货地址
	public void loadData() {
		String url = null;
		int UserId = MTApplication.getInt("UserId");
		url = MTUtils.getMTParams(URLs.UserAddress, "UserId", UserId);
		// 发送get请求
		SendActtionTool.get(url, ServiceAction.Action_Comment,
				UserAction.Action_GetUserAddress, this);
	}

	// 提交收货地址完成兑换产品请求接口
	private void wancheng() {
		String TimeStamp = MTUtils.getTimeStamp();
		org.json.JSONObject json = new org.json.JSONObject();
		int UserId = MTApplication.getInt("UserId");
		String UserToken = MTApplication.getString("Token");
		try {
			json.put(
					"Sign",
					MD5Util.getLowerCaseMD5(GoodsId + "" + Coin + ""
							+ UserToken + TimeStamp + URLs.KEY));
			json.put("UserId", UserId);
			json.put("GoodsId", GoodsId);
			json.put("Coin", Coin);
			json.put("RealName", RealName);
			json.put("Address", Place + Detailplace);
			json.put("Telephone", Telephone);
			json.put("Qq", Qq);
			json.put("Email", Email);
			json.put("Province", provinceName);
			json.put("City", cityName);
			json.put("Area", areaName);
			json.put("ProvinceId", provinceId);
			json.put("CityId", cityId);
			json.put("AreaId", areaId);
			json.put("UserToken", UserToken);
			json.put("TimeStamp", TimeStamp);
		} catch (org.json.JSONException e) {
			e.printStackTrace();
		}
		RequestParams p = new RequestParams();
		p.addBodyParameter(URLs.JSON_INFO, json.toString());
		SendActtionTool.post(URLs.WcDuihuan, ServiceAction.Action_User,
				UserAction.Action_WcDuihuan, this, p);
	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		// TODO Auto-generated method stub
		super.onSuccess(service, action, value);
		switch ((UserAction) action) {
		// 获取已有用户收货地址
		case Action_GetUserAddress:
			Bean bean = JSON.parseObject(value.toString(), Bean.class);
			Data data = bean.getData();
			editText.setText(data.getName());
			editText2.setText(data.getMobile());
			editText6.setText(data.getProvince() + data.getCity() + data.getArea());
			provinceName = data.getProvince();
			provinceId = data.getProvinceId();
			cityName = data.getCity();
			cityId = data.getCityId();
			areaName = data.getArea();
			areaId = data.getAreaId();
			editText3.setText(data.getStreet());
			editText4.setText(data.getQQ());
			editText5.setText(data.getEmail());
			break;
		// 提交收货地址完成兑换
		case Action_WcDuihuan:
			Bean bean2 = JSON.parseObject(value.toString(), Bean.class);// 获得数据实体
			Data data2 = bean2.getData();
			String Msg = bean2.getMsg();
			if (bean2.getCode() == 0 && data2.isOperateResult() == true) {// 执行成功跳转
				MTUtils.Toast(this, "兑换成功");
				finish();
				overridePendingTransition(R.anim.slide_left_in,
						R.anim.slide_left_out);
			}
			break;
		}
	}

	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		Bean bean = JSON.parseObject(value.toString(), Bean.class);
		String Msg = bean.getMsg();
		MTUtils.Toast(this, Msg);
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_shouhuo);
		ViewUtils.inject(this);
		loadData();
	}

	@Override
	public void findViewById() {
		Intent intent = getIntent();
		GoodsId = intent.getIntExtra("GoodsId", 0);
		Coin = intent.getIntExtra("Coin", 0);

		TopBar topBar = (TopBar) findViewById(R.id.sh_topbar);
		topBar.setOnTopBarClickListener(new topBarClickListener() {
			@Override
			public void rightClick() {
			}

			@Override
			public void leftClick() {
				UserAddressActivity.this.finish();
				// overridePendingTransition(R.anim.slide_left_in,
				// R.anim.slide_left_out);
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

}
