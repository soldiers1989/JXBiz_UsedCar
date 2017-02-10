package com.etong.android.jxappusedcar.bean;

/**
 * @author : by sunyao
 * @ClassName : UC_CarDetailBean
 * @Description : (车辆详情页面中的javabean)
 * @date : 2016/10/20 - 18:17
 */

public class UC_CarDetailBean {

    public static final int SECTION = 0;    // list中的title
    public static final int ITEM_CHAR_1 = 1;       // list中的item    包含一个文字，没有图片 （车辆历史、车辆配置）
    public static final int ITEM_CHAR_2 = 2;       // list中的item    包含两个文字，没有图片 （亮点配置）
    public static final int ITEM_CHAR_3 = 3;       // list中的item    包含一个文字，包含图片 （分期方案）
    public static final int ITEM_CHAR_4 = 4;       // list中的item    只包含图片的，（车辆图片）
    public int sectionPosition;             // 悬浮标题的位置
    public int listPosition;                // item的显示位置

    public int mType;                       // 当前item的类型
    public boolean mIsShowIm = true;        // 是否显示图片 (默认为显示）
    public String mTxt_l="";                   // 当前显示的文字_左边（默认设置为左边）
    public String mTxt_r="";                   // 当前显示的文字_右边

    // 构造方法、只有一个文字的item
    public UC_CarDetailBean(int type, String txt_l) {
        this.mType = type;
        this.mTxt_l = txt_l;
    }
    // 构造方法、包含图片和文字的item
    public UC_CarDetailBean(int type, boolean isShowIm, String txt_l) {
        this.mType = type;
        this.mIsShowIm = isShowIm;
        this.mTxt_l = txt_l;
    }
    // 构造方法、包含两个文字的item
    public UC_CarDetailBean(int type, String txt_l, String txt_r) {
        this.mType = type;
        this.mTxt_l = txt_l;
        this.mTxt_r = txt_r;
    }
    // 供外部将所有参数给Javabean
    public UC_CarDetailBean(int type, String txt_l, String txt_r, boolean isShowIm) {
        this.mType = type;
        this.mIsShowIm = isShowIm;
        this.mTxt_l = txt_l;
        this.mTxt_r = txt_r;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public boolean ismIsShowIm() {
        return mIsShowIm;
    }

    public void setmIsShowIm(boolean mIsShowIm) {
        this.mIsShowIm = mIsShowIm;
    }

    public String getmTxt_l() {
        return mTxt_l;
    }

    public void setmTxt_l(String mTxt_l) {
        this.mTxt_l = mTxt_l;
    }

    public String getmTxt_r() {
        return mTxt_r;
    }

    public void setmTxt_r(String mTxt_r) {
        this.mTxt_r = mTxt_r;
    }
}
