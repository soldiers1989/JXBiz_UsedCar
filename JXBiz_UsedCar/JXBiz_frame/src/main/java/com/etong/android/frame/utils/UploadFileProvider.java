package com.etong.android.frame.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.etong.android.frame.publisher.Publisher;
import com.etong.android.frame.request.MutipartParams;
import com.etong.android.frame.request.MutipartRequest;
import com.etong.android.frame.request_init.UC_HttpUrl;

import org.simple.eventbus.EventBus;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UploadFileProvider extends Publisher {
    //	public static String url = "http://113.247.237.98:10002/upload";
    public static String url = UC_HttpUrl.UPLOAD_IMAGE_SERVICER;
    static JSONObject data = new JSONObject();

    public static void uploadFile(Context context, Bitmap bitmap,
                                  final String tag, int compressRate) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressRate, baos);
            InputStream sbs = new ByteArrayInputStream(baos.toByteArray());
            MutipartParams params = new MutipartParams();
            params.put("dir", sbs, System.currentTimeMillis() + ".jpg");
//			params.put("img", sbs, System.currentTimeMillis() + ".jpg");
            baos.flush();
            baos.close();
            sbs.close();
            Response.ErrorListener errListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    data.put("errCode", -1);
                    data.put("errName", "image upload fail");
                    EventBus.getDefault().post(data, tag);
                }
            };
            MutipartRequest mutiRequest = new MutipartRequest(
                    Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String param) {
                    JSONArray jsonArray = JSON.parseObject(param)
                            .getJSONArray("files");
                    if (jsonArray != null && jsonArray.size() > 0) {
                        JSONObject obj = jsonArray.getJSONObject(0);
//							JSONObject obj = JSON.parseObject(param);
//								String url = obj.getString("object");
                        String url = obj.getString("url");
                        if (!TextUtils.isEmpty(url)) {
                            if(url.contains("113.247.237.98")){
                                url = url.replace("113.247.237.98", "222.247.51.114");
                            }
                            data.put("errCode", 0);
                            data.put("errName", "image upload success");
                            data.put("url", url);
                            data.put("fileName", obj.getString("name"));
                            EventBus.getDefault().post(data, tag);
                        } else {
                            data.put("errCode", -1);
                            data.put("errName", "image upload fail");
                            EventBus.getDefault().post(data, tag);
                        }
                    }
                }
            }, errListener, params);
            requestQueue.add(mutiRequest);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            if (context != null) {
                data.put("errCode", -2);
                data.put("errName", "image file error");
                EventBus.getDefault().post(data, tag);
            }
        }
    }

}
