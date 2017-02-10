package com.etong.android.jxbizusedcar.utils;

import java.io.Serializable;
import java.util.Map;

/**
 * @author : by sunyao
 * @ClassName : SerializMap
 * @Description : (可以被传递的序列号Map对象)
 * @date : 2016/11/10 - 17:41
 */

public class SerializMap implements Serializable {
    private Map<String, String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
