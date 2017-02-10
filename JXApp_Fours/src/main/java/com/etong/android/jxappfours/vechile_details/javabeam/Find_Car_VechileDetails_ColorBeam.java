package com.etong.android.jxappfours.vechile_details.javabeam;

import java.io.Serializable;

/**
 *
 * Created by Administrator on 2016/8/11 0011.
 */
public class Find_Car_VechileDetails_ColorBeam implements Serializable {

    private int id;
    private String title;
    private String image;
    private boolean isRoot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

}
