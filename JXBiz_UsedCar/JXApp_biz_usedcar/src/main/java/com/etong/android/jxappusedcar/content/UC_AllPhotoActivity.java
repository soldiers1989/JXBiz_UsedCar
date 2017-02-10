package com.etong.android.jxappusedcar.content;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.etong.android.frame.permissions.PermissionsManager;
import com.etong.android.frame.permissions.PermissionsResultAction;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.ActivitySkipUtil;
import com.etong.android.frame.utils.SerializableObject;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.frame.widget.dialog.UC_CancelConformDialog;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.adapter.UC_AllImageAdapter;
import com.etong.android.jxappusedcar.bean.UC_CarDetail_TitleBean;
import com.etong.android.jxappusedcar.bean.UC_SelectImageJavabean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoxue
 * @desc 查看全部图片页
 * @createtime 2016/10/24 - 9:04
 */
public class UC_AllPhotoActivity extends BaseSubscriberActivity {
    private TitleBar mTitleBar;
    private GridView gridView;
    private Button consult_bottom;
    private Button appoint_bottom;
    private String title;
    private List<UC_CarDetail_TitleBean.ImgUrlsBean> imageList=new ArrayList<>();//图片list
    private UC_CancelConformDialog mDialog;
    private String telPhone = "96512";
    private UC_CarDetail_TitleBean titleBean;
/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/
    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_uc__all_photo);
        initData();
        initView();
    }

/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/
    /**
     * @desc 初始化控件
     * @createtime 2016/10/24 - 9:19
     * @author xiaoxue
     */
    private void initView() {
        mTitleBar = new TitleBar(this);
        mTitleBar.showNextButton(false);
        mTitleBar.showBackButton(true);
        mTitleBar.showBottomLin(false);
        mTitleBar.setTitle("全部图片");
        gridView = findViewById(R.id.used_car_gridview, GridView.class);

        UC_AllImageAdapter allImageAdapter = new UC_AllImageAdapter(this, imageList);
        gridView.setAdapter(allImageAdapter);
        allImageAdapter.notifyDataSetChanged();
        consult_bottom = findViewById(R.id.uc_consult_btn_car_detail_bottom, Button.class);
        appoint_bottom = findViewById(R.id.uc_appoint_btn_car_detail_bottom, Button.class);

        addClickListener(R.id.uc_consult_btn_car_detail_bottom);
        addClickListener(R.id.uc_appoint_btn_car_detail_bottom);

        callphone();

    }
    /**
     * @desc 打电话
     * @createtime 2016/11/4 - 15:55
     * @author xiaoxue
     */
    private void callphone() {
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
                                UC_AllPhotoActivity.this,
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

/*
  ##################################################################################################
  ##                                     初始化数据的方法                                         ##
  ##################################################################################################
*/

    /**
     * @desc 初始化数据
     * @createtime 2016/10/24 - 10:09
     * @author xiaoxue
     */
    private void initData() {
        Intent intent = getIntent();
        // 从上个页面获取到Javabean
        titleBean =
                (UC_CarDetail_TitleBean) intent.getSerializableExtra(UC_CarDetail_Activity.POST_TITLE_CAR_DETAIL);
        if (titleBean != null) {
            imageList.addAll(titleBean.getImgUrls());
        }
    }

/*
  ##################################################################################################
  ##                                     点击事件的处理                                           ##
  ##################################################################################################
*/

    /**
     * 控件的点击事件
     */
    @Override
    protected void onClick(View view) {
        if (view.getId() == R.id.uc_consult_btn_car_detail_bottom) {//免费咨询
            mDialog.show();
        } else if (view.getId() == R.id.uc_appoint_btn_car_detail_bottom) {//预约看车
            Intent intent=new Intent(this, UC_AppointTakeCar_Activity.class);
            intent.putExtra(UC_CarDetail_Activity.POST_TITLE_CAR_DETAIL, titleBean);//传javabean 到预约看车页
            startActivity(intent);
        }
    }

/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/
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

/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
