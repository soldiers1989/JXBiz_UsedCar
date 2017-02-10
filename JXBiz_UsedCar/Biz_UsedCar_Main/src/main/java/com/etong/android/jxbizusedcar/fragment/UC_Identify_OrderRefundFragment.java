package com.etong.android.jxbizusedcar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.adapter.UC_Identify_OrderAdapter;
import com.etong.android.jxbizusedcar.bean.UC_Identify_OrderListBean;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (退款详情界面)
 * @createtime 2016/11/9 0009--16:00
 * @Created by wukefan.
 * <p>
 * f_tabstatus  :  全部-11，进行中-12，已完成-13，退款-14
 */
public class UC_Identify_OrderRefundFragment extends BaseSubscriberFragment {

    private int pageSize = 10;
    private int pageCurrent = 0;

    private List<UC_Identify_OrderListBean> mData = new ArrayList<>();
    private PullToRefreshListView mRefreshOrderList;
    private UC_UserProvider mUC_Provider;
    private UC_Identify_OrderAdapter mOrderListAdapter;
    public static final int tabStatus = 14;
    private int isPullDown = 1;
    private ViewGroup mNullView;
    private TextView mNullTxt;
    private ImageView mNullImg;
    private boolean refreshIsRequest;

      /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.uc_fragment_identify_order_all, container, false);

        HttpPublisher instance = HttpPublisher.getInstance();
        HttpPublisher.init(getActivity());
        mUC_Provider = UC_UserProvider.getInstance();
        mUC_Provider.initalize(instance);

        initView(view);
        initData();

        return view;
    }


/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/


    /**
     * 请使用快捷模板生成注释
     */
    private void initView(View view) {
        mRefreshOrderList = findViewById(view, R.id.uc_identify_order_all_lv, PullToRefreshListView.class);

        mNullView = findViewById(view, R.id.uc_identify_order_ll_null, ViewGroup.class);
        mNullTxt = findViewById(view, R.id.uc_identify_order_txt_null, TextView.class);
        mNullImg = findViewById(view, R.id.uc_identify_order_img_null, ImageView.class);
        mNullView.setClickable(false);
        mNullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageCurrent = 0;
                mUC_Provider.queryIdentified(tabStatus, pageSize, pageCurrent, 0);
            }
        });

        mRefreshOrderList.setMode(PullToRefreshBase.Mode.BOTH);
        mRefreshOrderList.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        mRefreshOrderList.getLoadingLayoutProxy(false, true).setRefreshingLabel(
                "正在加载更多...");
        mRefreshOrderList.getLoadingLayoutProxy(false, true).setReleaseLabel(
                "松开加载更多");

        mRefreshOrderList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //延迟0.3s
                mRefreshOrderList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            pageCurrent = 0;
                            mUC_Provider.queryIdentified(tabStatus, pageSize, pageCurrent, 0);
                    }
                }, 300);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //延迟3s
                mRefreshOrderList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshIsRequest) {//请求接口
                            mUC_Provider.queryIdentified(tabStatus, pageSize, ++pageCurrent, 1);
                        } else {//不请求接口
                            mRefreshOrderList.onRefreshComplete();//刷新完成
                            toastMsg("已加载全部数据");
                        }
                    }
                }, 300);
            }
        });

        mOrderListAdapter = new UC_Identify_OrderAdapter(getActivity(),tabStatus);

        mRefreshOrderList.setAdapter(mOrderListAdapter);
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
        pageCurrent = 0;
        mUC_Provider.queryIdentified(tabStatus, pageSize, pageCurrent, 0);
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

    //查询某个用户的所有车鉴定订单
    @Subscriber(tag = UC_HttpTag.QUERY_IDENTIFIED + tabStatus)
    public void queryIdentifiedResult(HttpMethod method) {
        ShowListView();
        String status = method.data().getString("status");
        String msg = method.data().getString("msg");

        if (status.equals("true")) {

            JSONArray dataArray = method.data().getJSONArray("data");

            isPullDown = Integer.valueOf((String) method.getParam().get("isPullDown"));

            if (1 != isPullDown) {
                mData.clear();
            }
            //判断全部数据是否加载完
            if (dataArray.size() < pageSize) {
                refreshIsRequest = false;//不请求数据
                if (1 == isPullDown) {//上拉
                    toastMsg("已加载全部数据");
                } else {
                    if (dataArray.size() == 0) {
                        ShowNullView(R.mipmap.uc_ic_no_cotent_img, "亲,暂时没有内容哦", false);
                    }
                }
            }
            //添加数据
            for (int i = 0; i < dataArray.size(); i++) {
                UC_Identify_OrderListBean mOrderListInfo = JSON.toJavaObject(dataArray.getJSONObject(i), UC_Identify_OrderListBean.class);
                mData.add(mOrderListInfo);
            }


            //更新下拉参数
            if (isPullDown == 1) {//上拉
                mOrderListAdapter.updateOrderList(mData);
            } else {
                refreshIsRequest = true;//允许请求数据
                mOrderListAdapter.updateOrderList(mData);
                setIsPullUpRefresh(mData.size());
            }

        } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {//服务器无响应
            ShowNullView(R.mipmap.uc_ic_no_network_img, "亲,网络不给力哦\n点击屏幕重试", true);
        } else if (status.equals(HttpPublisher.DATA_ERROR)) {//数据请求失败
            ShowNullView(R.mipmap.uc_ic_no_service_img, "Sorry,您访问的页面找不到了......", false);
        }
        mRefreshOrderList.onRefreshComplete();
    }

    /**
     * @desc (显示为空视图)
     * @createtime 2016/11/14 0014-16:11
     * @author wukefan
     */
    protected void ShowNullView(int image, String text, boolean isClick) {
        mNullView.setVisibility(View.VISIBLE);
        mRefreshOrderList.setVisibility(View.GONE);
        mNullImg.setBackgroundResource(image);
        mNullTxt.setText(text);
        mNullView.setClickable(isClick);
    }

    /**
     * @desc (显示listview视图)
     * @createtime 2016/11/14 0014-16:11
     * @author wukefan
     */
    protected void ShowListView() {
        mRefreshOrderList.setVisibility(View.VISIBLE);
        mNullView.setVisibility(View.GONE);
    }

    /**
     * @desc (得到退款的数据)
     * @createtime 2016/11/11 - 18:47
     * @author xiaoxue
     */
    @Subscriber(tag = UC_HttpTag.REFUND_AMT)
    protected void getRefund_amt(HttpMethod method) {
        String status = method.data().getString("status");
        String msg = method.data().getString("msg");
        String data = method.data().getString("data");

        if(status.equals("true")){
            toastMsg("退款成功");
//            mRefreshOrderList.setRefreshing();
            updataFrgmentContent();
        }else {
            toastMsg("退款失败");
        }
    }

    /**
     * @desc (更新Fragment内容)
     * @createtime 2016/11/14 0014-16:10
     * @author wukefan
     */
    public void updataFrgmentContent() {
        if (mData.isEmpty()) {
            pageCurrent = 0;
            mUC_Provider.queryIdentified(tabStatus, pageSize, pageCurrent, 0);
        } else {
            if (mRefreshOrderList.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_START) {//下拉
                mRefreshOrderList.setRefreshing();
            } else {//上拉
                mRefreshOrderList.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                mRefreshOrderList.setRefreshing();
                mRefreshOrderList.setMode(PullToRefreshBase.Mode.BOTH);
            }
        }
    }

    /**
     * @desc (根据第一次请求的数据个数判断设置listview是否可上拉加载)
     * @createtime 2016/11/14 0014-16:06
     * @author wukefan
     */
    private void setIsPullUpRefresh(int size) {
        if (size >= pageSize) {
            mRefreshOrderList.setMode(PullToRefreshBase.Mode.BOTH);
            return;
        }
        View listItem = mOrderListAdapter.getView(0, null, mRefreshOrderList);
        listItem.measure(0, 0);
        int height = listItem.getMeasuredHeight();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mHeight = (dm.heightPixels - ((int) ((46.5 + 42) * dm.density + 0.5f)));
        if (height * size < mHeight) {
            mRefreshOrderList.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }
    }
    /*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/
}
