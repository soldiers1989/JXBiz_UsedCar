package com.etong.android.jxappusedcar.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : by sunyao
 * @ClassName : UC_CarDetail_Utils
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/10/20 - 20:16
 */

public class UC_CarDetail_Utils {


    private JSONObject mObj;

    public UC_CarDetail_Utils(JSONObject obj) {
        this.mObj = obj;
    }

    static String[] strTitle = new String[]{
            "分期方案", "亮点配置", "车辆历史", "车辆配置"
    };

    static String [][]strItem = new String[][] {
            {
                    "年检到期：", "维修保养：",
                    "保险到期：", "过户次数：",
                    "质保到期：", "用途：",
                    "原厂延保：", "环保标准"
            },
            {
                    "发动机：", "变速器：",
                    "车辆级别：", "颜色：",
                    "最高车速：", "驱动方式：",
                    "燃油标号："
            }
    };

    static String [][] strLetterItems = new String[][] {

    };

    public List getCarDetailInfo() {
        if (mObj == null) {
            return null;
        }

        // 遍历其中的数据
        List beanList = new ArrayList();
        JSONObject fanace = (JSONObject) mObj.get("分期方案");


        return beanList;
    }
}
