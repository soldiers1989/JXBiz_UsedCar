package com.etong.android.frame.widget.three_slide_our.main_content;

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
import com.etong.android.frame.R;
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
import com.etong.android.frame.widget.three_slide_our.adapter.Et_SelectBrandAdapter;
import com.etong.android.frame.widget.three_slide_our.common.Et_PinyinComparator;
import com.etong.android.frame.widget.three_slide_our.impl.Et_OnChooseBrandListener;
import com.etong.android.frame.widget.three_slide_our.impl.Et_OnChooseCollectListener;
import com.etong.android.frame.widget.three_slide_our.impl.Et_OnCloseFragmentListener;
import com.etong.android.frame.widget.three_slide_our.javabean.Et_SelectBrand;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 选择品牌fragment
 * Created by Administrator on 2016/8/12.
 */
public class Et_SelectBrandFragment extends BaseSubscriberFragment {

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<Et_SelectBrand> SourceDateList;
    /*
    * 根据拼音来排列ListView里面的数据类
    */
    private Et_PinyinComparator pinyinComparator;
    private ClearEditText mClearEditText;
    private EtongSideBar sideBar;

    private PinnedSectionListView brand_list_view;              // 悬浮头部的ListView
    private UC_UserProvider mModelsContrasProvider;
    private HttpPublisher mHttpPublisher;
    private Et_SelectBrandAdapter adapter;      // 选择品牌界面中的悬浮标题的适配器
    public static DrawerLayout drawer_layout_carseries;
    private TextView txt_collect;
    private FrameLayout collectFrameLayout;
    private FrameLayout carseriesFrameLayout;
    private TextView dialog;

    // 添加的对外接口
    private Et_OnCloseFragmentListener mOnCloseFragmentListener;
    private Et_OnChooseBrandListener mOnChooseBrandListener;
    private Et_OnChooseCollectListener mOnChooseCollectListener;
    // 设置关闭按钮
    public void setOnCloseFragmentListener(Et_OnCloseFragmentListener listener) {
        this.mOnCloseFragmentListener = listener;
    }
    // 设置选择某一个品牌的监听事件
    public void setOnChooseBrandListener(Et_OnChooseBrandListener listener) {
        this.mOnChooseBrandListener = listener;
    }
    // 设置选择收藏的监听事件
    public void setOnChooseCollectListener(Et_OnChooseCollectListener listener) {
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
        pinyinComparator = new Et_PinyinComparator();

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
                mModelsContrasProvider.getAllBrand();//请求所有品牌的数据
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
        SourceDateList = new ArrayList<>();
        mModelsContrasProvider.getAllBrand();//请求所有品牌的数据


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
                if (((Et_SelectBrand) adapter.
                        getItem(position - brand_list_view.getHeaderViewsCount())).type == Et_SelectBrand.SECTION) {
                    return;
                }
                carseriesFrameLayout.setVisibility(View.VISIBLE);
                collectFrameLayout.setVisibility(View.GONE);

                // 将数据发送出去，进入到选择车系界面
//                mOnChooseBrandListener.onChooseBrand(Integer.parseInt(((Et_SelectBrand) adapter.
//                        getItem(position - brand_list_view.getHeaderViewsCount())).getF_carbrandid()));
                mOnChooseBrandListener.onChooseBrand((Et_SelectBrand) adapter.
                        getItem(position - brand_list_view.getHeaderViewsCount()));
                L.d("点击的品牌为：", ((Et_SelectBrand) adapter.
                        getItem(position - brand_list_view.getHeaderViewsCount())).getF_brand() + " " + position);
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

    @Subscriber(tag = UC_HttpTag.GET_ALL_BRAND)
    private void getBrandAll(HttpMethod method) {
        String errno = method.data().getString("errno");
        String status = method.data().getString("status");
        String msg = method.data().getString("msg");
        SourceDateList.clear();
        if (!TextUtils.isEmpty(status) && status.equals("true")) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                Et_SelectBrand mModels_Contrast_SelectBrand = JSON.toJavaObject(jsonArr.getJSONObject(i), Et_SelectBrand.class);
                SourceDateList.add(mModels_Contrast_SelectBrand);
            }
            SourceDateList = filledData(SourceDateList);
            // 根据a-z进行排序源数据
            Collections.sort(SourceDateList, pinyinComparator);
//            adapter.notifyDataSetChanged();

            // 获取到数据之后设置adapter
            try {
                adapter = new Et_SelectBrandAdapter(getActivity(), SourceDateList);
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
    private List<Et_SelectBrand> filledData(List<Et_SelectBrand> date) {
        List<Et_SelectBrand> mSortList = new ArrayList<Et_SelectBrand>();

        for (int i = 0; i < date.size(); i++) {
            Et_SelectBrand sortModel = new Et_SelectBrand();
            sortModel.setF_brand(date.get(i).getF_brand());
            sortModel.setF_carbrandid(date.get(i).getF_carbrandid());
            sortModel.setImage(date.get(i).getImage());
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date.get(i).getF_brand());
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
        List<Et_SelectBrand> filterDateList = new ArrayList<Et_SelectBrand>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (Et_SelectBrand sortModel : SourceDateList) {
                String name = sortModel.getF_brand();
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
