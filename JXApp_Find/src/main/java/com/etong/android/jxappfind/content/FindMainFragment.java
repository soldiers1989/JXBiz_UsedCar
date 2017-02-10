package com.etong.android.jxappfind.content;

import android.Manifest;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.permissions.PermissionsManager;
import com.etong.android.frame.permissions.PermissionsResultAction;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.frame.widget.CountDownView;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.nestedscrollview.IScrollView;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshScrollView;
import com.etong.android.jxappfind.R;
import com.etong.android.jxappfind.adapter.FindGridViewAdapter;
import com.etong.android.jxappfind.adapter.FindGuess_likeAdapter;
import com.etong.android.jxappfind.adapter.FindShowFunctionViewPagerAdapter;
import com.etong.android.jxappfind.adapter.FindVechileAdapter;
import com.etong.android.jxappfind.javabean.FindGuessBean;
import com.etong.android.jxappfind.javabean.FindImageBean;
import com.etong.android.jxappfind.javabean.FindVechileInfoBean;
import com.etong.android.jxappfind.javabean.FindWeatherDailyInfoJavabean;
import com.etong.android.jxappfind.javabean.Find_WeatherDailyJavaBean;
import com.etong.android.jxappfind.javabean.Find_WeatherSuggestionJavaBean;
import com.etong.android.jxappfind.javabean.Oil_Price_JavaBean;
import com.etong.android.jxappfind.utils.FindCountDownViews;
import com.etong.android.jxappfind.utils.FindNoScrollListView;
import com.etong.android.jxappfind.utils.FindProvider;
import com.etong.android.jxappfind.utils.Find_ImageIdByNameUtil;
import com.etong.android.jxappfind.view.FindJsonData;
import com.etong.android.jxappfours.order.FO_OrderActivity;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发现页面
 */
public class FindMainFragment extends BaseSubscriberFragment {
    private boolean isFirst = true;
    private TitleBar mTitleBar;
    LinearLayout ll_point;
    private ViewPager mViewPager;
    private LinearLayout group;
    private LinearLayout xianshigougroup;
    private List<View> mViewPagerGridList;
    //    private List<HeaderViewBean> mDatas = new ArrayList<>();
    private int currentItem = 0;//当前选中页
    private ImageView[] mPoint = null;//滑动图片的小圆点数组
    private ImageView[] mCuXiaoChePoint = null;//促销车
    private ImageView[] mXianShiGouPoint = null;//限时购
    private FindVechileAdapter mCuXiaoCheAdapter;
    private FindVechileAdapter mXianShiGouAdapter;
    private ViewPager mXianShiGouView;
    private ViewPager mCuXiaoCheView;
    private ImageProvider mImageProvider;
    private FindNoScrollListView mListView;
    private FindCountDownViews mFindCountDownViews;
    private List<FindImageBean> mImageList = new ArrayList<>();//总的图片list
    //    private List<FindVechileInfoBean> mXianshigouVechileList=new ArrayList<>();
    private List<FindGuessBean> mLikeList;
    private HttpPublisher mHttpPublisher;
    private FindProvider mFindProvider;
    private List<FindVechileInfoBean.ItemsBean> mTimeLimitedBuyList;
    private List<FindVechileInfoBean.ItemsBean> mPromotionList;
    private TextView time_limited_buy_more;
    private TextView sales_car_more;
    private TextView distance_time;
    private PullToRefreshScrollView sv_scroll;
    private long endTime;
    private LinearLayout distance_time_ll;
    private TextView like_more;
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    boolean isFirstLoc = true; // 是否首次定位
    public String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
    private LinearLayout oil_price;
    private LinearLayout weather;
    private TextView gasoline93;
    private TextView gasoline90;
    private TextView gasoline97;
    private TextView diesel;
    private String pointLocation;
    private TextView city;
    private TextView time;
    private ImageView daytime_img;
    private TextView car_wash;
    private String cityLocation;
    private Find_WeatherDailyJavaBean mWeatherDailyJavaBean;
    private Find_WeatherSuggestionJavaBean mWeatherSuggestionJavaBean;
    private TextView find_tv_weather;
    private TextView find_tv_tair;
    private LinearLayout find_ll_weather;
    private List<Find_WeatherSuggestionJavaBean> weatherList;
    private List<FindWeatherDailyInfoJavabean> mDailyInfoList;
    private boolean isIllegality = true;//第一次505错
    private View view;
    private boolean flag = false;
    private String provinceLocation;
    private boolean isResume = false;     //true 是onResume  false 不是onResume
    private boolean hidden = true;
    private FindGuess_likeAdapter mListAdapter;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.find_content_frg,
                container, false);
        //android6.0的权限判断
        if (Build.VERSION.SDK_INT >= 23) {
            if (!PermissionsManager.getInstance().hasAllPermissions(view.getContext(), permissions)) {
                // 未打开位置开关，可能导致定位失败或定位不准，提示用户或做相应处理
                initPermission();
            }
        }
        initDatas();
        initView(view);
        initLocation(view);
        setData();
        initViewPage(view);
        initTimeLimitedBuy();
        return view;
    }

    //顶部viewpager滑动数据处理
    public void initDatas() {
        JSONArray array = JSONArray.parseArray(FindJsonData.getJsonData());
        for (int i = 0; i < array.size(); i++) {
            FindImageBean mFindImageBean = JSON.toJavaObject(
                    array.getJSONObject(i), FindImageBean.class);
            mImageList.add(mFindImageBean);
        }
    }

    /**
     * 初始化view
     *
     * @param view
     */
    protected void initView(View view) {
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mFindProvider = FindProvider.getInstance();
        mFindProvider.initialize(mHttpPublisher);
        mImageProvider = ImageProvider.getInstance();
        mImageProvider.initialize(getActivity());
        mTitleBar = new TitleBar(view);
        mTitleBar.showNextButton(false);
        mTitleBar.showBackButton(false);
        mTitleBar.showBottomLin(false);
        mTitleBar.setTitle("津湘汽车");
        weather = (LinearLayout) view.findViewById(R.id.find_weather);
        oil_price = (LinearLayout) view.findViewById(R.id.find_oil_price);
        //今日油价
        gasoline93 = (TextView) view.findViewById(R.id.find_tv_gasoline93);
        gasoline90 = (TextView) view.findViewById(R.id.find_tv_gasoline90);
        gasoline97 = (TextView) view.findViewById(R.id.find_tv_gasoline97);
        diesel = (TextView) view.findViewById(R.id.find_tv_diesel);
        //天气
        find_ll_weather = (LinearLayout) view.findViewById(R.id.find_ll_weather);
        city = (TextView) view.findViewById(R.id.find_tv_city);
        time = (TextView) view.findViewById(R.id.find_tv_time);
        daytime_img = (ImageView) view.findViewById(R.id.find_iv_daytime);
        find_tv_weather = (TextView) view.findViewById(R.id.find_tv_weather);
        find_tv_tair = (TextView) view.findViewById(R.id.find_tv_tair);
        car_wash = (TextView) view.findViewById(R.id.find_tv_car_wash);
        addClickListener(view, R.id.find_ll_weather);
        //发现
        sv_scroll = (PullToRefreshScrollView) view.findViewById(R.id.find_sv_scroll);

        mViewPager = (ViewPager) view.findViewById(R.id.find_vp_pic_slide);
        ll_point = (LinearLayout) view.findViewById(R.id.find_ll_point);
        group = (LinearLayout) view.findViewById(R.id.find_ll_sales_car_point);
        xianshigougroup = (LinearLayout) view.findViewById(R.id.find_ll_time_limited_buy_point);
        mListView = (FindNoScrollListView) view.findViewById(R.id.find_lv_guess_like_list);

        distance_time_ll = (LinearLayout) view.findViewById(R.id.find_ll_distance_time);
        distance_time = (TextView) view.findViewById(R.id.find_txt_distance_time);
        mFindCountDownViews = (FindCountDownViews) view.findViewById(R.id.find_countdown_time);
        time_limited_buy_more = (TextView) view.findViewById(R.id.find_txt_time_limited_buy_more);//限时购更多
        sales_car_more = (TextView) view.findViewById(R.id.find_txt_sales_car_more);//促销车更多
        like_more = (TextView) view.findViewById(R.id.find_txt_like_more);//猜你喜欢更多

        addClickListener(view, R.id.find_txt_time_limited_buy_more);
        addClickListener(view, R.id.find_txt_sales_car_more);
        addClickListener(view, R.id.find_txt_like_more);
        //ScrollView 下拉刷新
        sv_scroll.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<IScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<IScrollView> refreshView) {
                isFirstLoc = true;
                if (!isIllegality) {
                    SDKInitializer.initialize(getEtongApplication().getApplicationContext());
                } else {
                    mLocClient.start();
                }
                //再次请求数据
                setData();
                initTimeLimitedBuy();
            }
        });
        //功能模块list（滑动）
        mViewPagerGridList = new ArrayList<>();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // 每页显示最大条目个数
        int pageSize = getResources().getInteger(R.integer.HomePageHeaderColumn) * 2;
        //页数
        int pageCount = (int) Math.ceil(mImageList.size() * 1.0 / pageSize);
        if (null == mPoint) {
            mPoint = new ImageView[pageCount];
        }
        if (pageCount <= 1) {
            ll_point.setVisibility(View.GONE);
        } else {
            ll_point.setVisibility(View.VISIBLE);
        }
        // 小圆点图标
        for (int i = 0; i < pageCount; i++) {
            if (null == mPoint[i]) {
                // 创建一个ImageView, 并设置宽高. 将该对象放入到数组中
                ImageView imageView = new ImageView(FindMainFragment.this.getActivity());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(5, 0, 5, 0);
                imageView.setLayoutParams(params);
                mPoint[i] = imageView;
                // 将小圆点放入到布局中
                ll_point.addView(mPoint[i]);
                if (i == 0) {
                    mPoint[i].setBackgroundResource(R.mipmap.find_click);
                } else {
                    mPoint[i].setBackgroundResource(R.mipmap.find_none_click);
                }
            }
        }
        //获取屏幕的宽度,单位px
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        //获取GridView中每个item的宽度 = 屏幕宽度 / GridView显示的列数
        int columnWidth = (int) Math.ceil((screenWidth) * 1.0 / (getResources().getInteger(R.integer.HomePageHeaderColumn)));
        for (int index = 0; index < pageCount; index++) {
            GridView grid = (GridView) inflater.inflate(R.layout.find_layout_gv, mViewPager, false);
            //设置GridView每个item的宽度
            grid.setColumnWidth(columnWidth);
            //设置GirdView的布局参数(宽和高，宽为包裹父容器，高 = columnWidth)
            AbsListView.LayoutParams param = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, columnWidth);
            grid.setLayoutParams(param);
            grid.setAdapter(new FindGridViewAdapter(getActivity(), mImageList, index));
            mViewPagerGridList.add(grid);
        }
        mViewPager.setAdapter(new FindShowFunctionViewPagerAdapter(mViewPagerGridList));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int arg0) {
                for (int i = 0; i < mPoint.length; i++) {
                    if (i == arg0) {
                        mPoint[i].setBackgroundResource(R.mipmap.find_click);
                    } else {
                        mPoint[i].setBackgroundResource(R.mipmap.find_none_click);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //限时购 倒计时设置
        mFindCountDownViews.setmTimeCallback(new FindCountDownViews.TimeCallback() {
            @Override
            public void getTimeStop() {
                if (distance_time.getText().toString().equals("距离开始")) {
                    distance_time.setText("距离结束");
                    mFindCountDownViews.startCountDown((endTime - System.currentTimeMillis()), 1000);
                } else if (endTime - System.currentTimeMillis() <= 0) {
                    distance_time.setVisibility(View.GONE);
                    mFindCountDownViews.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * @desc 定位
     * @createtime 2016/9/29 - 15:01
     * @author xiaoxue
     */
    public void initLocation(View view) {
        // 定位初始化
        mLocClient = new LocationClient(view.getContext());
        //注册定位监听函数
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
//        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
//        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocClient.setLocOption(option);
        mLocClient.start();
        mLocClient.requestLocation();
    }

    /**
     * @author xiaoxue
     * @desc 定位SDK监听函数(定位请求回调接口)
     * @createtime 2016/9/29 - 15:01
     */
    public class MyLocationListenner implements BDLocationListener {
        //BDLocation 回调的百度坐标类，内部封装了如经纬度、半径等属性信息
        @Override
        public void onReceiveLocation(BDLocation location) {
            L.d("================>", location.getLocType() + "");
//            L.d("================>所在省份", location.getProvince());
//            L.d("================>所在城市", location.getCity());
            // map view 销毁后不在处理新接收的位置
            if (location.getLocType() == 62) {
                if (isFirstLoc) {
                    FrameEtongApplication.getApplication().setIsLocation(false);
                    toastMsg("定位失败,请检查运营商网络或者wifi网络是否正常开启或检查应用管理中本app是否开启相关权限,尝试重新请求定位");
                    isFirstLoc = false;
                }
            }
            if (location.getLocType() == 505) {
                if (isIllegality) {
                    mLocClient.stop();
                    FrameEtongApplication.getApplication().setIsLocation(false);
                    L.d("", "初始化百度地图");
                    isIllegality = false;
                }
            }
            if (location.getLocType() == 63) {
                if (isFirstLoc) {
                    toastMsg("网络异常,没有成功向服务器发起请求，请确认当前测试手机网络是否通畅,尝试重新请求定位");
                    isFirstLoc = false;
                }
            }
            if (location.getLocType() == 167) {
                if (isFirstLoc) {
                    FrameEtongApplication.getApplication().setIsLocation(false);
                    toastMsg("服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位");
                    isFirstLoc = false;
                }
            }
            if (location.getLocType() == 161) {
                L.d("定位成功--------------");
                FrameEtongApplication.getApplication().setIsLocation(true);
                isIllegality = true;
                if (isFirstLoc) {
                    cityLocation = location.getCity();
                    provinceLocation = location.getProvince();
                    try {
                        //中文转拼音
                        pointLocation = PinyinHelper.convertToPinyinString(cityLocation.substring(0, location.getCity().length() - 1), "", PinyinFormat.WITHOUT_TONE);
                    } catch (PinyinException e) {
                        e.printStackTrace();
                    }
//                    //天气
//                    mFindProvider.getWeatherDaily(pointLocation);//生活指数
//                    mFindProvider.getWeatherSuggestion(pointLocation, 3);//天气情况
//                    //请求今日油价
//                    mFindProvider.getOilPrice(provinceLocation.substring(0, provinceLocation.length() - 1));
                    isFirstLoc = false;
                }
            }
            if (location == null) {
                toastMsg("定位失败，请检查手机网络或设置");
                return;
            }
        }
    }
    /**
     * @Title : initViewPage
     * @Description : TODO(初始化限时购  促销车的ViewPage)
     * @params
     */
    private void initViewPage(View view) {
        // 从布局文件中获取ViewPager父容器
        LinearLayout mpager1 = findViewById(view, R.id.find_ll_time_limited_buy_page,
                LinearLayout.class);
        LinearLayout mpager2 = findViewById(view, R.id.find_ll_sales_car_page,
                LinearLayout.class);
        // 创建ViewPager
        mXianShiGouView = new ViewPager(getActivity());
        mCuXiaoCheView = new ViewPager(getActivity());
        // 获取屏幕像素相关信息
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 根据屏幕信息设置ViewPager广告容器的宽高
        mXianShiGouView.setLayoutParams(new ViewGroup.LayoutParams(dm.widthPixels,
                dm.widthPixels * 6 / 13));
        mCuXiaoCheView.setLayoutParams(new ViewGroup.LayoutParams(dm.widthPixels,
                dm.widthPixels * 6 / 13));
        mXianShiGouView.setBackgroundResource(R.mipmap.find_activity_ic_no_data);
        mCuXiaoCheView.setBackgroundResource(R.mipmap.find_activity_ic_no_data);
        // 将ViewPager容器设置到布局文件父容器中
        mpager1.addView(mXianShiGouView);
        mpager2.addView(mCuXiaoCheView);
        mXianShiGouView.setOffscreenPageLimit(10);
        mCuXiaoCheView.setOffscreenPageLimit(10);
        //猜你喜欢adapter
        mListAdapter =new FindGuess_likeAdapter(getActivity());
        mListView.setAdapter(mListAdapter);
    }

    //请求猜你喜欢的数据
    public void setData() {
        mFindProvider.getFindRecommend();
    }
    //请求活动商品列表的接口(限时购  促销车)
    private void initTimeLimitedBuy() {
        mFindProvider.getFindActivityItemList();
    }

    //油价数据
    @Subscriber(tag = FrameHttpTag.OIL_PRICE)
    public void setJsonData(HttpMethod method) {
        // 只有当当前界面中请求了一个以上的接口、并且设置了缓存需要加入如下代码，
        // 因为当获取到的数据不为后台给定的规则时，会一直获取不到数据（如：!--STATUS OK-->// <html>// <head>  这种html页面
        if (null == method.data()) {
            if (mHttpPublisher != null) {
                // 需要在Provider中添加获取到HttpMethod的公开方法，然后根据公开的方法获取到唯一的请求缓存key，
                String cacheTagTemp = mHttpPublisher.getHttpTagFromMD5(mFindProvider.getOilPriceMethod());
//                L.d("获取到的tag--->", cacheTagTemp);
                if (!TextUtils.isEmpty(cacheTagTemp)) {
                    HttpPublisher.cleanCacheByTag(cacheTagTemp);        // 根据唯一的tag值来删除缓存
                    mFindProvider.getOilPrice(provinceLocation.substring(0, provinceLocation.length() - 1));     // 重新请求数据的方法，
                }
                return;
            }
        }
//        JSONObject data = JSON.parseObject(jsonData);//json string 转json object
        JSONObject body = method.data().getJSONObject("showapi_res_body");
        JSONArray list = body.getJSONArray("list");
        String errCode = "";
        // 如果返回的数据不为服务器返回的格式
        errCode = method.data().getString("errCode");
        String flag = method.data().getString("flag");
        if (!TextUtils.isEmpty(flag) && flag.equals(HttpPublisher.NETWORK_ERROR)) {
            oil_price.setVisibility(View.GONE);
            return;
        }
        if(!TextUtils.isEmpty(flag) && flag.equals(HttpPublisher.DATA_ERROR)){
            oil_price.setVisibility(View.GONE);
            return;
        }
        if(list.isEmpty()){
            oil_price.setVisibility(View.GONE);
            weather.setVisibility(View.GONE);
        }
        if (list.size() != 0) {
            initShow();
            for (int i = 0; i < list.size(); i++) {
                Oil_Price_JavaBean mOil_Price_JavaBean = JSON.toJavaObject(list.getJSONObject(i), Oil_Price_JavaBean.class);
                gasoline93.setText(mOil_Price_JavaBean.getP93());
                gasoline97.setText(mOil_Price_JavaBean.getP97());
                gasoline90.setText(mOil_Price_JavaBean.getP90());
                diesel.setText(mOil_Price_JavaBean.getP0());
            }
        }
    }
    //生活指数
    @Subscriber(tag = FrameHttpTag.WEATHER_DAILY)
    protected void getWeatherDailyData(HttpMethod method) {
        // 只有当当前界面中请求了一个以上的接口、并且设置了缓存需要加入如下代码，
        // 因为当获取到的数据不为后台给定的规则时，会一直获取不到数据（如：!--STATUS OK-->// <html>// <head>  这种html页面
        if (null == method.data()) {
            if (mHttpPublisher != null) {
                // 需要在Provider中添加获取到HttpMethod的公开方法，然后根据公开的方法获取到唯一的请求缓存key，
                String cacheTagTemp = mHttpPublisher.getHttpTagFromMD5(mFindProvider.getweatherDailyMethod());
//                L.d("获取到的tag--->", cacheTagTemp);
                if (!TextUtils.isEmpty(cacheTagTemp)) {
                    HttpPublisher.cleanCacheByTag(cacheTagTemp);        // 根据唯一的tag值来删除缓存
                    mFindProvider.getWeatherDaily(pointLocation);       // 重新请求数据的方法，
                }
                return;
            }
        }
        String errCode = "";
        // 如果返回的数据不为服务器返回的格式
        errCode = method.data().getString("errCode");
        String flag = method.data().getString("flag");
        if (!TextUtils.isEmpty(flag) && flag.equals(HttpPublisher.NETWORK_ERROR)) {
            weather.setVisibility(View.GONE);
            return;
        }
        String errNum=method.data().getString("errNum");
        if("300205".equals(errNum)){
            oil_price.setVisibility(View.GONE);
            weather.setVisibility(View.GONE);
            return;
        }
        initShow();
        JSONArray results = method.data().getJSONArray("results");
        JSONObject suggestion = results.getJSONObject(0).getJSONObject("suggestion");
        mWeatherDailyJavaBean = JSON.toJavaObject(suggestion, Find_WeatherDailyJavaBean.class);
        car_wash.setText(mWeatherDailyJavaBean.getCar_washing().getBrief());
//        wash_describe.setText((String) mWeatherDailyJavaBean.getCar_washing().getDetails());
        mDailyInfoList = new ArrayList<>();
        FindWeatherDailyInfoJavabean carwashing = new FindWeatherDailyInfoJavabean();
        carwashing.setTitle("洗车指数");
        carwashing.setBrief(mWeatherDailyJavaBean.getCar_washing().getBrief());
        carwashing.setDetails(mWeatherDailyJavaBean.getCar_washing().getDetails());
        FindWeatherDailyInfoJavabean dressing = new FindWeatherDailyInfoJavabean();
        dressing.setTitle("穿衣指数");
        dressing.setBrief(mWeatherDailyJavaBean.getDressing().getBrief());
        dressing.setDetails(mWeatherDailyJavaBean.getDressing().getDetails());
        FindWeatherDailyInfoJavabean flu = new FindWeatherDailyInfoJavabean();
        flu.setTitle("感冒指数");
        flu.setBrief(mWeatherDailyJavaBean.getFlu().getBrief());
        flu.setDetails(mWeatherDailyJavaBean.getFlu().getDetails());
        FindWeatherDailyInfoJavabean sport = new FindWeatherDailyInfoJavabean();
        sport.setTitle("运动指数");
        sport.setBrief(mWeatherDailyJavaBean.getSport().getBrief());
        sport.setDetails(mWeatherDailyJavaBean.getSport().getDetails());
        FindWeatherDailyInfoJavabean travel = new FindWeatherDailyInfoJavabean();
        travel.setTitle("旅游指数");
        travel.setBrief(mWeatherDailyJavaBean.getTravel().getBrief());
        travel.setDetails(mWeatherDailyJavaBean.getTravel().getDetails());
        FindWeatherDailyInfoJavabean uv = new FindWeatherDailyInfoJavabean();
        uv.setTitle("日晒指数");
        uv.setBrief(mWeatherDailyJavaBean.getUv().getBrief());
        uv.setDetails(mWeatherDailyJavaBean.getUv().getDetails());
        mDailyInfoList.add(carwashing);
        mDailyInfoList.add(dressing);
        mDailyInfoList.add(flu);
        mDailyInfoList.add(sport);
        mDailyInfoList.add(travel);
        mDailyInfoList.add(uv);
    }
    //天气
    @Subscriber(tag = FrameHttpTag.WEATHER_SUGGESTION)
    protected void getWeatherSuggestionData(HttpMethod method) {
        // 只有当当前界面中请求了一个以上的接口、并且设置了缓存需要加入如下代码，
        // 因为当获取到的数据不为后台给定的规则时，会一直获取不到数据（如：!--STATUS OK-->// <html>// <head>  这种html页面
        if (null == method.data()) {
            if (mHttpPublisher != null) {
                // 需要在Provider中添加获取到HttpMethod的公开方法，然后根据公开的方法获取到唯一的请求缓存key，
                String cacheTagTemp = mHttpPublisher.getHttpTagFromMD5(mFindProvider.getWeatherSuggestionMethod());
//                L.d("获取到的tag--->", cacheTagTemp);
                if (!TextUtils.isEmpty(cacheTagTemp)) {
                    HttpPublisher.cleanCacheByTag(cacheTagTemp);        // 根据唯一的tag值来删除缓存
                    mFindProvider.getWeatherSuggestion(pointLocation, 3);      // 重新请求数据的方法，
                }
                return;
            }
        }
        String errCode = "";
        // 如果返回的数据不为服务器返回的格式
        errCode = method.data().getString("errCode");
        String flag = method.data().getString("flag");
        if (!TextUtils.isEmpty(flag) && flag.equals(HttpPublisher.NETWORK_ERROR)) {
            weather.setVisibility(View.GONE);
            return;
        }
        String errNum=method.data().getString("errNum");
        if("300205".equals(errNum)){
            oil_price.setVisibility(View.GONE);
            weather.setVisibility(View.GONE);
            return;
        }
        initShow();
        weatherList = new ArrayList<>();
        JSONArray results = method.data().getJSONArray("results");
        JSONArray daily = results.getJSONObject(0).getJSONArray("daily");
        for (int i = 0; i < daily.size(); i++) {
            mWeatherSuggestionJavaBean = JSON.toJavaObject(daily.getJSONObject(i), Find_WeatherSuggestionJavaBean.class);
            weatherList.add(mWeatherSuggestionJavaBean);
        }
        // 判断获取到的值是否为空
        if (!TextUtils.isEmpty(cityLocation))
            city.setText(cityLocation.substring(0, cityLocation.length() - 1));
        time.setText(weatherList.get(0).getDate());
        find_tv_tair.setText(weatherList.get(0).getLow() + "℃" + "-" + weatherList.get(0).getHigh() + "℃");
        daytime_img.setImageResource(Find_ImageIdByNameUtil.getImageIdByName("find_weather" + weatherList.get(0).getCode_day()));
        find_tv_weather.setText(weatherList.get(0).getText_day());
    }

    //得到活动商品列表数据的处理
    @Subscriber(tag = FrameHttpTag.FIND_ACTIVITY_ITEM_LIST)
    protected void getFindActivityList(HttpMethod method) {
        String errCode = "";
        // 如果返回的数据不为服务器返回的格式
        errCode = method.data().getString("errCode");
        mTimeLimitedBuyList = new ArrayList<>();
        mPromotionList = new ArrayList<>();
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        if (flag.equals("0") && !TextUtils.isEmpty(flag)) {
            distance_time_ll.setVisibility(View.VISIBLE);
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                FindVechileInfoBean mFindVechileInfoBean = JSON.toJavaObject(jsonArr.getJSONObject(i), FindVechileInfoBean.class);
                endTime = mFindVechileInfoBean.getEnd_time();
                if (mFindVechileInfoBean.getType() == 1) {
                    for (int j = 0; j < mFindVechileInfoBean.getItems().size(); j++) {
                        mTimeLimitedBuyList.add(mFindVechileInfoBean.getItems().get(j));
                    }
                    L.d("____", System.currentTimeMillis() + "");
                    if ((mFindVechileInfoBean.getBegin_time() - System.currentTimeMillis()) <= 0) {
                        distance_time.setText("距离结束");
                        mFindCountDownViews.startCountDown((mFindVechileInfoBean.getEnd_time() - System.currentTimeMillis()), 1000);
                    } else {
                        mFindCountDownViews.startCountDown((mFindVechileInfoBean.getBegin_time() - System.currentTimeMillis()), 1000);
                    }
                    if (endTime - System.currentTimeMillis() <= 0) {
                        distance_time.setVisibility(View.GONE);
                        mFindCountDownViews.setVisibility(View.GONE);
                    }
                } else if (mFindVechileInfoBean.getType() == 2) {
                    for (int j = 0; j < mFindVechileInfoBean.getItems().size(); j++) {
                        mPromotionList.add(mFindVechileInfoBean.getItems().get(j));
                    }
                }
            }
            sv_scroll.onRefreshComplete();
            initTimeLimitedBuyData(mTimeLimitedBuyList);
            initPromotionData(mPromotionList);
        } else {
            sv_scroll.onRefreshComplete();
            return;
        }
    }

    //限时购
    private void initTimeLimitedBuyData(List<FindVechileInfoBean.ItemsBean> mTimeLimitedBuyList) {
        if (mTimeLimitedBuyList.size() % 2 != 0) {
            mTimeLimitedBuyList.add(new FindVechileInfoBean.ItemsBean());
        }
        if (null == mXianShiGouPoint
                || mXianShiGouPoint.length != (mTimeLimitedBuyList.size() / 2)) {
            mXianShiGouPoint = new ImageView[mTimeLimitedBuyList.size() / 2];
        }
        if (mTimeLimitedBuyList.size() / 2 <= 1) {
            xianshigougroup.setVisibility(View.GONE);
        } else {
            xianshigougroup.setVisibility(View.VISIBLE);
        }
        // 小圆点图标
        for (int i = 0; i < mTimeLimitedBuyList.size() / 2; i++) {
            if (null == mXianShiGouPoint[i]) {
                // 创建一个ImageView, 并设置宽高. 将该对象放入到数组中
                ImageView imageView = new ImageView(getActivity());
                // imageView.setLayoutParams(new LayoutParams(10, 10));
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                mXianShiGouPoint[i] = imageView;
                // 将小圆点放入到布局中
                xianshigougroup.addView(mXianShiGouPoint[i]);
                if (i == 0) {
                    mXianShiGouPoint[i].setBackgroundResource(R.mipmap.find_click);
                } else {
                    mXianShiGouPoint[i].setBackgroundResource(R.mipmap.find_none_click);
                }
            }
        }
        if (null != mXianShiGouAdapter) {
            mXianShiGouAdapter.notifyDataSetChanged();
        } else {
            mXianShiGouAdapter = new FindVechileAdapter(getActivity(), mTimeLimitedBuyList, 1);
            mXianShiGouView.setAdapter(mXianShiGouAdapter);
        }
        mXianShiGouView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                for (int i = 0; i < mXianShiGouPoint.length; i++) {
                    if (i == arg0) {
                        mXianShiGouPoint[i].setBackgroundResource(R.mipmap.find_click);
                    } else {
                        mXianShiGouPoint[i].setBackgroundResource(R.mipmap.find_none_click);
                    }
                }
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });
    }
    //促销车
    private void initPromotionData(List<FindVechileInfoBean.ItemsBean> mPromotionList) {
        if (mPromotionList.size() % 2 != 0) {
            mPromotionList.add(new FindVechileInfoBean.ItemsBean());
        }
        if (null == mCuXiaoChePoint || mCuXiaoChePoint.length != (mPromotionList.size() / 2)) {
            mCuXiaoChePoint = new ImageView[mPromotionList.size() / 2];
        }
        if (mPromotionList.size() / 2 <= 1) {
            group.setVisibility(View.GONE);
        } else {
            group.setVisibility(View.VISIBLE);
        }
        // 小圆点图标
        for (int i = 0; i < mPromotionList.size() / 2; i++) {
            if (null == mCuXiaoChePoint[i]) {
                // 创建一个ImageView, 并设置宽高. 将该对象放入到数组中
                ImageView imageView = new ImageView(FindMainFragment.this.getActivity());
                // imageView.setLayoutParams(new LayoutParams(10, 10));
                imageView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                mCuXiaoChePoint[i] = imageView;
                // 将小圆点放入到布局中
                group.addView(mCuXiaoChePoint[i]);
                if (i == 0) {
                    mCuXiaoChePoint[i].setBackgroundResource(R.mipmap.find_click);
                } else {
                    mCuXiaoChePoint[i].setBackgroundResource(R.mipmap.find_none_click);
                }
            }
        }
        if (null != mCuXiaoCheAdapter) {
            mCuXiaoCheAdapter.notifyDataSetChanged();
        } else {
            mCuXiaoCheAdapter = new FindVechileAdapter(getActivity(), mPromotionList, 2);
            mCuXiaoCheView.setAdapter(mCuXiaoCheAdapter);
        }
        mCuXiaoCheView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                for (int i = 0; i < mCuXiaoChePoint.length; i++) {
                    if (i == arg0) {
                        mCuXiaoChePoint[i].setBackgroundResource(R.mipmap.find_click);
                    } else {
                        mCuXiaoChePoint[i].setBackgroundResource(R.mipmap.find_none_click);
                    }
                }
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    //得到后台返回猜你喜欢的数据
    @Subscriber(tag = FrameHttpTag.FIND_RECOMMEND)
    protected void getFindRecommend(HttpMethod method) {
        mLikeList = new ArrayList<FindGuessBean>();
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
//        method.data().getString("errnoCode")
        sv_scroll.onRefreshComplete();
        // 如果返回的数据不为服务器返回的格式
        String errCode = method.data().getString("errCode");
        if (flag.equals("0") && !TextUtils.isEmpty(flag)) {
            mListAdapter.clear();
            if (!isIllegality) {
                mLocClient.start();
            }
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                FindGuessBean mFindGuessBean = JSON.toJavaObject(jsonArr.getJSONObject(i), FindGuessBean.class);
                mLikeList.add(mFindGuessBean);
            }
            mListAdapter.updateCarList(mLikeList);
        } else {
            toastMsg(msg);
        }
    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.find_txt_time_limited_buy_more) {
            Intent intent = new Intent(getActivity(), FindTimeLimitedBuyListActivity.class);
            Map map = new HashMap<>();
            map.put("mTimeLimitedBuyList", mTimeLimitedBuyList);
            final SerializableObject myMap = new SerializableObject();
            myMap.setObject(map);// 将map数据添加到封装的myMap中
            Bundle bundle = new Bundle();
            bundle.putSerializable("dataMap", myMap);
            bundle.putLong("finishTime", CountDownView.getmFinishTime());
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (view.getId() == R.id.find_txt_sales_car_more) {
            Intent intent = new Intent(getActivity(), FindPromotionCarListActivity.class);
            Map map = new HashMap<>();
            map.put("mPromotionList", mPromotionList);
            final SerializableObject myMap = new SerializableObject();
            myMap.setObject(map);// 将map数据添加到封装的myMap中
            Bundle bundle = new Bundle();
            bundle.putSerializable("dataMap", myMap);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (view.getId() == R.id.find_txt_like_more) {
            Intent intent = new Intent(getActivity(), FindGuessYouLikeListActivity.class);
            Map map = new HashMap<>();
            map.put("mLikeList", mLikeList);
            final SerializableObject myMap = new SerializableObject();
            myMap.setObject(map);// 将map数据添加到封装的myMap中
            Bundle bundle = new Bundle();
            bundle.putSerializable("dataMap", myMap);
            intent.putExtras(bundle);
            startActivity(intent);
        } else if (view.getId() == R.id.find_ll_weather) {
            if (null != weatherList && null != mDailyInfoList && !TextUtils.isEmpty(city.getText().toString())) {
                Intent intent = new Intent(getActivity(), FindWeatherActivity.class);
                Map map = new HashMap<>();
                map.put("weatherList", weatherList);
                map.put("mDailyInfoList", mDailyInfoList);
                final SerializableObject myMap = new SerializableObject();
                myMap.setObject(map);// 将map数据添加到封装的myMap中
                Bundle bundle = new Bundle();
                bundle.putSerializable("dataMap", myMap);
                intent.putExtras(bundle);
                intent.putExtra("city", city.getText().toString());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        this.hidden = hidden;
        if (!hidden && !isFirst) {
            if (!isResume) {//不是onResume时滑到顶部
                sv_scroll.getRefreshableView().scrollTo(0, 0);//listView不上滑的方法(获取PullToRefreshScrollView的ScrollView视图)
                mListView.setFocusable(false);
                mLocClient.start();
            }
            isResume = false;
        } else if (!hidden && isFirst) {
            if (null != mLocClient) {
                isFirstLoc = true;
            }
        } else if (hidden) {
            L.d("定位关闭", "");
            if (null != mLocClient) {
                mLocClient.stop();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mLocClient.stop();
    }
    @Override
    public void onResume() {
        super.onResume();
        isFirstLoc = true;
        if (!isFirst) {
            if (!hidden) {
                isResume = true;
            } else {
                isResume = false;
            }
        } else {
            isFirst = false;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocClient.stop();
    }

    //发现有网络时重新请求数据
    @Subscriber(tag = "NetWorkChanged")
    private void requestData(String str) {
        if (!isFirst && null != mTimeLimitedBuyList && null != mListAdapter && null != mPromotionList &&
                (mListAdapter.isEmpty() || mTimeLimitedBuyList.isEmpty() || mPromotionList.isEmpty())) {
            L.d("---------------------->", "发现重新刷新数据");
            setData();
            initTimeLimitedBuy();
        }
    }
    /**
     * @desc 从缓存中取 看是否显示天气 今日油价
     * @createtime 2016/9/29 - 14:56
     * @author xiaoxue
     */
    public void initShow() {
        if (FrameEtongApplication.getApplication().getIsShowOilPrice()) {
            oil_price.setVisibility(View.VISIBLE);
        } else {
            oil_price.setVisibility(View.GONE);
        }
        if (FrameEtongApplication.getApplication().getIsShowWeather()) {
            weather.setVisibility(View.VISIBLE);
        } else {
            weather.setVisibility(View.GONE);
        }
        view.requestLayout();//刷新布局
    }
    /**
     * @desc 对android6.0动态获取权限的处理
     * @createtime 2016/9/29 - 15:00
     * @author xiaoxue
     */
    public void initPermission() {
        PermissionsManager.getInstance()
                .requestPermissionsIfNecessaryForResult(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        new PermissionsResultAction() {
                            @Override
                            public void onGranted() {
                            }
                            @Override
                            public void onDenied(String permission) {
                                return;
                            }
                        });
    }
}
