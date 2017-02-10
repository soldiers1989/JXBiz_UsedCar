package com.etong.android.frame.widget.three_slide_300.javabean;

/**
 * 选择品牌
 * Created by Administrator on 2016/8/15.
 */
public class Models_Contrast_SelectBrand {
    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title
    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // item的显示位置

    public int type;
    public String charTv;

    private String update_time;         // 更新事件
    private String brand_id;            //品牌ID
    private String brand_name;          //品牌名称
    private String initial;             //品牌首字母

    public Models_Contrast_SelectBrand() {

    }
    public Models_Contrast_SelectBrand(int type, String charTv) {
        this.type = type;
        this.charTv = charTv;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
