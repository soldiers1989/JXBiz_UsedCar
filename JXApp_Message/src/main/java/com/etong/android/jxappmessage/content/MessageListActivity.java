package com.etong.android.jxappmessage.content;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappmessage.R;
import com.etong.android.jxappmessage.adapter.MessageListAdapter;
import com.etong.android.jxappmessage.javabean.MessageWebViewBean;
import com.etong.android.jxappmessage.utils.MessageProvider;

import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 资讯 8块点进来的列表页
 * Created by Administrator on 2016/8/30.
 */
public class MessageListActivity extends BaseSubscriberActivity {

    private TitleBar mTitleBar;
    private String mTitle = null;
    private HttpPublisher mHttpPublisher;
    private MessageProvider mMessageProvider;
    private PullToRefreshListView message_lv_list;
    private int id;
    private List<MessageWebViewBean> mList = new ArrayList<>();
    private ImageProvider mImageProvider;
    private int startNumber = 0;
    private int finishNumber = 10;//取的个数     //第一页应该是 start 0 ，limit 10     第二页 start 10 limit 10
    private boolean isRequest = true;//设置是否请求接口  true 请求  false 不请求
    private boolean isFirst = true;
    private LinearLayout mEmptyView;
    private TextView mEmptyTxt;
    private boolean refreshIsRequest;
    private int isPullDown = 1;
    private MessageListAdapter mListAdapter;
    private ImageView mNullImg;
    private SetEmptyViewUtil setEmptyViewUtil;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.message_activity_list);
        initView();
        initData();
//        initIsRefresh();
    }

    //请求资讯列表数据
    protected void initData() {
        startNumber = 0;
        mMessageProvider.MessageTypeList(String.valueOf(id), String.valueOf(startNumber), String.valueOf(finishNumber), null, null, 1, 0);
    }

    @Subscriber(tag = FrameHttpTag.MESSAGE_TYPE_LIST)
    public void getMessageList(HttpMethod method) {
        ShowListView();
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        if (!TextUtils.isEmpty(errno) && errno.equals("PT_ERROR_SUCCESS")) {
            message_lv_list.setMode(PullToRefreshBase.Mode.BOTH);
            JSONArray jsonArr = method.data().getJSONArray("data");
            isPullDown = Integer.valueOf((String) method.getParam().get("isPullDown"));
            //清空数据
            if (1 != isPullDown) {
                mList.clear();
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
                        ShowNullView("", false, setEmptyViewUtil.NullView);
                    } else {
//                        message_lv_list.onRefreshComplete();
//                        message_lv_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
                    }
                }
            }
            //添加数据
            for (int i = 0; i < jsonArr.size(); i++) {
                MessageWebViewBean mMessageWebViewBean = JSON.toJavaObject(jsonArr.getJSONObject(i), MessageWebViewBean.class);
                mList.add(mMessageWebViewBean);
            }
            if (isPullDown == 1) {//上拉
                mListAdapter.update(mList);
            } else {
                refreshIsRequest = true;//允许请求数据
                mListAdapter.update(mList);
                setIsPullUpRefresh(mList.size());
            }
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {//网络
            ShowNullView("点击屏幕重试", true, setEmptyViewUtil.NetworkErrorView);
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {
            ShowNullView("Sorry,您访问的页面找不到了......", false, setEmptyViewUtil.NoServerView);
        } else {//服务
//            toastMsg(msg);
        }
        message_lv_list.onRefreshComplete();//刷新完成
    }

    protected void initView() {
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mMessageProvider = MessageProvider.getInstance();
        mMessageProvider.initialize(mHttpPublisher);
        mImageProvider = ImageProvider.getInstance();
        mImageProvider.initialize(this);
        mTitleBar = new TitleBar(this);
        mTitleBar.showBottomLin(false);
        mTitleBar.showNextButton(false);

        Intent intent = this.getIntent();
        mTitle = intent.getStringExtra("title");
        id = intent.getIntExtra("type", -1);
        mTitleBar.setTitle(mTitle);

        message_lv_list = findViewById(R.id.message_lv_list, PullToRefreshListView.class);
        mEmptyView = findViewById(R.id.default_empty_content, LinearLayout.class);
        mEmptyTxt = findViewById(R.id.default_empty_lv_textview, TextView.class);
        mNullImg = findViewById(R.id.default_empty_img, ImageView.class);
        setEmptyViewUtil = new SetEmptyViewUtil(mEmptyView, mNullImg, mEmptyTxt);
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
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                //延迟0.3s
                message_lv_list.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                    }
                }, 300);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                //延迟3s
                message_lv_list.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshIsRequest) {//请求接口
                            startNumber = startNumber + finishNumber;
                            mMessageProvider.MessageTypeList(String.valueOf(id), String.valueOf(startNumber), String.valueOf(finishNumber), null, null, 1, 1);
                        } else {//不请求接口
                            message_lv_list.onRefreshComplete();//刷新完成
                            toastMsg("已加载全部数据");
                        }
                    }
                }, 300);
            }
        });

        mListAdapter = new MessageListAdapter(this);
        message_lv_list.setAdapter(mListAdapter);

    }

    /**
     * @desc (显示空视图)
     * @createtime 2016/12/7 - 9:16
     * @author xiaoxue
     */
    protected void ShowNullView(String text, boolean isClick, int type) {
        mEmptyView.setVisibility(View.VISIBLE);
        message_lv_list.setVisibility(View.GONE);
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
