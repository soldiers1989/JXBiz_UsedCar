package com.etong.android.jxappcarfinancial.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappcarfinancial.CF_Provider;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.adapter.CF_AllOrderRecordListAdapter;
import com.etong.android.jxappcarfinancial.bean.CF_AllOrderRecordCountBean;

import org.simple.eventbus.Subscriber;


/**
 * @desc (所有订单记录界面)
 * @createtime 2016/11/17 0017--18:52
 * @Created by wukefan.
 */
public class CF_AllOrderRecordActivity extends BaseSubscriberActivity {


    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    private TextView resultHead;            //头部：总计多少条的布局
    private ViewGroup resultHeadView;       //头部：总计多少条的视图
    private View emptyListView;             //ListView为空的布局
    private ViewGroup emptyListContent;     //为空视图
    private TitleBar mTltleBar;
    private ListView mListView;
    private CF_AllOrderRecordListAdapter mListAdapter;
    private HttpPublisher mHttpPublisher;
    private CF_Provider mProvider;
    public static boolean isTestDebug = false;


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.cf_activity_all_order_record);

        initView();
        initData();
    }


/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/

    /**
     * 请使用快捷模板生成注释
     */
    private void initView() {
        mTltleBar = new TitleBar(this);
        mTltleBar.setmTitleBarBackground("#252e3d");
        mTltleBar.setTitleTextColor("#ffffff");
        mTltleBar.setTitle("订单记录");

        mListView = findViewById(R.id.cf_lv_all_order_record, ListView.class);

        //头部：总计多少条的布局
        View headView = LayoutInflater.from(this).inflate(R.layout.cf_list_head_all_order_record, null);
        resultHeadView = (ViewGroup) headView.findViewById(R.id.cf_ll_head_all_or);
        resultHead = (TextView) headView.findViewById(R.id.cf_txt_head_all_or);
        mListView.addHeaderView(headView);

        // 设置数据为空的ListView显示
        emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        emptyListContent = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView emptyListTxt = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        ImageView emptyListImg = (ImageView) emptyListView.findViewById(R.id.default_empty_img);
        SetEmptyViewUtil setEmptyViewUtil = new SetEmptyViewUtil(emptyListContent, emptyListImg, emptyListTxt);
        setEmptyViewUtil.showNetworkErrorView();
//        emptyListTxt.setText("点击屏幕重试");
//        emptyListTxt.setTextColor(Color.parseColor("#80310B"));
//        emptyListImg.setBackgroundResource(R.drawable.ic_network_error);
        emptyListContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 从网络中获取到数据
                mProvider.queryMyOrder();
            }
        });
        ((ViewGroup) mListView.getParent()).addView(emptyListView);
        mListView.setEmptyView(emptyListView);

        mListAdapter = new CF_AllOrderRecordListAdapter(this);
        mListView.setAdapter(mListAdapter);
        mListView.setVisibility(View.GONE);
    }


/*
  ##################################################################################################
  ##                                     初始化数据的方法                                         ##
  ##################################################################################################
*/

    /**
     * 请使用快捷模板生成注释
     */
    private void initData() {
        //初始化请求
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mProvider = CF_Provider.getInstance();
        mProvider.initialize(mHttpPublisher);

        mProvider.queryMyOrder();

        if (!isTestDebug) {
            loadStart(null, 0);
        }
    }

/*
  ##################################################################################################
  ##                                     点击事件的处理                                           ##
  ##################################################################################################
*/

    /**
     * 控件的点击事件
     */
    @Override
    protected void onClick(View view) {

    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    /**
     * @desc (订单记录接口回调)
     * @createtime 2016/11/22 0022-11:20
     * @author wukefan
     */
    @Subscriber(tag = FrameHttpTag.QUERY_MY_ORDER)
    public void queryMyOrderResult(HttpMethod method) {
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        loadFinish();
        emptyListContent.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            JSONObject jsonData = method.data().getJSONObject("data");
            CF_AllOrderRecordCountBean mAllOrderRecordCountBean = JSON.toJavaObject(jsonData, CF_AllOrderRecordCountBean.class);
            mListAdapter.updateDatas(mAllOrderRecordCountBean);
            resultHeadView.setVisibility(View.VISIBLE);
//            resultHead.setText("总计" + mAllOrderRecordCountBean.getTotalCount() + "条订单~");
            resultHead.setText("总计" + mAllOrderRecordCountBean.getFinancialTotal() + "条订单~");
        } else {
            mListAdapter.notifyDataSetChanged();
            emptyListContent.setVisibility(View.VISIBLE);
            resultHeadView.setVisibility(View.GONE);
        }
    }

/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
