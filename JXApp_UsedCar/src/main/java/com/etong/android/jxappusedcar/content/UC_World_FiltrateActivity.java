package com.etong.android.jxappusedcar.content;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_World_FiltrateListAdapter;
import com.etong.android.jxappusedcar.javabean.UC_FilterBean;
import com.etong.android.jxappusedcar.javabean.UC_FilterRequestBean;
import com.etong.android.jxappusedcar.javabean.UC_World_FiltrateListItemBeam;
import com.etong.android.jxappusedcar.provider.UC_WorldProvider;
import com.etong.android.jxappusedcar.utils.UC_Filtrate_RangeSeekBar;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc (二手车筛选界面)
 * @createtime 2016/10/10 0010--10:32
 * @Created by wukefan.
 */
public class UC_World_FiltrateActivity extends BaseSubscriberActivity {

    /*
      ##################################################################################################
      ##                                        类中的变量                                            ##
      ##################################################################################################
    */

    /***
     * 选中条件的javabean
     */
    private UC_FilterBean mFilterBean;

    public static String FILTER_BEAN = "filterBean";
    public static int FILTER_RESULT_CODE = 321;

    private ListView mListView;
    private UC_Filtrate_RangeSeekBar mRangeSeekBar;
    private TitleBar mTitleBar;
    private List<UC_World_FiltrateListItemBeam> otherList = new ArrayList<>();
    private UC_World_FiltrateListAdapter mListAdapter;
    private SetEmptyViewUtil setEmptyViewUtil;


    private int priceStart = 0;
    private int priceEnd = 0;
    private String vehicleTypeName;     //车辆类型
    private String carAgeName;          //车龄
    private String mileAgeName;         //里程
    private String countryName;         //国别
    private String gearBoxName;         //变速箱

    private ViewGroup mContentView;
    private ViewGroup mWithOutNetWorkView;
    private HttpPublisher mHttpPublisher;
    private UC_WorldProvider mProvider;
    private Button mConfirmBt;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.used_car_world_activity_filtrate);
        setSwipeBackEnable(false);

        initView();
        initData();
    }


    /*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/

    private void initView() {

        Intent mIntent = getIntent();
        mFilterBean = (UC_FilterBean) mIntent.getSerializableExtra(FILTER_BEAN);

        setSelectData();

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("二手车筛选");

        //没网布局
        mWithOutNetWorkView = findViewById(R.id.default_empty_content, ViewGroup.class);
        TextView mWithOutNetWorkTxt = findViewById(R.id.default_empty_lv_textview, TextView.class);
        ImageView mWithOutNetWorkImg = findViewById(R.id.default_empty_img, ImageView.class);
        setEmptyViewUtil = new SetEmptyViewUtil(mWithOutNetWorkView, mWithOutNetWorkImg, mWithOutNetWorkTxt);
//        mWithOutNetWorkTxt.setText("亲暂时没有网络哦\n请点击屏幕重试~");
        mWithOutNetWorkView.setVisibility(View.GONE);
        mWithOutNetWorkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProvider.queryFilterDicData();
            }
        });

        mContentView = findViewById(R.id.uc_world_rl_filter, ViewGroup.class);
        mListView = findViewById(R.id.uc_world_lv_filtrate, ListView.class);
        mConfirmBt = findViewById(R.id.uc_world_btn_confirm, Button.class);

        initListHeaderView();
        initListView();

        addClickListener(R.id.uc_world_btn_reset);
        addClickListener(R.id.uc_world_btn_cancel);
        addClickListener(R.id.uc_world_btn_confirm);
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
        //初始化请求
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mProvider = UC_WorldProvider.getInstance();
        mProvider.initialize(mHttpPublisher);

        mProvider.queryFilterDicData();
    }

/*
  ##################################################################################################
  ##                                     点击事件的处理                                           ##
  ##################################################################################################
*/

    @Override
    protected void onClick(View v) {
        if (v.getId() == R.id.uc_world_btn_reset) {//重置
            onReSet();
        } else if (v.getId() == R.id.uc_world_btn_cancel) {//取消
            finish();
        } else if (v.getId() == R.id.uc_world_btn_confirm) {//确定
            setResultData();
        }
    }

    /*
      ##################################################################################################
      ##                              使用的逻辑方法，以及对外公开的方法                              ##
      ##                                      请求数据、获取数据                                      ##
      ##################################################################################################
    */

    @Subscriber(tag = FrameHttpTag.QUERY_FILTER_DIC_DATA)
    protected void setFilterListInfo(HttpMethod method) {

        otherList.clear();
        showContentView();
        String msg = method.data().getString("msg");
        String flag = method.data().getString("flag");
        try {
            if (flag.equals("0")) {
                Map<String, UC_World_FiltrateListItemBeam> tempMap = new HashMap<String, UC_World_FiltrateListItemBeam>();
//                JSONObject object = JSONArray.parseObject(UC_FilterJsonData.getJsonData());
//                JSONArray array = (JSONArray) object.get("data");
                JSONArray array = method.data().getJSONArray("data");
                for (int i = 0; i < array.size(); i++) {
                    UC_World_FiltrateListItemBeam mFilterDataDictionaryItemBeam = JSON.toJavaObject(
                            array.getJSONObject(i), UC_World_FiltrateListItemBeam.class);
                    tempMap.put(mFilterDataDictionaryItemBeam.getParaName(), mFilterDataDictionaryItemBeam);
                    if (!mFilterDataDictionaryItemBeam.getParaName().equals("价格")
                            && (null == mFilterDataDictionaryItemBeam.getMap() || mFilterDataDictionaryItemBeam.getMap().size() == 0)) {
                        reSetQuest();
                    }
                }

                if (null != carAgeName) {
                    tempMap = setItemSelect(tempMap, "车龄", carAgeName);
                }
                if (null != mileAgeName) {
                    tempMap = setItemSelect(tempMap, "里程", mileAgeName);
                }
                if (null != vehicleTypeName) {
                    tempMap = setItemSelect(tempMap, "级别", vehicleTypeName);
                }
                if (null != gearBoxName) {
                    tempMap = setItemSelect(tempMap, "变速箱", gearBoxName);
                }
                if (null != countryName) {
                    tempMap = setItemSelect(tempMap, "国别", countryName);
                }

                otherList.add(tempMap.get("车龄"));
                otherList.add(tempMap.get("里程"));
                otherList.add(tempMap.get("级别"));
                otherList.add(tempMap.get("变速箱"));
                otherList.add(tempMap.get("国别"));

                mListAdapter.updateCarList(otherList);
                if (otherList.size() < 5) {
                    reSetQuest();
                }

            } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
                reSetQuest();
                showNoNetworkView();
            } else if (flag.equals(HttpPublisher.DATA_ERROR)) {
                reSetQuest();
                showNoServerView();
            } else {
                reSetQuest();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @desc 得到设置上次选中参数
     * @createtime 2016/10/27 0027-16:26
     * @author wukefan
     */
    protected void setSelectData() {
        if (null != mFilterBean) {

            //价格
            Map<String, List<String>> price = mFilterBean.getPrice();
            if (null != price && price.size() != 0) {
                for (String key : price.keySet()) {
                    String priceName = key;
                    List<String> priceList = price.get(priceName);
                    if (null != priceList.get(0)) {
                        priceStart = Integer.valueOf(priceList.get(0));
                    }
                    if (null != priceList.get(1)) {
                        priceEnd = Integer.valueOf(priceList.get(1));
                    }
                }
            }
            //车龄
            Map<String, String> carAge = mFilterBean.getCarAge();
            if (null != carAge && carAge.size() != 0) {
                for (String key : carAge.keySet()) {
                    carAgeName = key;
                }
            }

            //里程
            Map<String, String> mileAge = mFilterBean.getMileAge();
            if (null != mileAge && mileAge.size() != 0) {
                for (String key : mileAge.keySet()) {
                    mileAgeName = key;
                }
            }

            //级别
            Map<String, String> vehicleType = mFilterBean.getVehicleType();
            if (null != vehicleType && vehicleType.size() != 0) {
                for (String key : vehicleType.keySet()) {
                    vehicleTypeName = key;
                }
            }

            //国别
            Map<String, String> country = mFilterBean.getCountry();
            if (null != country && country.size() != 0) {
                for (String key : country.keySet()) {
                    countryName = key;
                }
            }

            //变速箱
            Map<String, String> gearBox = mFilterBean.getGearBox();
            if (null != gearBox && gearBox.size() != 0) {
                for (String key : gearBox.keySet()) {
                    gearBoxName = key;
                }
            }
        }
    }

    /**
     * @desc 设置每个选项类型中上次选中的项的方法
     * @createtime 2016/10/27 0027-14:52
     * @author wukefan
     */
    protected Map<String, UC_World_FiltrateListItemBeam> setItemSelect(Map<String, UC_World_FiltrateListItemBeam> tempMap, String title, String name) {

        UC_World_FiltrateListItemBeam mFilterDataItemBeam = tempMap.get(title);
        List<UC_World_FiltrateListItemBeam.MapBean> mapBeanList = mFilterDataItemBeam.getMap();
        for (int j = 0; j < mapBeanList.size(); j++) {
            if (mapBeanList.get(j).getValue().equals(name)) {
                UC_World_FiltrateListItemBeam.MapBean temp = mapBeanList.get(j);
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
     * @desc (初始化listview)
     * @createtime 2016/11/28 0028-10:03
     * @author wukefan
     */
    private void initListView() {
        mListAdapter = new UC_World_FiltrateListAdapter(this);
        mListView.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化ListView中的头部
     */
    private void initListHeaderView() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.uesd_car_world_head_filtrate_view, null);
        mRangeSeekBar = (UC_Filtrate_RangeSeekBar) view.findViewById(R.id.uc_world_sb_price);

        mRangeSeekBar.setOnThumbMoveListener(new UC_Filtrate_RangeSeekBar.OnThumbMoveListener() {
            @Override
            public void onThumbMoveChange(int leftValue, int rightValue) {
//                toastMsg(leftValue + "        " + rightValue);
                L.d("-------------------", leftValue + "        " + rightValue);
                priceStart = leftValue;
                priceEnd = rightValue;

            }
        });

        mRangeSeekBar.setOnRangeChangeListener(new UC_Filtrate_RangeSeekBar.OnRangeChangeListener() {
            @Override
            public void onRangeChange(int leftValue, int rightValue) {
//                isScroll = true;

                // 根据游标的变化动态的改变游标的滑动范围
                mRangeSeekBar.mThumbLeft.setLimit(mRangeSeekBar.mLeftLimit, mRangeSeekBar.mThumbRight.getRight() -
                        UC_Filtrate_RangeSeekBar.LEFT_RANGE_RIGHT);
                mRangeSeekBar.mThumbRight.setLimit(mRangeSeekBar.mThumbLeft.getLeft() + UC_Filtrate_RangeSeekBar.LEFT_RANGE_RIGHT,
                        mRangeSeekBar.mRightLimit);

            }
        });

        mRangeSeekBar.setCurrentValue(priceStart, priceEnd);
        // 将头布局添加到ListView中
        mListView.addHeaderView(view);
    }


    /**
     * @desc 选择条件结果处理
     */
    protected void setResultData() {
        Map<String, UC_FilterRequestBean> selectMap = mListAdapter.getSelectItemMap();
        UC_FilterBean filterBean = new UC_FilterBean();

        if (selectMap.containsKey("级别")) {
            L.d("-----级别", selectMap.get("级别").getFlag());

            Map<String, String> vehicleType = new HashMap<String, String>();
            vehicleType.put(selectMap.get("级别").getName(), selectMap.get("级别").getFlag());
            filterBean.setVehicleType(vehicleType);
        }
        if (selectMap.containsKey("车龄")) {
            L.d("-----车龄", selectMap.get("车龄").getFlag());

            Map<String, String> carAge = new HashMap<String, String>();
            carAge.put(selectMap.get("车龄").getName(), selectMap.get("车龄").getFlag());
            filterBean.setCarAge(carAge);
        }


        Map<String, List<String>> price = new HashMap<String, List<String>>();
        List<String> priceList = new ArrayList<String>();
        L.d("-----价格", "priceStart" + priceStart + "   priceEnd" + priceEnd);
        if (priceEnd == 0) {
            priceList.add(null);
            priceList.add(null);
        } else {
            priceList.add(priceStart + "");
            priceList.add(priceEnd + "");
        }
        price.put("price", priceList);
        filterBean.setPrice(price);

        if (selectMap.containsKey("里程")) {
            L.d("-----里程", selectMap.get("里程").getFlag());

            Map<String, String> mileAge = new HashMap<String, String>();
            mileAge.put(selectMap.get("里程").getName(), selectMap.get("里程").getFlag());
            filterBean.setMileAge(mileAge);
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

        L.d("-----filterBean", filterBean + "");

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(UC_World_FiltrateActivity.FILTER_BEAN, filterBean);
        intent.putExtras(bundle);

        setResult(UC_World_FiltrateActivity.FILTER_RESULT_CODE, intent);

        finish();

    }

    /**
     * @desc 重置方法
     */
    private void onReSet() {
        priceStart = 0;
        priceEnd = 0;
        mRangeSeekBar.setCurrentValue(priceStart, priceEnd);
        mRangeSeekBar.reSet();
        mListAdapter.setCleanSeclect();
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
        setEmptyViewUtil.showNetworkErrorView();
    }

    /**
     * @desc (显示没服务的布局)
     * @createtime 2016/11/2 0002-18:09
     * @author wukefan
     */
    private void showNoServerView() {
        mContentView.setVisibility(View.GONE);
        mWithOutNetWorkView.setVisibility(View.VISIBLE);
        setEmptyViewUtil.showNoServerView();
    }

    private void reSetQuest() {
        L.d("============重新设置了筛选请求=========", "");
        // 需要在Provider中添加获取到HttpMethod的公开方法，然后根据公开的方法获取到唯一的请求缓存key，
        String cacheTagTemp = mHttpPublisher.getHttpTagFromMD5(mProvider.getFilterMethod());
//                L.d("获取到的tag--->", cacheTagTemp);
        if (!TextUtils.isEmpty(cacheTagTemp)) {
            HttpPublisher.cleanCacheByTag(cacheTagTemp);        // 根据唯一的tag值来删除缓存
        }
    }
    /*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/
}
