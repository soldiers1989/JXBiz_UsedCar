package com.etong.android.jxbizusedcar.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.etong.android.frame.payment.PayInfo;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.ActivityStackManager;
import com.etong.android.frame.utils.DisplayUtil;
import com.etong.android.frame.utils.EtongCommonUtils;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.bean.UC_Identify_OrderListBean;

/**
 * @author xiaoxue
 * @desc 支付结果页
 * @createtime 2016/11/9 - 17:51
 */
public class UC_PayResultsActivity extends BaseSubscriberActivity {
    private TextView pay_results;
    private ImageView pay_image;
    private TextView hint_message;
    private Button buttonLeft;
    private Button buttonRight;
    private String payResult;
    private PayInfo payInfo;
    private int errorCode;//判断支付成功还是支付失败  0成功  其他失败

    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_pay_results);
        //得到上一页面传来的值
        Intent intent = getIntent();
        payInfo = (PayInfo) intent.getSerializableExtra("PayInfo");
        errorCode = intent.getIntExtra("errorCode", -9999);

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
        TitleBar mTitleBar = new TitleBar(this);
        mTitleBar.setTitleTextColor("#3D3938");//设置title颜色
        mTitleBar.setTitle("支付结果");
        mTitleBar.showNextButton(false);
        mTitleBar.setmTitleBarBackground("#F6F6F6");//设置titlebar背景色
        mTitleBar.showBottomLin(false);

        pay_image = findViewById(R.id.uc_iv_pay_image, ImageView.class);//图片
        pay_results = findViewById(R.id.uc_tv_pay_results, TextView.class);//支付结果view
        hint_message = findViewById(R.id.uc_tv_hint_message, TextView.class);//支付成功或失败的提示信息
        buttonLeft = findViewById(R.id.uc_pay_buttonLeft, Button.class);//左边button
        buttonRight = findViewById(R.id.uc_pay_buttonRight, Button.class);//右边的button

        //动态设置订单支付成功或失败及支付金额的TextView
        payResult = "";
        if (errorCode == 0 || errorCode == 9000) {
            payResult = "成功";
        } else {
            payResult = "失败";
        }
        String payTitle = String.format("订单支付%1$s\n支付金额  ￥%2$s元", payResult, Double.valueOf(payInfo.getTxnAmt()));
        int position = payTitle.lastIndexOf("支");
        int price = payTitle.lastIndexOf("￥");
        SpannableStringBuilder style = new SpannableStringBuilder(payTitle);
        style.setSpan(new AbsoluteSizeSpan(DisplayUtil.dip2px(this, 13)), position, payTitle.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);//字号
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#cf1c36")), price, payTitle.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        pay_results.setText(style);

        addClickListener(R.id.uc_pay_buttonLeft);
        addClickListener(R.id.uc_pay_buttonRight);
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
        if (errorCode == 0 || errorCode == 9000) {//成功
            pay_image.setImageResource(R.mipmap.uc_ic_pay_success);
//            payResult="成功";
            hint_message.setText("鉴定报告约10分钟后出来! 请关注订单中心");
            buttonLeft.setText("查看订单");
            buttonRight.setText("返回首页");
        } else {//失败
            pay_image.setImageResource(R.mipmap.uc_ic_pay_failed);
//            payResult ="失败";
            hint_message.setText("支付失败, 请重新支付!");
            buttonLeft.setText("查看订单");
            buttonRight.setText("重新支付");
        }
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
            if (view.getId() == R.id.uc_pay_buttonLeft) {//左边按钮
                ActivityStackManager.create().finishActivity(UC_OrdercentreActivity.class);
                ActivitySkipUtil.skipActivity(this, UC_OrdercentreActivity.class);//跳转到查看订单页
                finish();
            } else if (view.getId() == R.id.uc_pay_buttonRight) {//右边按钮
                if (errorCode == 0 || errorCode == 9000) {//支付成功
                    Intent intent = new Intent(this, UsedCar_MainActivity.class);//首页
                    startActivity(intent);
                    finish();
                } else {//支付失败
                    //跳转到付款详情页
                    UC_Identify_OrderListBean mUC_Identify_OrderListBean = new UC_Identify_OrderListBean();
                    mUC_Identify_OrderListBean.setF_vin(payInfo.getF_vin());
                    mUC_Identify_OrderListBean.setF_cid(payInfo.getF_cid());
                    mUC_Identify_OrderListBean.setF_payamt(payInfo.getTxnAmt());
                    mUC_Identify_OrderListBean.setF_paytype(payInfo.getType() + "");
                    Intent intent = new Intent(this, UC_PaywayPopuwindow.class);
                    intent.putExtra("UC_Identify_OrderListBean", mUC_Identify_OrderListBean);
                    intent.putExtra("PaymentFailed", "PaymentFailed");
                    startActivity(intent);
//                finish();
                }
            }
        }
    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/





/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
