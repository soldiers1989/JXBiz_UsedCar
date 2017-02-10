package com.etong.android.frame.widget.three_slide_our.javabean;

/**
 * 选择品牌
 * Created by Administrator on 2016/8/15.
 */
public class Et_SelectBrand {
    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title
    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // item的显示位置

    public int type;
    public String charTv;

    private String f_carbrandid;            //品牌ID
    private String f_brand;          //品牌名称
    private String initial;             //品牌首字母
    private String image;               // 品牌中的图片

    public Et_SelectBrand() {

    }
    public Et_SelectBrand(int type, String charTv) {
        this.type = type;
        this.charTv = charTv;
    }

    public String getF_carbrandid() {
        return f_carbrandid;
    }

    public void setF_carbrandid(String f_carbrandid) {
        this.f_carbrandid = f_carbrandid;
    }

    public String getF_brand() {
        return f_brand;
    }

    public void setF_brand(String f_brand) {
        this.f_brand = f_brand;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
