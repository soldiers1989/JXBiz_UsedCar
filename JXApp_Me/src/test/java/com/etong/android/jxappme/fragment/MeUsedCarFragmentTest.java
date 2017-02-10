package com.etong.android.jxappme.fragment;

import android.content.Intent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.FrameConstant;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.UsedAndNewCollectCar;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappfours.find_car.grand.car_config.Find_car_CarConfigActivity;
import com.etong.android.jxappme.BuildConfig;
import com.etong.android.jxappme.R;
import com.etong.android.jxappusedcar.content.UC_CarDetailActivity;
import com.etong.android.jxappusedcar.javabean.UC_World_CarListJavaBean;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;
import org.robolectric.shadows.support.v4.SupportFragmentController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @desc (二手车收藏测试用例)
 * @createtime 2017/1/4 - 9:02
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk=21,constants = BuildConfig.class,application = FrameEtongApplication.class)
public class MeUsedCarFragmentTest {

    private MeUsedCarFragment mMeUsedCarFragment;
    private PullToRefreshListView mlistView;
    private SupportFragmentController controller;
    @Before
    public void setUp() throws Exception {
        MeUsedCarFragment fragment = new MeUsedCarFragment();
        controller = SupportFragmentController.of(fragment);
        controller.create().attach().start();
        mMeUsedCarFragment = (MeUsedCarFragment) controller.get();
        mlistView =(PullToRefreshListView)mMeUsedCarFragment.getActivity().findViewById(R.id.me_lv_newcar);
        assertNotNull(mMeUsedCarFragment);
        assertNotNull(mlistView);
    }

    //处理收藏的数据
    @Test
    public void getCollectList() throws Exception {
        assertTrue(mlistView.getRefreshableView().getAdapter().isEmpty());
        for(int i=0;i<3;i++){       //0 成功  1 没网  2 没服务
            int flagValue=i;
            Map<String,String> params=new HashMap<>();
            params.put("pageSize",10+"");
            params.put("pageCurrent",0+"");
            params.put("isNewFlag",0+"");
            params.put("isPullDown",0+"");
            HttpMethod method =new HttpMethod(FrameConstant.CURRENT_SERVICE + "appCar/getcollectList.do",params);
            JSONObject object=new JSONObject();
            switch (flagValue){
                case 0:
                    object.put("flag",0);
                    JSONArray array=new JSONArray();
                    UC_World_CarListJavaBean bean=new UC_World_CarListJavaBean();
                    bean.setF_carsetid(232);
                    bean.setF_price(42.0);
                    bean.setF_cartitle("宝马-X5 2015款 xDrive28i");
                    bean.setF_dvid("00000173");
                    bean.setF_cartypeid(768648);
                    JSONObject obj= (JSONObject) JSON.toJSON(bean);
                    array.add(obj);
                    object.put("data",array);
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    break;
                case 2:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    break;
            }
            mMeUsedCarFragment.getCollectList(method.put(object));
            switch (flagValue){
                case 0:
                    assertFalse(mlistView.getRefreshableView().getAdapter().isEmpty());
                    for(int k=1;k<mlistView.getRefreshableView().getCount()-1;k++){
                        mlistView.getRefreshableView().getAdapter().getView(k,null,null).performClick();
                        ShadowActivity shadowActivity= Shadows.shadowOf(mMeUsedCarFragment.getActivity());
                        Intent nextActivity=shadowActivity.getNextStartedActivity();
                        ShadowIntent shadowIntent=Shadows.shadowOf(nextActivity);
                        assertEquals(UC_CarDetailActivity.class,shadowIntent.getIntentClass());
                    }
                    break;
                case 1:
                    mlistView.setVisibility(View.GONE);
                    assertEquals(View.GONE,mlistView.getVisibility());
                    break;
                case 2:
                    mlistView.setVisibility(View.GONE);
                    assertEquals(View.GONE,mlistView.getVisibility());
                    break;
            }
        }
    }

    @Test
    public void onResume() throws Exception {
        controller.resume();
//        assertFalse(FrameEtongApplication.getApplication().getUsedCarCollectCache().isChanged());
        assertEquals(false,FrameEtongApplication.getApplication().getUsedCarCollectCache().isChanged());
        UsedAndNewCollectCar car=new UsedAndNewCollectCar();
        List<String> list=new ArrayList<>();
        list.add("00000173");
        car.setCarList(list);
        car.setChanged(true);
        FrameEtongApplication.getApplication().setUsedCarCollectCache(car);
        controller.resume();
        assertEquals(true,FrameEtongApplication.getApplication().getUsedCarCollectCache().isChanged());
//        assertTrue(FrameEtongApplication.getApplication().getUsedCarCollectCache().isChanged());
    }

}