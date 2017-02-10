package com.etong.android.jxappcarassistant.violation_query;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappcarassistant.R;
import com.etong.android.jxappcarassistant.gas_tation.javabean.Fours_MyCarItemBean;

import java.util.ArrayList;

/**
 * @desc 违章查询界面
 * @createtime 2016/9/28 0028--18:42
 * @Created by wukefan.
 */
public class CA_ViolationQueryMyCarListActivity extends BaseSubscriberActivity {

    private ListView mListView;
    private ListAdapter<Fours_MyCarItemBean> mListAdapter;
    private ArrayList<Fours_MyCarItemBean> mDataList;
//    private Fours_Order_Provider mFoursOrderProvider;
    private TitleBar mTitleBar;
    private boolean isFirst = true;
    private RelativeLayout mAddQueryView;
    private TextView mTxtDetails;
    private View emptyListView;
    private LinearLayout mListLayout;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.ca_activity_violquery_mycar_list);

//        mFoursOrderProvider = Fours_Order_Provider.getInstance();
//        mFoursOrderProvider.initialize(HttpPublisher.getInstance());


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

//        if (FrameEtongApplication.getApplication().isLogin()) {
//            mFoursOrderProvider.queryMyCar();
//        } else {
//            showAddQeuryView();
//        }


        isFirst = false;
    }

    private void initView() {

        mListLayout = findViewById(R.id.ca_vq_ll_lv_mycar, LinearLayout.class);
        mTxtDetails = findViewById(R.id.ca_vq_list_details_title, TextView.class);
        mListView = findViewById(R.id.ca_vq_lv_mycar, ListView.class);
        mAddQueryView = findViewById(R.id.ca_vq_ll_add_query, RelativeLayout.class);

        addClickListener(R.id.ca_vq_btn_add_query);

        // 设置数据为空的ListView显示
        emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("数据请求失败，点击重试");
        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mFoursOrderProvider.queryMyCar();
            }
        });
        ((ViewGroup) mListView.getParent()).addView(emptyListView);
        mListView.setEmptyView(emptyListView);

        mListAdapter = new ListAdapter<Fours_MyCarItemBean>(this, R.layout.ca_violquery_mycar_list_items) {

            @Override
            protected void onPaint(View view, final Fours_MyCarItemBean data, int position) {
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

//    @Subscriber(tag = FrameHttpTag.QUERY_MY_CAR)
//    protected void queryMyCar(HttpMethod method) {
//        mListAdapter.clear();
//        mDataList = new ArrayList<Fours_MyCarItemBean>();
//        String flag = method.data().getString("flag");
//        String msg = method.data().getString("msg");
//        if (flag == null) {
//            showListView();
//            mTxtDetails.setVisibility(View.GONE);
//        }
//        if (flag.equals("0")) {
//            JSONArray jsonArr = method.data().getJSONArray("data");
//            for (int i = 0; i < jsonArr.size(); i++) {
//                Fours_MyCarItemBean tempData = JSON.toJavaObject(jsonArr.getJSONObject(i), Fours_MyCarItemBean.class);
//                mDataList.add(tempData);
//            }
//            if (mDataList.size() != 0) {
//                showListView();
//            } else if (mDataList.size() == 0) {
//                showAddQeuryView();
//            }
//            mListAdapter.addAll(mDataList);
//            mListAdapter.notifyDataSetChanged();
//        } else {
//            toastMsg(msg);
//        }
//    }

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
//        }
//    }
}
