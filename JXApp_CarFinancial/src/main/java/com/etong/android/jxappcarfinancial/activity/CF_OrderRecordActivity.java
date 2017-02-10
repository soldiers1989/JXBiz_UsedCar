package com.etong.android.jxappcarfinancial.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappcarfinancial.CF_Provider;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.adapter.CF_OrderRecordListAdapter;
import com.etong.android.jxappcarfinancial.bean.CF_AllOrderRecordBean;
import com.etong.android.jxappcarfinancial.bean.CF_OrderRecordBean;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wukefan
 * @desc (订单记录界面)
 * @createtime 2016/11/21 0021-9:57
 */
public class CF_OrderRecordActivity extends BaseSubscriberActivity {

    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    private int cbWidth = 0;                            //选择分类的checkbox按钮的宽度
    private List<CF_AllOrderRecordBean> categoryList;   //选择分类的数据
    private int ruequestType = -1;                      //上次请求的type


    private static final String NEW_CAR = "newTotal";                                 //新车类订单
    private static final String AUTOMOBILE_FINANCE = "financialTotal";                //汽车金融订单
    private static final String ETC = "etcTotal";                                     //ETC类订单
    private static final String INSURANCE = "bxTotal";                                //保险类订单
    private static final String ONLINE_BUY_CAR = "onlineTotal";                       //在线购车订单
    private static final String USED_CAR = "secordTotal";                             //二手车订单
    private static final String PASSENGER_TRAFFIC = "kyTotal";                         //客运类订单
    private static final String COMMUNITY_SERVICES = "sqTotal";                       //社区服务类订单

    /**
     * 订单大类型标识
     * "newTotal"-新车类订单,"financialTotal"-汽车金融订单,"etcTotal"-ETC类订单,
     * "bxTotal"-保险类订单,"onlineTotal"-在线购车订单,"secordTotal"-二手车订单,
     * "kyTotal"-客运类订单,"sqTotal"-社区服务类订单
     */
    private String flag = AUTOMOBILE_FINANCE;

    private View emptyListView;                                     //ListView为空视图
    private ViewGroup emptyListContent;                             //ListView为空布局
    private TextView emptyListTxt;                                  //ListView为空显示的文字
    private ListAdapter<CF_AllOrderRecordBean> mPopListAdapter;     //选择类型的ppw的适配器
    private TextView mClassesTxt;                                   //订单大类型
    private CheckBox mSelectCb;                                     //选择分类的按钮
    private PopupWindow mPopup;                                     //选择分类的下拉框
    private ViewGroup mTitle;                                       //订单的title栏

    private List<CF_OrderRecordBean> mListData = new ArrayList<>();
    private boolean isInitRefresh = false;                          //是否可以上拉加载
    private int pageCurrent = 0;
    private int pageSize = 13;
    private TitleBar mTltleBar;
    private CF_Provider mProvider;
    private HttpPublisher mHttpPublisher;
    private PullToRefreshListView mPullListView;
    private CF_OrderRecordListAdapter mListAdapter;
    private ImageView emptyListImg;
    private SetEmptyViewUtil setEmptyViewUtil;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.cf_activity_order_record);

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

        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mProvider = CF_Provider.getInstance();
        mProvider.initialize(mHttpPublisher);

        mTltleBar = new TitleBar(this);
        mTltleBar.setmTitleBarBackground("#252e3d");
        mTltleBar.setTitleTextColor("#ffffff");
        mTltleBar.setTitle("订单记录");

        Intent intent = getIntent();
        if (null != intent.getStringExtra("flag")) {
            flag = intent.getStringExtra("flag");     //订单大类型标识
        }

        mTitle = findViewById(R.id.cf_ll_record_head, ViewGroup.class);
        mClassesTxt = findViewById(R.id.cf_order_record_txt_classes, TextView.class);       //订单类别
        mSelectCb = findViewById(R.id.cf_order_record_cb_item, CheckBox.class);             //选择分类的按钮
        addClickListener(mSelectCb);

        mPullListView = findViewById(R.id.cf_order_record_lv, PullToRefreshListView.class);
        mPullListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);//上拉

        mPullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {//上拉
                mProvider.addTotalOrderForApp(flag, ruequestType, ++pageCurrent, pageSize);
            }
        });

        //头部：title布局
//        View headView = LayoutInflater.from(this).inflate(R.layout.cf_list_head_order_record, null);
//        mPullListView.getRefreshableView().addHeaderView(headView);

        // 设置数据为空的ListView显示
        emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        emptyListContent = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        emptyListTxt = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        emptyListImg = (ImageView) emptyListView.findViewById(R.id.default_empty_img);
        setEmptyViewUtil = new SetEmptyViewUtil(emptyListContent, emptyListImg, emptyListTxt);
        emptyListContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 从网络中获取到数据
                if (ruequestType != -1) {
                    isInitRefresh = false;
                    pageCurrent = 0;
                    mProvider.addTotalOrderForApp(flag, ruequestType, pageCurrent, pageSize);
                }
            }
        });
        ((ViewGroup) mPullListView.getRefreshableView().getParent()).addView(emptyListView);
        mPullListView.getRefreshableView().setEmptyView(emptyListView);

        mListAdapter = new CF_OrderRecordListAdapter(this);
        mPullListView.setAdapter(mListAdapter);

        //获取checkbox的宽度
        ViewTreeObserver vto2 = mSelectCb.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mSelectCb.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                cbWidth = mSelectCb.getWidth();
                setPopupWindow();
            }
        });
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
        categoryList = new ArrayList<>();
        switch (flag) {
            //新车类订单
            case NEW_CAR:
                mClassesTxt.setText("新车类订单:");
                CF_AllOrderRecordBean testDrive = new CF_AllOrderRecordBean(0, 0, "试驾预约");
                categoryList.add(testDrive);
                CF_AllOrderRecordBean book = new CF_AllOrderRecordBean(1, 0, "订购预约");
                categoryList.add(book);
                CF_AllOrderRecordBean maintenance = new CF_AllOrderRecordBean(2, 0, "维保预约");
                categoryList.add(maintenance);
                mSelectCb.setText(categoryList.get(0).getItemName());
                mProvider.addTotalOrderForApp(flag, categoryList.get(0).getItemId(), pageCurrent, pageSize);
                break;
            //汽车金融订单
            case AUTOMOBILE_FINANCE:
                mClassesTxt.setText("汽车金融类订单:");
                CF_AllOrderRecordBean carLoans = new CF_AllOrderRecordBean(3, 0, "车贷申请");
                categoryList.add(carLoans);
                CF_AllOrderRecordBean financeLease = new CF_AllOrderRecordBean(4, 0, "融资租赁申请");
                categoryList.add(financeLease);
                CF_AllOrderRecordBean happyWallet = new CF_AllOrderRecordBean(5, 0, "畅通钱包申请");
                categoryList.add(happyWallet);
                CF_AllOrderRecordBean carDraw = new CF_AllOrderRecordBean(6, 0, "车辆撤押申请");
                categoryList.add(carDraw);
                mSelectCb.setText(categoryList.get(0).getItemName());
                mProvider.addTotalOrderForApp(flag, categoryList.get(0).getItemId(), pageCurrent, pageSize);
                break;
            //ETC类订单
            case ETC:
                break;
            //保险类订单
            case INSURANCE:
                break;
            //在线购车订单
            case ONLINE_BUY_CAR:
                mClassesTxt.setText("在线购车订单:");
                CF_AllOrderRecordBean askBottomPrice = new CF_AllOrderRecordBean(7, 0, "询底价");
                categoryList.add(askBottomPrice);
                mSelectCb.setText(categoryList.get(0).getItemName());
                mProvider.addTotalOrderForApp(flag, categoryList.get(0).getItemId(), pageCurrent, pageSize);
                break;
            //二手车订单
            case USED_CAR:
                mClassesTxt.setText("二手车订单:");
                CF_AllOrderRecordBean buyCar = new CF_AllOrderRecordBean(8, 0, "预约买车");
                categoryList.add(buyCar);
                CF_AllOrderRecordBean sellCar = new CF_AllOrderRecordBean(9, 0, "预约卖车");
                categoryList.add(sellCar);
                mSelectCb.setText(categoryList.get(0).getItemName());
                mProvider.addTotalOrderForApp(flag, categoryList.get(0).getItemId(), pageCurrent, pageSize);
                break;
            //客运类订单
            case PASSENGER_TRAFFIC:
                break;
            //社区服务类订单
            case COMMUNITY_SERVICES:
                break;
        }
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
        if (view.getId() == R.id.cf_order_record_cb_item) {//选择分类
            if (mSelectCb.isChecked()) {
                showPopupWindow(mSelectCb);
            } else {
                mPopup.dismiss();
            }
        }
    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    /**
     * @desc (申请进度获取的数据)
     * @createtime 2016/11/23 0023-14:22
     * @author wukefan
     */
    @Subscriber(tag = FrameHttpTag.ADD_TOTAL_ORDER_FOR_APP)
    protected void addTotalOrderResult(HttpMethod method) {
        mPullListView.onRefreshComplete();
        mPullListView.setVisibility(View.VISIBLE);
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        ruequestType = Integer.valueOf((String) method.getParam().get("f_ordertype"));

        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            JSONArray jsonArr = method.data().getJSONArray("data");

            if (pageCurrent == 0) {
                mListData.clear();
            }
            if (0 != jsonArr.size()) {
                for (int i = 0; i < jsonArr.size(); i++) {
                    CF_OrderRecordBean mOrderRecordBean = JSON.toJavaObject(jsonArr.getJSONObject(i), CF_OrderRecordBean.class);
                    mListData.add(mOrderRecordBean);
                }

            } else {
                if (isInitRefresh) {
                    toastMsg("已加载全部");
                }
                mPullListView.setMode(PullToRefreshBase.Mode.DISABLED);
            }

            if (mListData.isEmpty()) {
                setNullData("", false, setEmptyViewUtil.NullView);
            } else {
                mTitle.setVisibility(View.VISIBLE);
            }
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
            mListData.clear();
            setNullData("点击屏幕重试", true, setEmptyViewUtil.NetworkErrorView);
        } else {
            mListData.clear();
            setNullData("Sorry,您访问的页面找不到了......", false, setEmptyViewUtil.NoServerView);
        }

        mListAdapter.updateDatas(mListData);
        if (!isInitRefresh) {
            initIsRefresh(mListData.size());
            isInitRefresh = true;
        }
    }

    /**
     * 设置订单分类列表
     */
    private void setPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.cf_popup_order_record_category, null);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        ListView mListView = (ListView) view.findViewById(R.id.cf_txt_or_category_lv);

        mPopListAdapter = new ListAdapter<CF_AllOrderRecordBean>(this, R.layout.cf_list_order_record_category_item) {
            @Override
            protected void onPaint(View view, final CF_AllOrderRecordBean data, int position) {
                TextView mTitleItem = findViewById(view, R.id.cf_txt_or_txt_category_tltle, TextView.class);

                //设置选中的是哪一类
                mTitleItem.setText(data.getItemName());
                if (data.getItemName().equals(mSelectCb.getText().toString())) {
                    mTitleItem.setPressed(true);
                } else {
                    mTitleItem.setPressed(false);
                }

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSelectCb.setText(data.getItemName());
                        mSelectCb.setChecked(false);
                        isInitRefresh = false;
                        pageCurrent = 0;
                        mProvider.addTotalOrderForApp(flag, data.getItemId(), pageCurrent, pageSize);
                        mPopup.dismiss();
                    }
                });
            }
        };

        mListView.setAdapter(mPopListAdapter);
        mPopListAdapter.addAll(categoryList);
        mPopListAdapter.notifyDataSetChanged();

        mPopup = new PopupWindow(view, cbWidth, LinearLayout.LayoutParams.WRAP_CONTENT, false);
        // 允许点击外部消失
        mPopup.setBackgroundDrawable(new BitmapDrawable());//注意这里如果不设置，下面的setOutsideTouchable(true);允许点击外部消失会失效
        mPopup.setOutsideTouchable(true);   //设置外部点击关闭ppw窗口
        // 设置此参数获得焦点，否则无法点击
        mPopup.setFocusable(true);
        //当点击外部ppw窗口关闭时，设置选择分类的按钮为关闭状态
        mPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                mSelectCb.setChecked(false);
            }
        });
    }

    /**
     * 显示订单分类列表
     */
    private void showPopupWindow(View v) {
        mPopListAdapter.notifyDataSetChanged();
        //获取点击View的坐标
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        mPopup.showAsDropDown(v);//显示在v的下面
    }

    /**
     * 设置listview为空是的布局显示情况
     */
    private void setNullData(String cotent, boolean canClick, int type) {
        mTitle.setVisibility(View.GONE);
//        emptyListTxt.setText(cotent);
//        emptyListContent.setClickable(canClick);
//        emptyListImg.setBackgroundResource(image);
        setEmptyViewUtil.setView(type, cotent, canClick);
    }

    /**
     * @desc (如果listView的高度小于屏幕的高度 就不刷新)
     */
    protected void initIsRefresh(int size) {
        View item = mListAdapter.getView(0, null, mPullListView.getRefreshableView());
        item.measure(0, 0);
        int listHeight = size * item.getMeasuredHeight();
        if (listHeight < mHeight - (int) (267 * mDensity + 0.5f)) {
            mPullListView.setMode(PullToRefreshBase.Mode.DISABLED);
        } else {
            mPullListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        }
    }
/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
