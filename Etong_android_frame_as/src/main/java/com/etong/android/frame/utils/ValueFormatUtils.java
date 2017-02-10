package com.etong.android.frame.utils;

/**
 * @desc (格式转换工具类)
 * @createtime 2016/12/29 - 15:25
 * @Created by wukefan.
 */
public class ValueFormatUtils {

    /**
     * @desc (当Double值的小数点后一位为零时，去掉小数点, 返回String类型)
     */
    public static String setIntOrPointValue(Double value) {
        String valueStr = null;
        if (value % 1 == 0.0) {//小数点后面全为零则设置为int
            valueStr = value.intValue() + "";
        } else {
            valueStr = value + "";
        }
        return valueStr;
    }
}
