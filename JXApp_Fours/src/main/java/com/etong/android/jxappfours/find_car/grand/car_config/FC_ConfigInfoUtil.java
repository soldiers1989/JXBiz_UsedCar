package com.etong.android.jxappfours.find_car.grand.car_config;

import com.alibaba.fastjson.JSON;
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

/**
 * @author : by sunyao
 * @ClassName : FC_ConfigInfoUtil
 * @Description : (一个获取到车辆配置的工具类)
 * @date : 2016/10/10 - 11:35
 */

public class FC_ConfigInfoUtil {
    private JSONObject mObj;

    public FC_ConfigInfoUtil(JSONObject obj) {
        this.mObj = obj;
    }

    /**
     * 获取到所有车辆的ID
     *
     * @return
     */
    public List getCompareCarInfoList() {
        List<List> temp = new ArrayList<List>();
        if (mObj == null) {
            return null;
        }
        // 遍历其中的数据
        List beanList = new ArrayList();

        JSONObject basicparameter_object = (JSONObject) mObj.get("基本参数");
        FC_VecgileType_Detail_basicparameter mFC_VecgileType_Detail_basicparameter = JSON.toJavaObject(basicparameter_object, FC_VecgileType_Detail_basicparameter.class);
        beanList.add(mFC_VecgileType_Detail_basicparameter);

        JSONObject carbody_object = (JSONObject) mObj.get("车身");
        FC_VecgileType_Detail_carbody mFC_VecgileType_Detail_carbody = JSON.toJavaObject(carbody_object, FC_VecgileType_Detail_carbody.class);
        beanList.add(mFC_VecgileType_Detail_carbody);

        JSONObject engine_object = (JSONObject) mObj.get("发动机");
        FC_VecgileType_Detail_engine mFC_VecgileType_Detail_engine = JSON.toJavaObject(engine_object, FC_VecgileType_Detail_engine.class);
        beanList.add(mFC_VecgileType_Detail_engine);

        JSONObject air_condition_object = (JSONObject) mObj.get("空调/冰箱");
        FC_VecgileType_Detail_air_condition mFC_VecgileType_Detail_air_condition = JSON.toJavaObject(air_condition_object, FC_VecgileType_Detail_air_condition.class);
        beanList.add(mFC_VecgileType_Detail_air_condition);

        JSONObject inside_object = (JSONObject) mObj.get("内部配置");
        FC_VecgileType_Detail_inside_deploy mFC_VecgileType_Detail_inside_deploy = JSON.toJavaObject(inside_object, FC_VecgileType_Detail_inside_deploy.class);
        beanList.add(mFC_VecgileType_Detail_inside_deploy);

        JSONObject safety_device_object = (JSONObject) mObj.get("安全装备");
        FC_VecgileType_Detail_safety_device mFC_VecgileType_Detail_safety_device = JSON.toJavaObject(safety_device_object, FC_VecgileType_Detail_safety_device.class);
        beanList.add(mFC_VecgileType_Detail_safety_device);

        JSONObject gearbox_object = (JSONObject) mObj.get("变速箱");
        FC_VecgileType_Detail_gearbox mFC_VecgileType_Detail_gearbox = JSON.toJavaObject(gearbox_object, FC_VecgileType_Detail_gearbox.class);
        beanList.add(mFC_VecgileType_Detail_gearbox);

        JSONObject chassis_object = (JSONObject) mObj.get("底盘转向");
        FC_VecgileType_Detail_chassis mFC_VecgileType_Detail_chassis = JSON.toJavaObject(chassis_object, FC_VecgileType_Detail_chassis.class);
        beanList.add(mFC_VecgileType_Detail_chassis);

        JSONObject wheelbraking_object = (JSONObject) mObj.get("车轮制动");
        FC_VecgileType_Detail_wheelbraking mFC_VecgileType_Detail_wheelbraking = JSON.toJavaObject(wheelbraking_object, FC_VecgileType_Detail_wheelbraking.class);
        beanList.add(mFC_VecgileType_Detail_wheelbraking);

        JSONObject control_object = (JSONObject) mObj.get("操控配置");
        FC_VecgileType_Detail_control mFC_VecgileType_Detail_control = JSON.toJavaObject(control_object, FC_VecgileType_Detail_control.class);
        beanList.add(mFC_VecgileType_Detail_control);

        JSONObject multimedia_object = (JSONObject) mObj.get("多媒体配置");
        FC_VecgileType_Detail_multimedia mFC_VecgileType_Detail_multimedia = JSON.toJavaObject(multimedia_object, FC_VecgileType_Detail_multimedia.class);
        beanList.add(mFC_VecgileType_Detail_multimedia);

        JSONObject lamplight_object = (JSONObject) mObj.get("灯光配置");
        FC_VecgileType_Detail_lamplight mFC_VecgileType_Detail_lamplight = JSON.toJavaObject(lamplight_object, FC_VecgileType_Detail_lamplight.class);
        beanList.add(mFC_VecgileType_Detail_lamplight);

        JSONObject external_object = (JSONObject) mObj.get("外部配置");
        FC_VecgileType_Detail_external_deploy mFC_VecgileType_Detail_external_deploy = JSON.toJavaObject(external_object, FC_VecgileType_Detail_external_deploy.class);
        beanList.add(mFC_VecgileType_Detail_external_deploy);

        JSONObject glass_object = (JSONObject) mObj.get("玻璃/后视镜");
        FC_VecgileType_Detail_glass mFC_VecgileType_Detail_glass = JSON.toJavaObject(glass_object, FC_VecgileType_Detail_glass.class);
        beanList.add(mFC_VecgileType_Detail_glass);

        JSONObject high_tech_object = (JSONObject) mObj.get("高科技配置");
        FC_VecgileType_Detail_high_tech mFC_VecgileType_Detail_high_tech = JSON.toJavaObject(high_tech_object, FC_VecgileType_Detail_high_tech.class);
        beanList.add(mFC_VecgileType_Detail_high_tech);

        JSONObject seat_object = (JSONObject) mObj.get("座椅配置");
        FC_VecgileType_Detail_seat mFC_VecgileType_Detail_seat = JSON.toJavaObject(seat_object, FC_VecgileType_Detail_seat.class);
        beanList.add(mFC_VecgileType_Detail_seat);

        // 将车辆配置的字段添加到其中
        return beanList;
    }
}
