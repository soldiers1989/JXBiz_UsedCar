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
public class UC_VechileScannedRecordsCache_Method {

    protected static Map mVechileScannedRecordsMap;
    private static SharedPublisher mSharedPublisher = SharedPublisher.getInstance();
    protected static final String VECHILESCANNEDRECORDS_SHARED = "vechileScannedRecordsShared";
    private static boolean isLast = true;

    /**
     * @Title : carAdd
     * @Description : 增加浏览记录操作
     * @params
     */
    public static void carAdd(UC_CollectOrScannedCarInfo vechileScannedRecords, String phone) {
//        boolean isPut = false;

        if (null != getVechileScannedRecordsInfo(phone)) {
            Map oldMap = getVechileScannedRecordsInfo(phone);
            if (null != getVechileScannedRecordsInfo("") && getVechileScannedRecordsInfo("").size() != 0) {
                Map tempMap = getVechileScannedRecordsInfo("");
                Iterator<String> iterator = tempMap.keySet().iterator();
                while (iterator.hasNext()) {   // 遍历Map中的数据
                    String f_dvid = iterator.next();   // 获取到当前的ID
                    oldMap.put(f_dvid, tempMap.get(f_dvid));
                }
                setVechileScannedRecordsInfo(null, "");
            }
            oldMap.put(vechileScannedRecords.getF_dvid(), vechileScannedRecords);
            setVechileScannedRecordsInfo(oldMap, phone);
        } else {
            if (null != getVechileScannedRecordsInfo("") && getVechileScannedRecordsInfo("").size() != 0) {
                setVechileScannedRecordsInfo(getVechileScannedRecordsInfo(""), phone);
                setVechileScannedRecordsInfo(null, "");
            } else {
                Map newMap = new HashMap<>();
                newMap.put(vechileScannedRecords.getF_dvid(), vechileScannedRecords);
                setVechileScannedRecordsInfo(newMap, phone);
            }
        }
    }


    /**
     * @desc (设置用户登录后的浏览记录,(没登录前如果有浏览记录则把未登录的浏览记录加入用户登录后的浏览记录,然后清空未登录的浏览记录))
     * @createtime 2016/11/4 0004-18:07
     * @author wukefan
     */
    public static void setLoginScannedRecords(String phone) {
        if (null != getVechileScannedRecordsInfo("") && getVechileScannedRecordsInfo("").size() != 0) {
            Map tempMap = getVechileScannedRecordsInfo("");
            if (null != getVechileScannedRecordsInfo(phone)) {
                Map oldMap = getVechileScannedRecordsInfo(phone);
                Iterator<String> iterator = tempMap.keySet().iterator();
                while (iterator.hasNext()) {   // 遍历Map中的数据
                    String f_dvid = iterator.next();   // 获取到当前的ID
                    oldMap.put(f_dvid, tempMap.get(f_dvid));
                }
                setVechileScannedRecordsInfo(null, "");
                setVechileScannedRecordsInfo(oldMap, phone);

            } else {
                setVechileScannedRecordsInfo(tempMap, phone);
                setVechileScannedRecordsInfo(null, "");
            }
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
    public static void remove(String f_dvid, String phone) {
        if (null != getVechileScannedRecordsInfo(phone)) {
            Map oldMap = getVechileScannedRecordsInfo(phone);
            oldMap.remove(f_dvid);
            setVechileScannedRecordsInfo(oldMap, phone);
        }
    }

    /**
     * 清空浏览记录操作
     */
    public static void removeAll(String phone) {
        setVechileScannedRecordsInfo(null, phone);
    }


    /**
     * 浏览记录条数
     */
    public static int getScannedCarCount(String phone) {
        if (null != getVechileScannedRecordsInfo(phone)) {
            Map oldMap = getVechileScannedRecordsInfo(phone);
            return oldMap.size();
        }
        return 0;
    }

    /**
     * 获取浏览记录的List数据
     */
    public static List<UC_CollectOrScannedCarInfo> getVechilegeScannedRecordsList(String phone) {
        //得到缓存的浏览记录车辆
        List<UC_CollectOrScannedCarInfo> mList = new ArrayList<UC_CollectOrScannedCarInfo>();
        Map map = getVechileScannedRecordsInfo(phone);
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
    public static List<UC_CollectOrScannedCarInfo> getPagingVechileScannedRecordsList(int pageSize, int pageCurrent, String phone) {
        //得到缓存的收藏车辆
        List<UC_CollectOrScannedCarInfo> mList = new ArrayList<UC_CollectOrScannedCarInfo>();
        List<UC_CollectOrScannedCarInfo> mPageList = new ArrayList<UC_CollectOrScannedCarInfo>();
        Map map = getVechileScannedRecordsInfo(phone);
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
    public static Map getVechileScannedRecordsInfo(String phone) {
        Map getVechileScannedRecordsMap = null;
        try {
            String vechileScannedRecordsShared = mSharedPublisher.getString(VECHILESCANNEDRECORDS_SHARED + phone);
            getVechileScannedRecordsMap = JSON.parseObject(vechileScannedRecordsShared, Map.class);
        } catch (Exception e) {

        }

        return getVechileScannedRecordsMap;
    }

    /**
     * @Title :setVechileScannedRecordsInfo
     * @Description : 设置浏览记录车辆信息
     */
    public static void setVechileScannedRecordsInfo(Map vechileScannedRecordsList, String phone) {
        mVechileScannedRecordsMap = vechileScannedRecordsList;
        L.json(JSON.toJSONString(mVechileScannedRecordsMap));
        mSharedPublisher.put(VECHILESCANNEDRECORDS_SHARED + phone, JSON.toJSONString(mVechileScannedRecordsMap));
    }
}
