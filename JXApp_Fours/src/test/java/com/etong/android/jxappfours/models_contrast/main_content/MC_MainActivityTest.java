package com.etong.android.jxappfours.models_contrast.main_content;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.jxappfours.BuildConfig;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.config_compare.Find_car_CompareContentActivity;
import com.etong.android.jxappfours.models_contrast.common.Models_Contrast_AddVechileStyle_Method;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @desc (车型对比 主页面 测试用例)
 * @createtime 2017/1/6 - 14:39
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk=21,constants = BuildConfig.class,application = FrameEtongApplication.class)
public class MC_MainActivityTest {
    private MC_MainActivity activity;
    private LinearLayout addCar;
    private Button clear;
    private ListView contrast_list;
    private Button delete;
    private Button contrast;
    private LinearLayout car_null;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(MC_MainActivity.class);
        assertNotNull(activity);
        clear = (Button)activity.findViewById(R.id.models_contrast_rb_clear);
        contrast_list =(ListView)activity.findViewById(R.id.models_contrast_lv_list);
        delete =(Button)activity.findViewById(R.id.models_contrast_rb_delete);
        contrast =(Button)activity.findViewById(R.id.models_contrast_rb_contrast);
        car_null = (LinearLayout)activity.findViewById(R.id.models_contrast_ll_null);
    }

    @Test
    public void onClick() throws Exception {
        //清空
        assertNull(Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle());
        Models_Contrast_VechileType addVechileType = new Models_Contrast_VechileType();
        addVechileType.setVid(769596);
        addVechileType.setFullName("AC SchnitzerAC Schnitzer X52015款ACS35 35i");
        addVechileType.setChecked(false);
        Models_Contrast_AddVechileStyle_Method.cartAdd(addVechileType);
        assertNotNull(Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle());
        clear.performClick();
        int visibilty=contrast_list.getVisibility();
        assertEquals(View.GONE,visibilty);
        assertNull(Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle());
    }

    //删除
    @Test
    public void delect() throws Exception{
        assertNull(Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle());
        List<Models_Contrast_VechileType> list=new ArrayList<>();
        Models_Contrast_VechileType addVechile = new Models_Contrast_VechileType();
        addVechile.setVid(769596);
        addVechile.setFullName("AC SchnitzerAC Schnitzer X52015款ACS35 35i");
        addVechile.setChecked(false);
        Models_Contrast_AddVechileStyle_Method.cartAdd(addVechile);
        Models_Contrast_VechileType addVechile1 = new Models_Contrast_VechileType();
        addVechile1.setVid(769596);
        addVechile1.setFullName("2015款ACS35 35i");
        addVechile1.setChecked(true);
        Models_Contrast_AddVechileStyle_Method.cartAdd(addVechile1);
        list.add(addVechile);
        list.add(addVechile1);
        assertNotNull(Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle());
        delete.performClick();
        assertEquals(1,Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle().size());
    }

    //点击对比跳转
    @Test
    public void contrast() throws Exception{
        List<Models_Contrast_VechileType> list=new ArrayList<>();
        Models_Contrast_VechileType addVechile = new Models_Contrast_VechileType();
        addVechile.setVid(769596);
        addVechile.setFullName("AC SchnitzerAC Schnitzer X52015款ACS35 35i");
        addVechile.setChecked(true);
        Models_Contrast_VechileType addVechile1 = new Models_Contrast_VechileType();
        addVechile1.setVid(769596);
        addVechile1.setFullName("2015款ACS35 35i");
        addVechile1.setChecked(true);
//        list.add(addVechile);
//        list.add(addVechile1);
        activity.getCarModels(addVechile);
        activity.getCarModels(addVechile1);
        for(int i=0;i<contrast_list.getCount();i++){
            contrast_list.getAdapter().getView(i,null,null).performClick();
        }
        contrast.performClick();
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent nextActivity = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = Shadows.shadowOf(nextActivity);
        assertEquals(Find_car_CompareContentActivity.class, shadowIntent.getIntentClass());
    }


    @Test
    public void getCarModels() throws Exception {
        List<Models_Contrast_VechileType> list=new ArrayList<>();
        Models_Contrast_VechileType addVechile = new Models_Contrast_VechileType();
        addVechile.setVid(769596);
        addVechile.setFullName("AC SchnitzerAC Schnitzer X52015款ACS35 35i");
        addVechile.setChecked(true);
        Models_Contrast_VechileType addVechile1 = new Models_Contrast_VechileType();
        addVechile1.setVid(769596);
        addVechile1.setFullName("2015款ACS35 35i");
        addVechile1.setChecked(true);
        activity.getCarModels(addVechile);
        activity.getCarModels(addVechile1);
        assertEquals(View.VISIBLE,contrast_list.getVisibility());
        assertEquals(View.GONE,car_null.getVisibility());
    }

}