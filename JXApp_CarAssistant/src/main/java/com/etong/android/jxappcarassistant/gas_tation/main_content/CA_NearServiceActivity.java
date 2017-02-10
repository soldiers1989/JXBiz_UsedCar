package com.etong.android.jxappcarassistant.gas_tation.main_content;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.mapapi.utils.route.BaiduMapRoutePlan;
import com.baidu.mapapi.utils.route.RouteParaOption;
import com.baidu.navisdk.adapter.BNOuterLogUtil;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.DefinedToast;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappcarassistant.R;
import com.etong.android.jxappcarassistant.gas_tation.baidu_map.CA_GasStationPoiOverlay;
import com.etong.android.jxappcarassistant.gas_tation.baidu_map.CA_MyPoiOverlay;
import com.etong.android.jxappcarassistant.gas_tation.baidu_map.overlayutil.PoiOverlay;
import com.etong.android.jxappcarassistant.gas_tation.javabean.Gas_StationJavabean;
import com.etong.android.jxappcarassistant.gas_tation.javabean.Oil_Price_JavaBean;
import com.etong.android.jxappcarassistant.gas_tation.utils.Oil_PriceProvider;

import org.simple.eventbus.Subscriber;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @desc 附近加油站界面、周边社区服务界面、附近停车场界面
 * @createtime 2016/9/26 0026--11:50
 * @Created by wukefan.
 */
public class CA_NearServiceActivity extends BaseSubscriberActivity implements OnGetPoiSearchResultListener {

    public static List<Activity> activityList = new LinkedList<Activity>();
    private static final String APP_FOLDER_NAME = "CA_NearServiceActivity";
    private String mSDCardPath = null;
    String authinfo = null;

    public static final String ROUTE_PLAN_NODE = "routePlanNode";

    private MapView mMapView;

    protected BaiduMap mBaiduMap = null;
    /**
     * 定位服务的客户端。
     * 宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
     */
    private LocationClient mLocClient;

    private LatLng point = null;
    public MyLocationListenner myListener = new MyLocationListenner();

    // 百度地图默经纬度
    public static final double DEFAULT_LATITUDE = 28.222178;
    public static final double DEFAULT_LONGITUDE = 112.902131;

    boolean isFirstLoc = true; // 是否首次定位
    private PoiSearch mPoiSearch;
    private int loadIndex = 0;
    private int radius = 5000;
    private TextView mGasStastionName;
    private TextView mGasStastionDis;
    private TextView mGasStastionAddress;
    //    private ViewGroup mGasStationLayout;
    private TextView mTxt93Gas;
    private TextView mTxt90Gas;
    private TextView mTxt97Gas;
    private TextView mTxt0Gas;

    private LatLng poi;
    private String gasStationName;
    private String keyString;
    private TitleBar mTitleBar;
    private String titleName;
    private LinearLayout mGasPriceLayout;
    private Oil_PriceProvider mOilPriceProvider;
    private HttpPublisher mHttpPublisher;
    private List<Gas_StationJavabean> list;
    //    private ViewGroup mRouteBtn;
    private PopupWindow mPopup;
    private PoiResult poiList;
    private Oil_Price_JavaBean mOil_Price_JavaBean;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {

        activityList.add(this);
        setContentView(R.layout.ca_activity_near_service);

        setSwipeBackEnable(false);

        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mOilPriceProvider = Oil_PriceProvider.getInstance();
        mOilPriceProvider.initialize(mHttpPublisher);

        Intent i = getIntent();
        titleName = i.getStringExtra("title");
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle(titleName);

        if (titleName.equals("附近加油站")) {
            keyString = "加油站";
        }
        if (titleName.equals("周边社区服务")) {
            keyString = "汽车服务维修保养";
        }
        if (titleName.equals("附近停车场")) {
            keyString = "停车场";
        }

        initView();
        initBaiduMap();
        initSearch();

        // 打开log开关
        BNOuterLogUtil.setLogSwitcher(true);
        if (initDirs()) {
            initNavi();
        }
    }

    /**
     * @desc 初始化视图
     */
    private void initView() {
        mMapView = (MapView) findViewById(R.id.ca_bmapView);//地图视图

//        mGasStationLayout = findViewById(R.id.detail_bottom_gas_station, ViewGroup.class);
//        mGasStastionName = findViewById(R.id.detail_station_name, TextView.class);
//        mGasStastionDis = findViewById(R.id.detail_station_distance, TextView.class);
//        mGasStastionAddress = findViewById(R.id.detail_station_address, TextView.class);
//
//        mTxt93Gas = findViewById(R.id.ca_detail_station_gasoline93, TextView.class);
//        mTxt90Gas = findViewById(R.id.ca_detail_station_gasoline97, TextView.class);
//        mTxt97Gas = findViewById(R.id.ca_detail_station_gasoline90, TextView.class);
//        mTxt0Gas = findViewById(R.id.ca_detail_station_diesel, TextView.class);
//
//        mGasPriceLayout = findViewById(R.id.detail_station_ll_gas_price, LinearLayout.class);
//        mGasPriceLayout.setVisibility(View.GONE);
//
//        addClickListener(R.id.detail_station_route);
        addClickListener(R.id.ca_main_dinwei);

    }

    /**
     * @desc 初始化地图
     */
    private void initBaiduMap() {
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//普通地图
        mBaiduMap.setTrafficEnabled(true); //开启交通图
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(18));//地图缩放级别为17
        mBaiduMap.setMyLocationEnabled(true);
        mLocClient = new LocationClient(getApplicationContext());
        mLocClient.registerLocationListener(myListener);
        initLocation();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mPoiSearch.destroy();
        if (null != mPopup && mPopup.isShowing()) {
            mPopup.dismiss();
        }
        mMapView = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        isFirstLoc = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onClick(View view) {
//        if (view.getId() == R.id.detail_station_route) {//路线,到这里
//            if (BaiduNaviManager.isNaviInited()) {
//
//            }
//        } else
        if (view.getId() == R.id.ca_main_dinwei) {//定位位置
            if (null != point) {
                L.d("+++++++", "lat" + point.latitude + "lon" + point.longitude);
                MapStatus mMapStatus = new MapStatus.Builder().target(point).zoom(15.0f).build();//地图状态
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));
//                    mBaiduMap.setMapStatus((MapStatusUpdateFactory.newLatLng(point)));
            }
            if (null != mPopup && mPopup.isShowing()) {
                mPopup.dismiss();
                if (null != list) {
                    setDefaultMarker(getAllMarker(list, null));
                }
                if (null != poiList) {
                    setDefaultMarker(getAllMarker(null, poiList));
                }
            }
        }
    }

    /**
     * @desc 显示信息的popupwindow
     */
    private void showInfoPopupWindow(final LatLng point, final String mgasStationName, String gasStationAdress, String gasStationDis, Gas_StationJavabean mGas_StationInfo) {
        gasStationName = mgasStationName;
        poi = point;
        View view = LayoutInflater.from(this).inflate(R.layout.detail_bottom_gas_station, null);


        mGasStastionName = (TextView) view.findViewById(R.id.detail_station_name);
        mGasStastionDis = (TextView) view.findViewById(R.id.detail_station_distance);
        mGasStastionAddress = (TextView) view.findViewById(R.id.detail_station_address);

        mTxt93Gas = (TextView) view.findViewById(R.id.ca_detail_station_gasoline93);
        mTxt90Gas = (TextView) view.findViewById(R.id.ca_detail_station_gasoline90);
        mTxt97Gas = (TextView) view.findViewById(R.id.ca_detail_station_gasoline97);
        mTxt0Gas = (TextView) view.findViewById(R.id.ca_detail_station_diesel);


        mGasPriceLayout = (LinearLayout) view.findViewById(R.id.detail_station_ll_gas_price);

        ViewGroup mRouteBtn = (ViewGroup) view.findViewById(R.id.detail_station_route);

        mGasStastionName.setText(gasStationName);
        mGasStastionDis.setText(gasStationDis);
        mGasStastionAddress.setText(gasStationAdress);

        if (titleName.equals("附近加油站")) {
            if (null != mOil_Price_JavaBean) {
                mTxt93Gas.setText(mOil_Price_JavaBean.getP93());
                mTxt90Gas.setText(mOil_Price_JavaBean.getP90());
                mTxt97Gas.setText(mOil_Price_JavaBean.getP97());
                mTxt0Gas.setText(mOil_Price_JavaBean.getP0());
                mGasPriceLayout.setVisibility(View.VISIBLE);
            }
        }

        mRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopup.dismiss();
//                getBaiduMap();
                if (BaiduNaviManager.isNaviInited()) {
                    routeplanToNavi(gasStationName, poi);
                }
            }
        });

        mPopup = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        // 设置此参数获得焦点，否则无法点击
//        mPopup.setFocusable(true);
//        mPopup.setOutsideTouchable(false);   //设置外部点击关闭ppw窗口
//        mPopup.setBackgroundDrawable(new BitmapDrawable());//注意这里如果不设置，下面的setOutsideTouchable(true);允许点击外部消失会失效

        //显示PopupWindow
        View rootview = LayoutInflater.from(CA_NearServiceActivity.this).inflate(R.layout.ca_activity_near_service, null);
        mPopup.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    /**
     * @desc 调用百度地图
     */
    private void getBaiduMap() {
        // 构建 route搜索参数以及策略，起终点也可以用name构造
        RouteParaOption para = new RouteParaOption()
                .startPoint(point).startName("我的位置")
                .endPoint(poi).endName(gasStationName);
        try {
            BaiduMapRoutePlan.openBaiduMapDrivingRoute(para, CA_NearServiceActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //结束调启功能时调用finish方法以释放相关资源
        BaiduMapRoutePlan.finish(CA_NearServiceActivity.this);
    }

    /**
     * @desc 显示加油站信息
     */
    @Subscriber(tag = "GasStationInfo")
    private void showGasStationInfo(Gas_StationJavabean mGas_StationInfo) {

        String distanceString;
        String dis = mGas_StationInfo.getDistance() + "";
        double distance = Double.valueOf(dis.substring(0, dis.indexOf(".")));
        if (distance >= 1000) {
            distance = distance / 1000.0;
            DecimalFormat df = new DecimalFormat("0.0");
            distanceString = df.format(distance) + "公里";
        } else {
            distanceString = (distance + "").substring(0, (distance + "").indexOf(".")) + "米";
        }


        LatLng poi = new LatLng(Double.valueOf(mGas_StationInfo.getLat()), Double.valueOf(mGas_StationInfo.getLon()));
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(poi));//以这个点为中心设置地图状态
//        this.poi = poi;
//        this.gasStationName = mGas_StationInfo.getName();
//        mGasStationLayout.setVisibility(View.VISIBLE);
//        mGasPriceLayout.setVisibility(View.VISIBLE);
        if (null != mPopup && mPopup.isShowing()) {

            this.gasStationName = mGas_StationInfo.getName();
            this.poi = poi;
            mGasStastionName.setText(mGas_StationInfo.getName());
            mGasStastionDis.setText(distanceString);
            mGasStastionAddress.setText(mGas_StationInfo.getAddress());

        } else {
            showInfoPopupWindow(poi, mGas_StationInfo.getName(), mGas_StationInfo.getAddress(), distanceString, mGas_StationInfo);
        }
    }


    /**
     * @desc 显示周边社区服务信息或附近停车场信息
     */
//    private void showOtherSearchInfo(final LatLng poi, final String gasStationName, String gasStationAdress, String gasStationDis) {
//        mGasStastionName.setText(gasStationName);
//        mGasStastionDis.setText(gasStationDis);
//        mGasStastionAddress.setText(gasStationAdress);
//        this.poi = poi;
//        this.gasStationName = gasStationName;
//        mGasStationLayout.setVisibility(View.VISIBLE);
//    }

    /**
     * @desc 设置Mark标记
     */
//    public void setMapOverlay(LatLng point) {
//        mBaiduMap.clear();
//        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_position);
//        //构建MarkerOption，用于在地图上添加Marker
//        MarkerOptions ooD = new MarkerOptions().position(point).icon(bitmap)
//                .zIndex(0).period(10);
//        // 生长动画
//        ooD.animateType(MarkerOptions.MarkerAnimateType.grow);
//        //在地图上添加Marker，并显示
//        Marker mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));
//    }


    /**
     * @desc 获取今日油价信息
     */
    @Subscriber(tag = FrameHttpTag.OIL_PRICE)
    public void setJsonData(HttpMethod method) {
//        JSONObject data = JSON.parseObject(jsonData);//json string 转json object
        JSONObject body = method.data().getJSONObject("showapi_res_body");
        JSONArray list = body.getJSONArray("list");
        if (list.size() != 0) {
            mOil_Price_JavaBean = JSON.toJavaObject(list.getJSONObject(0), Oil_Price_JavaBean.class);

        }
        if (null != mPopup && mPopup.isShowing()) {
            mTxt93Gas.setText(mOil_Price_JavaBean.getP93());
            mTxt90Gas.setText(mOil_Price_JavaBean.getP90());
            mTxt97Gas.setText(mOil_Price_JavaBean.getP97());
            mTxt0Gas.setText(mOil_Price_JavaBean.getP0());
            mGasPriceLayout.setVisibility(View.VISIBLE);
        }
    }


    /**
     * @desc 获取附近加油站信息
     */
    @Subscriber(tag = FrameHttpTag.OIL_LOCAL)
    protected void getOilLocal(HttpMethod method) {
        list = new ArrayList<>();
        JSONObject result = method.data().getJSONObject("result");
        JSONArray data = result.getJSONArray("data");
        for (int i = 0; i < data.size(); i++) {
            Gas_StationJavabean mGas_StationJavabean = JSON.toJavaObject(data.getJSONObject(i), Gas_StationJavabean.class);
            list.add(mGas_StationJavabean);
        }
        Collections.sort(list, new MyOilLocalComp());

        mBaiduMap.clear();
        CA_GasStationPoiOverlay overlay = new CA_GasStationPoiOverlay(mBaiduMap, CA_NearServiceActivity.this);
        mBaiduMap.setOnMarkerClickListener(overlay);//设置地图单击事件监听者
        overlay.setData(list);//设置数据
        overlay.addToMap();//将所有Overlay 添加到地图上
        overlay.zoomToSpan();//缩放地图，使所有Overlay都在合适的视野内(该方法只对Marker类型的overlay有效)

        setDefaultSelect(getAllMarker(list, null));

        mEventBus.post(list.get(0), "GasStationInfo");
    }

    //距离由近到远排序
    public class MyOilLocalComp implements Comparator<Gas_StationJavabean> {

        @Override

        public int compare(Gas_StationJavabean o1, Gas_StationJavabean o2) {
            return Double.valueOf(o1.getDistance()).compareTo(Double.valueOf(o2.getDistance()));
        }
    }

    //设置，默认选择最近的一个
    public void setDefaultSelect(List<Marker> markerList) {
        for (int i = 0; i < markerList.size(); i++) {
            if (i == 0) {
                markerList.get(i).setIcon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark_blue.png"));
                markerList.get(i).setToTop();//设置在图层最上面
            } else {
                if (i + 1 < 10) {
                    markerList.get(i).setIcon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark" + (i + 1) + ".png"));
                } else {
                    markerList.get(i).setIcon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark_circle.png"));
                }
            }
        }
    }

    //设置默认marker图标
    public void setDefaultMarker(List<Marker> markerList) {
        for (int i = 0; i < markerList.size(); i++) {
            if (i + 1 < 10) {
                markerList.get(i).setIcon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark" + (i + 1) + ".png"));
            } else {
                markerList.get(i).setIcon(BitmapDescriptorFactory.fromAssetWithDpi("Icon_mark_circle.png"));
            }
        }
    }

    /**
     * @desc 获取所有marker标记
     */
    public List<Marker> getAllMarker(List<Gas_StationJavabean> gasStationList, PoiResult poiResult) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if (null != gasStationList) {
            for (int i = 0; i < gasStationList.size(); i++) {
                LatLng poi = new LatLng(Double.valueOf(gasStationList.get(i).getLat()), Double.valueOf(gasStationList.get(i).getLon()));
                builder = builder.include(poi);
            }
        }
        if (null != poiResult) {
            for (int i = 0; i < poiResult.getAllPoi().size(); i++) {
                builder = builder.include(poiResult.getAllPoi().get(i).location);
            }
        }

        LatLngBounds latlngBounds = builder.build();
        return mBaiduMap.getMarkersInBounds(latlngBounds);
    }
//==================================================================定位============================================================================


    /**
     * @desc 初始化定位参数
     */
    private void initLocation() {

        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
//        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocClient.setLocOption(option);
        mLocClient.start();
        mLocClient.requestLocation();
    }


    /**
     * 定位SDK监听函数(定位请求回调接口)
     */
    public class MyLocationListenner implements BDLocationListener {

        //BDLocation 回调的百度坐标类，内部封装了如经纬度、半径等属性信息
        @Override
        public void onReceiveLocation(BDLocation location) {

            L.d("~~~~~~~~~", location.getLocType() + "");//错误码信息


            if (location.getLocType() == 62) {
                if (isFirstLoc) {
                    DefinedToast.showToast(CA_NearServiceActivity.this, "定位失败,请检查运营商网络或者wifi网络是否正常开启或检查应用管理中本app是否开启相关权限,尝试重新请求定位", 1);
                    isFirstLoc = false;
                }
                return;
            }
            if (location.getLocType() == 63) {
                if (isFirstLoc) {
//                    CToast.toastMessage("网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位", 1);
                    DefinedToast.showToast(CA_NearServiceActivity.this, "网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位", 1);
                    isFirstLoc = false;
                }
                return;
            }
            if (location.getLocType() == 167) {
                if (isFirstLoc) {
//                    CToast.toastMessage("服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位", 1);
                    DefinedToast.showToast(CA_NearServiceActivity.this, "服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位", 1);
                    isFirstLoc = false;
                }
                return;
            }

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
//                CToast.toastMessage("定位失败，请检查手机网络或设置！", 1);
                DefinedToast.showToast(CA_NearServiceActivity.this, "定位失败，请检查手机网络或设置！", 1);
                return;
            }
            if (location.getLocType() == 161) {
                // 构造定位数据
                //MyLocationData.Builder  定位数据建造器
                //accuracy 定位精度
                //BDLocation getRadius() 获取定位精度,默认值0.0f
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        //direction GPS定位时方向角度
                        //latitude 百度纬度坐标
                        //longitude 百度经度坐标
                        .direction(100).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                // 设置定位数据
                mBaiduMap.setMyLocationData(locData);
                if (isFirstLoc) {
                    isFirstLoc = false;
                    //LatLng 地理坐标基本数据结构(经纬度)
                    LatLng ll = new LatLng(location.getLatitude(),
                            location.getLongitude());
                    point = ll;
                    //MapStatus.Builder 地图状态构造器
                    //target  地图操作的中心点
                    //zoom 地图缩放级别 3~21
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(18.0f);
//                //以动画方式更新地图状态，动画耗时 300 ms
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    MapStatus mMapStatus = new MapStatus.Builder().target(ll).zoom(18.0f).build();//地图状态
                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);//图状态将要发生的变化
                    mBaiduMap.setMapStatus(mMapStatusUpdate);
//                PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption().keyword(keyString)
//                        .sortType(PoiSortType.distance_from_near_to_far).location(ll)
//                        .radius(radius).pageNum(loadIndex);
                    if (titleName.equals("附近加油站")) {
                        mOilPriceProvider.getGas_station(ll.longitude, ll.latitude, radius);
                        mOilPriceProvider.getOilPrice(location.getProvince().substring(0, location.getProvince().length() - 1));
                    } else {
                        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption().keyword(keyString)
                                .sortType(PoiSortType.distance_from_near_to_far).location(ll)
                                .radius(radius).pageCapacity(50).pageNum(loadIndex);
                        mPoiSearch.searchNearby(nearbySearchOption);//周边检索
                    }
                }
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

//==================================================================搜索周边============================================================================

    /**
     * @desc 初始化搜索模块，注册搜索事件监听
     */
    private void initSearch() {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
    }

    /**
     * 获取POI搜索结果，包括searchInCity，searchNearby，searchInBound返回的搜索结果
     *
     * @param poiResult
     */
    @Override
    public void onGetPoiResult(PoiResult poiResult) {

        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {//没找到结果
//            CToast.toastMessage("未找到结果", 1);
            DefinedToast.showToast(CA_NearServiceActivity.this, "未找到结果", 1);
            return;
        }

        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {//结果没问题

            poiList = poiResult;

            mBaiduMap.clear();
//            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            CA_MyPoiOverlay overlay = new CA_MyPoiOverlay(mBaiduMap, CA_NearServiceActivity.this, mPoiSearch);
            mBaiduMap.setOnMarkerClickListener(overlay);//设置地图单击事件监听者
            overlay.setData(poiResult);//设置POI数据
            overlay.addToMap();//将所有Overlay 添加到地图上
            overlay.zoomToSpan();//缩放地图，使所有Overlay都在合适的视野内(该方法只对Marker类型的overlay有效)

            setDefaultSelect(getAllMarker(null, poiResult));
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poiResult.getAllPoi().get(0).uid));//搜索第一个详细信息
            return;
        }

        if (poiResult.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            // 当关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : poiResult.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
//            CToast.toastMessage(strInfo, 1);
            DefinedToast.showToast(CA_NearServiceActivity.this, strInfo, 1);
        }
    }

    /**
     * 获取POI详情搜索结果，得到searchPoiDetail返回的搜索结果
     *
     * @param poiDetailResult
     */
    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult.error != SearchResult.ERRORNO.NO_ERROR) {
            toastMsg("抱歉，未找到结果");
        } else {
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(poiDetailResult.getLocation()));//以这个点为中心设置地图状态
            String distanceString;
            String dis = DistanceUtil.getDistance(point, poiDetailResult.getLocation()) + "";
            double distance = Double.valueOf(dis.substring(0, dis.indexOf(".")));
            if (distance >= 1000) {
                distance = distance / 1000.0;
                DecimalFormat df = new DecimalFormat("0.0");
                distanceString = df.format(distance) + "公里";
            } else {
                distanceString = (distance + "").substring(0, (distance + "").indexOf(".")) + "米";
            }

            if (null != mPopup && mPopup.isShowing()) {

                this.gasStationName = poiDetailResult.getName();
                this.poi = poiDetailResult.getLocation();
                mGasStastionName.setText(poiDetailResult.getName());
                mGasStastionDis.setText(distanceString);
                mGasStastionAddress.setText(poiDetailResult.getAddress());
            } else {
                showInfoPopupWindow(poiDetailResult.getLocation(), poiDetailResult.getName(), poiDetailResult.getAddress(), distanceString, null);//周边社区服务或附近停车场
            }
        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));//POI 详情检索（欲检索的poi的uid）
            // }
            return true;
        }
    }

//==================================================================导航============================================================================

    /**
     * @desc 初始化导航
     */
    private void initNavi() {
        /**  init参数:
         *  activity - 建议是应用的主Activity
         *  sdcardRootPath - 系统SD卡根目录路径
         *  appFolderName - 应用在SD卡中的目录名
         *  naviInitListener - 百度导航初始化监听器
         *  ttsCallback - 外部TTS能力回调接口，若使用百度内置TTS能力，传入null即可
         *  ttsHandler - 异步获取百度内部TTS播报状态
         *  ttsStateListener - 同步获取百度内部TTS播报状态
         **/
        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME,
                new BaiduNaviManager.NaviInitListener() {

                    /** 授权校验结果
                     *  参数:
                     *  status - 0 表示成功，其他表示失败
                     *  msg - 具体授权校验失败信息
                     **/
                    @Override
                    public void onAuthResult(int status, String msg) {
                        if (0 == status) {
                            authinfo = "key校验成功!";
                        } else {
                            authinfo = "key校验失败, " + msg;
                        }
                        CA_NearServiceActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                L.d("initNavi", "authinfo");
//                                CToast.toastMessage("authinfo",1);
                            }
                        });
                    }

                    //百度导航初始化成功
                    public void initSuccess() {
//                        toastMsg("百度导航引擎初始化成功");
                        L.d("initSuccess", "百度导航引擎初始化成功");
                        initSetting();
                    }

                    //百度导航初始化开始
                    public void initStart() {
//                        toastMsg("百度导航引擎初始化开始");
                        L.d("initStart", "百度导航引擎初始化开始");
                    }

                    //百度导航初始化失败
                    public void initFailed() {
//                        toastMsg("百度导航引擎初始化失败");
                        L.d("initFailed", "百度导航引擎初始化失败");
                    }
                }, null, ttsHandler, ttsPlayStateListener);
    }


    /**
     * @desc 规划路线，发起算路
     */
    private void routeplanToNavi(String pointName, LatLng poi) {

        BNRoutePlanNode sNode = null;//算路节点
        BNRoutePlanNode eNode = null;

        /** 参数:
         *  longitude - 经度
         *  latitude - 纬度
         *  name - 算路节点名
         *  description - 算路节点地址描述
         *  coType - 坐标类型，参考 CoordinateType
         **/
        sNode = new BNRoutePlanNode(point.longitude, point.latitude, "我的位置", null, BNRoutePlanNode.CoordinateType.BD09LL);
        eNode = new BNRoutePlanNode(poi.longitude, poi.latitude, pointName, null, BNRoutePlanNode.CoordinateType.BD09LL);

        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);

            /** 发起算路操作并在算路成功后通过回调监听器进入导航过程.
             *  参数:
             *  activity - 建议是应用的主Activity
             *  nodes - 传入的算路节点，顺序是起点、途经点、终点，其中途经点最多三个，参考 BNRoutePlanNode
             *  preference - 算路偏好: 躲避拥堵 16,少走高速 4,高速优先 2,少收费 8,推荐 1
             *  isGPSNav - true表示真实GPS导航，false表示模拟导航
             *  listener - 开始导航回调监听器，在该监听器里一般是进入导航过程页面
             **/
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new CA_NearServiceActivity.RoutePlanListener(sNode));
        }
    }

    /**
     * @desc 获取系统SD卡根目录路径
     */
    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    /**
     * @desc 是否获取系统SD卡根目录路径
     */
    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * @desc 路线规划监听器，规划成功后一般跳转到导航过程页面
     */
    public class RoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public RoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        //路线规划成功，需要跳转到导航过程页面
        @Override
        public void onJumpToNavigator() {
            /*
             * 设置途径点以及resetEndNode会回调该接口
			 */
            for (Activity ac : activityList) {

                if (ac.getClass().getName().endsWith("AS_NearServiceGuideActivity")) {
                    return;
                }
            }
            Intent intent = new Intent(CA_NearServiceActivity.this, CA_NearServiceGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("tag", APP_FOLDER_NAME);
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);

        }

        //路线规划失败
        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            toastMsg("算路失败");
        }
    }

    /**
     * @desc 初始化设置
     */
    private void initSetting() {
        // 设置全程路况显示
        BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        // 设置导航语音播报模式
        //Novice 新手模式，Quite 静音，Veteran 老手模式
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        // 设置实时路况条
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
    }

    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    L.d("Handler", "TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    L.d("Handler", "TTS play end");
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

        @Override
        public void playEnd() {
            L.d("TTSPlayStateListener", "TTS play end");
        }

        @Override
        public void playStart() {
            L.d("TTSPlayStateListener", "TTS play start");
        }
    };


    private BNOuterTTSPlayerCallback mTTSCallback = new BNOuterTTSPlayerCallback() {

        @Override
        public void stopTTS() {
            // TODO Auto-generated method stub
            L.e("test_TTS", "stopTTS");
        }

        @Override
        public void resumeTTS() {
            // TODO Auto-generated method stub
            L.e("test_TTS", "resumeTTS");
        }

        @Override
        public void releaseTTSPlayer() {
            // TODO Auto-generated method stub
            L.e("test_TTS", "releaseTTSPlayer");
        }

        @Override
        public int playTTSText(String speech, int bPreempt) {
            // TODO Auto-generated method stub
            L.e("test_TTS", "playTTSText" + "_" + speech + "_" + bPreempt);

            return 1;
        }

        @Override
        public void phoneHangUp() {
            // TODO Auto-generated method stub
            L.e("test_TTS", "phoneHangUp");
        }

        @Override
        public void phoneCalling() {
            // TODO Auto-generated method stub
            L.e("test_TTS", "phoneCalling");
        }

        @Override
        public void pauseTTS() {
            // TODO Auto-generated method stub
            L.e("test_TTS", "pauseTTS");
        }

        @Override
        public void initTTSPlayer() {
            // TODO Auto-generated method stub
            L.e("test_TTS", "initTTSPlayer");
        }

        @Override
        public int getTTSState() {
            // TODO Auto-generated method stub
            L.e("test_TTS", "getTTSState");
            return 1;
        }
    };

}
