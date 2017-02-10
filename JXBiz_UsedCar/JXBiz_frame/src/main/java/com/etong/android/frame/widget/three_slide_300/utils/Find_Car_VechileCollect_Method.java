package com.etong.android.frame.widget.three_slide_300.utils;

import com.alibaba.fastjson.JSON;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.three_slide_300.javabean.Models_Contrast_VechileType;

import java.util.HashMap;
import java.util.Map;

/**
 * 收藏的方法
 * Created by Administrator on 2016/8/11.
 */
public class Find_Car_VechileCollect_Method {

    protected static Map mVechileCollectMap;
    private static SharedPublisher mSharedPublisher = SharedPublisher.getInstance();
    protected static final String VECHILECOLLECT_SHARED = "vechileCollectShared";


    /**
     * @Title : cartAdd
     * @Description : 收藏操作
     * @params
     */
    public static void cartAdd(Models_Contrast_VechileType vechileCollect) {
        if (null != getVechileCollectInfo()) {
            Map oldMap = getVechileCollectInfo();
            oldMap.put(vechileCollect.getModel_id(), vechileCollect);
            setVechileGoodsCartInfo(oldMap);
        } else {
            Map newMap = new HashMap<>();
            newMap.put(vechileCollect.getModel_id(), vechileCollect);
            setVechileGoodsCartInfo(newMap);
        }
    }

    ;

    /**
     * 取消收藏操作
     *
     * @param id
     */
    public static void remove(Integer id) {
        Map oldMap = getVechileCollectInfo();
        oldMap.remove(id);
        setVechileGoodsCartInfo(oldMap);
    }

    ;


    /**
     * @return Map 返回类型
     * @throws
     * @Title : getVechileGoodsCartInfo
     * @Description : 获取收藏车辆信息
     * @params
     */
    public static Map getVechileCollectInfo() {
        Map getVechileCollectMap = null;
        try {
            String vechileGoodsShared = mSharedPublisher.getString(VECHILECOLLECT_SHARED);
            getVechileCollectMap = JSON.parseObject(vechileGoodsShared, Map.class);
        } catch (Exception e) {

        }
        return getVechileCollectMap;
    }

    /**
     * @Title :setVechileGoodsCartInfo
     * @Description : 设置收藏车辆信息
     */
    public static void setVechileGoodsCartInfo(Map vechileCollectList) {
        mVechileCollectMap = vechileCollectList;
        L.json(JSON.toJSONString(mVechileCollectMap));
        mSharedPublisher.put(VECHILECOLLECT_SHARED, JSON.toJSONString(mVechileCollectMap));
    }
}
