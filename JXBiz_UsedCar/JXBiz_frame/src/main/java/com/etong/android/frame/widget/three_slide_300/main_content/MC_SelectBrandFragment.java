package com.etong.android.frame.widget.three_slide_300.main_content;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.ClearEditText;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.frame.widget.sortlistview.CharacterParser;
import com.etong.android.frame.widget.sortlistview.EtongSideBar;
import com.etong.android.frame.R;
import com.etong.android.frame.widget.three_slide_300.adapter.MC_SelectBrandAdapter;
import com.etong.android.frame.widget.three_slide_300.common.Models_Contrast_PinyinComparator;
import com.etong.android.frame.widget.three_slide_300.impl.OnChooseBrandListener;
import com.etong.android.frame.widget.three_slide_300.impl.OnChooseCollectListener;
import com.etong.android.frame.widget.three_slide_300.impl.OnCloseFragmentListener;
import com.etong.android.frame.widget.three_slide_300.javabean.Models_Contrast_SelectBrand;


import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 选择品牌fragment
 * Created by Administrator on 2016/8/12.
 */
public class MC_SelectBrandFragment extends BaseSubscriberFragment {

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<Models_Contrast_SelectBrand> SourceDateList;
    /*
    * 根据拼音来排列ListView里面的数据类
    */
    private Models_Contrast_PinyinComparator pinyinComparator;
    private ClearEditText mClearEditText;
    private EtongSideBar sideBar;

    private PinnedSectionListView brand_list_view;              // 悬浮头部的ListView
    private UC_UserProvider mModelsContrasProvider;
    private HttpPublisher mHttpPublisher;
    private MC_SelectBrandAdapter adapter;      // 选择品牌界面中的悬浮标题的适配器
    public static DrawerLayout drawer_layout_carseries;
    private TextView txt_collect;
    private FrameLayout collectFrameLayout;
    private FrameLayout carseriesFrameLayout;
    private TextView dialog;

    // 添加的对外接口
    private OnCloseFragmentListener mOnCloseFragmentListener;
    private OnChooseBrandListener mOnChooseBrandListener;
    private OnChooseCollectListener mOnChooseCollectListener;
    // 设置关闭按钮
    public void setOnCloseFragmentListener(OnCloseFragmentListener listener) {
        this.mOnCloseFragmentListener = listener;
    }
    // 设置选择某一个品牌的监听事件
    public void setOnChooseBrandListener(OnChooseBrandListener listener) {
        this.mOnChooseBrandListener = listener;
    }
    // 设置选择收藏的监听事件
    public void setOnChooseCollectListener(OnChooseCollectListener listener) {
        this.mOnChooseCollectListener = listener;
    }

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.models_contrast_selectbrand_frg,
                container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(getActivity());
        mModelsContrasProvider = UC_UserProvider.getInstance();
        mModelsContrasProvider.initalize(mHttpPublisher);
        final TitleBar mTitleBar = new TitleBar(view);
        mTitleBar.setTitle("选择品牌");
        mTitleBar.showBackButton(false);
        mTitleBar.showNextButton(true);
        mTitleBar.setNextButton("关闭");
        mTitleBar.setNextTextColor("#cf1c36");
        mTitleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 点击TitleBar的关闭按钮
                mOnCloseFragmentListener.closeFragmentAllFragment();

            }
        });
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new Models_Contrast_PinyinComparator();

        // 选择车系的DrawerLayout
        drawer_layout_carseries = (DrawerLayout) view.findViewById(R.id.models_contrast_drawer_layout_carseries);
        drawer_layout_carseries.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        txt_collect = (TextView) view.findViewById(R.id.models_contrast_txt_collect);
        addClickListener(view, R.id.models_contrast_txt_collect);

        initDrawerLayoutData();

        carseriesFrameLayout = (FrameLayout) view.findViewById(R.id.models_contrast_fly_drawer_carseries);
        collectFrameLayout = (FrameLayout) view.findViewById(R.id.models_contrast_fly_drawer_collect);
        brand_list_view = (PinnedSectionListView) view.findViewById(R.id.models_contrast_lv_brand_list);

        // 设置数据为空的ListView显示
        View emptyListView = LayoutInflater.from(getActivity()).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("数据请求失败，点击重试");
        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //刷新数据
                mModelsContrasProvider.getBrandAll();//请求所有品牌的数据
            }
        });
        ((ViewGroup) brand_list_view.getParent()).addView(emptyListView);
        brand_list_view.setEmptyView(emptyListView);

        sideBar = (EtongSideBar) view.findViewById(R.id.models_contrast_sidebar);
        String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z" };
        sideBar.setCharacter(b);
        dialog = (TextView) view.findViewById(R.id.dialog);

        sideBar.setTextView(dialog);

        // 添加头部
       /* View headView = LayoutInflater.from(getActivity()).inflate(R.layout.models_contrast_selectbrand_lv_header, null);
        TextView models_contrast_txt_collect = (TextView) headView.findViewById(R.id.models_contrast_txt_collect);
        models_contrast_txt_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                EventBus.getDefault().post("", "give collect");
                // 跳到收藏界面中
                mOnChooseCollectListener.onChooseCollect();

                carseriesFrameLayout.setVisibility(View.GONE);
                collectFrameLayout.setVisibility(View.VISIBLE);
            }
        });
        brand_list_view.addHeaderView(headView);*/

        SourceDateList = new ArrayList<>();
        mModelsContrasProvider.getBrandAll();//请求所有品牌的数据


        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new EtongSideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {

                if (SourceDateList != null && SourceDateList.size() > 0) {
                    //该字母首次出现的位置
                    int position = adapter.getCharForPosition(s);
                    L.d("点击字母出现的位置为：", position + "----" + s);
                    if (position != -1) {
                        position = position + brand_list_view.getHeaderViewsCount();
                        brand_list_view.setSelection(position);
                    } else {
                        if ("★".equals(s)) {
                            brand_list_view.setSelection(0);
                        } else if ("#".equals(s)) {
                            brand_list_view.setSelection(brand_list_view.getMaxScrollAmount());
                        }
                    }
                }

            }
        });

        // ListView中的item点击事件
        brand_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                if (position == 0) {
                    return;
                }

                // 如果点击的是悬浮头部， 则不进行点击事件
                if (((Models_Contrast_SelectBrand) adapter.
                        getItem(position - brand_list_view.getHeaderViewsCount())).type == Models_Contrast_SelectBrand.SECTION) {
                    return;
                }
                carseriesFrameLayout.setVisibility(View.VISIBLE);
                collectFrameLayout.setVisibility(View.GONE);
//                EventBus.getDefault().post(((Models_Contrast_SelectBrand) adapter.
//                        getItem(position - brand_list_view.getHeaderViewsCount())).getId(), "give car brand");

                // 将数据发送出去，进入到选择车系界面
                mOnChooseBrandListener.onChooseBrand(Integer.parseInt(((Models_Contrast_SelectBrand) adapter.
                        getItem(position - brand_list_view.getHeaderViewsCount())).getBrand_id()));
                L.d("点击的品牌为：", ((Models_Contrast_SelectBrand) adapter.
                        getItem(position - brand_list_view.getHeaderViewsCount())).getBrand_name() + " " + position);
            }
        });
    }

    private void initDrawerLayoutData() {
        drawer_layout_carseries.setDrawerListener(new DrawerLayout.DrawerListener() {
            /**
             * 当抽屉滑动状态改变的时候被调用
             * 状态值是STATE_IDLE（闲置--0）, STATE_DRAGGING（拖拽的--1）, STATE_SETTLING（固定--2）中之一。
             * 抽屉打开的时候，点击抽屉，drawer的状态就会变成STATE_DRAGGING，然后变成STATE_IDLE
             */
            @Override
            public void onDrawerStateChanged(int arg0) {
                L.i("drawer-------->", "drawer的状态：" + arg0);

            }

            /**
             * 当抽屉被滑动的时候调用此方法
             * arg1 表示 滑动的幅度（0-1）
             */
            @Override
            public void onDrawerSlide(View arg0, float arg1) {
                L.d("drawer正在滑动-------->", arg1 + "");
            }

            /**
             * 当一个抽屉被完全打开的时候被调用
             */
            @Override
            public void onDrawerOpened(View arg0) {
                L.i("drawer-------->", "抽屉被完全打开了！");
            }

            /**
             * 当一个抽屉完全关闭的时候调用此方法
             */
            @Override
            public void onDrawerClosed(View arg0) {
                L.i("drawer-------->", "抽屉被完全关闭了！");
            }
        });

        /**
         * 也可以使用DrawerListener的子类SimpleDrawerListener,
         * 或者是ActionBarDrawerToggle这个子类(详见FirstDemoActivity)
         */
        drawer_layout_carseries.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        });
    }

    @Subscriber(tag = UC_HttpTag.HTTP_BRAND_BY_300)
    private void getBrandAll(HttpMethod method) {
        String errno = method.data().getString("errno");
        String status = method.data().getString("status");
        String msg = method.data().getString("msg");
        SourceDateList.clear();
        if (!TextUtils.isEmpty(status) && status.equals("true")) {
            JSONArray jsonArr = method.data().getJSONObject("data").getJSONArray("brand_list");
            for (int i = 0; i < jsonArr.size(); i++) {
                Models_Contrast_SelectBrand mModels_Contrast_SelectBrand = JSON.toJavaObject(jsonArr.getJSONObject(i), Models_Contrast_SelectBrand.class);
                SourceDateList.add(mModels_Contrast_SelectBrand);
            }
            // 根据a-z进行排序源数据
            Collections.sort(SourceDateList, pinyinComparator);
//            adapter.notifyDataSetChanged();

            // 获取到数据之后设置adapter
            try {
                adapter = new MC_SelectBrandAdapter(getActivity(), SourceDateList);
            } catch (Exception e) {
            }
            brand_list_view.setAdapter(adapter);
        }
    }

    /*
     * 为ListView填充数据
	 * @param date
	 * @return
	 */
    private List<Models_Contrast_SelectBrand> filledData(String[] date) {
        List<Models_Contrast_SelectBrand> mSortList = new ArrayList<Models_Contrast_SelectBrand>();

        for (int i = 0; i < date.length; i++) {
            Models_Contrast_SelectBrand sortModel = new Models_Contrast_SelectBrand();
            sortModel.setBrand_name(date[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setInitial(sortString.toUpperCase());
            } else {
                sortModel.setInitial("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /*
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<Models_Contrast_SelectBrand> filterDateList = new ArrayList<Models_Contrast_SelectBrand>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (Models_Contrast_SelectBrand sortModel : SourceDateList) {
                String name = sortModel.getBrand_name();
                if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }
        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }

    public void setTextDialogInvisiable() {
        dialog.setVisibility(View.INVISIBLE);
    }
}
