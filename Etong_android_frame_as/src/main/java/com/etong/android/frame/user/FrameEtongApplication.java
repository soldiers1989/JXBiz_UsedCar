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
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.publisher.SharedPublisher;
import com.etong.android.frame.request_init.FrameConstant;
import com.etong.android.frame.utils.ConfigInfo;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.utils.MD5Utils;
import com.etong.android.frame.utils.NetUtil;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.pgyersdk.crash.PgyCrashManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class FrameEtongApplication extends BaseApplication {
    protected static final String TAG = "EtongApplication";
    protected static FrameEtongApplication application;
    protected FrameUserInfo mFrameUserInfo;
    protected ConfigInfo mConfigInfo;
    protected FrameUserProvider mFrameUserProvider;
    public static final String USER_SHARED = "userShared";
    public static final String MESSAGE_SHARED = "messageShared";
    public static final String CAR_SEARCH_SHARED = "car search Shared";
    public static final String USED_CAR_SEARCH_SHARED = "used car search Shared";
    public static final String IS_SHOW_WEATHER = "is show weather";
    public static final String IS_SHOW_OIL_PRICE = "is show oil price";
    public static final String IS_LOCATION = "is location";

    public static final String USED_CAR_COLLECT_SHARED = "usedcar collectShared";
    public static final String NEW_CAR_COLLECT_SHARED = "newcar collectShared";

    public static int mNetWorkState;
    /**
     * 还款信息保存到缓存中
     */
    public static final String REPAYMENT_REMIND_INFO = "repayment remind info";
    private UsedAndNewCollectCar mUsedCarCollectBean = new UsedAndNewCollectCar();
    private UsedAndNewCollectCar mNewCarCollectBean = new UsedAndNewCollectCar();

    static public FrameEtongApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        setDebugMode(true);
        super.onCreate();

        application = this;
        // 初始化用户请求
        mFrameUserProvider = FrameUserProvider.getInstance();
        mFrameUserProvider.initialize(mHttpPublisher);
        /**
         * 初始化服务器地址 Constants.LOCAL = 0 Constants.STAG = 1 Constants.PROD = 2
         * Constants.DEMO = 3
         */
        FrameConstant.initService(FrameConstant.STAG);

        // 蒲公英错误日志捕获
		PgyCrashManager.register(this);
        // 初始化网络请求
        HttpPublisher.init(this);
        // 初始化百度地图SDK
        SDKInitializer.initialize(getApplicationContext());

        // 极光推送设置
        JPushInterface.setDebugMode(false);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush

        // 初始化Logger日志类
        Logger.init(L.TAG)                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
//              .hideThreadInfo()               // default shown
                .logLevel(LogLevel.NONE)        // default LogLevel.FULL
                .methodOffset(2);                // default 0
//              .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter

        mNetWorkState = NetUtil.getNetworkState(this);
    }

    /**
     * @return User 返回类型
     * @throws
     * @Title : getUserInfo
     * @Description : 获取登录用户信息
     * @params
     */
    @SuppressLint("NewApi")
    public FrameUserInfo getUserInfo() {
        if (null == mFrameUserInfo) {
            // 如果没有用户信息则从缓存中读取
            String userShared = mSharedPublisher.getString(USER_SHARED);
            if (null != userShared && !userShared.isEmpty())
                mFrameUserInfo = JSON.parseObject(userShared, FrameUserInfo.class);
        }
        return mFrameUserInfo;
    }

    public void setUserInfo(FrameUserInfo frameUserInfo) {
        this.mFrameUserInfo = frameUserInfo;
        mSharedPublisher.put(USER_SHARED, JSON.toJSONString(frameUserInfo));
    }

    /**
     * @return
     */
    public FrameUserInfo getmFrameUserInfo() {
        if (mFrameUserInfo == null) {
            mFrameUserInfo = new FrameUserInfo();
        }
        return mFrameUserInfo;
    }

    public void setmFrameUserInfo(FrameUserInfo mFrameUserInfo) {
        this.mFrameUserInfo = mFrameUserInfo;
    }

    /**
     * 获取到用户提供类
     *
     * @return
     */
    public FrameUserProvider getmFrameUserProvider() {
        return mFrameUserProvider;
    }

    public void setmFrameUserProvider(FrameUserProvider mFrameUserProvider) {
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

        if (!TextUtils.isEmpty(uniqueId)) {
            uniqueId = MD5Utils.MD5(uniqueId);
        }

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


    //得到收藏搜索过的数据
    @SuppressWarnings({"unchecked"})
    public List<String> getSearchHistory() {
        List<String> mList = null;
        String userShared = mSharedPublisher.getString(CAR_SEARCH_SHARED);
        if (null != userShared && !userShared.isEmpty())
//				mUserInfo = JSON.parseObject(userShared, User.class);
            mList = JSON.parseObject(userShared, List.class);
        return mList;
    }

    //将得到的数据添加到缓存
    public void addSearchHistory(String value) {
        List<String> mList = getSearchHistory();
        if (mList == null)
            mList = new ArrayList<String>();
        mList.add(value);
        mSharedPublisher.put(CAR_SEARCH_SHARED, JSON.toJSONString(mList));
    }

    //清除缓存数据
    public void clearSearchHistory() {
        mSharedPublisher.put(CAR_SEARCH_SHARED, "");
    }


    //得到资讯搜索过的数据
    public List<String> getSearchMessageHistory() {
        List<String> mList = null;
        String messageShared = mSharedPublisher.getString(MESSAGE_SHARED);
        if (null != messageShared && !messageShared.isEmpty())
//				mUserInfo = JSON.parseObject(userShared, User.class);
            mList = JSON.parseObject(messageShared, List.class);
        return mList;
    }

    //将得到的数据添加到缓存
    public void addSearchMessageHistory(String value) {
        List<String> mList = getSearchMessageHistory();
        if (mList == null)
            mList = new ArrayList<String>();
        mList.add(value);
        mSharedPublisher.put(MESSAGE_SHARED, JSON.toJSONString(mList));
    }

    //清除缓存数据
    public void clearSearchMessageHistory() {
        mSharedPublisher.put(MESSAGE_SHARED, "");
    }

    //得到二手车世界搜索过的数据
    public List<String> getSearchUsedCarHistory() {
        List<String> mList = null;
        String usedCarShared = mSharedPublisher.getString(USED_CAR_SEARCH_SHARED);
        if (null != usedCarShared && !usedCarShared.isEmpty())
//				mUserInfo = JSON.parseObject(userShared, User.class);
            mList = JSON.parseObject(usedCarShared, List.class);
        return mList;
    }

    //将得到的数据添加到缓存
    public void addSearchUsedCarHistory(String value) {
        List<String> mList = getSearchUsedCarHistory();
        if (mList == null)
            mList = new ArrayList<String>();
        mList.add(value);
        mSharedPublisher.put(USED_CAR_SEARCH_SHARED, JSON.toJSONString(mList));
    }

    //清除缓存数据
    public void clearSearchUsedCarHistory() {
        mSharedPublisher.put(USED_CAR_SEARCH_SHARED, "");
    }


	/*//设置缓存天气  油价
    public void setIsShow(String title,boolean isShow){
		Map tempMap = new HashMap<>();
		tempMap.put(title,isShow);
		showMap = tempMap;
		mSharedPublisher.put(USER_SHARED, JSON.toJSONString(showMap));
	}*/

    //是否显示天气
    public void setIsShowWeather(boolean isShow) {

        mSharedPublisher.put(IS_SHOW_WEATHER, isShow + "");
    }

    public boolean getIsShowWeather() {

        String isShowWeatherString = mSharedPublisher.getString(IS_SHOW_WEATHER);
        boolean isShowWeather = true;
        if (isShowWeatherString.equals("true")) {
            isShowWeather = true;
        } else if (isShowWeatherString.equals("false")) {
            isShowWeather = false;
        }
        return isShowWeather;
    }

    //是否显示油价
    public void setIsShowOilPrice(boolean isShow) {

        mSharedPublisher.put(IS_SHOW_OIL_PRICE, isShow + "");
    }

    public boolean getIsShowOilPrice() {
        String isShowOilPriceString = mSharedPublisher.getString(IS_SHOW_OIL_PRICE);
        boolean isShowOilPrice = true;
        if (isShowOilPriceString.equals("true")) {
            isShowOilPrice = true;
        } else if (isShowOilPriceString.equals("false")) {
            isShowOilPrice = false;
        }
        return isShowOilPrice;
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

    /**
     * @param
     * @return
     * @desc (设置极光推送别名)
     * @user sunyao
     * @createtime 2016/12/5 - 17:02
     */
    public void setJPushAlias() {
        String alias = "";

        FrameUserInfo user = getUserInfo();
        if (user != null && !TextUtils.isEmpty(user.getUserId())) {
            alias = user.getUserId();
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

    /**
     * @param
     * @return
     * @desc (获取到还款信息)
     * @user sunyao
     * @createtime 2016/12/5 - 17:42
     */
    public List<FrameRepayRemindInfo> getRepaymentRemindInfo() {
        List<FrameRepayRemindInfo> mInfoList = new ArrayList<FrameRepayRemindInfo>();
        List<String> mList = null;
        String repayMentInfo = mSharedPublisher.getString(REPAYMENT_REMIND_INFO);
        if (!TextUtils.isEmpty(repayMentInfo)) {
            // 将本地的数据获取到， 并将所有的数据装换成javabean
            mList = JSON.parseObject(repayMentInfo, List.class);
            for (String s : mList) {
                FrameRepayRemindInfo remindInfo = JSON.parseObject(s, FrameRepayRemindInfo.class);
                mInfoList.add(remindInfo);
            }
        }
        return mInfoList;
    }

    /**
     * @param
     * @return
     * @desc (添加还款信息)
     * @user sunyao
     * @createtime 2016/12/5 - 18:00
     */
    public void addRepaymentRemindInfo(FrameRepayRemindInfo value) {
        List<String> mStrList = new ArrayList<String>();
        List<FrameRepayRemindInfo> mList = getRepaymentRemindInfo();
        if (mList == null) {
            mList = new ArrayList<FrameRepayRemindInfo>();
        }
        mList.add(value);
        if (mList != null) {
            // 将每一个javabean转换成String
            for (FrameRepayRemindInfo info : mList) {
                String s = JSON.toJSONString(info);
                mStrList.add(s);
            }
        }
        mSharedPublisher.put(REPAYMENT_REMIND_INFO, JSON.toJSONString(mStrList));
    }

    /**
     * @param
     * @return
     * @desc (删除本地的还款信息)
     * @user sunyao
     * @createtime 2016/12/5 - 18:05
     */
    public void clearRepaymentRemindInfo() {
        List<FrameRepayRemindInfo> mList = getRepaymentRemindInfo();
        if (mList != null) {
            mSharedPublisher.clearByTag(REPAYMENT_REMIND_INFO);
        }
    }


    /**
     * @desc 二手车个数缓存
     * @createtime 2016/11/7 0007-18:35
     * @author wukefan
     */
    public UsedAndNewCollectCar getUsedCarCollectCache() {
        String usedCarCollectShared = mSharedPublisher.getString(USED_CAR_COLLECT_SHARED);
        if (null != usedCarCollectShared && !usedCarCollectShared.isEmpty())
            mUsedCarCollectBean = JSON.parseObject(usedCarCollectShared, UsedAndNewCollectCar.class);
        return mUsedCarCollectBean;
    }

    public void setUsedCarCollectCache(UsedAndNewCollectCar mUsedCarCollectBean) {
        this.mUsedCarCollectBean = mUsedCarCollectBean;
        mSharedPublisher.put(USED_CAR_COLLECT_SHARED, JSON.toJSONString(mUsedCarCollectBean));
    }

    /**
     * @desc 新车个数缓存
     * @createtime 2016/11/7 0007-18:35
     * @author wukefan
     */
    public UsedAndNewCollectCar getNewCarCollectCache() {
        String newCarCollectShared = mSharedPublisher.getString(NEW_CAR_COLLECT_SHARED);
        if (null != newCarCollectShared && !newCarCollectShared.isEmpty())
            mNewCarCollectBean = JSON.parseObject(newCarCollectShared, UsedAndNewCollectCar.class);
        return mNewCarCollectBean;
    }

    public void setNewCarCollectCache(UsedAndNewCollectCar mNewCarCollectBean) {
        this.mNewCarCollectBean = mNewCarCollectBean;
        mSharedPublisher.put(NEW_CAR_COLLECT_SHARED, JSON.toJSONString(mNewCarCollectBean));
    }
}
