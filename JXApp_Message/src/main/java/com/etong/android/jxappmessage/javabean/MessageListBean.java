package com.etong.android.jxappmessage.javabean;

/**
 * 资讯列表对象
 * Created by Administrator on 2016/8/3.
 */
public class MessageListBean {
    private String title;//标题
    private String imageUrl;//图片url
    private String time;//时间

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
