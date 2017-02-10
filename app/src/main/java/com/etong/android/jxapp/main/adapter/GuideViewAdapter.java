package com.etong.android.jxapp.main.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author : by sunyao
 * @ClassName : GuideViewAdapter
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/12/5 - 14:47
 */

public class GuideViewAdapter extends PagerAdapter {

    private List<View> list;

    public GuideViewAdapter(List<View> list) {
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(list.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

}