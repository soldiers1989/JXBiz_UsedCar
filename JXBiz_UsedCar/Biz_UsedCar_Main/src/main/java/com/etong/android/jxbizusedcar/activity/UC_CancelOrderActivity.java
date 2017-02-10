package com.etong.android.jxbizusedcar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.etong.android.frame.publisher.HttpMethod;
import com.etong.android.frame.publisher.HttpPublisher;
import com.etong.android.frame.request_init.UC_HttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.user.UC_UserProvider;
import com.etong.android.jxbizusedcar.R;

import org.simple.eventbus.Subscriber;

/**
 * @author xiaoxue
 * @desc 取消订单原因页
 * @createtime 2016/11/10 - 14:04
 */
public class UC_CancelOrderActivity extends BaseSubscriberActivity {
    private ImageView cancle_image;
    private RadioButton fill_wrong;
    private RadioButton no_buy;
    private RadioButton other;
    private Button cancel_finish;
    private String f_cid;
    private UC_UserProvider mUsedCarProvider;
    private int tag;
    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_cancel_order);
        mUsedCarProvider = UC_UserProvider.getInstance();
        mUsedCarProvider.initalize(HttpPublisher.getInstance());
        //得到订单中心传来的订单id
        Intent intent = getIntent();
        f_cid = intent.getStringExtra("f_cid");
        tag = intent.getIntExtra("tag", 11);
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
        cancle_image = findViewById(R.id.uc_iv_cancle, ImageView.class);
        fill_wrong = findViewById(R.id.uc_cancel_fill_wrong, RadioButton.class);
        no_buy = findViewById(R.id.uc_cancel_no_buy, RadioButton.class);
        other = findViewById(R.id.uc_cancel_other, RadioButton.class);
        cancel_finish = findViewById(R.id.uc_cancel_finish, Button.class);
        addClickListener(R.id.uc_iv_cancle);
        addClickListener(R.id.uc_cancel_finish);
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
        if (view.getId() == R.id.uc_cancel_finish) {//完成按钮
            //请求取消订单的方法
            mUsedCarProvider.deleteOrder(f_cid);
        } else if (view.getId() == R.id.uc_iv_cancle) {//取消按钮
            finish();
            overridePendingTransition(0, R.anim.push_bottom_out);       // 退出动画
        }
    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    /**
     * @desc 得到取消订单的数据
     * @createtime 2016/11/11 - 15:45
     * @author xiaoxue
     */
    @Subscriber(tag = UC_HttpTag.DELETE_ORDER)
    protected void getOrder(HttpMethod method) {
        String msg = method.data().getString("msg");
        String status = method.data().getString("status");
        String data = method.data().getString("data");
        if (status.equals("true")) {
            toastMsg("取消订单成功");
            mEventBus.post("", tag + "");
            finish();
        } else {
            toastMsg("取消订单失败");
        }
    }



/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
