package com.etong.android.jxappusedcar.javabean;

import java.io.Serializable;
import java.util.List;

/**
 * @author : by sunyao
 * @ClassName : UC_CarDetailJavabean
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/12/13 - 16:30
 */

public class UC_CarDetailJavabean implements Serializable{

    /**
     * titleConfig : {"f_assurancedate":"2017年05月","f_carset":"X5","f_inspectiondate":"2019年08月","f_price":"42","f_carsetid":"","f_cartitle":"宝马 X5 2015款 xDrive28i","f_highinsurancedate":"","f_cartype":"2015款 xDrive28i","f_newprice":"58","f_clickcount":"1050","f_transfercount":"0次","plateColor":"黄色","f_insurancedate":"2016年12月","f_cartypeid":"768648","f_registerdate":"2016年08月购买","f_mileage":"10万公里","f_collectstatus":0,"f_brand":"宝马","gearmode":"自动挡","f_emission":"3L","f_dvid":"00000173"}
     * pictureConfig : {"imgUrlsCount":9,"imgUrls":[{"imgUrl":"http://222.247.51.114:10002/data///20161207/1265d320-0bf7-426e-ab4b-c381ab414e13.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/16b43517-6f68-476a-bb81-583452c28e66.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/411cc0f0-9345-4c79-a009-e86cf9b1facb.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/fd6ef929-d9f9-49a4-8d41-4534947b9bd1.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/8a7dab03-4177-4302-a414-6218f6196a28.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/0eb84a11-0fa1-49a3-bbc0-8a7f9253969d.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/3402ead5-43c8-49e5-bb99-ee125be07c55.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/86be17af-9936-440b-a6da-aff3294e22be.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/ce3ebeef-2669-475b-bcbe-20ac9a58d256.jpg"}]}
     */

    private TitleConfigBean titleConfig;
    private PictureConfigBean pictureConfig;

    public TitleConfigBean getTitleConfig() {
        return titleConfig;
    }

    public void setTitleConfig(TitleConfigBean titleConfig) {
        this.titleConfig = titleConfig;
    }

    public PictureConfigBean getPictureConfig() {
        return pictureConfig;
    }

    public void setPictureConfig(PictureConfigBean pictureConfig) {
        this.pictureConfig = pictureConfig;
    }

    public static class TitleConfigBean implements Serializable{
        /**
         * f_assurancedate : 2017年05月
         * f_carset : X5
         * f_inspectiondate : 2019年08月
         * f_price : 42
         * f_carsetid :
         * f_cartitle : 宝马 X5 2015款 xDrive28i
         * f_highinsurancedate :
         * f_cartype : 2015款 xDrive28i
         * f_newprice : 58
         * f_clickcount : 1050
         * f_transfercount : 0次
         * plateColor : 黄色
         * f_insurancedate : 2016年12月
         * f_cartypeid : 768648
         * f_registerdate : 2016年08月购买
         * f_mileage : 10万公里
         * f_collectstatus : 0
         * f_brand : 宝马
         * gearmode : 自动挡
         * f_emission : 3L
         * f_dvid : 00000173
         */

        private String f_assurancedate;
        private String f_carset;
        private String f_inspectiondate;
        private String f_price;
        private String f_carsetid;
        private String f_cartitle;
        private String f_highinsurancedate;
        private String f_cartype;
        private String f_newprice;
        private String f_clickcount;
        private String f_transfercount;
        private String plateColor;
        private String f_insurancedate;
        private String f_cartypeid;
        private String f_registerdate;
        private String f_mileage;
        private int f_collectstatus;
        private String f_brand;
        private String gearmode;
        private String f_emission;
        private String f_dvid;

        public String getF_assurancedate() {
            return f_assurancedate;
        }

        public void setF_assurancedate(String f_assurancedate) {
            this.f_assurancedate = f_assurancedate;
        }

        public String getF_carset() {
            return f_carset;
        }

        public void setF_carset(String f_carset) {
            this.f_carset = f_carset;
        }

        public String getF_inspectiondate() {
            return f_inspectiondate;
        }

        public void setF_inspectiondate(String f_inspectiondate) {
            this.f_inspectiondate = f_inspectiondate;
        }

        public String getF_price() {
            return f_price;
        }

        public void setF_price(String f_price) {
            this.f_price = f_price;
        }

        public String getF_carsetid() {
            return f_carsetid;
        }

        public void setF_carsetid(String f_carsetid) {
            this.f_carsetid = f_carsetid;
        }

        public String getF_cartitle() {
            return f_cartitle;
        }

        public void setF_cartitle(String f_cartitle) {
            this.f_cartitle = f_cartitle;
        }

        public String getF_highinsurancedate() {
            return f_highinsurancedate;
        }

        public void setF_highinsurancedate(String f_highinsurancedate) {
            this.f_highinsurancedate = f_highinsurancedate;
        }

        public String getF_cartype() {
            return f_cartype;
        }

        public void setF_cartype(String f_cartype) {
            this.f_cartype = f_cartype;
        }

        public String getF_newprice() {
            return f_newprice;
        }

        public void setF_newprice(String f_newprice) {
            this.f_newprice = f_newprice;
        }

        public String getF_clickcount() {
            return f_clickcount;
        }

        public void setF_clickcount(String f_clickcount) {
            this.f_clickcount = f_clickcount;
        }

        public String getF_transfercount() {
            return f_transfercount;
        }

        public void setF_transfercount(String f_transfercount) {
            this.f_transfercount = f_transfercount;
        }

        public String getPlateColor() {
            return plateColor;
        }

        public void setPlateColor(String plateColor) {
            this.plateColor = plateColor;
        }

        public String getF_insurancedate() {
            return f_insurancedate;
        }

        public void setF_insurancedate(String f_insurancedate) {
            this.f_insurancedate = f_insurancedate;
        }

        public String getF_cartypeid() {
            return f_cartypeid;
        }

        public void setF_cartypeid(String f_cartypeid) {
            this.f_cartypeid = f_cartypeid;
        }

        public String getF_registerdate() {
            return f_registerdate;
        }

        public void setF_registerdate(String f_registerdate) {
            this.f_registerdate = f_registerdate;
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

        public String getF_brand() {
            return f_brand;
        }

        public void setF_brand(String f_brand) {
            this.f_brand = f_brand;
        }

        public String getGearmode() {
            return gearmode;
        }

        public void setGearmode(String gearmode) {
            this.gearmode = gearmode;
        }

        public String getF_emission() {
            return f_emission;
        }

        public void setF_emission(String f_emission) {
            this.f_emission = f_emission;
        }

        public String getF_dvid() {
            return f_dvid;
        }

        public void setF_dvid(String f_dvid) {
            this.f_dvid = f_dvid;
        }
    }

    public static class PictureConfigBean implements Serializable{
        /**
         * imgUrlsCount : 9
         * imgUrls : [{"imgUrl":"http://222.247.51.114:10002/data///20161207/1265d320-0bf7-426e-ab4b-c381ab414e13.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/16b43517-6f68-476a-bb81-583452c28e66.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/411cc0f0-9345-4c79-a009-e86cf9b1facb.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/fd6ef929-d9f9-49a4-8d41-4534947b9bd1.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/8a7dab03-4177-4302-a414-6218f6196a28.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/0eb84a11-0fa1-49a3-bbc0-8a7f9253969d.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/3402ead5-43c8-49e5-bb99-ee125be07c55.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/86be17af-9936-440b-a6da-aff3294e22be.jpg"},{"imgUrl":"http://222.247.51.114:10002/data///20161207/ce3ebeef-2669-475b-bcbe-20ac9a58d256.jpg"}]
         */

        private int imgUrlsCount;
        private List<ImgUrlsBean> imgUrls;

        public int getImgUrlsCount() {
            return imgUrlsCount;
        }

        public void setImgUrlsCount(int imgUrlsCount) {
            this.imgUrlsCount = imgUrlsCount;
        }

        public List<ImgUrlsBean> getImgUrls() {
            return imgUrls;
        }

        public void setImgUrls(List<ImgUrlsBean> imgUrls) {
            this.imgUrls = imgUrls;
        }

        public static class ImgUrlsBean implements Serializable{
            /**
             * imgUrl : http://222.247.51.114:10002/data///20161207/1265d320-0bf7-426e-ab4b-c381ab414e13.jpg
             */

            private String imgUrl;

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }
    }
}
