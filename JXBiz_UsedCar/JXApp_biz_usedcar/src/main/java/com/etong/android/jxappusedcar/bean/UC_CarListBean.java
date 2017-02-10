package com.etong.android.jxappusedcar.bean;

/**
 * @desc 买车列表javabean
 * @createtime 2016/10/19 - 14:08
 * @Created by xiaoxue.
 */

public class UC_CarListBean {

    /**
     * f_plate_number : 湘W23453
     * f_cartitle : 宝马-X6 M 2015款 X6 M
     * f_dvid : 00000224
     * f_price : 10
     * f_carsetid : 4672
     * f_registerdate : 2016-08-25
     * f_carset : X6 M
     * f_carbrandid : 33
     * f_cartypeid : 756602
     * image : http://113.247.237.98:10002/data///20160815/870f11ef-7c22-4255-825d-5d81dfc95063.jpg
     * f_mileage : 4
     * f_isauthenticate : 1
     */
    public static int SMALL_IMG = 1;    // 显示小图的布局
    public static int BIG_IMG = 2;      // 显示大图的布局

    private int mType = SMALL_IMG;      // 默认显示为小图

    private String f_plate_number;
    private String f_cartitle;
    private String f_dvid;
    private Double f_price;
    private int f_carsetid;
    private String f_registerdate;
    private String f_carset;
    private int f_carbrandid;
    private int f_cartypeid;
    private String image;
    private Double f_mileage;
    private int f_isauthenticate;

    public String getF_plate_number() {
        return f_plate_number;
    }

    public void setF_plate_number(String f_plate_number) {
        this.f_plate_number = f_plate_number;
    }

    public String getF_cartitle() {
        return f_cartitle;
    }

    public void setF_cartitle(String f_cartitle) {
        this.f_cartitle = f_cartitle;
    }

    public String getF_dvid() {
        return f_dvid;
    }

    public void setF_dvid(String f_dvid) {
        this.f_dvid = f_dvid;
    }

    public Double getF_price() {
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

    public String getF_registerdate() {
        return f_registerdate;
    }

    public void setF_registerdate(String f_registerdate) {
        this.f_registerdate = f_registerdate;
    }

    public String getF_carset() {
        return f_carset;
    }

    public void setF_carset(String f_carset) {
        this.f_carset = f_carset;
    }

    public int getF_carbrandid() {
        return f_carbrandid;
    }

    public void setF_carbrandid(int f_carbrandid) {
        this.f_carbrandid = f_carbrandid;
    }

    public int getF_cartypeid() {
        return f_cartypeid;
    }

    public void setF_cartypeid(int f_cartypeid) {
        this.f_cartypeid = f_cartypeid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getF_mileage() {
        return f_mileage;
    }

    public void setF_mileage(Double f_mileage) {
        this.f_mileage = f_mileage;
    }

    public int getF_isauthenticate() {
        return f_isauthenticate;
    }

    public void setF_isauthenticate(int f_isauthenticate) {
        this.f_isauthenticate = f_isauthenticate;
    }

   /* // 返回该Javabean的类型
    public int getmType() {
        return mType;
    }
    // 设置该Javabean的类型 1 -- 小图  2 -- 大图
    public void setmType(int mType) {
        this.mType = mType;
    }*/
}
