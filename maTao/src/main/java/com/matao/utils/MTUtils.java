package com.matao.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.matao.activity.GygActivity;
import com.matao.activity.LoginActivity;
import com.matao.bean.BQ;
import com.matao.matao.R;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-1-3 上午10:26:10
 * @Description:工具类
 */
public class MTUtils {

	private static final String TAG = "BitmapCommonUtils";
	private static final long POLY64REV = 0x95AC9329AC4BC9B5L;
	private static final long INITIALCRC = 0xFFFFFFFFFFFFFFFFL;
	private static BitmapDisplayConfig config = new BitmapDisplayConfig();

	public static BitmapDisplayConfig getConfig(Context c, int imgId) {
		config.setLoadingDrawable(c.getResources().getDrawable(imgId));
		config.setLoadFailedDrawable(c.getResources().getDrawable(imgId));
		return config;
	}

	// 获取字符长度汉字2个字符 其他1个
	public static int getLength(String str) {
		int n = 0;
		char[] arr = str.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			if (isChinese(arr[i])) {
				n += 2;
			} else {
				n += 1;
			}
		}
		return n;

	}

	// 判断是否为汉字
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	// 进度圈 atView 在哪个view中间显示
	public static PopupWindow showProgressPop(Context context, View atView) {

		View v = LayoutInflater.from(context).inflate(R.layout.progressdialog,
				null);
		PopupWindow pro = new PopupWindow(v, 200, 200);
		pro.setFocusable(true); // 设置不允许在外点击消失
		pro.setOutsideTouchable(false);
		pro.showAtLocation(atView, Gravity.CENTER, 0, 0);
		return pro;
	}

	// 登录提示框
	public static Dialog showLoginDialog(final Context context) {
		Intent i = new Intent(context, LoginActivity.class);
		// 跳转登录页传递 是否是从 入口页面进入登录页的boolean值 默认为是从非入口进入
		i.putExtra("isOther", true);
		context.startActivity(i);
		((Activity) context).overridePendingTransition(
				R.anim.slide_in_from_bottom, R.anim.slide);
		return null;
	}

	// 逛一逛提示框框
	public static Dialog showGygDialog(final Context context) {
		Intent i = new Intent(context, GygActivity.class);
		// i.putExtra("isOther", true);
		context.startActivity(i);
		((Activity) context).overridePendingTransition(
				R.anim.slide_in_from_bottom, R.anim.slide);
		return null;
	}

	public static boolean isEmail(String email) {
		if (null == email || "".equals(email))
			return false;
		// Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
		Pattern p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	// 验证用户昵称 括字母、数字、下划线或中文
	public static boolean checkNickName(String nickName) {
		Pattern p = Pattern.compile("^[A-Za-z0-9\u4e00-\u9fa5]+$");// 复杂匹配
		Matcher m = p.matcher(nickName);
		return m.matches();
	}

	// 验证宝宝昵称 括字母，中文
	public static boolean checkBabyNickName(String passWord) {
		Pattern p = Pattern.compile("^[A-Za-z\u4e00-\u9fa5]+$");// 复杂匹配
		Matcher m = p.matcher(passWord);
		return m.matches();
	}

	// 验证用户昵称 括字母、数字、下划线
	public static boolean checkPassWord(String nickName) {
		Pattern p = Pattern.compile(" ^[0-9a-zA-Z_]+$");// 复杂匹配
		Matcher m = p.matcher(nickName);
		return m.matches();
	}

	// 自定义Toast
	public static void Toast(Context context, Object s) {
		View layout = LayoutInflater.from(context)
				.inflate(R.layout.toast, null);
		TextView text = (TextView) layout.findViewById(R.id.toast_text);
		text.setText("" + s);
		Toast toast = new Toast(context);
		toast.setDuration(0);
		toast.setView(layout);
		toast.show();
	}

	// 积分Toast
	public static void JiFenToast(Context context, Object s) {
		View layout = LayoutInflater.from(context).inflate(
				R.layout.toast_jifen_tip, null);
		TextView text = (TextView) layout.findViewById(R.id.toast_text);
		text.setText(""+s);
		Toast toast = new Toast(context);
		toast.setDuration(1);
		toast.setView(layout);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	// 无网络提示
	public static void netTip(Context context) {
		View layout = LayoutInflater.from(context).inflate(
				R.layout.toast_net_tip, null);
		TextView text = (TextView) layout.findViewById(R.id.toast_text);
		text.setText("  你的网络不给力哦  ");
		Toast toast = new Toast(context);
		toast.setDuration(0);
		toast.setView(layout);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public static List<BQ> getBQ(Context c) {
		String json = null;
		InputStream is = null;
		// 将json文件读取到buffer数组中
		try {
			is = c.getResources().getAssets().open("qqbq.txt");
			byte[] buffer = new byte[is.available()];
			is.read(buffer);
			json = new String(buffer, "UTF-8");
			return JSON.parseArray(json, BQ.class);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String[] getBQName(Context c) {
		List<BQ> y = getBQ(c);
		String[] x = new String[y.size()];
		for (int i = 0; i < x.length; i++) {
			x[i] = y.get(i).getText();
		}
		return x;
	}

	private static long[] sCrcTable = new long[256];

	/**
	 * 获取服务器所需时间戳
	 */
	public static String getTimeStamp() {
		StringBuffer sbf = new StringBuffer();
		for (int i = 0; i < 5; i++) {
			sbf.append(1 + (int) (Math.random() * 10));
		}

		long times = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date curDate = new Date(times);// 获取当前时间
		return formatter.format(curDate) + sbf.toString();
	};

	// 取到图片的绝对路径
	public static String getAbsoluteImagePath(Context context, Uri uri) {
		Cursor cursor = null;
		String string = "";
		try {
			ContentResolver contentResolver = context.getContentResolver();
			String[] proj = { MediaStore.Images.Media.DATA };
			cursor = contentResolver.query(uri, proj, null, null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			string = cursor.getString(column_index);
			cursor.close();
			return string;

		} catch (Exception e) {
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return string;
	}

	// 获取手机ip地址
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("WifiPreference IpAddress", ex.toString());
		}
		return null;
	}

	/**
	 * 获取可以使用的缓存目录
	 * 
	 * @param context
	 * @param uniqueName
	 *            目录名称
	 * @return
	 */
	public static File getDiskCacheDir(Context context, String uniqueName) {
		final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()) ? getExternalCacheDir(context)
				.getPath() : context.getCacheDir().getPath();

		return new File(cachePath + File.separator + uniqueName);
	}

	/**
	 * 
	 * @param header
	 *            请求地址的公用头
	 * @param values
	 *            key=value 对应传入
	 * @return
	 */
	public static String getMTParams(String header, Object... values) {
		StringBuilder params = new StringBuilder("");
		params.append(header);
		int n = values.length / 2;
		for (int i = 0; i < n * 2; i += 2) {
			params.append(values[i] + "=" + values[i + 1] + "&");
		}
		return params.toString();
	}

	// 普通提示框
	public static Dialog showDialog(Context c, OnClickListener click, String n,
			String y, String t) {
		final Dialog d = new Dialog(c, R.style.DialogLoginTip);
		final LayoutInflater inflater = LayoutInflater.from(c);
		View v = inflater.inflate(R.layout.dialog, null);
		TextView N = (TextView) v.findViewById(R.id.dialog_N);
		N.setOnClickListener(click);
		N.setText(n);
		TextView Y = (TextView) v.findViewById(R.id.dialog_Y);
		Y.setOnClickListener(click);
		Y.setText(y);
		TextView T = (TextView) v.findViewById(R.id.dialog_T);
		T.setText(t);
		d.setContentView(v);
		WindowManager.LayoutParams lp = d.getWindow().getAttributes();
		lp.width = (int) (MTUtils.getScreenWidth(c) * 0.81111);
		lp.height = -2;
		d.getWindow().setAttributes(lp);
		d.show();
		return d;
	}

	// 版本更新提示框
	public static Dialog VersionDialog(Context c, OnClickListener click,
			String n, String y, String t) {
		final Dialog d = new Dialog(c, R.style.DialogLoginTip);
		final LayoutInflater inflater = LayoutInflater.from(c);
		View v = inflater.inflate(R.layout.dialog_update, null);
		TextView title = (TextView) v.findViewById(R.id.dialog_title);
		title.setTypeface(MTApplication.jtz);
		TextView N = (TextView) v.findViewById(R.id.dialog_N);
		N.setOnClickListener(click);
		N.setText(n);
		TextView Y = (TextView) v.findViewById(R.id.dialog_Y);
		Y.setOnClickListener(click);
		Y.setText(y);
		TextView T = (TextView) v.findViewById(R.id.dialog_T);
		T.setGravity(Gravity.LEFT);
		T.setText(t);
		d.setContentView(v);
		WindowManager.LayoutParams lp = d.getWindow().getAttributes();
		lp.width = (int) (MTUtils.getScreenWidth(c) * 0.85);
		lp.height = -2;
		d.getWindow().setAttributes(lp);
		d.show();
		return d;
	}

	/**
	 * 获取bitmap的字节大小
	 * 
	 * @param bitmap
	 * @return
	 */
	public static int getBitmapSize(Bitmap bitmap) {
		return bitmap.getRowBytes() * bitmap.getHeight();
	}

	/**
	 * 获取程序外部的缓存目录
	 * 
	 * @param context
	 * @return
	 */
	public static File getExternalCacheDir(Context context) {
		final String cacheDir = "/Android/data/" + context.getPackageName()
				+ "/cache/";
		return new File(Environment.getExternalStorageDirectory().getPath()
				+ cacheDir);
	}

	/**
	 * 获取文件路径空间大小
	 * 
	 * @param path
	 * @return
	 */
	public static long getUsableSpace(File path) {
		try {
			final StatFs stats = new StatFs(path.getPath());
			return (long) stats.getBlockSize()
					* (long) stats.getAvailableBlocks();
		} catch (Exception e) {
			Log.e(TAG,
					"获取 sdcard 缓存大小 出错，请查看AndroidManifest.xml 是否添加了sdcard的访问权限");
			e.printStackTrace();
			return -1;
		}

	}

	public static byte[] getBytes(String in) {
		byte[] result = new byte[in.length() * 2];
		int output = 0;
		for (char ch : in.toCharArray()) {
			result[output++] = (byte) (ch & 0xFF);
			result[output++] = (byte) (ch >> 8);
		}
		return result;
	}

	public static boolean isSameKey(byte[] key, byte[] buffer) {
		int n = key.length;
		if (buffer.length < n) {
			return false;
		}
		for (int i = 0; i < n; ++i) {
			if (key[i] != buffer[i]) {
				return false;
			}
		}
		return true;
	}

	public static byte[] copyOfRange(byte[] original, int from, int to) {
		int newLength = to - from;
		if (newLength < 0)
			throw new IllegalArgumentException(from + " > " + to);
		byte[] copy = new byte[newLength];
		System.arraycopy(original, from, copy, 0,
				Math.min(original.length - from, newLength));
		return copy;
	}

	static {
		// 参考 http://bioinf.cs.ucl.ac.uk/downloads/crc64/crc64.c
		long part;
		for (int i = 0; i < 256; i++) {
			part = i;
			for (int j = 0; j < 8; j++) {
				long x = ((int) part & 1) != 0 ? POLY64REV : 0;
				part = (part >> 1) ^ x;
			}
			sCrcTable[i] = part;
		}
	}

	public static byte[] makeKey(String httpUrl) {
		return getBytes(httpUrl);
	}

	/**
	 * A function thats returns a 64-bit crc for string
	 * 
	 * @param in
	 *            input string
	 * @return a 64-bit crc value
	 */
	public static final long crc64Long(String in) {
		if (in == null || in.length() == 0) {
			return 0;
		}
		return crc64Long(getBytes(in));
	}

	public static final long crc64Long(byte[] buffer) {
		long crc = INITIALCRC;
		for (int k = 0, n = buffer.length; k < n; ++k) {
			crc = sCrcTable[(((int) crc) ^ buffer[k]) & 0xff] ^ (crc >> 8);
		}
		return crc;
	}

	public static boolean isEmpty(String val) {
		return null == val || "".equals(val.trim());
	}

	/**
	 * 隐藏 软键盘
	 * 
	 * @param context
	 */
	public static void KeyBoardCancle(Activity context) {
		View view = context.getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) context
					.getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/**
	 * 展现键盘
	 * 
	 * @param context
	 */
	public static void KeyBoardShow(Activity context, View view) {
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.showSoftInput(view, InputMethodManager.SHOW_FORCED);
		}
	}

	/**
	 * isOpen若返回true，则表示输入法打开
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isKeyBoardShow(Activity context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		return imm.isActive();

	}

	/**
	 * 调用系统的 照片查看器 云相册 选择不到 照片 (小米手机)
	 * 
	 * @param context
	 * @param requestcode
	 */
	public static void showFileChooser(Activity context, int requestcode) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		try {
			context.startActivityForResult(
					Intent.createChooser(intent, "请选择一个要上传的文件"), requestcode);
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(context, "对不起您的手机没有资源管理器软件", Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * 第三方分享
	 * 
	 * @param context
	 * @param content
	 */
	public static void showShareMessage(Context context, String content) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_TEXT, content);
		intent.setType("text/plain");
		context.startActivity(intent);
	}

	/**
	 * 得到指定路径文件的的名字
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getStringFile(String filePath) {
		return filePath.substring(filePath.lastIndexOf("/") + 1);
	}

	/**
	 * 网络请求data值是否为空(空数组为空) (php 数据检测)
	 * 
	 * @param data
	 * @return
	 */
	public static boolean isRequestDataEmpty(String data) {
		if (isEmpty(data) || "null".equals(data) || "[]".equals(data)) {
			return true;
		}
		return false;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 
	 * 2015年2月10日
	 * 
	 * @param values
	 *            key value 键值对 传入
	 * @return 服务器请求的参数
	 */
	public static String getUrlParams(String... values) {
		StringBuilder params = new StringBuilder("");
		for (int i = 0; i < values.length; i += 2) {
			params.append(values[i] + "=" + values[i + 1] + "&");
		}
		return params.substring(0, params.length() > 0 ? params.length() - 1
				: 0);
	}

	/**
	 * 
	 * 2015年2月27日
	 * 
	 * @param gbString
	 * @return 返回转换后的 Unicode 的数值
	 */
	public static String encodeUnicode(final String gbString) {
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2) {
				hexB = "00" + hexB;
			}
			unicodeBytes = unicodeBytes + "\\u" + hexB;
		}
		return unicodeBytes;
	}


	public static boolean isPhone(String phone) {
		Pattern p = Pattern.compile("^1[34578]\\d{9}$");
		Matcher m = p.matcher(phone);
		return m.matches();
	}

	/**
	 * 
	 * @param context
	 * @param Action
	 *            接收方的的 注册的 广播key ，也是传值得 key
	 * @param actionKey
	 *            处理的指令
	 */
	public static void sendOrdersBroadcast(Activity context, String Action,
			int actionKey) {
		Intent intent = new Intent(Action);
		intent.putExtra(Action, actionKey);
		context.getApplication().sendOrderedBroadcast(intent, null);
	}

	/**
	 * 
	 * @param time
	 *            yyyy-MM-dd
	 * @return millenSeconds
	 */
	public static long getMillTime(String time) {
		SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdff.parse(time).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static Bitmap returnBitMap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;

		try {
			myFileUrl = new URL(url);
			HttpURLConnection conn;

			conn = (HttpURLConnection) myFileUrl.openConnection();

			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	// 获取屏幕的宽度
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	} // 获取屏幕的高度

	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}

	public static Bitmap getRoundedCornerBitmap(String url) {
		Bitmap bitmap = returnBitMap(url);
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = bitmap.getWidth() / 2;

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	// 是否有存储卡
	public static boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取屏幕中控件顶部位置的高度--即控件顶部的Y点
	 * 
	 * @return
	 */
	public static int getScreenViewTopHeight(View view) {
		return view.getTop();
	}

	/**
	 * 获取屏幕中控件底部位置的高度--即控件底部的Y点
	 * 
	 * @return
	 */
	public static int getScreenViewBottomHeight(View view) {
		return view.getBottom();
	}

	/**
	 * 获取屏幕中控件左侧的位置--即控件左侧的X点
	 * 
	 * @return
	 */
	public static int getScreenViewLeftHeight(View view) {
		return view.getLeft();
	}

	/**
	 * 获取屏幕中控件右侧的位置--即控件右侧的X点
	 * 
	 * @return
	 */
	public static int getScreenViewRightHeight(View view) {
		return view.getRight();
	}

}
