package com.etong.android.jxappfours.models_contrast.javabean;

import java.util.List;

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

    private String title;       // 不认识。。。。   XR-V
    private String pTitle;      //车系名称
    private String fullName;  //车系详细名称
    private Integer id;         //车系id
    private boolean isRoot;

    public Models_Contrast_VechileSeries() {

    }
    public Models_Contrast_VechileSeries(int type, String strTitle) {
        this.type = type;
        this.charTv = strTitle;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
