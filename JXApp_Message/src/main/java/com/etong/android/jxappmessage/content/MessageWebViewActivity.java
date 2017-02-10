package com.etong.android.jxappmessage.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.safewebview.InjectedChromeClient;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappmessage.R;
import com.etong.android.jxappmessage.javabean.MessageDetailsBean;
import com.etong.android.jxappmessage.utils.MessageProvider;

import org.simple.eventbus.Subscriber;

/**
 * 资讯 详情页面   webview
 * Created by Administrator on 2016/8/30.
 */
public class MessageWebViewActivity extends BaseSubscriberActivity {
    private WebView mWebView;
    private LinearLayout mNullLayout;
    private TitleBar mTitleBar;
    private String mType = null;
    private String mTitle = null;
    private String mUrl = null;

    private HttpPublisher mHttpPublisher;
    private MessageProvider mMessageProvider;
    //    private List<MessageWebViewBean> mList;
    private MessageDetailsBean mMessageDetailsBean;
    private String strId;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.message_activity_webview);
        mHttpPublisher = HttpPublisher.getInstance();
        mHttpPublisher.initialize(this);
        mMessageProvider = MessageProvider.getInstance();
        mMessageProvider.initialize(mHttpPublisher);
        initView();

        //得到资讯id
        Intent intent = getIntent();
        Integer id = intent.getIntExtra("id", -1);
        strId = String.valueOf(id);
        //请求资讯详情接口
        mMessageProvider.MessageDetails(strId);


    }

    //得到后台返回的数据
    @Subscriber(tag = FrameHttpTag.MESSAGE_DETAILS)
    public void getMessageType(HttpMethod method) {

        String errno = method.data().getString("errno");
        String flag = method.data().getString("flag");
        String msg = method.data().getString("msg");

        if (null != errno && errno.equals("PT_ERROR_SUCCESS")) {
            JSONObject jsonObject = method.data().getJSONObject("data");
            mMessageDetailsBean = new MessageDetailsBean();
            mMessageDetailsBean = JSON.toJavaObject(jsonObject, MessageDetailsBean.class);

            //初始化webview
            initWebView();
        }else {
            mWebView.setVisibility(View.GONE);
            mNullLayout.setVisibility(View.VISIBLE);
            mNullLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //刷新数据
                    mMessageProvider.MessageDetails(strId);
                    mWebView.setVisibility(View.VISIBLE);
                    mNullLayout.setVisibility(View.GONE);

                }
            });
        }
    }

    private void initView() {
        mTitleBar = new TitleBar(this);
        mWebView = findViewById(R.id.Vechile_webview, WebView.class);
        mNullLayout = findViewById(R.id.layout_null, LinearLayout.class);
        mTitleBar.showBottomLin(false);

//        Intent intent = MessageWebViewActivity.this.getIntent();
//        mTitle = intent.getStringExtra("title");
        mTitleBar.setTitle("资讯详情");

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
            mWebView.loadDataWithBaseURL(null, mMessageDetailsBean.getContent(), "text/html", "utf-8", null);
            // 移除searchBoxJavaBridge_对象,防止远程调用
            mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
            // mWebView.loadUrl("file:///android_asset/welcome.htm");
            // 为了让WebView能够响应超链接功能，调用setWebViewClient( )方法，设置 WebView视图
            mWebView.setWebViewClient(new StewartWebViewClient());
//            // 设置WebView调用接口
            mWebView.setWebChromeClient(new InjectedChromeClient("HostApp",
                    MessageHostJsScope.class));

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
