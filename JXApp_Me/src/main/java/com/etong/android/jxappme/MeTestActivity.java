package com.etong.android.jxappme;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.jxappme.view.MeMainFragment;


/**
 * "我的"子模块测试的Activity
 */
public class MeTestActivity extends BaseSubscriberActivity {

    private MeMainFragment mFragment = new MeMainFragment();
//    private TitleBar mTitleBar;

    @Override
    protected void onInit(Bundle savedInstanceState) {

        setContentView(R.layout.activity_me_test);

//        mTitleBar = new TitleBar(this);
//        mTitleBar.showBackButton(false);
//        mTitleBar.showNextButton(false);
////        mTitleBar.setTitle("个人中心");
//        mTitleBar.showBottomLin(false);

        FragmentManager manager = getSupportFragmentManager();

        // 1.开启事务
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.me_fl_frg_container,mFragment);
        // 提交事务
        transaction.commitAllowingStateLoss();
    }

}
