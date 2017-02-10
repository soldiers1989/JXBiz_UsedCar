package com.etong.android.frame.widget.three_slide_300.javabean;

/**
 * 选择车系
 * Created by Administrator on 2016/8/15.
 */
public class Models_Contrast_VechileSeries {
    public static final int ITEM = 0;       // list中的item
    public static final int SECTION = 1;    // list中的title
    public int sectionPosition;     // 悬浮标题的位置
    public int listPosition;        // item的显示位置

    public int type;
    public String charTv;


    private String update_time;                     //更新时间  "2015-12-29 15:46:36",
    private String series_group_name;               //属于品牌  "一汽奥迪",
    private String series_name;                     //车系名称  "奥迪A3",
    private String maker_type;                      //"合资",
    private String series_id;                       //车系编号  "5"

    public Models_Contrast_VechileSeries() {

    }
    public Models_Contrast_VechileSeries(int type, String strTitle) {
        this.type = type;
        this.charTv = strTitle;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getSeries_group_name() {
        return series_group_name;
    }

    public void setSeries_group_name(String series_group_name) {
        this.series_group_name = series_group_name;
    }

    public String getSeries_name() {
        return series_name;
    }

    public void setSeries_name(String series_name) {
        this.series_name = series_name;
    }

    public String getMaker_type() {
        return maker_type;
    }

    public void setMaker_type(String maker_type) {
        this.maker_type = maker_type;
    }

    public String getSeries_id() {
        return series_id;
    }

    public void setSeries_id(String series_id) {
        this.series_id = series_id;
    }
}
