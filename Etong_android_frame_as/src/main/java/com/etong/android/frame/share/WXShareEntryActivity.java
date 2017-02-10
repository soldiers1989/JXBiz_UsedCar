package com.etong.android.frame.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * @ClassName : WXShareEntryActivity
 * @Description : 微信分享回调接收基类<br>
 *              当使用微信分享时须在PackageName.wxapi下添加名为WXPayEntryActivity的Activity,
 *              继承此类<br>
 *              且先调用{@link #initWXAPI(String WECHAT_APP_ID)}初始化微信API<br>
 *              Manifest文件中设置android:exported="true"<br>
 * @author : zhouxiqing
 * @date : 2016-3-18 下午5:14:31
 * 
 */
public abstract class WXShareEntryActivity extends BaseSubscriberActivity
		implements IWXAPIEventHandler {

	// IWXAPI 是第三方app和微信通信的openapi接口
	private IWXAPI api;

	@Override
	protected void onInit(@Nullable Bundle savedInstanceState) {
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

	// 微信发送请求到第三方应用时，会回调到该方法
	@Override
	public void onReq(BaseReq req) {
	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	@Override
	public void onResp(BaseResp resp) {
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			shareSuccess(ShareProvider.getInstance().getInfo(), 0, "正确返回",
					resp.openId);
			break;
		case BaseResp.ErrCode.ERR_COMM:
			shareFail(ShareProvider.getInstance().getInfo(), -1, "一般错误",
					resp.openId);
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			shareCancle(ShareProvider.getInstance().getInfo(), -2, "用户取消",
					resp.openId);
			break;
		case BaseResp.ErrCode.ERR_SENT_FAILED:
			shareFail(ShareProvider.getInstance().getInfo(), -3, "发送失败",
					resp.openId);
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			shareFail(ShareProvider.getInstance().getInfo(), -4, "认证被否决",
					resp.openId);
			break;
		case BaseResp.ErrCode.ERR_UNSUPPORT:
			shareFail(ShareProvider.getInstance().getInfo(), -5, "不支持错误",
					resp.openId);
			break;
		default:
			shareFail(ShareProvider.getInstance().getInfo(), -3, "发送失败",
					resp.openId);
			break;
		}
		finish();
	}

	/**
	 * @Title : onInit
	 * @Description : 初始化，子类可在该函数中对界面进行初始化(界面并不会显示)
	 * @params 设定文件
	 * @return void 返回类型
	 */
	abstract protected void onInit();

	/**
	 * @Title : shareSuccess
	 * @Description : 微信分享成功,子类可在该函数中对微信分享成功结果进行处理
	 * @params
	 * @param ShareInfo
	 *            分享信息
	 * @param errorCode
	 *            返回代码:0
	 * @param msg
	 *            返回消息
	 * @param tag
	 *            请求标识
	 * @return void 返回类型
	 */
	abstract protected void shareSuccess(ShareInfo info, int errorCode,
			String msg, String tag);

	/**
	 * @Title : shareCancle
	 * @Description : 微信分享取消,子类可在该函数中对微信分享取消结果进行处理
	 * @params
	 * @param ShareInfo
	 *            分享信息
	 * @param errorCode
	 *            返回代码:-2
	 * @param msg
	 *            返回消息
	 * @param tag
	 *            请求标识
	 * @return void 返回类型
	 */
	abstract protected void shareCancle(ShareInfo info, int errorCode,
			String msg, String tag);

	/**
	 * @Title : shareFail
	 * @Description : 微信分享失败,子类可在该函数中对微信分享失败结果进行处理
	 * @params
	 * @param ShareInfo
	 *            分享信息
	 * @param errorCode
	 *            返回代码:</br> -1:一般错误;</br>-3:发送失败;</br>-4:认证被否决;</br>-5:不支持错误;
	 * @param msg
	 *            返回消息
	 * @param tag
	 *            请求标识
	 * @return void 返回类型
	 */
	abstract protected void shareFail(ShareInfo info, int errorCode,
			String msg, String tag);
}