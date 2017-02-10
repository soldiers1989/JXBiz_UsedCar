package com.etong.android.jxappusedcar.content;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.user.UsedAndNewCollectCar;
import com.etong.android.frame.utils.ActivityStackManager;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_CarConfigAdapter;
import com.etong.android.jxappusedcar.javabean.UC_CarConfigJavabean;
import com.etong.android.jxappusedcar.javabean.UC_CarConfigListItem;
import com.etong.android.jxappusedcar.javabean.UC_CarDetailJavabean;
import com.etong.android.jxappusedcar.javabean.UC_CollectBean;
import com.etong.android.jxappusedcar.javabean.UC_World_CarListJavaBean;
import com.etong.android.jxappusedcar.provider.UC_WorldProvider;
import com.etong.android.jxappusedcar.utils.UC_ConfigInfoUtil;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : by sunyao
 * @ClassName : UC_ConfigInfo
 * @Description : (二手车中车辆配置界面)
 * @date : 2016/10/10 - 16:09
 */

public class UC_ConfigInfo extends BaseSubscriberActivity {
    /*
      ##################################################################################################
      ##                                        类中的变量                                            ##
      ##################################################################################################
    */
    public static final String TAG = "UC_ConfigInfo";
    public static String CONFIG_DATA;                   // 从车辆详情界面传送过来的config info
    private PinnedSectionListView listView;
    private UC_CarConfigAdapter configAdapter;
    private TextView titleName;
    private TextView titlePrice;
    private TextView realName;
    private DrawerLayout carconfig_drawerlayout;        // 侧滑
    private UC_CarconfigMenu selectCarconfigMenu;       // 侧滑出来的menu
    private List<UC_CarConfigJavabean> list;
    private UC_CarConfigJavabean mUC_CarConfigJavabean;
    private List datalist = new ArrayList();//所有参数的list
    private HttpPublisher mHttpPublisher;
    private UC_WorldProvider mUC_WorldProvider;
    private String carDvidId;
    private UC_World_CarListJavaBean mUC_World_CarListJavaBean;
    private int isNewFlag = 0;            //1--新车 0--二手车
    private Button default_appoint_drive;
    private Button default_appoint_order;
    private UC_CarDetailJavabean.TitleConfigBean configInfo;
    private String carTypeId;
    private View lv_footView;
    public static boolean isTestDebug = false;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.used_car_carconfig_content);

        initView();
        initFootView();
        initMenuSelectInfo();
    }


/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/

    /**
     * @param
     * @return
     * @desc (必要的初始化操作)
     * @user sunyao
     * @createtime 2016/10/10 - 16:49
     */
    private void initView() {
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mUC_WorldProvider = UC_WorldProvider.getInstance();
        mUC_WorldProvider.initialize(mHttpPublisher);

        Intent intent = getIntent();
        configInfo = (UC_CarDetailJavabean.TitleConfigBean) intent.getSerializableExtra(CONFIG_DATA);
        if (configInfo != null) {
            carTypeId = configInfo.getF_cartypeid();
            carDvidId = configInfo.getF_dvid();
        }
        mUC_World_CarListJavaBean = (UC_World_CarListJavaBean) intent.getSerializableExtra("UC_World_CarListJavaBean");

        // 初始化标题栏
        TitleBar titleBar = new TitleBar(this);
        titleBar.setTitle("车辆参数详情");
        titleBar.showNextButton(false);

        listView = (PinnedSectionListView) findViewById(R.id.used_car_carconfig_lv);
        configAdapter = new UC_CarConfigAdapter(this);

        // 获取车辆配置
        mUC_WorldProvider.getCarInfoConfig(carTypeId);
        if (!isTestDebug) {
            loadStart("数据获取中...", 0);
        }
    }

    /**
     * @param
     * @return
     * @desc (初始化头部布局)
     * @user sunyao
     * @createtime 2016/10/10 - 17:12
     */
    private void initFootView() {

        default_appoint_drive = (Button) findViewById(R.id.default_appoint_drive);      // 收藏
        default_appoint_order = (Button) findViewById(R.id.default_appoint_order);      // 预约看车
        addClickListener(R.id.default_appoint_drive);       // 收藏按钮
        addClickListener(R.id.default_appoint_order);       // 预约看车按钮

        carconfig_drawerlayout = (DrawerLayout) findViewById(R.id.used_car_dl_drawerlayout);
        carconfig_drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动，因为和左边的sidebar冲突

        // 添加header或者footer之后，才能setAdapter
        initializeHeaderAndFooter();    // 初始化ListView中的Header和footer
        listView.setAdapter(configAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                UC_CarConfigListItem item = configAdapter.getItem(position - listView.getHeaderViewsCount());
                if (item != null) {
                    // 只有SECTION的item才能点击
                    if (item.type == UC_CarConfigListItem.SECTION) {
//                        Toast.makeText(Find_car_CarConfigActivity.this, "Item " + (position-listView.getHeaderViewsCount()) + ": " + item.tText, Toast.LENGTH_SHORT).show();
                        selectCarconfigMenu.updateSelectPosition(item.tText);
                        carconfig_drawerlayout.openDrawer(Gravity.RIGHT);       // 从右边打开侧滑菜单
                    }
                }
            }
        });

        // 设置DrawerLayout不能侧滑出来，但是可以侧滑关闭
        carconfig_drawerlayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                carconfig_drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);      // 打开手势滑动，关闭的时候可以用手势滑动关闭
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                carconfig_drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //关闭手势滑动，因为和左边的sidebar冲突
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        setCollectState(UC_CarDetailActivity.isCollect);
    }

    /*
      ##################################################################################################
      ##                                     初始化数据的方法                                         ##
      ##################################################################################################
    */
    private void initMenuSelectInfo() {

        //侧滑界面的初始化，Fragment
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        selectCarconfigMenu = new UC_CarconfigMenu(this, carconfig_drawerlayout);
        transaction.add(R.id.used_car_frg_config_menu, selectCarconfigMenu);
        transaction.commitAllowingStateLoss();

        // 监听事件
        selectCarconfigMenu.setOnItemSelectListener(new UC_CarconfigMenu.OnCarconfigItemSelectListener() {
            @Override
            public void onCarconfigItemSelect(String positionName) {
                // 判断Section名字的位置，选中
                for (int i = 0; i < configAdapter.getCount(); i++) {
                    if (configAdapter.getItem(i).type == UC_CarConfigListItem.SECTION) {

                        if (positionName.equals(configAdapter.getItem(i).tText)) {
//                            return mFind_car_CarConfigAdapter.getItem(i).listPosition;
                            listView.setSelection(configAdapter.getItem(i).listPosition + listView.getHeaderViewsCount());
                        }
                    }
                }
            }
        });

    }


    /**
     * 初始化ListView中的头部
     */
    private void initializeHeaderAndFooter() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.used_car_carconfig_detail_header, null);
        titleName = (TextView) view.findViewById(R.id.used_car_header_titlename);       // 头部名字
        titlePrice = (TextView) view.findViewById(R.id.used_car_header_titleprice);     // 头部价格
        realName = (TextView) view.findViewById(R.id.used_car_header_title_realname);   // 真实名字

        // 底部文字
        lv_footView = LayoutInflater.from(this).inflate(R.layout.uc_car_config_lv_footview, null);

        // 设置头部的信息
        if (configInfo != null) {
            // 设置标题
            if (!TextUtils.isEmpty(configInfo.getF_cartitle())) {
                titleName.setText(configInfo.getF_cartitle());
            } else {
                titleName.setVisibility(View.GONE);
            }
            // 设置价格
            if (!TextUtils.isEmpty(configInfo.getF_price())) {
                titlePrice.setText(configInfo.getF_price() + " 万");
            } else {
                titlePrice.setVisibility(View.GONE);
            }
            // 设置品牌、车系、车型名字
            if (!TextUtils.isEmpty(configInfo.getF_brand())
                    || !TextUtils.isEmpty(configInfo.getF_carset())
                    || !TextUtils.isEmpty(configInfo.getF_cartype())) {
                realName.setText(configInfo.getF_brand() + " " + configInfo.getF_carset() + " " + configInfo.getF_cartype());
            }
        }

        // 将头布局添加到ListView中
        try {
            listView.addHeaderView(view, null, false);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //判断缓存是否收藏
        if (null != default_appoint_drive) {
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
    ##                                     点击事件的处理                                           ##
    ##################################################################################################
    */
    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.default_appoint_drive) {//收藏
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
                if (configInfo == null) {
                    return;
                }
                mUC_WorldProvider.orderCar(configInfo.getF_cartitle(), configInfo.getF_cartypeid(), TAG);//请求预约看车
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
                setCollectCache(true, dvid);//添加到缓存
                setCollectState(true);
            } else if (mUC_CollectBean.getF_collectstatus().equals("0")) {
                setCollectCache(false, dvid);//移除缓存
                setCollectState(false);
            }
            toastMsg(mUC_CollectBean.getMsg());
            L.d("------------->", "参数配置收藏： " + mUC_CollectBean.getF_collectstatus());
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

    @Subscriber(tag = FrameHttpTag.CONFIGINFO)
    protected void getCarConfigInfo(HttpMethod httpMethod) {
        loadFinish();
        String errno = httpMethod.data().getString("errno");
        String flag = httpMethod.data().getString("flag");
        String msg = httpMethod.data().getString("msg");
        if (!TextUtils.isEmpty(flag)) {
            if (("0").equals(flag)) {
                JSONObject jsonArr = httpMethod.data().getJSONObject("data");

                UC_ConfigInfoUtil util = new UC_ConfigInfoUtil(jsonArr);            // 新建工具类
                datalist = util.getCompareCarInfoList();                            // 获取到所有的对象，并保存到List中
                configAdapter.updateListData(datalist);

                if (listView.getFooterViewsCount() <= 0) {
                    listView.addFooterView(lv_footView);        // 添加脚部文字
                }

            } else if (HttpPublisher.NETWORK_ERROR.equals(flag)) {
                // 显示网络错误的视图

            } else if (HttpPublisher.DATA_ERROR.equals(flag)) {
                // 显示数据错误，网页找不到的视图

            }
        }
    }


    /**
     * @desc 弹出对话框
     * @createtime 2016/10/13 - 19:03
     * @author xiaoxue
     */
    protected void showInfoAlertDialog(Context context) {

        final UC_Dialog mDialog = new UC_Dialog(context, "亲, 您已经成功预约看车申请, 将会有客服与您联系, 请保持手机畅通哦! 谢谢~", "继续逛逛");
        mDialog.show();
        mDialog.setClicklistener(new UC_Dialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                // TODO Auto-generated method stub
                mDialog.dismiss();
                ActivityStackManager.create().finishActivity(UC_CarDetailActivity.class);
                finish();
            }
        });
    }

/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

    /**
     * @desc (设置收藏状态)
     * @createtime 2016/12/10 0010-15:56
     * @author wukefan
     */
    protected void setCollectState(boolean isCollect) {
        if (isCollect) {
            default_appoint_drive.setText("已收藏");
        } else {
            default_appoint_drive.setText("收藏");
        }
        default_appoint_drive.setSelected(isCollect);
        UC_CarDetailActivity.isCollect = isCollect;
    }

    /**
     * @desc (设置收藏缓存)
     * @createtime 2016/12/14 0014-16:52
     * @author wukefan
     */
    protected void setCollectCache(boolean isCollect, String dvid) {

        if (null == dvid || TextUtils.isEmpty(dvid.trim())) {
            return;
        }

        UsedAndNewCollectCar collectUsedCar = FrameEtongApplication.getApplication().getUsedCarCollectCache();
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
}
