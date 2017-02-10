package com.etong.android.jxappusedcar.content;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.EtongCommonUtils;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_World_CarList_Adapter;
import com.etong.android.jxappusedcar.javabean.UC_FilterBean;
import com.etong.android.jxappusedcar.javabean.UC_World_CarListJavaBean;
import com.etong.android.jxappusedcar.provider.UC_WorldProvider;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @desc 二手车世界列表页
 * @createtime 2016/10/9 0009--14:59
 * @Created by wukefan.
 */
public class UC_World_ListActivity extends BaseSubscriberActivity {

    private TitleBar mTitleBar;
    private UC_World_CarList_Adapter mListAdapter;
    private PullToRefreshListView mListView;
    private RadioButton mRadioButtons[] = new RadioButton[3];
    private List<UC_World_CarListJavaBean> mListData = new ArrayList<UC_World_CarListJavaBean>();

    private boolean isLoadAll = false;
    private HttpPublisher mHttpPublisher;
    private UC_WorldProvider mUC_WorldProvider;
    private int pageSize = 10;
    private int pageCurrent = 0;
    private boolean refreshIsRequest;
    /*0： 价格（默认排序）
     1:  里程数
    2： 车龄*/
    private int sortType = 0;
    private int isPullDown = 1;
    private LinearLayout default_empty_view;
    private TextView default_empty_textview;
    private ImageView default_empty_img;
    private SetEmptyViewUtil setEmptyViewUtil;

    private UC_FilterBean mUC_FilterBean = new UC_FilterBean();  //筛选的javabean
    private String registercode;                //车龄
    private String vehicletype;                 //车辆类型
    private String mileage;                     //里程
    private String gearmode;                    //变速器
    private String country;                     //国别
    private String pricemax;                    //价格:最大
    private String pricemin;                    //价格:最小
    public static int CAR_FILTER_CODE = 123;                    //跳到筛选页的tag值


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.used_car_world_activity_list);
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mUC_WorldProvider = UC_WorldProvider.getInstance();
        mUC_WorldProvider.initialize(mHttpPublisher);
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("二手车世界");
        mTitleBar.showNextButton(true);
        mTitleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivitySkipUtil.skipActivity(UC_World_ListActivity.this, UC_World_SearchActivity.class);
            }
        });
        pageCurrent = 0;
        mUC_WorldProvider.worldList(pageSize, pageCurrent, sortType, 0, pricemin, pricemax, mileage, registercode, vehicletype, country, gearmode); //请求车列表
        initView();
    }


    private void initView() {

        mRadioButtons[0] = findViewById(R.id.used_car_rab_price, RadioButton.class);
        mRadioButtons[1] = findViewById(R.id.used_car_rab_mileage, RadioButton.class);
        mRadioButtons[2] = findViewById(R.id.used_car_rab_car_age, RadioButton.class);
        mListView = findViewById(R.id.used_car_lv_car, PullToRefreshListView.class);
        default_empty_view = findViewById(R.id.default_empty_content, LinearLayout.class);
        default_empty_textview = findViewById(R.id.default_empty_lv_textview, TextView.class);
        default_empty_img = findViewById(R.id.default_empty_img, ImageView.class);
        setEmptyViewUtil = new SetEmptyViewUtil(default_empty_view, default_empty_img, default_empty_textview);
        default_empty_view.setClickable(false);
        default_empty_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageCurrent = 0;
                mUC_WorldProvider.worldList(pageSize, pageCurrent, sortType, 0, pricemin, pricemax, mileage, registercode, vehicletype, country, gearmode); //请求车列表
            }
        });
        mListAdapter = new UC_World_CarList_Adapter(this);
        mListView.setAdapter(mListAdapter);

        addClickListener(R.id.used_car_txt_filtrate);
        addClickListener(mRadioButtons);
        initListView();
    }

    private void initListView() {
        mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉

        mListView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        mListView.getLoadingLayoutProxy(false, true).setRefreshingLabel(
                "正在加载更多...");
        mListView.getLoadingLayoutProxy(false, true).setReleaseLabel(
                "松开加载更多");

        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //延迟0.3s
                mListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageCurrent = 0;
                        mUC_WorldProvider.worldList(pageSize, pageCurrent, sortType, 0, pricemin, pricemax, mileage, registercode, vehicletype, country, gearmode); //请求车列表
                    }
                }, 300);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //延迟3s
                mListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshIsRequest) {//请求接口
                            mUC_WorldProvider.worldList(pageSize, ++pageCurrent, sortType, 1, pricemin, pricemax, mileage, registercode, vehicletype, country, gearmode); //请求车列表
                        } else {//不请求接口
                            mListView.onRefreshComplete();//刷新完成
                            toastMsg("已加载全部数据");
                        }
                    }
                }, 300);
            }
        });


    }


    @Override
    protected void onClick(View view) {

        if (view.getId() == R.id.used_car_txt_filtrate) {//筛选
            if (!EtongCommonUtils.isFastDoubleClick()) {
                //跳转到筛选页
                Intent i = new Intent(this, UC_World_FiltrateActivity.class);
                i.putExtra(UC_World_FiltrateActivity.FILTER_BEAN, mUC_FilterBean);//传一个筛选的javabean
                startActivityForResult(i, CAR_FILTER_CODE);
//            ActivitySkipUtil.skipActivity(UC_World_ListActivity.this, UC_World_FiltrateActivity.class);
            }
        } else if (view.getId() == R.id.used_car_rab_price) {//价格
            sortType = 0;
            pageCurrent = 0;
            mUC_WorldProvider.worldList(pageSize, pageCurrent, sortType, 0, pricemin, pricemax, mileage, registercode, vehicletype, country, gearmode); //请求车列表
            isLoadAll = false;
            mListView.getRefreshableView().setSelection(0);
        } else if (view.getId() == R.id.used_car_rab_mileage) {//里程数
            sortType = 1;
            pageCurrent = 0;
            mUC_WorldProvider.worldList(pageSize, pageCurrent, sortType, 0, pricemin, pricemax, mileage, registercode, vehicletype, country, gearmode); //请求车列表
            isLoadAll = false;
            mListView.getRefreshableView().setSelection(0);
        } else if (view.getId() == R.id.used_car_rab_car_age) {//车龄
            sortType = 2;
            pageCurrent = 0;
            mUC_WorldProvider.worldList(pageSize, pageCurrent, sortType, 0, pricemin, pricemax, mileage, registercode, vehicletype, country, gearmode); //请求车列表
            isLoadAll = false;
            mListView.getRefreshableView().setSelection(0);
        }
    }

    /**
     * @desc (处理二手车列表的数据)
     * @createtime 2016/11/25 - 15:43
     * @author xiaoxue
     */
    @Subscriber(tag = FrameHttpTag.CARLIST)
    protected void getCarList(HttpMethod method) {
        ShowListView();
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            JSONArray data = method.data().getJSONArray("data");
            isPullDown = Integer.valueOf((String) method.getParam().get("isPullDown"));
            //清空数据
            if (1 != isPullDown) {
                mListData.clear();
            }
            //判断全部数据是否加载完
            if (data.size() < pageSize) {
                refreshIsRequest = false;//不请求数据
                if (1 == isPullDown) {//上拉
                    toastMsg("已加载全部数据");
                } else {
                    if (data.size() == 0) {
                        ShowNullView("亲,暂时没有内容哦", false, setEmptyViewUtil.NullView);
                    }
                }
            }
            //添加数据
            for (int i = 0; i < data.size(); i++) {
                UC_World_CarListJavaBean mCF_LoanListBean = JSON.toJavaObject((JSON) data.get(i), UC_World_CarListJavaBean.class);
                mListData.add(mCF_LoanListBean);
            }
            if (!mListData.isEmpty()) {
                mListView.setMode(PullToRefreshBase.Mode.BOTH);
            }
            //更新下拉参数
            if (isPullDown == 1) {//上拉
                mListAdapter.updateCarList(mListData);

            } else {
                refreshIsRequest = true;//允许请求数据
                mListAdapter.updateCarList(mListData);
                setIsPullUpRefresh(mListData.size());
            }

        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {//网络
            ShowNullView("亲,网络不给力哦\n点击屏幕重试", true, setEmptyViewUtil.NetworkErrorView);
        } else {//服务
            ShowNullView("亲,您访问的页面找不到了", true, setEmptyViewUtil.NoServerView);
        }
        mListView.onRefreshComplete();
    }


    /**
     * @desc (显示为空视图)
     * @createtime 2016/11/14 0014-16:11
     * @author wukefan
     */
    protected void ShowNullView(String text, boolean isClick, int type) {
        default_empty_view.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
//        mNullImg.setBackgroundResource(image);
//        default_empty_textview.setText(text);
//        default_empty_view.setClickable(isClick);
        setEmptyViewUtil.setView(type, text, isClick);
    }

    /**
     * @desc (显示listview视图)
     * @createtime 2016/11/14 0014-16:11
     * @author wukefan
     */
    protected void ShowListView() {
        mListView.setVisibility(View.VISIBLE);
        default_empty_view.setVisibility(View.GONE);
    }

    /**
     * @desc (根据第一次请求的数据个数判断设置listview是否可上拉加载)
     * @createtime 2016/11/14 0014-16:06
     * @author wukefan
     */
    private void setIsPullUpRefresh(int size) {
        if (size >= pageSize) {
            mListView.setMode(PullToRefreshBase.Mode.BOTH);
            return;
        }
        View listItem = mListAdapter.getView(0, null, mListView);
        listItem.measure(0, 0);
        int height = listItem.getMeasuredHeight();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mHeight = (dm.heightPixels - ((int) ((45 + 42) * dm.density + 0.5f)));
        if (height * size < mHeight) {
            mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }
    }

    /**
     * 处理筛选数据的方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            // 当返回的数据为空时
            return;
        }
        //处理筛选数据
        if (resultCode == UC_World_FiltrateActivity.FILTER_RESULT_CODE) {
            // 从筛选界面获取到数据
            Bundle extras = data.getExtras();
            mUC_FilterBean = (UC_FilterBean) extras.getSerializable(UC_World_FiltrateActivity.FILTER_BEAN);
            setFilterNull();//清空筛选参数
            Map<String, String> vehicleTypeMap = mUC_FilterBean.getVehicleType();
            Map<String, String> carAgeMap = mUC_FilterBean.getCarAge();
            Map<String, String> mileAgeMap = mUC_FilterBean.getMileAge();
            Map<String, String> gearBoxMap = mUC_FilterBean.getGearBox();
            Map<String, String> countryMap = mUC_FilterBean.getCountry();
            Map<String, List<String>> priceMap = mUC_FilterBean.getPrice();
            if (null != vehicleTypeMap && vehicleTypeMap.size() != 0) {//级别
                for (String vehicleTypeName : vehicleTypeMap.keySet()) {
                    vehicletype = vehicleTypeMap.get(vehicleTypeName);
                }
            }
            if (null != carAgeMap && carAgeMap.size() != 0) {//车龄
                for (String carAgeName : carAgeMap.keySet()) {
                    registercode = carAgeMap.get(carAgeName);
                }
            }
            if (null != mileAgeMap && mileAgeMap.size() != 0) {//里程
                for (String mileAgeName : mileAgeMap.keySet()) {
                    mileage = mileAgeMap.get(mileAgeName);
                }

            }
            if (null != gearBoxMap && gearBoxMap.size() != 0) {//变速箱
                for (String gearBoxName : gearBoxMap.keySet()) {
                    gearmode = gearBoxMap.get(gearBoxName);
                }
            }
            if (null != countryMap && countryMap.size() != 0) {//国别
                for (String countryName : countryMap.keySet()) {
                    country = countryMap.get(countryName);
                }
            }
            if (null != priceMap && priceMap.size() != 0) {//价格
                for (String priceName : priceMap.keySet()) {
                    List<String> priceList = (List<String>) priceMap.get(priceName);
                    pricemin = priceList.get(0);
                    pricemax = priceList.get(1);
                }
            }
            pageCurrent = 0;
            mUC_WorldProvider.worldList(pageSize, pageCurrent, sortType, 0, pricemin, pricemax, mileage, registercode, vehicletype, country, gearmode); //请求车列表
        }
    }

    /**
     * @desc 将筛选传过来的参数置为空
     * @createtime 2016/11/2 - 18:40
     * @author xiaoxue
     */
    public void setFilterNull() {
        pricemin = null;
        pricemax = null;
        mileage = null;
        registercode = null;
        vehicletype = null;
        country = null;
        gearmode = null;
    }

}
