package com.etong.android.jxappfind.content;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.LocationClient;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.request_init.FrameConstant;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.jxappfind.BuildConfig;
import com.etong.android.jxappfind.R;
import com.etong.android.jxappfind.testData.FindJsonData;
import com.etong.android.jxappfind.utils.FindNoScrollListView;
import com.etong.android.jxappfours.order.FO_OrderActivity;
import com.lzy.okhttputils.OkHttpUtils;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.support.v4.SupportFragmentController;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @desc (发现fragment测试用例)
 * @createtime 2016/12/24 - 11:46
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, application = FrameEtongApplication.class)
public class FindMainFragmentTest {

    private LinearLayout weather;
    private LinearLayout oil_price;
    private Intent intent;
    private FindMainFragment fragment;
    private LocationClient mLocClient;
    private Application application;
    private FindNoScrollListView mListView;
    private LinearLayout mpager1;
    private TextView gasoline93;
    private TextView gasoline90;
    private TextView gasoline97;
    private TextView diesel;
    private TextView car_wash;
    private LinearLayout find_ll_weather;
    private TextView city;
    private TextView time;
    private ImageView daytime_img;
    private TextView find_tv_weather;
    private TextView find_tv_tair;
    private TextView time_limited_buy_more;
    private TextView sales_car_more;
    private TextView like_more;


    @Before
    public void setUp() throws Exception {
        application = RuntimeEnvironment.application;
        mLocClient = new LocationClient(application);
        OkHttpUtils.init(application);
        FindMainFragment f = new FindMainFragment();
        SupportFragmentController controller = SupportFragmentController.of(f);
        controller.create().attach().start();
        fragment = (FindMainFragment) controller.get();
        mListView = (FindNoScrollListView) fragment.getActivity().findViewById(R.id.find_lv_guess_like_list);
//        mXianShiGouView = new ViewPager(fragment.getActivity());
//        mCuXiaoCheView = new ViewPager(fragment.getActivity());
        mpager1 = (LinearLayout) fragment.getActivity().findViewById(R.id.find_ll_time_limited_buy_page);
        assertNotNull(mpager1);
//        LinearLayout mpager2 = findViewById(view, R.id.find_ll_sales_car_page,
//                LinearLayout.class);
        weather = (LinearLayout) fragment.getActivity().findViewById(R.id.find_weather);
        oil_price = (LinearLayout) fragment.getActivity().findViewById(R.id.find_oil_price);
        gasoline93 = (TextView) fragment.getActivity().findViewById(R.id.find_tv_gasoline93);
        gasoline90 = (TextView) fragment.getActivity().findViewById(R.id.find_tv_gasoline90);
        gasoline97 = (TextView) fragment.getActivity().findViewById(R.id.find_tv_gasoline97);
        diesel = (TextView) fragment.getActivity().findViewById(R.id.find_tv_diesel);

        find_ll_weather = (LinearLayout) fragment.getActivity().findViewById(R.id.find_ll_weather);
        city = (TextView) fragment.getActivity().findViewById(R.id.find_tv_city);
        time = (TextView) fragment.getActivity().findViewById(R.id.find_tv_time);
        daytime_img = (ImageView) fragment.getActivity().findViewById(R.id.find_iv_daytime);
        find_tv_weather = (TextView) fragment.getActivity().findViewById(R.id.find_tv_weather);
        find_tv_tair = (TextView) fragment.getActivity().findViewById(R.id.find_tv_tair);
        car_wash = (TextView) fragment.getActivity().findViewById(R.id.find_tv_car_wash);

        time_limited_buy_more = (TextView) fragment.getActivity().findViewById(R.id.find_txt_time_limited_buy_more);//限时购更多
        sales_car_more = (TextView) fragment.getActivity().findViewById(R.id.find_txt_sales_car_more);//促销车更多
        like_more = (TextView) fragment.getActivity().findViewById(R.id.find_txt_like_more);//猜你喜欢更多
        assertNotNull(weather);
        assertNotNull(oil_price);
    }

    //天气 油价是否显示
    @Test
    public void testInitShow() throws Exception {
        int isweather = weather.getVisibility();
        int isoil_price = oil_price.getVisibility();
        assertEquals(View.GONE, isweather);
        assertEquals(View.GONE, isoil_price);
    }

    //猜你喜欢数据
    @Test
    public void getFindRecommend() throws Exception {
        Map<String, String> map = new HashMap<>();
        HttpMethod method = new HttpMethod(FrameConstant.CURRENT_SERVICE + "item/queryRecommend.do", map);
        JSONObject jsonObject = JSON.parseObject(FindJsonData.jsonString_guess);
        assertTrue(mListView.getAdapter().isEmpty());
        fragment.getFindRecommend(method.put(jsonObject));
        assertFalse(mListView.getAdapter().isEmpty());
        for (int i = 0; i < mListView.getCount(); i++) {
            mListView.getAdapter().getView(i, null, null).performClick();
            ShadowActivity shadowActivity = Shadows.shadowOf(fragment.getActivity());
            Intent nextActivity = shadowActivity.getNextStartedActivity();
            ShadowIntent shadowIntent = Shadows.shadowOf(nextActivity);
            assertEquals(FO_OrderActivity.class, shadowIntent.getIntentClass());
        }
    }


    //得到油价的数据
    @Test
    public void getOil() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("prov", "湖南");
        map.put("apikey", "c96397a1f2c3993435722edb31d17852");
        HttpMethod method = new HttpMethod("http://apis.baidu.com/showapi_open_bus/oil_price/find", map);
        JSONObject jsonObject = JSON.parseObject(FindJsonData.jsonString_oil);
        fragment.setJsonData(method.put(jsonObject));
        assertEquals("6.04", gasoline90.getText().toString());
        assertEquals("6.45", gasoline93.getText().toString());
        assertEquals("6.86", gasoline97.getText().toString());
        assertEquals("6.15", diesel.getText().toString());
    }

    //生活指数
    @Test
    public void getWeatherDailyData() throws Exception {
//        jsonString_life
        Map<String, String> map = new HashMap<>();
        map.put("location", "changsha");
        map.put("apikey", "c96397a1f2c3993435722edb31d17852");
        HttpMethod method = new HttpMethod("http://apis.baidu.com/thinkpage/weather_api/daily", map);
        JSONObject jsonObject = JSON.parseObject(FindJsonData.jsonString_life);
        fragment.getWeatherDailyData(method.put(jsonObject));
        assertEquals("不宜", car_wash.getText().toString());
    }

    //天气情况
    @Test
    public void getWeatherSuggestionData() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("location", "changsha");
        map.put("days", "3");
        map.put("apikey", "c96397a1f2c3993435722edb31d17852");
        HttpMethod method = new HttpMethod("http://apis.baidu.com/thinkpage/weather_api/suggestion", map);
        JSONObject jsonObject = JSON.parseObject(FindJsonData.jsonString_weather);
        fragment.getWeatherSuggestionData(method.put(jsonObject));

//        Assert.assertEquals("长沙",city.getText().toString());//只有定位成功才能获取到城市
        Assert.assertEquals("2017-01-06",time.getText().toString());
//        Assert.assertEquals("长沙",time.getText().toString());
        Assert.assertEquals("小雨",find_tv_weather.getText().toString());
        Assert.assertEquals("6℃-11℃",find_tv_tair.getText().toString());
    }

    //点击事件
    @Test
    public void onClick() throws Exception{
        time_limited_buy_more.performClick();
        ShadowActivity shadowActivity= Shadows.shadowOf(fragment.getActivity());
        Intent nextActivity=shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent=Shadows.shadowOf(nextActivity);
        assertEquals(FindTimeLimitedBuyListActivity.class,shadowIntent.getIntentClass());

        sales_car_more.performClick();
        ShadowActivity shadowActivity1= Shadows.shadowOf(fragment.getActivity());
        Intent nextActivity1=shadowActivity1.getNextStartedActivity();
        ShadowIntent shadowIntent1=Shadows.shadowOf(nextActivity1);
        assertEquals(FindPromotionCarListActivity.class,shadowIntent1.getIntentClass());

        like_more.performClick();
        ShadowActivity shadowActivity2= Shadows.shadowOf(fragment.getActivity());
        Intent nextActivity2=shadowActivity2.getNextStartedActivity();
        ShadowIntent shadowIntent2=Shadows.shadowOf(nextActivity2);
        assertEquals(FindGuessYouLikeListActivity.class,shadowIntent2.getIntentClass());
    }
}