package com.etong.android.jxappcarassistant.violation_query.main_content;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappcarassistant.R;
import com.etong.android.jxappfours.order.Fours_Order_Provider;
import com.etong.android.jxappfours.order.javabeam.Fours_MyCarItemBean;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 违章查询界面
 * @createtime 2016/9/28 0028--18:42
 * @Created by wukefan.
 */
public class CA_ViolationQueryMyCarListActivity extends BaseSubscriberActivity {

    private ListView mListView;
    private ListAdapter<FrameUserInfo.Frame_MyCarItemBean> mListAdapter;
    private ArrayList<FrameUserInfo.Frame_MyCarItemBean> mDataList;
    private Fours_Order_Provider mFoursOrderProvider;
    private TitleBar mTitleBar;
    private boolean isFirst = true;
    private RelativeLayout mAddQueryView;
    private TextView mTxtDetails;
    private View emptyListView;
    private LinearLayout mListLayout;
    private TextView default_empty_lv_textview;
    private ViewGroup default_empty_content;
    private ImageView default_empty_lv_img;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.ca_activity_violquery_mycar_list);

        mFoursOrderProvider = Fours_Order_Provider.getInstance();
        mFoursOrderProvider.initialize(HttpPublisher.getInstance());


        mTitleBar = new TitleBar(this);
        mTitleBar.showBottomLin(false);
        mTitleBar.setTitle("违章查询");
        mTitleBar.setNextButton("+新增查询");
        mTitleBar.setNextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CA_ViolationQueryMyCarListActivity.this, CA_ViolationQueryActivity.class);
                intent.putExtra("isHasCar", false);
                startActivity(intent);
            }
        });
        mTitleBar.showNextButton(false);

        initView();

        if (FrameEtongApplication.getApplication().isLogin()) {
            mFoursOrderProvider.queryMyCar();
        } else {
            showAddQeuryView();
        }

    }

    private void initView() {

        mListLayout = findViewById(R.id.ca_vq_ll_lv_mycar, LinearLayout.class);
        mTxtDetails = findViewById(R.id.ca_vq_list_details_title, TextView.class);
        mListView = findViewById(R.id.ca_vq_lv_mycar, ListView.class);
        mAddQueryView = findViewById(R.id.ca_vq_ll_add_query, RelativeLayout.class);

        addClickListener(R.id.ca_vq_btn_add_query);

        // 设置数据为空的ListView显示
        emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_img = (ImageView) emptyListView.findViewById(R.id.default_empty_img);
        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFoursOrderProvider.queryMyCar();
            }
        });
        ((ViewGroup) mListView.getParent()).addView(emptyListView);
        mListView.setEmptyView(emptyListView);

        mListAdapter = new ListAdapter<FrameUserInfo.Frame_MyCarItemBean>(this, R.layout.ca_violquery_mycar_list_items) {

            @Override
            protected void onPaint(View view, final FrameUserInfo.Frame_MyCarItemBean data, int position) {
                TextView mPlate = findViewById(view, R.id.ca_violquer_ymycar_list_item_plate, TextView.class);

                mPlate.setText(data.getPlate_no());

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(CA_ViolationQueryMyCarListActivity.this, CA_ViolationQueryActivity.class);
                        intent.putExtra("isHasCar", true);
                        intent.putExtra("mFours_MyCarItemBean", data);
                        startActivity(intent);
                    }
                });
            }
        };

        mListView.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();
    }

    @Subscriber(tag = FrameHttpTag.QUERY_MY_CAR)
    protected void queryMyCar(HttpMethod method) {
        mListAdapter.clear();
        mDataList = new ArrayList<FrameUserInfo.Frame_MyCarItemBean>();
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        if (flag.equals("0")) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                FrameUserInfo.Frame_MyCarItemBean tempData = JSON.toJavaObject(jsonArr.getJSONObject(i), FrameUserInfo.Frame_MyCarItemBean.class);
                mDataList.add(tempData);
            }
        } else {
//            toastMsg(msg);
            List<FrameUserInfo.Frame_MyCarItemBean> listData = FrameEtongApplication.getApplication().getUserInfo().getMyCars();
            mDataList.addAll(listData);
        }
        if (mDataList.size() != 0) {
            showListView();
        } else if (mDataList.size() == 0) {
            showAddQeuryView();
        }
        mListAdapter.addAll(mDataList);
        mListAdapter.notifyDataSetChanged();
    }

    //显示爱车列表
    private void showListView() {
        mTxtDetails.setText("亲，请在选择爱车后将车辆信息补充完整，并点击开始查询，即可完成违章查询哦~");
        mTxtDetails.setVisibility(View.VISIBLE);
        mListLayout.setVisibility(View.VISIBLE);
        mAddQueryView.setVisibility(View.GONE);
        mTitleBar.showNextButton(true);
    }

    //显示新增查询
    private void showAddQeuryView() {
        mTxtDetails.setText("亲，您尚未添加车辆，可新增车辆进行查询哦~");
        mTxtDetails.setVisibility(View.VISIBLE);
        mListLayout.setVisibility(View.GONE);
        mAddQueryView.setVisibility(View.VISIBLE);
        mTitleBar.showNextButton(false);
    }

    /**
     * @desc (显示出错视图)
     */
    protected void ShowErrorView(String text, boolean isClick) {
        default_empty_lv_textview.setText(text);
        default_empty_content.setClickable(isClick);
    }

    @Override
    protected void onClick(View view) {
        if (view.getId() == R.id.ca_vq_btn_add_query) {//新增查询
            Intent intent = new Intent(CA_ViolationQueryMyCarListActivity.this, CA_ViolationQueryActivity.class);
            intent.putExtra("isHasCar", false);
            startActivity(intent);
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (!isFirst) {
//            if (FrameEtongApplication.getApplication().isLogin()) {
//                mFoursOrderProvider.queryMyCar();
//            } else {
//                showAddQeuryView();
//            }
//        } else {
//            isFirst = false;
//        }
//    }
}
