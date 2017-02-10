package com.etong.android.jxbizusedcar.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.DisplayUtil;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxbizusedcar.R;

import org.simple.eventbus.Subscriber;

public class UC_CarIdentifyReportActivity extends BaseSubscriberActivity {
/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/
    public static String CAR_IDENTIFY_SHOW_CONTENT;     // 从其他界面传送过来的设置的key，到底是显示什么界面
    public static final String SHOW_ADD_INFO = "show add info";                   // 显示需要补录资料的界面
    public static final String SHOW_DRAWBACK = "application for drawback";        // 显示要申请退款的界面
    public static final String SHOW_GENERATE_REPORT = "generate report";          // 显示正在生成报告的界面
    private ImageView showIv;
    private TextView showTv;
    private static int TEXT_SIZE = 14;
    private String reportUrl;                     // 从上个界面传送过来的订单号
    private UC_UserProvider provider;       // 请求数据的封装类
    private WebView webView;                // 显示报告的webview
    private ViewGroup content;              // 包含两个控件的ViewGroup

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_car_identify_report_content);
        // 初始化请求数据类
        provider = UC_UserProvider.getInstance();
        UC_UserProvider.initalize(new HttpPublisher());

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
        mTitleBar.setTitle("车鉴定报告");
        mTitleBar.showNextButton(false);

        // 从上个页面发送过来的值
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        reportUrl = extras.getString(CAR_IDENTIFY_SHOW_CONTENT);

        // 显示在页面上的控件
        content = findViewById(R.id.uc_empty_car_identify_report, ViewGroup.class);
        showIv = findViewById(R.id.uc_iv_car_identify_show_img, ImageView.class);
        showTv = findViewById(R.id.uc_tv_car_identify_show_text, TextView.class);

        // 显示报告的WebView
        webView = findViewById(R.id.uc_webview_car_identify_report, WebView.class);
        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
    }

    /**
     * @desc (显示需要补充信息的)
     * @user sunyao
     * @createtime 2016/11/12 - 15:32
     * @param
     * @return
     */
    public void showAddInfo() {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables

        ImageProvider.getInstance().loadImage(showIv,
                "drawable://" + getValueOfImg("uc_iv_pay_for_report_add_info_to_get_report"),
                R.mipmap.uc_all_image_loading);

        String payTitle=String.format("请先补录资料后再查看报告！");
        int position=payTitle.lastIndexOf("补");
        int price=payTitle.lastIndexOf("后");
        SpannableStringBuilder style=new SpannableStringBuilder(payTitle);
        style.setSpan(new AbsoluteSizeSpan(DisplayUtil.dip2px(this,TEXT_SIZE)),0,payTitle.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);//字号
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#71bffc")),position, price, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        showTv.setText(style);

        // 补录资料的TextView点击事件
        showTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到补录信息的界面
                ActivitySkipUtil.skipActivity(UC_CarIdentifyReportActivity.this, UC_AdditionalInformationActivity.class);
            }
        });
    }

    /**
     * @desc (显示申请退款的界面)
     * @user sunyao
     * @createtime 2016/11/12 - 15:34
     * @param
     * @return
     */
    public void showApplicateDrawback() {
        ImageProvider.getInstance().loadImage(showIv,
                "drawable://" + getValueOfImg("uc_iv_pay_for_report_show_applicate_drawback"),
                R.mipmap.uc_all_image_loading);

        String payTitle=String.format("因为某某原因，报告无法生成，请点击退款");
        int position=payTitle.lastIndexOf("退");
        SpannableStringBuilder style=new SpannableStringBuilder(payTitle);
        style.setSpan(new AbsoluteSizeSpan(DisplayUtil.dip2px(this,TEXT_SIZE)), 0, payTitle.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);//字号
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#71bffc")),position, payTitle.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        showTv.setText(style);

        // 点击退款的TextView点击事件
        showTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到退款的界面
//                ActivitySkipUtil.skipActivity(UC_CarIdentifyReportActivity.this, UC_AdditionalInformationActivity.class);
            }
        });
    }

    /**
     * @desc (显示正在生成报告的界面)
     * @user sunyao
     * @createtime 2016/11/12 - 15:34
     * @param
     * @return
     */
    public void showGenerateReport() {
        ImageProvider.getInstance().loadImage(showIv,
                "drawable://" + getValueOfImg("uc_iv_pay_for_report_generate_report_img"),
                R.mipmap.uc_all_image_loading);

        String payTitle=String.format("报告正在生成中， 请稍后...");
        SpannableStringBuilder style=new SpannableStringBuilder(payTitle);
        style.setSpan(new AbsoluteSizeSpan(DisplayUtil.dip2px(this,TEXT_SIZE)), 0, payTitle.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);//字号
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#c9caca")),0, payTitle.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);//颜色
        showTv.setText(style);
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

        if(!TextUtils.isEmpty(reportUrl)) {
            content.setVisibility(View.GONE);
            webView.loadUrl(reportUrl);
        } else {
            content.setVisibility(View.VISIBLE);
            showGenerateReport();
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

    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    /**
     * @desc (从本地获取到图片的id)
     * @user sunyao
     * @createtime 2016/11/14 - 15:49
     * @param
     * @return
     */
    public int getValueOfImg(String imName) {
        int value = 0;
        Class<R.mipmap> cls = R.mipmap.class;
        try {
            value = cls.getDeclaredField(imName).getInt(null);
        } catch (Exception e) {
        }
        return value;
    }

    /**
     * @desc (从网络获取到订单详情的数据)
     * @user sunyao
     * @createtime 2016/11/14 - 15:50
     * @param
     * @return
     */
    @Subscriber(tag = UC_HttpTag.QUERY_ONE_IDENTIFIED)
    public void getOrderDetailInfo(HttpMethod httpMethod) {

        if (httpMethod == null) {
            return;
        }

        String status = httpMethod.data().getString("status");
        String msg = httpMethod.data().getString("msg");
        JSONObject obj = httpMethod.data().getJSONObject("data");

        if ("true".equals(status)) {
            // 如果获取到的数据正常




        } else if(HttpPublisher.NETWORK_ERROR.equals(status)){
            // 网络异常

        } else if(HttpPublisher.DATA_ERROR.equals(status)) {
            // 后台数据错误

        }

    }


/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
