package com.matao.pulltorefresh.library.internal;

import com.matao.utils.Logger;

public class Utils {

	static final String LOG_TAG = "PullToRefresh";

	public static void warnDeprecation(String depreacted, String replacement) {
		Logger.e(LOG_TAG, "You're using the deprecated " + depreacted
				+ " attr, please switch over to " + replacement);
	}

}
