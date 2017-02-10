package com.etong.android.frame.widget.three_slide_our.javabean;

/**
 * 选择车系
 * Created by Administrator on 2016/8/15.
 */
public class Et_VechileSeries {
    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title
    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // item的显示位置

    public int type;
    public String charTv;

    private Integer id;         // 0
    private String name;        // 一汽奥迪

    public Et_VechileSeries() {

    }
    public Et_VechileSeries(int type, String strTitle) {
        this.type = type;
        this.charTv = strTitle;
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
