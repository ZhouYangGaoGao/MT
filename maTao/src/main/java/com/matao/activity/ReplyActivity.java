package com.matao.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.matao.adapter.CommonAdapter;
import com.matao.adapter.ExperienceGridAdapter;
import com.matao.adapter.FPAdapter;
import com.matao.bean.BQ;
import com.matao.bean.Bean;
import com.matao.bean.DaTePager;
import com.matao.bean.ReplyList;
import com.matao.fragment.BQFragment;
import com.matao.fragment.BQFragment.BQClickListener;
import com.matao.matao.R;
import com.matao.pulltorefresh.library.PullToRefreshBase;
import com.matao.pulltorefresh.library.PullToRefreshListView;
import com.matao.pulltorefresh.library.PullToRefreshBase.Mode;
import com.matao.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.matao.utils.Logger;
import com.matao.utils.MD5Util;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.utils.SendActtionTool;
import com.matao.utils.ServiceAction;
import com.matao.utils.URLs;
import com.matao.utils.UserAction;
import com.matao.utils.ViewHolder;
import com.matao.view.NoSorceGridView;
import com.matao.view.RoundAngleImageView;
import com.matao.view.SmileyParser;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-5-25 下午4:08:52
 * @Description:所有评论界面
 */
@ContentView(R.layout.activity_reply)
public class ReplyActivity extends BaseActivity implements
		OnFocusChangeListener, OnRefreshListener2<ListView>,
		OnItemLongClickListener, OnPageChangeListener, OnScrollListener,
		topBarClickListener {
	@ViewInject(R.id.reply_topbar)
	private TopBar topBar;
	@ViewInject(R.id.reply_listview)
	private PullToRefreshListView listView;
	@ViewInject(R.id.reply_replyButton)
	private Button reply;// 输入框右边 回复
	@ViewInject(R.id.reply_inputEdit)
	private EditText editText;// 输入框
	@ViewInject(R.id.reply_inputIco)
	private ImageView inputIco;// 输入框左边指示图标
	@ViewInject(R.id.reply_pic)
	private RoundAngleImageView pic;// 输入框左边添加图片图标
	@ViewInject(R.id.reply_layout_biaoqing)
	private LinearLayout biaoqing;// 表情的布局
	@ViewInject(R.id.reply_layout_biaoqing_dot)
	private LinearLayout dot;// 表情页码指示图标
	@ViewInject(R.id.anim)
	private TextView aini;
	@ViewInject(R.id.favorable_tip)
	TextView tip_doData;
	@ViewInject(R.id.favorable_bg)
	TextView bg;
	private View foot;
	// private TextView footer;
	@ViewInject(R.id.reply_layout_biaoqing_ViewPager)
	private ViewPager bqPager;// 表情翻页viewpager

	private AnimationDrawable aniDraw;
	private Dialog dialog;// 提示登录框
	private Bean bean;// 数据bean
	private int pageIndex = 1;// 列表页码
	private String querytime = "2015-05-6";// 时间戳
	private int totalCount;// 列表总条数
	private int pagerCount;// 列表总页数
	private ReplyAdapter adapter;
	private List<ReplyList> list = new ArrayList<ReplyList>();
	private boolean isBiaoQing = false;
	private BQClickListener listener;
	private List<Fragment> fragments = new ArrayList<Fragment>();
	private List<BQ> bqs = new ArrayList<BQ>();
	private SmileyParser parser;
	private int type = 3;
	private int ReplyId = 0;
	private int ArticleId, CommentUserId;// 当前文章主键ID
	private String CommentNickName;
	/*
	 * 图片上传处理部分
	 */
	private static final int PHOTO_REQUEST_CAMERA = 987;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 654;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 321;// 结果
	/* 头像名称 */
	private String imgPath = Environment.getExternalStoragePublicDirectory(
			Environment.DIRECTORY_DCIM).getPath()
			+ "/Camera/";// 拍照时存储路径
	private String PHOTO_FILE_NAME;
	private File tempFile;
	private Bitmap bitmap;
	Dialog eduation_dialog;

	public String getTimeStamp() {
		StringBuffer sbf = new StringBuffer();
		for (int i = 0; i < 5; i++) {
			sbf.append(1 + (int) (Math.random() * 10));
		}

		long times = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		Date curDate = new Date(times);// 获取当前时间
		return formatter.format(curDate) + sbf.toString();
	};

	@Override
	public void findViewById() {
		listView.setMode(Mode.BOTH);
		foot = View.inflate(this, R.layout.fragment_bottom, null);
		// footer = (TextView) foot.findViewById(R.id.fragment_bottom_footer);
		// listView.getRefreshableView().addFooterView(foot);
		parser = SmileyParser.getInstance(this);
		bqs = MTUtils.getBQ(this);
		aniDraw = (AnimationDrawable) aini.getBackground();
		querytime = getTimeStamp();
		animaostart();
		loadData();

		listView.setOnRefreshListener(this);
		listView.getRefreshableView().setOnScrollListener(this);
		listView.getRefreshableView().setOnItemLongClickListener(this);
		topBar.setOnTopBarClickListener(this);
		listView.setOnScrollListener(new PauseOnScrollListener(
				MTApplication.bmu, false, true));
		adapter = new ReplyAdapter(this, list, R.layout.item_reply);
		listView.setAdapter(adapter);
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					reply(editText.getText().toString().trim());
				}
				return false;
			}
		});
		listView.setOnScrollListener(this);
		listener = new BQClickListener() {

			@Override
			public void click(int p) {
				int index = editText.getSelectionStart();
				Editable editable = editText.getText();
				editable.insert(index, bqs.get(p).getText());
				int newIndex = index + bqs.get(p).getText().length();
				editText.setText(parser.addSmileySpans(editText.getText()));
				editText.setSelection(newIndex);
			}

			@Override
			public void delet() {

				CharSequence ch = parser.addSmileySpans(editText.getText());
				String str = ch.toString();
				if (ch.length() > 0) {
					if (ch.charAt(ch.length() - 1) == ']') {
						editText.setText(ch.subSequence(0, str.lastIndexOf("[")));
						editText.setSelection(parser.addSmileySpans(
								editText.getText()).length());
					} else {
						editText.setText(ch.subSequence(0, ch.length() - 1));
						editText.setSelection(parser.addSmileySpans(
								editText.getText()).length());
					}
				}
			}
		};
		for (int i = 0; i < 5; i++) {
			fragments.add(new BQFragment(i, listener));
		}
		bqPager.setAdapter(new FPAdapter(getSupportFragmentManager(), fragments));
		initDot();
		bqPager.setOnPageChangeListener(this);
		LayoutParams lp = bqPager.getLayoutParams();
		lp.height = (int) (MTUtils.getScreenWidth(this) * 0.5055555);
		bqPager.setLayoutParams(lp);

		// 如果从回复消息页面点击回复跳转来，直接调起回复框
		if (!MTUtils.isEmpty(CommentNickName)) {
			Logger.i(CommentNickName, CommentNickName);
			initKeybord();
		}
		editText.setOnFocusChangeListener(this);
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		onFocusChange(editText, editText.isFocused());

	}

	// 弹出软键盘
	@Override
	public void onFocusChange(View arg0, final boolean arg1) {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (arg1) {
					MTUtils.KeyBoardShow(ReplyActivity.this, editText);
				}
			}
		}, 100);
	}

	// 调用回复框方法
	public void initKeybord() {
		editText.setHint("回复 " + CommentNickName + ":");
		editText.setHintTextColor(0xffaaaaaa);
		editText.setFocusable(true);
		MTUtils.KeyBoardShow(ReplyActivity.this, editText);
	}

	@OnClick({ R.id.dialog_collect, R.id.dialog_share, R.id.dialog_report,
			R.id.dialog_NoReplay })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reply_inputIco:
			if (!isBiaoQing) {
				isBiaoQing = true;
				biaoqing.setVisibility(View.VISIBLE);
				inputIco.setImageResource(R.drawable.ico_11_red);
				MTUtils.KeyBoardCancle(this);
			} else {
				isBiaoQing = false;
				biaoqing.setVisibility(View.GONE);
				inputIco.setImageResource(R.drawable.ico_9);
				MTUtils.KeyBoardShow(this, editText);
			}
			break;
		case R.id.reply_pic:
			MTUtils.KeyBoardCancle(this);
			choiseImgUpdate(isSee);
			break;
		case R.id.reply_listview:
			isBiaoQing = false;
			MTUtils.KeyBoardCancle(this);
			inputIco.setImageResource(R.drawable.ico_9);
			biaoqing.setVisibility(View.GONE);
			break;
		case R.id.reply_replyButton:
			reply(editText.getText().toString().trim());
			break;
		case R.id.reply_inputEdit:
			isBiaoQing = false;
			biaoqing.setVisibility(View.GONE);
			inputIco.setImageResource(R.drawable.ico_9);
			break;
		case R.id.favorable_bg:
			loadData();
			break;
		case R.id.dialog_collect:// 删除评论
			longClick.dismiss();
			if (MTApplication.isLogin) {
				delete();
			} else {
				dialog = MTUtils.showLoginDialog(ReplyActivity.this);
			}
			break;
		case R.id.dialog_share:// 复制评论
			copy();
			longClick.dismiss();
			break;
		case R.id.dialog_report:// 举报
			longClick.dismiss();
			if (MTApplication.isLogin) {
				report();
			} else {
				dialog = MTUtils.showLoginDialog(ReplyActivity.this);
			}
			break;
		case R.id.dialog_NoReplay:// 取消
			Logger.i("取消--longClick==null", longClick == null);
			longClick.dismiss();
			break;
		}

	}

	ClipboardManager copy;

	// 点赞
	private void dianZan(int replyId) {
		String TimeStamp = MTUtils.getTimeStamp();
		int userId = MTApplication.getInt("UserId");
		String token = MTApplication.getString("Token");
		org.json.JSONObject json = new org.json.JSONObject();
		try {
			json.put(
					"Sign",
					MD5Util.getLowerCaseMD5(userId + "" + ArticleId + TimeStamp
							+ URLs.KEY));
			json.put(URLs.TIME_STAMP, TimeStamp);
			json.put("Articleid", ArticleId);
			json.put("UserId", userId);
			json.put("UserToken", token);
			json.put("TypeId", type);
			json.put("ReplyId", replyId);
			Logger.i("json-ZAN==============>>>>>", json.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		RequestParams p = new RequestParams();
		p.addBodyParameter(URLs.JSON_INFO, json.toString());
		SendActtionTool.post(URLs.ZAN, ServiceAction.Action_User,
				UserAction.Action_Zan, this, p);
	}

	// 评论
	private void reply(String str) {
		if (MTApplication.isLogin) {
			if (isSee || MTUtils.getLength(str) >= 10) {
				String hint;
				if (editText.getHint() != null) {
					hint = editText.getHint().toString().trim();
					if (!MTUtils.isEmpty(hint)
							&& !hint.substring(0, 2).equals("回复")) {
						ReplyId = 0;
					}
				}
				String TimeStamp = MTUtils.getTimeStamp();
				int userId = MTApplication.getInt("UserId");
				String token = MTApplication.getString("Token");
				org.json.JSONObject json = new org.json.JSONObject();
				try {
					json.put(
							"Sign",
							MD5Util.getLowerCaseMD5(userId + "" + ArticleId
									+ TimeStamp + URLs.KEY));
					json.put(URLs.TIME_STAMP, TimeStamp);
					json.put("Articleid", ArticleId);
					json.put("UserId", userId);
					json.put("UserToken", token);
					json.put("Content", str);
					json.put("TypeId", type);
					json.put("ReplyId", ReplyId);
					Logger.i("json-Reply", json.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
				RequestParams p = new RequestParams();
				p.addBodyParameter(URLs.JSON_INFO, json.toString());
				if (tempFile != null) {
					p.addBodyParameter(PHOTO_FILE_NAME, tempFile);
					tempFile = null;
					// pic.setImageResource(R.drawable.ico_8);
					// editText.setText("");
				}
				SendActtionTool.post(URLs.REPLAY, ServiceAction.Action_User,
						UserAction.Action_Reply, this, p);
				pro = MTUtils.showProgressPop(this, listView);
				hide();
			} else {
				MTUtils.Toast(this, "评论不能少于10个字符哦~");
			}
		} else {
			dialog = MTUtils.showLoginDialog(ReplyActivity.this);
		}
	}

	private PopupWindow pro;

	@Override
	public void setContentView() {
		Intent i = getIntent();
		type = i.getIntExtra("type", 0);
		ArticleId = i.getIntExtra("ArticleId", 0);
		Logger.i("ArticleId", ArticleId + "");
		ReplyId = i.getIntExtra("ReplyId", 0);
		// CommentUserId = i.getIntExtra("CommentUserId", 0);
		CommentNickName = i.getStringExtra("CommentNickName");
		PHOTO_FILE_NAME = MTUtils.getTimeStamp() + ".jpg";
		ViewUtils.inject(this);
	}

	@Override
	public void leftClick() {
		finish();
	}

	@Override
	public void finish() {
		MTUtils.KeyBoardCancle(this);
		super.finish();
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		finish();
	}

	@Override
	public void rightClick() {

	}

	@Override
	public void onSuccess(ServiceAction service, Object action, Object value) {
		super.onSuccess(service, action, value);
		animstop();
		bean = JSON.parseObject(value.toString(), Bean.class);
		Logger.i("成功-bean", bean);
		switch ((ServiceAction) service) {
		case Action_Comment:
			List<ReplyList> tlist = bean.getData().getReplyLists();
			isZan = new boolean[list.size()];
			if (tlist != null && tlist.size() != 0) {
				lastPagerReply = tlist;
				list.addAll(tlist);
				tip_doData.setVisibility(View.GONE);
			} else if (list.size() == 0) {
				tip_doData.setVisibility(View.VISIBLE);
			}
			DaTePager dp = bean.getData().getDaTePager();
			if (pageIndex == 1) {// 当列表为第一页的时候初始化下列属性
				totalCount = dp.getTotalCount();
				pagerCount = dp.getPageCount();
			}

			// if (pagerCount != 0 && pageIndex >= pagerCount) {
			// footer.setVisibility(View.VISIBLE);
			// } else {
			// footer.setVisibility(View.GONE);
			// }
			querytime = dp.getQueryTime();
			animstop();
			break;
		}
		if (action != null) {
			switch ((UserAction) action) {
			case Action_Reply:
				editText.setText("");
				list.clear();
				pageIndex = 1;
				loadData();
				isSee = false;
				editText.setHint("");
				ReplyId = 0;
				pic.setImageResource(R.drawable.ico_8);
				pro.dismiss();
				if (bean.getData().getPoint()>0) {
					MTUtils.JiFenToast(this, bean.getMsg());
				}else {
					MTUtils.Toast(this, "回复成功!");
				}
				break;
			case Action_Zan:
				Logger.i("Action_Zan", bean);
				MTUtils.Toast(this, "点赞成功");
				list.clear();
				loadData();
				break;
			case Action_Report:
				MTUtils.Toast(this, bean.getMsg());
				break;
			case Action_Delete:
				MTUtils.Toast(this, bean.getMsg());
				if (bean.getData().isOperateResult()) {
					list.removeAll(lastPagerReply);
					adapter.notifyDataSetChanged();
					loadData();
				}
				break;
			}
		}
	}

	private List<ReplyList> lastPagerReply = new ArrayList<ReplyList>();

	// 开始请求
	@Override
	public void onStart(ServiceAction service, Object action) {
		super.onStart(service, action);
	}

	// 当前能否加载数据
	private boolean isCanLoad = true;

	@Override
	public void onFinish(ServiceAction service, Object action) {
		super.onFinish(service, action);
		listView.getRefreshableView().removeFooterView(foot);
		if (pro != null) {
			pro.dismiss();
		}
		isCanLoad = true;
	}

	// 发送请求
	public void loadData() {
		if (MTUtils.isNetworkConnected(this)) {
			if (isCanLoad) {
				isCanLoad = false;
				String url = null;
				try {
					String querytime1 = URLEncoder.encode(querytime, "utf-8");
					url = MTUtils.getMTParams(URLs.REPLAYLIST, URLs.PAGE_INDEX,
							pageIndex, "ArticleId", ArticleId, "TypeId", type,
							URLs.QUERY_TIME, querytime1, "currentUserId",
							MTApplication.getInt("UserId"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				Logger.i("Action_Comment-url", url);
				// 发送get请求
				SendActtionTool.get(url, ServiceAction.Action_Comment, null,
						this);
			}
		} else {
			animstop();
			reset();
			tip_doData.setVisibility(View.GONE);
			// footer.setVisibility(View.GONE);
			listView.onRefreshComplete();
			listView.setVisibility(View.INVISIBLE);
			bg.setVisibility(View.VISIBLE);
			MTUtils.netTip(this);
		}
	}

	/**
	 * 重置属性值
	 * 
	 * 20150420:ZhouYang
	 * 
	 * 2015-5-12下午5:47:39
	 * 
	 */
	private void reset() {
		pagerCount = 0;
		list.clear();
		pageIndex = 1;
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		reset();
		loadData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		listView.getRefreshableView().addFooterView(foot);
		pageIndex += 1;
		loadData();
	}

	// 启动动画
	private void animaostart() {
		bg.setVisibility(View.GONE);
		tip_doData.setVisibility(View.GONE);
		if (aniDraw != null) {
			aniDraw.start();
			aini.setVisibility(View.VISIBLE);
			listView.setVisibility(View.INVISIBLE);
		}
	}

	// 暂停动画
	private void animstop() {
		adapter.notifyDataSetChanged();
		bg.setVisibility(View.GONE);
		listView.onRefreshComplete();
		listView.setVisibility(View.VISIBLE);
		if (aniDraw != null) {
			aniDraw.stop();
			aini.setVisibility(View.INVISIBLE);
		}
	}

	// 请求失败
	@Override
	public void onFaile(ServiceAction service, Object action, Object value) {
		super.onFaile(service, action, value);
		switch ((ServiceAction) service) {
		case Action_Comment:
			animstop();
			reset();
			bg.setVisibility(View.VISIBLE);
			break;
		}
		if (action != null) {
			Logger.i("UserAction", value.toString());
			switch ((UserAction) action) {
			case Action_Reply:
				pro.dismiss();
				MTUtils.Toast(this, "回复失败");
				isSee = false;
				break;
			case Action_Zan:
				MTUtils.Toast(this, "点赞失败");
				break;
			case Action_Report:
				MTUtils.Toast(this, "举报失败");
				break;
			case Action_Delete:
				MTUtils.Toast(this, "删除失败");
				break;
			}
		}
	}

	@Override
	public void onException(ServiceAction service, Object action, Object value) {
		super.onException(service, action, value);
		if (pro != null) {
			pro.dismiss();
		}
		animstop();
		reset();
		bg.setVisibility(View.VISIBLE);
	}

	// 初始化pager标记原点
	private void initDot() {
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(10, 10);
		lp.setMargins(10, 0, 0, 0);
		ImageView imgdot;
		for (int i = 0; i < 5; i++) {
			imgdot = new ImageView(this);
			imgdot.setImageResource(R.drawable.ico_float_2);
			imgdot.setLayoutParams(lp);
			dot.addView(imgdot);
		}
		imgdot = (ImageView) dot.getChildAt(0);
		imgdot.setImageResource(R.drawable.ico_float_2_red);
	}

	// 改变pager点的指示位置，
	private void dotChange(int n) {
		ImageView imag;
		for (int i = 0; i < 5; i++) {
			imag = (ImageView) dot.getChildAt(i);
			if (imag != null) {
				imag.setImageResource(R.drawable.ico_float_2);
			}
		}
		imag = (ImageView) dot.getChildAt(n);
		if (imag != null) {
			imag.setImageResource(R.drawable.ico_float_2_red);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		dotChange(arg0);
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		hide();
	}

	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int ResultCode, Intent data) {
		super.onActivityResult(requestCode, ResultCode, data);
		switch (requestCode) {
		case PHOTO_REQUEST_GALLERY:
			if (data != null) {
				Uri uri = data.getData();// 得到图片的全路径uri
				String path = MTUtils.getAbsoluteImagePath(this, uri);
				// tempFile = new File(path);
				// Logger.i("GALLERY--tempFile.length()", tempFile.length() +
				// "");
				Logger.i("path", path);
				MTApplication.bmu.display(pic, path);
				// pic.setImageURI(uri);
				PreviewActivity.uri = path;
				isSee = true;
				getimage(path);
				// Logger.i("cut--tempFile.length()", tempFile.length() + "");
			}
			break;
		case PHOTO_REQUEST_CAMERA:
			if (hasSdcard()) {
				tempFile = new File(imgPath, PHOTO_FILE_NAME);
				Logger.i("CAMERA--tempFile.length()", tempFile.length() + "");
				if (tempFile.length() != 0) {
					// Uri uri = Uri.fromFile(tempFile);
					String path = tempFile.getAbsolutePath();
					new BitmapUtils(this).display(pic, path);
					PreviewActivity.uri = path;
					isSee = true;
					getimage(path);
					// Logger.i("cut--tempFile.length()", tempFile.length() +
					// "");
				}
			} else {
				MTUtils.Toast(this, "未找到存储卡，无法存储照片！");
			}
			break;
		case PHOTO_REQUEST_CUT:
			// try {
			// // bitmap = data.getParcelableExtra("data");
			// // pic.setImageBitmap(bitmap);
			// Uri uri = data.getData();
			// String path = MTUtils.getAbsoluteImagePath(this, uri);
			// Logger.i("path", path);
			// pic.setImageURI(uri);
			// PreviewActivity.uri = uri;
			// isSee = true;
			// Logger.i("cut--tempFile.length()", tempFile.length() + "");
			//
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			break;
		case SEE_IMG_CODE:
			if (data != null) {
				if (data.getBooleanExtra("isDelete", false)) {
					pic.setImageResource(R.drawable.ico_8);
					isSee = false;
					tempFile = null;
				}
				break;
			}
		}
	}

	boolean isSee = false;
	private final int SEE_IMG_CODE = 1001;

	private void choiseImgUpdate(boolean isSee) {
		View eatsView = View.inflate(this, R.layout.complete_choise_img, null);
		eduation_dialog = new Dialog(this, R.style.FullHeightDialog);

		OnClickListener choiseListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				PHOTO_FILE_NAME = MTUtils.getTimeStamp() + ".jpg";
				switch (v.getId()) {
				case R.id.choise_img_phone:
					camera();
					eduation_dialog.dismiss();
					break;
				case R.id.choise_img_pic:
					gallery();
					eduation_dialog.dismiss();
					break;
				case R.id.choise_img_cancle:
					eduation_dialog.dismiss();
					break;
				case R.id.choise_img_see:
					eduation_dialog.dismiss();
					Intent intent = new Intent(ReplyActivity.this,
							PreviewActivity.class);
					startActivityForResult(intent, SEE_IMG_CODE);
					break;
				}
			}
		};
		TextView title = (TextView) eatsView
				.findViewById(R.id.choise_img_title), see = (TextView) eatsView
				.findViewById(R.id.choise_img_see);
		title.setText("选择图片");
		if (isSee) {
			see.setVisibility(View.VISIBLE);
		} else {
			see.setVisibility(View.GONE);
		}
		eatsView.findViewById(R.id.choise_img_phone).setOnClickListener(
				choiseListener);
		eatsView.findViewById(R.id.choise_img_pic).setOnClickListener(
				choiseListener);
		eatsView.findViewById(R.id.choise_img_cancle).setOnClickListener(
				choiseListener);
		see.setOnClickListener(choiseListener);

		eduation_dialog.setContentView(eatsView);
		eduation_dialog.setCanceledOnTouchOutside(true);
		Window dialogWindow = eduation_dialog.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);
		dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
		WindowManager.LayoutParams p = eduation_dialog.getWindow()
				.getAttributes(); // 获取对话框当前的参数值
		p.width = MTUtils.getScreenWidth(this);// 宽度设置为屏幕的
		dialogWindow.setAttributes(p);
		eduation_dialog.show();
	}

	/*
	 * 从相册获取
	 */
	public void gallery() {
		// 激活系统图库，选择一张图片
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
	}

	/*
	 * 从相机获取
	 */
	public void camera() {
		Intent intentc = new Intent("android.media.action.IMAGE_CAPTURE");
		// 判断存储卡是否可以用，可用进行存储
		if (hasSdcard()) {
			intentc.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(imgPath, PHOTO_FILE_NAME)));
		}
		startActivityForResult(intentc, PHOTO_REQUEST_CAMERA);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
	}

	/**
	 * 图片裁剪
	 * 
	 * 作者:ZhouYang
	 * 
	 * 2015-5-28上午10:37:32
	 * 
	 * @param uri
	 */
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		// intent.putExtra("aspectX", 1);
		// intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		// intent.putExtra("outputX", 500);
		// intent.putExtra("outputY", 500);
		intent.putExtra("scale", true);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
	}

	// 是否有存储卡
	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public void compressBmpToFile(Bitmap bmp) {
		tempFile = new File(Environment.getExternalStorageDirectory(),
				PHOTO_FILE_NAME);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;// 压缩比例
		bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		if (baos.toByteArray().length / 1024 > 512) {
			options = 51200 / (baos.toByteArray().length / 1024);
		}
		Logger.i("baos大小", baos.toByteArray().length + "");
		Logger.i("options大小", options + "");
		// while (baos.toByteArray().length / 1024 > 200) {
		baos.reset();
		// options -= 1;
		Logger.i("options大小", options + "");
		bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		Logger.i("baos大小", baos.toByteArray().length + "");
		// }
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(tempFile);
			fos.write(baos.toByteArray());
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				bmp.recycle();
				bmp = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void getimage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		// newOpts.inJustDecodeBounds = true;
		// Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//
		// 此时返回bm为空
		//
		// newOpts.inJustDecodeBounds = false;
		// int w = newOpts.outWidth;
		// int h = newOpts.outHeight;
		// // 现在主流手机比较多是1280*720分辨率，所以高和宽我们设置为
		// float hh = 1280f;// 这里设置高度
		// float ww = 720f;// 这里设置宽度
		// // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		// int be = 1;// be=1表示不缩放
		// if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
		// be = (int) (newOpts.outWidth / ww);
		// } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
		// be = (int) (newOpts.outHeight / hh);
		// }
		// if (be <= 0)
		// be = 1;
		// newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		compressBmpToFile(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	boolean[] isZan = {};

	/*
	 * Spannable WordtoSpan = new SpannableString("大字小字");
	 * WordtoSpan.setSpan(new AbsoluteSizeSpan(20), 0, 2,
	 * Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); WordtoSpan.setSpan(new
	 * AbsoluteSizeSpan(14), 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	 * remoteViews.setCharSequence(R.id.text11, "setText", WordtoSpan);
	 * ComponentName com = new ComponentName("com.jftt.widget",
	 * "com.jftt.widget.MyWidgetProvider");
	 * appWidgetManager.updateAppWidget(com, remoteViews);
	 */
	String tip = "";

	/**
	 * @author: ZhouYang
	 * @time:2015-5-25 下午5:33:24
	 * @Description:评论列表适配器
	 */
	public class ReplyAdapter extends CommonAdapter<ReplyList> {
		Context context;

		public ReplyAdapter(Context context, List<ReplyList> mDatas,
				int itemLayoutId) {
			super(context, mDatas, itemLayoutId);
			this.context = context;
		}

		@Override
		public void convert(final ViewHolder h, final ReplyList i, final int p) {
			OnClickListener clic = new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (MTApplication.isLogin) {
						ReplyId = i.getId();
						editText.setHint("回复 " + i.getNickName() + ":");
						editText.setHintTextColor(0xffaaaaaa);
						MTUtils.KeyBoardShow(ReplyActivity.this, editText);
						// editText.setSelection(parser.addSmileySpans(
						// editText.getText()).length());
						// tip = editText.getText().toString().trim();
						// SpannableStringBuilder style = new
						// SpannableStringBuilder(
						// tip);
						// style.setSpan(new AbsoluteSizeSpan(11), 0,
						// tip.length(),
						// Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
						// editText.setText(style);
					} else {
						dialog = MTUtils.showLoginDialog(ReplyActivity.this);
					}
				}
			};
			h.setImageByUrl(R.id.reply_item_icon, i.getAvatar());
			h.getView(R.id.linearLayout1).setOnClickListener(house(i));
			h.getView(R.id.reply_item_icon).setOnClickListener(house(i));
			h.setText(R.id.reply_item_nickName, i.getNickName());
			if (!MTUtils.isEmpty(i.getContent())) {
				TextView t = h.getView(R.id.reply_item_title);
				t.setVisibility(View.VISIBLE);
				t.setText(SmileyParser.getInstance(context).addSmileySpans(
						i.getContent()));
				if (i.isIsDelete()) {
					t.setTextColor(0xffaaaaaa);
				} else {
					t.setTextColor(0xff444444);
				}
			} else {
				h.getView(R.id.reply_item_title).setVisibility(View.GONE);
			}
			h.setText(R.id.reply_item_time, i.getReplyDate());
			h.setText(R.id.reply_item_good, i.getYes() + "");
			if (type == 1) {
				h.getView(R.id.reply_item_floor_layout)
						.setVisibility(View.GONE);
			} else {
				h.setText(R.id.reply_item_floor, i.getFloor() + "F");
			}
			if (MTUtils.isEmpty(i.getBabyNickName())) {
				h.setText(R.id.reply_item_age, i.getBabyInfo());
			} else {
				h.setText(R.id.reply_item_age,
						i.getBabyNickName() + "," + i.getBabyInfo());
			}
			switch (i.getBabyGender()) {
			case 0:
				h.getView(R.id.reply_item_sex).setVisibility(View.VISIBLE);
				h.setImageResource(R.id.reply_item_sex, R.drawable.ico_male);
				break;
			case 1:
				h.getView(R.id.reply_item_sex).setVisibility(View.VISIBLE);
				h.setImageResource(R.id.reply_item_sex, R.drawable.ico_female);
				break;
			case 2:
				h.getView(R.id.reply_item_sex).setVisibility(View.INVISIBLE);
				break;

			}

			h.getView(R.id.reply_item_reply_click).setOnClickListener(clic);// 点击回复
			if (i.isIsYes()) {
				h.setImageResource(R.id.reply_item_good_ico,
						R.drawable.pl_ico_recommend);
			} else {
				h.setImageResource(R.id.reply_item_good_ico,
						R.drawable.pl_ico_recommend_gray);
			}
			h.getView(R.id.reply_item_good_click).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							if (MTApplication.isLogin) {
								if (!i.isIsYes()) {
									dianZan(i.getId());
									// h.setImageResource(
									// R.id.reply_item_good_ico,
									// R.drawable.pl_ico_recommend);
									// i.setYes(i.getYes() + 1);
									// h.setText(R.id.reply_item_good,
									// i.getYes()
									// + "");
									// i.setIsYes(true);
								} else {
									MTUtils.Toast(context, "不能重复点赞哦!");
								}
							} else {
								dialog = MTUtils
										.showLoginDialog(ReplyActivity.this);
							}
						}
					});// 点赞

			if (i.getImgNum() > 0) {
				h.getView(R.id.reply_item_grid).setVisibility(View.VISIBLE);
				NoSorceGridView grid = (NoSorceGridView) h
						.getView(R.id.reply_item_grid);
				grid.setAdapter(new ExperienceGridAdapter(mContext, i
						.getImgList(), 150));
				grid.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						BigPicActivity.imgs = i.getBigImgList();
						BigPicActivity.currImg = BigPicActivity.imgs.get(arg2);
						startActivity(new Intent(ReplyActivity.this,
								BigPicActivity.class));
					}
				});
			} else {
				h.getView(R.id.reply_item_grid).setVisibility(View.GONE);
			}
			if (i.getReferencedReply() != null) {
				h.getView(R.id.reply_item_reply).setVisibility(View.VISIBLE);
				final ReplyList reply = i.getReferencedReply();
				h.setText(R.id.reply_item_reply_nickName, reply.getNickName());
				if (MTUtils.isEmpty(reply.getBabyNickName())) {
					h.setText(R.id.reply_item_reply__age, reply.getBabyInfo());
				} else {
					h.setText(R.id.reply_item_reply__age,
							reply.getBabyNickName() + "," + reply.getBabyInfo());
				}
				switch (reply.getBabyGender()) {
				case 0:
					h.getView(R.id.reply_item_reply__sex).setVisibility(
							View.VISIBLE);
					h.setImageResource(R.id.reply_item_reply__sex,
							R.drawable.ico_male);
					break;
				case 1:
					h.getView(R.id.reply_item_reply__sex).setVisibility(
							View.VISIBLE);
					h.setImageResource(R.id.reply_item_reply__sex,
							R.drawable.ico_female);
					break;
				case 2:
					h.getView(R.id.reply_item_reply__sex).setVisibility(
							View.INVISIBLE);
					break;

				}
				h.setText(R.id.reply_item_reply_title, SmileyParser
						.getInstance(context)
						.addSmileySpans(reply.getContent()));
				if (reply.getImgNum() > 0) {
					h.getView(R.id.reply_item_reply_pic1).setVisibility(
							View.VISIBLE);
					h.getView(R.id.reply_item_reply_pic1).setOnClickListener(
							new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									BigPicActivity.imgs = reply.getBigImgList();
									BigPicActivity.currImg = BigPicActivity.imgs
											.get(0);
									startActivity(new Intent(
											ReplyActivity.this,
											BigPicActivity.class));
								}
							});
					h.setImageByUrl(R.id.reply_item_reply_pic1,
							reply.getImgList()[0]);
					if (reply.getImgNum() > 1) {
						h.getView(R.id.reply_item_reply_picCount)
								.setVisibility(View.VISIBLE);
						h.setText(R.id.reply_item_reply_picCount,
								"" + reply.getImgNum());
					} else {
						h.getView(R.id.reply_item_reply_picCount)
								.setVisibility(View.GONE);
					}
				} else {
					h.getView(R.id.reply_item_reply_pic1).setVisibility(
							View.GONE);
					h.getView(R.id.reply_item_reply_picCount).setVisibility(
							View.GONE);
				}

			} else {
				h.getView(R.id.reply_item_reply).setVisibility(View.GONE);
			}

			if (p > lastPosition) {// 这里就是动画的应用
				Animation animation = AnimationUtils.loadAnimation(context,
						(p > lastPosition) ? R.anim.slide_bottom_to_top
								: R.anim.slide_top_to_bottom);
				h.getConvertView().startAnimation(animation);
			}
			lastPosition = p;
		}

		private OnClickListener house(final ReplyList i) {
			return new OnClickListener() {

				@Override
				public void onClick(View v) {
					MTUtils.KeyBoardCancle(ReplyActivity.this);

					if (i.getUserId() == MTApplication.getInt("UserId")) {
						Intent ih = new Intent(ReplyActivity.this,
								MyHouseActivity.class);
						ih.putExtra("OwnerUserId", i.getUserId());
						startActivity(ih);
					} else {
						Intent ih = new Intent(ReplyActivity.this,
								TaHouseActivity.class);
						ih.putExtra("OwnerUserId", i.getUserId());
						startActivity(ih);
					}
				}
			};
		}
	}

	int lastPosition = 0;

	/**
	 * 隐藏软键盘和表情页
	 * 
	 * 作者:ZhouYang
	 * 
	 * 2015-6-4下午2:11:24
	 * 
	 */
	private void hide() {
		isBiaoQing = false;
		biaoqing.setVisibility(View.GONE);
		inputIco.setImageResource(R.drawable.ico_9);
		MTUtils.KeyBoardCancle(this);
	}

	Dialog longClick;

	/**
	 * 
	 * 屏幕底部弹窗
	 * 
	 * 作者:ZhouYang
	 * 
	 * 2015-5-18下午5:28:58
	 * 
	 * @param dialog
	 *            要显示的弹窗对象
	 * @param layoutId
	 *            布局Id
	 */
	public Dialog showDialog(Dialog dialog, int layoutId, int delete_visibility) {
		View v = View.inflate(this, layoutId, null);
		v.findViewById(R.id.dialog_collect).setVisibility(delete_visibility);
		dialog = new Dialog(this, R.style.MmsDialogTheme);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(v);
		dialog.getWindow().setBackgroundDrawable(new BitmapDrawable());
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		Display dsp = getWindowManager().getDefaultDisplay();
		lp.width = (int) dsp.getWidth();
		lp.y = (int) dsp.getHeight();
		dialog.getWindow().setAttributes(lp);
		dialog.show();
		return dialog;
	}

	private ReplyList r;

	// 举报
	private void report() {
		String TimeStamp = MTUtils.getTimeStamp();
		int userId = MTApplication.getInt("UserId");
		String token = MTApplication.getString("Token");
		org.json.JSONObject json = new org.json.JSONObject();
		try {
			json.put(
					"Sign",
					MD5Util.getLowerCaseMD5(userId + "" + r.getUserId()
							+ TimeStamp + URLs.KEY));
			json.put(URLs.TIME_STAMP, TimeStamp);
			json.put(URLs.USER_ID, userId);
			json.put("ReportObject", 2);
			json.put("ReferUserId", r.getUserId());
			json.put("Content", r.getContent());
			json.put("ContentId", r.getId());
			json.put("TypeId", type);
			json.put("UserToken", token);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		RequestParams p = new RequestParams();
		p.addBodyParameter(URLs.JSON_INFO, json.toString());
		SendActtionTool.post(URLs.REPORT, ServiceAction.Action_User,
				UserAction.Action_Report, this, p);
	}

	// 复制
	private void copy() {
		copy = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		copy.setText(r.getContent());
		MTUtils.Toast(this, "复制成功");
	}

	// 删除
	private void delete() {
		String TimeStamp = MTUtils.getTimeStamp();
		int userId = MTApplication.getInt("UserId");
		int ReferUserId = 0;
		String token = MTApplication.getString("Token");
		org.json.JSONObject json = new org.json.JSONObject();
		try {
			json.put(
					"Sign",
					MD5Util.getLowerCaseMD5(userId + "" + r.getArticleId()
							+ TimeStamp + URLs.KEY));
			json.put(URLs.TIME_STAMP, TimeStamp);
			json.put(URLs.USER_ID, userId);
			json.put("ArticleId", r.getArticleId());
			json.put("ReplyId", r.getId());
			json.put("TypeId", type);
			json.put("UserToken", token);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Logger.i("删除评论json", json);
		RequestParams p = new RequestParams();
		p.addBodyParameter(URLs.JSON_INFO, json.toString());
		SendActtionTool.post(URLs.DELETE, ServiceAction.Action_User,
				UserAction.Action_Delete, this, p);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int p,
			long arg3) {
		r = list.get(p - 1);
		if (MTApplication.getInt("UserId") == r.getUserId() && !r.isIsDelete()) {
			longClick = showDialog(longClick, R.layout.dialog_reply_longclick,
					View.VISIBLE);
		} else {
			longClick = showDialog(longClick, R.layout.dialog_reply_longclick,
					View.GONE);
		}
		return false;
	}
}
