package com.etong.android.jxappusedcar.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.FrameEtongApplication;
import com.etong.android.frame.user.FramePersonalLoginActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappusedcar.R;

/**
 * @author : by sunyao
 * @ClassName : UC_SelectWay
 * @Description : (进入预约卖车界面的主界面)
 * @date : 2016/10/5 - 15:42
 */

public class UC_SelectWay extends BaseSubscriberActivity implements View.OnClickListener{


    private TitleBar mTitleBar;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.usedcar_selectway_activity);

        initView();
    }

   /**
    * @desc (使用控件之前使用必要的初始化方法)
    * @user sunyao
    * @createtime 2016/10/6 - 14:52
    * @param
    * @return
    */
    private void initView() {

        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("预约卖车");
        mTitleBar.showNextButton(false);

        View way1_btn = findViewById(R.id.used_car_way1_btn);
        View way2_btn = findViewById(R.id.used_car_way2_btn);

        addClickListener(R.id.used_car_way1_btn);
        addClickListener(R.id.used_car_way2_btn);
    }

    @Override
    public void onClick(View view) {
        // 启动到选择车辆牌照的界面
        Intent i = new Intent();
        if(view.getId() == R.id.used_car_way1_btn) {
            if (FrameEtongApplication.getApplication().isLogin()) {
                i.setClass(this, UC_Select_CarNumber.class);
                startActivity(i);
            } else {
                toastMsg("您还未登录！");
                i.setClass(this, FramePersonalLoginActivity.class);
                startActivity(i);
            }
        } else if(view.getId() == R.id.used_car_way2_btn) {
            i.setClass(this, UC_Submit_SalesCar.class);
            startActivity(i);
        }
    }
}
