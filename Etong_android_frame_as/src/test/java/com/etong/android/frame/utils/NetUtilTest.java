package com.etong.android.frame.utils;

import com.etong.android.frame.BuildConfig;
import com.etong.android.frame.user.FrameEtongApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/12/30 - 15:39
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, application = FrameEtongApplication.class, constants = BuildConfig.class)
public class NetUtilTest {
    @Test
    public void getNetworkState() throws Exception {
        int i = NetUtil.getNetworkState(FrameEtongApplication.getApplication().getApplicationContext());
        assertEquals(0,i);
    }

}