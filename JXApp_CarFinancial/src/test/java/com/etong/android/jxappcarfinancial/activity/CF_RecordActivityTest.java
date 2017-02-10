package com.etong.android.jxappcarfinancial.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameConstant;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappcarfinancial.BuildConfig;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.bean.CF_LoanListBean;
import com.etong.android.jxappcarfinancial.bean.CF_OverdueBean;
import com.etong.android.jxappcarfinancial.bean.CF_RecordDetailsBean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.util.ActivityController;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @desc (贷款 还款 逾期记录页 测试用例)
 * @createtime 2017/1/3 - 11:35
 * @Created by xiaoxue.
 * 注：测试前需要注释所要测试的代码中的loadstart
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk=21,constants = BuildConfig.class,application = FrameEtongApplication.class)
public class CF_RecordActivityTest {
    private int flag=1;
    private CF_RecordActivity mActivity;
    private PullToRefreshListView lv_record;
    private ActivityController<CF_RecordActivity> controller;
    private TextView default_empty_textview;

   /* @Before
    public void setUp() throws Exception {
        Intent intent =new Intent();
        intent.putExtra("flag",flag);
        FrameUserInfo userInfo=new FrameUserInfo();
        userInfo.setUserId("10000004");
        userInfo.setFcustid("25713");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        assertNotNull(FrameEtongApplication.getApplication().getUserInfo().getFcustid());
        controller= Robolectric.buildActivity(CF_RecordActivity.class).withIntent(intent);
        controller.create().start();
        mActivity=controller.get();
        lv_record = (PullToRefreshListView)mActivity.findViewById(R.id.cf_lv_record);    //初始化ListView
        lv_record.setVisibility(View.VISIBLE);
        default_empty_textview =(TextView)mActivity.findViewById(R.id.default_empty_lv_textview);
        assertNotNull(mActivity);
        assertNotNull(lv_record);
        assertNotNull(default_empty_textview);
    }*/
    @Before
    public void setUp() throws Exception{
        CF_RecordActivity.isTestDebug=true;
    }

    //贷款列表查询处理
    @Test
    public void loanList() throws Exception {
        Intent intent =new Intent();
        intent.putExtra("flag",0);
        FrameUserInfo userInfo=new FrameUserInfo();
        userInfo.setUserId("10000004");
        userInfo.setFcustid("25713");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        assertNotNull(FrameEtongApplication.getApplication().getUserInfo().getFcustid());
        ActivityController<CF_RecordActivity> controller= Robolectric.buildActivity(CF_RecordActivity.class).withIntent(intent);
        controller.create().start();
        CF_RecordActivity mActivity=controller.get();
        PullToRefreshListView lv_record = (PullToRefreshListView)mActivity.findViewById(R.id.cf_lv_record);    //初始化ListView
        lv_record.setVisibility(View.VISIBLE);
        TextView default_empty_textview =(TextView)mActivity.findViewById(R.id.default_empty_lv_textview);
        assertNotNull(mActivity);
        assertNotNull(lv_record);
        assertNotNull(default_empty_textview);
        assertTrue(lv_record.getRefreshableView().getAdapter().isEmpty());
        for(int i=0;i<3;i++){       //0 成功  1 没网  2 没服务
            int flagValue=i;
            Map<String,String> params=new HashMap<>();
            params.put("fcustid","25713");
            HttpMethod method=new HttpMethod(FrameConstant.CURRENT_SERVICE + "user/hg/loan/detail.do",params);
            JSONObject object=new JSONObject();
            switch (flagValue){
                case 0:
                    object.put("flag",0);
                    JSONArray array=new JSONArray();
                    CF_LoanListBean mCF_LoanListBean=new CF_LoanListBean();
                    mCF_LoanListBean.setFKdate("--");
                    mCF_LoanListBean.setJDStatus("审核中");
                    mCF_LoanListBean.setRepayCard("9159");
                    mCF_LoanListBean.setLoanAmount(90000);
                    mCF_LoanListBean.setMonthRepay(2761);
                    mCF_LoanListBean.setSQdate("2013-01-01");
                    JSONObject obj= (JSONObject) JSON.toJSON(mCF_LoanListBean);
                    array.add(obj);
                    object.put("data",array);
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    break;
                case 2:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    break;

            }

//            if(flagValue==0){
//                assertTrue(lv_record.getRefreshableView().getAdapter().isEmpty());
//            }
            mActivity.loanList(method.put(object));
            switch (flagValue){
                case 0:
                    assertFalse(lv_record.getRefreshableView().getAdapter().isEmpty());
//                    assertEquals(0,lv_record.getRefreshableView().getChildCount());
                    for(int k=1;k<lv_record.getRefreshableView().getCount()-1;k++){
                        lv_record.getRefreshableView().getAdapter().getView(k,null,null).performClick();
                        ShadowActivity shadowActivity= Shadows.shadowOf(mActivity);
                        Intent nextActivity=shadowActivity.getNextStartedActivity();
                        ShadowIntent shadowIntent=Shadows.shadowOf(nextActivity);
                        assertEquals(CF_LoanDocumentationDetailsActivity.class,shadowIntent.getIntentClass());
                    }
                    break;
                case 1:
                    lv_record.setVisibility(View.GONE);
                    assertEquals(View.GONE,lv_record.getVisibility());
                    break;
                case 2:
                    lv_record.setVisibility(View.GONE);
                    assertEquals(View.GONE,lv_record.getVisibility());
                    break;
            }
        }
    }

   // 还款列表查询处理
    @Test
    public void repaymentList() throws Exception {
        Intent intent =new Intent();
        intent.putExtra("flag",1);
        FrameUserInfo userInfo=new FrameUserInfo();
        userInfo.setUserId("10000004");
        userInfo.setFcustid("25713");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        assertNotNull(FrameEtongApplication.getApplication().getUserInfo().getFcustid());
        ActivityController<CF_RecordActivity> controller= Robolectric.buildActivity(CF_RecordActivity.class).withIntent(intent);
        controller.create().start();
        CF_RecordActivity mActivity=controller.get();
        PullToRefreshListView lv_record = (PullToRefreshListView)mActivity.findViewById(R.id.cf_lv_record);    //初始化ListView
        lv_record.setVisibility(View.VISIBLE);
        TextView default_empty_textview =(TextView)mActivity.findViewById(R.id.default_empty_lv_textview);
        assertNotNull(mActivity);
        assertNotNull(lv_record);
        assertNotNull(default_empty_textview);
        assertTrue(lv_record.getRefreshableView().getAdapter().isEmpty());
        for(int i=0;i<3;i++){       //0 成功  1 没网  2 没服务
            int flagValue=i;
            Map<String,String> params=new HashMap<>();
            params.put("fcustid","25714");
            HttpMethod method=new HttpMethod(FrameConstant.CURRENT_SERVICE + "user/hg/repay/getall.do",params);
            JSONObject object=new JSONObject();
            switch (flagValue){
                case 0:
                    object.put("flag",0);
                    JSONArray array=new JSONArray();
                    CF_RecordDetailsBean mCF_RecordDetailsBean=new CF_RecordDetailsBean();
                    mCF_RecordDetailsBean.setFKdate("2013-12-18");
                    mCF_RecordDetailsBean.setRepaySum(5);
                    mCF_RecordDetailsBean.setRemainSum(5);
                    mCF_RecordDetailsBean.setFcustid("11668");
                    mCF_RecordDetailsBean.setFPeriodTotal(10);
                    mCF_RecordDetailsBean.setSQdate("2013-11-25");
                    JSONObject obj= (JSONObject) JSON.toJSON(mCF_RecordDetailsBean);
                    array.add(obj);
                    object.put("data",array);
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    break;
                case 2:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    break;

            }
            mActivity.repaymentList(method.put(object));
            switch (flagValue){
                case 0:
                    assertFalse(lv_record.getRefreshableView().getAdapter().isEmpty());
                    for(int j=1;j<lv_record.getRefreshableView().getCount()-1;j++){
                        lv_record.getRefreshableView().getAdapter().getView(j,null,null).performClick();
                        ShadowActivity shadowActivity1= Shadows.shadowOf(mActivity);
                        Intent nextActivity1=shadowActivity1.getNextStartedActivity();
                        ShadowIntent shadowIntent1=Shadows.shadowOf(nextActivity1);
                        assertEquals(CF_RecordDetailsActivity.class,shadowIntent1.getIntentClass());
                    }
                    break;
                case 1:
                    lv_record.setVisibility(View.GONE);
                    assertEquals(View.GONE,lv_record.getVisibility());
                    break;
                case 2:
                    lv_record.setVisibility(View.GONE);
                    assertEquals(View.GONE,lv_record.getVisibility());
                    break;
            }
        }


    }


    //逾期记录数据处理
    @Test
    public void overdueList() throws Exception {
        Intent intent =new Intent();
        intent.putExtra("flag",2);
        FrameUserInfo userInfo=new FrameUserInfo();
        userInfo.setUserId("10000004");
        userInfo.setFcustid("25713");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        assertNotNull(FrameEtongApplication.getApplication().getUserInfo().getFcustid());
        ActivityController<CF_RecordActivity> controller= Robolectric.buildActivity(CF_RecordActivity.class).withIntent(intent);
        controller.create().start();
        CF_RecordActivity mActivity=controller.get();
        PullToRefreshListView lv_record = (PullToRefreshListView)mActivity.findViewById(R.id.cf_lv_record);    //初始化ListView
        lv_record.setVisibility(View.VISIBLE);
        TextView default_empty_textview =(TextView)mActivity.findViewById(R.id.default_empty_lv_textview);
        assertNotNull(mActivity);
        assertNotNull(lv_record);
        assertNotNull(default_empty_textview);
        assertTrue(lv_record.getRefreshableView().getAdapter().isEmpty());
        for(int i=0;i<3;i++){       //0 成功  1 没网  2 没服务
            int flagValue=i;
            Map<String,String> params=new HashMap<>();
            params.put("fcustid","25715");
            HttpMethod method=new HttpMethod( FrameConstant.CURRENT_SERVICE + "user/hg/overdue/getall.do",params);
            JSONObject object=new JSONObject();
            switch (flagValue){
                case 0:
                    object.put("flag",0);
                    JSONArray array=new JSONArray();
                    CF_OverdueBean mCF_OverdueBean=new CF_OverdueBean();
                    mCF_OverdueBean.setFKdate("2013-12-18");
                    mCF_OverdueBean.setFPerRevSum("97780");
                    mCF_OverdueBean.setOverdueTotal(6);
                    mCF_OverdueBean.setFcustid("11668");
                    mCF_OverdueBean.setFPeriodTotal("10");
                    mCF_OverdueBean.setSQdate("2013-11-25");
                    JSONObject obj= (JSONObject) JSON.toJSON(mCF_OverdueBean);
                    array.add(obj);
                    object.put("data",array);
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    break;
                case 2:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    break;

            }
            mActivity.overdueList(method.put(object));
            switch (flagValue){
                case 0:
                    assertFalse(lv_record.getRefreshableView().getAdapter().isEmpty());
                    for(int k=1;k<lv_record.getRefreshableView().getCount()-1;k++){
                        lv_record.getRefreshableView().getAdapter().getView(k,null,null).performClick();
                        ShadowActivity shadowActivity= Shadows.shadowOf(mActivity);
                        Intent nextActivity=shadowActivity.getNextStartedActivity();
                        ShadowIntent shadowIntent=Shadows.shadowOf(nextActivity);
                        assertEquals(CF_RecordDetailsActivity.class,shadowIntent.getIntentClass());
                    }
                    break;
                case 1:
                    lv_record.setVisibility(View.GONE);
                    assertEquals(View.GONE,lv_record.getVisibility());
                    break;
                case 2:
                    lv_record.setVisibility(View.GONE);
                    assertEquals(View.GONE,lv_record.getVisibility());
                    break;
            }
        }
    }
}