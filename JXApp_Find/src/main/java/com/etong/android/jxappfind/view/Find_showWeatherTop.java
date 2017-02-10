package com.etong.android.jxappfind.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.jxappfind.R;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/9/28 - 10:10
 * @Created by xiaoxue.
 */

public class Find_showWeatherTop extends ViewGroup{

    private Context mContext;
    private ImageView top_img_daytime;
    private TextView top_tv_daytime_weather;
    private ImageView img_night;
    private TextView night_weather;
    private TextView top_tv_tair;
    private TextView top_tv_time;

    public Find_showWeatherTop(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public Find_showWeatherTop(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();

//        LayoutInflater.from(mContext).inflate(R.layout.find_top_weather_item , this);
//        top_img_daytime =(ImageView)findViewById(R.id.find_top_img_daytime);
//        top_tv_daytime_weather = (TextView)findViewById(R.id.find_top_tv_daytime_weather);
//
//        img_night = (ImageView)findViewById(R.id.find_more_img_night);
//        night_weather = (TextView)findViewById(R.id.find_more_tv_night_weather);
//
//        top_tv_tair = (TextView)findViewById(R.id.find_top_tv_tair);
//        top_tv_time = (TextView)findViewById(R.id.find_top_tv_time);
    }

    public Find_showWeatherTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        initView();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    /**
     * @desc (加载布局,初始化操作)
     * @createtime 2016/9/28 - 10:14
     * @author xiaoxue
     */
    private void initView() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.find_top_weather_item , this);
        top_img_daytime =(ImageView)view.findViewById(R.id.find_top_img_daytime);
        top_tv_daytime_weather = (TextView)view.findViewById(R.id.find_top_tv_daytime_weather);

        img_night = (ImageView)view.findViewById(R.id.find_more_img_night);
        night_weather = (TextView)view.findViewById(R.id.find_more_tv_night_weather);

        top_tv_tair = (TextView)view.findViewById(R.id.find_top_tv_tair);
        top_tv_time = (TextView)view.findViewById(R.id.find_top_tv_time);

//        this.addView(view);
    }
}
