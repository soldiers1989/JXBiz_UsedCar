package com.etong.android.jxappfours.find_car.grand.carset;

import android.content.Intent;

/**
 * @desc (排量设置工具类)
 * @createtime 2017/1/5 - 18:37
 * @Created by wukefan.
 */
public class FC_OutFormatUtil {


    public static String setCarLelOutValue(String carlevel, Integer minOut, Integer maxOut) {
        String valueStr = null;

        if (null == carlevel) {
            if (null != minOut && null != maxOut) {
                double v = minOut;         // 最低排量
                v = v / 1000;
                String format = String.format("%.1f", v);

                double v1 = maxOut;        // 最高排量
                v1 = v1 / 1000;
                String format1 = String.format("%.1f", v1);
                valueStr = "未记录" + "/" + format + "T  " + format1 + "T"; // 车辆等级
            } else if (null != minOut) {
                double v = minOut;         // 最低排量
                v = v / 1000;
                String format = String.format("%.1f", v);
                valueStr = "未记录" + "/" + format + "T  "; // 车辆等级
            } else if (null != maxOut) {
                double v1 = maxOut;        // 最高排量
                v1 = v1 / 1000;
                String format1 = String.format("%.1f", v1);
                valueStr = "未记录" + "/" + format1 + "T"; // 车辆等级
            } else {
                valueStr = "未记录";
            }

        } else if (null != carlevel) {
            if (null != minOut && null != maxOut) {
                double v = minOut;         // 最低排量
                v = v / 1000;
                String format = String.format("%.1f", v);

                double v1 = maxOut;        // 最高排量
                v1 = v1 / 1000;
                String format1 = String.format("%.1f", v1);
                valueStr = carlevel + "/" + format + "T  " + format1 + "T"; // 车辆等级
            } else if (null != minOut) {
                double v = minOut;         // 最低排量
                v = v / 1000;
                String format = String.format("%.1f", v);
                valueStr = carlevel + "/" + format + "T  "; // 车辆等级
            } else if (null != maxOut) {
                double v1 = maxOut;        // 最高排量
                v1 = v1 / 1000;
                String format1 = String.format("%.1f", v1);
                valueStr = carlevel + "/" + format1 + "T"; // 车辆等级
            } else {
                valueStr = carlevel + "/未记录";
            }

        }
        return valueStr;
    }

    public static String setPriceValue(Double minguide, Double maxguide) {
        String valueStr = "未记录";
        if (null != minguide && null != maxguide) {
            valueStr = minguide + " - " + maxguide + "万";   // 最高、最低指导价
        }
        return valueStr;
    }

}
