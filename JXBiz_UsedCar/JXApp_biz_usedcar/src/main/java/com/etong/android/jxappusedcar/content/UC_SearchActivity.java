package com.etong.android.jxappusedcar.content;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.UC_FrameEtongApplication;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.Etong_SqlLiteDao;
import com.etong.android.frame.widget.UC_BrandCarset;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_HistoryCarRecycleViewAdapter;
import com.etong.android.jxappusedcar.adapter.UC_HotCarRecycleViewAdapter;
import com.etong.android.jxappusedcar.adapter.UC_SearchAdapter;
import com.etong.android.jxappusedcar.bean.UC_BrandBean;
import com.etong.android.jxappusedcar.bean.UC_HotCarType;
import com.etong.android.jxappusedcar.utils.UC_SpaceItemDecoration;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 搜索界面
 * Created by Dasheng on 2016/10/12.
 */

public class UC_SearchActivity extends BaseSubscriberActivity implements UC_SearchAdapter.UC_SearchCallBack{

    private String ALL_DATA = "all_car";  //所有车标记
    private String HIS_DATA = "his_car";  //历史记录标记

    private RecyclerView mSearchRecView, mHisCarView;   //所有车和历史车布局
    private List<UC_HotCarType> mHotCarNameList;        //热销车系数据容器
    private LinearLayout mHisCarLayout;                 //历史记录布局
    private EditText mSearchView;
    private List<UC_BrandCarset> mAllBrandType, mHisstoryList; //所有车和历史记录容器
    private JSONArray mAllCarArray;
    private Etong_SqlLiteDao mSqliteDao;                    //数据库
    private ListView mCarShowView;                          //加载所有车系的布局
    private ListAdapter<UC_BrandCarset> mShowListAdapter;   //所有车的适配器
    private boolean mIsChoiceCar;
    private UC_HistoryCarRecycleViewAdapter mHisCarAdapter; //历史记录适配器
    private UC_HotCarRecycleViewAdapter mHotTypeAdapter;    //热销车适配器
    private UC_UserProvider mUsedCarProvider;
    private TextView cancel_search;
    private TextView uc_tv_hot_car_type;
    private ImageView uc_search_no_network;
    private UC_SearchAdapter adapter;//搜索所有车的适配器
    private List<UC_BrandCarset> listSearch=new ArrayList<>();//所有车型 车系的list

    @Override
    protected void onInit(Bundle bundle) {
        setContentView(R.layout.used_car_activity_search);
        mUsedCarProvider = UC_UserProvider.getInstance();
        mUsedCarProvider.initalize(HttpPublisher.getInstance());

        initView();
        initData();
    }

    private void initView() {
        // 数据库初始化
        mSqliteDao = Etong_SqlLiteDao.getInstance(this, ALL_DATA, HIS_DATA);
        mAllBrandType = new ArrayList<>();
//        mAllBrandType=mSqliteDao.find("宝马", ALL_DATA, 0);
//        if(mAllBrandType.size()==0){
//            loadStart("正在更新数据......",0);
//            UC_FrameEtongApplication.getApplication().getAllCar();
//        }

        mHisstoryList = new ArrayList<>();
        // 历史记录布局
        mHisCarLayout = findViewById(R.id.uc_ll_history_layout, LinearLayout.class);
        mHisCarView = findViewById(R.id.uc_rv_history_car, RecyclerView.class);  //历史记录recycleView
        mHisCarView.setLayoutManager(new LinearLayoutManager(this));
        // 热销车系
        uc_tv_hot_car_type = findViewById(R.id.uc_tv_hot_car_type, TextView.class);
        uc_search_no_network = findViewById(R.id.uc_search_no_network, ImageView.class);
        // 热销车系list
        mHotCarNameList = new ArrayList<>();

        mSearchView = findViewById(R.id.uc_et_search, EditText.class);
        mCarShowView = findViewById(R.id.uc_lv_car_show, ListView.class);
        cancel_search = findViewById(R.id.uc_tv_cancel_search, TextView.class);
        //所有车的适配器
         adapter =new UC_SearchAdapter(this, UC_SearchActivity.this);
        mCarShowView.setAdapter(adapter);

        mSearchRecView = findViewById(R.id.uc_rv_hot_car_type, RecyclerView.class);   //热销车系recycleView
        mSearchRecView.setLayoutManager(new GridLayoutManager(this, 3));
        mHotTypeAdapter = new UC_HotCarRecycleViewAdapter(this, mHotCarNameList);
        mSearchRecView.setAdapter(mHotTypeAdapter);
        mSearchRecView.addItemDecoration(new UC_SpaceItemDecoration(2, 2, 2, 1));

        mSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mIsChoiceCar) {    //被选择了就不需要查询数据了
                    mIsChoiceCar = false;
                    return;
                }
                mAllBrandType.clear();
                if (!"".equals(s.toString().trim())) {
                    mAllBrandType = mSqliteDao.find(s + "", ALL_DATA, 0);
                }else {
                    mCarShowView.setVisibility(View.GONE);
                    return;
                }
                if (mAllBrandType.size() != 0) {
                    mCarShowView.setVisibility(View.VISIBLE);
                    listSearch.clear();

                    for (UC_BrandCarset set : mAllBrandType){
                        listSearch.add(set);
                    }
                } else {
                        mCarShowView.setVisibility(View.GONE);
                        listSearch.clear();
                        toastMsg("找不到您查询的车辆,请重新输入!");
                }
                adapter.update(listSearch);//更新数据
            }
            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        addClickListener(R.id.uc_tv_cancel_search);
        addClickListener(R.id.uc_tv_delete_history);
    }

    /**
     * @desc (请求数据)
     * @createtime 2016/11/2 - 15:45
     * @author xiaoxue
     */
    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("f_org_id", "001");
        mUsedCarProvider.getHotType(map);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAllBrandType=mSqliteDao.find("宝马", ALL_DATA, 0);
        if(mAllBrandType.size()==0){
            loadStart("正在更新数据......",0);
            UC_FrameEtongApplication.getApplication().getAllCar();
        }
        //当界面获取焦点之后就查询数据库时候有历史记录
        mHisstoryList = mSqliteDao.find("", HIS_DATA, 1);
        if (null != mHisstoryList && !mHisstoryList.isEmpty()) {
            mHisCarLayout.setVisibility(View.VISIBLE);
            mHisCarAdapter = new UC_HistoryCarRecycleViewAdapter(UC_SearchActivity.this, mHisstoryList);
            mHisCarView.setAdapter(mHisCarAdapter);
        }


    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);

        if (view.getId() == R.id.uc_tv_cancel_search) {//取消搜索
//            mSearchView.setText("");
            back();
            UC_BuyCarFragment.isReqest = false;
        } else if (view.getId() == R.id.uc_tv_delete_history) {//删除历史记录

            mSqliteDao.deleteHistory(HIS_DATA);
            mHisstoryList.clear();
            mHisCarLayout.setVisibility(View.GONE);
        }
    }

    /**
     * 获取热销车系数据
     *
     * @param method
     */
    @Subscriber(tag = UC_HttpTag.HOT_CAR_TYPE)
    private void hotCar(HttpMethod method) {
        uc_tv_hot_car_type.setVisibility(View.VISIBLE);
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");
        if (status.equals("true")) {
            if (!mHotCarNameList.isEmpty()) {
                mHotCarNameList.clear();
            }
            JSONArray arrays = method.data().getJSONArray("data");
            for (int i = 0; i < arrays.size(); i++) {
                UC_HotCarType set = JSON.toJavaObject((JSON) arrays.get(i), UC_HotCarType.class);
                mHotCarNameList.add(set);
            }
            mHotTypeAdapter.updata(mHotCarNameList);
        } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {
            uc_tv_hot_car_type.setVisibility(View.GONE);
        } else if (status.equals(HttpPublisher.DATA_ERROR)) {
            uc_tv_hot_car_type.setVisibility(View.GONE);
        } else{
            uc_tv_hot_car_type.setVisibility(View.GONE);
        }
    }

    /**
     * @desc 处理点击item后的操作 （回调）
     * @createtime 2016/11/4 - 16:46
     * @author xiaoxue
     */
    @Override
    public void SearchIntent(UC_BrandCarset brandCarset) {
        mIsChoiceCar = true;  //选择了车
        mCarShowView.setVisibility(View.GONE);   //隐藏所有车系列表
        if (!mHisstoryList.isEmpty()) {   //判断历史列表是否有数据
            mHisCarLayout.setVisibility(View.VISIBLE);
        }
        mSqliteDao.save(brandCarset, HIS_DATA); //保存历史数据
    }


    /**
     * 获取所有搜索数据
     *
     * @param method
     */
    @Subscriber(tag = UC_HttpTag.ALL_CAR_BRAND_TYPE)
    public void allCar(HttpMethod method) {
        L.json( method.data().toString());
        loadFinish();
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");

        if (!TextUtils.isEmpty(status) && status.equals("true")) {
            JSONArray arrays = method.data().getJSONArray("data");
            mSqliteDao.insert(arrays, ALL_DATA);
            UC_FrameEtongApplication.getApplication().setTime(new Date().toString());
        }else if (status.equals(HttpPublisher.NETWORK_ERROR)) {
        }else {
//            toastMsg(msg);
        }
    }
}
