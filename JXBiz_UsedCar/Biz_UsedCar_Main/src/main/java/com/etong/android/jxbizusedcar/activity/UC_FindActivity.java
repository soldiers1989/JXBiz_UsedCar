package com.etong.android.jxbizusedcar.activity;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.fragment.UC_FindFragment;


/**
 * @desc 发现activity ==>第一个fragment
 * @createtime 2016/11/14 - 9:51
 * @author xiaoxue
 */
public class UC_FindActivity extends BaseSubscriberActivity {
/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    UC_FindFragment fragment=new UC_FindFragment();

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_find_activity);

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

        transaction.add(R.id.uc_find_frg,fragment);
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





/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
