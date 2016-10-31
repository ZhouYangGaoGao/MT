/**
 *TODO
 *
 *
 */
package com.matao.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.matao.adapter.BQGridAdapter;
import com.matao.matao.R;

/**
 * @author: ZhouYang
 * @time:2015-5-26 下午6:23:54
 * @Description:表情fragment
 */

public class BQFragment extends BaseFragment implements OnItemClickListener {
	@ViewInject(R.id.reply_layout_biaoqing_grid)
	private GridView gridView;
	private int x = 0;
	private BQClickListener listener;

	public BQFragment(int x, BQClickListener listener) {
		super();
		this.x = x;
		this.listener = listener;
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public View setContentView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.biaoqing_gridview, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initView(View v) {
		gridView.setAdapter(new BQGridAdapter(getActivity(), x));
		gridView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int p, long arg3) {
		if (p == 20 || (x == 4 && p == 10)) {
			listener.delet();
		} else {
			listener.click(x * 20 + p);
		}
	}

	public interface BQClickListener {
		void click(int p);

		void delet();
	}
}
