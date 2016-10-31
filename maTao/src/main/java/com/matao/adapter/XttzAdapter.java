package com.matao.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.matao.activity.HomeActivity;
import com.matao.bean.HeadAdList;
import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.ViewHolder;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-7-13 上午11:02:48
 * @Description:系统通知列表适配器
 */
public class XttzAdapter extends CommonAdapter<HeadAdList> {
	private HomeActivity home;
	private int msgnum;

	/**
	 * @param context
	 * @param mDatas
	 * @param itemLayoutId
	 */
	public XttzAdapter(Context context, List<HeadAdList> mDatas,
			int itemLayoutId, int num) {
		super(context, mDatas, itemLayoutId);
		home = HomeActivity.getHomeAvtivity();
		msgnum = num;
	}

	@Override
	public void convert(final ViewHolder helper, final HeadAdList item, int position) {
		helper.setImageByUrl(R.id.xttz_icon, item.getHeadImage());
		helper.setText(R.id.xttz_name, item.getNickName());
		helper.setText(R.id.xttz_content, item.getNoticeContent());
		if (msgnum > position) {
			helper.getView(R.id.xttz_layout).setBackgroundResource(
					R.color.item_msg);
		} else {
			helper.getView(R.id.xttz_layout).setBackgroundResource(
					android.R.color.white);
		}
		Logger.e("msgnum----------------", msgnum);
		Logger.e("position----------------", position);
		if (msgnum > position) {
			helper.getView(R.id.xttz_layout).setBackgroundResource(R.color.msg);
		} else {
			helper.getView(R.id.xttz_layout).setBackgroundResource(R.color.set);
		}
		helper.getConvertView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				helper.getView(R.id.xttz_layout).setBackgroundResource(
						R.color.set);
				home.linkTo(item);
			}
		});
	}
}
