package com.etong.android.jxappusedcar.content;

import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameHttpUri;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.UsedAndNewCollectCar;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappusedcar.BuildConfig;

import com.etong.android.jxappusedcar.testData.ConfigJsonData;

import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.javabean.UC_CarDetailJavabean;
import com.etong.android.jxappusedcar.javabean.UC_CollectBean;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;


/**
 * @desc (参数配置测试用例)
 * @createtime 2016/12/26 - 15:24
 * @Created by wukefan.
 * 注释：测试前需注释所要测试的代码中的loadStart
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class UC_ConfigInfoTest {


    private UC_ConfigInfo configInfoActivity;
    private Button default_appoint_drive;
    private UC_CarDetailJavabean.TitleConfigBean bean;
    private ActivityController<UC_ConfigInfo> controller;
    private PinnedSectionListView listView;

    @Before
    public void setUp() throws Exception {
        UC_ConfigInfo.isTestDebug = true;
        // 提示可以按 ctrl + 空格
        bean = new UC_CarDetailJavabean.TitleConfigBean();
        bean.setF_carset("X5");
        bean.setF_price("42");
        bean.setF_cartitle("宝马-X5 2015款 xDrive28i");
        bean.setF_cartype("2015款 xDrive28i");
        bean.setF_cartypeid("768648");
        bean.setF_collectstatus(0);
        bean.setF_brand("宝马");
        bean.setF_dvid("00000173");
        Intent intent = new Intent();
        intent.putExtra(UC_ConfigInfo.CONFIG_DATA, bean);
//        configInfoActivity = Robolectric.setupActivity(UC_ConfigInfo.class);//创建Activity
//         创建Activity控制器
        controller = Robolectric.buildActivity(UC_ConfigInfo.class).withIntent(intent).create().start();
        configInfoActivity = controller.get();

        //也可以是这种：controller = Robolectric.buildActivity(UC_CarDetailActivity.class,intent);

        assertNotNull(configInfoActivity);//判断是否为空
        default_appoint_drive = (Button) configInfoActivity.findViewById(R.id.default_appoint_drive);      // 收藏
        default_appoint_drive.setText("收藏");

        listView = (PinnedSectionListView) configInfoActivity.findViewById(R.id.used_car_carconfig_lv);
        assertNotNull(listView.getAdapter());
    }

    /***
     * 验证Intent跳转的数据
     */
    @Test
    public void testIntentValue() throws Exception {
        UC_CarDetailJavabean.TitleConfigBean getBean =
                (UC_CarDetailJavabean.TitleConfigBean) configInfoActivity.getIntent().getSerializableExtra(UC_ConfigInfo.CONFIG_DATA);
        assertEquals(bean, getBean);
    }

    @Test
    public void onResume() throws Exception {
        controller.resume();
        //以下是对onResume()里面的的操作验证
        assertEquals("收藏", default_appoint_drive.getText().toString());

        //模拟设置用户缓存中已收藏了该辆车
        UsedAndNewCollectCar usedBean = new UsedAndNewCollectCar();
        List<String> carList = new ArrayList<>();
        carList.add("00000173");
        usedBean.setCarList(carList);
        FrameEtongApplication.getApplication().setUsedCarCollectCache(usedBean);

        controller.resume();
        //以下是对onResume()里面的的操作验证
        assertEquals("已收藏", default_appoint_drive.getText().toString());
    }

    /***
     * 验证设置收藏状态操作
     */
    @Test
    public void setCollectState() throws Exception {
        configInfoActivity.setCollectState(true);
        assertEquals("已收藏", default_appoint_drive.getText().toString());

        configInfoActivity.setCollectState(false);
        assertEquals("收藏", default_appoint_drive.getText().toString());
    }

    /***
     * 验证收藏缓存操作
     */
    @Test
    public void setCollectCache() throws Exception {
        FrameEtongApplication application = FrameEtongApplication.getApplication();
        int n = null == application.getUsedCarCollectCache().getCarList() ?
                0 : application.getUsedCarCollectCache().getCarList().size();

        configInfoActivity.setCollectCache(true, "00000173");//添加收藏
        assertTrue(application.getUsedCarCollectCache().getCarList().contains("00000173"));
        Assert.assertEquals(n + 1, application.getUsedCarCollectCache().getCarList().size());
        n = application.getUsedCarCollectCache().getCarList().size();

        configInfoActivity.setCollectCache(true, "00000173");//重复添加收藏
        assertTrue(application.getUsedCarCollectCache().getCarList().contains("00000173"));
        Assert.assertEquals(n, application.getUsedCarCollectCache().getCarList().size());
        n = application.getUsedCarCollectCache().getCarList().size();

        configInfoActivity.setCollectCache(true, "   ");//添加空字符串收藏
        assertFalse(application.getUsedCarCollectCache().getCarList().contains("   "));
        Assert.assertEquals(n, application.getUsedCarCollectCache().getCarList().size());
        n = application.getUsedCarCollectCache().getCarList().size();

        configInfoActivity.setCollectCache(true, null);//添加null收藏
        Assert.assertEquals(n, application.getUsedCarCollectCache().getCarList().size());
        n = application.getUsedCarCollectCache().getCarList().size();

        configInfoActivity.setCollectCache(false, "00000173");//移除缓存中有的收藏
        Assert.assertEquals(n - 1, application.getUsedCarCollectCache().getCarList().size());
        assertFalse(application.getUsedCarCollectCache().getCarList().contains("00000173"));
        n = application.getUsedCarCollectCache().getCarList().size();

        configInfoActivity.setCollectCache(false, "00000171");//移除缓存中没有的收藏
        assertFalse(application.getUsedCarCollectCache().getCarList().contains("00000171"));
        Assert.assertEquals(n, application.getUsedCarCollectCache().getCarList().size());
        n = application.getUsedCarCollectCache().getCarList().size();

        configInfoActivity.setCollectCache(false, "    ");//移除空字符串收藏
        assertFalse(application.getUsedCarCollectCache().getCarList().contains("    "));
        Assert.assertEquals(n, application.getUsedCarCollectCache().getCarList().size());
        n = application.getUsedCarCollectCache().getCarList().size();

        configInfoActivity.setCollectCache(false, null);//移除null收藏
        Assert.assertEquals(n, application.getUsedCarCollectCache().getCarList().size());
    }

    /***
     * 验证Dialog是否正确弹出
     */
    @Test
    public void showInfoAlertDialog() throws Exception {
        UC_Dialog dialog = (UC_Dialog) ShadowDialog.getLatestDialog();
        assertNull(dialog); //判断Dialog尚未弹出
        configInfoActivity.showInfoAlertDialog(configInfoActivity);
        dialog = (UC_Dialog) ShadowDialog.getLatestDialog();
        assertNotNull(dialog); // 判断Dialog已经弹出
    }

    /***
     * 验证收藏请求结果后的操作
     */
    @Test
    public void clickCollection() throws Exception {

        for (int i = 0; i < 4; i++) {

            int condition = i;//0 = 成功 收藏成功;1 = 成功 取消收藏; 2 = NETWORK_ERROR; 3 DATA_ERROR;
            String msg = null;

            //模拟请求得到的结果数据
            Map<String, String> map = new HashMap<>();
            map.put("dvid", "00000173");
            map.put("userId", "10000026");
            HttpMethod method = new HttpMethod(FrameHttpUri.CLICKCOLLECTION, map);

            JSONObject object = new JSONObject();
            switch (condition) {
                case 0:
                    UC_CollectBean collectBean = new UC_CollectBean();
                    collectBean.setF_collectstatus("1");
                    collectBean.setMsg("收藏成功");
                    object.put("flag", "0");
                    object.put("data", collectBean);
                    msg = "收藏成功";
                    break;
                case 1:
                    UC_CollectBean collectCancelBean = new UC_CollectBean();
                    collectCancelBean.setF_collectstatus("0");
                    collectCancelBean.setMsg("取消收藏成功");
                    object.put("flag", "0");
                    object.put("data", collectCancelBean);
                    msg = "取消收藏成功";
                    break;
                case 2:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    object.put("msg", "访问失败");
                    msg = "访问失败";
                    break;
                case 3:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    object.put("msg", "数据请求失败!");
                    msg = "数据请求失败!";
                    break;
            }
            method.put(object);


            Toast toast = ShadowToast.getLatestToast();
            // 判断Toast尚未弹出
            assertNull(toast);

            //请求结果
            configInfoActivity.clickCollection(method);

            if (condition == 0) {//收藏
                assertEquals("已收藏", default_appoint_drive.getText().toString());
            }

            if (condition == 1) {//取消收藏
                assertEquals("收藏", default_appoint_drive.getText().toString());
            }

            toast = ShadowToast.getLatestToast();
            // 判断Toast已经弹出
            assertNotNull(toast);
            // 获取Shadow类进行验证
            ShadowToast shadowToast = shadowOf(toast);
            assertEquals(Toast.LENGTH_SHORT, shadowToast.getDuration());
            assertEquals(msg, shadowToast.getTextOfLatestToast());
            ShadowToast.reset();

        }
    }

    /***
     * 验证预约看车请求结果后的操作
     */
    @Test
    public void getOrderCar() throws Exception {
        for (int i = 0; i < 3; i++) {

            int condition = i;//0 = 成功; 1 = NETWORK_ERROR; 2 = DATA_ERROR;
            String msg = null;

            //模拟请求得到的结果数据
            Map<String, String> map = new HashMap<>();
            map.put("f_phone", "13755174943");
            map.put("f_cartype", "宝马 X5 2015款 xDrive28i");
            map.put("f_cartypeid", "768648");
            HttpMethod method = new HttpMethod(FrameHttpUri.ORDERCAR, map);
            JSONObject object = new JSONObject();
            switch (condition) {
                case 0:
                    object.put("flag", "0");
                    object.put("data", "Success");
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    object.put("msg", "访问失败");
                    msg = "访问失败";
                    break;
                case 2:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    object.put("msg", "数据请求失败!");
                    msg = "数据请求失败!";
                    break;
            }
            method.put(object);

            Toast toast = ShadowToast.getLatestToast();
            // 判断Toast尚未弹出
            assertNull(toast);

            //请求结果
            configInfoActivity.getOrderCar(method);

            switch (condition) {
                case 0:
                    UC_Dialog dialog = (UC_Dialog) ShadowDialog.getLatestDialog();
                    assertNotNull(dialog); // 判断Dialog已经弹出
                    break;
                default:
                    toast = ShadowToast.getLatestToast();
                    // 判断Toast已经弹出
                    assertNotNull(toast);
                    // 获取Shadow类进行验证
                    ShadowToast shadowToast = shadowOf(toast);
                    assertEquals(Toast.LENGTH_SHORT, shadowToast.getDuration());
                    assertEquals(msg, shadowToast.getTextOfLatestToast());

                    ShadowToast.reset();

                    break;
            }

        }

    }

    /***
     * 验证参数详情请求结果后的操作（数据是否设置进适配器中）
     */
    @Test
    public void getCarConfigInfo() throws Exception {
        //模拟请求得到的结果数据
        HttpMethod method = new HttpMethod(FrameHttpUri.CARCONFIGINFO + "?f_cartypeid=768648", null);
        JSONObject object = JSON.parseObject(ConfigJsonData.jsonStr);
        method.put(object);

        assertTrue(listView.getAdapter().isEmpty());
        configInfoActivity.getCarConfigInfo(method);
        assertFalse(listView.getAdapter().isEmpty());

    }

}

