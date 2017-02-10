package com.etong.android.frame.widget.three_slide_300.main_content;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.etong.android.frame.R;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.three_slide_300.impl.OnChooseBrandListener;
import com.etong.android.frame.widget.three_slide_300.impl.OnChooseCarSeriesListener;
import com.etong.android.frame.widget.three_slide_300.impl.OnChooseCarTypeListener;
import com.etong.android.frame.widget.three_slide_300.impl.OnChooseCollectListener;
import com.etong.android.frame.widget.three_slide_300.impl.OnCloseFragmentListener;
import com.etong.android.frame.widget.three_slide_300.impl.OnCloseOnlyoneFragmentListener;
import com.etong.android.frame.widget.three_slide_300.javabean.Models_Contrast_VechileType;

import org.simple.eventbus.EventBus;

/**
 * @author : by sunyao
 * @ClassName : MC_ChooseCarType
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/9/27 - 20:21
 */

public class MC_ChooseCarType implements OnCloseFragmentListener
                    , OnChooseBrandListener
                    , OnChooseCarSeriesListener
                    , OnChooseCollectListener
                    , OnCloseOnlyoneFragmentListener
                    , OnChooseCarTypeListener {

    public static final String CAT_TYPE_TAG = "mc_choose_cartype_tag";

    private Context mContext;                   // 全局上下文
    private FragmentActivity mFragmentActivity; // FragmentActivity
    private int mBrandLayoutId;                 // 需要依附的布局id

    // 设置侧滑出来的界面一个唯一的标记，1， 2， 3
    public static int LEVEL = 1;
    // 三个侧滑出来的界面
    private MC_SelectBrandFragment brandFragment;
    private MC_SelectCarSeriesFragment seriesFragment;
    private MC_SelectCarType_typeFragment carTypeFragment;
    private DrawerLayout brandDrawerLayout;     // 车辆品牌侧滑
    private DrawerLayout carSeriesDrawerLayou;  // 车系侧滑
    private DrawerLayout carTypeDrawerLayou;    // 车型侧滑

    public static boolean IsNeedChecked = false;

    // 构造函数初始化必要参数
    public MC_ChooseCarType(Context context, FragmentActivity fa, DrawerLayout bDrawerLayout, int brandLayoutId) {
        this.mContext = context;
        this.mFragmentActivity = fa;
        this.brandDrawerLayout = bDrawerLayout;
        this.mBrandLayoutId = brandLayoutId;

        this.LEVEL = 1;
        initDrawerLayout();
    }

    /**
     * @desc (初始化必要的Fragment 侧滑界面)
     * @user sunyao
     * @createtime 2016/9/27 - 20:26
     *
     * @param
     * @return
     */
    private void initDrawerLayout() {
        //品牌fragment
        FragmentManager manager = mFragmentActivity.getSupportFragmentManager();
        // 1.开启事务
        FragmentTransaction transaction = manager.beginTransaction();
        brandFragment = new MC_SelectBrandFragment();
        transaction.replace(mBrandLayoutId, brandFragment);         // 替换所要找到的id
        // 提交事务
        transaction.commitAllowingStateLoss();
        // 找到品牌界面中侧滑出来选择
        brandDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        brandDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            /**
             * 当抽屉滑动状态改变的时候被调用
             * 状态值是STATE_IDLE（闲置--0）, STATE_DRAGGING（拖拽的--1）, STATE_SETTLING（固定--2）中之一。
             * 抽屉打开的时候，点击抽屉，drawer的状态就会变成STATE_DRAGGING，然后变成STATE_IDLE
             */
            @Override
            public void onDrawerStateChanged(int arg0) {
                L.i("drawer", "drawer的状态：" + arg0);
            }
            /**
             * 当抽屉被滑动的时候调用此方法
             * arg1 表示 滑动的幅度（0-1）
             */
            @Override
            public void onDrawerSlide(View arg0, float arg1) {
                L.i("drawer", arg1 + "");
                brandFragment.setTextDialogInvisiable();
            }
            /**
             * 当一个抽屉被完全打开的时候被调用
             */
            @Override
            public void onDrawerOpened(View arg0) {
                L.d("drawer---------------", "抽屉被完全打开了！");
                brandDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);      // 打开手势滑动，关闭的时候可以用手势滑动关闭
            }
            /**
             * 当一个抽屉完全关闭的时候调用此方法
             */
            @Override
            public void onDrawerClosed(View arg0) {
                L.d("drawer", "抽屉被完全关闭了！");
                brandDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });


        // 初始化监听事件
        brandFragment.setOnChooseBrandListener(this);
        brandFragment.setOnChooseCollectListener(this);
        brandFragment.setOnCloseFragmentListener(this);

        //车系fragment
        FragmentManager managerSeries = mFragmentActivity.getSupportFragmentManager();
        FragmentTransaction transactionSeries = managerSeries.beginTransaction();
        seriesFragment = new MC_SelectCarSeriesFragment();
        transactionSeries.replace(R.id.models_contrast_fly_drawer_carseries, seriesFragment);
        // 提交事务
        transactionSeries.commitAllowingStateLoss();

        // 初始化监听事件
        seriesFragment.setOnCloseFragmentListener(this);
        seriesFragment.setOnChooseCarSeriesListener(this);
        seriesFragment.setOnCloseOnlyoneFragmentListener(this);

        //车型fragment
        FragmentManager managerType = mFragmentActivity.getSupportFragmentManager();
        FragmentTransaction transactionType = managerType.beginTransaction();
        carTypeFragment = new MC_SelectCarType_typeFragment();
        transactionType.replace(R.id.models_contrast_fly_drawer_cartype, carTypeFragment);
        // 提交事务
        transactionType.commitAllowingStateLoss();
        carTypeFragment.setOnChooseCarTypeListener(this);
        carTypeFragment.setOnCloseAllFragmentListener(this);
        carTypeFragment.setOnCloseOneFragmentListener(this);
    }

    @Override
    public void closeFragmentAllFragment() {
        if(1 == this.LEVEL) {
            brandDrawerLayout.closeDrawer(Gravity.RIGHT);
//            this.LEVEL = 1;
        } else if (2 == this.LEVEL) {
            carSeriesDrawerLayou.closeDrawer(Gravity.RIGHT);
            brandDrawerLayout.closeDrawer(Gravity.RIGHT);
//            this.LEVEL = 2;
        } else if (3 == this.LEVEL) {
            carTypeDrawerLayou.closeDrawer(Gravity.RIGHT);
            carSeriesDrawerLayou.closeDrawer(Gravity.RIGHT);
            brandDrawerLayout.closeDrawer(Gravity.RIGHT);
//            this.LEVEL = 3;
        }
        this.LEVEL = 1;
    }

    /**
     * @desc (根据当前的级数来关闭当前的侧滑界面)
     * @user sunyao
     * @createtime 2016/9/28 - 15:23
     *
     * @param
     * @return
     */
    @Override
    public void closeOnlyOneFragment() {
        if(2 == this.LEVEL) {
            this.LEVEL = 1;
            this.carSeriesDrawerLayou.closeDrawer(Gravity.RIGHT);
        } else if (3 == this.LEVEL) {
            this.LEVEL = 2;
            this.carTypeDrawerLayou.closeDrawer(Gravity.RIGHT);
        }
    }

    /**
     * @desc (根据当前的级数来打开侧滑界面)
     * @user sunyao
     * @createtime 2016/9/28 - 15:22
     *
     * @param
     * @return
     */
    public void openOneFragment() {
        if (1 == this.LEVEL) {
            brandDrawerLayout.openDrawer(Gravity.RIGHT);//右边
        } else if (2 == this.LEVEL) {
            brandDrawerLayout.openDrawer(Gravity.RIGHT);//右边
            carSeriesDrawerLayou.openDrawer(Gravity.RIGHT);
        } else if (3 == this.LEVEL) {
            brandDrawerLayout.openDrawer(Gravity.RIGHT);//右边
            carSeriesDrawerLayou.openDrawer(Gravity.RIGHT);
            carTypeDrawerLayou.openDrawer(Gravity.RIGHT);
        }
    }

    @Override
    public void onChooseBrand(int id) {
        this.LEVEL = 2;     // 控制侧滑出来的页面是哪个
        carSeriesDrawerLayou = (DrawerLayout) mFragmentActivity.findViewById(R.id.models_contrast_drawer_layout_carseries);
        carSeriesDrawerLayou.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        EventBus.getDefault().post(id, "car serice");
        carSeriesDrawerLayou.openDrawer(Gravity.RIGHT);//右边
    }

    /**
     * @desc (在品牌界面点击收藏item时跳转到收藏页面)
     * @user sunyao
     * @createtime 2016/9/28 - 17:31
     *
     * @param
     * @return
     */
    @Override
    public void onChooseCollect() {
        this.LEVEL = 2;     // 控制侧滑出来的页面是哪个
        FragmentManager manager2 = mFragmentActivity.getSupportFragmentManager();
        // 1.开启事务
        FragmentTransaction transaction2 = manager2.beginTransaction();

        MC_CollectFragment fragment2 = new MC_CollectFragment();
        transaction2.replace(R.id.models_contrast_fly_drawer_collect, fragment2);
        // 提交事务
        transaction2.commitAllowingStateLoss();

        // 设置点击事件
        fragment2.setOnCloseOneFragmentListener(this);
        fragment2.setOnCloseAllFragmentListener(this);
        fragment2.setOnChooseCarSeriesListener(this);

        carSeriesDrawerLayou = (DrawerLayout) mFragmentActivity.findViewById(R.id.models_contrast_drawer_layout_carseries);
        carSeriesDrawerLayou.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        EventBus.getDefault().post("onChooseCollect", "go to collect fragment");

        carSeriesDrawerLayou.openDrawer(Gravity.RIGHT);//右边
    }

    /**
     * @desc (点击车系列表页之后获取到的车系id，后面打开车型侧滑)
     * @user sunyao
     * @createtime 2016/9/28 - 17:31
     *
     * @param
     * @return
     */
    @Override
    public void onChooseCarSeries(int id) {
        this.LEVEL = 3;     // 控制侧滑出来的页面是哪个
        carTypeDrawerLayou = (DrawerLayout) mFragmentActivity.findViewById(R.id.models_contrast_drawer_layout_cartype);

        carTypeDrawerLayou.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        EventBus.getDefault().post(id, "car type");
        carTypeDrawerLayou.openDrawer(Gravity.RIGHT);//右边
    }
    // 设置是否当前添加的车辆需不需要检查
    public void setNeedChecked(boolean needChecked) {
        IsNeedChecked = needChecked;
    }

    /**
     * @desc (最后点击车型获取到的最后车型数据)
     * @user sunyao
     * @createtime 2016/9/28 - 17:30
     */
    @Override
    public void onChooseCarType(Models_Contrast_VechileType addVechile) {
//        if (addVechile != null) {
//            return addVechile;
//        }
//        return null;
        EventBus.getDefault().post(addVechile, CAT_TYPE_TAG);
    }
}
