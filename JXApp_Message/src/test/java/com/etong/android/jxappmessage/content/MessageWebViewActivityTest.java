package com.etong.android.jxappmessage.content;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameConstant;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.jxappmessage.BuildConfig;
import com.etong.android.jxappmessage.R;
import com.etong.android.jxappmessage.javabean.MessageDetailsBean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @desc (资讯详情页 webview 测试用例)
 * @createtime 2017/1/4 - 17:58
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk=21,constants = BuildConfig.class,application = FrameEtongApplication.class)
public class MessageWebViewActivityTest {

    private ActivityController<MessageWebViewActivity> controller;
    private MessageWebViewActivity mActivity;
    private WebView mWebView;

    @Before
    public void setUp() throws Exception {
        Intent intent =new Intent();
        intent.putExtra("id","10000199");
        controller = Robolectric.buildActivity(MessageWebViewActivity.class).withIntent(intent);
        controller.create().start();
        mActivity= controller.get();
        mWebView = (WebView)mActivity.findViewById(R.id.Vechile_webview);
        assertNotNull(mActivity);
        assertNotNull(mWebView);

    }

    @Test
    public void getIntent() throws Exception{
        String id=mActivity.getIntent().getStringExtra("id");
        assertEquals("10000199",id);
    }


    @Test
    public void getMessageType() throws Exception {
        for(int i=0;i<3;i++){       //0 成功  1 没网  2 没服务
            int flagValue=i;
            Map<String,String> params=new HashMap<>();
            HttpMethod method=new HttpMethod(FrameConstant.CURRENT_SERVICE + "information/detail/"+10000199+".do",null);
            JSONObject object=new JSONObject();
            switch (flagValue){
                case 0:
                    object.put("flag",0);
//                    JSONObject array=new JSONObject();
                    MessageDetailsBean mMessageDetailsBean=new MessageDetailsBean();
                    mMessageDetailsBean.setId(10000004);
                    mMessageDetailsBean.setTitle("TEST");
                    mMessageDetailsBean.setStatus(1);
                    mMessageDetailsBean.setForward_count(0);
                    mMessageDetailsBean.setType(10000);

                    object.put("data",mMessageDetailsBean);
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    break;
                case 2:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    break;

            }
            mActivity.getMessageType(method.put(object));
            switch (flagValue){
                case 0:
                    assertNotNull(mWebView.getSettings());
                    break;
                case 1:
                    mWebView.setVisibility(View.GONE);
                    assertEquals(View.GONE,mWebView.getVisibility());
                    break;
                case 2:
                    mWebView.setVisibility(View.GONE);
                    assertEquals(View.GONE,mWebView.getVisibility());
                    break;
            }
        }
    }

}