package com.etong.android.jxappfind.javabean;

/**
 * 全部车款javabean
 * Created by Administrator on 2016/9/2.
 */
public class FindAllCarBean {
    private Integer id;
    private Double guidPrice;
    private String title;
    private String image;
    private String year;
    boolean isFirst;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getGuidPrice() {
        return guidPrice;
    }

    public void setGuidPrice(Double guidPrice) {
        this.guidPrice = guidPrice;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }
}
