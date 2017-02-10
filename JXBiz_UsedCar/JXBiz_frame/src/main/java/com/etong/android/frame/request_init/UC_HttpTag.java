package com.etong.android.frame.request_init;

/**
 * Created by Ellison.Sun on 2016/8/21.
 */
public class UC_HttpTag {


/*
    ################################################################################################
    ##                                    首页中的接口                                            ##
    ################################################################################################
*/

    public static final String BRAND_AND_PRICE = "brand and price";//首页价格和品牌
    public static final String RECOMMNET_GOOD_CAR = "recommnet good car";//好车推荐
    public static final String GUESS_LOVE_CAR = "guess love car";//猜你喜欢
    public static final String HOME_PAGER_BANNER = "home pager banner";//首页banner
    public static final String HOT_CAR_TYPE = "hot car type";//热销车系
    public static final String ALL_CAR_BRAND_TYPE = "all brand type";//获取所有的车品牌和车系

/*
    ################################################################################################
    ##                                    卖车中的接口                                            ##
    ################################################################################################
*/

    public static final String ADVANCE_SELL_CAR = "advance sell car";//预约卖车
    public static final String GET_BRAND_LIST_DATA = "get brand list data";//卖车：品牌列表
    public static final String GET_CAR_SERIES_LIST = "get car series list";//卖车：车系列表
    public static final String GET_CAR_MODEL_LIST = "get car model list";//卖车：车型列表

/*
    ################################################################################################
    ##                                    买车中的接口                                            ##
    ################################################################################################
*/
    public static final String CAR_LIST = "car list";//买车列表

    public static final String FILTRATE = "car filtrate";//买车筛选条件
    public static final String ADVANCE_BUY_CAR = "advance buy car";//预约看车

	/** 选择品牌界面中的接口或者EventBus传值*/
    public static final String GET_HOT_BRAND = "get hot brand";         // 获取到热门品牌
    public static final String GET_ALL_BRAND = "select all brand";     // 获取所有品牌
    public static final String POST_BRAND_TO_MENU = "post brand id to menu"; // 将获取到的品牌id传送给侧滑栏
    public static final String GET_CARSET_DETAIL_BY_GRAND_ID = "get carset detail by brand id";     // 根据品牌id获取到所有车系


    /*
    ################################################################################################
    ##                                    我的中的接口                                            ##
    ################################################################################################
*/
    public static final String GET_VER_CODE = "get verification code";//获取验证码
    public static final String LOGIN = "login";//登录
    public static final String FEEDBACK = "feedback";//反馈
    public static final String UPLOAD_HEAD_IMAGE = "upload head image";//上传头像
    public static final String COLLECT_LIST_COUNT = "collect list count";//收藏车辆总和
    public static final String HISTORY_LIST_COUNT = "history list count";//浏览车辆总和
    public static final String COLLECT_LIST = "collect list";//收藏列表
    public static final String HISTORY_LIST = "history list";//查询浏览记录
    public static final String INSERT_COLLECT_DATA = "insert collet data";//点击收藏
    public static final String DELETE_ONE_COLLECT_DATA = "delete one collet data";//单条删除收藏记录
    public static final String DELETE_ONE_HISTORY_DATA = "delete one history data";//单条删除浏览记录
    public static final String DELETE_COLLECT_DATA = "delete collet data";//清空所有收藏记录
    public static final String DELETE_HISTORY_DATA = "delete history data";//清空所有浏览记录
    public static final String MINE_INFO = "mine info";//我的信息
    public static final String GET_CITY_LIST = "get city list";//车辆估值：城市列表
    public static final String GET_USED_CAR_PRICE = "get used car price";//车辆估值：精确估值
    public static final String HTTP_BRAND_BY_300 = "http brand by 300";         // 获取品牌的Tag值
    public static final String HTTP_SERIES_BY_300 = "http series by 300";       // 获取车系的Tag值
    public static final String HTTP_TYPE_BY_300 = "http type by 300";           // 获取车型的Tag值
    public static final String CHECK_VIN_IS_SUPPORT = "check brand is support";           // 查询vin码是否支持查询
    public static final String QUERY_IDENTIFIED = "query identified";           // 查询某个用户的所有车鉴定订单
    public static final String QUERY_ONE_IDENTIFIED = "query one identified";           // 查询车鉴定订单详情
    public static final String REGET_REPORT = "reget report";           // 重新下单购买报告
    public static final String DELETE_ORDER = "delete order";           // 删除订单
    public static final String REFUND_AMT = "refund amt";           // 退款

/*
    ################################################################################################
    ##                            弘高车世界中第二版用车助手                                      ##
    ################################################################################################
*/
    public static final String OIL_PRICE = "oil price";//今日油价
    public static final String OIL_LOCAL = "oil local";//附近加油站

/*
    ################################################################################################
    ##                                    其他的接口                                              ##
    ################################################################################################
*/

    public static final String CAR_DETAIL_DATA_TAG = "get car detail data";         // 获取到车辆详情的tag

    public static final String MESSAGE_LIST = "message list";//查询资讯列表
    public static final String MESSAGE_TYPE_LIST = "message type list";//查询资讯列表(8块)
    public static final String MESSAGE_DETAILS = "message details";//查询资讯详情


}
