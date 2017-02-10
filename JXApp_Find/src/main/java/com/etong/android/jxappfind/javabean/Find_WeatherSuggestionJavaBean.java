package com.etong.android.jxappfind.javabean;

import java.io.Serializable;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/9/19 0019--11:05
 * @Created by wukefan.
 */
public class Find_WeatherSuggestionJavaBean implements Serializable{


    /**
     * date : 2016-09-19
     * text_day : 晴
     * code_day : 0
     * text_night : 晴
     * code_night : 0
     * high : 25
     * low : 13
     * precip :
     * wind_direction : 无持续风向
     * wind_direction_degree :
     * wind_speed : 10
     * wind_scale : 2
     */

    private String date;//日期
    private String text_day; //白天天气现象文字
    private String code_day; //白天天气现象代码
    private String text_night;//晚间天气现象文字
    private String code_night;//晚间天气现象代码
    private String high;  //当天最高温度
    private String low; //当天最低温度
    private String precip;  //降水概率，范围0~100，单位百分比
    private String wind_direction;//风向文字
    private String wind_direction_degree;//风向角度，范围0~360
    private String wind_speed;//风速，单位km/h（当unit=c时）、mph（当unit=f时）
    private String wind_scale;//风力等级

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText_day() {
        return text_day;
    }

    public void setText_day(String text_day) {
        this.text_day = text_day;
    }

    public String getCode_day() {
        return code_day;
    }

    public void setCode_day(String code_day) {
        this.code_day = code_day;
    }

    public String getText_night() {
        return text_night;
    }

    public void setText_night(String text_night) {
        this.text_night = text_night;
    }

    public String getCode_night() {
        return code_night;
    }

    public void setCode_night(String code_night) {
        this.code_night = code_night;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getPrecip() {
        return precip;
    }

    public void setPrecip(String precip) {
        this.precip = precip;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getWind_direction_degree() {
        return wind_direction_degree;
    }

    public void setWind_direction_degree(String wind_direction_degree) {
        this.wind_direction_degree = wind_direction_degree;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getWind_scale() {
        return wind_scale;
    }

    public void setWind_scale(String wind_scale) {
        this.wind_scale = wind_scale;
    }
}
