package com.etong.android.frame.widget.three_slide_300.main_content;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.R;
import com.etong.android.frame.widget.three_slide_300.common.Models_Contrast_AddVechileStyle_Method;
import com.etong.android.frame.widget.three_slide_300.impl.OnChooseCarTypeListener;
import com.etong.android.frame.widget.three_slide_300.impl.OnCloseFragmentListener;
import com.etong.android.frame.widget.three_slide_300.impl.OnCloseOnlyoneFragmentListener;
import com.etong.android.frame.widget.three_slide_300.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.frame.widget.three_slide_300.javabean.Models_Contrast_VechileType;
import com.etong.android.frame.widget.three_slide_300.utils.Find_Car_VechileCollect_Method;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 车型对比  点击收藏的fragment
 * Created by Administrator on 2016/8/12.
 */
public class MC_CollectFragment extends BaseSubscriberFragment {

    private ListView brand_list_view;
    private UC_UserProvider mModelsContrasProvider;
    private HttpPublisher mHttpPublisher;

    private TextView txt_collect;
    public static DrawerLayout drawer_collect;
    private ListView collect_list;
    private ListAdapter<Models_Contrast_VechileType> mListAdapters;
    private LinearLayout data_null;


    // 添加的对外接口
    private OnCloseOnlyoneFragmentListener mOnCloseOneFragment;
    private OnCloseFragmentListener mCloseAllFragment;
    private OnChooseCarTypeListener mOnChooseCarTypeListener;

    // 设置关闭按钮
    public void setOnCloseOneFragmentListener(OnCloseOnlyoneFragmentListener listener) {
        this.mOnCloseOneFragment = listener;
    }
    // 设置点击关闭所有侧滑界面
    public void setOnCloseAllFragmentListener(OnCloseFragmentListener listener) {
        this.mCloseAllFragment = listener;
    }
    // 设置选择某一个车系的监听事件
    public void setOnChooseCarSeriesListener(OnChooseCarTypeListener listener) {
        this.mOnChooseCarTypeListener = listener;
    }


    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.models_contrast_collect_frg,
                container, false);
        initViews(view);
        return view;
    }

    @Subscriber(tag = "go to collect fragment")
    protected void getDataCollect() {

    }

    private void initViews(View view) {
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mModelsContrasProvider = UC_UserProvider.getInstance();
        mModelsContrasProvider.initalize(mHttpPublisher);
        final TitleBar mTitleBar = new TitleBar(view);
        mTitleBar.setTitle("选择车型");
        mTitleBar.showBackButton(true);
        mTitleBar.showNextButton(true);
        mTitleBar.setNextButton("关闭");
        mTitleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO  设置关闭所有的侧滑界面
                mCloseAllFragment.closeFragmentAllFragment();
            }
        });

        mTitleBar.setBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  TODO 设置TitleBar中的返回键，关闭当前一个按钮
                mOnCloseOneFragment.closeOnlyOneFragment();
            }
        });


        collect_list = (ListView) view.findViewById(R.id.models_contrast_lv_collect_list);
        data_null = (LinearLayout) view.findViewById(R.id.models_contrast_ll_null);

        mListAdapters = new ListAdapter<Models_Contrast_VechileType>(
                getActivity(), R.layout.models_contrast_collect_list_item) {

            @Override
            protected void onPaint(View view, final Models_Contrast_VechileType data, int position) {

                final TextView collect_title = (TextView) view.findViewById(R.id.models_contrast_txt_collect_title);
                collect_title.setText(data.getShort_name());

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        if (Models_Contrast_MainActivity.isStart || FC_CompareCarSelectActivity.isStart) {//车型对比界面和参数对比选车界面
                        if (MC_ChooseCarType.IsNeedChecked) {//车型对比界面和参数对比选车界面
                            Map map = Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle();
                            Models_Contrast_Add_VechileStyle info = null;
                            if (map != null) {
                                boolean isHave = false;
                                Iterator it = map.entrySet().iterator();
                                while (it.hasNext()) {
                                    Map.Entry entry = (Map.Entry) it.next();
                                    Object key = entry.getKey();
                                    String id = String.valueOf(key);
                                    if (String.valueOf(data.getShort_name()).equals(id)) {
//                                        Toast.makeText(getActivity(), "该车型已添加", Toast.LENGTH_SHORT).show();
                                        toastMsg("该车型已添加");
                                        isHave = true;
                                    }
                                }
                                if (!isHave) {
                                    Models_Contrast_AddVechileStyle_Method.cartAdd(data);
                                    // TODO 设置选中的车型到缓存，并关闭当前侧滑页面
                                    mOnChooseCarTypeListener.onChooseCarType(data);
                                    mCloseAllFragment.closeFragmentAllFragment();
                                }
                            } else {
                                Models_Contrast_AddVechileStyle_Method.cartAdd(data);

                                // TODO 设置选中的车型到缓存，并关闭当前侧滑页面
                                mOnChooseCarTypeListener.onChooseCarType(data);
                                mCloseAllFragment.closeFragmentAllFragment();
                            }
                        } else {
                            // TODO 设置选中的车型到缓存，并关闭当前侧滑页面
                            mOnChooseCarTypeListener.onChooseCarType(data);
                            mCloseAllFragment.closeFragmentAllFragment();
                        }
                    }
                });

            }
        };
        collect_list.setAdapter(mListAdapters);
        mListAdapters.notifyDataSetChanged();

        //得到缓存的收藏车辆
        List<Models_Contrast_VechileType> mList = new ArrayList<Models_Contrast_VechileType>();
        Map map = Find_Car_VechileCollect_Method.getVechileCollectInfo();
        Models_Contrast_VechileType info = null;
        if (map != null) {
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                String data = String.valueOf(map.get(Integer.valueOf(String.valueOf(key))));
                L.d("从本地获取到的数据:", data);
                info = JSON.parseObject(data, Models_Contrast_VechileType.class);
                if (null != info) {
                    mList.add(info);
                }
            }
        }
        if (mList.size() == 0) {
            collect_list.setVisibility(View.GONE);
            data_null.setVisibility(View.VISIBLE);
        } else {
            collect_list.setVisibility(View.VISIBLE);
            data_null.setVisibility(View.GONE);
            mListAdapters.addAll(mList);
        }

    }
}
