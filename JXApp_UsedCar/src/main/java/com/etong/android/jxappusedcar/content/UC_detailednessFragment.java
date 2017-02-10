package com.etong.android.jxappusedcar.content;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.etong.android.frame.safewebview.InjectedChromeClient;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.jxappusedcar.R;

/**
 * @desc 详细内容的fragment
 * @createtime 2016/10/10 - 15:40
 * @Created by xiaoxue.
 */

public class UC_detailednessFragment extends BaseSubscriberFragment {

    private WebView mWebview;

    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.used_car_details_detailedness_fragment, container, false);
        initView(view);
        return view;
    }

    /**
     * @desc 初始化webview
     * @createtime 2016/10/11 - 9:16
     * @author xiaoxue
     */

    protected void initView(View view) {
        mWebview = (WebView) view.findViewById(R.id.used_car_detailedness_webview);
    }

    private void initWebView() {
        try {
            WebSettings webSettings = mWebview.getSettings();
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
//            mWebview.loadDataWithBaseURL(null, mMessageDetailsBean.getContent(), "text/html", "utf-8", null);
            // 移除searchBoxJavaBridge_对象,防止远程调用
            mWebview.removeJavascriptInterface("searchBoxJavaBridge_");
            // mWebView.loadUrl("file:///android_asset/welcome.htm");
            // 为了让WebView能够响应超链接功能，调用setWebViewClient( )方法，设置 WebView视图
            mWebview.setWebViewClient(new StewartWebViewClient());
//            // 设置WebView调用接口
            mWebview.setWebChromeClient(new InjectedChromeClient("HostApp",
                    UC_detailednessHostJsScope.class));

        } catch (Exception e) {
            throw e;
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
                throw e;
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
                mWebview.setVisibility(View.GONE);
//                mNullLayout.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                throw e;
            }

        }
    }
}
