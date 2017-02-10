package com.etong.android.jxbizusedcar.utils;

import com.etong.android.jxbizusedcar.bean.UC_RegionSel_ItemBean;

import java.util.Comparator;

/**
 * @author : by sunyao
 * @ClassName : UC_RegionSelct_Comparator
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/10/24 - 16:16
 */

public class UC_RegionSelct_Comparator implements Comparator<UC_RegionSel_ItemBean> {

    public int compare(UC_RegionSel_ItemBean o1, UC_RegionSel_ItemBean o2) {
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
