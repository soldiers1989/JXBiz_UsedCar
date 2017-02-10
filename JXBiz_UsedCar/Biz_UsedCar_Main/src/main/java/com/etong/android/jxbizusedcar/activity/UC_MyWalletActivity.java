package com.etong.android.jxbizusedcar.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxbizusedcar.R;

public class UC_MyWalletActivity extends BaseSubscriberActivity {

    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/
    private TitleBar mTitleBar;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_my_wallet);

        initView();
        initData();
    }


/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/

    /**
     * 请使用快捷模板生成注释
     */
    private void initView() {

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("我的钱包");
        mTitleBar.showBottomLin(false);

        ImageView mNullImage = findViewById(R.id.uc_empty_img, ImageView.class);
        mNullImage.setBackgroundResource(R.mipmap.uc_ic_no_cotent_img);
    }

/*
  ##################################################################################################
  ##                                     初始化数据的方法                                         ##
  ##################################################################################################
*/

    /**
     * 请使用快捷模板生成注释
     */
    private void initData() {

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

    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/





/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
