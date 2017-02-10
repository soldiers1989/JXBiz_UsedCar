package com.etong.android.jxappusedcar.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_FilterListAdapter;
import com.etong.android.jxappusedcar.bean.UC_FilterBean;
import com.etong.android.jxappusedcar.bean.UC_FilterDataDictionaryBean;
import com.etong.android.jxappusedcar.bean.UC_FilterRequestBean;
import com.etong.android.frame.utils.L;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UC_AdvancedFilterActivity extends BaseSubscriberActivity {

/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/
    //业务

    /*** 选中条件的javabean */
    private UC_FilterBean mFilterBean;

    /*** 选中条件的参数*/
    private String vehicleTypeName;     //车辆类型
    private String carAgeName;          //车龄
    private String carAgeMin;           //车龄最小值
    private String carAgeMax;           //车龄最大值
    private String mileAgeName;         //里程
    private String mileAgeMin;          //里程最小值
    private String mileAgeMax;          //里程最大值
    private String gearBoxName;         //变速箱
    private String countryName;         //国别
    private String isauthenticateName;  //认证情况
    private String colorName;           //颜色

    public static String FILTER_BEAN = "filterBean";
    public static int FILTER_RESULT_CODE = 321;

    /*** 选中条件的Map*/
    private Map<String, UC_FilterRequestBean> selectMap;

    /*** 筛选的ListView的数据*/
    private List<UC_FilterDataDictionaryBean> filterDataDictionaryBeenList = new ArrayList<UC_FilterDataDictionaryBean>();

    //类型
    private TitleBar mTitleBar;

    /*** 筛选的ListView*/
    private ListView mListView;

    private UC_FilterListAdapter mListAdapter;
    private UC_UserProvider mUsedCarProvider;

    /*** 没网显示的图片*/
    private ImageView mWithOutNetWorkView;

    /*** 筛选整个内容视图*/
    private ViewGroup mContentView;


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_advanced_filter);

        initView();
        initData();
    }


/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/

    /**
     * 请使用快捷模板生成注释
     */
    private void initView() {

        Intent mIntent = getIntent();
        mFilterBean = (UC_FilterBean) mIntent.getSerializableExtra(FILTER_BEAN);

        setSelectData();

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("高级筛选");
        mTitleBar.setNextButton("重置");
        mTitleBar.setNextTextColor("#CF1C36");
        mTitleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReset();
            }
        });

        mListView = findViewById(R.id.uc_lv_filtrate, ListView.class);

        mWithOutNetWorkView = findViewById(R.id.uc_filter_no_nework, ImageView.class);
        mContentView = findViewById(R.id.uc_filter_content, ViewGroup.class);

        addClickListener(R.id.uc_txt_filtrate_check);
        addClickListener(mWithOutNetWorkView);
        initListView();
    }


/*
  ##################################################################################################
  ##                                     初始化数据的方法                                         ##
  ##################################################################################################
*/

    /**
     * 请使用快捷模板生成注释
     */
    private void initData() {
        mUsedCarProvider = UC_UserProvider.getInstance();
        mUsedCarProvider.initalize(HttpPublisher.getInstance());

        mUsedCarProvider.filtrateconditions();
    }

/*
  ##################################################################################################
  ##                                     点击事件的处理                                           ##
  ##################################################################################################
*/

    /**
     * 控件的点击事件
     */
    @Override
    protected void onClick(View view) {
        if (view.getId() == R.id.uc_txt_filtrate_check) {//查看
            setResultData();
        } else if (view.getId() == R.id.uc_filter_no_nework) {//没网
            mUsedCarProvider.filtrateconditions();
        }
    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    /**
     * @desc 得到设置上次选中参数
     * @createtime 2016/10/27 0027-16:26
     * @author wukefan
     */
    private void setSelectData() {

        //车辆类型
        Map<String, String> vehicleType = mFilterBean.getVehicleType();
        if (null != vehicleType && vehicleType.size() != 0) {
            for (String key : vehicleType.keySet()) {
                vehicleTypeName = key;
            }
        }

        //车龄
        Map<String, List<String>> carAge = mFilterBean.getCarAge();
        if (null != carAge && carAge.size() != 0) {
            for (String key : carAge.keySet()) {
                carAgeName = key;
                List<String> carAgeList = carAge.get(carAgeName);
                if (null != carAgeList.get(0)) {
                    carAgeMin = carAgeList.get(0);
                }
                if (null != carAgeList.get(1)) {
                    carAgeMax = carAgeList.get(1);
                }
            }
        }

        //里程
        Map<String, List<String>> mileAge = mFilterBean.getMileAge();
        if (null != mileAge && mileAge.size() != 0) {
            for (String key : mileAge.keySet()) {
                mileAgeName = key;
                List<String> mileAgeList = mileAge.get(mileAgeName);
                if (null != mileAgeList.get(0)) {
                    mileAgeMin = mileAgeList.get(0);
                }
                if (null != mileAgeList.get(1)) {
                    mileAgeMax = mileAgeList.get(1);
                }
            }
        }

        //变速箱
        Map<String, String> gearBox = mFilterBean.getGearBox();
        if (null != gearBox && gearBox.size() != 0) {
            for (String key : gearBox.keySet()) {
                gearBoxName = key;
            }
        }

        //国别
        Map<String, String> country = mFilterBean.getCountry();
        if (null != country && country.size() != 0) {
            for (String key : country.keySet()) {
                countryName = key;
            }
        }

        //认证情况
        Map<String, String> isauthenticate = mFilterBean.getIsauthenticate();
        if (null != isauthenticate && isauthenticate.size() != 0) {
            for (String key : isauthenticate.keySet()) {
                isauthenticateName = key;
            }
        }

        //颜色
        Map<String, String> color = mFilterBean.getColor();
        if (null != color && color.size() != 0) {
            for (String key : color.keySet()) {
                colorName = key;
            }
        }
    }


    /**
     * @desc (初始化ListView)
     * @createtime 2016/10/25 0025-15:14
     * @author wukefan
     */
    private void initListView() {
        mListAdapter = new UC_FilterListAdapter(this);
        mListView.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();
    }

    @Subscriber(tag = UC_HttpTag.FILTRATE)
    private void setFilterListInfo(HttpMethod method) {
        filterDataDictionaryBeenList.clear();
        showContentView();
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");
        try {
            if (status.equals("true")) {
                Map<String, UC_FilterDataDictionaryBean> tempMap = new HashMap<String, UC_FilterDataDictionaryBean>();
//                JSONObject object = JSONArray.parseObject(UC_FilterJsonData.getJsonData());
//                JSONArray array = (JSONArray) object.get("data");
                JSONArray array = method.data().getJSONArray("data");
                for (int i = 0; i < array.size(); i++) {
                    UC_FilterDataDictionaryBean mFilterDataDictionaryItemBeam = JSON.toJavaObject(
                            array.getJSONObject(i), UC_FilterDataDictionaryBean.class);
                    tempMap.put(mFilterDataDictionaryItemBeam.getParaName(), mFilterDataDictionaryItemBeam);
                }

                if (null != vehicleTypeName) {
                    tempMap = setItemSelect(tempMap, "车辆类型", vehicleTypeName);
                }
                if (null != gearBoxName) {
                    tempMap = setItemSelect(tempMap, "变速箱", gearBoxName);
                }
                if (null != countryName) {
                    tempMap = setItemSelect(tempMap, "国别", countryName);
                }
                if (null != isauthenticateName) {
                    tempMap = setItemSelect(tempMap, "认证情况", isauthenticateName);
                }
                if (null != colorName) {
                    tempMap = setItemSelect(tempMap, "颜色", colorName);
                }
                if (null != carAgeName) {
                    tempMap = setRangeSelect(tempMap, "车龄", carAgeMin, carAgeMax);
                }

                if (null != mileAgeName) {
                    tempMap = setRangeSelect(tempMap, "里程", mileAgeMin, mileAgeMax);
                }

                filterDataDictionaryBeenList.add(tempMap.get("车辆类型"));
                filterDataDictionaryBeenList.add(tempMap.get("车龄"));
                filterDataDictionaryBeenList.add(tempMap.get("里程"));
                filterDataDictionaryBeenList.add(tempMap.get("变速箱"));
                filterDataDictionaryBeenList.add(tempMap.get("国别"));
                filterDataDictionaryBeenList.add(tempMap.get("认证情况"));
                filterDataDictionaryBeenList.add(tempMap.get("颜色"));

                mListAdapter.updateCarList(filterDataDictionaryBeenList);
            } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {
                showNoNetworkView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @desc 设置每个选项类型中上次选中的项的方法
     * @createtime 2016/10/27 0027-14:52
     * @author wukefan
     */
    private Map<String, UC_FilterDataDictionaryBean> setItemSelect(Map<String, UC_FilterDataDictionaryBean> tempMap, String title, String name) {

        UC_FilterDataDictionaryBean mFilterDataItemBeam = tempMap.get(title);
        List<UC_FilterDataDictionaryBean.MapBean> mapBeanList = mFilterDataItemBeam.getMap();
        for (int j = 0; j < mapBeanList.size(); j++) {
            if (mapBeanList.get(j).getValue().equals(name)) {
                UC_FilterDataDictionaryBean.MapBean temp = mapBeanList.get(j);
                temp.setSelect(true);
                mapBeanList.set(j, temp);
                mFilterDataItemBeam.setMap(mapBeanList);
                tempMap.put(mFilterDataItemBeam.getParaName(), mFilterDataItemBeam);
                return tempMap;
            }
        }
        return tempMap;
    }


    /**
     * @desc 设置每个选择范围的类型的选中最大值最小值的方法
     * @createtime 2016/10/27 0027-14:52
     * @author wukefan
     */
    private Map<String, UC_FilterDataDictionaryBean> setRangeSelect(Map<String, UC_FilterDataDictionaryBean> tempMap, String title, String min, String max) {

        UC_FilterDataDictionaryBean mFilterDataItemBeam = tempMap.get(title);
        mFilterDataItemBeam.setRangeIsSelect(true);
        mFilterDataItemBeam.setSelectMin(min);
        mFilterDataItemBeam.setSelectMax(max);

        tempMap.put(mFilterDataItemBeam.getParaName(), mFilterDataItemBeam);
        return tempMap;
    }

    /**
     * @desc 重置方法
     */
    private void onReset() {
        mListAdapter.setCleanSeclect();
    }

    /**
     * @desc 选择条件结果处理
     */
    private void setResultData() {
        selectMap = mListAdapter.getSelectItemMap();
        UC_FilterBean filterBean = new UC_FilterBean();

        if (selectMap.containsKey("车辆类型")) {
            L.d("-----车辆类型", selectMap.get("车辆类型").getFlag());

            Map<String, String> vehicleType = new HashMap<String, String>();
            vehicleType.put(selectMap.get("车辆类型").getName(), selectMap.get("车辆类型").getFlag());
            filterBean.setVehicleType(vehicleType);
        }
        if (selectMap.containsKey("车龄")) {
            if (!selectMap.get("车龄").getName().equals("8年以下")) {
                Map<String, List<String>> carAge = new HashMap<String, List<String>>();
                List<String> carAgeList = new ArrayList<String>();

                if (null != selectMap.get("车龄").getMinValue()) {
                    L.d("-----车龄Min", selectMap.get("车龄").getMinValue());
                    carAgeList.add(selectMap.get("车龄").getMinValue());
                } else {
                    carAgeList.add(null);
                }
                if (null != selectMap.get("车龄").getMaxValue()) {
                    L.d("-----车龄Max", selectMap.get("车龄").getMaxValue());
                    carAgeList.add(selectMap.get("车龄").getMaxValue());
                } else {
                    carAgeList.add(null);
                }

                carAge.put(selectMap.get("车龄").getName(), carAgeList);
                filterBean.setCarAge(carAge);
            }
        }

        if (selectMap.containsKey("里程")) {

            if (!selectMap.get("里程").getName().equals("15万公里以下")) {

                Map<String, List<String>> mileAge = new HashMap<String, List<String>>();
                List<String> mileAgeList = new ArrayList<String>();

                if (null != selectMap.get("里程").getMinValue()) {
                    L.d("-----里程Min", selectMap.get("里程").getMinValue());
                    mileAgeList.add(selectMap.get("里程").getMinValue());
                } else {
                    mileAgeList.add(null);
                }
                if (null != selectMap.get("里程").getMaxValue()) {
                    L.d("-----里程Max", selectMap.get("里程").getMaxValue());
                    mileAgeList.add(selectMap.get("里程").getMaxValue());
                } else {
                    mileAgeList.add(null);
                }

                mileAge.put(selectMap.get("里程").getName(), mileAgeList);
                filterBean.setMileAge(mileAge);
            }
        }
        if (selectMap.containsKey("变速箱")) {
            L.d("-----变速箱", selectMap.get("变速箱").getFlag());

            Map<String, String> gearBox = new HashMap<String, String>();
            gearBox.put(selectMap.get("变速箱").getName(), selectMap.get("变速箱").getFlag());
            filterBean.setGearBox(gearBox);
        }
        if (selectMap.containsKey("国别")) {
            L.d("-----国别", selectMap.get("国别").getFlag());

            Map<String, String> country = new HashMap<String, String>();
            country.put(selectMap.get("国别").getName(), selectMap.get("国别").getFlag());
            filterBean.setCountry(country);

        }
        if (selectMap.containsKey("认证情况")) {
            L.d("-----认证情况", selectMap.get("认证情况").getFlag());

            Map<String, String> isauthenticate = new HashMap<String, String>();
            isauthenticate.put(selectMap.get("认证情况").getName(), selectMap.get("认证情况").getFlag());
            filterBean.setIsauthenticate(isauthenticate);
        }
        if (selectMap.containsKey("颜色")) {
            L.d("-----颜色", selectMap.get("颜色").getFlag());

            Map<String, String> color = new HashMap<String, String>();
            color.put(selectMap.get("颜色").getName(), selectMap.get("颜色").getFlag());
            filterBean.setColor(color);
        }
        L.d("-----filterBean", filterBean + "");

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(UC_AdvancedFilterActivity.FILTER_BEAN, filterBean);
        intent.putExtras(bundle);
        setResult(UC_AdvancedFilterActivity.FILTER_RESULT_CODE, intent);
        finish();
    }


    /**
     * @desc (显示筛选全部内容的布局)
     * @createtime 2016/11/2 0002-18:09
     * @author wukefan
     */
    private void showContentView() {
        mContentView.setVisibility(View.VISIBLE);
        mWithOutNetWorkView.setVisibility(View.GONE);
    }

    /**
     * @desc (显示没网的布局)
     * @createtime 2016/11/2 0002-18:09
     * @author wukefan
     */
    private void showNoNetworkView() {
        mContentView.setVisibility(View.GONE);
        mWithOutNetWorkView.setVisibility(View.VISIBLE);
    }
/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
