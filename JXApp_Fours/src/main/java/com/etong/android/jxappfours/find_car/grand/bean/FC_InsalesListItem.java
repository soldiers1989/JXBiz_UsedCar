package com.etong.android.jxappfours.find_car.grand.bean;

import java.io.Serializable;

/**
 * Created by Ellison.Sun on 2016/8/12.
 *
 * Menu中在售车型的List中的单个item
 */
public class FC_InsalesListItem implements Serializable{
    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title
    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // 标题下面的List


    public int type;          // Item的类型
    public String charTv;   // 显示在头部的文字

//    private int id;         // 车系ID
//    private int pid;        // 此处为厂商ID, 父ID
//    private String pTitle;      // 总的 ，悬浮标题
//    private String image;       // 车系上面显示的图片
//    private String fullName;    // 车系完整名称
//    private Double minguide;    // 最低指导价
//    private Double maxguide;    // 最高指导价
//    private int level;              // 车辆等级
//    private String carlevel;        // 车辆等级中文
//    private String outVol;          // 汽车的排量


    private String image;               // 车系上面显示的图片
    private String website;             // 车的官网
    private Integer maxOut;             // 最高排量
    private String level;               // 车辆等级
    private Integer minOut;             // 最低排量
    private String pTitle;              // 总的 ，悬浮标题
    private Integer pid;                // 此处为厂商ID, 父ID
    private String title;               // 简称
    private Double minguide;            // 最低指导价
    private String brandDesc;           // 品牌描述
    private String outVol;              // 汽车的排量
    private String letter;              // 首字母
    private Integer menuId;             // 厂商ID
    private String pLetter;             //
    private Integer id;                 // 车系ID
    private Double maxguide;            // 最高指导价
    private String fullName;            // 车系完整名称
    private String carlevel;            // 车辆等级中文

    public FC_InsalesListItem() {

    }
    // 标题的构造函数
    public FC_InsalesListItem(int type, String tText) {
        this.type = type;
        this.charTv = tText;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getMaxOut() {
        return maxOut;
    }

    public void setMaxOut(Integer maxOut) {
        this.maxOut = maxOut;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getMinOut() {
        return minOut;
    }

    public void setMinOut(Integer minOut) {
        this.minOut = minOut;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getMinguide() {
        return minguide;
    }

    public void setMinguide(Double minguide) {
        this.minguide = minguide;
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    public String getOutVol() {
        return outVol;
    }

    public void setOutVol(String outVol) {
        this.outVol = outVol;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getpLetter() {
        return pLetter;
    }

    public void setpLetter(String pLetter) {
        this.pLetter = pLetter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMaxguide() {
        return maxguide;
    }

    public void setMaxguide(Double maxguide) {
        this.maxguide = maxguide;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCarlevel() {
        return carlevel;
    }

    public void setCarlevel(String carlevel) {
        this.carlevel = carlevel;
    }
}
