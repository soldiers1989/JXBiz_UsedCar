package com.etong.android.jxappmore.content;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.alibaba.fastjson.JSON;
import com.etong.android.frame.BaseApplication;
import com.etong.android.frame.common.ImageProvider;
import com.etong.android.frame.utils.ConfigInfo;
import com.etong.android.frame.utils.L;
import com.etong.android.jxappmore.user.MoreUserInfo;

import java.util.UUID;

/**
 * Created by Ellison.Sun on 2016/8/8.
 */
public class MoreEtongApplication extends BaseApplication {
    protected static final String TAG = "MessageEtongApplication";
    protected static MoreEtongApplication application;
    protected MoreUserInfo mUserInfo;
    protected ConfigInfo mConfigInfo;
    protected ImageProvider mImageProvider;

    static public MoreEtongApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        setDebugMode(true);
        super.onCreate();

        application = this;
        // 初始化用户请求
//		mUserProvider = UserProvider.getInstance();
//		mUserProvider.initialize(mHttpPublisher);


        mImageProvider = ImageProvider.getInstance();
        mImageProvider.initialize(getApplicationContext());
    }

    /**
     * 获取登录用户信息
     *
     * @return
     */
    public MoreUserInfo getUserInfo() {
        if (null == mUserInfo) {
            // 如果没有用户信息则从缓存中读取
            String userShared = mSharedPublisher.getString(USER_SHARED);
            if (null != userShared && !userShared.isEmpty())
                mUserInfo = JSON.parseObject(userShared, MoreUserInfo.class);
        }
        return mUserInfo;
    }

    public void setUserInfo(MoreUserInfo userInfo) {
        this.mUserInfo = userInfo;
        mSharedPublisher.put(USER_SHARED, JSON.toJSONString(userInfo));
    }

    /**
     * @return ConfigInfo 配置信息
     * @throws
     * @Title : getConfigInfo
     * @Description : TODO(获取配置信息)
     * @params
     */
    public ConfigInfo getConfigInfo() {
        if (null == mConfigInfo) {
            String userConfig = mSharedPublisher.getString(USER_CONFIG);
            if (null != userConfig && !userConfig.isEmpty())
                mConfigInfo = JSON.parseObject(userConfig, ConfigInfo.class);
        }
        return mConfigInfo;
    }

    /**
     * @param configInfo 配置信息
     * @return void 返回类型
     * @throws
     * @Title : setConfigInfo
     * @Description : TODO(设置配置信息)
     * @params
     */
    public void setConfigInfo(ConfigInfo configInfo) {
        this.mConfigInfo = configInfo;
        mSharedPublisher.put(USER_CONFIG, JSON.toJSONString(configInfo));
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            L.e(TAG, "getPackageInfo NameNotFoundException :" + e);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    public NotificationManager getNotificationManager() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        return mNotificationManager;
    }

    public String getUniqueId() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext()
                .getSystemService(Context.TELEPHONY_SERVICE);

        @SuppressWarnings("unused")
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = ""
                + android.provider.Settings.Secure.getString(
                getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用(其实还是只能判断网络是否连接成功)
                    return true;
                }
            }
        }
        return false;
    }

}
