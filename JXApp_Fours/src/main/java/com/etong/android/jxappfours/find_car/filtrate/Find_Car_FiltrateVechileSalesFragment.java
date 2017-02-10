package com.etong.android.jxappfours.find_car.filtrate;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.filtrate.javabeam.Find_Car_VechileSeries;
import com.etong.android.jxappfours.find_car.grand.carset.FC_CarsetActivity;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * 筛选结果界面里面选择销量的fragment  数据处理页
 * Created by Administrator on 2016/8/8.
 */
public class Find_Car_FiltrateVechileSalesFragment extends BaseSubscriberFragment {
    protected float mDensity;
    protected int mWidth;
    protected int mHeight;
    private View view;
    private ImageProvider mImageProvider;
    private ListView mlistView;
    ListAdapter<Find_Car_VechileSeries> mListAdapters;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        Find_Car_FiltrateFragment.getVechileSeriesData();


        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mImageProvider = ImageProvider.getInstance();
        view = inflater.inflate(R.layout.find_car_collect_list_frg,
                container, false);
//        setData();
        L.d("---------", "------>>>>");
        initView(view);
        return view;
    }

    protected void initView(View view) {
        mlistView = (ListView) view.findViewById(R.id.find_car_lv_collect);


        mListAdapters = new ListAdapter<Find_Car_VechileSeries>(
                getActivity(), R.layout.find_car_filtrate_result_items_lv) {

            @Override
            protected void onPaint(View view, Find_Car_VechileSeries data, int position) {

                RelativeLayout titlezone = (RelativeLayout) view.findViewById(R.id.find_car_filtrate_rl_title);
                TextView zero = (TextView) view
                        .findViewById(R.id.find_car_filtrate_txt_vechile_series_zero);

                TextView title = (TextView) view
                        .findViewById(R.id.find_car_filtrate_txt_vechile_series_title);
                TextView price = (TextView) view
                        .findViewById(R.id.find_car_filtrate_txt_vechile_series_price);
                ImageView logo = (ImageView) view
                        .findViewById(R.id.find_car_filtrate_img_vechile_series_logo);
                View item = view.findViewById(R.id.find_car_filtrate_rl_vechile_series_item);

                TextView  mResetBt = (TextView) view.findViewById(R.id.find_car_filtrate_txt_reset);

                if (data.isRoot()) {
                    titlezone.setVisibility(View.VISIBLE);
                    item.setVisibility(View.GONE);
                    zero.setText(data.getTitle());
                } else {
                    titlezone.setVisibility(View.GONE);
                    item.setVisibility(View.VISIBLE);
                }

                if(position==0){
                    mResetBt.setVisibility(View.VISIBLE);
                }else{
                    mResetBt.setVisibility(View.GONE);
                }

                title.setText(data.getPTitle()+data.getTitle());
                price.setText(data.getMinguide() + "-" + data.getMaxguide() + "万");
                mImageProvider.loadImage(logo, data.getImage());

                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(getActivity(),FC_CarsetActivity.class);//跳转车系列表页面
                        startActivity(intent);
                    }

                });

                mResetBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Find_Car_FiltrateFragment.closeDrawerLayout();
                    }
                });
            }

        };
        mlistView.setAdapter(mListAdapters);

//        mListAdapters.addAll(mList);
        mListAdapters.notifyDataSetChanged();
    }



    //获取车系列表，并把每个车系按销量升序排序，全部加入到适配器
    @Subscriber(tag = "VechileSeriesInfo")
    public void getListInfo(List<Find_Car_VechileSeries> list) {
//        Collections.sort(list, new MyComp());
        System.out.println("-------" + list);

        List<Find_Car_VechileSeries> nList = new ArrayList<Find_Car_VechileSeries>();
        List<Find_Car_VechileSeries> tempList = new ArrayList<Find_Car_VechileSeries>();
        int n=0;
        int m = 1;
        for(Find_Car_VechileSeries data:list){
            if(data.isRoot()){
                if(m!=n){
                    nList.add(data);
                    ++n;
                }else{
//                    Collections.sort(tempList, new MyComp());
                    nList.addAll(tempList);
                    tempList.clear();
                    nList.add(data);
                    ++n;
                    m=n;
                }
            }else{
                if(m==n){
                    tempList.add(data);
                }
            }
        }
//        Collections.sort(tempList, new MyComp());
        nList.addAll(tempList);
        tempList.clear();

        mListAdapters.clear();
        mListAdapters.addAll(nList);
        mListAdapters.notifyDataSetChanged();
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

    //根据销量升序排序的方法类
//    public class MyComp implements Comparator<Find_Car_VechileSeries> {
//
//        @Override
//        public int compare(Find_Car_VechileSeries o1, Find_Car_VechileSeries o2) {
//            return Double.valueOf(o1.getSales()).compareTo(Double.valueOf(o2.getSales()));
//        }
//    }
}
