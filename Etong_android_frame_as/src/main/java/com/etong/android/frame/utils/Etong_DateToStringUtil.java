package com.etong.android.frame.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @desc (时间戳转换成字符串)
 * @createtime 2017/1/4 - 11:36
 * @Created by xiaoxue.
 */

public class Etong_DateToStringUtil {
    public static String getDateToString(long time) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }
}
