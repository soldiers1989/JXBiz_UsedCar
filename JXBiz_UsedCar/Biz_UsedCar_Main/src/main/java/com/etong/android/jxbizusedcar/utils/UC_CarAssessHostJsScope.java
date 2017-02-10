package com.etong.android.jxbizusedcar.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.webkit.WebView;

import com.etong.android.frame.safewebview.JsCallback;
import com.etong.android.frame.safewebview.TaskExecutor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @desc HostJsScope中需要被JS调用的函数，必须定义成public static，且必须包含WebView这个参数
 * @createtime 2016/9/12 - 17:14
 * @Created by xiaoxue.
 */
public class UC_CarAssessHostJsScope {

    /**
     * @Title        : buyCar
     * @Description  : TODO(限时购  询底价)
     * @params
     *     @param webView
     *     @param carsetId		车系ID
     *     @param huodongId    	活动ID
     * @return
     *     void    返回类型
     */
  /*  public static void buyCar(WebView webView, int carsetId, int huodongId) {
        Context context = webView.getContext();
        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
        Intent intent = new Intent();
        VechileSeries vechileSeries = new VechileSeries();
        vechileSeries.setId(carsetId);
        intent.setClass(activity, VechileStyleActivity.class);
        intent.putExtra("vechileSeries", vechileSeries);
        intent.putExtra("huodong_id", huodongId);
        activity.startActivity(intent);
    }*/

    /**
     * @Title        : buyCar
     * @Description  : TODO(底价购车)
     * @params
     *     @param webView
     * @return
     *     void    返回类型
     */
   /* public static void buyCar(WebView webView) {
        Context context = webView.getContext();
        Activity activity = null;
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
        Intent intent = new Intent();
        intent.setClass(activity, VechileMainActivity.class);
        activity.startActivity(intent);
    }*/

    /**
     * 系统弹出提示框
     *
     * @param webView
     *            浏览器
     * @param message
     *            提示信息
     */
    public static void alert(WebView webView, String message) {
        // 构建一个Builder来显示网页中的alert对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(
                webView.getContext());
        builder.setTitle("来自网页的消息");
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok,
                new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }

    public static void alert(WebView webView, int msg) {
        alert(webView, String.valueOf(msg));
    }

    public static void alert(WebView webView, boolean msg) {
        alert(webView, String.valueOf(msg));
    }

    /**
     * 获取设备IMSI
     *
     * @param webView
     *            浏览器
     * @return 设备IMSI
     * */
    public static String getIMSI(WebView webView) {
        return ((TelephonyManager) webView.getContext().getSystemService(
                Context.TELEPHONY_SERVICE)).getSubscriberId();
    }

    /**
     * 获取用户系统版本大小
     *
     * @param webView
     *            浏览器
     * @return 安卓SDK版本
     * */
    public static int getOsSdk(WebView webView) {
        return Build.VERSION.SDK_INT;
    }

    // ---------------- 界面切换类 ------------------

    /**
     * 结束当前窗口
     *
     * @param view
     *            浏览器
     * */
    public static void goBack(WebView view) {
        if (view.getContext() instanceof Activity) {
            ((Activity) view.getContext()).finish();
        }
    }

    /**
     * 传入Json对象
     *
     * @param view
     *            浏览器
     * @param jo
     *            传入的JSON对象
     * @return 返回对象的第一个键值对
     * */
    public static String passJson2Java(WebView view, JSONObject jo) {
        @SuppressWarnings("rawtypes")
        Iterator iterator = jo.keys();
        String res = null;
        if (iterator.hasNext()) {
            try {
                String keyW = (String) iterator.next();
                res = keyW + ": " + jo.getString(keyW);
            } catch (JSONException je) {

            }
        }
        return res;
    }

    /**
     * 将传入Json对象直接返回
     *
     * @param view
     *            浏览器
     * @param jo
     *            传入的JSON对象
     * @return 返回对象的第一个键值对
     * */
    public static JSONObject retBackPassJson(WebView view, JSONObject jo) {
        return jo;
    }

    public static int overloadMethod(WebView view, int val) {
        return val;
    }

    public static String overloadMethod(WebView view, String val) {
        return val;
    }

    public static class RetJavaObj {
        public int intField;
        public String strField;
        public boolean boolField;
    }

    public static List<RetJavaObj> retJavaObject(WebView view) {
        RetJavaObj obj = new RetJavaObj();
        obj.intField = 1;
        obj.strField = "mine str";
        obj.boolField = true;
        List<RetJavaObj> rets = new ArrayList<RetJavaObj>();
        rets.add(obj);
        return rets;
    }

    public static void delayJsCallBack(WebView view, int ms,
                                       final String backMsg, final JsCallback jsCallback) {
        TaskExecutor.scheduleTaskOnUiThread(ms * 1000, new Runnable() {
            @Override
            public void run() {
                try {
                    jsCallback.apply(backMsg);
                } catch (JsCallback.JsCallbackException je) {
                    je.printStackTrace();
                }
            }
        });
    }

    public static long passLongType(WebView view, long i) {
        return i;
    }
}
