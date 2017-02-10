package com.etong.android.jxappcarfinancial.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappcarfinancial.CF_Provider;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.adapter.CF_OrderRecordDetailAdapter;
import com.etong.android.jxappcarfinancial.bean.CF_OrderRecordBean;
import com.etong.android.jxappcarfinancial.bean.CF_OrderRecordDetailsBean;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc (订单记录详情界面)
 * @createtime 2016/11/18 0018--16:48
 * @Created by wukefan.
 */
public class CF_OrderRecordDetailActivity extends BaseSubscriberActivity {

    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    private String f_id;                                     //申请id
    private int f_ordertype;                                 //类型id
    private View emptyListView;                              //ListView为空视图
    private PullToRefreshListView mListView;                 //详情ListView
    private CF_OrderRecordDetailAdapter mDetailAdapter;      //详情适配器
    private HttpPublisher mHttpPublisher;
    private CF_Provider mProvider;
    private TitleBar mTltleBar;
    private SetEmptyViewUtil setEmptyViewUtil;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.cf_activity_record);

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
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mProvider = CF_Provider.getInstance();
        mProvider.initialize(mHttpPublisher);

        Intent intent = getIntent();
        f_id = intent.getStringExtra("f_id");//申请id
        f_ordertype = intent.getIntExtra("f_ordertype", 3);//类型id

        if (null != f_id) {
            mProvider.queryCarPayOrder(f_id, f_ordertype);
        }

        mTltleBar = new TitleBar(this);
        mTltleBar.setmTitleBarBackground("#252e3d");
        mTltleBar.setTitleTextColor("#ffffff");
        mTltleBar.setTitle("订单记录详情");

        mListView = findViewById(R.id.cf_lv_record, PullToRefreshListView.class);
        mListView.setVisibility(View.GONE);
        mListView.setMode(PullToRefreshBase.Mode.DISABLED);

        // 设置数据为空的ListView显示
        emptyListView = LayoutInflater.from(this).inflate(R.layout.default_empty_listview, null);
        ViewGroup emptyListContent = (ViewGroup) emptyListView.findViewById(R.id.default_empty_content);
        TextView emptyListTxt = (TextView) emptyListView.findViewById(R.id.default_empty_lv_textview);
        ImageView emptyListImg = (ImageView) emptyListView.findViewById(R.id.default_empty_img);
        setEmptyViewUtil = new SetEmptyViewUtil(emptyListContent, emptyListImg, emptyListTxt);
        emptyListContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 从网络中获取到数据
                if (null != f_id) {
                    mProvider.queryCarPayOrder(f_id, f_ordertype);
                }
            }
        });
        ((ViewGroup) mListView.getRefreshableView().getParent()).addView(emptyListView);
        mListView.getRefreshableView().setEmptyView(emptyListView);

        mDetailAdapter = new CF_OrderRecordDetailAdapter(this);
        mListView.setAdapter(mDetailAdapter);
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
     * @desc (申请记录详情获取的数据)
     * @createtime 2016/11/23 0023-14:22
     * @author wukefan
     */
    @Subscriber(tag = FrameHttpTag.QUERY_CAR_PAY_ORDER)
    protected void queryCarPayOrderResult(HttpMethod method) {
        List<CF_OrderRecordBean> mListData = new ArrayList<>();
        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        mListView.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            JSONArray jsonArr = method.data().getJSONArray("data");
            if (!jsonArr.isEmpty()) {
                CF_OrderRecordDetailsBean mOrderRecordDetailsBean = JSON.toJavaObject(jsonArr.getJSONObject(0), CF_OrderRecordDetailsBean.class);
                List<String> data = new ArrayList<String>();
                data.add(mOrderRecordDetailsBean.getF_orderid());                            //1、订单编号
                data.add(mOrderRecordDetailsBean.getF_createtime());                         //2、创建时间
                data.add(mOrderRecordDetailsBean.getF_ordertypename());                      //3、订单分类
                if (null != mOrderRecordDetailsBean.getF_orderman()
                        && !TextUtils.isEmpty(mOrderRecordDetailsBean.getF_orderman())) {    //4、姓名
                    data.add(mOrderRecordDetailsBean.getF_orderman());
                } else {
                    data.add("无");
                }

                data.add(mOrderRecordDetailsBean.getF_phone());                              //5、手机号码

                if (null != mOrderRecordDetailsBean.getF_cardid()
                        && !TextUtils.isEmpty(mOrderRecordDetailsBean.getF_cardid())) {      //6、身份证号码
                    data.add(mOrderRecordDetailsBean.getF_cardid());
                } else {
                    data.add("无");
                }
                data.add(mOrderRecordDetailsBean.getF_orderstatusname());                    //7、订单状态

                if (null != mOrderRecordDetailsBean.getF_remark()
                        && !TextUtils.isEmpty(mOrderRecordDetailsBean.getF_remark())) {      //8、备注
                    data.add(mOrderRecordDetailsBean.getF_remark());
                } else {
                    data.add("无");
                }
                mDetailAdapter.updateDatas(data);
            }
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {
            setEmptyViewUtil.showNoServerView();
        } else {
            setEmptyViewUtil.showNetworkErrorView();
        }
    }



/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
