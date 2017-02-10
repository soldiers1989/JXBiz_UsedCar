package com.etong.android.jxappusedcar.bean;

import java.io.Serializable;

/**
 * @author : by sunyao
 * @ClassName : UC_SelectBrand_ExtraBean
 * @Description : (从买车界面中选择品牌，品牌传送过去的javabean)
 * @date : 2016/10/25 - 19:49
 */

public class UC_SelectBrand_ExtraBean implements Serializable {

    private int brandId;
    private int carsetId;
    private String brandName;
    private String carsetName;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCarsetName() {
        return carsetName;
    }

    public void setCarsetName(String carsetName) {
        this.carsetName = carsetName;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getCarsetId() {
        return carsetId;
    }

    public void setCarsetId(int carsetId) {
        this.carsetId = carsetId;
    }
}
