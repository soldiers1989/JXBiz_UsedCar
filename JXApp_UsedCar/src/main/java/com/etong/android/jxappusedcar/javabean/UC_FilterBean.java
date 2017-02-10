package com.etong.android.jxappusedcar.javabean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @desc 筛选javabean
 * @createtime 2016/10/26 - 19:50
 * @Created by xiaoxue.
 */

public class UC_FilterBean implements Serializable {

    private Map<String, String> mileAge;
    private Map<String, List<String>> price;
    private Map<String, String> vehicleType;
    private Map<String, String> gearBox;
    private Map<String, String> country;
    private Map<String, String> carAge;

    public Map<String, String> getMileAge() {
        return mileAge;
    }

    public void setMileAge(Map<String, String> mileAge) {
        this.mileAge = mileAge;
    }

    public Map<String, List<String>> getPrice() {
        return price;
    }

    public void setPrice(Map<String, List<String>> price) {
        this.price = price;
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

    public Map<String, String> getCountry() {
        return country;
    }

    public void setCountry(Map<String, String> country) {
        this.country = country;
    }

    public Map<String, String> getCarAge() {
        return carAge;
    }

    public void setCarAge(Map<String, String> carAge) {
        this.carAge = carAge;
    }
}
