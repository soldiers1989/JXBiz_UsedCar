package com.etong.android.jxappmore.user;

import com.alibaba.fastjson.JSONArray;

/**
 * Created by Ellison.Sun on 2016/8/8.
 */
public class MoreUserInfo {
    String userId;
    String phone;
    String passwd;
    String token;
    JSONArray authority;



    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswd() {
        return passwd;
    }
    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public JSONArray getAuthority() {
        return authority;
    }
    public void setAuthority(JSONArray authority) {
        this.authority = authority;
    }
}