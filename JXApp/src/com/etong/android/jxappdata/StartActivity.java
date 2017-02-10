package com.etong.android.jxappdata;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.mobstat.StatService;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.frame.update.AppUpdateProvider;
import com.etong.android.frame.update.AppUpdateResultAction;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.jxappdata.common.BaseActivity;
import com.etong.android.jxappdata.common.CommonEvent;
import com.etong.android.jxappdata.common.MarkUtils;
import com.etong.android.jxappdata.user.LoginActivity;
import com.etong.android.jxappdata.user.UserInfo;
import com.etong.android.jxappdata.user.UserProvider;

import org.simple.eventbus.Subscriber;

//import com.baidu.mobstat.StatService;
/**
 * 启动页
 * @author Administrator
 *
 */
public class StartActivity extends BaseActivity {
	private RelativeLayout rootView;
	private ImageView mImageView;
	private final static long ANIMATION_DURATION = 1000;
	public static Boolean CAN_START = false;
	private SharedPublisher mSharedPublisher = SharedPublisher.getInstance();
	private UserProvider mUserProvider;
	private LinearLayout check_network;
	ImageView imageIcon;
	TextView refreshButton;
	@Override
	protected void onInit(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	
		setContentView(R.layout.activity_start);
		this.myTitle="开始";
		
		StatService.setAppKey("58d8b80bb7");
		// 初始化Http请求
		HttpPublisher mHttpPublisher = HttpPublisher.getInstance();
		mHttpPublisher.initialize(this);
		
		mUserProvider = UserProvider.getInstance();
		// 初始化自动更新
		AppUpdateProvider.getInstance().initialize(mHttpPublisher,
				MarkUtils.ETONG_APPKEY);
		initView();
		// 自动更新
//		 AppUpdateProvider.getInstance().getUpdateInfo();
		AppUpdateProvider.getInstance().getUpdateInfo(
				"http://payment.suiyizuche.com:8080/version/app/1007",
				new AppUpdateResultAction() {

					@Override
					public void noUpdate() {
						// TODO Auto-generated method stub
//						srartActivity();
					}

					@Override
					public void fail(int errCode, String errStr) {
						// TODO Auto-generated method stub
						switch (errCode) {
						case AppUpdateProvider.ERR_NULL:// 返回更新内容为空
							break;
						case AppUpdateProvider.ERR_NETWORK: // 网络异常
							break;
						case AppUpdateProvider.ERR_CANCLE:// 取消更新
							srartActivity();
							break;
						case AppUpdateProvider.ERR_LATER:// 稍后更新
							srartActivity();
							break;
						}
						return;
					}
				
				});
//		initView();
	}

	public void initView() {
		mImageView = findViewById(R.id.start_img, ImageView.class);
		rootView = (RelativeLayout) this.findViewById(R.id.start_root);
		rootView.startAnimation(setAnimation());
		
		check_network = findViewById(R.id.ll_check_network, LinearLayout.class);
		imageIcon = findViewById(R.id.iv_come, ImageView.class);
		refreshButton = findViewById(R.id.tv_refresh, TextView.class);
		addClickListener(R.id.tv_refresh);
	}

	/**
	 * 设置动画
	 * 
	 * @return
	 */
	private AnimationSet setAnimation() {
		// 透明度的动画
		AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
		alphaAnimation.setDuration(ANIMATION_DURATION);// 设置动画的时长
		alphaAnimation.setFillAfter(true);

		// 动画集合
		AnimationSet set = new AnimationSet(false);
		set.addAnimation(alphaAnimation);
		// 监听动画执行
		set.setAnimationListener(new StartAnimationListener());

		return set;
	}

	public void srartActivity() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		JSONObject obj = JSONObject.parseObject(mSharedPublisher
				.getString("userShared"));
		// String token = obj.getString("token");
		if (obj != null) {
		
			mUserProvider.getUserInfo();
			
			
			

		} else {
			ActivitySkipUtil.skipActivity(this, LoginActivity.class);
			this.finish();
		}
		
	}
	
	
	@Subscriber(tag = CommonEvent.USER_INFO)
	public void onGetUserInfo(HttpMethod method) {
		try {
			showView();
			String ptError = method.data().getString("errno");
			String flag = method.data().getString("flag");
			String message = method.data().getString("msg");
//			String succeed=method.data().getString("succeed");
//			if(UserProvider.USER_SUCCEED.equals(ptError)){
			
			if (!flag.equals("0")) {
				if(flag.equals(HttpPublisher.NETWORK_ERROR)){
					hideView();
					return;
				}
				toastMsg("用户信息已失效,请重新登录");
//				hideWebView();
				ActivitySkipUtil.skipActivity(this, LoginActivity.class);
				this.finish();
				return;
			}
			
				JSONObject data = method.data().getJSONObject("data");
//				int userID=data.getInteger("userid");
////				int organID=data.getInteger("organID");
//				int phone=data.getInteger("phone");
////				String userName=data.getString("userName");
//				int roleID=data.getInteger("roleid");
//				String remark=data.getString("remark");
				JSONArray authority=data.getJSONArray("authority");
				
				UserInfo mUserInfo = EtongApplication.getApplication().getUserInfo();
				mUserInfo.setAuthority(authority);
				EtongApplication.getApplication().setUserInfo(mUserInfo);
				ActivitySkipUtil.skipActivity(this, WelcomeActivity.class);
				this.finish();
		} catch (Exception e) {
			ActivitySkipUtil.skipActivity(this, LoginActivity.class);
			this.finish();
		}
		
		
	}
	public void hideView() {
		check_network.setVisibility(View.VISIBLE);
		imageIcon.setVisibility(View.VISIBLE);
		refreshButton.setVisibility(View.VISIBLE);
		mImageView.setVisibility(View.GONE);
		}
	public void showView() {
		mImageView.setVisibility(View.VISIBLE);
		imageIcon.setVisibility(View.GONE);
		refreshButton.setVisibility(View.GONE);
		check_network.setVisibility(View.GONE);
	}
	
	class StartAnimationListener implements AnimationListener {
		protected final long ANIMATION_DELAY = System.currentTimeMillis();

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			// 动画结束后，等待一会，再进行页面跳转

			new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						if ((System.currentTimeMillis() - ANIMATION_DELAY) > 1000) {
							srartActivity();
							return;
						}
					}
				}
			}).start();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

	}
	
	@Override
	protected void onClick(View view) {
		// TODO Auto-generated method stub
		super.onClick(view);
//		switch (view.getId()) {
//		case R.id.tv_refresh:
//			mUserProvider.getUserInfo();
//			break;
//
//		default:
//			break;
//		}

		if (R.id.tv_refresh == view.getId()) {
			mUserProvider.getUserInfo();
		}
	}
	
}
