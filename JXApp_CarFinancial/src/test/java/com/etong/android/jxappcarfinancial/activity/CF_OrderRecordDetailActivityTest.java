package com.etong.android.jxappcarfinancial.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.FrameEtongApplication;

import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappcarfinancial.BuildConfig;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.testData.OrderRecordDetailJsonData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.*;

/**
 * @desc (申请进度详情测试用例)
 * @createtime 2017/1/3 - 17:19
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class CF_OrderRecordDetailActivityTest {

    private CF_OrderRecordDetailActivity orderRecordDetailActivity;
    private PullToRefreshListView mListView;
    private ImageView emptyListImg;
    private String f_id = "1000537";

    @Before
    public void setUp() throws Exception {

        orderRecordDetailActivity = Robolectric.setupActivity(CF_OrderRecordDetailActivity.class);

        mListView = (PullToRefreshListView) orderRecordDetailActivity.findViewById(R.id.cf_lv_record);
        emptyListImg = (ImageView) mListView.getRefreshableView().getEmptyView().findViewById(R.id.default_empty_img);
        //去掉头部尾部个数
        assertEquals(0, mListView.getRefreshableView().getAdapter().getCount() - 2);
    }

    /**
     * @desc (测试Intent传来的值是否正确)
     */
    @Test
    public void testIntentValue() throws Exception {
        Intent intent = new Intent();
        intent.putExtra("f_id", f_id);

        ActivityController<CF_OrderRecordDetailActivity> controller =
                Robolectric.buildActivity(CF_OrderRecordDetailActivity.class, intent).create().start();
        CF_OrderRecordDetailActivity tempActivity = controller.get();

        assertEquals(f_id, tempActivity.getIntent().getStringExtra("f_id"));
    }

    /**
     * @desc (申请进度详情得到数据后的结果操作测试)
     */
    @Test
    public void queryCarPayOrderResult() throws Exception {
        //模拟请求得到的结果数据
        HttpMethod method = new HttpMethod("url", null);
        for (int i = 0; i < 3; i++) {
            String msg = null;
            int condition = i;//2 = 成功; 1 = NETWORK_ERROR; 0 = DATA_ERROR;
            JSONObject object = new JSONObject();
            switch (condition) {
                case 2:
                    object = JSON.parseObject(OrderRecordDetailJsonData.jsonStr);
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    object.put("msg", "访问失败");
                    break;
                case 0:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    object.put("msg", "数据请求失败!");
                    break;
            }
            method.put(object);

            orderRecordDetailActivity.queryCarPayOrderResult(method);

            switch (condition) {
                case 2:
                    //去掉头部尾部个数
                    assertEquals(8, mListView.getRefreshableView().getAdapter().getCount() - 2);
                    assertFalse(mListView.getRefreshableView().getAdapter().isEmpty());
                    assertEquals(View.GONE, mListView.getRefreshableView().getEmptyView().getVisibility());
                    break;
                default:
                    assertTrue(mListView.getRefreshableView().getAdapter().isEmpty());
                    assertEquals(View.VISIBLE, mListView.getRefreshableView().getEmptyView().getVisibility());
                    break;
            }
        }
    }

}