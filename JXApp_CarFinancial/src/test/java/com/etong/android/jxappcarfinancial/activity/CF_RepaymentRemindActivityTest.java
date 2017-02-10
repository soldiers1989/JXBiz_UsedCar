package com.etong.android.jxappcarfinancial.activity;

import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FrameRepayRemindInfo;
import com.etong.android.frame.widget.pulltofresh.PullToRefreshListView;
import com.etong.android.jxappcarfinancial.BuildConfig;
import com.etong.android.jxappcarfinancial.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @desc (还款提醒页 测试用例)
 * @createtime 2017/1/3 - 15:21
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk=21,constants = BuildConfig.class,application = FrameEtongApplication.class)
public class CF_RepaymentRemindActivityTest {
    private CF_RepaymentRemindActivity mActivity;
    private PullToRefreshListView lv_record;

    @Before
    public void setUp() throws Exception {

        ActivityController<CF_RepaymentRemindActivity> controller=Robolectric.buildActivity(CF_RepaymentRemindActivity.class);
        controller.create().start();
        mActivity=controller.get();
        lv_record =(PullToRefreshListView)mActivity.findViewById(R.id.cf_lv_record);  //ListView
        assertTrue(lv_record.getRefreshableView().getAdapter().isEmpty());
//        assertEquals(0,lv_record.getRefreshableView().getAdapter().getCount());
        assertNotNull(mActivity);
        assertNotNull(lv_record);

        FrameRepayRemindInfo info=new FrameRepayRemindInfo();
        info.setData("您的还款3天后即将到期，请及时还款！");
        info.setFDate("2012-10-19");
        info.setFPeriod("第1期");
        info.setFPerRev("应还1709");
        info.setTime("2016-12-06 10:44:05");
        FrameEtongApplication.getApplication().addRepaymentRemindInfo(info);
    }

    //从本地获取数据
    @Test
    public void getRepaymentData() throws Exception{
        mActivity.getRepaymentData();
//        assertEquals(0,lv_record.getRefreshableView().getAdapter().getCount());
        List<FrameRepayRemindInfo> infoList = FrameEtongApplication.getApplication().getRepaymentRemindInfo();
        assertNotNull(infoList);
        assertFalse(lv_record.getRefreshableView().getAdapter().isEmpty());
    }
}