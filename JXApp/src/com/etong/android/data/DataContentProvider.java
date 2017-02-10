package com.etong.android.data;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.frame.request_init.FrameConstant;
import com.etong.android.frame.user.FrameDataUnionUri;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.jxappdata.common.CommonEvent;

import java.util.HashMap;
import java.util.Map;

public class DataContentProvider{
	public static final String ORDER_SUCCEED = "PT_ERROR_SUCCESS";
	private static HttpPublisher mHttpPublisher;
	private static SharedPublisher mSharedPublisher=SharedPublisher.getInstance();
	private static FrameUserInfo mFrameUserInfo = FrameEtongApplication.getApplication().getUserInfo();
	private static DataContentProvider instance;

	
	public void initialize(HttpPublisher httpPublisher) {
		this.mHttpPublisher = httpPublisher;
	}
	private DataContentProvider() {
		
	}

	public static DataContentProvider getInstance() {
		if (null == instance) {
			instance = new DataContentProvider();
		}
		
		return instance;
	}
	
	/**
	 * @Title : getVechileDataList
	 * @Description : TODO(获取数据)
	 * @return void 返回类型
	 */
	public void getDataContent() {
		Map<String, String> map = new HashMap<String, String>();
//		HttpMethod method = new HttpMethod(HttpUri.URL_DATA_CONTENT, map);
//		mHttpPublisher.sendRequest(method, CommonEvent.DATA_CONTENT);
	}
	
	
	//请求后台数据的方法
	 public static void sendRequestData(String sumTime,String url,int ExtarTime,String fragmentName) {
		 Map map=new HashMap<>();
//		 JSONObject obj=JSONObject.parseObject(mSharedPublisher.getString("userShared"));
//		 String token=obj.getString("token");
		 String token = mFrameUserInfo.getToken();
		 map.put("token",token);
		 if(sumTime !=null && ( !(sumTime.isEmpty()))){
			 map.put("sumTime",sumTime);
		 }
//		 map=covertDateString(ExtarTime,map);
//		 map.put("ExtarTime", ExtarTime);
		 map.put("tagName", fragmentName);
		 HttpMethod method = new HttpMethod(FrameConstant.CURRENT_SERVICE+url,map);
		 mHttpPublisher.sendRequest(method,CommonEvent.CHART); 
	         
	 }
	 
	 public static void sendRequestData(String url) {
		 Map map=new HashMap<>();
//		 JSONObject obj=JSONObject.parseObject(mSharedPublisher.getString("userShared"));
//		 String token=obj.getString("token");
		 String token = mFrameUserInfo.getToken();
		 map.put("token",token);
		 HttpMethod method = new HttpMethod(url,map);
		 mHttpPublisher.sendRequest(method,CommonEvent.CHART); 
	 }
	
	 public static Map covertDateString(int targetDate,Map map){
//		 if(targetDate.length()==4){
//			 map.put("ExtarTime", targetDate+"年");
//		 }else if(Integer.parseInt(targetDate)>0 && Integer.parseInt(targetDate)<13){
//			 map.put("ExtarTime", targetDate+"月");		 
//		 }
		 map.put("ExtarTime", targetDate+"");
		 return map;
	 }
	 
	 
	 
	 public static void getListData(String sumTime) {
		 Map map=new HashMap<>();
//		 JSONObject obj=JSONObject.parseObject(mSharedPublisher.getString("userShared"));
//		 String token=obj.getString("token");
		 String token = mFrameUserInfo.getToken();
		 map.put("token",token);
		 map.put("sumTime", sumTime);

		 
		 HttpMethod method = new HttpMethod(FrameDataUnionUri.DATA_INFO,map);
		 mHttpPublisher.sendRequest(method,CommonEvent.DATA_INFO); 
	         
	 }
	 
	 public static void getContentListData(String sumTime,String userId) {
		 Map map=new HashMap<>();
//		 JSONObject obj=JSONObject.parseObject(mSharedPublisher.getString("userShared"));
//		 String token=obj.getString("token");
		 String token = mFrameUserInfo.getToken();
		 map.put("token",token);
		 map.put("sumTime", sumTime);
		 map.put("userID", userId);

		 HttpMethod method = new HttpMethod(FrameDataUnionUri.USER_DATA_INFO,map);
		 mHttpPublisher.sendRequest(method,CommonEvent.DATA_CONTENT_INFO); 
	         
	 }
}
