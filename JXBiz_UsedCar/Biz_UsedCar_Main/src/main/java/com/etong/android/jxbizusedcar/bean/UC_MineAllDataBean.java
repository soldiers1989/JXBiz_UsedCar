package com.etong.android.jxbizusedcar.bean;

import java.util.List;

/**
 * @desc 我的界面所有数据
 * @createtime 2016/10/21 0021--15:03
 * @Created by wukefan.
 */
public class UC_MineAllDataBean {


    /**
     * f_userimage :
     * carlist : [{"f_cartitle":"宝马\n\t\tX5 2015款 xDrive28i","f_registerdate":"2016年08月","f_dvid":"00000173","image":"http://113.247.237.98:10002/data///20161020/1a266001-149d-4122-9af3-5d0acf190430.jpg","f_mileage":210,"f_price":60},{"f_cartitle":"阿斯顿·马丁\n\t\tDB9 2014款 6.0L Carbon Black Coupe","f_registerdate":"2016年08月","f_dvid":"00000223","image":"/resource/images/car-img-loading.png","f_mileage":1233,"f_price":20},{"f_cartitle":"阿斯顿·马丁\n\t\tV12 Vantage 2014款 6.0L S","f_registerdate":"2016年08月","f_dvid":"00000226","image":"/resource/images/car-img-loading.png","f_mileage":11112,"f_price":12},{"f_cartitle":"别克\n\t\t君威 2015款 1.6T 时尚技术型","f_registerdate":"2016年08月","f_dvid":"00000222","image":"/resource/images/car-img-loading.png","f_mileage":1111,"f_price":11},{"f_cartitle":"宝马\n\t\tX6 M 2015款 X6 M","f_registerdate":"2016年08月","f_dvid":"00000224","image":"http://113.247.237.98:10002/data///20160815/870f11ef-7c22-4255-825d-5d81dfc95063.jpg","f_mileage":4,"f_price":10},{"f_cartitle":"比亚迪\n\t\tS8 2009款 2.0 手动尊贵型","f_registerdate":"2016年08月","f_dvid":"00000186","image":"http://113.247.237.98:10002/data///20161019/3f477303-5860-4b97-b3d0-00caafa7651c.jpg","f_mileage":50,"f_price":15},{"f_cartitle":"宝马\n\t\tX6 M 2013款 X6 M","f_registerdate":"2016年08月","f_dvid":"00000228","image":"/resource/images/car-img-loading.png","f_mileage":33222,"f_price":233},{"f_cartitle":"本田\n\t\t飞度 2014款 1.5L EXLI CVT领先型","f_registerdate":"2016年08月","f_dvid":"00000211","image":"http://113.247.237.98:10002/data///20161021/97058710-0676-4bc9-a1b7-4a1b8195f6a8.jpg","f_mileage":86525,"f_price":12},{"f_cartitle":"雪佛兰\n\t\t科鲁兹 2015款 1.5L 自动时尚版","f_registerdate":"2015年08月","f_dvid":"00000175","image":"/resource/images/car-img-loading.png","f_mileage":20000,"f_price":15}]
     * f_historycount : 10
     * f_collectcount : 1
     */

    private String f_userimage;
    private int f_historycount;
    private int f_collectcount;
    /**
     * f_cartitle : 宝马
     X5 2015款 xDrive28i
     * f_registerdate : 2016年08月
     * f_dvid : 00000173
     * image : http://113.247.237.98:10002/data///20161020/1a266001-149d-4122-9af3-5d0acf190430.jpg
     * f_mileage : 210
     * f_price : 60
     */

    private List<CarlistBean> carlist;

    public String getF_userimage() {
        return f_userimage;
    }

    public void setF_userimage(String f_userimage) {
        this.f_userimage = f_userimage;
    }

    public int getF_historycount() {
        return f_historycount;
    }

    public void setF_historycount(int f_historycount) {
        this.f_historycount = f_historycount;
    }

    public int getF_collectcount() {
        return f_collectcount;
    }

    public void setF_collectcount(int f_collectcount) {
        this.f_collectcount = f_collectcount;
    }

    public List<CarlistBean> getCarlist() {
        return carlist;
    }

    public void setCarlist(List<CarlistBean> carlist) {
        this.carlist = carlist;
    }

    public static class CarlistBean {
        private String f_cartitle;
        private String f_registerdate;
        private String f_dvid;
        private String image;
        private double f_mileage;
        private double f_price;

        public String getF_cartitle() {
            return f_cartitle;
        }

        public void setF_cartitle(String f_cartitle) {
            this.f_cartitle = f_cartitle;
        }

        public String getF_registerdate() {
            return f_registerdate;
        }

        public void setF_registerdate(String f_registerdate) {
            this.f_registerdate = f_registerdate;
        }

        public String getF_dvid() {
            return f_dvid;
        }

        public void setF_dvid(String f_dvid) {
            this.f_dvid = f_dvid;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public double getF_mileage() {
            return f_mileage;
        }

        public void setF_mileage(double f_mileage) {
            this.f_mileage = f_mileage;
        }

        public double getF_price() {
            return f_price;
        }

        public void setF_price(double f_price) {
            this.f_price = f_price;
        }
    }
}
