package com.etong.android.jxappusedcar.content;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.jxappusedcar.BuildConfig;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.testData.UC_FilterJsonData;

import com.etong.android.jxappusedcar.javabean.UC_FilterBean;
import com.etong.android.jxappusedcar.javabean.UC_World_FiltrateListItemBeam;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @desc (筛选测试用例)
 * @createtime 2017/1/3 - 9:32
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class UC_World_FiltrateActivityTest {

    private UC_World_FiltrateActivity filtrateActivity;
    private ListView mListView;
    private ViewGroup mWithOutNetWorkView;
    private ImageView mWithOutNetWorkImg;
    private ViewGroup mContentView;
    private UC_FilterBean mFilterBean;
    private String title = "国产";
    private String key = "1";

    @Before
    public void setUp() throws Exception {
        Intent intent = new Intent();
        mFilterBean = new UC_FilterBean();

        Map<String, String> country = new HashMap<String, String>();
        country.put(title, key);
        mFilterBean.setCountry(country);
        intent.putExtra(UC_World_FiltrateActivity.FILTER_BEAN, mFilterBean);
        ActivityController<UC_World_FiltrateActivity> controller =
                Robolectric.buildActivity(UC_World_FiltrateActivity.class, intent).create().start();
        filtrateActivity = controller.get();
//        filtrateActivity = Robolectric.setupActivity(UC_World_FiltrateActivity.class);
        mContentView = (ViewGroup) filtrateActivity.findViewById(R.id.uc_world_rl_filter);
        mListView = (ListView) filtrateActivity.findViewById(R.id.uc_world_lv_filtrate);
        assertTrue(mListView.getAdapter().isEmpty());
        //没网布局
        mWithOutNetWorkView = (ViewGroup) filtrateActivity.findViewById(R.id.default_empty_content);
        mWithOutNetWorkImg = (ImageView) filtrateActivity.findViewById(R.id.default_empty_img);

    }

    /**
     * @desc (测试Intent传来的值是否正确)
     */
    @Test
    public void testIntentValue() throws Exception {
        UC_FilterBean getBean =
                (UC_FilterBean) filtrateActivity.getIntent().getSerializableExtra(UC_World_FiltrateActivity.FILTER_BEAN);
        assertEquals(mFilterBean, getBean);
    }

    /**
     * @desc (测试筛选数据字典得到数据后的操作)
     */
    @Test
    public void setFilterListInfo() throws Exception {

        //模拟请求得到的结果数据
        HttpMethod method = new HttpMethod("url", null);

        for (int i = 0; i < 3; i++) {
            int condition = i;//0 = 成功;1 = NETWORK_ERROR; 2 = DATA_ERROR;
            JSONObject object = new JSONObject();
            switch (condition) {
                case 0:
                    object = JSON.parseObject(UC_FilterJsonData.jsonStr);
                    break;
                case 1:
                    object.put("flag", HttpPublisher.NETWORK_ERROR);
                    object.put("msg", "访问失败");
                    break;
                case 2:
                    object.put("flag", HttpPublisher.DATA_ERROR);
                    object.put("msg", "数据请求失败!");
                    break;
            }
            method.put(object);

            filtrateActivity.setFilterListInfo(method);

            switch (condition) {
                case 0:
                    assertEquals(View.VISIBLE, mContentView.getVisibility());
                    assertEquals(View.GONE, mWithOutNetWorkView.getVisibility());
                    assertFalse(mListView.getAdapter().isEmpty());

                    for (int j = 1; j < mListView.getAdapter().getCount(); j++) {
                        //注意有头部的ListView的适配器被HeaderViewListAdapter封装了
                        UC_World_FiltrateListItemBeam tempBean =
                                (UC_World_FiltrateListItemBeam) ((HeaderViewListAdapter) mListView.getAdapter()).getItem(j);
                        for (int n = 0; n < tempBean.getMap().size(); n++) {
                            if (tempBean.getParaName().equals("国别") && tempBean.getMap().get(n).getKey().equals(key)) {
                                assertTrue(tempBean.getMap().get(n).isSelect());
                                break;
                            }
                            assertFalse(tempBean.getMap().get(n).isSelect());
                        }
                    }

                    break;
                default:
                    assertEquals(View.GONE, mContentView.getVisibility());
                    assertEquals(View.VISIBLE, mWithOutNetWorkView.getVisibility());
                    break;
            }
        }
    }

    /**
     * @desc (测试设置选中项)
     */
    @Test
    public void setItemSelect() throws Exception {
        Map<String, UC_World_FiltrateListItemBeam> map = new HashMap();
        UC_World_FiltrateListItemBeam bean1 = new UC_World_FiltrateListItemBeam();
        List<UC_World_FiltrateListItemBeam.MapBean> list = new ArrayList<>();
        UC_World_FiltrateListItemBeam.MapBean mapBean1 = new UC_World_FiltrateListItemBeam.MapBean();
        mapBean1.setKey("0");
        mapBean1.setValue("手自一体");
        list.add(mapBean1);
        UC_World_FiltrateListItemBeam.MapBean mapBean2 = new UC_World_FiltrateListItemBeam.MapBean();
        mapBean2.setKey("1");
        mapBean2.setValue("自动档");
        list.add(mapBean2);
        UC_World_FiltrateListItemBeam.MapBean mapBean3 = new UC_World_FiltrateListItemBeam.MapBean();
        mapBean3.setKey("2");
        mapBean3.setValue("手动档");
        list.add(mapBean3);
        bean1.setMap(list);
        bean1.setParaName("变速箱");
        map.put(bean1.getParaName(), bean1);
        for (int i = 0; i < map.get("变速箱").getMap().size(); i++) {
            assertFalse(map.get("变速箱").getMap().get(i).isSelect());
        }
        Map<String, UC_World_FiltrateListItemBeam> resultMap = filtrateActivity.setItemSelect(map, "变速箱", "自动档");
        for (int i = 0; i < map.get("变速箱").getMap().size(); i++) {
            if (map.get("变速箱").getMap().get(i).getValue().equals("自动档")) {
                assertTrue(map.get("变速箱").getMap().get(i).isSelect());
                break;
            }
            assertFalse(map.get("变速箱").getMap().get(i).isSelect());
        }
    }

}