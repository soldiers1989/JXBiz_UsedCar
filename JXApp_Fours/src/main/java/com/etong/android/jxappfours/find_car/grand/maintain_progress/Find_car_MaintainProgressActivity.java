package com.etong.android.jxappfours.find_car.grand.maintain_progress;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
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
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.adapter.Find_car_MaintanceAdapter;
import com.etong.android.jxappfours.find_car.grand.bean.Find_car_MaintanceItemBean;
import com.etong.android.jxappfours.order.FO_OrderActivity;
import com.etong.android.jxappfours.order.Fours_Order_Provider;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * 维保进度界面
 */
public class Find_car_MaintainProgressActivity extends BaseSubscriberActivity {

    private ListView maintance_lv;
    private Fours_Order_Provider mFours_Order_Provider;
    private List<Find_car_MaintanceItemBean> mMaintanceItemList = new ArrayList<Find_car_MaintanceItemBean>();
    private Find_car_MaintanceAdapter findcarMaintanceAdapter;
    private LinearLayout emptyLayout;
    private TextView empty_textview;
    private LinearLayout maintance_ll;
    private boolean isShowNext;
    private SetEmptyViewUtil setEmptyViewUtil;
    private ImageView empty_img;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_car_content_mainten_progress);

        isShowNext = getIntent().getBooleanExtra("isShowNext", true);

        mFours_Order_Provider = Fours_Order_Provider.getInstance();
        mFours_Order_Provider.initialize(HttpPublisher.getInstance());

        mFours_Order_Provider.queryMaintenanceOrderList(FrameEtongApplication.getApplication().getUserInfo().getUserPhone());
        loadStart(null, 0);
        initView();
    }

    private void initView() {
        TitleBar titleBar = new TitleBar(this);
        titleBar.setTitleTextColor("#000000");
        titleBar.setmTitleBarBackground("#FFFFFF");
        titleBar.setTitle("维保进度");
        if (isShowNext) {
            titleBar.setNextButton("维保预约");
        }
        titleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Find_car_MaintainProgressActivity.this, Fours_Order_OrderActivity.class);
                Intent i = new Intent(Find_car_MaintainProgressActivity.this, FO_OrderActivity.class);
                i.putExtra("flag", 2);
                i.putExtra("isShowNext", false);
                startActivity(i);
//                Toast.makeText(Find_car_MaintainProgressActivity.this, "进入维保预约", Toast.LENGTH_SHORT).show();
            }
        });

        maintance_lv = findViewById(R.id.maintance_progress_lv, ListView.class);

        maintance_ll = findViewById(R.id.maintance_progress_ll, LinearLayout.class);


        emptyLayout = (LinearLayout) findViewById(R.id.default_empty_content);
        empty_textview = (TextView) findViewById(R.id.default_empty_lv_textview);
        empty_img = (ImageView) findViewById(R.id.default_empty_img);
        setEmptyViewUtil = new SetEmptyViewUtil(emptyLayout, empty_img, empty_textview);
        emptyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFours_Order_Provider.queryMaintenanceOrderList(FrameEtongApplication.getApplication().getUserInfo().getUserPhone());
                loadStart();
            }
        });
        setEmptyViewUtil.showNoServerView();
        findcarMaintanceAdapter = new Find_car_MaintanceAdapter(this, mMaintanceItemList);

        maintance_lv.setAdapter(findcarMaintanceAdapter);
    }

    @Subscriber(tag = FrameHttpTag.QUERY_MAINTENANCE_ORDER_LIST)
    protected void getMaintenanceOrderList(HttpMethod method) {
        showEmptyView();
        loadFinish();
        mMaintanceItemList.clear();
        String flag = method.data().getString("flag");
        String msg = method.data().getString("message");
        if (flag.equals("0")) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                Find_car_MaintanceItemBean mModels_Contrast_SelectBrand = JSON.toJavaObject(jsonArr.getJSONObject(i), Find_car_MaintanceItemBean.class);
                mMaintanceItemList.add(mModels_Contrast_SelectBrand);
            }
            if (mMaintanceItemList.size() != 0) {
                showListView();
            } else {
                setEmptyViewUtil.showOtherView("您暂时还没有可查询的维保！", false);
//                empty_textview.setText("您暂时还没有可查询的维保！");
//                emptyLayout.setEnabled(false);
            }
            findcarMaintanceAdapter.updateDataChanged(mMaintanceItemList);
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {
            setEmptyViewUtil.showNetworkErrorView();
        } else {
            setEmptyViewUtil.showNoServerView();
        }
    }

    private void showListView() {
        maintance_ll.setVisibility(View.VISIBLE);
        emptyLayout.setVisibility(View.GONE);
    }

    private void showEmptyView() {
        maintance_ll.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.VISIBLE);
    }
}
