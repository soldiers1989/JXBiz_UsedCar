package com.etong.android.jxappfours.find_car.grand.maintain_progress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.adapter.Find_car_MaintanceDetailAdapter;
import com.etong.android.jxappfours.find_car.grand.bean.Find_car_MaintanceItemBean;
import com.etong.android.jxappfours.find_car.grand.bean.Find_car_MaintanceProjectItemBean;
import com.etong.android.jxappfours.order.Fours_Order_Provider;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 维保详情界面
 */
public class Find_car_MaintainDetailActivity extends BaseSubscriberActivity {

    private ListView maintance_lv;
    private Fours_Order_Provider mFours_Order_Provider;
    private List<String> data = new ArrayList<String>();
    private Find_car_MaintanceDetailAdapter maintanceAdapter;
    private String[] mMaintanStates={"预约","已接车，待派工","已派工，在修","已完工，待结算","已结算","已收款","已出厂"};
    private Find_car_MaintanceItemBean mDatas = new Find_car_MaintanceItemBean();
//    private String date;
//    private String store;
//    private String type;
//    private String plate;


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_car_content_mainten_detail);
//        Intent i = getIntent();
//
//        date=i.getStringExtra("date");
//        store=i.getStringExtra("store");
//        type=i.getStringExtra("type");
//        plate=i.getStringExtra("plate");
        //得到传过来的配置
        Bundle bundle = getIntent().getExtras();
        SerializableObject serializableMap = (SerializableObject) bundle.get("dataMap");
        Map map = (Map) serializableMap.getObject();
        mDatas= (Find_car_MaintanceItemBean) map.get("data");

        mFours_Order_Provider = Fours_Order_Provider.getInstance();
        mFours_Order_Provider.initialize(HttpPublisher.getInstance());
        mFours_Order_Provider.queryMaintenanceOrderDetail(mDatas.getSv_id());
        initView();
    }

    private void initView() {
        TitleBar titleBar = new TitleBar(this);
        titleBar.setTitleTextColor("#000000");
        titleBar.setmTitleBarBackground("#FFFFFF");
        titleBar.setTitle("维保记录详情");

        maintance_lv = findViewById(R.id.find_car_content_mainten_detail_lv, ListView.class);


        maintanceAdapter = new Find_car_MaintanceDetailAdapter(this, data);

        maintance_lv.setAdapter(maintanceAdapter);
    }

    // 从网络中获取到数据
    @Subscriber(tag = FrameHttpTag.QUERY_MAINTENANCE_ORDER_DETAIL)
    private void getDataFromNet(HttpMethod method) {

        String flag = method.data().getString("flag");
        String msg = method.data().getString("message");

        String project="";
        if(flag.equals("0")){
            JSONArray jsonArr = method.data().getJSONArray("data");
            for(int j = 0; j < jsonArr.size(); j++){
                JSONArray jsonProjectArr = jsonArr.getJSONArray(j);
                for (int i = 0; i < jsonProjectArr.size(); i++) {
                    if(jsonProjectArr.get(i).toString().contains("clname")){
                        Find_car_MaintanceProjectItemBean mMaintanceProjectItem = JSON.toJavaObject(jsonProjectArr.getJSONObject(i), Find_car_MaintanceProjectItemBean.class);
                        project=project+mMaintanceProjectItem.getClname()+"、";
                    }
                }
            }
        }else{
            toastMsg("查询失败！"+msg);
        }
        project=project.substring(0,project.length()-1);
        data.add(mDatas.getBilldate());//接车时间
        data.add(mDatas.getVehicleno());//车牌号
//        data.add(mDatas.getCarsname());//车系
//        data.add(mDatas.getCarsname()+mDatas.getVehiclename());//车型
        data.add(mDatas.getVehiclename());//车型
        data.add(mDatas.getMilimetre()+"KM");//进厂里程
        data.add(mDatas.getDeptname());//维保机构
        data.add(mDatas.getSvtype());//维保类型
        data.add(project);//维保项目
        if(mDatas.getStarts().equals("-1")){
            data.add("已作废");//进度状态
        }else{
            data.add(mMaintanStates[Integer.valueOf(mDatas.getStarts())]);//进度状态
        }

        maintanceAdapter.notifyDataSetChanged();
    }

}
