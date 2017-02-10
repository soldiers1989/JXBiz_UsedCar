package com.etong.android.jxappusedcar.javabean;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 二手车详情json数据
 * @createtime 2016/10/10 - 12:43
 * @Created by xiaoxue.
 */

public class UC_Details_JsonData {
    public static String getJsonArray() {
        UC_DetailsJavaBean mUC_DetailsJavaBean = new UC_DetailsJavaBean();
        List<UC_DetailsJavaBean> list=new ArrayList<>();
        mUC_DetailsJavaBean.setTitle("长城M4 2012款 1.5手动豪华版");
//        mUC_DetailsJavaBean.setImage("http://113.247.237.98:10002/data//car/20160323/17b4dfc4-6784-4613-8ab9-3bfcabd147fe.jpg");
        mUC_DetailsJavaBean.setImage("");
        mUC_DetailsJavaBean.setPrice(4.07);
        mUC_DetailsJavaBean.setNew_car_min_price(15.49);
        mUC_DetailsJavaBean.setNew_car_max_price(18.45);
        mUC_DetailsJavaBean.setMileage(4.2+"");
        mUC_DetailsJavaBean.setCar_age(2015+"");
        mUC_DetailsJavaBean.setDisplacement(1.5+"");
        mUC_DetailsJavaBean.setAMT("手动");
        mUC_DetailsJavaBean.setExpire(2016+"年");
        mUC_DetailsJavaBean.setExpire1(2016+"年");
        mUC_DetailsJavaBean.setExpire2(2016+"年");
        mUC_DetailsJavaBean.setRecord_time(0+"");


        mUC_DetailsJavaBean.setBrief_title("该车已认证通过");
        mUC_DetailsJavaBean.setWebview("");
        mUC_DetailsJavaBean.setService_commitment("不划算拒绝后v聚聚会v和v我测测操场上的");



        list.add(mUC_DetailsJavaBean);
        String jsonArr = JSONArray.toJSONString(list);

        return jsonArr;
    }
}
