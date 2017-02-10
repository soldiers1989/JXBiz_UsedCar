package com.etong.android.jxappusedcar.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_ImageViewPagerAdapter;
import com.etong.android.jxappusedcar.javabean.UC_CarDetailJavabean;
import com.etong.android.jxappusedcar.javabean.UC_SelectImageJavabean;
import com.etong.android.jxappusedcar.javabean.UC_SelectImage_JsonData;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 查看图片页面
 * @createtime 2016/10/11 - 18:31
 * @Created by xiaoxue.
 */

public class UC_SelectImageActivity extends BaseSubscriberActivity {

    public static String IMAGE_DATA = "imge data";
    private ImageButton back_button;
    private TextView image_count;
    private TextView image_count_sum;
    private ViewPager image_viewpager;
    private TextView title;
    private List<UC_SelectImageJavabean> mUC_SelectImageList;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.used_car_select_image);

        setSwipeBackEnable(false);

        mUC_SelectImageList=new ArrayList<>();
//        JSONArray array = JSONArray.parseArray(UC_SelectImage_JsonData.getJsonArray());
//        for (int i = 0; i < array.size(); i++) {
//            UC_SelectImageJavabean  mUC_SelectImageJavabean = JSON.toJavaObject(
//                    array.getJSONObject(i), UC_SelectImageJavabean.class);
//            mUC_SelectImageList.add(mUC_SelectImageJavabean);
//        }
        Intent intent = getIntent();
        UC_CarDetailJavabean.PictureConfigBean configBean =
                (UC_CarDetailJavabean.PictureConfigBean) intent.getSerializableExtra(IMAGE_DATA);
        if (configBean != null) {
            List<UC_CarDetailJavabean.PictureConfigBean.ImgUrlsBean> imgUrls = configBean.getImgUrls();
            for (int i=0; i<imgUrls.size(); i++) {
                UC_SelectImageJavabean  mImagebean = new UC_SelectImageJavabean();
                mImagebean.setUrl(imgUrls.get(i).getImgUrl());
                mUC_SelectImageList.add(mImagebean);
            }
        }

        initView();
        initSelectImage();
    }

    /**
     * @desc 初始化控件
     * @createtime 2016/10/11 - 18:32
     * @author xiaoxue
     */

    public void initView() {
        back_button = findViewById(R.id.find_car_select_image_ib_back_button, ImageButton.class);
        image_count = findViewById(R.id.find_car_select_image_txt_count, TextView.class);
        image_count_sum = findViewById(R.id.find_car_select_image_txt_count_sum1, TextView.class);
//        image_viewpager = findViewById(R.id.find_car_select_image_viewpager, ViewPager.class);
        image_viewpager = findViewById(R.id.used_car_select_image, ViewPager.class);

        //viewpager页面滑动监听
        image_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //设置页面改变的页面数
                image_count.setText(position + 1 + "");
//                selectImage = imagList.get(position).getUrl();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        title = findViewById(R.id.used_car_title, TextView.class);

        addClickListener(R.id.find_car_select_image_ib_back_button);
//        title.setText(mUC_SelectImageJavabean.getTitle());

        //图片总数
//        image_count_sum.setText(image.size() + "");


    }


    //点击查看图片的方法
    protected void initSelectImage() {
        image_viewpager.setCurrentItem(0);//设置当前位置
        if (mUC_SelectImageList.size() > 0) {
            image_count.setText(1 + "");
            image_viewpager.setAdapter(new UC_ImageViewPagerAdapter(this,mUC_SelectImageList ));
            //图片总数
            image_count_sum.setText(mUC_SelectImageList.size() + "");
        } else {
            //没有图片就设置radiobutton不能点击
//            mBtns[pos].setClickable(false);
        }
    }


    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if(view.getId()==R.id.find_car_select_image_ib_back_button){
            back();
        }
    }
}
