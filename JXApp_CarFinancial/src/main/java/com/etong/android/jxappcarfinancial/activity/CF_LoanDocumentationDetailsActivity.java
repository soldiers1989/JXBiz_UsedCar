package com.etong.android.jxappcarfinancial.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.AddCommaToMoney;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.adapter.CF_LoanDocumentationDatailsAdapter;
import com.etong.android.jxappcarfinancial.bean.CF_LoanDocumentBean;
import com.etong.android.jxappcarfinancial.bean.CF_LoanListBean;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoxue
 * @desc 贷款记录详情
 * @createtime 2016/11/18 - 12:57
 */
public class CF_LoanDocumentationDetailsActivity extends BaseSubscriberActivity {
    //初始化控件
    private PullToRefreshListView lv_record;
    private TitleBar mTitleBar;
    private List<String> mLoanList;                 //贷款记录详情的list
    private CF_LoanDocumentBean mLoanDocumentBean;  //贷款记录javabean
    private CF_LoanDocumentationDatailsAdapter mLoanDetailsAdapter;
    private CF_LoanListBean mCF_LoanListBean;
    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.cf_activity_record);

        Intent intent =getIntent();
        mCF_LoanListBean = (CF_LoanListBean) intent.getSerializableExtra("CF_LoanListBean");
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
        mTitleBar.setTitle("贷款记录详情");
        mTitleBar.setTitleTextColor("#ffffff");     //设置title颜色
        mTitleBar.setmTitleBarBackground("#252E3D");//设置titlebar背景色

        lv_record = findViewById(R.id.cf_lv_record, PullToRefreshListView.class);    //初始化listview
        mLoanDetailsAdapter = new CF_LoanDocumentationDatailsAdapter(this);  //贷款记录详情adapter
        lv_record.setAdapter(mLoanDetailsAdapter);
    }

/*
  ##################################################################################################
  ##                                     初始化数据的方法                                         ##
  ##################################################################################################
*/

    /**
     * 初始化数据
     */
    protected void initData() {

        mLoanList = new ArrayList<>();

        mLoanList.add(mCF_LoanListBean.getSQdate());    //申请时间
        mLoanList.add(mCF_LoanListBean.getFKdate());     //放款时间
        mLoanList.add(mCF_LoanListBean.getJDStatus());     //办理进度
        mLoanList.add("¥" + AddCommaToMoney.AddCommaToMoney(String.valueOf(mCF_LoanListBean.getLoanAmount())));    //贷款金额
        mLoanList.add("¥" + AddCommaToMoney.AddCommaToMoney(String.valueOf(mCF_LoanListBean.getMonthRepay())));     //月供
        String card=mCF_LoanListBean.getRepayCard();
        if(card.length()>=4){// 判断是否长度大于等于4
            String cardNum=card.substring(card.length()-4,card.length());
            mLoanList.add(cardNum);                                           //卡号
        }
        mLoanDetailsAdapter.update(mLoanList);
        lv_record.setVisibility(View.VISIBLE);
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





/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
