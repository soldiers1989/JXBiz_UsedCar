package com.etong.android.jxappfours.find_car.grand.bean;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.jxappfours.R;

/**
 * Created by Ellison.Sun on 2016/8/12.
 */
public class Find_car_OnSaleFragment extends BaseSubscriberFragment {

    private Context mContext;

    public Find_car_OnSaleFragment(Context context) {
        this.mContext = context;
    }

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.find_car_detail_list_item, null);

        return view;
    }
}
