package com.etong.android.frame.user;

import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.publisher.SharedPublisher;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FrameUserProvider {
	private HttpPublisher mHttpPublisher;

	private static String TAG = "UserProvider";
	private static FrameUserProvider instance;


	public static final String USER_SUCCEED = "PT_ERROR_SUCCESS";
	private static SharedPublisher mSharedPublisher=SharedPublisher.getInstance();

	private FrameUserProvider() {
		
	}
	
	public void initialize(HttpPublisher httpPublisher) {
		this.mHttpPublisher = httpPublisher;
	}

	public static FrameUserProvider getInstance() {
		if (null == instance) {
			instance = new FrameUserProvider();
		}
		return instance;
	}
	
	//登录
	public void login(FrameUserInfo login) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", login.userPhone);
		map.put("verify", login.passwd);
//		if(null!=login.avatar){
//			map.put("avatar",login.avatar);
//		}

		HttpMethod method = new HttpMethod(FrameDataUnionUri.LOGIN, map);

		mHttpPublisher.sendRequest(method,FrameCommonEvent.LOGIN);
	}
	
	//发送验证码
	public void sendVerify(String phone) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", phone);
		HttpMethod method = new HttpMethod(FrameDataUnionUri.SEND_VERIFY, map);
		mHttpPublisher.sendRequest(method, FrameCommonEvent.SEND_VERIFY);
	}
	
	
		//用户信息（之前津湘汽车数据分析时的获取用户信息接口）
		public void getUserInfo() {
			 Map  map = new HashMap<>();
			 JSONObject obj=JSONObject.parseObject(mSharedPublisher.getString("userShared"));
			 String token=obj.getString("token");
			 String userid=obj.getString("userId");
//			 String userId=obj.getString("userId");
			 map.put("token",token);
			 map.put("userID",userid);

			HttpMethod method = new HttpMethod(FrameDataUnionUri.USER_INFO, map);

			mHttpPublisher.sendRequest(method,FrameCommonEvent.USER_INFO);
		}

		//上传头像
		public  void uploadingImage(File img){
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("img", img+"");
//			HttpMethod method = new HttpMethod(FrameDataUnionUri.UPLOAD_IMAGE+img+".do", map);
			HttpMethod method = new HttpMethod(FrameDataUnionUri.UPLOAD_IMAGE, null);
			mHttpPublisher.sendRequest(method,FrameCommonEvent.UPLOAD_IMAGE);
		}

		
}
