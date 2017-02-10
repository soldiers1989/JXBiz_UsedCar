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
 * 浏览记录的方法
 * Created by Administrator on 2016/8/11.
 */
public class UC_VechileScannedRecords_Method {

    protected static Map mVechileScannedRecordsMap;
    private static SharedPublisher mSharedPublisher = SharedPublisher.getInstance();
    protected static final String VECHILESCANNEDRECORDS_SHARED = "vechileScannedRecordsShared";
    private static boolean isLast = true;


    /**
     * @Title : carAdd
     * @Description : 增加浏览记录操作
     * @params
     */
    public static void carAdd(UC_CollectOrScannedCarInfo vechileScannedRecords) {
//        boolean isPut = false;
        if (null != getVechileScannedRecordsInfo()) {
            Map oldMap = getVechileScannedRecordsInfo();
//            if (oldMap.size() > 0) {            // 没有值得时候判断是否为空
//                Iterator it = oldMap.entrySet().iterator();
//                while (it.hasNext()) {
//                    Map.Entry entry = (Map.Entry) it.next();
//                    Object key = entry.getKey();
//                    String data = String.valueOf(oldMap.get(Integer.valueOf(String.valueOf(key))));
//                    UC_CollectOrScannedCarInfo info = JSON.parseObject(data, UC_CollectOrScannedCarInfo.class);
//                    if (info.getF_dvid().equals(vechileScannedRecords.getF_dvid())) {
////					vechileGoodsCartInfo.setAmount(info.getAmount() + amount);
//                        L.d("要删除的数据id：", "---" + info.getF_historyid());
//                        oldMap.remove(info.getF_historyid());
//                        L.d("要加入的数据id：", "---" + vechileScannedRecords.getF_historyid());
//                        oldMap.put(vechileScannedRecords.getF_historyid(), vechileScannedRecords);
//                        isPut = true;
//                    }
//                }
//
//                if (!isPut) {
//                    oldMap.put(vechileScannedRecords.getF_historyid(), vechileScannedRecords);
//                }
//            }
            oldMap.put(vechileScannedRecords.getF_dvid(), vechileScannedRecords);
            setVechileScannedRecordsInfo(oldMap);
        } else {
            Map newMap = new HashMap<>();
            newMap.put(vechileScannedRecords.getF_dvid(), vechileScannedRecords);
            setVechileScannedRecordsInfo(newMap);
        }
    }


/*    *//**
     * 删除浏览记录操作
     *
     * @param f_historyid
     *//*
    public static void remove(Integer f_historyid) {
        if (null != getVechileScannedRecordsInfo()) {
            Map oldMap = getVechileScannedRecordsInfo();
            oldMap.remove(f_historyid);
            setVechileScannedRecordsInfo(oldMap);
        }
    } */
    /**
     * 删除浏览记录操作
     *
     * @param f_dvid
     */
    public static void remove(String f_dvid) {
        if (null != getVechileScannedRecordsInfo()) {
            Map oldMap = getVechileScannedRecordsInfo();
            oldMap.remove(f_dvid);
            setVechileScannedRecordsInfo(oldMap);
        }
    }

    /**
     * 清空浏览记录操作
     */
    public static void removeAll() {
        setVechileScannedRecordsInfo(null);
    }


    /**
     * 浏览记录条数
     */
    public static int getScannedCarCount() {
        if (null != getVechileScannedRecordsInfo()) {
            Map oldMap = getVechileScannedRecordsInfo();
            return oldMap.size();
        }
        return 0;
    }

    /**
     * 获取浏览记录的List数据
     */
    public static List<UC_CollectOrScannedCarInfo> getVechilegeScannedRecordsList() {
        //得到缓存的浏览记录车辆
        List<UC_CollectOrScannedCarInfo> mList = new ArrayList<UC_CollectOrScannedCarInfo>();
        Map map = getVechileScannedRecordsInfo();
        UC_CollectOrScannedCarInfo info = null;
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                String data = String.valueOf(map.get(String.valueOf(key)));
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
     * 获取分页浏览记录的List数据
     */
    public static List<UC_CollectOrScannedCarInfo> getPagingVechileScannedRecordsList(int pageSize, int pageCurrent) {
        //得到缓存的收藏车辆
        List<UC_CollectOrScannedCarInfo> mList = new ArrayList<UC_CollectOrScannedCarInfo>();
        List<UC_CollectOrScannedCarInfo> mPageList = new ArrayList<UC_CollectOrScannedCarInfo>();
        Map map = getVechileScannedRecordsInfo();
        UC_CollectOrScannedCarInfo info = null;
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                String data = String.valueOf(map.get(String.valueOf(key)));
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
     * @Title : getVechileScannedRecordsInfo
     * @Description : 获取浏览记录车辆信息
     * @params
     */
    public static Map getVechileScannedRecordsInfo() {
        Map getVechileScannedRecordsMap = null;
        try {
            String vechileScannedRecordsShared = mSharedPublisher.getString(VECHILESCANNEDRECORDS_SHARED);
            getVechileScannedRecordsMap = JSON.parseObject(vechileScannedRecordsShared, Map.class);
        } catch (Exception e) {

        }
        return getVechileScannedRecordsMap;
    }

    /**
     * @Title :setVechileScannedRecordsInfo
     * @Description : 设置浏览记录车辆信息
     */
    public static void setVechileScannedRecordsInfo(Map vechileScannedRecordsList) {
        mVechileScannedRecordsMap = vechileScannedRecordsList;
        L.json(JSON.toJSONString(mVechileScannedRecordsMap));
        mSharedPublisher.put(VECHILESCANNEDRECORDS_SHARED, JSON.toJSONString(mVechileScannedRecordsMap));
    }
}
