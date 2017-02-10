package com.etong.android.jxappfours.find_car.filtrate.javabeam;

/**
 * Created by Administrator on 2016/8/2 0002.
 */
public class Find_Car_LevelItemBeam {

    public String title;

    public String itemIconName;

    public int id;

    public Find_Car_LevelItemBeam(String title, String itemIconName) {
        this.title = title;
        this.itemIconName = itemIconName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return title;
    }
    public String getItemIconName() {
        return itemIconName;
    }
    public int getId() {
        return id;
    }
}
