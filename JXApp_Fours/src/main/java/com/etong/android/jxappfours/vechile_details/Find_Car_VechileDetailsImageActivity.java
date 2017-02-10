package com.etong.android.jxappfours.vechile_details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshGridView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;
import com.etong.android.jxappfours.order.Fours_Order_Provider;
import com.etong.android.jxappfours.vechile_details.adapter.Find_Car_VechileDetailsCarListAdapter;
import com.etong.android.jxappfours.vechile_details.adapter.Find_Car_VechileDetailsColorListAdapter;
import com.etong.android.jxappfours.vechile_details.adapter.Find_Car_VechileDetailsDefaultListAdapter;
import com.etong.android.jxappfours.vechile_details.adapter.Find_Car_VechileDetailsImageListAdapter;
import com.etong.android.jxappfours.vechile_details.adapter.Find_Car_VechileDetailsImageSeeAllGridViewAdapter;
import com.etong.android.jxappfours.vechile_details.javabeam.Find_Car_VechileDetailsImageBean;
import com.etong.android.jxappfours.vechile_details.javabeam.Find_Car_VechileDetails_ColorBeam;
import com.etong.android.jxappfours.vechile_details.widget.HotFixRecyclerView;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车系或车型的图片详情页面
 * Created by Administrator on 2016/8/15 0015.
 */
public class Find_Car_VechileDetailsImageActivity extends BaseSubscriberActivity implements Find_Car_VechileDetailsDefaultListAdapter.ICallBack, Find_Car_VechileDetailsColorListAdapter.LCallBack, Find_Car_VechileDetailsCarListAdapter.ICarCallBack {

    private TitleBar mTitleBar;
    private HotFixRecyclerView recyclerView;// 解决RecyclerView.stopscroll为空
    private LinearLayoutManager linearLayoutManager;
    private MyRecyclerAdapter myAdapter;// recyclerView适配器
    private ListView mListView;//选择车型或颜色时显示的大的ListView
    private PullToRefreshGridView mPullToRefreshGridView;//选择类型时显示的GridView
    private DrawerLayout mDrawerLayout;//抽屉布局
    private TextView mDrawerTitle;//侧滑布局的Title

    //private ListView mDrawerListView;
    private ListView mVechileModelDrawerListView;//选择车型的ListView
    private ListView mColorDrawerListView;//选择颜色的ListView
    private ListView mTypeDrawerListView;//选择类型的ListView
    private Find_Car_VechileDetailsColorListAdapter mColorDrawerListAdapter;//选择车型的ListAdapter
    private Find_Car_VechileDetailsCarListAdapter mVechileModelDrawerListAdapter;//选择颜色的ListAdapter
    private Find_Car_VechileDetailsDefaultListAdapter mTypeDrawerListAdapter;//选择类型的ListAdapter

    private Find_Car_VechileDetailsImageListAdapter mImageListAdapter;//选择车型或颜色时显示的大的ListView的ListAdapter
    private Find_Car_VechileDetailsImageSeeAllGridViewAdapter imageGridListAdapter;//选择类型时显示的GridView的Adapter

    private List<Find_Car_VechileDetailsImageBean.PhotoListBean> mGridViewImageList = new ArrayList<Find_Car_VechileDetailsImageBean.PhotoListBean>();//选择类型的图片javabean的List
    private List<String> mTitleList;//RecyclerView的titles
    private List<Find_Car_VechileDetails_ColorBeam> itemColorList;//选择颜色的颜色list
    private List<Find_Car_VechileDetailsImageBean> itemImageList = new ArrayList<Find_Car_VechileDetailsImageBean>();//选择车型或颜色的图片javabean的List
    private List<Models_Contrast_VechileType> mVechileTypeList = new ArrayList<Models_Contrast_VechileType>();//选择车型的车型javabean的List
    private String title;//上一界面传过来的title
    private int pos = 0;//类型的位置

    private int flag; // 跳转过来的界面类型
    private List<String> mTypeDrawerData = new ArrayList<String>();//选择类型的List

//    private String[][] mTitleData = {{"全部车型", "全部颜色", "全部类型"}, {"全部颜色", "全部类型"}};
//    private String[][] mDrawerTitleData = {{"选择车型", "选择颜色", "选择类型"}, {"选择颜色", "选择类型"}};

    private String[] mTempTitleData = {"全部车型"};
    private String[] mTempDrawerTitleData = {"选择车型"};

    private Fours_Order_Provider mFours_Order_Provider;

    //上拉加载的GridView的分页处理参数
    private int pagerNum = 0;
    private int currentPager = 0;
    private int pagerSize = 30;//设置一页显示的图片个数
    private Map<Integer, List<Find_Car_VechileDetailsImageBean.PhotoListBean>> imageMap;//页数对应的图片List的Map
    private List<Find_Car_VechileDetailsImageBean.PhotoListBean> imageList = new ArrayList<Find_Car_VechileDetailsImageBean.PhotoListBean>();//每一页的图片List

    private int id;//车系id
    private int vid;//车型id
    private String brand;//车品牌
    private ViewGroup default_empty_content;
    private TextView default_empty_lv_textview;
    private SetEmptyViewUtil setEmptyViewUtil;
    private ImageView default_empty_img;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_car_activity_vechile_details_image);

        mFours_Order_Provider = Fours_Order_Provider.getInstance();
        mFours_Order_Provider.initialize(HttpPublisher.getInstance());

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        flag = intent.getIntExtra("flag", 0);
        id = intent.getIntExtra("carsetId", -1);
        brand = intent.getStringExtra("brand");
        vid = intent.getIntExtra("id", -1);
        if (flag == 1) {//车型页面进来
            title = "图片";
            mFours_Order_Provider.queryCarPhoto(vid);
            loadStart(null, 0);
        } else {//车系页面进来
            mFours_Order_Provider.queryCarsetPhoto(id);
            mFours_Order_Provider.getVechileType(id);
            loadStart(null, 0);
        }
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle(title);
        mTitleBar.showNextButton(false);

        initView();
    }

    private void initView() {

        mDrawerLayout = findViewById(R.id.find_car_btn_vechile_details_paramater_drawerlayout, DrawerLayout.class);

        recyclerView = findViewById(R.id.find_car_rv_vechile_details_select,
                HotFixRecyclerView.class);
        mListView = findViewById(R.id.find_car_lv_vechile_details_image, ListView.class);

        mPullToRefreshGridView = findViewById(R.id.find_car_gv_vechile_details_image_refreshable, PullToRefreshGridView.class);

        mDrawerTitle = findViewById(R.id.find_car_vd_title_name, TextView.class);
//        mDrawerListView = findViewById(R.id.find_car_lv_vd_parameter_select, ListView.class);

        mVechileModelDrawerListView = findViewById(R.id.find_car_lv_vd_parameter_model_select, ListView.class);
        mColorDrawerListView = findViewById(R.id.find_car_lv_vd_parameter_color_select, ListView.class);
        mTypeDrawerListView = findViewById(R.id.find_car_lv_vd_parameter_type_select, ListView.class);


        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);


//        mTitleList = Arrays.asList(mTitleData[flag]);
        if (flag == 1) {
            recyclerView.setVisibility(View.GONE);
        } else if (flag == 0) {
            mTitleList = Arrays.asList(mTempTitleData[flag]);
            myAdapter = new MyRecyclerAdapter(this, new ArrayList<String>(mTitleList));
            recyclerView.setAdapter(myAdapter);
        }

        //锁定右面的侧滑界面，不能通过手势关闭或者打开，只能通过代码打开，即调用openDrawer方法！
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
//        mDrawerLayout.setScrimColor(Color.TRANSPARENT);// 去除侧边栏蒙板效果

        addClickListener(R.id.find_car_vd_title_button);

//        // 设置数据为空的ListView显示
//        View emptyListView = LayoutInflater.from(Find_Car_VechileDetailsImageActivity.this).inflate(R.layout.default_empty_listview, null);
        default_empty_content = (ViewGroup) findViewById(R.id.default_empty_content);
        default_empty_lv_textview = (TextView) findViewById(R.id.default_empty_lv_textview);
        default_empty_img = (ImageView) findViewById(R.id.default_empty_img);
        setEmptyViewUtil = new SetEmptyViewUtil(default_empty_content, default_empty_img, default_empty_lv_textview);
        setEmptyViewUtil.showNetworkErrorView();
        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vid == -1) {
                    mFours_Order_Provider.queryCarsetPhoto(id);
                } else {
                    mFours_Order_Provider.queryCarPhoto(vid);
                }
                loadStart(null, 0);
            }
        });
        default_empty_content.setVisibility(View.GONE);
//        ((ViewGroup)mListView.getParent()).addView(emptyListView);
//        mListView.setEmptyView(emptyListView);


        initDrawerLayoutData();

        showVechileModelDrawer();
        // 设置数据为空的ListView显示
        View emptyVehicleModelListView = LayoutInflater.from(Find_Car_VechileDetailsImageActivity.this).inflate(R.layout.default_empty_listview, null);
        ViewGroup def_empty_content = (ViewGroup) emptyVehicleModelListView.findViewById(R.id.default_empty_content);
        TextView def_empty_lv_textview = (TextView) emptyVehicleModelListView.findViewById(R.id.default_empty_lv_textview);
        def_empty_lv_textview.setText("数据请求失败，请点击屏幕重试");
        def_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFours_Order_Provider.getVechileType(id);
            }
        });
        ((ViewGroup) mVechileModelDrawerListView.getParent()).addView(emptyVehicleModelListView);
        mVechileModelDrawerListView.setEmptyView(emptyVehicleModelListView);

        initVehicleModelDrawerListView();
//        initColorDrawerListView();
//        initTypeDrawerListView();
        initListView();
//        initGridView();

    }

    private void initListView() {
        mImageListAdapter = new Find_Car_VechileDetailsImageListAdapter(brand, this, itemImageList);
        mListView.setAdapter(mImageListAdapter);
        mImageListAdapter.notifyDataSetChanged();
    }

    private void initGridView() {
        imageGridListAdapter = new Find_Car_VechileDetailsImageSeeAllGridViewAdapter(brand, pos, this, imageList, itemImageList);
        mPullToRefreshGridView.setAdapter(imageGridListAdapter);
        imageGridListAdapter.notifyDataSetChanged();

        mPullToRefreshGridView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新");
        mPullToRefreshGridView.getLoadingLayoutProxy(true, false).setRefreshingLabel(
                "正在刷新...");
        mPullToRefreshGridView.getLoadingLayoutProxy(true, false).setReleaseLabel(
                "松开刷新");

        mPullToRefreshGridView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        mPullToRefreshGridView.getLoadingLayoutProxy(false, true).setRefreshingLabel(
                "正在加载更多...");
        mPullToRefreshGridView.getLoadingLayoutProxy(false, true).setReleaseLabel(
                "松开加载更多");

        mPullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                currentPager++;
                if (currentPager < pagerNum) {
                    imageList.addAll(imageMap.get(currentPager));
                    imageGridListAdapter.notifyDataSetChanged();
                    mPullToRefreshGridView.onRefreshComplete();
                } else {
                    toastMsg("已加载全部");
                    mPullToRefreshGridView.onRefreshComplete();
                    currentPager = pagerNum;
                }
            }
        });
    }

    private void initVehicleModelDrawerListView() {
//        String[] mDrawerData = {"全部车型", "2016款Limousine 40 TFSI 豪华型", "2016款Sportback 35 TFSI 进取型", "2016款Sportback 35 TFSI 领英型", "2016款Limousine 40 TFSI 风尚型", "2016款Sportback 35 TFSI 风尚型"};
//        mModelDrawerData = Arrays.asList(mDrawerData);
        mVechileModelDrawerListAdapter = new Find_Car_VechileDetailsCarListAdapter(0, mVechileModelDrawerListView, this, Find_Car_VechileDetailsImageActivity.this, mVechileTypeList);
        mVechileModelDrawerListView.setAdapter(mVechileModelDrawerListAdapter);
        mVechileModelDrawerListAdapter.notifyDataSetChanged();
    }

    private void initColorDrawerListView() {
        initColorData();
        mColorDrawerListAdapter = new Find_Car_VechileDetailsColorListAdapter(1, mColorDrawerListView, this, Find_Car_VechileDetailsImageActivity.this, itemColorList);
        mColorDrawerListView.setAdapter(mColorDrawerListAdapter);
        mColorDrawerListAdapter.notifyDataSetChanged();
    }

    private void initTypeDrawerListView() {
        mTypeDrawerListAdapter = new Find_Car_VechileDetailsDefaultListAdapter(2, mTypeDrawerListView, this, Find_Car_VechileDetailsImageActivity.this, mTypeDrawerData);
        mTypeDrawerListView.setAdapter(mTypeDrawerListAdapter);
        mTypeDrawerListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onClick(View v) {
        if (v.getId() == R.id.find_car_vd_title_button) {//侧滑 关闭
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVechileModelDrawerListAdapter.notifyDataSetChanged();
//        mTypeDrawerListAdapter.notifyDataSetChanged();
//        mColorDrawerListAdapter.notifyDataSetChanged();

    }

    @Subscriber(tag = FrameHttpTag.VECHILE_TYPE)
    protected void getVechileTypeData(HttpMethod method) {
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        if (flag.equals("0")) {
            Models_Contrast_VechileType head = new Models_Contrast_VechileType();
            head.setTitle("全部车型");
            mVechileTypeList.add(head);
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                Models_Contrast_VechileType mModels_Contrast_VechileType = JSON.toJavaObject(jsonArr.getJSONObject(i), Models_Contrast_VechileType.class);
                mVechileTypeList.add(mModels_Contrast_VechileType);
            }
            mVechileModelDrawerListAdapter.notifyDataSetChanged();
        } else {
            toastMsg(msg);
        }
    }

    @Subscriber(tag = FrameHttpTag.QUERY_CARSET_PHOTO)
    protected void getCarserPhoto(HttpMethod method) {
        String flag = method.data().getString("flag");
        String msg = method.data().getString("message");
        mImageListAdapter.notifyDataSetChanged();
        itemImageList.clear();
        mTypeDrawerData.clear();
        mTypeDrawerData.add("全部类型");
        showListView();


        if (flag.equals("0")) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                Find_Car_VechileDetailsImageBean mFind_Car_VechileDetailsImageBean = JSON.toJavaObject(jsonArr.getJSONObject(i), Find_Car_VechileDetailsImageBean.class);

                itemImageList.add(mFind_Car_VechileDetailsImageBean);
                mTypeDrawerData.add(mFind_Car_VechileDetailsImageBean.getType());
            }
            loadFinish();
            mImageListAdapter.notifyDataSetChanged();

            if (itemImageList.size() == 0) {
                setEmptyViewUtil.showNullView();
//                default_empty_lv_textview.setText("暂无图片");
//                default_empty_content.setEnabled(false);
                showEmptyView();

            }
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {
            loadFinish();
            setEmptyViewUtil.showNoServerView();
//            default_empty_lv_textview.setText("Sorry,您访问的页面找不到了......");
//            default_empty_content.setEnabled(false);
            showEmptyView();
        } else {
            loadFinish();
            setEmptyViewUtil.showNetworkErrorView();
//            default_empty_lv_textview.setText("数据请求失败，点击重试");
//            default_empty_content.setEnabled(true);
            showEmptyView();
        }
    }

    @Subscriber(tag = FrameHttpTag.QUERY_CAR_PHOTO)
    protected void getCarPhoto(HttpMethod method) {
        String flag = method.data().getString("flag");
        String msg = method.data().getString("message");
        mImageListAdapter.notifyDataSetChanged();
        itemImageList.clear();
        mTypeDrawerData.clear();
        mTypeDrawerData.add("全部类型");
        showListView();

        if (flag.equals("0")) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                Find_Car_VechileDetailsImageBean mFind_Car_VechileDetailsImageBean = JSON.toJavaObject(jsonArr.getJSONObject(i), Find_Car_VechileDetailsImageBean.class);
                itemImageList.add(mFind_Car_VechileDetailsImageBean);
                mTypeDrawerData.add(mFind_Car_VechileDetailsImageBean.getType());
            }
            loadFinish();
            mImageListAdapter.notifyDataSetChanged();
//            mTypeDrawerListAdapter.notifyDataSetChanged();
            if (itemImageList.size() == 0) {
                setEmptyViewUtil.showNullView();
//                default_empty_lv_textview.setText("暂无图片");
//                default_empty_content.setEnabled(false);
                showEmptyView();

            }
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {
            loadFinish();
            setEmptyViewUtil.showNoServerView();
//            default_empty_lv_textview.setText("Sorry,您访问的页面找不到了......");
//            default_empty_content.setEnabled(false);
            showEmptyView();
        } else {
            loadFinish();
            setEmptyViewUtil.showNetworkErrorView();
//            default_empty_lv_textview.setText("数据请求失败，点击重试");
//            default_empty_content.setEnabled(true);
            showEmptyView();
        }
    }


    //选择类型的Adapter的回调
    @Override
    public void answer(int position, String str, int anotherPos) {
        showListView();
        if (flag == 1) {
            mTitleList.set(position - 1, str);
        } else {
            mTitleList.set(position, str);
        }
        myAdapter.clear();
        myAdapter.addAll(mTitleList);
        myAdapter.notifyDataSetChanged();
        mTypeDrawerListAdapter.notifyDataSetChanged();
        if (str != "全部类型") {
            SetGridViewData(str);
//            initGridView();
            showGridView();
            pos = anotherPos - 1;
            imageGridListAdapter.setPos(pos);
            imageGridListAdapter.notifyDataSetChanged();
        } else {
            if (mTitleList.contains("全部车型")) {
                mFours_Order_Provider.queryCarsetPhoto(id);
            } else {
                mFours_Order_Provider.queryCarPhoto(vid);
            }
        }
        mDrawerLayout.closeDrawer(Gravity.RIGHT);
    }

    //选择颜色的Adapter的回调
    @Override
    public void answerColor(int position, String str) {
        showListView();
        if (flag == 1) {
            mTitleList.set(position - 1, str);
        } else {
            mTitleList.set(position, str);
        }
        myAdapter.clear();
        myAdapter.addAll(mTitleList);
        myAdapter.notifyDataSetChanged();
        mColorDrawerListAdapter.notifyDataSetChanged();
        mDrawerLayout.closeDrawer(Gravity.RIGHT);
    }

    //选择车型的Adapter的回调
    @Override
    public void answerCar(int position, String str, int vid) {
        this.vid = vid;
        showListView();
        mTitleList.set(position, str);
        myAdapter.clear();
        myAdapter.addAll(mTitleList);
        myAdapter.notifyDataSetChanged();
        mVechileModelDrawerListAdapter.notifyDataSetChanged();

        if (mTitleList.contains("全部车型")) {
            mFours_Order_Provider.queryCarsetPhoto(id);
        } else {
            mFours_Order_Provider.queryCarPhoto(vid);
        }

        mDrawerLayout.closeDrawer(Gravity.RIGHT);
    }


    private void showGridView() {
        mListView.setVisibility(View.GONE);
        default_empty_content.setVisibility(View.GONE);
        mPullToRefreshGridView.setVisibility(View.VISIBLE);
    }

    private void showListView() {
        default_empty_content.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
        mPullToRefreshGridView.setVisibility(View.GONE);
    }

    private void showEmptyView() {
        default_empty_content.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
        mPullToRefreshGridView.setVisibility(View.GONE);
    }

    private void SetGridViewData(String str) {
        mGridViewImageList.clear();
        for (int i = 0; i < itemImageList.size(); i++) {
            if (itemImageList.get(i).getType().equals(str)) {
                mGridViewImageList = itemImageList.get(i).getPhotoList();
                break;
            }
        }
        //上拉加载的GridView的分页处理
        imageList.clear();
        currentPager = 0;
        imageMap = new HashMap<Integer, List<Find_Car_VechileDetailsImageBean.PhotoListBean>>();
//        List<String> pagerImageList = new ArrayList<String>();
        pagerNum = mGridViewImageList.size() / pagerSize + 1;
        if (pagerNum > 1) {
            mPullToRefreshGridView.setMode(PullToRefreshBase.Mode.PULL_UP_TO_REFRESH);
        } else {
            mPullToRefreshGridView.setMode(PullToRefreshBase.Mode.DISABLED);
        }
        for (int i = 0; i < pagerNum; i++) {
            List newlist = mGridViewImageList.subList(i * pagerSize, (i == pagerNum - 1 ? ((i * pagerSize) + (mGridViewImageList.size() % pagerSize)) : ((i + 1) * pagerSize)));
            imageMap.put(i, newlist);
        }
        imageList.addAll(imageMap.get(currentPager));
    }


// ============================MyRecyclerAdapter=============================================================================

    public class MyRecyclerAdapter extends
            RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
        ArrayList<String> mData;

        public MyRecyclerAdapter(Context context, ArrayList<String> mData) {
            this.mData = mData;
        }

        public void add(String str) {
            mData.add(str);
            this.notifyDataSetChanged();
        }

        public void addAll(Collection<? extends String> data) {
            mData.addAll(data);
            this.notifyDataSetChanged();
        }

        public void clear() {
            mData.clear();
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {// 绑定数据
            holder.mBtn.setText(mData.get(position));

            holder.mBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    if (position == 0) {
//                        if (mData.size() < 3) {
//                            showColorDrawer();
//                        } else {
//                            showVechileModelDrawer();
//                        }
//                    } else if (position == 1) {
//                        if (mData.size() < 3) {
//                            showTypeDrawer();
//                        } else {
//                            showColorDrawer();
//                        }
//                    } else if (position == 2) {
//                        showTypeDrawer();
//                    }
//                    mDrawerTitle.setText(mDrawerTitleData[flag][position]);
                    mDrawerTitle.setText(mTempDrawerTitleData[0]);
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                    notifyDataSetChanged();
                }
            });
        }

        ViewHolder viewHolder;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
            View view = LayoutInflater.from(arg0.getContext()).inflate(
                    R.layout.find_car_button_rv_adapter, null);
            viewHolder = new ViewHolder(view);

            viewHolder.mBtn = (Button) view
                    .findViewById(R.id.find_car_btn_vechile_details_paramater);

            return viewHolder;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View arg0) {
                super(arg0);
            }

            Button mBtn;
        }

    }

    //显示选择类型的ListView抽屉布局
    private void showTypeDrawer() {
        mTypeDrawerListView.setVisibility(View.VISIBLE);
        mVechileModelDrawerListView.setVisibility(View.GONE);
        mColorDrawerListView.setVisibility(View.GONE);
    }

    //显示选择车型的ListView抽屉布局
    private void showVechileModelDrawer() {
        mVechileModelDrawerListView.setVisibility(View.VISIBLE);
        mColorDrawerListView.setVisibility(View.GONE);
        mTypeDrawerListView.setVisibility(View.GONE);
    }

    //显示选择颜色的ListView抽屉布局
    private void showColorDrawer() {
        mColorDrawerListView.setVisibility(View.VISIBLE);
        mVechileModelDrawerListView.setVisibility(View.GONE);
        mTypeDrawerListView.setVisibility(View.GONE);
    }

    //初始化DrawerLayout数据
    private void initDrawerLayoutData() {
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            /**
             * 当抽屉滑动状态改变的时候被调用
             * 状态值是STATE_IDLE（闲置--0）, STATE_DRAGGING（拖拽的--1）, STATE_SETTLING（固定--2）中之一。
             * 抽屉打开的时候，点击抽屉，drawer的状态就会变成STATE_DRAGGING，然后变成STATE_IDLE
             */
            @Override
            public void onDrawerStateChanged(int arg0) {
            }

            /**
             * 当抽屉被滑动的时候调用此方法
             * arg1 表示 滑动的幅度（0-1）
             */
            @Override
            public void onDrawerSlide(View arg0, float arg1) {
            }

            /**
             * 当一个抽屉被完全打开的时候被调用
             */
            @Override
            public void onDrawerOpened(View arg0) {
//                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED); // 打开手势滑动，关闭的时候可以用手势滑动关闭
            }

            /**
             * 当一个抽屉完全关闭的时候调用此方法
             */
            @Override
            public void onDrawerClosed(View arg0) {
//                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });

    }


    public void initColorData() {
        itemColorList = new ArrayList<Find_Car_VechileDetails_ColorBeam>();
        Find_Car_VechileDetails_ColorBeam beam1 = new Find_Car_VechileDetails_ColorBeam();
        beam1.setTitle("全部颜色");
        beam1.setImage("find_car_allcolor");
        itemColorList.add(beam1);
        Find_Car_VechileDetails_ColorBeam beam2 = new Find_Car_VechileDetails_ColorBeam();
        beam2.setTitle("外观颜色");
        beam2.setRoot(true);
        itemColorList.add(beam2);
        Find_Car_VechileDetails_ColorBeam beam3 = new Find_Car_VechileDetails_ColorBeam();
        beam3.setTitle("萨摩亚橙");
        beam3.setImage("find_car_samoa_orange");
        itemColorList.add(beam3);
        Find_Car_VechileDetails_ColorBeam beam4 = new Find_Car_VechileDetails_ColorBeam();
        beam4.setTitle("冰川白");
        beam4.setImage("find_car_glacier_white");
        itemColorList.add(beam4);
        Find_Car_VechileDetails_ColorBeam beam5 = new Find_Car_VechileDetails_ColorBeam();
        beam5.setTitle("水晶银");
        beam5.setImage("find_car_ice_silver");
        itemColorList.add(beam5);
        Find_Car_VechileDetails_ColorBeam beam6 = new Find_Car_VechileDetails_ColorBeam();
        beam6.setTitle("米萨诺红");
        beam6.setImage("find_car_misano_red");
        itemColorList.add(beam6);
        Find_Car_VechileDetails_ColorBeam beam7 = new Find_Car_VechileDetails_ColorBeam();
        beam7.setTitle("阿玛菲白");
        beam7.setImage("find_car_amafei_white");
        itemColorList.add(beam7);
        Find_Car_VechileDetails_ColorBeam beam8 = new Find_Car_VechileDetails_ColorBeam();
        beam8.setTitle("炫目红");
        beam8.setImage("find_car_glitz_red");
        itemColorList.add(beam8);
        Find_Car_VechileDetails_ColorBeam beam9 = new Find_Car_VechileDetails_ColorBeam();
        beam9.setTitle("幻影黑");
        beam9.setImage("find_car_phantom_black");
        itemColorList.add(beam9);
        Find_Car_VechileDetails_ColorBeam beam10 = new Find_Car_VechileDetails_ColorBeam();
        beam10.setTitle("希纳兹红");
        beam10.setImage("find_car_xinazi_red");
        itemColorList.add(beam10);
        Find_Car_VechileDetails_ColorBeam beam11 = new Find_Car_VechileDetails_ColorBeam();
        beam11.setTitle("内饰颜色");
        beam11.setRoot(true);
        itemColorList.add(beam11);
        Find_Car_VechileDetails_ColorBeam beam12 = new Find_Car_VechileDetails_ColorBeam();
        beam12.setTitle("黑色");
        beam12.setImage("find_car_black");
        itemColorList.add(beam12);
    }


}
