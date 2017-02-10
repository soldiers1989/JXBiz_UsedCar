package com.etong.android.frame.payment;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.etong.android.frame.common.BaseHttpUri;
import com.etong.android.frame.event.CommonEvent;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.tencent.mm.sdk.openapi.IWXAPI;

import java.util.HashMap;
import java.util.Map;

public class PaymentProvider {
	// “00” – 银联正式环境
	// “01” – 银联测试环境，该环境中不发生真实交易
	public static final String PAYMENT_MODE_CODE = "00";

	// 初始化微信支付
	public static IWXAPI api = null;
	private HttpPublisher mHttpPublisher;
	@SuppressWarnings("unused")
	private static String TAG = "PaymentProvider";
	private static PaymentProvider instance;

	private PaymentProvider() {
		
	}
	
	public void initialize(HttpPublisher httpPublisher, Context context) {
		this.mHttpPublisher = httpPublisher;
	}

	public static PaymentProvider getInstance() {
		if (null == instance) {
			instance = new PaymentProvider();
		}
		return instance;
	}

	/**
	 * @desc (使用FastJson来请求网络的情况)
	 * @user sunyao
	 * @createtime 2016/11/14 - 11:26
	 * @param
	 * @return
	 */
	public void payMent(PayInfo info) {

		Map<String, String> map = new HashMap<String, String>();

		map.put("param", JSON.toJSONString(info));
		String url = "", tag = "";
		switch (info.getType()) {
		case 0:// 0：微信支付;
			url = BaseHttpUri.URI_WECHAT_PAY;
			tag = CommonEvent.WECHAT_PAY;
			break;
		case 1:// 1：支付宝;
			url = BaseHttpUri.URI_ALIPAY_PAY;
			tag = CommonEvent.ALI_PAY;
			break;
		case 2:// 2：财付通;
			url = "";
			tag = "";
			break;
		case 3:// 3：信用卡;
			url = "";
			tag = "";
			break;
		case 4:// 4：储蓄卡;
			url = BaseHttpUri.URI_UNION_PAY;
			tag = CommonEvent.UNION_PAY;
			break;
		}
		HttpMethod method = new HttpMethod(url, map);

		mHttpPublisher.sendRequest(method, tag);
	}

	/**
	 * @desc (使用url拼接的形式来请求)
	 * @user sunyao
	 * @createtime 2016/11/14 - 11:27
	 * @param
	 * @return
	 */
	public void payMentSpliceUrl(PayInfo info) {
		Map map = new HashMap();
		String url = "", tag = "";

		switch (info.getType()) {
			case 0:// 0：微信支付;
				url = BaseHttpUri.URI_WECHAT_PAY  +
						"?f_uid="+info.getF_uid() +
						"&f_org_id="+info.getF_org_id() +
						"&f_vin="+info.getF_vin() +
						"&f_cid="+info.getF_cid();
				tag = CommonEvent.WECHAT_PAY;
				break;
			case 1:// 1：支付宝;
				url = BaseHttpUri.URI_ALIPAY_PAY +
						"?f_uid=" + info.getF_uid() +
						"&f_org_id=" + info.getF_org_id() +
						"&f_vin="+info.getF_vin() +
						"&f_cid="+info.getF_cid();
				tag = CommonEvent.ALI_PAY;
				break;
			case 2:// 2：财付通;
				url = "";
				tag = "";
				break;
			case 3:// 3：信用卡;
				url = "";
				tag = "";
				break;
			case 4:// 4：储蓄卡;
				url = BaseHttpUri.URI_UNION_PAY;
				tag = CommonEvent.UNION_PAY;
				break;
		}
		HttpMethod method = new HttpMethod(url, map);

		mHttpPublisher.sendRequest(method, tag);
	}

	/**
	 * @desc (支付宝支付确认结果)
	 * @user sunyao
	 * @createtime 2016/11/17 - 19:23
	 * @param
	 * @return
	 */
	public void confirmPayment(AlipayResult payResult) {

		if (payResult == null) {
			return;
		}

		String rStr = payResult.getResult();
		if (TextUtils.isEmpty(rStr)) {
			return;
		}
		Map map = new HashMap();
		map.put("params", rStr);
		String url = BaseHttpUri.URI_ALIPAY_CONFIRM;
		String tag = CommonEvent.CONFIRM_ALIPAY;
		HttpMethod method = new HttpMethod(url, map);
		mHttpPublisher.sendRequest(method, tag);
	}
}