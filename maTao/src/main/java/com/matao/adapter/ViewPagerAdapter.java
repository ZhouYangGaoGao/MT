package com.matao.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-29 上午10:49:04
 * @Description:首页广告位适配器
 */

public class ViewPagerAdapter extends PagerAdapter {
	private List<ImageView> imgs;

	@Override
	public int getCount() {
		return imgs.size();
	}

	public ViewPagerAdapter(List<ImageView> imgs) {
		super();
		this.imgs = imgs;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(imgs.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(imgs.get(position));
		return imgs.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

}
