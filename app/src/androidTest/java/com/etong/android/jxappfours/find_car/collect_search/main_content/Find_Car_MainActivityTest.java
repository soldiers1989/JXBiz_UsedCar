package com.etong.android.jxappfours.find_car.collect_search.main_content;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.etong.android.jxappfours.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author : by sunyao
 * @ClassName : Find_Car_MainActivityTest
 * @Description : (这里用一句话描述这个类的作用)
 * @date : 2016/12/29 - 10:57
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class Find_Car_MainActivityTest {

    @Rule
    ActivityTestRule<Find_Car_MainActivity> mainTest = new ActivityTestRule<Find_Car_MainActivity>(Find_Car_MainActivity.class);

    @Test
    public void mainTest() {
        // 点击
        Espresso.onView(ViewMatchers.withId(R.id.find_car_rb_vechile_brand)).perform(ViewActions.click());
    }
}