package com.matao.view;import android.content.Context;import android.util.AttributeSet;import android.widget.GridView;/** * @author zhouyang *  */public class NoSorceGridView extends GridView {	public NoSorceGridView(Context context) {		super(context);	}	public NoSorceGridView(Context context, AttributeSet attrs) {		super(context, attrs);	}	public NoSorceGridView(Context context, AttributeSet attrs, int defStyle) {		super(context, attrs, defStyle);	}	@Override	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {		// 根据模式计算每个child的高度和宽度		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,				MeasureSpec.AT_MOST);		super.onMeasure(widthMeasureSpec, expandSpec);	}}