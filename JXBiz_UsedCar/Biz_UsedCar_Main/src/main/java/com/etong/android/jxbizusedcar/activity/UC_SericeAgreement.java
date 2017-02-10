package com.etong.android.jxbizusedcar.activity;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxbizusedcar.R;

public class UC_SericeAgreement extends BaseSubscriberActivity {
    private WebView serviceAgreement;
    private WebSettings mWebSettings;
    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_serice_agreement_content);

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
        mTitleBar.setTitle("服务协议");
        mTitleBar.showNextButton(false);

        serviceAgreement = (WebView)findViewById(R.id.uc_webview_service_agreement);

        // 设置支持JavaScript等
        mWebSettings = serviceAgreement.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        serviceAgreement.loadUrl("file:///android_asset/service_agreement/service_agreement.html");
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





/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
