package com.etong.android.frame.widget.pulltofresh.internal;

import com.etong.android.frame.utils.L;

public class Utils {

	static final String LOG_TAG = "PullToRefresh";

	public static void warnDeprecation(String depreacted, String replacement) {
		L.e(LOG_TAG, "You're using the deprecated " + depreacted + " attr, please switch over to " + replacement);
	}

}
