package com.etong.android.jxappfours.find_car.filtrate;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.utils.L;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.filtrate.adapter.Find_Car_FiltrateResultListAdapter;
import com.etong.android.jxappfours.find_car.filtrate.javabeam.Find_Car_VechileSeries;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 筛选结果界面里面选择价格的fragment  数据处理页
 * Created by Administrator on 2016/8/8.
 */
public class Find_Car_FiltrateVechilePriceFragment extends BaseSubscriberFragment {
    protected float mDensity;
    protected int mWidth;
    protected int mHeight;
    private View view;
    private ImageProvider mImageProvider;
    private ListView mlistView;
    private Find_Car_FiltrateResultListAdapter mListAdapters;
    //    ListAdapter<Find_Car_VechileSeries> mListAdapters;
    private LinearLayout nullLayout;
    private RelativeLayout nullTitleLayout;
    private RelativeLayout mlistLayout;
    private ImageButton mUpTopBtn;
    private ViewGroup default_empty_content;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        Find_Car_FiltrateFragment.getVechileSeriesData();

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mImageProvider = ImageProvider.getInstance();
        view = inflater.inflate(R.layout.find_car_filtrate_list_frg,
                container, false);
//        setData();
        L.d("---------", "------>>>>");
        initView(view);
        return view;
    }

    protected void initView(View view) {

        mlistLayout = (RelativeLayout) view.findViewById(R.id.find_car_rl_filtrate_result);
        mlistView = (ListView) view.findViewById(R.id.find_car_lv_filtrate_result);
        nullLayout = (LinearLayout) view.findViewById(R.id.filtrate_result_ll_null);
        nullTitleLayout = (RelativeLayout) view.findViewById(R.id.find_car_filtrate_result_rl_null_title);
        mUpTopBtn = (ImageButton) view.findViewById(R.id.find_car_filtrate_result_btn_uptop);
        TextView nullText = (TextView) view.findViewById(R.id.filtrate_result_txt_null);
        nullText.setText("未找到符合条件的车系");

        addClickListener(view, R.id.find_car_filtrate_result_txt_null_reset);
        addClickListener(mUpTopBtn);

        // 设置数据为空的ListView显示
        View emptyListView = LayoutInflater.from(getActivity()).inflate(R.layout.default_empty_listview, null);
        default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        ImageView default_empty_img = (ImageView) emptyListView.findViewById(R.id.default_empty_img);
        default_empty_img.setBackgroundResource(R.drawable.ic_network_error);
        default_empty_lv_textview.setText("点击返回重试");
        default_empty_lv_textview.setTextColor(Color.parseColor("#80310B"));
        default_empty_content.setVisibility(View.GONE);
        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 返回
                Find_Car_FiltrateFragment.closeDrawerLayout();
            }
        });
        ((ViewGroup) mlistView.getParent()).addView(emptyListView);
        mlistView.setEmptyView(emptyListView);


        mlistView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem != 0) {
                    mUpTopBtn.setVisibility(View.VISIBLE);
                } else {
                    mUpTopBtn.setVisibility(View.GONE);
                }
            }
        });

        mListAdapters = new Find_Car_FiltrateResultListAdapter(getActivity());
        mlistView.setAdapter(mListAdapters);

    }

    @Subscriber(tag = "load")
    public void startLoad(String load) {
        loadStart(null, 0);
        mlistLayout.setVisibility(View.GONE);
        nullLayout.setVisibility(View.GONE);
        nullTitleLayout.setVisibility(View.GONE);
    }

    @Subscriber(tag = FrameHttpTag.QUERY_CARSET_BY_CONDITION)
    protected void getFiltrateResult(HttpMethod method) {

        List<Find_Car_VechileSeries> mList = new ArrayList<Find_Car_VechileSeries>();
        String flag = method.data().getString("flag");
        if (null != flag && flag.equals("0")) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (Object o : jsonArr) {
                JSONObject root = (JSONObject) o;
                Find_Car_VechileSeries series = new Find_Car_VechileSeries();
//            series.setId(root.getIntValue("id"));
                series.setTitle(root.getString("title"));
                series.setRoot(true);
                mList.add(series);

                JSONArray carset = root.getJSONArray("carsetList");
                for (Object carJson : carset) {
                    Find_Car_VechileSeries car = JSON.toJavaObject((JSONObject) carJson,
                            Find_Car_VechileSeries.class);
                    if (null == car.getMinguide()) {
                        car.setMinguide(0.0);
                    }
                    mList.add(car);
                }
            }
//            EventBus.getDefault().post(mList, "VechileSeriesInfo");
            getListInfo(mList);
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
            loadFinish();
            showListView();
            mListAdapters.cleanList();
        } else {
            loadFinish();
            showListView();
        }
    }

    //    //获取车系列表，并把每个车系按价格升序排序，全部加入到适配器
//    @Subscriber(tag = "VechileSeriesInfo")
//    public void getListInfo(List<Find_Car_VechileSeries> list) {
    public void getListInfo(List<Find_Car_VechileSeries> list) {
        L.d("----------------->", list.toString());

        List<Find_Car_VechileSeries> nList = new ArrayList<Find_Car_VechileSeries>();
        List<Find_Car_VechileSeries> tempList = new ArrayList<Find_Car_VechileSeries>();
        int n = 0;
        int m = 1;
        for (Find_Car_VechileSeries data : list) {
            if (data.isRoot()) {
                if (m != n) {
                    nList.add(data);
                    ++n;
                } else {
                    Collections.sort(tempList, new MyComp());
                    nList.addAll(tempList);
                    tempList.clear();
                    nList.add(data);
                    ++n;
                    m = n;
                }
            } else {
                if (m == n) {
                    tempList.add(data);
                }
            }
        }
        Collections.sort(tempList, new MyComp());
        nList.addAll(tempList);
        tempList.clear();
        showListView();
        loadFinish();
        L.d("======================>", nList.toString());
        mListAdapters.updateCarList(nList);
        if (nList.size() == 0) {
            showNullView();
        }
    }

//    /**
//     *
//     * @param hidden  Fragment是否显示   显示 hidden false   |  隐藏  hidden  true
//     */
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//
//        if(!hidden) {
//            // 显示的操作
//            // 请求网络 -- 方法
//
//        }
//
//    }

    private void showListView() {
        default_empty_content.setVisibility(View.VISIBLE);
        mlistLayout.setVisibility(View.VISIBLE);
        nullLayout.setVisibility(View.GONE);
        nullTitleLayout.setVisibility(View.GONE);
    }

    private void showNullView() {
        mlistLayout.setVisibility(View.GONE);
        nullLayout.setVisibility(View.VISIBLE);
        nullTitleLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onClick(View v) {
        if (v.getId() == R.id.find_car_filtrate_result_txt_null_reset) {
            mEventBus.post("", "reset");
            Find_Car_FiltrateFragment.closeDrawerLayout();
        } else if (v.getId() == R.id.find_car_filtrate_result_btn_uptop) {
            mlistView.setSelection(0);
        }
    }

    //根据价格升序排序的方法类
    public class MyComp implements Comparator<Find_Car_VechileSeries> {

        @Override
        public int compare(Find_Car_VechileSeries o1, Find_Car_VechileSeries o2) {
            return o1.getMinguide().compareTo(o2.getMinguide());
        }
    }
}