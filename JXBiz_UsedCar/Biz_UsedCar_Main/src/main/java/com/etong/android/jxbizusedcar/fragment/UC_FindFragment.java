package com.etong.android.jxbizusedcar.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.widget.EtongNoScrollGridView;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.nestedscrollview.IScrollView;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshScrollView;
import com.etong.android.jxappusedcar.content.UC_BuyCarActivity;
import com.etong.android.jxappusedcar.content.UC_BuyCarFragment;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.activity.UC_Good_car_recommendedActivity;
import com.etong.android.jxbizusedcar.activity.UsedCar_MainActivity;
import com.etong.android.jxbizusedcar.adapter.UC_FindHeadAdapter;
import com.etong.android.jxbizusedcar.adapter.UC_GuessLikeAdapter;
import com.etong.android.jxbizusedcar.adapter.UC_HomePagerRecycleViewAdapter;
import com.etong.android.jxbizusedcar.bean.UC_FindImageBean;
import com.etong.android.jxbizusedcar.bean.UC_HomePageCar;
import com.etong.android.jxbizusedcar.utils.UC_SpaceItemDecoration;
import com.etong.android.jxbizusedcar.widget.UC_FindJsonData;
import com.etong.android.jxbizusedcar.widget.UC_MyRecycleView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @desc 发现fragment==》第一个fragment(二手车)
 * @createtime 2016/11/14 - 9:49
 * @Created by xiaoxue.
 */
public class UC_FindFragment extends BaseSubscriberFragment {
    private List<UC_FindImageBean> mImageList;      //头部list
    // 好车推荐和猜你喜欢数据容器
    private List<UC_HomePageCar> mGoodCArsList, mGuessCarsList;
    private int pageSize = 2;           //一页2个
    private int pageCurrent = 0;        //当前页
    private boolean isResume=false;     //true 是onResume  false 不是onResume
    //二手车布局和adapter
    private UC_MyRecycleView mRecyclerView;
    private UC_HomePagerRecycleViewAdapter mHomeAdapter;
    //初始化控件
    private View view;
    private EtongNoScrollGridView find_head;
    private HttpPublisher mHttpPublisher;
    private UC_UserProvider mUsedCarProvider;
    private ImageProvider mImageProvider;
    private TitleBar mTitleBar;
    private TextView used_car_more;
    private EtongNoScrollGridView guesslike;
    private UC_GuessLikeAdapter guessLikeAdapter;
    private TextView guess_like_more;
    private PullToRefreshScrollView sv_scroll;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //初始化http请求
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mUsedCarProvider = UC_UserProvider.getInstance();
        mUsedCarProvider.initalize(HttpPublisher.getInstance());
        mImageProvider = ImageProvider.getInstance();
        mImageProvider.initialize(getActivity());
        view = inflater.inflate(R.layout.uc_find_fragment,
                container, false);
        initData();
        initView();
        scroll("scroll");  //进入页面使scrollview到顶部
        return view;
    }

    //初始化控件
    private void initView() {
        mGoodCArsList = new ArrayList<>();
        mGuessCarsList = new ArrayList<>();
        mTitleBar = new TitleBar(view);
        mTitleBar.showNextButton(false);
        mTitleBar.showBackButton(false);
        mTitleBar.showBottomLin(false);
        mTitleBar.setTitle("弘高车世界");
        mTitleBar.setTitleTextColor("#ffffff");     //设置title颜色
        mTitleBar.setmTitleBarBackground("#cf1c36");//设置titlebar背景色
        sv_scroll =findViewById(view,R.id.uc_find_sv_scroll,PullToRefreshScrollView.class);       //下拉刷新scrollview
        sv_scroll.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<IScrollView>() {    //下拉
            @Override
            public void onRefresh(PullToRefreshBase<IScrollView> refreshView) {
                //请求好车推荐的接口
                initRecommentCar();
                //请求猜你喜欢的接口
                Map<String, String> youLikeMap = new HashMap<>();
                youLikeMap.put("f_org_id", "001");
                mUsedCarProvider.guessLoveCar(youLikeMap);
            }
        });
        //头部的GridView
        find_head = findViewById(view, R.id.uc_gv_head, EtongNoScrollGridView.class);
        UC_FindHeadAdapter headAdapter = new UC_FindHeadAdapter(getActivity(), mImageList);
        find_head.setAdapter(headAdapter);

        //二手车布局（好车推荐的1、2条数据）和adapter
        mRecyclerView = findViewById(view, R.id.uc_rl_fill_data, UC_MyRecycleView.class);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addItemDecoration(new UC_SpaceItemDecoration(30, 10, 20, 30, 0));
        mHomeAdapter = new UC_HomePagerRecycleViewAdapter(getActivity());
        mRecyclerView.setAdapter(mHomeAdapter);
        //二手车点击更多
        used_car_more = findViewById(view, R.id.uc_used_car_more, TextView.class);
        //猜你喜欢的gridview
        guesslike = findViewById(view, R.id.uc_gv_guesslike, EtongNoScrollGridView.class);
        //代码设置gridview的间隔
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) guesslike.getLayoutParams();
        params.leftMargin=30;
        params.rightMargin=30;
        params.topMargin=20;
        params.bottomMargin=20;
        guesslike.setHorizontalSpacing(20);
        guesslike.setVerticalSpacing(20);
        guesslike.setLayoutParams(params);
        //猜你喜欢的adapter
        guessLikeAdapter = new UC_GuessLikeAdapter(getActivity());
        guesslike.setAdapter(guessLikeAdapter);
        //猜你喜欢点击更多
        guess_like_more = findViewById(view, R.id.uc_tv_guess_like_more, TextView.class);

        addClickListener(view, R.id.uc_used_car_more);
        addClickListener(view, R.id.uc_tv_guess_like_more);
    }

    //顶部gridview数据处理
    private void initData() {
        mImageList = new ArrayList<>();
        JSONArray array = JSONArray.parseArray(UC_FindJsonData.getJsonData());
        for (int i = 0; i < array.size(); i++) {
            UC_FindImageBean mFindImageBean = JSON.toJavaObject(
                    array.getJSONObject(i), UC_FindImageBean.class);
            mImageList.add(mFindImageBean);

        }
        //请求好车推荐的接口
        initRecommentCar();
        //请求猜你喜欢的接口
        Map<String, String> youLikeMap = new HashMap<>();
        youLikeMap.put("f_org_id", "001");
        mUsedCarProvider.guessLoveCar(youLikeMap);
    }

    /**
     * 请求好车推荐接口
     */
    private void initRecommentCar() {
        Map<String, String> recommentMap = new HashMap<>();
        recommentMap.put("f_org_id", "001");
        recommentMap.put("pageSize", pageSize + "");
        recommentMap.put("pageCurrent", pageCurrent + "");
        mUsedCarProvider.recommentCar(recommentMap, "only 2");
    }

    /**
     * 处理好车推荐的数据
     * @param method
     */
    @Subscriber(tag = UC_HttpTag.RECOMMNET_GOOD_CAR + "only 2")
    private void getRecommentCar(HttpMethod method) {
        sv_scroll.onRefreshComplete();  //刷新完成
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");
        if (status.equals("true")) {
            if (!mGoodCArsList.isEmpty() && pageCurrent == 0) {
                mGoodCArsList.clear();
            }
            JSONArray array = method.data().getJSONArray("data");
            for (int i = 0; i < array.size(); i++) {
                UC_HomePageCar car = JSON.toJavaObject((JSON) array.get(i), UC_HomePageCar.class);
                mGoodCArsList.add(car);
            }
            mHomeAdapter.updateData(mGoodCArsList, false);
        } else {
            toastMsg(msg);
        }
    }

    /**
     * 处理猜你喜欢的数据
     *
     * @param method
     */
    @Subscriber(tag = UC_HttpTag.GUESS_LOVE_CAR)
    private void guessCar(HttpMethod method) {
        sv_scroll.onRefreshComplete();  //刷新完成
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");
        if (status.equals("true")) {
            if (!mGuessCarsList.isEmpty()) {
                mGuessCarsList.clear();
            }
            JSONArray array = method.data().getJSONArray("data");
            for (int i = 0; i < array.size(); i++) {
                UC_HomePageCar car = JSON.toJavaObject((JSON) array.get(i), UC_HomePageCar.class);
                mGuessCarsList.add(car);
            }
            guessLikeAdapter.updateData(mGuessCarsList,true);
        } else {
            toastMsg(msg);
        }
    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.uc_used_car_more) {//二手车更多
            ActivitySkipUtil.skipActivity(UC_FindFragment.this, UC_Good_car_recommendedActivity.class);//跳到好车推荐
        } else if (view.getId() == R.id.uc_tv_guess_like_more) {//猜你喜欢更多
            ActivitySkipUtil.skipActivity(UC_FindFragment.this, UC_BuyCarActivity.class);//跳到买车
            EventBus.getDefault().postSticky("1", UC_BuyCarFragment.BUY_CAR_IS_REQUEST);//传给买车页的标识
        }
    }

    /**
     * 点击homepage按钮时让整个布局滚到最顶部
     *
     * @param msg
     */
    @Subscriber(tag = UsedCar_MainActivity.TAG)
    private void scroll(String msg) {
        if(!isResume){//不是onResume时滑到顶部
            mRecyclerView.setFocusable(false);
            guesslike.setFocusable(false);
            sv_scroll.getRefreshableView().scrollTo(0, 0);
        }
        isResume =false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isResume =true;
    }
}
