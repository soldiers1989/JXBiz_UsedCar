package com.etong.android.jxbizusedcar.bean;

import java.io.Serializable;

/**
 * @author : by sunyao
 * @ClassName : UC_CityName
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/10/26 - 14:37
 */

public class UC_CityName implements Serializable{
    private String city_id;
    private String city_name;
    private String letter;

    public UC_CityName() {
    }

    public UC_CityName(String city_id, String city_name) {
        this.city_id = city_id;
        this.city_name = city_name;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
