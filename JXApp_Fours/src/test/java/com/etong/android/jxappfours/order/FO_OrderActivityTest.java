package com.etong.android.jxappfours.order;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.jxappfours.BuildConfig;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.maintain_progress.Find_car_MaintainProgressActivity;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;

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
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

/**
 * @desc (预约界面测试用例)
 * @createtime 2017/1/5 - 15:21
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class FO_OrderActivityTest {

    private ActivityController<FO_OrderActivity> controller;
    private FO_OrderActivity orderActivitry;
    private EditText mEtOrderPhone;
    private EditText mEtOrderName;
    private int flag = 4;
    private TextView mTitleNameAnother;
    private ImageView mImgSelectAnother;
    private TextView mTitleName;
    private RelativeLayout mRLSelect;
    private LinearLayout mLLFirstSelect;
    private LinearLayout mOrderList;
    private RelativeLayout mRLSelectAnother;

    @Before
    public void setUp() throws Exception {
        Intent intent = new Intent();
        intent.putExtra("flag", flag);
        controller = Robolectric.buildActivity(FO_OrderActivity.class, intent).create().start();
        orderActivitry = controller.get();
        assertEquals(flag, orderActivitry.getIntent().getIntExtra("flag", 1));

        mEtOrderName = (EditText) orderActivitry.findViewById(R.id.fours_order_edit_order_name);
        mEtOrderPhone = (EditText) orderActivitry.findViewById(R.id.fours_order_edit_order_phone);

        mRLSelect = (RelativeLayout) orderActivitry.findViewById(R.id.fours_order_rl_order_selectcar);
        mLLFirstSelect = (LinearLayout) orderActivitry.findViewById(R.id.fours_order_ll_order_first_select);
        mRLSelectAnother = (RelativeLayout) orderActivitry.findViewById(R.id.fours_order_rl_order_selectcar_another);
        mOrderList = (LinearLayout) orderActivitry.findViewById(R.id.fours_order_ll_order_list);
        mTitleNameAnother = (TextView) orderActivitry.findViewById(R.id.fours_order_txt_order_titlename_another);
        mTitleName = (TextView) orderActivitry.findViewById(R.id.fours_order_txt_order_titlename);
        mImgSelectAnother = (ImageView) orderActivitry.findViewById(R.id.fours_order_img_another);

        TextView mTxtNameTitle = (TextView) orderActivitry.findViewById(R.id.fours_order_txt_order_nametitle);
        TextView mTxtPhoneTitle = (TextView) orderActivitry.findViewById(R.id.fours_order_txt_order_phonetitle);
        TextView mOrderListTitle = (TextView) orderActivitry.findViewById(R.id.fours_order_txt_order_list_title);
        LinearLayout mLLMaintenance = (LinearLayout) orderActivitry.findViewById(R.id.fours_order_ll_maintenance_order);

        TextView maintainBtn = (TextView) orderActivitry.findViewById(R.id.titlebar_next_button);

        if (flag == 3 || flag == 4) {//订购预约、询底价（底价购车）
            assertEquals(View.GONE, mRLSelect.getVisibility());
            assertEquals(View.GONE, mLLFirstSelect.getVisibility());
            assertEquals(View.GONE, mOrderList.getVisibility());
            assertEquals(View.VISIBLE, mRLSelectAnother.getVisibility());
            assertEquals("可询价4S店", mOrderListTitle.getText().toString());
        } else {//试驾预约、维保预约
            assertEquals(View.GONE, mRLSelect.getVisibility());
            assertEquals(View.GONE, mOrderList.getVisibility());
            assertEquals(View.GONE, mRLSelectAnother.getVisibility());
            assertEquals(View.VISIBLE, mLLFirstSelect.getVisibility());
            assertEquals("可预约4S店", mOrderListTitle.getText().toString());
            if (flag == 2) {//维保预约
                assertEquals(View.VISIBLE, maintainBtn.getVisibility());
                assertEquals(View.VISIBLE, mLLMaintenance.getVisibility());
                assertEquals("维保进度查询", maintainBtn.getText().toString());
                assertEquals("确认姓名 :", mTxtNameTitle.getText().toString());
                assertEquals("确认手机号码 :", mTxtPhoneTitle.getText().toString());
                assertEquals("", mEtOrderName.getHint().toString());
                assertEquals("", mEtOrderPhone.getHint().toString());
            }
        }
    }

    /**
     * @desc (测试已选车的intent跳转后操作)
     */
    @Test
    public void testSelectCarIntent() throws Exception {

        for (int i = 0; i < 4; i++) {
            int flags = i + 1;
            Intent intent = new Intent();
            intent.putExtra("isSelectCar", true);
            intent.putExtra("carImage", "");
            intent.putExtra("vTitleName", "别克-昂科雷 2014款 3.6L 四驱智享旗舰型");
            intent.putExtra("vid", "639716");
            intent.putExtra("flag", flags);
            ActivityController<FO_OrderActivity> controller = Robolectric.buildActivity(FO_OrderActivity.class, intent).create().start();
            FO_OrderActivity orderActivitry = controller.get();

            RelativeLayout mRLSelect = (RelativeLayout) orderActivitry.findViewById(R.id.fours_order_rl_order_selectcar);
            LinearLayout mLLFirstSelect = (LinearLayout) orderActivitry.findViewById(R.id.fours_order_ll_order_first_select);
            RelativeLayout mRLSelectAnother = (RelativeLayout) orderActivitry.findViewById(R.id.fours_order_rl_order_selectcar_another);
            LinearLayout mOrderList = (LinearLayout) orderActivitry.findViewById(R.id.fours_order_ll_order_list);
            TextView mTitleNameAnother = (TextView) orderActivitry.findViewById(R.id.fours_order_txt_order_titlename_another);
            TextView mTitleName = (TextView) orderActivitry.findViewById(R.id.fours_order_txt_order_titlename);

            assertEquals(true, orderActivitry.getIntent().getBooleanExtra("isSelectCar", false));
            assertEquals("", orderActivitry.getIntent().getStringExtra("carImage"));
            assertEquals("别克-昂科雷 2014款 3.6L 四驱智享旗舰型", orderActivitry.getIntent().getStringExtra("vTitleName"));
            assertEquals("639716", orderActivitry.getIntent().getStringExtra("vid"));
            assertEquals(flags, orderActivitry.getIntent().getIntExtra("flag", 1));
            if (flags == 3 || flags == 4) {//订购预约、询底价（底价购车）
                assertEquals(View.GONE, mRLSelect.getVisibility());
                assertEquals(View.GONE, mLLFirstSelect.getVisibility());
                assertEquals(View.VISIBLE, mOrderList.getVisibility());
                assertEquals(View.VISIBLE, mRLSelectAnother.getVisibility());
                assertEquals("别克-昂科雷 2014款 3.6L 四驱智享旗舰型", mTitleNameAnother.getText().toString());
            } else {//试驾预约、维保预约
                assertEquals(View.GONE, mRLSelectAnother.getVisibility());
                assertEquals(View.GONE, mLLFirstSelect.getVisibility());
                assertEquals(View.VISIBLE, mOrderList.getVisibility());
                assertEquals(View.VISIBLE, mRLSelect.getVisibility());
                assertEquals("别克-昂科雷 2014款 3.6L 四驱智享旗舰型", mTitleName.getText().toString());
            }
        }
    }

    /**
     * @desc (测试提交按钮点击事件)
     */
    @Test
    public void testComfirmClick() throws Exception {
        Button comfirmBtn = (Button) orderActivitry.findViewById(R.id.fours_order_btn_orde_confirm);
        EditText mEtOrderPhone = (EditText) orderActivitry.findViewById(R.id.fours_order_edit_order_phone);

        Toast toast = ShadowToast.getLatestToast();
        // 判断Toast尚未弹出
        assertNull(toast);

        comfirmBtn.performClick();
        toast = ShadowToast.getLatestToast();
        // 判断Toast已经弹出
        assertNotNull(toast);
        // 获取Shadow类进行验证
        ShadowToast shadowToast = shadowOf(toast);
        assertEquals(Toast.LENGTH_SHORT, shadowToast.getDuration());
        assertEquals("请选车!", shadowToast.getTextOfLatestToast());

        Models_Contrast_VechileType bean = new Models_Contrast_VechileType();
        bean.setBrand("别克");
        bean.setVid(639716);
        bean.setTitle("昂科雷 2014款 3.6L 四驱智享旗舰型");
        bean.setImage("");
        orderActivitry.getSelectCar(bean);
        comfirmBtn.performClick();
        assertEquals("请输入手机号!", shadowToast.getTextOfLatestToast());

        if (flag == 3 || flag == 4) {//订购预约、询底价
            assertEquals("别克昂科雷 2014款 3.6L 四驱智享旗舰型", mTitleNameAnother.getText().toString());
            assertEquals(View.GONE, mRLSelect.getVisibility());
            assertEquals(View.GONE, mLLFirstSelect.getVisibility());
            assertEquals(View.VISIBLE, mRLSelectAnother.getVisibility());
        } else {//试驾预约、维保预约
            assertEquals("别克昂科雷 2014款 3.6L 四驱智享旗舰型", mTitleName.getText().toString());
            assertEquals(View.VISIBLE, mRLSelect.getVisibility());
            assertEquals(View.GONE, mLLFirstSelect.getVisibility());
            assertEquals(View.GONE, mRLSelectAnother.getVisibility());
        }

        mEtOrderPhone.setText("132");
        comfirmBtn.performClick();
        assertEquals("请输入正确的手机号！", shadowToast.getTextOfLatestToast());

        mEtOrderPhone.setText("11111111111");
        comfirmBtn.performClick();
        assertEquals("请输入正确的手机号！", shadowToast.getTextOfLatestToast());

        mEtOrderPhone.setText("13234454242");
        comfirmBtn.performClick();
        assertFalse(comfirmBtn.isClickable());
        comfirmBtn.setClickable(true);
    }

    /**
     * @desc (测试按钮点击事件跳转)
     */
    @Test
    public void testIntentClick() throws Exception {
        View[] buttons = new View[2];
        buttons[0] = (Button) orderActivitry.findViewById(R.id.fours_order_btn_order_addcar);
        buttons[1] = (TextView) orderActivitry.findViewById(R.id.titlebar_next_button);

        for (int i = 0; i < 2; i++) {
            FrameEtongApplication.getApplication().setUserInfo(null);
            buttons[i].performClick();
            ShadowActivity shadowActivity = Shadows.shadowOf(orderActivitry);
            Intent nextIntent = shadowActivity.getNextStartedActivity();
            Assert.assertEquals(FramePersonalLoginActivity.class.getName(), nextIntent.getComponent().getClassName());

            FrameEtongApplication.getApplication().setUserInfo(new FrameUserInfo());
            buttons[i].performClick();
            ShadowActivity shadow2Activity = Shadows.shadowOf(orderActivitry);
            Intent next2Intent = shadow2Activity.getNextStartedActivity();
            Assert.assertEquals(i == 0 ? FM_AddActivity.class.getName() : Find_car_MaintainProgressActivity.class.getName()
                    , next2Intent.getComponent().getClassName());
        }

    }

    /**
     * @desc (测试提交请求结果后的操作)
     */
    @Test
    public void gCallBackData() throws Exception {

        //模拟请求得到的结果数据
        HttpMethod method = new HttpMethod("url", null);

        for (int i = 0; i < 3; i++) {
            String msg = null;
            int condition = i;//0 = 成功;1 = NETWORK_ERROR; 2 = DATA_ERROR;
            JSONObject object = new JSONObject();
            switch (condition) {
                case 0:
                    object.put("flag", "0");
                    object.put("data", "1000510");
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

            Toast toast = ShadowToast.getLatestToast();
            // 判断Toast尚未弹出
            assertNull(toast);

            orderActivitry.gCallBackData(method);

            if (condition == 0) {
                msg = flag == 4 ? "询底价成功！" : "提交预约成功！";
            } else {
                msg = flag == 4 ? "询底价失败！" : "提交预约失败！";
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
     * @desc (测试onResume后操作)
     */
    @Test
    public void onResume() throws Exception {
        controller.resume();
        assertTrue(TextUtils.isEmpty(mEtOrderName.getText().toString()));
        assertTrue(TextUtils.isEmpty(mEtOrderPhone.getText().toString()));

        FrameUserInfo userInfo = new FrameUserInfo();
        userInfo.setUserPhone("13755174943");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        controller.resume();
        assertEquals("13755174943", mEtOrderPhone.getText().toString());
        assertTrue(TextUtils.isEmpty(mEtOrderName.getText().toString()));

        userInfo.setUserName("嘻嘻");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        controller.resume();
        assertEquals("13755174943", mEtOrderPhone.getText().toString());
        assertEquals("嘻嘻", mEtOrderName.getText().toString());
    }

}