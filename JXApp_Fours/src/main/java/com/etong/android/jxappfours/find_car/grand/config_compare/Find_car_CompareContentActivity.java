package com.etong.android.jxappfours.find_car.grand.config_compare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.adapter.Find_car_CarconfigCompareAdapter;
import com.etong.android.jxappfours.find_car.grand.provider.FC_GetInfo_Provider;
import com.etong.android.jxappfours.find_car.grand.view.HVScrollView;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.jxappfours.order.FO_OrderActivity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * 车辆配置界面的包含界面
 */
public class Find_car_CompareContentActivity extends BaseSubscriberActivity {

    public static final String FIND_CAR_COMPARE_ALL_ITEM = "find_car_compare_item";
    public static final String IS_SHOW_ADD_CONTENT = "false";
    public static final int COMPARECONTENT_REQUEST_CODE = 10001;

    private ListView mListView;

    // 方便测试，直接写的public
    public HorizontalScrollView mTouchView;

    // 装入所有的HScrollView, 在滑动的时候处理所有List中的滑动事件
    private List<HVScrollView> mHScrollViews = new ArrayList<HVScrollView>();

    // 配置界面中头部显示的标题
    private HVScrollView cofTitName;
    // 悬浮头部
    private PinnedSectionListView pinned_lv;
    private LinearLayout confTitleItem;

    private int carNum = 0;     // 传送过来的car的数量
    private ArrayList<Integer> compareCarId;
    private ArrayList<Models_Contrast_Add_VechileStyle> allCarItem;
    private HttpPublisher mHttpPublisher;           // 请求初始化
    private FC_GetInfo_Provider mGetInfoProvider;   // 内容提供者

    private List<String> compareTitleNameList;          // 对比界面中头部车辆的名称
    private List<String> compareTitleCarId;              // 获取到车辆的ID
    private Find_car_CarconfigCompareAdapter carCompareInfoAdapter;     // 对比界面中的Adapter
    private ViewGroup addContent;

    private boolean isShowAddContent;                   // 是否显示头部的添加按钮
    private TextView addContentText;
    private View lv_footView;
    private TitleBar titleBar;

    // 包裹内容的ViewGroup
    private ViewGroup contentInfo;
    private ViewGroup contentEmpty;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_car_activity_config_compare_content);

        initView();
        initData();
    }

    /**
     * 初始化界面中的组件
     */
    private void initView() {
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mGetInfoProvider = FC_GetInfo_Provider.getInstance();
        mGetInfoProvider.initialize(mHttpPublisher);

        titleBar = new TitleBar(this);
        titleBar.setmTitleBarBackground("#FFFFFF");
        titleBar.setTitleTextColor("#000000");
        titleBar.setTitle("参数配置");
        titleBar.setNextButton("隐藏相同项");
        titleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 隐藏相同的Item项
                if ("隐藏相同项".equals(((TextView) titleBar.getNextButton()).getText().toString())) {
                    carCompareInfoAdapter.hideSameListViewData();
                } else if ("显示所有项".equals(((TextView) titleBar.getNextButton()).getText().toString())) {
                    carCompareInfoAdapter.showAllListViewData();
                }

                titleBar.setNextButton("隐藏相同项".equals(((TextView) titleBar.getNextButton()).getText().toString()) ? "显示所有项" : "隐藏相同项");
            }
        });

        // 包裹内容的ViewGroup
        contentInfo = (ViewGroup) findViewById(R.id.fc_config_compare_content_info);
        contentEmpty = (ViewGroup) findViewById(R.id.fc_config_compare_content_empty);

        // Title上面的ScrollView      包含Item的ViewGroup
        cofTitName = (HVScrollView) findViewById(R.id.cofig_compare_title_car_name, HVScrollView.class);
        confTitleItem = (LinearLayout) findViewById(R.id.config_compare_title_item, ViewGroup.class);

        // Title上面的添加按钮        按钮上面的数字
        addContent = (ViewGroup) findViewById(R.id.find_car_carconfig_compare_add_content);
        addContentText = (TextView) findViewById(R.id.find_car_carconfig_compare_add_content_txt);
        addContent.setVisibility(View.INVISIBLE);
        // 设置点击添加按钮的点击事件
        addContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confTitleItem.getChildCount() < 0) {
                    return;
                }
                if (confTitleItem.getChildCount() >= 12) {
                    toastMsg("抱歉，最多只能选择12款车，不能再进入选车！");
                    return;
                }
                // 将所有的item传递过去
//                Intent i = new Intent(Find_car_CompareContentActivity.this, FC_CompareCarSelectActivity.class);
                Intent i = new Intent(Find_car_CompareContentActivity.this, FC_CCarSelectActivity.class);
                i.putParcelableArrayListExtra(Find_car_CompareContentActivity.FIND_CAR_COMPARE_ALL_ITEM, allCarItem);
                i.putExtra(Find_car_CompareContentActivity.IS_SHOW_ADD_CONTENT, true);
                startActivityForResult(i, COMPARECONTENT_REQUEST_CODE);
            }
        });

        // 底部文字
        lv_footView = LayoutInflater.from(this).inflate(R.layout.fc_compare_content_lv_footview, null);

        pinned_lv = (PinnedSectionListView) findViewById(R.id.pinned_lv, PinnedSectionListView.class);
        carCompareInfoAdapter = new Find_car_CarconfigCompareAdapter(this, mHScrollViews, pinned_lv);       // 初始化完了才给传
        pinned_lv.setAdapter(carCompareInfoAdapter);

        // 获取到上个界面传送过来的ID值
        // 初始化保存已经选中的item
        compareCarId = new ArrayList<Integer>();
        allCarItem = getIntent().getParcelableArrayListExtra(FIND_CAR_COMPARE_ALL_ITEM);
        // 遍历其中的值，判断选中与不选中
        for (int i = 0; i < allCarItem.size(); i++) {
            Models_Contrast_Add_VechileStyle carStyle = allCarItem.get(i);
            if (carStyle.isChecked()) {
                // 将选中的item的id添加到存放选中的id的List中
                compareCarId.add(carStyle.getId());
            }
        }
        isShowAddContent = getIntent().getBooleanExtra(IS_SHOW_ADD_CONTENT, false);
        // 获取到传送过来的ID
        if (compareCarId != null) {
            carNum = compareCarId.size();
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        loadStart(null, 0);
        if (compareCarId != null) {
            // 不为空请求网络
            mGetInfoProvider.getCompareCarConfig(compareCarId);
        }
    }

    /**
     * 从网络上获取到所有id型号的车辆信息
     *
     * @param httpMethod
     */
    @Subscriber(tag = FrameHttpTag.GET_COMPARE_INFO)
    public void getCarCompareInfo(HttpMethod httpMethod) {
        // 清除到所有的item
        if (confTitleItem != null) {
            confTitleItem.removeAllViews();
        }
        if (compareTitleNameList != null) {
            compareTitleNameList.clear();
        }
        if (compareTitleCarId != null) {
            compareTitleCarId.clear();
        }
        carNum = confTitleItem.getChildCount();

        String errno = httpMethod.data().getString("errno");
        String flag = httpMethod.data().getString("flag");
        String msg = httpMethod.data().getString("msg");
        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {

            // 设置内容区域为显示，空视图区域隐藏
            contentInfo.setVisibility(View.VISIBLE);
            contentEmpty.setVisibility(View.GONE);

            JSONArray jsonArr = httpMethod.data().getJSONArray("data");

            FC_CompareInfoUtil util = new FC_CompareInfoUtil(jsonArr);           // 新建工具类
            compareTitleNameList = util.getCompareTitleName();                // 获取到所有车辆的名字
            compareTitleCarId = util.getCompareIdList();                      // 获取到所有对比车辆的ID
            List compareCarInfo = util.getCompareCarInfoList();

            initTitleView(compareTitleNameList);                            // 初始化标题
            carCompareInfoAdapter.updateListViewData(compareCarInfo);    // 初始化ListView中的内容

            if (pinned_lv.getFooterViewsCount() <= 0) {
                pinned_lv.addFooterView(lv_footView);        // 添加脚部文字
            }
            loadFinish();
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
            loadFinish();
            // 设置内容区域为隐藏，空视图区域显示
            contentInfo.setVisibility(View.GONE);
            contentEmpty.setVisibility(View.VISIBLE);
        } else if(!TextUtils.isEmpty(flag) && HttpPublisher.DATA_ERROR.equals(flag)) {
            loadFinish();
            // 设置内容区域为隐藏，空视图区域显示
            contentInfo.setVisibility(View.GONE);
            contentEmpty.setVisibility(View.VISIBLE);
        }

        // 获取到数据之后改标题栏
        if (confTitleItem.getChildCount() >= 2) {
            titleBar.showNextButton(true);
            titleBar.setNextButton("隐藏相同项");
        } else {
            titleBar.showNextButton(false);
        }
        if (isShowAddContent) {
            addContent.setVisibility(View.VISIBLE);
            addContentText.setText("已经选择\n" + confTitleItem.getChildCount() + "/12");
        }
    }

    /**
     * 初始化标题中车辆
     */
    private void initTitleView(List<String> carFullName) {

        for (int i = 0; i < carFullName.size(); i++) {
            final int temp = i;
            final View view = LayoutInflater.from(this).inflate(R.layout.find_car_config_compare_title_item, null);
            TextView title_name = (TextView) view.findViewById(R.id.find_car_config_compare_title_name);
            TextView trial_run_btn = (TextView) view.findViewById(R.id.find_car_config_compare_title_trial_run_btn);
            ImageView title_delete = (ImageView) view.findViewById(R.id.find_car_config_compare_title_delete);
            // 删除的点击事件
            title_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (confTitleItem != null && confTitleItem.getChildCount() > 1) {

                        // 设置存放所有item的List选中与未选中
                        for (int i = 0; i < allCarItem.size(); i++) {
                            Models_Contrast_Add_VechileStyle vechileStyle = allCarItem.get(i);
                            if (Integer.parseInt(compareTitleCarId.get(temp)) == vechileStyle.getId()) {
                                vechileStyle.setChecked(false);
                            }
                        }
                        // 将移除的方法写到后面先完成移除之前List里面的东西
                        compareTitleCarId.remove(temp);
                        compareCarId.remove(temp);          // 移除到存放选中的id的List
                        addContentText.setText("已经选择\n" + confTitleItem.getChildCount() + "/12");
                        initData();
                    }
                }
            });

            // 根据获取到的车辆名字来设置TextView
            title_name.setText(carFullName.get(i));
            trial_run_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 试驾预约的点击事件
//                    Toast.makeText(Find_car_CompareContentActivity.this, "试驾预约 :id="+compareTitleCarId.get(temp), Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(Find_car_CompareContentActivity.this, Fours_Order_OrderActivity.class);
                    Intent i = new Intent(Find_car_CompareContentActivity.this, FO_OrderActivity.class);
                    i.putExtra("flag", 1);
                    i.putExtra("isSelectCar", true);
                    i.putExtra("vid", compareCarId.get(temp) + "");
                    i.putExtra("carsetId", -1);
                    i.putExtra("vTitleName", compareTitleNameList.get(temp) + "");
                    i.putExtra("carImage", "");
                    startActivity(i);
                }
            });

            confTitleItem.addView(view);
        }
        // 添加头部的ScrollView
        mHScrollViews.add(cofTitName);
    }

    /**
     * 收到来自子ScrollView的滑动来处理滑动事件
     *
     * @param left // 左边的距离
     * @param top
     * @param oldl
     * @param oldt
     */
    public void onScrollChanged(int left, int top, int oldl, int oldt) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
//        if (left == 0) {
//            leftOk.setVisibility(View.GONE);
//            leftNo.setVisibility(View.VISIBLE);
//        } else {
//            leftOk.setVisibility(View.VISIBLE);
//            leftNo.setVisibility(View.GONE);
//        }
//        if (left == width) {
//            rightOk.setVisibility(View.GONE);
//            rightNo.setVisibility(View.VISIBLE);
//        } else {
//            rightOk.setVisibility(View.VISIBLE);
//            rightNo.setVisibility(View.GONE);
//        }

        for (HVScrollView scrollView : mHScrollViews) {
            // 防止重复滑动
            if (mTouchView != scrollView)
                scrollView.smoothScrollTo(left, left);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {
            return;
        }
        // 如果返回的数据不为空，且code码一样
        if (requestCode == COMPARECONTENT_REQUEST_CODE) {
            allCarItem = data.getParcelableArrayListExtra(FIND_CAR_COMPARE_ALL_ITEM);
            // 将List中的数据先清除掉
            if (compareCarId != null) {
                compareCarId.clear();
            }
            // 遍历其中的值，判断选中与不选中
            for (int i = 0; i < allCarItem.size(); i++) {
                Models_Contrast_Add_VechileStyle carStyle = allCarItem.get(i);
                if (carStyle.isChecked()) {
                    // 将选中的item的id添加到存放选中的id的List中
                    compareCarId.add(carStyle.getId());
                }
            }
        }
        // 初始化数据
        initData();

    }
}
