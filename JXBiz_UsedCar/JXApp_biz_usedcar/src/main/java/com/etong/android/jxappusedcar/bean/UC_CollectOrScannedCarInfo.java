package com.etong.android.jxappusedcar.bean;

/**
 * Created by Administrator on 2016/10/14.
 */

public class UC_CollectOrScannedCarInfo {


    /**
     * f_collectid : 32
     * f_historyid : 31
     * f_cartitle : 别克 君威 2015款 1.6T 时尚技术型
     * f_registerdate : 2016年08月
     * f_dvid : 00000222
     * image : /resource/images/car-img-loading.png
     * f_mileage : 1111
     * f_isauthenticate : 1
     * f_price : 11
     */

    private int f_collectid;
    private int f_historyid;
    private String f_cartitle;
    private String f_registerdate;
    private String f_dvid;
    private String image;
    private double f_mileage;
    private int f_isauthenticate;
    private double f_price;

    public int getF_collectid() {
        return f_collectid;
    }

    public void setF_collectid(int f_collectid) {
        this.f_collectid = f_collectid;
    }

    public int getF_historyid() {
        return f_historyid;
    }

    public void setF_historyid(int f_historyid) {
        this.f_historyid = f_historyid;
    }

    public String getF_cartitle() {
        return f_cartitle;
    }

    public void setF_cartitle(String f_cartitle) {
        this.f_cartitle = f_cartitle;
    }

    public String getF_registerdate() {
        return f_registerdate;
    }

    public void setF_registerdate(String f_registerdate) {
        this.f_registerdate = f_registerdate;
    }

    public String getF_dvid() {
        return f_dvid;
    }

    public void setF_dvid(String f_dvid) {
        this.f_dvid = f_dvid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getF_mileage() {
        return f_mileage;
    }

    public void setF_mileage(double f_mileage) {
        this.f_mileage = f_mileage;
    }

    public int getF_isauthenticate() {
        return f_isauthenticate;
    }

    public void setF_isauthenticate(int f_isauthenticate) {
        this.f_isauthenticate = f_isauthenticate;
    }

    public double getF_price() {
        return f_price;
    }

    public void setF_price(double f_price) {
        this.f_price = f_price;
    }
}
