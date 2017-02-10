package com.etong.android.frame.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * @desc (String类型的时间转Calendar)
 * @createtime 2016/12/24 - 16:59
 * @Created by xiaoxue.
 */
public class DateUtilsTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testDate(){
        assertEquals(1482571440L,DateUtils.getSecondsFormDateString("2016-12-24 17:24"));
    }

}