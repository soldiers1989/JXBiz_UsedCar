package com.etong.android.jxappcarfinancial.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @desc (逾期明细javabean)
 * @createtime 2016/11/29 - 14:13
 * @Created by xiaoxue.
 */

public class CF_OverdueBean  implements Serializable {


    /**
     * FKdate : 2013-12-18
     * fcustid : 11668
     * FPerRevSum : 97780
     * FPeriodTotal : 10
     * SQdate : 2013-11-25
     * FPerRevMouth : 9778
     * overdueList : [{"FActDate":"2014-01-10","FPerRev":"9778","FActRev1":"0","FPeriod":1,"FDate":"2014-01-10"},{"FActDate":"2014-02-24","FPerRev":"9778","FActRev1":"0","FPeriod":2,"FDate":"2014-02-10"},{"FActDate":"2014-03-21","FPerRev":"9778","FActRev1":"500","FPeriod":3,"FDate":"2014-03-10"},{"FActDate":"2014-05-04","FPerRev":"9778","FActRev1":"500","FPeriod":4,"FDate":"2014-04-10"},{"FActDate":"2014-05-22","FPerRev":"9778","FActRev1":"9722","FPeriod":5,"FDate":"2014-05-10"},{"FActDate":"2014-06-23","FPerRev":"9778","FActRev1":"0","FPeriod":6,"FDate":"2014-06-10"}]
     * OverdueTotal : 6
     */

    public String FKdate;
    public String fcustid;
    public String FPerRevSum;
    public String FPeriodTotal;
    public String SQdate;
    public int FPerRevMouth;
    public int OverdueTotal;
    public List<OverdueListBean> overdueList;

    public String getFKdate() {
        return FKdate;
    }

    public void setFKdate(String FKdate) {
        this.FKdate = FKdate;
    }

    public String getFcustid() {
        return fcustid;
    }

    public void setFcustid(String fcustid) {
        this.fcustid = fcustid;
    }

    public String getFPerRevSum() {
        if(null==FPerRevSum||"".equals(FPerRevSum) || TextUtils.isEmpty(FPerRevSum)){
            FPerRevSum="0";
        }
        return FPerRevSum;
    }

    public void setFPerRevSum(String FPerRevSum) {
        this.FPerRevSum = FPerRevSum;
    }

    public String getFPeriodTotal() {
        return FPeriodTotal;
    }

    public void setFPeriodTotal(String FPeriodTotal) {
        this.FPeriodTotal = FPeriodTotal;
    }

    public String getSQdate() {
        return SQdate;
    }

    public void setSQdate(String SQdate) {
        this.SQdate = SQdate;
    }

    public int getFPerRevMouth() {
        return FPerRevMouth;
    }

    public void setFPerRevMouth(int FPerRevMouth) {
        this.FPerRevMouth = FPerRevMouth;
    }

    public int getOverdueTotal() {
        return OverdueTotal;
    }

    public void setOverdueTotal(int OverdueTotal) {
        this.OverdueTotal = OverdueTotal;
    }

    public List<OverdueListBean> getOverdueList() {
        return overdueList;
    }

    public void setOverdueList(List<OverdueListBean> overdueList) {
        this.overdueList = overdueList;
    }

    public static class OverdueListBean  implements Serializable{
        /**
         * FActDate : 2014-01-10
         * FPerRev : 9778
         * FActRev1 : 0
         * FPeriod : 1
         * FDate : 2014-01-10
         */

        public String FActDate;
        public String FPerRev;
        public String FActRev2;
        public int FPeriod;
        public String FDate;

        public String getFActDate() {
            return FActDate;
        }

        public void setFActDate(String FActDate) {
            this.FActDate = FActDate;
        }

        public String getFPerRev() {
            return FPerRev;
        }

        public void setFPerRev(String FPerRev) {
            this.FPerRev = FPerRev;
        }

        public String getFActRev2() {
            return FActRev2;
        }

        public void setFActRev2(String FActRev2) {
            this.FActRev2 = FActRev2;
        }

        public int getFPeriod() {
            return FPeriod;
        }

        public void setFPeriod(int FPeriod) {
            this.FPeriod = FPeriod;
        }

        public String getFDate() {
            return FDate;
        }

        public void setFDate(String FDate) {
            this.FDate = FDate;
        }
    }
}
