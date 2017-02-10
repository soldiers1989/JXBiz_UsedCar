package com.etong.android.jxappfours.find_car.grand.bean;

import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/12.
 *
 * 车辆配置中List的item
 */
public class FC_CarconfigCompareItem {
    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title

    public int type;          // Item的类型
    public String tText="";
    public String lText ="";       // Item上面显示的左边文字
    public List<String> rText;       // Item上面显示的右边文字

    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // 标题下面的List
    public boolean isDiff=false;          // 判断该行所有的元素是否相同

    // 标题的构造函数
    public FC_CarconfigCompareItem(int type, String tText) {
        this.type = type;
        this.tText = tText;
    }

    // item的构造函数
    public FC_CarconfigCompareItem(int type, String lText, List<String> rText) {
        this.type = type;
        this.lText = lText;
        this.rText = rText;
    }

    @Override public String toString() {
        return tText;
    }
}
