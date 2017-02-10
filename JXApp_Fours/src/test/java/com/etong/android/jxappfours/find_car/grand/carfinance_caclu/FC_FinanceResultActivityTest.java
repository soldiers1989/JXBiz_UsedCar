package com.etong.android.jxappfours.find_car.grand.carfinance_caclu;

import android.content.Intent;
import android.widget.TextView;

import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.jxappfours.BuildConfig;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc.Find_car_CarcalculatorUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.*;

/**
 * @desc (融资计算结果 测试用例)
 * @createtime 2017/1/3 - 17:39
 * @Created by xiaoxue.
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21,constants = BuildConfig.class,application = FrameEtongApplication.class)
public class FC_FinanceResultActivityTest {
    private ActivityController<FC_FinanceResultActivity> controller;
    private FC_FinanceResultActivity mActivity;
    private TextView carloan_down_payment;
    private TextView carloan_monthly_payment;

    @Before
    public void setUp() throws Exception {
        Intent intent =new Intent();
        intent.putExtra(FC_FinanceActivity.LOAN_PRICE,"10000");
        controller= Robolectric.buildActivity(FC_FinanceResultActivity.class).withIntent(intent);
        controller.create().start();
        mActivity=controller.get();
        carloan_down_payment = (TextView)mActivity.findViewById(R.id.carloan_down_payment);            // 首付
        carloan_monthly_payment = (TextView)mActivity.findViewById(R.id.carloan_monthly_payment);    // 月供
        assertNotNull(mActivity);
        assertNotNull(carloan_down_payment);
        assertNotNull(carloan_monthly_payment);
    }

    @Test
    public void carcalculatorResult() throws Exception{
       String price= mActivity.getIntent().getStringExtra(FC_FinanceActivity.LOAN_PRICE);
        assertEquals("10000",price);
        mActivity.carcalculatorResult(Long.parseLong(price));

        assertEquals("首付：\n"+"3,000元",carloan_down_payment.getText().toString());
        assertEquals("月供：\n"+"228元", carloan_monthly_payment.getText().toString());
    }



}