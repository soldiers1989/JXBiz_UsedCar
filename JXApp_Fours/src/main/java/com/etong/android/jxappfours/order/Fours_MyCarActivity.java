package com.etong.android.jxappfours.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
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
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.order.javabeam.Fours_MyCarItemBean;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class Fours_MyCarActivity extends BaseSubscriberActivity {

    private TitleBar mTitleBar;
    private ListView mListView;
    private ListAdapter<FrameUserInfo.Frame_MyCarItemBean> mListAdapter;
    private List<FrameUserInfo.Frame_MyCarItemBean> mDataList;
    private LinearLayout emptyLayout;
    private TextView empty_textview;
    private Fours_Order_Provider mFoursOrderProvider;
    private boolean isOrderActivity;
    private boolean isFirst = true;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.fours_activity_mycar);

        mFoursOrderProvider = Fours_Order_Provider.getInstance();
        mFoursOrderProvider.initialize(HttpPublisher.getInstance());

        Intent i = getIntent();
        isOrderActivity = i.getBooleanExtra("isOrderActivity", false);

        mTitleBar = new TitleBar(this);
        mTitleBar.showBottomLin(false);
        mTitleBar.setTitle("我的爱车");
        mTitleBar.setNextButton("添加爱车");

        mTitleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ActivitySkipUtil.skipActivity(Fours_MyCarActivity.this, Fours_MyCar_AddActivity.class);
                ActivitySkipUtil.skipActivity(Fours_MyCarActivity.this, FM_AddActivity.class);
            }
        });

        mFoursOrderProvider.queryMyCar();
        loadStart();
        initView();
        isFirst = false;
    }

    private void initView() {
        mListView = findViewById(R.id.fours_lv_mycar_list, ListView.class);
        emptyLayout = (LinearLayout) findViewById(R.id.default_empty_content);
        empty_textview = (TextView) findViewById(R.id.default_empty_lv_textview);
        empty_textview.setText("爱车还空着呢，赶快去添加吧！");
//        // 设置数据为空的ListView显示
//        View emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
//        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
//        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
//        default_empty_lv_textview.setText("爱车还空着呢，赶快去添加吧！");
//        default_empty_content.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        ((ViewGroup)mListView.getParent()).addView(emptyListView);
//        mListView.setEmptyView(emptyListView);


        mListAdapter = new ListAdapter<FrameUserInfo.Frame_MyCarItemBean>(this, R.layout.fours_mycar_list_items) {

            @Override
            protected void onPaint(View view, final FrameUserInfo.Frame_MyCarItemBean data, int position) {
                TextView mNumber = findViewById(view, R.id.fours_mycar_list_item_num, TextView.class);
                TextView mPlate = findViewById(view, R.id.fours_mycar_list_item_plate, TextView.class);

                mNumber.setText((position + 1) + "");
                mPlate.setText(data.getPlate_no());

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isOrderActivity) {
//                            FrameUserInfo.Frame_MyCarItemBean tempData = new FrameUserInfo.Frame_MyCarItemBean();
//                            tempData.setPlate_no(data.getPlate_no());
//                            tempData.setCarsetId(data.getCarsetId());
//                            tempData.setVid(data.getVid());
//                            tempData.setVtitle(data.getVtitle());
                            mEventBus.post(data, "plateNo");
                            finish();
                        }
                    }
                });
            }
        };

        mListView.setAdapter(mListAdapter);
//        initData();
//        mListAdapter.addAll(mDataList);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirst) {
            mFoursOrderProvider.queryMyCar();
        }
    }

    private void initData() {
        mDataList = new ArrayList<FrameUserInfo.Frame_MyCarItemBean>();
        FrameUserInfo.Frame_MyCarItemBean temp = new FrameUserInfo.Frame_MyCarItemBean();
        temp.setPlate_no("湘A-66666");
        mDataList.add(temp);
        FrameUserInfo.Frame_MyCarItemBean temp2 = new FrameUserInfo.Frame_MyCarItemBean();
        temp2.setPlate_no("湘A-12345");
        mDataList.add(temp2);
        FrameUserInfo.Frame_MyCarItemBean temp3 = new FrameUserInfo.Frame_MyCarItemBean();
        temp3.setPlate_no("湘A-6S576");
        mDataList.add(temp3);

    }

    @Subscriber(tag = FrameHttpTag.QUERY_MY_CAR)
    protected void queryMyCar(HttpMethod method) {
        showEmptyView();
        mListAdapter.clear();
        mDataList = new ArrayList<FrameUserInfo.Frame_MyCarItemBean>();
//        List<FrameUserInfo.Frame_MyCarItemBean> tempList = new ArrayList<FrameUserInfo.Frame_MyCarItemBean>();
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        loadFinish();
        if (flag.equals("0")) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                FrameUserInfo.Frame_MyCarItemBean tempData = JSON.toJavaObject(jsonArr.getJSONObject(i), FrameUserInfo.Frame_MyCarItemBean.class);;
                mDataList.add(tempData);
            }
//            if (mDataList.size() != 0) {
//                showListView();
//            }
//            mListAdapter.addAll(mDataList);
//            mListAdapter.notifyDataSetChanged();

        } else {
            List<FrameUserInfo.Frame_MyCarItemBean> listData = FrameEtongApplication.getApplication().getUserInfo().getMyCars();
            mDataList.addAll(listData);
//            toastMsg(msg);
        }
        if (mDataList.size() != 0) {
            showListView();
        }
        mListAdapter.addAll(mDataList);
        mListAdapter.notifyDataSetChanged();
    }


    private void showEmptyView() {
        emptyLayout.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    private void showListView() {
        mListView.setVisibility(View.VISIBLE);
        emptyLayout.setVisibility(View.GONE);
    }

}
