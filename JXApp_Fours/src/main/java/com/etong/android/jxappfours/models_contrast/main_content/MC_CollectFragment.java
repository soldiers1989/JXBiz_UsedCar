package com.etong.android.jxappfours.models_contrast.main_content;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
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
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.collect_search.javabean.FC_NewCarCollectBean;
import com.etong.android.jxappfours.find_car.collect_search.main_content.Find_Car_VechileCollectPriceFragment;
import com.etong.android.jxappfours.find_car.collect_search.utils.Find_Car_SearchProvider;
import com.etong.android.jxappfours.find_car.collect_search.utils.Find_Car_VechileCollect_Method;
import com.etong.android.jxappfours.find_car.grand.adapter.FC_CarsetDetailAdapter;
import com.etong.android.jxappfours.models_contrast.adapter.MC_CollectAdapter;
import com.etong.android.jxappfours.models_contrast.adapter.Models_Contrast_SelectBrandAdapter;
import com.etong.android.jxappfours.models_contrast.common.Models_Contrast_AddVechileStyle_Method;
import com.etong.android.jxappfours.models_contrast.common.Models_Contrast_Provider;
import com.etong.android.jxappfours.models_contrast.impl.OnChooseCarTypeListener;
import com.etong.android.jxappfours.models_contrast.impl.OnCloseFragmentListener;
import com.etong.android.jxappfours.models_contrast.impl.OnCloseOnlyoneFragmentListener;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 车型对比  点击收藏的fragment
 * Created by Administrator on 2016/8/12.
 */
public class MC_CollectFragment extends BaseSubscriberFragment implements MC_CollectAdapter.ItemOnClickListener {

    private ListView brand_list_view;
    private Models_Contrast_Provider mModelsContrasProvider;
    private HttpPublisher mHttpPublisher;
    private Models_Contrast_SelectBrandAdapter adapter;

    private TextView txt_collect;
    public static DrawerLayout drawer_collect;
    private PullToRefreshListView collect_list;
    private ListAdapter<Models_Contrast_VechileType> mListAdapters;
    private LinearLayout data_null;

    private boolean refreshIsRequest;
    private int pageSize = 20;
    private int pageCurrent = 0;
    private int isPullDown = 1;
    public static final int isNewFlag = 1;//是否是新车 1--新车 0--二手车
    private boolean isFirst = true; //是否是第一次进来

    // 添加的对外接口
    private OnCloseOnlyoneFragmentListener mOnCloseOneFragment;
    private OnCloseFragmentListener mCloseAllFragment;
    private OnChooseCarTypeListener mOnChooseCarTypeListener;
    private Find_Car_SearchProvider mProvider;
    private LinearLayout mEmptyView;
    private TextView mEmptyTxt;
    private List<FC_NewCarCollectBean> mListData = new ArrayList<>();
    private MC_CollectAdapter mCollectAdapteradapter;
    private FC_NewCarCollectBean mCF_LoanListBean;

    // 设置关闭按钮
    public void setOnCloseOneFragmentListener(OnCloseOnlyoneFragmentListener listener) {
        this.mOnCloseOneFragment = listener;
    }
    // 设置点击关闭所有侧滑界面
    public void setOnCloseAllFragmentListener(OnCloseFragmentListener listener) {
        this.mCloseAllFragment = listener;
    }
    // 设置选择某一个车系的监听事件
    public void setOnChooseCarSeriesListener(OnChooseCarTypeListener listener) {
        this.mOnChooseCarTypeListener = listener;
    }


    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.models_contrast_collect_frg,
                container, false);

        initViews(view);
        initData();
        return view;
    }
    private void initData() {
        //初始化请求
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mModelsContrasProvider = Models_Contrast_Provider.getInstance();
        mModelsContrasProvider.initialize(mHttpPublisher);
        pageCurrent = 0;
        mModelsContrasProvider.getCollectList(pageSize, pageCurrent, isNewFlag, 0);
    }

    @Subscriber(tag = "go to collect fragment")
    protected void getDataCollect() {

    }

    private void initViews(View view) {
//        mHttpPublisher = HttpPublisher.getInstance();
//        mHttpPublisher.initialize(getActivity());
//        mModelsContrasProvider = Models_Contrast_Provider.getInstance();
//        mModelsContrasProvider.initialize(mHttpPublisher);
        final TitleBar mTitleBar = new TitleBar(view);
        mTitleBar.setTitle("选择车型");
        mTitleBar.showBackButton(true);
        mTitleBar.showNextButton(true);
        mTitleBar.setNextButton("关闭");
        mTitleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO  设置关闭所有的侧滑界面
                mCloseAllFragment.closeFragmentAllFragment();
            }
        });

        mTitleBar.setBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  TODO 设置TitleBar中的返回键，关闭当前一个按钮
                mOnCloseOneFragment.closeOnlyOneFragment();
            }
        });


        collect_list = (PullToRefreshListView) view.findViewById(R.id.models_contrast_lv_collect_list);
//        data_null = (LinearLayout) view.findViewById(R.id.models_contrast_ll_null);
        mEmptyView = findViewById(view, R.id.default_empty_content, LinearLayout.class);
        mEmptyTxt = findViewById(view, R.id.default_empty_lv_textview, TextView.class);
        mEmptyView.setClickable(false);
        mEmptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    pageCurrent = 0;
                    mModelsContrasProvider.getCollectList(pageSize, pageCurrent, isNewFlag,0);
            }
        });
        collect_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//下拉
        collect_list.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        collect_list.getLoadingLayoutProxy(false, true).setRefreshingLabel(
                "正在加载更多...");
        collect_list.getLoadingLayoutProxy(false, true).setReleaseLabel(
                "松开加载更多");

        collect_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                //延迟0.3s
                collect_list.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageCurrent = 0;
                        mModelsContrasProvider.getCollectList(pageSize, pageCurrent, isNewFlag, 0);
                    }
                }, 300);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                //延迟3s
                collect_list.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (refreshIsRequest) {//请求接口
                            mModelsContrasProvider.getCollectList(pageSize, ++pageCurrent, isNewFlag, 1);
                        } else {//不请求接口
                            collect_list.onRefreshComplete();//刷新完成
                            toastMsg("已加载全部数据");
                        }
                    }
                }, 300);
            }
        });

        mCollectAdapteradapter =new MC_CollectAdapter(getActivity(), MC_CollectFragment.this);
        collect_list.setAdapter(mCollectAdapteradapter);

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
                        ShowNullView("亲,暂时没有内容哦", false);
                    }
                }
            }
            //添加数据
            for (int i = 0; i < data.size(); i++) {
                mCF_LoanListBean = JSON.toJavaObject((JSON) data.get(i), FC_NewCarCollectBean.class);
                mListData.add(mCF_LoanListBean);
            }
            if (!mListData.isEmpty()) {
                collect_list.setMode(PullToRefreshBase.Mode.BOTH);
            }
            //更新下拉参数
            if (isPullDown == 1) {//上拉
                mCollectAdapteradapter.update(mListData);

            } else {
                refreshIsRequest = true;//允许请求数据
                mCollectAdapteradapter.update(mListData);
                setIsPullUpRefresh(mListData.size());
            }

        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {//网络
            ShowNullView("亲,网络不给力哦\n点击屏幕重试", true);
        } else if(flag.equals(HttpPublisher.DATA_ERROR)){
            ShowNullView("Sorry,您访问的页面找不到了......", false);
        } else {//服务
//            toastMsg(msg);
        }
        collect_list.onRefreshComplete();
    }


    /**
     * @desc (显示空视图)
     * @createtime 2016/12/7 - 9:16
     * @author xiaoxue
     */
    protected void ShowNullView(String text, boolean isClick) {
        mEmptyView.setVisibility(View.VISIBLE);
        collect_list.setVisibility(View.GONE);
//        mNullImg.setBackgroundResource(image);
        mEmptyTxt.setText(text);
        mEmptyView.setClickable(isClick);
    }


    /**
     * @desc (显示ListView视图)
     * @createtime 2016/12/7 - 9:15
     * @author xiaoxue
     */
    protected void ShowListView() {
        collect_list.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }

    /**
     * @desc (根据第一次请求的数据个数判断设置listview是否可上拉加载)
     * @createtime 2016/11/14 0014-16:06
     * @author wukefan
     */
    private void setIsPullUpRefresh(int size) {
        if (size >= pageSize) {
            collect_list.setMode(PullToRefreshBase.Mode.BOTH);
            return;
        }
        View listItem = mCollectAdapteradapter.getView(0, null, collect_list);
        listItem.measure(0, 0);
        int height = listItem.getMeasuredHeight();
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mHeight = (dm.heightPixels - ((int) ((42 + 42) * dm.density + 0.5f)));
        if (height * size < mHeight) {
            collect_list.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }
    }

    @Override
    public void itemOnClickListener(FC_NewCarCollectBean data) {
        if (MC_ChooseCarType.IsNeedChecked) {//车型对比界面和参数对比选车界面
            Map map = Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle();
            Models_Contrast_Add_VechileStyle info = null;
            if (map != null) {
                boolean isHave = false;
                Iterator it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    Object key = entry.getKey();
                    String id = String.valueOf(key);
                    if (String.valueOf(data.getF_vid()).equals(id)) {
//                                        Toast.makeText(getActivity(), "该车型已添加", Toast.LENGTH_SHORT).show();
                        toastMsg("该车型已添加");
                        isHave = true;
                    }
                }
                if (!isHave) {
                    Models_Contrast_AddVechileStyle_Method.cartAdd(changeBean(data));
                    // TODO 设置选中的车型到缓存，并关闭当前侧滑页面
                    mOnChooseCarTypeListener.onChooseCarType(changeBean(data));
                    mCloseAllFragment.closeFragmentAllFragment();
                }
            } else {
                Models_Contrast_AddVechileStyle_Method.cartAdd(changeBean(data));

                // TODO 设置选中的车型到缓存，并关闭当前侧滑页面
                mOnChooseCarTypeListener.onChooseCarType(changeBean(data));
                mCloseAllFragment.closeFragmentAllFragment();
            }
        } else {
            // TODO 设置选中的车型到缓存，并关闭当前侧滑页面
            mOnChooseCarTypeListener.onChooseCarType(changeBean(data));
            mCloseAllFragment.closeFragmentAllFragment();
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            pageCurrent = 0;
            mModelsContrasProvider.getCollectList(pageSize, pageCurrent, isNewFlag, 0);
        }
    }

    /**
     * @desc (将javabean转换成下一页面需要的javabean)
     * @createtime 2016/12/13 - 19:29
     * @author xiaoxue
     */
    public Models_Contrast_VechileType changeBean(FC_NewCarCollectBean mCF_LoanListBean){
        Models_Contrast_VechileType mModels_Contrast_VechileType=new Models_Contrast_VechileType();
        mModels_Contrast_VechileType.setFullName(mCF_LoanListBean.getF_fullname());
        mModels_Contrast_VechileType.setVid(mCF_LoanListBean.getF_vid());
        mModels_Contrast_VechileType.setTitle(mCF_LoanListBean.getF_title());
        mModels_Contrast_VechileType.setBrand(mCF_LoanListBean.getF_brand());
        mModels_Contrast_VechileType.setCarset(mCF_LoanListBean.getF_carset());
        mModels_Contrast_VechileType.setCarsetTitle(mCF_LoanListBean.getF_carsettitle());
        mModels_Contrast_VechileType.setImage(mCF_LoanListBean.getF_image());
        mModels_Contrast_VechileType.setImageNum(mCF_LoanListBean.getF_imagenum());
        mModels_Contrast_VechileType.setPrices(mCF_LoanListBean.getF_price());
        mModels_Contrast_VechileType.setManu(mCF_LoanListBean.getF_manu());
        mModels_Contrast_VechileType.setF_collectstatus(mCF_LoanListBean.getF_collectstatus());
        return mModels_Contrast_VechileType;
    }
}
