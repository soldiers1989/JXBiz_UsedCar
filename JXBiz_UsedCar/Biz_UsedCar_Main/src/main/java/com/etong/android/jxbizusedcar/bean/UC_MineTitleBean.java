package com.etong.android.jxbizusedcar.bean;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/11/8 0008--15:21
 * @Created by wukefan.
 */
public class UC_MineTitleBean {

    private String titleName;
    private String titleImg;
    private boolean hasChild;

    public UC_MineTitleBean(String titleName, String titleImg, boolean hasChild) {
        this.titleName = titleName;
        this.titleImg = titleImg;
        this.hasChild = hasChild;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public String getTitleName() {
        return titleName;
    }

    public boolean isHasChild() {
        return hasChild;
    }
}
