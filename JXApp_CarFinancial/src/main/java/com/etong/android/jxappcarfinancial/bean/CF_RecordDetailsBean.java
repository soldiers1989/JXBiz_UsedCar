package com.etong.android.jxappcarfinancial.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @desc (还款明细 javabean)
 * @createtime 2016/11/17 - 20:17
 * @Created by xiaoxue.
 */

public class CF_RecordDetailsBean implements Serializable{


    /**
     * FKdate : 2013-12-18
     * RepaySum : 5
     * RemainSum : 5
     * fcustid : 11668
     * FPerRevSum : 97780
     * FPeriodTotal : 10
     * SQdate : 2013-11-25
     * FPerRevMouth : 9778
     * repayList : [{"FActDate":"2014-01-10","FPerRev":9778,"FActRev1":0,"FPeriod":1,"FDate":"2014-01-10"},{"FActDate":"2014-02-24","FPerRev":9778,"FActRev1":0,"FPeriod":2,"FDate":"2014-02-10"},{"FActDate":"2014-03-21","FPerRev":9778,"FActRev1":500,"FPeriod":3,"FDate":"2014-03-10"},{"FActDate":"2014-05-04","FPerRev":9778,"FActRev1":500,"FPeriod":4,"FDate":"2014-04-10"},{"FActDate":"2014-05-22","FPerRev":9778,"FActRev1":9722,"FPeriod":5,"FDate":"2014-05-10"},{"FActDate":"2014-06-23","FPerRev":9778,"FActRev1":0,"FPeriod":6,"FDate":"2014-06-10"},{"FActDate":"2014-08-01","FPerRev":9778,"FActRev1":0,"FPeriod":7,"FDate":"2014-07-10"},{"FActDate":"2014-09-03","FPerRev":9778,"FActRev1":400,"FPeriod":8,"FDate":"2014-08-10"},{"FActDate":"2014-10-24","FPerRev":9778,"FActRev1":0,"FPeriod":9,"FDate":"2014-09-10"},{"FActDate":"2014-10-24","FPerRev":9778,"FActRev1":200,"FPeriod":10,"FDate":"2014-10-10"}]
     * RemainPaySum : 86458
     */

    public String FKdate;
    public int RepaySum;
    public int RemainSum;
    public String fcustid;
    public String FPerRevSum;
    public int FPeriodTotal;
    public String SQdate;
    public int FPerRevMouth;
    public String RemainPaySum;
    public List<RepayListBean> repayList;

    public String getFKdate() {
        return FKdate;
    }

    public void setFKdate(String FKdate) {
        this.FKdate = FKdate;
    }

    public int getRepaySum() {
        return RepaySum;
    }

    public void setRepaySum(int RepaySum) {
        this.RepaySum = RepaySum;
    }

    public int getRemainSum() {
        return RemainSum;
    }

    public void setRemainSum(int RemainSum) {
        this.RemainSum = RemainSum;
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

    public int getFPeriodTotal() {
        return FPeriodTotal;
    }

    public void setFPeriodTotal(int FPeriodTotal) {
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

    public String getRemainPaySum() {
        return RemainPaySum;
    }

    public void setRemainPaySum(String RemainPaySum) {
        this.RemainPaySum = RemainPaySum;
    }

    public List<RepayListBean> getRepayList() {
        return repayList;
    }

    public void setRepayList(List<RepayListBean> repayList) {
        this.repayList = repayList;
    }

    public static class RepayListBean implements Serializable {
        /**
         * FActDate : 2014-01-10
         * FPerRev : 9778
         * FActRev1 : 0
         * FPeriod : 1
         * FDate : 2014-01-10
         */

        public String FActDate;
        public int FPerRev;
        public int FActRev2;
        public int FPeriod;
        public String FDate;

        public String getFActDate() {
            return FActDate;
        }

        public void setFActDate(String FActDate) {
            this.FActDate = FActDate;
        }

        public int getFPerRev() {
            return FPerRev;
        }

        public void setFPerRev(int FPerRev) {
            this.FPerRev = FPerRev;
        }

        public int getFActRev2() {
            return FActRev2;
        }

        public void setFActRev2(int FActRev2) {
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
