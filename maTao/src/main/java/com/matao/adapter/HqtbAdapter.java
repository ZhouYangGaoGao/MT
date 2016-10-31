package com.matao.adapter;
/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-8-3 下午3:01:12
 * @Description:获取淘币适配器
 */
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.matao.bean.TaskList;
import com.matao.matao.R;
import com.matao.view.RoundAngleImageView;

public class HqtbAdapter extends MeBaseAdapter<TaskList> {

	public HqtbAdapter(List<TaskList> list, Context context) {
		super(list, context);
	}

	@Override
	public View createView(int position, View convertView, ViewGroup parent) {
		ViewHold hold;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_hqtb, null);
			hold = new ViewHold();
			// 实例化
			hold.image = (RoundAngleImageView) convertView
					.findViewById(R.id.hqtb_icon);
			hold.textView = (TextView) convertView
					.findViewById(R.id.hqtb_style);
			hold.textView2 = (TextView) convertView
					.findViewById(R.id.hqtb_tbnum);
			hold.textView3 = (TextView) convertView
					.findViewById(R.id.hqtb_rule);

			convertView.setTag(hold);
		} else {
			hold = (ViewHold) convertView.getTag();
		}

		hold.textView.setText(list.get(position).getName());
		hold.textView2.setText(list.get(position).getPoint() + "");
		hold.textView3.setText(list.get(position).getType());

		BitmapUtils bitmapUtils = new BitmapUtils(context);
		bitmapUtils.display(hold.image, list.get(position).getImgUrl());

		return convertView;
	}

	class ViewHold {
		RoundAngleImageView image;
		TextView textView, textView2, textView3;
	}

}
