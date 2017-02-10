package com.etong.android.jxappme.fragment;

import android.content.Context;
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
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappme.R;
import com.etong.android.jxappme.common.MeProvider;
import com.etong.android.jxappusedcar.adapter.UC_World_CarList_Adapter;
import com.etong.android.jxappusedcar.javabean.UC_World_CarListJavaBean;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (二手车收藏fragment)
 * @createtime 2016/12/5 - 10:58
 * @Created by xiaoxue.
 */

public class MeUsedCarFragment extends BaseSubscriberFragment {


    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    private PullToRefreshListView mlistView;
    private LinearLayout mEmptyView;
    private TextView mEmptyTxt;
    private UC_World_CarList_Adapter mListAdapter;
    private List<UC_World_CarListJavaBean> mListData = new ArrayList<>();
    private boolean isFirst = true; //是否是第一次进来
    private HttpPublisher mHttpPublisher;
    private MeProvider mProvider;
    private boolean refreshIsRequest;
    private int pageSize = 10;
    private int pageCurrent = 0;
    private int isPullDown = 1;
    public static final int isNewFlag = 0;//是否是新车 1--新车 0--二手车
    private ImageView mEmptyImg;
    private SetEmptyViewUtil setEmptyViewUtil;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me_newcar_fragment, container, false);
        initView(view);
        initData();
        isFirst = false;
        return view;
    }

/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/

    /**
     * @desc (初始化view)
     * @createtime 2016/12/5 - 20:17
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
        mlistView.setMode(PullToRefreshBase.Mode.BOTH);
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

        mListAdapter = new UC_World_CarList_Adapter(getActivity());
        mlistView.setAdapter(mListAdapter);

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
        mHttpPublisher.initialize(getActivity());
        mProvider = MeProvider.getInstance();
        mProvider.initialize(mHttpPublisher);

        pageCurrent = 0;
        mProvider.getCollectList(pageSize, pageCurrent, isNewFlag, 0);
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
                UC_World_CarListJavaBean mCF_LoanListBean = JSON.toJavaObject((JSON) data.get(i), UC_World_CarListJavaBean.class);
                mListData.add(mCF_LoanListBean);
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
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {
            ShowNullView("Sorry,您访问的页面找不到了......", false, setEmptyViewUtil.NoServerView);
        } else {//服务
            toastMsg(msg);
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
        View listItem = mListAdapter.getView(0, null, mlistView);
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


    /**
     * @desc (不是第一次进来时，再得到一次缓存数据)
     * @createtime 2016/12/5 - 20:18
     * @author xiaoxue
     */
    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst) {
            if (FrameEtongApplication.getApplication().getUsedCarCollectCache().isChanged()) {
                pageCurrent = 0;
                mProvider.getCollectList(pageSize, pageCurrent, isNewFlag, 0);
            }
        }
    }
//    /**
//     * @desc (按价格排序)
//     * @createtime 2016/12/5 - 20:22
//     * @author xiaoxue
//     */
//    public class MyComp implements Comparator<UC_World_CarListJavaBean> {
//        @Override
//        public int compare(UC_World_CarListJavaBean o1, UC_World_CarListJavaBean o2) {
//            return Double.valueOf(o1.getF_price()).compareTo(Double.valueOf(o2.getF_price()));
//        }
//    }
    /*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/
}
