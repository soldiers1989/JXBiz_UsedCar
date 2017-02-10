package com.etong.android.jxappcarfinancial.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.jxappcarfinancial.BuildConfig;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.bean.CF_AllOrderRecordBean;
import com.etong.android.jxappcarfinancial.bean.CF_AllOrderRecordCountBean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * @desc (所有订单测试用例)
 * @createtime 2016/12/30 - 16:16
 * @Created by wukefan.
 * 注释：测试前需注释所要测试的代码中的loadStart
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class CF_AllOrderRecordActivityTest {

    private CF_AllOrderRecordActivity allOrderRecordActivity;
    private ListView mListView;
    private TextView resultHead;
    private ViewGroup resultHeadView;
    private ViewGroup emptyListContent;

    @Before
    public void setUp() throws Exception {
        CF_AllOrderRecordActivity.isTestDebug = true;
        allOrderRecordActivity = Robolectric.setupActivity(CF_AllOrderRecordActivity.class);

        mListView = (ListView) allOrderRecordActivity.findViewById(R.id.cf_lv_all_order_record);
        emptyListContent = (ViewGroup) mListView.getEmptyView().findViewById(R.id.default_empty_content);
    }

    /**
     * @desc (订单记录接口回调后操作测试)
     */
    @Test
    public void queryMyOrderResult() throws Exception {

        //模拟请求得到的结果数据
        HttpMethod method = new HttpMethod("url", null);
        int count = 5;
        for (int i = 0; i < 3; i++) {
            String msg = null;
            int condition = i;//0 = 成功;1 = NETWORK_ERROR; 2 = DATA_ERROR;
            JSONObject object = new JSONObject();
            switch (condition) {
                case 0:
                    CF_AllOrderRecordCountBean bean = new CF_AllOrderRecordCountBean();
                    bean.setFinancialTotal(count);
                    bean.setTotalCount(count);
                    object.put("flag", "0");
                    object.put("data", bean);
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    object.put("msg", "提交失败，请检查网络！");
                    break;
                case 2:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    object.put("msg", "数据请求失败!");
                    break;
            }
            method.put(object);

            allOrderRecordActivity.queryMyOrderResult(method);
            //头部布局需要在设置了适配器后才能得到否则为空
            resultHead = (TextView) mListView.getChildAt(0).findViewById(R.id.cf_txt_head_all_or);
            resultHeadView = (ViewGroup) mListView.getChildAt(0).findViewById(R.id.cf_ll_head_all_or);


            switch (condition) {
                case 0:
                    assertEquals(View.VISIBLE, resultHeadView.getVisibility());
                    assertEquals(View.GONE, emptyListContent.getVisibility());
                    assertEquals("总计" + count + "条订单~", resultHead.getText().toString());
                    assertEquals(8, mListView.getCount() - 1);
                    assertEquals(8, mListView.getChildCount() - 1);
                    break;
                default:
                    assertEquals(View.GONE, resultHeadView.getVisibility());
                    assertEquals(View.VISIBLE, emptyListContent.getVisibility());
                    break;
            }

        }

    }

}