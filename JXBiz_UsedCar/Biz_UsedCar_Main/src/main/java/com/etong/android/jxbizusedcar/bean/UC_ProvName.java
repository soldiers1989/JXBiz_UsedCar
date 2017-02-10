package com.etong.android.jxbizusedcar.bean;

import java.util.List;

/**
 * @author : by sunyao
 * @ClassName : UC_ProvName
 * @Description : (省份列表)
 * @date : 2016/10/26 - 11:38
 */

public class UC_ProvName {

    private String prov_id;
    private String prov_name;
    private List<UC_CityName> listCity;

    public UC_ProvName() {
    }

    public UC_ProvName(String prov_id, String prov_name) {
        this.prov_id = prov_id;
        this.prov_name = prov_name;
    }

    public String getProv_id() {
        return prov_id;
    }

    public void setProv_id(String prov_id) {
        this.prov_id = prov_id;
    }

    public String getProv_name() {
        return prov_name;
    }

    public void setProv_name(String prov_name) {
        this.prov_name = prov_name;
    }

    public List<UC_CityName> getListCity() {
        return listCity;
    }

    public void setListCity(List<UC_CityName> listCity) {
        this.listCity = listCity;
    }
}