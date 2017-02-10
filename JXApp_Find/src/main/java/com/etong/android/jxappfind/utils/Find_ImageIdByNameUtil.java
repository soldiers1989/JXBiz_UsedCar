package com.etong.android.jxappfind.utils;

import com.etong.android.jxappfind.R;

/**
 * @desc (根据图片名称获取R.java中对应的id)
 * @createtime 2017/1/5 - 16:24
 * @Created by xiaoxue.
 */

public class Find_ImageIdByNameUtil {

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
}
