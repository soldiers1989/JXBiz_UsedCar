package com.etong.android.jxbizusedcar.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;

import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.user.UC_FrameEtongApplication;
import com.etong.android.frame.utils.ActivityStackManager;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.Etong_SqlLiteDao;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.fragment.UC_FindFragment;
import com.etong.android.jxbizusedcar.fragment.UC_MessageMainFragment;
import com.etong.android.jxbizusedcar.fragment.UC_MineFragment;
import com.etong.android.jxbizusedcar.fragment.UC_MoreMainFragment;
import com.etong.android.jxbizusedcar.subscriber.UC_SubscriberActivity;

import org.simple.eventbus.Subscriber;

import java.util.Date;

public class UsedCar_MainActivity extends UC_SubscriberActivity {

    public Etong_SqlLiteDao mSqliteDao;  //数据库
    private String HIS_DATA = "his_car";  //历史记录标记
    private String ALL_DATA = "all_car";  //所有车标记

    public static final String TAG = "MainActivity";
    public static final String SWITCH_PAGE = "switch page";

    private Fragment[] mFragments;
    private RadioButton[] mRadioButtons;
    private static final int HOME_PAGE = 0;
    private static final int MESSAGE = 1;
    private static final int MORE =2;
    private static final int MINE =3;
    private long mExitTime=0;
    @Override
    protected void myInit(Bundle bundle) {

        setContentView(R.layout.uc_activity_used_car_main_content);


        initView();
        // 设置极光推送的别名
        UC_FrameEtongApplication.getApplication().setJPushAlias();

        initFragment();
        switchFragment(0);

    }
    private void initFragment() {
    }

    private void initView() {
        mSqliteDao = Etong_SqlLiteDao.getInstance(this, ALL_DATA, HIS_DATA);

        mFragments = new Fragment[4];
        mRadioButtons = new RadioButton[4];
        mRadioButtons[0] = findViewById(R.id.uc_rb_home_page,RadioButton.class);    //二手车
        mRadioButtons[1] = findViewById(R.id.uc_rb_message,RadioButton.class);      //资讯
        mRadioButtons[2] = findViewById(R.id.uc_rb_more,RadioButton.class);     //更多
        mRadioButtons[3] = findViewById(R.id.uc_rb_mine,RadioButton.class);         //我的

        addClickListener(R.id.uc_rb_home_page);
        addClickListener(R.id.uc_rb_message);
        addClickListener(R.id.uc_rb_more);
        addClickListener(R.id.uc_rb_mine);

    }

    /**
     * 选择对应的fragment
     * @param index
     */
    @Subscriber(tag = SWITCH_PAGE)
    private void switchFragment(int index) {
        FragmentManager manager = getSupportFragmentManager();
        // 1.开启事务
        FragmentTransaction transaction = manager.beginTransaction();

        switch (index){
            case HOME_PAGE:
                if(mFragments[HOME_PAGE] == null){

//                    mFragments[HOME_PAGE] = new UC_HomePageFragment();
                    mFragments[HOME_PAGE] = new UC_FindFragment();
                    transaction
                            .add(R.id.uc_fl_fill_layouot, mFragments[index]);
                }
                mEventBus.post(TAG,TAG);
                break;
            case MESSAGE:
                if(mFragments[MESSAGE] == null){

                    mFragments[MESSAGE] = new UC_MessageMainFragment();
                    transaction
                            .add(R.id.uc_fl_fill_layouot, mFragments[index]);
                }
                break;
            case MORE:
                if(mFragments[MORE] == null){

                    mFragments[MORE] = new UC_MoreMainFragment();
                    transaction
                            .add(R.id.uc_fl_fill_layouot, mFragments[index]);
                }
                break;
            case MINE:
                if(mFragments[MINE] == null){

                    mFragments[MINE] = new UC_MineFragment();
                    transaction
                            .add(R.id.uc_fl_fill_layouot, mFragments[index]);
                }
                break;
        }
        hideFragments(transaction);
        transaction.show(mFragments[index]);
        mRadioButtons[index].setChecked(true);
        // 提交事务
        transaction.commitAllowingStateLoss();
    }

   /* @Subscriber(tag = UC_BuyCarFragment.GET_DATABEAN_FROME_HOMEPAGE)
    public void switchToBuyCar(Object bean) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(mFragments[BUY_CAR] == null){
            mFragments[BUY_CAR] = new UC_BuyCarFragment();
            transaction.add(R.id.uc_fl_fill_layouot, mFragments[BUY_CAR]);
            Bundle mBundle = new Bundle();
            if (bean.getClass() == UC_BrandBean.class) {
                mBundle.putSerializable(UC_BuyCarFragment.UC_BUYCAR_DATABEAN, (UC_BrandBean)bean);
            } else if (bean.getClass() == UC_PriceBean.class) {
                mBundle.putSerializable(UC_BuyCarFragment.UC_BUYCAR_DATABEAN, (UC_PriceBean)bean);
            }
            mFragments[BUY_CAR].setArguments(mBundle);
        } else {
            if (bean.getClass() == UC_BrandBean.class) {
                mEventBus.post((UC_BrandBean)bean, "home page brand");
            } else if (bean.getClass() == UC_PriceBean.class) {
                mEventBus.post((UC_PriceBean)bean, "home page price");
            }
        }
        hideFragments(transaction);
        transaction.show(mFragments[1]);
        mRadioButtons[1].setChecked(true);
        // 提交事务
        transaction.commitAllowingStateLoss();
    }*/

    /**
     * 隐藏所有的fragment
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {

        for (int i = 0; i < mFragments.length; i++) {
            if (mFragments[i] != null)
                transaction.hide(mFragments[i]);
        }
    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.uc_rb_home_page:
                switchFragment(HOME_PAGE);
                break;
            case R.id.uc_rb_message:
                switchFragment(MESSAGE);
                break;
            case R.id.uc_rb_more:
                switchFragment(MORE);
                break;
            case R.id.uc_rb_mine:
                switchFragment(MINE);
                break;
        }
    }

    /**
     * 获取所有搜索数据
     *
     * @param method
     */
    @Subscriber(tag = UC_HttpTag.ALL_CAR_BRAND_TYPE)
    public void allCar(HttpMethod method) {
        L.json(method.data().toString());
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");

        if (!TextUtils.isEmpty(status) && status.equals("true")) {
            JSONArray arrays = method.data().getJSONArray("data");
            mSqliteDao.insert(arrays, ALL_DATA);
            UC_FrameEtongApplication.getApplication().setTime(new Date().toString());
        } else {
//            toastMsg(msg);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                toastMsg("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
//				finish();
                //ActivityStackManager.create().AppExit(this);
                ActivityStackManager.create().finishAllActivity();
                // System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onResume() {
        super.onResume();

        for(int i=0; i<mRadioButtons.length; i++) {
            if (mRadioButtons[i].isChecked()) {
                switchFragment(i);
            }
        }
    }
}
