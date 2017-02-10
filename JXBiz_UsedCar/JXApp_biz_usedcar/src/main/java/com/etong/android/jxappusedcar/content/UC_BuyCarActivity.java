package com.etong.android.jxappusedcar.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.jxappusedcar.R;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class UC_BuyCarActivity extends BaseSubscriberActivity {

    /*
      ##################################################################################################
      ##                                        类中的变量                                            ##
      ##################################################################################################
    */
    private UC_BuyCarFragment mFragment = new UC_BuyCarFragment();

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        //注册eventbus
        EventBus.getDefault().registerSticky(this);
        setContentView(R.layout.used_car_activity_buycar_content);

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
        FragmentManager manager = getSupportFragmentManager();

        // 1.开启事务
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.used_car_buycar_frg, mFragment);
        // 提交事务
        transaction.commitAllowingStateLoss();
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

    //得到二手车模块的标识
    @Subscriber(tag=UC_BuyCarFragment.BUY_CAR_IS_REQUEST)
    public void getBack(String tag){
        if(null!=mFragment) {
            if ("1".equals(tag)) {
                mFragment.isRequest();
            }
        }
    }



/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
