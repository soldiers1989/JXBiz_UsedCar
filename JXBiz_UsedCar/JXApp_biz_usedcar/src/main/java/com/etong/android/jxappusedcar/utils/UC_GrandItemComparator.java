package com.etong.android.jxappusedcar.utils;

import com.etong.android.jxappusedcar.bean.UC_GrandBrandItemBean;

import java.util.Comparator;


/**
 * Created by Ellison.Sun on 2016/8/17.
 */
public class UC_GrandItemComparator implements Comparator<UC_GrandBrandItemBean> {

    public int compare(UC_GrandBrandItemBean o1, UC_GrandBrandItemBean o2) {
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