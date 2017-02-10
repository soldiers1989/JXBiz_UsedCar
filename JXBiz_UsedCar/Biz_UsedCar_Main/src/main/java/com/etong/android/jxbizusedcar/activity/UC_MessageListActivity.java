package com.etong.android.jxbizusedcar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.adapter.UC_MessageArrayListAdapter;
import com.etong.android.jxbizusedcar.bean.UC_MessageWebViewBean;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯 9块点进来的列表页
 * Created by Administrator on 2016/8/30.
 */
public class UC_MessageListActivity extends BaseSubscriberActivity {

    private TitleBar mTitleBar;
    private String mTitle = null;
    private HttpPublisher mHttpPublisher;
    private PullToRefreshListView message_lv_list;
    //    private ListAdapter<UC_MessageWebViewBean> mListAdapter;
    private UC_MessageArrayListAdapter mListAdapter;
    private int id;
    private List<UC_MessageWebViewBean> mList=new ArrayList<>();
    private ImageProvider mImageProvider;
    private int startNumber = 0;
    private int finishNumber = 10;//取的个数     //第一页应该是 start 0 ，limit 10     第二页 start 10 limit 10
    private boolean isRequest = true;//设置是否请求接口  true 请求  false 不请求
    private UC_UserProvider mProvider;
    private LinearLayout mEmptyView;
    private TextView mEmptyTxt;
    private ImageView mNullImg;
    private boolean refreshIsRequest;
    private int isPullDown = 1;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_message_activity_list);
        initView();
        initData();

    }

    //如果listView的高度小于屏幕的高度  就不刷新
    protected void initIsRefresh() {
        int listHeight = message_lv_list.getRefreshableView().getHeight();
        if (listHeight < mHeight) {
            message_lv_list.setMode(PullToRefreshBase.Mode.DISABLED);
        }
    }

    //请求资讯列表数据
    protected void initData() {
        startNumber = 0;
        mProvider.MessageTypeList(String.valueOf(id), String.valueOf(startNumber), String.valueOf(finishNumber), null, null, 2,0);
        loadStart("加载中...", 0);
    }


    @Subscriber(tag = UC_HttpTag.MESSAGE_TYPE_LIST)
    public void getMessageList(HttpMethod method) {
        loadFinish();
        message_lv_list.setVisibility(View.VISIBLE);
        mListAdapter.clear();
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");

        if (null != errno && errno.equals("PT_ERROR_SUCCESS")) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            isPullDown = Integer.valueOf((String) method.getParam().get("isPullDown"));
            //清空数据
            if (1 != isPullDown) {
                mList.clear();
            }
            if (jsonArr.size() == 0) {
                isRequest = false;//不请求数据
                toastMsg("已加载全部数据");
            }
            //判断全部数据是否加载完
            if (jsonArr.size() < finishNumber) {
                refreshIsRequest = false;//不请求数据
                if (1 == isPullDown) {//上拉
                    toastMsg("已加载全部数据");
                    message_lv_list.onRefreshComplete();
                    message_lv_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
                } else {
                    if (jsonArr.size() == 0) {
                        setEmptyView(R.mipmap.uc_ic_no_cotent_img, "亲,暂时没有活动哦", false);
                    }else {
//                        message_lv_list.onRefreshComplete();
//                        message_lv_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
                    }
                }
            }
            for (int i = 0; i < jsonArr.size(); i++) {
                UC_MessageWebViewBean mMessageWebViewBean = JSON.toJavaObject(jsonArr.getJSONObject(i), UC_MessageWebViewBean.class);
                mList.add(mMessageWebViewBean);
            }
            if (isPullDown == 1) {//上拉
                mListAdapter.addAllData(mList);
                message_lv_list.onRefreshComplete();//刷新完成
            } else {
                refreshIsRequest = true;//允许请求数据
                mListAdapter.addAllData(mList);
                setIsPullUpRefresh(mList.size());
                message_lv_list.onRefreshComplete();//刷新完成
            }
        } else if (null != status && status.equals(HttpPublisher.NETWORK_ERROR)) {//服务器无响应
            setEmptyView(R.mipmap.uc_ic_no_network_img, "亲,网络不给力哦\n点击屏幕重试", true);
            message_lv_list.onRefreshComplete();
        } else if (null != status && status.equals(HttpPublisher.DATA_ERROR)) {//数据请求失败
            setEmptyView(R.mipmap.uc_ic_no_service_img, "Sorry,您访问的页面找不到了......", false);
            message_lv_list.onRefreshComplete();
        }
    }

    protected void initView() {
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mProvider = UC_UserProvider.getInstance();
        mProvider.initalize(mHttpPublisher);
        mImageProvider = ImageProvider.getInstance();
        mImageProvider.initialize(this);
        mTitleBar = new TitleBar(this);
        mTitleBar.showBottomLin(false);
        mTitleBar.showNextButton(false);

        Intent intent = this.getIntent();
        mTitle = intent.getStringExtra("title");
        id = intent.getIntExtra("type", -1);
        mTitleBar.setTitle(mTitle);

        message_lv_list = findViewById(R.id.uc_message_lv_list, PullToRefreshListView.class);
        message_lv_list.setVisibility(View.GONE);

        mEmptyView = findViewById(R.id.default_empty_content, LinearLayout.class);
        mEmptyTxt = findViewById(R.id.default_empty_lv_textview, TextView.class);
        mNullImg = findViewById(R.id.default_empty_lv_image, ImageView.class);
        mEmptyView.setClickable(false);
        mEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });
        message_lv_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
        message_lv_list.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        message_lv_list.getLoadingLayoutProxy(false, true).setRefreshingLabel(
                "正在加载更多...");
        message_lv_list.getLoadingLayoutProxy(false, true).setReleaseLabel(
                "松开加载更多");
        message_lv_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {//下拉刷新
                //延迟0.3s
                message_lv_list.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                    }
                }, 300);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {//上拉加载
                startNumber = startNumber + finishNumber;

                if (isRequest) {
                    mProvider.MessageTypeList(String.valueOf(id), String.valueOf(startNumber), String.valueOf(finishNumber), null, null, 2,1);
                } else {
                    //延迟1s
                    message_lv_list.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            message_lv_list.onRefreshComplete();//刷新完成
                            toastMsg("已加载全部数据");
                        }
                    }, 300);

                }
            }
        });

        mListAdapter = new UC_MessageArrayListAdapter(UC_MessageListActivity.this);
        message_lv_list.setAdapter(mListAdapter);
    }

    /**
     * @desc (显示空视图)
     * @createtime 2016/12/7 - 9:16
     * @author xiaoxue
     */
    protected void setEmptyView(int image, String text, boolean isClick) {
        mEmptyView.setVisibility(View.VISIBLE);
        message_lv_list.setVisibility(View.GONE);
        mNullImg.setBackgroundResource(image);
        mEmptyTxt.setText(text);
        mEmptyView.setClickable(isClick);
    }

    /**
     * @desc (显示ListView视图)
     * @createtime 2016/12/7 - 9:15
     * @author xiaoxue
     */
    protected void ShowListView() {
        message_lv_list.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }

    /**
     * @desc (根据第一次请求的数据个数判断设置listview是否可上拉加载)
     * @createtime 2016/11/14 0014-16:06
     * @author wukefan
     */
    private void setIsPullUpRefresh(int size) {
        if (size >= finishNumber) {
            message_lv_list.setMode(PullToRefreshBase.Mode.BOTH);
            return;
        }
        View listItem = mListAdapter.getView(0, null, message_lv_list);
        listItem.measure(0, 0);
        int height = listItem.getMeasuredHeight();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mHeight = (dm.heightPixels - ((int) ((42) * dm.density + 0.5f)));
        if (height * size < mHeight) {
            message_lv_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }
    }
}
