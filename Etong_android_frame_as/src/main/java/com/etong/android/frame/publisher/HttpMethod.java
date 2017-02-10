package com.etong.android.frame.publisher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.model.HttpHeaders;
import com.lzy.okhttputils.model.HttpParams;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class HttpMethod {

	public static final int GET = 0;
	public static final int POST = 1;
	// 如果设置缓存，则设置默认的缓存时间为一个小时，否则调用时设置
	public static final int CACHE_LIVE_TIME = 60 * 60 * 1;
	// 默认请求为post请求
	private HttpMethodWay way = HttpMethodWay.POST;
	// 需要传的url和参数
	private String url;
	// 请求参数
	private Map param = null;
	private HttpParams mHttpParams;
	// 请求头部
	private Map mHeaders = null;
	private HttpHeaders mHttpHeaders;
	// 请求的缓存模式, 默认先获取到缓存，如果没有缓存的话再请求网络
	private CacheMode mCacheMode = CacheMode.FIRST_CACHE_THEN_REQUEST;

	private boolean isSetCache = false;
	private int cacheTimeLive = 0;

	private JSONObject data = null;

	/** 请求有参数，没有头部*/
	public HttpMethod(String url, Map params) {
		this.url = url;
		this.param = params;
		mHttpParams = new HttpParams();
		mHttpHeaders = new HttpHeaders();
	}
	/** 请求有参数，有头部*/
	public HttpMethod(String url, Map params, Map headers) {
		this(url, params);
		this.mHeaders = headers;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCacheTimeLive() {
		return cacheTimeLive;
	}

	public void setCacheTimeLive(int cacheTimeLive) {
		this.cacheTimeLive = cacheTimeLive;
	}

	public HttpMethodWay getWay() {
		return way;
	}

	public void setWay(HttpMethodWay way) {
		this.way = way;
	}

	public boolean isSetCache() {
		return isSetCache;
	}

	public void setSetCache(boolean setCache) {
		isSetCache = setCache;
	}

	/** 获取到Headers*/
	public HttpHeaders getHttpHeaders() {
		if(mHeaders!=null) {
			Iterator it=mHeaders.entrySet().iterator();
			String key;
			String value;
			while(it.hasNext()){
				Map.Entry entry = (Map.Entry)it.next();
				key=entry.getKey().toString();
				value=entry.getValue().toString();
				mHttpHeaders.put(key, value);
			}
			return mHttpHeaders;
		}
		return null;
	}

	/** 获取到设置的Map参数*/
	public Map getParam() {
		return param;
	}
	/** 获取到Params*/
	public HttpParams getHttpParams() {
		if(param!=null) {
			Iterator it=param.entrySet().iterator();
			String key;
			String value;
			while(it.hasNext()){
				Map.Entry entry = (Map.Entry)it.next();
				key=entry.getKey().toString();
				value=entry.getValue().toString();
				mHttpParams.put(key, value);
			}
			return mHttpParams;
		}
		return null;
	}

	public CacheMode getmCacheMode() {
		return mCacheMode;
	}

	/**
	 * @desc (设置当前网络请求的缓存模式)
	 * @user sunyao
	 * @createtime 2016/9/18 - 16:47
	 *
	 * @param cacheMode{@link CacheMode}
	 * @return
	 */
	public void setmCacheMode(CacheMode cacheMode) {
		this.mCacheMode = cacheMode;
	}

	public JSONObject data() {
		return data;
	}

	public HttpMethod put(JSONObject data) {
		this.data = data;
		return this;
	}

	public String toJSONString() {
		return JSON.toJSONString(this);
	}

	public JSONObject getJson(String key) {
		return data().getJSONObject(key);
	}

	public <T> T get(String key, Class<T> clazz) {
		return data().getObject(key, clazz);
	}

	public <T> T get(String key, String key2, Class<T> clazz, T def) {
		JSONObject keyJson = data().getJSONObject(key);
		if (null != keyJson) {
			return keyJson.getObject(key2, clazz);
		}
		return def;
	}

	public JSONArray getArray(String key) {
		return data().getJSONArray(key);
	}

	public <T> List<T> getList(String key, Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		JSONArray array = data().getJSONArray(key);
		if (null != array) {
			for (Object o : array) {
				list.add(JSON.toJavaObject((JSONObject) o, clazz));
			}
		}
		return list;
	}

	public <T> List<T> getList(String key1, String key2, Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		JSONObject dataJson = data().getJSONObject(key1);
		if (null != dataJson) {
			JSONArray array = dataJson.getJSONArray(key2);
			if (null != array) {
				for (Object o : array) {
					list.add(JSON.toJavaObject((JSONObject) o, clazz));
				}
			}
		}
		return list;
	}
}