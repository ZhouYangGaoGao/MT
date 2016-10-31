package com.matao.adapter;import android.annotation.SuppressLint;import android.content.Context;import android.view.View;import android.view.ViewGroup;import android.widget.BaseAdapter;import android.widget.RelativeLayout;import com.matao.matao.R;import com.matao.utils.MTApplication;import com.matao.utils.MTUtils;import com.matao.view.RoundAngleImageView;/** * @author: ZhouYang * @E-mail: ZhouYangGaoGao@163.com * @time:2015-5-6 下午4:46:35 * @Description:经验列表中item图片适配器 */public class ExperienceGridAdapter extends BaseAdapter {	private Context context;	private String[] list;	private int withSpace;	public ExperienceGridAdapter(Context context, String[] list, int withSpace) {		super();		this.context = context;		this.list = list;		this.withSpace = withSpace;	}	@Override	public int getCount() {		return list.length;	}	@Override	public Object getItem(int position) {		return list[position];	}	@Override	public long getItemId(int position) {		return position;	}	@SuppressLint("ViewHolder")	@Override	public View getView(int position, View cv, ViewGroup parent) {		cv = View.inflate(context, R.layout.gridview_img_layout, null);		RoundAngleImageView img = (RoundAngleImageView) cv				.findViewById(R.id.experience_item_gridview_img);		RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) img				.getLayoutParams();		lp.height = (int) ((MTUtils.getScreenWidth(context) - withSpace) * 0.33333);		lp.width = lp.height;		img.setLayoutParams(lp);		MTApplication.bmu.display(img, list[position],				MTUtils.getConfig(context, R.drawable.matao_220));		cv.setFocusable(false);		return cv;	}}