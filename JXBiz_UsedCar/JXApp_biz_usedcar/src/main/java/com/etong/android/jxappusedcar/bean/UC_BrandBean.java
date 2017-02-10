package com.etong.android.jxappusedcar.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * @desc 品牌javabean
 * @createtime 2016/10/26 - 19:50
 * @Created by xiaoxue.
 */

public class UC_BrandBean implements Serializable{
    private Map<String,String> brand;
    private Map<String,String> carset;
//    private String brand;
//    private String carset;

    public UC_BrandBean(Map<String,String> brand,Map<String,String> carset) {
        this.brand = brand;
        this.carset = carset;
    }

    public Map<String, String> getBrand() {
        return brand;
    }

    public void setBrand(Map<String, String> brand) {
        this.brand = brand;
    }

    public Map<String, String> getCarset() {
        return carset;
    }

    public void setCarset(Map<String, String> carset) {
        this.carset = carset;
    }
}
