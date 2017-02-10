package com.etong.android.frame.widget.three_slide_300.javabean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 增加车款Javabean
 * Created by Administrator on 2016/8/15.
 */
public class Models_Contrast_Add_VechileStyle implements Parcelable{
    private String title;
    private Integer id;
    private boolean isChecked=false;//是否选中

    public Models_Contrast_Add_VechileStyle() {

    }

    protected Models_Contrast_Add_VechileStyle(Parcel in) {
        title = in.readString();
        id = in.readInt();
        isChecked = in.readByte() != 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(id);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    public static final Creator<Models_Contrast_Add_VechileStyle> CREATOR = new Creator<Models_Contrast_Add_VechileStyle>() {
        @Override
        public Models_Contrast_Add_VechileStyle createFromParcel(Parcel in) {
            return new Models_Contrast_Add_VechileStyle(in);
        }

        @Override
        public Models_Contrast_Add_VechileStyle[] newArray(int size) {
            return new Models_Contrast_Add_VechileStyle[size];
        }
    };
}
