package com.etong.android.jxappfours.find_car.grand.carset;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.jxappcarfinancial.activity.CF_ApplyForActivity;
import com.etong.android.jxappfours.BuildConfig;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.bean.FC_InsalesListItem;
import com.etong.android.jxappfours.find_car.grand.car_config.Find_car_CarConfigActivity;
import com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc.Find_car_CalcuTotalActivity;
import com.etong.android.jxappfours.find_car.grand.view.FC_GrandListView;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;
import com.etong.android.jxappfours.models_contrast.main_content.MC_MainActivity;
import com.etong.android.jxappfours.order.FO_OrderActivity;
import com.etong.android.jxappfours.vechile_details.Find_Car_VechileDetailsImageActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;

/**
 * @desc (进入车系列表页单元测试)
 * @createtime 2017/1/5 - 18:26
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class FC_CarsetActivityTest {

    private FC_CarsetActivity carsetActivity;
    private String jsonStr = "{\n" +
            "    \"msg\": \"成功\",\n" +
            "    \"errno\": \"成功\",\n" +
            "    \"flag\": 0,\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"image\": \"http://222.247.51.114:10002/auto5.1/userfiles/car/2014080415/2014080415302791.jpg\",\n" +
            "            \"year\": \"2014\",\n" +
            "            \"subject\": \"RS 7 Sportback\",\n" +
            "            \"imageNum\": 73,\n" +
            "            \"manu\": \"奥迪RS\",\n" +
            "            \"fullName\": \"奥迪RSRS 72014款RS 7 Sportback\",\n" +
            "            \"title\": \"2014款RS 7 Sportback\",\n" +
            "            \"vid\": 662363,\n" +
            "            \"carset\": 4685,\n" +
            "            \"salestatus\": \"在售\",\n" +
            "            \"carsetTitle\": \"RS 7\",\n" +
            "            \"f_collectstatus\": 0,\n" +
            "            \"prices\": 178.8,\n" +
            "            \"outputVol\": 3993,\n" +
            "            \"brand\": \"奥迪\",\n" +
            "            \"salestatusid\": 1325\n" +
            "        },\n" +
            "        {\n" +
            "            \"image\": \"http://222.247.51.114:10002/auto5.1/userfiles/car/2015101216/20151012163826370.jpg\",\n" +
            "            \"year\": \"2016\",\n" +
            "            \"subject\": \"RS 7 Sportback\",\n" +
            "            \"imageNum\": 69,\n" +
            "            \"manu\": \"奥迪RS\",\n" +
            "            \"fullName\": \"奥迪RSRS 72016款RS 7 Sportback\",\n" +
            "            \"title\": \"2016款RS 7 Sportback\",\n" +
            "            \"vid\": 769151,\n" +
            "            \"carset\": 4685,\n" +
            "            \"salestatus\": \"在售\",\n" +
            "            \"carsetTitle\": \"RS 7\",\n" +
            "            \"f_collectstatus\": 0,\n" +
            "            \"prices\": 169.88,\n" +
            "            \"outputVol\": 3993,\n" +
            "            \"brand\": \"奥迪\",\n" +
            "            \"salestatusid\": 1325\n" +
            "        }\n" +
            "    ]\n" +
            "}";
    private FC_GrandListView Lv;

    @Before
    public void setUp() throws Exception {
        carsetActivity = Robolectric.setupActivity(FC_CarsetActivity.class);

        Lv = (FC_GrandListView) carsetActivity.findViewById(R.id.lv_test);
    }

    @Test
    public void testClickIntent() throws Exception {
        getVechileTypeData();
        View bottomGroup = carsetActivity.findViewById(R.id.default_appoint);

        View default_appoint_drive = bottomGroup.findViewById(R.id.default_appoint_drive);
        View default_appoint_order = bottomGroup.findViewById(R.id.default_appoint_order);
        View default_appoint_credit = bottomGroup.findViewById(R.id.default_appoint_credit);

        View compereBtn = carsetActivity.findViewById(R.id.titlebar_next_button);

        ImageView fc_carset_title_iv = (ImageView) carsetActivity.findViewById(R.id.fc_carset_title_iv);

        default_appoint_drive.performClick();
        ShadowActivity shadow1Activity = Shadows.shadowOf(carsetActivity);
        Intent next1Intent = shadow1Activity.getNextStartedActivity();
        assertEquals(FO_OrderActivity.class.getName(), next1Intent.getComponent().getClassName());

        default_appoint_order.performClick();
        ShadowActivity shadow2Activity = Shadows.shadowOf(carsetActivity);
        Intent next2Intent = shadow2Activity.getNextStartedActivity();
        assertEquals(FO_OrderActivity.class.getName(), next2Intent.getComponent().getClassName());

        default_appoint_credit.performClick();
        ShadowActivity shadow3Activity = Shadows.shadowOf(carsetActivity);
        Intent next3Intent = shadow3Activity.getNextStartedActivity();
        assertEquals(CF_ApplyForActivity.class.getName(), next3Intent.getComponent().getClassName());

        fc_carset_title_iv.performClick();
        ShadowActivity shadow4Activity = Shadows.shadowOf(carsetActivity);
        Intent next4Intent = shadow4Activity.getNextStartedActivity();
        assertEquals(Find_Car_VechileDetailsImageActivity.class.getName(), next4Intent.getComponent().getClassName());

        compereBtn.performClick();
        ShadowActivity shadow5Activity = Shadows.shadowOf(carsetActivity);
        Intent next5Intent = shadow5Activity.getNextStartedActivity();
        assertEquals(MC_MainActivity.class.getName(), next5Intent.getComponent().getClassName());
    }

    @Test
    public void getVechileTypeData() throws Exception {
        assertTrue(Lv.getAdapter().isEmpty());
        HttpMethod method = new HttpMethod("url", null);
        JSONObject object = JSON.parseObject(jsonStr);

        carsetActivity.getVechileTypeData(method.put(object));
        assertFalse(Lv.getAdapter().isEmpty());

    }

    @Test
    public void testListItemClick() throws Exception {
        getVechileTypeData();
        for (int i = 0; i < Lv.getAdapter().getCount(); i++) {
            View itemView = Lv.getAdapter().getView(i, null, null);
            if (Lv.getAdapter().getItemViewType(i) == Models_Contrast_VechileType.ITEM) {

                itemView.findViewById(R.id.find_car_carset_detail_item_title).performClick();
                ShadowActivity shadow1Activity = Shadows.shadowOf(carsetActivity);
                Intent next1Intent = shadow1Activity.getNextStartedActivity();
                assertEquals(Find_car_CarConfigActivity.class.getName(), next1Intent.getComponent().getClassName());


                itemView.findViewById(R.id.fc_detail_list_btn_ask).performClick();
                ShadowActivity shadow2Activity = Shadows.shadowOf(carsetActivity);
                Intent next2Intent = shadow2Activity.getNextStartedActivity();
                assertEquals(FO_OrderActivity.class.getName(), next2Intent.getComponent().getClassName());


                itemView.findViewById(R.id.fc_detail_list_btn_calculate).performClick();
                ShadowActivity shadow3Activity = Shadows.shadowOf(carsetActivity);
                Intent next3Intent = shadow3Activity.getNextStartedActivity();
                assertEquals(Find_car_CalcuTotalActivity.class.getName(), next3Intent.getComponent().getClassName());

            }

        }


    }

}