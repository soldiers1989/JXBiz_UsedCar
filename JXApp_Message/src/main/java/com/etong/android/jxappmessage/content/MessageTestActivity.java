package com.etong.android.jxappmessage.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.jxappmessage.R;

/**
 * 测试的activity
 */
public class MessageTestActivity extends BaseSubscriberActivity {


    MessageMainFragment fragment=new MessageMainFragment();

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_message_test);




        FragmentManager manager = getSupportFragmentManager();
        // 1.开启事务
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.message_fl_frg_container,fragment);
        // 提交事务
        transaction.commitAllowingStateLoss();


    }
}
