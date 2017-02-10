package com.etong.android.jxappusedcar.utils;

import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.DateUtils;
import com.etong.android.jxappfours.find_car.collect_search.main_content.Find_Car_MainActivity;
import com.etong.android.jxappusedcar.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * @desc (像素与分辨率互转的工具类测试用例)
 * @createtime 2016/12/30 - 14:41
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class UC_DensityUtilTest {
    @Before
    public void setUp() throws Exception {
        UC_DensityUtil util = new UC_DensityUtil();

    }

    @Test
    public void dip2px() throws Exception {
        int value = UC_DensityUtil.dip2px(FrameEtongApplication.getApplication().getApplicationContext(), 1.245f);
        assertEquals(1,value);
    }

    @Test
    public void px2dip() throws Exception {
        int pxValue=UC_DensityUtil.px2dip(FrameEtongApplication.getApplication().getApplicationContext(), 1.245f);
        assertEquals(1,pxValue);
    }

}