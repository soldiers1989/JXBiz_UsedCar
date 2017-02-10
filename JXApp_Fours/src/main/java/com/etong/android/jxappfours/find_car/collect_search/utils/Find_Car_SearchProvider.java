package com.etong.android.jxappfours.find_car.collect_search.utils;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameConstant;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.user.FrameEtongApplication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 搜索的调用接口的方法类
 * Created by Administrator on 2016/8/11.
 */
public class Find_Car_SearchProvider {
    private HttpPublisher mHttpPublisher;
    private static String TAG = "UserProvider";
    private static Find_Car_SearchProvider instance;

    public static final String USER_SUCCEED = "PT_ERROR_SUCCESS";
    public static final String USER_EXISTS = "PT_ERROR_REG_REDUPLICATED";
    public static final String USER_DUPLICATE = "PT_ERROR_RECORD_REDUPLICATED";

    private Find_Car_SearchProvider() {

    }

    public void initialize(HttpPublisher httpPublisher) {
        this.mHttpPublisher = httpPublisher;
    }

    public static Find_Car_SearchProvider getInstance() {
        if (null == instance) {
            instance = new Find_Car_SearchProvider();
        }
        return instance;
    }

    /**
     * @Title        : SearchCar
     * @Description  : 通过车系名称查询车系
     * @params
     *     @param str    设定文件
     * @return
     *     void    返回类型
     * @throws
     */
    public void SearchCar(String str) {
        Map<String, String> map = new HashMap<String, String>();
        String carName="";
        try {
            carName= URLEncoder.encode(str,"utf-8");
            if(carName.contains("+")){
                carName.replace("+","20%");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put("key", str);
        //把地址变成测试地址
//        FrameConstant.initService(0);
        HttpMethod method = new HttpMethod(FrameHttpUri.GetCarsetByKey+carName+".do", map);

        mHttpPublisher.sendRequest(method, FrameHttpTag.BY_NAME_SELECT_CAR_SERIES);
    }


    /**
     * @desc (收藏列表)
     * @createtime 2016/12/10 0010-14:50
     * @author wukefan
     * @param isNewFlag  1--新车 0--二手车
     */
    public void getCollectList(int pageSize,int pageCurrent,int isNewFlag,int isPullDown) {

        Map<String, String> map = new HashMap<String, String>();
        if(FrameEtongApplication.getApplication().isLogin()){
            String userId = FrameEtongApplication.getApplication().getUserInfo().getUserId();
            map.put("userId", userId);
        }
        map.put("pageSize", pageSize+"");
        map.put("pageCurrent", pageCurrent+"");
        map.put("isNewFlag", isNewFlag+"");
        map.put("isPullDown", isPullDown+"");


        HttpMethod method = new HttpMethod(FrameHttpUri.COLLECTIONLIST, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.COLLECTIONLIST+isNewFlag);//区分tag
    }

}
