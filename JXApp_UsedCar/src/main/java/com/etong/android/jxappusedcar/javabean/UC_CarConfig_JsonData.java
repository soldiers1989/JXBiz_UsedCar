package com.etong.android.jxappusedcar.javabean;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 车辆配置json数据
 * @createtime 2016/10/14 - 15:14
 * @Created by xiaoxue.
 */

public class UC_CarConfig_JsonData {

    public static String getJsonArray() {
        List<UC_CarConfigJavabean> list=new ArrayList<>();
        UC_CarConfigJavabean bean=new UC_CarConfigJavabean();
        bean.setColor("季风灰,朱鹭白,柚木棕,极地银,火山红,水晶银,幻影黑,月光蓝,白金色");
        bean.setAvgOilwear(0);
        bean.setBrand("奥迪");
        bean.setOutputVol(1984);
        bean.setMaxSpeed("215");
        bean.setOfficialSpeedup("8.8");
        bean.setFullname("长城M4 2012款手动豪华型");
        list.add(bean);

        String jsonArr = JSONArray.toJSONString(list);

        return jsonArr;
    }
}
