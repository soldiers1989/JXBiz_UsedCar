package com.etong.android.jxappusedcar.provider;

import android.text.TextUtils;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpMethodWay;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @desc (津湘二手车请求)
 * @createtime 2016/11/25 - 10:37
 * @Created by xiaoxue.
 */

public class UC_WorldProvider {
    private HttpPublisher mHttpPublisher;
    private static String TAG = "UC_WorldProvider";
    private static UC_WorldProvider instance;
    private String f_orderman;
    private String f_phone;
    private HttpMethod queryFilterMethod;

    private UC_WorldProvider() {

    }

    public void initialize(HttpPublisher httpPublisher) {
        this.mHttpPublisher = httpPublisher;
    }

    public static UC_WorldProvider getInstance() {
        if (null == instance) {
            instance = new UC_WorldProvider();
        }
        return instance;
    }


    /**
     * @param
     * @return
     * @desc (津湘二手车中的查询车辆详情接口)
     * @user sunyao
     * @createtime 2016/11/25 - 11:20
     */
    public void getJXUsedCarDetail(Map<String, String> map) {
        Iterator<String> it = map.keySet().iterator();
        String params = "?";
        while (it.hasNext()) {
            String key = it.next();
            String value = map.get(key);
            params = params + key + "=" + value + "&";
        }

        HttpMethod method = new HttpMethod(FrameHttpUri.JXUC_CarDetail + params, null);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, FrameHttpTag.USED_CAR_CARDETAIL_INFO);
    }

    /**
     * @desc (预约看车)
     * @createtime 2016/11/25 - 10:48
     * @author xiaoxue
     */
    public void orderCar(String f_cartype, String f_cartypeid, String tag) {
        Map<String, String> map = new HashMap<>();
        if (FrameEtongApplication.getApplication().isLogin()) {
            if (null != FrameEtongApplication.getApplication().getUserInfo().getUserName()) {
                map.put("f_orderman", FrameEtongApplication.getApplication().getUserInfo().getUserName());
            }
            map.put("f_phone", FrameEtongApplication.getApplication().getUserInfo().getUserPhone());
        }
        map.put("f_cartype", f_cartype);
        map.put("f_cartypeid", f_cartypeid);
        HttpMethod method = new HttpMethod(FrameHttpUri.ORDERCAR, map);
       /* if(FrameEtongApplication.getApplication().isLogin()){
            f_orderman = FrameEtongApplication.getApplication().getUserInfo().getUserName();
            f_phone =FrameEtongApplication.getApplication().getUserInfo().getUserPhone();
        }
        HttpMethod method = new HttpMethod(FrameHttpUri.ORDERCAR+"?f_phone="+ f_phone
                +"&f_orderman="+ f_orderman +"&f_cartype="+f_cartype+"&f_cartypeid="+f_cartypeid, null);*/
        mHttpPublisher.sendRequest(method, FrameHttpTag.ORDERCAR + tag);
    }


    /**
     * @desc (二手车世界 车列表)
     * @createtime 2016/11/25 - 13:46
     * @author xiaoxue
     */
    public void worldList(int pageSize, int pageCurrent, int sortType, int isPullDown, String pricemin, String pricemax,
                          String mileage, String registercode, String vehicletype, String country, String gearmode) {
        Map<String, String> map = new HashMap<>();
        map.put("pageSize", pageSize + "");
        map.put("pageCurrent", pageCurrent + "");
        map.put("isPullDown", isPullDown + "");
        map.put("sortType", sortType + "");
        if (null != pricemin) {
            map.put("pricemin", pricemin);
        }
        if (null != pricemax) {
            map.put("pricemax", pricemax);
        }
        if (null != mileage) {
            map.put("mileage", mileage);
        }
        if (null != registercode) {
            map.put("registercode", registercode);
        }
        if (null != vehicletype) {
            map.put("vehicletype", vehicletype);
        }
        if (null != country) {
            map.put("country", country);
        }
        if (null != gearmode) {
            map.put("gearmode", gearmode);
        }
        HttpMethod method = new HttpMethod(FrameHttpUri.CARLIST, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.CARLIST);
    }

    /**
     * @desc (二手车世界 搜索)
     * @createtime 2016/11/25 - 16:12
     * @author xiaoxue
     */
    public void worldSearch(int pageSize, int pageCurrent, String title, String str) {
        Map<String, String> map = new HashMap<>();
        map.put("pageSize", pageSize + "");
        map.put("pageCurrent", pageCurrent + "");
        String carName = "";
        try {
            carName = URLEncoder.encode(title, "utf-8");
            if (carName.contains("+")) {
                carName.replace("+", "20%");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put("title", carName);
        HttpMethod method = new HttpMethod(FrameHttpUri.CARLIST, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.CARLIST + str);
    }

    /**
     * @desc (预约卖车)
     * @createtime 2016/11/28 0028-9:58
     * @author wukefan
     */
    public void orderSellCar(String f_phone, String f_orderman, String f_cartype, String f_cartypeid, String f_carset, String f_carsetid, String f_brand, String f_platenumber, String f_remark) {

        Map<String, String> map = new HashMap<String, String>();

        FrameUserInfo mUser = FrameEtongApplication.getApplication().getUserInfo();
        if (FrameEtongApplication.getApplication().isLogin() && null != mUser.getUserIdCard() && TextUtils.isEmpty(mUser.getUserIdCard())) {
            map.put("f_cardid", mUser.getUserIdCard());
        }

        map.put("f_phone", f_phone);
        map.put("f_orderman", f_orderman);
        map.put("f_cartype", f_cartype);
        map.put("f_cartypeid", f_cartypeid);
        map.put("f_carsetid", f_carsetid);
        map.put("f_platenumber", f_platenumber);

        if (null != f_remark && !TextUtils.isEmpty(f_remark.trim())) {
            map.put("f_remark", f_remark);
        }
        if (null != f_carset) {
            map.put("f_carset", f_carset);

        }
        if (null != f_brand) {
            map.put("f_brand", f_brand);
        }

        HttpMethod method = new HttpMethod(FrameHttpUri.OrderSellCar, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.ORDER_SELL_CAR);
    }

    /**
     * @desc (筛选数据字典)
     * @createtime 2016/11/28 0028-9:58
     * @author wukefan
     */
    public void queryFilterDicData() {

        Map<String, String> map = new HashMap<String, String>();
        queryFilterMethod = new HttpMethod(FrameHttpUri.QueryFilterDicData, map);
        queryFilterMethod.setSetCache(true);
        queryFilterMethod.setCacheTimeLive(1 * 60 * 60 * 24);//设置缓存24个小时
        mHttpPublisher.sendRequest(queryFilterMethod, FrameHttpTag.QUERY_FILTER_DIC_DATA);
    }

    public HttpMethod getFilterMethod() {
        if (queryFilterMethod != null) {
            return queryFilterMethod;
        }
        return null;
    }
    /**
     * @desc (点击收藏接口)
     * @createtime 2016/12/2 - 14:46
     * @author xiaoxue
     */
    public void clickCollection(String dvid, String tag) {
        Map<String, String> map = new HashMap<>();
        if (FrameEtongApplication.getApplication().isLogin()) {
            if (null != FrameEtongApplication.getApplication().getUserInfo().getUserId()) {
                map.put("userId", FrameEtongApplication.getApplication().getUserInfo().getUserId());
            }
        }
        map.put("dvid", dvid);
        HttpMethod method = new HttpMethod(FrameHttpUri.CLICKCOLLECTION, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.CLICKCOLLECTION + tag);
    }

    /**
     * @param
     * @return
     * @desc (车辆配置获取)
     * @user sunyao
     * @createtime 2016/12/12 - 18:42
     */
    public void getCarInfoConfig(String carTypeId) {

        if (TextUtils.isEmpty(carTypeId)) {
            return;
        }
        String url = FrameHttpUri.CARCONFIGINFO + "?f_cartypeid=" + carTypeId;
        HttpMethod method = new HttpMethod(url, null);
        mHttpPublisher.sendRequest(method, FrameHttpTag.CONFIGINFO);
    }
}
