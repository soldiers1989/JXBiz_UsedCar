package com.etong.android.frame.publisher;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.MD5Utils;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheEntity;
import com.lzy.okhttputils.cache.CacheManager;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * @author : sunyao
 * @ClassName : HttpPublisher
 * @Description : Http事件发布者 使用OKHttp作为网络请求
 * @date : 2015-9-2 上午10:14:07
 */
@SuppressLint("NewApi")
public class HttpPublisher extends Publisher {
    /**
     * 访问网络失败
     */
//	public static final int NETWORK_ERROR = 0X1101;
    public static final String NETWORK_ERROR = "network error";
    /**
     * 数据请求失败
     */
    public static final String DATA_ERROR = "data error";
    /**
     * 调试打印的信息TAG
     */
    private static String TAG = "EtongOkHttpPubliser";
    /**
     * EventBus事件的标示
     */
    private static HttpPublisher instance = null;
    private static Context mContext;

    private String HttpTag = "EtongHttpTag";        // 默认的请求key
    private String CacheTag = "EtongCacheTag";        // 默认的缓存key
    private static final String STATUS = "flag";    // 接口返回的数据格式中，通过状态值判断请求情况
    private static final String MESSAGE = "msg";    // 接口返回的数据格式中，返回的信息

    public static HttpPublisher getInstance() {
        if (instance == null) {
            instance = new HttpPublisher();
        }
        return instance;
    }

    public void initialize(Context context) {
        mContext = context.getApplicationContext();
    }

    // 初始化OkHttp
    public static void init(Context context) {
        mContext = context.getApplicationContext();
        OkHttpUtils.init((Application) context.getApplicationContext());
    }


    /**
     * @param
     * @return
     * @desc (清除网络请求中的缓存)
     * @user sunyao
     * @createtime 2016/9/19 - 16:05
     */
    public static boolean clearCache() {
        boolean isClear = false;
        try {
            List<CacheEntity<Object>> all = CacheManager.INSTANCE.getAll();
            if (all!=null && all.size()>0) {
                isClear = CacheManager.INSTANCE.clear();
            }
            L.d("<< isClear: >>", isClear + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isClear;
    }

    /**
     * @desc (根据某个给定的Tag移除掉缓存)
     * @createtime 2016/10/17 - 16:38
     * @author xiaoxue
     */
    public static void cleanCacheByTag(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return;
        }
        try {
            boolean isRemoveTag = CacheManager.INSTANCE.remove(tag);
            L.d("<< isRemoveTag: >>", isRemoveTag + "");
        } catch (Exception e) {
        }

    }

    /**
     * @desc (获取到当前请求的key值)
     * @createtime 2016/10/17 - 17:41
     * @author xiaoxue
     */
    public String getCacheTag() {
        if (!TextUtils.isEmpty(CacheTag) && "EtongCacheTag".equals(CacheTag)) {
            return CacheTag;
        }
        return null;
    }

    /**
     * @param
     * @return
     * @desc (获取到手机中所有缓存)
     * @user sunyao
     * @createtime 2016/9/19 - 16:05
     */
    public void getAllCache() {
        List<CacheEntity<Object>> all = CacheManager.INSTANCE.getAll();
        StringBuilder sb = new StringBuilder();
        sb.append("共" + all.size() + "条缓存：").append("\n\n");
        for (int i = 0; i < all.size(); i++) {
            CacheEntity<Object> cacheEntity = all.get(i);
            sb.append("第" + (i + 1) + "条缓存：").append("\n").append(cacheEntity).append("\n\n");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("所有缓存显示").setMessage(sb.toString()).show();
    }

    public OkHttpUtils getOkHttpUtils() {
        return OkHttpUtils.getInstance();
    }

    // 检查网络的状态
    private boolean checkNetworkState() {
        ConnectivityManager connectivity = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        // TODO : 需要添加事件通知
//		Toast.makeText(mContext, "网络异常,请检查网络设置!", Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * @param
     * @return
     * @desc (发送请求)
     * @user sunyao
     * @createtime 2016/9/18 - 16:33
     */
    public void sendRequest(final HttpMethod okHttpMethod, final String tag) {
        if (okHttpMethod == null) {
            return;
        }
//		this.mOkHttpMethod = okHttpMethod;
        this.HttpTag = tag;
        this.CacheTag = getHttpTagFromMD5(okHttpMethod);

        /*Get 请求方法*/
        if (HttpMethodWay.GET.equals(okHttpMethod.getWay())) {
            useGet(okHttpMethod, tag);
        } else if (HttpMethodWay.POST.equals(okHttpMethod.getWay())) {
            usePost(okHttpMethod, tag);
        }
    }

    /**
     * @param
     * @return
     * @desc (获取到唯一的cacheKey)
     * @user sunyao
     * @createtime 2016/9/26 - 16:46
     */
    public String getHttpTagFromMD5(HttpMethod okHttpMethod) {

        String strHeaders = "";
        String strParams = "";
        String cTag = "";
        String httpUrl = okHttpMethod.getUrl();

        if (httpUrl != null) {
            cTag += httpUrl;
        }
        if (okHttpMethod.getHttpHeaders() != null) {
            strHeaders = okHttpMethod.getHttpHeaders().toString();
            cTag += strHeaders;
        }
        if (okHttpMethod.getHttpParams() != null) {
            strParams = okHttpMethod.getHttpParams().toString();
            cTag += strParams;
        }
        if (!TextUtils.isEmpty(cTag)) {
            L.d("----->cTag", cTag);
            L.d("MD5------>", MD5Utils.MD5(cTag));
            return MD5Utils.MD5(cTag);
        }
        return "EtongCacheTag";
    }

    /**
     * 使用get的方法请求
     */
    public void useGet(final HttpMethod okHttpMethod, final String tag) {
        // 判断网络是否可用
        // 网络不可用时不进行网络连接
        if (!checkNetworkState() && !okHttpMethod.isSetCache()) {
            JSONObject data = new JSONObject();
            data.put(STATUS, NETWORK_ERROR);
            data.put(MESSAGE, "访问失败");
            okHttpMethod.put(data);
            getEventBus().post(okHttpMethod, tag);// 添加访问失败时的异常
            return;
        }
        if (okHttpMethod.isSetCache() == false) {
            useGetNoCache(okHttpMethod, tag);
        } else if (okHttpMethod.isSetCache() == true) {
            useGetHasCache(okHttpMethod, tag);
        }
    }

    /**
     * 使用post的方法请求
     */
    public void usePost(final HttpMethod okHttpMethod, final String tag) {
        // 判断网络是否可用
        // 网络不可用时不进行网络连接  并且在没有设置缓存的时候调用
        if (!checkNetworkState() && !okHttpMethod.isSetCache()) {
            JSONObject data = new JSONObject();
            data.put(STATUS, NETWORK_ERROR);
            data.put(MESSAGE, "访问失败");
            okHttpMethod.put(data);
            getEventBus().post(okHttpMethod, tag);// 添加访问失败时的异常
            return;
        }
        if (okHttpMethod.isSetCache() == false) {
            usePostNoCache(okHttpMethod, tag);
        } else {
            usePostHasCache(okHttpMethod, tag);
        }
    }

    /**
     * 使用get方法没有缓存的方式
     */
    private void useGetNoCache(final HttpMethod okHttpMethod, final String tag) {
        okHttpMethod.setCacheTimeLive(0);     // 设置缓存超时时间为0
        useGetHasCache(okHttpMethod, tag);
    }

    private void useGetHasCache(final HttpMethod okHttpMethod, final String tag) {

        if (okHttpMethod.isSetCache() == true) {
            okHttpMethod.setCacheTimeLive(okHttpMethod.getCacheTimeLive());
        }
        try {
            OkHttpUtils.get(okHttpMethod.getUrl())
                    .headers(okHttpMethod.getHttpHeaders())
                    .params(okHttpMethod.getHttpParams())
                    .cacheKey(CacheTag)            //
                    .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                    .cacheTime(okHttpMethod.getCacheTimeLive() * 1000)
                    .execute(new StringCallback() {
                        @Override
                        public void onCacheSuccess(String s, Call call) {
                            String msg = JSON.toJSONString(okHttpMethod.getHttpParams());
                            // 打印参数
                            L.json(msg);
                            // 进行消息通知
                            L.json(s);
                            JSONObject obj = null;
                            try {
                                obj = JSONObject.parseObject(s);
                            } catch (Exception e) {
                            }
                            getEventBus().post(okHttpMethod.put(obj), tag);
                            return;
                        }

                        @Override
                        public void onSuccess(String s, Call call, okhttp3.Response response) {
                            String msg = JSON.toJSONString(okHttpMethod.getHttpParams());
                            // 打印参数
                            L.json(msg);
                            // 进行消息通知
                            L.json(s);
                            try {
                                JSONObject obj = null;
                                try {
                                    obj = JSONObject.parseObject(s);
                                } catch (Exception e) {
                                }
                                getEventBus().post(okHttpMethod.put(obj), tag);
                            } catch (JSONException e) {
                                L.e(TAG + "json解析出错");
                            }
                        }

                        @Override
                        public void onError(Call call, okhttp3.Response response, Exception e) {
                            L.e(TAG, okHttpMethod.toJSONString() + e);
                            JSONObject data = new JSONObject();
                            data.put(STATUS, DATA_ERROR);
                            data.put(MESSAGE, "数据请求失败!");
                            okHttpMethod.put(data);
                            getEventBus().post(okHttpMethod, tag);// 添加访问失败时的异常
                        }

                    });
        } catch (Exception e) {

        }
    }

    /**
     * 使用Post方法没有缓存的方式
     */
    private void usePostNoCache(final HttpMethod okHttpMethod, final String tag) {
        okHttpMethod.setCacheTimeLive(0);     // 设置缓存超时时间为0
        usePostHasCache(okHttpMethod, tag);
    }

    /**
     * 使用Post方法 + 缓存的方式
     */
    private void usePostHasCache(final HttpMethod okHttpMethod, final String tag) {
        if (okHttpMethod.isSetCache() == true) {
            okHttpMethod.setCacheTimeLive(okHttpMethod.getCacheTimeLive());
        }
        try {
            OkHttpUtils.post(okHttpMethod.getUrl())
                    .headers(okHttpMethod.getHttpHeaders())
                    .params(okHttpMethod.getHttpParams())
                    .cacheKey(CacheTag)
                    .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                    .cacheTime(okHttpMethod.getCacheTimeLive() * 1000)
                    .execute(new StringCallback() {
                        @Override
                        public void onCacheSuccess(String s, Call call) {
                            String msg = JSON.toJSONString(okHttpMethod.getHttpParams());
                            // 打印参数
                            L.json(msg);
                            // 进行消息通知
                            L.json(s);
                            JSONObject obj = null;
                            try {
                                obj = JSONObject.parseObject(s);
                            } catch (Exception e) {
                            }
                            getEventBus().post(okHttpMethod.put(obj), tag);
                            return;
                        }

                        @Override
                        public void onSuccess(String s, Call call, okhttp3.Response response) {
                            String msg = JSON.toJSONString(okHttpMethod.getHttpParams());
                            // 打印参数
                            L.json(msg);
                            // 进行消息通知
                            L.json(s);
                            JSONObject obj = null;
                            try {
                                obj = JSONObject.parseObject(s);
                            } catch (Exception e) {
                            }
                            getEventBus().post(okHttpMethod.put(obj), tag);
                        }

                        @Override
                        public void onError(Call call, okhttp3.Response response, Exception e) {
                            // 失败的回调，将错误信息发送
                            L.e(TAG, okHttpMethod.toJSONString() + e);
                            JSONObject data = new JSONObject();
                            data.put(STATUS, DATA_ERROR);
                            data.put(MESSAGE, "数据请求失败!");
                            okHttpMethod.put(data);
                            getEventBus().post(okHttpMethod, tag);// 添加访问失败时的异常
                        }

                    });
        } catch (Exception e) {

        }
    }
}
