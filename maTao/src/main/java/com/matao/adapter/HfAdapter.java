package com.matao.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.matao.activity.DetailsActivity;
import com.matao.activity.ReplyActivity;
import com.matao.bean.ReceiveCommentList;
import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.ViewHolder;

/**
 * @author: YeClazy
 * @E-mail: 18701120043@163.com
 * @version: 创建时间：2015-7-13 下午3:22:18
 * @Description: 回复列表适配器
 */
public class HfAdapter extends CommonAdapter<ReceiveCommentList> {

	private int msgnum;

	/**
	 * @param context
	 * @param mDatas
	 * @param itemLayoutId
	 */
	public HfAdapter(Context context, List<ReceiveCommentList> mDatas,
			int itemLayoutId, int num) {
		super(context, mDatas, itemLayoutId);
		this.mContext = context;
		msgnum = num;

	}

	@Override
	public void convert(final ViewHolder helper, final ReceiveCommentList item,
			int position) {
		OnClickListener clic = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				helper.getView(R.id.hf_layout2).setBackgroundResource(
						R.color.set);
				switch (arg0.getId()) {
				case R.id.hf_item_reply_click:
					Intent intent = new Intent(mContext, ReplyActivity.class);
					intent.putExtra("type", item.getType());
					intent.putExtra("ArticleId", item.getArticleId());
					intent.putExtra("ReplyId", item.getReplyId());
					// intent.putExtra("CommentUserId",
					// item.getCommentUserId());
					intent.putExtra("CommentNickName",
							item.getCommentNickName());
					mContext.startActivity(intent);
					break;
				case R.id.hf_layout:
					Intent intent2 = new Intent(mContext, DetailsActivity.class);
					intent2.putExtra("url", item.getUrl());
					mContext.startActivity(intent2);
				}
			}
		};
		helper.getView(R.id.hf_item_reply_click).setOnClickListener(clic);
		helper.getView(R.id.hf_layout).setOnClickListener(clic);
		helper.setImageByUrl(R.id.hf_icon, item.getAvatar());
		// 自定义文本显示
		String textTip = item.getCommentNickName() + "回复了我：" + " "
				+ item.getContent();
		String keyWord = item.getCommentNickName() + "回复了我：";
		SpannableStringBuilder style = new SpannableStringBuilder(textTip);
		style.setSpan(new ForegroundColorSpan(0xff444444),
				textTip.indexOf(keyWord),
				textTip.indexOf(keyWord) + keyWord.length(),
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		helper.setText(R.id.hf_name, style);

		helper.setText(R.id.hf_time, item.getAddDate());
		helper.setText(R.id.hf_title, item.getTitle());

		Logger.e("msgnum----------------", msgnum);
		Logger.e("position----------------", position);
		if (msgnum > position) {
			helper.getView(R.id.hf_layout2).setBackgroundResource(R.color.msg);
		} else {
			helper.getView(R.id.hf_layout2).setBackgroundResource(R.color.set);
		}

		if (position > lastPosition) {// 这里就是动画的应用
			Animation animation = AnimationUtils.loadAnimation(mContext,
					(position > lastPosition) ? R.anim.slide_bottom_to_top
							: R.anim.slide_top_to_bottom);
			helper.getConvertView().startAnimation(animation);
		}
		lastPosition = position;
	}

	int lastPosition = 0;
}
