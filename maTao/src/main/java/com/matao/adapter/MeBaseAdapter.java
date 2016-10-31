package com.matao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-8-3 下午3:01:12
 * @Description:基础适配器
 */
public abstract class MeBaseAdapter<T> extends BaseAdapter {
	public List<T> list;
	public Context context;
	public LayoutInflater inflater;

	public MeBaseAdapter(List<T> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list != null && !list.isEmpty() ? list.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return createView(position, convertView, parent);
	}

	public abstract View createView(int position, View convertView,
			ViewGroup parent);

}
