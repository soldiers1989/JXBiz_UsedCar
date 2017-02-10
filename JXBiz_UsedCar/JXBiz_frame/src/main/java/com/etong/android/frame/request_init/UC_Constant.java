package com.etong.android.frame.request_init;

/**
 * Created by Dasheng on 2016/10/12.
 */

public class UC_Constant {


    public static final int LOCAL = 0;          // 内网测试，开发环境
    public static final int STAG = 1;           // 外网测试环境，开发环境
    public static final int PROD = 2;           // 正式环境
    public static final int DEMO = 3;           // 测试组环境
    public static String CURRENT_SERVICE = "";   // 当前服务地址
    public static String CURRENT_SERVICE_MESS = "";   // 当前服务地址

    /**
     * stag环境
     */
    private static final String STAG_SERVICE = "http://test.hg-rzzl.com.cn/etong2sc-app-consumer";
    /**
     * 产品环境
     */
    private static final String PROD_SERVICE = "";
    /**
     * demo环境
     */
//    private static final String DEMO_SERVICE = "http://192.168.30.15:8999/etong2sc-app-consumer";        // 潘雪娟本机地址
    private static final String DEMO_SERVICE = "http://192.168.30.8:8989/etong2sc-app-consumer";      // 罗永红本机地址

    /**
     * 本地环境
     */
    private static final String LOCAL_SERVICE = "http://192.168.10.167:8090/etong2sc-app-consumer";

    /**
     * stag环境
     */
    private static final String STAG_SERVICE_MESS = "http://120.25.98.100:8080/jxapi/";
    /**
     * 产品环境
     */
    private static final String PROD_SERVICE_MESS = "";
    /**
     * demo环境
     */
    private static final String DEMO_SERVICE_MESS = "";
    /**
     * 本地环境
     */
    private static final String LOCAL_SERVICE_MESS = "http://192.168.10.169:8090/jxapi/";

    /**
     * @param @param flag 设定文件
     * @return void 返回类型
     * @throws
     * @Title: initService
     * @Description: TODO(设置服务器)
     */
    public static void initService(int flag) {
        if (flag == LOCAL) {
            // 本地
            CURRENT_SERVICE = LOCAL_SERVICE;
            CURRENT_SERVICE_MESS = LOCAL_SERVICE_MESS;
        } else if (flag == STAG) {
            // stag
            CURRENT_SERVICE = STAG_SERVICE;
            CURRENT_SERVICE_MESS = STAG_SERVICE_MESS;
        } else if (flag == PROD) {
            // 产品
            CURRENT_SERVICE = PROD_SERVICE;
            CURRENT_SERVICE_MESS = PROD_SERVICE_MESS;
        } else if (flag == DEMO) {
            CURRENT_SERVICE = DEMO_SERVICE;
            CURRENT_SERVICE = DEMO_SERVICE;
        } else {
            CURRENT_SERVICE = LOCAL_SERVICE;
            CURRENT_SERVICE_MESS = LOCAL_SERVICE_MESS;
        }
    }
}
