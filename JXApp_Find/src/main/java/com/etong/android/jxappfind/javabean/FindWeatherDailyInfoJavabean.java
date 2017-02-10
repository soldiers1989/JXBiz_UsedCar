package com.etong.android.jxappfind.javabean;

import java.io.Serializable;

/**
 * @desc  生活指数
 * @createtime 2016/9/28 - 11:00
 * @Created by xiaoxue.
 */

public class FindWeatherDailyInfoJavabean implements Serializable {
    public String brief;
    public String details;
    public String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
