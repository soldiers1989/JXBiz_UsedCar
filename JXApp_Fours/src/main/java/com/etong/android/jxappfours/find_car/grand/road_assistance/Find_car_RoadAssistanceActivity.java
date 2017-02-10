package com.etong.android.jxappfours.find_car.grand.road_assistance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.adapter.Find_car_RoadAssistanceAdapter;
import com.etong.android.jxappfours.find_car.grand.bean.FC_roadassiBean;

import java.util.List;

/**
 * 道路救援界面显示
 */
public class Find_car_RoadAssistanceActivity extends BaseSubscriberActivity {

    private ListView maintance_lv;
    private Find_car_RoadAssistanceAdapter findcarRoadAssistanceAdapter;
    private View emptyListView;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_car_content_road_assistance);

        initView();
        initData();
    }

    /**
     * 初始化数据，更新视图
     */
    private void initData() {
        List<FC_roadassiBean> roadAssisData = FC_roadassisData.getRoadAssisData();
        findcarRoadAssistanceAdapter.updateCompanyData(roadAssisData);

    }

    private void initView() {
        TitleBar titleBar = new TitleBar(this);
        titleBar.setmTitleBarBackground("#FFFFFF");
        titleBar.setTitleTextColor("#000000");
        titleBar.setTitle("道路救援");

        maintance_lv = findViewById(R.id.road_assistance_lv, ListView.class);
        emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("暂无");

        findcarRoadAssistanceAdapter = new Find_car_RoadAssistanceAdapter(this);

        maintance_lv.setAdapter(findcarRoadAssistanceAdapter);
    }
}
