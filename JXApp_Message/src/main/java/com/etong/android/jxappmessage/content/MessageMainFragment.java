package com.etong.android.jxappmessage.content;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.utils.Etong_DateToStringUtil;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.ShowSideViewData;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.loopbanner.BGABanner;
import com.etong.android.frame.widget.nestedscrollview.IScrollView;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshScrollView;
import com.etong.android.jxappmessage.R;
import com.etong.android.jxappmessage.adapter.MessageListAdapter;
import com.etong.android.jxappmessage.javabean.MessageBannerModel;
import com.etong.android.jxappmessage.javabean.MessageImageCarouselBean;
import com.etong.android.jxappmessage.javabean.MessageWebViewBean;
import com.etong.android.jxappmessage.utils.MessageNoScrollListView;
import com.etong.android.jxappmessage.utils.MessageProvider;
import com.etong.android.jxappmessage.utils.MessageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 资讯页面
 * Created by Administrator on 2016/8/2.
 */
public class MessageMainFragment extends BaseSubscriberFragment implements BGABanner.OnItemClickListener, BGABanner.Adapter {
    private HttpPublisher mHttpPublisher;
    private MessageProvider mMessageProvider;
    private TitleBar mTitleBar;
    //    private MessageShowSideView mSideView;
    private static final String SIDE_VIEW_TAG = "side view tag";
    //    private ListAdapter<MessageWebViewBean> mListAdapter;
    private ImageProvider mImageProvider;
    private MessageNoScrollListView mListView;
    private PullToRefreshScrollView mSV;
    private LinearLayout ll_xianshigou;
    private LinearLayout ll_goucheyouhui;
    private LinearLayout ll_weibaoyouhui;
    private LinearLayout ll_chexun;
    private LinearLayout ll_chezhanhuodong;
    private LinearLayout ll_chedaiyouhui;
    private LinearLayout ll_changtongkahuodong;
    private LinearLayout ll_baoxianyouhui;

    private ImageView image_xianshigou;
    private TextView text_xianshigou;
    private List<MessageWebViewBean> mList = new ArrayList<>();
    String[] photo = {"message_bander_nowait", "message_bander_connaught", "message_bander_beau", "message_bander_audi"};
    private ImageButton message_search;
    private TextView limited_buy;
    private TextView gallon;
    private TextView maintenance;
    private TextView motorzine;
    private TextView car_show;
    private TextView car_loans;
    private TextView happly_car;
    private TextView insurance;
    private LinearLayout mNullLayout;
    private int startNumber = 0;
    private int finishNumber = 5;//取的个数
    private boolean isRequest = true;//设置是否请求接口  true 请求  false 不请求
    private int isPullDown = 1;
    // 顶部轮播图
    private BGABanner topMainBanner;
    // 轮播图中的模型
    private MessageBannerModel bannerModel;
    // 展示图片的选项
    private static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .showImageForEmptyUri(R.mipmap.message_bander_ic)           // 错误的状态都显示默认的图片
            .showImageOnFail(R.mipmap.message_bander_ic)
            .showImageOnLoading(R.mipmap.message_bander_ic)
            .build();
    // 使用universal-image-loader插件读取网络图片，需要工程导入universal-image-loader-1.9.4.jar
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ViewGroup root;
    private int mWinWidth;  // 最小的高度

    private long refreshTime;
    private static long currentRefreshedTime = 5 * 1000;
    private boolean isOnResume = false;
    private MessageListAdapter mListAdapter;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_main_frg,
                container, false);

        WindowManager mWm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);
        mWinWidth = mWm.getDefaultDisplay().getWidth();

        initView(view);
        //请求推荐的资讯列表
        setData();

//        initSideViewData(photo);
        // 初始化banner轮播图中的数据及Adapter
        initBannerViewData(photo);
        return view;
    }


    protected void setData() {
        startNumber = 0;
        mMessageProvider.MessageType(String.valueOf(startNumber), String.valueOf(finishNumber), String.valueOf(1), 1, 0);
//        mMessageProvider.MessageTypeList(null,String.valueOf(startNumber), String.valueOf(finishNumber), String.valueOf(1), null, true);
    }

    //得到后台返回的数据  资讯列表
    @Subscriber(tag = FrameHttpTag.MESSAGE_LIST)
    protected void getMessageList(HttpMethod method) {

        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

//        mSV.onRefreshComplete();
        // 如果返回的数据不为服务器返回的格式
        if (null != errno && errno.equals("PT_ERROR_SUCCESS")) {
            mSV.setMode(PullToRefreshBase.Mode.BOTH);
            JSONArray jsonArr = method.data().getJSONArray("data");
            isPullDown = Integer.valueOf((String) method.getParam().get("isPullDown"));
            //清空数据
            if (1 != isPullDown) {
                mList.clear();
            }
            //判断全部数据是否加载完
            if (jsonArr.size() < finishNumber) {
                isRequest = false;//不请求数据
                if (1 == isPullDown) {//上拉
                    toastMsg("已加载全部数据");
                    mSV.onRefreshComplete();
                    mSV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
                } else {
                    if (jsonArr.size() == 0) {
                        isRequest = false;//不请求数据
                        toastMsg("已加载全部数据");
                    } else {
                    }
                }
            }

            for (int i = 0; i < jsonArr.size(); i++) {
                MessageWebViewBean mMessageWebViewBean = JSON.toJavaObject(jsonArr.getJSONObject(i), MessageWebViewBean.class);
                mList.add(mMessageWebViewBean);
            }
            if (isPullDown == 1) {//上拉
                mListAdapter.update(mList);
            } else {
                isRequest = true;//允许请求数据
                mListAdapter.update(mList);
            }
            //没数据则只下拉
            if (mListAdapter.isEmpty()) {
                mSV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
            }
            mSV.onRefreshComplete();//刷新完成
        } else {
            mSV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
            mSV.onRefreshComplete();
//            toastMsg(msg);
        }
    }

    protected void initView(View view) {
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mMessageProvider = MessageProvider.getInstance();
        mMessageProvider.initialize(mHttpPublisher);

        mImageProvider = ImageProvider.getInstance();
        mImageProvider.initialize(getActivity());

        message_search = (ImageButton) view.findViewById(R.id.message_img_search);
        addClickListener(view, R.id.message_img_search);

        topMainBanner = findViewById(view, R.id.banner_main_accordion, BGABanner.class);
        topMainBanner.setOnItemClickListener(this);
        root = (ViewGroup) topMainBanner.getRootView();           // 获取到包裹轮播图的ViewGroup

        mListView = (MessageNoScrollListView) view.findViewById(R.id.message_lv_messagelist);

        mSV = (PullToRefreshScrollView) view.findViewById(R.id.message_sv_scroll);

        //初始化8个模块
        ll_xianshigou = (LinearLayout) view.findViewById(R.id.message_ll_time_limited_buy);
        limited_buy = (TextView) view.findViewById(R.id.message_txt_time_limited_buy);
        ll_goucheyouhui = (LinearLayout) view.findViewById(R.id.message_ll_the_gallon);
        gallon = (TextView) view.findViewById(R.id.message_txt_the_gallon);

        ll_weibaoyouhui = (LinearLayout) view.findViewById(R.id.message_ll_maintenance);
        maintenance = (TextView) view.findViewById(R.id.message_txt_maintenance);

        ll_chexun = (LinearLayout) view.findViewById(R.id.message_ll_motorzine);
        motorzine = (TextView) view.findViewById(R.id.message_txt_motorzine);

        ll_chezhanhuodong = (LinearLayout) view.findViewById(
                R.id.message_ll_car_show);
        car_show = (TextView) view.findViewById(R.id.message_txt_car_show);


        ll_chedaiyouhui = (LinearLayout) view.findViewById(R.id.message_ll_car_loans);
        car_loans = (TextView) view.findViewById(R.id.message_txt_car_loans);

        ll_changtongkahuodong = (LinearLayout) view.findViewById(R.id.message_ll_happly_car);
        happly_car = (TextView) view.findViewById(R.id.message_txt_happly_car);

        ll_baoxianyouhui = (LinearLayout) view.findViewById(R.id.message_ll_insurance);
        insurance = (TextView) view.findViewById(R.id.message_txt_insurance);


        addClickListener(view, R.id.message_ll_time_limited_buy);
        addClickListener(view, R.id.message_ll_the_gallon);
        addClickListener(view, R.id.message_ll_maintenance);
        addClickListener(view, R.id.message_ll_motorzine);
        addClickListener(view, R.id.message_ll_car_show);
        addClickListener(view, R.id.message_ll_car_loans);
        addClickListener(view, R.id.message_ll_happly_car);
        addClickListener(view, R.id.message_ll_insurance);

        mSV.setMode(PullToRefreshBase.Mode.BOTH);

        mSV.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        mSV.getLoadingLayoutProxy(false, true).setRefreshingLabel(
                "正在加载更多...");
        mSV.getLoadingLayoutProxy(false, true).setReleaseLabel(
                "松开加载更多");

        mSV.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<IScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<IScrollView> refreshView) {

                //延迟0.3s
                mSV.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setData();
                    }
                }, 300);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<IScrollView> refreshView) {

                //延迟3s
                mSV.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isRequest) {//请求接口
                            startNumber = startNumber + finishNumber;
                            mMessageProvider.MessageType(String.valueOf(startNumber), String.valueOf(finishNumber), String.valueOf(1), 1, 1);
                        } else {//不请求接口
                            mSV.onRefreshComplete();//刷新完成
                            toastMsg("已加载全部数据");
                        }
                    }
                }, 300);
            }
        });

        mListAdapter = new MessageListAdapter(getActivity());
        mListView.setAdapter(mListAdapter);
    }


    @Override
    protected void onClick(View view) {
        super.onClick(view);
        //限时购活动
        if (view.getId() == R.id.message_ll_time_limited_buy) {
            Intent intent = new Intent(getActivity(), MessageListActivity.class);
            intent.putExtra("type", 10002);//分类id
            intent.putExtra("title", limited_buy.getText().toString());
            startActivity(intent);
//

        }
        //购车优惠
        else if (view.getId() == R.id.message_ll_the_gallon) {
            Intent intent = new Intent(getActivity(), MessageListActivity.class);
            intent.putExtra("type", 10004);
            intent.putExtra("title", gallon.getText().toString());
            startActivity(intent);

        }
        //维保优惠
        else if (view.getId() == R.id.message_ll_maintenance) {
            Intent intent = new Intent(getActivity(), MessageListActivity.class);
            intent.putExtra("type", 10006);
            intent.putExtra("title", maintenance.getText().toString());
            startActivity(intent);

        }
        //车讯

        else if (view.getId() == R.id.message_ll_motorzine) {
            Intent intent = new Intent(getActivity(), MessageListActivity.class);
            intent.putExtra("type", 10000);
            intent.putExtra("title", motorzine.getText().toString());
            startActivity(intent);
        }
        //车展活动
        else if (view.getId() == R.id.message_ll_car_show) {
            Intent intent = new Intent(getActivity(), MessageListActivity.class);
            intent.putExtra("type", 10001);
            intent.putExtra("title", car_show.getText().toString());
            startActivity(intent);
        }
        //车贷优惠
        else if (view.getId() == R.id.message_ll_car_loans) {
            Intent intent = new Intent(getActivity(), MessageListActivity.class);
            intent.putExtra("type", 10003);
            intent.putExtra("title", car_loans.getText().toString());
            startActivity(intent);
        }
        //畅通卡活动
        else if (view.getId() == R.id.message_ll_happly_car) {
            Intent intent = new Intent(getActivity(), MessageListActivity.class);
            intent.putExtra("type", 10005);
            intent.putExtra("title", happly_car.getText().toString());
            startActivity(intent);
        }
        //保险优惠
        else if (view.getId() == R.id.message_ll_insurance) {
            Intent intent = new Intent(getActivity(), MessageListActivity.class);
            intent.putExtra("type", 10007);
            intent.putExtra("title", insurance.getText().toString());
            startActivity(intent);
        } else if (view.getId() == R.id.message_img_search) {//搜索按钮
            Intent intent = new Intent(getActivity(), MessageSearchActivity.class);
            startActivity(intent);
        }
    }

    /**
     * @param
     * @return
     * @desc (初始化轮播图中的数据)
     * @user sunyao
     * @createtime 2016/9/13 - 10:55
     */
    protected void initBannerViewData(String[] array) {
        bannerModel = new MessageBannerModel();

        if (null == array)
            return;
        // 初始化其中的图片
        List<String> imageStr = new ArrayList<String>();
        for (String Str : array) {
            String url = MessageUtils.getImageUrl(Str);
            if (!TextUtils.isEmpty(url))
                imageStr.add(url);
        }
        bannerModel.imgs = imageStr;

        topMainBanner.setAdapter(MessageMainFragment.this);
        topMainBanner.setData(bannerModel.imgs, bannerModel.tips);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            if (!isOnResume) {
                if (null != mListView) {
                    mSV.getRefreshableView().scrollTo(0, 0);//listView不上滑的方法(获取PullToRefreshScrollView的ScrollView视图)
                    mListView.setFocusable(false);
                }
            }
            isOnResume = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        isOnResume = true;

        L.e("onResume()-----------------");
        if (topMainBanner != null) {
            topMainBanner.startAutoPlay();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        L.e("onPause()-----------------");

        if (topMainBanner != null) {
            topMainBanner.stopAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        L.e("onStop()-----------------");
    }

    @Override
    public void onStart() {
        super.onStart();

        L.e("onStart()-----------------");
    }

    /**
     * @param
     * @return
     * @desc (填充Banner图中的数据)
     * @user sunyao
     * @createtime 2016/9/13 - 10:22
     */
    @Override
    public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
        try {
            imageLoader.displayImage(model.toString(), (ImageView) view, options,
                    new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String arg0, View arg1) {
                            L.d("onLoadingStarted", arg0);
                        }

                        @Override
                        public void onLoadingFailed(String arg0, View arg1,
                                                    FailReason arg2) {

                            L.d("onLoadingFailed", arg0);
                        }

                        @Override
                        public void onLoadingComplete(String arg0, View arg1,
                                                      Bitmap arg2) {
                            L.d("onLoadingComplete", arg0);
                            if (arg2 == null) {
                                return;
                            }
                            int with = arg2.getWidth() - 2;
                            int height = arg2.getHeight();
                            topMainBanner.getLayoutParams().width = mWinWidth;
                            topMainBanner.getLayoutParams().height = mWinWidth * height
                                    / with;
                        }

                        @Override
                        public void onLoadingCancelled(String arg0, View arg1) {
                        }
                    });
        } catch (Exception e) {
        }
    }

    /**
     * @param
     * @return
     * @desc (banner图中的点击事件)
     * @user sunyao
     * @createtime 2016/9/13 - 10:22
     */
    @Override
    public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
        L.d("点击了：", position + "---" + model.toString());
    }


    @Subscriber(tag = "NetWorkChanged")
    private void requestData(String str) {
        if (null != mListAdapter && mListAdapter.isEmpty()) {
            L.d("---------------------->", "资讯重新刷新数据");
            isPullDown = 1;
            startNumber = 0;
            isRequest = true;
            setData();
        }
    }

}
