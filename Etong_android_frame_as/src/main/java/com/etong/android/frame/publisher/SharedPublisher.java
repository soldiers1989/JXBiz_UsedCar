package com.etong.android.frame.publisher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.etong.android.frame.BaseApplication;

/**
 * @ClassName : SharedPublisher
 * @Description : 共享数据发布者
 * @author : yuanjie
 * @date : 2015-9-2 上午10:14:51
 * 
 */
public class SharedPublisher extends Publisher {
	static private SharedPublisher instance;

	private final String PREFS_NAME = BaseApplication.getApplication().getPackageName();
	private SharedPreferences mPreferences = null;

	public class Shared {
		String key;
		Object val;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public Object getVal() {
			return val;
		}

		public void setVal(Object val) {
			this.val = val;
		}

		Shared(String key, Object val) {
			this.key = key;
			this.val = val;
		}
	}

	private SharedPublisher() {
	}

	static public SharedPublisher getInstance() {
		if (null == instance) {
			instance = new SharedPublisher();
		}
		return instance;
	}

	public void initialize(Context context) {
		mPreferences = context.getSharedPreferences(PREFS_NAME,
				Context.MODE_PRIVATE);
	}

	@SuppressLint("NewApi")
	public void put(String key, Object value, String tag) {
		Editor editor = mPreferences.edit();
		if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		} else if (value instanceof Long) {
			editor.putLong(key, (Long) value);
		} else if (value instanceof Float) {
			editor.putFloat(key, (Long) value);
		}

		if (null != tag && !tag.isEmpty())
			getEventBus().post(new Shared(key, value), tag);

		editor.commit();
	}

	/**
	 * @desc (清空缓存中的数据)
	 * @user sunyao
	 * @createtime 2016/12/5 - 18:03
	 * @param
	 * @return
	 */
	public void clearAll() {
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * @desc (根据指定的key来删除缓存)
	 * @user sunyao
	 * @createtime 2016/12/5 - 18:04
	 * @param
	 * @return
	 */
	public void clearByTag(String tag) {
		if (TextUtils.isEmpty(tag)) {
			return;
		}
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.remove(tag);
		editor.commit();
	}

	public void put(String key, Object value) {
		this.put(key, value, null);
	}

	public String getString(String key, String def) {
		return mPreferences.getString(key, def);
	}

	public String getString(String key) {
		return mPreferences.getString(key, "");
	}

	public boolean getBoolean(String key) {
		return mPreferences.getBoolean(key, false);
	}

	public int getInteger(String key) {
		return mPreferences.getInt(key, 0);
	}

	public long getLong(String key) {
		return mPreferences.getLong(key, 0L);
	}

	public float getFloat(String key) {
		return mPreferences.getFloat(key, 0f);
	}

}