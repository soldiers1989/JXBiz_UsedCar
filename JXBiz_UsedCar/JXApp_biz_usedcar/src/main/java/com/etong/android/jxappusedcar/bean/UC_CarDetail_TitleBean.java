package com.etong.android.jxappusedcar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : by sunyao
 * @ClassName : UC_CarDetail_TitleBean
 * @Description : (头部显示的javabean)
 * @date : 2016/10/28 - 18:03
 */

public class UC_CarDetail_TitleBean implements Serializable{

    /**
     * f_cartitle : 宝马 X6 M 2015款 X6 M
     * imgUrlsCount : 8
     * f_brand : 宝马
     * f_dvid : 00000224
     * imgUrls : [{"imgUrl":"http://113.247.237.98:10002/data///20160815/870f11ef-7c22-4255-825d-5d81dfc95063.jpg"},{"imgUrl":"http://113.247.237.98:10002/data///20160815/31eac44e-0c23-4a32-b54c-64554c497eda.jpg"},{"imgUrl":"http://113.247.237.98:10002/data///20160815/b49aeab1-2abb-4742-8ace-d981346a159d.jpg"},{"imgUrl":"http://113.247.237.98:10002/data///20160906/f22d4e01-0727-4c97-926b-a65cfb623d3c.jpg"},{"imgUrl":"http://113.247.237.98:10002/data///20160906/01a83296-ef78-4a2b-9b57-36f17ec3a0eb.jpg"},{"imgUrl":"http://113.247.237.98:10002/data///20160906/4d62c70d-40ea-43ea-bd5b-fbf5bcb8c673.jpg"},{"imgUrl":"http://113.247.237.98:10002/data///20160906/40244754-6873-4ca3-9a54-e75573411e55.jpg"},{"imgUrl":"http://113.247.237.98:10002/data///20160906/88762a43-a121-4739-91d1-b4180c12c5e6.jpg"}]
     * f_price : 10
     * f_collectid : null
     * f_registerdate : 2016年08月
     * f_carposition : B-101,A-103
     * f_mileage : 4
     * f_collectstatus : 0
     * f_clickcount : 3703
     * historyList : {"f_isdelete":true,"f_org_id":"001","f_machinecode":"00000000-1da9-ea9e-ffff-ffff99d603a9","f_vehicleid":"00000224","f_editor":"","f_createtime":1477648275000,"f_historyid":307,"f_userid":""}
     */

    private String f_cartitle;
    private int imgUrlsCount;
    private String f_brand;
    private String f_dvid;
    private String f_price;
    private String f_collectid;
    private String f_registerdate;
    private String f_carposition;
    private String f_mileage;
    private int f_collectstatus;
    private String f_clickcount;
    /**
     * f_isdelete : true
     * f_org_id : 001
     * f_machinecode : 00000000-1da9-ea9e-ffff-ffff99d603a9
     * f_vehicleid : 00000224
     * f_editor :
     * f_createtime : 1477648275000
     * f_historyid : 307
     * f_userid :
     */

    private HistoryListBean historyList;
    /**
     * imgUrl : http://113.247.237.98:10002/data///20160815/870f11ef-7c22-4255-825d-5d81dfc95063.jpg
     */

    private List<ImgUrlsBean> imgUrls;

    public String getF_cartitle() {
        return f_cartitle;
    }

    public void setF_cartitle(String f_cartitle) {
        this.f_cartitle = f_cartitle;
    }

    public int getImgUrlsCount() {
        return imgUrlsCount;
    }

    public void setImgUrlsCount(int imgUrlsCount) {
        this.imgUrlsCount = imgUrlsCount;
    }

    public String getF_brand() {
        return f_brand;
    }

    public void setF_brand(String f_brand) {
        this.f_brand = f_brand;
    }

    public String getF_dvid() {
        return f_dvid;
    }

    public void setF_dvid(String f_dvid) {
        this.f_dvid = f_dvid;
    }

    public String getF_price() {
        return f_price;
    }

    public void setF_price(String f_price) {
        this.f_price = f_price;
    }

    public String getF_collectid() {
        return f_collectid;
    }

    public void setF_collectid(String f_collectid) {
        this.f_collectid = f_collectid;
    }

    public String getF_registerdate() {
        return f_registerdate;
    }

    public void setF_registerdate(String f_registerdate) {
        this.f_registerdate = f_registerdate;
    }

    public String getF_carposition() {
        return f_carposition;
    }

    public void setF_carposition(String f_carposition) {
        this.f_carposition = f_carposition;
    }

    public String getF_mileage() {
        return f_mileage;
    }

    public void setF_mileage(String f_mileage) {
        this.f_mileage = f_mileage;
    }

    public int getF_collectstatus() {
        return f_collectstatus;
    }

    public void setF_collectstatus(int f_collectstatus) {
        this.f_collectstatus = f_collectstatus;
    }

    public String getF_clickcount() {
        return f_clickcount;
    }

    public void setF_clickcount(String f_clickcount) {
        this.f_clickcount = f_clickcount;
    }

    public HistoryListBean getHistoryList() {
        return historyList;
    }

    public void setHistoryList(HistoryListBean historyList) {
        this.historyList = historyList;
    }

    public List<ImgUrlsBean> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<ImgUrlsBean> imgUrls) {
        this.imgUrls = imgUrls;
    }

    public static class HistoryListBean implements Serializable{
        private boolean f_isdelete;
        private String f_org_id;
        private String f_machinecode;
        private String f_vehicleid;
        private String f_editor;
        private long f_createtime;
        private int f_historyid;
        private String f_userid;

        public boolean isF_isdelete() {
            return f_isdelete;
        }

        public void setF_isdelete(boolean f_isdelete) {
            this.f_isdelete = f_isdelete;
        }

        public String getF_org_id() {
            return f_org_id;
        }

        public void setF_org_id(String f_org_id) {
            this.f_org_id = f_org_id;
        }

        public String getF_machinecode() {
            return f_machinecode;
        }

        public void setF_machinecode(String f_machinecode) {
            this.f_machinecode = f_machinecode;
        }

        public String getF_vehicleid() {
            return f_vehicleid;
        }

        public void setF_vehicleid(String f_vehicleid) {
            this.f_vehicleid = f_vehicleid;
        }

        public String getF_editor() {
            return f_editor;
        }

        public void setF_editor(String f_editor) {
            this.f_editor = f_editor;
        }

        public long getF_createtime() {
            return f_createtime;
        }

        public void setF_createtime(long f_createtime) {
            this.f_createtime = f_createtime;
        }

        public int getF_historyid() {
            return f_historyid;
        }

        public void setF_historyid(int f_historyid) {
            this.f_historyid = f_historyid;
        }

        public String getF_userid() {
            return f_userid;
        }

        public void setF_userid(String f_userid) {
            this.f_userid = f_userid;
        }
    }

    public static class ImgUrlsBean implements Serializable{
        private String imgUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
