package com.etong.android.jxbizusedcar.bean;

/**
 * 图片轮播对象
 * Created by Administrator on 2016/8/3.
 */
public class UC_MessageImageCarouselBean {
    private String title;//标题
    private String image;//图片uri
    private String url;//地址
    private String key;

    public void setTitle(String title){
        this.title=title;
    }

    public String getTitle(){
        return title;
    }

    public void setImage(String image){
        this.image=image;
    }

    public String getImage(){
        return image;
    }

    public void setUrl(String url){
        this.url=url;
    }

    public String getUrl(){
        return url;
    }

    public void setKey(String key){
        this.key=key;
    }

    public String getKey(){
        return key;
    }
}
