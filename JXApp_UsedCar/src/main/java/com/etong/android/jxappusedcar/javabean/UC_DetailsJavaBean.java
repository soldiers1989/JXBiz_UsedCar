package com.etong.android.jxappusedcar.javabean;

/**
 * @desc 二手车详情javabean
 * @createtime 2016/10/10 - 12:31
 * @Created by xiaoxue.
 */

public class UC_DetailsJavaBean {
    private Integer id;
    private Double price;
    private String title;
    private Double new_car_min_price;
    private Double new_car_max_price;
    private String image;
    private String mileage;//里程数
    private String car_age;//车龄
    private String displacement;//排量
    private String AMT;//变速箱
    private String expire;//年检到期
    private String expire1;//交强险到期
    private String expire2;//商业险到期
    private String record_time;//过户记录次数


    private String brief_title;
    private String webview;

    private String service_commitment;


    public String getService_commitment() {
        return service_commitment;
    }

    public void setService_commitment(String service_commitment) {
        this.service_commitment = service_commitment;
    }

    public String getBrief_title() {
        return brief_title;
    }

    public void setBrief_title(String brief_title) {
        this.brief_title = brief_title;
    }

    public String getWebview() {
        return webview;
    }

    public void setWebview(String webview) {
        this.webview = webview;
    }

    public Double getNew_car_min_price() {
        return new_car_min_price;
    }

    public void setNew_car_min_price(Double new_car_min_price) {
        this.new_car_min_price = new_car_min_price;
    }

    public Double getNew_car_max_price() {
        return new_car_max_price;
    }

    public void setNew_car_max_price(Double new_car_max_price) {
        this.new_car_max_price = new_car_max_price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getCar_age() {
        return car_age;
    }

    public void setCar_age(String car_age) {
        this.car_age = car_age;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getAMT() {
        return AMT;
    }

    public void setAMT(String AMT) {
        this.AMT = AMT;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getExpire1() {
        return expire1;
    }

    public void setExpire1(String expire1) {
        this.expire1 = expire1;
    }

    public String getRecord_time() {
        return record_time;
    }

    public void setRecord_time(String record_time) {
        this.record_time = record_time;
    }

    public String getExpire2() {
        return expire2;
    }

    public void setExpire2(String expire2) {
        this.expire2 = expire2;
    }
}
