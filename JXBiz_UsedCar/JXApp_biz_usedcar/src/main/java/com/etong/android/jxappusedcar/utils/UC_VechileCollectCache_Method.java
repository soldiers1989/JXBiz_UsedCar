package com.etong.android.jxappusedcar.utils;

import com.alibaba.fastjson.JSON;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.frame.utils.L;
import com.etong.android.jxappusedcar.bean.UC_CollectOrScannedCarInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 收藏的方法
 * Created by Administrator on 2016/8/11.
 */
public class UC_VechileCollectCache_Method {

    protected static Map mVechileCollectMap;
    private static SharedPublisher mSharedPublisher = SharedPublisher.getInstance();
    protected static final String VECHILECOLLECT_SHARED = "vechileCollectShared";
    private static boolean isLast = true;


    /**
     * @Title : cartAdd
     * @Description : 收藏操作
     * @params
     */
    public static void carAdd(UC_CollectOrScannedCarInfo vechileCollect, String phone) {
        if (null != getVechileCollectInfo(phone)) {
            Map oldMap = getVechileCollectInfo(phone);
            oldMap.put(vechileCollect.getF_collectid(), vechileCollect);
            setVechileCollectInfo(oldMap, phone);
        } else {
            Map newMap = new HashMap<>();
            newMap.put(vechileCollect.getF_collectid(), vechileCollect);
            setVechileCollectInfo(newMap, phone);
        }
    }

    ;

    /**
     * 取消收藏操作
     *
     * @param f_collectid
     */
    public static void remove(Integer f_collectid,String phone) {
        if (null != getVechileCollectInfo(phone)) {
            Map oldMap = getVechileCollectInfo(phone);
            oldMap.remove(f_collectid);
            setVechileCollectInfo(oldMap,phone);
        }
    }

    /**
     * 清空收藏操作
     */
    public static void removeAll(String phone) {
        setVechileCollectInfo(null,phone);
    }

    /**
     * 收藏条数
     */
    public static int getCollectCount(String phone) {
        if (null != getVechileCollectInfo(phone)) {
            Map oldMap = getVechileCollectInfo(phone);
            return oldMap.size();
        }
        return 0;
    }

    /**
     * 获取收藏的List数据
     */
    public static List<UC_CollectOrScannedCarInfo> getVechileColectList(String phone) {
        //得到缓存的收藏车辆
        List<UC_CollectOrScannedCarInfo> mList = new ArrayList<UC_CollectOrScannedCarInfo>();
        Map map = getVechileCollectInfo(phone);
        UC_CollectOrScannedCarInfo info = null;
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                String data = String.valueOf(map.get(Integer.valueOf(String.valueOf(key))));
                L.d("从本地获取到的数据:", data);
                info = JSON.parseObject(data, UC_CollectOrScannedCarInfo.class);
                if (null != info) {
                    mList.add(info);
                }
            }
        }
        return mList;
    }

    /**
     * 获取分页收藏的List数据
     */
    public static List<UC_CollectOrScannedCarInfo> getPagingVechileColectList(int pageSize, int pageCurrent,String phone) {
        //得到缓存的收藏车辆
        List<UC_CollectOrScannedCarInfo> mList = new ArrayList<UC_CollectOrScannedCarInfo>();
        List<UC_CollectOrScannedCarInfo> mPageList = new ArrayList<UC_CollectOrScannedCarInfo>();
        Map map = getVechileCollectInfo(phone);
        UC_CollectOrScannedCarInfo info = null;
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                String data = String.valueOf(map.get(Integer.valueOf(String.valueOf(key))));
                L.d("从本地获取到的数据:", data);
                info = JSON.parseObject(data, UC_CollectOrScannedCarInfo.class);
                if (null != info) {
                    mList.add(info);
                }
            }
        }
        if (mList.size() >= pageSize * (pageCurrent + 1)) {
            mPageList = mList.subList(pageSize * pageCurrent, pageSize * pageCurrent + pageSize);
            isLast = false;
        } else {
            if (pageCurrent == 0) {
                mPageList = mList;
                isLast = true;
            } else {
                if (!isLast) {
                    int size = mList.size();
                    mPageList = mList.subList(pageSize * pageCurrent, size);
                    isLast = true;
                }
            }
        }
        return mPageList;
    }

    /**
     * @return Map 返回类型
     * @throws
     * @Title : getVechileGoodsCartInfo
     * @Description : 获取收藏车辆信息
     * @params
     */
    public static Map getVechileCollectInfo(String phone) {
        Map getVechileCollectMap = null;
        try {
            String vechileGoodsShared = mSharedPublisher.getString(VECHILECOLLECT_SHARED + phone);
            getVechileCollectMap = JSON.parseObject(vechileGoodsShared, Map.class);
        } catch (Exception e) {

        }
        return getVechileCollectMap;
    }

    /**
     * @Title :setVechileGoodsCartInfo
     * @Description : 设置收藏车辆信息
     */
    public static void setVechileCollectInfo(Map vechileCollectList, String phone) {
        mVechileCollectMap = vechileCollectList;
        L.json( JSON.toJSONString(mVechileCollectMap));
        mSharedPublisher.put(VECHILECOLLECT_SHARED + phone, JSON.toJSONString(mVechileCollectMap));
    }
}
