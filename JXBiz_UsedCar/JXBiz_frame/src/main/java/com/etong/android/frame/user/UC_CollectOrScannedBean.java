package com.etong.android.frame.user;

import java.util.List;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/11/7 0007--18:43
 * @Created by wukefan.
 */
public class UC_CollectOrScannedBean {


    private List<String> carList;
    private boolean isChanged;

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public List<String> getCarList() {
        return carList;
    }

    public void setCarList(List<String> carList) {
        this.carList = carList;
    }

    //    public List<CarListBean> getCarList() {
//        return carList;
//    }
//
//    public void setCarList(List<CarListBean> carList) {
//        this.carList = carList;
//    }
//
//
//    public static class CarListBean {
//        private int f_collectid;
//        private String f_vehicleid;
//
//        public int getF_collectid() {
//            return f_collectid;
//        }
//
//        public void setF_collectid(int f_collectid) {
//            this.f_collectid = f_collectid;
//        }
//
//        public String getF_vehicleid() {
//            return f_vehicleid;
//        }
//
//        public void setF_vehicleid(String f_vehicleid) {
//            this.f_vehicleid = f_vehicleid;
//        }
//    }
}
