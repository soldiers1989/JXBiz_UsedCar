package com.etong.android.jxappusedcar.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.order.Fours_Order_Provider;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_Select_Carnumber_Adapter;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : by sunyao
 * @ClassName : UC_Select_CarNumber
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/10/6 - 15:25
 */

public class UC_Select_CarNumber extends BaseSubscriberActivity {

    private TitleBar mTitleBar;
    private ListView carNumberLv;
    private UC_Select_Carnumber_Adapter adapter;
    private Fours_Order_Provider mFoursOrderProvider;
    private LinearLayout mListLayout;
    private View emptyListView;
    private boolean isFirst = true;
    private TextView default_empty_lv_textview;
    private ViewGroup default_empty_content;
    private TextView mTxtDetails;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.used_car_select_car_number);

        mFoursOrderProvider = Fours_Order_Provider.getInstance();
        mFoursOrderProvider.initialize(HttpPublisher.getInstance());


        initView();
//        queryMyCar();
        mFoursOrderProvider.queryMyCar();
//        initDatas();
    }

    /**
     * @param
     * @return
     * @desc (使用控件之前使用必要的初始化方法)
     * @user sunyao
     * @createtime 2016/10/6 - 14:52
     */
    private void initView() {

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("预约卖车");
        mTitleBar.showNextButton(false);

        mTxtDetails = findViewById(R.id.used_car_select_detail_title, TextView.class);
        mListLayout = findViewById(R.id.used_car_ll_list, LinearLayout.class);
        carNumberLv = (ListView) findViewById(R.id.used_car_select_carnumber_lv);

        // 设置数据为空的ListView显示
        emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFoursOrderProvider.queryMyCar();
            }
        });
        default_empty_content.setClickable(false);
        ((ViewGroup) carNumberLv.getParent()).addView(emptyListView);
        carNumberLv.setEmptyView(emptyListView);

        adapter = new UC_Select_Carnumber_Adapter(this);
        carNumberLv.setAdapter(adapter);
    }


    /**
     * @desc (获取我的爱车数据)
     * @createtime 2016/10/8 0008-9:43
     * @author wukefan
     */
    @Subscriber(tag = FrameHttpTag.QUERY_MY_CAR)
    protected void queryMyCar(HttpMethod method) {
//    protected void queryMyCar() {
//        List<FrameUserInfo.Frame_MyCarItemBean> mDataList = FrameEtongApplication.getApplication().getUserInfo().getMyCars();
        List<FrameUserInfo.Frame_MyCarItemBean> mDataList = new ArrayList<>();
        mListLayout.setVisibility(View.VISIBLE);
        mTxtDetails.setVisibility(View.GONE);
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        default_empty_content.setClickable(false);
        default_empty_lv_textview.setText("车库还空着呢，赶快去添加吧!");
        if (flag.equals("0")) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                FrameUserInfo.Frame_MyCarItemBean tempData = JSON.toJavaObject(jsonArr.getJSONObject(i), FrameUserInfo.Frame_MyCarItemBean.class);
                mDataList.add(tempData);
            }
        } else {
            List<FrameUserInfo.Frame_MyCarItemBean> listData = FrameEtongApplication.getApplication().getUserInfo().getMyCars();
            mDataList.addAll(listData);
        }
        adapter.updateCarNumber(mDataList);
        if (!adapter.isEmpty()) {
            mTxtDetails.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirst) {
            mFoursOrderProvider.queryMyCar();
//            queryMyCar();
        } else {
            isFirst = false;
        }
    }
    /**
     * @desc (数据做一些初始化)
     * @user sunyao
     * @createtime 2016/10/6 - 15:58
     * @param
     * @return
     */
//    private void initDatas() {
//
//        List<String> list = new ArrayList<String>();
//        list.add("湘A 123456");
//        list.add("湘A 888888");
//        list.add("湘A 666666");
//        list.add("湘A 512316");
//        list.add("湘A JZ1236");
//
//        adapter.updateCarNumber(list);
//    }
//

}
