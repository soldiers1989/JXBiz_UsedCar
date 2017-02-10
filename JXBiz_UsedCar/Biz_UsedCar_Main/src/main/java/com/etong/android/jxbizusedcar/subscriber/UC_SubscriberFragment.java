package com.etong.android.jxbizusedcar.subscriber;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.UC_UserProvider;

/**
 * Created by Dasheng on 2016/10/11.
 */

public abstract class UC_SubscriberFragment extends BaseSubscriberFragment {

    protected UC_UserProvider mProvider;

    @Override
    protected View onInit(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
//        UC_StatuBarUtils.setFullScreen(getActivity());
        mProvider = UC_UserProvider.getInstance();
        mProvider.initalize(HttpPublisher.getInstance());
        return myInit(layoutInflater,viewGroup,bundle);
    }

    protected abstract View myInit(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle);
}
