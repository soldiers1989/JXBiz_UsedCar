package com.etong.android.jxappcarassistant.gas_tation.javabean;

import java.io.Serializable;

/**
 * @desc 附近加油站
 * @createtime 2016/9/19 - 8:52
 * @Created by xiaoxue.
 */
public class Gas_StationJavabean implements Serializable{

    /**
     * id : 36145
     * name : 桂溪南站加油站‎
     * area : 610041
     * areaname : 四川省 成都市 武侯区
     * address : 四川省成都市武侯区南站西路21 - 4 公里 东南
     * brandname : 不详
     * type : 其他
     * discount : 非打折加油站
     * exhaust : 国Ⅳ
     * position : 104.060289,30.608449
     * lon : 104.06675250977
     * lat : 30.614603397793
     * price : {"E90":"6.99","E93":"7.74","E97":"8.04","E0":"7.34"}
     * gastprice : {"o93":"7.74","o97":"8.34"}
     * fwlsmc : 加油卡
     * distance : 1645
     */

    private String id;
    private String name;
    private String area;
    private String areaname;
    private String address;
    private String brandname;
    private String type;
    private String discount;
    private String exhaust;
    private String position;
    private String lon;
    private String lat;
    /**
     * E90 : 6.99
     * E93 : 7.74
     * E97 : 8.04
     * E0 : 7.34
     */

    private PriceBean price;
    /**
     * o93 : 7.74
     * o97 : 8.34
     */

//    private GastpriceBean gastprice;
    private String fwlsmc;
    private Double distance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getExhaust() {
        return exhaust;
    }

    public void setExhaust(String exhaust) {
        this.exhaust = exhaust;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public PriceBean getPrice() {
        return price;
    }

    public void setPrice(PriceBean price) {
        this.price = price;
    }

//    public GastpriceBean getGastprice() {
//        return gastprice;
//    }
//
//    public void setGastprice(GastpriceBean gastprice) {
//        this.gastprice = gastprice;
//    }

    public String getFwlsmc() {
        return fwlsmc;
    }

    public void setFwlsmc(String fwlsmc) {
        this.fwlsmc = fwlsmc;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public static class PriceBean implements Serializable {
        public String E90;
        public String E93;
        public String E97;
        public String E0;

        public PriceBean() {
            super();
        }

        public String getE90() {
            return E90;
        }

        public void setE90(String E90) {
            this.E90 = E90;
        }

        public String getE93() {
            return E93;
        }

        public void setE93(String E93) {
            this.E93 = E93;
        }

        public String getE97() {
            return E97;
        }

        public void setE97(String E97) {
            this.E97 = E97;
        }

        public String getE0() {
            return E0;
        }

        public void setE0(String E0) {
            this.E0 = E0;
        }
    }

    public static class GastpriceBean {
        public String o93;
        public String o97;
        public String o92;
        public String o95;
        public String o0;
        public GastpriceBean() {
            super();
        }
        public String getO93() {
            return o93;
        }

        public void setO93(String o93) {
            this.o93 = o93;
        }

        public String getO97() {
            return o97;
        }

        public void setO97(String o97) {
            this.o97 = o97;
        }

        public String getO95() {
            return o95;
        }

        public void setO95(String o95) {
            this.o95 = o95;
        }

        public String getO0() {
            return o0;
        }

        public void setO0(String o0) {
            this.o0 = o0;
        }

        public String getO92() {
            return o92;
        }

        public void setO92(String o92) {
            this.o92 = o92;
        }
    }
}
