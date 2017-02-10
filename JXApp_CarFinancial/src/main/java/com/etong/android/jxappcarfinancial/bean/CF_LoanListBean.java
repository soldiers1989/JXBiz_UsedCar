package com.etong.android.jxappcarfinancial.bean;

import java.io.Serializable;

/**
 * @desc (贷款列表查询javabean)
 * @createtime 2016/11/24 - 17:41
 * @Created by xiaoxue.
 */

public class CF_LoanListBean implements Serializable {

            /*"SQdate": "2013-01-01",
            "JDStatus": "审核中",
            "repayCard": "9159",
            "loanAmount": 90000,
            "FKdate": "--",
            "monthRepay": 2761*/

    public String SQdate;
    public String JDStatus;
    public String repayCard;
    public int loanAmount;
    public String FKdate;
    public int monthRepay;

    public String getSQdate() {
        return SQdate;
    }

    public void setSQdate(String SQdate) {
        this.SQdate = SQdate;
    }

    public int  getMonthRepay() {
        return monthRepay;
    }

    public void setMonthRepay(int monthRepay) {
        this.monthRepay = monthRepay;
    }

    public String getFKdate() {
        return FKdate;
    }

    public void setFKdate(String FKdate) {
        this.FKdate = FKdate;
    }

    public int  getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getRepayCard() {
        return repayCard;
    }

    public void setRepayCard(String repayCard) {
        this.repayCard = repayCard;
    }

    public String getJDStatus() {
        return JDStatus;
    }

    public void setJDStatus(String JDStatus) {
        this.JDStatus = JDStatus;
    }
}
