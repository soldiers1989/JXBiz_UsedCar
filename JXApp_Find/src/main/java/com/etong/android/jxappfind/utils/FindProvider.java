package com.etong.android.jxappfind.utils;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpMethodWay;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.request_init.FrameHttpUri;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/31.
 */
public class FindProvider {

    private HttpPublisher mHttpPublisher;
    private static String TAG = "UserProvider";
    private static FindProvider instance;

    public static final String USER_SUCCEED = "PT_ERROR_SUCCESS";
    public static final String USER_EXISTS = "PT_ERROR_REG_REDUPLICATED";
    public static final String USER_DUPLICATE = "PT_ERROR_RECORD_REDUPLICATED";
    private HttpMethod weatherSuggestionMethod;
    private HttpMethod weatherDailyMethod;
    private HttpMethod oilPriceMethod;

    private FindProvider() {

    }

    public void initialize(HttpPublisher httpPublisher) {
        this.mHttpPublisher = httpPublisher;
    }

    public static FindProvider getInstance() {
        if (null == instance) {
            instance = new FindProvider();
        }
        return instance;
    }


      /*
     * @Title
     * @Description  : 猜你喜欢
     * @params
     *     @param
     * @return
     *     void    返回类型
     * @throws
     * */

    public void getFindRecommend() {
        Map map = new HashMap<>();

        HttpMethod method = new HttpMethod(FrameHttpUri.Find_Recommend, null);
        mHttpPublisher.sendRequest(method, FrameHttpTag.FIND_RECOMMEND);
    }



     /*
     * @Title
     * @Description  : 促销车  限时购
     * @params
     *     @param
     * @return
     *     void    返回类型
     * @throws
     * */

    public void getFindActivityItemList() {
        Map map = new HashMap<>();

        HttpMethod method = new HttpMethod(FrameHttpUri.Find_ActivityItemList, null);
        mHttpPublisher.sendRequest(method, FrameHttpTag.FIND_ACTIVITY_ITEM_LIST);
    }

    /**
     * 获取天气预报的逐日预报
     */
    public void getWeatherSuggestion(String location, int days) {
        Map<String, String> headMap = new HashMap<String, String>();
        headMap.put("apikey", "8b940eb30b6ddd5584e97cd6f0c08a92");
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("location", location);
        paramsMap.put("days", days + "");
        weatherSuggestionMethod = new HttpMethod(FrameHttpUri.WeatherSuggestion, paramsMap, headMap);
        weatherSuggestionMethod.setWay(HttpMethodWay.GET);
        weatherSuggestionMethod.setSetCache(true);
        weatherSuggestionMethod.setCacheTimeLive(1 * 60 * 60 * 12);//设置缓存12个小时
        mHttpPublisher.sendRequest(weatherSuggestionMethod, FrameHttpTag.WEATHER_SUGGESTION);

    }


    /**
     * 获取天气预报的生活指数
     */
    public void getWeatherDaily(String location) {
        Map<String, String> headMap = new HashMap<String, String>();

        headMap.put("apikey", "8b940eb30b6ddd5584e97cd6f0c08a92");
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("location", location);
        weatherDailyMethod = new HttpMethod(FrameHttpUri.WeatherDaily, paramsMap, headMap);
        weatherDailyMethod.setWay(HttpMethodWay.GET);
        weatherDailyMethod.setSetCache(true);
        weatherDailyMethod.setCacheTimeLive(1 * 60 * 60 * 12);//设置缓存12个小时
        mHttpPublisher.sendRequest(weatherDailyMethod, FrameHttpTag.WEATHER_DAILY);

    }

    /**
     * @Title        : getOilpPrice
     * @Description  :获取今日油价
     * @params
     *     @param
     * @return
     *     void    返回类型
     * @throws
     */
    public void getOilPrice(String prov) {
        Map headMap = new HashMap<String,String>();
        headMap.put("apikey","8b940eb30b6ddd5584e97cd6f0c08a92");
        Map paramsMap = new HashMap<String,String>();
        paramsMap.put("prov",prov);
        oilPriceMethod = new HttpMethod(FrameHttpUri.OIL_PRICE, paramsMap, headMap);
        oilPriceMethod.setWay(HttpMethodWay.GET);
        oilPriceMethod.setSetCache(true);
        oilPriceMethod.setCacheTimeLive(1 * 60 * 60 * 24 * 1);//设置缓存1天
        mHttpPublisher.sendRequest(oilPriceMethod, FrameHttpTag.OIL_PRICE);
    }

    /**
     * @desc (获取到天气预报的HttpMethod)
     * @createtime 2016/10/17 - 18:08
     * @author xiaoxue
     */
    public HttpMethod getWeatherSuggestionMethod() {
        if (weatherSuggestionMethod != null) {
            return weatherSuggestionMethod;
        }
        return null;
    }

    /**
     * @desc (洗车指数的Method)
     * @createtime 2016/10/17 - 18:08
     * @author xiaoxue
     */
    public HttpMethod getweatherDailyMethod() {
        if (weatherDailyMethod != null) {
            return weatherDailyMethod;
        }
        return null;
    }

    /**
     * @desc (油价)
     * @createtime 2016/10/17 - 18:08
     * @author xiaoxue
     */
    public HttpMethod getOilPriceMethod() {
        if (oilPriceMethod != null) {
            return oilPriceMethod;
        }
        return null;
    }
}
