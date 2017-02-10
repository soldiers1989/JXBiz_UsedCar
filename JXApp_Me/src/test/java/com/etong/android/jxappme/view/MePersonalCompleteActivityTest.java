package com.etong.android.jxappme.view;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.utils.PhotoHeadUtils;
import com.etong.android.frame.widget.CircleImageView;
import com.etong.android.jxappme.BuildConfig;
import com.etong.android.jxappme.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

/**
 * @desc (完善个人信息界面)
 * @createtime 2017/1/4 - 15:23
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class MePersonalCompleteActivityTest {

    private MePersonalCompleteActivity mePersonalCompleteActivity;
    private CircleImageView compileHead;
    private EditText compilePhone;
    private EditText compileName;
    private EditText compileIdCard;
    private Button saveBtn;
    private RadioButton[] mSexRadioButtons = new RadioButton[2];
    private RadioButton[] mMarryRadioButtons = new RadioButton[2];
    private FrameEtongApplication frameEtongApplication;
    private FrameUserInfo userInfo;

    @Before
    public void setUp() throws Exception {
        userInfo = new FrameUserInfo();
        userInfo.setUserPhone("13755174943");
        frameEtongApplication = FrameEtongApplication.getApplication();
        frameEtongApplication.setUserInfo(userInfo);
        mePersonalCompleteActivity = Robolectric.setupActivity(MePersonalCompleteActivity.class);

        compileHead = (CircleImageView) mePersonalCompleteActivity.findViewById(R.id.me_img_compile_head);         //头像
        compilePhone = (EditText) mePersonalCompleteActivity.findViewById(R.id.me_edt_compile_tel);                //手机号码
        compileName = (EditText) mePersonalCompleteActivity.findViewById(R.id.me_edt_compile_name);                //姓名
        compileIdCard = (EditText) mePersonalCompleteActivity.findViewById(R.id.me_edt_compile_idcard);            //身份证号码
        saveBtn = (Button) mePersonalCompleteActivity.findViewById(R.id.me_btn_put_save);                          //保存按钮

        mSexRadioButtons[0] = (RadioButton) mePersonalCompleteActivity.findViewById(R.id.me_compile_sex_man);
        mSexRadioButtons[1] = (RadioButton) mePersonalCompleteActivity.findViewById(R.id.me_compile_sex_girl);
        mMarryRadioButtons[0] = (RadioButton) mePersonalCompleteActivity.findViewById(R.id.me_compile_single);
        mMarryRadioButtons[1] = (RadioButton) mePersonalCompleteActivity.findViewById(R.id.me_compile_married);

    }

    /**
     * @desc (测试从缓存中得到用户信息并设置)
     */
    @Test
    public void getInfo() throws Exception {
        mePersonalCompleteActivity.getInfo();
        assertEquals("13755174943", compilePhone.getText().toString());
        assertTrue(TextUtils.isEmpty(compileName.getText().toString()));
        assertTrue(TextUtils.isEmpty(compileIdCard.getText().toString()));
        assertTrue(mSexRadioButtons[0].isChecked());
        assertTrue(mMarryRadioButtons[0].isChecked());
        assertFalse(compilePhone.isEnabled());

        userInfo.setUserName("    ");
        frameEtongApplication.setUserInfo(userInfo);
        mePersonalCompleteActivity.getInfo();
        assertTrue(TextUtils.isEmpty(compileName.getText().toString()));
        assertTrue(TextUtils.isEmpty(compileIdCard.getText().toString()));
        assertTrue(mSexRadioButtons[0].isChecked());
        assertTrue(mMarryRadioButtons[0].isChecked());
        assertFalse(compilePhone.isEnabled());


        userInfo.setUserName("哈哈");
        frameEtongApplication.setUserInfo(userInfo);
        mePersonalCompleteActivity.getInfo();
        assertEquals("哈哈", compileName.getText().toString());
        assertTrue(TextUtils.isEmpty(compileIdCard.getText().toString()));
        assertTrue(mSexRadioButtons[0].isChecked());
        assertTrue(mMarryRadioButtons[0].isChecked());

        userInfo.setUserIdCard("     ");
        frameEtongApplication.setUserInfo(userInfo);
        mePersonalCompleteActivity.getInfo();
        assertEquals("哈哈", compileName.getText().toString());
        assertTrue(TextUtils.isEmpty(compileIdCard.getText().toString()));
        assertTrue(mSexRadioButtons[0].isChecked());
        assertTrue(mMarryRadioButtons[0].isChecked());

        userInfo.setUserIdCard("430681199403212333");
        frameEtongApplication.setUserInfo(userInfo);
        mePersonalCompleteActivity.getInfo();
        assertEquals("哈哈", compileName.getText().toString());
        assertEquals("430681199403212333", compileIdCard.getText().toString());
        assertTrue(mSexRadioButtons[0].isChecked());
        assertTrue(mMarryRadioButtons[0].isChecked());

        userInfo.setUserSex("女");
        frameEtongApplication.setUserInfo(userInfo);
        mePersonalCompleteActivity.getInfo();
        assertEquals("哈哈", compileName.getText().toString());
        assertEquals("430681199403212333", compileIdCard.getText().toString());
        assertTrue(mSexRadioButtons[1].isChecked());
        assertTrue(mMarryRadioButtons[0].isChecked());

        userInfo.setUserMarry("已婚");
        frameEtongApplication.setUserInfo(userInfo);
        mePersonalCompleteActivity.getInfo();
        assertEquals("哈哈", compileName.getText().toString());
        assertEquals("430681199403212333", compileIdCard.getText().toString());
        assertTrue(mSexRadioButtons[1].isChecked());
        assertTrue(mMarryRadioButtons[1].isChecked());

        //还原设置
        userInfo.setUserMarry(null);
        userInfo.setUserSex(null);
        userInfo.setUserIdCard(null);
        userInfo.setUserName(null);
    }

    /**
     * @desc (测试提交修改的用户信息的操作)
     */
    @Test
    public void setUserInfo() throws Exception {
        Toast toast = ShadowToast.getLatestToast();
        // 判断Toast尚未弹出
        assertNull(toast);
        mePersonalCompleteActivity.setUserInfo();
        assertFalse(saveBtn.isClickable());
        saveBtn.setClickable(true);


        compileIdCard.setText("432034");
        mePersonalCompleteActivity.setUserInfo();
        toast = ShadowToast.getLatestToast();
        ShadowToast shadowToast = shadowOf(toast);
        assertEquals("请输入完整身份证号", shadowToast.getTextOfLatestToast());
        assertTrue(saveBtn.isClickable());

        compileIdCard.setText("432034199403xxxxxx");
        mePersonalCompleteActivity.setUserInfo();
        toast = ShadowToast.getLatestToast();
        ShadowToast shadowToast2 = shadowOf(toast);
        assertEquals("请输入正确的身份证号", shadowToast2.getTextOfLatestToast());
        assertTrue(saveBtn.isClickable());

        compileIdCard.setText("      ");
        mePersonalCompleteActivity.setUserInfo();
        assertFalse(saveBtn.isClickable());
        saveBtn.setClickable(true);
        assertEquals("", FrameEtongApplication.getApplication().getUserInfo().getUserIdCard());

        compileIdCard.setText("432034199403223233");
        mePersonalCompleteActivity.setUserInfo();
        assertFalse(saveBtn.isClickable());
        saveBtn.setClickable(true);
        assertEquals("432034199403223233", FrameEtongApplication.getApplication().getUserInfo().getUserIdCard());

        compileName.setText("      ");
        mePersonalCompleteActivity.setUserInfo();
        assertFalse(saveBtn.isClickable());
        saveBtn.setClickable(true);
        assertEquals("", FrameEtongApplication.getApplication().getUserInfo().getUserName());

        compileName.setText("哈哈");
        mePersonalCompleteActivity.setUserInfo();
        assertFalse(saveBtn.isClickable());
        saveBtn.setClickable(true);
        assertEquals("哈哈", FrameEtongApplication.getApplication().getUserInfo().getUserName());

        mSexRadioButtons[0].setChecked(true);
        mePersonalCompleteActivity.setUserInfo();
        assertFalse(mSexRadioButtons[1].isChecked());
        assertFalse(saveBtn.isClickable());
        saveBtn.setClickable(true);
        assertEquals("男", FrameEtongApplication.getApplication().getUserInfo().getUserSex());

        mSexRadioButtons[1].setChecked(true);
        mePersonalCompleteActivity.setUserInfo();
        assertFalse(mSexRadioButtons[0].isChecked());
        assertFalse(saveBtn.isClickable());
        saveBtn.setClickable(true);
        assertEquals("女", FrameEtongApplication.getApplication().getUserInfo().getUserSex());

        mMarryRadioButtons[0].setChecked(true);
        mePersonalCompleteActivity.setUserInfo();
        assertFalse(mMarryRadioButtons[1].isChecked());
        assertFalse(saveBtn.isClickable());
        saveBtn.setClickable(true);
        assertEquals("未婚", FrameEtongApplication.getApplication().getUserInfo().getUserMarry());

        mMarryRadioButtons[1].setChecked(true);
        mePersonalCompleteActivity.setUserInfo();
        assertFalse(mMarryRadioButtons[0].isChecked());
        assertFalse(saveBtn.isClickable());
        saveBtn.setClickable(true);
        assertEquals("已婚", FrameEtongApplication.getApplication().getUserInfo().getUserMarry());


    }

    /**
     * @desc (测试修改用户信息接口回调后的操作)
     */
    @Test
    public void updateUserInfoResult() throws Exception {

        //模拟请求得到的结果数据
        HttpMethod method = new HttpMethod("url", null);

        for (int i = 0; i < 3; i++) {
            String msg = null;
            int condition = i;//0 = 成功;1 = NETWORK_ERROR; 2 = DATA_ERROR;
            JSONObject object = new JSONObject();
            switch (condition) {
                case 0:
                    object.put("flag", "0");
                    object.put("data", "成功");
                    msg = "保存成功";
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    object.put("msg", "提交失败，请检查网络！");
                    msg = "保存失败，请检查网络！";
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

            mePersonalCompleteActivity.updateUserInfoResult(method);

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
    /**
     * @desc (测试跳转)
     */
    @Test
    public void testIntent() throws Exception {
        //登录按钮
        compileHead.performClick();
        // 获取对应的Shadow类
        ShadowActivity shadowLoginActivity = Shadows.shadowOf(mePersonalCompleteActivity);
        // 借助Shadow类获取启动下一Activity的Intent
        Intent nextLoginIntent = shadowLoginActivity.getNextStartedActivity();
        // 校验Intent的正确性
        assertEquals(PhotoHeadUtils.class.getName(),nextLoginIntent.getComponent().getClassName());
    }
}