package com.etong.android.jxappfind.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfind.R;

/**
 * 限时购详情 webview
 * Created by Administrator on 2016/9/2.
 */
public class FindWebViewActivity extends BaseSubscriberActivity {
    private HttpPublisher mHttpPublisher;
    private TitleBar mTitleBar;
    private WebView mWebView;
    private LinearLayout mNullLayout;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_activity_webview);
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
//        mMessageProvider = MessageProvider.getInstance();
//        mMessageProvider.initialize(mHttpPublisher);
        initView();
    }


    protected void initView(){
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("限时购");
        mWebView = findViewById(R.id.find_webview, WebView.class);
        mNullLayout = findViewById(R.id.layout_null, LinearLayout.class);
        mTitleBar.showBottomLin(false);

    }



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
//            mWebView.loadDataWithBaseURL(null, mMessageDetailsBean.getContent(), "text/html", "utf-8", null);
            // 移除searchBoxJavaBridge_对象,防止远程调用
            mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
            // mWebView.loadUrl("file:///android_asset/welcome.htm");
            // 为了让WebView能够响应超链接功能，调用setWebViewClient( )方法，设置 WebView视图
            mWebView.setWebViewClient(new StewartWebViewClient());
//            // 设置WebView调用接口
//            mWebView.setWebChromeClient(new InjectedChromeClient("HostApp",
//                    HostJsScope.class));

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
                mNullLayout.setVisibility(View.VISIBLE);
            } catch (Exception e) {
            }

        }
    }

}
