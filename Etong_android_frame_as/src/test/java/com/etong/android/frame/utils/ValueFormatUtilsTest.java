package com.etong.android.frame.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/12/29 - 15:27
 * @Created by wukefan.
 */
public class ValueFormatUtilsTest {


    @Test
    public void setIntOrPointValue() throws Exception {
        String value = ValueFormatUtils.setIntOrPointValue(20.0);
        assertEquals("20", value);
        value = ValueFormatUtils.setIntOrPointValue(12.4);
        assertEquals("12.4", value);
        value = ValueFormatUtils.setIntOrPointValue(0.0);
        assertEquals("0", value);
        value = ValueFormatUtils.setIntOrPointValue(0.6);
        assertEquals("0.6", value);
        value = ValueFormatUtils.setIntOrPointValue(0.03);
        assertEquals("0.03", value);
        value = ValueFormatUtils.setIntOrPointValue(0.73);
        assertEquals("0.73", value);
    }

}