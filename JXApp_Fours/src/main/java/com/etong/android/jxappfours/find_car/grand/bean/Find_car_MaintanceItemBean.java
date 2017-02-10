package com.etong.android.jxappfours.find_car.grand.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class Find_car_MaintanceItemBean implements Serializable{
    /**
     * vehicleno : 湘A-3519M
     * vehiclename : 3厢致臻1.6T6速自动增强版
     * billdate : 2015/11/19
     * deptcode : 0.01
     * amt : 0
     * milimetre : 10019
     * carsname : 观致3
     * sv_id : 7297
     * starts : 6 (0:预约、1:已接车，待派工、2:已派工，在修、3:已完工，待结算、4:已结算、5:已收款、6:已出厂、-1:已作废)
     * svtypeid : 689
     * deptname : 长沙汇顺汽车销售服务有限公司
     * svtype : 事故修理
     */

    private String vehicleno;
    private String vehiclename;
    private String billdate;
    private String deptcode;
    private Double amt;
    private Integer milimetre;
    private String carsname;
    private String sv_id;
    private String starts;
    private String svtypeid;
    private String deptname;
    private String svtype;

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    public String getVehiclename() {
        return vehiclename;
    }

    public void setVehiclename(String vehiclename) {
        this.vehiclename = vehiclename;
    }

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public Integer getMilimetre() {
        return milimetre;
    }

    public void setMilimetre(Integer milimetre) {
        this.milimetre = milimetre;
    }

    public String getCarsname() {
        return carsname;
    }

    public void setCarsname(String carsname) {
        this.carsname = carsname;
    }

    public String getSv_id() {
        return sv_id;
    }

    public void setSv_id(String sv_id) {
        this.sv_id = sv_id;
    }

    public String getStarts() {
        return starts;
    }

    public void setStarts(String starts) {
        this.starts = starts;
    }

    public String getSvtypeid() {
        return svtypeid;
    }

    public void setSvtypeid(String svtypeid) {
        this.svtypeid = svtypeid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getSvtype() {
        return svtype;
    }

    public void setSvtype(String svtype) {
        this.svtype = svtype;
    }


}
