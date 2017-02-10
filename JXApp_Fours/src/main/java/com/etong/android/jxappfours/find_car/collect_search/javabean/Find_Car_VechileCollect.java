package com.etong.android.jxappfours.find_car.collect_search.javabean;

/**
 * 收藏车辆列表的javabean
 * Created by Administrator on 2016/8/9.
 */
public class Find_Car_VechileCollect {
    private int id;
    private String title;
    private String imageUri;
    private Double price;
    private int  sales;//销量

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int  getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
