package com.etong.android.jxappusedcar.content;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.user.UsedAndNewCollectCar;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_CarDetailAdapter;
import com.etong.android.jxappusedcar.javabean.UC_CarDetailJavabean;
import com.etong.android.jxappusedcar.javabean.UC_CollectBean;
import com.etong.android.jxappusedcar.provider.UC_WorldProvider;
import com.etong.android.jxappusedcar.utils.UC_ConfigItemUtils;

import org.simple.eventbus.Subscriber;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UC_CarDetailActivity extends BaseSubscriberActivity {
    /*
      ##################################################################################################
      ##                                        类中的变量                                            ##
      ##################################################################################################
    */
    public static final String TAG = "UC_CarDetailActivity";
    public static String UC_CARDETAIL_ID = "f_dvid";
    private int pWidht;                                         // 获取到屏幕的宽度
    private int pHeight;                                        // 获取到屏幕的高度

    private HttpPublisher mHttpPublisher;                       // 数据请求类
    private UC_WorldProvider mUC_WorldProvider;                 // 二手车模块请求
    private ImageProvider mImageProvider;                       // 图片请求
    private TitleBar mTitleBar;
    private ListView carDetailLv;
    private TextView emptyViewTv;
    private ViewGroup emptyViewVp;
    private Button collect;
    private Button order_car;

    private TextView carDetailTxt;                              // ListView头部图集
    private ImageView carDetailIv;                              // ListView头部图片
    private TextView carDetailTitlte;                           // ListView头部title
    private TextView carDetailP;                                // ListView 售价
    private TextView carDetailNP;                               // ListView 新车价
    private String carDvidId;
    private boolean defaultCollect = false;                      //收藏原始状态
    public static boolean isCollect = false;                   // 是否收藏字段(收藏改变状态)
    private UC_CarDetailJavabean detailBean;
    private UC_CarDetailAdapter carDetailAdapter;
    private UC_CarDetailJavabean.PictureConfigBean pictureConfig;
    private UC_CarDetailJavabean.TitleConfigBean titleConfig;
    private ImageView emptyViewImg;
    private SetEmptyViewUtil setEmptyViewUtil;

    public static boolean isTestDebug=false;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.used_car_cardetail_content);
        initView();
        initData();
    }


/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/

    /**
     * @desc 初始化控件
     * @createtime 2016/10/24 - 9:19
     * @author xiaoxue
     */
    private void initView() {
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mUC_WorldProvider = UC_WorldProvider.getInstance();
        mUC_WorldProvider.initialize(mHttpPublisher);
        mImageProvider = ImageProvider.getInstance();
        mImageProvider.initialize(this);

        // 获取屏幕宽高
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        pWidht = display.getWidth();
        pHeight = display.getHeight();

        // 初始化标题栏
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("");
        mTitleBar.showNextButton(false);
        mTitleBar.showBackButton(true);
        mTitleBar.setBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCollect();
                UC_CarDetailActivity.this.finish();
            }
        });

        carDetailLv = findViewById(R.id.uc_car_detail_lv, ListView.class);                      // ListView
        emptyViewTv = findViewById(R.id.default_empty_lv_textview, TextView.class);             // 显示空视图的TextView
        emptyViewImg = findViewById(R.id.default_empty_img, ImageView.class);                   // 显示空视图的ImageView
        emptyViewVp = findViewById(R.id.default_empty_content, ViewGroup.class);                // 显示空视图的Content

        setEmptyViewUtil = new SetEmptyViewUtil(emptyViewVp, emptyViewImg, emptyViewTv);
        initHeadAndFooter();
        carDetailAdapter = new UC_CarDetailAdapter(this);
        carDetailLv.setAdapter(carDetailAdapter);

        collect = findViewById(R.id.default_appoint_drive, Button.class);                                        //收藏
        order_car = findViewById(R.id.default_appoint_order, Button.class);                                      //预约看车
        addClickListener(R.id.default_appoint_drive);
        addClickListener(R.id.default_appoint_order);
    }

    /**
     * @param
     * @return
     * @desc (初始化ListView中的头部和脚部)
     * @user sunyao
     * @createtime 2016/12/13 - 16:03
     */
    private void initHeadAndFooter() {
        ViewGroup.LayoutParams para;
        View headerView = LayoutInflater.from(this).inflate(R.layout.used_car_details_top, null);
        carDetailIv = (ImageView) headerView.findViewById(R.id.used_car_iv_photo);
        carDetailTxt = (TextView) headerView.findViewById(R.id.used_car_txt_photo);
        para = carDetailIv.getLayoutParams();
        // 设置图片的宽高比
        para.width = pWidht;
        para.height = pWidht * 400 / 750;
        carDetailIv.setLayoutParams(para);
        addClickListener(carDetailIv);          // 给头部图片添加点击事件
        carDetailTitlte = (TextView) headerView.findViewById(R.id.used_car_tv_title);
        carDetailP = (TextView) headerView.findViewById(R.id.used_car_price);
        carDetailNP = (TextView) headerView.findViewById(R.id.used_car_tv_new_car_price);
        // 为ListView添加头部
        carDetailLv.addHeaderView(headerView);

        View footerView = LayoutInflater.from(this).inflate(R.layout.used_car_details_list_item_footer, null);
        View goCarConfig = footerView.findViewById(R.id.used_car_details_parameter);
        addClickListener(goCarConfig);
        // 为ListView添加脚部
        carDetailLv.addFooterView(footerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断缓存是否收藏
        if (null != collect) {
//            setCollectState(isCollect);
            UsedAndNewCollectCar collectUsedCar = FrameEtongApplication.getApplication().getUsedCarCollectCache();
            if (null != collectUsedCar.getCarList() && collectUsedCar.getCarList().size() != 0) {
                if (collectUsedCar.getCarList().contains(carDvidId)) {
                    setCollectState(true);
                } else {
                    setCollectState(false);
                }
            }
        }
    }
/*
  ##################################################################################################
  ##                                     初始化数据的方法                                         ##
  ##################################################################################################
*/

    /**
     * @desc 初始化数据
     * @createtime 2016/10/24 - 10:09
     * @author xiaoxue
     */
    private void initData() {

        // 从车辆列表点击Item中传送字段获取，并请求数据
        Intent i = getIntent();
        carDvidId = i.getStringExtra(UC_CARDETAIL_ID);
        getDataFromNet(carDvidId);
    }

    /**
     * @param
     * @return
     * @desc (从网络上获取信息)
     * @user sunyao
     * @createtime 2016/11/25 - 15:21
     */
    public void getDataFromNet(String carDvidId) {
        if (!TextUtils.isEmpty(carDvidId)) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("dvid", carDvidId);
            if (FrameEtongApplication.getApplication().isLogin()) {
                if (null != FrameEtongApplication.getApplication().getUserInfo().getUserId()) {
                    map.put("userId", FrameEtongApplication.getApplication().getUserInfo().getUserId());
                }
            }
            if(!isTestDebug){
                loadStart();
            }
            mUC_WorldProvider.getJXUsedCarDetail(map);
        } else {
            showDataError();
        }
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

        if (view.getId() == R.id.used_car_iv_photo) {
            if (pictureConfig == null || pictureConfig.getImgUrlsCount() <= 0) {
                toastMsg("暂无图片");
                return;
            }
            Bundle mBundle = new Bundle();
            mBundle.putSerializable(UC_SelectImageActivity.IMAGE_DATA, pictureConfig);
            ActivitySkipUtil.skipActivity(UC_CarDetailActivity.this, UC_SelectImageActivity.class, mBundle);
        } else if (view.getId() == R.id.used_car_details_parameter) {//参数配置
            if (titleConfig == null || TextUtils.isEmpty(titleConfig.getF_cartypeid())) {
                return;
            }
            Bundle mBundle = new Bundle();
            mBundle.putSerializable(UC_ConfigInfo.CONFIG_DATA, titleConfig);
            ActivitySkipUtil.skipActivity(UC_CarDetailActivity.this, UC_ConfigInfo.class, mBundle);
        } else if (view.getId() == R.id.default_appoint_drive) {//收藏
            if (FrameEtongApplication.getApplication().isLogin()) {
                mUC_WorldProvider.clickCollection(carDvidId, TAG);//点击收藏
                return;
            } else {
                toastMsg("您还未登录，请登录");
                Intent intent = new Intent();
                intent.setClass(this, FramePersonalLoginActivity.class);
                startActivity(intent);
            }
        } else if (view.getId() == R.id.default_appoint_order) {//预约看车

            if (FrameEtongApplication.getApplication().isLogin()) {
                if (detailBean == null || detailBean.getTitleConfig() == null) {
                    return;
                }
                mUC_WorldProvider.orderCar(detailBean.getTitleConfig().getF_cartitle(), detailBean.getTitleConfig().getF_cartypeid(), TAG);//请求预约看车
                return;
            } else {
                toastMsg("您还未登录，请登录");
                Intent intent = new Intent();
                intent.setClass(this, FramePersonalLoginActivity.class);
                startActivity(intent);
            }
        }


    }

/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    /**
     * @desc (预约看车处理)
     * @createtime 2016/11/25 - 11:19
     * @author xiaoxue
     */
    @Subscriber(tag = FrameHttpTag.ORDERCAR + TAG)
    protected void getOrderCar(HttpMethod method) {
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            showInfoAlertDialog(this);
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
            if (null != msg && !"".equals(msg) && !TextUtils.isEmpty(msg)) {
                toastMsg(msg);
            } else {
                toastMsg("预约看车失败");
            }
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {
            if (null != msg && !"".equals(msg) && !TextUtils.isEmpty(msg)) {
                toastMsg(msg);
            } else {
                toastMsg("服务器无响应");
            }
        } else {
            toastMsg(msg);
        }
    }


    /**
     * @desc (点击收藏处理)
     * @createtime 2016/12/2 - 14:57
     * @author xiaoxue
     */
    @Subscriber(tag = FrameHttpTag.CLICKCOLLECTION + TAG)
    protected void clickCollection(HttpMethod method) {
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        String dvid = (String) method.getParam().get("dvid");
        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            JSONObject data = method.data().getJSONObject("data");
            UC_CollectBean mUC_CollectBean = JSON.toJavaObject(data, UC_CollectBean.class);
            //根据状态设置是否收藏到缓存
            if (mUC_CollectBean.getF_collectstatus().equals("1")) {
                setCollectCache(true, dvid);
                setCollectState(true);
            } else if (mUC_CollectBean.getF_collectstatus().equals("0")) {
                setCollectCache(false, dvid);
                setCollectState(false);
            }
            L.d("------------->", "车辆详情收藏： " + mUC_CollectBean.getF_collectstatus());
            toastMsg(mUC_CollectBean.getMsg());
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
            if (null != msg && !"".equals(msg) && !TextUtils.isEmpty(msg)) {
                toastMsg(msg);
            }
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {
            if (null != msg && !"".equals(msg) && !TextUtils.isEmpty(msg)) {
                toastMsg(msg);
            }
        } else {
            toastMsg(msg);
        }
    }


    @Subscriber(tag = FrameHttpTag.USED_CAR_CARDETAIL_INFO)
    public void getCarDetailData(HttpMethod method) {
        loadFinish();
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            carDetailLv.setVisibility(View.VISIBLE);
            emptyViewVp.setVisibility(View.GONE);
            JSONObject data = method.data().getJSONObject("data");
            detailBean = JSON.toJavaObject(data, UC_CarDetailJavabean.class);

            setCarDetailData(detailBean);
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
            showNetWorKError();
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {
            showDataError();
        } else {
            showDataError();
        }
    }

    /**
     * @param
     * @return
     * @desc (根据获取到的数据设置页面的值)
     * @user sunyao
     * @createtime 2016/12/13 - 16:37
     */
    private void setCarDetailData(UC_CarDetailJavabean detailJavabean) {
        if (detailJavabean == null) {
            return;
        }
        titleConfig = detailJavabean.getTitleConfig();
        pictureConfig = detailJavabean.getPictureConfig();
        getListItemByInfo(titleConfig);
        if (titleConfig == null) {
            return;
        }
        // 设置标题名字
        if (!TextUtils.isEmpty(titleConfig.getF_brand()) && !TextUtils.isEmpty(titleConfig.getF_carset())) {
            mTitleBar.setTitle(titleConfig.getF_brand() + titleConfig.getF_carset());
        }
        // 设置头部图片
        if (detailJavabean.getPictureConfig() != null && detailJavabean.getPictureConfig().getImgUrlsCount() > 1) {
            String imgeUrl = detailJavabean.getPictureConfig().getImgUrls().get(0).getImgUrl();
            mImageProvider.loadImage(carDetailIv, imgeUrl, R.mipmap.used_car_default_img);
            carDetailTxt.setText("图集(" + detailJavabean.getPictureConfig().getImgUrls().size() + ")");
        } else {
            carDetailTxt.setText("图集(0)");
        }
        // 设置头部名字
        if (!TextUtils.isEmpty(titleConfig.getF_cartitle())) {
            carDetailTitlte.setText(titleConfig.getF_cartitle());
        }
        // 设置头部售价
        if (!TextUtils.isEmpty(titleConfig.getF_price())) {
            carDetailP.setText(titleConfig.getF_price() + "万");
        }
        // 设置头部新车价
        if (!TextUtils.isEmpty(titleConfig.getF_newprice())) {
            carDetailNP.setText("新车价 ：" + titleConfig.getF_newprice() + "万");
        } else {
            carDetailNP.setText("新车价 ：暂无");
        }
        //根据状态设置是否收藏到缓存
        if (detailJavabean.getTitleConfig().getF_collectstatus() == 1) {
            defaultCollect = true;
            setCollectState(true);
        } else if (detailJavabean.getTitleConfig().getF_collectstatus() == 0) {
            defaultCollect = false;
            setCollectState(false);
        }
    }

    /**
     * @param
     * @return
     * @desc (从ConfigBean中获取到item的信息)
     * @user sunyao
     * @createtime 2016/12/13 - 19:32
     */
    private void getListItemByInfo(UC_CarDetailJavabean.TitleConfigBean titleConfig) {
        if (titleConfig == null) {
            return;
        }
        String[] configLetters = UC_ConfigItemUtils.getCarConLetterStr();
        Field field;
        List<String> datas = new ArrayList<String>();
        for (int i = 0; i < configLetters.length; i++) {
            Object object = "暂无记录";
            try {
                field = titleConfig.getClass().getDeclaredField(configLetters[i]);
                field.setAccessible(true);
                object = field.get(titleConfig);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            datas.add(TextUtils.isEmpty(object.toString()) ? "暂无记录" : object.toString());
        }
        carDetailAdapter.updateDatas(datas);
    }

    /**
     * @desc 弹出对话框
     * @createtime 2016/10/13 - 19:03
     * @author xiaoxue
     */
    private void showInfoAlertDialog(Context context) {

        final UC_Dialog mDialog = new UC_Dialog(context, "亲, 您已经成功预约看车申请, 将会有客服与您联系, 请保持手机畅通哦! 谢谢~", "继续逛逛");
        mDialog.show();
        mDialog.setClicklistener(new UC_Dialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                // TODO Auto-generated method stub
                mDialog.dismiss();
//                ActivitySkipUtil.skipActivity(UC_DetailsActivity.this, UC_World_ListActivity.class);
                finish();
            }
        });
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

/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

    /**
     * @param
     * @return
     * @desc (显示数据错误的视图)
     * @user sunyao
     * @createtime 2016/11/25 - 15:19
     */
    private void showDataError() {
        // 则显示数据错误的视图
        carDetailLv.setVisibility(View.GONE);
        emptyViewVp.setVisibility(View.VISIBLE);
//        emptyViewTv.setText("数据错误，请返回重试！");
        setEmptyViewUtil.showNoServerView();
    }

    /**
     * @param
     * @return
     * @desc (显示网络错误时的视图)
     * @user sunyao
     * @createtime 2016/11/25 - 15:19
     */
    private void showNetWorKError() {
        carDetailLv.setVisibility(View.GONE);
        setEmptyViewUtil.showNetworkErrorView();
//        emptyViewTv.setText("网络错误，点击重试！");
        emptyViewVp.setVisibility(View.VISIBLE);
        emptyViewVp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 再次请求网络数据
                getDataFromNet(carDvidId);
            }
        });
    }

    /**
     * @desc (设置收藏状态)
     * @createtime 2016/12/10 0010-15:56
     * @author wukefan
     */
    public void setCollectState(boolean isCollect) {
        if (isCollect) {
            collect.setText("已收藏");
        } else {
            collect.setText("收藏");
        }
        collect.setSelected(isCollect);
        this.isCollect = isCollect;
    }

    /**
     * @desc (设置收藏缓存)
     * @createtime 2016/12/14 0014-16:52
     * @author wukefan
     */
    protected void setCollectCache(boolean isCollect, String dvid) {

        UsedAndNewCollectCar collectUsedCar = FrameEtongApplication.getApplication().getUsedCarCollectCache();
        if (null == dvid || TextUtils.isEmpty(dvid.trim())) {
            return;
        }
        if (isCollect) { //设置收藏缓存
            if (null != collectUsedCar.getCarList()) {//收藏缓存有数据
                if (!collectUsedCar.getCarList().contains(dvid)) {
                    List<String> collectUsedCarList = collectUsedCar.getCarList();
                    collectUsedCarList.add(dvid);
                    collectUsedCar.setCarList(collectUsedCarList);
                }
            } else {//收藏缓存没数据
                List<String> collectList = new ArrayList<>();
                collectList.add(dvid);
                collectUsedCar.setCarList(collectList);
            }
        } else {//移除收藏缓存
            if (null != collectUsedCar.getCarList()) {
                if (collectUsedCar.getCarList().contains(dvid)) {
                    List<String> collectList = collectUsedCar.getCarList();
                    collectList.remove(dvid);
                    collectUsedCar.setCarList(collectList);
                }
            }
        }
//        collectUsedCar.setChanged(true);
        FrameEtongApplication.getApplication().setUsedCarCollectCache(collectUsedCar);
    }

    /**
     * @desc (判断收藏状态是否改变)
     * @createtime 2016/11/8 0008-10:34
     * @author wukefan
     */
    public void setCollect() {
        if (defaultCollect != isCollect) {
            UsedAndNewCollectCar temp = FrameEtongApplication.getApplication().getUsedCarCollectCache();
            temp.setChanged(true);
            FrameEtongApplication.getApplication().setUsedCarCollectCache(temp);
        }
    }
}
