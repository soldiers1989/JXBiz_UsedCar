package com.etong.android.jxappusedcar.bean;

import java.io.Serializable;

/**
 * @desc 查看图片javabean
 * @createtime 2016/10/11 - 19:23
 * @Created by xiaoxue.
 */

public class UC_SelectImageJavabean implements Serializable{
    private String title;
    private String url;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
