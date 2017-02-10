package com.etong.android.jxappcarfinancial.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.jxappcarfinancial.BuildConfig;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.utils.CF_CancelConformDialog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowToast;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/12/28 - 17:19
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class CF_ApplyForActivityTest {

    private CF_ApplyForActivity applyForActivity;
    private TextView bindBtn;
    private EditText mEdtName;
    private EditText mEdtPhone;
    private EditText mEdtIdCard;
    private EditText mEdtAdditional;
    private Button mSubmitBtn;

    @Before
    public void setUp() throws Exception {
        applyForActivity = Robolectric.setupActivity(CF_ApplyForActivity.class);
        bindBtn = (TextView) applyForActivity.findViewById(com.etong.android.frame.R.id.titlebar_next_button);

        mEdtName = (EditText) applyForActivity.findViewById(R.id.cf_edt_input_name);                    //姓名
        mEdtPhone = (EditText) applyForActivity.findViewById(R.id.cf_edt_input_tel);                    //手机号码
        mEdtIdCard = (EditText) applyForActivity.findViewById(R.id.cf_edt_input_idcard);                //身份证号码
        mEdtAdditional = (EditText) applyForActivity.findViewById(R.id.cf_edt_input_additional);        //附加信息
        mSubmitBtn = (Button) applyForActivity.findViewById(R.id.cf_btn_put_submit);
        assertNotNull(applyForActivity);
        assertNotNull(bindBtn);

    }

    /**
     * @desc (验证Intent跳转)
     */
    @Test
    public void testJump() throws Exception {

        // 触发按钮点击
        bindBtn.performClick();

        // 获取对应的Shadow类
        ShadowActivity shadowActivity = Shadows.shadowOf(applyForActivity);
        // 借助Shadow类获取启动下一Activity的Intent
        Intent nextIntent = shadowActivity.getNextStartedActivity();
        // 校验Intent的正确性
        assertEquals(nextIntent.getComponent().getClassName(), CF_NoRegisterActivity.class.getName());

    }

    /**
     * @desc (验证申请数据请求结果后的操作)
     */
    @Test
    public void addCarPayOrderResult() throws Exception {

        CF_CancelConformDialog dialog = (CF_CancelConformDialog) ShadowDialog.getLatestDialog();
        assertNull(dialog); // 判断Dialog未弹出

        //模拟请求得到的结果数据
        HttpMethod method = new HttpMethod("url", null);

        for (int i = 0; i < 3; i++) {
            String msg = null;
            int condition = i;//0 = 成功;1 = NETWORK_ERROR; 2 = DATA_ERROR;
            JSONObject object = new JSONObject();
            switch (condition) {
                case 0:
                    object.put("flag", "0");
                    object.put("data", "1000010");
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    object.put("msg", "提交失败，请检查网络！");
                    msg = "提交失败，请检查网络！";
                    break;
                case 2:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    object.put("msg", "数据请求失败!");
                    msg = "数据请求失败!";
                    break;
            }
            method.put(object);

            Toast toast = ShadowToast.getLatestToast();
            // 判断Toast尚未弹出
            assertNull(toast);

            applyForActivity.addCarPayOrderResult(method);

            switch (condition) {
                case 0:
                    dialog = (CF_CancelConformDialog) ShadowDialog.getLatestDialog();
                    assertNotNull(dialog); // 判断Dialog已经弹出
                    break;
                default:
                    toast = ShadowToast.getLatestToast();
                    // 判断Toast已经弹出
                    assertNotNull(toast);
                    // 获取Shadow类进行验证
                    ShadowToast shadowToast = shadowOf(toast);
                    assertEquals(Toast.LENGTH_SHORT, shadowToast.getDuration());
                    assertEquals(msg, shadowToast.getTextOfLatestToast());

                    ShadowToast.reset();

                    break;
            }

        }
    }

    /**
     * @desc (验证输入操作)
     */
    @Test
    public void onSubmit() throws Exception {

        Toast toast = ShadowToast.getLatestToast();
        // 判断Toast尚未弹出
        assertNull(toast);

        applyForActivity.onSubmit();
        toast = ShadowToast.getLatestToast();
        // 判断Toast已经弹出
        assertNotNull(toast);
        // 获取Shadow类进行验证
        ShadowToast shadowToast = shadowOf(toast);
        assertEquals(Toast.LENGTH_SHORT, shadowToast.getDuration());
        assertEquals("请输入姓名！", shadowToast.getTextOfLatestToast());

        mEdtName.setText("   ");
        applyForActivity.onSubmit();
        toast = ShadowToast.getLatestToast();
        assertEquals("请输入姓名！", shadowToast.getTextOfLatestToast());

        mEdtName.setText("哈哈哈");
        applyForActivity.onSubmit();
        toast = ShadowToast.getLatestToast();
        assertEquals("请输入手机号码！", shadowToast.getTextOfLatestToast());

        mEdtPhone.setText("1375455");
        applyForActivity.onSubmit();
        toast = ShadowToast.getLatestToast();
        assertEquals("请输入正确的手机号！", shadowToast.getTextOfLatestToast());

        mEdtPhone.setText("11155174943");
        applyForActivity.onSubmit();
        toast = ShadowToast.getLatestToast();
        assertEquals("请输入正确的手机号！", shadowToast.getTextOfLatestToast());

        mEdtPhone.setText("13755174943");
        assertTrue(mSubmitBtn.isClickable());
        applyForActivity.onSubmit();
        assertFalse(mSubmitBtn.isClickable());
        mSubmitBtn.setClickable(true);

        mEdtIdCard.setText("430534");
        applyForActivity.onSubmit();
        toast = ShadowToast.getLatestToast();
        assertEquals("请输入完整身份证号", shadowToast.getTextOfLatestToast());

        mEdtIdCard.setText("xxxxxxxxxxxxxxxxxx");
        applyForActivity.onSubmit();
        toast = ShadowToast.getLatestToast();
        assertEquals("请输入正确的身份证号", shadowToast.getTextOfLatestToast());

        mEdtIdCard.setText("430681199514282622");
        applyForActivity.onSubmit();
        toast = ShadowToast.getLatestToast();
        assertEquals("请输入正确的身份证号", shadowToast.getTextOfLatestToast());

        mEdtIdCard.setText("430681199504282622");
        assertTrue(mSubmitBtn.isClickable());
        applyForActivity.onSubmit();
        assertFalse(mSubmitBtn.isClickable());
        mSubmitBtn.setClickable(true);

        mEdtAdditional.setText("这是附加的。。。");
        assertTrue(mSubmitBtn.isClickable());
        applyForActivity.onSubmit();
        assertFalse(mSubmitBtn.isClickable());
        mSubmitBtn.setClickable(true);
    }

    @Test
    public void testInitUserInfo() throws Exception {
        FrameUserInfo userInfo = new FrameUserInfo();

        userInfo.setUserPhone("18229938144");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        applyForActivity.initUserInfo();
        assertEquals("18229938144",mEdtPhone.getText().toString());
        assertTrue(TextUtils.isEmpty(mEdtName.getText().toString()));
        assertTrue(TextUtils.isEmpty(mEdtIdCard.getText().toString()));



        userInfo.setUserName("呵呵");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        applyForActivity.initUserInfo();
        assertEquals("18229938144",mEdtPhone.getText().toString());
        assertEquals("呵呵",mEdtName.getText().toString());
        assertTrue(TextUtils.isEmpty(mEdtIdCard.getText().toString()));

        userInfo.setUserIdCard("324541199504282322");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        applyForActivity.initUserInfo();
        assertEquals("18229938144",mEdtPhone.getText().toString());
        assertEquals("呵呵",mEdtName.getText().toString());
        assertEquals("324541199504282322",mEdtIdCard.getText().toString());


        userInfo.setF_phone("13755174943");
        userInfo.setF_name("呵呵");
        userInfo.setF_cardId("430681199504282322");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        applyForActivity.initUserInfo();
        assertEquals("13755174943",mEdtPhone.getText().toString());
        assertEquals("呵呵",mEdtName.getText().toString());
        assertEquals("430681199504282322",mEdtIdCard.getText().toString());
    }

}