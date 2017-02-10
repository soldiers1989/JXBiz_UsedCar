package com.etong.android.jxappcarfinancial.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.jxappcarfinancial.BuildConfig;
import com.etong.android.jxappcarfinancial.R;
import com.etong.android.jxappcarfinancial.bean.CF_OverdueBean;
import com.etong.android.jxappcarfinancial.bean.CF_RecordDetailsBean;
import com.etong.android.jxappcarfinancial.utils.CF_CancelConformDialog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.*;

/**
 * @desc (还款明细 逾期明细 测试用例)
 * @createtime 2016/12/28 - 13:41
 * @Created by xiaoxue.
 */

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class CF_RecordDetailsActivityTest {

    private CF_RecordDetailsActivity activity;
    private TextView foot_phone;
    private int flag = 1;// 还款：1  逾期：2
    private CF_RecordDetailsBean mCF_RecordDetailsBean;
    private CF_OverdueBean mCF_OverdueBean;
    private TextView default_empty_textview;

    @Before
    public void setUp() throws Exception {
        Intent intent = new Intent();
        intent.putExtra("flag", flag);
        switch (flag) {
            case 1:
                mCF_RecordDetailsBean = new CF_RecordDetailsBean();
                mCF_RecordDetailsBean.setFcustid("11668");
                mCF_RecordDetailsBean.setFKdate("2013-12-18");
                mCF_RecordDetailsBean.setRepaySum(5);
                mCF_RecordDetailsBean.setRemainSum(5);
                mCF_RecordDetailsBean.setFPeriodTotal(0);
                intent.putExtra("CF_RecordDetailsBean", mCF_RecordDetailsBean);
                break;
            case 2:
                mCF_OverdueBean = new CF_OverdueBean();
                mCF_OverdueBean.setFKdate("2013-12-18");
                mCF_OverdueBean.setFcustid("11668");
                mCF_OverdueBean.setOverdueTotal(0);
                mCF_OverdueBean.setFPeriodTotal(10 + "");
                mCF_OverdueBean.setOverdueList(null);
                intent.putExtra("CF_OverdueBean", mCF_OverdueBean);
                break;

        }
        ActivityController<CF_RecordDetailsActivity> controller = Robolectric.buildActivity(CF_RecordDetailsActivity.class).withIntent(intent);
        controller.create().start();
        activity = controller.get();
        default_empty_textview = (TextView) activity.findViewById(R.id.default_empty_lv_textview);
        foot_phone = (TextView) activity.findViewById(R.id.cf_txt_foot_phone);//电话
        assertNotNull(activity);
        assertNotNull(foot_phone);
        assertNotNull(default_empty_textview);
    }

    //得到intent的值
    @Test
    public void getIntent() throws Exception {

        flag = activity.getIntent().getIntExtra("flag", -1);
        switch (flag) {
            case 1:
                CF_RecordDetailsBean mDetailsBean = (CF_RecordDetailsBean) activity.getIntent().getSerializableExtra("CF_RecordDetailsBean");
                assertEquals(mCF_RecordDetailsBean, mDetailsBean);
                break;
            case 2:
                CF_OverdueBean mOverdueBean = (CF_OverdueBean) activity.getIntent().getSerializableExtra("CF_OverdueBean");
                assertEquals(mCF_OverdueBean, mOverdueBean);
                break;
        }
    }

    //数据显示
    @Test
    public void initData() throws Exception {
        switch (flag) {
            case 1:
                if (0 == mCF_RecordDetailsBean.getFPeriodTotal()) {
                    default_empty_textview.setText("亲,暂无还款明细");
                    assertEquals("亲,暂无还款明细", default_empty_textview.getText().toString());
                }
                break;
            case 2:
                if (null == mCF_OverdueBean.getOverdueList()) {
                    default_empty_textview.setText("亲,暂无逾期明细");
                    assertEquals("亲,暂无逾期明细", default_empty_textview.getText().toString());
                }
                if (0 == mCF_OverdueBean.getOverdueTotal()) {
                    default_empty_textview.setText("亲,暂无逾期明细");
                    assertEquals("亲,暂无逾期明细", default_empty_textview.getText().toString());
                }
                break;
        }
    }

    //打电话dialog
    @Test
    public void dialog() throws Exception {
        foot_phone.performClick();
        CF_CancelConformDialog dialog = (CF_CancelConformDialog) ShadowDialog.getLatestDialog();
        assertNotNull(dialog);
    }

}