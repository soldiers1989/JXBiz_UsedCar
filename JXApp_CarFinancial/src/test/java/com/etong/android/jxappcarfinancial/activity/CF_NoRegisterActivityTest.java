package com.etong.android.jxappcarfinancial.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameConstant;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.jxappcarfinancial.BuildConfig;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.bean.CF_OverdueBean;
import com.etong.android.jxappcarfinancial.bean.CF_RegisterBean;

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

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * @desc (未绑定金融结构页面 测试用例)
 * @createtime 2017/1/3 - 14:27
 * @Created by xiaoxue.
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21,constants = BuildConfig.class,application = FrameEtongApplication.class)
public class CF_NoRegisterActivityTest {
    private int f_ordertype=0;
    private CF_NoRegisterActivity mActivity;
    private ListView mListview;

    @Before
    public void setUp() throws Exception {
        Intent intent =new Intent();
        intent.putExtra("f_ordertype",f_ordertype);

        ActivityController<CF_NoRegisterActivity> controller= Robolectric.buildActivity(CF_NoRegisterActivity.class).withIntent(intent);
        controller.create().start();
        mActivity=controller.get();
        mListview = (ListView)mActivity.findViewById(R.id.cf_lv_register);
        assertNotNull(mActivity);
        assertNotNull(mListview);
    }

    //得到intent值
    @Test
    public void getIntent() throws Exception{
        int type=mActivity.getIntent().getIntExtra("f_ordertype",-1);
        assertEquals(f_ordertype,type);
    }


    //得到查询的金融机构
    @Test
    public void getQueryTheFinancial() throws Exception {
        assertTrue(mListview.getAdapter().isEmpty());
        for(int i=0;i<3;i++){       //0 成功  1 没网  2 没服务
            int flagValue=i;
            Map<String,String> params=new HashMap<>();
            HttpMethod method=new HttpMethod(FrameConstant.CURRENT_SERVICE + "financial/addfinancialAgent.do",params);
            JSONObject object=new JSONObject();
            switch (flagValue){
                case 0:
                    object.put("flag",0);
                    JSONArray array=new JSONArray();
                    CF_RegisterBean mCF_RegisterBean=new CF_RegisterBean();
                    mCF_RegisterBean.setF_institutId(11);
                    mCF_RegisterBean.setF_institutName("弘高金融");
                    mCF_RegisterBean.setF_institutPhone("56546565");
                    JSONObject obj= (JSONObject) JSON.toJSON(mCF_RegisterBean);
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

            mActivity.getQueryTheFinancial(method.put(object));
            switch (flagValue){
                case 0:
                    assertFalse(mListview.getAdapter().isEmpty());
                    for(int k=0;k<mListview.getCount();k++){
                        View itemView=mListview.getAdapter().getView(k,null,null);
                        itemView.findViewById(R.id.cf_ll_make_sure).performClick();
                        ShadowActivity shadowActivity= Shadows.shadowOf(mActivity);
                        Intent nextActivity=shadowActivity.getNextStartedActivity();
//                        ShadowIntent shadowIntent=Shadows.shadowOf(nextActivity);
//                        assertEquals(CF_FirstBindingInfoActivity.class,shadowIntent.getIntentClass());
                        assertEquals(CF_FirstBindingInfoActivity.class.getName(),nextActivity.getComponent().getClassName());
                    }
                    break;
                case 1:
                    mListview.setVisibility(View.GONE);
                    assertEquals(View.GONE,mListview.getVisibility());
                    break;
                case 2:
                    mListview.setVisibility(View.GONE);
                    assertEquals(View.GONE,mListview.getVisibility());
                    break;
            }
        }
    }

}