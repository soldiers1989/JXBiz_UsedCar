package com.etong.android.jxappmessage.content;

import android.content.Intent;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameConstant;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.widget.loopbanner.BGABanner;
import com.etong.android.jxappmessage.BuildConfig;
import com.etong.android.jxappmessage.R;
import com.etong.android.jxappmessage.javabean.MessageBannerModel;
import com.etong.android.jxappmessage.javabean.MessageWebViewBean;
import com.etong.android.jxappmessage.utils.MessageNoScrollListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.Shadows;
import org.robolectric.shadows.support.v4.SupportFragmentController;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @desc (资讯页面 测试用例)
 * @createtime 2017/1/4 - 14:57
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk=21,constants = BuildConfig.class,application = FrameEtongApplication.class)
public class MessageMainFragmentTest {
    private MessageMainFragment mMessageMainFragment;
    private MessageNoScrollListView mListView;
    private SupportFragmentController controller;
    private LinearLayout ll_xianshigou;
    private LinearLayout ll_goucheyouhui;
    private LinearLayout ll_weibaoyouhui;
    private LinearLayout ll_chexun;
    private LinearLayout ll_chezhanhuodong;
    private LinearLayout ll_chedaiyouhui;
    private LinearLayout ll_changtongkahuodong;
    private LinearLayout ll_baoxianyouhui;
    private ImageButton message_search;
    private BGABanner topMainBanner;

    @Before
    public void setUp() throws Exception {
        MessageMainFragment fragment =new MessageMainFragment();
        controller= SupportFragmentController.of(fragment);
        controller.create().attach().start();
        mMessageMainFragment= (MessageMainFragment) controller.get();
        topMainBanner = (BGABanner)mMessageMainFragment.getActivity().findViewById(R.id.banner_main_accordion);
        mListView = (MessageNoScrollListView)mMessageMainFragment.getActivity().findViewById(R.id.message_lv_messagelist);
        ll_xianshigou = (LinearLayout) mMessageMainFragment.getActivity().findViewById(R.id.message_ll_time_limited_buy);
        ll_goucheyouhui = (LinearLayout) mMessageMainFragment.getActivity().findViewById(R.id.message_ll_the_gallon);
        ll_weibaoyouhui = (LinearLayout) mMessageMainFragment.getActivity().findViewById(R.id.message_ll_maintenance);
        ll_chexun = (LinearLayout) mMessageMainFragment.getActivity().findViewById(R.id.message_ll_motorzine);
        ll_chezhanhuodong = (LinearLayout) mMessageMainFragment.getActivity().findViewById(R.id.message_ll_car_show);
        ll_chedaiyouhui = (LinearLayout) mMessageMainFragment.getActivity().findViewById(R.id.message_ll_car_loans);
        ll_changtongkahuodong = (LinearLayout) mMessageMainFragment.getActivity().findViewById(R.id.message_ll_happly_car);
        ll_baoxianyouhui = (LinearLayout) mMessageMainFragment.getActivity().findViewById(R.id.message_ll_insurance);
        message_search = (ImageButton) mMessageMainFragment.getActivity().findViewById(R.id.message_img_search);
        assertNotNull(mMessageMainFragment);
        assertNotNull(mListView);
    }

    //得到的数据
    @Test
    public void getMessageList() throws Exception {
        assertTrue(mListView.getAdapter().isEmpty());
        for (int i = 1; i < 4; i++) {  //  1 没网 2 没服务  3 成功 errno.equals("PT_ERROR_SUCCESS")
            int flagValue = i;
            Map<String, String> parmas = new HashMap<>();
            parmas.put("isJxapp", 1 + "");
            parmas.put("recommend", 1 + "");
            parmas.put("start", 0 + "");
            parmas.put("limit", 5 + "");
            parmas.put("isPullDown", 0 + "");
            HttpMethod method = new HttpMethod(FrameConstant.CURRENT_SERVICE + "information/queryInfomationListNext.do", parmas);
            JSONObject object = new JSONObject();
            switch (flagValue) {
                case 3:
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
            mMessageMainFragment.getMessageList(method.put(object));
            switch (flagValue) {
                case 3:
                    assertFalse(mListView.getAdapter().isEmpty());
                    for(int k=0;k<mListView.getCount();k++){
                        mListView.getAdapter().getView(k,null,null).performClick();
                        ShadowActivity shadowActivity= Shadows.shadowOf(mMessageMainFragment.getActivity());
                        Intent nextActivity=shadowActivity.getNextStartedActivity();
                        ShadowIntent shadowIntent=Shadows.shadowOf(nextActivity);
                        assertEquals(MessageWebViewActivity.class,shadowIntent.getIntentClass());
                    }
                    break;
                case 1:
                    assertTrue(mListView.getAdapter().isEmpty());
                    break;
                case 2:
                    assertTrue(mListView.getAdapter().isEmpty());
                    break;
            }
        }
    }

    //点击view进行跳转
    @Test
    public void onClick() throws Exception{
        ll_xianshigou.performClick();
        ShadowActivity shadowActivity= Shadows.shadowOf(mMessageMainFragment.getActivity());
        Intent nextActivity=shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent=Shadows.shadowOf(nextActivity);
        assertEquals(MessageListActivity.class,shadowIntent.getIntentClass());

        ll_goucheyouhui.performClick();
        ShadowActivity shadowActivity1= Shadows.shadowOf(mMessageMainFragment.getActivity());
        Intent nextActivity1=shadowActivity1.getNextStartedActivity();
        ShadowIntent shadowIntent1=Shadows.shadowOf(nextActivity1);
        assertEquals(MessageListActivity.class,shadowIntent1.getIntentClass());

        ll_weibaoyouhui.performClick();
        ShadowActivity shadowActivity2= Shadows.shadowOf(mMessageMainFragment.getActivity());
        Intent nextActivity2=shadowActivity2.getNextStartedActivity();
        ShadowIntent shadowIntent2=Shadows.shadowOf(nextActivity2);
        assertEquals(MessageListActivity.class,shadowIntent2.getIntentClass());

        ll_chexun .performClick();
        ShadowActivity shadowActivity3= Shadows.shadowOf(mMessageMainFragment.getActivity());
        Intent nextActivity3=shadowActivity3.getNextStartedActivity();
        ShadowIntent shadowIntent3=Shadows.shadowOf(nextActivity3);
        assertEquals(MessageListActivity.class,shadowIntent3.getIntentClass());

        ll_chezhanhuodong.performClick();
        ShadowActivity shadowActivity4= Shadows.shadowOf(mMessageMainFragment.getActivity());
        Intent nextActivity4=shadowActivity4.getNextStartedActivity();
        ShadowIntent shadowIntent4=Shadows.shadowOf(nextActivity4);
        assertEquals(MessageListActivity.class,shadowIntent4.getIntentClass());

        ll_chedaiyouhui.performClick();
        ShadowActivity shadowActivity5= Shadows.shadowOf(mMessageMainFragment.getActivity());
        Intent nextActivity5=shadowActivity5.getNextStartedActivity();
        ShadowIntent shadowIntent5=Shadows.shadowOf(nextActivity5);
        assertEquals(MessageListActivity.class,shadowIntent5.getIntentClass());

        ll_changtongkahuodong.performClick();
        ShadowActivity shadowActivity6= Shadows.shadowOf(mMessageMainFragment.getActivity());
        Intent nextActivity6=shadowActivity6.getNextStartedActivity();
        ShadowIntent shadowIntent6=Shadows.shadowOf(nextActivity6);
        assertEquals(MessageListActivity.class,shadowIntent6.getIntentClass());

        ll_baoxianyouhui.performClick();
        ShadowActivity shadowActivity7= Shadows.shadowOf(mMessageMainFragment.getActivity());
        Intent nextActivity7=shadowActivity7.getNextStartedActivity();
        ShadowIntent shadowIntent7=Shadows.shadowOf(nextActivity7);
        assertEquals(MessageListActivity.class,shadowIntent7.getIntentClass());

        message_search.performClick();
        ShadowActivity shadowActivity8= Shadows.shadowOf(mMessageMainFragment.getActivity());
        Intent nextActivity8=shadowActivity8.getNextStartedActivity();
        ShadowIntent shadowIntent8=Shadows.shadowOf(nextActivity8);
        assertEquals(MessageSearchActivity.class,shadowIntent8.getIntentClass());
    }


}