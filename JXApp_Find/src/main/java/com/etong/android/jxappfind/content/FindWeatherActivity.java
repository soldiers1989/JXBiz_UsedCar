package com.etong.android.jxappfind.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfind.R;
import com.etong.android.jxappfind.javabean.FindGuessBean;
import com.etong.android.jxappfind.javabean.FindWeatherDailyInfoJavabean;
import com.etong.android.jxappfind.javabean.Find_WeatherDailyJavaBean;
import com.etong.android.jxappfind.javabean.Find_WeatherSuggestionJavaBean;
import com.etong.android.jxappfind.utils.FindNoScrollListView;
import com.etong.android.jxappfind.utils.Find_ImageIdByNameUtil;
import com.etong.android.jxappfind.view.Find_showWeatherTop;

import java.util.List;
import java.util.Map;

/**
 * @desc 天气页面
 * @createtime 2016/9/27 - 15:07
 * @Created by xiaoxue.
 */

public class FindWeatherActivity extends BaseSubscriberActivity{

    private TitleBar mTitleBar;
    private Find_WeatherDailyJavaBean mFind_WeatherDaily;
    private Find_WeatherSuggestionJavaBean mFind_WeatherSuggestion;
    private ImageView img_daytime;
    private TextView daytime_weather;
    private ImageView img_night;
    private TextView night_weather;

    private TextView time;
    private LinearLayout ll_first_weather;
    private LinearLayout ll_two_weather;
    private ImageView img_daytime1;
    private TextView daytime_weather1;
    private ImageView img_night1;
    private TextView night_weather1;

    private TextView time1;
    private LinearLayout ll_three_weather;
    private ImageView img_daytime2;
    private TextView daytime_weather2;
    private ImageView img_night2;
    private TextView night_weather2;

    private TextView time2;
    private TextView tair;
    private TextView tair1;
    private TextView tair2;
    private List<Find_WeatherSuggestionJavaBean> weatherList;
    private TextView city;
    private TextView first_time;
    private TextView rainfall;
    private ImageView first_img_daytime;
    private ImageView first_img_night;
    private TextView first_weather;
    private TextView daytime_first_weather;
    private TextView night_first_weather;
    private TextView windscale;
    private FindNoScrollListView life_listview;
    private ListAdapter<FindWeatherDailyInfoJavabean> mListAdapter;
    private List<FindWeatherDailyInfoJavabean> mDailyInfoList;
    private ScrollView scrollView;
    private TextView first_max_tair;
    private TextView first_min_tair;
    private TextView wind_speed;
    private String getcity;
    private Find_showWeatherTop top_dirst;
    private ImageView top_img_daytime;
    private TextView top_tv_daytime_weather;
    private TextView top_tv_tair;
    private TextView top_tv_time;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_more_weather);

        initView();
        scrollView.scrollTo(0, 0);//listView不上滑的方法(获取PullToRefreshScrollView的ScrollView视图)
        life_listview.setFocusable(false);

    }
    public void initView() {
        mTitleBar = new TitleBar(this);
        mTitleBar.showNextButton(false);
        mTitleBar.showBackButton(true);
        mTitleBar.showBottomLin(false);
        mTitleBar.setTitle("天气");

        //得到天气 生活指数的javabean
        Intent intent = getIntent();
        getcity = intent.getStringExtra("city");

        // 得到传过来的配置
        Bundle bundle = getIntent().getExtras();
        SerializableObject serializableMap = (SerializableObject) bundle
                .get("dataMap");
        Map map = (Map) serializableMap.getObject();
        weatherList = (List<Find_WeatherSuggestionJavaBean>) map.get("weatherList");
        mDailyInfoList = (List<FindWeatherDailyInfoJavabean>) map.get("mDailyInfoList");

        scrollView =findViewById(R.id.find_more_scrollView, ScrollView.class);

        //今天的天气状况  初始化控件
        ll_first_weather = findViewById(R.id.find_more_ll_first_weather, LinearLayout.class);

        img_daytime = findViewById(R.id.find_more_img_daytime, ImageView.class);
        daytime_weather = findViewById(R.id.find_more_tv_daytime_weather, TextView.class);
        img_night = findViewById(R.id.find_more_img_night, ImageView.class);
        night_weather = findViewById(R.id.find_more_tv_night_weather, TextView.class);
        tair = findViewById(R.id.find_more_tv_tair, TextView.class);
        time = findViewById(R.id.find_more_tv_time, TextView.class);


        img_daytime.setImageResource(Find_ImageIdByNameUtil.getImageIdByName("find_weather" + weatherList.get(0).getCode_day()));
        daytime_weather.setText(weatherList.get(0).getText_day());
        img_night.setImageResource(Find_ImageIdByNameUtil.getImageIdByName("find_weather" + weatherList.get(0).getCode_night()));
        night_weather.setText(weatherList.get(0).getText_night());
        tair.setText(weatherList.get(0).getHigh() + "℃" + "/" + weatherList.get(0).getLow() + "℃");
        time.setText(weatherList.get(0).getDate());


        //明天的天气状况  初始化控件
        ll_two_weather = findViewById(R.id.find_more_ll_two_weather, LinearLayout.class);
        img_daytime1 = findViewById(R.id.find_more_img_daytime1, ImageView.class);
        daytime_weather1 = findViewById(R.id.find_more_tv_daytime_weather1, TextView.class);
        img_night1 = findViewById(R.id.find_more_img_night1, ImageView.class);
        night_weather1 = findViewById(R.id.find_more_tv_night_weather1, TextView.class);
        tair1 = findViewById(R.id.find_more_tv_tair1, TextView.class);

        time1 = findViewById(R.id.find_more_tv_time1, TextView.class);


        img_daytime1.setImageResource(Find_ImageIdByNameUtil.getImageIdByName("find_weather" + weatherList.get(1).getCode_day()));
        daytime_weather1.setText(weatherList.get(1).getText_day());
        img_night1.setImageResource(Find_ImageIdByNameUtil.getImageIdByName("find_weather" + weatherList.get(1).getCode_night()));
        night_weather1.setText(weatherList.get(1).getText_night());
        tair1.setText(weatherList.get(1).getHigh() + "℃" + "/" + weatherList.get(1).getLow() + "℃");
        time1.setText(weatherList.get(1).getDate());

        //后天的天气状况  初始化控件
        ll_three_weather = findViewById(R.id.find_more_ll_three_weather, LinearLayout.class);
        img_daytime2 = findViewById(R.id.find_more_img_daytime2, ImageView.class);
        daytime_weather2 = findViewById(R.id.find_more_tv_daytime_weather2, TextView.class);
        img_night2 = findViewById(R.id.find_more_img_night2, ImageView.class);
        night_weather2 = findViewById(R.id.find_more_tv_night_weather2, TextView.class);
        tair2 = findViewById(R.id.find_more_tv_tair2, TextView.class);
        time2 = findViewById(R.id.find_more_tv_time2, TextView.class);

        img_daytime2.setImageResource(Find_ImageIdByNameUtil.getImageIdByName("find_weather" + weatherList.get(2).getCode_day()));
        daytime_weather2.setText(weatherList.get(2).getText_day());
        img_night2.setImageResource(Find_ImageIdByNameUtil.getImageIdByName("find_weather" + weatherList.get(2).getCode_night()));
        night_weather2.setText(weatherList.get(2).getText_night());
        tair2.setText(weatherList.get(2).getHigh() + "℃" + "/" + weatherList.get(2).getLow() + "℃");
        time2.setText(weatherList.get(2).getDate());

        city = findViewById(R.id.find_more_tv_city, TextView.class);
        first_time = findViewById(R.id.find_more_tv_first_time, TextView.class);
        rainfall = findViewById(R.id.find_more_tv_rainfall, TextView.class);
        first_img_daytime = findViewById(R.id.find_more_iv_daytime, ImageView.class);
        first_img_night = findViewById(R.id.find_more_iv_night, ImageView.class);
        daytime_first_weather = findViewById(R.id.find_more_tv_daytime_first_weather, TextView.class);
        night_first_weather = findViewById(R.id.find_more_tv_night_first_weather, TextView.class);
        windscale = findViewById(R.id.find_more_tv_windscale, TextView.class);
        first_max_tair =findViewById(R.id.find_more_tv_first_max_tair,TextView.class);
        first_min_tair =findViewById(R.id.find_more_tv_first_min_tair,TextView.class);
        wind_speed =findViewById(R.id.find_more_tv_wind_speed,TextView.class);

        city.setText(getcity);
        first_time.setText(weatherList.get(0).getDate());
        if(weatherList.get(0).getPrecip().equals("")){
            rainfall.setText("降水概率: "+0+"%");
        }else{
            rainfall.setText("降水概率: "+weatherList.get(0).getPrecip()+"%");
        }
        first_img_daytime.setImageResource(Find_ImageIdByNameUtil.getImageIdByName("find_weather" + weatherList.get(0).getCode_day()));
        first_img_night.setImageResource(Find_ImageIdByNameUtil.getImageIdByName("find_weather" + weatherList.get(0).getCode_night()));
        daytime_first_weather.setText(weatherList.get(0).getText_day());
        night_first_weather.setText(weatherList.get(0).getText_night());
        windscale.setText(weatherList.get(0).getWind_direction()+"风"+weatherList.get(0).getWind_scale()+"级");
        first_max_tair.setText("最高气温: "+weatherList.get(0).getHigh()+"℃");
        first_min_tair.setText("最低气温: "+weatherList.get(0).getLow()+"℃");
        wind_speed.setText("风速: "+weatherList.get(0).getWind_speed()+"km/h");



        life_listview = findViewById(R.id.find_more_lv_life, FindNoScrollListView.class);

        mListAdapter = new ListAdapter<FindWeatherDailyInfoJavabean>(this,
                R.layout.find_more_weather_list_item) {

            @Override
            protected void onPaint(View view, FindWeatherDailyInfoJavabean data, int position) {
                TextView list_title= (TextView) view.findViewById(R.id.find_more_list_title);
                TextView list_details= (TextView) view.findViewById(R.id.find_more_list_details);
                list_title.setText(data.getTitle()+": "+data.getBrief());
                list_details.setText(data.getDetails());
            }

        };
        mListAdapter.addAll(mDailyInfoList);
        life_listview.setAdapter(mListAdapter);
    }
}
