package com.etong.android.jxappmore;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.jxappmore.view.MoreMainFragment;


/**
 * "更多"子模块测试的Activity
 */
public class MoreTestActivity extends BaseSubscriberActivity {


    private MoreMainFragment mFragment = new MoreMainFragment();
    @Override
    protected void onInit(Bundle savedInstanceState) {
        setContentView(R.layout.activity_more_test);

        FragmentManager manager = getSupportFragmentManager();

        // 1.开启事务
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.more_fl_frg_container,mFragment);
        // 提交事务
        transaction.commitAllowingStateLoss();
    }
}
