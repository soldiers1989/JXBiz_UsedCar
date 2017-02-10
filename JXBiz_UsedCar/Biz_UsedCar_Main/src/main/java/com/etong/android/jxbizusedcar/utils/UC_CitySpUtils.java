package com.etong.android.jxbizusedcar.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.frame.utils.L;
import com.etong.android.jxappusedcar.bean.UC_CollectOrScannedCarInfo;
import com.etong.android.jxbizusedcar.bean.UC_CityName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author : by sunyao
 * @ClassName : UC_CitySpUtils
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/11/1 - 17:18
 */

public class UC_CitySpUtils {

    public static String CITY_SP_NAME = "sp city name";
    private static SharedPublisher mSharedPublisher = SharedPublisher.getInstance();

    /**
     * @Title : cartAdd
     * @Description : 收藏操作
     * @params
     */
    public static void addCityName(UC_CityName uc_cityName) {
        int i = 1;
        Map newMap = new HashMap();
        newMap.put(uc_cityName.getCity_id(), uc_cityName);
        if (null != getCityName()) {
            Map oldMap = getCityNameMap();
            if (oldMap != null) {
                Iterator it = oldMap.entrySet().iterator();
                while (it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        Object key = entry.getKey();
                        String data = String.valueOf(oldMap.get(String.valueOf(key)));
                        UC_CityName c = JSON.parseObject(data, UC_CityName.class);
                        newMap.put(key, c);
                        break;
                }
            }
        }
        L.json(JSON.toJSONString(newMap));
        mSharedPublisher.put(CITY_SP_NAME, JSON.toJSONString(newMap));
    }

    /**
     * @return Map 返回类型
     * @throws
     * @Title : getVechileGoodsCartInfo
     * @Description : 获取收藏车辆信息
     * @params
     */
    public static List<UC_CityName> getCityName() {
        List<UC_CityName> mList = new ArrayList<UC_CityName>();
        Map getCityName = null;
        try {
            String cityStr = mSharedPublisher.getString(CITY_SP_NAME);
            getCityName = JSON.parseObject(cityStr, Map.class);
            UC_CityName info = null;
            if (getCityName != null) {
                Iterator it = getCityName.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    Object key = entry.getKey();
                    String data = String.valueOf(getCityName.get(String.valueOf(key)));
                    L.d("从本地获取到的数据:", data);
                    info = JSON.parseObject(data, UC_CityName.class);
                    if (null != info) {
                        mList.add(info);
                    }
                }
            }
        } catch (Exception e) {
        }
        return mList;
    }

    public static Map getCityNameMap() {
        Map cityMap = null;
        try {
            String s = mSharedPublisher.getString(CITY_SP_NAME);
            cityMap = JSON.parseObject(s, Map.class);
        } catch (Exception e) {

        }
        return cityMap;
    }

}
