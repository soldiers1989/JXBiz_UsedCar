package com.etong.android.jxappcarfinancial.bean;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/12/5 0005--13:41
 * @Created by wukefan.
 */
public class CF_AllOrderRecordCountBean {


    /**
     * bxTotal : 0
     * sqTotal : 0
     * secordTotal : 0
     * onlineTotal : 0
     * etcTotal : 0
     * financialTotal : 1
     * newTotal : 3
     * kyTotal : 0
     * totalCount : 4
     */


    private Integer bxTotal;
    private Integer sqTotal;
    private Integer secordTotal;
    private Integer onlineTotal;
    private Integer etcTotal;
    private Integer financialTotal;
    private Integer newTotal;
    private Integer kyTotal;
    private Integer totalCount;

    public Integer getBxTotal() {
        if (null == bxTotal)
            bxTotal = 0;
        return bxTotal;
    }

    public void setBxTotal(Integer bxTotal) {
        this.bxTotal = bxTotal;
    }

    public Integer getSqTotal() {
        if (null == sqTotal)
            sqTotal = 0;
        return sqTotal;
    }

    public void setSqTotal(Integer sqTotal) {
        this.sqTotal = sqTotal;
    }

    public Integer getSecordTotal() {
        if (null == secordTotal)
            secordTotal = 0;
        return secordTotal;
    }

    public void setSecordTotal(Integer secordTotal) {
        this.secordTotal = secordTotal;
    }

    public Integer getOnlineTotal() {
        if (null == onlineTotal)
            onlineTotal = 0;
        return onlineTotal;
    }

    public void setOnlineTotal(Integer onlineTotal) {
        this.onlineTotal = onlineTotal;
    }

    public Integer getEtcTotal() {
        if (null == etcTotal)
            etcTotal = 0;
        return etcTotal;
    }

    public void setEtcTotal(Integer etcTotal) {
        this.etcTotal = etcTotal;
    }

    public Integer getFinancialTotal() {
        if (null == financialTotal)
            financialTotal = 0;
        return financialTotal;
    }

    public void setFinancialTotal(Integer financialTotal) {
        this.financialTotal = financialTotal;
    }

    public Integer getNewTotal() {
        if (null == newTotal)
            newTotal = 0;
        return newTotal;
    }

    public void setNewTotal(Integer newTotal) {
        this.newTotal = newTotal;
    }

    public Integer getKyTotal() {
        if (null == kyTotal)
            kyTotal = 0;
        return kyTotal;
    }

    public void setKyTotal(Integer kyTotal) {
        this.kyTotal = kyTotal;
    }


    public Integer getTotalCount() {
        if (null == totalCount)
            totalCount = 0;
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
