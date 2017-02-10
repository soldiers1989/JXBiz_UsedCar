package com.etong.android.jxappusedcar.content;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameConstant;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.widget.EtongNoScrollListView;
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
import org.robolectric.shadows.ShadowToast;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

/**
 * @desc (搜索页的测试用例)
 * @createtime 2016/12/29 - 15:12
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, application = FrameEtongApplication.class, constants = BuildConfig.class)
public class UC_World_SearchActivityTest {

    private UC_World_SearchActivity mActivity;
    private ActivityController<UC_World_SearchActivity> controller;
    private LinearLayout searchHistory;
    private PullToRefreshListView mListViewResult;


    @Before
    public void setUp() throws Exception {
        controller = Robolectric.buildActivity(UC_World_SearchActivity.class);
        controller.create().start();
        mActivity = controller.get();
        searchHistory = (LinearLayout) mActivity.findViewById(R.id.used_car_ll_search_history);
        mListViewResult = (PullToRefreshListView)mActivity.findViewById(R.id.used_car_search_more_result);
        assertNotNull(mActivity);
        assertNotNull(searchHistory);
        assertNotNull(mListViewResult);
    }

    /**
     * @desc (测试onResume())
     * @createtime 2016/12/29 - 16:05
     * @author xiaoxue
     */
    @Test
    public void onResume() throws Exception {
        assertNull(FrameEtongApplication.getApplication().getSearchUsedCarHistory());
        FrameEtongApplication.getApplication().addSearchUsedCarHistory("大众-途锐 2016款 3.0TSI 标配型");
        controller.resume();
        assertNotNull(FrameEtongApplication.getApplication().getSearchUsedCarHistory());
        int viewVisibility = searchHistory.getVisibility();
        assertEquals(View.VISIBLE, viewVisibility);
    }

    //处理搜索出来的数据
    @Test
    public void getCarList() throws Exception {
        assertTrue(mListViewResult.getRefreshableView().getAdapter().isEmpty());
        for (int i = 0; i < 3; i++) {
            int flagValue = 0;   //0:成功 2：没网  3：没服务
            String msgValue = "";
            Map<String, String> params = new HashMap<>();
            params.put("pageSize", "10");
            params.put("pageCurrent", "0");
            params.put("title", "大众-途锐 2016款 3.0TSI 标配型");
            HttpMethod method = new HttpMethod(FrameConstant.CURRENT_SERVICE + "appCar/queryCarlist.do", params);
            JSONObject jsonObject = new JSONObject();
            switch (flagValue) {
                case 0:
                    jsonObject.put("flag", 0);
                    JSONArray array = new JSONArray();
//                    JSONObject object = new JSONObject();
                    UC_World_CarListJavaBean bean = new UC_World_CarListJavaBean();
                    bean.setF_cartitle("大众-途锐 2016款 3.0TSI 标配型");
                    bean.setF_cartypeid(768648);
                    bean.setF_dvid("00000173");
                    JSONObject object= (JSONObject) JSON.toJSON(bean);
                    array.add(object);
                    jsonObject.put("data",array);
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
            /*Toast toast = ShadowToast.getLatestToast();
            // 判断Toast尚未弹出
            assertNull(toast);*/
            // 验证状态是否正确
//            assertTrue(mListViewResult.getRefreshableView().getAdapter().isEmpty());
            mActivity.getCarList(method.put(jsonObject));
            switch (flagValue) {
                case 0:
                    assertFalse(mListViewResult.getRefreshableView().getAdapter().isEmpty());
//                    assertEquals(0,mListViewResult.getRefreshableView().getChildCount());
                    for (int k = 2; k < mListViewResult.getRefreshableView().getCount() - 1; k++) {
                        mListViewResult.getRefreshableView().getAdapter().getView(k,null,null).performClick();
                        ShadowActivity shadowActivity = Shadows.shadowOf(mActivity);
                        Intent nextActivity = shadowActivity.getNextStartedActivity();
                        ShadowIntent shadowIntent = Shadows.shadowOf(nextActivity);
                        Assert.assertEquals(UC_CarDetailActivity.class, shadowIntent.getIntentClass());
                    }
                    break;
                case 1:
                    mListViewResult.setVisibility(View.GONE);
                    Assert.assertEquals(View.GONE, mListViewResult.getVisibility());
                    break;
                case 2:
                    mListViewResult.setVisibility(View.GONE);
                    Assert.assertEquals(View.GONE, mListViewResult.getVisibility());
                    break;
               /* default:
                    toast = ShadowToast.getLatestToast();
                    // 判断Toast已经弹出
                    assertNotNull(toast);
                    // 获取Shadow类进行验证
                    ShadowToast shadowToast = shadowOf(toast);
                    assertEquals(Toast.LENGTH_SHORT, shadowToast.getDuration());
                    assertEquals(msgValue, shadowToast.getTextOfLatestToast());
                    ShadowToast.reset();//清除之前弹过的toast
                    break;*/
            }
        }
    }

}