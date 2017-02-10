package com.etong.android.jxappme.JavaBeam;

/**
 * 优惠券的javabean
 * Created by Administrator on 2016/8/30.
 */
public class MeCouponItem {
    private String title;
    private Double discount;//9.9
    private String discountCarName;
    private String time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getDiscountCarName() {
        return discountCarName;
    }

    public void setDiscountCarName(String discountCarName) {
        this.discountCarName = discountCarName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
