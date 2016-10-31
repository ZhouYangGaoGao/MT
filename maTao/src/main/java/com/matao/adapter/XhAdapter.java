package com.matao.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.matao.activity.DetailsActivity;
import com.matao.activity.TaHouseActivity;
import com.matao.bean.LikeLogList;
import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.ViewHolder;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-7-13 上午11:02:48
 * @Description:喜欢列表适配器
 */
public class XhAdapter extends CommonAdapter<LikeLogList> {
	private int msgnum ;

	/**
	 * @param context
	 * @param mDatas
	 * @param itemLayoutId
	 * @param msg_num
	 */
	public XhAdapter(Context context, List<LikeLogList> mDatas,
			int itemLayoutId, int num) {
		super(context, mDatas, itemLayoutId);
		this.mContext = context;
		msgnum = num;
	}

	@Override
	public void convert(final ViewHolder helper, final LikeLogList item, int position) {
		// TODO Auto-generated method stub
		helper.setImageByUrl(R.id.xh_icon, item.getAvatar());
		helper.setImageByUrl(R.id.xh_img, item.getImgUrl());
		helper.setText(R.id.xh_contact, item.getContent());
		OnClickListener clic = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				helper.getView(R.id.xh_layout).setBackgroundResource(
						R.color.set);
				switch (arg0.getId()) {
				case R.id.xh_layout:
					Intent intent = new Intent(mContext, DetailsActivity.class);
					intent.putExtra("url", item.getUrl());
					mContext.startActivity(intent);
					break;
				case R.id.xh_icon:
					Intent intent2= new Intent(mContext, TaHouseActivity.class);
					intent2.putExtra("OwnerUserId", item.getUserId());
					mContext.startActivity(intent2);
					break;
				}
			}
		};
		helper.getView(R.id.xh_layout).setOnClickListener(clic);
		helper.getView(R.id.xh_icon).setOnClickListener(clic);

		Logger.e("msgnum----------------", msgnum);
		Logger.e("position----------------", position);
		if (msgnum > position) {
			helper.getView(R.id.xh_layout).setBackgroundResource(
			R.color.item_msg);
		}else {
			helper.getView(R.id.xh_layout).setBackgroundResource(
			R.color.set);
		}
	}
}
