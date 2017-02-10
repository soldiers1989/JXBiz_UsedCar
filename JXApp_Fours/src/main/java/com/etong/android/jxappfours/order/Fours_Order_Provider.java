package com.etong.android.jxappfours.order;

import android.text.TextUtils;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.user.FrameEtongApplication;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class Fours_Order_Provider {

    private HttpPublisher mHttpPublisher;

    private static String TAG = "Fours_Order_Provider";
    private static Fours_Order_Provider instance;


    public static final String USER_SUCCEED = "PT_ERROR_SUCCESS";
    private static SharedPublisher mSharedPublisher=SharedPublisher.getInstance();

    private Fours_Order_Provider() {

    }

    public void initialize(HttpPublisher httpPublisher) {
        this.mHttpPublisher = httpPublisher;
    }

    public static Fours_Order_Provider getInstance() {
        if (null == instance) {
            instance = new Fours_Order_Provider();
        }
        return instance;
    }

    //提交预约
    public void putOrder(String vid,String plateNumber,String phone,String userName,int type,String storeId) {

        Map map = new HashMap<>();
        map.put("vid", vid);
        map.put("phone", phone);
        map.put("type",type+"");

        if(null!=plateNumber&&(!TextUtils.isEmpty(plateNumber))){
            map.put("plateNumber",plateNumber);
        }
        if(null!=userName&&userName!=""&&!(TextUtils.isEmpty(userName.trim()))){
            map.put("userName",userName);
        }
        if(null!=storeId){
            map.put("storeId",storeId);
        }

        HttpMethod method = new HttpMethod(FrameHttpUri.PutOrder, map);
//        HttpMethod method = new HttpMethod(FrameHttpUri.PutOrder+"?vid="+vid+"&type="+type+"&phone="+phone+"&plateNumber="+plateNumber+"&userName="+userName+"&storeId="+storeId, null);
        mHttpPublisher.sendRequest(method, FrameHttpTag.PUT_ID_TO_ORDER);
    }


    //查询维保预约列表
    public void queryMaintenanceOrderList(String phone){
        HttpMethod method = new HttpMethod(FrameHttpUri.QueryMaintenanceOrderList+phone+".do",null);
        mHttpPublisher.sendRequest(method, FrameHttpTag.QUERY_MAINTENANCE_ORDER_LIST);
    }

    //查询维保预约详情
    public void queryMaintenanceOrderDetail(String sv_id){
        HttpMethod method = new HttpMethod(FrameHttpUri.QueryMaintenanceOrderDetail+sv_id+".do",null);
        mHttpPublisher.sendRequest(method, FrameHttpTag.QUERY_MAINTENANCE_ORDER_DETAIL);
    }


    //查询所有车等级
    public void queryAllCarLevel(){
        HttpMethod method = new HttpMethod(FrameHttpUri.QueryAllCarLevel,null);
        mHttpPublisher.sendRequest(method, FrameHttpTag.QUERY_ALL_CAR_LEVEL);
    }

    //筛选
    public void queryCarsetByCondition(String priceStart,String priceEnd,String modelsLevel,String country,String category,String summary,String outVolStar,String outVolEnd){

        Map map = new HashMap<>();
        if(priceStart!=null){
            map.put("priceStart",priceStart);
        }
        if(priceEnd!=null) {
            map.put("priceEnd", priceEnd);
        }
        if(modelsLevel!=null) {
            map.put("modelsLevel", modelsLevel);
        }
        if(country!=null) {
            map.put("country", country);
        }
        if(category!=null) {
            map.put("category", category);
        }
        if(summary!=null) {
            map.put("summary", summary);
        }
        if(outVolStar!=null) {
            map.put("outVolStar", outVolStar);
        }
        if(outVolEnd!=null) {
            map.put("outVolEnd", outVolEnd);
        }

        HttpMethod method = new HttpMethod(FrameHttpUri.QueryCarsetByCondition, map);
//        HttpMethod method = new HttpMethod(FrameHttpUri.QueryCarsetByCondition+"?modelsLevel="+modelsLevel+"&category="+category+"&priceStart="+priceStart+"&priceEnd="+priceEnd+"&country="+country+"&outVolStar="+outVolStar+"&outVolEnd="+outVolEnd+"&summary="+summary,null);
        mHttpPublisher.sendRequest(method, FrameHttpTag.QUERY_CARSET_BY_CONDITION);
    }


    //通过车系id查询车系图片
    public void queryCarsetPhoto(int id){
        HttpMethod method = new HttpMethod(FrameHttpUri.QueryCarsetPhoto+id+".do",null);
        mHttpPublisher.sendRequest(method, FrameHttpTag.QUERY_CARSET_PHOTO);
    }

    //通过车型id查询车系图片
    public void queryCarPhoto(int id){
        HttpMethod method = new HttpMethod(FrameHttpUri.QueryCarPhoto+id+".do",null);
        mHttpPublisher.sendRequest(method, FrameHttpTag.QUERY_CAR_PHOTO);
    }

    /**
     *获取车型列表页
     */

    public void getVechileType(Integer id) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("id",id+"");
        HttpMethod method = new HttpMethod(FrameHttpUri.GetVechileType+id+".do", map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.VECHILE_TYPE);
    }

    //获取4S店的组织架构
    public void get4sStructure(int carset_id){
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("carset_id",carset_id+"");
        HttpMethod method = new HttpMethod(FrameHttpUri.Get4sStructure+carset_id+".do",null);
        mHttpPublisher.sendRequest(method, FrameHttpTag.GET_4S_STRUCTURE);
    }

    //统计车系筛选结果总数
    public void coutSearchCarsetCount(String priceStart,String priceEnd,String modelsLevel,String country,String category,String summary,String outVolStar,String outVolEnd){

        Map map = new HashMap<>();
        if(priceStart!=null){
            map.put("priceStart",priceStart);
        }
        if(priceEnd!=null) {
            map.put("priceEnd", priceEnd);
        }
        if(modelsLevel!=null) {
            map.put("modelsLevel", modelsLevel);
        }
        if(country!=null) {
            map.put("country", country);
        }
        if(category!=null) {
            map.put("category", category);
        }
        if(summary!=null) {
            map.put("summary", summary);
        }
        if(outVolStar!=null) {
            map.put("outVolStar", outVolStar);
        }
        if(outVolEnd!=null) {
            map.put("outVolEnd", outVolEnd);
        }

        HttpMethod method = new HttpMethod(FrameHttpUri.CoutSearchCarsetCount, map);
//        HttpMethod method = new HttpMethod(FrameHttpUri.CoutSearchCarsetCount+"?modelsLevel="+modelsLevel+"&category="+category+"&priceStart="+priceStart+"&priceEnd="+priceEnd+"&country="+country+"&outVolStar="+outVolStar+"&outVolEnd="+outVolEnd+"&summary="+summary,null);

        mHttpPublisher.sendRequest(method, FrameHttpTag.COUT_SEARCH_CARSET_COUNT);
    }



    public void addMyCar(String plateNo,String chassisNo,String engineNo,String vid,String carsetId,String vtitle) {

        String token = FrameEtongApplication.getApplication().getUserInfo().getToken();
        int platformID = FrameEtongApplication.getApplication().getUserInfo().getPlatformID();

        Map mapParams = new HashMap<>();
//        map.put("token",token);
        mapParams.put("token", token);
        mapParams.put("plateNo",plateNo);
        mapParams.put("engineNo",engineNo);
        mapParams.put("chassisNo",chassisNo);
        mapParams.put("platformID",platformID+"");
        mapParams.put("vid", vid);
        mapParams.put("carsetId",carsetId);
        mapParams.put("vtitle",vtitle);
        try{
            HttpMethod method = new HttpMethod(FrameHttpUri.AddMyCar, mapParams);
//        HttpMethod method = new HttpMethod(FrameHttpUri.AddMyCar+"?token="+token+"&platformID="+platformID+"&plateNo="+plateNo+"&chassisNo="+chassisNo+"&engineNo="+engineNo, null);
            mHttpPublisher.sendRequest(method, FrameHttpTag.ADD_MY_CAR);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void queryMyCar() {

        Map map = new HashMap<>();

        if(null!=FrameEtongApplication.getApplication().getUserInfo().getToken()){
            String token = FrameEtongApplication.getApplication().getUserInfo().getToken();
            map.put("token",token);
        }
        int platformID = FrameEtongApplication.getApplication().getUserInfo().getPlatformID();
        map.put("platformID",platformID+"");
//        HttpMethod method = new HttpMethod(FrameHttpUri.QueryMyCar+"?token="+token+"&platformID="+platformID, null);
        HttpMethod method = new HttpMethod(FrameHttpUri.QueryMyCar, map);
        mHttpPublisher.sendRequest(method, FrameHttpTag.QUERY_MY_CAR);
    }

    }


