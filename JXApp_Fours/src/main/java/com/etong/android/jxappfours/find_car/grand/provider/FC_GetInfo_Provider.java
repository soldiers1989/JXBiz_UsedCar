package com.etong.android.jxappfours.find_car.grand.provider;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.L;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车型对比的调用接口的方法类
 * Created by Administrator on 2016/8/11.
 */
public class FC_GetInfo_Provider {
    private HttpPublisher mHttpPublisher;
    private static String TAG = "UserProvider";
    private static FC_GetInfo_Provider instance;

    public static final String USER_SUCCEED = "PT_ERROR_SUCCESS";
    public static final String USER_EXISTS = "PT_ERROR_REG_REDUPLICATED";
    public static final String USER_DUPLICATE = "PT_ERROR_RECORD_REDUPLICATED";

    private FC_GetInfo_Provider() {

    }

    public void initialize(HttpPublisher httpPublisher) {
        this.mHttpPublisher = httpPublisher;
    }

    public static FC_GetInfo_Provider getInstance() {
        if (null == instance) {
            instance = new FC_GetInfo_Provider();
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
        map.put("id",id+"");
        HttpMethod method = new HttpMethod(FrameHttpUri.GetVechileType+id+".do", map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.VECHILE_TYPE);
    }

    /**
     * 通过车辆品牌名ID获取到车系
     */
    public void getCarsetByGrandId(Integer id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id",id+"");
        HttpMethod method = new HttpMethod(FrameHttpUri.GetCarsetByGrandID +id+".do", map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.GET_CARSET_DETAIL);
    }

    /**
     *  获取车型详情页参数配置
     */
    public void getVechileDetails(Integer id) {
        Map<String, String> map = new HashMap<String, String>();
//        map.put("Id",id+"");
        HttpMethod method = new HttpMethod(FrameHttpUri.GetVechileTypeDetail +id+".do", map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.GET_VECHILE_TYPE_DETAIL);
    }

    public void getCompareCarConfig(List<Integer> idList) {
        // 判断传来的idList是否合法
        if (idList == null || idList.size()<=0) {
            return;
        }
        StringBuilder sb = new StringBuilder();

        // 将所有的id添加到List中
        for (int i=0; i<idList.size(); i++) {
            sb.append(idList.get(i)+",");
        }
        L.d("所有的id为：", sb.toString());
        sb.deleteCharAt(sb.length()-1);
        L.d("删除之后的所有的id为：", sb.toString());

        HttpMethod method = new HttpMethod(FrameHttpUri.GetCompareInfo + sb.toString() + ".do", new HashMap());
        method.setSetCache(true);
        method.setCacheTimeLive(1 * 60 * 60 * 24);      // 设置缓存存活时间为一天
        mHttpPublisher.sendRequest(method, FrameHttpTag.GET_COMPARE_INFO);
    }

    /**
     * @desc (点击收藏接口)
     * @createtime 2016/12/2 - 14:46
     * @author xiaoxue
     */
    public void clickCollection(String vid,String image,String imageNum,String manu,String fullName,
                                String title,String carset,String carsetTitle,String prices,String brand,
                                String tag){
        Map<String,String> map=new HashMap<>();
        if (FrameEtongApplication.getApplication().isLogin()) {
            if (null != FrameEtongApplication.getApplication().getUserInfo().getUserId()) {
                map.put("userId", FrameEtongApplication.getApplication().getUserInfo().getUserId());
            }
        }
        map.put("vid", vid);
        map.put("image",image);
        map.put("imageNum",imageNum);
        map.put("manu",manu);
        map.put("fullName",fullName);
        map.put("title",title);
        map.put("carset",carset);
        map.put("carsetTitle",carsetTitle);
        map.put("prices",prices);
        map.put("brand",brand);

        HttpMethod method = new HttpMethod(FrameHttpUri.CLICKCOLLECTION_NEW, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.CLICKCOLLECTION_NEW+tag);
    }

}
