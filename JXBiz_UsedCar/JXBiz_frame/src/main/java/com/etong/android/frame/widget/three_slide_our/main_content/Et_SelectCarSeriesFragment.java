package com.etong.android.frame.widget.three_slide_our.main_content;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.R;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.frame.widget.three_slide_our.adapter.Et_SelectVechileSeriesAdapter;
import com.etong.android.frame.widget.three_slide_our.impl.Et_OnChooseCarSeriesListener;
import com.etong.android.frame.widget.three_slide_our.impl.Et_OnCloseFragmentListener;
import com.etong.android.frame.widget.three_slide_our.impl.Et_OnCloseOnlyoneFragmentListener;
import com.etong.android.frame.widget.three_slide_our.javabean.Et_SelectBrand;
import com.etong.android.frame.widget.three_slide_our.javabean.Et_VechileSeries;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;


/**
 * 选择车系
 * Created by Administrator on 2016/8/12.
 */
public class Et_SelectCarSeriesFragment extends BaseSubscriberFragment {

    private UC_UserProvider mModelsContrasProvider;
    private HttpPublisher mHttpPublisher;
    private Et_SelectVechileSeriesAdapter adapter;
    public static DrawerLayout drawer_layout_type;
    private List<Et_VechileSeries> SourceDateLists;
    private TextView title;

    private View view;
    private PinnedSectionListView car_series_list;      // 悬浮头部的ListView
    private Integer sid;
    private String selectBrandName;

    // 添加的对外接口
    private Et_OnCloseFragmentListener mOnCloseFragmentListener;
    private Et_OnChooseCarSeriesListener mOnChooseCarSeriesListener;
    private Et_OnCloseOnlyoneFragmentListener mCloseOnlyone;

    // 设置关闭按钮
    public void setOnCloseFragmentListener(Et_OnCloseFragmentListener listener) {
        this.mOnCloseFragmentListener = listener;
    }

    // 设置选择某一个车系的监听事件
    public void setOnChooseCarSeriesListener(Et_OnChooseCarSeriesListener listener) {
        this.mOnChooseCarSeriesListener = listener;
    }

    // 设置选择收藏的监听事件
    public void setEt_OnCloseOnlyoneFragmentListener(Et_OnCloseOnlyoneFragmentListener listener) {
        this.mCloseOnlyone = listener;
    }


    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.models_contrast_select_car_series_frg,
                container, false);
        initViews(view);
        return view;
    }

    /**
     * @param
     * @return
     * @desc (初始化操作)
     * @user sunyao
     * @createtime 2016/9/27 - 19:11
     */
    private void initViews(View view) {
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mModelsContrasProvider = UC_UserProvider.getInstance();
        mModelsContrasProvider.initalize(mHttpPublisher);
        TitleBar mTitleBar = new TitleBar(view);
        mTitleBar.setTitle("选择车系");
        mTitleBar.showBackButton(true);
        mTitleBar.showNextButton(true);
        mTitleBar.setNextButton("关闭");
        mTitleBar.setNextTextColor("#cf1c36");
        mTitleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO  设置关闭所有的侧滑界面
                mOnCloseFragmentListener.closeFragmentAllFragment();
            }
        });
        mTitleBar.setBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();
                //  TODO 设置TitleBar中的返回键，关闭当前一个按钮
                mCloseOnlyone.closeOnlyOneFragment();
            }
        });

        drawer_layout_type = (DrawerLayout) view.findViewById(R.id.models_contrast_drawer_layout_cartype);
        drawer_layout_type.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);

        // 注释抽屉的监听事件
//        initDrawerLayoutData();
        car_series_list = (PinnedSectionListView) view.findViewById(R.id.models_contrast_lv_car_series_list);

        // 设置数据为空的ListView显示
        View emptyListView = LayoutInflater.from(getActivity()).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("数据请求失败，点击重试");
        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //刷新数据
                mModelsContrasProvider.getCarset(sid);
            }
        });
        ((ViewGroup) car_series_list.getParent()).addView(emptyListView);
        car_series_list.setEmptyView(emptyListView);


        // PinnedSectionListView的点击事件
        car_series_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 如果点击的是悬浮头部， 则不进行点击事件
                if (((Et_VechileSeries) adapter.
                        getItem(position - car_series_list.getHeaderViewsCount())).type == Et_SelectBrand.SECTION) {
                    return;
                }
//                mEventBus.post(((Et_VechileSeries) adapter.
//                        getItem(position - car_series_list.getHeaderViewsCount())).getId(), "give car type");

                // TODO 点击车系中的某一个Item传送数据、打开侧滑界面
                Et_VechileSeries newSelectSeries = new Et_VechileSeries();
                newSelectSeries.setId(((Et_VechileSeries)
                        adapter.getItem(position - car_series_list.getHeaderViewsCount())).getId());
                newSelectSeries.setName(selectBrandName +
                        ((Et_VechileSeries) adapter.getItem(position - car_series_list.getHeaderViewsCount())).getName());
                mOnChooseCarSeriesListener.onChooseCarSeries(newSelectSeries);
//                mOnChooseCarSeriesListener.onChooseCarSeries(((Et_VechileSeries) adapter.
//                        getItem(position - car_series_list.getHeaderViewsCount())).getId());
                L.d("点击的车系为：", ((Et_VechileSeries) adapter.
                        getItem(position - car_series_list.getHeaderViewsCount())).getName() + " " + position);
            }
        });

        adapter = new Et_SelectVechileSeriesAdapter(getActivity());
        car_series_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Subscriber(tag = UC_HttpTag.GET_CARSET_DETAIL_BY_GRAND_ID)
    protected void getVechileSeriesData(HttpMethod method) {
        car_series_list.setVisibility(View.VISIBLE);
        adapter.clear();
        SourceDateLists = new ArrayList<Et_VechileSeries>();
        String errno = method.data().getString("errno");
        String status = method.data().getString("status");
        String msg = method.data().getString("msg");
        if (!TextUtils.isEmpty(status) && status.equals("true")) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (Object o : jsonArr) {
                JSONObject root = (JSONObject) o;

                Et_VechileSeries mSeries = new Et_VechileSeries();
                mSeries.setId(root.getInteger("id"));
                mSeries.setName(root.getString("name"));
                SourceDateLists.add(mSeries);
            }
            adapter.updateListView(SourceDateLists);
            adapter.notifyDataSetChanged();
        }
    }

    @Subscriber(tag = "car serice")
//    protected void getCarSerice(Integer id) {
    protected void getCarSerice(Et_SelectBrand selectBrand) {
        sid = Integer.valueOf(selectBrand.getF_carbrandid());
        selectBrandName = selectBrand.getF_brand();
        car_series_list.setVisibility(View.GONE);
        mModelsContrasProvider.getCarset(sid);
    }
}
