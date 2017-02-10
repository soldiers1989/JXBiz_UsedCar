package com.etong.android.jxbizusedcar.bean;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/11/8 0008--15:21
 * @Created by wukefan.
 */
public class UC_MessageItemBean {

    private String titleName;
    private String titleImg;
    private String color;

    public UC_MessageItemBean(String titleName, String titleImg, String color) {
        this.titleName = titleName;
        this.titleImg = titleImg;
        this.color = color;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public String getTitleName() {
        return titleName;
    }

    public String getColor() {
        return color;
    }
}
