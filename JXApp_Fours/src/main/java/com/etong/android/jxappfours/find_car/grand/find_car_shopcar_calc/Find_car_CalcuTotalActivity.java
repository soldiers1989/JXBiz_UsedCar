package com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.utils.AddCommaToMoney;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.bean.FC_CalcuCarPriceBean;

import org.simple.eventbus.Subscriber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellison.Sun on 2016/8/25.
 *
 * 购车计算器界面
 */
public class Find_car_CalcuTotalActivity extends BaseSubscriberActivity {

    private FragmentManager fragment_manager;
    private FragmentTransaction transaction;

    List<BaseSubscriberFragment> listFrag;      // 存放Fragment的List
    private BaseSubscriberFragment mFragments[];    // 当前显示的Fragment

    FC_CalcuFullpayFrg fullPay;     // 全款页面
    FC_CalcuLoanFrg loan;            // 贷款页面
    FC_CalcuCarPriceBean carInfoBean;      // 从上个界面获取到的字段
    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.fc_calcu_total_cotent);

        // 从上个界面获取到的值
        Intent i = getIntent();
        carInfoBean = (FC_CalcuCarPriceBean) i.getSerializableExtra(FrameHttpTag.TRANSFER_CAR_PRICE_INFO);

        // 设置TitleBar
        TitleBar titleBar = new TitleBar(Find_car_CalcuTotalActivity.this);
        titleBar.setTitle("购车计算器");
        titleBar.setmTitleBarBackground("#FFFFFF");
        titleBar.setTitleTextColor("#898989");
        titleBar.showNextButton(false);

        RadioGroup group = (RadioGroup) findViewById(R.id.fc_calcu_total_content_rg);
        RadioButton left = (RadioButton) findViewById(R.id.fc_calcu_total_content_rb_left);
        RadioButton right = (RadioButton) findViewById(R.id.fc_calcu_total_content_rb_right);

         listFrag = new ArrayList<BaseSubscriberFragment>();

        fragment_manager = getSupportFragmentManager();
        transaction = fragment_manager.beginTransaction();
        fullPay = new FC_CalcuFullpayFrg();
        loan = new FC_CalcuLoanFrg();

        listFrag.add(fullPay);
        listFrag.add(loan);
        mFragments = new BaseSubscriberFragment[listFrag.size()];
        switchFragments(transaction, 0);

        group.setOnCheckedChangeListener(listener);
    }

    // radiobutton 切换
    RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            // 左边的按钮选中
            if (R.id.fc_calcu_total_content_rb_left
                    == checkedId) {
                FragmentManager fragment_manager = getSupportFragmentManager();
                FragmentTransaction transaction = fragment_manager.beginTransaction();
                switchFragments(transaction, 0);
            }
            // 右边的按钮选中
            if (R.id.fc_calcu_total_content_rb_right
                    == checkedId) {
                FragmentManager fragment_manager = getSupportFragmentManager();
                FragmentTransaction transaction = fragment_manager.beginTransaction();
                switchFragments(transaction, 1);
            }
        }
    };

    // 切换fragment
    private void switchFragments(FragmentTransaction mTransaction, int index) {
        for (int i = 0; i < listFrag.size(); i++) {

            if (index == i) {
                if (mFragments[i] == null) {
                    mFragments[i] = listFrag.get(i);

                    // 将值传送过去
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(FrameHttpTag.TRANSFER_CAR_PRICE_INFO, carInfoBean);
                    mFragments[i].setArguments(bundle);

                    mTransaction.add(R.id.fc_calcu_total_content_main,
                            mFragments[i]);
                }



                mTransaction.show(mFragments[i]);
                mTransaction.commitAllowingStateLoss();
            } else {
                if (mFragments[i] != null) {
                    mTransaction.hide(mFragments[i]);

                }
            }
        }
    }
}
