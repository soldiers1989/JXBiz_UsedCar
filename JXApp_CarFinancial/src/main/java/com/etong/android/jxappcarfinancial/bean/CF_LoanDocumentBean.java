package com.etong.android.jxappcarfinancial.bean;

/**
 * @desc 贷款记录详情javabean
 * @createtime 2016/11/18 - 14:10
 * @Created by xiaoxue.
 */

public class CF_LoanDocumentBean {
    private String applyTime;//申请时间
    private String lendingTime;//放款时间
    private String progress;//办理进度
    private String loanAmount;//贷款金额
    private String month;//月供
    private String cardNumber;//卡号尾号

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getLendingTime() {
        return lendingTime;
    }

    public void setLendingTime(String lendingTime) {
        this.lendingTime = lendingTime;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
