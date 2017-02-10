package com.etong.android.frame.utils;


import com.orhanobut.logger.Logger;

/**
 * @author : by sunyao
 * @ClassName : L
 * @Description : (打印日志的类)
 * @date : 2016/11/4 - 17:49
 */

public class L {

    private L() {
		/* 不能被初始化 cannot be instantiated*/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static final String TAG = "jxbiz_usedcar";      // 二手车的打印Tag值

/*
####################################################################################################
#                               使用默认Tag值                                                      #
####################################################################################################
*/
    public static void i(String msg) {
        Logger.i(msg);
    }
    public static void d(String msg) {
        Logger.d(msg);
    }
    public static void e(String msg) {
        Logger.e(msg);
    }
    public static void v(String msg) {
        Logger.v(msg);
    }
    public static void json(String msg) {
        Logger.json(msg);
    }

/*
####################################################################################################
#                               使用自定义Tag值                                                    #
####################################################################################################
*/
    public static void i(String tag, String msg) {
        Logger.t(tag).i(msg);
    }

    public static void d(String tag, String msg) {
        Logger.t(tag).d(msg);
    }
    public static void e(String tag, String msg) {
        Logger.t(tag).e(msg);
    }
    public static void v(String tag, String msg) {
        Logger.t(tag).v(msg);
    }

}
