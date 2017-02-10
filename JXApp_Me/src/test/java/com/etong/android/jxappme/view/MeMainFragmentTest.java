package com.etong.android.jxappme.view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.frame.user.UsedAndNewCollectCar;
import com.etong.android.frame.widget.CircleImageView;
import com.etong.android.jxappme.BuildConfig;

import com.etong.android.jxappme.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import org.robolectric.shadows.ShadowToast;
import org.robolectric.shadows.support.v4.SupportFragmentController;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

/**
 * @desc ("我的"子模块的Fragment主界面测试用例)
 * @createtime 2017/1/4 - 8:53
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class MeMainFragmentTest {

    private MeMainFragment meMainFragment;
    private Button loginBtnHead;
    private CircleImageView personMyHead;
    private TextView personMyHeadName;
    private ListView personListview;
    private TextView tvCollectNum;


    @Before
    public void setUp() throws Exception {

        MeMainFragment mainFragment = new MeMainFragment();
        SupportFragmentController controller = SupportFragmentController.of(mainFragment);
        controller.create().attach().start();
        meMainFragment = (MeMainFragment) controller.get();

        loginBtnHead = (Button) meMainFragment.getView().findViewById(R.id.me_btn_head_login);
        personMyHead = (CircleImageView) meMainFragment.getView().findViewById(R.id.me_img_head);
        personMyHeadName = (TextView) meMainFragment.getView().findViewById(R.id.me_txt_headname);
        personListview = (ListView) meMainFragment.getView().findViewById(R.id.me_lv_info);
        tvCollectNum = (TextView) meMainFragment.getView().findViewById(R.id.me_txt_collect_num);

        assertEquals(View.VISIBLE, loginBtnHead.getVisibility());
        assertEquals(View.GONE, personMyHead.getVisibility());
        assertEquals("立即登录体验更多", personMyHeadName.getText().toString());
    }

    /**
     * @desc (测试是否登录后的操作)
     */
    @Test
    public void isLogin() throws Exception {
        FrameUserInfo userInfo = new FrameUserInfo();
        userInfo.setF_phone("13755174943");
        userInfo.setUserSex("女");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);

        meMainFragment.isLogin();
        assertEquals(View.GONE, loginBtnHead.getVisibility());
        assertEquals(View.VISIBLE, personMyHead.getVisibility());
        assertEquals("未设置", personMyHeadName.getText().toString());
        int girl = R.mipmap.ic_me_head_girl;
        int ivDefault = R.mipmap.ic_me_head_boy;

//        assertEquals(meMainFragment.getResources().getDrawable(girl).getConstantState()
//                , personMyHead.getDrawable().getConstantState());
    }

    /**
     * @desc (测试通过名字获取图片资源)
     */
    @Test
    public void getImageIdByName() throws Exception {
        assertEquals(com.etong.android.jxappme.R.mipmap.bound_vehicle, MeMainFragment.getImageIdByName("bound_vehicle"));
    }


    /**
     * @desc (测试收藏个数请求结果后的操作)
     */
    @Test
    public void getCollectTotalResult() throws Exception {
        //模拟请求得到的结果数据
        HttpMethod method = new HttpMethod("url", null);

        for (int i = 0; i < 5; i++) {
            int condition = i;//0 = 成功;1 = NETWORK_ERROR(未登录); 2 = DATA_ERROR(未登录);3 = NETWORK_ERROR(登录); 4 = DATA_ERROR(登录);
            JSONObject object = new JSONObject();
            switch (condition) {
                case 0:
                    object.put("flag", "0");
                    object.put("data", "3");
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    object.put("msg", "访问失败");
                    break;
                case 2:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    object.put("msg", "数据请求失败!");
                    break;
                case 3:
                    UsedAndNewCollectCar bean = new UsedAndNewCollectCar();
                    List<String> list = new ArrayList<>();
                    list.add("1000043");
                    list.add("1000123");
                    list.add("1000315");
                    bean.setCarList(list);
                    FrameEtongApplication.getApplication().setUsedCarCollectCache(bean);
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    object.put("msg", "访问失败");
                    break;
                case 4:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    object.put("msg", "数据请求失败!");
                    break;
            }
            method.put(object);

            meMainFragment.getCollectTotalResult(method);

            //未登录
            if (condition == 2 || condition == 1) {
                assertEquals("0", tvCollectNum.getText().toString());
                return;
            }

            assertEquals("3", tvCollectNum.getText().toString());
        }
    }

    /**
     * @desc (测试跳转)
     */
    @Test
    public void testIntent() throws Exception {

        //设置
        personListview.getAdapter().getView(5, null, null).performClick();
        ShadowActivity shadowSetActivity = Shadows.shadowOf(meMainFragment.getActivity());
        Intent nextSetIntent = shadowSetActivity.getNextStartedActivity();
        assertEquals(MePersonalSettingActivity.class.getName(), nextSetIntent.getComponent().getClassName());

        //我的爱车、维保进度、申请进度、贷款记录、还款提醒未登录
        for (int i = 0; i < 5; i++) {
            personListview.getAdapter().getView(i, null, null).performClick();
            ShadowActivity shadowForActivity = Shadows.shadowOf(meMainFragment.getActivity());
            Intent nextForIntent = shadowForActivity.getNextStartedActivity();
            assertEquals(FramePersonalLoginActivity.class.getName(), nextForIntent.getComponent().getClassName());
        }

        //登录按钮
        loginBtnHead.performClick();
        // 获取对应的Shadow类
        ShadowActivity shadowLoginActivity = Shadows.shadowOf(meMainFragment.getActivity());
        // 借助Shadow类获取启动下一Activity的Intent
        Intent nextLoginIntent = shadowLoginActivity.getNextStartedActivity();
        // 校验Intent的正确性
        assertEquals(FramePersonalLoginActivity.class.getName(), nextLoginIntent.getComponent().getClassName());

        //收藏未登录
        ((ViewGroup) meMainFragment.getView().findViewById(R.id.me_ll_collect)).performClick();
        ShadowActivity shadowColActivity = Shadows.shadowOf(meMainFragment.getActivity());
        Intent nextColIntent = shadowColActivity.getNextStartedActivity();
        assertEquals(FramePersonalLoginActivity.class.getName(), nextColIntent.getComponent().getClassName());

        //优惠券未登录
        ((ViewGroup) meMainFragment.getView().findViewById(R.id.me_ll_coupon)).performClick();
        ShadowActivity shadowCopActivity = Shadows.shadowOf(meMainFragment.getActivity());
        Intent nextCopIntent = shadowCopActivity.getNextStartedActivity();
        assertEquals(FramePersonalLoginActivity.class.getName(), nextCopIntent.getComponent().getClassName());

        //消息未登录
        ((TextView) meMainFragment.getView().findViewById(R.id.titlebar_next_button)).performClick();
        ShadowActivity shadowMsgActivity = Shadows.shadowOf(meMainFragment.getActivity());
        Intent nextMsgIntent = shadowMsgActivity.getNextStartedActivity();
        assertEquals(FramePersonalLoginActivity.class.getName(), nextMsgIntent.getComponent().getClassName());


        FrameEtongApplication.getApplication().setUserInfo(new FrameUserInfo());

        //收藏登录
        ((ViewGroup) meMainFragment.getView().findViewById(R.id.me_ll_collect)).performClick();
        ShadowActivity shadowColActivity2 = Shadows.shadowOf(meMainFragment.getActivity());
        Intent nextColIntent2 = shadowColActivity2.getNextStartedActivity();
        assertEquals(MeCollectActivity.class.getName(), nextColIntent2.getComponent().getClassName());

        //优惠券登录
        ((ViewGroup) meMainFragment.getView().findViewById(R.id.me_ll_coupon)).performClick();
        Toast toast = ShadowToast.getLatestToast();
        // 判断Toast已经弹出
        assertNotNull(toast);
        // 获取Shadow类进行验证
        ShadowToast shadowToast = shadowOf(toast);
        assertEquals(Toast.LENGTH_SHORT, shadowToast.getDuration());
        assertEquals("暂无优惠券~", shadowToast.getTextOfLatestToast());
        ShadowToast.reset();

        //消息登录
        ((TextView) meMainFragment.getView().findViewById(R.id.titlebar_next_button)).performClick();
        ShadowActivity shadowMsgActivity2 = Shadows.shadowOf(meMainFragment.getActivity());
        Intent nextMsgIntent2 = shadowMsgActivity2.getNextStartedActivity();
        assertEquals(MePersonalMessageActivity.class.getName(), nextMsgIntent2.getComponent().getClassName());
    }

}