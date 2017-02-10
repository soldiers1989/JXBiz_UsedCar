package com.etong.android.jxappcarassistant.violation_query.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @desc (违章查询的javabean)
 * @createtime 2016/11/23 - 11:13
 * @Created by xiaoxue.
 */

public class CA_ViolationQueryBean implements Serializable {


    /**
     * lsnum : A1E79H
     * usercarid : 4966276
     * lsprefix : 湘
     * carorg : changsha
     * list : [{"score":"3","address":"长沙市枫林路的白云路路口","agency":"","illegalid":"6262825","price":"100","time":"2016-09-24 23:22:49","legalnum":"1345","content":"机动车违反禁止标线指示的"}]
     */

    private String lsnum;
    private String usercarid;
    private String lsprefix;
    private String carorg;
    /**
     * score : 3
     * address : 长沙市枫林路的白云路路口
     * agency :
     * illegalid : 6262825
     * price : 100
     * time : 2016-09-24 23:22:49
     * legalnum : 1345
     * content : 机动车违反禁止标线指示的
     */

    private List<ListBean> list ;

    public String getLsnum() {
        return lsnum;
    }

    public void setLsnum(String lsnum) {
        this.lsnum = lsnum;
    }

    public String getUsercarid() {
        return usercarid;
    }

    public void setUsercarid(String usercarid) {
        this.usercarid = usercarid;
    }

    public String getLsprefix() {
        return lsprefix;
    }

    public void setLsprefix(String lsprefix) {
        this.lsprefix = lsprefix;
    }

    public String getCarorg() {
        return carorg;
    }

    public void setCarorg(String carorg) {
        this.carorg = carorg;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        private String score;
        private String address;
        private String agency;
        private String illegalid;
        private String price;
        private String time;
        private String legalnum;
        private String content;

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAgency() {
            return agency;
        }

        public void setAgency(String agency) {
            this.agency = agency;
        }

        public String getIllegalid() {
            return illegalid;
        }

        public void setIllegalid(String illegalid) {
            this.illegalid = illegalid;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getLegalnum() {
            return legalnum;
        }

        public void setLegalnum(String legalnum) {
            this.legalnum = legalnum;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
