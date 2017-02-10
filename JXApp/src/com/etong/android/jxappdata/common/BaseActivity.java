package com.etong.android.jxappdata.common;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baidu.mobstat.StatService;
import com.etong.android.jxappdata.SubscriberActivity;

public class BaseActivity extends SubscriberActivity{

	public String myTitle;
	@Override
	protected void onInit(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
//		StatService.setDebugOn(true);
		
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
//		StatService.setSendLogStrategy(this,SendStrategyEnum.APP_START,	2,false);
		StatService.setDebugOn(true);
		StatService.onPageStart(this, myTitle);
//		StatService.onResume(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		StatService.setSendLogStrategy(this,SendStrategyEnum.APP_START,	2,false);
		// 调试百度统计SDK的Log开关，可以在Eclipse中看到sdk打印的日志，发布时去除调用，或者设置为false
		StatService.setDebugOn(true);
		StatService.onPageEnd(this, myTitle);
//		StatService.onPause(this);
	}
	
	
	

}
