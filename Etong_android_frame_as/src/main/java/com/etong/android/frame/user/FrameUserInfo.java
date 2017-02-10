package com.etong.android.frame.user;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;
import java.util.List;

/***
 * 用户信息
 *
 * @author Administrator
 */
public class FrameUserInfo implements Serializable {

    String userPhone;                        //用户手机号码
    String passwd;                           //密码
    String token;                            //令牌
    String userIdCard;                       //身份证号码
    String userSex;                          //性别
    String userMarry;                        //婚否
    String userHeadImg;                      //用户头像（暂时没用）
    Integer platformID;                          //平台id
    List<Frame_MyCarItemBean> myCars;        //我的爱车列表
    String avatar;                           //头像

    JSONArray authority;                     //数据分析数据
    String userId;                           //用户id
    String roleID;                           //
    String roleName;                         //
    int status;                              //
    String userName;                         //用户姓名

    String fcustid;                          //业务编号
    String f_name;                           //绑定金融账号的姓名
    String f_phone;                          //绑定金融账号的手机号
    String f_cardId;                         //绑定金融账号的身份证号

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_phone() {
        return f_phone;
    }

    public void setF_phone(String f_phone) {
        this.f_phone = f_phone;
    }

    public String getF_cardId() {
        return f_cardId;
    }

    public void setF_cardId(String f_cardId) {
        this.f_cardId = f_cardId;
    }

    public String getFcustid() {
        return fcustid;
    }

    public void setFcustid(String fcustid) {
        this.fcustid = fcustid;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Frame_MyCarItemBean> getMyCars() {
        return myCars;
    }

    public void setMyCars(List<Frame_MyCarItemBean> myCars) {
        this.myCars = myCars;
    }

    public Integer getPlatformID() {
        if (null == platformID) {
            platformID = 0;
        }
        return platformID;
    }

    public void setPlatformID(int platformID) {
        this.platformID = platformID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public void setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserMarry() {
        return userMarry;
    }

    public void setUserMarry(String userMarry) {
        this.userMarry = userMarry;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
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

    public static class Frame_MyCarItemBean implements Serializable {

        private String id;                  //爱车id
        private String create_time;         //创建时间
        private String platformID;          //平台id
        private String plate_no;            //车牌号
        private String chassis_no;          //车架号
        private String engine_no;           //发动机号
        private String vtitle;              //爱车名称
        private int carsetId;               //车系id
        private int vid;                    //车型id


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPlatformID() {
            return platformID;
        }

        public void setPlatformID(String platformID) {
            this.platformID = platformID;
        }

        public String getPlate_no() {
            return plate_no;
        }

        public void setPlate_no(String plate_no) {
            this.plate_no = plate_no;
        }

        public String getChassis_no() {
            return chassis_no;
        }

        public void setChassis_no(String chassis_no) {
            this.chassis_no = chassis_no;
        }

        public String getEngine_no() {
            return engine_no;
        }

        public void setEngine_no(String engine_no) {
            this.engine_no = engine_no;
        }

        public String getVtitle() {
            return vtitle;
        }

        public void setVtitle(String vtitle) {
            this.vtitle = vtitle;
        }

        public int getCarsetId() {
            return carsetId;
        }

        public void setCarsetId(int carsetId) {
            this.carsetId = carsetId;
        }

        public int getVid() {
            return vid;
        }

        public void setVid(int vid) {
            this.vid = vid;
        }
    }

}
