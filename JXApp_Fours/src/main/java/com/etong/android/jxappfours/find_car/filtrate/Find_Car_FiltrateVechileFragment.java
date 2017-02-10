package com.etong.android.jxappfours.find_car.filtrate;


import android.os.Bundle;
import android.util.DisplayMetrics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.jxappfours.R;

import com.etong.android.jxappfours.find_car.collect_search.main_content.Find_Car_VechileFragment;

import java.util.ArrayList;

import java.util.List;


/**
 *找车模块中的筛选结果界面的Fragment
 * Created by Administrator on 2016/8/8.
 */
public class Find_Car_FiltrateVechileFragment extends Find_Car_VechileFragment {




    protected float mDensity;
    protected int mWidth;
    protected int mHeight;
    private View view;
    private ImageProvider mImageProvider;
    private String[] list = {"价格","销量"};


    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mImageProvider = ImageProvider.getInstance();
        view = inflater.inflate(R.layout.find_car_base_fragment_frg,
                container, false);
        List<BaseSubscriberFragment> fragmentList=new ArrayList<>();

        Find_Car_FiltrateVechilePriceFragment fragmentA=new Find_Car_FiltrateVechilePriceFragment();
        Find_Car_FiltrateVechileSalesFragment fragmentB=new Find_Car_FiltrateVechileSalesFragment();

        fragmentList.add(fragmentA);
        fragmentList.add(fragmentB);
        getFragment(fragmentList);
        initTopButton(getActivity(),view,list);
//        initView(view);
        return view;
    }

    @Override
    public void processing_data() {
//        super.processing_data();
//        Find_Car_FiltrateFragment.getVechileSeriesData();
    }
}


