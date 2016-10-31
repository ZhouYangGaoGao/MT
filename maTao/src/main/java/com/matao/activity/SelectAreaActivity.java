package com.matao.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.matao.adapter.CommonAdapter;
import com.matao.bean.City;
import com.matao.matao.R;
import com.matao.utils.ViewHolder;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-22 下午4:30:11
 * @Description: 选择完一级省市后， 显示二级市区再选择，再显示三级区县 选择，选择完后
 *               自动返回到完善资料页。（北京、上海、天津、重庆四个直辖市市，不需要选择三级区县）
 */

public class SelectAreaActivity extends BaseActivity implements
		OnItemClickListener {
	private ListView list;
	private String provinceName = "", cityName = "", areaName = "";
	private int provinceId = 0, cityId = 0, areaId = 0;
	private List<City> citys = new ArrayList<City>();
	private CommonAdapter<City> adapter;
	private TopBar topBar;

	// private String code;

	/**
	 * 设置返回结果数据
	 * 
	 * 作者:ZhouYang
	 * 
	 * 2015-5-20上午11:39:51
	 * 
	 */
	private void setRst() {
		Intent data = new Intent();
		data.putExtra("provinceName", provinceName);
		data.putExtra("provinceId", provinceId);
		data.putExtra("cityName", cityName);
		data.putExtra("cityId", cityId);
		data.putExtra("areaName", areaName);
		data.putExtra("areaId", areaId);
		setResult(PerfectActivity.CODE_SELECT_AREA, data);
		SelectAreaActivity.this.finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
	}

	/**
	 * 初始化列表
	 * 
	 * 作者:ZhouYang
	 * 
	 * 2015-5-20上午11:17:19
	 * 
	 */
	private void initList() {
		adapter = new CommonAdapter<City>(this, citys,
				R.layout.item_select_the_area) {
			@Override
			public void convert(ViewHolder h, City i, int position) {
				switch (x) {
				case 1:
					h.setText(R.id.select_the_area_item_city, i.getProvince());
					break;
				case 2:
					h.setText(R.id.select_the_area_item_city, i.getCityName());
					break;
				case 3:
					h.setText(R.id.select_the_area_item_city, i.getAreaName());
					break;
				}
			}
		};
		list.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		}
	}

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_select_the_area);

	}

	String json;

	@Override
	public void findViewById() {
		// 判断跳转页面
		// Intent intent = getIntent();
		// code = intent.getStringExtra("code");
		// 将json文件读取到buffer数组中
		try {
			InputStream is = this.getResources().getAssets().open("area.txt");
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			json = new String(buffer, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		list = (ListView) findViewById(R.id.select_the_area_list);
		topBar = (TopBar) findViewById(R.id.select_city_topbar);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		citys = JSON.parseArray(json, City.class);
		initList();
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

	int x = 1;// 层级

	@Override
	public void onItemClick(AdapterView<?> listView, View arg1, int i, long arg3) {
		City city = citys.get(i);
		switch (x) {
		case 1:
			provinceName = city.getProvince();
			provinceId = city.getProvinceId();
			List<City> cities = citys.get(i).getCityList();
			citys.clear();
			citys.addAll(cities);
			adapter.notifyDataSetChanged();
			x = 2;
			break;
		case 2:
			cityName = city.getCityName();
			cityId = city.getCityId();
			List<City> area = citys.get(i).getAreaList();
			if (area != null && area.size() != 0) {
				citys.clear();
				citys.addAll(area);
				adapter.notifyDataSetChanged();
				x = 3;
			} else {
				setRst();
			}
			break;
		case 3:
			areaName = city.getAreaName();
			provinceId = city.getAreaId();
			setRst();
			break;
		}

		adapter.notifyDataSetChanged();
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
