package com.etong.android.jxbizusedcar.bean;

import java.io.Serializable;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/11/9 0009--19:12
 * @Created by wukefan.
 */
public class UC_Identify_OrderListBean implements Serializable {


    /**
     * f_payamt : 22
     * f_status : 4
     * f_updatetime : 1478686457000
     * f_vin : 1424242
     * f_paytype : 1
     * f_licensePlate : 1354354
     * f_engine : 1354354
     * f_createtime : 1478685028000
     * f_cid : 111111
     */

    private String f_cid;               //主键 订单号
    private String f_payamt;            //支付金额
    private String f_status;            //流程记录（状态码）
    private String f_updatetime;        //订单更新时间
    private String f_vin;               //车辆vin码
    private String f_paytype;           //支付类型
    private String f_licensePlate;      //车牌号
    private String f_engine;            //发动机号
    private String f_createtime;        //订单创建时间
    private String f_reporturl="";      // 报告的地址


    public String getF_payamt() {
        if (null == f_payamt) {
            f_payamt = "18";
        }
        return f_payamt;
    }

    public void setF_payamt(String f_payamt) {
        this.f_payamt = f_payamt;
    }

    public String getF_status() {
        if (null == f_status) {
            f_status = "-1";
        }
        return f_status;
    }

    public void setF_status(String f_status) {
        this.f_status = f_status;
    }

    public String getF_updatetime() {
        return f_updatetime;
    }

    public void setF_updatetime(String f_updatetime) {
        this.f_updatetime = f_updatetime;
    }

    public String getF_vin() {
        return f_vin;
    }

    public void setF_vin(String f_vin) {
        this.f_vin = f_vin;
    }

    public String getF_paytype() {
        return f_paytype;
    }

    public void setF_paytype(String f_paytype) {
        this.f_paytype = f_paytype;
    }

    public String getF_licensePlate() {
        return f_licensePlate;
    }

    public void setF_licensePlate(String f_licensePlate) {
        this.f_licensePlate = f_licensePlate;
    }

    public String getF_engine() {
        return f_engine;
    }

    public void setF_engine(String f_engine) {
        this.f_engine = f_engine;
    }

    public String getF_createtime() {
        return f_createtime;
    }

    public void setF_createtime(String f_createtime) {
        this.f_createtime = f_createtime;
    }

    public String getF_cid() {
        return f_cid;
    }

    public void setF_cid(String f_cid) {
        this.f_cid = f_cid;
    }

    public String getF_reporturl() {
        return f_reporturl;
    }

    public void setF_reporturl(String f_reporturl) {
        this.f_reporturl = f_reporturl;
    }
}
