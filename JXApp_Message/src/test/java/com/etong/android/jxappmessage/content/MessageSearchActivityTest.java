package com.etong.android.jxappmessage.content;

import android.content.Intent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameConstant;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappmessage.BuildConfig;
import com.etong.android.jxappmessage.R;
import com.etong.android.jxappmessage.javabean.MessageWebViewBean;

import org.junit.Assert;
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
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

/**
 * @desc (资讯搜索 测试用例)
 * @createtime 2017/1/5 - 9:24
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk=21,constants = BuildConfig.class,application = FrameEtongApplication.class)
public class MessageSearchActivityTest {

    private ActivityController<MessageSearchActivity> controller;
    private MessageSearchActivity mActivity;
    private ListView listview;
    private AutoCompleteTextView search_text;
    private LinearLayout search_history;
    private TextView clear;

    @Before
    public void setUp() throws Exception {
        controller = Robolectric.buildActivity(MessageSearchActivity.class);
        controller.create().start();
        mActivity = controller.get();
        listview = (ListView)mActivity.findViewById(R.id.find_car_search_more_result);
        search_history =(LinearLayout)mActivity.findViewById(R.id.find_car_ll_search_history);
        search_text =(AutoCompleteTextView)mActivity.findViewById(R.id.find_car_tct_search_content);
        search_text.setText("宝马");
        clear =(TextView)mActivity.findViewById(R.id.find_car_txt_cancel);
        assertNotNull(mActivity);
        assertNotNull(listview );
    }

    @Test
    public void onResume() throws Exception {
        assertNull(FrameEtongApplication.getApplication().getSearchMessageHistory());
        FrameEtongApplication.getApplication().addSearchMessageHistory("宝马");
        controller.resume();
        assertNotNull(FrameEtongApplication.getApplication().getSearchMessageHistory());
        int viewVisibility = search_history.getVisibility();
        assertEquals(View.VISIBLE, viewVisibility);
    }

    //点击事件
    @Test
    public void onClick() throws Exception {
        FrameEtongApplication.getApplication().addSearchMessageHistory("宝马");
        FrameEtongApplication.getApplication().addSearchMessageHistory("奥迪");
        assertNotNull(FrameEtongApplication.getApplication().getSearchMessageHistory());
        clear.performClick();
        FrameEtongApplication.getApplication().clearSearchMessageHistory();
        assertNull(FrameEtongApplication.getApplication().getSearchMessageHistory());
    }

    //处理搜索的数据
    @Test
    public void getMessageSearch() throws Exception {
        assertTrue(listview.getAdapter().isEmpty());
        for (int i = 0; i < 3; i++) {
            int flagValue = 0;   //0:成功 2：没网  3：没服务
            String msgValue = "";
            Map<String, String> params = new HashMap<>();
            params.put("key","宝马");
            params.put("isJxapp",1+"");
            HttpMethod method = new HttpMethod(FrameHttpUri.Message_List + "?key=" + "宝马" + "&isJxapp=" + 1, params);
            JSONObject jsonObject = new JSONObject();
            switch (flagValue) {
                case 0:
                    jsonObject.put("flag", 0);
                    jsonObject.put("errno","PT_ERROR_SUCCESS");
                    JSONArray array = new JSONArray();
                    MessageWebViewBean bean = new MessageWebViewBean();
                    bean.setId(10000004);
                    bean.setTitle("TEST");
                    bean.setStatus(1);
                    bean.setForward_count(0);
                    bean.setType(10000);
                    bean.setStore_id("isJxapp");
                    JSONObject object = (JSONObject) JSON.toJSON(bean);
                    array.add(object);
                    jsonObject.put("data", array);
                    break;
                case 1:
                    jsonObject.put("flag", HttpPublisher.NETWORK_ERROR);
                    jsonObject.put("msg", "访问失败");
                    msgValue = "访问失败";
                    break;
                case 2:
                    jsonObject.put("flag", HttpPublisher.DATA_ERROR);
                    jsonObject.put("msg", "数据请求失败");
                    msgValue = "数据请求失败";
                    break;
            }
            mActivity.getMessageSearch(method.put(jsonObject));
            assertEquals("宝马",params.get("key"));
            assertEquals(params.get("key"),search_text.getText().toString());
            switch (flagValue) {
                case 0:
                    assertFalse(listview.getAdapter().isEmpty());
                    for(int k=1;k<listview.getCount();k++){
                        listview.getAdapter().getView(k,null,null).performClick();
                        ShadowActivity shadowActivity= Shadows.shadowOf(mActivity);
                        assertNotNull(shadowActivity);
                        Intent nextIntent = shadowActivity.getNextStartedActivity();
                        assertNotNull(nextIntent);
                        assertEquals(MessageWebViewActivity.class.getName(),nextIntent.getComponent().getClassName());
                    }
                    break;
                case 1:
                    assertEquals(View.GONE,listview.getVisibility());
                    break;
                case 2:
                    assertEquals(View.GONE,listview.getVisibility());
                    break;
            }
        }

    }

}