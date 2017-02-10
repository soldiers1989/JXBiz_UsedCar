package com.etong.android.jxappfours.find_car.grand.hotkey;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.bean.HotKeyBean;
import com.etong.android.jxappfours.find_car.grand.carset.FC_CarsetActivity;

import java.util.ArrayList;

/**
 * Created by Ellison.Sun on 2016/8/16.
 *
 * 以销售量排序的Fragment
 */
public class Find_Car_FilteSalesFrg extends BaseSubscriberFragment {

    private View view;
    private ListView mlistView;
    private ListAdapter<HotKeyBean> mListAdapters;
    private ImageProvider mImageProvider;
    // 获取到的数据
    private ArrayList<HotKeyBean> mDatas;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.find_car_collect_list_frg,
                container, false);
        mImageProvider = ImageProvider.getInstance();
        initData();
        initView(view);
        return view;
    }

    /**
     * 初始化数据，从网络上获取
     */
    private void initData() {
        mDatas = new ArrayList<HotKeyBean>();

        for (int i=0; i<20; i++) {

            HotKeyBean bean1 = new HotKeyBean();
            bean1.setId(i);
            bean1.setImageUrl("http://113.247.237.98:10002/data//car/20160324/c4f7db1f-7284-4bf5-a2ae-f2b592b11556.png");
            bean1.setStrPrice("10.12-24.98万");
            bean1.setCarTitle("东风风神");
            mDatas.add(bean1);
        }
    }

    protected void initView(View view) {
        mlistView = (ListView) view.findViewById(R.id.find_car_lv_collect);

        mListAdapters = new ListAdapter<HotKeyBean>(
                getActivity(), R.layout.find_car_hot_key_list_item) {

            @Override
            protected void onPaint(View view, HotKeyBean data, int position) {

                ImageView iv = (ImageView) view.findViewById(R.id.hot_key_img_pic);
                TextView title = (TextView) view.findViewById(R.id.hot_key_item_title);
                TextView price = (TextView) view.findViewById(R.id.hot_key_item_price);

                mImageProvider.loadImage(iv, data.getImageUrl());
                title.setText(data.getCarTitle());
                price.setText(data.getStrPrice());
            }

        };

        // Item的点击事件
        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 进入车系界面
//                Toast.makeText(getActivity(), "进入车系界面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), FC_CarsetActivity.class);
                startActivity(intent);
            }
        });

        mlistView.setAdapter(mListAdapters);
        mListAdapters.addAll(mDatas);
        mListAdapters.notifyDataSetChanged();
    }
}

