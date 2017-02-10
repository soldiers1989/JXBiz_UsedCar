package com.etong.android.jxappcarfinancial.bean;

/**
 * @desc 查询金融机构得到数据的javabean
 * @createtime 2016/11/22 - 15:44
 * @Created by xiaoxue.
 */

public class CF_RegisterBean {
    private Integer f_institutId;  //金融机构id
    private String f_institutName; //金融机构名称
    private String f_institutPhone;//金融机构电话
    private String  f_institutLogs;//图标

    public String getF_institutLogs() {
        return f_institutLogs;
    }

    public void setF_institutLogs(String f_institutLogs) {
        this.f_institutLogs = f_institutLogs;
    }

    public Integer getF_institutId() {
        return f_institutId;
    }

    public void setF_institutId(Integer f_institutId) {
        this.f_institutId = f_institutId;
    }

    public String getF_institutName() {
        return f_institutName;
    }

    public void setF_institutName(String f_institutName) {
        this.f_institutName = f_institutName;
    }

    public String getF_institutPhone() {
        return f_institutPhone;
    }

    public void setF_institutPhone(String f_institutPhone) {
        this.f_institutPhone = f_institutPhone;
    }
}
