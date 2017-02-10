package com.etong.android.jxappusedcar.bean;

import java.util.List;

/**
 * @author : by sunyao
 * @ClassName : UC_CarDetail_LightConfig
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/10/28 - 18:17
 */

public class UC_CarDetail_LightConfig {

    /**
     * ldList : [{"key":"db_inducedwiper","value":"感应雨刷"},{"key":"db_elecdormer","value":"电动天窗"},{"key":"db_carphone","value":"蓝牙/车载电话"},{"key":"db_engine_secure","value":"发动机电子防盗"},{"key":"db_insulatglass","value":"防紫外线/隔热玻璃"},{"key":"db_lampadjust","value":"大灯高度可调"},{"key":"db_speedcruise","value":"定速巡航"},{"key":"db_abs","value":"ABS防抱死"},{"key":"db_tyremonitor","value":"胎压监测装置"}]
     * ldListCount : 9
     */

    private int ldListCount;
    /**
     * key : db_inducedwiper
     * value : 感应雨刷
     */

    private List<LdListBean> ldList;

    public int getLdListCount() {
        return ldListCount;
    }

    public void setLdListCount(int ldListCount) {
        this.ldListCount = ldListCount;
    }

    public List<LdListBean> getLdList() {
        return ldList;
    }

    public void setLdList(List<LdListBean> ldList) {
        this.ldList = ldList;
    }

    public static class LdListBean {
        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
