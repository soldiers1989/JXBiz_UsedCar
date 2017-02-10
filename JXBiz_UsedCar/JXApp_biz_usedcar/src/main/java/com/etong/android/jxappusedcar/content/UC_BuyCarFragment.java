package com.etong.android.jxappusedcar.content;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.permissions.PermissionsManager;
import com.etong.android.frame.permissions.PermissionsResultAction;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.InputMethodUtil;
import com.etong.android.frame.widget.dialog.UC_CancelConformDialog;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_BuyCarDataOneListAdapter;
import com.etong.android.jxappusedcar.adapter.UC_BuyCarTagAdapter;
import com.etong.android.jxappusedcar.adapter.UC_OrderAdapter;
import com.etong.android.jxappusedcar.adapter.UC_PriceRangeRecycleViewAdapter;
import com.etong.android.jxappusedcar.bean.UC_BrandBean;
import com.etong.android.jxappusedcar.bean.UC_CarListBean;
import com.etong.android.jxappusedcar.bean.UC_FilterBean;
import com.etong.android.jxappusedcar.bean.UC_FilterDataDictionaryBean;
import com.etong.android.jxappusedcar.bean.UC_PriceBean;
import com.etong.android.jxappusedcar.bean.UC_SelectBrand_ExtraBean;
import com.etong.android.jxappusedcar.utils.UCUitls;
import com.etong.android.jxappusedcar.utils.UC_GrandFlowTagLayout;
import com.etong.android.jxappusedcar.utils.UC_SpaceItemDecoration;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 买车页面
 * Created by Dasheng on 2016/10/15.
 */
public class UC_BuyCarFragment extends BaseSubscriberFragment implements
        UC_BuyCarTagAdapter.UC_tagCallBack, UC_BuyCarTagAdapter.UC_CarsetCallBack, UC_OrderAdapter.UC_OrderCallBack, UC_PriceRangeRecycleViewAdapter.UC_PriceCallBack {
    //从主页传过来的TAG值
    public static final String GET_DATABEAN_FROME_HOMEPAGE = "get databean from homepage";
    public static final String UC_BUYCAR_DATABEAN = "buy car data bean";
    public static String TAG_BRAND = "brand";                   // 回调传送过来的品牌Tag值
    public static String TAG_CARSET = "carset";                 // 回调传送过来的车系Tag值
    public static String TAG_PRICE = "price";                   // 回调传送过来的价格Tag值
    public static String TAG_VEHICLE_TYPE = "vehicleType";      // 回调传送过来的车辆类型Tag值
    public static String TAG_CARAGE = "carAge";                 // 回调传送过来的车龄Tag值
    public static String TAG_MILEAGE = "mileAge";               // 回调传送过来的里程Tag值
    public static String TAG_GEARBOX = "gearBox";               // 回调传送过来的变速箱Tag值
    public static String TAG_COUNTRY = "country";               // 回调传送过来的国别Tag值
    public static String TAG_ISAUTHENTICATE = "isauthenticate"; // 回调传送过来的是否认证Tag值
    public static String TAG_COLOR = "color";                   // 回调传送过来的颜色Tag值
    public static int CAR_FILTER_CODE = 123;                    //跳到筛选页的tag值
    public static int CAR_BRAND_CODE = 111;                     //跳到品牌页的tag值
    private CheckBox[] mCheckBox; // 排序，品牌，价格，筛选
    //排序布局 listview adapter
    private ListView mPopOrderListView;
    private UC_OrderAdapter mOrderAdapter;
    //灰色背景
    private View mGrayLayout;
    //价格布局
    private LinearLayout mPriceLayout;
    private RecyclerView mPriceRangeView;
    private int mOrderChoicePos = 0, mPriceChoicePos = 0;       //排序和价格选择记录
    private UC_PriceRangeRecycleViewAdapter mPriceRangeAdapter; //价格的适配器
    private PullToRefreshListView mDataOneRecyclerView;         //显示数据的布局
    private CheckBox mDataTypeView;                             //数据样式选择按钮
    private UC_BuyCarDataOneListAdapter mDataOneAdapter;        //listview适配器
    //请求买车列表所需要的参数
    private String carsetid;                                        //车系id
    private String f_price;                                         //价格id
    private String f_pricemin;                                       //最小价格
    private String f_pricemax;                                       //最大价格
    private String f_mileagemin;                                     //最小里程
    private String f_mileagemax;                                     //最大里程
    private String f_registercodemin;                                //车龄（最小值）
    private String f_registercodemax;                                //车龄（最大值）
    private String f_carbrandid;                                      //品牌编码
    private String sortType;                                          //排序类型
    private String f_gear_mode;                                       //变速器
    private String f_vehicletype;                                     //车辆类型
    private String f_color;                                           //颜色
    private String f_country;                                         //国别
    private String f_isauthenticate;                                  //是否认证
    private UC_UserProvider mUsedCarProvider;
    private int pageSize = 10;                                           //一页显示多少条数据
    private int pageCurrent = 0;                                        //当前页
    private List<UC_CarListBean> mCarList = new ArrayList<>();          //买车列表数据的list
    public static boolean isReqest = true;                              //是否请求  true请求
    private boolean refreshIsRequest = true;                            //刷新是否请求接口  true 请求  false 不请求
    private boolean isInit = false;                                     //是否初始化  false 没初始化  true 初始化
    private boolean isBrand = false;                                    //true 是品牌 false 不是品牌（是车系）
    private String telPhone = "96512";                                  //电话
    //标签的list adapter
    private List<Object> labelList = new ArrayList<>();
    private UC_BuyCarTagAdapter mUC_BuyCarTagAdapter;
    //价格list
    private List<UC_FilterDataDictionaryBean.MapBean> priceList = new ArrayList<UC_FilterDataDictionaryBean.MapBean>();
    //初始化控件
    private EditText min_price;
    private EditText max_price;
    private Button btn_sure_price;
    private ImageView used_car_iv_network;
    private LinearLayout used_car_tag;
    private TextView uc_tv_hint;
    private RelativeLayout uc_rl_accident;
    private RelativeLayout input_layout;
    private LinearLayout choice_layout;
    private Button uc_btn_call_phone;
    private TextView used_car_reset;
    private ViewGroup vpParent;
    private ViewGroup uc_xrv_fill_data_one_content;
    private ViewGroup uc_xrv_fill_data_two_content;
    private UC_GrandFlowTagLayout mUC_GrandFlowTagLayout;
    private UC_CancelConformDialog mDialog;
    private boolean isClickTwo = false;           //记录点击的状态  true 点击(第二种布局) false 没点击(第一种布局)
    private ViewGroup lv_footerView;        // ListView的脚布局的content
    private boolean isResume;

    @Override
    protected View onInit(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.used_car_fragment_buy_car, viewGroup, false);
        mUsedCarProvider = UC_UserProvider.getInstance();
        mUsedCarProvider.initalize(HttpPublisher.getInstance());
        initView(view);
        initData();
        //请求筛选条件
        mUsedCarProvider.filtrateconditions();
        return view;
    }

    /**
     * @desc 初始化view
     * @createtime 2016/11/2 - 18:30
     * @author xiaoxue
     */
    private void initView(View view) {
        vpParent = findViewById(view, R.id.vp_parent, ViewGroup.class);

        mDataTypeView = findViewById(view, R.id.uc_cb_table_switch, CheckBox.class);      //选择两种数据显示模式的按钮
        input_layout = findViewById(view, R.id.uc_rl_input_layout, RelativeLayout.class); //输入框的布局
        choice_layout = findViewById(view, R.id.uc_ll_choice_layout, LinearLayout.class); //四个checkbox的布局
        mCheckBox = new CheckBox[4];
        mCheckBox[0] = findViewById(view, R.id.uc_cb_order, CheckBox.class);//排序
        mCheckBox[1] = findViewById(view, R.id.uc_cb_brand, CheckBox.class);//品牌
        mCheckBox[2] = findViewById(view, R.id.uc_cb_price, CheckBox.class);//价格
        mCheckBox[3] = findViewById(view, R.id.uc_cb_choice, CheckBox.class);//筛选
        mPopOrderListView = findViewById(view, R.id.uc_lv_pop_fill, ListView.class);//排序的popwindow
        mGrayLayout = findViewById(view, R.id.uc_gray_layout, View.class);//灰色背景
        //价格布局
        mPriceLayout = findViewById(view, R.id.uc_ll_price_layout, LinearLayout.class);
        min_price = findViewById(view, R.id.uc_rt_min_price, EditText.class);
        max_price = findViewById(view, R.id.uc_rt_max_price, EditText.class);
        btn_sure_price = findViewById(view, R.id.uc_btn_sure_price, Button.class);
        uc_btn_call_phone = findViewById(view, R.id.uc_btn_call_phone, Button.class);//打电话
        //选择价格的RecyclerView
        mPriceRangeView = findViewById(view, R.id.uc_rv_price_range, RecyclerView.class);
        mPriceRangeView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mPriceRangeView.addItemDecoration(new UC_SpaceItemDecoration(50, 18, 35, 50, 2));
        //标签布局
        used_car_tag = findViewById(view, R.id.used_car_tag, LinearLayout.class);
        mUC_GrandFlowTagLayout = findViewById(view, R.id.used_car_flowTag, UC_GrandFlowTagLayout.class);
        used_car_reset = findViewById(view, R.id.used_car_reset, TextView.class);//重置
        //标签适配器
        mUC_BuyCarTagAdapter = new UC_BuyCarTagAdapter(getActivity(), UC_BuyCarFragment.this, UC_BuyCarFragment.this);
        mUC_GrandFlowTagLayout.setAdapter(mUC_BuyCarTagAdapter);
        uc_xrv_fill_data_one_content = findViewById(view, R.id.uc_xrv_fill_data_one_content, ViewGroup.class);//显示数据的RecyclerView的布局
        initRecycleType(view);
        //显示数据的RecyclerView的适配器
        mDataOneAdapter = new UC_BuyCarDataOneListAdapter(getActivity());
        mDataOneRecyclerView.setAdapter(mDataOneAdapter);
        addClickListener(view, R.id.uc_tv_input_content);
        addClickListener(view, R.id.uc_cb_order);
        addClickListener(view, R.id.uc_cb_brand);
        addClickListener(view, R.id.uc_cb_price);
        addClickListener(view, R.id.uc_cb_choice);
        addClickListener(view, R.id.uc_gray_layout);
        addClickListener(view, R.id.uc_cb_table_switch);
        addClickListener(view, R.id.uc_btn_sure_price);
        addClickListener(view, R.id.uc_btn_call_phone);
        addClickListener(view, R.id.used_car_reset);
        addClickListener(view, R.id.uc_titlebar_back_button);
        phone();
        noDataClick();
    }

    //初始化数据
    private void initData() {
        //清空品牌 价格 筛选的值
        initLabelList();
        //价格的适配器
        mPriceRangeAdapter = new UC_PriceRangeRecycleViewAdapter(getActivity(), priceList, UC_BuyCarFragment.this);
        mPriceRangeView.setAdapter(mPriceRangeAdapter);
        //排序的list 适配器
        mOrderAdapter = new UC_OrderAdapter(getActivity(), UC_BuyCarFragment.this);
        mPopOrderListView.setAdapter(mOrderAdapter);
        mOrderAdapter.notifyDataSetChanged();

        /**得到从首页传过来的javabean*/
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            Object getDataBean = mBundle.getSerializable(UC_BUYCAR_DATABEAN);
            if (getDataBean.getClass() == UC_BrandBean.class) {
                getHomePageBrandTagData((UC_BrandBean) getDataBean);
            } else if (getDataBean.getClass() == UC_PriceBean.class) {
                getHomePagePriceTagData((UC_PriceBean) getDataBean);
            }
        }
    }

    /**
     * 初始化两种不同数据的布局（买车界面右上角按钮显示的不同布局）
     *
     * @param view
     */
    private void initRecycleType(View view) {
        mDataOneRecyclerView = findViewById(view, R.id.uc_xrv_fill_data_one, PullToRefreshListView.class);//填充数据的xrecyclerview
        initFooterView();
        //没网等情况的布局
        uc_rl_accident = findViewById(view, R.id.uc_rl_accident, RelativeLayout.class);
        used_car_iv_network = findViewById(view, R.id.used_car_iv_network, ImageView.class);//提示图片
        uc_tv_hint = findViewById(view, R.id.uc_tv_hint, TextView.class);//文字
        mDataOneRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
        mDataOneRecyclerView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        mDataOneRecyclerView.getLoadingLayoutProxy(false, true).setRefreshingLabel(
                "正在加载更多...");
        mDataOneRecyclerView.getLoadingLayoutProxy(false, true).setReleaseLabel(
                "松开加载更多");

        mDataOneRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshIsRequest) {
                    pageCurrent = 0;
                    mUsedCarProvider.getCarType(pageSize + "", pageCurrent + "", f_price, f_pricemin, f_pricemax,
                            f_mileagemin, f_mileagemax, f_registercodemin, f_registercodemax, f_carbrandid, carsetid, sortType, f_gear_mode, f_vehicletype, f_color, f_country, f_isauthenticate);
                } else {
                    //延迟0.3s
                    mDataOneRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDataOneRecyclerView.onRefreshComplete();
                        }
                    }, 300);
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshIsRequest) {//请求接口
                    Map<String, String> map = new HashMap<>();
                    List keyList1 = Arrays.asList("pageSize", "pageCurrent", "f_price", "f_pricemin", "f_pricemax", "f_mileagemin", "f_mileagemax", "f_registercodemin", "f_registercodemax", "f_carbrandid", "f_carsetid", "sortType", "isPullDown",
                            "f_gear_mode", "f_vehicletype", "f_vehicletype", "f_country", "f_isauthenticate");
                    List valueList1 = Arrays.asList(pageSize + "", (pageCurrent + 1) + "", f_price, f_pricemin, f_pricemax, f_mileagemin, f_mileagemax, f_registercodemin, f_registercodemax, f_carbrandid, carsetid, sortType, 1, f_gear_mode, f_vehicletype, f_vehicletype, f_country, f_isauthenticate);
                    map = UCUitls.fastMapRemoveNullValue(keyList1, valueList1);//另一种请求接口的方式
                    mUsedCarProvider.getCarType(map);
                } else {//不请求接口
                    //延迟1s
                    mDataOneRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDataOneRecyclerView.onRefreshComplete();//刷新完成
                            lv_footerView.setVisibility(View.VISIBLE);
                            mDataOneRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
                        }
                    }, 300);
                }
            }
        });
    }
    /**
     * @desc (为ListView添加FooterView)
     * @createtime 2016/11/7 - 18:42
     * @author xiaoxue
     */
    private void initFooterView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.uc_load_complete_view, null);
        lv_footerView = (ViewGroup) view.findViewById(R.id.uc_ll_buy_car_layout);
        lv_footerView.setVisibility(View.GONE);
        mDataOneRecyclerView.getRefreshableView().addFooterView(view);
    }
    /**
     * @desc 点击排序 价格 品牌 筛选 按钮的处理
     * @createtime 2016/11/7 - 10:41
     * @author xiaoxue
     */
    protected void Click(int position) {
        if (mCheckBox[position].isChecked()) {
            if (position == 0) {
                mPopOrderListView.setVisibility(View.VISIBLE);  //排序布局显示
                mGrayLayout.setVisibility(View.VISIBLE);    //灰色背景显示
                mPriceLayout.setVisibility(View.GONE);  //价格布局隐藏
            } else if (position == 2) {
                mPopOrderListView.setVisibility(View.GONE);
                mGrayLayout.setVisibility(View.VISIBLE);
                mPriceLayout.setVisibility(View.VISIBLE);
            } else if (position == 3 || position == 1) {
                mCheckBox[0].setChecked(false);
                mCheckBox[1].setChecked(false);
                mCheckBox[2].setChecked(false);
                mPopOrderListView.setVisibility(View.GONE);
                mGrayLayout.setVisibility(View.GONE);
                mPriceLayout.setVisibility(View.GONE);
                mCheckBox[3].setChecked(false);
            }
        } else {
            if (position == 0) {
                mPopOrderListView.setVisibility(View.GONE);  //排序布局隐藏
                mGrayLayout.setVisibility(View.GONE);   //灰色背景隐藏
            } else if (position == 2) {
                mPopOrderListView.setVisibility(View.GONE);
                mGrayLayout.setVisibility(View.GONE);
                mPriceLayout.setVisibility(View.GONE);
            }
        }
        for (int i = 0; i < mCheckBox.length; i++) {
            if (i != position) {
                mCheckBox[i].setChecked(false);
            }
        }
    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.uc_tv_input_content) {//搜索
            lv_footerView.setVisibility(View.GONE);
            ActivitySkipUtil.skipActivity(UC_BuyCarFragment.this, UC_SearchActivity.class);
        } else if (view.getId() == R.id.uc_titlebar_back_button) { //返回
            getActivity().finish();
        } else if (view.getId() == R.id.uc_cb_order) { //排序
            mOrderAdapter.update(mOrderChoicePos);//更新位置
            lv_footerView.setVisibility(View.GONE);
            Click(0);
        } else if (view.getId() == R.id.uc_cb_brand) { //品牌
            lv_footerView.setVisibility(View.GONE);
            Click(1);
            //跳转到选择品牌页
            Intent i = new Intent(getActivity(), UC_SelectBrandActivity.class);
            i.putExtra(UC_SelectBrandActivity.SELECT_BRAND_NAME, (UC_BrandBean) labelList.get(0));
            startActivityForResult(i, CAR_BRAND_CODE);
        } else if (view.getId() == R.id.uc_cb_price) {//价格
            lv_footerView.setVisibility(View.GONE);
            Click(2);
            /**记录选中的价格*/
            if (mPriceChoicePos != -1) {
                mPriceChoicePos = 0;
            }
            for (int i = 0; i < priceList.size(); i++) {
                if (null != f_price && f_price.equals(priceList.get(i).getKey())) {
                    mPriceChoicePos = i;
                    break;
                }
            }
            mPriceRangeAdapter.upDate(mPriceChoicePos);//更新位置
        } else if (view.getId() == R.id.uc_btn_sure_price) {//自定义价格确定
            Customprice();
        } else if (view.getId() == R.id.uc_cb_choice) { //筛选
            lv_footerView.setVisibility(View.GONE);
            Click(3);
            //跳转到筛选页
            Intent i = new Intent(getActivity(), UC_AdvancedFilterActivity.class);
            i.putExtra(UC_AdvancedFilterActivity.FILTER_BEAN, (UC_FilterBean) labelList.get(2));//传一个筛选的javabean
            startActivityForResult(i, CAR_FILTER_CODE);
        } else if (view.getId() == R.id.uc_gray_layout) {//灰色背景
            mPopOrderListView.setVisibility(View.GONE);
            mGrayLayout.setVisibility(View.GONE);
            mPriceLayout.setVisibility(View.GONE);
            mCheckBox[0].setChecked(false);
            mCheckBox[2].setChecked(false);
        } else if (view.getId() == R.id.uc_cb_table_switch) {//点击切换显示不同布局
            startAnimtor();
//            if (mDataTypeView.isChecked()) {//点击按钮
//                isClickTwo = true;
//                mDataOneAdapter.upDate(mCarList, isClickTwo);
//            } else {
//                isClickTwo = false;
//                mDataOneAdapter.upDate(mCarList, isClickTwo);
//            }
        } else if (view.getId() == R.id.uc_btn_call_phone) {//打电话
            mDialog.show();
        } else if (view.getId() == R.id.used_car_reset) {//重置
            lv_footerView.setVisibility(View.GONE);
            used_car_tag.setVisibility(View.GONE);
            initLabelList();
            mUC_BuyCarTagAdapter.upListData(labelList);
            setNull();
            mPriceChoicePos = 0;//价格设为不限
            mOrderChoicePos = 0;//排序设为智能
            min_price.setText("");
            max_price.setText("");
            pageCurrent = 0;
            mUsedCarProvider.getCarType(pageSize + "", pageCurrent + "", f_price, f_pricemin, f_pricemax, f_mileagemin, f_mileagemax,
                    f_registercodemin, f_registercodemax, f_carbrandid, carsetid, sortType, f_gear_mode, f_vehicletype, f_color, f_country, f_isauthenticate);
        }
    }

    /**
     * @desc 自定义价格处理方法
     * @createtime 2016/11/7 - 10:03
     * @author xiaoxue
     */
    private void Customprice() {
        if (!TextUtils.isEmpty(min_price.getText().toString()) || !TextUtils.isEmpty(max_price.getText().toString())) {
            mPriceChoicePos = -1;//清空上面选的价格
            if (!TextUtils.isEmpty(min_price.getText().toString())) {
                f_pricemin = min_price.getText().toString();
            } else {
                f_pricemin = null;
            }
            if (!TextUtils.isEmpty(max_price.getText().toString())) {
                f_pricemax = max_price.getText().toString();
            } else {
                f_pricemax = null;
            }
        } else {
            //没有填自定义的价格就不关闭PopOrderListView GrayLayout PriceLayout(不可点击)
            if (TextUtils.isEmpty(min_price.getText().toString()) && TextUtils.isEmpty(max_price.getText().toString())) {
                return;
            }
            if (TextUtils.isEmpty(min_price.getText().toString())) {
                f_pricemin = null;
                btn_sure_price.setClickable(true);
            }
            if (TextUtils.isEmpty(max_price.getText().toString())) {
                f_pricemax = null;
                btn_sure_price.setClickable(true);
            }
        }
        Map<String, List<String>> map = new HashMap();
        List<String> priceList = new ArrayList<String>();
        if (!TextUtils.isEmpty(min_price.getText().toString()) && TextUtils.isEmpty(max_price.getText().toString())) {
            priceList.add(f_pricemin);
            priceList.add(null);
            priceList.add(null);
            map.put(f_pricemin + "万以上", priceList);
        } else if (TextUtils.isEmpty(min_price.getText().toString()) && !TextUtils.isEmpty(max_price.getText().toString())) {
            priceList.add(null);
            priceList.add(f_pricemax);
            priceList.add(null);
            map.put(f_pricemax + "万以下", priceList);
        } else if (!TextUtils.isEmpty(min_price.getText().toString()) && !TextUtils.isEmpty(max_price.getText().toString())) {
            priceList.add(f_pricemin);
            priceList.add(f_pricemax);
            priceList.add(null);
            map.put(f_pricemin + "-" + f_pricemax + "万", priceList);
        }
        UC_PriceBean mUC_PriceBean = new UC_PriceBean(map);
        mEventBus.post(mUC_PriceBean, "get type price data");//传一个价格javabean给处理价格的方法
        InputMethodUtil.hiddenInputMethod(getActivity(), btn_sure_price);//关闭软键盘
        //隐藏价格的布局
        mPopOrderListView.setVisibility(View.GONE);
        mGrayLayout.setVisibility(View.GONE);
        mPriceLayout.setVisibility(View.GONE);
        mCheckBox[2].setChecked(false);   //设置不选中状态
    }

    /**
     * @desc 点击车系的跳转 回调
     * @createtime 2016/10/27 - 19:07
     * @author xiaoxue
     */
    @Override
    public void answerIntent(View view) {
        Intent i = new Intent(getActivity(), UC_SelectBrandActivity.class);
        i.putExtra(UC_SelectBrandActivity.SELECT_BRAND_NAME, (UC_BrandBean) labelList.get(0));//传一个品牌的javabean
        startActivityForResult(i, CAR_BRAND_CODE);
    }

    @Override
    public void onResume() {
        super.onResume();
        isResume =true;
        //对排序 价格显示的布局进行隐藏
        if (mCheckBox[0].isChecked()) {
            mPopOrderListView.setVisibility(View.GONE);  //排序布局隐藏
            mGrayLayout.setVisibility(View.GONE);    //灰色背景隐藏
            mCheckBox[0].setChecked(false);
        }
        if (mCheckBox[2].isChecked()) {
            mPopOrderListView.setVisibility(View.GONE);
            mGrayLayout.setVisibility(View.GONE);
            mPriceLayout.setVisibility(View.GONE);
            mCheckBox[2].setChecked(false);
        }
    }

    /**
     * @desc 得到买车列表数据
     * @createtime 2016/11/3 - 17:49
     * @author xiaoxue
     */
    @Subscriber(tag = UC_HttpTag.CAR_LIST)
    private void carList(HttpMethod method) {
        mDataOneRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        //step1：获取数据
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");
        if (status.equals("true")) {
            JSONObject data = method.data().getJSONObject("data");
            String count = data.getString("carListCount");
            JSONArray arrays = data.getJSONArray("carList");
            Object oj = method.getParam().get("isPullDown");
            int isPullDown = 0;
            if (null != oj) {
                isPullDown = (int) oj;
            }
            //step2：根据刷新动作决定是否清空list：选择排序：清空；下拉刷洗：清空；上拉刷新：不清空
            if (1 != isPullDown) {
                mCarList.clear();
                mDataOneAdapter.clear();
            }
            //step3：将请求到的数据添加至list
            //1.未清空数据，未请求到数据，告知用户已经到底了
            //2.清空数据后，未请求到数据处理
            if (arrays.size() < pageSize) {
                refreshIsRequest = false;//不请求数据
                if (1 == isPullDown) {//上拉
//                    toastMsg("已加载全部数据");
                    lv_footerView.setVisibility(View.VISIBLE);
                    mDataOneRecyclerView.onRefreshComplete();
                    mDataOneRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
                } else {
                    if(arrays.size()==0){
                        ShowView(R.mipmap.uc_ic_no_cotent_img, "亲,暂时没有内容哦", false);
                        mDataOneRecyclerView.onRefreshComplete();
                    }
                    else{
                        lv_footerView.setVisibility(View.VISIBLE);
                        mDataOneRecyclerView.onRefreshComplete();
                        mDataOneRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
                    }
                }
            }
            //3.添加数据
            for (int i = 0; i < arrays.size(); i++) {
                UC_CarListBean set = JSON.toJavaObject((JSON) arrays.get(i), UC_CarListBean.class);
                mCarList.add(set);
            }
            //数据为空的时候
            if (mCarList.isEmpty()) {
                mDataOneRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            }
            //更新下拉参数
            if (isPullDown == 1) {//上拉
                pageCurrent = pageCurrent + 1;
                mDataOneAdapter.upDate(mCarList, isClickTwo);
                mDataOneRecyclerView.onRefreshComplete();
            } else {
                pageCurrent = 0;
                refreshIsRequest = true;//允许请求数据
                mDataOneAdapter.upDate(mCarList, isClickTwo);
                if (!mCarList.isEmpty()) {
                    uc_rl_accident.setVisibility(View.GONE);
                    used_car_iv_network.setVisibility(View.GONE);
                    mDataOneRecyclerView.setVisibility(View.VISIBLE);
                    mDataOneRecyclerView.onRefreshComplete();
                }
            }
        } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {//服务器无响应
            ShowView(R.mipmap.uc_ic_no_network_img, "亲,网络不给力哦\n点击屏幕重试", true);
            mDataOneRecyclerView.onRefreshComplete();
        } else if (status.equals(HttpPublisher.DATA_ERROR)) {//数据请求失败
            ShowView(R.mipmap.uc_ic_no_service_img, "Sorry,您访问的页面找不到了......", false);
            mDataOneRecyclerView.onRefreshComplete();
        }
    }

    /**
     * @desc 得到筛选的数据
     * @createtime 2016/10/19 - 21:31
     * @author xiaoxue
     */
    @Subscriber(tag = UC_HttpTag.FILTRATE)
    private void filtrate(HttpMethod method) {
        mPriceRangeView.setVisibility(View.VISIBLE); //显示价格
        priceList.clear();//清空价格list
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");
        if (status.equals("true")) {
            JSONArray data = method.data().getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {
                UC_FilterDataDictionaryBean mUC_FilterDataDictionaryBean = JSON.toJavaObject((JSON) data.get(i), UC_FilterDataDictionaryBean.class);
                if ("价格".equals(mUC_FilterDataDictionaryBean.getParaName())) {//和得到数据里的价格进行比对
                    priceList.addAll(mUC_FilterDataDictionaryBean.getMap());
                }
            }
        } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {//访问网络失败
            mPriceRangeView.setVisibility(View.GONE);
        } else if (status.equals(HttpPublisher.DATA_ERROR)) {   //请求数据失败
            toastMsg(msg);
        }
    }

    /**
     * @desc 设置没网 没请求到数据的显示处理
     * @createtime 2016/11/1 - 16:42
     * @author xiaoxue
     */
    protected void ShowView(int image, String text, boolean isClick) {
        uc_rl_accident.setVisibility(View.VISIBLE);
        used_car_iv_network.setVisibility(View.VISIBLE);
        used_car_iv_network.setBackgroundResource(image);
        uc_tv_hint.setText(text);
        mDataOneRecyclerView.setVisibility(View.GONE);
        uc_rl_accident.setClickable(isClick);
    }

    /**
     * @desc 打电话
     * @createtime 2016/10/24 - 11:23
     * @author xiaoxue
     */
    private void callPhoneDialog() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri
                    .parse("tel:" + telPhone));
            startActivity(intent);
        } catch (SecurityException e) {
        }
        mDialog.dismiss();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            if (isInit) {
                if (null != mCheckBox) {
                    if (mCheckBox[0].isChecked()) {
                        mPopOrderListView.setVisibility(View.GONE);  //排序布局隐藏
                        mGrayLayout.setVisibility(View.GONE);        //灰色背景隐藏
                        mCheckBox[0].setChecked(false);             //设置排序不选中
                    }
                    if (mCheckBox[2].isChecked()) {
                        mPopOrderListView.setVisibility(View.GONE);
                        mGrayLayout.setVisibility(View.GONE);
                        mPriceLayout.setVisibility(View.GONE);
                        mCheckBox[2].setChecked(false);
                    }
                }
//                if(null!=lv_footerView){
//                    lv_footerView.setVisibility(View.GONE);
//                }
            }
            if(!isResume){//切换请求  onResume不请求
                if(null!=lv_footerView){
                    lv_footerView.setVisibility(View.GONE);
                }
                pageCurrent = 0;
                mUsedCarProvider.getCarType(pageSize + "", pageCurrent + "", f_price, f_pricemin, f_pricemax, f_mileagemin,
                        f_mileagemax, f_registercodemin, f_registercodemax, f_carbrandid, carsetid, sortType, f_gear_mode,
                        f_vehicletype, f_color, f_country, f_isauthenticate);
            }
            isResume=false;
        }
        //没有初始化就初始化UsedCarProvider
        if (!isInit) {
            mUsedCarProvider = UC_UserProvider.getInstance();
            mUsedCarProvider.initalize(HttpPublisher.getInstance());
            isInit = true;
        }
    }

    /**
     * @desc 适配器item为空时处理方法
     * @createtime 2016/10/25 - 17:51
     * @author xiaoxue
     */
    @Subscriber(tag = "list data is null")
    protected void getInfo(String str) {
        lv_footerView.setVisibility(View.GONE);
        used_car_tag.setVisibility(View.GONE);//标签布局不显示
        setNull();
        pageCurrent = 0;
        mUsedCarProvider.getCarType(pageSize + "", pageCurrent + "", f_price, f_pricemin, f_pricemax, f_mileagemin, f_mileagemax, f_registercodemin, f_registercodemax, f_carbrandid, carsetid, sortType, f_gear_mode, f_vehicletype, f_color, f_country, f_isauthenticate);
    }

    /**
     * @desc 价格传来的值
     * @createtime 2016/10/26 - 12:34
     * @author xiaoxue
     */
    @Subscriber(tag = "get type price data")
    protected void getTagPriceData(UC_PriceBean mUC_PriceBean) {
        //得到传过来的价格数据
        Map<String, List<String>> priceMap = mUC_PriceBean.getPrice();
        if (null != priceMap && priceMap.size() != 0) {
            for (String priceName : priceMap.keySet()) {
                List<String> priceList = (List<String>) priceMap.get(priceName);
                //选的价格
                f_price = priceList.get(2);
                //自定义的价格
                f_pricemin = priceList.get(0);
                f_pricemax = priceList.get(1);
                used_car_tag.setVisibility(View.VISIBLE);
            }
        } else {
            f_price = null;
            f_pricemin = null;
            f_pricemax = null;
        }
        labelList.set(1, mUC_PriceBean);
        mUC_BuyCarTagAdapter.upListData(labelList);
        if (mUC_BuyCarTagAdapter.isEmpty()) {//数据为空
            used_car_tag.setVisibility(View.GONE);
        }
        pageCurrent = 0;
        mUsedCarProvider.getCarType(pageSize + "", pageCurrent + "", f_price, f_pricemin, f_pricemax, f_mileagemin, f_mileagemax,
                f_registercodemin, f_registercodemax, f_carbrandid, carsetid, sortType, f_gear_mode,
                f_vehicletype, f_color, f_country, f_isauthenticate);
    }

    /**
     * @desc 处理搜索传过来的值
     * @createtime 2016/10/25 - 16:38
     * @author xiaoxue
     */
    @Subscriber(tag = "get type search data")
    protected void getTagData(UC_BrandBean mUC_BrandBean) {
        //清空请求参数
        initLabelList();
        setNull();
        mOrderChoicePos = 0;//设置默认选中智能排序
        mPriceChoicePos = 0;//默认选中不限
        //得到品牌 车系的数据
        Map<String, String> brandMap = mUC_BrandBean.getBrand();
        Map<String, String> carsetMap = mUC_BrandBean.getCarset();
        if (null != brandMap && brandMap.size() != 0) {//品牌
            for (String brandName : brandMap.keySet()) {
                f_carbrandid = brandMap.get(brandName);
                carsetid = null;
            }
        }
        if (null != carsetMap && carsetMap.size() != 0) {//车系
            for (String carsetName : carsetMap.keySet()) {
                carsetid = carsetMap.get(carsetName);
                f_carbrandid = null;
            }
        }
        used_car_tag.setVisibility(View.VISIBLE);
        labelList.set(0, mUC_BrandBean);//把javabean设置到大的list里
        mUC_BuyCarTagAdapter.upListData(labelList);//更新适配器
        pageCurrent = 0;
        mUsedCarProvider.getCarType(pageSize + "", pageCurrent + "", f_price, f_pricemin, f_pricemax, f_mileagemin, f_mileagemax, f_registercodemin, f_registercodemax, f_carbrandid, carsetid, sortType, f_gear_mode, f_vehicletype, f_color, f_country, f_isauthenticate);
    }

    /**
     * @desc 处理首页品牌传过来的值
     * @createtime 2016/10/25 - 16:38
     * @author xiaoxue
     */
    @Subscriber(tag = "home page brand")
    public void getHomePageBrandTagData(UC_BrandBean mUC_BrandBean) {
        initLabelList(); //清空请求参数
        setNull();
        mOrderChoicePos = 0;//设置默认选中
        mPriceChoicePos = 0;
        Map<String, String> brandMap = mUC_BrandBean.getBrand();
        Map<String, String> carsetMap = mUC_BrandBean.getCarset();
        if (null != brandMap && brandMap.size() != 0) {
            for (String brandName : brandMap.keySet()) {
                f_carbrandid = brandMap.get(brandName);
                carsetid = null;
            }
        }
        if (null != carsetMap && carsetMap.size() != 0) {
            for (String carsetName : carsetMap.keySet()) {
                carsetid = carsetMap.get(carsetName);
                f_carbrandid = null;
            }
        }
        used_car_tag.setVisibility(View.VISIBLE);
        labelList.set(0, mUC_BrandBean);
        mUC_BuyCarTagAdapter.upListData(labelList);
        pageCurrent = 0;
        mUsedCarProvider.getCarType(pageSize + "", pageCurrent + "", f_price, f_pricemin, f_pricemax, f_mileagemin, f_mileagemax, f_registercodemin, f_registercodemax, f_carbrandid, carsetid, sortType, f_gear_mode, f_vehicletype, f_color, f_country, f_isauthenticate);
    }

    /**
     * @desc 处理首页价格传过来的值
     * @createtime 2016/10/25 - 16:38
     * @author xiaoxue
     */
    @Subscriber(tag = "home page price")
    protected void getHomePagePriceTagData(UC_PriceBean mUC_PriceBean) {
        initLabelList();
        setNull();
        mOrderChoicePos = 0;
        Map<String, List<String>> priceMap = mUC_PriceBean.getPrice();
        if (null != priceMap && priceMap.size() != 0) {
            for (String priceName : priceMap.keySet()) {
                List<String> priceLists = (List<String>) priceMap.get(priceName);
                //选的价格
                f_price = priceLists.get(2);
            }
        }
        used_car_tag.setVisibility(View.VISIBLE);
        labelList.set(1, mUC_PriceBean);
        mUC_BuyCarTagAdapter.upListData(labelList);
        pageCurrent = 0;
        mUsedCarProvider.getCarType(pageSize + "", pageCurrent + "", f_price, f_pricemin, f_pricemax, f_mileagemin, f_mileagemax, f_registercodemin, f_registercodemax, f_carbrandid, carsetid, sortType, f_gear_mode, f_vehicletype, f_color, f_country, f_isauthenticate);
    }

    /**
     * @desc 将请求的参数设置为空的方法
     * @createtime 2016/10/25 - 17:04
     * @author xiaoxue
     */
    public void setNull() {
        f_price = null;
        f_pricemin = null;
        f_pricemax = null;
        f_carbrandid = null;
        carsetid = null;
        sortType = null;
        setFilterNull();
    }

    /**
     * @desc 将筛选传过来的参数置为空
     * @createtime 2016/11/2 - 18:40
     * @author xiaoxue
     */
    public void setFilterNull() {
        f_mileagemin = null;
        f_mileagemax = null;
        f_registercodemin = null;
        f_registercodemax = null;
        f_gear_mode = null;
        f_vehicletype = null;
        f_color = null;
        f_country = null;
        f_isauthenticate = null;
    }

    /**
     * @desc 删除某条标签的回调
     * @createtime 2016/10/26 - 15:22
     * @author xiaoxue
     */
    @Override
    public void answerTag(String tag) {
        switch (tag) {
            case "brand"://品牌
                f_carbrandid = null;
                UC_BrandBean brandBean = (UC_BrandBean) labelList.get(0);
                brandBean.setBrand(null);
                if (null != brandBean.getCarset()) {
                    brandBean.setCarset(null);
                }
                labelList.set(0, brandBean);
                break;
            case "carset"://车系
                carsetid = null;
                UC_BrandBean carsetBean = (UC_BrandBean) labelList.get(0);
                carsetBean.setCarset(null);
                labelList.set(0, carsetBean);
                break;
            case "price"://价格
                f_price = null;
                f_pricemin = null;
                f_pricemax = null;
                mPriceChoicePos = 0;//价格设为不限
                min_price.setText("");
                max_price.setText("");
                UC_PriceBean priceBean = (UC_PriceBean) labelList.get(1);
                priceBean.setPrice(null);
                labelList.set(1, priceBean);
                break;
            case "vehicleType"://车辆类型
                f_vehicletype = null;
                UC_FilterBean vehicleTypeBean = (UC_FilterBean) labelList.get(2);
                vehicleTypeBean.setVehicleType(null);
                labelList.set(2, vehicleTypeBean);
                break;
            case "carAge"://车龄
                f_registercodemin = null;
                f_registercodemax = null;
                UC_FilterBean carAgeBean = (UC_FilterBean) labelList.get(2);
                carAgeBean.setCarAge(null);
                labelList.set(2, carAgeBean);
                break;
            case "mileAge"://里程
                f_mileagemin = null;
                f_mileagemax = null;
                UC_FilterBean mileAgeBean = (UC_FilterBean) labelList.get(2);
                mileAgeBean.setMileAge(null);
                labelList.set(2, mileAgeBean);
                break;
            case "gearBox"://变速箱
                f_gear_mode = null;
                UC_FilterBean gearBoxBean = (UC_FilterBean) labelList.get(2);
                gearBoxBean.setGearBox(null);
                labelList.set(2, gearBoxBean);
                break;
            case "country"://国别
                f_country = null;
                UC_FilterBean countryBean = (UC_FilterBean) labelList.get(2);
                countryBean.setCountry(null);
                labelList.set(2, countryBean);
                break;
            case "isauthenticate"://认证情况
                f_isauthenticate = null;
                UC_FilterBean isauthenticateBean = (UC_FilterBean) labelList.get(2);
                isauthenticateBean.setIsauthenticate(null);
                labelList.set(2, isauthenticateBean);
                break;
            case "color"://颜色
                f_color = null;
                UC_FilterBean colorBean = (UC_FilterBean) labelList.get(2);
                colorBean.setColor(null);
                labelList.set(2, colorBean);
                break;
        }
        mUC_BuyCarTagAdapter.upListData(labelList);
        if (mUC_BuyCarTagAdapter.isEmpty()) {
            mOrderChoicePos = 0;//排序设置为智能
            EventBus.getDefault().post("", "list data is null");
        }
        pageCurrent = 0;
        mUsedCarProvider.getCarType(pageSize + "", pageCurrent + "", f_price, f_pricemin, f_pricemax, f_mileagemin, f_mileagemax,
                f_registercodemin, f_registercodemax, f_carbrandid, carsetid, sortType, f_gear_mode,
                f_vehicletype, f_color, f_country, f_isauthenticate);
    }

    /**
     * 清空品牌 价格 筛选的值
     */
    private void initLabelList() {
        labelList.clear();
        UC_BrandBean brand = new UC_BrandBean(null, null);
        labelList.add(brand);
        UC_PriceBean price = new UC_PriceBean(null);
        labelList.add(price);
        UC_FilterBean filter = new UC_FilterBean();
        labelList.add(filter);
    }

    /**
     * 处理筛选 品牌 数据的方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            // 当返回的数据为空时
            return;
        }
        //处理筛选数据
        if (resultCode == UC_AdvancedFilterActivity.FILTER_RESULT_CODE) {
            // 从筛选界面获取到数据
            Bundle extras = data.getExtras();
            UC_FilterBean mUC_FilterBean = (UC_FilterBean) extras.getSerializable(UC_AdvancedFilterActivity.FILTER_BEAN);
            setFilterNull();//清空筛选参数
            boolean isSelect = false;//标识筛选页选中 或没选中的  true选中 false 没选中
            Map<String, String> vehicleTypeMap = mUC_FilterBean.getVehicleType();
            Map<String, List<String>> carAgeMap = mUC_FilterBean.getCarAge();
            Map<String, List<String>> mileAgeMap = mUC_FilterBean.getMileAge();
            Map<String, String> gearBoxMap = mUC_FilterBean.getGearBox();
            Map<String, String> countryMap = mUC_FilterBean.getCountry();
            Map<String, String> isauthenticateMap = mUC_FilterBean.getIsauthenticate();
            Map<String, String> colorMap = mUC_FilterBean.getColor();
            if (null != vehicleTypeMap && vehicleTypeMap.size() != 0) {//车辆类型
                for (String vehicleTypeName : vehicleTypeMap.keySet()) {
                    f_vehicletype = vehicleTypeMap.get(vehicleTypeName);
                }
                isSelect = true;
            }
            if (null != carAgeMap && carAgeMap.size() != 0) {//车龄
                for (String carAgeName : carAgeMap.keySet()) {
                    List<String> carAgeList = (List<String>) carAgeMap.get(carAgeName);
                    f_registercodemin = carAgeList.get(0);
                    f_registercodemax = carAgeList.get(1);
                }
                isSelect = true;
            }
            if (null != mileAgeMap && mileAgeMap.size() != 0) {//里程
                for (String mileAgeName : mileAgeMap.keySet()) {
                    List<String> mileAgeList = (List<String>) mileAgeMap.get(mileAgeName);
                    f_mileagemin = mileAgeList.get(0);
                    f_mileagemax = mileAgeList.get(1);
                }
                isSelect = true;
            }
            if (null != gearBoxMap && gearBoxMap.size() != 0) {//变速箱
                for (String gearBoxName : gearBoxMap.keySet()) {
                    f_gear_mode = gearBoxMap.get(gearBoxName);
                }
                isSelect = true;
            }
            if (null != countryMap && countryMap.size() != 0) {//国别
                for (String countryName : countryMap.keySet()) {
                    f_country = countryMap.get(countryName);
                }
                isSelect = true;
            }
            if (null != isauthenticateMap && isauthenticateMap.size() != 0) {//是否认证
                for (String isauthenticateName : isauthenticateMap.keySet()) {
                    f_isauthenticate = isauthenticateMap.get(isauthenticateName);
                }
                isSelect = true;
            }
            if (null != colorMap && colorMap.size() != 0) {//颜色
                for (String colorName : colorMap.keySet()) {
                    f_color = colorMap.get(colorName);
                }
                isSelect = true;
            }
            if (isSelect) {
                used_car_tag.setVisibility(View.VISIBLE);
            }
            labelList.set(2, mUC_FilterBean);
            mUC_BuyCarTagAdapter.upListData(labelList);
            if (mUC_BuyCarTagAdapter.isEmpty()) {
                used_car_tag.setVisibility(View.GONE);
            }
            pageCurrent = 0;
            mUsedCarProvider.getCarType(pageSize + "", pageCurrent + "", f_price, f_pricemin,
                    f_pricemax, f_mileagemin, f_mileagemax, f_registercodemin, f_registercodemax,
                    f_carbrandid, carsetid, sortType, f_gear_mode, f_vehicletype,
                    f_color, f_country, f_isauthenticate);
        }
        //处理品牌数据
        else if (resultCode == UC_SelectBrandActivity.SELECT_BRAND_RESULT_CODE) {
            f_carbrandid = null;//置空
            carsetid = null;
            Bundle extras = data.getExtras();// 从筛选界面获取到数据
            if (null != extras) {
                UC_SelectBrand_ExtraBean mUC_SelectBrand_ExtraBean = (UC_SelectBrand_ExtraBean) extras.getSerializable(UC_SelectBrandActivity.SELECT_BRAND_NAME);
                if (null != mUC_SelectBrand_ExtraBean) {
                    carsetid = null;
                    Map<String, String> brandMap = new HashMap<>();
                    Map<String, String> carsetMap = new HashMap<>();
                    brandMap.put(mUC_SelectBrand_ExtraBean.getBrandName(), mUC_SelectBrand_ExtraBean.getBrandId() + "");
                    if (null != mUC_SelectBrand_ExtraBean.getCarsetName()) {
                        carsetMap.put(mUC_SelectBrand_ExtraBean.getCarsetName(), mUC_SelectBrand_ExtraBean.getCarsetId() + "");
                    }
                    UC_BrandBean mUC_BrandBean = new UC_BrandBean(brandMap, carsetMap);
                    if (null != brandMap && brandMap.size() != 0) {
                        for (String brandName : brandMap.keySet()) {
                            f_carbrandid = brandMap.get(brandName);
                        }
                    }
                    if (null != carsetMap && carsetMap.size() != 0) {
                        for (String carsetName : carsetMap.keySet()) {
                            carsetid = carsetMap.get(carsetName);
                        }
                    }
                    used_car_tag.setVisibility(View.VISIBLE);
                    labelList.set(0, mUC_BrandBean);
                    mUC_BuyCarTagAdapter.upListData(labelList);
                }
            } else {
                UC_BrandBean brandBean = (UC_BrandBean) labelList.get(0);
                brandBean.setBrand(null);//清空操作
                brandBean.setCarset(null);
                labelList.set(0, brandBean);
                mUC_BuyCarTagAdapter.upListData(labelList);
            }
            if (mUC_BuyCarTagAdapter.isEmpty()) {
                used_car_tag.setVisibility(View.GONE);//标签布局不显示
            }
            pageCurrent = 0;
            mUsedCarProvider.getCarType(pageSize + "", pageCurrent + "", f_price, f_pricemin,
                    f_pricemax, f_mileagemin, f_mileagemax, f_registercodemin, f_registercodemax,
                    f_carbrandid, carsetid, sortType, f_gear_mode, f_vehicletype,
                    f_color, f_country, f_isauthenticate);
        }
    }

    /**
     * @desc //没网等情况的点击事件处理
     * @createtime 2016/11/4 - 13:46
     * @author xiaoxue
     */
    private void noDataClick() {
        uc_rl_accident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageCurrent = 0;
                mUsedCarProvider.getCarType(pageSize + "", pageCurrent + "", f_price, f_pricemin, f_pricemax, f_mileagemin,
                        f_mileagemax, f_registercodemin, f_registercodemax, f_carbrandid, carsetid, sortType, f_gear_mode,
                        f_vehicletype, f_color, f_country, f_isauthenticate);
                uc_rl_accident.setVisibility(View.GONE);
                mDataOneRecyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * @desc 打电话
     * @createtime 2016/11/4 - 13:00
     * @author xiaoxue
     */
    private void phone() {
        mDialog = new UC_CancelConformDialog(getActivity(), false);
        mDialog.setTitle("提示");
        mDialog.setContent("确定拨打电话：" + telPhone + "吗？");
        mDialog.setContentSize(15);
        mDialog.setConfirmButtonClickListener("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //确定操作
                PermissionsManager.getInstance()
                        .requestPermissionsIfNecessaryForResult(
                                UC_BuyCarFragment.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                new PermissionsResultAction() {
                                    @Override
                                    public void onGranted() {
                                        callPhoneDialog();
                                    }

                                    @Override
                                    public void onDenied(String permission) {
                                        toastMsg("授权失败，无法完成操作！");
                                        return;
                                    }
                                });
            }
        });
        mDialog.setCancleButtonClickListener("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    /**
     * @desc 排序的回调处理
     * @createtime 2016/11/7 - 14:14
     * @author xiaoxue
     */
    @Override
    public void OrderIntent(int position, String str) {
        sortType = str;
        mOrderChoicePos = position;
        mPopOrderListView.setVisibility(View.GONE);
        mCheckBox[0].setChecked(false);
        mGrayLayout.setVisibility(View.GONE);
        mUsedCarProvider.getCarType(pageSize + "", 0 + "", f_price, f_pricemin, f_pricemax, f_mileagemin, f_mileagemax, f_registercodemin, f_registercodemax, f_carbrandid, carsetid, sortType, f_gear_mode, f_vehicletype, f_color, f_country, f_isauthenticate);
    }

    /**
     * @desc 价格的回调处理
     * @createtime 2016/11/7 - 15:33
     * @author xiaoxue
     */
    @Override
    public void PriceIntent(int position, String key) {
        f_price = key;
        mPriceChoicePos = position; //记录位置
        mCheckBox[2].setChecked(false);   //未选中状态
        mGrayLayout.setVisibility(View.GONE);  //隐藏阴影
        mPriceLayout.setVisibility(View.GONE); //隐藏弹出布局
        //清空下面自定义的价格
        min_price.setText("");
        max_price.setText("");
    }

    //二手车页跳到买车页进行请求数据的处理
    public static final String BUY_CAR_IS_REQUEST = "buy car is request";
    public void isRequest() {
        //请求买车列表数据
        pageCurrent = 0;
        mUsedCarProvider.getCarType(pageSize + "", pageCurrent + "", f_price, f_pricemin, f_pricemax, f_mileagemin, f_mileagemax,
                f_registercodemin, f_registercodemax, f_carbrandid, carsetid, sortType, f_gear_mode, f_vehicletype, f_color, f_country,
                f_isauthenticate);
    }

    public void startAnimtor() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(vpParent,"rotationY",0,360);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mDataTypeView.isChecked()) {//点击按钮
                    isClickTwo = true;
                    mDataOneAdapter.upDate(mCarList, isClickTwo);
                } else {
                    isClickTwo = false;
                    mDataOneAdapter.upDate(mCarList, isClickTwo);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(2000);
        animator.start();
    }
}
