package com.etong.android.jxbizusedcar.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.user.UC_CollectOrScannedBean;
import com.etong.android.frame.user.UC_FrameEtongApplication;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.dialog.UC_CancelConformDialog;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappusedcar.bean.UC_CollectOrScannedCarInfo;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.adapter.UC_CollectOrScannedListAdapter;
import com.etong.android.jxbizusedcar.subscriber.UC_SubscriberActivity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * 车辆收藏
 * Created by Administrator on 2016/10/14.
 */

public class UC_CollectCarActivity extends UC_SubscriberActivity implements UC_CollectOrScannedListAdapter.UC_CarCallBack {

    private PullToRefreshListView mSlideListView;
    private UC_CollectOrScannedListAdapter mListAdapter;
    private TitleBar mTitleBar;
    private List<UC_CollectOrScannedCarInfo> mData = new ArrayList<UC_CollectOrScannedCarInfo>();
    private int pageSize = 10;
    private int pageCurrent = 0;
    private boolean isInitRefresh = false;     //是否可以上拉加载
    private UC_CancelConformDialog cleanDialog;
    private boolean isFirst = true;
    private ViewGroup mNullView;               //数据为空视图
    private ImageView mNullImg;
    private TextView mNullTxt;

    @Override
    protected void myInit(Bundle bundle) {
        setContentView(R.layout.uc_activity_scanned_car_content);

        mProvider.collectList(pageSize, pageCurrent);
        initView();
        initAdapter();
        initCleanDialog();
    }

    private void initView() {

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("收藏车辆");
        mTitleBar.setNextButton("清空");
        mTitleBar.setNextTextColor("#cf1c36");
        mTitleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanDialog.show();
            }
        });

        mNullView = findViewById(R.id.uc_collect_ll_null, ViewGroup.class);
        mNullTxt = findViewById(R.id.uc_collect_txt_null, TextView.class);
        mNullImg = findViewById(R.id.uc_collect_img_null, ImageView.class);
        mNullView.setClickable(false);
        mNullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProvider.collectList(pageSize, pageCurrent);
            }
        });

        mSlideListView = findViewById(R.id.uc_sv_scroll, PullToRefreshListView.class);
        mSlideListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mSlideListView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        mSlideListView.getLoadingLayoutProxy(false, true).setRefreshingLabel(
                "正在加载更多...");
        mSlideListView.getLoadingLayoutProxy(false, true).setReleaseLabel(
                "松开加载更多");
        mSlideListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mListAdapter.close();
            }
        });

        mSlideListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mSlideListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mProvider.collectList(pageSize, ++pageCurrent);
                    }
                }, 300);
            }
        });
    }


    private void initAdapter() {

        mListAdapter = new UC_CollectOrScannedListAdapter(this, UC_CollectCarActivity.this);
        mSlideListView.setAdapter(mListAdapter);
    }

    /**
     * @desc (如果listView的高度小于屏幕的高度  就不刷新)
     */
    protected void initIsRefresh(int size) {
        int listHeight = (int) (110 * mDensity + 0.5f) * size;
        if (listHeight < mHeight - (int) (42 * mDensity + 0.5f) - 30) {
            mSlideListView.setMode(PullToRefreshBase.Mode.DISABLED);
        }
    }

    /**
     * @desc (是否清空收藏的dialog)
     */
    private void initCleanDialog() {
        cleanDialog = new UC_CancelConformDialog(this);
        cleanDialog.setContent("确定清空收藏车辆么？");

        cleanDialog.setConfirmButtonClickListener("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mData.isEmpty()) {
                    mProvider.deleteColletData();
                    loadStart(null, 0);
                } else {
                    toastMsg("您已清空所有收藏~");
                }
                cleanDialog.dismiss();
            }
        });
    }

    /**
     * @desc (收藏车辆列表接口回调)
     */
    @Subscriber(tag = UC_HttpTag.COLLECT_LIST)
    public void collectListResult(HttpMethod method) {
        showListView();
        mSlideListView.onRefreshComplete();//刷新完成
        String status = method.data().getString("status");
        JSONArray dataArray = method.data().getJSONArray("data");
        String msg = method.data().getString("msg");

        if (status.equals("true")) {
            if (0 != dataArray.size()) {
                for (int i = 0; i < dataArray.size(); i++) {
                    UC_CollectOrScannedCarInfo mCollectOrScannedCarInfo = JSON.toJavaObject(dataArray.getJSONObject(i), UC_CollectOrScannedCarInfo.class);
                    mData.add(mCollectOrScannedCarInfo);
                }
                mListAdapter.updateCarList(mData);
                if (!isInitRefresh) {
                    initIsRefresh(dataArray.size());
                    isInitRefresh = true;
                }
            } else {
                if (!isFirst) {
                   toastMsg("已加载全部");
                }
                mSlideListView.setMode(PullToRefreshBase.Mode.DISABLED);
            }

            if (mData.isEmpty()) {
                showNullView();
            }
        } else if(status.equals(HttpPublisher.NETWORK_ERROR)){
            showNoNetworkView();
        }else if(status.equals(HttpPublisher.DATA_ERROR)){
            showNoServiceView();
        }

        isFirst = false;
    }

    /**
     * @desc (清空收藏接口回调)
     */
    @Subscriber(tag = UC_HttpTag.DELETE_COLLECT_DATA)
    public void deleteCollectResult(HttpMethod method) {
        String status = method.data().getString("status");
        String msg = method.data().getString("msg");
        loadFinish();
        if (status.equals("true")) {
            mData.clear();
            mListAdapter.updateCarList(mData);


            if (mData.isEmpty()) {
                showNullView();
            }

            UC_CollectOrScannedBean tempCollect = new UC_CollectOrScannedBean();
            tempCollect.setChanged(true);
            UC_FrameEtongApplication.getApplication().setCollectCache(tempCollect);

            toastMsg("清空成功!");
        } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {
            toastMsg("清空失败，请检查网络!");
        } else {
            toastMsg("清空失败!");
        }
    }


    /**
    * @desc (删除单条收藏接口回调)
    */
    @Subscriber(tag = UC_HttpTag.DELETE_ONE_COLLECT_DATA)
    public void deleteOneCollectResult(HttpMethod method) {
        String status = method.data().getString("status");
        String msg = method.data().getString("msg");
        int pos = Integer.valueOf((String) method.getParam().get("position"));
        int f_collectid = Integer.valueOf((String) method.getParam().get("f_collectid"));

        if (status.equals("true")) {
            mData.remove(pos);
            mListAdapter.updateCarList(mData);


            if (mData.isEmpty()) {
                showNullView();
            }

            toastMsg("删除成功!");
            UC_CollectOrScannedBean tempCollect = UC_FrameEtongApplication.getApplication().getCollectCache();
            if (null != tempCollect.getCarList()) {
                if (tempCollect.getCarList().contains(f_collectid + "")) {
                    List<String> collectList = tempCollect.getCarList();
                    collectList.remove(f_collectid + "");
                    tempCollect.setCarList(collectList);
                }
            }
            tempCollect.setChanged(true);
            UC_FrameEtongApplication.getApplication().setCollectCache(tempCollect);

        } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {
            mListAdapter.close();
            toastMsg("删除失败，请检查网络!");
        } else {
            mListAdapter.close();
            toastMsg("删除失败!");
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirst) {
            mData.clear();
            pageCurrent = 0;
            isInitRefresh = false;
            mSlideListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
            mProvider.collectList(pageSize, pageCurrent);
            isFirst = true;
        }
    }

    /**
     * @desc (显示收藏列表视图)
    */
    private void showListView() {
        mSlideListView.setVisibility(View.VISIBLE);
        mNullView.setVisibility(View.GONE);
        mNullView.setClickable(false);
    }

    /**
     * @desc (显示数据为空的视图)
     */
    private void showNullView() {
        mSlideListView.setVisibility(View.GONE);
        mNullView.setVisibility(View.VISIBLE);
        mNullView.setClickable(false);
        mNullImg.setBackgroundResource(R.mipmap.uc_ic_no_cotent_img);
        mNullTxt.setText("亲，暂时没有内容哦");
    }

    /**
     * @desc (显示网络错误的视图)
     */
    private void showNoNetworkView() {
        mSlideListView.setVisibility(View.GONE);
        mNullView.setVisibility(View.VISIBLE);
        mNullView.setClickable(true);
        mNullImg.setBackgroundResource(R.mipmap.uc_ic_no_network_img);
        mNullTxt.setText("亲，网络不给力哦\n点击屏幕重试");
    }

    /**
     * @desc (显示网络错误的视图)
     */
    private void showNoServiceView() {
        mSlideListView.setVisibility(View.GONE);
        mNullView.setVisibility(View.VISIBLE);
        mNullView.setClickable(false);
        mNullImg.setBackgroundResource(R.mipmap.uc_ic_no_service_img);
        mNullTxt.setText("Sorry,您访问的界面找不到了...");
    }




    /**
     * @desc (删除回调)
     */
    @Override
    public void answerCarDelete(int position) {
        mProvider.deleteOneColletData(mData.get(position).getF_collectid() + "", position + "");

    }
}
