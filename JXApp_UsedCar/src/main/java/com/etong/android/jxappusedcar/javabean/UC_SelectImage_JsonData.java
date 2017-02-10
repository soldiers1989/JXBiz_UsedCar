package com.etong.android.jxappusedcar.javabean;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 查看图片json数据
 * @createtime 2016/10/10 - 12:43
 * @Created by xiaoxue.
 */

public class UC_SelectImage_JsonData {

    public static String getJsonArray() {
        List<UC_SelectImageJavabean> list=new ArrayList<>();

        UC_SelectImageJavabean mUC_SelectImageJavabean = new UC_SelectImageJavabean();

        mUC_SelectImageJavabean.setTitle("长城M4 2012款 1.5手动豪华版保护局北京北京把县级伙伴们把，能尽快就和你开个康，困难了能看看叫你看么可能包括那个卡难看门口了农林发个八佰伴");
        mUC_SelectImageJavabean.setUrl("http://113.247.237.98:10002/data//car/20160323/17b4dfc4-6784-4613-8ab9-3bfcabd147fe.jpg");
        list.add(mUC_SelectImageJavabean);


        UC_SelectImageJavabean mUC_SelectImageJavabean1 = new UC_SelectImageJavabean();

        mUC_SelectImageJavabean1.setTitle("长城M4 2012款 ");
        mUC_SelectImageJavabean1.setUrl("http://113.247.237.98:10002/data//car/20160323/17b4dfc4-6784-4613-8ab9-3bfcabd147fe.jpg");
        list.add(mUC_SelectImageJavabean1);

        UC_SelectImageJavabean mUC_SelectImageJavabean2 = new UC_SelectImageJavabean();

        mUC_SelectImageJavabean2.setTitle("长城M4 ");
        mUC_SelectImageJavabean2.setUrl("http://113.247.237.98:10002/data//car/20160323/17b4dfc4-6784-4613-8ab9-3bfcabd147fe.jpg");
        list.add(mUC_SelectImageJavabean2);

        UC_SelectImageJavabean mUC_SelectImageJavabean3 = new UC_SelectImageJavabean();

        mUC_SelectImageJavabean3.setTitle("长城M4 ");
        mUC_SelectImageJavabean3.setUrl("");
        list.add(mUC_SelectImageJavabean3);



        String jsonArr = JSONArray.toJSONString(list);

        return jsonArr;
    }
}
