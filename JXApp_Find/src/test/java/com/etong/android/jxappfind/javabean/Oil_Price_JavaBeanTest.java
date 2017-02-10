package com.etong.android.jxappfind.javabean;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @desc (今日油价的javabean测试用例)
 * @createtime 2016/12/24 - 19:06
 * @Created by xiaoxue.
 */
public class Oil_Price_JavaBeanTest {

    private Oil_Price_JavaBean oil_price_javaBean;

    @Before
    public void setUp() throws Exception {
        oil_price_javaBean = new Oil_Price_JavaBean();
    }

    @Test
    public void testsetProv(){
        oil_price_javaBean.setProv("长沙");
        assertEquals("长沙",oil_price_javaBean.prov);
    }

    @Test
    public void testgetProv() {
        String prov = oil_price_javaBean.getProv();
        assertEquals(prov,oil_price_javaBean.prov);
    }




}