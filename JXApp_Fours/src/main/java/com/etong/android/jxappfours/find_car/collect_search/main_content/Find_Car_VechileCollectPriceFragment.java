package com.etong.android.jxappfours.find_car.collect_search.main_content;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.ActivityStackManager;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.collect_search.adapter.FC_CollectAdapter;
import com.etong.android.jxappfours.find_car.collect_search.javabean.FC_NewCarCollectBean;
import com.etong.android.jxappfours.find_car.collect_search.utils.Find_Car_SearchProvider;
import com.etong.android.jxappfours.find_car.collect_search.utils.Find_Car_VechileCollect_Method;
import com.etong.android.jxappfours.find_car.grand.car_config.Find_car_CarConfigActivity;
import com.etong.android.jxappfours.models_contrast.common.Models_Contrast_AddVechileStyle_Method;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 收藏的fragment  数据处理页
 * Created by Administrator on 2016/8/8.
 */
public class Find_Car_VechileCollectPriceFragment extends BaseSubscriberFragment {

    protected float mDensity;
    protected int mWidth;
    protected int mHeight;
    private View view;
    private ImageProvider mImageProvider;
    private PullToRefreshListView mlistView;
    ListAdapter<Models_Contrast_VechileType> mListAdapters;
    private LinearLayout data_null;
    private final String listInfo = "listInfo";
    private LinearLayout mlistLayout;

    private boolean refreshIsRequest;
    private int pageSize = 10;
    private int pageCurrent = 0;
    private int isPullDown = 1;
    public static final int isNewFlag = 1;//是否是新车 1--新车 0--二手车
    private HttpPublisher mHttpPublisher;
    private Find_Car_SearchProvider mProvider;
    private boolean isFirst = true; //是否是第一次进来
    private LinearLayout mEmptyView;
    private TextView mEmptyTxt;
    private List<FC_NewCarCollectBean> mListData = new ArrayList<>();
    private FC_CollectAdapter adapter;
    private FC_NewCarCollectBean mCF_LoanListBean;
    private LinearLayout ll_collect;
    private double carNum = 0;
    private SetEmptyViewUtil setEmptyViewUtil;
    private ImageView mEmptyImg;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.find_car_collect_list_frg,
                container, false);
        initView(view);
        initData();
        isLogin();
        return view;
    }

    private void initData() {
        //初始化请求
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mProvider = Find_Car_SearchProvider.getInstance();
        mProvider.initialize(mHttpPublisher);
//        pageCurrent = 0;
//        mProvider.getCollectList(pageSize, pageCurrent, isNewFlag, 0);
    }

    protected void initView(View view) {
        ll_collect = findViewById(view, R.id.find_car_ll_collect, LinearLayout.class);
        mlistView = (PullToRefreshListView) view.findViewById(R.id.find_car_lv_collect);
        mEmptyView = findViewById(view, R.id.default_empty_content, LinearLayout.class);
        mEmptyTxt = findViewById(view, R.id.default_empty_lv_textview, TextView.class);
        mEmptyImg = findViewById(view, R.id.default_empty_img, ImageView.class);
        mEmptyView.setClickable(false);
        mEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEmptyTxt.getText().toString().equals("您还未登录")) {
                    ActivitySkipUtil.skipActivity(getActivity(), FramePersonalLoginActivity.class);
                } else {
                    pageCurrent = 0;
                    mProvider.getCollectList(pageSize, pageCurrent, isNewFlag, 0);
                }
            }
        });
        setEmptyViewUtil = new SetEmptyViewUtil(mEmptyView, mEmptyImg, mEmptyTxt);
        mlistView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
        mlistView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        mlistView.getLoadingLayoutProxy(false, true).setRefreshingLabel(
                "正在加载更多...");
        mlistView.getLoadingLayoutProxy(false, true).setReleaseLabel(
                "松开加载更多");

        mlistView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                //延迟0.3s
                mlistView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageCurrent = 0;
                        mProvider.getCollectList(pageSize, pageCurrent, isNewFlag, 0);
                    }
                }, 300);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                //延迟3s
                mlistView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshIsRequest) {//请求接口
                            mProvider.getCollectList(pageSize, ++pageCurrent, isNewFlag, 1);
                        } else {//不请求接口
                            mlistView.onRefreshComplete();//刷新完成
                            toastMsg("已加载全部数据");
                        }
                    }
                }, 300);
            }
        });
        adapter = new FC_CollectAdapter(getActivity());
        mlistView.setAdapter(adapter);
    }

    /**
     * @param hidden Fragment是否显示   显示 hidden false   |  隐藏  hidden  true
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            isLogin();
        }
    }

    /**
     * @desc (判断是否登录)
     * @createtime 2016/12/14 - 14:29
     * @author xiaoxue
     */
    private void isLogin() {
        if (FrameEtongApplication.getApplication().isLogin()) {
            pageCurrent = 0;
            mProvider.getCollectList(pageSize, pageCurrent, isNewFlag, 0);
        } else {
            ShowNullView("您还未登录", true, setEmptyViewUtil.OtherView);
        }
    }


    public class MyComp implements Comparator<FC_NewCarCollectBean> {
        @Override
        public int compare(FC_NewCarCollectBean o1, FC_NewCarCollectBean o2) {
            return String.valueOf(o1.getF_price()).compareTo(String.valueOf(o2.getF_price()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst) {
            if (null!=FrameEtongApplication.getApplication().getNewCarCollectCache().getCarList() && carNum != FrameEtongApplication.getApplication().getNewCarCollectCache().getCarList().size() ) {
                isLogin();
            } else {
                adapter.notifyDataSetChanged();
            }
        } else {
            isFirst = false;
        }
    }

    /**
     * @desc (处理收藏的数据)
     * @createtime 2016/12/6 - 20:14
     * @author xiaoxue
     */
    @Subscriber(tag = FrameHttpTag.COLLECTIONLIST + isNewFlag)
    protected void getCollectList(HttpMethod method) {
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
                        ShowNullView("亲,暂时没有内容哦", false, setEmptyViewUtil.NoCollectView);
                    }
                }
            }
            //添加数据
            for (int i = 0; i < data.size(); i++) {
                mCF_LoanListBean = JSON.toJavaObject((JSON) data.get(i), FC_NewCarCollectBean.class);
                mListData.add(mCF_LoanListBean);
            }
            if (!mListData.isEmpty()) {
                mlistView.setMode(PullToRefreshBase.Mode.BOTH);
            }
            //排序
//            Collections.sort(mListData, new MyComp());
            //更新下拉参数
            if (isPullDown == 1) {//上拉
                adapter.update(mListData);

            } else {
                refreshIsRequest = true;//允许请求数据
                adapter.update(mListData);
                setIsPullUpRefresh(mListData.size());
            }
            carNum = mListData.size();
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {//网络
            ShowNullView("亲,网络不给力哦\n点击屏幕重试", true, setEmptyViewUtil.NetworkErrorView);
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {
            ShowNullView("Sorry,您访问的页面找不到了......", false, setEmptyViewUtil.NoServerView);
        } else {//服务
//            toastMsg(msg);
        }
        mlistView.onRefreshComplete();
    }

    /**
     * @desc (显示空视图)
     * @createtime 2016/12/7 - 9:16
     * @author xiaoxue
     */
    protected void ShowNullView(String text, boolean isClick, int type) {
        mEmptyView.setVisibility(View.VISIBLE);
        mlistView.setVisibility(View.GONE);
        ll_collect.setVisibility(View.GONE);
//        mNullImg.setBackgroundResource(image);
//        mEmptyTxt.setText(text);
//        mEmptyView.setClickable(isClick);
        setEmptyViewUtil.setView(type, text, isClick);
    }


    /**
     * @desc (显示ListView视图)
     * @createtime 2016/12/7 - 9:15
     * @author xiaoxue
     */
    protected void ShowListView() {
        mlistView.setVisibility(View.VISIBLE);
        ll_collect.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }

    /**
     * @desc (根据第一次请求的数据个数判断设置listview是否可上拉加载)
     * @createtime 2016/11/14 0014-16:06
     * @author wukefan
     */
    private void setIsPullUpRefresh(int size) {
        if (size >= pageSize) {
            mlistView.setMode(PullToRefreshBase.Mode.BOTH);
            return;
        }
        View listItem = adapter.getView(0, null, mlistView);
        listItem.measure(0, 0);
        int height = listItem.getMeasuredHeight();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mHeight = (dm.heightPixels - ((int) ((52) * dm.density + 0.5f)));
        if (height * size < mHeight) {
            mlistView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }
    }
}
