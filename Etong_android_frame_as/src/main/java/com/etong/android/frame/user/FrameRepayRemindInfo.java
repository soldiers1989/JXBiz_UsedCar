package com.etong.android.frame.user;

/**
 * @author : by sunyao
 * @ClassName : FrameRepayRemindInfo
 * @Description : (还款提醒中的javabean)
 * @date : 2016/12/5 - 18:09
 */

public class FrameRepayRemindInfo {
    /**
     * data : 您的还款3天后即将到期，请及时还款！
     * fDate : 2012-10-19
     * fPeriod : 第1期
     * fPerRev : 应还1709
     * time : 2016-12-06 10:44:05
     *
     * {"data":"您的还款3天后即将到期，请及时还款！","fDate":"2012-10-19","fPeriod":"第1期","fPerRev":"应还1709","time":"2016-12-06 10:44:05"}
     *
     */

    private String data;
    private String fDate;
    private String fPeriod;
    private String fPerRev;
    private String time;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFDate() {
        return fDate;
    }

    public void setFDate(String fDate) {
        this.fDate = fDate;
    }

    public String getFPeriod() {
        return fPeriod;
    }

    public void setFPeriod(String fPeriod) {
        this.fPeriod = fPeriod;
    }

    public String getFPerRev() {
        return fPerRev;
    }

    public void setFPerRev(String fPerRev) {
        this.fPerRev = fPerRev;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
