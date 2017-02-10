package com.etong.android.jxappusedcar.content;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.permissions.PermissionsManager;
import com.etong.android.frame.permissions.PermissionsResultAction;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.UC_CollectOrScannedBean;
import com.etong.android.frame.user.UC_FrameEtongApplication;
import com.etong.android.frame.user.UC_LoginActivity;
import com.etong.android.frame.user.UC_User;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.EtongToast;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.MD5Utils;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.dialog.UC_CancelConformDialog;
import com.etong.android.frame.widget.loopbanner.BGABanner;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_CarDetail_LvAdapter;
import com.etong.android.jxappusedcar.bean.UC_CarDetail_BannerBean;
import com.etong.android.jxappusedcar.bean.UC_CarDetail_CarConfig;
import com.etong.android.jxappusedcar.bean.UC_CarDetail_History;
import com.etong.android.jxappusedcar.bean.UC_CarDetail_InstallPlan;
import com.etong.android.jxappusedcar.bean.UC_CarDetail_LightConfig;
import com.etong.android.jxappusedcar.bean.UC_CarDetail_TitleBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆详情界面
 */
public class UC_CarDetail_Activity extends BaseSubscriberActivity implements
        BGABanner.Adapter,
        BGABanner.OnItemClickListener,
        ViewPager.OnPageChangeListener,
        AbsListView.OnScrollListener {
    /*
      ##################################################################################################
      ##                                        类中的变量                                            ##
      ##################################################################################################
    */
    public static final String POST_BUNDLE_INFO = "post bundle info";
    public static final String POST_CURRENT_PIC_NUM = "post current pic num";   // 将Banner图的页面传送
    public static final String POST_PIC_DETAIL = "post car pic detail";
    public static final String POST_TITLE_CAR_DETAIL = "post car detail";       // 预约看车按钮传送过去的Key值
    public static final String CAR_DETAIL_DVID = "f_dvid";      // 该标注字符串，其他页面到该页面需要传入该字符串的值
    private PinnedSectionListView carDetailLv;
    private TitleBar mTitleBar;
    private UC_CarDetail_LvAdapter carDetailAdapter;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    public static DisplayImageOptions options = new DisplayImageOptions.Builder()      // 展示图片的选项
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .showImageForEmptyUri(R.mipmap.uc_image_loading_two)           // 错误的状态都显示默认的图片
            .showImageOnFail(R.mipmap.uc_image_loading_two)
            .showImageOnLoading(R.mipmap.uc_image_loading_two)
            .build();
    private String telPhone = "96512";//拨打的电话号码
    private Integer mCollectId;

    private int mWinWidth;  // 最小的宽度
    private UC_CarDetail_BannerBean bannerModel;
    private BGABanner topBanner;
    private boolean isLoadMinwidth = false;     // 是否已经加载过最小的宽度
    private View appointBtn;
    private View consultBtn;
    private UC_CancelConformDialog mDialog;
    private UC_UserProvider mProvider;          // 数据请求类
    private ViewGroup carDetailVp;
    private TextView txtBrowse;             // 浏览量
    private TextView txtDvidId;             // 车源号
    private TextView bannerIndictor;        // 头部的轮播图切换指示器
    private TextView carName;               // 车辆的名字
    private TextView carRegistAdd;          // 车辆上牌公里的位置
    private TextView carPrice;              // 车辆的价格
    private CheckBox carCollect;            // 车辆收藏中的图片
    private UC_CarDetail_TitleBean uc_carDetail_titleBean;      // 需要发送的车辆头部信息
    private ImageView ivScollTop;
    private SparseArray recordSp = new SparseArray(0);
    private int mCurrentfirstVisibleItem = 0;
    private ImageView noNetWork;
    private String carDetailDvid;
    private boolean defaultCollect = false; //收藏原始状态
    private boolean isCollect = false;      //收藏改变的状态
    private boolean isLogin = true;        //是否的状态

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_content_car_detail_activity);
        initView();

        initHeaderAndFooter();
        // 在initData() 方法中再设置adapter
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != carCollect && !isLogin) {
            L.d("-------------->", "登录后请求数据刷新");
            initPostData();
        }
    }
/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/

    /**
     * @param
     * @return
     * @desc (初始化view组件的方法)
     * @user sunyao
     * @createtime 2016/10/20 - 15:51
     */
    private void initView() {


        UC_UserProvider.initalize(HttpPublisher.getInstance());
        mProvider = UC_UserProvider.getInstance();

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("车辆详情");
        mTitleBar.showNextButton(false);
        mTitleBar.setBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCollect();
                UC_CarDetail_Activity.this.finish();
            }
        });

        // 获取到屏幕的宽度，动态的设置Banner图的宽高
        WindowManager mWm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mWinWidth = mWm.getDefaultDisplay().getWidth();
        // 获取到包含整个ListView的Content，只有当获取到数据时才显示整个页面
        carDetailVp = findViewById(R.id.uc_content_car_detail_vp, ViewGroup.class);
        carDetailLv = findViewById(R.id.uc_lv_car_detail_content, PinnedSectionListView.class);     // PinnedListView
        carDetailLv.setOnScrollListener(this);                                                      // 设置ListView的滑动监听事件
        ivScollTop = findViewById(R.id.uc_iv_car_detail_scroll_to_top, ImageView.class);            // 滑动到顶部的ImageView
        appointBtn = findViewById(R.id.uc_appoint_btn_car_detail_bottom, View.class);               // 预约看车按钮
        consultBtn = findViewById(R.id.uc_consult_btn_car_detail_bottom, View.class);               // 免费咨询按钮

        // 无网络情况下的显示
        noNetWork = findViewById(R.id.uc_iv_car_detail_no_network, ImageView.class);

        addClickListener(ivScollTop);
        addClickListener(appointBtn);
        addClickListener(consultBtn);

        initDialog();
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
        // 首先设置整个页面不显示
        carDetailVp.setVisibility(View.GONE);
        loadStart();

        Intent intent = getIntent();
        carDetailDvid = intent.getStringExtra(CAR_DETAIL_DVID);
        // 将获取到接口参数的数据请求部分抽取
        initPostData();
        // 给ListView设置适配器
        carDetailAdapter = new UC_CarDetail_LvAdapter(this);
        carDetailLv.setAdapter(carDetailAdapter);

    }

/*
  ##################################################################################################
  ##                                     点击事件的处理                                           ##
  ##################################################################################################
*/

    /**
     * 控件的点击事件
     * http://192.168.10.167:8090/etong2sc-app-consumer/getCarDetailData?f_machinecode=00000000-6297-6ca3-ffff-ffff99d603a9&f_org_id=001&f_dvid=00000224&f_userid=482706626789244928
     * http://192.168.10.167:8090/etong2sc-app-consumer/getCarDetailData?f_machinecode=00000000-1da9-ea9e-ffff-ffff99d603a9&f_userid=487124366325186560&f_org_id=001&f_dvid=00000173
     */
    @Override
    protected void onClick(View view) {
        if (view.getId() == R.id.uc_appoint_btn_car_detail_bottom) {
            if (uc_carDetail_titleBean != null) {
                // 只有当车辆信息不为空时才跳
                Bundle bundle = new Bundle();
                bundle.putSerializable(POST_TITLE_CAR_DETAIL, uc_carDetail_titleBean);
                // 预约看车
                ActivitySkipUtil.skipActivity(this, UC_AppointTakeCar_Activity.class, bundle);
            } else {
                EtongToast.makeText(UC_CarDetail_Activity.this, "车辆信息获取失败!", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.uc_consult_btn_car_detail_bottom) {
            // 免费咨询 -- 弹出框
            mDialog.show();
        } else if (view.getId() == R.id.uc_iv_car_detail_scroll_to_top) {
            // 点击滑动到顶部的图片按钮
            carDetailLv.smoothScrollBy(-getListViewVerticalScroll(), 1000);
        } else if (view.getId() == R.id.uc_txt_car_detail_car_collect) {
            // 收藏
            if (UC_FrameEtongApplication.getApplication().isLogin()) {
                if (carCollect.isChecked()) {
                    //加入收藏
                    mProvider.insertColletData(uc_carDetail_titleBean.getF_dvid());
//                carCollect.setChecked(true);
                } else {
                    if (null != mCollectId) {
                        //取消收藏
                        mProvider.deleteOneColletData(mCollectId + "", "-1");
                    }
//                carCollect.setChecked(false);
                }
            } else {
                carCollect.setChecked(false);
                Intent i = new Intent(UC_CarDetail_Activity.this, UC_LoginActivity.class);
                i.putExtra("isShowToast", true);
                startActivity(i);
            }
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

        if (uc_carDetail_titleBean != null
                && uc_carDetail_titleBean.getImgUrls() != null
                && uc_carDetail_titleBean.getImgUrlsCount() > 0) {
            Intent intent = new Intent(this, UC_SelectImageActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable(this.POST_PIC_DETAIL, uc_carDetail_titleBean);
            mBundle.putInt(this.POST_CURRENT_PIC_NUM, position);
            intent.putExtra(this.POST_BUNDLE_INFO, mBundle);
            startActivity(intent);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    // 当页面选择时
    @Override
    public void onPageSelected(int position) {

        String indTxt = bannerIndictor.getText().toString();
        String[] split = indTxt.split("/");

        if (split.length == 2) {
            if (!split[0].equals("0")) {
                split[0] = String.valueOf(position + 1);
                bannerIndictor.setText(split[0] + "/" + split[1]);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    // PinnedListView滑动事件
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mCurrentfirstVisibleItem = firstVisibleItem;
        View firstView = view.getChildAt(0);
        if (null != firstView) {
            ItemRecod itemRecord = (ItemRecod) recordSp.get(firstVisibleItem);
            if (null == itemRecord) {
                itemRecord = new ItemRecod();
            }
            itemRecord.height = firstView.getHeight();
            itemRecord.top = firstView.getTop();
            recordSp.append(firstVisibleItem, itemRecord);
        }
        // 滑动的总的高度
        if (getListViewVerticalScroll() > 500) {
            ivScollTop.setVisibility(View.VISIBLE);
        } else {
            ivScollTop.setVisibility(View.GONE);
        }
    }

/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    public void initPostData() {
        /*$$$$$$$$$$$$$$$$$$$$$$$$$以下是获取车辆详情接口字段设置$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
        Map<String, String> carDetailMap = new HashMap<String, String>();
        // 获取到车辆的编号
        if (!TextUtils.isEmpty(carDetailDvid)) {
            carDetailMap.put("f_dvid", carDetailDvid);
        }
        // 获取到园区编码
        carDetailMap.put("f_org_id", "001");
        // 获取到用户id   如果用户登录了之后就必须传标准的机器码
        UC_User userInfo = UC_FrameEtongApplication.getApplication().getUserInfo();
        if (userInfo != null) {
            String userId = userInfo.getF_userid();
            if (!TextUtils.isEmpty(userId)) {
                carDetailMap.put("f_userid", userId);
            }
            // 获取到唯一机器码
            String uniqueId = UC_FrameEtongApplication.getApplication().getUniqueId();
            if (!TextUtils.isEmpty(uniqueId)) {
                carDetailMap.put("f_machinecode", uniqueId);
            }
        }

        // 获取到唯一机器码
        String uniqueId = UC_FrameEtongApplication.getApplication().getUniqueId();
        if (!TextUtils.isEmpty(uniqueId)) {
            String md5Key = MD5Utils.MD5(uniqueId);
            carDetailMap.put("f_key", md5Key);
        }

        mProvider.getCarDetail(carDetailMap);
/*$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ */
    }

    /**
     * @param
     * @return
     * @desc (从接口中获取到车辆详情的数据)
     * @user sunyao
     * @createtime 2016/10/28 - 11:05
     */
    @Subscriber(tag = UC_HttpTag.CAR_DETAIL_DATA_TAG)
    public void getCarDetailFromNet(HttpMethod mHttpMethod) {
        loadFinish();
        isLogin = UC_FrameEtongApplication.getApplication().isLogin();
        if (mHttpMethod == null) {
            return;
        }
        String status = "", msg = "";
        try {
            status = mHttpMethod.data().getString("status");
            msg = mHttpMethod.data().getString("msg");
        } catch (Exception e) {

        }
        // 成功获取到数据
        if (!TextUtils.isEmpty(status) && "true".equals(status)) {
            noNetWork.setVisibility(View.GONE);
            // 设置整个页面显示
            carDetailVp.setVisibility(View.VISIBLE);
            JSONObject jsonObject = mHttpMethod.data().getJSONObject("data");

            JSONObject titleConfig = jsonObject.getJSONObject("titleConfig");
            uc_carDetail_titleBean = JSON.toJavaObject(titleConfig, UC_CarDetail_TitleBean.class);  // 需要发送的titleBean;

            if (null != uc_carDetail_titleBean.getF_collectid()) {
                mCollectId = Integer.valueOf(uc_carDetail_titleBean.getF_collectid());
                defaultCollect = true;
                isCollect = defaultCollect;
            }
            //  用来加缓存
            //设置浏览记录缓存
            if (null != uc_carDetail_titleBean.getHistoryList()) {
                UC_CollectOrScannedBean history = UC_FrameEtongApplication.getApplication().getHistoryCache();
                if (null != history.getCarList()) {//浏览记录缓存有数据
                    if (!history.getCarList().contains(uc_carDetail_titleBean.getF_dvid())) {
                        List<String> historyList = history.getCarList();
                        historyList.add(uc_carDetail_titleBean.getF_dvid());
                        history.setChanged(true);
                        history.setCarList(historyList);
                        UC_FrameEtongApplication.getApplication().setHistoryCache(history);
                    }
                } else {//浏览记录缓存没数据
                    List<String> historyList = new ArrayList<>();
                    historyList.add(uc_carDetail_titleBean.getF_dvid());
                    history.setChanged(true);
                    history.setCarList(historyList);
                    UC_FrameEtongApplication.getApplication().setHistoryCache(history);
                }
            }

            List<Object> beanList = new ArrayList<Object>();            // 用来存放这几组数据

            JSONObject fqConfig = jsonObject.getJSONObject("fqConfig");
            UC_CarDetail_InstallPlan uc_carDetail_installPlan = JSON.toJavaObject(fqConfig, UC_CarDetail_InstallPlan.class);
            beanList.add(uc_carDetail_installPlan);     /** 添加分期方案*/
            JSONObject ldConfig = jsonObject.getJSONObject("ldConfig");
            UC_CarDetail_LightConfig uc_carDetail_lightConfig = JSON.toJavaObject(ldConfig, UC_CarDetail_LightConfig.class);
            beanList.add(uc_carDetail_lightConfig);     /** 亮点配置*/
            JSONObject carHistory = jsonObject.getJSONObject("carHistory");
            UC_CarDetail_History uc_carDetail_history = JSON.toJavaObject(carHistory, UC_CarDetail_History.class);
            beanList.add(uc_carDetail_history);         /** 车辆历史*/
            JSONObject carConfig = jsonObject.getJSONObject("carConfig");
            UC_CarDetail_CarConfig uc_carDetail_carConfig = JSON.toJavaObject(carConfig, UC_CarDetail_CarConfig.class);
            beanList.add(uc_carDetail_carConfig);       /** 车辆配置*/
            List<UC_CarDetail_TitleBean.ImgUrlsBean> imgUrls = uc_carDetail_titleBean.getImgUrls();
            List<String> lImgs = new ArrayList<String>();
            if (imgUrls != null) {
                for (UC_CarDetail_TitleBean.ImgUrlsBean imBean : imgUrls) {
                    lImgs.add(imBean.getImgUrl());
                }
            }
            beanList.add(lImgs);                        /** 车辆下方的图片*/
            carDetailAdapter.updateListDatas(beanList);     // 将数据添加到适配器中
            initTitleBannerViewData(uc_carDetail_titleBean);      // 将头部的信息传递给下面初始化操作
        } else if (status.equals(HttpPublisher.NETWORK_ERROR)) {
            carDetailVp.setVisibility(View.VISIBLE);
            noNetWork.setVisibility(View.VISIBLE);
            ImageProvider.getInstance().loadImage(noNetWork, "noNetWork", R.mipmap.uc_no_network);

            noNetWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    carDetailVp.setVisibility(View.GONE);
                    noNetWork.setVisibility(View.GONE);
                    // 请求数据
                    initPostData();
                }
            });
        } else if (status.equals(HttpPublisher.DATA_ERROR)) {
            carDetailVp.setVisibility(View.VISIBLE);
            noNetWork.setVisibility(View.VISIBLE);
            ImageProvider.getInstance().loadImage(noNetWork, "noNetWork", R.mipmap.uc_no_service);

            noNetWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    carDetailVp.setVisibility(View.GONE);
                    noNetWork.setVisibility(View.GONE);
                    // 请求数据
                    initPostData();
                }
            });
        }
    }

    /**
     * @param
     * @return
     * @desc (头部中的内容)
     * @user sunyao
     * @createtime 2016/10/28 - 19:19
     */
    public void initHeaderAndFooter() {
        View header = View.inflate(this, R.layout.uc_content_car_detail_header, null);

        // banner图中的内容
        txtBrowse = (TextView) header.findViewById(R.id.uc_txt_car_detail_header_browse);       // banner图中的浏览数量
        txtDvidId = (TextView) header.findViewById(R.id.uc_txt_car_detail_header_car_divid);    // banner图中的车源号
        bannerIndictor = (TextView) header.findViewById(R.id.uc_txt_car_detail_header_siwtch_item);// banner图中的数字切换

        // 数字指示器
        topBanner = (BGABanner) header.findViewById(R.id.uc_content_car_detail_header_banner);
        topBanner.setOnItemClickListener(this);
        topBanner.setOnPageChangeListener(this);

        // 轮播图下面的车辆信息
        carName = (TextView) header.findViewById(R.id.uc_txt_car_detail_car_name);                  // 车辆的名字
        carRegistAdd = (TextView) header.findViewById(R.id.uc_txt_car_detail_car_regist_address);   // 车辆上牌公里的位置
        carPrice = (TextView) header.findViewById(R.id.uc_txt_car_detail_car_price);                // 车辆的价格
        carCollect = (CheckBox) header.findViewById(R.id.uc_txt_car_detail_car_collect);           // 车辆收藏中的图片


        addClickListener(carCollect);
        carDetailLv.addHeaderView(header);
    }

    /**
     * @param
     * @return
     * @desc (初始化轮播图中的数据)
     * @user sunyao
     * @createtime 2016/9/13 - 10:55
     */
    private void initTitleBannerViewData(UC_CarDetail_TitleBean titleConfig) {
        bannerModel = new UC_CarDetail_BannerBean();
        if (titleConfig == null) {
            return;
        }

        // 初始化其中的图片
        List<String> imgLists = new ArrayList<String>();
        List<UC_CarDetail_TitleBean.ImgUrlsBean> imgUrls = titleConfig.getImgUrls();
        if (imgUrls != null && titleConfig.getImgUrlsCount() > 0) {
            for (UC_CarDetail_TitleBean.ImgUrlsBean i : imgUrls) {
                imgLists.add(i.getImgUrl());        // 将图片添加到
            }
            bannerIndictor.setText("1/" + titleConfig.getImgUrlsCount());
            // 如果获取到的图片不为空时，可以左右滑动
            topBanner.setAllowUserScrollable(true);
        } else {
            // 没有获取到图片的情况
            int value = 0;
            Class<R.mipmap> cls = R.mipmap.class;
            try {
                value = cls.getDeclaredField("uc_image_loading_two").getInt(null);
            } catch (Exception e) {
            }
            bannerIndictor.setText("0/" + titleConfig.getImgUrlsCount());
            // 如果获取到的图片为空时，轮播图设置为不可滑动
            topBanner.setAllowUserScrollable(false);
            imgLists.add("drawable://" + value + "");
        }
        bannerModel.imgs = imgLists;
//        bannerIndictor.setText("1/" + titleConfig.getImgUrlsCount());
        topBanner.setAdapter(this);
        topBanner.setData(bannerModel.imgs, bannerModel.tips);

        txtBrowse.setText(titleConfig.getF_clickcount());           // 浏览数量
        txtDvidId.setText(titleConfig.getF_dvid());                 // 车源号
        carName.setText(titleConfig.getF_cartitle());               // 车辆名称
        carRegistAdd.setText(titleConfig.getF_registerdate() + "上牌"
                + titleConfig.getF_mileage() + " 万公里所属位置 ："
                + titleConfig.getF_carposition());                  // 上牌注册时间
        carPrice.setText("¥ " + titleConfig.getF_price() + " 万");      // 车辆价格
        if (titleConfig.getF_collectstatus() == 1) {
            carCollect.setChecked(true);                                       // 这里需要加上图片是否选中的状态
        } else {
            carCollect.setChecked(false);
        }
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
                        if (arg2 == null || isLoadMinwidth) {
                            return;
                        }
                        int with = arg2.getWidth() - 2;
                        int height = arg2.getHeight();
                        topBanner.getLayoutParams().width = mWinWidth;
//                        topBanner.getLayoutParams().height = mWinWidth * height / with;
                        topBanner.getLayoutParams().height = mWinWidth * 300 / 750;
                        isLoadMinwidth = true;     // 表示已经加载过最小的宽度
                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                    }
                });
    }

    /**
     * @desc (初始化打电话的弹出框)
     * @createtime 2016/10/25 0025-14:54
     * @author wukefan
     */
    private void initDialog() {
        mDialog = new UC_CancelConformDialog(UC_CarDetail_Activity.this, false);
        mDialog.setTitle("提示");
        mDialog.setContent("确定拨打电话：" + telPhone + "吗？");
        mDialog.setContentSize(15);
        mDialog.setConfirmButtonClickListener("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //确定操作
                PermissionsManager.getInstance()
                        .requestPermissionsIfNecessaryForResult(
                                UC_CarDetail_Activity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                new PermissionsResultAction() {

                                    @Override
                                    public void onGranted() {
                                        callPhoneDialog();
                                    }

                                    @Override
                                    public void onDenied(String permission) {
                                        toastMsg("授权失败，无法完成操作！");
                                        mDialog.dismiss();
                                        return;
                                    }
                                });
            }
        });
    }

    /**
     * @desc 打电话
     * @createtime 2016/10/24 - 11:23
     * @author xiaoxue
     */
    private void callPhoneDialog() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri
                    .parse("tel:"
                            + telPhone));
            startActivity(intent);
        } catch (SecurityException e) {
        }
        mDialog.dismiss();
    }


    @Subscriber(tag = UC_HttpTag.INSERT_COLLECT_DATA)
    private void insertCollectDataResult(HttpMethod method) {

        String msg = method.data().getString("msg");
        String errCode = method.data().getString("errCode");

        if (!TextUtils.isEmpty(errCode) && errCode.equals("4353")) {
            toastMsg("加入收藏失败,请检查网络~");
            carCollect.setChecked(false);
            return;
        }

        boolean status = method.data().getBoolean("status");
        if (status) {
            JSONObject object = method.data().getJSONObject("data");

            mCollectId = object.getInteger("f_collectid");
            String vehicleid = object.getString("f_vehicleid");

            //设置收藏缓存
            UC_CollectOrScannedBean collect = UC_FrameEtongApplication.getApplication().getCollectCache();
            if (null != collect.getCarList()) {//收藏缓存有数据
                if (!collect.getCarList().contains(mCollectId + "")) {
                    List<String> collectList = collect.getCarList();
                    collectList.add(mCollectId + "");
                    collect.setCarList(collectList);
                    UC_FrameEtongApplication.getApplication().setCollectCache(collect);
                }
            } else {//收藏缓存没数据
                List<String> collectList = new ArrayList<>();
                collectList.add(mCollectId + "");
                collect.setCarList(collectList);
                UC_FrameEtongApplication.getApplication().setCollectCache(collect);
            }

            carCollect.setChecked(true);
            isCollect = true;
            toastMsg("收藏成功~");
        } else {
            toastMsg(msg);
            carCollect.setChecked(false);
        }
    }


    //删除单条收藏接口回调
    @Subscriber(tag = UC_HttpTag.DELETE_ONE_COLLECT_DATA)
    public void deleteOneCollectResult(HttpMethod method) {
        String errCode = method.data().getString("errCode");
        String status = method.data().getString("status");
        String msg = method.data().getString("msg");
        int f_collectid = Integer.valueOf((String) method.getParam().get("f_collectid"));

        if (!TextUtils.isEmpty(errCode) && errCode.equals("4353")) {
            toastMsg("取消收藏失败，请检查网络~");
            carCollect.setChecked(true);
            return;
        }

        if (status.equals("true")) {
            carCollect.setChecked(false);
            isCollect = false;

            //删除这条收藏的缓存
            UC_CollectOrScannedBean collect = UC_FrameEtongApplication.getApplication().getCollectCache();
            if (null != collect.getCarList()) {
                if (collect.getCarList().contains(f_collectid + "")) {
                    List<String> collectList = collect.getCarList();
                    collectList.remove(f_collectid + "");
                    collect.setCarList(collectList);
                    UC_FrameEtongApplication.getApplication().setCollectCache(collect);
                }
            }

            toastMsg("取消收藏成功!");
        } else {
            toastMsg(msg);
            carCollect.setChecked(true);
        }
    }

    /**
     * @desc (返回键监听)
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            setCollect();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @desc (判断收藏状态是否改变)
     * @createtime 2016/11/8 0008-10:34
     * @author wukefan
     */
    public void setCollect() {
        if (defaultCollect != isCollect) {
            UC_CollectOrScannedBean temp = UC_FrameEtongApplication.getApplication().getCollectCache();
            temp.setChanged(true);
            UC_FrameEtongApplication.getApplication().setCollectCache(temp);
        }
    }

  /*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

    /**
     * @param
     * @return
     * @desc (获取到ListView的纵向滑动的距离)
     * @user sunyao
     * @createtime 2016/10/31 - 10:41
     */
    public int getListViewVerticalScroll() {
//        View c = carDetailLv.getChildAt(0);
//        L.d("获取到的第一个子元素为：", c.toString());
//        if (c == null) {
//            return 0;
//        }
//        int firstVisiblePosition = carDetailLv.getFirstVisiblePosition();
//        int top = c.getTop();
//        return -top + firstVisiblePosition * c.getHeight() ;

        int height = 0;
        for (int i = 0; i < mCurrentfirstVisibleItem; i++) {
            ItemRecod itemRecod = (ItemRecod) recordSp.get(i);
            if (itemRecod != null) {
                height += itemRecod.height;
            }
        }
        ItemRecod itemRecod = (ItemRecod) recordSp.get(mCurrentfirstVisibleItem);
        if (null == itemRecod) {
            itemRecod = new ItemRecod();
        }
        return height - itemRecod.top;
    }

    class ItemRecod {
        int height = 0;
        int top = 0;
    }

}
