package com.etong.android.jxappfind.javabean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 限时购  促销车对象
 * Created by Administrator on 2016/8/2.
 */
public class FindVechileInfoBean implements Serializable{
    /**
     * update_time : 2016-08-24
     * update_user : 10000001
     * create_time : 2016-08-24
     * name : 促销车
     * end_time : 2016-08-31
     * description : 促销车
     * begin_time : 2016-08-25
     * id : 3
     * create_user : 10000001
     * type : 2
     * items : [{"original_price":255.5,"update_time":1472739320000,"update_user":1,"picturesList":[{"size":11,"create_time":1472739679000,"name":"test","id":3,"type":1,"url":"http://192.168.10.152:8090/viewResourceAction/jxadmin/2016/08/22/20160822154621.jpg","status":1}],"create_time":1472739318000,"price":200.5,"name":"test2","description":"感觉好慢啊2","recommend":1,"id":"2","create_user":1,"status":1}]
     * status : 2
     */

    private String update_time;
    private Integer update_user;
    private String create_time;
    private String name;
    private Long end_time;
    private String description;
    private Long begin_time;
    private Integer id;
    private Integer create_user;
    private Integer type;
    private Integer status;
    /*
     * original_price : 255.5
     * update_time : 1472739320000
     * update_user : 1
     * picturesList : [{"size":11,"create_time":1472739679000,"name":"test","id":3,"type":1,"url":"http://192.168.10.152:8090/viewResourceAction/jxadmin/2016/08/22/20160822154621.jpg","status":1}]
     * create_time : 1472739318000
     * price : 200.5
     * name : test2
     * description : 感觉好慢啊2
     * recommend : 1
     * id : 2
     * create_user : 1
     * status : 1
     * */


    private List<ItemsBean> items;

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public Integer getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(Integer update_user) {
        this.update_user = update_user;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Long end_time) {
        this.end_time = end_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Long begin_time) {
        this.begin_time = begin_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreate_user() {
        return create_user;
    }

    public void setCreate_user(Integer create_user) {
        this.create_user = create_user;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean implements Serializable{
        private Double original_price;
        private Long update_time;
        private Integer update_user;
        private Long create_time;
        private Double price;
        private String name;
        private String description;
        private Integer recommend;
        private Integer id;
        private Integer create_user;
        private Integer status;
        private Integer carset_id;
        private Integer vid;
        public Integer getCarset_id() {
            return carset_id;
        }

        public Integer getVid() {
            return vid;
        }

        public void setVid(Integer vid) {
            this.vid = vid;
        }

        public void setCarset_id(Integer carset_id) {
            this.carset_id = carset_id;
        }

        /*
         * size : 11
         * create_time : 1472739679000
         * name : test
         * id : 3
         * type : 1
         * url : http://192.168.10.152:8090/viewResourceAction/jxadmin/2016/08/22/20160822154621.jpg
         * status : 1
         * */


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

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
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

        public static class PicturesListBean implements Serializable{
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
    }




  /*  private String title;//标题
    private Double  costprice;//原价
    private Double  price;//现价
    private String image;//图片
    private Long end_seconds;//倒计时时间
    private int carset_id=0;
    private int huodong_id =0;//活动id

    public int getHuodong_id() {
        return huodong_id;
    }

    public void setHuodong_id(int huodong_id) {
        this.huodong_id = huodong_id;
    }

    public int getCarset_id() {
        return carset_id;
    }

    public void setCarset_id(int carset_id) {
        this.carset_id = carset_id;
    }



    public Long getEnd_seconds() {
        return end_seconds;
    }

    public void setEnd_seconds(Long end_seconds) {
        this.end_seconds = end_seconds;
    }



    public Double getCostprice() {
        return costprice;
    }

    public void setCostprice(Double costprice) {
        this.costprice = costprice;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }*/




}
