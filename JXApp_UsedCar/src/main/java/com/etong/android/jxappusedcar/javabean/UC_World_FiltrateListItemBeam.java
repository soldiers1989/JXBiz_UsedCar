package com.etong.android.jxappusedcar.javabean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class UC_World_FiltrateListItemBeam implements Serializable{


    /**
     * para : gearmode
     * id : gearmode
     * type : 1
     * map : [{"value":"手自一体","key":"0"},{"value":"自动挡","key":"1"},{"value":"手动档","key":"2"}]
     * minValue : 0
     * unit : 单位:万
     * maxName : pricemax
     * maxValue : 100
     * minName : pricemin
     * remark : 万以下
     * intervalValue : 5
     * paraName : 变速箱
     */

    private String para;
    private String id;
    private String type;
    private String minValue;
    private String unit;
    private String maxName;
    private String maxValue;
    private String minName;
    private String remark;
    private String intervalValue;
    private String paraName;
    private List<MapBean> map;

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMaxName() {
        return maxName;
    }

    public void setMaxName(String maxName) {
        this.maxName = maxName;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getMinName() {
        return minName;
    }

    public void setMinName(String minName) {
        this.minName = minName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIntervalValue() {
        return intervalValue;
    }

    public void setIntervalValue(String intervalValue) {
        this.intervalValue = intervalValue;
    }

    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    public List<MapBean> getMap() {
        return map;
    }

    public void setMap(List<MapBean> map) {
        this.map = map;
    }

    public static class MapBean implements Serializable{
        /**
         * value : 手自一体
         * key : 0
         */

        private String value;
        private String key;
        private boolean isSelect;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }
}
