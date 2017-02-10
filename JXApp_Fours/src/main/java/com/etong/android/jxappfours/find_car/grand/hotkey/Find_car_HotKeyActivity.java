package com.etong.android.jxappfours.find_car.grand.hotkey;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.ListAdapter;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.collect_search.javabean.Find_Car_Search_Result;
import com.etong.android.jxappfours.find_car.grand.bean.FC_InsalesListItem;
import com.etong.android.jxappfours.find_car.grand.carset.FC_CarsetActivity;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;

import org.simple.eventbus.Subscriber;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试的Activity页面
 * 热点的activity
 */
public class Find_car_HotKeyActivity extends BaseSubscriberActivity {

    private ListView hot_key;
    private String hotTitleName;
    private ListAdapter<Find_Car_Search_Result> mListAdapter;
    // 事件发布者
    private HttpPublisher mHttpPublisher;
    private ImageProvider mImageProvider;
    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_find_car__hot_key);

        init();

    }

    private void init() {
        // 初始化事件发布者
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mImageProvider = ImageProvider.getInstance();
        Intent i = getIntent();
        hotTitleName = i.getStringExtra("hotTitleName");

        TitleBar titleBar = new TitleBar(this);
        titleBar.showNextButton(false);
        titleBar.setmTitleBarBackground("#FFFFFF");
        titleBar.setTitleTextColor("#000000");
        titleBar.setTitle(hotTitleName);
        initHotspotData(hotTitleName);

        hot_key =findViewById(R.id.find_car_lv_hot_key,ListView.class);
        // 设置数据为空的ListView显示
        View emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        ViewGroup default_empty_content = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView default_empty_lv_textview = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        default_empty_lv_textview.setText("我已经用了洪荒之力,可还是没找到......");
        default_empty_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //刷新数据
                initHotspotData(hotTitleName);
            }
        });
        ((ViewGroup)hot_key.getParent()).addView(emptyListView);
        hot_key.setEmptyView(emptyListView);


        mListAdapter=new ListAdapter<Find_Car_Search_Result>(this,R.layout.find_car_search_list_item) {
            @Override
            protected void onPaint(View view, final Find_Car_Search_Result data, int position) {
                ImageView vechilePic = (ImageView) view.findViewById(R.id.find_car_img_pic);
                TextView vechileTitle = (TextView) view.findViewById(R.id.find_car_txt_title);
                TextView vechileMinPrice= (TextView) view.findViewById(R.id.find_car_txt_minprice);
                TextView vechileMaxPrice = (TextView) view.findViewById(R.id.find_car_txt_maxprice);
                vechileTitle.setText(data.getFullName());
                vechileMinPrice.setText(String.valueOf(data.getMinguide())+"-");
                vechileMaxPrice.setText(String.valueOf(data.getMaxguide())+"万");
                try {
                    mImageProvider.loadImage(vechilePic, data.getImage(),R.mipmap.fours_grand_loading);

                } catch (Exception e) {
                }
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent =new Intent(Find_car_HotKeyActivity.this, FC_CarsetActivity.class);
                        FC_InsalesListItem mFC_InsalesListItem=new FC_InsalesListItem();
                        mFC_InsalesListItem.setId(data.getId());
                        mFC_InsalesListItem.setImage(data.getImage());
                        mFC_InsalesListItem.setPid(data.getPid());
                        mFC_InsalesListItem.setpTitle(data.getPTitle());
                        mFC_InsalesListItem.setFullName(data.getFullName());
                        mFC_InsalesListItem.setMaxguide(data.getMaxguide());
                        mFC_InsalesListItem.setMinguide(data.getMinguide());
                        mFC_InsalesListItem.setLevel(data.getLevel()+"");
                        mFC_InsalesListItem.setCarlevel(data.getCarlevel());
                        mFC_InsalesListItem.setOutVol(data.getOutVol());
                        mFC_InsalesListItem.setMinOut(data.getMinOut());
                        mFC_InsalesListItem.setMaxOut(data.getMaxOut());
                        intent.putExtra("carset_name",mFC_InsalesListItem);
                        FrameEtongApplication.getApplication().addSearchHistory(data.getFullName());
                        startActivity(intent);
//                        FrameEtongApplication.getApplication().addSearchHistory(data.getFullName());
                    }
                });
            }
        };
        hot_key.setAdapter(mListAdapter);

       /* FragmentManager manager = getSupportFragmentManager();
        Find_Car_HotkeyFrg frg = new Find_Car_HotkeyFrg();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.find_car_hot_key_content,
                frg, "");
        transaction.commit();*/
    }


    /*
    *从网络获取热点的数据
    */
    private void initHotspotData(String str) {
//        loadStart();
        Map<String, String> map = new HashMap<String, String>();
        String carName="";
        try {
            carName= URLEncoder.encode(str,"utf-8");
            if(carName.contains("+")){
                carName.replace("+","20%");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpMethod httpMethod = new HttpMethod(FrameHttpUri.GetCarsetByKey+carName+".do", null);
        mHttpPublisher.sendRequest(httpMethod, FrameHttpTag.GET_HOT_NAME);
    }

    @Subscriber(tag=FrameHttpTag.GET_HOT_NAME)
    protected void getHotData(HttpMethod method){
        mListAdapter.clear();
        String ptError = method.data().getString("ptError");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        if(flag.equals("0")){
            JSONArray jsonArr = method.data().getJSONArray("data");
            List<Find_Car_Search_Result> listSort = new ArrayList<Find_Car_Search_Result>();
            for (int i=0;i<jsonArr.size();i++) {
                Find_Car_Search_Result mFind_Car_Search_Result = JSON.toJavaObject(jsonArr.getJSONObject(i), Find_Car_Search_Result.class);
                listSort.add(mFind_Car_Search_Result);
//                mListAdapter.add(mFind_Car_Search_Result);
            }
            Collections.sort(listSort, new FC_HotKeyPrice_Comp());
            mListAdapter.addAll(listSort);
        }else {
//            toastMsg(msg);
        }
    }

    public class FC_HotKeyPrice_Comp implements Comparator<Find_Car_Search_Result> {
        @Override
        public int compare(Find_Car_Search_Result o1, Find_Car_Search_Result o2) {
            return o1.getMinguide().compareTo(o2.getMinguide());
        }
    }
}
