package com.etong.android.jxappusedcar.javabean;

import java.io.Serializable;

/**
 * @desc 二手车世界列表javabean
 * @createtime 2016/10/9 0009--15:14
 * @Created by wukefan.
 */
public class UC_World_CarListJavaBean implements Serializable {
    /**
     * image : http://113.247.237.98:10002/datahttp://113.247.237.98:10002/data//2sc/20161123/4cc5d56e-68c0-4661-b7ee-6694189cdabb.png
     * f_carset : X5
     * f_price : 42
     * f_carsetid : 232
     * f_carage : 116天
     * f_cartitle : 宝马-X5 2015款 xDrive28i
     * f_carageyear : 0.32年
     * f_plate_number : 湘A56453
     * f_isauthenticate : 1
     * f_cartypeid : 768648
     * f_registerdate : 2016-08-01
     * f_carbrandid : 33
     * f_createtime : 2016-08-16
     * f_mileage : 10
     * f_dvid : 00000173
     */

    public String image;
    public String f_carset;
    public Double f_price;
    public int f_carsetid;
    public String f_carage;
    public String f_cartitle;
    public String f_carageyear;
    public String f_plate_number;
    public int f_isauthenticate;
    public int f_cartypeid;
    public String f_registerdate;
    public int f_carbrandid;
    public String f_createtime;
    public Double f_mileage;
    public String f_dvid;

    /**
     * f_collectid : 504
     */

    private int f_collectid;
    /**
     * f_registerdate1 : 2016-08-31
     */

    private String f_registerdate1;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getF_carset() {
        return f_carset;
    }

    public void setF_carset(String f_carset) {
        this.f_carset = f_carset;
    }

    public Double getF_price() {
        if (null == f_price)
            f_price = 0.0;
        return f_price;
    }

    public void setF_price(Double f_price) {
        this.f_price = f_price;
    }

    public int getF_carsetid() {
        return f_carsetid;
    }

    public void setF_carsetid(int f_carsetid) {
        this.f_carsetid = f_carsetid;
    }

    public String getF_carage() {
        return f_carage;
    }

    public void setF_carage(String f_carage) {
        this.f_carage = f_carage;
    }

    public String getF_cartitle() {
        return f_cartitle;
    }

    public void setF_cartitle(String f_cartitle) {
        this.f_cartitle = f_cartitle;
    }

    public String getF_carageyear() {
        return f_carageyear;
    }

    public void setF_carageyear(String f_carageyear) {
        this.f_carageyear = f_carageyear;
    }

    public String getF_plate_number() {
        return f_plate_number;
    }

    public void setF_plate_number(String f_plate_number) {
        this.f_plate_number = f_plate_number;
    }

    public int getF_isauthenticate() {
        return f_isauthenticate;
    }

    public void setF_isauthenticate(int f_isauthenticate) {
        this.f_isauthenticate = f_isauthenticate;
    }

    public int getF_cartypeid() {
        return f_cartypeid;
    }

    public void setF_cartypeid(int f_cartypeid) {
        this.f_cartypeid = f_cartypeid;
    }

    public String getF_registerdate() {
        return f_registerdate;
    }

    public void setF_registerdate(String f_registerdate) {
        this.f_registerdate = f_registerdate;
    }

    public int getF_carbrandid() {
        return f_carbrandid;
    }

    public void setF_carbrandid(int f_carbrandid) {
        this.f_carbrandid = f_carbrandid;
    }

    public String getF_createtime() {
        return f_createtime;
    }

    public void setF_createtime(String f_createtime) {
        this.f_createtime = f_createtime;
    }

    public Double getF_mileage() {
        if (null == f_mileage)
            f_mileage = 0.0;
        return f_mileage;
    }

    public void setF_mileage(Double f_mileage) {
        this.f_mileage = f_mileage;
    }

    public String getF_dvid() {
        return f_dvid;
    }

    public void setF_dvid(String f_dvid) {
        this.f_dvid = f_dvid;
    }

    public int getF_collectid() {
        return f_collectid;
    }

    public void setF_collectid(int f_collectid) {
        this.f_collectid = f_collectid;
    }

    public String getF_registerdate1() {
        return f_registerdate1;
    }

    public void setF_registerdate1(String f_registerdate1) {
        this.f_registerdate1 = f_registerdate1;
    }

   /* private String img;
    private String title;
    private int year;
    private Double mileage;
    private String time;
    private Double price;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }*/
}
