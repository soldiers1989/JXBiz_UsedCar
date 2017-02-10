package com.etong.android.jxappfind.view;

import com.alibaba.fastjson.JSONArray;
import com.etong.android.jxappfind.javabean.FindPromotionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 促销车详情json数据
 * Created by Administrator on 2016/9/6.
 */
public class FindJsonPromotionCarData {

    public static String getJsonArray(){

        FindPromotionBean bean=new FindPromotionBean();
        FindPromotionBean bean1=new FindPromotionBean();
        FindPromotionBean bean2=new FindPromotionBean();
        FindPromotionBean bean3=new FindPromotionBean();
        FindPromotionBean bean4=new FindPromotionBean();
        FindPromotionBean bean5=new FindPromotionBean();
        List<FindPromotionBean> listbean=new ArrayList<>();
        bean.setCar_id(744810);
        bean.setTitle("东风标致5082015款 2.0L 自动致逸版");
        bean.setCuxiaojia("14.07万");
        bean.setColors("可选颜色[碳晶黑]");
        bean.setGuide_price("18.57万");
        bean.setSave_money("已节省45000元");
        bean.setComment1("数量有限，售完即止");
        bean.setEnd_seconds(1693163832000L);


        bean1.setCar_id(744811);
        bean1.setTitle("东风标致5082015款 2.0L 自动版");
        bean1.setCuxiaojia("14.07万");
        bean1.setColors("可选颜色[碳晶百]");
        bean1.setGuide_price("18.57万");
        bean1.setSave_money("已节省45000元");
        bean1.setComment1("数量有限，售完即止");
        bean1.setEnd_seconds(1893163832000L);

        bean2.setCar_id(744811);
        bean2.setTitle("东风标致5082015款 2.0L 自动版");
        bean2.setCuxiaojia("14.07万");
        bean2.setColors("可选颜色[碳晶百]");
        bean2.setGuide_price("18.57万");
        bean2.setSave_money("已节省45000元");
        bean2.setComment1("数量有限，售完即止");
        bean2.setEnd_seconds(1593163832000L);

        bean3.setCar_id(744811);
        bean3.setTitle("东风标致5082015款 2.0L 自动版");
        bean3.setCuxiaojia("14.07万");
        bean3.setColors("可选颜色[碳晶百]");
        bean3.setGuide_price("18.57万");
        bean3.setSave_money("已节省45000元");
        bean3.setComment1("数量有限，售完即止");
        bean3.setEnd_seconds(1493175832000L);

        bean4.setCar_id(744811);
        bean4.setTitle("东风标致5082015款 2.0L 自动版");
        bean4.setCuxiaojia("14.07万");
        bean4.setColors("可选颜色[碳晶百]");
        bean4.setGuide_price("18.57万");
        bean4.setSave_money("已节省45000元");
        bean4.setComment1("数量有限，售完即止");
        bean4.setEnd_seconds(1493453832000L);

        bean5.setCar_id(744811);
        bean5.setTitle("东风标致5082015款 2.0L 自动版");
        bean5.setCuxiaojia("14.07万");
        bean5.setColors("可选颜色[碳晶百]");
        bean5.setGuide_price("18.57万");
        bean5.setSave_money("已节省45000元");
        bean5.setComment1("数量有限，售完即止");
        bean5.setEnd_seconds(1493165632000L);

        listbean.add(bean);
        listbean.add(bean1);
        listbean.add(bean2);
        listbean.add(bean3);
        listbean.add(bean4);
        listbean.add(bean5);
        String jsonArr = JSONArray.toJSONString(listbean);
        return jsonArr;
    }


}
