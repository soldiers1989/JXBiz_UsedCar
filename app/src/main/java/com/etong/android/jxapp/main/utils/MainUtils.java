package com.etong.android.jxapp.main.utils;

import android.text.TextUtils;

import com.etong.android.jxapp.R;

/**
 * @author : by sunyao
 * @ClassName : MessageUtils
 * @Description : (本地图片获取工具类)
 * @date : 2016/9/13 - 11:01
 */
public class MainUtils {

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

    /**
     * @desc (根据图片的名字获取到图片的uri)
     * @user sunyao
     * @createtime 2016/12/5 - 15:21
     * @param
     * @return
     */
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
