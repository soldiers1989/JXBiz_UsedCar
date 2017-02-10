package com.etong.android.jxappfours.find_car.grand.road_assistance;

import android.widget.ListView;

import com.etong.android.jxappfours.BuildConfig;
import com.etong.android.jxappfours.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * @author : by sunyao
 * @ClassName : Find_car_RoadAssistanceActivityTest
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2017/1/4 - 16:07
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class Find_car_RoadAssistanceActivityTest {

    Find_car_RoadAssistanceActivity roadAssisActivity;
    @Before
    public void setUp() throws Exception {
        // 提示可以按 ctrl + 空格
        roadAssisActivity = Robolectric.setupActivity(Find_car_RoadAssistanceActivity.class);//创建Activity
        assertNotNull(roadAssisActivity);//判断是否为空
    }

    @Test
    public void testListView() {
        ListView roadListView = (ListView) roadAssisActivity.findViewById(R.id.road_assistance_lv);
        assertNotNull(roadListView);
        int counts = roadListView.getAdapter().getCount();
        assertEquals(29, counts);
    }
}