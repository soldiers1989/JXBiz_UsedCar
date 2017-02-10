package com.etong.android.jxappfours.find_car.grand.hotkey;

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
 * Created by Ellison.Sun on 2016/8/16.
 *
 * 热点点击进去包含的Fragment
 */
public class Find_Car_HotkeyFrg extends Find_Car_VechileFragment {

    protected float mDensity;
    protected int mWidth;
    protected int mHeight;
    private View view;
    private ImageProvider mImageProvider;
    private String[] list = {"价格", "销量"};


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
        List<BaseSubscriberFragment> fragmentList = new ArrayList<>();

        Find_Car_FiltePriceFrg fragmentA = new Find_Car_FiltePriceFrg();
        Find_Car_FilteSalesFrg fragmentB = new Find_Car_FilteSalesFrg();

        fragmentList.add(fragmentA);
        fragmentList.add(fragmentB);
        getFragment(fragmentList);
        initTopButton(getActivity(), view, list);

        return view;
    }

    /**
     * 获取到数据
     */
    @Override
    public void processing_data() {

    }
//
//        @Override
//        public void processing_data() {
//        super.processing_data();
//            FindCarFiltrateFragment.getVechileSeriesData();
//        }
}
