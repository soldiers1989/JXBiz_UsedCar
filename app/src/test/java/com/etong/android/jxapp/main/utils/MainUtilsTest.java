package com.etong.android.jxapp.main.utils;

import com.etong.android.jxappusedcar.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/12/30 - 15:04
 * @Created by xiaoxue.
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk=21,constants = BuildConfig.class)
public class MainUtilsTest {
    @Test
    public void getImageIdByName() throws Exception {
        String url=MainUtils.getImageUrl("main_buy_car");
        assertEquals("drawable://2130903215",url);

        String url1=MainUtils.getImageUrl("");
        assertEquals(null,url1);

        String urlNull=MainUtils.getImageUrl(null);
        assertEquals(null,urlNull);

    }

    @Test
    public void getImageUrl() throws Exception {
        int id=MainUtils.getImageIdByName("main_start_ic");
        assertEquals(2130903219,id);
    }

}