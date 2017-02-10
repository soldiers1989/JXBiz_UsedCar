package com.etong.android.frame.request_init;

/**
 * Created by Ellison.Sun on 2016/8/16.
 */
public class FrameConstant {

    public static final int LOCAL = 0;          // 内网测试，开发环境
    public static final int STAG = 1;           // 外网测试环境，开发环境
    public static final int PROD = 2;           // 正式环境
    public static final int DEMO = 3;           // 测试组环境
    public static String CURRENT_SERVICE = "";   // 当前服务地址

    /**stag环境*/
    private static final String STAG_SERVICE = "http://120.25.98.100:8080/jxapi/";
    /**产品环境*/
    private static final String PROD_SERVICE = "";
    /**demo环境*/
//    private static final String DEMO_SERVICE = "";
    private static final String DEMO_SERVICE = "192.168.30.15:8080/web-jx-admin/";     // 潘雪娟本机地址
    /**本地环境*/
    private static final String LOCAL_SERVICE = "http://192.168.10.169:8090/jxapi/";

    /**
     *
     * @Title: initService
     * @Description: TODO(设置服务器)
     * @param @param flag 设定文件
     * @return void 返回类型
     * @throws
     */
    public static void initService(int flag) {
        if (flag == LOCAL) {
            // 本地
            CURRENT_SERVICE = LOCAL_SERVICE;
        } else if (flag == STAG) {
            // stag
            CURRENT_SERVICE = STAG_SERVICE;
        }else if (flag == PROD) {
            // 产品
            CURRENT_SERVICE = PROD_SERVICE;
        }else if (flag == DEMO) {
            CURRENT_SERVICE = DEMO_SERVICE;
        } else {
            CURRENT_SERVICE = LOCAL_SERVICE;
        }
    }
}
