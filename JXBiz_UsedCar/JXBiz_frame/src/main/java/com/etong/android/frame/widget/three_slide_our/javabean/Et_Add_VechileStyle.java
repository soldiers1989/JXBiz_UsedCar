package com.etong.android.frame.widget.three_slide_our.javabean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 增加车款Javabean
 * Created by Administrator on 2016/8/15.
 */
public class Et_Add_VechileStyle implements Parcelable{
    private String title;
    private Integer id;
    private boolean isChecked=false;//是否选中

    public Et_Add_VechileStyle() {

    }

    protected Et_Add_VechileStyle(Parcel in) {
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

    public static final Creator<Et_Add_VechileStyle> CREATOR = new Creator<Et_Add_VechileStyle>() {
        @Override
        public Et_Add_VechileStyle createFromParcel(Parcel in) {
            return new Et_Add_VechileStyle(in);
        }

        @Override
        public Et_Add_VechileStyle[] newArray(int size) {
            return new Et_Add_VechileStyle[size];
        }
    };
}
