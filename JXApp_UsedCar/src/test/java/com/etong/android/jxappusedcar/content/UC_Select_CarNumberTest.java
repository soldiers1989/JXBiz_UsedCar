package com.etong.android.jxappusedcar.content;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.jxappusedcar.BuildConfig;
import com.etong.android.jxappusedcar.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @desc (这里用一句话描述这个类的作用)
 * @createtime 2017/1/4 - 17:46
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class UC_Select_CarNumberTest {

    private UC_Select_CarNumber select_CarNumber;
    private TextView mTxtDetails;
    private TextView default_empty_lv_textview;
    private ListView carNumberLv;
    private List<FrameUserInfo.Frame_MyCarItemBean> carList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        FrameUserInfo userInfo = new FrameUserInfo();
        userInfo.setUserPhone("13755174943");
        FrameUserInfo.Frame_MyCarItemBean tempData1 = new FrameUserInfo.Frame_MyCarItemBean();
        tempData1.setVtitle("比亚迪-S8 2009款 2.0 手动尊贵型");
        tempData1.setPlate_no("湘A12340");
        tempData1.setVid(95324);
        FrameUserInfo.Frame_MyCarItemBean tempData2 = new FrameUserInfo.Frame_MyCarItemBean();
        tempData2.setVtitle("别克-昂科雷 2014款 3.6L 四驱智享旗舰型");
        tempData2.setPlate_no("湘A87751");
        tempData2.setVid(639716);
        carList.add(tempData1);
        carList.add(tempData2);
        userInfo.setMyCars(carList);
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        select_CarNumber = Robolectric.setupActivity(UC_Select_CarNumber.class);

        mTxtDetails = (TextView) select_CarNumber.findViewById(R.id.used_car_select_detail_title);
        carNumberLv = (ListView) select_CarNumber.findViewById(R.id.used_car_select_carnumber_lv);
        default_empty_lv_textview = (TextView) carNumberLv.getEmptyView().findViewById(R.id.default_empty_lv_textview);

    }

    @Test
    public void queryMyCar() throws Exception {
        assertTrue(carNumberLv.getAdapter().isEmpty());
        //模拟请求得到的结果数据

        HttpMethod method = new HttpMethod("url", null);

        for (int i = 0; i < 4; i++) {
            int condition = i;//0 = 成功; 1 = 成功(数据为空); 2 = NETWORK_ERROR; 3 = DATA_ERROR;
            JSONObject object = new JSONObject();
            switch (condition) {
                case 0:
                    object.put("flag", "0");
                    object.put("data", carList);
                    object.put("msg", "成功");
                    break;
                case 1:
                    object.put("flag", "0");
                    JSONArray jsonArray = new JSONArray();
                    object.put("data", jsonArray);
                    object.put("msg", "成功");
                    break;
                case 2:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    object.put("msg", "访问失败");
                    break;
                case 3:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    object.put("msg", "数据请求失败!");
                    break;
            }
            method.put(object);

            select_CarNumber.queryMyCar(method);

            if (condition == 1) {
                assertEquals(View.GONE, mTxtDetails.getVisibility());
                assertEquals("车库还空着呢，赶快去添加吧!", default_empty_lv_textview.getText().toString());
                return;
            }

            assertEquals(View.VISIBLE, mTxtDetails.getVisibility());
            assertFalse(carNumberLv.getAdapter().isEmpty());
        }
    }

    /**
     * @desc (测试listview中item的跳转)
     */
    @Test
    public void testClickIntent() throws Exception {

        HttpMethod method = new HttpMethod("url", null);
        JSONObject object = new JSONObject();
        object.put("flag", HttpPublisher.NETWORK_ERROR);
        object.put("msg", "访问失败");
        method.put(object);

        select_CarNumber.queryMyCar(method);

        FrameUserInfo userInfo = FrameEtongApplication.getApplication().getUserInfo();
        for (int i = 0; i < userInfo.getMyCars().size(); i++) {
            carNumberLv.getChildAt(i).performClick();
            ShadowActivity shadowCopActivity = Shadows.shadowOf(select_CarNumber);
            Intent nextCopIntent = shadowCopActivity.getNextStartedActivity();
            assertEquals( UC_Submit_SalesCar.class.getName(),nextCopIntent.getComponent().getClassName());

        }
    }
}