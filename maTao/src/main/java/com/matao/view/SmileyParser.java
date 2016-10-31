package com.matao.view;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import com.matao.matao.R;
import com.matao.utils.Logger;
import com.matao.utils.MTUtils;

/**
 * 匹配文字和表情
 * 
 * @author: ZhouYang
 * @time:2015-5-27 下午2:30:54
 * @Description:TODO
 */
public class SmileyParser {
	/*
	 * 单例模式 1文字资源，图片资源 2.使用正则表达式进行匹配文字 3.把edittext当中整体的内容匹配正则表达式一次
	 * 4.SpannableStringBuilder 进行替换
	 */
	private static SmileyParser sInstance;

	public static SmileyParser getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new SmileyParser(context);
		}
		return sInstance;
	}

	private final Context mContext;
	private final String[] arrText;
	// 正则表达式
	private final Pattern mPattern;
	// String 图片字符串 Integer表情
	private final HashMap<String, Integer> mSmileyToRes;

	private SmileyParser(Context context) {
		mContext = context;
		String json = null;
		// 获取表情文字资源
		arrText = MTUtils.getBQName(context);
		// 获取表情ID与表情图标的Map
		mSmileyToRes = buildSmileyToRes();
		// 获取构建的正则表达式
		mPattern = buildPattern();
	}

	// static class Smileys {
	// 表情图片集合
	private static final int[] DEFAULT_SMILEY_RES_IDS = { R.drawable.p0,
			R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4,
			R.drawable.p5, R.drawable.p6, R.drawable.p7, R.drawable.p8,
			R.drawable.p9, R.drawable.p10, R.drawable.p11, R.drawable.p12,
			R.drawable.p13, R.drawable.p14, R.drawable.p15, R.drawable.p16,
			R.drawable.p17, R.drawable.p18, R.drawable.p19, R.drawable.p20,
			R.drawable.p21, R.drawable.p22, R.drawable.p23, R.drawable.p24,
			R.drawable.p25, R.drawable.p26, R.drawable.p27, R.drawable.p28,
			R.drawable.p29, R.drawable.p30, R.drawable.p31, R.drawable.p32,
			R.drawable.p33, R.drawable.p34, R.drawable.p35, R.drawable.p36,
			R.drawable.p37, R.drawable.p38, R.drawable.p39, R.drawable.p40,
			R.drawable.p41, R.drawable.p42, R.drawable.p43, R.drawable.p44,
			R.drawable.p45, R.drawable.p46, R.drawable.p47, R.drawable.p48,
			R.drawable.p49, R.drawable.p50, R.drawable.p51, R.drawable.p52,
			R.drawable.p53, R.drawable.p54, R.drawable.p55, R.drawable.p56,
			R.drawable.p57, R.drawable.p58, R.drawable.p59, R.drawable.p60,
			R.drawable.p61, R.drawable.p62, R.drawable.p63, R.drawable.p64,
			R.drawable.p65, R.drawable.p66, R.drawable.p67, R.drawable.p68,
			R.drawable.p69, R.drawable.p70, R.drawable.p71, R.drawable.p72,
			R.drawable.p73, R.drawable.p74, R.drawable.p75, R.drawable.p76,
			R.drawable.p77, R.drawable.p78, R.drawable.p79, R.drawable.p80,
			R.drawable.p81, R.drawable.p82, R.drawable.p83, R.drawable.p84,
			R.drawable.p85, R.drawable.p86, R.drawable.p87, R.drawable.p88,
			R.drawable.p89, };

	/**
	 * 使用HashMap的key-value的形式来影射表情的ID和图片资源
	 * 
	 * @return
	 */
	private HashMap<String, Integer> buildSmileyToRes() {
		if (DEFAULT_SMILEY_RES_IDS.length != arrText.length) {
			Logger.e("buildSmileyToRes", DEFAULT_SMILEY_RES_IDS.length + "---"
					+ arrText.length);
		}
		HashMap<String, Integer> smileyToRes = new HashMap<String, Integer>(
				arrText.length);
		for (int i = 0; i < arrText.length; i++) {
			// 图片名称作为key值，图片资源ID作为value值
			smileyToRes.put(arrText[i], DEFAULT_SMILEY_RES_IDS[i]);
		}
		return smileyToRes;
	}

	/**
	 * 构建正则表达式,用来找到我们所要使用的图片
	 * 
	 * @return
	 */
	private Pattern buildPattern() {
		StringBuilder patternString = new StringBuilder(arrText.length * 3);
		patternString.append('(');
		for (String s : arrText) {
			patternString.append(Pattern.quote(s));
			patternString.append('|');
		}
		patternString.replace(patternString.length() - 1,
				patternString.length(), ")");
		// 把String字符串编译成正则表达式(sad|sad|asd,zhang@163.com)
		// ([调皮]|[调皮]|[调皮])
		return Pattern.compile(patternString.toString());
	}

	/**
	 * 根据文本替换成图片
	 * 
	 * @param text
	 *            对应表情
	 * @return 一个表示图片的序列
	 */
	public CharSequence addSmileySpans(CharSequence text) {
		// 把文字替换为对应图片
		SpannableStringBuilder builder = new SpannableStringBuilder(text);
		// 判断提取工具类（按照正则表达式）
		Matcher matcher = mPattern.matcher(text);
		while (matcher.find()) {
			// 获取对应表情的图片id
			int resId = mSmileyToRes.get(matcher.group());
			// 替换制定字符
			builder.setSpan(new ImageSpan(mContext, resId), matcher.start(),
					matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return builder;
	}
}
