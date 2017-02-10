package com.etong.android.jxappfours.find_car.grand.bean;

import java.io.Serializable;

/**
 * Created by Ellison.Sun on 2016/8/12.
 *
 * Menu中在售车型的List中的单个item
 */
public class FC_AllSalesListItem implements Serializable{
    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title
    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // 标题下面的List


    public int type;          // Item的类型
    public String charTv;   // 显示在头部的文字

    private int id;         // 车系ID
    private int pid;        // 此处为厂商ID, 父ID
    private String pTitle;      // 总的 ，悬浮标题
    private String image;       // 车系上面显示的图片
    private String fullName;    // 车系完整名称
    private double minguide;    // 最低指导价
    private double maxguide;    // 最高知道价

    public FC_AllSalesListItem() {

    }
    // 标题的构造函数
    public FC_AllSalesListItem(int type, String tText) {
        this.type = type;
        this.charTv = tText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getMinguide() {
        return minguide;
    }

    public void setMinguide(double minguide) {
        this.minguide = minguide;
    }

    public double getMaxguide() {
        return maxguide;
    }

    public void setMaxguide(double maxguide) {
        this.maxguide = maxguide;
    }
}
