package com.etong.android.jxbizusedcar.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.etong.android.frame.payment.PayInfo;
import com.etong.android.frame.payment.PaymentActivity;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.UC_FrameEtongApplication;
import com.etong.android.frame.utils.ActivityStackManager;
import com.etong.android.frame.utils.EtongCommonUtils;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.MarkUtils;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.bean.UC_Identify_OrderListBean;

import org.simple.eventbus.EventBus;

public class UC_PaywayPopuwindow extends PaymentActivity {
/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    private ImageView pay_cancle;
    private TextView order_number;
    private RadioButton payAlipay;
    private RadioButton payWeChat;
    private TextView pay_money;
    private Button pay_makesure;
    private UC_Identify_OrderListBean mIdentifyOrderBean;
    private String vinCode;
    private String cidCode;
    private Boolean timeCounterFinish = false;// 计时器完成状态
    private TimeCounter timeCounter = null;
    /**
     * 支付方式, 0为微信支付, 1为支付宝支付
     */
    private int pay_way = 0;
    private String PaymentFailed = "";

    @Override
    protected void onInit() {

        EventBus.getDefault().registerSticky(this);
        setContentView(R.layout.uc_activity_payment);

        initView();
        initData();
    }

/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/

    /**
     * 请使用快捷模板生成注释
     */
    private void initView() {
        // 初始化微信支付
        initWXAPI(MarkUtils.WECHAT_APP_ID);

        //得到订单中心 付款传来的javabean
        Intent intent = getIntent();
        mIdentifyOrderBean = (UC_Identify_OrderListBean) intent.getSerializableExtra("UC_Identify_OrderListBean");
        PaymentFailed = intent.getStringExtra("PaymentFailed");             //得到支付失败页传来的标识

        pay_cancle = findViewById(R.id.uc_iv_pay_cancle, ImageView.class);//取消按钮
        order_number = findViewById(R.id.uc_order_tv_number, TextView.class);//订单编号
        payAlipay = findViewById(R.id.uc_pay_buy_alipay, RadioButton.class);//支付宝
        payWeChat = findViewById(R.id.uc_pay_buy_WeChat, RadioButton.class);//微信
        pay_money = findViewById(R.id.uc_tv_pay_money, TextView.class);//付款金额
        pay_makesure = findViewById(R.id.uc_bt_pay_makesure, Button.class);//确认支付按钮
        payWeChat.setChecked(true);
        addClickListener(R.id.uc_iv_pay_cancle);
        addClickListener(R.id.uc_bt_pay_makesure);
//        addClickListener(R.id.uc_pay_buy_alipay);
//        addClickListener(R.id.uc_pay_buy_WeChat);
    }

/*
  ##################################################################################################
  ##                                     初始化数据的方法                                         ##
  ##################################################################################################
*/

    /**
     * 请使用快捷模板生成注释
     */
    private void initData() {
        order_number.setText(mIdentifyOrderBean.getF_cid());
        pay_money.setText(Double.valueOf(mIdentifyOrderBean.getF_payamt()) + "元");

        vinCode = mIdentifyOrderBean.getF_vin();        // 获取到上个页面的vin码
        cidCode = mIdentifyOrderBean.getF_cid();        // 获取到页面上传送到的cid码

    }

/*
  ##################################################################################################
  ##                                     点击事件的处理                                           ##
  ##################################################################################################
*/

    /**
     * 控件的点击事件
     */
    @Override
    protected void onClick(View view) {
        if (!EtongCommonUtils.isFastDoubleClick()) {
            if (view.getId() == R.id.uc_bt_pay_makesure) {//确认支付按钮
                pay();
            } else if (view.getId() == R.id.uc_iv_pay_cancle) {//取消按钮
                finish();
                overridePendingTransition(0, R.anim.push_bottom_out);       // 退出动画
            }
        }
    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    public void pay() {

        // 如果获取到的
        if (TextUtils.isEmpty(vinCode) || TextUtils.isEmpty(cidCode)) {
            toastMsg("信息获取错误！");
            return;
        }
        // 看哪个被选中
        if (payAlipay.isChecked()) {
            pay_way = 1;
        } else if (payWeChat.isChecked()) {
            pay_way = 0;
        }

        PayInfo payInfo = new PayInfo();
        payInfo.setF_uid(UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid());
        payInfo.setF_org_id("001");
        payInfo.setF_vin(vinCode);
        payInfo.setF_cid(cidCode);
        payInfo.setTxnAmt(String.valueOf(getString(R.string.pay_for_report_price)));    // 实际所需要的付款
//        payInfo.setTxnAmt(""+0.01);						// 测试付款
        payInfo.setBusId(MarkUtils.ETONG_APPKEY);
//        payInfo.setOrderType("2");
        switch (pay_way) {
            case 0:// 微信支付
                payInfo.setType(0);
                payInfo.setTradeInfo("鉴定报告");
                setPayInfo(payInfo);
                break;
            case 1:// 支付宝支付
                payInfo.setType(1);
                payInfo.setTradeName("鉴定报告 - 金额");
                payInfo.setTradeInfo("鉴定报告");
                setPayInfo(payInfo);
                break;
            default:
                break;
        }
    }

    @Override
    protected void paySuccess(PayInfo info, int errorCode, String msg) {

        if (info.getOrderId().equals(cidCode)) {
            // TODO 支付成功
            loadStart("支付结果确认中...", 0);
            timeCounterFinish = false;
            timeCounter = new TimeCounter(5 * 1000, 1 * 1000);
            timeCounter.start();
        }

        L.d("支付成功", "支付成功了~~~~");
        Intent intent = new Intent(this, UC_PayResultsActivity.class);//跳转到支付结果页
        intent.putExtra("PayInfo", info);
        intent.putExtra("errorCode", errorCode);
        startActivity(intent);
        finish();
    }

    @Override
    protected void payCancle(PayInfo info, int errorCode, String msg) {

    }

    @Override
    protected void payFail(PayInfo info, int errorCode, String msg) {
        L.d("支付失败", "支付失败了~~~~");
//        ActivityStackManager.create().findActivity(UC_PayResultsActivity.class);
        if ("".equals(PaymentFailed) || TextUtils.isEmpty(PaymentFailed)) {
            Intent intent = new Intent(this, UC_PayResultsActivity.class);
            intent.putExtra("PayInfo", info);
            intent.putExtra("errorCode", errorCode);
            startActivity(intent);
        }
        finish();
    }





/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/


    /**
     * @desc ( 定义一个倒计时的内部类 )
     * @user sunyao
     * @createtime 2016/11/18 - 15:13
     * @param
     * @return
     */
    class TimeCounter extends CountDownTimer {

        public TimeCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示mPayProvider

        }
    }

}
