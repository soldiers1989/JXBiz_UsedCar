package com.etong.android.frame.subscriber;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.etong.android.frame.BaseApplication;
import com.etong.android.frame.common.LoadingDialog;
import com.etong.android.frame.permissions.PermissionsManager;
import com.etong.android.frame.subscriber.swipeback.SwipeBackLayout;
import com.etong.android.frame.subscriber.swipeback.Utils;
import com.etong.android.frame.subscriber.swipeback.base.SwipeBackActivityBase;
import com.etong.android.frame.subscriber.swipeback.base.SwipeBackActivityHelper;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.ActivityStackManager;
import com.etong.android.frame.utils.DefinedToast;

import org.simple.eventbus.EventBus;

import java.util.List;

/**
 * @ClassName : BaseSubscriberActivity
 * @Description : Activity抽象基类</br> 初始化EventBus,LoadingDialog，自定义消息显示等
 * @author : zhouxiqing
 * @date : 2016-3-21 下午3:32:31
 * 
 */
abstract public class BaseSubscriberActivity extends FragmentActivity implements SwipeBackActivityBase {
	protected EventBus mEventBus = EventBus.getDefault();
	protected FrameEtongApplication mFrameEtongApplication = FrameEtongApplication.getApplication();
	private LoadingDialog mLoading = null;

	protected float mDensity;
	protected int mWidth;
	protected int mHeight;

	// Activity侧滑帮助类
	public SwipeBackActivityHelper mSwipeBackActivityHelper;

	private OnClickListener mClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			BaseSubscriberActivity.this.onClick(arg0);
		}
	};

	/**
	 * @Title : getEtongApplication
	 * @Description : 获取到应用中的Application
	 * @return
     */
	protected FrameEtongApplication getEtongApplication() {
		return mFrameEtongApplication;
	}

	/**
	 * @Title : getEventBus
	 * @Description : 获取EventBus实例
	 * @return EventBus EventBus实例
	 */
	protected EventBus getEventBus() {
		return mEventBus;
	}

	/**
	 * @Title : registerSticky
	 * @Description : 静态注册EventBus
	 * @params 设定文件
	 * @return void 返回类型
	 */
	protected void registerSticky() {
		mEventBus.registerSticky(this);
	}

	/**
	 * @Title : back
	 * @Description : back ,finish this activity
	 * @params 设定文件
	 * @return void 返回类型
	 */
	protected void back() {
		finish();
	}

	/**
	 * @Title : toastMsg
	 * @Description : 自定义消息显示
	 * @params
	 * @param msg
	 *            消息内容
	 * @return void 返回类型
	 */
	protected void toastMsg(String msg) {
//		CToast.toastMessage(msg, 0);
		DefinedToast.showToast(this,msg,0);
	}

	/**
	 * @Title : toastMsg
	 * @Description : 自定义消息显示
	 * @params
	 * @param msg
	 *            消息内容
	 * @param errno
	 *            错误内容，当{@link BaseApplication#isDebug}为true时会显示在消息中
	 * @return void 返回类型
	 */
	protected void toastMsg(String msg, String errno) {
		if (BaseApplication.isDebug)
//			CToast.toastMessage(msg + ":" + errno, 0);
			DefinedToast.showToast(this,msg + ":" + errno,0);
		else
//			CToast.toastMessage(msg, 0);
			DefinedToast.showToast(this,msg,0);
	}

	/**
	 * @Title : toastMsg
	 * @Description : 自定义消息显示
	 * @params
	 * @param err
	 *            错误代码，当{@link BaseApplication#isDebug}为true时会显示在消息中
	 * @param msg
	 *            消息内容
	 * @return void 返回类型
	 */
	protected void toastMsg(int err, String msg) {
		if (BaseApplication.isDebug)
//			CToast.toastMessage(msg + "(" + err + ")", 0);
			DefinedToast.showToast(this,msg + "(" + err + ")",0);
		else
//			CToast.toastMessage(msg, 0);
			DefinedToast.showToast(this,msg,0);
	}

	/**
	 * @Title : addClickListener
	 * @Description : 添加点击事件
	 * @params
	 * @param view
	 *            View
	 * @return void 返回类型
	 */
	protected void addClickListener(View view) {
		if (null != view)
			view.setOnClickListener(mClickListener);
	}

	/**
	 * @Title : addClickListener
	 * @Description : 添加点击事件
	 * @params
	 * @param id
	 *            id
	 * @return 设定文件
	 * @return View 返回类型
	 */
	protected View addClickListener(int id) {
		View view = findViewById(id);
		addClickListener(view);
		return view;
	}

	/**
	 * @Title : addClickListener
	 * @Description : 添加点击事件
	 * @params
	 * @param views
	 *            View's
	 * @return void 返回类型
	 */
	protected void addClickListener(View[] views) {
		for (View view : views) {
			addClickListener(view);
		}
	}

	/**
	 * @Title : addClickListener
	 * @Description : 添加点击事件
	 * @params
	 * @param views
	 *            View's
	 * @return void 返回类型
	 */
	protected void addClickListener(List<View> views) {
		for (View view : views) {
			addClickListener(view);
		}
	}

	@SuppressWarnings("unchecked")
	protected <T> T findViewById(int viewId, Class<T> clazz) {
		return (T) super.findViewById(viewId);
	}

	@SuppressWarnings("unchecked")
	protected <T> T findViewById(View parent, int viewId, Class<T> clazz) {
		return (T) parent.findViewById(viewId);
	}

	@Override
	final protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//  must be called before adding content
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 初始化帮助类
		mSwipeBackActivityHelper = new SwipeBackActivityHelper(this);
		mSwipeBackActivityHelper.onActivityCreate();

		if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			
		}
		ActivityStackManager.create().addActivity(this);
		mEventBus.register(this);

		mLoading = new LoadingDialog(this);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mDensity = dm.density;
		mWidth = dm.widthPixels;
		mHeight = dm.heightPixels;

		onInit(savedInstanceState);
	}

	protected void loadStart() {
		mLoading.show();
	}

	/**
	 * @Title : loadStart
	 * @Description : 显示加载Dialog
	 * @params
	 * @param tip 显示的标题
	 *            (null为使用默认标题)
	 * @param time
	 *            显示倒计时时间(秒)(0为不显示倒计时)
	 * @return void 返回类型
	 */
	protected void loadStart(String tip, int time) {
		mLoading.setTip(tip, time);
		mLoading.show();
	}

	/**
	 * @Title : loadFinish
	 * @Description : 隐藏加载Dialog
	 * @params 设定文件
	 * @return void 返回类型
	 */
	protected void loadFinish() {
		mLoading.hide();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		back();
		ActivityStackManager.create().finishActivity(this);
		mEventBus.unregister(this);
		mLoading.dismiss();
	}

	protected void onClick(View view) {
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
			@NonNull String[] permissions, @NonNull int[] grantResults) {
		PermissionsManager.getInstance().notifyPermissionsChange(permissions,
				grantResults);
	}

	abstract protected void onInit(@Nullable Bundle savedInstanceState);

	/*************************************************************************************/
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mSwipeBackActivityHelper.onPostCreate();
	}

	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v == null && mSwipeBackActivityHelper != null)
			return mSwipeBackActivityHelper.findViewById(id);
		return v;
	}

	@Override
	public SwipeBackLayout getSwipeBackLayout() {
		return mSwipeBackActivityHelper.getSwipeBackLayout();
	}

	@Override
	public void setSwipeBackEnable(boolean enable) {
		getSwipeBackLayout().setEnableGesture(enable);
	}

	@Override
	public void scrollToFinishActivity() {
		Utils.convertActivityToTranslucent(this);
		getSwipeBackLayout().scrollToFinishActivity();
	}

	/*************************************************************************************/
}
