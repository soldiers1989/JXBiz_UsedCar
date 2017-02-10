package com.etong.android.jxappcarfinancial.activity;

import android.content.Intent;

import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.AddCommaToMoney;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappcarfinancial.BuildConfig;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.bean.CF_LoanListBean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @desc (贷款记录详情 测试用例)
 * @createtime 2017/1/3 - 16:33
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk=21,constants = BuildConfig.class,application = FrameEtongApplication.class)
public class CF_LoanDocumentationDetailsActivityTest {

    private ActivityController<CF_LoanDocumentationDetailsActivity> controller;
    private CF_LoanDocumentationDetailsActivity mActivity;
    private PullToRefreshListView lv_record;
    private CF_LoanListBean mCF_LoanListBean;

    @Before
    public void setUp() throws Exception {
        mCF_LoanListBean=new CF_LoanListBean();
        mCF_LoanListBean.setSQdate("2013-01-01");
        mCF_LoanListBean.setJDStatus("审核中");
        mCF_LoanListBean.setRepayCard("9159");
        mCF_LoanListBean.setLoanAmount(90000);
        mCF_LoanListBean.setFKdate("--");
        mCF_LoanListBean.setMonthRepay(2761);

        Intent intent=new Intent();
        intent.putExtra("CF_LoanListBean",mCF_LoanListBean);

        controller= Robolectric.buildActivity
                (CF_LoanDocumentationDetailsActivity.class).withIntent(intent);
        controller.create().start();
        mActivity=controller.get();
        lv_record = (PullToRefreshListView)mActivity.findViewById(R.id.cf_lv_record);    //初始化listview
        assertNotNull(mActivity);
        assertNotNull(lv_record);
    }


    //初始化数据
    @Test
    public void initData() throws Exception{
        CF_LoanListBean mListBean= (CF_LoanListBean) mActivity.getIntent().getSerializableExtra("CF_LoanListBean");
        assertEquals(mCF_LoanListBean,mListBean);
//        mActivity.initData();
        assertFalse(lv_record.getRefreshableView().getAdapter().isEmpty());
    }


}