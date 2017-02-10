package com.etong.android.jxappfours.models_contrast.adapter;

import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.jxappfours.BuildConfig;
import com.etong.android.jxappfours.models_contrast.javabean.Models_Contrast_SelectBrand;
import com.etong.android.jxappfours.models_contrast.main_content.MC_CollectFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentController;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * @desc (自定义adapter实现按字母排序单元测试用例)
 * @createtime 2016/12/27 - 10:28
 * @Created by wukefan.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class, application = FrameEtongApplication.class)
public class Models_Contrast_SelectBrandAdapterTest {

    private Models_Contrast_SelectBrandAdapter adapter;

    @Before
    public void setUp() throws Exception {

        MC_CollectFragment collectFragment = new MC_CollectFragment();

        SupportFragmentTestUtil.startFragment(collectFragment);
//        SupportFragmentController controller = SupportFragmentController.of(collectFragment);
//        controller.create().attach().start();
//        MC_CollectFragment f = (MC_CollectFragment) controller.get();
//        MC_CollectFragment f = (MC_CollectFragment) controller.get();

        List<Models_Contrast_SelectBrand> list = new ArrayList<>();
        Models_Contrast_SelectBrand t1 = new Models_Contrast_SelectBrand();
        t1.setLetter("A");
        list.add(t1);
        Models_Contrast_SelectBrand t2 = new Models_Contrast_SelectBrand();
        t2.setLetter("B");
        list.add(t2);
        Models_Contrast_SelectBrand t3 = new Models_Contrast_SelectBrand();
        t3.setLetter("B");
        list.add(t3);
        Models_Contrast_SelectBrand t4 = new Models_Contrast_SelectBrand();
        t4.setLetter("#");
        list.add(t4);
        Models_Contrast_SelectBrand t5 = new Models_Contrast_SelectBrand();
        t5.setLetter("2");
        list.add(t5);
        Models_Contrast_SelectBrand t6 = new Models_Contrast_SelectBrand();
        t6.setLetter("O");
        list.add(t6);

//        adapter = new Models_Contrast_SelectBrandAdapter(f.getActivity(), list);
        adapter = new Models_Contrast_SelectBrandAdapter(collectFragment.getActivity(), list);
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值的测试
     */
    @Test
    public void getSectionForPosition() throws Exception {
        int position = adapter.getSectionForPosition(2);
        assertEquals(66, position);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置的测试
     */
    @Test
    public void getPositionForSection() throws Exception {
        int position = adapter.getPositionForSection(66);
        assertEquals(1, position);
    }


}