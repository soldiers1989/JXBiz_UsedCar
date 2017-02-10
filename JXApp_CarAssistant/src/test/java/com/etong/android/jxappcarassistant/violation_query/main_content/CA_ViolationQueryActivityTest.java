package com.etong.android.jxappcarassistant.violation_query.main_content;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.jxappcarassistant.BuildConfig;
import com.etong.android.jxappcarassistant.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.util.ActivityController;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

/**
 * @desc (违章查询界面测试用例)
 * @createtime 2017/1/5 - 11:03
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class CA_ViolationQueryActivityTest {


    private CA_ViolationQueryActivity violationQueryActivity;
    private LinearLayout mViolationQueryView;
    private TextView mTxtDetails;
    private Button mBtnPlate;
    private EditText mEdtPlate;
    private EditText mEdtChassis;
    private EditText mEdtEngine;
    private Button mCommit;

    private String jsonStr = "{\"lsnum\":\"A1E79H\",\n" +
            "\"usercarid\":\"4966276\",\n" +
            "\"lsprefix\":\"湘\",\n" +
            "\"carorg\":\"changsha\",\n" +
            "\"list\":[{\"score\":\"3\"," +
            "\"address\":\"长沙市枫林路的白云路路口\"," +
            "\"agency\":\"\"," +
            "\"illegalid\":\"6262825\"," +
            "\"price\":\"100\"," +
            "\"time\":\"2016-09-24 23:22:49\"," +
            "\"legalnum\":\"1345\"," +
            "\"content\":\"机动车违反禁止标线指示的\"}]}";

    @Before
    public void setUp() throws Exception {
        violationQueryActivity = Robolectric.setupActivity(CA_ViolationQueryActivity.class);

        mViolationQueryView = (LinearLayout) violationQueryActivity.findViewById(R.id.ca_vq_ll);
        mTxtDetails = (TextView) violationQueryActivity.findViewById(R.id.ca_vq_txt_detail_title);

        mBtnPlate = (Button) violationQueryActivity.findViewById(R.id.ca_vq_btn_type);
        mEdtPlate = (EditText) violationQueryActivity.findViewById(R.id.ca_vq_edt_plate);
        mEdtChassis = (EditText) violationQueryActivity.findViewById(R.id.ca_vq_edt_chassis);
        mEdtEngine = (EditText) violationQueryActivity.findViewById(R.id.ca_vq_edt_engine);
        mCommit = (Button) violationQueryActivity.findViewById(R.id.ca_vq_btn_commit);
    }

    /**
     * @desc (测试Intent传过来的值以及后续操作)
     */
    @Test
    public void testIntentValue() throws Exception {
        Intent intent = new Intent();
        intent.putExtra("isHasCar", true);
        FrameUserInfo.Frame_MyCarItemBean mMyCarItemBean = new FrameUserInfo.Frame_MyCarItemBean();
        mMyCarItemBean.setVid(769596);
        mMyCarItemBean.setCarsetId(4922);
        mMyCarItemBean.setVtitle("AC Schnitzer X52015款ACS35 35i");
        mMyCarItemBean.setPlate_no("湘A9543G");
        mMyCarItemBean.setEngine_no("878787");
        mMyCarItemBean.setChassis_no("FDKFR4532FVDSFDFF");
        intent.putExtra("mFours_MyCarItemBean", mMyCarItemBean);

        ActivityController<CA_ViolationQueryActivity> carActivityController =
                Robolectric.buildActivity(CA_ViolationQueryActivity.class, intent).create().start();

        CA_ViolationQueryActivity testActivity = carActivityController.get();

        FrameUserInfo.Frame_MyCarItemBean getBean =
                (FrameUserInfo.Frame_MyCarItemBean) testActivity.getIntent().getSerializableExtra("mFours_MyCarItemBean");
        assertEquals(mMyCarItemBean, getBean);
        assertTrue(testActivity.getIntent().getBooleanExtra("isHasCar", false));

        Button mBtn2Plate = (Button) testActivity.findViewById(R.id.ca_vq_btn_type);
        EditText mEdt2Plate = (EditText) testActivity.findViewById(R.id.ca_vq_edt_plate);
        EditText mEdt2Chassis = (EditText) testActivity.findViewById(R.id.ca_vq_edt_chassis);
        EditText mEdt2Engine = (EditText) testActivity.findViewById(R.id.ca_vq_edt_engine);

        assertEquals("湘", mBtn2Plate.getText().toString());
        assertEquals("A9543G", mEdt2Plate.getText().toString());
        assertEquals("FDKFR4532FVDSFDFF", mEdt2Chassis.getText().toString());
        assertEquals("878787", mEdt2Engine.getText().toString());

        assertFalse(mBtn2Plate.isEnabled());
        assertFalse(mEdt2Plate.isEnabled());
        assertFalse(mEdt2Chassis.isEnabled());
        assertFalse(mEdt2Engine.isEnabled());
    }

    /**
     * @desc (提交违章查询操作测试)
     */
    @Test
    public void onCommit() throws Exception {
        Toast toast = ShadowToast.getLatestToast();
        // 判断Toast尚未弹出
        assertNull(toast);

        violationQueryActivity.onCommit();
        toast = ShadowToast.getLatestToast();
        // 判断Toast已经弹出
        assertNotNull(toast);
        // 获取Shadow类进行验证
        ShadowToast shadowToast = shadowOf(toast);
        assertEquals(Toast.LENGTH_SHORT, shadowToast.getDuration());
        assertEquals("请输入完整车牌号", shadowToast.getTextOfLatestToast());

        mEdtPlate.setText("A24");
        violationQueryActivity.onCommit();
        assertEquals("请输入完整车牌号", shadowToast.getTextOfLatestToast());

        mEdtPlate.setText("A245SW");
        violationQueryActivity.onCommit();
        assertEquals("请输入完整发动机号", shadowToast.getTextOfLatestToast());

        mEdtEngine.setText("234");
        violationQueryActivity.onCommit();
        assertEquals("请输入完整发动机号", shadowToast.getTextOfLatestToast());

        mEdtEngine.setText("234567");
        violationQueryActivity.onCommit();
        assertEquals("请输入完整车架号", shadowToast.getTextOfLatestToast());

        mEdtChassis.setText("FRHYHH");
        violationQueryActivity.onCommit();
        assertEquals("请输入完整车架号", shadowToast.getTextOfLatestToast());


        assertTrue(mCommit.isClickable());
        mEdtChassis.setText("FDKFR4532FVDSFDFF");
        violationQueryActivity.onCommit();
        assertFalse(mCommit.isClickable());
        mCommit.setClickable(true);

    }

    /**
     * @desc (测试提交违章查询请求结果操作)
     */
    @Test
    public void getViolationQuery() throws Exception {
//模拟请求得到的结果数据
        HttpMethod method = new HttpMethod("url", null);

        for (int i = 0; i < 4; i++) {
            String msg = null;
            int condition = i;//0 = 成功;1 = NETWORK_ERROR; 2 = DATA_ERROR;
            JSONObject object = new JSONObject();
            switch (condition) {
                case 0:
                    object.put("flag", "0");
                    object.put("data", jsonStr);
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    object.put("msg", "查询失败，请检查网络！");
                    msg = "查询失败，请检查网络！";
                    break;
                case 2:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    object.put("msg", "数据请求失败!");
                    msg = "数据请求失败!";
                    break;
                case 3:
                    object.put("flag", "1");
                    object.put("msg", "交管局为空");
                    msg = "交管局为空";
                    break;
            }
            method.put(object);

            Toast toast = ShadowToast.getLatestToast();
            // 判断Toast尚未弹出
            assertNull(toast);

            violationQueryActivity.getViolationQuery(method);

            if (condition == 0) {
                ShadowActivity shadowItemActivity = Shadows.shadowOf(violationQueryActivity);
                Intent nextItemIntent = shadowItemActivity.getNextStartedActivity();
                Assert.assertEquals(CA_ViolationOfTheQueryActivity.class.getName(), nextItemIntent.getComponent().getClassName());
              return;
            }

            toast = ShadowToast.getLatestToast();
            // 判断Toast已经弹出
            assertNotNull(toast);
            // 获取Shadow类进行验证
            ShadowToast shadowToast = shadowOf(toast);
            assertEquals(Toast.LENGTH_SHORT, shadowToast.getDuration());
            assertEquals(msg, shadowToast.getTextOfLatestToast());

            ShadowToast.reset();
        }
    }
}