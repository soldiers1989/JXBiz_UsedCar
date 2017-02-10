package com.etong.android.jxappfours.order;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * 已选择某款车后选择车型界面
 * Created by Administrator on 2016/8/12.
 */
public class Fours_Order_SelectTypeFragment extends BaseSubscriberFragment {


    private ListAdapter<Models_Contrast_VechileType> mListAdapters;
    private ListView mListView;
    private List<Models_Contrast_VechileType> mList = new ArrayList<Models_Contrast_VechileType>();
    private Fours_Order_Provider mFours_Order_Provider;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fours_order_vechile_type_frg, container, false);

        mFours_Order_Provider = Fours_Order_Provider.getInstance();
        mFours_Order_Provider.initialize(HttpPublisher.getInstance());
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        final TitleBar mTitleBar = new TitleBar(view);
        mTitleBar.setTitle("选择车型");
        mTitleBar.showBackButton(false);
        mTitleBar.showNextButton(true);
        mTitleBar.setNextButton("关闭");
        mTitleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FO_OrderActivity.mOrderDrawerLayout.closeDrawer(Gravity.RIGHT);//右边
            }
        });

        mListView = (ListView) view.findViewById(R.id.fours_order_lv_vechile_type);

        View emptyListView = LayoutInflater.from(getActivity()).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("数据请求失败，点击重试");
        // 获取屏幕像素相关信息
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        int mWidth = dm.widthPixels;
        emptyListView.setMinimumWidth(mWidth);
        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFours_Order_Provider.getVechileType(FO_OrderActivity.carsetId);
            }
        });
        ((ViewGroup) mListView.getParent()).addView(emptyListView);
        mListView.setEmptyView(emptyListView);

        mListAdapters = new ListAdapter<Models_Contrast_VechileType>(getActivity(), R.layout.fours_order_vechile_type_item_lv) {

            @Override
            protected void onPaint(View view, final Models_Contrast_VechileType data, int position) {

                ImageView mImage = (ImageView) view.findViewById(R.id.fours_order_img_vt_pic);
                TextView mTitle = (TextView) view.findViewById(R.id.fours_order_txt_vt_title);
                TextView mPrice = (TextView) view.findViewById(R.id.fours_order_txt_vt_price);

                ImageProvider.getInstance().loadImage(mImage, data.getImage(), R.mipmap.fours_default_img);
                mTitle.setText(data.getTitle());
                mPrice.setText(data.getPrices() + "万起");

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mEventBus.post(data, "selectCarResult");
                        FO_OrderActivity.mOrderDrawerLayout.closeDrawer(Gravity.RIGHT);
                    }
                });

            }
        };
        mListView.setAdapter(mListAdapters);
        mListAdapters.notifyDataSetChanged();
    }

//    //获取车系对应的所有车型
//    @Subscriber(tag = Find_Car_CommonEvent.VECHILE_TYPE)
//    protected void getVechileTypeData(HttpMethod method) {
//
//        String errno = method.data().getString("errno");
//        String flag = method.data().getString("flag");
//        String msg = method.data().getString("msg");
//        mList.clear();
//        if (flag.equals("0")) {
//            JSONArray jsonArr = method.data().getJSONArray("data");
//            for (int i = 0; i < jsonArr.size(); i++) {
//                Models_Contrast_VechileType mModels_Contrast_VechileType = JSON.toJavaObject(jsonArr.getJSONObject(i), Models_Contrast_VechileType.class);
//                mList.add(mModels_Contrast_VechileType);
//                if(null!=FO_OrderActivity.imageTitle){
//                    if(FO_OrderActivity.imageTitle.contains(mModels_Contrast_VechileType.getYear()+"款 "+mModels_Contrast_VechileType.getSubject())){
//                        mEventBus.post(mModels_Contrast_VechileType.getVid(),"imageVid");
//                    }
//                }
//            }
//            mListAdapters.addAll(mList);
//            mListAdapters.notifyDataSetChanged();
//        } else {
//            toastMsg(msg);
//        }
//    }

    @Subscriber(tag = "SelectTypeFragment")
    protected void getSelectCarTypeList(List<Models_Contrast_VechileType> list) {
        mListAdapters.clear();
        mListAdapters.addAll(list);
        mListAdapters.notifyDataSetChanged();
    }

}
