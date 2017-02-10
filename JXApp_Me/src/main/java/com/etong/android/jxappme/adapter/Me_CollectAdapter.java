package com.etong.android.jxappme.adapter;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.etong.android.frame.utils.L;

import java.util.List;

/**
 * @desc 收藏 viewpager适配器
 * @createtime 2016/11/9 - 12:51
 * @Created by xiaoxue.
 */
public class Me_CollectAdapter extends FragmentPagerAdapter {

    private List<Fragment> mViews;
    private List<String> mtitleList;
    public FragmentManager fm;

    public Me_CollectAdapter(FragmentManager fm, List<Fragment> mViews, List<String> mtitleList) {
        super(fm);
        this.fm = fm;
        this.mViews = mViews;
        this.mtitleList = mtitleList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        L.d("--------------UC_OrderViewPagerAdapter", "getItem");
        fragment = mViews.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("id", "" + position);
        fragment.setArguments(bundle);
        return fragment;
//        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mViews.size();
    }


    //配置标题的方法
    @Override
    public CharSequence getPageTitle(int position) {
        return mtitleList.get(position);
    }

    //设置记住状态
    @Override
    public Fragment instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container,
                position);
        fm.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        Fragment fragment = mViews.get(position);
        fm.beginTransaction().hide(fragment).commit();
    }
}
