package com.etong.android.jxappusedcar.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @desc 价格javabean
 * @createtime 2016/10/26 - 19:50
 * @Created by xiaoxue.
 */

public class UC_PriceBean implements Serializable {
    private Map<String,List<String>> price;

    public UC_PriceBean(Map<String,List<String>> price) {
        this.price = price;

    }


    public Map<String, List<String>> getPrice() {
        return price;
    }

    public void setPrice(Map<String, List<String>> price) {
        this.price = price;
    }
}
