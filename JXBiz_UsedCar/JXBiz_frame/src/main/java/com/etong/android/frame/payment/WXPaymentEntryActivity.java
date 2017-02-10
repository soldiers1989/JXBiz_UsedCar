package com.etong.android.frame.payment;

import android.content.Intent;
import android.os.Bundle;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * @ClassName : WXPaymentEntryActivity
 * @Description: <p>
 *               微信支付回调接收基类
 *               </p>
 *               <p>
 *               当使用微信支付时须在PackageName.wxapi下添加名为WXPayEntryActivity的Activity,
 *               继承此类<br>
 *               且先调用{@link #initWXAPI(String WECHAT_APP_ID)}初始化微信API
 *               </p>
 *               <p>
 *               Manifest文件中设置android:exported="true"
 *               </p>
 * @author : zhouxiqing
 * @date : 2016-3-2 上午11:23:31
 * 
 */
public abstract class WXPaymentEntryActivity extends BaseSubscriberActivity
		implements IWXAPIEventHandler {

	private static final String TAG = ".WXPayEntryActivity";

	private IWXAPI api;

	@Override
	protected void onInit(Bundle savedInstanceState) {
		// 初始化
		onInit();
	}

	/**
	 * @Title : initWXAPI
	 * @Description : 初始化微信API
	 * @params
	 * @param WECHAT_APP_ID
	 *            微信APP ID
	 * @return void 返回类型
	 */
	public void initWXAPI(String WECHAT_APP_ID) {
		api = WXAPIFactory.createWXAPI(this, WECHAT_APP_ID);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		System.out.println(TAG + ".onReq(BaseReq req)" + req.toString());
		mEventBus.post(req);
		this.finish();
	}

	@Override
	public void onResp(BaseResp resp) {
		System.out.println(TAG + ".onResp(BaseResp resp)" + resp.toString());
		mEventBus.post(resp);
		this.finish();
	}

	/**
	 * @Title : onInit
	 * @Description : 初始化，子类可在该函数中对界面进行初始化(界面并不会显示)
	 * @params 设定文件
	 * @return void 返回类型
	 */
	abstract protected void onInit();
}