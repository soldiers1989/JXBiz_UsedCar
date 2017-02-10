package com.etong.android.jxappcarassistant.gas_tation.javabean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class Fours_MyCarItemBean implements Serializable{

    private String plate_no;
    private String chassis_no;
    private String engine_no;
    private String vtitle;
    private int carsetId;
    private int vid;



    public String getPlate_no() {
        return plate_no;
    }

    public void setPlate_no(String plate_no) {
        this.plate_no = plate_no;
    }

    public String getChassis_no() {
        return chassis_no;
    }

    public void setChassis_no(String chassis_no) {
        this.chassis_no = chassis_no;
    }

    public String getEngine_no() {
        return engine_no;
    }

    public void setEngine_no(String engine_no) {
        this.engine_no = engine_no;
    }

    public String getVtitle() {
        return vtitle;
    }

    public void setVtitle(String vtitle) {
        this.vtitle = vtitle;
    }

    public int getCarsetId() {
        return carsetId;
    }

    public void setCarsetId(int carsetId) {
        this.carsetId = carsetId;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }
}
