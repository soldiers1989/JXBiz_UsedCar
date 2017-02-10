package com.etong.android.jxappcarfinancial.activity;

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
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowToast;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

/**
 * @desc (绑定金融账号测试用例)
 * @createtime 2016/12/30 - 14:33
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class CF_FirstBindingInfoActivityTest {

    private CF_FirstBindingInfoActivity firstBindingInfoActivity;
    private EditText mEdtName;
    private EditText mEdtPhone;
    private EditText mEdtIdCard;
    private Button mSaveBtn;

    @Before
    public void setUp() throws Exception {
        firstBindingInfoActivity = Robolectric.setupActivity(CF_FirstBindingInfoActivity.class);

        mEdtName = (EditText) firstBindingInfoActivity.findViewById(R.id.cf_edt_input_name);                    //姓名
        mEdtPhone = (EditText) firstBindingInfoActivity.findViewById(R.id.cf_edt_input_tel);                    //手机号码
        mEdtIdCard = (EditText) firstBindingInfoActivity.findViewById(R.id.cf_edt_input_idcard);                //身份证号码

        mSaveBtn = (Button) firstBindingInfoActivity.findViewById(R.id.cf_btn_input_save);
        assertNotNull(firstBindingInfoActivity);
    }

    @Test
    public void bindFinancialAgentResult() throws Exception {
        //模拟请求得到的结果数据
        HttpMethod method = new HttpMethod("url", null);

        for (int i = 0; i < 3; i++) {
            String msg = null;
            int condition = i;//0 = 成功;1 = NETWORK_ERROR; 2 = 失败;
            JSONObject object = new JSONObject();
            switch (condition) {
                case 0:
                    Map map = new HashMap();
                    map.put("fcustid", "981");
                    map.put("f_name", "万达");
                    map.put("f_phone", "15295456843");
                    map.put("f_cardId", "430761199102224525");
                    object.put("flag", "0");
                    object.put("data", map);
                    msg = "保存成功~";
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    object.put("msg", "保存失败，请检查网络！");
                    msg = "保存失败，请检查网络！";
                    break;
                case 2:
                    object.put("flag", "1");
                    object.put("msg", "该用户已经绑定，请不要重复绑定！");
                    msg = "该用户已经绑定，请不要重复绑定！";
                    break;
            }
            method.put(object);

            Toast toast = ShadowToast.getLatestToast();
            // 判断Toast尚未弹出
            assertNull(toast);

            FrameEtongApplication.getApplication().setUserInfo(new FrameUserInfo());
            firstBindingInfoActivity.bindFinancialAgentResult(method);

            if (condition == 0) {
                assertEquals("万达", FrameEtongApplication.getApplication().getUserInfo().getF_name());
                assertEquals("981", FrameEtongApplication.getApplication().getUserInfo().getFcustid());
                assertEquals("15295456843", FrameEtongApplication.getApplication().getUserInfo().getF_phone());
                assertEquals("430761199102224525", FrameEtongApplication.getApplication().getUserInfo().getF_cardId());
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

    /**
     * @desc (验证输入操作)
     */
    @Test
    public void onSave() throws Exception {
        Toast toast = ShadowToast.getLatestToast();
        // 判断Toast尚未弹出
        assertNull(toast);

        firstBindingInfoActivity.onSave();
        toast = ShadowToast.getLatestToast();
        // 判断Toast已经弹出
        assertNotNull(toast);
        // 获取Shadow类进行验证
        ShadowToast shadowToast = shadowOf(toast);
        assertEquals(Toast.LENGTH_SHORT, shadowToast.getDuration());
        assertEquals("请输入姓名！", shadowToast.getTextOfLatestToast());

        mEdtName.setText("   ");
        firstBindingInfoActivity.onSave();
        toast = ShadowToast.getLatestToast();
        assertEquals("请输入姓名！", shadowToast.getTextOfLatestToast());

        mEdtName.setText("哈哈哈");
        firstBindingInfoActivity.onSave();
        toast = ShadowToast.getLatestToast();
        assertEquals("请输入手机号码！", shadowToast.getTextOfLatestToast());

        mEdtPhone.setText("1375455");
        firstBindingInfoActivity.onSave();
        toast = ShadowToast.getLatestToast();
        assertEquals("请输入正确的手机号！", shadowToast.getTextOfLatestToast());

        mEdtPhone.setText("11155174943");
        firstBindingInfoActivity.onSave();
        toast = ShadowToast.getLatestToast();
        assertEquals("请输入正确的手机号！", shadowToast.getTextOfLatestToast());

        mEdtPhone.setText("13755174943");
        firstBindingInfoActivity.onSave();
        toast = ShadowToast.getLatestToast();
        assertEquals("请输入身份证号！", shadowToast.getTextOfLatestToast());


        mEdtIdCard.setText("430534");
        firstBindingInfoActivity.onSave();
        toast = ShadowToast.getLatestToast();
        assertEquals("请输入完整身份证号", shadowToast.getTextOfLatestToast());

        mEdtIdCard.setText("xxxxxxxxxxxxxxxxxx");
        firstBindingInfoActivity.onSave();
        toast = ShadowToast.getLatestToast();
        assertEquals("请输入正确的身份证号", shadowToast.getTextOfLatestToast());

        mEdtIdCard.setText("430681199514282622");
        firstBindingInfoActivity.onSave();
        toast = ShadowToast.getLatestToast();
        assertEquals("请输入正确的身份证号", shadowToast.getTextOfLatestToast());

        mEdtIdCard.setText("430681199504282622");
        assertTrue(mSaveBtn.isClickable());
        firstBindingInfoActivity.onSave();
        toast = ShadowToast.getLatestToast();
        assertEquals("您还未登录，请先登录~", shadowToast.getTextOfLatestToast());
        assertTrue(mSaveBtn.isClickable());

        FrameUserInfo user = new FrameUserInfo();
        user.setF_phone("13755174943");
        FrameEtongApplication.getApplication().setUserInfo(user);
        mEdtIdCard.setText("430681199504282622");
        assertTrue(mSaveBtn.isClickable());
        firstBindingInfoActivity.onSave();
        assertFalse(mSaveBtn.isClickable());
        mSaveBtn.setClickable(true);

    }

    @Test
    public void initUserInfo() throws Exception {
        FrameUserInfo userInfo = new FrameUserInfo();

        userInfo.setUserPhone("18229938144");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        firstBindingInfoActivity.initUserInfo();
        assertEquals("18229938144", mEdtPhone.getText().toString());
        assertTrue(TextUtils.isEmpty(mEdtName.getText().toString()));
        assertTrue(TextUtils.isEmpty(mEdtIdCard.getText().toString()));

        userInfo.setUserName("呵呵");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        firstBindingInfoActivity.initUserInfo();
        assertEquals("18229938144", mEdtPhone.getText().toString());
        assertEquals("呵呵", mEdtName.getText().toString());
        assertTrue(TextUtils.isEmpty(mEdtIdCard.getText().toString()));

        userInfo.setUserIdCard("324541199504282322");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        firstBindingInfoActivity.initUserInfo();
        assertEquals("18229938144", mEdtPhone.getText().toString());
        assertEquals("呵呵", mEdtName.getText().toString());
        assertEquals("324541199504282322", mEdtIdCard.getText().toString());

        userInfo.setF_phone("13755174943");
        userInfo.setF_name("呵呵");
        userInfo.setF_cardId("430681199504282322");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        firstBindingInfoActivity.initUserInfo();
        assertEquals("13755174943", mEdtPhone.getText().toString());
        assertEquals("呵呵", mEdtName.getText().toString());
        assertEquals("430681199504282322", mEdtIdCard.getText().toString());
    }
}