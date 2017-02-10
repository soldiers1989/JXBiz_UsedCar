package com.etong.android.jxappfours.find_car.grand.bean;

import java.io.Serializable;

/**
 * Created by Ellison.Sun on 2016/8/25.
 */
public class FC_CalcuCarPriceBean implements Serializable{

    String carName;
    int carPrice;
    String fromWhere;

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(int carPrice) {
        this.carPrice = carPrice;
    }

    public String getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(String fromWhere) {
        this.fromWhere = fromWhere;
    }
}
