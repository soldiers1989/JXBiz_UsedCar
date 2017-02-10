package com.etong.android.jxappcarfinancial.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshBase;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappcarfinancial.BuildConfig;
import com.etong.android.jxappcarfinancial.testData.OrderRecordJsonData;
import com.etong.android.jxappcarfinancial.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @desc (订单记录测试用例)
 * @createtime 2017/1/3 - 15:11
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class CF_OrderRecordActivityTest {

    private CF_OrderRecordActivity orderRecordActivity;
    private PullToRefreshListView mPullListView;
    private ImageView emptyListImg;
    private ViewGroup mTitle;

    @Before
    public void setUp() throws Exception {
        orderRecordActivity = Robolectric.setupActivity(CF_OrderRecordActivity.class);

        mTitle = (ViewGroup) orderRecordActivity.findViewById(R.id.cf_ll_record_head);
        mPullListView = (PullToRefreshListView) orderRecordActivity.findViewById(R.id.cf_order_record_lv);
        emptyListImg = (ImageView) mPullListView.getRefreshableView().getEmptyView().findViewById(R.id.default_empty_img);
        //去掉头部尾部个数
        assertEquals(0, mPullListView.getRefreshableView().getAdapter().getCount() - 2);
        assertTrue(mPullListView.getRefreshableView().getAdapter().isEmpty());
    }

    /**
     * @desc (测试跳转)
     */
    @Test
    public void testListIntent() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("f_ordertype", "3");
        //模拟请求得到的结果数据
        HttpMethod method = new HttpMethod("url", map);
        JSONObject object = JSON.parseObject(OrderRecordJsonData.jsonStr);
        method.put(object);
        //调用请求结果方法使ListView有数据
        orderRecordActivity.addTotalOrderResult(method);

        //由于是PullToRefreshListView，它有头部和尾部布局，所以循环item时要去掉头部和尾部
        for (int i = 1; i < mPullListView.getRefreshableView().getCount() - 1; i++) {
//            mPullListView.getRefreshableView().getChildAt(i).performClick();
            mPullListView.getRefreshableView().getAdapter().getView(i, null, null).performClick();

            ShadowActivity shadowCopActivity = Shadows.shadowOf(orderRecordActivity);
            Intent nextCopIntent = shadowCopActivity.getNextStartedActivity();
            assertEquals(CF_OrderRecordDetailActivity.class.getName(), nextCopIntent.getComponent().getClassName());
        }
    }

    /**
     * @desc (申请进度得到结果后的操作)
     */
    @Test
    public void addTotalOrderResult() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("f_ordertype", "3");
        //模拟请求得到的结果数据
        HttpMethod method = new HttpMethod("url", map);
        for (int i = 0; i < 4; i++) {
            String msg = null;
            int condition = i;//0 = 成功; 1 = 成功(数据为空); 2 = NETWORK_ERROR; 3 = DATA_ERROR;
            JSONObject object = new JSONObject();
            switch (condition) {
                case 0:
                    //将成功的请求结果放进一个String中再转成JSONObject
                    object = JSON.parseObject(OrderRecordJsonData.jsonStr);
                    break;
                case 1:
                    object.put("flag", "0");
                    JSONArray jsonArray = new JSONArray();
                    object.put("data", jsonArray);
                    object.put("msg", "成功");
                    break;
                case 2:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    object.put("msg", "访问失败");
                    break;
                case 3:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    object.put("msg", "数据请求失败!");
                    break;
            }
            method.put(object);

            //调用该请求结果方法
            orderRecordActivity.addTotalOrderResult(method);

            //判断相应处理的结果是否正确
            switch (condition) {
                case 0:
                    assertEquals(View.VISIBLE, mTitle.getVisibility());
                    //由于是PullToRefreshListView，它有头部和尾部布局，所以要去掉头部尾部个数
                    assertEquals(4, mPullListView.getRefreshableView().getAdapter().getCount() - 2);
                    assertEquals(View.GONE, mPullListView.getRefreshableView().getEmptyView().getVisibility());
                    assertFalse(mPullListView.getRefreshableView().getAdapter().isEmpty());
                    break;
                case 1:
                    assertEquals(View.GONE, mTitle.getVisibility());
                    assertEquals(PullToRefreshBase.Mode.DISABLED, mPullListView.getMode());
                    assertEquals(View.VISIBLE, mPullListView.getRefreshableView().getEmptyView().getVisibility());
                    assertTrue(mPullListView.getRefreshableView().getAdapter().isEmpty());
                    break;
                default:
                    assertEquals(View.GONE, mTitle.getVisibility());
                    assertEquals(View.VISIBLE, mPullListView.getRefreshableView().getEmptyView().getVisibility());
                    assertTrue(mPullListView.getRefreshableView().getAdapter().isEmpty());
                    break;
            }
        }
    }

}