package com.etong.android.frame.request_init;

/**
 * Created by Ellison.Sun on 2016/8/21.
 */
public class FrameHttpTag {

/*
 ################################################################################################
 ##                                   新车购买的接口                                            ##
 ################################################################################################
*/

    public static final String GET_APP_UPDATE_INFO = "get_app_update_info";                 // 获取到App的信息
    public static final String GETCARSETBYNAME = "get_carset_by_name";
    public static final String GET_CARSET_BY_GRAND_NAME = "get_carset_by_grand_name";
    public static final String GET_CARSET_BY_GRAND_ID = "get_carset_by_grand_id";
    public static final String GET_CARSET_DETAIL_BY_GRAND_ID = "get_carset_detail_by_grand_id";
    public static final String GET_CARSET_DETAIL = "get_carset_detail";
    public static final String GET_CARSET_PIC_NUM = "get_carset_pic_num";                   // 通过车系id获取到车系图片
    public static final String TRANSFER_CAR_PRICE_INFO = "transfer_car_price_info";        // 传送车辆名字和车辆价格的EventBus tag
    public static final String GET_COMPARE_INFO = "get_compare_info";                        // 通过车型ID进行对比获取到信息
    public static final String BY_NAME_SELECT_CAR_SERIES = "by name select car series";//通过车系名称查询车系
    public static final String BRAND_ALL = "brand all";//获取所有品牌
    public static final String VECHILE_SERIES = "vechile series";//获取品牌车系列表
    public static final String VECHILE_TYPE = "vechile type";//获取车型列表页

    public static final String GET_VECHILE_TYPE_DETAIL = "get vechile type detail";//车型详情页 参数配置
    public static final String GET_HOT_NAME = "get hot name";//热点  ——》通过名字获取车系


    public static final String PUT_ID_TO_ORDER = "put id to order";                          //提交预约
    public static final String QUERY_MAINTENANCE_ORDER_LIST = "query maintenance order list";//查询维保预约列表
    public static final String QUERY_MAINTENANCE_ORDER_DETAIL = "query maintenance order detail";//查询维保预约详情
    public static final String QUERY_ALL_CAR_LEVEL = "query all car level";//查询所有车等级
    public static final String QUERY_CARSET_BY_CONDITION = "query carset by condition";//筛选
    public static final String QUERY_CARSET_PHOTO = "query carset photo";//通过车系id查询车系图片
    public static final String QUERY_CAR_PHOTO = "query car photo";//通过车型id查询车系图片
    public static final String GET_4S_STRUCTURE = "get 4s structure";//获取4S店的组织架构
    public static final String COUT_SEARCH_CARSET_COUNT = "cout search carset count";//统计车系筛选结果总数
    public static final String QUERY_MY_CAR = "query my car";//查询爱车
    public static final String ADD_MY_CAR = "add my car";//添加爱车

/*
 ################################################################################################
 ##                   四个RadioButton的接口(发现、资讯、更多、我的)                             ##
 ################################################################################################
*/

    public static final String MESSAGE_LIST = "message list";//查询资讯列表
    public static final String MESSAGE_TYPE_LIST = "message type list";//查询资讯列表(8块)
    public static final String MESSAGE_SEARCH_LIST = "message search list";//查询资讯列表  搜索
    public static final String MESSAGE_DETAILS = "message details";//查询资讯详情
    public static final String FIND_RECOMMEND = "find recommend";//查询推荐商品接口(猜你喜欢)
    public static final String FIND_ACTIVITY_ITEM_LIST = "find activity item list";//查询活动商品列表(促销车  限时购)
    public static final String OIL_PRICE = "oil price";//今日油价
    public static final String OIL_LOCAL = "oil local";//附近加油站
    public static final String VIOLATION_QUERY = "violation query";//违章查询


    public static final String WEATHER_SUGGESTION = "weather suggestion";//天气预报的逐日预报
    public static final String WEATHER_DAILY = "weather daily";//天气预报的生活指数
    public static final String UPDATE_USER_INFO = "update user info";//更新用户信息
    public static final String QUERY_USER_ID = "query user id";//获取用户信息


/*
 ################################################################################################
 ##                                          汽车金融的接口                                     ##
 ################################################################################################
*/

    public static final String ADD_CAR_ORDER_FOR_APP = "add car order for app";//车贷申请 融资租赁申请 畅通钱包 车辆撤押申请
    public static final String ADD_TOTAL_ORDER_FOR_APP = "add total order for app";//申请进度
    public static final String QUERY_CAR_PAY_ORDER = "query car pay order";//申请进度详情
    public static final String BIND_FINANCIAL_AGENT = "bind financial agent";//绑定金融账号

    public static final String QUERY_THE_FINANCIAL = "query the financial";//查询金融机构
    public static final String LOANLIST = "loan list";//贷款列表查询
    public static final String REPAYMENTLIST = "repayment list";//还款列表查询
    public static final String OVERDUELIST = "overdue list";//逾期列表查询
    public static final String QUERY_MY_ORDER = "query my order";//订单记录


    /*
     ################################################################################################
     ##                                          二手车中的接口                                     ##
     ################################################################################################
    */
    public static final String USED_CAR_CARDETAIL_INFO = "jx used car cardetail";    // 津湘汽车二手车中的车辆详情页面

    public static final String ORDERCAR = "order car";//预约看车
    public static final String CARLIST = "car list";//二手车列表
    public static final String ORDER_SELL_CAR = "order sell car";//预约卖车
    public static final String QUERY_FILTER_DIC_DATA = "query filter dic data";//筛选数据字典

    public static final String CLICKCOLLECTION = "click collection";//点击收藏
    public static final String COLLECTIONLIST = "collection list";//收藏列表
    public static final String CONFIGINFO = "get config info";      // 车辆参数配置界面信息
    public static final String GET_COLLECT_TOTAL = "get collect total";//收藏总数

    public static final String CLICKCOLLECTION_NEW = "click collection new";//新车点击收藏


}
