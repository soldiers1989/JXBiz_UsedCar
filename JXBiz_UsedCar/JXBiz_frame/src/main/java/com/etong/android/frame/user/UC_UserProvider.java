package com.etong.android.frame.user;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpMethodWay;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.request_init.UC_HttpUrl;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.MD5Utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Dasheng on 2016/8/29.
 */
public class UC_UserProvider {

    private static UC_UserProvider mProvider;
    private static SharedPublisher mSharedPublisher;
    private static HttpPublisher mHttpPublisher;
    private static UC_User mUser;


    public static final String USER_INFO = "Customer info";


    private UC_UserProvider() {

    }

    public static void initalize(HttpPublisher httpPublisher) {

        mHttpPublisher = httpPublisher;

    }

    public static void initalize(SharedPublisher sharedPublisher, HttpPublisher httpPublisher) {

        mSharedPublisher = sharedPublisher;
        mHttpPublisher = httpPublisher;

    }

    public static UC_UserProvider getInstance() {

        if (mProvider == null) {

            mProvider = new UC_UserProvider();

        }

        return mProvider;

    }

    public void logout() {
      /*  mUser = null;
        mSharedPublisher.clear(USER_INFO);*/
    }


    /**
     * 判断是否已经登录
     * @return
     */
   /* public boolean isLogin() {
        return (getUser().getDeptname()!= null &&getUser().getDeptcode() != null);
    }*/

    /**
     * 获取用户的类型
     * @return
     */
   /* public String getUserType(){

        String userType = getUser().getRole()+"";
        if(userType != null)
            return userType;
        return "";
    }*/


    /**
     * 获取缓存中的数据
     *
     * @return
     */
    public UC_User getUser() {

        String userInfo = mSharedPublisher.getString(USER_INFO);
        if (userInfo != null && !userInfo.isEmpty())
            mUser = JSON.parseObject(userInfo, UC_User.class);
        else
            mUser = new UC_User();

        return mUser;

    }

    /**
     * 缓存用户信息
     *
     * @param userInfo
     */
    public void saveUserInfo(JSONObject userInfo) {

        if (userInfo != null) {
            mSharedPublisher.put(UC_UserProvider.USER_INFO, userInfo.toJSONString());
        }

    }

    /**
     * 获取验证码
     *
     * @param map
     */
    public void getVerificationCode(Map<String, String> map) {
        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_GET_VERIFICASTION_CODE, map);
        mHttpPublisher.sendRequest(method, UC_HttpTag.GET_VER_CODE);
    }

    /**
     * 登录
     *
     * @param map
     */
    public void login(Map<String, String> map) {
        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_LOGIN, map);
        mHttpPublisher.sendRequest(method, UC_HttpTag.LOGIN);
    }

    /**
     * 意见反馈
     *
     * @param map
     */
    public void feedback(Map<String, String> map) {

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_FEEDBACK, map);
        mHttpPublisher.sendRequest(method, UC_HttpTag.FEEDBACK);
    }


    /**
     * 搜索界面获取所有的车品牌和车系
     */
    public void getAllCar() {

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_SEARCH_CAR_URL, null);
        mHttpPublisher.sendRequest(method, UC_HttpTag.ALL_CAR_BRAND_TYPE);

    }

    /**
     * 搜索界面获取热销车系
     */
    public void getHotType(Map<String, String> map) {

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_HOT_CAR_URL, map);
        mHttpPublisher.sendRequest(method, UC_HttpTag.HOT_CAR_TYPE);

    }

    /**
     * 首页banner
     */
    public void getBanner(Map<String, String> map) {

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_BANNER_URL, map);
        mHttpPublisher.sendRequest(method, UC_HttpTag.HOME_PAGER_BANNER);

    }

    /**
     * 首页价格和品牌
     *
     * @param map
     */
    public void getHotBrandPrice(Map<String, String> map) {

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_BRAND_PRICE_URL, map);
        method.setSetCache(true);
        method.setCacheTimeLive(1 * 60 * 60 * 24);//设置缓存24个小时
        mHttpPublisher.sendRequest(method, UC_HttpTag.BRAND_AND_PRICE);

    }

    /**
     * 首页好车推荐
     *
     * @param map
     */
    public void recommentCar(Map<String, String> map, String str) {

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_RECOMMENT_CAR_URL, map);
        mHttpPublisher.sendRequest(method, UC_HttpTag.RECOMMNET_GOOD_CAR + str);

    }

    /**
     * 首页猜你喜欢
     *
     * @param map
     */
    public void guessLoveCar(Map<String, String> map) {

        if (UC_FrameEtongApplication.getApplication().isLogin()) {
            String f_userid = UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid();
            map.put("f_userid", f_userid);
        }

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_LOVE_CAR_URL, map);
        mHttpPublisher.sendRequest(method, UC_HttpTag.GUESS_LOVE_CAR);

    }


    /**
     * @desc 买车列表 查询车辆
     * @createtime 2016/10/19 - 11:16
     * @author xiaoxue
     */
    public void getCarType(String pageSize, String pageCurrent, String f_price, String f_pricemin,
                           String f_pricemax, String f_mileagemin, String f_mileagemax, String f_registercodemin, String f_registercodemax,
                           String f_carbrandid, String f_carsetid, String sortType, String f_gear_mode,
                           String f_vehicletype, String f_color, String f_country, String f_isauthenticate) {

        Map<String, String> map = new HashMap<>();
        map.put("f_org_id", "001");
        map.put("pageSize", pageSize + "");
        map.put("pageCurrent", pageCurrent + "");
        if (null != f_price) {
            map.put("f_price", f_price);
        }
        if (null != f_pricemin) {
            map.put("f_pricemin", f_pricemin);
        }
        if (null != f_pricemax) {
            map.put("f_pricemax", f_pricemax);
        }
        if (null != f_mileagemin) {
            map.put("f_mileagemin", f_mileagemin);
        }
        if (null != f_mileagemax) {
            map.put("f_mileagemax", f_mileagemax);
        }

        if (null != f_registercodemin) {
            map.put("f_registercodemin", f_registercodemin);
        }
        if (null != f_registercodemax) {
            map.put("f_registercodemax", f_registercodemax);
        }


        if (null != f_carbrandid) {
            map.put("f_carbrandid", f_carbrandid);
        }

        if (null != f_carsetid) {
            map.put("f_carsetid", f_carsetid);
        }
        if (null != sortType) {
            map.put("sortType", sortType);
        }
        if (null != f_gear_mode) {
            map.put("f_gear_mode", f_gear_mode);
        }
        if (null != f_vehicletype) {
            map.put("f_vehicletype", f_vehicletype);
        }
        if (null != f_color) {
            map.put("f_color", f_color);
        }
        if (null != f_country) {
            map.put("f_country", f_country);
        }
        if (null != f_isauthenticate) {
            map.put("f_isauthenticate", f_isauthenticate);
        }
        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_CAR_LIST_URL, map);
        mHttpPublisher.sendRequest(method, UC_HttpTag.CAR_LIST);

    }


    /**
     * @desc 买车列表 查询车辆
     * @createtime 2016/10/19 - 11:16
     * @author xiaoxue
     */
    public void getCarType(Map map) {


        map.put("f_org_id", "001");

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_CAR_LIST_URL, map);
        mHttpPublisher.sendRequest(method, UC_HttpTag.CAR_LIST);

    }

    /**
     * @desc 买车筛选条件
     * @createtime 2016/10/19 - 20:47
     * @author xiaoxue
     */
    public void filtrateconditions() {

        Map<String, String> map = new HashMap<>();
        map.put("f_org_id", "001");

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_FILTRATE_URL, map);
        method.setWay(HttpMethodWay.POST);
        method.setSetCache(true);
        method.setCacheTimeLive(1 * 60 * 60 * 24);//设置缓存24个小时
        mHttpPublisher.sendRequest(method, UC_HttpTag.FILTRATE);

    }

    /**
     * @desc 用户头像上传
     * @createtime 2016/10/20 0020-15:57
     * @author wukefan
     */
    public void uploadUserHead(String f_image) {

        String f_userid = UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid();

        Map<String, String> map = new HashMap<>();
        map.put("f_org_id", "001");
        map.put("f_userid", f_userid);
        map.put("f_image", f_image);

        HttpMethod method = new HttpMethod(UC_HttpUrl.UPLOAD_IMAGE, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.UPLOAD_HEAD_IMAGE);

    }


    /**
     * @desc 收藏车辆总和
     * @createtime 2016/10/20 0020-15:57
     * @author wukefan
     */
    public void collectListCount() {

        String f_userid = UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid();

        Map<String, String> map = new HashMap<>();
        map.put("f_org_id", "001");
        map.put("f_userid", f_userid);

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_COLLECT_LIST_COUNT, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.COLLECT_LIST_COUNT);

    }

    /**
     * @desc 收藏列表
     * @createtime 2016/10/20 0020-19:41
     * @author wukefan
     */
    public void collectList(int pageSize, int pageCurrent) {

        String f_userid = UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid();

        Map<String, String> map = new HashMap<>();
        map.put("f_org_id", "001");
        map.put("f_userid", f_userid);
        map.put("pageSize", pageSize + "");
        map.put("pageCurrent", pageCurrent + "");

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_COLLECT_LIST, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.COLLECT_LIST);

    }

    /**
     * @desc 点击收藏
     * @createtime 2016/10/20 0020-19:45
     * @author wukefan
     */
    public void insertColletData(String f_dvid) {

        String f_userid = UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid();

        Map<String, String> map = new HashMap<>();
        map.put("f_org_id", "001");
        map.put("f_userid", f_userid);
        map.put("f_dvid", f_dvid);

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_INSERT_COLLECT_DATA, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.INSERT_COLLECT_DATA);

    }

    /**
     * @desc 删除单条收藏记录
     * @createtime 2016/10/20 0020-19:48
     * @author wukefan
     */
    public void deleteOneColletData(String f_collectid, String position) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("position", position);
        map.put("f_org_id", "001");
        map.put("f_collectid", f_collectid);

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_DELETE_ONE_COLLECT_DATA, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.DELETE_ONE_COLLECT_DATA);

    }

    /**
     * @desc 清空收藏记录
     * @createtime 2016/10/20 0020-19:58
     * @author wukefan
     */
    public void deleteColletData() {

        String f_userid = UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid();

        Map<String, String> map = new HashMap<String, String>();
        map.put("f_org_id", "001");
        map.put("f_userid", f_userid);

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_DELETE_COLLECT_DATA, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.DELETE_COLLECT_DATA);

    }


    /**
     * @desc 浏览车辆总和
     * @createtime 2016/10/20 0020-15:57
     * @author wukefan
     */
    public void historyListCount() {

        Map<String, String> map = new HashMap<>();
        map.put("f_org_id", "001");

        if (UC_FrameEtongApplication.getApplication().isLogin()) {
            String f_userid = UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid();
            map.put("f_userid", f_userid);
        }
//            String f_machinecode = UC_FrameEtongApplication.getApplication().getUniqueId();
//            map.put("f_machinecode", f_machinecode);

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_HISTORY_LIST_COUNT, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.HISTORY_LIST_COUNT);
    }

    /**
     * @desc 浏览记录
     * @createtime 2016/10/20 0020-19:43
     * @author wukefan
     */
    public void historyList(int pageSize, int pageCurrent) {

        Map<String, String> map = new HashMap<>();
        map.put("f_org_id", "001");
        map.put("pageSize", pageSize + "");
        map.put("pageCurrent", pageCurrent + "");

        if (UC_FrameEtongApplication.getApplication().isLogin()) {
            String f_userid = UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid();
            map.put("f_userid", f_userid);
        } else {
            String uniqueId = UC_FrameEtongApplication.getApplication().getUniqueId();

            if (!TextUtils.isEmpty(uniqueId)) {
                String md5Key = MD5Utils.MD5(uniqueId);
                map.put("f_key", md5Key);
            }
        }

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_HISTORY_LIST, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.HISTORY_LIST);

    }

    /**
     * @desc 删除单条浏览记录
     * @createtime 2016/10/20 0020-19:48
     * @author wukefan
     */
    public void deleteOneHistoryData(String f_historyid, String position, String f_dvid) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("position", position);
        map.put("f_org_id", "001");
        map.put("f_historyid", f_historyid);
        map.put("f_dvid", f_dvid);

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_DELETE_ONE_HISTORY_DATA, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.DELETE_ONE_HISTORY_DATA);

    }

    /**
     * @desc 清空浏览记录
     * @createtime 2016/10/20 0020-19:58
     * @author wukefan
     */
    public void deleteHistoryData() {

        String uniqueId = UC_FrameEtongApplication.getApplication().getUniqueId();

        Map<String, String> map = new HashMap<>();
        map.put("f_org_id", "001");

        if (!TextUtils.isEmpty(uniqueId)) {
            String md5Key = MD5Utils.MD5(uniqueId);
            map.put("f_key", md5Key);
        }

        if (UC_FrameEtongApplication.getApplication().isLogin()) {
            String f_userid = UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid();
            map.put("f_userid", f_userid);
            map.put("f_machinecode", uniqueId);
        }

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_DELETE_HISTORY_DATA, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.DELETE_HISTORY_DATA);

    }

    /**
     * @desc 我的页面（整合所有的数据一起返回）
     * @createtime 2016/10/21 0021-11:50
     * @author wukefan
     */
    public void mineInfo() {

        Map<String, String> map = new HashMap<>();
        map.put("f_org_id", "001");

        if (UC_FrameEtongApplication.getApplication().isLogin()) {
            String f_userid = UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid();
            map.put("f_userid", f_userid);
        } else {
            String uniqueId = UC_FrameEtongApplication.getApplication().getUniqueId();

            if (!TextUtils.isEmpty(uniqueId)) {
                String md5Key = MD5Utils.MD5(uniqueId);
                map.put("f_key", md5Key);
            }
        }

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_MINE_INFO, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.MINE_INFO);

    }

    /**
     * @param
     * @return
     * @desc (品牌界面中热门品牌获取)
     * @user sunyao
     * @createtime 2016/10/25 - 16:19
     */
    public void getHotBrand(String f_org_id) {
        HttpMethod httpMethod = new HttpMethod(UC_HttpUrl.HTTP_GET_HOT_BRAND + "?f_org_id=" + f_org_id, new HashMap<>());
        httpMethod.setWay(HttpMethodWay.GET);
        httpMethod.setSetCache(true);
        httpMethod.setCacheTimeLive(1 * 60 * 60 * 24);      // 设置缓存时间为一天
        mHttpPublisher.sendRequest(httpMethod, UC_HttpTag.GET_HOT_BRAND);
    }

    /**
     * @param
     * @return
     * @desc (获取到所有的品牌)
     * @user sunyao
     * @createtime 2016/10/25 - 14:17
     */
    public void getAllBrand() {
        HttpMethod httpMethod = new HttpMethod(UC_HttpUrl.HTTP_GET_ALL_BRAND, new HashMap<>());
        httpMethod.setSetCache(true);
        httpMethod.setCacheTimeLive(1 * 60 * 60 * 24);      // 设置缓存时间为一天
        mHttpPublisher.sendRequest(httpMethod, UC_HttpTag.GET_ALL_BRAND);
    }

    /**
     * @param
     * @return
     * @desc (根据品牌id获取到车系列表)
     * @user sunyao
     * @createtime 2016/10/25 - 14:21
     */
    public void getCarset(int brandId) {
        Map<String, String> map = new HashMap<String, String>();
        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_GET_CARSET_BY_BRAND_ID + "?id=" + brandId, map);
        mHttpPublisher.sendRequest(method, UC_HttpTag.GET_CARSET_DETAIL_BY_GRAND_ID);
    }

    /**
     * @desc 车辆估值：车型列表
     * @createtime 2016/10/25 0025-15:48
     * @author wukefan
     */
    public void getCarType(String seriesId) {
        // 卖车页面中侧滑获取到车型的接口数据
        Map<String, String> map = new HashMap<String, String>();
        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_GET_CARTYPE_BY_CARSET_ID + "?id=" + seriesId, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.GET_CAR_MODEL_LIST);

    }


    /**
     * @desc 预约看车
     * @createtime 2016/10/25 0025-15:48
     * @author wukefan
     */
    public void advanceBuycar(String f_dvid, String f_brand, String f_ordertime) {

        Map<String, String> map = new HashMap<>();

        if (UC_FrameEtongApplication.getApplication().isLogin()) {
            String f_phone = UC_FrameEtongApplication.getApplication().getUserInfo().getF_phone();
            map.put("f_phone", f_phone);
        }

        map.put("f_org_id", "001");
        map.put("f_dvid", f_dvid);
        map.put("f_brand", f_brand);
        map.put("f_ordertime", f_ordertime);

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_ADVANCE_BUY_CAR, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.ADVANCE_BUY_CAR);

    }


    /**
     * @desc 预约卖车
     * @createtime 2016/10/25 0025-15:48
     * @author wukefan
     */
    public void advanceSellcar(String f_brand, String f_registerdate, String f_mileage) {

        Map<String, String> map = new HashMap<>();

        if (UC_FrameEtongApplication.getApplication().isLogin()) {
            String f_phone = UC_FrameEtongApplication.getApplication().getUserInfo().getF_phone();
            map.put("f_phone", f_phone);
        }

        map.put("f_org_id", "001");
        map.put("f_brand", f_brand);
        map.put("f_registerdate", f_registerdate);
        map.put("f_mileage", f_mileage);

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_ADVANCE_SELL_CAR, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.ADVANCE_SELL_CAR);

    }

    /**
     * @desc 车辆估值：城市列表
     * @createtime 2016/10/25 0025-15:48
     * @author wukefan
     */
    public void getCityList() {
        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_GET_CITY_LIST, null);
        method.setWay(HttpMethodWay.POST);
        method.setSetCache(true);
        method.setCacheTimeLive(7 * 60 * 60 * 24);      // 设置缓存时间为一周
        mHttpPublisher.sendRequest(method, UC_HttpTag.GET_CITY_LIST);

    }

    /**
     * @desc 车辆估值：精确估值
     * @createtime 2016/10/25 0025-15:48
     * @author wukefan
     */
    public void getUsedCarPrice(String modelId, String regDate, String mile, String zone, String color) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("modelId", modelId);
        map.put("regDate", regDate);
        map.put("mile", mile);
        map.put("zone", zone);
        map.put("color", color);

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_GET_USED_CAR_PRICE, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.GET_USED_CAR_PRICE);
    }

    /**
     * @desc 车辆鉴定查询vin码是否支持查询
     * @createtime 2016/10/31 0031-9:17
     * @author wukefan
     */
    public void checkVinIsSupport(String f_vin) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("f_vin", f_vin);        // 发送过来的vin码
        UC_User userInfo = UC_FrameEtongApplication.getApplication().getUserInfo();
        if (userInfo != null) {
            map.put("f_uid", userInfo.getF_userid());
        }
        map.put("f_dataresource", "1");     // 表示请求的是App端

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_CHECK_BRAND_IS_SUPPORT, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.CHECK_VIN_IS_SUPPORT);
    }


    /**
     * @desc (查询某个用户的所有车鉴定订单)
     * @createtime 2016/11/9 0009-19:26
     * @author wukefan
     * <p>
     * f_tabstatus  :   全部-11，进行中-12，已完成-13，退款-14
     */
    public void queryIdentified(int f_tabstatus, int pageSize, int pageCurrent, int isPullDown) {

        Map<String, String> map = new HashMap<String, String>();
        if (UC_FrameEtongApplication.getApplication().isLogin()) {
            String f_uid = UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid();
            map.put("f_uid", f_uid);
        }
//        map.put("f_uid", "489920629202485248");

        map.put("f_tabstatus", f_tabstatus + "");
        map.put("pageSize", pageSize + "");
        map.put("pageCurrent", pageCurrent + "");
        map.put("isPullDown", isPullDown + "");

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_QUERY_IDENTIFIED, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.QUERY_IDENTIFIED + f_tabstatus);
    }

    /**
     * @desc (查询车鉴定订单详情)
     * @createtime 2016/11/9 0009-19:26
     * @author wukefan
     */
    public void queryOneIdentified(String f_cid) {

        Map<String, String> map = new HashMap<String, String>();

        map.put("f_cid", f_cid);

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_QUERY_ONE_IDENTIFIED, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.QUERY_ONE_IDENTIFIED);
    }

    /**
     * @desc (重新下单购买报告)
     * @createtime 2016/11/10 0010-18:40
     * @author wukefan
     */
    public void reGetReport(String f_vin, String f_cid, String f_licensePlate, String f_engine) {
        Map<String, String> map = new HashMap<String, String>();
        if (UC_FrameEtongApplication.getApplication().isLogin()) {
            String f_uid = UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid();
            map.put("f_uid", f_uid);
        }

//        map.put("f_uid", "489920629202485248");

        map.put("f_vin", f_vin);
        map.put("f_cid", f_cid);

        if (null != f_licensePlate) {
            map.put("f_licensePlate", f_licensePlate);
        }
        if (null != f_engine) {
            map.put("f_engine", f_engine);
        }
        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_REGET_REPORT, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.REGET_REPORT);
    }


    /**
     * @desc (删除订单)
     * @createtime 2016/11/11 0011-11:28
     * @author wukefan
     */
    public void deleteOrder(String f_cid) {
        Map<String, String> map = new HashMap<String, String>();

        if (UC_FrameEtongApplication.getApplication().isLogin()) {
            String f_uid = UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid();
            map.put("f_uid", f_uid);
        }

//        map.put("f_uid", "489920629202485248");

        map.put("f_cid", f_cid);

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_DELETE_ORDER, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.DELETE_ORDER);
    }


    /**
     * @desc (退款)
     * @createtime 2016/11/11 0011-17:54
     * @author wukefan
     */
    public void refundAmt(String f_cid) {
        Map<String, String> map = new HashMap<String, String>();

        if (UC_FrameEtongApplication.getApplication().isLogin()) {
            String f_uid = UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid();
            map.put("f_uid", f_uid);
        }

//        map.put("f_uid", "489920629202485248");

        map.put("f_cid", f_cid);

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_REFUND_AMT, map);
        method.setWay(HttpMethodWay.POST);
        mHttpPublisher.sendRequest(method, UC_HttpTag.REFUND_AMT);
    }

/*
    ################################################################################################
    #                                   三个侧滑页面中获取的接口 （调用车300接口）                 #
    ################################################################################################
*/

    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title : getBrandAll
     * @Description :获取所有品牌
     * @params
     */
    public void getBrandAll() {
        Map<String, String> map = new HashMap<String, String>();

        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_GET_BRAND_300, map);
        method.setSetCache(true);
//        method.setCacheTimeLive(1 * 60 * 60 * 24);      // 设置缓存时间为一天
        mHttpPublisher.sendRequest(method, UC_HttpTag.HTTP_BRAND_BY_300);
    }


    /**
     * 获取车系列表
     */
    public void getVechileSeries(Integer id) {
        Map<String, String> map = new HashMap<String, String>();
        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_GET_SERIES_300 + id, map);
        method.setWay(HttpMethodWay.GET);
        mHttpPublisher.sendRequest(method, UC_HttpTag.HTTP_SERIES_BY_300);
    }

    /**
     * 获取车型列表页
     */
    public void getVechileType(Integer id) {
        Map<String, String> map = new HashMap<String, String>();
        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_GET_TYPE_300 + id, map);
        method.setWay(HttpMethodWay.GET);
        mHttpPublisher.sendRequest(method, UC_HttpTag.HTTP_TYPE_BY_300);
    }

    /**
     * @param
     * @return
     * @desc (获取到车辆详情)
     * @user sunyao
     * @createtime 2016/10/28 - 10:33
     */
    public void getCarDetail(Map<String, String> paraMap) {
        if (paraMap == null) {
            return;
        }
        String params = "?";

        // 遍历map中的元素，将不为空的参数添加到params中
        Iterator<String> iterator = paraMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();       // 调用几次next() 下标就会增加几次
            String value = paraMap.get(key);

            if (!TextUtils.isEmpty(value)) {
                params = params + key + "=" + value + "&";
            }
        }
        L.d("传送的参数列表为：", params);
        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_GET_CAR_DETAIL_DATA + params, null);
        mHttpPublisher.sendRequest(method, UC_HttpTag.CAR_DETAIL_DATA_TAG);
    }


/*
    ################################################################################################
    #                                          资讯接口                                            #
    ################################################################################################
*/

        /*
     * @Title
     * @Description  : 推荐资讯列表
     * @params
     *     @param
     * @return
     *     void    返回类型
     * @throws
     * */

    public void MessageType(String start, String limit, String recommend, int isJxapp) {
        Map map = new HashMap<>();

        map.put("isJxapp", isJxapp + "");


        if (null != recommend) {
            map.put("recommend", recommend);
        }
        if (null != start) {
            map.put("start", start);
        }
        if (null != limit) {
            map.put("limit", limit);
        }


        HttpMethod method = new HttpMethod(UC_HttpUrl.Message_List, map);
//        HttpMethod method = new HttpMethod(FrameHttpUri.Message_List+"?start="+start+"&limit="+limit+"&recommend="+recommend+"&isJxapp="+isJxapp, null);
//        HttpMethod method = new HttpMethod(FrameHttpUri.Message_List, map);
        mHttpPublisher.sendRequest(method, UC_HttpTag.MESSAGE_LIST);
    }

     /*
     * @Title
     * @Description  : 查询资讯列表接口
     * @params
     *     @param isJxapp   1津湘app调用,2 弘高app调用,3 其他app调用

     * @return
     *     void    返回类型
     * @throws
     * */

    public void MessageTypeList(String type, String start, String limit, String recommend, String key, int isJxapp,int isPullDown) {
        Map map = new HashMap<>();

        map.put("isJxapp", isJxapp + "");

        if (null != type) {
            map.put("type", type);
        }
        if (null != start) {
            map.put("start", start);
        }
        if (null != limit) {
            map.put("limit", limit);
        }
        if (null != recommend) {
            map.put("recommend", recommend);
        }
        if (null != key) {
            map.put("key", key);
        }
        map.put("isPullDown", isPullDown+"");
        HttpMethod method = new HttpMethod(UC_HttpUrl.Message_List, map);
//        HttpMethod method = new HttpMethod(FrameHttpUri.Message_List, map);
        mHttpPublisher.sendRequest(method, UC_HttpTag.MESSAGE_TYPE_LIST);
    }


    /**
     * @param
     * @return void    返回类型
     * @throws
     * @Title
     * @Description : 查询资讯详情接口
     * @params
     */
    public void MessageDetails(String id) {
        Map map = new HashMap<>();
//        map.put("id",id);

        HttpMethod method = new HttpMethod(UC_HttpUrl.Message_Details + id + ".do", map);
        mHttpPublisher.sendRequest(method, UC_HttpTag.MESSAGE_DETAILS);
    }

}
