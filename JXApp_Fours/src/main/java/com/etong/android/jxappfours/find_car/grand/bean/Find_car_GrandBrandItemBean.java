package com.etong.android.jxappfours.find_car.grand.bean;

/**
 * Created by Ellison.Sun on 2016/8/9.
 * <p/>
 * 品牌item
 */
public class Find_car_GrandBrandItemBean {
    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title
    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // item的显示位置

    public int type;
    public String charTv;

    int id;                     //  车辆品牌id
    String letter;                   //  品牌中第一个文字的字母
    String title;               //  品牌名字
    String image;          //   品牌的图片url

    int brand_id;            // 获取热门品牌的id

    public Find_car_GrandBrandItemBean() {

    }

    public Find_car_GrandBrandItemBean(int type, String charTv) {
        this.type = type;
        this.charTv = charTv;
    }


    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public Find_car_GrandBrandItemBean(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public Find_car_GrandBrandItemBean(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }
}
