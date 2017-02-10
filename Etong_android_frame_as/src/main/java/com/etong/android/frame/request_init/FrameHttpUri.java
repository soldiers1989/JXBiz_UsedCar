package com.etong.android.frame.request_init;

/**
 * Created by Ellison.Sun on 2016/8/16.
 */
public class FrameHttpUri {


/*
 ################################################################################################
 ##                                   新车购买的接口                                            ##
 ################################################################################################
*/
    /**
     * 0、获取到App的更新信息
     */
//    public final static String GetAppUpdateInfo = "https://www.pgyer.com/apiv1/app/getAppKeyByShortcut";
    /**
     * 1、获取热门品牌接口
     */
    public final static String GetHotBrand = FrameConstant.CURRENT_SERVICE + "brand/hot.do";
    /**
     * 2、获取所有品牌
     */
    public final static String GetAllBrand = FrameConstant.CURRENT_SERVICE + "brand/all.do";
    /**
     * 3、获取品牌车系列表，需要在后面传入品牌ID.do
     */
    public final static String GetBrandCarLine = FrameConstant.CURRENT_SERVICE + "carset/list/";
    /**
     * 4、根据车辆品牌获取到车系，后面需要加品牌的id.do
     */
    public final static String GetCarsetByGrandID = FrameConstant.CURRENT_SERVICE + "carset/list/";
    /**
     * 5、通过车系ID查询车系图片
     */
    public final static String GetCarsetPicNum = FrameConstant.CURRENT_SERVICE + "carset/photo/";
    /**
     * 6、通过车型ID进行对比接口, 后面需要加所有的车型id 621298,621296,639793.do
     */
    public final static String GetCompareInfo = FrameConstant.CURRENT_SERVICE + "car/compare/";

    /**
     * 12、根据车系名称获取到车系， 后面需要加 车系名.do
     */
    public final static String GetCarsetByKey = FrameConstant.CURRENT_SERVICE + "car/queryCarsetByKey/";


    /**
     * 0、通过车系id查询车系图片  后面需加  id.do
     */
    public final static String QueryCarsetPhoto = FrameConstant.CURRENT_SERVICE + "carset/photo/";

    /**
     * 1、通过车型id查询车系图片  后面需加  id.do
     */
    public final static String QueryCarPhoto = FrameConstant.CURRENT_SERVICE + "car/photo/";
    //获取车型列表页 需要在后面传入车系ID.do
    public final static String GetVechileType = FrameConstant.CURRENT_SERVICE + "carset/";


    /**
     * 0、提交预约接口
     */
    public final static String PutOrder = FrameConstant.CURRENT_SERVICE + "book/addBook.do";

    /**
     * 1、查询维保预约列表接口  后面需要加 手机号.do
     */
    public final static String QueryMaintenanceOrderList = FrameConstant.CURRENT_SERVICE + "book/queryMaintenanceBookList/";

    /**
     * 2、查询维保预约详情接口  后面需要加 手机号.do
     */
    public final static String QueryMaintenanceOrderDetail = FrameConstant.CURRENT_SERVICE + "book/queryMaintenanceBookDetail/";

    /**
     * 3、获取车型详情页参数配置 接口
     */
    public final static String GetVechileTypeDetail = FrameConstant.CURRENT_SERVICE + "car/detail/";

    /**
     * 4、查询所有车等级接口
     */
    public final static String QueryAllCarLevel = FrameConstant.CURRENT_SERVICE + "carset/queryAllCarLevel.do";

    /**
     * 5、筛选接口
     */
    public final static String QueryCarsetByCondition = FrameConstant.CURRENT_SERVICE + "carset/queryCarsetbyCondition.do";

    /**
     * 6、获取4S店的组织架构接口
     */
    //    public final static String Get4sStructure = FrameConstant.CURRENT_SERVICE + "carDealer/sum/get4sStructure.do";
    public final static String Get4sStructure = FrameConstant.CURRENT_SERVICE + "superApp/selectdeptcat/";

    /**
     * 7、统计车系筛选结果总数接口
     */
    public final static String CoutSearchCarsetCount = FrameConstant.CURRENT_SERVICE + "carset/coutSearchCarsetCount.do";
    /**
     * 8、查询爱车接口
     */
    public final static String QueryMyCar = FrameConstant.CURRENT_SERVICE + "mycar/queryMyCar.do";

    /**
     * 9、添加爱车接口
     */
    public final static String AddMyCar = FrameConstant.CURRENT_SERVICE + "mycar/addMyCar.do";
//    public final static String AddMyCar = "http://192.168.30.12:8080/jxapi/" + "mycar/addMyCar.do";



/*
 ################################################################################################
 ##                   四个RadioButton的接口(发现、资讯、更多、我的)                             ##
 ################################################################################################
*/

    /**
     * 0、查询资讯列表接口
     */
    public final static String Message_List = FrameConstant.CURRENT_SERVICE + "information/queryInfomationListNext.do";

    /**
     * 1、查询资讯详情接口
     */
    public final static String Message_Details = FrameConstant.CURRENT_SERVICE + "information/detail/";

    /**
     * 2、猜你喜欢列表接口
     */
    public final static String Find_Recommend = FrameConstant.CURRENT_SERVICE + "item/queryRecommend.do";

    /**
     * 3、查询活动商品列表接口   （促销车  限时购）
     */
    public final static String Find_ActivityItemList = FrameConstant.CURRENT_SERVICE + "activity/activityItemList.do";

    /**
     * 4、今日油价
     */
    public final static String OIL_PRICE = "http://apis.baidu.com/showapi_open_bus/oil_price/find";
    /**
     * 5、检索周边加油站
     */
    public final static String OIL_LOCAL = "http://apis.juhe.cn/oil/local";

    /**
     * 6.违章查询
     */
    public final static String VIOLATION_QUERY = FrameConstant.CURRENT_SERVICE + "carOrder/queryCarIllegal.do";


    /**
     * 0、天气预报的逐日预报接口
     */
    public final static String WeatherSuggestion = "http://apis.baidu.com/thinkpage/weather_api/suggestion";

    /**
     * 1、天气预报的生活指数接口
     */
    public final static String WeatherDaily = "http://apis.baidu.com/thinkpage/weather_api/daily";
    /**
     * 3、更新用户信息接口
     */
    public final static String UpdateUserInfo = FrameConstant.CURRENT_SERVICE +"user/updateInfo.do";
    /**
     * 4、获取用户信息接口
     */
    public final static String QueryUserId = FrameConstant.CURRENT_SERVICE +"user/queryUserId.do";


/*
 ################################################################################################
 ##                                          汽车金融的接口                                     ##
 ################################################################################################
*/

    /**
     * 0、车贷申请 融资租赁申请 畅通钱包申请接口
     */
    public final static String AddCarPayOrderForApp = FrameConstant.CURRENT_SERVICE + "carPayments/addCarPayOrderForApp.do";
    /**
     * 1、车辆撤销申请接口
     */
    public final static String AddCarDrawOrderForApp = FrameConstant.CURRENT_SERVICE + "carPayments/addCarDrawOrderForApp.do";
    /**
     * 2、申请进度接口
     */
    public final static String AddTotalOrderForApp = FrameConstant.CURRENT_SERVICE + "carTotal/addTotalOrderForApp.do";
    /**
     * 3、申请进度详情接口
     */
    public final static String QueryCarPayOrder = FrameConstant.CURRENT_SERVICE + "carTotal/queryCarPayOrder.do";
    /**
     * 4、绑定金融账号接口
     */
    public final static String BindFinancialAgent = FrameConstant.CURRENT_SERVICE + "financial/bindfinancialAgent.do";
    /**
     * 1 查询金融机构接口
     */
    public final static String QueryTheFinancial = FrameConstant.CURRENT_SERVICE + "financial/addfinancialAgent.do";
    /**
     * 贷款列表查询接口
     */
    public final static String LOANLIST = FrameConstant.CURRENT_SERVICE + "user/hg/loan/detail.do";
    /**
     * 还款列表查询接口
     */
    public final static String REPAYMENTLIST = FrameConstant.CURRENT_SERVICE + "user/hg/repay/getall.do";

    /**
     * 逾期列表查询接口
     */
    public final static String OVERDUELIST = FrameConstant.CURRENT_SERVICE + "user/hg/overdue/getall.do";
    /**
     * 订单记录接口
     */
    public final static String QueryMyOrder = FrameConstant.CURRENT_SERVICE + "carTotal/queryMyOrder.do";
/*
 ################################################################################################
 ##                                        二手车的接口                                     ##
 ################################################################################################
*/

    /**
     * 5.预约看车接口
     */
    public final static String ORDERCAR = FrameConstant.CURRENT_SERVICE + "appCar/addBuyReserveForApp.do";
    /** 3、车辆详情接口*/
    public static final String JXUC_CarDetail = FrameConstant.CURRENT_SERVICE + "appCar/queryCarDetail.do";
    /**
     * 二手车列表
     */
    public final static String CARLIST = FrameConstant.CURRENT_SERVICE + "appCar/queryCarlist.do";

    /**
     * 1.预约卖车接口
     */
    public final static String OrderSellCar = FrameConstant.CURRENT_SERVICE + "appCar/addSellReserveForApp.do";
    /**
     * 2.筛选数据字典接口
     */
    public final static String QueryFilterDicData = FrameConstant.CURRENT_SERVICE + "appCar/queryDicData.do";

    /**
     * 4.点击收藏
     */
    public final static String CLICKCOLLECTION = FrameConstant.CURRENT_SERVICE + "appCar/insertCollectData.do";

    /**
     * 10.新车收藏
     */
    public final static String CLICKCOLLECTION_NEW = FrameConstant.CURRENT_SERVICE + "appCar/insertCollectForNew.do";

    /**
     * 8.收藏列表
     */
    public final static String COLLECTIONLIST = FrameConstant.CURRENT_SERVICE + "appCar/getcollectList.do";

    /** 7.查询标准参数配置 */
    public final static String CARCONFIGINFO = FrameConstant.CURRENT_SERVICE + "appCar/getCarConfigDetail.do";
    /**
    * 9.收藏总数
     */
    public final static String GetCollectTotal = FrameConstant.CURRENT_SERVICE + "appCar/getcollectTotal.do";
}

