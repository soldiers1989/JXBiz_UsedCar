package com.etong.android.jxbizusedcar.bean;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/10/25 0025--20:53
 * @Created by wukefan.
 */
public class UC_PriceAndBrandBean {


    /**
     * isBrand : false
     * f_priceid : 0
     * f_price : 3万以下
     * f_brand : 宝马
     * f_carbrandid : 33
     * image : http://113.247.237.98:10002/auto5.1/images/common/brandlogo/bm.jpg
     */

    private boolean isBrand;
    private String f_priceid;
    private String f_price;
    private String f_brand;
    private String f_carbrandid;
    private String image;
    private String all;

    public boolean isIsBrand() {
        return isBrand;
    }

    public void setIsBrand(boolean isBrand) {
        this.isBrand = isBrand;
    }

    public String getF_priceid() {
        return f_priceid;
    }

    public void setF_priceid(String f_priceid) {
        this.f_priceid = f_priceid;
    }

    public String getF_price() {
        return f_price;
    }

    public void setF_price(String f_price) {
        this.f_price = f_price;
    }

    public String getF_brand() {
        return f_brand;
    }

    public void setF_brand(String f_brand) {
        this.f_brand = f_brand;
    }

    public String getF_carbrandid() {
        return f_carbrandid;
    }

    public void setF_carbrandid(String f_carbrandid) {
        this.f_carbrandid = f_carbrandid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }
}
