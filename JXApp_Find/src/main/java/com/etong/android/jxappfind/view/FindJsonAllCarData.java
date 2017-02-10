package com.etong.android.jxappfind.view;

import com.alibaba.fastjson.JSONArray;
import com.etong.android.jxappfind.javabean.FindAllCarBean;
import com.etong.android.jxappfind.javabean.FindPromotionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 促销车详情json数据
 * Created by Administrator on 2016/9/6.
 */
public class FindJsonAllCarData {

    public static String getJsonArray(){

        FindAllCarBean bean=new FindAllCarBean();
        FindAllCarBean bean1=new FindAllCarBean();
        FindAllCarBean bean2=new FindAllCarBean();
        FindAllCarBean bean3=new FindAllCarBean();
        List<FindAllCarBean> listbean=new ArrayList<>();
        bean.setId(744810);
        bean.setTitle("东风标致5082015款 2.0L 自动致逸版");
        bean.setImage("http://113.247.237.98:10002/data//car/20160323/17b4dfc4-6784-4613-8ab9-3bfcabd147fe.jpg");
        bean.setGuidPrice(18.56);


        bean1.setId(744811);
        bean1.setTitle("东风标致5082015款 2.0L");
        bean1.setImage("http://113.247.237.98:10002/data//car/20160323/17b4dfc4-6784-4613-8ab9-3bfcabd147fe.jpg");
        bean1.setGuidPrice(14.51);

        bean2.setId(744812);
        bean2.setTitle("东风标致5082015款 2.0L  手动版");
        bean2.setImage("http://113.247.237.98:10002/data//car/20160323/17b4dfc4-6784-4613-8ab9-3bfcabd147fe.jpg");
        bean2.setGuidPrice(16.24);


        bean3.setId(744813);
        bean3.setTitle("东风标致5082015款 手动版");
        bean3.setImage("http://113.247.237.98:10002/data//car/20160323/17b4dfc4-6784-4613-8ab9-3bfcabd147fe.jpg");
        bean3.setGuidPrice(17.25);

        listbean.add(bean);
        listbean.add(bean1);
        listbean.add(bean2);
        listbean.add(bean3);
        String jsonArr = JSONArray.toJSONString(listbean);
        return jsonArr;
    }


}
