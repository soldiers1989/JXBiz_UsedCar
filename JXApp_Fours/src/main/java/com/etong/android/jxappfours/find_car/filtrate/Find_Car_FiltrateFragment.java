package com.etong.android.jxappfours.find_car.filtrate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.EtongLineNoScrollGridView;
import com.etong.android.frame.widget.EtongNoScrollListView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.filtrate.adapter.Find_Car_LevelGridAdapter;
import com.etong.android.jxappfours.find_car.filtrate.adapter.Find_Car_OtherListAdapter;
import com.etong.android.jxappfours.find_car.filtrate.javabeam.Find_Car_LevelItemBeam;
import com.etong.android.jxappfours.find_car.filtrate.javabeam.Find_Car_OtherItemBeam;
import com.etong.android.jxappfours.find_car.filtrate.utils.Find_Car_Filtrate_RangeSeekBar;
import com.etong.android.jxappfours.order.Fours_Order_Provider;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * 筛选主界面的Fragment
 * Created by Administrator on 2016/8/8 0008.
 */
public class Find_Car_FiltrateFragment extends BaseSubscriberFragment implements Find_Car_LevelGridAdapter.LCallBack {


    private ScrollView mScrollView;
    private EtongLineNoScrollGridView mGridView;
    private EtongNoScrollListView mListView;
    private Button mConfirmBt;
    private Button mCancelBt;
    private Button mResetBt;
    private Find_Car_LevelGridAdapter gridListAdapter;
    private List<Find_Car_LevelItemBeam> itemList;
    private Find_Car_OtherListAdapter mListAdapter;
    private List<Find_Car_OtherItemBeam> otherList;
    public static String selectLevel;
    private Find_Car_Filtrate_RangeSeekBar mRangeSeekBar;
    public static DrawerLayout mDrawerLayout;
    private Fours_Order_Provider mFours_Order_Provider;
    private List<Integer> carLevelId;
    public static String priceStart;
    public static String priceEnd;
    private String country;
    private String category;
    private String summary;
    private String outVolStar;
    private String outVolEnd;


    private boolean isRequest = false;
    private boolean isSelect = false;
    private boolean isScroll = false;

    private boolean isHasNetwork = true;

    public static boolean canClick = true;
    public static boolean canClickOther = true;
    private ConnectionChangeReceiver myReceiver;


    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.find_car_filtrate_frg,
                container, false);

        registerReceiver();
        mFours_Order_Provider = Fours_Order_Provider.getInstance();
        mFours_Order_Provider.initialize(HttpPublisher.getInstance());
        mFours_Order_Provider.queryAllCarLevel();

        initView(view);

        mFours_Order_Provider.coutSearchCarsetCount(null, null, null, null, null, null, null, null);
        return view;
    }

    private void initView(View view) {

        mDrawerLayout = findViewById(view, R.id.find_car_filtrate_drawerlayout, DrawerLayout.class);
        mRangeSeekBar = findViewById(view, R.id.find_car_sb_price, Find_Car_Filtrate_RangeSeekBar.class);
        mScrollView = findViewById(view, R.id.find_car_sv_scroll, ScrollView.class);
        mGridView = findViewById(view, R.id.find_car_gv_level, EtongLineNoScrollGridView.class);
        mListView = findViewById(view, R.id.find_car_lv_others, EtongNoScrollListView.class);
        mResetBt = findViewById(view, R.id.find_car_btn_reset, Button.class);
        mCancelBt = findViewById(view, R.id.find_car_btn_cancel, Button.class);
        mConfirmBt = findViewById(view, R.id.find_car_btn_confirm, Button.class);

        //锁定右面的侧滑界面，不能通过手势关闭或者打开，只能通过代码打开，即调用openDrawer方法！
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);

        //侧滑界面的初始化，Fragment
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Find_Car_FiltrateVechilePriceFragment filtrateVechileFragment = new Find_Car_FiltrateVechilePriceFragment();
        transaction.replace(R.id.find_car_filtrate_framlayout, filtrateVechileFragment, "");
        transaction.commitAllowingStateLoss();

        addClickListener(mResetBt);
        addClickListener(mCancelBt);
        addClickListener(mConfirmBt);

        initGridView(view);
        initListView(view);
        initDrawerLayoutData();


        mRangeSeekBar.setOnThumbMoveListener(new Find_Car_Filtrate_RangeSeekBar.OnThumbMoveListener() {
            @Override
            public void onThumbMoveChange(int leftValue, int rightValue) {
                L.d("-------------------", leftValue + "        " + rightValue);
                priceStart = leftValue + "";
                priceEnd = rightValue + "";
                setOtherConfig();
                mFours_Order_Provider.coutSearchCarsetCount(priceStart, priceEnd, selectLevel, country, category, summary, outVolStar, outVolEnd);
                isScroll = false;
            }
        });

        mRangeSeekBar.setOnRangeChangeListener(new Find_Car_Filtrate_RangeSeekBar.OnRangeChangeListener() {
            @Override
            public void onRangeChange(int leftValue, int rightValue) {
                isScroll = true;

                // 根据游标的变化动态的改变游标的滑动范围
                mRangeSeekBar.mThumbLeft.setLimit(mRangeSeekBar.mLeftLimit, mRangeSeekBar.mThumbRight.getRight() -
                        Find_Car_Filtrate_RangeSeekBar.LEFT_RANGE_RIGHT);
                mRangeSeekBar.mThumbRight.setLimit(mRangeSeekBar.mThumbLeft.getLeft() + Find_Car_Filtrate_RangeSeekBar.LEFT_RANGE_RIGHT,
                        mRangeSeekBar.mRightLimit);

            }
        });

//        mScrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View arg0, MotionEvent arg1) {
//                return isScroll;
//            }
//        });
    }

    private void initDrawerLayoutData() {
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            /**
             * 当抽屉滑动状态改变的时候被调用
             * 状态值是STATE_IDLE（闲置--0）, STATE_DRAGGING（拖拽的--1）, STATE_SETTLING（固定--2）中之一。
             * 抽屉打开的时候，点击抽屉，drawer的状态就会变成STATE_DRAGGING，然后变成STATE_IDLE
             */
            @Override
            public void onDrawerStateChanged(int arg0) {
            }

            /**
             * 当抽屉被滑动的时候调用此方法
             * arg1 表示 滑动的幅度（0-1）
             */
            @Override
            public void onDrawerSlide(View arg0, float arg1) {
            }

            /**
             * 当一个抽屉被完全打开的时候被调用
             */
            @Override
            public void onDrawerOpened(View arg0) {
            }

            /**
             * 当一个抽屉完全关闭的时候调用此方法
             */
            @Override
            public void onDrawerClosed(View arg0) {
            }
        });

    }

    private void initGridView(View view) {
        setGridInfo();
        gridListAdapter = new Find_Car_LevelGridAdapter(mGridView, getActivity(), Find_Car_FiltrateFragment.this, itemList);
        mGridView.setAdapter(gridListAdapter);
//        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
//                if(canClick){
//                    gridListAdapter.setSeclection(position);
//                    gridListAdapter.notifyDataSetChanged();
//                }
//            }
//        });
    }


    private void initListView(View view) {
        setListInfo();
        mListAdapter = new Find_Car_OtherListAdapter(getActivity(), otherList);
        mListView.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();
    }

    private void setListInfo() {
        otherList = new ArrayList<Find_Car_OtherItemBeam>();
        JSONArray array = JSONArray.parseArray(Find_Car_OtherJsonData.getJsonData());
        for (int i = 0; i < array.size(); i++) {
            Find_Car_OtherItemBeam mFindCarOtherItemBeam = JSON.toJavaObject(
                    array.getJSONObject(i), Find_Car_OtherItemBeam.class);
            otherList.add(mFindCarOtherItemBeam);
        }

    }

    private void setGridInfo() {
        itemList = new ArrayList<Find_Car_LevelItemBeam>();
        Find_Car_LevelItemBeam itemBean1 = new Find_Car_LevelItemBeam("微型车", "minicar");
        itemList.add(itemBean1);
        Find_Car_LevelItemBeam itemBean2 = new Find_Car_LevelItemBeam("小型车", "smallcar");
        itemList.add(itemBean2);
        Find_Car_LevelItemBeam itemBean3 = new Find_Car_LevelItemBeam("紧凑型车", "compact_car");
        itemList.add(itemBean3);
        Find_Car_LevelItemBeam itemBean4 = new Find_Car_LevelItemBeam("中型车", "middle_car");
        itemList.add(itemBean4);
        Find_Car_LevelItemBeam itemBean5 = new Find_Car_LevelItemBeam("中大型车", "middle_large_car");
        itemList.add(itemBean5);
        Find_Car_LevelItemBeam itemBean6 = new Find_Car_LevelItemBeam("豪华车", "limousine");
        itemList.add(itemBean6);
        Find_Car_LevelItemBeam itemBean7 = new Find_Car_LevelItemBeam("MPV", "mpv");
        itemList.add(itemBean7);
        Find_Car_LevelItemBeam itemBean8 = new Find_Car_LevelItemBeam("SUV", "allsuv");
        itemList.add(itemBean8);
        Find_Car_LevelItemBeam itemBean9 = new Find_Car_LevelItemBeam("跑车", "sports_car");
        itemList.add(itemBean9);
        Find_Car_LevelItemBeam itemBean10 = new Find_Car_LevelItemBeam("面包车", "small_van");
        itemList.add(itemBean10);
        Find_Car_LevelItemBeam itemBean11 = new Find_Car_LevelItemBeam("皮卡", "pickup_truck");
        itemList.add(itemBean11);
    }

    @Subscriber(tag = FrameHttpTag.QUERY_ALL_CAR_LEVEL)
    protected void getCarLevelInfo(HttpMethod method) {
        String flag = method.data().getString("flag");
        if ("0".equals(flag)) {
            carLevelId = new ArrayList<Integer>();
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (Object o : jsonArr) {
                JSONObject root = (JSONObject) o;
                carLevelId.add(root.getInteger("id"));
            }
            for (int i = 0; i < itemList.size(); i++) {
                Find_Car_LevelItemBeam temp = itemList.get(i);
                temp.setId(carLevelId.get(i));
                itemList.set(i, temp);
            }
            mListAdapter.notifyDataSetChanged();
        }
    }


    @Subscriber(tag = FrameHttpTag.COUT_SEARCH_CARSET_COUNT)
    protected void getFiltrateResultCount(HttpMethod method) {
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        if (flag.equals("0")) {
            int jsonData = method.data().getInteger("data");
            mConfirmBt.setText("确定（共" + jsonData + "个车系）");
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
            mConfirmBt.setText("确定");
        } else {
            toastMsg(msg);
        }
        canClick = true;
        canClickOther = true;
    }

    @Override
    protected void onClick(View v) {
        if (v.getId() == R.id.find_car_btn_reset) {//重置
            onReSet();
            mFours_Order_Provider.coutSearchCarsetCount(null, null, null, null, null, null, null, null);

        } else if (v.getId() == R.id.find_car_btn_cancel) {//取消

            if (isRequest) {
                //打开侧滑界面
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            } else {
                toastMsg("您还未筛选车");
            }

        } else if (v.getId() == R.id.find_car_btn_confirm) {//确定

//            onConfirm();
            setOtherConfig();
            mFours_Order_Provider.queryCarsetByCondition(priceStart, priceEnd, selectLevel, country, category, summary, outVolStar, outVolEnd);
            isRequest = true;
//            getVechileSeriesData();
            //重新加载一遍Fragment，打开侧滑界面
//            FragmentManager manager = getActivity().getSupportFragmentManager();
//            FragmentTransaction transaction = manager.beginTransaction();
//            Find_Car_FiltrateVechilePriceFragment filtrateVechileFragment = new Find_Car_FiltrateVechilePriceFragment();
//            transaction.replace(R.id.find_car_filtrate_framlayout, filtrateVechileFragment, "");
//            transaction.commitAllowingStateLoss();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
            if (isHasNetwork) {
                mEventBus.post("", "load");
            }
        }
    }

    //确定按钮的逻辑处理
//    private void onConfirm() {

//        for (int n : mRangeSeekBar.getValue()) {
//            L.d("################>", n + "");
//        }
//        L.d("++++++++++++++++>", "Select" + mListAdapter.getSelectItemMap());
//        if (null != selectLevel) {
//            L.d("---------------->", selectLevel + "");
//          mFours_Order_Provider.queryCarsetByCondition(Integer.valueOf(mRangeSeekBar.getValue()[0]),Integer.valueOf(mRangeSeekBar.getValue()[1]),selectLevel);
//            setOtherConfig();
//            mFours_Order_Provider.queryCarsetByCondition(priceStart, priceEnd, selectLevel, country, category, summary, outVolStar, outVolEnd);
//        }
//        Iterator it = mListAdapter.getSelectItemMap().entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            Object key = entry.getKey();
//            String name = String.valueOf(mListAdapter.getSelectItemMap().get(Integer.valueOf(String.valueOf(key))).getTitle());
//            L.d("~~~~~~~~~~~~~~~~>", name);
//        }
//    }

    //重置刷新所有控件
    private void onReSet() {
        mRangeSeekBar.reSet();
        gridListAdapter.notifyDataSetChanged();
        gridListAdapter.setCleanSelect();
        mListAdapter.setCleanSeclect();
        mListAdapter.notifyDataSetChanged();

        selectLevel = null;
        priceStart = null;
        priceEnd = null;
        country = null;
        category = null;
        summary = null;
        outVolStar = null;
        outVolEnd = null;
    }


    @Subscriber(tag = "reset")
    protected void getResetMsg(String reset) {
        onReSet();
        mFours_Order_Provider.coutSearchCarsetCount(null, null, null, null, null, null, null, null);
    }


//    //得到车辆车系信息
//    public static void getVechileSeriesData() {
//        List<Find_Car_VechileSeries> mList = new ArrayList<Find_Car_VechileSeries>();
//        JSONArray array = JSONArray.parseArray(Find_Car_FiltrateJsonData.getJsonData());
//        for (Object o : array) {
//            JSONObject root = (JSONObject) o;
//            Find_Car_VechileSeries series = new Find_Car_VechileSeries();
////            series.setId(root.getIntValue("id"));
//            series.setTitle(root.getString("title"));
//            series.setRoot(true);
//            mList.add(series);
//
//            JSONArray carset = root.getJSONArray("carset");
//            for (Object carJson : carset) {
//                Find_Car_VechileSeries car = JSON.toJavaObject((JSONObject) carJson,
//                        Find_Car_VechileSeries.class);
//                mList.add(car);
//            }
//        }
//        EventBus.getDefault().post(mList, "VechileSeriesInfo");
//    }


    public void setOtherConfig() {
        if (mListAdapter.getSelectItemMap().containsKey(0)) {
            country = mListAdapter.getSelectItemMap().get(0).getId() + "";
        } else {
            country = null;
        }
        if (mListAdapter.getSelectItemMap().containsKey(1)) {
            category = mListAdapter.getSelectItemMap().get(1).getId() + "";
        } else {
            category = null;
        }
        if (mListAdapter.getSelectItemMap().containsKey(2)) {
            summary = mListAdapter.getSelectItemMap().get(2).getTitle();
        } else {
            summary = null;
        }
        if (mListAdapter.getSelectItemMap().containsKey(3)) {
            outVolStar = mListAdapter.getSelectItemMap().get(3).getOutVolStar();
            outVolEnd = mListAdapter.getSelectItemMap().get(3).getOutVolEnd();
        } else {
            outVolStar = null;
            outVolEnd = null;
        }
    }

    //ListView的回调接口，得到选中的级别item
    @Override
    public void answer(boolean isSelect, Integer id) {
        this.isSelect = isSelect;
        if (isSelect) {
            selectLevel = id + "";
            L.d("~~~~~~~~~~~~~~~~>", id + "");
        } else {
            selectLevel = null;
        }

        setOtherConfig();

        mFours_Order_Provider.coutSearchCarsetCount(priceStart, priceEnd, selectLevel, country, category, summary, outVolStar, outVolEnd);
        canClick = false;
    }

    //关闭侧滑界面的方法
    public static void closeDrawerLayout() {
        mDrawerLayout.closeDrawer(Gravity.RIGHT);
    }


    @Override
    public void onResume() {
        super.onResume();

        if (!isSelect) {
            selectLevel = null;
        }
        priceStart = null;
        priceEnd = null;
        country = null;
        category = null;
        summary = null;
        outVolStar = null;
        outVolEnd = null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    //实时监测有无网络的广播
    public class ConnectionChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                isHasNetwork = false;
                mConfirmBt.setText("确定");
            } else {
                isHasNetwork = true;
                setOtherConfig();
                mFours_Order_Provider.coutSearchCarsetCount(priceStart, priceEnd, selectLevel, country, category, summary, outVolStar, outVolEnd);
            }
        }
    }

    //注册广播
    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        myReceiver = new ConnectionChangeReceiver();
        getActivity().registerReceiver(myReceiver, filter);
    }

    //注销广播
    private void unregisterReceiver() {
        getActivity().unregisterReceiver(myReceiver);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            mListView.setFocusableInTouchMode(false);
            mListView.setFocusable(false);

            mScrollView.setFocusableInTouchMode(true);
            mScrollView.setFocusableInTouchMode(true);
            mScrollView.scrollTo(0, 0);
        }
    }
}

