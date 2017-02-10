package com.etong.android.jxappfours.find_car.collect_search.main_content;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.car_config.Find_car_CarConfigActivity;
import com.etong.android.jxappfours.models_contrast.common.Models_Contrast_AddVechileStyle_Method;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;

import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收藏的fragment  数据处理页
 * Created by Administrator on 2016/8/8.
 */
public class Find_Car_VechileCollectSalesFragment extends BaseSubscriberFragment {
    protected float mDensity;
    protected int mWidth;
    protected int mHeight;
    private View view;
    private ImageProvider mImageProvider;
    private  ListView mlistView;
    ListAdapter<Models_Contrast_VechileType> mListAdapters;
    private LinearLayout data_null;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FindCarVechileCollectFragment.getCacheData();


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
         mlistView= (ListView) view.findViewById(R.id.find_car_lv_collect);
        data_null = (LinearLayout) view.findViewById(R.id.models_contrast_ll_null);

        mListAdapters = new ListAdapter<Models_Contrast_VechileType>(
                getActivity(), R.layout.find_car_collect_list_item) {

            @Override
            protected void onPaint(View view, final Models_Contrast_VechileType data, int position) {
                ImageView mImage= (ImageView) view.findViewById(R.id.find_car_img_pic);
                TextView mTitle= (TextView) view.findViewById(R.id.find_car_txt_title);
                TextView mPrice= (TextView) view.findViewById(R.id.find_car_txt_price);
                TextView mAddContrast= (TextView) view.findViewById(R.id.find_car_txt_contrast);

                ImageProvider.getInstance().loadImage(mImage,data.getImage());
                mTitle.setText(data.getTitle());
                mPrice.setText((data.getPrices())+"万起");
                mAddContrast.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Models_Contrast_Add_VechileStyle mAdd_VechileStyle=new Models_Contrast_Add_VechileStyle();
//                        mAdd_VechileStyle.setId(data.getVid());
//                        mAdd_VechileStyle.setTitle(data.getTitle());
//                        Models_Contrast_AddVechileStyle_Method.cartAdd(mAdd_VechileStyle);
                        Models_Contrast_AddVechileStyle_Method.cartAdd(data);
                        Toast.makeText(getActivity(),"加入对比成功",Toast.LENGTH_LONG).show();
                    }
                });


                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =new Intent(getActivity(), Find_car_CarConfigActivity.class);
                        Map map = new HashMap<>();
                        map.put("dataType", data);
                        // 传递数据
                        final SerializableObject myMap = new SerializableObject();
                        myMap.setObject(map);// 将map数据添加到封装的myMap中
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("dataTypeMap", myMap);
                        intent.putExtras(bundle);

                        startActivity(intent);

                    }
                });
            }

        };
        mlistView.setAdapter(mListAdapters);

//        mListAdapters.addAll(mList);
        mListAdapters.notifyDataSetChanged();
    }


    @Subscriber(tag = "listInfo")
    public void getListInfo(List<Models_Contrast_VechileType> list){
//        Collections.sort(list, new MyComp());

        System.out.println("-------"+list);
        mListAdapters.clear();
        if (list.size() == 0) {
            mlistView.setVisibility(View.GONE);
            data_null.setVisibility(View.VISIBLE);
        } else {
            mlistView.setVisibility(View.VISIBLE);
            data_null.setVisibility(View.GONE);

            mListAdapters.addAll(list);

            mListAdapters.notifyDataSetChanged();
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


//    public class MyComp implements Comparator<Models_Contrast_VechileType> {
//
//        @Override
//        public int compare(Models_Contrast_VechileType o1, Models_Contrast_VechileType o2) {
//            return Double.valueOf(o1.getSales()).compareTo(Double.valueOf(o2.getSales()));
//        }
//    }
}
