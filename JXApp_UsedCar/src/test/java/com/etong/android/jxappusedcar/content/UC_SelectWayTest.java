package com.etong.android.jxappusedcar.content;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.jxappusedcar.BuildConfig;
import com.etong.android.jxappusedcar.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * @desc (进入预约卖车界面的主界面测试用例)
 * @createtime 2017/1/4 - 17:35
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class UC_SelectWayTest {

    private UC_SelectWay selectWay;
    private View way1_btn;
    private View way2_btn;

    @Before
    public void setUp() throws Exception {
        selectWay = Robolectric.setupActivity(UC_SelectWay.class);

        way1_btn = selectWay.findViewById(R.id.used_car_way1_btn);
        way2_btn = selectWay.findViewById(R.id.used_car_way2_btn);
    }

    /**
     * @desc (点击跳转测试)
     */
    @Test
    public void testClickIntent() throws Exception {
        way1_btn.performClick();
        ShadowActivity shadowActivity = Shadows.shadowOf(selectWay);
        Intent nextIntent = shadowActivity.getNextStartedActivity();
        assertEquals( FramePersonalLoginActivity.class.getName(),nextIntent.getComponent().getClassName());

        FrameEtongApplication.getApplication().setUserInfo(new FrameUserInfo());//设置用户已登录
        way1_btn.performClick();
        ShadowActivity shadow1Activity = Shadows.shadowOf(selectWay);
        Intent next1Intent = shadow1Activity.getNextStartedActivity();
        assertEquals( UC_Select_CarNumber.class.getName(),next1Intent.getComponent().getClassName());

        way2_btn.performClick();
        ShadowActivity shadow2Activity = Shadows.shadowOf(selectWay);
        Intent next2Intent = shadow2Activity.getNextStartedActivity();
        assertEquals(UC_Submit_SalesCar.class.getName(),next2Intent.getComponent().getClassName());

    }

}