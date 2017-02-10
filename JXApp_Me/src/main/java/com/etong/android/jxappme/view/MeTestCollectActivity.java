package com.etong.android.jxappme.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappme.R;
import com.etong.android.jxappme.fragment.MeNewCarFragment;

/**
 * @desc (收藏activity)
 * @createtime 2016/12/9 - 16:43
 * @Created by xiaoxue.
 */

public class MeTestCollectActivity extends BaseSubscriberActivity {
    MeNewCarFragment fragment=new MeNewCarFragment();
    private TitleBar mTitleBar;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_me_collect_test);
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("收藏");
        mTitleBar.showBottomLin(false);


        FragmentManager manager = getSupportFragmentManager();
        // 1.开启事务
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.me_fl_frg_collect,fragment);
        // 提交事务
        transaction.commitAllowingStateLoss();


    }
}
