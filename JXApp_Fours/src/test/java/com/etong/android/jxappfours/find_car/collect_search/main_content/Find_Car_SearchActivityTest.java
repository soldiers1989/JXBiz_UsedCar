package com.etong.android.jxappfours.find_car.collect_search.main_content;

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
import com.etong.android.jxappfours.BuildConfig;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.collect_search.javabean.Find_Car_Search_Result;
import com.etong.android.jxappfours.find_car.grand.carset.FC_CarsetActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

/**
 * @desc (搜索 测试用例)
 * @createtime 2016/12/27 - 16:22
 * @Created by xiaoxue.
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk=21,constants = BuildConfig.class,application = FrameEtongApplication.class)
public class Find_Car_SearchActivityTest {

    private TextView cancel;
    ActivityController<Find_Car_SearchActivity> controller;
    Find_Car_SearchActivity mActivity;
    private LinearLayout search_history;
    private AutoCompleteTextView search_text;
    private TextView clear;
    private ListView listview;

    @Before
    public void setUp() throws Exception{
        controller=Robolectric.buildActivity(Find_Car_SearchActivity.class);
        controller.create().start();
        mActivity=controller.get();
        listview = (ListView)mActivity.findViewById(R.id.find_car_search_more_result);
        search_history =(LinearLayout)mActivity.findViewById(R.id.find_car_ll_search_history);
        search_text =(AutoCompleteTextView)mActivity.findViewById(R.id.find_car_tct_search_content);
        search_text.setText("宝马");
        clear =(TextView)mActivity.findViewById(R.id.find_car_txt_cancel);
    }

    @Test
    public void onResume() throws Exception {
        assertNull(FrameEtongApplication.getApplication().getSearchHistory());
        FrameEtongApplication.getApplication().addSearchHistory("宝马");
        controller.resume();
        assertNotNull(FrameEtongApplication.getApplication().getSearchHistory());
        int viewVisibility = search_history.getVisibility();
        assertEquals(View.VISIBLE, viewVisibility);
    }

    //点击事件
    @Test
    public void onClick() throws Exception {
        FrameEtongApplication.getApplication().addSearchHistory("宝马");
        FrameEtongApplication.getApplication().addSearchHistory("奥迪");
        assertNotNull(FrameEtongApplication.getApplication().getSearchHistory());
        clear.performClick();
        FrameEtongApplication.getApplication().clearSearchHistory();
        assertNull(FrameEtongApplication.getApplication().getSearchHistory());
    }

    //处理搜索的数据
    @Test
    public void getSearch() throws Exception {
        assertTrue(listview.getAdapter().isEmpty());
        for (int i = 0; i < 3; i++) {
            int flagValue = 0;   //0:成功 2：没网  3：没服务
            String msgValue = "";
            Map<String, String> params = new HashMap<>();
            params.put("key","宝马");
            HttpMethod method = new HttpMethod(FrameConstant.CURRENT_SERVICE + "car/queryCarsetByKey/"+"宝马"+".do", params);
            JSONObject jsonObject = new JSONObject();
            switch (flagValue) {
                case 0:
                    jsonObject.put("flag", 0);
                    jsonObject.put("errno","PT_ERROR_SUCCESS");
                    JSONArray array = new JSONArray();
                    Find_Car_Search_Result bean = new Find_Car_Search_Result();
                    bean.setId(149);
                    bean.setFullName("一汽奥迪A6L");
                    bean.setPid(146);
                    bean.setPTitle("一汽奥迪");
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
            mActivity.getResult(method.put(jsonObject));
            assertEquals("宝马",params.get("key"));
            assertEquals(params.get("key"),search_text.getText().toString());
            switch (flagValue) {
                case 0:
                    assertFalse(listview.getAdapter().isEmpty());
                    for(int k = 1; k< listview.getCount(); k++){
                        listview.getAdapter().getView(k,null,null).performClick();
                        ShadowActivity shadowActivity= Shadows.shadowOf(mActivity);
                        assertNotNull(shadowActivity);
                        Intent nextIntent = shadowActivity.getNextStartedActivity();
                        assertNotNull(nextIntent);
                        assertEquals(FC_CarsetActivity.class.getName(),nextIntent.getComponent().getClassName());
                    }
                    break;
                case 1:
                    assertEquals(View.GONE, listview.getVisibility());
                    break;
                case 2:
                    assertEquals(View.GONE, listview.getVisibility());
                    break;
            }
        }

    }
}