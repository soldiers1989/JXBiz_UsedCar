package com.etong.android.frame.request_init;

/**
 * Created by Dasheng on 2016/10/12.
 */

public class UC_HttpUrl {

/*
    ################################################################################################
    ##                                    首页中的接口                                            ##
    ################################################################################################
*/

    public static final String HTTP_RECOMMENT_CAR_URL = UC_Constant.CURRENT_SERVICE + "/index/goodcar";  //好车推荐
    public static final String HTTP_LOVE_CAR_URL = UC_Constant.CURRENT_SERVICE + "/index/like";       //猜你喜欢
    public final static String HTTP_BANNER_URL = UC_Constant.CURRENT_SERVICE + "/index/bannerlist";   //获取首页banner
    public final static String HTTP_HOT_CAR_URL = UC_Constant.CURRENT_SERVICE + "/index/hotCarset";   //获取热销车系
    public static final String HTTP_BRAND_PRICE_URL = UC_Constant.CURRENT_SERVICE + "/index/hotBrand";//获取首页品牌和价格
    public final static String HTTP_SEARCH_CAR_URL = UC_Constant.CURRENT_SERVICE + "/search/getAllfirst";  //获取所有品牌和车系

/*
    ################################################################################################
    ##                                    卖车中的接口                                            ##
    ################################################################################################
*/

    public static final String HTTP_ADVANCE_SELL_CAR = UC_Constant.CURRENT_SERVICE + "/advanceSellcar";  //预约卖车

    /*
        ################################################################################################
        ##                                    买车中的接口                                            ##
        ################################################################################################
    */
    public static final String HTTP_CAR_LIST_URL = UC_Constant.CURRENT_SERVICE + "/getCarListData";  //买车列表（查询车辆）

    public static final String HTTP_FILTRATE_URL = UC_Constant.CURRENT_SERVICE + "/getFilterDataDetail";  //买车筛选条件
    public static final String HTTP_GET_HOT_BRAND = UC_Constant.CURRENT_SERVICE + "/search/getSearchBrand";      // 买车、卖车页面中点击品牌到品牌界面中的热门品牌
    public static final String HTTP_GET_ALL_BRAND = UC_Constant.CURRENT_SERVICE + "/search/getBrands";          // 买车、卖车页面中点击品牌跳转到选择品牌界面
    public static final String HTTP_GET_CARSET_BY_BRAND_ID = UC_Constant.CURRENT_SERVICE + "/search/getCarset"; // 买车、卖车页面中根据品牌id获取车系列表接口
    public static final String HTTP_GET_CARTYPE_BY_CARSET_ID = UC_Constant.CURRENT_SERVICE + "/search/getCar";  // 买车、卖车页面中根据车系id获取车型列表接口
    public static final String HTTP_ADVANCE_BUY_CAR = UC_Constant.CURRENT_SERVICE + "/advanceBuycar";  //预约看车


    /*
        ################################################################################################
        ##                                    我的中的接口                                            ##
        ################################################################################################
    */
    public final static String HTTP_GET_VERIFICASTION_CODE = UC_Constant.CURRENT_SERVICE + "/login/sendCode"; //获取验证码
    public final static String HTTP_LOGIN = UC_Constant.CURRENT_SERVICE + "/login/login";//登录
    public final static String HTTP_FEEDBACK = UC_Constant.CURRENT_SERVICE + "/mine/insertFeedback";  //意见反馈
    public static final String UPLOAD_IMAGE = UC_Constant.CURRENT_SERVICE + "/updateUserData";                // 用户上传头像
    public static final String UPLOAD_IMAGE_SERVICER = "http://222.247.51.114:10002/upload";                    // 用户上传图片到服务器
    public static final String HTTP_COLLECT_LIST_COUNT = UC_Constant.CURRENT_SERVICE + "/mine/collectListCount";  //收藏车辆总和  不用了
    public static final String HTTP_HISTORY_LIST_COUNT = UC_Constant.CURRENT_SERVICE + "/mine/historyListCount";  //浏览车辆总和  不用了
    public static final String HTTP_COLLECT_LIST = UC_Constant.CURRENT_SERVICE + "/mine/collectList";  //收藏列表
    public static final String HTTP_HISTORY_LIST = UC_Constant.CURRENT_SERVICE + "/mine/historyList";  //查询浏览记录
    public static final String HTTP_INSERT_COLLECT_DATA = UC_Constant.CURRENT_SERVICE + "/insertColletData";//点击收藏
    public static final String HTTP_DELETE_ONE_COLLECT_DATA = UC_Constant.CURRENT_SERVICE + "/deleteOneColletData";//单条删除收藏记录
    public static final String HTTP_DELETE_ONE_HISTORY_DATA = UC_Constant.CURRENT_SERVICE + "/deleteOneHistoryData";//单条删除浏览记录
    public static final String HTTP_DELETE_COLLECT_DATA = UC_Constant.CURRENT_SERVICE + "/deleteColletData";//清空所有收藏记录
    public static final String HTTP_DELETE_HISTORY_DATA = UC_Constant.CURRENT_SERVICE + "/deleteHistoryData";//清空所有浏览记录
    public static final String HTTP_MINE_INFO = UC_Constant.CURRENT_SERVICE + "/mine/mine";  //我的信息
    /**
     * 三个选择品牌、车系、车型的接口
     */
    public final static String HTTP_GET_BRAND_300 = UC_Constant.CURRENT_SERVICE + "/getBrandListData";              //车辆估值：品牌列表接口
    public final static String HTTP_GET_SERIES_300 = UC_Constant.CURRENT_SERVICE + "/getCarSeriesList?brandId=";    //车辆估值：车系列表接口
    public final static String HTTP_GET_TYPE_300 = UC_Constant.CURRENT_SERVICE + "/getCarModelList?seriesId=";      //车辆估值：车型列表接口


    public static final String HTTP_GET_CITY_LIST = UC_Constant.CURRENT_SERVICE + "/getCityList";             //车辆估值：城市列表接口
    public static final String HTTP_GET_USED_CAR_PRICE = UC_Constant.CURRENT_SERVICE + "/getUsedCarPrice";  //车辆估值：精确估值接口


    public static final String HTTP_CHECK_BRAND_IS_SUPPORT = UC_Constant.CURRENT_SERVICE + "/caridentifi/checkBrandIsSupport";  //查询vin码是否支持查询接口
    public static final String HTTP_QUERY_IDENTIFIED = UC_Constant.CURRENT_SERVICE + "/mine/queryIdentified";  //查询某个用户的所有车鉴定订单接口
    public static final String HTTP_QUERY_ONE_IDENTIFIED = UC_Constant.CURRENT_SERVICE + "/mine/queryoneIdentified";  //查询车鉴定订单详情
    public static final String HTTP_REGET_REPORT = UC_Constant.CURRENT_SERVICE + "/caridentifi/getReport";  //重新下单购买报告接口
    public static final String HTTP_DELETE_ORDER = UC_Constant.CURRENT_SERVICE + "/mine/deleteOrder";  //删除订单接口
    public static final String HTTP_REFUND_AMT = UC_Constant.CURRENT_SERVICE + "/order/refundAmt";  //退款接口

/*
    ################################################################################################
    ##                                    弘高车世界中第二版用车助手                              ##
    ################################################################################################
*/
    /**
     * 4、今日油价
     */
    public final static String OIL_PRICE = "http://apis.baidu.com/showapi_open_bus/oil_price/find";
    /**
     * 5、检索周边加油站
     */
    public final static String OIL_LOCAL = "http://apis.juhe.cn/oil/local";

    /*
        ################################################################################################
        ##                                    其他的接口                                              ##
        ################################################################################################
    */
    public static final String HTTP_GET_CAR_DETAIL_DATA = UC_Constant.CURRENT_SERVICE + "/getCarDetailData";     // 获取到车辆详情接口

    /**
     * 2、查询资讯列表接口
     */
//    public final static String Message_List = UC_Constant.CURRENT_SERVICE_MESS + "information/queryInfomationList.do";
    public final static String Message_List = UC_Constant.CURRENT_SERVICE_MESS + "information/queryInfomationListNext.do";

    /***
     * 3、查询资讯详情接口
     */
    public final static String Message_Details = UC_Constant.CURRENT_SERVICE_MESS + "information/detail/";

}
