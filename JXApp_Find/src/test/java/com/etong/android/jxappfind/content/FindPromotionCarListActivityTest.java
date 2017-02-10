package com.etong.android.jxappfind.content;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.jxappfind.BuildConfig;
import com.etong.android.jxappfind.R;
import com.etong.android.jxappfind.javabean.FindVechileInfoBean;
import com.etong.android.jxappfours.order.FO_OrderActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @desc (促销车更多  测试用例)
 * @createtime 2017/1/6 - 11:33
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk=21,constants = BuildConfig.class,application = FrameEtongApplication.class)
public class FindPromotionCarListActivityTest {

    public static String jsonString_sales="[\n" +
            "    {\n" +
            "        \"original_price\": 18.98,\n" +
            "        \"picturesList\": [\n" +
            "            {\n" +
            "                \"size\": 100,\n" +
            "                \"create_time\": 1472107512000,\n" +
            "                \"name\": \"雷诺风朗\",\n" +
            "                \"id\": 1,\n" +
            "                \"type\": 1,\n" +
            "                \"url\": \"http://222.247.51.114:10002/data//dasheng/20160812/ed422977-c9ec-4a8b-8640-5d7cf38614e5.png\",\n" +
            "                \"status\": 1\n" +
            "            }\n" +
            "        ],\n" +
            "        \"create_time\": 1472106495000,\n" +
            "        \"description\": \"雷诺风朗\",\n" +
            "        \"recommend\": 1,\n" +
            "        \"update_time\": 1472106498000,\n" +
            "        \"update_user\": 1,\n" +
            "        \"price\": 12.68,\n" +
            "        \"name\": \"雷诺风朗\",\n" +
            "        \"id\": \"1\",\n" +
            "        \"create_user\": 1,\n" +
            "        \"carset_id\": 4032,\n" +
            "        \"status\": 1\n" +
            "    },\n" +
            "    {\n" +
            "        \"original_price\": 18.98,\n" +
            "        \"picturesList\": [\n" +
            "            {\n" +
            "                \"size\": 100,\n" +
            "                \"create_time\": 1472107512000,\n" +
            "                \"name\": \"雷诺风朗\",\n" +
            "                \"id\": 1,\n" +
            "                \"type\": 1,\n" +
            "                \"url\": \"http://222.247.51.114:10002/data//dasheng/20160812/ed422977-c9ec-4a8b-8640-5d7cf38614e5.png\",\n" +
            "                \"status\": 1\n" +
            "            }\n" +
            "        ],\n" +
            "        \"create_time\": 1472106495000,\n" +
            "        \"description\": \"雷诺风朗\",\n" +
            "        \"recommend\": 1,\n" +
            "        \"update_time\": 1472106498000,\n" +
            "        \"update_user\": 1,\n" +
            "        \"price\": 12.68,\n" +
            "        \"name\": \"雷诺风朗\",\n" +
            "        \"id\": \"1\",\n" +
            "        \"create_user\": 1,\n" +
            "        \"carset_id\": 4032,\n" +
            "        \"status\": 1\n" +
            "    },\n" +
            "    {\n" +
            "        \"original_price\": 18.98,\n" +
            "        \"picturesList\": [\n" +
            "            {\n" +
            "                \"size\": 100,\n" +
            "                \"create_time\": 1472107512000,\n" +
            "                \"name\": \"雷诺风朗\",\n" +
            "                \"id\": 1,\n" +
            "                \"type\": 1,\n" +
            "                \"url\": \"http://222.247.51.114:10002/data//dasheng/20160812/ed422977-c9ec-4a8b-8640-5d7cf38614e5.png\",\n" +
            "                \"status\": 1\n" +
            "            }\n" +
            "        ],\n" +
            "        \"create_time\": 1472106495000,\n" +
            "        \"description\": \"雷诺风朗\",\n" +
            "        \"recommend\": 1,\n" +
            "        \"update_time\": 1472106498000,\n" +
            "        \"update_user\": 1,\n" +
            "        \"price\": 12.68,\n" +
            "        \"name\": \"雷诺风朗\",\n" +
            "        \"id\": \"1\",\n" +
            "        \"create_user\": 1,\n" +
            "        \"carset_id\": 4032,\n" +
            "        \"status\": 1\n" +
            "    },\n" +
            "    {\n" +
            "        \"original_price\": 18.98,\n" +
            "        \"picturesList\": [\n" +
            "            {\n" +
            "                \"size\": 100,\n" +
            "                \"create_time\": 1472107512000,\n" +
            "                \"name\": \"雷诺风朗\",\n" +
            "                \"id\": 1,\n" +
            "                \"type\": 1,\n" +
            "                \"url\": \"http://222.247.51.114:10002/data//dasheng/20160812/ed422977-c9ec-4a8b-8640-5d7cf38614e5.png\",\n" +
            "                \"status\": 1\n" +
            "            }\n" +
            "        ],\n" +
            "        \"create_time\": 1472106495000,\n" +
            "        \"description\": \"雷诺风朗\",\n" +
            "        \"recommend\": 1,\n" +
            "        \"update_time\": 1472106498000,\n" +
            "        \"update_user\": 1,\n" +
            "        \"price\": 12.68,\n" +
            "        \"name\": \"雷诺风朗\",\n" +
            "        \"id\": \"1\",\n" +
            "        \"create_user\": 1,\n" +
            "        \"carset_id\": 4032,\n" +
            "        \"status\": 1\n" +
            "    }\n" +
            "]";
    private FindPromotionCarListActivity mActivty;
    private ListView mList;

    @Before
    public void setUp() throws Exception {
        JSONArray array=JSONArray.parseArray(jsonString_sales);
        List<FindVechileInfoBean.ItemsBean> list=new ArrayList<>();
        for(int i=0;i<array.size();i++){
            FindVechileInfoBean.ItemsBean bean= JSON.toJavaObject(array.getJSONObject(i),FindVechileInfoBean.ItemsBean.class);
            list.add(bean);
        }
        Intent intent = new Intent();
        Map map = new HashMap<>();
        map.put("mPromotionList", list);
        final SerializableObject myMap = new SerializableObject();
        myMap.setObject(map);// 将map数据添加到封装的myMap中
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataMap", myMap);
        intent.putExtras(bundle);

        ActivityController<FindPromotionCarListActivity> controller= Robolectric.buildActivity(FindPromotionCarListActivity.class).withIntent(intent);
        controller.create().start();
        mActivty=controller.get();
        mList = (ListView)mActivty.findViewById(R.id.vechile_xianshigou_list);
        assertNotNull(mActivty);
    }

    @Test
    public void getValue() throws Exception{
        assertFalse(mList.getAdapter().isEmpty());
        for(int i=0;i<mList.getCount();i++){
            mList.getAdapter().getView(i,null,null).performClick();
            ShadowActivity shadowActivity = Shadows.shadowOf(mActivty);
            Intent nextActivity = shadowActivity.getNextStartedActivity();
            ShadowIntent shadowIntent = Shadows.shadowOf(nextActivity);
            assertEquals(FO_OrderActivity.class, shadowIntent.getIntentClass());
        }
    }

}