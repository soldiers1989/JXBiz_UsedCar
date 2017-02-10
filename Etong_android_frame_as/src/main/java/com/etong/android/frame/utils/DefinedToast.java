package com.etong.android.frame.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/10/13 0013--9:01
 * @Created by wukefan.
 */
public class DefinedToast {

    /**
     * 之前显示的内容
     */
    private static String oldMsg;
    /**
     * Toast对象
     */
    private static Toast toast = null;
    /**
     * 第一次时间
     */
    private static long oneTime = 0;
    /**
     * 第二次时间
     */
    private static long twoTime = 0;

    /**
     * 显示Toast
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message, int T) {
        // 如果传过来的值为空则将message设置为空字符串
        if(TextUtils.isEmpty(message)) {
            message = "";
        }

        int time = (T == 0) ? Toast.LENGTH_SHORT
                : ((T == 1) ? Toast.LENGTH_LONG : T);

        if (toast == null) {
            toast = Toast.makeText(context, message, time);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (message.equals(oldMsg)) {
                if (twoTime - oneTime > time) {
                    toast.show();
                }
            } else {
                oldMsg = message;
                toast.setText(message);
                toast.show();
            }
        }
        oneTime = twoTime;
    }



    //    private static Toast mToast;
//    private static Handler mhandler = new Handler();
//    private static Runnable r = new Runnable(){
//        public void run() {
//            mToast.cancel();
//        };
//    };
//
//    public static void showToast (Context context, String text, int T) {
//        mhandler.removeCallbacks(r);
//
//        int time = (T == 0) ? Toast.LENGTH_SHORT
//                : ((T == 1) ? Toast.LENGTH_LONG : T);
//
//        if (null != mToast) {
//            mToast.setText(text);
//        } else {
//            mToast = Toast.makeText(context, text, time);
//        }
//        mhandler.postDelayed(r, 5000);
//        mToast.show();
//    }
//
//    public static void showToast (Context context, int strId, int T) {
//
//        showToast (context, context.getString(strId), T);
//    }
}
