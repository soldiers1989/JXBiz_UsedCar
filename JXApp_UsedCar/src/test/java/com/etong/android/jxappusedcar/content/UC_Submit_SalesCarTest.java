package com.etong.android.jxappusedcar.content;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameUserInfo;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_Add_VechileStyle;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_VechileType;
import com.etong.android.jxappusedcar.BuildConfig;
import com.etong.android.jxappusedcar.R;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.util.ActivityController;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

/**
 * @desc (预约卖车测试用例)
 * @createtime 2017/1/3 - 11:52
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)

public class UC_Submit_SalesCarTest {

    private UC_Submit_SalesCar salesCarActivity;
    private LinearLayout resultLayout;
    private TextView detailTitle;
    private LinearLayout selectPlateLayout;
    private TextView selectPlate;
    private Button plateBtn;
    private EditText plateEdt;
    private EditText mNameEdt;
    private EditText mPhpneEdt;
    private EditText mRemarkEdt;
    private Button mSubmitBtn;
    private TextView carInfoTxt;
    private DrawerLayout submitSalesCarDl;
    private ViewGroup carTypeContent;
    private ScrollView scrollView;

    @Before
    public void setUp() throws Exception {
        salesCarActivity = Robolectric.setupActivity(UC_Submit_SalesCar.class);

        scrollView = (ScrollView) salesCarActivity.findViewById(R.id.used_car_select_sv_scroll);
        resultLayout = (LinearLayout) salesCarActivity.findViewById(R.id.used_car_select_ll_result);

        detailTitle = (TextView) salesCarActivity.findViewById(R.id.used_car_select_txt_detail_title);
        selectPlateLayout = (LinearLayout) salesCarActivity.findViewById(R.id.used_car_select_ll_plate);
        selectPlate = (TextView) salesCarActivity.findViewById(R.id.used_car_submit_car_number_text);
        plateBtn = (Button) salesCarActivity.findViewById(R.id.used_car_select_btn_type);
        plateEdt = (EditText) salesCarActivity.findViewById(R.id.used_car_select_edt_plate);
        mNameEdt = (EditText) salesCarActivity.findViewById(R.id.used_car_select_edt_name);
        mPhpneEdt = (EditText) salesCarActivity.findViewById(R.id.used_car_select_edt_phone);
        mRemarkEdt = (EditText) salesCarActivity.findViewById(R.id.used_car_select_edt_remark);
        mSubmitBtn = (Button) salesCarActivity.findViewById(R.id.used_car_select_btn_commit);

        carInfoTxt = (TextView) salesCarActivity.findViewById(R.id.used_car_select_car_car_info_text);
        // 初始化侧滑组件
        submitSalesCarDl = (DrawerLayout) salesCarActivity.findViewById(R.id.used_car_select_car_info_drawerlayout);
        carTypeContent = (ViewGroup) salesCarActivity.findViewById(R.id.used_car_select_car_car_info_content);

    }

    @Test
    @Ignore
    public void testShowPopupWindow() throws Exception {
        plateBtn.performClick();

    }

    @Test
    @Ignore
    public void testopenDrawer() throws Exception {
        carTypeContent.performClick();

    }

    /**
     * @desc (测试Intent传过来的值以及后续操作)
     */
    @Test
    public void testIntentValue() throws Exception {
        Intent intent = new Intent();
        intent.putExtra("isSelectMyCar", true);
        FrameUserInfo.Frame_MyCarItemBean mMyCarItemBean = new FrameUserInfo.Frame_MyCarItemBean();
        mMyCarItemBean.setVid(769596);
        mMyCarItemBean.setCarsetId(4922);
        mMyCarItemBean.setVtitle("AC Schnitzer X52015款ACS35 35i");
        mMyCarItemBean.setPlate_no("湘A9543G");
        intent.putExtra("MyCar", mMyCarItemBean);

        ActivityController<UC_Submit_SalesCar> carActivityController =
                Robolectric.buildActivity(UC_Submit_SalesCar.class, intent).create().start();

        UC_Submit_SalesCar testActivity = carActivityController.get();

        FrameUserInfo.Frame_MyCarItemBean getBean =
                (FrameUserInfo.Frame_MyCarItemBean) testActivity.getIntent().getSerializableExtra("MyCar");
        //判断Intent传过来的值是否一致
        assertEquals(mMyCarItemBean, getBean);
        assertTrue(testActivity.getIntent().getBooleanExtra("isSelectMyCar", false));

        Button plateBtn2 = (Button) testActivity.findViewById(R.id.used_car_select_btn_type);
        EditText plateEdt2 = (EditText) testActivity.findViewById(R.id.used_car_select_edt_plate);
        TextView carInfoTxt2 = (TextView) testActivity.findViewById(R.id.used_car_select_car_car_info_text);
        ViewGroup carTypeContent2 = (ViewGroup) testActivity.findViewById(R.id.used_car_select_car_car_info_content);

        //判断相应的设置是否正确
        assertEquals("湘", plateBtn2.getText().toString());
        assertEquals("A9543G", plateEdt2.getText().toString());
        assertEquals("AC Schnitzer X52015款ACS35 35i", carInfoTxt2.getText().toString());

        assertFalse(plateBtn2.isEnabled());
        assertFalse(plateEdt2.isEnabled());
        assertFalse(carTypeContent2.isClickable());
    }


    /**
     * @desc (提交预约操作测试)
     */
    @Test
    public void onCommit() throws Exception {
        Toast toast = ShadowToast.getLatestToast();
        // 判断Toast尚未弹出
        assertNull(toast);

        salesCarActivity.onCommit();
        toast = ShadowToast.getLatestToast();
        // 判断Toast已经弹出
        assertNotNull(toast);
        // 获取Shadow类进行验证
        ShadowToast shadowToast = shadowOf(toast);
        assertEquals(Toast.LENGTH_SHORT, shadowToast.getDuration());
        assertEquals("请选车！", shadowToast.getTextOfLatestToast());

        Models_Contrast_VechileType selectCar = new Models_Contrast_VechileType();
        selectCar.setVid(769596);
        selectCar.setCarset(4922);
        selectCar.setCarsetTitle("AC Schnitzer X5");
        selectCar.setBrand("AC Schnitzer");
        salesCarActivity.getCarTypeInfo(selectCar);
        salesCarActivity.onCommit();
        assertEquals("请输入正确车牌号！", shadowToast.getTextOfLatestToast());

        plateEdt.setText("A89");
        salesCarActivity.onCommit();
        assertEquals("请输入正确车牌号！", shadowToast.getTextOfLatestToast());

        plateEdt.setText("A8951N");
        salesCarActivity.onCommit();
        assertEquals("请输入姓名！", shadowToast.getTextOfLatestToast());

        mNameEdt.setText("   ");
        salesCarActivity.onCommit();
        assertEquals("请输入姓名！", shadowToast.getTextOfLatestToast());

        mNameEdt.setText("哈哈哈");
        salesCarActivity.onCommit();
        assertEquals("请输入正确的手机号！", shadowToast.getTextOfLatestToast());

        mPhpneEdt.setText("1375455");
        salesCarActivity.onCommit();
        assertEquals("请输入正确的手机号！", shadowToast.getTextOfLatestToast());

        mPhpneEdt.setText("11155174943");
        salesCarActivity.onCommit();
        assertEquals("请输入正确的手机号！", shadowToast.getTextOfLatestToast());

        mPhpneEdt.setText("13755174943");
        assertTrue(mSubmitBtn.isClickable());
        salesCarActivity.onCommit();
        assertFalse(mSubmitBtn.isClickable());
        mSubmitBtn.setClickable(true);
    }

    /**
     * @desc (预约卖车请求结果测试)
     */
    @Test
    public void orderSellCarResult() throws Exception {
        //模拟请求得到的结果数据
        HttpMethod method = new HttpMethod("url", null);

        for (int i = 0; i < 4; i++) {
            String msg = null;
            int condition = i;//0 = 成功;1 = NETWORK_ERROR; 2 = DATA_ERROR;
            JSONObject object = new JSONObject();
            switch (condition) {
                case 0:
                    object.put("flag", "0");
                    object.put("data", "1000010");
                    msg = "提交成功~";
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    object.put("msg", "提交失败，请检查网络！");
                    msg = "提交失败，请检查网络！";
                    break;
                case 2:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    object.put("msg", "数据请求失败!");
                    msg = "数据请求失败!";
                    break;
                case 3:
                    object.put("flag", "1");
                    object.put("msg", "您已提交过预约卖车订单，请勿重复提交~");
                    msg = "您已提交过预约卖车订单，请勿重复提交~";
                    break;
            }
            method.put(object);

            Toast toast = ShadowToast.getLatestToast();
            // 判断Toast尚未弹出
            assertNull(toast);

            salesCarActivity.orderSellCarResult(method);

            if (condition == 0) {
                assertEquals(View.VISIBLE, resultLayout.getVisibility());
                assertEquals(View.GONE, scrollView.getVisibility());
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

    /**
     * @desc (取缓存设置测试)
     */
    @Test
    public void setUserInfo() throws Exception {
        assertTrue(TextUtils.isEmpty(mPhpneEdt.getText().toString()));
        assertTrue(TextUtils.isEmpty(mNameEdt.getText().toString()));
        FrameUserInfo userInfo = new FrameUserInfo();

        userInfo.setUserPhone("18229938144");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        salesCarActivity.setUserInfo();
        assertEquals("18229938144", mPhpneEdt.getText().toString());
        assertTrue(TextUtils.isEmpty(mNameEdt.getText().toString()));

        userInfo.setUserName("呵呵");
        FrameEtongApplication.getApplication().setUserInfo(userInfo);
        salesCarActivity.setUserInfo();
        assertEquals("18229938144", mPhpneEdt.getText().toString());
        assertEquals("呵呵", mNameEdt.getText().toString());
    }

}