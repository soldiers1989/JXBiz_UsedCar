package com.etong.android.jxappmessage.content;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameConstant;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappmessage.BuildConfig;
import com.etong.android.jxappmessage.R;
import com.etong.android.jxappmessage.javabean.MessageWebViewBean;

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
 * @desc (资讯列表 测试用例)
 * @createtime 2017/1/4 - 18:43
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class MessageListActivityTest {

    private ActivityController<MessageListActivity> controller;
    private MessageListActivity mActivity;
    private PullToRefreshListView message_lv_list;

    @Before
    public void setUp() throws Exception {
        Intent intent = new Intent();
        intent.putExtra("title", "车讯");
        intent.putExtra("type", "10000");
        controller = Robolectric.buildActivity(MessageListActivity.class).withIntent(intent);
        controller.create().start();
        mActivity = controller.get();
        message_lv_list = (PullToRefreshListView) mActivity.findViewById(R.id.message_lv_list);
        assertNotNull(mActivity);
        assertNotNull(message_lv_list);

    }

    @Test
    public void getIntent() throws Exception {
        String title = mActivity.getIntent().getStringExtra("title");
        assertEquals("车讯", title);
        String type = mActivity.getIntent().getStringExtra("type");
        assertEquals("10000", type);


    }

    @Test
    public void getMessageList() throws Exception {
        assertTrue(message_lv_list.getRefreshableView().getAdapter().isEmpty());
        for (int i = 0; i < 3; i++) {  //  1 没网 2 没服务  0成功 errno.equals("PT_ERROR_SUCCESS")errno.equals("PT_ERROR_SUCCESS")
            int flagValue = 0;
            Map<String, String> parmas = new HashMap<>();
            parmas.put("isJxapp", 1 + "");
            parmas.put("recommend", 1 + "");
            parmas.put("start", 0 + "");
            parmas.put("limit", 5 + "");
            parmas.put("isPullDown", 0 + "");
            parmas.put("type", 10000 + "");
            parmas.put("key", null);
            HttpMethod method = new HttpMethod(FrameConstant.CURRENT_SERVICE + "information/queryInfomationListNext.do", parmas);
            JSONObject object = new JSONObject();
            switch (flagValue) {
                case 0:
                    object.put("flag", 0);
                    object.put("errno", "PT_ERROR_SUCCESS");
                    JSONArray array = new JSONArray();
                    MessageWebViewBean bean = new MessageWebViewBean();
                    bean.setId(10000004);
                    bean.setTitle("TEST");
                    bean.setStatus(1);
                    bean.setForward_count(0);
                    bean.setType(10000);
                    bean.setStore_id("isJxapp");
                    JSONObject obj = (JSONObject) JSON.toJSON(bean);
                    array.add(obj);
                    object.put("data", array);
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    break;
                case 2:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    break;
            }
            mActivity.getMessageList(method.put(object));
            switch (flagValue) {
                case 0:
                    assertFalse(message_lv_list.getRefreshableView().getAdapter().isEmpty());
                    for (int k = 1; k < message_lv_list.getRefreshableView().getCount() - 1; k++) {
                        message_lv_list.getRefreshableView().getAdapter().getView(k, null, null).performClick();
                        ShadowActivity shadowActivity = Shadows.shadowOf(mActivity);
                        Intent nextActivity = shadowActivity.getNextStartedActivity();
                        ShadowIntent shadowIntent = Shadows.shadowOf(nextActivity);
                        assertEquals(MessageWebViewActivity.class, shadowIntent.getIntentClass());
                    }
                    break;
                case 1:
                    message_lv_list.setVisibility(View.GONE);
                    assertEquals(View.GONE, message_lv_list.getVisibility());
                    break;
                case 2:
                    message_lv_list.setVisibility(View.GONE);
                    assertEquals(View.GONE, message_lv_list.getVisibility());
                    break;
            }
        }
    }

}