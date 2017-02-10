package com.etong.android.jxappfind.javabean;

import java.io.Serializable;
import java.util.List;

/**
 * 猜你喜欢对象
 * Created by Administrator on 2016/8/2.
 */
public class FindGuessBean implements Serializable {
    /**
     * original_price : 200
     * update_time : 1472135298000
     * update_user : 1
     * picturesList : [{"size":100,"create_time":1472136312000,"name":"test","id":1,"type":1,"url":"http://192.168.10.152:8090/viewResourceAction/jxadmin/2016/08/22/20160822154621.jpg","status":1}]
     * create_time : 1472135295000
     * price : 100
     * name : test
     * description : 感觉好慢啊
     * recommend : 1
     * id : 1
     * create_user : 1
     * status : 1
     */

    private Double original_price;
    private Long update_time;
    private Integer update_user;
    private Long create_time;
    private Double price;
    private String name;
    private String description;
    private Integer recommend;
    private String id;
    private Integer create_user;
    private Integer status;
    private Integer carset_id;
    private Integer vid;

    public Integer getCarset_id() {
        return carset_id;
    }

    public void setCarset_id(Integer carset_id) {
        this.carset_id = carset_id;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    /**
     * size : 100
     * create_time : 1472136312000
     * name : test
     * id : 1
     * type : 1
     * url : http://192.168.10.152:8090/viewResourceAction/jxadmin/2016/08/22/20160822154621.jpg
     * status : 1
     */

    private List<PicturesListBean> picturesList;

    public Double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(Double original_price) {
        this.original_price = original_price;
    }

    public Long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Long update_time) {
        this.update_time = update_time;
    }

    public Integer getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(Integer update_user) {
        this.update_user = update_user;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCreate_user() {
        return create_user;
    }

    public void setCreate_user(Integer create_user) {
        this.create_user = create_user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<PicturesListBean> getPicturesList() {
        return picturesList;
    }

    public void setPicturesList(List<PicturesListBean> picturesList) {
        this.picturesList = picturesList;
    }

    public static class PicturesListBean implements Serializable {
        private Integer size;
        private Long create_time;
        private String name;
        private Integer id;
        private Integer type;
        private String url;
        private Integer status;

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public Long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Long create_time) {
            this.create_time = create_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }
  /*  private String title;//标题
    private String imageUrl;//图片url
    private Double  costprice;//原价
    private Double  price;//现价

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCostprice() {
        return costprice;
    }

    public void setCostprice(Double costprice) {
        this.costprice = costprice;
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
*/



}
