package com.etong.android.jxappusedcar.bean;

import java.util.List;

/**
 * @author : by sunyao
 * @ClassName : UC_CarDetailTotal_Bean
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/10/28 - 9:52
 */

public class UC_CarDetailTotal_Bean {

    /**
     * fqListCount : 0
     * fqList : null
     */

    private FqConfigBean fqConfig;
    /**
     * f_cartitle : 比亚迪 S8 2009款 2.0 手动尊贵型
     * imgUrlsCount : 3
     * f_brand : 比亚迪
     * f_dvid : 00000186
     * imgUrls : [{"imgUrl":"http://113.247.237.98:10002/data///20161019/3f477303-5860-4b97-b3d0-00caafa7651c.jpg"},{"imgUrl":"http://113.247.237.98:10002/data///20161019/a08afb73-6046-4cc4-b958-0f8aba9f9eb0.jpg"},{"imgUrl":"http://113.247.237.98:10002/data///20161027/f7ba60b0-da9d-4951-8868-8f790370c2d1.jpg"}]
     * f_price : 15
     * f_collectid : null
     * f_registerdate : 2016年08月
     * f_carposition : A-104,A-105
     * f_mileage : 50
     * f_collectstatus : 0
     * f_clickcount : 38
     * historyList : {"f_isdelete":true,"f_org_id":"001","f_machinecode":"EA-IPHONE6","f_vehicleid":"00000186","f_editor":"1","f_createtime":1477617263000,"f_historyid":241,"f_userid":"1"}
     */

    private TitleConfigBean titleConfig;
    /**
     * engine : 2.0L 140马力 L4BYD483QB
     * plateColor : 靛蓝
     * drivesystem : 前置前驱
     * carlever : 跑车
     * maxSpeed : 180
     * gearmode : 自动挡
     * fuelLabel : 97号
     */

    private CarConfigBean carConfig;
    /**
     * ldList : []
     * ldListCount : 0
     */

    private LdConfigBean ldConfig;
    /**
     * f_transfercount : null
     * f_assurancedate :
     * f_repairtype :
     * warranty : 两年或6万公里
     * envstandard : 国Ⅰ
     * f_inspectiondate : 2017年08月
     * f_insurancedate :
     * f_useproperty : 公路客运
     */

    private CarHistoryBean carHistory;

    public FqConfigBean getFqConfig() {
        return fqConfig;
    }

    public void setFqConfig(FqConfigBean fqConfig) {
        this.fqConfig = fqConfig;
    }

    public TitleConfigBean getTitleConfig() {
        return titleConfig;
    }

    public void setTitleConfig(TitleConfigBean titleConfig) {
        this.titleConfig = titleConfig;
    }

    public CarConfigBean getCarConfig() {
        return carConfig;
    }

    public void setCarConfig(CarConfigBean carConfig) {
        this.carConfig = carConfig;
    }

    public LdConfigBean getLdConfig() {
        return ldConfig;
    }

    public void setLdConfig(LdConfigBean ldConfig) {
        this.ldConfig = ldConfig;
    }

    public CarHistoryBean getCarHistory() {
        return carHistory;
    }

    public void setCarHistory(CarHistoryBean carHistory) {
        this.carHistory = carHistory;
    }

    public static class FqConfigBean {
        private int fqListCount;
        private Object fqList;

        public int getFqListCount() {
            return fqListCount;
        }

        public void setFqListCount(int fqListCount) {
            this.fqListCount = fqListCount;
        }

        public Object getFqList() {
            return fqList;
        }

        public void setFqList(Object fqList) {
            this.fqList = fqList;
        }
    }

    public static class TitleConfigBean {
        private String f_cartitle;
        private int imgUrlsCount;
        private String f_brand;
        private String f_dvid;
        private String f_price;
        private Object f_collectid;
        private String f_registerdate;
        private String f_carposition;
        private String f_mileage;
        private int f_collectstatus;
        private String f_clickcount;
        /**
         * f_isdelete : true
         * f_org_id : 001
         * f_machinecode : EA-IPHONE6
         * f_vehicleid : 00000186
         * f_editor : 1
         * f_createtime : 1477617263000
         * f_historyid : 241
         * f_userid : 1
         */

        private HistoryListBean historyList;
        /**
         * imgUrl : http://113.247.237.98:10002/data///20161019/3f477303-5860-4b97-b3d0-00caafa7651c.jpg
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

        public Object getF_collectid() {
            return f_collectid;
        }

        public void setF_collectid(Object f_collectid) {
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

        public static class HistoryListBean {
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

        public static class ImgUrlsBean {
            private String imgUrl;

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }
    }

    public static class CarConfigBean {
        private String engine;
        private String plateColor;
        private String drivesystem;
        private String carlever;
        private String maxSpeed;
        private String gearmode;
        private String fuelLabel;

        public String getEngine() {
            return engine;
        }

        public void setEngine(String engine) {
            this.engine = engine;
        }

        public String getPlateColor() {
            return plateColor;
        }

        public void setPlateColor(String plateColor) {
            this.plateColor = plateColor;
        }

        public String getDrivesystem() {
            return drivesystem;
        }

        public void setDrivesystem(String drivesystem) {
            this.drivesystem = drivesystem;
        }

        public String getCarlever() {
            return carlever;
        }

        public void setCarlever(String carlever) {
            this.carlever = carlever;
        }

        public String getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(String maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        public String getGearmode() {
            return gearmode;
        }

        public void setGearmode(String gearmode) {
            this.gearmode = gearmode;
        }

        public String getFuelLabel() {
            return fuelLabel;
        }

        public void setFuelLabel(String fuelLabel) {
            this.fuelLabel = fuelLabel;
        }
    }

    public static class LdConfigBean {
        private int ldListCount;
        private List<?> ldList;

        public int getLdListCount() {
            return ldListCount;
        }

        public void setLdListCount(int ldListCount) {
            this.ldListCount = ldListCount;
        }

        public List<?> getLdList() {
            return ldList;
        }

        public void setLdList(List<?> ldList) {
            this.ldList = ldList;
        }
    }

    public static class CarHistoryBean {
        private Object f_transfercount;
        private String f_assurancedate;
        private String f_repairtype;
        private String warranty;
        private String envstandard;
        private String f_inspectiondate;
        private String f_insurancedate;
        private String f_useproperty;

        public Object getF_transfercount() {
            return f_transfercount;
        }

        public void setF_transfercount(Object f_transfercount) {
            this.f_transfercount = f_transfercount;
        }

        public String getF_assurancedate() {
            return f_assurancedate;
        }

        public void setF_assurancedate(String f_assurancedate) {
            this.f_assurancedate = f_assurancedate;
        }

        public String getF_repairtype() {
            return f_repairtype;
        }

        public void setF_repairtype(String f_repairtype) {
            this.f_repairtype = f_repairtype;
        }

        public String getWarranty() {
            return warranty;
        }

        public void setWarranty(String warranty) {
            this.warranty = warranty;
        }

        public String getEnvstandard() {
            return envstandard;
        }

        public void setEnvstandard(String envstandard) {
            this.envstandard = envstandard;
        }

        public String getF_inspectiondate() {
            return f_inspectiondate;
        }

        public void setF_inspectiondate(String f_inspectiondate) {
            this.f_inspectiondate = f_inspectiondate;
        }

        public String getF_insurancedate() {
            return f_insurancedate;
        }

        public void setF_insurancedate(String f_insurancedate) {
            this.f_insurancedate = f_insurancedate;
        }

        public String getF_useproperty() {
            return f_useproperty;
        }

        public void setF_useproperty(String f_useproperty) {
            this.f_useproperty = f_useproperty;
        }
    }
}
