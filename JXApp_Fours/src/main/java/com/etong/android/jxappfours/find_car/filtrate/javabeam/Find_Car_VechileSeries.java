package com.etong.android.jxappfours.find_car.filtrate.javabeam;

import java.io.Serializable;

/**
 *
 * Created by Administrator on 2016/8/11 0011.
 */
public class Find_Car_VechileSeries implements Serializable {

    /**
     * image : images/common/qichetupian/aodi_a6l.jpg##
     * website :
     * maxOut : 2995
     * level : 2
     * minOut : 1798
     * pTitle : 一汽奥迪
     * pid : 146
     * title : A6L
     * minguide : 27.28
     * brandDesc :
     * outVol : 1798	1984	2393	2498
     * letter : A
     * menuId : 146
     * pLetter : Y
     * id : 149
     * maxguide : 74.26
     */

    private String image;
    private String website;
    private Integer maxOut;
    private String level;
    private Integer minOut;
    private String pTitle;
    private Integer pid;
    private String title;
    private Double minguide;
    private String brandDesc;
    private String outVol;
    private String letter;
    private Integer menuId;
    private String pLetter;
    private Integer id;
    private Double maxguide;
    private boolean isRoot;

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

    public String getPTitle() {
        return pTitle;
    }

    public void setPTitle(String pTitle) {
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

    public String getPLetter() {
        return pLetter;
    }

    public void setPLetter(String pLetter) {
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

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

}
