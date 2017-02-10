package com.etong.android.jxapp.main.provider;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.user.FrameEtongApplication;

import java.util.HashMap;
import java.util.Map;


/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2016/12/6 0006--19:05
 * @Created by wukefan.
 */
public class Main_Provider {

    private HttpPublisher mHttpPublisher;

    private static String TAG = "Main_Provider";
    private static Main_Provider instance;

    public static final String USER_SUCCEED = "PT_ERROR_SUCCESS";
    private static SharedPublisher mSharedPublisher = SharedPublisher.getInstance();

    private Main_Provider() {

    }

    public void initialize(HttpPublisher httpPublisher) {
        this.mHttpPublisher = httpPublisher;
    }

    public static Main_Provider getInstance() {
        if (null == instance) {
            instance = new Main_Provider();
        }
        return instance;
    }

    /**
     * @desc (获取用户信息)
     * @createtime 2016/12/6 0006-19:07
     * @author wukefan
     */
    public void queryUserId() {

        Map<String, String> map = new HashMap<String, String>();
        if (FrameEtongApplication.getApplication().isLogin()) {
            if (null != FrameEtongApplication.getApplication().getUserInfo().getUserId()) {
                map.put("userID", FrameEtongApplication.getApplication().getUserInfo().getUserId());
            }
        }

        HttpMethod method = new HttpMethod(FrameHttpUri.QueryUserId, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.QUERY_USER_ID);
    }

}
