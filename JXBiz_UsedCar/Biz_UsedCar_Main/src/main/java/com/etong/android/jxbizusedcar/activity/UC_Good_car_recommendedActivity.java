package com.etong.android.jxbizusedcar.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshGridView;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.adapter.UC_GuessLikeAdapter;
import com.etong.android.jxbizusedcar.bean.UC_HomePageCar;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoxue
 * @desc 第一个fragment里的二手车点击更多跳到好车推荐的activity
 * @createtime 2016/11/14 - 19:41
 */
public class UC_Good_car_recommendedActivity extends BaseSubscriberActivity {
    private HttpPublisher mHttpPublisher;
    private UC_UserProvider mUsedCarProvider;
    private ImageProvider mImageProvider;
    //好车推荐的list 和adapter
    private List<UC_HomePageCar> mGoodCArsList = new ArrayList<>();
    private UC_GuessLikeAdapter guessLikeAdapter;
    //初始化控件
    private PullToRefreshGridView mPullToRefreshGridView;
    private RelativeLayout uc_rl_accident;
    private ImageView used_car_iv_network;
    private TextView uc_tv_hint;
    private TitleBar mTitleBar;
    private boolean refreshIsRequest = true;             //刷新是否请求接口  true 请求  false 不请求
    private int isPullDown = 0;                         //0下拉  1 上拉
    private int pageSize = 10;                          //每页10条
    private int pageCurrent = 0;                        //当前页
    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_good_car_recommended);
        //初始化http请求
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mUsedCarProvider = UC_UserProvider.getInstance();
        mUsedCarProvider.initalize(HttpPublisher.getInstance());
        mImageProvider = ImageProvider.getInstance();
        mImageProvider.initialize(this);
        //请求好车推荐的接口
        initRecommentCar(pageCurrent);
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
        mTitleBar = new TitleBar(this);
        mTitleBar.showNextButton(false);
        mTitleBar.showBackButton(true);
        mTitleBar.showBottomLin(false);
        mTitleBar.setTitle("好车推荐");
      /*  mTitleBar.setTitleTextColor("#ffffff");//设置title颜色
        mTitleBar.setmTitleBarBackground("#cf1c36");//设置titlebar背景色*/
        //没网等情况的布局
        uc_rl_accident = findViewById(com.etong.android.jxappusedcar.R.id.uc_rl_accident, RelativeLayout.class);
        used_car_iv_network = findViewById(com.etong.android.jxappusedcar.R.id.used_car_iv_network, ImageView.class);//提示图片
        uc_tv_hint = findViewById(com.etong.android.jxappusedcar.R.id.uc_tv_hint, TextView.class);                  //提示文字
        mPullToRefreshGridView = findViewById(R.id.uc_gv_good_car, PullToRefreshGridView.class);                    //下拉刷新的gridview
        //代码动态设置gridview的间隔
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mPullToRefreshGridView.getLayoutParams();
        params.leftMargin = 30;
        params.rightMargin = 30;
        params.topMargin = 20;
        params.bottomMargin = 20;
        mPullToRefreshGridView.getRefreshableView().setHorizontalSpacing(20);
        mPullToRefreshGridView.getRefreshableView().setVerticalSpacing(20);
        mPullToRefreshGridView.setLayoutParams(params);

        mPullToRefreshGridView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
        mPullToRefreshGridView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        mPullToRefreshGridView.getLoadingLayoutProxy(false, true).setRefreshingLabel(
                "正在加载更多...");
        mPullToRefreshGridView.getLoadingLayoutProxy(false, true).setReleaseLabel(
                "松开加载更多");
        mPullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {//下拉
                if (refreshIsRequest) {
                    isPullDown = 0;
                    pageCurrent = 0;
                    initRecommentCar(pageCurrent);  //请求接口
                } else {
                    //延迟0.3s
                    mPullToRefreshGridView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullToRefreshGridView.onRefreshComplete();
                        }
                    }, 300);
                }

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {//上拉
                if (refreshIsRequest) {//请求接口
                    isPullDown = 1;
                    pageCurrent++;
                    initRecommentCar(pageCurrent);
                } else {//不请求接口
                    //延迟1s
                    mPullToRefreshGridView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPullToRefreshGridView.onRefreshComplete();//刷新完成
                            toastMsg("已加载全部数据");
                        }
                    }, 300);
                }

            }
        });
        guessLikeAdapter = new UC_GuessLikeAdapter(this);
        mPullToRefreshGridView.setAdapter(guessLikeAdapter);
        noDataClick();
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

    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    /**
     * 好车推荐
     */
    private void initRecommentCar(int pageCurrent) {
        Map<String, String> recommentMap = new HashMap<>();
        recommentMap.put("f_org_id", "001");
        recommentMap.put("pageSize", pageSize + "");
        recommentMap.put("pageCurrent", pageCurrent + "");
        mUsedCarProvider.recommentCar(recommentMap, "");
    }

    /**
     * 好车推荐
     *
     * @param method
     */
    @Subscriber(tag = UC_HttpTag.RECOMMNET_GOOD_CAR)
    private void getRecommentCar(HttpMethod method) {
        mPullToRefreshGridView.setMode(PullToRefreshBase.Mode.BOTH);
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");
        if (status.equals("true")) {
            if (!mGoodCArsList.isEmpty() && pageCurrent == 0 && 1 != isPullDown) {
                mGoodCArsList.clear();
            }
            JSONArray array = method.data().getJSONArray("data");
            //添加数据
            for (int i = 0; i < array.size(); i++) {
                UC_HomePageCar car = JSON.toJavaObject((JSON) array.get(i), UC_HomePageCar.class);
                mGoodCArsList.add(car);
            }
            //数据为空的时候
            if (mGoodCArsList.isEmpty()) {
                mPullToRefreshGridView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            }
            //更新下拉参数
            if (isPullDown == 1) {//上拉
//                pageCurrent = pageCurrent + 1;
                guessLikeAdapter.updateData(mGoodCArsList, false);
                mPullToRefreshGridView.onRefreshComplete();
            } else {
                pageCurrent = 0;
                refreshIsRequest = true;//允许请求数据
                guessLikeAdapter.updateData(mGoodCArsList, false);
                if (!mGoodCArsList.isEmpty()) {
                    uc_rl_accident.setVisibility(View.GONE);
                    used_car_iv_network.setVisibility(View.GONE);
                    mPullToRefreshGridView.setVisibility(View.VISIBLE);
                    mPullToRefreshGridView.onRefreshComplete();
                }
            }
            if (array.size() < pageSize) {
                refreshIsRequest = false;//不请求数据
                if (1 == isPullDown) {//上拉
                    mPullToRefreshGridView.onRefreshComplete();
                    mPullToRefreshGridView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
//                    mPullToRefreshGridView.getRefreshableView().smoothScrollToPosition(mGoodCArsList.size() - 1);
//                    toastMsg("已加载全部数据");
                } else {
                    if (array.size() == 0) {
                        ShowView(com.etong.android.jxappusedcar.R.mipmap.uc_ic_no_cotent_img, "亲,暂时没有内容哦", false);
                        mPullToRefreshGridView.onRefreshComplete();
                    } else {
                        mPullToRefreshGridView.onRefreshComplete();
                        mPullToRefreshGridView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
                    }
                }
            }
        } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {//服务器无响应
            ShowView(com.etong.android.jxappusedcar.R.mipmap.uc_ic_no_network_img, "亲,网络不给力哦\n点击屏幕重试", true);
            mPullToRefreshGridView.onRefreshComplete();
        } else if (status.equals(HttpPublisher.DATA_ERROR)) {//数据请求失败
            ShowView(com.etong.android.jxappusedcar.R.mipmap.uc_ic_no_service_img, "Sorry,您访问的页面找不到了......", false);
            mPullToRefreshGridView.onRefreshComplete();
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
        mPullToRefreshGridView.setVisibility(View.GONE);
        uc_rl_accident.setClickable(isClick);
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
                initRecommentCar(pageCurrent);
                uc_rl_accident.setVisibility(View.GONE);
                mPullToRefreshGridView.setVisibility(View.VISIBLE);
            }
        });
    }

/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
