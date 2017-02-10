package com.etong.android.jxappfours.find_car.grand.carset;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @desc (排量设置工具类测试用例)
 * @createtime 2017/1/5 - 18:55
 * @Created by wukefan.
 */
public class FC_OutFormatUtilTest {

    @Test
    public void setOutValue() throws Exception {
        String value = FC_OutFormatUtil.setCarLelOutValue("中大型车", 1800, 3000);
        assertEquals("中大型车/1.8T  3.0T", value);
        value = FC_OutFormatUtil.setCarLelOutValue(null, 1800, 3000);
        assertEquals("未记录/1.8T  3.0T", value);
        value = FC_OutFormatUtil.setCarLelOutValue(null, null, 3000);
        assertEquals("未记录/3.0T", value);
        value = FC_OutFormatUtil.setCarLelOutValue(null, 1800, null);
        assertEquals("未记录/1.8T  ", value);
        value = FC_OutFormatUtil.setCarLelOutValue(null, null, null);
        assertEquals("未记录", value);
        value = FC_OutFormatUtil.setCarLelOutValue("中大型车", null, 3000);
        assertEquals("中大型车/3.0T", value);
        value = FC_OutFormatUtil.setCarLelOutValue("中大型车", 1800, null);
        assertEquals("中大型车/1.8T  ", value);
        value = FC_OutFormatUtil.setCarLelOutValue("中大型车", null, null);
        assertEquals("中大型车/未记录", value);
    }

    @Test
    public void setPriceValue() throws Exception {
        String value = FC_OutFormatUtil.setPriceValue(18.0, 30.0);
        assertEquals("18.0 - 30.0万", value);
        value = FC_OutFormatUtil.setPriceValue(null, 30.0);
        assertEquals("未记录", value);
        value = FC_OutFormatUtil.setPriceValue(18.0, null);
        assertEquals("未记录", value);
        value = FC_OutFormatUtil.setPriceValue(null, null);
        assertEquals("未记录", value);
    }

}