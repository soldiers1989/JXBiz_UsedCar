package com.etong.android.jxappdata.user;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.jxappdata.common.CommonEvent;
import com.etong.android.jxappdata.common.HttpUri;

public class UserProvider {
	private HttpPublisher mHttpPublisher;
	
	private static String TAG = "UserProvider";
	private static UserProvider instance;
	
	
	public static final String USER_SUCCEED = "PT_ERROR_SUCCESS";
	private static SharedPublisher mSharedPublisher=SharedPublisher.getInstance();
	
	private UserProvider() {
		
	}
	
	public void initialize(HttpPublisher httpPublisher) {
		this.mHttpPublisher = httpPublisher;
	}

	public static UserProvider getInstance() {
		if (null == instance) {
			instance = new UserProvider();
		}
		return instance;
	}
	
	//登录
	public void login(UserInfo login) {

		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", login.phone);
		map.put("verify", login.passwd);

		HttpMethod method = new HttpMethod(HttpUri.LOGIN, map);

		mHttpPublisher.sendRequest(method,CommonEvent.LOGIN);
	}
	
	//发送验证码
	public void sendVerify(String phone) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", phone);
		HttpMethod method = new HttpMethod(HttpUri.SEND_VERIFY, map);
		mHttpPublisher.sendRequest(method, CommonEvent.SEND_VERIFY);
	}
	
	
		//用户信息
		public void getUserInfo() {
			 Map  map = new HashMap<>();
			 JSONObject obj=JSONObject.parseObject(mSharedPublisher.getString("userShared"));
			 String token=obj.getString("token");
			 String userid=obj.getString("userId");
//			 String userId=obj.getString("userId");
			 map.put("token",token);
			 map.put("userID",userid);

			HttpMethod method = new HttpMethod(HttpUri.USER_INFO, map);

			mHttpPublisher.sendRequest(method,CommonEvent.USER_INFO);
		}
		
}
