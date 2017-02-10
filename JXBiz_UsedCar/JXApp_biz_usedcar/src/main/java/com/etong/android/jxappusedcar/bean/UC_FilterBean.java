package com.etong.android.jxappusedcar.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @desc 筛选javabean
 * @createtime 2016/10/26 - 19:50
 * @Created by xiaoxue.
 */

public class UC_FilterBean implements Serializable {

    private Map<String, List<String>> mileAge;
    private Map<String, List<String>> carAge;
    private Map<String, String> vehicleType;
    private Map<String, String> gearBox;
    private Map<String, String> color;
    private Map<String, String> country;
    private Map<String, String> isauthenticate;


    public Map<String, List<String>> getMileAge() {
        return mileAge;
    }

    public void setMileAge(Map<String, List<String>> mileAge) {
        this.mileAge = mileAge;
    }

    public Map<String, List<String>> getCarAge() {
        return carAge;
    }

    public void setCarAge(Map<String, List<String>> carAge) {
        this.carAge = carAge;
    }

    public Map<String, String> getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Map<String, String> vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Map<String, String> getGearBox() {
        return gearBox;
    }

    public void setGearBox(Map<String, String> gearBox) {
        this.gearBox = gearBox;
    }

    public Map<String, String> getColor() {
        return color;
    }

    public void setColor(Map<String, String> color) {
        this.color = color;
    }

    public Map<String, String> getCountry() {
        return country;
    }

    public void setCountry(Map<String, String> country) {
        this.country = country;
    }

    public Map<String, String> getIsauthenticate() {
        return isauthenticate;
    }

    public void setIsauthenticate(Map<String, String> isauthenticate) {
        this.isauthenticate = isauthenticate;
    }
}
