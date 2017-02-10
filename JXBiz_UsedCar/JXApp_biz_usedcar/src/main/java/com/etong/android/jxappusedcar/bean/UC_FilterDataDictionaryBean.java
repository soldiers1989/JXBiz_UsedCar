package com.etong.android.jxappusedcar.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/10/21 0021--19:52
 * @Created by wukefan.
 */
public class UC_FilterDataDictionaryBean implements Serializable{


    /**
     * paraName : 颜色
     * type : 1
     * map : [{"key":"0","value":"靛蓝","rgb":"#0000CD"},{"key":"1","value":"酒红色","rgb":"#B22222"},{"key":"2","value":"蓝色","rgb":"#0000FF"},{"key":"3","value":"银色","rgb":"#E6E8FA"},{"key":"4","value":"白色","rgb":"#FFFFFF"},{"key":"5","value":"黑色","rgb":"#000000"},{"key":"6","value":"紫色","rgb":"#A020F0"},{"key":"7","value":"绿色","rgb":"#00FF00"},{"key":"8","value":"红色","rgb":"#FF0000"}]
     * para : f_color
     * id : color
     * minName : f_registercodemin
     * minValue : 0
     * maxValue : 8
     * unit : 单位:年
     * intervalValue : 2
     * remark : 年以下
     * maxName : f_registercodemax
     */

    private String paraName;
    private String type;
    private String para;
    private String id;
    private String minName;
    private String minValue;
    private String maxValue;
    private String unit;
    private String intervalValue;
    private String remark;
    private String maxName;

    private boolean rangeIsSelect;
    private String selectMax;
    private String selectMin;

    /**
     * key : 0
     * value : 靛蓝
     * rgb : #0000CD
     */

    private List<MapBean> map;

    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getMinName() {
        return minName;
    }

    public void setMinName(String minName) {
        this.minName = minName;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getIntervalValue() {
        return intervalValue;
    }

    public void setIntervalValue(String intervalValue) {
        this.intervalValue = intervalValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMaxName() {
        return maxName;
    }

    public void setMaxName(String maxName) {
        this.maxName = maxName;
    }

    public boolean isRangeIsSelect() {
        return rangeIsSelect;
    }

    public void setRangeIsSelect(boolean rangeIsSelect) {
        this.rangeIsSelect = rangeIsSelect;
    }

    public String getSelectMax() {
        return selectMax;
    }

    public void setSelectMax(String selectMax) {
        this.selectMax = selectMax;
    }

    public String getSelectMin() {
        return selectMin;
    }

    public void setSelectMin(String selectMin) {
        this.selectMin = selectMin;
    }

    public List<MapBean> getMap() {
        return map;
    }

    public void setMap(List<MapBean> map) {
        this.map = map;
    }

    public static class MapBean implements Serializable{
        private String key;
        private String value;
        private String rgb;
        private boolean isSelect;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getRgb() {
            return rgb;
        }

        public void setRgb(String rgb) {
            this.rgb = rgb;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }
}
