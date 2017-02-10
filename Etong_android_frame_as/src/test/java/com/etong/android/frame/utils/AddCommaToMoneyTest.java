package com.etong.android.frame.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/12/21 - 17:34
 * @Created by wukefan.
 */
public class AddCommaToMoneyTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void addCommaToMoney() throws Exception {
        String addCommaToMoney=AddCommaToMoney.AddCommaToMoney("2345667");
        assertEquals("2,345,667", addCommaToMoney);

        addCommaToMoney=AddCommaToMoney.AddCommaToMoney("2345667.2");
        assertEquals("2,345,667.2", addCommaToMoney);

        addCommaToMoney=AddCommaToMoney.AddCommaToMoney("2345667.23");
        assertEquals("2,345,667.23", addCommaToMoney);

    }

}