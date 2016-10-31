package com.matao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;

/**
 * Created by zhouyang on 15/4/26.
 */
public class TopBar extends RelativeLayout {
	private TextView title;
	private ImageView left, right;
	private int titleColor;
	private Drawable tileImg, leftImg, rightImg;
	private String titleT;
	private float leftW, leftH, titleSize, rightW, rightH;
	private LayoutParams titleLp, leftLp, rightLp;
	private boolean rightVisiable, leftVisiable;

	public interface topBarClickListener {
		public void leftClick();

		public void rightClick();
	}

	public void setOnTopBarClickListener(topBarClickListener listener) {
		this.listener = listener;
	}

	public TopBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	public TopBar(Context context) {
		this(context, null);
	}

	private topBarClickListener listener;

	public TopBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	private void init(Context context, AttributeSet attrs, int defStyle) {
		TypedArray ta = context.obtainStyledAttributes(attrs,
				R.styleable.Topbar);

		titleColor = ta.getColor(R.styleable.Topbar_titleColor, Color.WHITE);
		tileImg = ta.getDrawable(R.styleable.Topbar_titleImg);
		titleT = ta.getString(R.styleable.Topbar_titleText);
		titleSize = ta.getDimension(R.styleable.Topbar_titleSize, 21);
		leftVisiable = ta.getBoolean(R.styleable.Topbar_leftVisiable, true);
		leftW = ta.getDimension(R.styleable.Topbar_leftW,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		leftH = ta.getDimension(R.styleable.Topbar_leftH,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		leftImg = ta.getDrawable(R.styleable.Topbar_leftImg);

		rightVisiable = ta.getBoolean(R.styleable.Topbar_rightVisiable, true);
		rightH = ta.getDimension(R.styleable.Topbar_rightH,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		rightW = ta.getDimension(R.styleable.Topbar_rightW,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		rightImg = ta.getDrawable(R.styleable.Topbar_rightImg);

		ta.recycle();
		title = new TextView(context);
		left = new ImageView(context);
		right = new ImageView(context);
		title.setSingleLine();
		title.setPadding(0, 10, 0, 0);
		title.setEllipsize(TruncateAt.END);
		title.setTypeface(MTApplication.jtz);
		title.setMaxWidth(MTUtils.getScreenWidth(context) - 200);
		if (!leftVisiable) {
			left.setVisibility(GONE);
		} else {
			left.setVisibility(VISIBLE);
		}
		if (rightVisiable) {
			right.setVisibility(VISIBLE);
		} else {
			right.setVisibility(GONE);
		}
		Logger.i("topBar---titleT--->", titleT);
		title.setText(titleT);
		title.setTextColor(titleColor);
		title.setTextSize((int) titleSize);
		title.setBackgroundDrawable(tileImg);
		title.setGravity(Gravity.CENTER);

		left.setImageDrawable(leftImg);
		left.setScaleType(ScaleType.FIT_CENTER);
		right.setImageDrawable(rightImg);
		right.setScaleType(ScaleType.FIT_CENTER);

		titleLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		titleLp.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);

		leftLp = new LayoutParams((int) leftW, (int) leftH);
		leftLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
		leftLp.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);

		rightLp = new LayoutParams((int) rightW, (int) rightH);
		rightLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
		rightLp.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);

		addView(title, titleLp);
		addView(left, leftLp);
		addView(right, rightLp);

		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.leftClick();
				}
			}
		});

		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.rightClick();
				}
			}
		});
	}

	public void setLeftVisiable(boolean flag) {
		if (flag) {
			left.setVisibility(VISIBLE);
		} else {
			left.setVisibility(GONE);
		}
	}

	public void setRightVisiable(boolean flag) {
		if (flag) {
			right.setVisibility(VISIBLE);
		} else {
			right.setVisibility(GONE);
		}
	}

	public void setTitle(String s) {
		title.setText(s);
	}

	public ImageView getLeftImageView() {
		return left;
	}

	public ImageView getRightImageView() {
		return right;
	}

	public TextView getTitleTextView() {
		return title;
	}

	public void setTypeface(Typeface tf) {
		title.setTypeface(tf);
	}
}
