package com.etong.android.jxappusedcar.bean;

/**
 * Created by Ellison.Sun on 2016/8/9.
 * <p/>
 * 品牌item
 */
public class UC_GrandBrandItemBean {
    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title
    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // item的显示位置

    public int type;
    public String charTv;

    int f_carbrandid;                     //  车辆品牌id
    String letter;                   //  品牌中第一个文字的字母
    String f_brand;               //  品牌名字
    String image;          //   品牌的图片url

    public UC_GrandBrandItemBean() {

    }

    public UC_GrandBrandItemBean(int type, String charTv) {
        this.type = type;
        this.charTv = charTv;
    }

    public UC_GrandBrandItemBean(String f_brand, String image) {
        this.f_brand = f_brand;
        this.image = image;
    }

    public UC_GrandBrandItemBean(String f_brand, int f_carbrandid) {
        this.f_brand = f_brand;
        this.f_carbrandid = f_carbrandid;
    }

    public int getF_carbrandid() {
        return f_carbrandid;
    }

    public void setF_carbrandid(int f_carbrandid) {
        this.f_carbrandid = f_carbrandid;
    }

    public String getF_brand() {
        return f_brand;
    }

    public void setF_brand(String f_brand) {
        this.f_brand = f_brand;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

}
