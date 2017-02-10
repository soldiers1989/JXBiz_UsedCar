package com.etong.android.jxbizusedcar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.adapter.UC_MoreListAdapter;
import com.etong.android.jxbizusedcar.bean.UC_MoreJsonData;
import com.etong.android.jxbizusedcar.bean.UC_MoreTitleBeam;

import java.util.ArrayList;
import java.util.List;


/**
 * "更多"子模块的主Fragment界面
 * Created by Administrator on 2016/8/2 0002.
 */
public class UC_MoreMainFragment extends BaseSubscriberFragment {

    private ListView moreListview;
    private UC_MoreListAdapter moreListAdapter;
    private TitleBar mTitleBar;
    private List<UC_MoreTitleBeam> mList = new ArrayList<>();

    private boolean isShow = false;
    public static boolean isOnResume = false;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.uc_more_content_frg,
                container, false);
        mTitleBar = new TitleBar(view);
        mTitleBar.showBottomLin(false);
        mTitleBar.showBackButton(false);
        mTitleBar.setTitleTextColor("#ffffff");//设置title颜色
        mTitleBar.setmTitleBarBackground("#cf1c36");//设置titlebar背景色
        mTitleBar.setTitle("更多");

        initView(view);
        isShow = true;    // 是否显示

        return view;
    }

    private void initView(View view) {
        moreListview = findViewById(view, R.id.uc_more_lv_content, ListView.class);
        initData();
        initListView();
    }

    public void initData() {
        mList.clear();
        JSONArray array = JSONArray.parseArray(UC_MoreJsonData.getJsonData());
        for (int i = 0; i < array.size(); i++) {
            UC_MoreTitleBeam mTitleBeam = JSON.toJavaObject(
                    array.getJSONObject(i), UC_MoreTitleBeam.class);
            mList.add(mTitleBeam);
        }
    }

    private void initListView() {
        moreListAdapter = new UC_MoreListAdapter(getActivity(), mList);
        moreListview.setAdapter(moreListAdapter);
        moreListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isShow) {
            isOnResume = true;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);

        if (!hidden) {
            if (isShow) {
                if (isOnResume) {
                    isOnResume = false;
                } else {
                    initData();
                    moreListview.setFocusable(true);
                    moreListAdapter.updateListView(mList);
//            moreListAdapter.notifyDataSetChanged();
                    moreListview.setSelection(0);
//            moreListAdapter = null;
//            initListView();
                }
            }
        }

    }
}
