package com.etong.android.jxappmessage.utils;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 资讯的调用接口的方法类
 * Created by Administrator on 2016/8/11.
 */
public class MessageProvider {
    private HttpPublisher mHttpPublisher;
    private static String TAG = "UserProvider";
    private static MessageProvider instance;

    public static final String USER_SUCCEED = "PT_ERROR_SUCCESS";
    public static final String USER_EXISTS = "PT_ERROR_REG_REDUPLICATED";
    public static final String USER_DUPLICATE = "PT_ERROR_RECORD_REDUPLICATED";

    private MessageProvider() {

    }

    public void initialize(HttpPublisher httpPublisher) {
        this.mHttpPublisher = httpPublisher;
    }

    public static MessageProvider getInstance() {
        if (null == instance) {
            instance = new MessageProvider();
        }
        return instance;
    }

    /*
     * @Title
     * @Description  : 推荐资讯列表
     * @params
     *     @param
     * @return
     *     void    返回类型
     * @throws
     * */
/*
    public void MessageType(String recommend,Boolean isJxapp) {
        Map map = new HashMap<>();

        map.put("isJxapp",isJxapp);


        if(null!=recommend){
            map.put("recommend",recommend);
        }

        HttpMethod method = new HttpMethod(FrameHttpUri.Message_List+"?recommend="+recommend+"&isJxapp="+isJxapp, null);
//        HttpMethod method = new HttpMethod(FrameHttpUri.Message_List, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.MESSAGE_LIST);
    }*/

    /*
     * @Title
     * @Description  : 推荐资讯列表
     * @params
     *     @param
     * @return
     *     void    返回类型
     * @throws
     * */

    public void MessageType(String start,String limit,String recommend,int isJxapp,int isPullDown) {
        Map map = new HashMap<>();

        map.put("isJxapp",isJxapp+"");

        if(null!=recommend){
            map.put("recommend",recommend);
        }
        if(null!=start){
            map.put("start",start);
        }
        if(null!=limit){
            map.put("limit",limit);
        }
        map.put("isPullDown", isPullDown+"");
        HttpMethod method = new HttpMethod(FrameHttpUri.Message_List, map);
//        HttpMethod method = new HttpMethod(FrameHttpUri.Message_List+"?start="+start+"&limit="+limit+"&recommend="+recommend+"&isJxapp="+isJxapp, null);
//        HttpMethod method = new HttpMethod(FrameHttpUri.Message_List, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.MESSAGE_LIST);
    }

     /*
     * @Title
     * @Description  : 查询资讯列表接口
     * @params
     *     @param
     * @return
     *     void    返回类型
     * @throws
     * */

    public void MessageTypeList(String type,String start,String limit,String recommend,String key,int isJxapp,int isPullDown) {
        Map map = new HashMap<>();

        map.put("isJxapp",isJxapp+"");

        if(null!=type){
            map.put("type",type);
        }
        if(null!=start){
            map.put("start",start);
        }
        if(null!=limit){
            map.put("limit",limit);
        }
        if(null!=recommend){
            map.put("recommend",recommend);
        }
        if(null!=key){
            map.put("key",key);
        }
        map.put("isPullDown", isPullDown+"");
        HttpMethod method = new HttpMethod(FrameHttpUri.Message_List, map);
//        HttpMethod method = new HttpMethod(FrameHttpUri.Message_List, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.MESSAGE_TYPE_LIST);
    }

    /*
     * @Title
     * @Description  : 搜索资讯
     * @params
     *     @param
     * @return
     *     void    返回类型
     * @throws
     * */

    public void MessageSearchList(String key,int isJxapp) {
        Map map = new HashMap<>();

        map.put("isJxapp",isJxapp);


        if(null!=key){
            map.put("key",key);
        }

        String carName="";
        try {
            carName= URLEncoder.encode(key,"utf-8");
            if(carName.contains("+")){
                carName.replace("+","20%");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpMethod method = new HttpMethod(FrameHttpUri.Message_List+"?key="+carName+"&isJxapp="+isJxapp, map);
//        HttpMethod method = new HttpMethod(FrameHttpUri.Message_List, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.MESSAGE_SEARCH_LIST);
    }



    /**
     * @Title
     * @Description  : 查询资讯详情接口
     * @params
     *     @param
     * @return
     *     void    返回类型
     * @throws
     */
    public void MessageDetails(String id) {
        Map map = new HashMap<>();
//        map.put("id",id);

        HttpMethod method = new HttpMethod(FrameHttpUri.Message_Details+id+".do", map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.MESSAGE_DETAILS);
    }



}
