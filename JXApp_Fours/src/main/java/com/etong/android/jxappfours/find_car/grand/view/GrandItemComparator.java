package com.etong.android.jxappfours.find_car.grand.view;


import com.etong.android.jxappfours.find_car.grand.bean.Find_car_GrandBrandItemBean;

import java.util.Comparator;

/**
 * Created by Ellison.Sun on 2016/8/17.
 */
public class GrandItemComparator implements Comparator<Find_car_GrandBrandItemBean> {

    public int compare(Find_car_GrandBrandItemBean o1, Find_car_GrandBrandItemBean o2) {
        if (o1.getLetter().equals("@")
                || o2.getLetter().equals("#")) {
            return -1;
        } else if (o1.getLetter().equals("#")
                || o2.getLetter().equals("@")) {
            return 1;
        } else {
            return o1.getLetter().compareTo(o2.getLetter());
        }
    }

}