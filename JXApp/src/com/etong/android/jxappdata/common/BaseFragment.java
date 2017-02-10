package com.etong.android.jxappdata.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mobstat.StatService;
import com.etong.android.jxappdata.SubscriberFragment;

public class BaseFragment extends SubscriberFragment{
	public  String myTitle;
//	public WebView webView;
	@Override
	protected View onInit(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onResume() {
		super.onResume();
//		StatService.setSendLogStrategy(getContext(),SendStrategyEnum.APP_START,	2,false);
		StatService.setDebugOn(true);
//		StatService.onResume(this);
		StatService.onPageStart(getContext(), myTitle);
		
//		StatService.bindJSInterface(getContext(), webView);
	}

	@Override
	public void onPause() {
		super.onPause();
//		StatService.setSendLogStrategy(getContext(),SendStrategyEnum.APP_START,	2,false);
		StatService.setDebugOn(true);
		StatService.onPageEnd(getContext(), myTitle);
	
//		StatService.bindJSInterface(getContext(), webView);
//		StatService.onPause(this);
	}
}
