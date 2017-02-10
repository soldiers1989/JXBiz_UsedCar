package com.etong.android.jxappfours.vechile_details.javabeam;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class Find_Car_VechileDetailsImageBean implements Serializable{


    /**
     * photoList : [{"vid":621296,"thumbnail":"http://113.247.237.98:10002/auto5.1/userfiles/car/2014021412/20140214120810332.jpg","carsetId":4546,"name":"2014款 1.2L创业型LJ469Q-AE2","url":"http://113.247.237.98:10002/auto5.1/userfiles/car/2014021412/20140214120810332.jpg"}]
     * type : 车身外观
     */

    private String type;
    /**
     * vid : 621296
     * thumbnail : http://113.247.237.98:10002/auto5.1/userfiles/car/2014021412/20140214120810332.jpg
     * carsetId : 4546
     * name : 2014款 1.2L创业型LJ469Q-AE2
     * url : http://113.247.237.98:10002/auto5.1/userfiles/car/2014021412/20140214120810332.jpg
     */

    private List<PhotoListBean> photoList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PhotoListBean> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<PhotoListBean> photoList) {
        this.photoList = photoList;
    }

    public static class PhotoListBean implements Serializable{
        private Integer vid;
        private String thumbnail;
        private Integer carsetId;
        private String name;
        private String url;

        public Integer getVid() {
            return vid;
        }

        public void setVid(Integer vid) {
            this.vid = vid;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public Integer getCarsetId() {
            return carsetId;
        }

        public void setCarsetId(Integer carsetId) {
            this.carsetId = carsetId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
