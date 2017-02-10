package com.etong.android.jxappfours.models_contrast.main_content;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.models_contrast.adapter.MC_SelectCarTypeAdapter;
import com.etong.android.jxappfours.models_contrast.common.Models_Contrast_AddVechileStyle_Method;
import com.etong.android.jxappfours.models_contrast.common.Models_Contrast_Provider;
import com.etong.android.jxappfours.models_contrast.impl.OnChooseCarTypeListener;
import com.etong.android.jxappfours.models_contrast.impl.OnCloseFragmentListener;
import com.etong.android.jxappfours.models_contrast.impl.OnCloseOnlyoneFragmentListener;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_SelectBrand;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 选择车型   数据处理fragemnt
 * Created by Administrator on 2016/8/15.
 */
public class MC_SelectCarType_typeFragment extends BaseSubscriberFragment {
    private View view;
    private PinnedSectionListView mlistView;        // 悬浮头部的ListView
    //    private ListAdapter<Models_Contrast_VechileType> mListAdapters;
    public static List<Models_Contrast_VechileType> mVechileTypeList = new ArrayList<>();
    private Models_Contrast_Provider mModelsContrasProvider;
    private HttpPublisher mHttpPublisher;

    private MC_SelectCarTypeAdapter adapter;
    List<Models_Contrast_VechileType> SourceDateLists;

    private LinearLayout data_null;
    private Integer vid;
    private RadioButton left_radiobutton;
    private RadioButton right_radiobutton;
    private List<Models_Contrast_VechileType> mVechileOnsellList;
    private List<Models_Contrast_VechileType> mVechileStopList;

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
    public void setOnChooseCarTypeListener(OnChooseCarTypeListener listener) {
        this.mOnChooseCarTypeListener = listener;
    }

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mModelsContrasProvider = Models_Contrast_Provider.getInstance();
        mModelsContrasProvider.initialize(mHttpPublisher);
        view = inflater.inflate(R.layout.fc_select_car_type_content,
                container, false);

        initView(view);
        left_radiobutton.setChecked(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        left_radiobutton.setChecked(true);
    }

    protected void initView(View view) {
        TitleBar mTitleBar = new TitleBar(view);
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

        left_radiobutton = (RadioButton) view.findViewById(R.id.find_car_left_radiobutton);
        right_radiobutton = (RadioButton) view.findViewById(R.id.find_car_right_radiobutton);
        addClickListener(view, R.id.find_car_left_radiobutton);
        addClickListener(view, R.id.find_car_right_radiobutton);

        mlistView = (PinnedSectionListView) view.findViewById(R.id.fc_select_car_type_content_lv);

        // 设置数据为空的ListView显示
        View emptyListView = LayoutInflater.from(getActivity()).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("暂无内容");
        ((ViewGroup) mlistView.getParent()).addView(emptyListView);
        mlistView.setEmptyView(emptyListView);

        adapter = new MC_SelectCarTypeAdapter(getActivity());
        mlistView.setAdapter(adapter);

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long lId) {

                // 如果点击的是悬浮头部， 则不进行点击事件
                if (((Models_Contrast_VechileType) adapter.
                        getItem(position - mlistView.getHeaderViewsCount())).type == Models_Contrast_SelectBrand.SECTION) {
                    return;
                }
//                L.d("++++++>车型对比界面", Models_Contrast_MainActivity.isStart + "");
//                if (Models_Contrast_MainActivity.isStart || FC_CompareCarSelectActivity.isStart) {//车型对比界面和参数对比选车界面
                if (MC_ChooseCarType.IsNeedChecked) {//车型对比界面和参数对比选车界面
                    //得到缓存的信息
                    Map map = Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle();
                    Models_Contrast_Add_VechileStyle info = null;
                    if (map != null) {
                        boolean isHave = false;
                        Iterator it = map.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry entry = (Map.Entry) it.next();
                            Object key = entry.getKey();
                            String id = String.valueOf(key);
                            if (String.valueOf(adapter.getItem(position).getVid()).equals(id)) {
//                                Toast.makeText(getActivity(), "该车型已添加", Toast.LENGTH_SHORT).show();
                                toastMsg("该车型已添加");
                                isHave = true;
                            }
                        }
                        if (!isHave) {

                            Models_Contrast_AddVechileStyle_Method.cartAdd(adapter.getItem(position));
                            // TODO 设置选中的车型到缓存，并关闭当前侧滑页面
                            mOnChooseCarTypeListener.onChooseCarType(adapter.getItem(position));
                            mCloseAllFragment.closeFragmentAllFragment();
                            MC_ChooseCarType.LEVEL = 3;
//                            Models_Contrast_Add_VechileStyle add = new Models_Contrast_Add_VechileStyle();
//                            add.setTitle(adapter.getItem(position).getFullName());
//                            add.setId(adapter.getItem(position).getVid());
//                            Models_Contrast_AddVechileStyle_Method.cartAdd(add);
//                            if (Models_Contrast_MainActivity.isStart) {//车型对比界面
//                                mEventBus.post(add, "add car models");
//                                Models_Contrast_MainActivity.level = 2;
//                                Models_Contrast_MainActivity.drawer_layout.closeDrawer(Gravity.RIGHT);
//                            } else if (FC_CompareCarSelectActivity.isStart) {//参数对比选车界面
//                                mEventBus.post(add, "select car add");
//                                FC_CompareCarSelectActivity.level = 2;
//                                FC_CompareCarSelectActivity.mSelectCarDrawerLayout.closeDrawer(Gravity.RIGHT);
//                            }
                        }
                    } else {

                        Models_Contrast_AddVechileStyle_Method.cartAdd(adapter.getItem(position));

                        // TODO 设置选中的车型到缓存，并关闭当前侧滑页面
                        mOnChooseCarTypeListener.onChooseCarType(adapter.getItem(position));
                        mCloseAllFragment.closeFragmentAllFragment();
                        MC_ChooseCarType.LEVEL = 3;

//                        Models_Contrast_Add_VechileStyle add = new Models_Contrast_Add_VechileStyle();
//                        add.setTitle(adapter.getItem(position).getFullName());
//                        add.setId(adapter.getItem(position).getVid());
//                        Models_Contrast_AddVechileStyle_Method.cartAdd(add);
//                        if (Models_Contrast_MainActivity.isStart) {//车型对比界面
//                            mEventBus.post(add, "add car models");
//                            Models_Contrast_MainActivity.level = 2;
//                            Models_Contrast_MainActivity.drawer_layout.closeDrawer(Gravity.RIGHT);
//                        } else if (FC_CompareCarSelectActivity.isStart) {//参数对比选车界面
//                            mEventBus.post(add, "select car add");
//                            FC_CompareCarSelectActivity.level = 2;
//                            FC_CompareCarSelectActivity.mSelectCarDrawerLayout.closeDrawer(Gravity.RIGHT);
//                        }
                    }
//                    L.d("------------------->", "车型对比" + Models_Contrast_MainActivity.isStart + "          " + "参数对比选车" + FC_CompareCarSelectActivity.isStart);
                }else {
                    // TODO 设置选中的车型到缓存，并关闭当前侧滑页面
                    mOnChooseCarTypeListener.onChooseCarType(adapter.getItem(position));
                    mCloseAllFragment.closeFragmentAllFragment();
                    MC_ChooseCarType.LEVEL = 3;
                }

//                  } else if (Fours_Order_OrderActivity.isStart) {//预约界面
//                    mEventBus.post(adapter.getItem(position), "selectCarResult");
//                    Fours_Order_OrderActivity.level = 2;
//                    Fours_Order_OrderActivity.mOrderDrawerLayout.closeDrawer(Gravity.RIGHT);
//                    L.d("====================>", "预约");
//                } else if (Fours_MyCar_AddActivity.isStart) {//添加爱车界面
//                    mEventBus.post(adapter.getItem(position), "selectMyCarResult");
//                    Fours_MyCar_AddActivity.level = 2;
//                    Fours_MyCar_AddActivity.mMyCarDrawerLayout.closeDrawer(Gravity.RIGHT);
//                    L.d("====================>", "添加爱车");
//                }
            }
        });
    }

    @Subscriber(tag = FrameHttpTag.VECHILE_TYPE)
    protected void getVechileTypeData(HttpMethod method) {
        adapter.clear();
        SourceDateLists = new ArrayList<Models_Contrast_VechileType>();
        mVechileOnsellList = new ArrayList<>();//在售
        mVechileStopList = new ArrayList<>();//停售
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        String errCode= method.data().getString("errCode");

        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                Models_Contrast_VechileType mModels_Contrast_VechileType = JSON.toJavaObject(jsonArr.getJSONObject(i), Models_Contrast_VechileType.class);

                String tempTitle;
                String tempFullName;
                tempTitle = mModels_Contrast_VechileType.getTitle();
                tempFullName = mModels_Contrast_VechileType.getFullName();
                if(tempTitle.contains("null款")){
                    mModels_Contrast_VechileType.setTitle(tempTitle.replace("null款",""));
                }
                if(tempFullName.contains("null款")){
                    mModels_Contrast_VechileType.setFullName(tempFullName.replace("null款",""));
                }

                if (mModels_Contrast_VechileType.getSalestatus().equals("在售")) {
                    mVechileOnsellList.add(mModels_Contrast_VechileType);


                } else if (mModels_Contrast_VechileType.getSalestatus().equals("停售")) {
                    mVechileStopList.add(mModels_Contrast_VechileType);

                }
            }
            Collections.sort(mVechileOnsellList, new MyComp());
            Collections.sort(mVechileStopList, new MyComp());
            //页面一进来就把在售数据加到适配器里
            left_radiobutton.setChecked(true);
            adapter.updateListView(mVechileOnsellList);
        }
    }

    public class MyComp implements Comparator<Models_Contrast_VechileType> {
        @Override
        public int compare(Models_Contrast_VechileType o1, Models_Contrast_VechileType o2) {
            return o1.getOutputVol().compareTo(o2.getOutputVol());
        }
    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.find_car_left_radiobutton) {

            mlistView.setAdapter(adapter);
            if (null != mVechileStopList) {
                adapter.updateListView(mVechileOnsellList);
            }


        } else if (view.getId() == R.id.find_car_right_radiobutton) {

            mlistView.setAdapter(adapter);
            if (null != mVechileStopList) {
                adapter.updateListView(mVechileStopList);
            }


        }
    }

    @Subscriber(tag = "car type")
    protected void getCarType(Integer id) {
        vid = id;
        mModelsContrasProvider.getVechileType(vid);//请求车型列表页
    }
}