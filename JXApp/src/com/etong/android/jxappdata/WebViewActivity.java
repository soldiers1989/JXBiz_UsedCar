package com.etong.android.jxappdata;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.jxappdata.jsbridge.BridgeWebView;
import com.etong.android.jxappdata.jsbridge.CallBackFunction;
import com.etong.android.util.SerializableObject;

import java.util.Map;
import java.util.UUID;

/**
 * 点击图表变横屏页面
 * 
 * @author Administrator
 * 
 */
public class WebViewActivity extends SubscriberActivity {
	private BridgeWebView webView;
	// private Object datas;
	private String datas;
	public String idName = "" + UUID.randomUUID().toString()
			+ (int) (1 + Math.random() * (10000 - 1 + 1));// id
	String jsonString;
	@Override
	protected void onInit(@Nullable Bundle savedInstanceState) {
		setContentView(R.layout.activity_webview);
//		this.myTitle = "图表全屏显示页";
		// 全屏显示

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);


		setSwipeBackEnable(false);

		webView = findViewById(R.id.show_webView, BridgeWebView.class);

		Bundle bundle = getIntent().getExtras();
		SerializableObject serializableMap = (SerializableObject) bundle
				.get("dataMap");
		Map map = (Map) serializableMap.getObject();

		datas = (String) map.get("data");
		// datas = (Object) map.get("data");
		JSONObject json = JSONObject.parseObject(datas);

		JSONObject object = new JSONObject();
		object.put("id", idName);
		object.put("data", json);
		object.put("height", "100%");
		jsonString = JSON.toJSONString(object);
		webView.getSettings().setAllowFileAccess(true);
		// 开启脚本支持
		webView.getSettings().setJavaScriptEnabled(true);
//		webView.setWebViewClient(new WebViewClientDemo(webView));
		webView.loadUrl("file:///android_asset/myechart.html");



		webView.callHandler("functionInJs", jsonString,new CallBackFunction() {

			@Override
			public void onCallBack(String data) {
				// TODO Auto-generated method stub

			}
		});


	}
	
//	public void loadChart(String jsonString){
//	if(null==webView){
//		webView = findViewById(R.id.show_webView, BridgeWebView.class);
//		
//	}
//	webView.loadUrl("javascript:doCreatChart('"+jsonString+"')");
//	webView.send(jsonString);
//}
	
	
	
	
	/*private class WebViewClientDemo extends BridgeWebViewClient {
		public WebViewClientDemo(BridgeWebView webView) {
			super(webView);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
		
			
			
			view.loadUrl(url);// 当打开新链接时，使用当前的 WebView，不会使用系统其他浏览器
			return true;
		}
		
		@Override
		  public void onPageFinished(WebView view, String url) {
		      super.onPageFinished(view, url);
		      //在这里执行你想调用的js函数
		  	webView.send(jsonString);
//		      loadChart(jsonString);
		      
		  }
	}*/

}
