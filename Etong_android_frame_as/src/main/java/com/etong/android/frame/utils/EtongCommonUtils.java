package com.etong.android.frame.utils;

/**
 * @desc  防止多次点击的工具类
 * @createtime 2016/11/16 - 16:07
 * @Created by xiaoxue.
 */

public class EtongCommonUtils {
    private static long lastClickTime;
    public static final int MIN_CLICK_DELAY_TIME = 1500;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < MIN_CLICK_DELAY_TIME) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
