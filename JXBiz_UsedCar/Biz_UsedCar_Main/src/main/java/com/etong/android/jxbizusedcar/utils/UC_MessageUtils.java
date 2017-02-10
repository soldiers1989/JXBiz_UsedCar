package com.etong.android.jxbizusedcar.utils;

import android.text.TextUtils;

import com.etong.android.jxbizusedcar.R;


/**
 * @author : by sunyao
 * @ClassName : MessageUtils
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/9/13 - 11:01
 */
public class UC_MessageUtils {

    /**
     * 根据图片名称获取R.java中对应的id
     *
     * @param name
     * @return
     */
    public static int getImageIdByName(String name) {
        int value = 0;
        if (null != name) {
            if (name.indexOf(".") != -1) {
                name = name.substring(0, name.indexOf("."));
            }
            Class<R.mipmap> cls = R.mipmap.class;
            try {
                value = cls.getDeclaredField(name).getInt(null);
            } catch (Exception e) {

            }
        }
        return value;
    }

    public static String getImageUrl(String name) {
        int value = 0;
        if (!TextUtils.isEmpty(name)) {
            if (name.indexOf(".") != -1) {
                name = name.substring(0, name.indexOf("."));
            }
            Class<R.mipmap> cls = R.mipmap.class;
            try {
                value = cls.getDeclaredField(name).getInt(null);
            } catch (Exception e) {

            }
            return  "drawable://"+value+"";
        }
        return null;
    }
}
