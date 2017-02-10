package com.etong.android.jxappfours.find_car.grand.car_config;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author : by sunyao
 * @ClassName : FC_ConfigItemInfoUtilTest
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2017/1/4 - 18:04
 */
public class FC_ConfigItemInfoUtilTest {

//    "基本参数", "车身", "发动机", "空调/冰箱", "内部配置", "安全装备", "变速箱",
//            "底盘转向", "车轮制动",
//            "操控配置", "多媒体配置",
//            "灯光配置", "外部配置", "玻璃/后视镜", "高科技配置", "座椅配置"
    @Test
    public void getStrTitle() {
        String[] strTitle = FC_ConfigItemInfoUtil.getStrTitle();
        assertEquals("基本参数", strTitle[0]);
        assertEquals("车身", strTitle[1]);
        assertEquals("玻璃/后视镜", strTitle[13]);
        assertEquals("高科技配置", strTitle[14]);
        assertEquals("座椅配置", strTitle[15]);
    }

    @Test
    public void getStrItem() {
        String[][] strItems = FC_ConfigItemInfoUtil.getStrItems();
    }

}