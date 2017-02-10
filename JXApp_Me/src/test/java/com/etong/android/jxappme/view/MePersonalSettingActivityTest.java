package com.etong.android.jxappme.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.utils.PhotoHeadUtils;
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
import org.robolectric.util.ActivityController;

import java.io.File;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

/**
 * @desc (设置测试用例)
 * @createtime 2017/1/4 - 18:49
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class MePersonalSettingActivityTest {

    private MePersonalSettingActivity settingActivity;
    private TextView verTv;
    private TextView clear_cache;
    private Button log_off;
    private ActivityController<MePersonalSettingActivity> controller;
    private RelativeLayout ll_open_location;

    @Before
    public void setUp() throws Exception {
        FrameEtongApplication.getApplication().setUserInfo(new FrameUserInfo());
        controller = Robolectric.buildActivity(MePersonalSettingActivity.class).create().start();
        settingActivity = controller.get();
        verTv = (TextView) settingActivity.findViewById(R.id.me_setting_version_name_tv);
        log_off = (Button) settingActivity.findViewById(R.id.me_log_off);
        clear_cache = (TextView) settingActivity.findViewById(R.id.me_txt_clear_cache);
        ll_open_location = (RelativeLayout) settingActivity.findViewById(R.id.me_rl_open_location);

        assertEquals(View.GONE, ll_open_location.getVisibility());
        assertEquals("津湘汽车", verTv.getText().toString());
    }

    /**
     * @desc (测试点击事件)
     */
    @Test
    public void testClick() throws Exception {
        Toast toast = ShadowToast.getLatestToast();
        // 判断Toast尚未弹出
        assertNull(toast);
        clear_cache.performClick();
        toast = ShadowToast.getLatestToast();
        ShadowToast shadowToast = shadowOf(toast);
        assertEquals("清除缓存失败！", shadowToast.getTextOfLatestToast());

        assertTrue(FrameEtongApplication.getApplication().isLogin());
        log_off.performClick();
        toast = ShadowToast.getLatestToast();
        ShadowToast shadowToastLog = shadowOf(toast);
        assertEquals("退出登录成功!", shadowToastLog.getTextOfLatestToast());
        assertFalse(FrameEtongApplication.getApplication().isLogin());
        assertNull(FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList());
        assertNull(FrameEtongApplication.getApplication().getNewCarCollectCache().getCarList());
        assertTrue(FrameEtongApplication.getApplication().getRepaymentRemindInfo().isEmpty());

        // 获取对应的Shadow类
        ShadowActivity shadowLoginActivity = Shadows.shadowOf(settingActivity);
        // 借助Shadow类获取启动下一Activity的Intent
        Intent nextLoginIntent = shadowLoginActivity.getNextStartedActivity();
        // 校验Intent的正确性
        assertEquals(FramePersonalLoginActivity.class.getName(),nextLoginIntent.getComponent().getClassName());

    }

    /**
     * @desc (清除WebView缓存测试)
     */
    @Test
    public void clearWebViewCache() throws Exception {

        // WebView 缓存文件
        File appCacheDir = new File(settingActivity.getFilesDir().getAbsolutePath() + "/webcache");
        File webviewCacheDir = new File(settingActivity.getCacheDir().getAbsolutePath()
                + "/webviewCache");

        settingActivity.clearWebViewCache();

        assertFalse(webviewCacheDir.exists());
        assertFalse(appCacheDir.exists());
    }

    /**
     * @desc (递归删除 文件/文件夹测试)
     */
    @Test
    public void deleteFile() throws Exception {
        File appCacheDir = new File(settingActivity.getFilesDir().getAbsolutePath() + "/webcache");
        settingActivity.deleteFile(appCacheDir);
        assertFalse(appCacheDir.exists());
    }
}