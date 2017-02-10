package com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberFragment;
import com.etong.android.frame.utils.AddCommaToMoney;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.bean.FC_CalcuCarPriceBean;

/**
 * Created by Ellison.Sun on 2016/8/25.
 */
public class FC_CalcuFullpayFrg extends BaseSubscriberFragment {

    private View view;      // 布局文件

    private ViewGroup selectCar;    // 选车的content
    private TextView carloan_title_money, carName;
    private EditText carloan_nakedcar_money;
    private long nakedcarMoney;            // 裸车价	设置为long型
    FC_CalcuCarPriceBean  carInfoBean;       // 获取到的车辆的信息
    // 基本费用
    private ViewGroup carloan_content_basicmoney;
    private TextView carloan_bottom_basicmoney;
    // 商业保险
    private ViewGroup carloan_content_business;
    private TextView carloan_bottom_business;

    private double basicPrice = 5240;            // 基本费用
    private double commercialInsurance;    // 商业保险

    private String checkedPositon = ""; // 商业保险选中的位置
    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fc_calcu_full_payment, null);
        initData();     // 初始化数据
        initView();     // 初始化组件


        return view;
    }

    /**
     * 初始化组件
     */
    private void initView() {
        // 知道车名字、总共花费的钱、裸车价、
        selectCar = (ViewGroup) view.findViewById(R.id.fc_calcu_full_payment_carname_content);
        carloan_title_money = (TextView) view.findViewById(R.id.fc_calcu_full_payment_total_money);
        carName = (TextView) view.findViewById(R.id.fc_calcu_full_payment_carname);
        carloan_nakedcar_money = (EditText) view.findViewById(R.id.fc_calcu_full_payment_carprice);

        // 设置车辆的信息
        nakedcarMoney = carInfoBean.getCarPrice();
        carName.setText(carInfoBean.getCarName());    // 获取到车的信息并显示
        carloan_nakedcar_money.setText(carInfoBean.getCarPrice()+"");

        carloan_content_basicmoney = (ViewGroup) view.findViewById(R.id.carloan_content_basicmoney);    // 基本费用的content
        carloan_bottom_basicmoney = (TextView) view.findViewById(R.id.carloan_bottom_basicmoney);        // 基本费用的TextView
        carloan_bottom_basicmoney.setText(AddCommaToMoney.AddCommaToMoney("5240") + "元");

        carloan_content_business = (ViewGroup) view.findViewById(R.id.carloan_content_business);        // 商业保险的content
        carloan_bottom_business = (TextView) view.findViewById(R.id.carloan_bottom_business);            // 商业保险的TextView
        carloan_bottom_business.setText("0元");


        addClickListener(carloan_content_basicmoney);           // 基本费用的content
        addClickListener(carloan_content_business);             // 商业保险的content

        carloan_nakedcar_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                // 动态的根据车辆的价格计算车辆花费的钱
                String tempMoney = carloan_nakedcar_money.getText().toString();
                if(!TextUtils.isEmpty(tempMoney)) {
                    carcalculatorResult(Long.valueOf(tempMoney));
                } else {
                    carcalculatorResult(0);
                }

            }
        });

        // 计算值，并把值设置在控件上
        carcalculatorResult(nakedcarMoney);
    }

    /**
     * 初始化数据
     */
    private void initData() {

        Bundle bundle = getArguments();
        carInfoBean = (FC_CalcuCarPriceBean) bundle.getSerializable(FrameHttpTag.TRANSFER_CAR_PRICE_INFO);
    }

    @Override
    protected void onClick(View view) {

        Intent go = new Intent();

        if (view == carloan_content_basicmoney) {
            // 基本费用
            go.setClass(getActivity(), Find_car_BasicCostActivity.class);
            startActivityForResult(go, FC_CalcuLoanFrg.basicmoney);
        }
        if (view == carloan_content_business) {
            // 商业保险
            go.setClass(getActivity(), Find_car_CommercialInsuranceActivity.class);
            go.putExtra("nakedcarMoney", carloan_nakedcar_money.getText().toString());
            go.putExtra("insuranceCheckedPositon", checkedPositon);
            startActivityForResult(go, FC_CalcuLoanFrg.business);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        if (data == null || "".equals(data)) {
            return;
        }
        if (requestCode == FC_CalcuLoanFrg.basicmoney) {
            // 基本费用
        }
        if (requestCode == FC_CalcuLoanFrg.business) {
            // 商业保险
            String commerPrice = data.getStringExtra("commercialInsurance");
            carloan_bottom_business.setText(AddCommaToMoney.AddCommaToMoney(commerPrice) + "元");
            commercialInsurance = Double.valueOf(commerPrice);

            // 获取到商业保险中选中item的状态
            checkedPositon = data.getStringExtra("checkedPosition");
        }

        String tempMoney = carloan_nakedcar_money.getText().toString();
        if(!TextUtils.isEmpty(tempMoney)) {
            carcalculatorResult(Long.valueOf(tempMoney));
        } else {
            carcalculatorResult(0);
        }
    }
    /**
     * @return void    返回类型
     * @throws
     * @Title : carcalculatorResult
     * @Description : (最后计算结果显示在界面上)
     * @params 设定文件
     */
    private void carcalculatorResult(long nakedcarMoney) {
        String totalPrice = Find_car_CarcalculatorUtils.getTotalMoney(
                nakedcarMoney,
                0,
                basicPrice,
                commercialInsurance,
                0);
        carloan_title_money.setText(totalPrice);
    }
}
