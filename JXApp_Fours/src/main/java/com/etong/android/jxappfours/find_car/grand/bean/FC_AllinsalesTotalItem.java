package com.etong.android.jxappfours.find_car.grand.bean;

import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/23.
 */
public class FC_AllinsalesTotalItem {

    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title
    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // 标题下面的List


    public int type;          // Item的类型
    public String charTv;   // 显示在头部的文字

    private String letter;
    private int menuId;
    private List<FC_AllSalesListItem> carsetList;
    private String title;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public List<FC_AllSalesListItem> getCarsetList() {
        return carsetList;
    }

    public void setCarsetList(List<FC_AllSalesListItem> carsetList) {
        this.carsetList = carsetList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
