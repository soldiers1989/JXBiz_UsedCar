package com.etong.android.jxappusedcar.bean;

import java.io.Serializable;

/**
 * @author : by sunyao
 * @ClassName : UC_SelectBrandMenuPinnedBean
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/10/25 - 18:06
 */

public class UC_SelectBrandMenuPinnedBean implements Serializable {
    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title
    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // 标题下面的List


    public int type;          // Item的类型
    public String charTv;   // 显示在头部的文字

    public Integer id;
    public String name;

    public UC_SelectBrandMenuPinnedBean() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
