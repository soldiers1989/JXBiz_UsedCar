package com.etong.android.jxappfours.models_contrast.common;

import com.alibaba.fastjson.JSON;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;

import java.util.HashMap;
import java.util.Map;

/**
 * 增加车款  缓存的方法
 * Created by Administrator on 2016/8/11.
 */
public class Models_Contrast_AddVechileStyle_Method {

    protected static Map mVechileCollectMap;
    private static SharedPublisher mSharedPublisher = SharedPublisher.getInstance();
    protected static final String VECHILECOLLECT_SHARED = "vechileCollectShared";
    protected static Map mAdd_VechileStyle;
    protected static final String ADD_VECHILESTYLE = "add  vechileStyle";

    /*
     * @Title : cartAdd
     * @Description : 增加缓存
     * @params
     * */

    public static void cartAdd(Models_Contrast_VechileType add) {
        if (null != getAdd_VechileStyle()) {
            Map oldMap = getAdd_VechileStyle();
            oldMap.put(add.getVid(), add);
            setAdd_VechileStyle(oldMap);
        } else {
            Map newMap = new HashMap<>();
            newMap.put(add.getVid(), add);
            setAdd_VechileStyle(newMap);
        }
    }



   /*
     * 删除操作
     *
     * @param id
     *
     */

    public static void remove(Integer id) {
        Map oldMap = getAdd_VechileStyle();
        oldMap.remove(id);
        setAdd_VechileStyle(oldMap);
    }



    /**
     * @Title : getAdd_VechileStyle
     * @Description : 获取添加车款信息
     * @params
     * @return 设定文件
     * @return User 返回类型
     * @throws
     */

    public static Map getAdd_VechileStyle() {
        Map getVechileStyleMap = null;
        try {
            String vechileGoodsShared = mSharedPublisher.getString(ADD_VECHILESTYLE);
            getVechileStyleMap = JSON.parseObject(vechileGoodsShared, Map.class);
        } catch (Exception e) {

        }
        return getVechileStyleMap;
    }

    public static void setAdd_VechileStyle(Map add_vechileStyle) {
        mAdd_VechileStyle = add_vechileStyle;
        mSharedPublisher.put(ADD_VECHILESTYLE, JSON.toJSONString(mAdd_VechileStyle));
    }

    //清空数据
    public static void clear() {
        mSharedPublisher.put(ADD_VECHILESTYLE, "");
    }
}
