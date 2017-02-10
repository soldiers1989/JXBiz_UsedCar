package com.etong.android.jxappcarfinancial.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.etong.android.frame.permissions.PermissionsManager;
import com.etong.android.frame.permissions.PermissionsResultAction;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.AddCommaToMoney;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappcarfinancial.CF_Provider;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.adapter.CF_RecordAdapter;
import com.etong.android.jxappcarfinancial.adapter.CF_RecordDetailsAdapter;
import com.etong.android.jxappcarfinancial.bean.CF_OverdueBean;
import com.etong.android.jxappcarfinancial.bean.CF_RecordDetailsBean;
import com.etong.android.jxappcarfinancial.utils.CF_CancelConformDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 还款明细 逾期明细 （ 点击记录页的item 跳到明细  ）
 * @createtime 2016/11/17 - 19:35
 * @author xiaoxue
 */
public class CF_RecordDetailsActivity extends BaseSubscriberActivity {
    private List<CF_RecordDetailsBean> mRecordDetailsList;  //放明细javabean的list
    private List<CF_RecordDetailsBean.RepayListBean> mRepaymentDetailsList;//还款明细list
    private List<CF_OverdueBean.OverdueListBean> mOverdueDetailsList;       //逾期明细list
    private int flag;   //标识   1还款明细  2 逾期明细
    private CF_RecordDetailsBean mCF_RecordDetailsBean;  //还款明细javabean
    private CF_OverdueBean mCF_OverdueBean;   //逾期明细javabean
    private TitleBar mTitleBar;
    //初始化控件
    private TextView foot_title1;
    private TextView foot_title2;
    private PullToRefreshListView lv_record;
    private ViewGroup ll_record_details_head;
    private TextView cf_number;
    private TextView cf_time;
    private TextView cf_should_also_money;
    private TextView cf_real_also_money;
    private CF_Provider mCF_Provider;
    private HttpPublisher mHttpPublisher;
    private CF_RecordDetailsAdapter mRecordDetailsAdapter;
    private LinearLayout default_empty_view;
    private TextView default_empty_textview;
    private LinearLayout record_details_foot;
    private LinearLayout cf_ll_space;
    private TextView foot_title;
    private TextView foot_phone;
    private CF_CancelConformDialog mCallPhoneDialog; // 打电话的dialog
    private String telPhone="96512";
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
        //得到上一页面传来的flag值
        Intent intent =getIntent();
        flag =intent.getIntExtra("flag",-1);
        switch (flag){
            case 1:
                mCF_RecordDetailsBean= (CF_RecordDetailsBean) intent.getSerializableExtra("CF_RecordDetailsBean");
                break;
            case 2:
                mCF_OverdueBean= (CF_OverdueBean) intent.getSerializableExtra("CF_OverdueBean");

                break;
        }
        initView();
        initData();
        setCallPhone();
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
        switch (flag){
            case 1:
                mTitleBar.setTitle("还款明细");
                break;
            case 2:
                mTitleBar.setTitle("逾期明细");
                break;
        }
        default_empty_view = findViewById(R.id.default_empty_content, LinearLayout.class);
        default_empty_textview = findViewById(R.id.default_empty_lv_textview, TextView.class);
        default_empty_view.setClickable(false);
        default_empty_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (flag){
                    case 1:
                        mCF_Provider.repaymentList();//请求还款列表查询
                        break;
                    case 2:
                        mCF_Provider.overdueList();//请求逾期列表查询
                        break;
                }
            }
        });
        cf_ll_space =findViewById(R.id.cf_ll_space,LinearLayout.class);//头部上面的灰色区域
        ll_record_details_head = (ViewGroup)findViewById(R.id.cf_ll_record_details_head);//LISTVIEW 头部
        record_details_foot =findViewById(R.id.cf_ll_record_details_foot,LinearLayout.class);
        foot_title =findViewById(R.id.cf_txt_foot_title,TextView.class);  //备注/说明
        foot_phone =findViewById(R.id.cf_txt_foot_phone,TextView.class);//电话
        foot_title1 =(TextView)findViewById(R.id.cf_txt_foot_title1);
        foot_title2 =(TextView)findViewById(R.id.cf_txt_foot_title2);
        lv_record =findViewById(R.id.cf_lv_record, PullToRefreshListView.class);    //初始化ListView
        lv_record.setVisibility(View.GONE);
        mRecordDetailsAdapter =new CF_RecordDetailsAdapter(this); //明细的adapter
        lv_record.setAdapter(mRecordDetailsAdapter);

        addClickListener(R.id.cf_txt_foot_phone);
    }

/*
  ##################################################################################################
  ##                                     初始化数据的方法                                         ##
  ##################################################################################################
*/

    /**
     * 请使用快捷模板生成注释
     */
    protected void initData() {
        mRepaymentDetailsList =new ArrayList<>();//还款list
        mOverdueDetailsList=new ArrayList<>();   //逾期list
        ShowListView();

        switch (this.flag){
            case 1:
                if(null==mCF_RecordDetailsBean){
                    ShowNullView("亲,暂无还款明细", false);
                    return;
                }
                if(0==mCF_RecordDetailsBean.getFPeriodTotal()){//总期数
                    ShowNullView("亲,暂无还款明细", false);
                    return;
                }
                /*if(0==mCF_RecordDetailsBean.getRepaySum()){//已还期数
                    ShowNullView("亲,暂无还款明细", false);
                    return;
                }*/

                //显示头部
                initHeadView();
//                ll_record_details_head.setVisibility(View.VISIBLE);
                record_details_foot.setVisibility(View.VISIBLE);//显示脚部
                foot_title.setText("还款信息说明：");
                foot_title1.setText("1.本笔贷款总金额¥"+ AddCommaToMoney.AddCommaToMoney(String.valueOf(mCF_RecordDetailsBean.getFPerRevSum()))+"元,分期"+mCF_RecordDetailsBean.getFPeriodTotal()+"期,月供¥"+AddCommaToMoney.AddCommaToMoney(String.valueOf(mCF_RecordDetailsBean.getFPerRevMouth()))+"元。");
                foot_title2.setText("2.已还款"+mCF_RecordDetailsBean.getRepaySum()+"期,剩余未还"+String.valueOf(mCF_RecordDetailsBean.getRemainSum())+"期," +
                        "剩余未还款¥"+AddCommaToMoney.AddCommaToMoney(String.valueOf(mCF_RecordDetailsBean.getRemainPaySum()))+"元。");
                foot_phone.setText(telPhone);
                if(null!=mCF_RecordDetailsBean.getRepayList()){
                    mRepaymentDetailsList.addAll(mCF_RecordDetailsBean.getRepayList());
                }
                mRecordDetailsAdapter.updateRepayList(mRepaymentDetailsList,this.flag);
                break;
            case 2:
                if(null== mCF_OverdueBean){
                    ShowNullView("亲,暂无逾期明细", false);
                    return;
                }
                if(0==Integer.valueOf(mCF_OverdueBean.getFPeriodTotal())){//总期数
                    ShowNullView("亲,暂无逾期明细", false);
                    return;
                }
                if(0==mCF_OverdueBean.getOverdueTotal()){//逾期数
                    ShowNullView("亲,暂无逾期明细", false);
                    return;
                }
                if(null==mCF_OverdueBean.getOverdueList()){
                    ShowNullView("亲,暂无逾期明细", false);
                    return;
                }

                //显示头部
                initHeadView();
                record_details_foot.setVisibility(View.VISIBLE);//显示脚部
                foot_title.setVisibility(View.GONE);
                foot_title1.setVisibility(View.GONE);
                foot_title2.setVisibility(View.GONE);
//                foot_title.setText("注：");
                foot_phone.setText(telPhone);

//                ll_record_details_head.setVisibility(View.VISIBLE);
                if(null!=mCF_OverdueBean.getOverdueList()){
                    mOverdueDetailsList.addAll(mCF_OverdueBean.getOverdueList());
                }
                mRecordDetailsAdapter.updateOverdueList(mOverdueDetailsList,this.flag);
                break;
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
        if(view.getId()==R.id.cf_txt_foot_phone){//打电话
            mCallPhoneDialog.show();
        }
    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/
    /**
     * @desc (为ListView添加头部)
     * @createtime 2016/11/7 - 18:42
     * @author xiaoxue
     */
    private void initHeadView() {
//        cf_ll_space =findViewById(R.id.cf_ll_space,LinearLayout.class);
//        ll_record_details_head = (ViewGroup)findViewById(R.id.cf_ll_record_details_head);
        cf_number =(TextView)findViewById(R.id.cf_txt_number);
        cf_time =(TextView)findViewById(R.id.cf_txt_time);
        cf_should_also_money =(TextView)findViewById(R.id.cf_txt_should_also_money);
        cf_real_also_money =(TextView)findViewById(R.id.cf_txt_real_also_money);
        cf_number.setText("期数");
        cf_time.setText("应还时间");
        cf_should_also_money.setText("应还金额");
        cf_real_also_money.setText("实还金额");
    }

    /**
     * @desc (显示为空视图)
     * @createtime 2016/11/14 0014-16:11
     * @author wukefan
     */
    protected void ShowNullView(String text, boolean isClick) {
        default_empty_view.setVisibility(View.VISIBLE);
        ll_record_details_head.setVisibility(View.GONE);
        lv_record.setVisibility(View.GONE);
        cf_ll_space.setVisibility(View.GONE);
//        mNullImg.setBackgroundResource(image);
        default_empty_textview.setText(text);
        default_empty_view.setClickable(isClick);
    }

/**
 * @desc (显示ListView)
 * @createtime 2016/11/29 - 15:49
 * @author xiaoxue
 */
    protected void ShowListView() {
        ll_record_details_head.setVisibility(View.VISIBLE);
        cf_ll_space.setVisibility(View.VISIBLE);
        lv_record.setVisibility(View.VISIBLE);
        default_empty_view.setVisibility(View.GONE);
    }

    /**
     * @desc (设置打电话的dialog)
     * @createtime 2016/12/8 - 13:46
     * @author xiaoxue
     */
    protected void setCallPhone() {
        mCallPhoneDialog = new CF_CancelConformDialog(this, false);
        mCallPhoneDialog.setTitle("提示");
        mCallPhoneDialog.setContent("确定拨打电话：" + telPhone + "吗？");
        mCallPhoneDialog.setContentSize(15);
        mCallPhoneDialog.setConfirmButtonClickListener("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //确定操作
                PermissionsManager.getInstance()
                        .requestPermissionsIfNecessaryForResult(
                                CF_RecordDetailsActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                new PermissionsResultAction() {

                                    @Override
                                    public void onGranted() {
                                        callPhoneDialog();
                                    }

                                    @Override
                                    public void onDenied(String permission) {
                                        toastMsg("授权失败，无法完成操作！");
                                        return;
                                    }
                                });
//                mCallPhoneDialog.dismiss();
            }
        });
    }


    /**
     * @desc (打电话)
     * @createtime 2016/12/8 - 13:46
     * @author xiaoxue
     */
    private void callPhoneDialog() {

        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri
                    .parse("tel:"
                            + telPhone));
            startActivity(intent);
        } catch (SecurityException e) {
        }
        mCallPhoneDialog.dismiss();
    }
/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
