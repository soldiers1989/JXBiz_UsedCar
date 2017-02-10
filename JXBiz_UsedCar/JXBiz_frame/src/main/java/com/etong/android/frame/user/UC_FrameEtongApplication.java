package com.etong.android.frame.user;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.baidu.mapapi.SDKInitializer;
import com.etong.android.frame.BaseApplication;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.frame.request_init.UC_Constant;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.request_init.UC_HttpUrl;
import com.etong.android.frame.utils.ConfigInfo;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.MD5Utils;
import com.etong.android.frame.widget.Etong_SqlLiteDao;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.pgyersdk.crash.PgyCrashManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class UC_FrameEtongApplication extends BaseApplication {
    protected static final String TAG = "EtongApplication";
    protected static UC_FrameEtongApplication application;
    protected UC_User mUC_User;
    protected ConfigInfo mConfigInfo;
    protected UC_UserProvider mFrameUserProvider;
    public static final String USER_SHARED = "userShared";
    public static final String COLLECT_SHARED = "collectShared";
    public static final String HISTORY_SHARED = "historyShared";
    public static final String IS_LOCATION = "is location";
    public String time;//请求时间
    public Etong_SqlLiteDao mSqliteDao;  //数据库
    private String ALL_DATA = "all_car";  //所有车标记
    private String HIS_DATA = "his_car";  //历史记录标记
    private static int outTime = 30;
    private UC_CollectOrScannedBean mHistoryBean = new UC_CollectOrScannedBean();
    private UC_CollectOrScannedBean mCollectBean= new UC_CollectOrScannedBean();


    static public UC_FrameEtongApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        setDebugMode(true);
        super.onCreate();

        application = this;
        UC_UserProvider.initalize(SharedPublisher.getInstance(), HttpPublisher.getInstance());
        /**
         * 初始化服务器地址 Constants.LOCAL = 0 Constants.STAG = 1 Constants.PROD = 2
         * Constants.DEMO = 3
         */
        UC_Constant.initService(UC_Constant.STAG);

        // 蒲公英的错误日志获取
        PgyCrashManager.register(this);

        // 初始化网络请求
        HttpPublisher.init(this);
        mSqliteDao = Etong_SqlLiteDao.getInstance(this, ALL_DATA, HIS_DATA);

        // 初始化Logger日志类
        Logger.init(L.TAG)                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
//              .hideThreadInfo()               // default shown
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                .methodOffset(2);                // default 0
//              .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter
        initData();

        // 极光推送设置
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        // 初始化百度地图SDK
        SDKInitializer.initialize(getApplicationContext());
    }

    /**
     * @return User 返回类型
     * @throws
     * @Title : getUserInfo
     * @Description : 获取登录用户信息
     * @params
     */
    @SuppressLint("NewApi")
    public UC_User getUserInfo() {
        if (null == mUC_User) {
            // 如果没有用户信息则从缓存中读取
            String userShared = mSharedPublisher.getString(USER_SHARED);
            if (null != userShared && !userShared.isEmpty())
                mUC_User = JSON.parseObject(userShared, UC_User.class);
        }
        return mUC_User;
    }

    public void setUserInfo(UC_User mUC_User) {
        this.mUC_User = mUC_User;
        mSharedPublisher.put(USER_SHARED, JSON.toJSONString(mUC_User));
    }


    /**
     * 获取到用户提供类
     *
     * @return
     */
    public UC_UserProvider getmFrameUserProvider() {
        return mFrameUserProvider;
    }

    public void setmFrameUserProvider(UC_UserProvider mFrameUserProvider) {
        this.mFrameUserProvider = mFrameUserProvider;
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
     * @return boolean 返回类型
     * @throws
     * @Title : isLogin
     * @Description : 判断用户是否登录
     * @params
     */
    public boolean isLogin() {
        return (null != getUserInfo());
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
        } catch (NameNotFoundException e) {
            L.e(TAG, "getPackageInfo NameNotFoundException" + e);
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
        uniqueId = MD5Utils.MD5(uniqueId);
        return uniqueId;
    }

    /**
     * 判断activity是否引导过
     *
     * @param context
     * @return 是否已经引导过 true引导过了 false未引导
     */
    public boolean activityIsGuided(Context context, String phone) {
        if (context == null || phone == null || "".equalsIgnoreCase(phone))
            return false;
        String[] classNames = SharedPublisher.getInstance()
                .getString("guide_activity", "").split("\\|");// 取得所有类名
        for (String string : classNames) {
            if (phone.equalsIgnoreCase(string)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param context
     * @param phone   设定文件
     * @return void 返回类型
     * @Title : setIsGuided
     * @Description : 设置该activity被引导过了, 将类名已 |a|b|c这种形式保存为value
     * @params
     */
    public void setIsGuided(Context context, String phone) {
        if (context == null || phone == null || "".equalsIgnoreCase(phone))
            return;
        String classNames = SharedPublisher.getInstance().getString(
                "guide_activity", "");
        StringBuilder sb = new StringBuilder(classNames).append("|").append(
                phone);// 添加值
        SharedPublisher.getInstance().put("guide_activity", sb.toString());
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


    /**
     * @desc 获取请求所有品牌车系的时间
     * @createtime 2016/10/31 - 8:53
     * @author xiaoxue
     */
    @SuppressLint("NewApi")
    public String getTime() {
        return mSharedPublisher.getString(TIME);
    }

    public void setTime(String time) {
        this.time = time;
        mSharedPublisher.put(TIME, time);
    }

    public Date StringToDate(String args) throws ParseException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sim.parse(args);
    }


    private void initData() {
        try {
            if (null == this.getTime() || "".equals(this.getTime())) {
                getAllCar();
            } else if (getDistanceDays(this.getTime(), new Date().toString()) > outTime) {
                getAllCar();
                L.d("请求", outTime + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllCar() {
        Map map = new HashMap();
        HttpMethod method = new HttpMethod(UC_HttpUrl.HTTP_SEARCH_CAR_URL, map);
        mHttpPublisher.sendRequest(method, UC_HttpTag.ALL_CAR_BRAND_TYPE);

    }

    /**
     * 两个时间之间相差距离多少天
     *
     * @param str1 时间参数 1：
     * @param str2 时间参数 2：
     * @return 相差天数
     */
    public static long getDistanceDays(String str1, String str2) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * @desc
     * @createtime 2016/11/7 0007-18:35
     * @author wukefan
     */
    public UC_CollectOrScannedBean getCollectCache() {
        String collectShared = mSharedPublisher.getString(COLLECT_SHARED);
        if (null != collectShared && !collectShared.isEmpty())
            mCollectBean = JSON.parseObject(collectShared, UC_CollectOrScannedBean.class);
        return mCollectBean;
    }

    public void setCollectCache(UC_CollectOrScannedBean mCollectBean) {
        this.mCollectBean = mCollectBean;
        mSharedPublisher.put(COLLECT_SHARED, JSON.toJSONString(mCollectBean));
    }

    /**
     * @desc
     * @createtime 2016/11/7 0007-18:35
     * @author wukefan
     */
    public UC_CollectOrScannedBean getHistoryCache() {
        String historyShared = mSharedPublisher.getString(HISTORY_SHARED);
        if (null != historyShared && !historyShared.isEmpty())
            mHistoryBean = JSON.parseObject(historyShared, UC_CollectOrScannedBean.class);
        return mHistoryBean;
    }

    public void setHistoryCache(UC_CollectOrScannedBean mHistoryBean) {
        this.mHistoryBean = mHistoryBean;
        mSharedPublisher.put(HISTORY_SHARED, JSON.toJSONString(mHistoryBean));
    }

    public void setJPushAlias() {
        String alias = "";

        UC_User user = getUserInfo();
        if (user != null && !TextUtils.isEmpty(user.getF_userid())) {
            alias = user.getF_userid();
        } else {
            alias = getUniqueId();
        }

        JPushInterface.setAlias(this, alias, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if (0 == i) {
                    L.i("设置别名成功! -- alias : " + s);
                } else {
                    L.e("设置别名失败---失败状态码： " + i);
                }
            }
        });
    }


    //是否定位
    public void setIsLocation(boolean isLocation) {
        mSharedPublisher.put(IS_LOCATION, isLocation + "");
    }

    public boolean getIsLocation() {
        String isLoactionString = mSharedPublisher.getString(IS_LOCATION);
        boolean isLocation = false;
        if (isLoactionString.equals("true")) {
            isLocation = true;
        } else if (isLoactionString.equals("false")) {
            isLocation = false;
        }
        return isLocation;
    }
}
