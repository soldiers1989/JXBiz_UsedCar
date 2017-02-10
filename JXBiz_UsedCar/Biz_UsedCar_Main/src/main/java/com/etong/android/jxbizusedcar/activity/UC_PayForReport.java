package com.etong.android.jxbizusedcar.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.etong.android.frame.payment.PayInfo;
import com.etong.android.frame.payment.PaymentActivity;
import com.etong.android.frame.user.UC_FrameEtongApplication;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.DensityUtils;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.MarkUtils;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.utils.SerializMap;

import org.simple.eventbus.EventBus;

import java.util.Map;

public class UC_PayForReport extends PaymentActivity {
/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/
    private TextView payMoney;          // 支付的金额
    private TextView readServiceTv;     // 阅读的服务协议的TextView
    private CheckBox readServiceCb;     // 阅读的服务协议的CheckBox
    private TitleBar mTitleBar;
    /** 支付方式, 0为微信支付, 1为支付宝支付 */
    private int pay_way = 0;
    private Button payImmediately;
    private String vinCode;
    private String cidCode;
    private RadioButton payAlipay;     // 选中支付宝付款
    private RadioButton payWeChat;     // 选中微信付款

    @Override
    protected void onInit() {
        EventBus.getDefault().registerSticky(this);
        setContentView(R.layout.uc_activity_pay_for_report_content);

        initWXAPI(MarkUtils.WECHAT_APP_ID);

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

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("支付获取报告");
        mTitleBar.showNextButton(false);

        payAlipay = findViewById(R.id.uc_cb_pay_for_report_alipay, RadioButton.class);         // 选中支付宝付款
        payWeChat = findViewById(R.id.uc_cb_pay_for_report_wechat, RadioButton.class);         // 选中微信付款
        payMoney = findViewById(R.id.uc_pay_for_report_tv_pay_money, TextView.class);       // 付款的TextView
        readServiceTv = findViewById(R.id.uc_tv_pay_for_report_read, TextView.class);         // 阅读的TextView
        readServiceCb = findViewById(R.id.uc_cb_pay_for_report_read, CheckBox.class);         // 阅读的CheckBox
        payImmediately = findViewById(R.id.uc_btn_pay_for_immediately, Button.class);       // 付款的Button

        addClickListener(R.id.uc_tv_pay_for_report_read);
        addClickListener(R.id.uc_btn_pay_for_immediately);
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

        // 获取到上个页面传送过来的值
        Intent i = getIntent();
        SerializMap dMap = (SerializMap) i.getSerializableExtra(UC_Car_IdentifyActivity.IDENTIFY_DATA);     // 获取到序列化对象
        if(dMap != null) {
            Map<String, String> m = dMap.getMap();
            // 获取到的VIN码
            vinCode = m.get(UC_Car_IdentifyActivity.IDENTIFY_VIN_CODE);
            // 获取到的cid码
            cidCode = m.get(UC_Car_IdentifyActivity.IDENTIFY_CID_CODE);
        }
        // 设置支付金额的TextView文字
        String text = String.format("支付金额   ¥%1$s元", getString(R.string.pay_for_report_price));
        int index = text.lastIndexOf("¥");
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(this, 16)), 0, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);  // 设置字号
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#cf1c36")), index, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        payMoney.setText(style);

        // 设置阅读协议TextView
        String readServiceStr = String.format("我已阅读过  《车鉴定服务协议》");
        int fromIndex = text.lastIndexOf(" ");
        SpannableStringBuilder readStyle = new SpannableStringBuilder(readServiceStr);
        readStyle.setSpan(new AbsoluteSizeSpan(DensityUtils.dp2px(this, 16)), 0, readServiceStr.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);  // 设置字号
        readStyle.setSpan(new ForegroundColorSpan(Color.parseColor("#5199F6")), fromIndex, readServiceStr.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        readServiceTv.setText(readStyle);
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
        if (view.getId() == R.id.uc_tv_pay_for_report_read) {
            ActivitySkipUtil.skipActivity(this, UC_SericeAgreement.class);
        } else if (view.getId() == R.id.uc_btn_pay_for_immediately) {
            if(readServiceCb.isChecked()) {
                pay();
            } else {
                toastMsg("请确保您已经阅读车鉴定服务协议！");
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
        if(TextUtils.isEmpty(vinCode) || TextUtils.isEmpty(cidCode)) {
            toastMsg("信息获取错误！");
            return;
        }
        // 看哪个被选中
        if(payAlipay.isChecked()) {
            pay_way = 1;
        } else if(payWeChat.isChecked()) {
            pay_way = 0;
        }

        PayInfo payInfo = new PayInfo();
        payInfo.setF_uid(UC_FrameEtongApplication.getApplication().getUserInfo().getF_userid());
        payInfo.setF_org_id("001");
        payInfo.setF_vin(vinCode.toUpperCase());            // 将获取到的字符串转换成大写
        payInfo.setF_cid(cidCode);
//        payInfo.setOrderId(orderNum);
//        payInfo.setTxnAmt(String.valueOf(payMoney));	// 实际所需要的付款
		payInfo.setTxnAmt(""+getString(R.string.pay_for_report_price));						// 测试付款
        payInfo.setBusId(MarkUtils.ETONG_APPKEY);
//        payInfo.setOrderType("2");
        switch (pay_way) {
            case 0:// 微信支付
                payInfo.setType(0);
                payInfo.setTradeInfo("汽车商品");
                setPayInfo(payInfo);
                break;
            case 1:// 支付宝支付
                payInfo.setType(1);
                payInfo.setTradeName("汽车商品 - 金额");
                payInfo.setTradeInfo("汽车商品");
                setPayInfo(payInfo);
                break;
            default:
                break;
        }
    }

    @Override
    protected void paySuccess(PayInfo info, int errorCode, String msg) {
        L.d("支付成功","支付成功了~~~~");
        Intent intent =new Intent(this,UC_PayResultsActivity.class);//跳转到支付结果页
        intent.putExtra("PayInfo",info);
        intent.putExtra("errorCode",errorCode);
        startActivity(intent);
        finish();
    }

    @Override
    protected void payCancle(PayInfo info, int errorCode, String msg) {

    }

    @Override
    protected void payFail(PayInfo info, int errorCode, String msg) {
        L.d("支付失败","支付失败了~~~~");
        Intent intent =new Intent(this,UC_PayResultsActivity.class);
        intent.putExtra("PayInfo",info);
        intent.putExtra("errorCode",errorCode);
        startActivity(intent);
        finish();
    }

/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
