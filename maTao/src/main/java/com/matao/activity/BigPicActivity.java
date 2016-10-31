package com.matao.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.matao.adapter.ViewPagerAdapter;
import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.MTApplication;
import com.matao.utils.MTUtils;
import com.matao.view.TopBar;
import com.matao.view.TopBar.topBarClickListener;

/**
 * @author: ZhouYang
 * @E-mail: ZhouYangGaoGao@163.com
 * @time:2015-4-27 下午4:08:52
 * @Description:浏览大图界面
 */
@ContentView(R.layout.activity_bigimag)
public class BigPicActivity extends BaseActivity implements
		OnPageChangeListener {
	@ViewInject(R.id.bigimag_topbar)
	private TopBar topBar;
	@ViewInject(R.id.bigimag_viewpager)
	private ViewPager pager;
	private ViewPagerAdapter pagerAdapter;
	public static List<String> imgs = new ArrayList<String>();
	private List<ImageView> ivs = new ArrayList<ImageView>();// 广告展示内容
	public static String currImg = "";
	private int index = 0;

	@Override
	public void onClick(View v) {

	}

	// 初始化viewpager
	private void initViewPager() {
		pagerAdapter = new ViewPagerAdapter(ivs);
		pager.setOnPageChangeListener(this);
		pager.setAdapter(pagerAdapter);
		pager.setCurrentItem(index);
	}

	public static Uri uri = null;

	@Override
	public void setContentView() {
		ViewUtils.inject(this);
		topBar = (TopBar) findViewById(R.id.bigimag_topbar);
		topBar.setOnTopBarClickListener(new topBarClickListener() {

			@Override
			public void leftClick() {
				finish();
			}

			@Override
			public void rightClick() {
				// 获取当前展示图片保存到相册
				if (ivs.size() != 0) {
					ImageView img = ivs.get(index);
					img.setDrawingCacheEnabled(true);
					Bitmap bitmap = img.getDrawingCache(true);
					saveImageToGallery(BigPicActivity.this, bitmap);
					img.setDrawingCacheEnabled(false);
				}
			}
		});
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	// 保存图片
	@SuppressLint("NewApi")
	public static void saveImageToGallery(Context context, Bitmap bmp) {
		Logger.i("bmp.length", "" + bmp.getByteCount());
		// 首先保存图片
		File file = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DCIM).getPath()
				+ "/Camera/", MTUtils.getTimeStamp() + ".jpg");

		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
			Logger.i("file.length", "" + file.length());
			bmp.recycle();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 其次把文件插入到系统图库
		// try {
		// MediaStore.Images.Media.insertImage(context.getContentResolver(),
		// file.getAbsolutePath(), file.getName(), null);
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
		// MediaScannerConnection.scanFile(context,
		// new String[] { file.getAbsolutePath() }, null, null);
		// 最后通知图库更新
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
				Uri.parse(file.getAbsolutePath())));
		MTUtils.Toast(context, "保存成功！");
	}

	@Override
	public void findViewById() {
		ImageView img = null;
		for (int i = 0; i < imgs.size(); i++) {
			img = new ImageView(this);
			MTApplication.bmu.display(img, imgs.get(i));
			img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					finish();
				}
			});
			ivs.add(img);
			if (currImg.toLowerCase().equals(imgs.get(i).toLowerCase())) {
				index = i;
			}
		}
		topBar.setTitle((index + 1) + "/" + imgs.size());
		initViewPager();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int p) {
		index = p;
		topBar.setTitle((p + 1) + "/" + imgs.size());
	}
}
