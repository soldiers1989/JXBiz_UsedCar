package com.etong.android.frame.widget.three_slide_300.javabean;

import java.io.Serializable;

/**
 * 选择车型
 * Created by Administrator on 2016/8/15.
 */
public class Models_Contrast_VechileType implements Serializable {
    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title
    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // item的显示位置

    public int type;
    public String charTv;

    private String max_reg_year;                   //"2016",
    private String liter;                         // "1.4T",
    private String update_time;                   // "2016-10-23 22:20:01",
    private String gear_type;                     // "自动",
    private String model_id;                      // "30452",
    private String model_year;                    // "2016",
    private String discharge_standard;            // "国5",
    private String min_reg_year;                  // "2016",
    private String short_name;                    // "2016款 Sportback 35 TFSI 进取型",
    private String model_name;                    // "2016款 奥迪A3 Sportback 35 TFSI 进取型",
    private String seat_number;                   // "5",
    private String model_price;                   // "18.49"
    
    
    private boolean isChecked=false;//是否选中

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Models_Contrast_VechileType() {

    }
    public Models_Contrast_VechileType(int type, String outputVol) {
        this.type = type;
        this.charTv = outputVol;
    }

    public String getMax_reg_year() {
        return max_reg_year;
    }

    public void setMax_reg_year(String max_reg_year) {
        this.max_reg_year = max_reg_year;
    }

    public String getLiter() {
        return liter;
    }

    public void setLiter(String liter) {
        this.liter = liter;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getGear_type() {
        return gear_type;
    }

    public void setGear_type(String gear_type) {
        this.gear_type = gear_type;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getModel_year() {
        return model_year;
    }

    public void setModel_year(String model_year) {
        this.model_year = model_year;
    }

    public String getDischarge_standard() {
        return discharge_standard;
    }

    public void setDischarge_standard(String discharge_standard) {
        this.discharge_standard = discharge_standard;
    }

    public String getMin_reg_year() {
        return min_reg_year;
    }

    public void setMin_reg_year(String min_reg_year) {
        this.min_reg_year = min_reg_year;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }

    public String getModel_price() {
        return model_price;
    }

    public void setModel_price(String model_price) {
        this.model_price = model_price;
    }
}

