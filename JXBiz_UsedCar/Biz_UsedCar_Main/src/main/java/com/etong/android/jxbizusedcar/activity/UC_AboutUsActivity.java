package com.etong.android.jxbizusedcar.activity;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxbizusedcar.R;

public class UC_AboutUsActivity extends BaseSubscriberActivity {
/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/
    private WebView mWebView;
    private Handler mHandler;
    private WebSettings mWebSettings;


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_uc_about_us);

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
        mTitleBar.setTitle("关于我们");
        mTitleBar.showNextButton(false);

        mWebView = (WebView) this.findViewById(R.id.uc_content_about_us_webview);

        // 设置支持JavaScript等
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/aboutus/about-us.html");

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
