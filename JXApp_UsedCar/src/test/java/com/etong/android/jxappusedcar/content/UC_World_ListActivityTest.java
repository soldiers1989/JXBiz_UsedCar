package com.etong.android.jxappusedcar.content;

import android.content.Intent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameConstant;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappusedcar.BuildConfig;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.javabean.UC_World_CarListJavaBean;

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

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @desc (二手车列表测试用例)
 * @createtime 2016/12/27 - 16:03
 * @Created by xiaoxue.
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class UC_World_ListActivityTest {
    private UC_World_ListActivity activity;
    private PullToRefreshListView mListView;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(UC_World_ListActivity.class);
        Assert.assertNotNull(activity);
        mListView = (PullToRefreshListView) activity.findViewById(R.id.used_car_lv_car);
    }

    @Test
    public void getCarList() throws Exception {
        assertTrue(mListView.getRefreshableView().getAdapter().isEmpty());
        for (int i = 0; i < 3; i++) {
            int flagValue = 0;
            Map<String, String> params = new HashMap<>();
            params.put("sortType", 0 + "");
            params.put("pageSize", 10 + "");
            params.put("pageCurrent", 0 + "");
            params.put("isPullDown", 0 + "");
            HttpMethod method = new HttpMethod(FrameConstant.CURRENT_SERVICE + "appCar/queryCarlist.do", params);
            JSONObject object = new JSONObject();
            switch (flagValue) {
                case 0:
                    object.put("flag", 0);
                    JSONArray array = new JSONArray();
                    UC_World_CarListJavaBean bean = new UC_World_CarListJavaBean();
                    bean.setF_dvid("00000186");
                    bean.setF_cartitle("比亚迪-S8 2009款 2.0手动尊贵型");
                    bean.setF_cartypeid(95324);
                    bean.setF_price(5.20);
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
            activity.getCarList(method.put(object));
            switch (flagValue){
                case 0:
                    assertFalse(mListView.getRefreshableView().getAdapter().isEmpty());
//        assertEquals(0, mListView.getRefreshableView().getChildCount());
                    for (int k = 1; k < mListView.getRefreshableView().getCount() - 1; k++) {
                        mListView.getRefreshableView().getChildAt(k).performClick();
                        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
                        Intent nextActivity = shadowActivity.getNextStartedActivity();
                        ShadowIntent shadowIntent = Shadows.shadowOf(nextActivity);
                        assertEquals(UC_CarDetailActivity.class, shadowIntent.getIntentClass());
                    }
                    break;
                case 1:
                    mListView.setVisibility(View.GONE);
                    Assert.assertEquals(View.GONE, mListView.getVisibility());
                    break;
                case 2:
                    mListView.setVisibility(View.GONE);
                    Assert.assertEquals(View.GONE, mListView.getVisibility());
                    break;
            }
        }
    }
}