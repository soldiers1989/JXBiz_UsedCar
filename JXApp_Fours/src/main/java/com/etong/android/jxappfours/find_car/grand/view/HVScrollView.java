package com.etong.android.jxappfours.find_car.grand.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.etong.android.jxappfours.find_car.grand.config_compare.Find_car_CompareContentActivity;

/**
 * 多个 HorizontalScrollView 在ListView中获取到焦点传送给父类
 */
public class HVScrollView extends HorizontalScrollView {
	
	private Find_car_CompareContentActivity activity;
	
	public HVScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		activity = (Find_car_CompareContentActivity) context;
	}

	
	public HVScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		activity = (Find_car_CompareContentActivity) context;
	}

	public HVScrollView(Context context) {
		super(context);
		activity = (Find_car_CompareContentActivity) context;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//进行触摸赋值
		activity.mTouchView = this;
		return super.onTouchEvent(ev);
	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		//当当前的CHSCrollView被触摸时，滑动其它
		if(activity.mTouchView == this) {
			activity.onScrollChanged(l, t, oldl, oldt);
		}else{
			super.onScrollChanged(l, t, oldl, oldt);
		}
	}
}
