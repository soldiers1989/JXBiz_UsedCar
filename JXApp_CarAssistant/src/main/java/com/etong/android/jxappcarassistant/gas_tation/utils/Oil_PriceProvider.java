package com.etong.android.jxappcarassistant.gas_tation.utils;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpMethodWay;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/9/18 - 11:50
 * @Created by xiaoxue.
 */
public class Oil_PriceProvider {

    private HttpPublisher mHttpPublisher;
    private static String TAG = "Oil_PriceProvider";
    private static Oil_PriceProvider instance;

    public static final String USER_SUCCEED = "PT_ERROR_SUCCESS";
    public static final String USER_EXISTS = "PT_ERROR_REG_REDUPLICATED";
    public static final String USER_DUPLICATE = "PT_ERROR_RECORD_REDUPLICATED";

    private Oil_PriceProvider() {

    }

    public void initialize(HttpPublisher httpPublisher) {
        this.mHttpPublisher = httpPublisher;
    }

    public static Oil_PriceProvider getInstance() {
        if (null == instance) {
            instance = new Oil_PriceProvider();
        }
        return instance;
    }

    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title : getOilpPrice
     * @Description :获取今日油价
     * @params
     */
    public void getOilPrice(String prov) {
        Map headMap = new HashMap<String, String>();
        headMap.put("apikey", "8b940eb30b6ddd5584e97cd6f0c08a92");
        Map paramsMap = new HashMap<String, String>();
        paramsMap.put("prov", prov);
        HttpMethod method = new HttpMethod(FrameHttpUri.OIL_PRICE, paramsMap, headMap);
        method.setWay(HttpMethodWay.GET);
//        method.setSetCache(true);
//        method.setCacheTimeLive(1 * 60 * 60 * 24);//设置缓存3个小时
        mHttpPublisher.sendRequest(method, FrameHttpTag.OIL_PRICE);
    }


    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title : getGas_station
     * @Description : 检索周边加油站
     * @params
     */
    public void getGas_station(Double lon, Double lat, Integer r) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", "4fbae0245e944c26aed425c0785508fe");
        map.put("lon", lon + "");
        map.put("lat", lat + "");
        map.put("r", r + "");
        HttpMethod method = new HttpMethod(FrameHttpUri.OIL_LOCAL, map);
        method.setSetCache(true);
        method.setCacheTimeLive(1 * 60 * 60 * 24);//设置缓存24个小时
        mHttpPublisher.sendRequest(method, FrameHttpTag.OIL_LOCAL);
    }

    /**
     * @desc 违章查询
     * @createtime 2016/11/23 - 9:41
     * @author xiaoxue
     */
    public void getViolation_query(String lsprefix,String lsnum,String frameno,String engineno){
        Map<String,String> map=new HashMap<>();
        map.put("lsprefix",lsprefix);
        map.put("lsnum",lsnum);
        map.put("frameno",frameno);
        map.put("engineno",engineno);
        HttpMethod method = new HttpMethod(FrameHttpUri.VIOLATION_QUERY, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.VIOLATION_QUERY);
    }

}
