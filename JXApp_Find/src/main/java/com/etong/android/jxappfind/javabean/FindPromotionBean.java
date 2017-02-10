package com.etong.android.jxappfind.javabean;

/**
 * 促销车javabean
 * Created by Administrator on 2016/9/2.
 */
public class FindPromotionBean {

    private boolean isLoaded;

    private String title;
    private String zhiiang;
    private String image;
    private int car_id;
    private String cuxiaojia;
    private String save_money;
    private Long end_seconds;
    private String guide_price;
    private String time_range;
    private String end;
    private Boolean end_key = true;
    private String colors;
    private String comment1;
    private String comment2;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setZhiiang(String zhiiang) {
        this.zhiiang = zhiiang;
    }

    public String getZhiiang() {
        return zhiiang;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCuxiaojia(String cuxiaojia) {
        this.cuxiaojia = cuxiaojia;
    }

    public String getCuxiaojia() {
        return cuxiaojia;
    }

    public void setSave_money(String save_money) {
        this.save_money = save_money;
    }

    public String getSave_money() {
        return save_money;
    }

    public void setEnd_seconds(Long end_seconds) {
        this.end_seconds = end_seconds;
    }

    public Long getEnd_seconds() {
        return end_seconds;
    }

    public void setGuide_price(String guide_price) {
        this.guide_price = guide_price;
    }

    public String getGuide_price() {
        return guide_price;
    }

    public void setTime_range(String time_range) {
        this.time_range = time_range;
    }

    public String getTime_range() {
        return time_range;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd_key(Boolean end_key) {
        this.end_key = end_key;
    }

    public Boolean getEnd_key() {
        return end_key;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getColors() {
        return colors;
    }

    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    public String getComment1() {
        return comment1;
    }

    public void setComment2(String comment2) {
        this.comment2 = comment2;
    }

    public String getComment2() {
        return comment2;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }
}
