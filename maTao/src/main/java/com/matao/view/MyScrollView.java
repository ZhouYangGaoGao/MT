//   登录
//首页 专栏 专家 热文
//淡定从容的专栏
//[原]【Android】高仿QQ的上下回弹效果之自定义的ScrollView
//2014-7-23阅读296 评论0
//
//1.概述
//  QQ是大家都很熟悉和常用的软件，Android版的QQ有这样一个效果，在很多页面中，如果用户下拉或上拉页面，会出现整个页面一起上下移动的效果。下面两幅图片分别是QQ原始效果和作者的模仿效果。
//
//
//
//2.思想简述
// 用ScrollView中的第一个孩子初始位置作为ScrollView的初始位置，并利用一个Rect对象来保存这个初始位置。利用dispatchTouchEvent（MotionEvent event）来分派touch事件，在其移动过程中计算相邻两次MotionEvent.ACTION_UP间，ScrollView的相对位移并更新其最新的位置。当手指弹起时，根据相对位移初始移动动画效果，最后将ScrollView的位置设置为其初始位置(有一个Rect对象记录这个位置).
//
//3.代码实现
/**
 * 
 */
package com.matao.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Description: 自定义的ScrollView--具有回缩弹性效果
 * 
 * @author danDingCongRong
 * @Version 1.0.0
 * @Created at 2014-7-23 01:44:34
 * @Modified by [作者] on [修改日期]
 */
public class MyScrollView extends ScrollView {

	// y方向上当前触摸点的前一次记录位置
	private int previousY = 0;
	// y方向上的触摸点的起始记录位置
	private int startY = 0;
	// y方向上的触摸点当前记录位置
	private int currentY = 0;
	// y方向上两次移动间移动的相对距离
	private int deltaY = 0;

	// 第一个子视图
	private View childView;

	// 用于记录childView的初始位置
	private Rect topRect = new Rect();

	public MyScrollView(Context context) {
		super(context);
		;
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		;
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		;
	}

	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			childView = getChildAt(0);
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (null == childView) {
			return super.dispatchTouchEvent(event);
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = (int) event.getY();
			previousY = startY;
			break;
		case MotionEvent.ACTION_MOVE:
			currentY = (int) event.getY();
			deltaY = previousY - currentY;
			previousY = currentY;

			if (0 == getScrollY()
					|| childView.getMeasuredHeight() - getHeight() <= getScrollY()) {
				// 记录childView的初始位置
				if (topRect.isEmpty()) {
					topRect.set(childView.getLeft(), childView.getTop(),
							childView.getRight(), childView.getBottom());
				}

				// 更新childView的位置
				childView.layout(childView.getLeft(), childView.getTop()
						- deltaY / 3, childView.getRight(),
						childView.getBottom() - deltaY / 3);
			}
			break;
		case MotionEvent.ACTION_UP:
			if (!topRect.isEmpty()) {
				upDownMoveAnimation();
				// 子控件回到初始位置
				childView.layout(topRect.left, topRect.top, topRect.right,
						topRect.bottom);
			}

			startY = 0;
			currentY = 0;
			topRect.setEmpty();
			break;
		default:
			break;
		}

		return super.dispatchTouchEvent(event);
	}

	// 初始化上下回弹的动画效果
	private void upDownMoveAnimation() {
		TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f,
				childView.getTop(), topRect.top);
		animation.setDuration(200);
		animation.setInterpolator(new AccelerateInterpolator());
		childView.setAnimation(animation);
	}

}


