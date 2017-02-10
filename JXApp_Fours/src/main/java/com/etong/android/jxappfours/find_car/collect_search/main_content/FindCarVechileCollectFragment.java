package com.etong.android.jxappfours.find_car.collect_search.main_content;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.utils.L;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.collect_search.utils.Find_Car_VechileCollect_Method;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * 收藏的fragment
 * Created by Administrator on 2016/8/8.
 */
public class FindCarVechileCollectFragment extends Find_Car_VechileFragment {
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

        Find_Car_VechileCollectPriceFragment fragmentPrice=new Find_Car_VechileCollectPriceFragment();
        Find_Car_VechileCollectSalesFragment fragmentSales=new Find_Car_VechileCollectSalesFragment();

        fragmentList.add(fragmentPrice);
        fragmentList.add(fragmentSales);
        getFragment(fragmentList);
        initTopButton(getActivity(),view,list);
//        initView(view);
        return view;
    }

    @Override
    public void processing_data() {
//        super.processing_data();
        getCacheData();
    }

    public static void getCacheData(){
        //得到缓存的收藏车辆
        List<Models_Contrast_VechileType> mList=new ArrayList<Models_Contrast_VechileType>();
        Map map = Find_Car_VechileCollect_Method.getVechileCollectInfo();
        Models_Contrast_VechileType info = null;
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                String data =  String.valueOf(map.get(Integer.valueOf(String.valueOf(key))));
                L.d("从本地获取到的数据:", data);
                info = JSON.parseObject(data, Models_Contrast_VechileType.class);
                if(null!=info){
                    mList.add(info);
                }
            }
        }
        EventBus.getDefault().post(mList,"listInfo");
    }

    @Override
    public void onResume() {
        super.onResume();
        getCacheData();
    }
}
