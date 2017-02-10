package com.etong.android.jxbizusedcar.bean;

/**
 * 资讯详情javabean  webview
 * Created by Administrator on 2016/8/30.
 */
public class UC_MessageDetailsBean {


    /**
     * store_id : isJxapp
     * img : http://192.168.10.152:8090/viewResourceAction/jxadmin/2016/08/29/20160829155435.jpg
     * create_time : 1471275954000
     * forward_count : 0
     * recommend : 1
     * title : test
     * type : 10000
     * content : TEST&lt;div&gt;test&lt;/div&gt;&lt;div&gt;&lt;img src="https://pic4.zhimg.com/8e3a777d116c71ccb66296a12c1f9c13_b.jpg"&gt;&lt;br&gt;&lt;/div&gt;
     * tags : TEST
     * update_time : 1472588030000
     * subTitle : teeee
     * can_book : 0
     * id : 10000004
     * view_count : 6
     * status : 1
     */

    private String store_id;
    private String img;
    private Long create_time;
    private Integer forward_count;
    private Integer recommend;
    private String title;
    private Integer type;
    private String content;
    private String tags;
    private Long update_time;
    private String subTitle;
    private Integer can_book;
    private Integer id;
    private Integer view_count;
    private Integer status;

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Integer getForward_count() {
        return forward_count;
    }

    public void setForward_count(Integer forward_count) {
        this.forward_count = forward_count;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Long update_time) {
        this.update_time = update_time;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Integer getCan_book() {
        return can_book;
    }

    public void setCan_book(Integer can_book) {
        this.can_book = can_book;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getView_count() {
        return view_count;
    }

    public void setView_count(Integer view_count) {
        this.view_count = view_count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
