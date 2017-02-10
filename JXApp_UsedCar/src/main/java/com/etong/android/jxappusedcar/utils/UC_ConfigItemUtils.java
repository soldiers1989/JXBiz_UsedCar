package com.etong.android.jxappusedcar.utils;

/**
 * @author : by sunyao
 * @ClassName : UC_ConfigItemUtils
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/12/13 - 19:33
 */

public class UC_ConfigItemUtils {

    private static String [] carConfigStr = {
            "里程数：",
            "年龄：",
            "排放量：",
            "变速箱：",
            "年检到期：",
            "交强制到期：",
            "商业险到期：",
            "过户记录次数：",
    };

    private static String [] carConLetterStr = {
//            "f_mileage",
//            "f_registerdate",
//            "排放量：",
//            "gearmode",
//            "f_inspectiondate",
//            "f_highinsurancedate",
//            "f_insurancedate",
//            "f_transfercount",
            "f_mileage",
            "f_registerdate",
            "f_emission",
            "gearmode",
            "f_inspectiondate",
            "f_highinsurancedate",
            "f_insurancedate",
            "f_transfercount",
    };

    public static String[] getCarConfigStr() {
        return carConfigStr;
    }

    public static String[] getCarConLetterStr() {
        return carConLetterStr;
    }
}
