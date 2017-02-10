package com.etong.android.jxappusedcar.content;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.etong.android.frame.permissions.PermissionsManager;
import com.etong.android.frame.permissions.PermissionsResultAction;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.dialog.UC_CancelConformDialog;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_ImageViewPagerAdapter;
import com.etong.android.jxappusedcar.bean.UC_CarDetail_TitleBean;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc 查看图片页面
 * @createtime 2016/10/11 - 18:31
 * @Created by xiaoxue.
 */
public class UC_SelectImageActivity extends BaseSubscriberActivity {
    private ImageButton back_button;
    private TextView image_count;
    private TextView image_count_sum;
    private ViewPager image_viewpager;
    private TextView title;
    private List<UC_CarDetail_TitleBean.ImgUrlsBean> mUC_SelectImageList;
    private TitleBar mTitleBar;
    private Button consult_detail_bottom;
    private Button appoint_detail_bottom;
    private UC_CancelConformDialog mDialog;
    private String telPhone = "96512";
    private int position;
    private UC_CarDetail_TitleBean mUC_CarDetail_TitleBean;
    private int selectPosition;
    public static int selectImagePosition;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.used_car_select_image);
        initView();
        initData();
        initSelectImage();
    }
    /**
     * 得到车辆详情页传过来的数据
     */
    public void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(UC_CarDetail_Activity.POST_BUNDLE_INFO);

        if (null != (UC_CarDetail_TitleBean) bundle.get(UC_CarDetail_Activity.POST_PIC_DETAIL)) {
            mUC_CarDetail_TitleBean = (UC_CarDetail_TitleBean) bundle.get(UC_CarDetail_Activity.POST_PIC_DETAIL);
            mUC_SelectImageList.addAll(mUC_CarDetail_TitleBean.getImgUrls());//图片list
        }
        selectPosition = bundle.getInt(UC_CarDetail_Activity.POST_CURRENT_PIC_NUM);//位置
    }

    /**
     * @desc 初始化控件
     * @createtime 2016/10/11 - 18:32
     * @author xiaoxue
     */
    public void initView() {
        mTitleBar = new TitleBar(this);
        mTitleBar.showNextButton(true);
        mTitleBar.showBackButton(true);
        mTitleBar.showBottomLin(false);
        mTitleBar.setNextButton("全部图片");
        mTitleBar.setNextTextColor("#cf1c36");
        mTitleBar.setTitle("车身外观");

        // 存放所有图片的List
        mUC_SelectImageList = new ArrayList<>();
        image_count = findViewById(R.id.find_car_select_image_txt_count, TextView.class);
        image_count_sum = findViewById(R.id.find_car_select_image_txt_count_sum, TextView.class);
        image_viewpager = findViewById(R.id.used_car_select_image, ViewPager.class);
        consult_detail_bottom = findViewById(R.id.uc_consult_btn_car_detail_bottom, Button.class);
        appoint_detail_bottom = findViewById(R.id.uc_appoint_btn_car_detail_bottom, Button.class);

        //viewpager页面滑动监听
        image_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //设置页面改变的页面数
                image_count.setText(position + 1 + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        addClickListener(R.id.titlebar_next_button);
        addClickListener(R.id.uc_consult_btn_car_detail_bottom);
        addClickListener(R.id.uc_appoint_btn_car_detail_bottom);

        callPhone();
    }

    /**
     * @desc 打电话
     * @createtime 2016/11/4 - 15:52
     * @author xiaoxue
     */
    private void callPhone() {
        mDialog = new UC_CancelConformDialog(this, false);
        mDialog.setTitle("提示");
        mDialog.setContent("确定拨打电话：" + telPhone + "吗？");
        mDialog.setContentSize(15);
        mDialog.setConfirmButtonClickListener("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //确定操作
                PermissionsManager.getInstance()
                        .requestPermissionsIfNecessaryForResult(
                                UC_SelectImageActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                new PermissionsResultAction() {
                                    @Override
                                    public void onGranted() {
                                        callPhoneDialog();
                                    }

                                    @Override
                                    public void onDenied(String permission) {
                                        toastMsg("授权失败，无法完成操作！");
                                        return;
                                    }
                                });
            }
        });
        mDialog.setCancleButtonClickListener("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    /**
     * @desc 打电话
     * @createtime 2016/10/24 - 11:23
     * @author xiaoxue
     */
    private void callPhoneDialog() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri
                    .parse("tel:"
                            + telPhone));
            startActivity(intent);
        } catch (SecurityException e) {
        }
        mDialog.dismiss();
    }


    //点击查看图片的方法
    protected void initSelectImage() {
//        image_viewpager.setCurrentItem(selectPosition);//设置当前位置
        if (mUC_SelectImageList.size() > 0) {
            image_count.setText(1 + "");
            image_viewpager.setAdapter(new UC_ImageViewPagerAdapter(this, mUC_SelectImageList));
            //图片总数
            image_count_sum.setText(mUC_SelectImageList.size() + "");
            image_viewpager.setCurrentItem(selectPosition);//设置当前位置
        } else {
        }
    }

    @Override
    protected void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.uc_consult_btn_car_detail_bottom) {//免费咨询
            mDialog.show();
        } else if (view.getId() == R.id.uc_appoint_btn_car_detail_bottom) {//预约看车
            Intent intent = new Intent(this, UC_AppointTakeCar_Activity.class);
            intent.putExtra(UC_CarDetail_Activity.POST_TITLE_CAR_DETAIL, mUC_CarDetail_TitleBean);//传javabean到预约看车
            startActivity(intent);
        } else if (view.getId() == R.id.titlebar_next_button) {//全部图片
            Intent intent = new Intent(this, UC_AllPhotoActivity.class);
            intent.putExtra(UC_CarDetail_Activity.POST_TITLE_CAR_DETAIL, mUC_CarDetail_TitleBean);//传javabean到全部图片
            startActivity(intent);
        }
    }

    //得到全部图片的位置
    @Subscriber(tag="pass a position")
    protected void getPosition(int position){
        selectPosition=position;
        image_viewpager.setCurrentItem(selectPosition);//设置当前位置
        image_count.setText(selectPosition+1+ "");
    }
}
