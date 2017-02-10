package com.etong.android.frame.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.HttpEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MutipartRequest extends StringRequest {

	private MutipartParams params = null;
	private HttpEntity httpEntity = null;
	private Map<String, String> headers = new HashMap<String, String>(1);

	public MutipartRequest(int method, String url, Listener<String> listener,
			ErrorListener errorListener, MutipartParams params) {
		super(method, url, listener, errorListener);
		this.params = params;
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (params != null) {
			httpEntity = params.getEntity();
			try {
				httpEntity.writeTo(baos);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return baos.toByteArray();
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		String parsed;
		try {
			parsed = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
		} catch (UnsupportedEncodingException e) {
			parsed = new String(response.data);
		}
		return Response.success(parsed,
				HttpHeaderParser.parseCacheHeaders(response));
	}

	public void setCookie(String cookie) {
		headers.put("Cookie", cookie);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers;
	}

	@Override
	public String getBodyContentType() {
		return httpEntity.getContentType().getValue();
	}
}

