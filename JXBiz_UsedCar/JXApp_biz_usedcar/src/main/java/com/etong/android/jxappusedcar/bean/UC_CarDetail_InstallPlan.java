package com.etong.android.jxappusedcar.bean;

import java.util.List;

/**
 * @author : by sunyao
 * @ClassName : UC_CarDetail_InstallPlan
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/10/28 - 18:02
 */

public class UC_CarDetail_InstallPlan {


    /**
     * fqListCount : 3
     * fqList : [{"f_stage_num":12,"f_org_id":"001","f_fapid":"10105070","f_month_rate":3,"monthPay":8498,"f_firstpay":1,"f_planname":"弘融易","f_remark":"","f_company":"湖南鼎力科技有限公司","firstPay":0.1},{"f_stage_num":12,"f_org_id":"001","f_fapid":"10105071","f_month_rate":2,"monthPay":8330,"f_firstpay":2,"f_planname":"平安金融","f_remark":"专注  我们是认真的","f_company":"湖南聊撇科技有限公司","firstPay":0.2},{"f_stage_num":36,"f_org_id":"001","f_fapid":"10105072","f_month_rate":2,"monthPay":2777,"f_firstpay":2,"f_planname":"海发金融","f_remark":"","f_company":"湖南海发金融有限公司","firstPay":0.2}]
     */

    private int fqListCount;
    /**
     * f_stage_num : 12
     * f_org_id : 001
     * f_fapid : 10105070
     * f_month_rate : 3
     * monthPay : 8498
     * f_firstpay : 1
     * f_planname : 弘融易
     * f_remark :
     * f_company : 湖南鼎力科技有限公司
     * firstPay : 0.1
     */

    private List<FqListBean> fqList;

    public int getFqListCount() {
        return fqListCount;
    }

    public void setFqListCount(int fqListCount) {
        this.fqListCount = fqListCount;
    }

    public List<FqListBean> getFqList() {
        return fqList;
    }

    public void setFqList(List<FqListBean> fqList) {
        this.fqList = fqList;
    }

    public static class FqListBean {
        private int f_stage_num;
        private String f_org_id;
        private String f_fapid;
        private int f_month_rate;
        private int monthPay;
        private int f_firstpay;
        private String f_planname;
        private String f_remark;
        private String f_company;
        private double firstPay;

        public int getF_stage_num() {
            return f_stage_num;
        }

        public void setF_stage_num(int f_stage_num) {
            this.f_stage_num = f_stage_num;
        }

        public String getF_org_id() {
            return f_org_id;
        }

        public void setF_org_id(String f_org_id) {
            this.f_org_id = f_org_id;
        }

        public String getF_fapid() {
            return f_fapid;
        }

        public void setF_fapid(String f_fapid) {
            this.f_fapid = f_fapid;
        }

        public int getF_month_rate() {
            return f_month_rate;
        }

        public void setF_month_rate(int f_month_rate) {
            this.f_month_rate = f_month_rate;
        }

        public int getMonthPay() {
            return monthPay;
        }

        public void setMonthPay(int monthPay) {
            this.monthPay = monthPay;
        }

        public int getF_firstpay() {
            return f_firstpay;
        }

        public void setF_firstpay(int f_firstpay) {
            this.f_firstpay = f_firstpay;
        }

        public String getF_planname() {
            return f_planname;
        }

        public void setF_planname(String f_planname) {
            this.f_planname = f_planname;
        }

        public String getF_remark() {
            return f_remark;
        }

        public void setF_remark(String f_remark) {
            this.f_remark = f_remark;
        }

        public String getF_company() {
            return f_company;
        }

        public void setF_company(String f_company) {
            this.f_company = f_company;
        }

        public double getFirstPay() {
            return firstPay;
        }

        public void setFirstPay(double firstPay) {
            this.firstPay = firstPay;
        }
    }
}
