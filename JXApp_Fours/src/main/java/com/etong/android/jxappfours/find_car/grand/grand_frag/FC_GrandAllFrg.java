package com.etong.android.jxappfours.find_car.grand.grand_frag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.adapter.FC_GrandAllgoodsAdapter;
import com.etong.android.jxappfours.find_car.grand.bean.FC_InsalesListItem;
import com.etong.android.jxappfours.find_car.grand.bean.FC_InsalesTotalItem;
import com.etong.android.jxappfours.find_car.grand.carset.FC_CarsetActivity;

import org.simple.eventbus.Subscriber;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/18.
 *
 * 品牌界面点击单个车系跳出所有的车型menu
 */
public class FC_GrandAllFrg extends BaseSubscriberFragment {
    private View view;
    private Context mContext;
    private HttpPublisher mHttpPublisher;       // 事件发布者
    PinnedSectionListView allSaleLv;
    FC_GrandAllgoodsAdapter allAdapter;          // 全部的适配器

    List<FC_InsalesTotalItem> SourceDateList;       // 保存存放ListView中Item的数据

    public FC_GrandAllFrg(Context context) {
        this.mContext = context;
    }

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fc_lv_content_allgoods, container, false);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 初始化操作
     */
    private void initView() {

        allSaleLv =  (PinnedSectionListView) view.findViewById(R.id.fc_content_allgoods_lv);
        allAdapter = new FC_GrandAllgoodsAdapter(getActivity());
        allSaleLv.setAdapter(allAdapter);

        allSaleLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Toast.makeText(getActivity(), "进入车系详情界面", Toast.LENGTH_SHORT).show();

                // 如果点击的是悬浮头部， 则不进行点击事件
                if (((FC_InsalesListItem) allAdapter.
                        getItem(position - allSaleLv.getHeaderViewsCount())).type == FC_InsalesListItem.SECTION) {
                    return;
                }

                // 跳到车型详情
                Intent toCarsetActivity = new Intent(getActivity(), FC_CarsetActivity.class);
                toCarsetActivity.putExtra("carset_name", ((FC_InsalesListItem) allAdapter.
                        getItem(position - allSaleLv.getHeaderViewsCount())));
                startActivity(toCarsetActivity);
            }
        });
    }

    /**
     * 获取到车系的名字
     * @param carsetName
     */
    @Subscriber (tag = FrameHttpTag.GET_CARSET_BY_GRAND_ID)
    public void getCarsetNameToupdateAll(String carsetName) {
        L.d("insales", carsetName);
        String encodeName="";
        try {
            encodeName = URLEncoder.encode(carsetName, "utf-8");
            if (encodeName.contains("+")) {
                encodeName.replace("+", "20%");
            }
        } catch (Exception e) {
        }

        // 开始请求网络
//        loadStart();
//         初始化事件发布者
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(mContext);

        HttpMethod httpMethod = new HttpMethod(FrameHttpUri.GetCarsetByGrandID + encodeName+".do", new HashMap());
        mHttpPublisher.sendRequest(httpMethod, FrameHttpTag.GET_CARSET_DETAIL);
    }

    @Subscriber (tag = FrameHttpTag.GET_CARSET_DETAIL)
    public void getCarsetDetail(HttpMethod method) {
        // 请求网络完成
        loadFinish();
//        if (method.data().get(""))
        L.d("获取到所有的车辆为：", method.data().toString());
        SourceDateList = new ArrayList<FC_InsalesTotalItem>();

        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        if (flag.equals("0")) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            for (int i = 0; i < jsonArr.size(); i++) {
                FC_InsalesTotalItem mAllItem = JSON.toJavaObject(jsonArr.getJSONObject(i), FC_InsalesTotalItem.class);
                SourceDateList.add(mAllItem);
            }

            // 获取到数据之后设置adapter
            allAdapter.clear();
            allAdapter.updateListView(SourceDateList);
            allAdapter.notifyDataSetChanged();
        } else {
//            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            toastMsg(msg);
        }
    }
}
