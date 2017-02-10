package com.etong.android.frame.widget.three_slide_our.javabean;

import java.io.Serializable;

/**
 * 选择车型
 * Created by Administrator on 2016/8/15.
 */
public class Et_VechileType implements Serializable {
    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title
    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // item的显示位置

    public int type;
    public String charTv;

    private Integer id;         // 车型id       662347
    private String name;        // 车型名字     "2015款 35 TFSI 进取型"
    
    private boolean isChecked=false;//是否选中

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Et_VechileType() {

    }
    public Et_VechileType(int type, String outputVol) {
        this.type = type;
        this.charTv = outputVol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

