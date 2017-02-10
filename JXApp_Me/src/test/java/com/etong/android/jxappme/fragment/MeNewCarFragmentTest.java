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
import com.etong.android.jxappfours.find_car.collect_search.javabean.FC_NewCarCollectBean;
import com.etong.android.jxappfours.find_car.grand.car_config.Find_car_CarConfigActivity;
import com.etong.android.jxappfours.models_contrast.common.Models_Contrast_AddVechileStyle_Method;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;
import com.etong.android.jxappme.BuildConfig;
import com.etong.android.jxappme.R;
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
 * @desc (新车收藏 测试用例)
 * @createtime 2017/1/4 - 10:12
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class MeNewCarFragmentTest {
    private MeNewCarFragment mMeNewCarFragment;
    private PullToRefreshListView mlistView;
    private SupportFragmentController controller;

    @Before
    public void setUp() throws Exception {
        MeNewCarFragment fragment = new MeNewCarFragment();
        controller = SupportFragmentController.of(fragment);
        controller.create().attach().start();
        mMeNewCarFragment = (MeNewCarFragment) controller.get();
        mlistView = (PullToRefreshListView) mMeNewCarFragment.getActivity().findViewById(R.id.me_lv_newcar);
        assertNotNull(mMeNewCarFragment);
        assertNotNull(mlistView);


    }

    @Test
    public void onResume() throws Exception {
        UsedAndNewCollectCar car1=new UsedAndNewCollectCar();
        List<String> list1=new ArrayList<>();
        car1.setCarList(list1);
        car1.setChanged(true);
        FrameEtongApplication.getApplication().setNewCarCollectCache(car1);

        controller.resume();
        assertEquals(0,FrameEtongApplication.getApplication().getNewCarCollectCache().getCarList().size());
        UsedAndNewCollectCar car=new UsedAndNewCollectCar();
        List<String> list=new ArrayList<>();
        list.add("207673");
        car.setCarList(list);
        car.setChanged(true);
        FrameEtongApplication.getApplication().setNewCarCollectCache(car);
        controller.resume();
        assertFalse(FrameEtongApplication.getApplication().getNewCarCollectCache().getCarList().isEmpty());

    }

    //处理收藏的数据
    @Test
    public void getCollectList() throws Exception {
        assertTrue(mlistView.getRefreshableView().getAdapter().isEmpty());
        for (int i = 0; i < 3; i++) {       //0 成功  1 没网  2 没服务
            int flagValue = i;
            Map<String, String> params = new HashMap<>();
            params.put("pageSize", 10 + "");
            params.put("pageCurrent", 0 + "");
            params.put("isNewFlag", 1 + "");
            params.put("isPullDown", 0 + "");
            HttpMethod method = new HttpMethod(FrameConstant.CURRENT_SERVICE + "appCar/getcollectList.do", params);
            JSONObject object = new JSONObject();
            switch (flagValue) {
                case 0:
                    object.put("flag", 0);
                    JSONArray array = new JSONArray();
                    FC_NewCarCollectBean bean = new FC_NewCarCollectBean();
                    bean.setF_vid(207673);
                    bean.setF_price(42.0);
                    bean.setF_title("2014款30TFSI舒适型");
                    bean.setF_fullname("进口奥迪A1 2014款30TFSI舒适型");
                    bean.setF_org_id("001");
                    JSONObject obj = (JSONObject) JSON.toJSON(bean);
                    array.add(obj);
                    object.put("data", array);
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    break;
                case 2:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    break;
            }
            mMeNewCarFragment.getCollectList(method.put(object));
            switch (flagValue) {
                case 0:
                    assertFalse(mlistView.getRefreshableView().getAdapter().isEmpty());
//                    assertNotEquals(2,mlistView.getRefreshableView().getCount());
//                    assertEquals(0,mlistView.getRefreshableView().getChildCount());
                    for(int k=1;k<mlistView.getRefreshableView().getCount()-1;k++){
                        mlistView.getRefreshableView().getAdapter().getView(k,null,null).performClick();
                        ShadowActivity shadowActivity= Shadows.shadowOf(mMeNewCarFragment.getActivity());
                        Intent nextActivity=shadowActivity.getNextStartedActivity();
                        ShadowIntent shadowIntent=Shadows.shadowOf(nextActivity);
                        assertEquals(Find_car_CarConfigActivity.class,shadowIntent.getIntentClass());
                    }
                    break;
                case 1:
                    mlistView.setVisibility(View.GONE);
                    assertEquals(View.GONE, mlistView.getVisibility());
                    break;
                case 2:
                    mlistView.setVisibility(View.GONE);
                    assertEquals(View.GONE, mlistView.getVisibility());
                    break;
            }
        }
    }

    //获取到对比的Item
    @Test
    public void getComparisonVidList() throws Exception{
        assertNull(Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle());
        Models_Contrast_VechileType bean=new Models_Contrast_VechileType();
        bean.setVid(769596);
        Models_Contrast_AddVechileStyle_Method.cartAdd(bean);
        assertNotNull(Models_Contrast_AddVechileStyle_Method.getAdd_VechileStyle());

    }

}