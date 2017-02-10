package com.etong.android.frame.user;

import java.util.List;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/12/13 0013--16:47
 * @Created by wukefan.
 */
public class UsedAndNewCollectCar {

    private List<String> carList;
    private boolean isChanged;

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public List<String> getCarList() {
        return carList;
    }

    public void setCarList(List<String> carList) {
        this.carList = carList;
    }

}
