package com.etong.android.jxappusedcar.content;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.UsedAndNewCollectCar;
import com.etong.android.jxappusedcar.BuildConfig;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.testData.UC_CarDetailsData;
import com.etong.android.jxappusedcar.javabean.UC_CollectBean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

import org.robolectric.shadows.ShadowIntent;

/**
 * @desc (二手车详情页测试用例)
 * @createtime 2016/12/26 - 15:58
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class UC_CarDetailActivityTest {

    private Button collect;
    private UC_CarDetailActivity mActivity;
    private Button order;
    private TextView parameter;
    private ImageView photo;
    private ActivityController<UC_CarDetailActivity> controller;
    private ListView carDetailLv;

    /**
     * @desc (初始化操作)
     * @createtime 2016/12/28 - 16:14
     * @author xiaoxue
     */
    @Before
    public void setUp() throws Exception {
        UC_CarDetailActivity.isTestDebug=true;
//        ActivityController<UC_CarDetailActivity> controller = Robolectric.buildActivity(UC_CarDetailActivity.class).create().attach().start();
//        mActivity = controller.get();
//        mActivity = Robolectric.setupActivity(UC_CarDetailActivity.class);//创建activity
        Intent intent = new Intent();
        intent.putExtra(UC_CarDetailActivity.UC_CARDETAIL_ID, "00000230");

        // 创建Activity控制器
        controller = Robolectric.buildActivity(UC_CarDetailActivity.class).withIntent(intent);
        controller.create().start();
        mActivity = controller.get();
        Assert.assertNotNull(mActivity);
        collect = (Button) mActivity.findViewById(R.id.default_appoint_drive);
        order = (Button) mActivity.findViewById(R.id.default_appoint_order);
        parameter = (TextView) mActivity.findViewById(R.id.used_car_details_parameter);
        photo = (ImageView) mActivity.findViewById(R.id.used_car_iv_photo);
        carDetailLv = (ListView) mActivity.findViewById(R.id.uc_car_detail_lv);

        assertNotNull(parameter);
        Assert.assertNotNull(collect);
        Assert.assertNotNull(order);
        assertNotNull(photo);
    }

    //验证intent得到的值
    @Test
    public void getIntent() throws Exception {
        String intent=mActivity.getIntent().getStringExtra(UC_CarDetailActivity.UC_CARDETAIL_ID);
        assertEquals("00000230",intent);
    }


    /**
     * @desc (判断收藏的状态)
     * @createtime 2016/12/28 - 16:12
     * @author xiaoxue
     */
    @Test
    public void testSetCollect() throws Exception {
        mActivity.setCollectState(true);
        Assert.assertEquals("已收藏", collect.getText().toString());

        mActivity.setCollectState(false);
        Assert.assertEquals("收藏", collect.getText().toString());
    }

//.create()  创建对象
//.start();  走了onCreate()方法

    /**
     * @desc (onResume生命周期)
     * @createtime 2016/12/28 - 16:02
     * @author xiaoxue
     */
    @Test
    public void onResume() throws Exception {

        // 调用Activity的performResume方法
        controller.resume();
        Assert.assertEquals("收藏", collect.getText().toString());//没有缓存的时候是收藏
        UsedAndNewCollectCar collectBean = new UsedAndNewCollectCar();
        List<String> carList = new ArrayList<>();
        carList.add("00000230");
        collectBean.setCarList(carList);
        FrameEtongApplication.getApplication().setUsedCarCollectCache(collectBean);
        controller.resume();
        Assert.assertEquals("已收藏", collect.getText().toString());
    }

    /**
     * @desc (点击二手车收藏弹出提示信息是否正确)
     * @createtime 2016/12/28 - 15:57
     * @author xiaoxue
     */
    @Test
    public void toast() throws Exception {
        //设置method的数据
        for (int i = 0; i < 4; i++) {
            int flagValue = i;   //0:成功收藏成功 1：取消收藏  2：没网  3：没服务
            String msgValue = "";
            Map<String, String> params = new HashMap<>();
            params.put("userId", "10000004");
            params.put("dvid", "00000230");
            HttpMethod method = new HttpMethod("http://120.25.98.100:8080/jxapi/appCar/insertCollectData.do", params);
            JSONObject jsonObject = new JSONObject();
            switch (flagValue) {
                case 0:
                    UC_CollectBean bean = new UC_CollectBean();
                    bean.setMsg("收藏成功");
                    bean.setF_collectstatus("1");
                    jsonObject.put("flag", 0);
                    jsonObject.put("data", bean);
                    msgValue = "收藏成功";
                    break;
                case 1:
                    UC_CollectBean cancelBean = new UC_CollectBean();
                    cancelBean.setMsg("取消收藏成功");
                    cancelBean.setF_collectstatus("0");
                    jsonObject.put("flag", 0);
                    jsonObject.put("data", cancelBean);
                    msgValue = "取消收藏成功";
                    break;
                case 2:
                    jsonObject.put("flag", HttpPublisher.NETWORK_ERROR);
                    jsonObject.put("msg", "访问失败");
                    msgValue = "访问失败";
                    break;
                case 3:
                    jsonObject.put("flag", HttpPublisher.DATA_ERROR);
                    jsonObject.put("msg", "数据请求失败");
                    msgValue = "数据请求失败";
                    break;
            }
            Toast toast = ShadowToast.getLatestToast();
            // 判断Toast尚未弹出
            assertNull(toast);
            //调用点击收藏得到结果的处理方法
            mActivity.clickCollection(method.put(jsonObject));

            toast = ShadowToast.getLatestToast();
            // 判断Toast已经弹出
            assertNotNull(toast);

            // 获取Shadow类进行验证
            ShadowToast shadowToast = shadowOf(toast);
            assertEquals(Toast.LENGTH_SHORT, shadowToast.getDuration());
            Assert.assertEquals(msgValue, shadowToast.getTextOfLatestToast());
            ShadowToast.reset();//清除之前弹过的toast

            if (flagValue == 0) {
                Assert.assertEquals("已收藏", collect.getText().toString());
            } else if (flagValue == 1) {
                Assert.assertEquals("收藏", collect.getText().toString());
            }
        }
    }

    /**
     * @desc (点击预约看车的处理)
     * @createtime 2016/12/28 - 16:09
     * @author xiaoxue
     */
    @Test
    public void order() throws Exception {
        //设置method的数据
        for (int i = 0; i < 3; i++) {    //0 成功  1 没网  2 没服务
            int flagValue = i;
            String msgValue = "";
            Map<String, String> params = new HashMap<>();
            params.put("f_cartypeid", "662473");
            params.put("f_cartype", "本田 飞度 2014款 1.5L EXLI CVT领先型");
            params.put("f_phone", "15074821257");
            params.put("f_orderman", "asd");
            HttpMethod method = new HttpMethod("http://120.25.98.100:8080/jxapi/appCar/addBuyReserveForApp.do", params);
            JSONObject jsonObject = new JSONObject();
            switch (flagValue) {
                case 0:
                    jsonObject.put("flag", 0);
                    break;
                case 1:
                    jsonObject.put("flag", HttpPublisher.NETWORK_ERROR);
                    jsonObject.put("msg", "访问失败");
                    msgValue = "访问失败";
                    break;
                case 2:
                    jsonObject.put("flag", HttpPublisher.DATA_ERROR);
                    jsonObject.put("msg", "数据请求失败");
                    msgValue = "数据请求失败";
                    break;

            }
            Toast toast = ShadowToast.getLatestToast();
            // 判断Toast尚未弹出
            assertNull(toast);
            mActivity.getOrderCar(method.put(jsonObject));
            switch (flagValue) {
                case 0:
                    UC_Dialog dialog = (UC_Dialog) ShadowDialog.getLatestDialog();
                    assertNotNull(dialog);
                    break;
                default:
                    toast = ShadowToast.getLatestToast();
                    // 判断Toast已经弹出
                    assertNotNull(toast);

                    // 获取Shadow类进行验证
                    ShadowToast shadowToast = shadowOf(toast);
                    assertEquals(Toast.LENGTH_SHORT, shadowToast.getDuration());
                    Assert.assertEquals(msgValue, shadowToast.getTextOfLatestToast());
                    ShadowToast.reset();//清除之前弹过的toast
                    break;
            }
        }
    }


    /**
     * @desc (点击参数配置跳转)
     * @createtime 2016/12/28 - 18:39
     * @author xiaoxue
     */
    @Test
    public void testIntent() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("dvid", "00000172");
        params.put("userId", "10000004");
        HttpMethod method = new HttpMethod("http://120.25.98.100:8080/jxapi/appCar/queryCarDetail.do", params);
        JSONObject jsonObject = JSON.parseObject(UC_CarDetailsData.jsonString);
        mActivity.getCarDetailData(method.put(jsonObject));

        parameter.performClick();
        // 获取对应的Shadow类
        ShadowActivity shadowActivity = Shadows.shadowOf(mActivity);
        assertNotNull(shadowActivity);
        // 借助Shadow类获取启动下一Activity的Intent
        Intent nextIntent = shadowActivity.getNextStartedActivity();
        assertNotNull(nextIntent);
        ShadowIntent shadowIntent = Shadows.shadowOf(nextIntent);
        Assert.assertEquals(UC_ConfigInfo.class, shadowIntent.getIntentClass());
    }

    /**
     * @desc (点击图片跳转)
     * @createtime 2016/12/28 - 18:46
     * @author xiaoxue
     */
    @Test
    public void clickImage() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("dvid", "00000172");
        params.put("userId", "10000004");
        HttpMethod method = new HttpMethod("http://120.25.98.100:8080/jxapi/appCar/queryCarDetail.do", params);
        JSONObject jsonObject = JSON.parseObject(UC_CarDetailsData.jsonString);
        mActivity.getCarDetailData(method.put(jsonObject));

        photo.performClick();
        // 获取对应的Shadow类
        ShadowActivity shadowActivity = Shadows.shadowOf(mActivity);
        assertNotNull(shadowActivity);
        // 借助Shadow类获取启动下一Activity的Intent
        Intent nextIntent = shadowActivity.getNextStartedActivity();
        assertNotNull(nextIntent);
        ShadowIntent shadowIntent = Shadows.shadowOf(nextIntent);
        Assert.assertEquals(UC_SelectImageActivity.class, shadowIntent.getIntentClass());
    }

    //设置收藏缓存
    @Test
    public void chageCollect() throws Exception {
        int size = null != FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList() ?
                FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size() : 0;
        mActivity.setCollectCache(true, "00000230");//添加收藏
        assertTrue(FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().contains("00000230"));
        assertEquals(size + 1, FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size());
        size = FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size();

        mActivity.setCollectCache(true,"00000230");//重复添加收藏
        assertTrue(FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().contains("00000230"));
        assertEquals(size,FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size());
        size=FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size();

        mActivity.setCollectCache(true,"");//添加空字符串收藏
        assertFalse(FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().contains(""));
        assertEquals(size,FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size());
        size=FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size();

        mActivity.setCollectCache(true,null);//添加null收藏
        assertFalse(FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().contains(null));
        assertEquals(size,FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size());
        size=FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size();

        mActivity.setCollectCache(false, "00000230");//移除缓存中有的收藏
        assertEquals(size - 1, FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size());
        assertFalse(FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().contains("00000230"));
        size=FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size();

        mActivity.setCollectCache(false,"000000");//移除缓存中没有的收藏
        assertFalse(FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().contains("000000"));
        assertEquals(size,FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size());
        size=FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size();

        mActivity.setCollectCache(false,"");//移除缓存为“”的收藏
        assertFalse(FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().contains(""));
        assertEquals(size,FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size());
        size=FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size();

        mActivity.setCollectCache(false,null);//移除缓存为null的收藏
        assertFalse(FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().contains(null));
        assertEquals(size,FrameEtongApplication.getApplication().getUsedCarCollectCache().getCarList().size());
    }

    /**
     * @desc (得到车辆详情数据后 判断ListView的adapter是否有数据)
     * @createtime 2016/12/29 - 12:48
     * @author xiaoxue
     */
    @Test
    public void listTest() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("dvid", "00000172");
        params.put("userId", "10000004");
        HttpMethod method = new HttpMethod("http://120.25.98.100:8080/jxapi/appCar/queryCarDetail.do", params);
        JSONObject jsonObject = JSON.parseObject(UC_CarDetailsData.jsonString);

        assertTrue(carDetailLv.getAdapter().isEmpty());
        mActivity.getCarDetailData(method.put(jsonObject));
        assertFalse(carDetailLv.getAdapter().isEmpty());
    }
}