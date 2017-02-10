package com.etong.android.jxappcarassistant.violation_query.main_content;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.jxappcarassistant.BuildConfig;
import com.etong.android.jxappcarassistant.R;
import com.etong.android.jxappcarassistant.violation_query.bean.CA_ViolationQueryBean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.*;

/**
 * @desc (违章查询页测试用例)
 * @createtime 2017/1/5 - 14:03
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class CA_ViolationOfTheQueryActivityTest {


    private CA_ViolationOfTheQueryActivity activity;
    private String jsonStr = "{\"lsnum\":\"A1E79H\",\n" +
            "\"usercarid\":\"4966276\",\n" +
            "\"lsprefix\":\"湘\",\n" +
            "\"carorg\":\"changsha\",\n" +
            "\"list\":[{\"score\":\"3\"," +
            "\"address\":\"长沙市枫林路的白云路路口\"," +
            "\"agency\":\"\"," +
            "\"illegalid\":\"6262825\"," +
            "\"price\":\"100\"," +
            "\"time\":\"2016-09-24 23:22:49\"," +
            "\"legalnum\":\"1345\"," +
            "\"content\":\"机动车违反禁止标线指示的\"}]}";
    private CA_ViolationQueryBean mCA_ViolationQueryBean;
    private ListView listview_violation;

    @Before
    public void setUp() throws Exception {

        Intent intent = new Intent();
        JSONObject json = JSONObject.parseObject(jsonStr);
        mCA_ViolationQueryBean = (CA_ViolationQueryBean) JSON.toJavaObject(json, CA_ViolationQueryBean.class);
        intent.putExtra("CA_ViolationQueryBean", mCA_ViolationQueryBean);
        ActivityController<CA_ViolationOfTheQueryActivity> controller =
                Robolectric.buildActivity(CA_ViolationOfTheQueryActivity.class, intent).create().start();

        activity = controller.get();
        listview_violation = (ListView) activity.findViewById(R.id.ca_lv_violation);  //初始化ListView
    }

    /**
     * @desc (测试Intnet传值)
     */
    @Test
    public void testIntentValue() throws Exception {
        CA_ViolationQueryBean testBean = (CA_ViolationQueryBean) activity.getIntent().getSerializableExtra("CA_ViolationQueryBean");
        assertEquals(mCA_ViolationQueryBean, testBean);
    }

    /**
     * @desc (测试通过传过来的数据显示的视图)
     */
    @Test
    public void testShowView() throws Exception {
        assertFalse(listview_violation.getAdapter().isEmpty());
        assertEquals(View.GONE, listview_violation.getEmptyView().getVisibility());

        //传过来的javabean为空
        Intent intent2 = new Intent();
        intent2.putExtra("CA_ViolationQueryBean", new CA_ViolationQueryBean());
        ActivityController<CA_ViolationOfTheQueryActivity> controller2 =
                Robolectric.buildActivity(CA_ViolationOfTheQueryActivity.class, intent2).create().start();
        CA_ViolationOfTheQueryActivity activity2 = controller2.get();
        ListView listview_violation2 = (ListView) activity2.findViewById(R.id.ca_lv_violation);  //初始化ListView

        assertTrue(listview_violation2.getAdapter().isEmpty());
        assertEquals(View.VISIBLE, listview_violation2.getEmptyView().getVisibility());

        //传过来的javabean的list为空
        Intent intent3 = new Intent();
        String jsonStr2 = "{\"lsnum\":\"A1E79H\",\n" +
                "\"usercarid\":\"4966276\",\n" +
                "\"lsprefix\":\"湘\",\n" +
                "\"carorg\":\"changsha\",\n" +
                "\"list\":\"\"}";
        JSONObject json2 = JSONObject.parseObject(jsonStr2);
        CA_ViolationQueryBean mCA_ViolationQueryBean2 = (CA_ViolationQueryBean) JSON.toJavaObject(json2, CA_ViolationQueryBean.class);
        intent3.putExtra("CA_ViolationQueryBean", mCA_ViolationQueryBean2);
        ActivityController<CA_ViolationOfTheQueryActivity> controller3 =
                Robolectric.buildActivity(CA_ViolationOfTheQueryActivity.class, intent3).create().start();
        CA_ViolationOfTheQueryActivity activity3 = controller3.get();
        ListView listview_violation3 = (ListView) activity3.findViewById(R.id.ca_lv_violation);  //初始化ListView

        assertTrue(listview_violation3.getAdapter().isEmpty());
        assertEquals(View.VISIBLE, listview_violation3.getEmptyView().getVisibility());
    }

    /**
     * @desc (测试ListView的头部显示)
     */
    @Test
    public void testListHeadView() throws Exception {
        TextView ca_tv_violation = (TextView) listview_violation.getAdapter().getView(0, null, null).findViewById(R.id.ca_tv_violation);

        assertEquals("您的车辆违章查询结果：\n" + "共计" + 1 + "条违法行为，扣"
                + 3 + "分，罚款金额" + 100 + "元（人民币）", ca_tv_violation.getText().toString());
    }

    /**
     * @desc (测试ListView的item的点击事件跳转)
     */
    @Test
    public void testListItemClickIntent() throws Exception {

        for (int i = 1; i < listview_violation.getCount(); i++) {
            System.out.print("--------------------" + i);
            View itemView = listview_violation.getAdapter().getView(i, null, null);
            itemView.findViewById(R.id.ca_ll_violation_item).performClick();
            ShadowActivity shadowItemActivity = Shadows.shadowOf(activity);
            Intent nextItemIntent = shadowItemActivity.getNextStartedActivity();
            Assert.assertEquals(CA_ViolationQueryDetailsActivity.class.getName(), nextItemIntent.getComponent().getClassName());
        }
    }

}