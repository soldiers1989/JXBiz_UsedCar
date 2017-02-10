package com.etong.android.jxappfours.models_contrast.common;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameConstant;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.user.FrameEtongApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * 车型对比的调用接口的方法类
 * Created by Administrator on 2016/8/11.
 */
public class Models_Contrast_Provider {
    private HttpPublisher mHttpPublisher;
    private static String TAG = "UserProvider";
    private static Models_Contrast_Provider instance;

    public static final String USER_SUCCEED = "PT_ERROR_SUCCESS";
    public static final String USER_EXISTS = "PT_ERROR_REG_REDUPLICATED";
    public static final String USER_DUPLICATE = "PT_ERROR_RECORD_REDUPLICATED";

    private Models_Contrast_Provider() {

    }

    public void initialize(HttpPublisher httpPublisher) {
        this.mHttpPublisher = httpPublisher;
    }

    public static Models_Contrast_Provider getInstance() {
        if (null == instance) {
            instance = new Models_Contrast_Provider();
        }
        return instance;
    }

    /**
     * @Title        : getBrandAll
     * @Description  :获取所有品牌
     * @params
     *     @param
     * @return
     *     void    返回类型
     * @throws
     */
    public void getBrandAll() {
        Map<String, String> map = new HashMap<String, String>();

        HttpMethod method = new HttpMethod(FrameHttpUri.GetAllBrand, map);
        method.setSetCache(true);
        method.setCacheTimeLive(1 * 60 * 60 * 24);      // 设置缓存时间为一天
        mHttpPublisher.sendRequest(method, FrameHttpTag.BRAND_ALL);
    }


    /**
     *获取品牌车系列表
     */

    public void getVechileSeries(Integer id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("brandId",id+"");
        HttpMethod method = new HttpMethod(FrameHttpUri.GetBrandCarLine+id+".do", map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.VECHILE_SERIES);
    }

    /**
     *获取车型列表页
     */
    public void getVechileType(Integer id) {
        Map<String, String> map = new HashMap<String, String>();
        if(FrameEtongApplication.getApplication().isLogin()){
            map.put("userID",FrameEtongApplication.getApplication().getUserInfo().getUserId());
            HttpMethod method = new HttpMethod(FrameHttpUri.GetVechileType+id+".do", map);
            mHttpPublisher.sendRequest(method, FrameHttpTag.VECHILE_TYPE);
        }else {
            HttpMethod method = new HttpMethod(FrameHttpUri.GetVechileType+id+".do", map);
            mHttpPublisher.sendRequest(method, FrameHttpTag.VECHILE_TYPE);
        }
    }

    /**
     * 获取车系详情页、车型列表页图片数量的接口
     * @param id
     */
    public void getCarsetImageNum(Integer id) {
        Map<String, String> map = new HashMap<String, String>();
        HttpMethod method = new HttpMethod(FrameHttpUri.GetCarsetPicNum+id+".do", map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.GET_CARSET_PIC_NUM);
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
