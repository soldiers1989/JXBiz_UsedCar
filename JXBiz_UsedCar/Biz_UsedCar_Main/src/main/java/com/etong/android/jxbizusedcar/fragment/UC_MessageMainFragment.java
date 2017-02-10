package com.etong.android.jxbizusedcar.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.widget.EtongLineNoScrollGridView;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.loopbanner.BGABanner;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.activity.UC_MessageListActivity;
import com.etong.android.jxbizusedcar.adapter.UC_MessageArrayListAdapter;
import com.etong.android.jxbizusedcar.adapter.UC_MessageGridAdapter;
import com.etong.android.jxbizusedcar.bean.UC_MessageBannerModel;
import com.etong.android.jxbizusedcar.bean.UC_MessageWebViewBean;
import com.etong.android.jxbizusedcar.utils.UC_MessageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯页面
 * Created by Administrator on 2016/8/2.
 */
public class UC_MessageMainFragment extends BaseSubscriberFragment implements BGABanner.OnItemClickListener, BGABanner.Adapter, UC_MessageGridAdapter.UC_ItemCallBack {

    private HttpPublisher mHttpPublisher;
    private TitleBar mTitleBar;
    private UC_MessageArrayListAdapter mListAdapter;
    private ImageProvider mImageProvider;
    private PullToRefreshListView mListView;
    private List<UC_MessageWebViewBean> mList;
    //    String[] photo = {"uc_ic_banner_default"};
    String[] photo = {"message_bander_nowait", "message_bander_connaught", "message_bander_beau", "message_bander_audi"};
    private int startNumber = 0;
    private int finishNumber = 5;//取的个数
    private boolean isRequest = true;//设置是否请求接口  true 请求  false 不请求
    private int isPullDown = 0;

    // 顶部轮播图
    private BGABanner topMainBanner;
    // 轮播图中的模型
    private UC_MessageBannerModel bannerModel;
    // 展示图片的选项
    private static DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
            .build();
    // 使用universal-image-loader插件读取网络图片，需要工程导入universal-image-loader-1.9.4.jar
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private ViewGroup root;
    private int mWinWidth;  // 最小的高度

    private long refreshTime;
    private static long currentRefreshedTime = 5 * 1000;
    private View mListHead;
    private EtongLineNoScrollGridView mGridView;

    public final static String RECOMMEDED_DAILY = "每日推荐资讯";
    public final static String PREFERENTIAL_ACTIVITIES = "活动优惠资讯";
    public final static String AUCTION_NOTICE = "园区拍卖公告";
    public final static String COMMUNITY_INFORMATION = "社区资讯";
    public final static String AUTO_HOSPITAL_INFORMATION = "汽车医院资讯";
    public final static String INANCIAL_INFORMATION = "金融资讯";
    public final static String TRANSFER_POLICY_GUILD_REGULATIONS = "过户政策资讯";
    public final static String QUESTIONS_AND_ANSWERS = "二手车问答";
    public final static String OTHER_INFORMATION = "其他资讯";
    private UC_UserProvider mProvider;
    private boolean isOnResume = false;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.uc_message_main_frg,
                container, false);

        WindowManager mWm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);
        mWinWidth = mWm.getDefaultDisplay().getWidth();

        initHeadListView();
        initView(view);
        //请求推荐的资讯列表
        setData();

        // 初始化banner轮播图中的数据及Adapter
        initBannerViewData(photo);
        return view;
    }


    protected void setData() {
        mProvider.MessageType(String.valueOf(startNumber), String.valueOf(finishNumber), String.valueOf(1), 2);
    }

    //得到后台返回的数据  资讯列表
    @Subscriber(tag = UC_HttpTag.MESSAGE_LIST)
    protected void getMessageList(HttpMethod method) {

//        mListAdapter.clear();
        mList = new ArrayList<>();
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        // 如果返回的数据不为服务器返回的格式
//        String errCode = method.data().getString("errCode");
//        if (!TextUtils.isEmpty(errCode) && "4353".equals(errCode)) {
//            mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
//            mListView.onRefreshComplete();
//            return;
//        }
        if (null != errno && errno.equals("PT_ERROR_SUCCESS")) {
            mListView.setMode(PullToRefreshBase.Mode.BOTH);
            JSONArray jsonArr = method.data().getJSONArray("data");
            if (jsonArr.size() == 0 && isPullDown == 0) {
                isRequest = false;//不请求数据
                toastMsg("已加载全部数据");
            }

            for (int i = 0; i < jsonArr.size(); i++) {
                UC_MessageWebViewBean mMessageWebViewBean = JSON.toJavaObject(jsonArr.getJSONObject(i), UC_MessageWebViewBean.class);
                mList.add(mMessageWebViewBean);
            }

            if (isPullDown == 0) {//上拉
                startNumber = startNumber + finishNumber;
                mListAdapter.addAllData(mList);
            } else if (isPullDown == 1) {//下拉
//            if (mListAdapter.getCount() == 0) {//判断adapter是否有数据，没数据就加载数据
                startNumber = startNumber + finishNumber;
                mListAdapter.addFirstData(mList);
//            }
            }
            mListView.onRefreshComplete();//刷新完成
        } else {
            mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
            mListView.onRefreshComplete();
        }
    }

    protected void initView(View view) {
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mProvider = UC_UserProvider.getInstance();
        mProvider.initalize(mHttpPublisher);

        mTitleBar = new TitleBar(view);
        mTitleBar.setTitle("资讯");
        mTitleBar.showBottomLin(false);
        mTitleBar.showBackButton(false);
        mTitleBar.setTitleTextColor("#ffffff");//设置title颜色
        mTitleBar.setmTitleBarBackground("#cf1c36");//设置titlebar背景色
        mImageProvider = ImageProvider.getInstance();
        mImageProvider.initialize(getActivity());

        mListView = (PullToRefreshListView) view.findViewById(R.id.uc_message_lv_messagelist);
        mListView.getRefreshableView().addHeaderView(mListHead);//添加头部

        mListView.setMode(PullToRefreshBase.Mode.BOTH);

        mListView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        mListView.getLoadingLayoutProxy(false, true).setRefreshingLabel(
                "正在加载更多...");
        mListView.getLoadingLayoutProxy(false, true).setReleaseLabel(
                "松开加载更多");

        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshTime = System.currentTimeMillis();
                isPullDown = 1;
                startNumber = 0;
                isRequest = true;
                //延迟1s
                mListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setData();
                    }
                }, 300);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //延迟1s
                mListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        isPullDown = 0;
                        if (isRequest) {
                            mProvider.MessageType(String.valueOf(startNumber), String.valueOf(finishNumber), String.valueOf(1), 2);
                        } else {
                            mListView.onRefreshComplete();//刷新完成
                            toastMsg("已加载全部数据");
                        }
                    }
                }, 300);
            }
        });

        mListAdapter = new UC_MessageArrayListAdapter(getActivity());
        mListView.setAdapter(mListAdapter);

    }

    private void initHeadListView() {
        //listview头部布局
        mListHead = LayoutInflater.from(getContext()).inflate(R.layout.uc_head_message_main_list, null);

        topMainBanner = findViewById(mListHead, R.id.uc_banner_main_accordion, BGABanner.class);
        topMainBanner.setOnItemClickListener(this);
        root = (ViewGroup) topMainBanner.getRootView();           // 获取到包裹轮播图的ViewGroup

        mGridView = findViewById(mListHead, R.id.uc_message_gv_list, EtongLineNoScrollGridView.class);
        mGridView.setAdapter(new UC_MessageGridAdapter(getActivity(), UC_MessageMainFragment.this));

    }


    /**
     * @param
     * @return
     * @desc (初始化轮播图中的数据)
     * @user sunyao
     * @createtime 2016/9/13 - 10:55
     */
    private void initBannerViewData(String[] array) {
        bannerModel = new UC_MessageBannerModel();

        if (null == array)
            return;
        // 初始化其中的图片
        List<String> imageStr = new ArrayList<String>();
        for (String Str : array) {
            String url = UC_MessageUtils.getImageUrl(Str);
            if (!TextUtils.isEmpty(url))
                imageStr.add(url);
        }
        bannerModel.imgs = imageStr;

        //设置如果图片数量小于2就不轮播（手动、自动）
        if (array.length < 2) {
            topMainBanner.setAllowUserScrollable(false);
            topMainBanner.setAutoPlayAble(false);
        }
        topMainBanner.setAdapter(UC_MessageMainFragment.this);
        topMainBanner.setData(bannerModel.imgs, bannerModel.tips);

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            if (!isOnResume) {
                if (null != mListView) {
                    mListView.getRefreshableView().setSelection(0);//listView不上滑的方法(获取PullToRefreshListView的ListView视图)
                }
            }
            isOnResume = false;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        isOnResume = true;
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
                            Log.d("onLoadingStarted", arg0);
                        }

                        @Override
                        public void onLoadingFailed(String arg0, View arg1,
                                                    FailReason arg2) {

                            Log.d("onLoadingFailed", arg0);
                        }

                        @Override
                        public void onLoadingComplete(String arg0, View arg1,
                                                      Bitmap arg2) {
                            Log.d("onLoadingComplete", arg0);
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
        Log.d("点击了：", position + "---" + model.toString());
    }


    @Override
    public void answerItem(String titleName) {

        Intent intent = new Intent(getActivity(), UC_MessageListActivity.class);

        switch (titleName) {
            case RECOMMEDED_DAILY:
                intent.putExtra("type", 20000);//分类id
                intent.putExtra("title", RECOMMEDED_DAILY);
                startActivity(intent);
                break;
            case PREFERENTIAL_ACTIVITIES:
                intent.putExtra("type", 20001);//分类id
                intent.putExtra("title", PREFERENTIAL_ACTIVITIES);
                startActivity(intent);
                break;
            case AUCTION_NOTICE:
                intent.putExtra("type", 20002);//分类id
                intent.putExtra("title", AUCTION_NOTICE);
                startActivity(intent);
                break;
            case COMMUNITY_INFORMATION:
                intent.putExtra("type", 20003);//分类id
                intent.putExtra("title", COMMUNITY_INFORMATION);
                startActivity(intent);
                break;
            case AUTO_HOSPITAL_INFORMATION:
                intent.putExtra("type", 20004);//分类id
                intent.putExtra("title", AUTO_HOSPITAL_INFORMATION);
                startActivity(intent);
                break;
            case INANCIAL_INFORMATION:
                intent.putExtra("type", 20005);//分类id
                intent.putExtra("title", INANCIAL_INFORMATION);
                startActivity(intent);
                break;
            case TRANSFER_POLICY_GUILD_REGULATIONS:
                intent.putExtra("type", 20006);//分类id
                intent.putExtra("title", TRANSFER_POLICY_GUILD_REGULATIONS);
                startActivity(intent);
                break;
            case QUESTIONS_AND_ANSWERS:
                intent.putExtra("type", 20007);//分类id
                intent.putExtra("title", QUESTIONS_AND_ANSWERS);
                startActivity(intent);
                break;
            case OTHER_INFORMATION:
                intent.putExtra("type", 20008);//分类id
                intent.putExtra("title", OTHER_INFORMATION);
                startActivity(intent);
                break;
        }
    }
}
