package com.etong.android.jxappme.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;
import com.etong.android.jxappme.R;
import com.etong.android.jxappme.adapter.Me_CollectAdapter;
import com.etong.android.jxappme.fragment.MeNewCarFragment;
import com.etong.android.jxappme.fragment.MeUsedCarFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (收藏的activity)
 * @createtime 2016/12/5 - 20:32
 * @author xiaoxue
 */
public class MeCollectActivity extends BaseSubscriberActivity {
    private TitleBar mTitleBar;
    private ListAdapter<Models_Contrast_VechileType> mListAdapters;
    private TabLayout tab_collect;
    private ViewPager viewpager_content;
    private Me_CollectAdapter mViewPagerAdapter;
    private List<Fragment> mFragments;
    private List<String> titleList;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_me_collect);

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("收藏");
        mTitleBar.showBottomLin(false);

        initView();
        initData();
    }

    protected void initView() {
        tab_collect =findViewById(R.id.me_car_tl_collect,TabLayout.class);
        viewpager_content =findViewById(R.id.me_car_vp_content, ViewPager.class);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mFragments = new ArrayList<>();       //存放4个fragment的list
        titleList = new ArrayList<>();    //存放title的list
        titleList.add("新车");
        titleList.add("二手车");
        //添加fragment
        mFragments.add(new MeNewCarFragment());
        mFragments.add(new MeUsedCarFragment());

        //viewpager 适配器
        mViewPagerAdapter = new Me_CollectAdapter(getSupportFragmentManager(), mFragments, titleList);

        viewpager_content.setAdapter(mViewPagerAdapter);
        //和ViewPager联动起来
        tab_collect.setupWithViewPager(viewpager_content);
        tab_collect.setTabsFromPagerAdapter(mViewPagerAdapter);

        // 设置Tab的选择监听
        tab_collect.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 每当我们选择了一个Tab就将ViewPager滚动至对应的Page
                viewpager_content.setCurrentItem(tab.getPosition(), true);
                switch (tab.getPosition()) {
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
