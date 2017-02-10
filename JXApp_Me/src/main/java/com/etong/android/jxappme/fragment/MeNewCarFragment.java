package com.etong.android.jxappme.fragment;

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
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappfours.find_car.collect_search.adapter.FC_CollectAdapter;
import com.etong.android.jxappfours.find_car.collect_search.javabean.FC_NewCarCollectBean;
import com.etong.android.jxappfours.find_car.collect_search.utils.Find_Car_VechileCollect_Method;
import com.etong.android.jxappfours.find_car.grand.car_config.Find_car_CarConfigActivity;
import com.etong.android.jxappfours.models_contrast.common.Models_Contrast_AddVechileStyle_Method;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;
import com.etong.android.jxappme.R;

import com.etong.android.jxappme.common.MeProvider;
import com.etong.android.jxappusedcar.javabean.UC_World_CarListJavaBean;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @desc (新车收藏的fragment)
 * @createtime 2016/12/5 - 10:50
 * @Created by xiaoxue.
 */

public class MeNewCarFragment extends BaseSubscriberFragment {

    private PullToRefreshListView mlistView;
    private ListAdapter<Models_Contrast_VechileType> mListAdapters;
    private boolean isFirst = true;//是否是第一次进来
    private LinearLayout mlistLayout;
    private LinearLayout mEmptyView;
    private TextView mEmptyTxt;
    private int pageSize = 10;
    private int pageCurrent = 0;
    private int isPullDown = 1;
    public static final int isNewFlag = 1;//是否是新车 1--新车 0--二手车
    private HttpPublisher mHttpPublisher;
    private MeProvider mProvider;
    private boolean refreshIsRequest;
    private FC_CollectAdapter adapter;
    private List<FC_NewCarCollectBean> mListData = new ArrayList<>();
    private int carNum = 0;
    private ImageView mEmptyImg;
    private SetEmptyViewUtil setEmptyViewUtil;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_newcar_fragment, container, false);
        initView(view);
        //初始化请求
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mProvider = MeProvider.getInstance();
        mProvider.initialize(mHttpPublisher);
        pageCurrent = 0;
        mProvider.getCollectList(pageSize, pageCurrent, isNewFlag, 0);
        isFirst = false;
        //得到收藏的缓存和加入对比的缓存
//        getListInfo();
        getComparisonVidList();
        return view;
    }


    /**
     * @desc (初始化view)
     * @createtime 2016/12/5 - 20:21
     * @author xiaoxue
     */
    public void initView(View view) {
        mlistView = findViewById(view, R.id.me_lv_newcar, PullToRefreshListView.class);
        mEmptyView = findViewById(view, R.id.default_empty_content, LinearLayout.class);
        mEmptyTxt = findViewById(view, R.id.default_empty_lv_textview, TextView.class);
        mEmptyImg = findViewById(view, R.id.default_empty_img, ImageView.class);
        setEmptyViewUtil = new SetEmptyViewUtil(mEmptyView, mEmptyImg, mEmptyTxt);
        mEmptyView.setClickable(false);
        mEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageCurrent = 0;
                mProvider.getCollectList(pageSize, pageCurrent, isNewFlag, 0);
            }
        });
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
     * 获取到对比的Item
     *
     * @return
     */
    protected List<Integer> getComparisonVidList() {
        //得到车型对比缓存的信息的vid
        List<Integer> mVidList = new ArrayList<Integer>();
        List<Models_Contrast_Add_VechileStyle> mList = new ArrayList<Models_Contrast_Add_VechileStyle>();
        Map map = Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle();
        Models_Contrast_VechileType info = null;
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                String data = String.valueOf(map.get(Integer.valueOf(String.valueOf(key))));
                info = JSON.parseObject(data, Models_Contrast_VechileType.class);
                if (null != info) {
                    mVidList.add(info.getVid());
                }
            }
        }
        return mVidList;
    }


    /**
     * @desc (不是第一次进来时，再得到一次缓存数据)
     * @createtime 2016/12/5 - 20:23
     * @author xiaoxue
     */
    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst) {
//            getListInfo();
            if (carNum != FrameEtongApplication.getApplication().getNewCarCollectCache().getCarList().size()) {
                pageCurrent = 0;
                mProvider.getCollectList(pageSize, pageCurrent, isNewFlag, 0);
            }
            getComparisonVidList();
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * @author xiaoxue
     * @desc (按价格排序)
     * @createtime 2016/12/5 - 20:22
     */
    public class MyComp implements Comparator<Models_Contrast_VechileType> {
        @Override
        public int compare(Models_Contrast_VechileType o1, Models_Contrast_VechileType o2) {
            return o1.getPrices().compareTo(o2.getPrices());
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
                FC_NewCarCollectBean mCF_LoanListBean = JSON.toJavaObject((JSON) data.get(i), FC_NewCarCollectBean.class);
                mListData.add(mCF_LoanListBean);
            }
            if (!mListData.isEmpty()) {
                mlistView.setMode(PullToRefreshBase.Mode.BOTH);
            }
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
        int mHeight = (dm.heightPixels - ((int) ((46.5 + 42) * dm.density + 0.5f)));
        if (height * size < mHeight) {
            mlistView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }
    }

}
