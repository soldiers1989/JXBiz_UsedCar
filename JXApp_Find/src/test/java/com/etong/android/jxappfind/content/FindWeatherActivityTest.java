package com.etong.android.jxappfind.content;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.jxappfind.BuildConfig;
import com.etong.android.jxappfind.R;
import com.etong.android.jxappfind.javabean.FindWeatherDailyInfoJavabean;
import com.etong.android.jxappfind.javabean.Find_WeatherSuggestionJavaBean;
import com.etong.android.jxappfind.utils.FindNoScrollListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @desc (天气情况测试用例)
 * @createtime 2017/1/6 - 9:48
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21,constants = BuildConfig.class,application = FrameEtongApplication.class)
public class FindWeatherActivityTest {
    //天气
    public static String jsonString_weather="[\n" +
            "                {\n" +
            "                    \"date\": \"2017-01-06\",\n" +
            "                    \"text_day\": \"小雨\",\n" +
            "                    \"code_day\": \"13\",\n" +
            "                    \"text_night\": \"中雨\",\n" +
            "                    \"code_night\": \"14\",\n" +
            "                    \"high\": \"11\",\n" +
            "                    \"low\": \"6\",\n" +
            "                    \"precip\": \"\",\n" +
            "                    \"wind_direction\": \"西北\",\n" +
            "                    \"wind_direction_degree\": \"315\",\n" +
            "                    \"wind_speed\": \"15\",\n" +
            "                    \"wind_scale\": \"3\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"date\": \"2017-01-07\",\n" +
            "                    \"text_day\": \"小雨\",\n" +
            "                    \"code_day\": \"13\",\n" +
            "                    \"text_night\": \"阴\",\n" +
            "                    \"code_night\": \"9\",\n" +
            "                    \"high\": \"9\",\n" +
            "                    \"low\": \"5\",\n" +
            "                    \"precip\": \"\",\n" +
            "                    \"wind_direction\": \"西北\",\n" +
            "                    \"wind_direction_degree\": \"315\",\n" +
            "                    \"wind_speed\": \"15\",\n" +
            "                    \"wind_scale\": \"3\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"date\": \"2017-01-08\",\n" +
            "                    \"text_day\": \"多云\",\n" +
            "                    \"code_day\": \"4\",\n" +
            "                    \"text_night\": \"多云\",\n" +
            "                    \"code_night\": \"4\",\n" +
            "                    \"high\": \"12\",\n" +
            "                    \"low\": \"6\",\n" +
            "                    \"precip\": \"\",\n" +
            "                    \"wind_direction\": \"北\",\n" +
            "                    \"wind_direction_degree\": \"0\",\n" +
            "                    \"wind_speed\": \"10\",\n" +
            "                    \"wind_scale\": \"2\"\n" +
            "                }\n" +
            "            ]";
    //生活指数
    public static String jsonString_live="{\n" +
            "                \"car_washing\": {\n" +
            "                    \"brief\": \"不宜\",\n" +
            "                    \"details\": \"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。\"\n" +
            "                },\n" +
            "                \"dressing\": {\n" +
            "                    \"brief\": \"冷\",\n" +
            "                    \"details\": \"天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。\"\n" +
            "                },\n" +
            "                \"flu\": {\n" +
            "                    \"brief\": \"较易发\",\n" +
            "                    \"details\": \"天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。\"\n" +
            "                },\n" +
            "                \"sport\": {\n" +
            "                    \"brief\": \"较不宜\",\n" +
            "                    \"details\": \"有降水，且风力较强，推荐您在室内进行低强度运动；若坚持户外运动，请注意保暖并携带雨具。\"\n" +
            "                },\n" +
            "                \"travel\": {\n" +
            "                    \"brief\": \"一般\",\n" +
            "                    \"details\": \"天气稍凉，风稍大会加大些凉意，且预报有降水，旅游指数一般，外出旅游请注意防风保暖并携带雨具。\"\n" +
            "                },\n" +
            "                \"uv\": {\n" +
            "                    \"brief\": \"最弱\",\n" +
            "                    \"details\": \"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。\"\n" +
            "                }\n" +
            "            }";
    private ActivityController<FindWeatherActivity> controller;
    private FindWeatherActivity mActivity;
    private FindNoScrollListView life_listview;
    private LinearLayout ll_two_weather;
    private ImageView img_daytime1;
    private TextView daytime_weather1;
    private ImageView img_night1;
    private TextView night_weather1;
    private TextView tair1;
    private TextView time1;

    @Before
    public void setUp() throws Exception {
        //天气
        List<Find_WeatherSuggestionJavaBean> weatherlist=new ArrayList<>();
        JSONArray array = JSONArray.parseArray(jsonString_weather);
        for(int i=0;i<array.size();i++){
            Find_WeatherSuggestionJavaBean weatherJavaBean= JSON.toJavaObject(array.getJSONObject(i),Find_WeatherSuggestionJavaBean.class);
            weatherlist.add(weatherJavaBean);
        }
        //生活指数
        List<FindWeatherDailyInfoJavabean> liveList=new ArrayList<>();
        JSONObject live_array = JSONArray.parseObject(jsonString_live);
//        for(int j=0;j<live_array.size();j++){
            FindWeatherDailyInfoJavabean liveJavaBean= live_array.toJavaObject(FindWeatherDailyInfoJavabean.class);
            liveList.add(liveJavaBean);
//        }
        Intent intent =new Intent();
        intent.putExtra("city","长沙");
        Map map = new HashMap<>();
        map.put("weatherList", weatherlist);
        map.put("mDailyInfoList", liveList);
        final SerializableObject myMap = new SerializableObject();
        myMap.setObject(map);// 将map数据添加到封装的myMap中
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataMap", myMap);
        intent.putExtras(bundle);
        controller= Robolectric.buildActivity(FindWeatherActivity.class).withIntent(intent);
        controller.create().start();
        mActivity=controller.get();
        life_listview = (FindNoScrollListView)mActivity.findViewById(R.id.find_more_lv_life);
        ll_two_weather = (LinearLayout)mActivity.findViewById(R.id.find_more_ll_two_weather);
        img_daytime1 = (ImageView)mActivity.findViewById(R.id.find_more_img_daytime1);
        daytime_weather1 = (TextView)mActivity.findViewById(R.id.find_more_tv_daytime_weather1);
        img_night1 = (ImageView)mActivity.findViewById(R.id.find_more_img_night1);
        night_weather1 =(TextView)mActivity. findViewById(R.id.find_more_tv_night_weather1);
        tair1 = (TextView)mActivity.findViewById(R.id.find_more_tv_tair1);
        time1 = (TextView)mActivity.findViewById(R.id.find_more_tv_time1);
        assertNotNull(mActivity);
    }

    @Test
    public void getValue() throws Exception{
        assertFalse(life_listview.getAdapter().isEmpty());

        assertEquals("小雨",daytime_weather1.getText().toString());
        assertEquals("阴",night_weather1.getText().toString());
        assertEquals("9℃/5℃",tair1.getText().toString());
        assertEquals("2017-01-07",time1.getText().toString());

    }
}