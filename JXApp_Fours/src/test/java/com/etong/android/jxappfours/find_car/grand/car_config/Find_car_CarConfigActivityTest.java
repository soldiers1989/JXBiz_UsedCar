package com.etong.android.jxappfours.find_car.grand.car_config;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.frame.widget.pinnedlistview.PinnedSectionListView;
import com.etong.android.jxappcarfinancial.activity.CF_ApplyForActivity;
import com.etong.android.jxappfours.BuildConfig;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc.Find_car_CalcuTotalActivity;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;
import com.etong.android.jxappfours.models_contrast.main_content.MC_MainActivity;
import com.etong.android.jxappfours.order.FO_OrderActivity;
import com.etong.android.jxappfours.testData.FC_ConfigJsonData;
import com.etong.android.jxappfours.vechile_details.Find_Car_VechileDetailsImageActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.*;

/**
 * @desc (车辆配置界面)
 * @createtime 2017/1/6 - 9:47
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class Find_car_CarConfigActivityTest {

    private String intentDataStr = "{\n" +
            "    \"image\": \"http://222.247.51.114:10002/auto5.1/userfiles/car/2015101216/20151012163826370.jpg\",\n" +
            "    \"year\": \"2016\",\n" +
            "    \"subject\": \"RS 7 Sportback\",\n" +
            "    \"imageNum\": 69,\n" +
            "    \"manu\": \"奥迪RS\",\n" +
            "    \"fullName\": \"奥迪RSRS 72016款RS 7 Sportback\",\n" +
            "    \"title\": \"2016款RS 7 Sportback\",\n" +
            "    \"vid\": 769151,\n" +
            "    \"carset\": 4685,\n" +
            "    \"salestatus\": \"在售\",\n" +
            "    \"carsetTitle\": \"RS 7\",\n" +
            "    \"f_collectstatus\": 0,\n" +
            "    \"prices\": 169.88,\n" +
            "    \"outputVol\": 3993,\n" +
            "    \"brand\": \"奥迪\",\n" +
            "    \"salestatusid\": 1325\n" +
            "}";
    private Find_car_CarConfigActivity carCarConfigActivity;
    private PinnedSectionListView listView;

    @Before
    public void setUp() throws Exception {
        JSONObject object = JSON.parseObject(intentDataStr);
        Models_Contrast_VechileType intentBean = JSON.toJavaObject(object, Models_Contrast_VechileType.class);
        Intent intent = new Intent();
        Map map = new HashMap<>();
        map.put("dataType", intentBean);
        // 传递数据
        final SerializableObject myMap = new SerializableObject();
        myMap.setObject(map);// 将map数据添加到封装的myMap中
        Bundle bundle = new Bundle();
        bundle.putSerializable("dataTypeMap", myMap);
        intent.putExtras(bundle);
        ActivityController<Find_car_CarConfigActivity> controller =
                Robolectric.buildActivity(Find_car_CarConfigActivity.class, intent).create().start();

        carCarConfigActivity = controller.get();
        listView = (PinnedSectionListView) carCarConfigActivity.findViewById(R.id.find_car_config_listview);
    }

    @Test
    public void testClickIntent() throws Exception {
        View bottomGroup = carCarConfigActivity.findViewById(R.id.default_appoint);

        View default_appoint_drive = bottomGroup.findViewById(R.id.default_appoint_drive);
        View default_appoint_order = bottomGroup.findViewById(R.id.default_appoint_order);
        View default_appoint_credit = bottomGroup.findViewById(R.id.default_appoint_credit);

        View compereBtn = carCarConfigActivity.findViewById(R.id.titlebar_next_button);

        ImageView fc_detail_one_title_iv = (ImageView) listView.getAdapter().getView(0, null, null).findViewById(R.id.fc_detail_one_title_iv);
        Button askBtn = (Button) listView.getAdapter().getView(0, null, null).findViewById(R.id.fc_detail_list_btn_ask);
        Button calculateBtn = (Button) listView.getAdapter().getView(0, null, null).findViewById(R.id.fc_detail_list_btn_calculate);

        fc_detail_one_title_iv.performClick();
        ShadowActivity shadow1Activity = Shadows.shadowOf(carCarConfigActivity);
        Intent next1Intent = shadow1Activity.getNextStartedActivity();
        assertEquals(Find_Car_VechileDetailsImageActivity.class.getName(), next1Intent.getComponent().getClassName());

        compereBtn.performClick();
        ShadowActivity shadow2Activity = Shadows.shadowOf(carCarConfigActivity);
        Intent next2Intent = shadow2Activity.getNextStartedActivity();
        assertEquals(MC_MainActivity.class.getName(), next2Intent.getComponent().getClassName());

        askBtn.performClick();
        ShadowActivity shadow3Activity = Shadows.shadowOf(carCarConfigActivity);
        Intent next3Intent = shadow3Activity.getNextStartedActivity();
        assertEquals(FO_OrderActivity.class.getName(), next3Intent.getComponent().getClassName());


        calculateBtn.performClick();
        ShadowActivity shadow4Activity = Shadows.shadowOf(carCarConfigActivity);
        Intent next4Intent = shadow4Activity.getNextStartedActivity();
        assertEquals(Find_car_CalcuTotalActivity.class.getName(), next4Intent.getComponent().getClassName());

        default_appoint_drive.performClick();
        ShadowActivity shadow5Activity = Shadows.shadowOf(carCarConfigActivity);
        Intent next5Intent = shadow5Activity.getNextStartedActivity();
        assertEquals(FO_OrderActivity.class.getName(), next5Intent.getComponent().getClassName());

        default_appoint_order.performClick();
        ShadowActivity shadow6Activity = Shadows.shadowOf(carCarConfigActivity);
        Intent next6Intent = shadow6Activity.getNextStartedActivity();
        assertEquals(FO_OrderActivity.class.getName(), next6Intent.getComponent().getClassName());

        default_appoint_credit.performClick();
        ShadowActivity shadow7Activity = Shadows.shadowOf(carCarConfigActivity);
        Intent next7Intent = shadow7Activity.getNextStartedActivity();
        assertEquals(CF_ApplyForActivity.class.getName(), next7Intent.getComponent().getClassName());
    }

    @Test
    public void getVechileTypeDetailData() throws Exception {
//模拟请求得到的结果数据
        HttpMethod method = new HttpMethod("url", null);
        JSONObject object = JSON.parseObject(FC_ConfigJsonData.jsonStr);
        method.put(object);

        assertTrue(listView.getAdapter().isEmpty());
        carCarConfigActivity.getVechileTypeDetailData(method);
        assertFalse(listView.getAdapter().isEmpty());
    }

}