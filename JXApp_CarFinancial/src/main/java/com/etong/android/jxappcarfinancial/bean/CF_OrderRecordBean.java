package com.etong.android.jxappcarfinancial.bean;

/**
 * @desc (某类订单javabean)
 * @createtime 2016/11/18 0018--9:02
 * @Created by wukefan.
 */
public class CF_OrderRecordBean {




    /**
     * f_dealman1 : 客户创建
     * f_deptname1 : 客户创建
     * f_ordertype1 : 3
     * f_id1 : 10000066
     * f_createtime1 : 2016-11-22
     * f_orderman1 : 好的
     * f_phone1 : 13755174943
     * f_orderinfo1 : 0
     * f_orderid1 : 800906974249222144
     * f_ordertypename : 融资租赁申请
     * f_orderstatusname : 待处理
     */

    private String f_dealman1;
    private String f_deptname1;
    private int f_orderinfo1;
    private int f_ordertype1;           //订单分类id
    private String f_id1;               //申请id
    private String f_createtime1;       //创建时间
    private String f_orderman1;         //申请人
    private String f_phone1;            //申请人手机号
    private String f_orderid1;          //订单编号
    private int f_orderstatus1;         //状态值
    private String f_ordertypename;     //订单分类名字
    private String f_orderstatusname;   //状态名字

    public String getF_dealman1() {
        return f_dealman1;
    }

    public void setF_dealman1(String f_dealman1) {
        this.f_dealman1 = f_dealman1;
    }

    public String getF_deptname1() {
        return f_deptname1;
    }

    public void setF_deptname1(String f_deptname1) {
        this.f_deptname1 = f_deptname1;
    }

    public int getF_ordertype1() {
        return f_ordertype1;
    }

    public void setF_ordertype1(int f_ordertype1) {
        this.f_ordertype1 = f_ordertype1;
    }

    public String getF_id1() {
        return f_id1;
    }

    public void setF_id1(String f_id1) {
        this.f_id1 = f_id1;
    }

    public String getF_createtime1() {
        return f_createtime1;
    }

    public void setF_createtime1(String f_createtime1) {
        this.f_createtime1 = f_createtime1;
    }

    public String getF_orderman1() {
        return f_orderman1;
    }

    public void setF_orderman1(String f_orderman1) {
        this.f_orderman1 = f_orderman1;
    }

    public String getF_phone1() {
        return f_phone1;
    }

    public void setF_phone1(String f_phone1) {
        this.f_phone1 = f_phone1;
    }

    public int getF_orderinfo1() {
        return f_orderinfo1;
    }

    public void setF_orderinfo1(int f_orderinfo1) {
        this.f_orderinfo1 = f_orderinfo1;
    }

    public String getF_orderid1() {
        return f_orderid1;
    }

    public void setF_orderid1(String f_orderid1) {
        this.f_orderid1 = f_orderid1;
    }

    public int getF_orderstatus1() {
        return f_orderstatus1;
    }

    public void setF_orderstatus1(int f_orderstatus1) {
        this.f_orderstatus1 = f_orderstatus1;
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
