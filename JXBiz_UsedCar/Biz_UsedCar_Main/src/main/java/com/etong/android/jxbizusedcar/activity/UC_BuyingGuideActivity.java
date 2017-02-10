package com.etong.android.jxbizusedcar.activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.safewebview.InjectedChromeClient;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.utils.UC_CarAssessHostJsScope;

/**
 * @desc 买车攻略
 * @createtime 2016/11/22 - 9:15
 * @author xiaoxue
 */
public class UC_BuyingGuideActivity extends BaseSubscriberActivity {
    private TitleBar mTitleBar;
    private WebView mWebView;
    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_buying_guide);


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
        mTitleBar.setTitle("买车攻略");
        mTitleBar.showNextButton(false);
        mTitleBar.showBottomLin(false);
        mWebView =findViewById(R.id.uc_web_webview, WebView.class);
        initWebView();

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

    /**
     * @desc (初始化webview)
     * @createtime 2016/11/4 0004-9:21
     * @author wukefan
     */
    private void initWebView() {
        try {
            WebSettings webSettings = mWebView.getSettings();
            // 支持javascript
            webSettings.setJavaScriptEnabled(true);
            // 设置可以支持缩放
            webSettings.setSupportZoom(true);
            // 扩大比例的缩放
            webSettings.setUseWideViewPort(true);
            // 自适应屏幕
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webSettings.setLoadWithOverviewMode(true);
            // 设置只读取本地缓存
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            // 加载需要显示的网页
//            mWebView.loadUrl(urlData);
            // 移除searchBoxJavaBridge_对象,防止远程调用
            mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
             mWebView.loadUrl("file:///android_asset/cf_buycars_strategy.png");
            // 为了让WebView能够响应超链接功能，调用setWebViewClient( )方法，设置 WebView视图
            mWebView.setWebViewClient(new UC_BuyingGuideActivity.StewartWebViewClient());
//            // 设置WebView调用接口
            mWebView.setWebChromeClient(new InjectedChromeClient("HostApp",
                    UC_CarAssessHostJsScope.class));

        } catch (Exception e) {
        }
//        // 开启 DOM storage API 功能
//        mWebView.getSettings().setDomStorageEnabled(true);
//        //开启 database storage API 功能
//        mWebView.getSettings().setDatabaseEnabled(true);
//        String cacheDirPath = getFilesDir().getAbsolutePath()+"/webcache";
//        //设置数据库缓存路径
//        mWebView.getSettings().setDatabasePath(cacheDirPath);
//        //设置  Application Caches 缓存目录
//        mWebView.getSettings().setAppCachePath(cacheDirPath);
//        //开启 Application Caches 功能
//        mWebView.getSettings().setAppCacheEnabled(true);
    }

    // Web视图
    private class StewartWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                view.loadUrl(url);

            } catch (Exception e) {
            }
            // mProgressBar.setVisibility(View.VISIBLE);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            // mProgressBar.setVisibility(View.GONE);
        }

        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            try {
                mWebView.setVisibility(View.GONE);
            } catch (Exception e) {
            }

        }
    }



/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
