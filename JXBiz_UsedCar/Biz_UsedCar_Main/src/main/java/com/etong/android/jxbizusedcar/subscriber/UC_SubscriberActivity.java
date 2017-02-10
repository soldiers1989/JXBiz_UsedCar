package com.etong.android.jxbizusedcar.subscriber;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.UC_UserProvider;


/**
 * Created by Dasheng on 2016/10/11.
 */

public abstract class UC_SubscriberActivity extends BaseSubscriberActivity {

    protected UC_UserProvider mProvider;

    @Override
    protected void onInit(@Nullable Bundle bundle) {

        mProvider = UC_UserProvider.getInstance();
        mProvider.initalize(HttpPublisher.getInstance());
        myInit(bundle);
//        UC_StatuBarUtils.setFullScreen(this);

    }

    protected abstract void myInit(Bundle bundle);
}
