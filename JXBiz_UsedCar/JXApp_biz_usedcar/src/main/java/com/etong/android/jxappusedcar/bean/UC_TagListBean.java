package com.etong.android.jxappusedcar.bean;

/**
 * @desc 标签的javabean
 * @createtime 2016/10/26 - 14:04
 * @Created by xiaoxue.
 */

public class UC_TagListBean {
    private String tagName;
    private String tag;//分类标识
    private boolean isBrand;//是不是品牌

    public UC_TagListBean(String tagName, String tag, boolean isBrand) {
        this.tagName = tagName;
        this.tag = tag;
        this.isBrand = isBrand;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isBrand() {
        return isBrand;
    }

    public void setBrand(boolean brand) {
        isBrand = brand;
    }
}

