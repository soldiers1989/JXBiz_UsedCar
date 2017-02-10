package com.etong.android.jxappusedcar.utils;

import com.alibaba.fastjson.JSON;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.jxappusedcar.javabean.UC_CollectBean;
import com.etong.android.jxappusedcar.javabean.UC_World_CarListJavaBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc (二手车收藏的处理缓存方法)
 * @createtime 2016/12/2 - 15:38
 * @Created by xiaoxue.
 */

public class UC_CollectMethod {
    private static SharedPublisher mSharedPublisher = SharedPublisher.getInstance();
    private static Map mCollectMap;
    private static final String USED_CAR_COLLECT="used car collect";


    /**
     * @desc (添加收藏操作)
     * @createtime 2016/12/2 - 15:53
     * @author xiaoxue
     */
    public static void addCollect(UC_World_CarListJavaBean mCarListJavaBean){
        if(null!=getCollect()){
            Map oldMap=getCollect();
            oldMap.put(mCarListJavaBean.getF_dvid(),mCarListJavaBean);
            setCollect(oldMap);
        }else {
            Map newMap=new HashMap<>();
            newMap.put(mCarListJavaBean.getF_dvid(),mCarListJavaBean);
            setCollect(newMap);
        }

    }



    /**
     * @desc (取消收藏)
     * @createtime 2016/12/2 - 16:26
     * @author xiaoxue
     */
    public static void removeCollect(String id){
        Map oldMap = getCollect();
        oldMap.remove(id);
        setCollect(oldMap);
    }


    /**
     * @desc (获取二手车收藏车辆)
     * @createtime 2016/12/2 - 15:54
     * @author xiaoxue
     */
    public static  Map getCollect(){
        Map getCollectMap = null;
        try {
            String collectShared = mSharedPublisher.getString(USED_CAR_COLLECT);
            getCollectMap = JSON.parseObject(collectShared, Map.class);
        } catch (Exception e) {
        }
        return getCollectMap;
    }

    /**
     * @desc (设置收藏车辆)
     * @createtime 2016/12/2 - 15:55
     * @author xiaoxue
     */
    public static void setCollect(Map setCollectMap){
        mCollectMap=setCollectMap;
        mSharedPublisher.put(USED_CAR_COLLECT,JSON.toJSONString(setCollectMap));
    }
}
