package com.etong.android.jxappfours.models_contrast.common;

import com.alibaba.fastjson.JSON;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.jxappfours.BuildConfig;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/12/27 - 11:37
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class Models_Contrast_AddVechileStyle_MethodTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    @Ignore
    public void setAdd_VechileStyle() throws Exception {

    }

    @Test
    @Ignore
    public void getAdd_VechileStyle() throws Exception {

    }

    @Test
    public void cartAdd() throws Exception {
        assertNull(Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle());
        Models_Contrast_VechileType add = new Models_Contrast_VechileType();
        add.setVid(1);
        Models_Contrast_AddVechileStyle_Method.cartAdd(add);

        assertTrue(Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle().containsKey(add.getVid()));
    }

    @Test
    public void remove() throws Exception {
        cartAdd();
        Models_Contrast_AddVechileStyle_Method.remove(1);
        assertFalse(Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle().containsKey(1));
    }

    @Test
    public void clear() throws Exception {
        cartAdd();
        assertNotNull(Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle());
        Models_Contrast_AddVechileStyle_Method.clear();
        assertNull(Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle());
    }

}