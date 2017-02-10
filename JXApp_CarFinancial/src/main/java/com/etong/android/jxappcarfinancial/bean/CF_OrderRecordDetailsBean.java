package com.etong.android.jxappcarfinancial.bean;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/11/22 0022--19:56
 * @Created by wukefan.
 */
public class CF_OrderRecordDetailsBean {

    /**
     * f_deptname : 客户创建
     * f_orderstatus : 0
     * f_edittime : 1479844405000
     * f_phone : 18390809839
     * f_cardid : 430681199303272622
     * f_remark : 测试测试测试
     * f_orderid : 801031299283419136
     * f_orderinfo : 0
     * f_createuser : 我就是我
     * f_dealman : 客户创建
     * f_createtime : 2016-11-22
     * f_editor : 我就是我
     * f_ordertype : 4
     * f_id : 10000078
     * f_orderman : 我就是我
     * f_ordertypename : 融资租赁申请
     * f_orderstatusname : 待处理
     */


    private int f_orderstatus;              //状态值
    private String f_phone;                 //申请人手机号
    private String f_cardid;                //身份证号码
    private String f_remark;                //备注
    private String f_orderid;               //订单编号
    private String f_createtime;            //创建时间
    private int f_ordertype;                //订单分类id
    private int f_id;                       //申请id
    private String f_orderman;              //申请人
    private String f_ordertypename;         //订单分类名字
    private String f_orderstatusname;       //状态名字
    private String f_createuser;
    private String f_dealman;
    private String f_editor;
    private long f_edittime;
    private String f_deptname;
    private int f_orderinfo;

    public String getF_deptname() {
        return f_deptname;
    }

    public void setF_deptname(String f_deptname) {
        this.f_deptname = f_deptname;
    }

    public int getF_orderstatus() {
        return f_orderstatus;
    }

    public void setF_orderstatus(int f_orderstatus) {
        this.f_orderstatus = f_orderstatus;
    }

    public long getF_edittime() {
        return f_edittime;
    }

    public void setF_edittime(long f_edittime) {
        this.f_edittime = f_edittime;
    }

    public String getF_phone() {
        return f_phone;
    }

    public void setF_phone(String f_phone) {
        this.f_phone = f_phone;
    }

    public String getF_cardid() {
        return f_cardid;
    }

    public void setF_cardid(String f_cardid) {
        this.f_cardid = f_cardid;
    }

    public String getF_remark() {
        return f_remark;
    }

    public void setF_remark(String f_remark) {
        this.f_remark = f_remark;
    }

    public String getF_orderid() {
        return f_orderid;
    }

    public void setF_orderid(String f_orderid) {
        this.f_orderid = f_orderid;
    }

    public int getF_orderinfo() {
        return f_orderinfo;
    }

    public void setF_orderinfo(int f_orderinfo) {
        this.f_orderinfo = f_orderinfo;
    }

    public String getF_createuser() {
        return f_createuser;
    }

    public void setF_createuser(String f_createuser) {
        this.f_createuser = f_createuser;
    }

    public String getF_dealman() {
        return f_dealman;
    }

    public void setF_dealman(String f_dealman) {
        this.f_dealman = f_dealman;
    }

    public String getF_createtime() {
        return f_createtime;
    }

    public void setF_createtime(String f_createtime) {
        this.f_createtime = f_createtime;
    }

    public String getF_editor() {
        return f_editor;
    }

    public void setF_editor(String f_editor) {
        this.f_editor = f_editor;
    }

    public int getF_ordertype() {
        return f_ordertype;
    }

    public void setF_ordertype(int f_ordertype) {
        this.f_ordertype = f_ordertype;
    }

    public int getF_id() {
        return f_id;
    }

    public void setF_id(int f_id) {
        this.f_id = f_id;
    }

    public String getF_orderman() {
        return f_orderman;
    }

    public void setF_orderman(String f_orderman) {
        this.f_orderman = f_orderman;
    }

    public String getF_ordertypename() {
        return f_ordertypename;
    }

    public void setF_ordertypename(String f_ordertypename) {
        this.f_ordertypename = f_ordertypename;
    }

    public String getF_orderstatusname() {
        return f_orderstatusname;
    }

    public void setF_orderstatusname(String f_orderstatusname) {
        this.f_orderstatusname = f_orderstatusname;
    }
}
