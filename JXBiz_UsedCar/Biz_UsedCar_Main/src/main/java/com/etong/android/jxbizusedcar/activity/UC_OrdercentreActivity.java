package com.etong.android.jxbizusedcar.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.L;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxbizusedcar.R;
import com.etong.android.jxbizusedcar.adapter.UC_OrderViewPagerAdapter;
import com.etong.android.jxbizusedcar.fragment.UC_Identify_OrderAllFragment;
import com.etong.android.jxbizusedcar.fragment.UC_Identify_OrderDoingFragment;
import com.etong.android.jxbizusedcar.fragment.UC_Identify_OrderDoneFragment;
import com.etong.android.jxbizusedcar.fragment.UC_Identify_OrderRefundFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoxue
 * @desc 订单中心页
 * @createtime 2016/11/9 - 12:34
 */
public class UC_OrdercentreActivity extends BaseSubscriberActivity {
    private TabLayout order_choices;
    private ViewPager order_content;
    private UC_OrderViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> mFragments;
    private List<String> titleList;
    public static boolean isFont;

    /*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/


    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.uc_activity_orders_centre);

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
        TitleBar mTitleBar = new TitleBar(this);
        mTitleBar.setTitleTextColor("#ffffff");//设置title颜色
        mTitleBar.setTitle("订单中心");
        mTitleBar.showNextButton(false);
        mTitleBar.setmTitleBarBackground("#cf1c36");//设置titlebar背景色

        order_choices = findViewById(R.id.uc_tl_order_choices, TabLayout.class);  //tab
        order_content = findViewById(R.id.uc_vp_order_content, ViewPager.class);  //viewpager

    }

/*
  ##################################################################################################
  ##                                     初始化数据的方法                                         ##
  ##################################################################################################
*/

    /**
     * 初始化数据
     */
    private void initData() {
        mFragments = new ArrayList<>();       //存放4个fragment的list
        titleList = new ArrayList<>();    //存放title的list
        titleList.add("全部");
        titleList.add("进行中");
        titleList.add("已完成");
        titleList.add("退款详情");
//        order_choices.addTab(order_choices.newTab().setText("全部"));
//        order_choices.addTab(order_choices.newTab().setText("待付款"));
//        order_choices.addTab(order_choices.newTab().setText("已完成"));
//        order_choices.addTab(order_choices.newTab().setText("退款详情"));
        //添加4个fragment
        mFragments.add(new UC_Identify_OrderAllFragment());
        mFragments.add(new UC_Identify_OrderDoingFragment());
        mFragments.add(new UC_Identify_OrderDoneFragment());
        mFragments.add(new UC_Identify_OrderRefundFragment());

        //viewpager 适配器
        mViewPagerAdapter = new UC_OrderViewPagerAdapter(getSupportFragmentManager(), mFragments, titleList);

        order_content.setAdapter(mViewPagerAdapter);
        //和ViewPager联动起来
        order_choices.setupWithViewPager(order_content);
        order_choices.setTabsFromPagerAdapter(mViewPagerAdapter);

        // 设置Tab的选择监听
        order_choices.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 每当我们选择了一个Tab就将ViewPager滚动至对应的Page
                order_content.setCurrentItem(tab.getPosition(), true);

//                mFragments.get(tab.getPosition()).updataFrgmentContent();

                switch (tab.getPosition()) {
                    case 0:
                        ((UC_Identify_OrderAllFragment)mFragments.get(0)).updataFrgmentContent("");
                        break;
                    case 1:
                        ((UC_Identify_OrderDoingFragment)mFragments.get(1)).updataFrgmentContent("");
                        break;
                    case 2:
                        ((UC_Identify_OrderDoneFragment)mFragments.get(2)).updataFrgmentContent("");
                        break;
                    case 3:
                        ((UC_Identify_OrderRefundFragment)mFragments.get(3)).updataFrgmentContent();
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
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

    /**
     * @desc 设置选中的fragment
     * @createtime 2016/11/11 - 10:36
     * @author xiaoxue
     */
    public int setCurrentFragment(int position){
        if(position >=0  && position<4){
            order_content.setCurrentItem(position);
        }
        return position;
    }

    @Override
    protected void onStop() {
        super.onStop();
        L.d("onStop---------界面被隐藏了");
    }

    @Override
    protected void onResume() {
        super.onResume();

        isFont = true;
        L.d("onResume ------ 界面被重现");
    }

    @Override
    protected void onStart() {
        super.onStart();
        L.d("onStart ------界面被重现");
    }

    @Override
    protected void onPause() {
        super.onPause();
        isFont = false;
        L.d("onPause ------界面被隐藏");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFont = false;
        L.d("onDestroy ------界面被隐藏");
    }

    /*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
