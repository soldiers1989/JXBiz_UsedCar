package com.etong.android.jxappfind.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.jxappfind.R;

/**
 * 测试activity
 * Created by Administrator on 2016/8/2.
 */
public class FindTestActivity extends BaseSubscriberActivity {


    FindMainFragment fragment=new FindMainFragment();

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_find_test);



        FragmentManager manager = getSupportFragmentManager();
        // 1.开启事务
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.find_fl_frg_container,fragment);
        // 提交事务
        transaction.commitAllowingStateLoss();


    }
}
