package com.etong.android.jxappusedcar.content;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.jxappusedcar.BuildConfig;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.javabean.UC_CarDetailJavabean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * @desc (查看图片的测试用例)
 * @createtime 2016/12/30 - 11:30
 * @Created by xiaoxue.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk=21,constants = BuildConfig.class,application = FrameEtongApplication.class)
public class UC_SelectImageActivityTest {

    private UC_SelectImageActivity mActivity;
    private UC_CarDetailJavabean.PictureConfigBean pictureConfig;
    private TextView image_count_sum;

    @Before
    public void setUp() throws Exception {
        pictureConfig=new UC_CarDetailJavabean.PictureConfigBean();
        pictureConfig.setImgUrlsCount(2);
        List<UC_CarDetailJavabean.PictureConfigBean.ImgUrlsBean> list=new ArrayList<>();
        UC_CarDetailJavabean.PictureConfigBean.ImgUrlsBean bean=new UC_CarDetailJavabean.PictureConfigBean.ImgUrlsBean();
        bean.setImgUrl("http://222.247.51.114:10002/data//2sc/20161123/a358ffe9-72cc-4e73-b4fb-5b6d315c5343.png");
        UC_CarDetailJavabean.PictureConfigBean.ImgUrlsBean bean1=new UC_CarDetailJavabean.PictureConfigBean.ImgUrlsBean();
        bean1.setImgUrl("http://222.247.51.114:10002/data//2sc/20161123/b0397eee-836f-44c5-89c0-48d2dcb78e1b.png");

        list.add(bean);
        list.add(bean1);
        pictureConfig.setImgUrls(list);

        Intent intent=new Intent();
        intent.putExtra(UC_SelectImageActivity.IMAGE_DATA,pictureConfig);

        ActivityController<UC_SelectImageActivity> controller= Robolectric.buildActivity(UC_SelectImageActivity.class).withIntent(intent);
        controller.create().start();
        mActivity=controller.get();
        image_count_sum = (TextView) mActivity.findViewById(R.id.find_car_select_image_txt_count_sum1);
        assertNotNull(mActivity);
        assertNotNull(image_count_sum);
    }

    /**
     * @desc (得到intent的值)
     * @createtime 2016/12/30 - 14:05
     * @author xiaoxue
     */
    @Test
    public void getIntent() throws Exception{
        UC_CarDetailJavabean.PictureConfigBean intentValue= (UC_CarDetailJavabean.PictureConfigBean) mActivity.getIntent().getSerializableExtra(UC_SelectImageActivity.IMAGE_DATA);
        assertEquals(pictureConfig,intentValue);
    }

    /**
     * @desc (点击查看图片的方法)
     * @createtime 2016/12/30 - 14:06
     * @author xiaoxue
     */
    @Test
    public void initSelectImage() throws Exception {
        image_count_sum.setText(pictureConfig.getImgUrls().size()+"");
        assertEquals(2,pictureConfig.getImgUrls().size());
    }

}