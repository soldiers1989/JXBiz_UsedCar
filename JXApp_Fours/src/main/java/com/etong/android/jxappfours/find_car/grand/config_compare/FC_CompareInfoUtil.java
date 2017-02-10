package com.etong.android.jxappfours.find_car.grand.config_compare;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_air_condition;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_basicparameter;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_carbody;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_chassis;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_control;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_engine;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_external_deploy;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_gearbox;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_glass;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_high_tech;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_inside_deploy;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_lamplight;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_multimedia;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_safety_device;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_seat;
import com.etong.android.jxappfours.find_car.grand.parameter_detail_javabean.FC_VecgileType_Detail_wheelbraking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ellison.Sun on 2016/8/30.
 * <p/>
 * 车辆对比信息帮助类
 */
public class FC_CompareInfoUtil {

    private JSONArray mObj;

    public FC_CompareInfoUtil(JSONArray obj) {
        this.mObj = obj;
    }

    /**
     * 获取到所有车辆的标题名字
     *
     * @return
     */
    public List<String> getCompareTitleName() {

        List<String> temp = new ArrayList<String>();
        if (mObj == null) {
            return null;
        }
        // 遍历其中的数据
        for (int i = 0; i < mObj.size(); i++) {
            JSONObject o = (JSONObject) mObj.get(i);
            String fullName = (String) o.get("fullName");

            // 将车辆的名字添加到其中
            temp.add(fullName);
        }
        return temp;
    }

    /**
     * 获取到所有车辆的ID
     *
     * @return
     */
    public List<String> getCompareIdList() {

        List<String> temp = new ArrayList<String>();
        if (mObj == null) {
            return null;
        }
        // 遍历其中的数据
        for (int i = 0; i < mObj.size(); i++) {
            JSONObject o = (JSONObject) mObj.get(i);
            String id = (String) o.get("id");

            // 将车辆的名字添加到其中
            temp.add(id);
        }
        return temp;
    }

    /**
     * 获取到所有车辆的ID
     *
     * @return
     */
    public List getCompareCarInfoList() {
        List resultList= new ArrayList();
        List<List> temp = new ArrayList<List>();
        Map tempConmpare= new HashMap();
        if (mObj == null) {
            return null;
        }
        // 遍历其中的数据
        for (int i = 0; i < mObj.size(); i++) {
            List beanList = new ArrayList();
            JSONObject o = (JSONObject) mObj.get(i);
            JSONObject jsonArr = o.getJSONObject("data");

            JSONObject basicparameter_object = (JSONObject) jsonArr.get("基本参数");
            FC_VecgileType_Detail_basicparameter mFC_VecgileType_Detail_basicparameter = JSON.toJavaObject(basicparameter_object, FC_VecgileType_Detail_basicparameter.class);
            beanList.add(mFC_VecgileType_Detail_basicparameter);

            JSONObject carbody_object = (JSONObject) jsonArr.get("车身");
            FC_VecgileType_Detail_carbody mFC_VecgileType_Detail_carbody = JSON.toJavaObject(carbody_object, FC_VecgileType_Detail_carbody.class);
            beanList.add(mFC_VecgileType_Detail_carbody);

            JSONObject engine_object = (JSONObject) jsonArr.get("发动机");
            FC_VecgileType_Detail_engine mFC_VecgileType_Detail_engine = JSON.toJavaObject(engine_object, FC_VecgileType_Detail_engine.class);
            beanList.add(mFC_VecgileType_Detail_engine);

            JSONObject air_condition_object = (JSONObject) jsonArr.get("空调/冰箱");
            FC_VecgileType_Detail_air_condition mFC_VecgileType_Detail_air_condition = JSON.toJavaObject(air_condition_object, FC_VecgileType_Detail_air_condition.class);
            beanList.add(mFC_VecgileType_Detail_air_condition);

            JSONObject inside_object = (JSONObject) jsonArr.get("内部配置");
            FC_VecgileType_Detail_inside_deploy mFC_VecgileType_Detail_inside_deploy = JSON.toJavaObject(inside_object, FC_VecgileType_Detail_inside_deploy.class);
            beanList.add(mFC_VecgileType_Detail_inside_deploy);

            JSONObject safety_device_object = (JSONObject) jsonArr.get("安全装备");
            FC_VecgileType_Detail_safety_device mFC_VecgileType_Detail_safety_device = JSON.toJavaObject(safety_device_object, FC_VecgileType_Detail_safety_device.class);
            beanList.add(mFC_VecgileType_Detail_safety_device);

            JSONObject gearbox_object = (JSONObject) jsonArr.get("变速箱");
            FC_VecgileType_Detail_gearbox mFC_VecgileType_Detail_gearbox = JSON.toJavaObject(gearbox_object, FC_VecgileType_Detail_gearbox.class);
            beanList.add(mFC_VecgileType_Detail_gearbox);

            JSONObject chassis_object = (JSONObject) jsonArr.get("底盘转向");
            FC_VecgileType_Detail_chassis mFC_VecgileType_Detail_chassis = JSON.toJavaObject(chassis_object, FC_VecgileType_Detail_chassis.class);
            beanList.add(mFC_VecgileType_Detail_chassis);

            JSONObject wheelbraking_object = (JSONObject) jsonArr.get("车轮制动");
            FC_VecgileType_Detail_wheelbraking mFC_VecgileType_Detail_wheelbraking = JSON.toJavaObject(wheelbraking_object, FC_VecgileType_Detail_wheelbraking.class);
            beanList.add(mFC_VecgileType_Detail_wheelbraking);

            JSONObject control_object = (JSONObject) jsonArr.get("操控配置");
            FC_VecgileType_Detail_control mFC_VecgileType_Detail_control = JSON.toJavaObject(control_object, FC_VecgileType_Detail_control.class);
            beanList.add(mFC_VecgileType_Detail_control);

            JSONObject multimedia_object = (JSONObject) jsonArr.get("多媒体配置");
            FC_VecgileType_Detail_multimedia mFC_VecgileType_Detail_multimedia = JSON.toJavaObject(multimedia_object, FC_VecgileType_Detail_multimedia.class);
            beanList.add(mFC_VecgileType_Detail_multimedia);

            JSONObject lamplight_object = (JSONObject) jsonArr.get("灯光配置");
            FC_VecgileType_Detail_lamplight mFC_VecgileType_Detail_lamplight = JSON.toJavaObject(lamplight_object, FC_VecgileType_Detail_lamplight.class);
            beanList.add(mFC_VecgileType_Detail_lamplight);

            JSONObject external_object = (JSONObject) jsonArr.get("外部配置");
            FC_VecgileType_Detail_external_deploy mFC_VecgileType_Detail_external_deploy = JSON.toJavaObject(external_object, FC_VecgileType_Detail_external_deploy.class);
            beanList.add(mFC_VecgileType_Detail_external_deploy);

            JSONObject glass_object = (JSONObject) jsonArr.get("玻璃/后视镜");
            FC_VecgileType_Detail_glass mFC_VecgileType_Detail_glass = JSON.toJavaObject(glass_object, FC_VecgileType_Detail_glass.class);
            beanList.add(mFC_VecgileType_Detail_glass);

            JSONObject high_tech_object = (JSONObject) jsonArr.get("高科技配置");
            FC_VecgileType_Detail_high_tech mFC_VecgileType_Detail_high_tech = JSON.toJavaObject(high_tech_object, FC_VecgileType_Detail_high_tech.class);
            beanList.add(mFC_VecgileType_Detail_high_tech);

            JSONObject seat_object = (JSONObject) jsonArr.get("座椅配置");
            FC_VecgileType_Detail_seat mFC_VecgileType_Detail_seat = JSON.toJavaObject(seat_object, FC_VecgileType_Detail_seat.class);
            beanList.add(mFC_VecgileType_Detail_seat);

            for (String strTitle : jsonArr.keySet()) {
                for (String strItem:jsonArr.getJSONObject(strTitle).keySet()){
                    List tempItemList = (List) tempConmpare.get(strItem);
                    if (null==tempItemList){
                        tempItemList = new ArrayList();
                    }
                    // 获取到当前的item值，将值添加到是否相同的list中
                    String tempStrItem = String.valueOf(jsonArr.getJSONObject(strTitle).get(strItem));
                    if(TextUtils.isEmpty(tempStrItem)) {
                        tempStrItem = "-";
                    }
                    if (!tempItemList.contains(tempStrItem)){
//                        tempItemList.add(jsonArr.getJSONObject(strTitle).get(strItem));
                        tempItemList.add(tempStrItem);
                        tempConmpare.put(strItem,tempItemList);
                    }
                }

            }
            // 将车辆的名字添加到其中
            temp.add(beanList);
        }
        //数据清理
        Iterator iterator = tempConmpare.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if (1==((List)tempConmpare.get(key)).size()){
                //完全相同
                iterator.remove();
                tempConmpare.remove(key);
            }
        }

        resultList.add(tempConmpare.keySet());
        resultList.add(temp);
        return resultList;
    }


}
