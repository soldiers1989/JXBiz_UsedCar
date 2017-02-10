package com.etong.android.jxbizusedcar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.EtongToast;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.EtongNoScrollGridView;
import com.etong.android.frame.widget.loopbanner.BGABanner;
import com.etong.android.jxappusedcar.content.UC_SearchActivity;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.activity.UsedCar_MainActivity;
import com.etong.android.jxbizusedcar.adapter.UC_HomePagerRecycleViewAdapter;
import com.etong.android.jxbizusedcar.adapter.UC_PriceAndBrandAdapter;
import com.etong.android.jxbizusedcar.bean.UC_Banner;
import com.etong.android.jxbizusedcar.bean.UC_HomePageCar;
import com.etong.android.jxbizusedcar.bean.UC_PriceAndBrandBean;
import com.etong.android.jxbizusedcar.utils.UC_SpaceItemDecoration;
import com.etong.android.jxbizusedcar.widget.UC_MyRecycleView;
import com.etong.android.jxbizusedcar.widget.UC_MyScrollerView;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class UC_HomePageFragment extends BaseSubscriberFragment {

    private BGABanner mMainBanner;

    private int pageSize = 10;
    private int pageCurrent = 0;

    // banner图片路径
    private List<String> mImageUrl;
    private UC_MyScrollerView mScrollView;

    // 好车推荐和猜你喜欢布局
    private UC_MyRecycleView mRecyclerView;
    private RadioButton mRecommenCarView, mGuessLikeView;

    // 搜索布局，和底部提示布局
    private LinearLayout mSearchLayout, mHintLayout, mLoveHintLayout;

    // 好车推荐和猜你喜欢数据的适配器
    private UC_HomePagerRecycleViewAdapter mHomeAdapter;
    // 好车推荐和猜你喜欢数据容器
    private List<UC_HomePageCar> mGoodCArsList, mGuessCarsList;
    /*** 价格品牌布局*/
    private EtongNoScrollGridView mGridView;
    /*** 价格品牌数据的适配器*/
    private UC_PriceAndBrandAdapter mGridAdapter;
    /*** 价格品牌数据容器*/
    private List<UC_PriceAndBrandBean> mPriceBrandList;
    /*** 没网视图*/
    private ViewGroup mWithOutNetworkView;

    private UC_UserProvider mProvider;
    private boolean isInit = false;                 //是否初始化
    private boolean isCanRequest = true;            //是否请求
    private boolean isLoadAllRecommentCar = false;  //好车推荐是否全部加载
    private int mWinWidth;                          // 最小的宽度
    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.uc_fragment_home_pager, container, false);
        mProvider = UC_UserProvider.getInstance();
        mProvider.initalize(HttpPublisher.getInstance());
        initView(view);
        initBanner(view);
        initData();
        return view;
    }

    /**
     * 初始化banner
     *
     * @param view
     */
    private void initBanner(View view) {

        mMainBanner = findViewById(view, R.id.uc_banner_main, BGABanner.class);

        mMainBanner.getLayoutParams().width = mWinWidth;
//                        topBanner.getLayoutParams().height = mWinWidth * height / with;
        mMainBanner.getLayoutParams().height = mWinWidth * 380 / 750;
        mMainBanner.setOnItemClickListener(new BGABanner.OnItemClickListener() {
            @Override
            public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {

//                Toast.makeText(getActivity(), "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initView(View view) {

        // 获取到屏幕的宽度，动态的设置Banner图的宽高
        WindowManager mWm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        mWinWidth = mWm.getDefaultDisplay().getWidth();

        mImageUrl = new ArrayList<>();
        mGoodCArsList = new ArrayList<>();
        mGuessCarsList = new ArrayList<>();

        mWithOutNetworkView = findViewById(view, R.id.uc_ll_home_without_network, ViewGroup.class);
        TextView mWithOutNetworkTxt = findViewById(view, R.id.uc_home_txt_null, TextView.class);
        mWithOutNetworkTxt.setText("亲，网络不给力哦\n点击屏幕重试");

        mHintLayout = findViewById(view, R.id.uc_ll_hint_layout, LinearLayout.class);
        mLoveHintLayout = findViewById(view, R.id.uc_ll_love_hint, LinearLayout.class);
        mSearchLayout = findViewById(view, R.id.uc_ll_search_layout, LinearLayout.class);
        mGridView = findViewById(view, R.id.uc_gv_price_brand, EtongNoScrollGridView.class);
        mRecommenCarView = findViewById(view, R.id.uc_rb_recommend_car, RadioButton.class);
        mGuessLikeView = findViewById(view, R.id.uc_rb_guess_like, RadioButton.class);
        mRecommenCarView.setChecked(true);

        mScrollView = findViewById(view, R.id.uc_csv_all_layout, UC_MyScrollerView.class);
        mScrollView.setGradualLayout(mSearchLayout);
        mScrollView.setmScrollListener(new UC_MyScrollerView.ScrollViewListener() {
            @Override
            public void onScrollToBottom() {

                if (mRecommenCarView.isChecked() && !isLoadAllRecommentCar) {
//                    Toast.makeText(getActivity(), "我到底部了", Toast.LENGTH_SHORT).show();
                    initRecommentCar(++pageCurrent);
                }
            }
        });

        mWithOutNetworkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });

        mRecyclerView = findViewById(view, R.id.uc_rl_fill_data, UC_MyRecycleView.class);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addItemDecoration(new UC_SpaceItemDecoration(30, 10, 20, 30, 0));

        mHomeAdapter = new UC_HomePagerRecycleViewAdapter(getActivity());
        mRecyclerView.setAdapter(mHomeAdapter);

        addClickListener(view, R.id.uc_rl_search_content);
        addClickListener(view, R.id.uc_btn_buy_car);
        addClickListener(view, R.id.uc_btn_sell_car);
        addClickListener(view, R.id.uc_rb_recommend_car);
        addClickListener(view, R.id.uc_rb_guess_like);
        addClickListener(view, R.id.uc_csv_all_layout);

        initGridView();

        scroll("scroll");
    }

    private void initGridView() {
        mGridAdapter = new UC_PriceAndBrandAdapter(getActivity());
        mGridView.setAdapter(mGridAdapter);
    }

    private void initData() {
        /**
         * 价格和品牌
         */
        Map<String, String> map = new HashMap<>();
        map.put("f_org_id", "001");
        mProvider.getHotBrandPrice(map);
        /**
         * 猜你喜欢
         */
        Map<String, String> youLikeMap = new HashMap<>();
        youLikeMap.put("f_org_id", "001");
        mProvider.guessLoveCar(youLikeMap);
        /**
         * banner
         */
        Map<String, String> bannerMap = new HashMap<>();
        bannerMap.put("f_org_id", "001");
        mProvider.getBanner(bannerMap);

        pageCurrent = 0;
        initRecommentCar(pageCurrent);
        isLoadAllRecommentCar = false;

        if (mRecommenCarView.isChecked()) {
            mLoveHintLayout.setVisibility(View.GONE);
            mHintLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 好车推荐
     */
    private void initRecommentCar(int pageCurrent) {
        Map<String, String> recommentMap = new HashMap<>();
        recommentMap.put("f_org_id", "001");
        recommentMap.put("pageSize", pageSize + "");
        recommentMap.put("pageCurrent", pageCurrent + "");
        mProvider.recommentCar(recommentMap,"");
    }

    /**
     * 填充Banner
     */
    private void fillBanner() {
        mMainBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                Glide.with(UC_HomePageFragment.this)
                        .load(model)
                        .placeholder(R.mipmap.uc_ic_banner_default)
                        .error(R.mipmap.uc_ic_banner_default)
                        .into((ImageView) view);
            }
        });
        mMainBanner.setData(mImageUrl, null);
    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.uc_rl_search_content:   //搜索
                ActivitySkipUtil.skipActivity(UC_HomePageFragment.this, UC_SearchActivity.class);
                break;
            case R.id.uc_btn_buy_car:   //买车
                mEventBus.post(1, "switch page");
                break;
            case R.id.uc_btn_sell_car:  //卖车
                mEventBus.post(2, "switch page");
                break;
            case R.id.uc_rb_recommend_car:  //好车推荐
                if (isLoadAllRecommentCar) {
                    mLoveHintLayout.setVisibility(View.VISIBLE);
                    mHintLayout.setVisibility(View.GONE);
                } else {
                    mLoveHintLayout.setVisibility(View.GONE);
                    mHintLayout.setVisibility(View.VISIBLE);
                }
                mHomeAdapter.updateData(mGoodCArsList, false);
                break;
            case R.id.uc_rb_guess_like:  //猜你喜欢
                mLoveHintLayout.setVisibility(View.VISIBLE);
                mHintLayout.setVisibility(View.GONE);
                mHomeAdapter.updateData(mGuessCarsList, true);
                break;
        }
    }

    /**
     * 点击homepage按钮时让整个布局滚到最顶部
     *
     * @param msg
     */
    @Subscriber(tag = UsedCar_MainActivity.TAG)
    private void scroll(String msg) {
        mRecyclerView.setFocusable(false);
        mScrollView.scrollTo(0, 0);
    }

    /**
     * banner数据
     *
     * @param method
     */
    @Subscriber(tag = UC_HttpTag.HOME_PAGER_BANNER)
    private void banner(HttpMethod method) {

        String msg = method.data().getString("msg");
        String status = method.data().getString("status");
        if (status.equals("true")) {
            if (!mImageUrl.isEmpty()) {
                mImageUrl.clear();
            }
            JSONArray array = method.data().getJSONArray("data");
            for (int i = 0; i < array.size(); i++) {
                UC_Banner banner = JSON.toJavaObject((JSON) array.get(i), UC_Banner.class);
                String url = banner.getF_address().replace(";", "").trim();
                mImageUrl.add(url);
            }
            fillBanner();
        } else {
			toastMsg( msg);
        }
    }


    /**
     * 品牌和价格布局数据
     *
     * @param method
     */
    @Subscriber(tag = UC_HttpTag.BRAND_AND_PRICE)
    private void brandAndPrice(HttpMethod method) {

        mPriceBrandList = new ArrayList<UC_PriceAndBrandBean>();
        showView();
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");
        if (status.equals("true")) {
            JSONObject object = method.data().getJSONObject("data");
            JSONArray brands = object.getJSONArray("brands");
            JSONArray prices = object.getJSONArray("prices");

            for (int i = 0; i < prices.size(); i++) {
                UC_PriceAndBrandBean price = JSON.toJavaObject((JSON) prices.get(i), UC_PriceAndBrandBean.class);
                mPriceBrandList.add(price);
            }
            for (int i = 0; i < brands.size(); i++) {
                UC_PriceAndBrandBean brand = JSON.toJavaObject((JSON) brands.get(i), UC_PriceAndBrandBean.class);
                brand.setIsBrand(true);
                mPriceBrandList.add(brand);
            }
            UC_PriceAndBrandBean allBtn = new UC_PriceAndBrandBean();
            allBtn.setAll("全部");
            mPriceBrandList.add(allBtn);
            mGridAdapter.updateCarList(mPriceBrandList);
        } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {
            showNoNetworkView();
        } else {
            toastMsg("品牌价格" + msg);
        }
    }

    /**
     * 好车推荐
     *
     * @param method
     */
    @Subscriber(tag = UC_HttpTag.RECOMMNET_GOOD_CAR)
    private void getRecommentCar(HttpMethod method) {
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
            if (array.size() < pageSize) {
                isLoadAllRecommentCar = true;
                mLoveHintLayout.setVisibility(View.VISIBLE);
                mHintLayout.setVisibility(View.GONE);
            }
        } else {
            toastMsg(msg);
        }
    }

    /**
     * 猜你喜欢
     *
     * @param method
     */
    @Subscriber(tag = UC_HttpTag.GUESS_LOVE_CAR)
    private void guessCar(HttpMethod method) {

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
        } else {
            toastMsg(msg);
        }
    }

    /**
     * @desc (显示首页当前的布局)
     * @createtime 2016/11/2 - 11:08
     * @author xiaoxue
     */
    private void showView() {
        mWithOutNetworkView.setVisibility(View.GONE);
        mSearchLayout.setVisibility(View.VISIBLE);
        mScrollView.setVisibility(View.VISIBLE);
    }

    /**
     * @desc (显示无网络的首页布局)
     * @createtime 2016/11/2 - 11:08
     * @author xiaoxue
     */
    private void showNoNetworkView() {
        mWithOutNetworkView.setVisibility(View.VISIBLE);
        mSearchLayout.setVisibility(View.GONE);
        mScrollView.setVisibility(View.GONE);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            if (!isInit) {
                mProvider = UC_UserProvider.getInstance();          // 初始化
                mProvider.initalize(HttpPublisher.getInstance());
                isInit = true;

            } else {
                if (isCanRequest) {
                    initData();
                    L.d("---------->", "请求了~");
                    setRequestTime(20);
                }
            }
        }
    }

    /**
     * @desc (设置请求时间间隔)
     */
    private void setRequestTime(int sec) {
        isCanRequest = false;
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                isCanRequest = true;
                timer.cancel();
            }
        }, sec * 1000);
    }
}
