package com.etong.android.jxappcarfinancial.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.utils.SetEmptyViewUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappcarfinancial.CF_Provider;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.adapter.CF_RecordAdapter;
import com.etong.android.jxappcarfinancial.bean.CF_LoanListBean;
import com.etong.android.jxappcarfinancial.bean.CF_OverdueBean;
import com.etong.android.jxappcarfinancial.bean.CF_RecordDetailsBean;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoxue
 * @desc 贷款 还款 逾期记录页
 * @createtime 2016/11/17 - 16:38
 */
public class CF_RecordActivity extends BaseSubscriberActivity {
    private List<CF_LoanListBean> mRecordList; //贷款记录的list
    private int flag;//  贷款：0  还款：1  逾期：2
    //初始化控件
    private PullToRefreshListView lv_record;
    private ViewGroup ll_record_head;
    private TextView head_title;
    private LinearLayout default_empty_view;
    private TextView default_empty_textview;
    private TitleBar mTitleBar;
    private CF_RecordAdapter mRecordAdapter;
    private HttpPublisher mHttpPublisher;
    private CF_Provider mCF_Provider;
    private List<CF_RecordDetailsBean> mRepaymentList;//还款list
    private List<CF_OverdueBean> mOverdueList;//逾期list
    private LinearLayout cf_ll_space;
    private SetEmptyViewUtil setEmptyViewUtil;
    private ImageView default_empty_img;
    public static boolean isTestDebug=false;


    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.cf_activity_record);
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mCF_Provider = CF_Provider.getInstance();
        mCF_Provider.initialize(mHttpPublisher);
        //得到上一页面传来的tag值
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", -1);
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
        mTitleBar = new TitleBar(this);
        mTitleBar.showNextButton(false);
        mTitleBar.showBackButton(true);
        mTitleBar.showBottomLin(false);
        mTitleBar.setTitleTextColor("#ffffff");     //设置title颜色
        mTitleBar.setmTitleBarBackground("#252E3D");//设置titlebar背景色
        switch (flag) {
            case 0:
                mTitleBar.setTitle("贷款记录");
                break;
            case 1:
                mTitleBar.setTitle("还款记录");
                break;
            case 2:
                mTitleBar.setTitle("逾期记录");
                break;
        }
        cf_ll_space = findViewById(R.id.cf_ll_space, LinearLayout.class);//头部上面的灰色区域
        ll_record_head = (ViewGroup) findViewById(R.id.cf_ll_record_head);//初始化ListView头部
        //设置文字
        head_title = (TextView) findViewById(R.id.cf_txt_head_title);
        switch (flag) {
            case 0:
                head_title.setText("进度");
                break;
            case 1:
                head_title.setText("还款明细");
                break;
            case 2:
                head_title.setText("逾期明细");
                break;
        }
        //ListView为空或没网显示的view
        default_empty_view = findViewById(R.id.default_empty_content, LinearLayout.class);
        default_empty_textview = findViewById(R.id.default_empty_lv_textview, TextView.class);
        default_empty_img = findViewById(R.id.default_empty_img, ImageView.class);
        default_empty_view.setClickable(false);
        setEmptyViewUtil = new SetEmptyViewUtil(default_empty_view, default_empty_img, default_empty_textview);
        default_empty_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (flag) {
                    case 0:
                        mCF_Provider.loanList();//请求贷款列表查询
                        break;
                    case 1:
                        if (null != FrameEtongApplication.getApplication().getUserInfo().getFcustid() &&
                                !TextUtils.isEmpty(FrameEtongApplication.getApplication().getUserInfo().getFcustid())) {
                            mCF_Provider.repaymentList();//请求还款列表查询
                        } else {
                            ShowNullView("暂无绑定金融账号，请去申请处绑定", false, setEmptyViewUtil.OtherView);
                        }
                        break;
                    case 2:
                        if (null != FrameEtongApplication.getApplication().getUserInfo().getFcustid() &&
                                !TextUtils.isEmpty(FrameEtongApplication.getApplication().getUserInfo().getFcustid())) {
                            mCF_Provider.overdueList();//请求逾期列表查询
                        } else {
                            ShowNullView("暂无绑定金融账号，请去申请处绑定", false, setEmptyViewUtil.OtherView);
                        }
                        break;
                }
            }
        });
        lv_record = findViewById(R.id.cf_lv_record, PullToRefreshListView.class);    //初始化ListView
        lv_record.setVisibility(View.GONE);
        mRecordAdapter = new CF_RecordAdapter(this);    //记录的adapter
        lv_record.setAdapter(mRecordAdapter);
    }

/*
  ##################################################################################################
  ##                                     初始化数据的方法                                         ##
  ##################################################################################################
*/

    /**
     * 初始化数据
     */
    private void initData() {
        mRecordList = new ArrayList<>();
        mRepaymentList = new ArrayList<>();
        mOverdueList = new ArrayList<>();
        if (null != FrameEtongApplication.getApplication().getUserInfo().getFcustid() &&
                !TextUtils.isEmpty(FrameEtongApplication.getApplication().getUserInfo().getFcustid())) {
            switch (flag) {
                case 0:
                    if(!isTestDebug){
                        loadStart("加载中", 0);
                    }
                    mCF_Provider.loanList();//请求贷款列表查询
                    break;
                case 1:
                    if(!isTestDebug){
                        loadStart("加载中", 0);
                    }
                    mCF_Provider.repaymentList();//请求还款列表查询
                    break;
                case 2:
                    if(!isTestDebug){
                        loadStart("加载中", 0);
                    }
                    mCF_Provider.overdueList();//请求逾期列表查询
                    break;
            }
        } else {
            ShowNullView("暂无绑定金融账号，请去申请处绑定", false, setEmptyViewUtil.OtherView);
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
     * @desc (贷款列表查询处理)
     * @createtime 2016/11/24 - 17:35
     * @author xiaoxue
     */
    @Subscriber(tag = FrameHttpTag.LOANLIST)
    protected void loanList(HttpMethod method) {
        loadFinish();
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        String errno = method.data().getString("errno");
        if (null != errno && errno.equals("PT_ERROR_VERIFY_TOKEN")) {
            ShowNullView("你的账号在别的地方登录，请退出重新登录", false, setEmptyViewUtil.OtherView);
            return;
        } else if (null != errno && errno.equals("PT_ERROR_NODATA")) {
            ShowNullView("亲,暂无贷款记录", false, setEmptyViewUtil.OtherView);
            return;
        }
        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            JSONArray data = method.data().getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {
                CF_LoanListBean mCF_LoanListBean = JSON.toJavaObject((JSON) data.get(i), CF_LoanListBean.class);
                mRecordList.add(mCF_LoanListBean);
            }
            mRecordAdapter.updateListDatasLoan(mRecordList, this.flag);
            if (!mRecordList.isEmpty()) {
                ShowListView();
            }
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {//服务器无响应
            ShowNullView("亲,网络不给力哦\n点击屏幕重试", true, setEmptyViewUtil.NetworkErrorView);
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {//数据请求失败
            ShowNullView("Sorry,您访问的页面找不到了......", false, setEmptyViewUtil.NoServerView);
        } else {
            if (!TextUtils.isEmpty(msg)) {
                toastMsg(msg);
            } else {
                toastMsg("查询贷款记录失败");
            }
        }

    }


    /**
     * @desc (还款列表查询处理)
     * @createtime 2016/11/24 - 20:29
     * @author xiaoxue
     */
    @Subscriber(tag = FrameHttpTag.REPAYMENTLIST)
    protected void repaymentList(HttpMethod method) {
        loadFinish();
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        String errno = method.data().getString("errno");
        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            JSONArray data = method.data().getJSONArray("data");
            //添加数据
            for (int i = 0; i < data.size(); i++) {
                CF_RecordDetailsBean mCF_RecordDetailsBean = JSON.toJavaObject(data.getJSONObject(i), CF_RecordDetailsBean.class);
                mRepaymentList.add(mCF_RecordDetailsBean);
            }
            if (mRepaymentList.isEmpty()) {
                ShowNullView("亲,暂无还款记录", false, setEmptyViewUtil.OtherView);
                return;
            } else {
                ShowListView();
            }
            mRecordAdapter.updateListDatasRepay(mRepaymentList, this.flag);
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {//服务器无响应
            ShowNullView("亲,网络不给力哦\n点击屏幕重试", true, setEmptyViewUtil.NetworkErrorView);
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {//数据请求失败
            ShowNullView("Sorry,您访问的页面找不到了......", false, setEmptyViewUtil.NoServerView);
        } else {
            if (!TextUtils.isEmpty(msg)) {
                toastMsg(msg);
            } else {
                toastMsg("查询还款记录失败");
            }
        }
    }

    /**
     * @desc (逾期记录数据处理)
     * @createtime 2016/11/28 - 18:53
     * @author xiaoxue
     */
    @Subscriber(tag = FrameHttpTag.OVERDUELIST)
    protected void overdueList(HttpMethod method) {
        loadFinish();
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");
        String errno = method.data().getString("errno");
        if (!TextUtils.isEmpty(flag) && flag.equals("0")) {
            JSONArray data = method.data().getJSONArray("data");
            for (int i = 0; i < data.size(); i++) {
                CF_OverdueBean mCF_OverdueBean = JSON.toJavaObject(data.getJSONObject(i), CF_OverdueBean.class);
                mOverdueList.add(mCF_OverdueBean);
            }
            if (mOverdueList.isEmpty()) {
                ShowNullView("亲,暂无逾期记录", false, setEmptyViewUtil.OtherView);
                return;
            } else {
                ShowListView();
            }
            mRecordAdapter.updateListDatasOverdue(mOverdueList, this.flag);
        } else if (flag.equals(HttpPublisher.NETWORK_ERROR)) {//服务器无响应
            ShowNullView("亲,网络不给力哦\n点击屏幕重试", true, setEmptyViewUtil.NetworkErrorView);
        } else if (flag.equals(HttpPublisher.DATA_ERROR)) {//数据请求失败
            ShowNullView("Sorry,您访问的页面找不到了......", false, setEmptyViewUtil.NoServerView);
        } else {
            if (!TextUtils.isEmpty(msg)) {
                toastMsg(msg);
            } else {
                toastMsg("查询逾期记录失败");
            }
        }
    }

    /**
     * @desc (显示为空视图)
     * @createtime 2016/11/14 0014-16:11
     * @author wukefan
     */
    protected void ShowNullView(String text, boolean isClick, int type) {
        default_empty_view.setVisibility(View.VISIBLE);
        lv_record.setVisibility(View.GONE);
        cf_ll_space.setVisibility(View.GONE);
        ll_record_head.setVisibility(View.GONE);
//        mNullImg.setBackgroundResource(image);
//        default_empty_textview.setText(text);
//        default_empty_view.setClickable(isClick);
        setEmptyViewUtil.setView(type, text, isClick);
    }

    /**
     * @desc (显示ListView)
     * @createtime 2016/11/29 - 15:49
     * @author xiaoxue
     */
    protected void ShowListView() {
        lv_record.setVisibility(View.VISIBLE);
        cf_ll_space.setVisibility(View.VISIBLE);
        ll_record_head.setVisibility(View.VISIBLE);
        default_empty_view.setVisibility(View.GONE);
    }


/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
