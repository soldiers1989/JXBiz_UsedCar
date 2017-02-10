package com.etong.android.jxappme.common;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.user.FrameEtongApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class MeProvider {

    private HttpPublisher mHttpPublisher;

    private static String TAG = "MeProvider";
    private static MeProvider instance;


    public static final String USER_SUCCEED = "PT_ERROR_SUCCESS";
    private static SharedPublisher mSharedPublisher = SharedPublisher.getInstance();

    private MeProvider() {

    }

    public void initialize(HttpPublisher httpPublisher) {
        this.mHttpPublisher = httpPublisher;
    }

    public static MeProvider getInstance() {
        if (null == instance) {
            instance = new MeProvider();
        }
        return instance;
    }


    public void queryMyCar() {

        Map<String, String> map = new HashMap<String, String>();

        String token = FrameEtongApplication.getApplication().getUserInfo().getToken();
        map.put("token", token);

        HttpMethod method = new HttpMethod(FrameHttpUri.QueryMyCar, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.QUERY_MY_CAR);
    }


    /**
     * @desc (更新用户信息)
     * @createtime 2016/12/2 0002-14:15
     * @author wukefan
     * sex 1男 2女 3不详
     * maritalStatus 1未婚 2已婚 3不详
     */
//    public void updateUserInfo(String userName,String cardID,String sex,String maritalStatus) {
    public void updateUserInfo(Map map) {

//        Map<String, String> map = new HashMap<String, String>();
        String phone = FrameEtongApplication.getApplication().getUserInfo().getUserPhone();
        int platformID = FrameEtongApplication.getApplication().getUserInfo().getPlatformID();
        map.put("phone", phone);
        map.put("platformID", platformID + "");

        HttpMethod method = new HttpMethod(FrameHttpUri.UpdateUserInfo, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.UPDATE_USER_INFO);
    }

    /**
     * @param isNewFlag 1--新车 0--二手车
     * @desc (收藏列表)
     * @createtime 2016/12/10 0010-14:50
     * @author wukefan
     */
    public void getCollectList(int pageSize, int pageCurrent, int isNewFlag, int isPullDown) {

        Map<String, String> map = new HashMap<String, String>();
        if (FrameEtongApplication.getApplication().isLogin()) {
            String userId = FrameEtongApplication.getApplication().getUserInfo().getUserId();
            map.put("userId", userId);
        }
        map.put("pageSize", pageSize + "");
        map.put("pageCurrent", pageCurrent + "");
        map.put("isNewFlag", isNewFlag + "");
        map.put("isPullDown", isPullDown + "");


        HttpMethod method = new HttpMethod(FrameHttpUri.COLLECTIONLIST, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.COLLECTIONLIST + isNewFlag);//区分tag
    }

    /**
     * @desc (收藏总数)
     * @createtime 2016/12/13 0013-12:01
     * @author wukefan
     */
    public void getCollectTotal() {

        Map<String, String> map = new HashMap<String, String>();
        if (FrameEtongApplication.getApplication().isLogin()) {
            String userId = FrameEtongApplication.getApplication().getUserInfo().getUserId();
            map.put("userId", userId);
        }

        HttpMethod method = new HttpMethod(FrameHttpUri.GetCollectTotal, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.GET_COLLECT_TOTAL);
    }


}
