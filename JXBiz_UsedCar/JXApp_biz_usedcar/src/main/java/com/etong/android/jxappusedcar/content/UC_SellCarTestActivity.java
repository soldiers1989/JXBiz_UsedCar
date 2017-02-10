package com.etong.android.jxappusedcar.content;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappusedcar.R;
import com.etong.android.jxappusedcar.utils.UC_StatuBarUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

public class UC_SellCarTestActivity extends BaseSubscriberActivity {

/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/
    UC_SellCarFragment fragment=new UC_SellCarFragment();

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().registerSticky(this);
        setContentView(R.layout.activity_test);
        initView();
        initData();
    }


/*
  ##################################################################################################
  ##                                     初始化View的方法                                         ##
  ##################################################################################################
*/

    /**
     * @desc 初始化Fragment
     * @createtime 2016/10/18 - 14:31
     * @author xiaoxue
     */
    private void initView() {
        FragmentManager manager = getSupportFragmentManager();
        // 1.开启事务
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.used_car_fragment,fragment);
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
    //得到二手车模块传来的标识
    @Subscriber(tag=UC_SellCarFragment.SELL_CAR_BACK_TITLE)
    public void getBack(String tag){
        if(null!=fragment) {
            if ("1".equals(tag)) {
                fragment.showBackButton();
            } else {
                fragment.hideBackButton();
            }
        }
    }


/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
