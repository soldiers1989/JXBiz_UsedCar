package com.etong.android.jxbizusedcar.bean;

/**
 * Created by Ellison.Sun on 2016/8/9.
 * <p/>
 * 品牌item
 */
public class UC_RegionSel_ItemBean {
    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title
    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // item的显示位置

    public int type;
    public String charTv;

    int id;                     //  车辆品牌id
    String letter;                   //  第一个文字的字母
    String title;               //  显示的名字

    public UC_RegionSel_ItemBean() {

    }

    public UC_RegionSel_ItemBean(int type, String charTv) {
        this.type = type;
        this.charTv = charTv;
    }

    public UC_RegionSel_ItemBean(String title) {
        this.title = title;
    }

    public UC_RegionSel_ItemBean(String title, int id) {
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

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

}
