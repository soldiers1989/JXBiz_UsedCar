package com.etong.android.jxbizusedcar.wxapi;

import com.etong.android.frame.payment.WXPaymentEntryActivity;
import com.etong.android.frame.utils.MarkUtils;

/**
 * @author : by sunyao
 * @ClassName : WXPayEntryActivity
 * @Description : (微信支付回调)
 * @date : 2016/11/9 - 15:00
 */

public class WXPayEntryActivity extends WXPaymentEntryActivity {

    @Override
    protected void onInit() {
        initWXAPI(MarkUtils.WECHAT_APP_ID);
    }
}
