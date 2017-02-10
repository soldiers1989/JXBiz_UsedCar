package com.etong.android.jxappfours.models_contrast.common;

import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_SelectBrand;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @desc (拼音比较器测试用例)
 * @createtime 2016/12/27 - 10:11
 * @Created by wukefan.
 */
public class Models_Contrast_PinyinComparatorTest {

    private Models_Contrast_PinyinComparator pinyinComparator;

    @Before
    public void setUp() throws Exception {
        pinyinComparator = new Models_Contrast_PinyinComparator();
    }

    @Test
    public void compare() throws Exception {
        Models_Contrast_SelectBrand t1 = new Models_Contrast_SelectBrand();
        t1.setLetter("A");
        Models_Contrast_SelectBrand t2 = new Models_Contrast_SelectBrand();
        t2.setLetter("B");
        int result = pinyinComparator.compare(t1, t2);

        assertEquals(-1, result);
    }

}