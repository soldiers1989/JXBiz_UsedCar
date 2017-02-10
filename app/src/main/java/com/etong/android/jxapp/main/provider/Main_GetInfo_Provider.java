package com.etong.android.jxapp.main.provider;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.utils.L;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车型对比的调用接口的方法类
 * Created by Administrator on 2016/8/11.
 */
public class Main_GetInfo_Provider {
    private HttpPublisher mHttpPublisher;
    private static String TAG = "UserProvider";
    private static Main_GetInfo_Provider instance;

    public static final String USER_SUCCEED = "PT_ERROR_SUCCESS";
    public static final String USER_EXISTS = "PT_ERROR_REG_REDUPLICATED";
    public static final String USER_DUPLICATE = "PT_ERROR_RECORD_REDUPLICATED";

    private Main_GetInfo_Provider() {

    }

    public void initialize(HttpPublisher httpPublisher) {
        this.mHttpPublisher = httpPublisher;
    }

    public static Main_GetInfo_Provider getInstance() {
        if (null == instance) {
            instance = new Main_GetInfo_Provider();
        }
        return instance;
    }

    /**
     * 获取到App的最新版本信息
     */
   /* public void getAppInfo() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("shortcut", "JXAPP_Android");
        map.put("_api_key", "cd5e4e910a516ec2d5ebcc845c792c52");
        HttpMethod method = new HttpMethod(FrameHttpUri.GetAppUpdateInfo, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.GET_APP_UPDATE_INFO);
    }*/

    /**
     *  获取车型详情页参数配置
     */
    public void getVechileDetails(Integer id) {
        Map<String, String> map = new HashMap<String, String>();
//        map.put("Id",id+"");
        HttpMethod method = new HttpMethod(FrameHttpUri.GetVechileTypeDetail +id+".do", map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.GET_VECHILE_TYPE_DETAIL);
    }

    public void getCompareCarConfig(List<Integer> idList) {
        // 判断传来的idList是否合法
        if (idList == null || idList.size()<=0) {
            return;
        }
        StringBuilder sb = new StringBuilder();

        // 将所有的id添加到List中
        for (int i=0; i<idList.size(); i++) {
            sb.append(idList.get(i)+",");
        }
        L.d("所有的id为：", sb.toString());
        sb.deleteCharAt(sb.length()-1);
        L.d("删除之后的所有的id为：", sb.toString());

        HttpMethod method = new HttpMethod(FrameHttpUri.GetCompareInfo + sb.toString() + ".do", new HashMap());
        mHttpPublisher.sendRequest(method, FrameHttpTag.GET_COMPARE_INFO);
    }
}
