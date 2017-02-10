package com.etong.android.jxappdata;

//import com.baidu.mobstat.StatService;
import com.etong.android.jxappdata.common.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
/**
 * 欢迎页
 * @author Administrator
 *
 */
public class WelcomeActivity extends BaseActivity{
	private RelativeLayout rootView;
	private ImageView mImageView;
	private View cancalView;
	private TimeCounter mTimeCounter;//计时器
	@Override
	protected void onInit(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_welcome);
		this.myTitle="欢迎";
		initView();
		mTimeCounter = new TimeCounter(3000, 1000);
		mTimeCounter.start();
	
	}

	public void initView(){
		mImageView = findViewById(R.id.welcome_img, ImageView.class);
		rootView = (RelativeLayout) this.findViewById(R.id.welcome_root);
		cancalView = findViewById(R.id.bt_cancel, View.class);
		addClickListener(R.id.bt_cancel);
		
	}
	
	
	
	@Override
	protected void onClick(View view) {
//		switch (view.getId()) {
//		case R.id.bt_cancel:
//			mTimeCounter.cancel();
//			Intent intent=new Intent(WelcomeActivity.this,DataContentActivity.class);
//			startActivity(intent);
////			this.finish();
//			finish();
//			break;
//		}

		if (R.id.bt_cancel == view.getId()) {
			mTimeCounter.cancel();
			Intent intent=new Intent(WelcomeActivity.this,DataContentActivity.class);
			startActivity(intent);
			this.finish();
			finish();
		}
	}
	
	/* 定义一个倒计时的内部类 */
	class TimeCounter extends CountDownTimer {
		public TimeCounter(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			System.out.println("3s倒计时结束");
			Intent intent=new Intent(WelcomeActivity.this,DataContentActivity.class);
			startActivity(intent);
			finish();
			
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
		}

		public void setClickable(Button clickable) {
		}
	}
	
	
	

}
