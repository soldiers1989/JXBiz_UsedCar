package com.etong.android.jxappcarassistant.violation_query.main_content;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.jxappcarassistant.BuildConfig;
import com.etong.android.jxappcarassistant.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @desc (违章查询界面测试用例)
 * @createtime 2017/1/5 - 9:06
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class CA_ViolationQueryMyCarListActivityTest {

    private ActivityController<CA_ViolationQueryMyCarListActivity> controller;
    private CA_ViolationQueryMyCarListActivity violationQueryMyCarListActivity;
    private TextView addNewCarButton;
    private LinearLayout mListLayout;
    private TextView mTxtDetails;
    private ListView mListView;
    private RelativeLayout mAddQueryView;
    private List<FrameUserInfo.Frame_MyCarItemBean> carList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {

        FrameUserInfo userInfo = new FrameUserInfo();
        userInfo.setUserPhone("13755174943");
        FrameUserInfo.Frame_MyCarItemBean tempData1 = new FrameUserInfo.Frame_MyCarItemBean();
        tempData1.setVtitle("比亚迪-S8 2009款 2.0 手动尊贵型");
        tempData1.setPlate_no("湘A12340");
        tempData1.setVid(95324);
        FrameUserInfo.Frame_MyCarItemBean tempData2 = new FrameUserInfo.Frame_MyCarItemBean();
        tempData2.setVtitle("别克-昂科雷 2014款 3.6L 四驱智享旗舰型");
        tempData2.setPlate_no("湘A87751");
        tempData2.setVid(639716);
        carList.add(tempData1);
        carList.add(tempData2);
        userInfo.setMyCars(carList);
        FrameEtongApplication.getApplication().setUserInfo(userInfo);

        controller = Robolectric.buildActivity(CA_ViolationQueryMyCarListActivity.class).create().start();
        violationQueryMyCarListActivity = controller.get();

        mListLayout = (LinearLayout) violationQueryMyCarListActivity.findViewById(R.id.ca_vq_ll_lv_mycar);
        mTxtDetails = (TextView) violationQueryMyCarListActivity.findViewById(R.id.ca_vq_list_details_title);
        mListView = (ListView) violationQueryMyCarListActivity.findViewById(R.id.ca_vq_lv_mycar);
        mAddQueryView = (RelativeLayout) violationQueryMyCarListActivity.findViewById(R.id.ca_vq_ll_add_query);
        addNewCarButton = (TextView) violationQueryMyCarListActivity.findViewById(com.etong.android.frame.R.id.titlebar_next_button);

        //初始化状态（由于是已登录，需要请求得到数据后设置视图显示与否，但是测试测不了请求所以显示初始化状态）
        assertEquals(View.GONE, addNewCarButton.getVisibility());
        assertEquals(View.GONE, mListLayout.getVisibility());
        assertEquals(View.GONE, mAddQueryView.getVisibility());
        assertEquals(View.GONE, mTxtDetails.getVisibility());
    }

    @Test
    public void queryMyCar() throws Exception {
        assertTrue(mListView.getAdapter().isEmpty());
        //模拟请求得到的结果数据

        HttpMethod method = new HttpMethod("url", null);

        for (int i = 0; i < 4; i++) {
            int condition = i;//0 = 成功; 1 = 成功(数据为空); 2 = NETWORK_ERROR; 3 = DATA_ERROR;
            JSONObject object = new JSONObject();
            switch (condition) {
                case 0:
                    object.put("flag", "0");
                    object.put("data", carList);
                    object.put("msg", "成功");
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

            violationQueryMyCarListActivity.queryMyCar(method);

            if (condition == 1) {
                assertEquals(View.GONE, addNewCarButton.getVisibility());
                assertEquals(View.GONE, mListLayout.getVisibility());
                assertEquals(View.VISIBLE, mAddQueryView.getVisibility());
                assertEquals("亲，您尚未添加车辆，可新增车辆进行查询哦~", mTxtDetails.getText().toString());
                return;
            }

            assertEquals(View.VISIBLE, addNewCarButton.getVisibility());
            assertEquals(View.VISIBLE, mListLayout.getVisibility());
            assertEquals(View.GONE, mAddQueryView.getVisibility());
            assertEquals("亲，请在选择爱车后将车辆信息补充完整，并点击开始查询，即可完成违章查询哦~", mTxtDetails.getText().toString());
            assertFalse(mListView.getAdapter().isEmpty());
        }

    }

    @Test
    public void testClickIntent() throws Exception {
        violationQueryMyCarListActivity.findViewById(R.id.ca_vq_btn_add_query).performClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(violationQueryMyCarListActivity);
        Intent nextIntent = shadowActivity.getNextStartedActivity();
        assertEquals(CA_ViolationQueryActivity.class.getName(), nextIntent.getComponent().getClassName());

        addNewCarButton.performClick();
        ShadowActivity shadow2Activity = Shadows.shadowOf(violationQueryMyCarListActivity);
        Intent next2Intent = shadow2Activity.getNextStartedActivity();
        assertEquals(CA_ViolationQueryActivity.class.getName(), next2Intent.getComponent().getClassName());

        HttpMethod method = new HttpMethod("url", null);
        JSONObject object = new JSONObject();
        object.put("flag", HttpPublisher.NETWORK_ERROR);
        object.put("msg", "访问失败");
        method.put(object);
        violationQueryMyCarListActivity.queryMyCar(method);
        for (int i = 0; i < mListView.getCount(); i++) {
            mListView.getAdapter().getView(i, null, null).performClick();
            ShadowActivity shadowItemActivity = Shadows.shadowOf(violationQueryMyCarListActivity);
            Intent nextItemIntent = shadowItemActivity.getNextStartedActivity();
            assertEquals(CA_ViolationQueryActivity.class.getName(), nextItemIntent.getComponent().getClassName());
        }
    }

//    @Test
//    public void onResume() throws Exception {
//        //登录
//        controller.resume();
//        //初始化状态（由于是已登录，需要请求得到数据后设置视图显示与否，但是测试测不了请求所以显示初始化状态）
//        assertEquals(View.GONE, addNewCarButton.getVisibility());
//        assertEquals(View.GONE, mListLayout.getVisibility());
//        assertEquals(View.GONE, mAddQueryView.getVisibility());
//        assertEquals(View.GONE, mTxtDetails.getVisibility());
//
//        //未登录
//        FrameEtongApplication.getApplication().setUserInfo(null);
//        controller.resume();
//        assertEquals(View.GONE, addNewCarButton.getVisibility());
//        assertEquals(View.GONE, mListLayout.getVisibility());
//        assertEquals(View.VISIBLE, mAddQueryView.getVisibility());
//        assertEquals("亲，您尚未添加车辆，可新增车辆进行查询哦~", mTxtDetails.getText().toString());
//    }

}