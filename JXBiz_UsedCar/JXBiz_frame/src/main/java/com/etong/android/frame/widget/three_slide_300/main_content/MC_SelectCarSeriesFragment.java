package com.etong.android.frame.widget.three_slide_300.main_content;

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
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.frame.widget.three_slide_300.adapter.MC_SelectVechileSeriesAdapter;
import com.etong.android.frame.widget.three_slide_300.impl.OnChooseCarSeriesListener;
import com.etong.android.frame.widget.three_slide_300.impl.OnCloseFragmentListener;
import com.etong.android.frame.widget.three_slide_300.impl.OnCloseOnlyoneFragmentListener;
import com.etong.android.frame.widget.three_slide_300.javabean.Models_Contrast_SelectBrand;
import com.etong.android.frame.widget.three_slide_300.javabean.Models_Contrast_VechileSeries;
import com.etong.android.frame.utils.L;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;


/**
 * 选择车系
 * Created by Administrator on 2016/8/12.
 */
public class MC_SelectCarSeriesFragment extends BaseSubscriberFragment {

    private UC_UserProvider mModelsContrasProvider;
    private HttpPublisher mHttpPublisher;
    private MC_SelectVechileSeriesAdapter adapter;
    public static DrawerLayout drawer_layout_type;
    private List<Models_Contrast_VechileSeries> SourceDateLists;
    private TextView title;

    private View view;
    private PinnedSectionListView car_series_list;      // 悬浮头部的ListView
    private Integer sid;

    // 添加的对外接口
    private OnCloseFragmentListener mOnCloseFragmentListener;
    private OnChooseCarSeriesListener mOnChooseCarSeriesListener;
    private OnCloseOnlyoneFragmentListener mCloseOnlyone;
    // 设置关闭按钮
    public void setOnCloseFragmentListener(OnCloseFragmentListener listener) {
        this.mOnCloseFragmentListener = listener;
    }
    // 设置选择某一个车系的监听事件
    public void setOnChooseCarSeriesListener(OnChooseCarSeriesListener listener) {
        this.mOnChooseCarSeriesListener = listener;
    }
    // 设置选择收藏的监听事件
    public void setOnCloseOnlyoneFragmentListener(OnCloseOnlyoneFragmentListener listener) {
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
     * @desc (初始化操作)
     * @user sunyao
     * @createtime 2016/9/27 - 19:11
     *
     * @param
     * @return
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

//                if (Models_Contrast_MainActivity.isStart) {//车型对比界面
//                    Models_Contrast_MainActivity.level = 0;
//                    Models_Contrast_MainActivity.drawer_layout_carseries.closeDrawer(Gravity.RIGHT);//右边
//                    Models_Contrast_MainActivity.drawer_layout.closeDrawer(Gravity.RIGHT);
//                } else if (Fours_Order_OrderActivity.isStart) {//预约界面
//                    Fours_Order_OrderActivity.level = 0;
//                    Fours_Order_OrderActivity.drawer_layout_carseries.closeDrawer(Gravity.RIGHT);//右边
//                    Fours_Order_OrderActivity.mOrderDrawerLayout.closeDrawer(Gravity.RIGHT);
//                } else if (Fours_MyCar_AddActivity.isStart) {//添加爱车界面
//                    Fours_MyCar_AddActivity.level = 0;
//                    Fours_MyCar_AddActivity.drawer_layout_carseries.closeDrawer(Gravity.RIGHT);//右边
//                    Fours_MyCar_AddActivity.mMyCarDrawerLayout.closeDrawer(Gravity.RIGHT);
//                } else if (FC_CompareCarSelectActivity.isStart) {//参数对比选车界面
//                    FC_CompareCarSelectActivity.level = 0;
//                    FC_CompareCarSelectActivity.drawer_layout_carseries.closeDrawer(Gravity.RIGHT);//右边
//                    FC_CompareCarSelectActivity.mSelectCarDrawerLayout.closeDrawer(Gravity.RIGHT);
//                }

            }
        });
        mTitleBar.setBackOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();
                //  TODO 设置TitleBar中的返回键，关闭当前一个按钮
                mCloseOnlyone.closeOnlyOneFragment();

                /*if (Models_Contrast_MainActivity.isStart) {//车型对比界面
                    Models_Contrast_MainActivity.level = 0;
                    Models_Contrast_MainActivity.drawer_layout_carseries.closeDrawer(Gravity.RIGHT);//右边
                } else if (Fours_Order_OrderActivity.isStart) {//预约界面
                    Fours_Order_OrderActivity.level = 0;
                    Fours_Order_OrderActivity.drawer_layout_carseries.closeDrawer(Gravity.RIGHT);//右边
                } else if (Fours_MyCar_AddActivity.isStart) {//添加爱车界面
                    Fours_MyCar_AddActivity.level = 0;
                    Fours_MyCar_AddActivity.drawer_layout_carseries.closeDrawer(Gravity.RIGHT);//右边
                } else if (FC_CompareCarSelectActivity.isStart) {//参数对比选车界面
                    FC_CompareCarSelectActivity.level = 0;
                    FC_CompareCarSelectActivity.drawer_layout_carseries.closeDrawer(Gravity.RIGHT);//右边
                }*/
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
                mModelsContrasProvider.getVechileSeries(sid);
            }
        });
        ((ViewGroup) car_series_list.getParent()).addView(emptyListView);
        car_series_list.setEmptyView(emptyListView);


        // PinnedSectionListView的点击事件
        car_series_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 如果点击的是悬浮头部， 则不进行点击事件
                if (((Models_Contrast_VechileSeries) adapter.
                        getItem(position - car_series_list.getHeaderViewsCount())).type == Models_Contrast_SelectBrand.SECTION) {
                    return;
                }
//                mEventBus.post(((Models_Contrast_VechileSeries) adapter.
//                        getItem(position - car_series_list.getHeaderViewsCount())).getId(), "give car type");

                // TODO 点击车系中的某一个Item传送数据、打开侧滑界面
                mOnChooseCarSeriesListener.onChooseCarSeries(Integer.parseInt(((Models_Contrast_VechileSeries) adapter.
                        getItem(position - car_series_list.getHeaderViewsCount())).getSeries_id()));
                L.d("点击的车系为：", ((Models_Contrast_VechileSeries) adapter.
                        getItem(position - car_series_list.getHeaderViewsCount())).getSeries_name() + " " + position);
            }
        });

        adapter = new MC_SelectVechileSeriesAdapter(getActivity());
        car_series_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Subscriber(tag = UC_HttpTag.HTTP_SERIES_BY_300)
    protected void getVechileSeriesData(HttpMethod method) {
        car_series_list.setVisibility(View.VISIBLE);
        adapter.clear();
        SourceDateLists = new ArrayList<Models_Contrast_VechileSeries>();
        String errno = method.data().getString("errno");
        String status = method.data().getString("status");
        String msg = method.data().getString("msg");
        if (!TextUtils.isEmpty(status)&& status.equals("true")) {
            JSONArray jsonArr = method.data().getJSONObject("data").getJSONArray("series_list");
            for (Object o : jsonArr) {
                JSONObject root = (JSONObject) o;

                Models_Contrast_VechileSeries mSeries = new Models_Contrast_VechileSeries();
                mSeries.setUpdate_time(root.getString("update_time"));
                mSeries.setSeries_group_name(root.getString("series_group_name"));
                mSeries.setSeries_name(root.getString("series_name"));
                mSeries.setMaker_type(root.getString("maker_type"));
                mSeries.setSeries_id(root.getString("series_id"));
                SourceDateLists.add(mSeries);
//                mSeries.setRoot(true);
//                adapter.add(mSeries);
//                JSONArray carsetList = root.getJSONArray("carsetList");
//                for (Object carJson : carsetList) {
//                    Models_Contrast_VechileSeries car = JSON.toJavaObject((JSONObject) carJson,
//                            Models_Contrast_VechileSeries.class);
//                    SourceDateLists.add(car);
//                }
            }
            adapter.updateListView(SourceDateLists);
            adapter.notifyDataSetChanged();
        }
        /*else {
//            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            toastMsg(msg);
        }*/
    }

    @Subscriber(tag = "car serice")
    protected void getCarSerice(Integer id) {
        sid = id;
        car_series_list.setVisibility(View.GONE);
        mModelsContrasProvider.getVechileSeries(sid);
    }
}
