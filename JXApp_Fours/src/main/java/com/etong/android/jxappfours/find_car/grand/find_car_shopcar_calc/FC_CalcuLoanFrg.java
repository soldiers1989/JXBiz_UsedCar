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
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.bean.FC_CalcuCarPriceBean;

public class FC_CalcuLoanFrg extends BaseSubscriberFragment {

    public static final int modle = 0;            // 融资方案
    public static final int downpayment = 1;    // 首付
    public static final int paybackloan = 2;    // 贷款年限
    public static final int basicmoney = 3;    // 基本费用
    public static final int business = 4;        // 商业保险


    private TitleBar mTitleBar;
    private TextView carloan_title_money;
    private TextView carloan_down_payment;
    private TextView carloan_monthly_payment;
    private TextView carloan_result_showcar;
    private View carloan_result_divider;
    private EditText carloan_nakedcar_money;
    private ViewGroup carloan_result_modlecontent;
    private TextView carloan_result_modle;
    private ViewGroup carloan_content_downpayment;
    private TextView carloan_bottom_downpayment;
    private ViewGroup carloan_content_paybackloan;
    private TextView carloan_bottom_paybackloan;
    private ViewGroup carloan_content_basicmoney;
    private TextView carloan_bottom_basicmoney;
    private ViewGroup carloan_content_business;
    private TextView carloan_bottom_business;

    private double totalPrice;            // 总的金额
    private double firstPay;            // 首付
    private double monthlyPay;            // 月供
    private long nakedcarMoney;            // 裸车价	设置为long型
    private int modlecontent = 2;            // 融资方案
    private double firstPayRatio = 0.3;        // 首付比例
    private double interestRate = 0.175;        // 根据贷款年限获取到利率
    private double basicPrice = 5240;            // 基本费用
    private double commercialInsurance;    // 商业保险

    private String checkedPositon = "";

    private View view;

    FC_CalcuCarPriceBean carInfoBean;       // 获取到的车辆名字和价格
    @Override
    protected View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.find_car_calcu_result, null);


        Bundle bundle = getArguments();
        carInfoBean = (FC_CalcuCarPriceBean) bundle.getSerializable(FrameHttpTag.TRANSFER_CAR_PRICE_INFO);
        initView();
        return view;
    }

    /**
     * @return void    返回类型
     * @throws
     * @Title : initView
     * @Description : TODO(初始化控件)
     * @params 设定文件
     */
    private void initView() {

//	L.d("获取到的checkedPosition:", checkedPositon);
        // 初始化组件
        carloan_title_money = (TextView) view.findViewById(R.id.carloan_title_money);            // 花费的总金额
        carloan_down_payment = (TextView) view.findViewById(R.id.carloan_down_payment);            // 首付
        carloan_monthly_payment = (TextView) view.findViewById(R.id.carloan_monthly_payment);    // 月供
        carloan_result_showcar = (TextView) view.findViewById(R.id.carloan_result_showcar);        // 具体的车
        carloan_result_divider = view.findViewById(R.id.carloan_result_divider);            // 分割线，两种进来的方式
        carloan_nakedcar_money = (EditText) view.findViewById(R.id.carloan_nakedcar_money);        // 裸车价

        carloan_result_modlecontent = (ViewGroup) view.findViewById(R.id.carloan_result_modlecontent);    // 融资方案的content
        carloan_result_modle = (TextView) view.findViewById(R.id.carloan_result_modle);                    // 综合模式TextView
        carloan_result_modle.setText("等额本息");

        carloan_content_downpayment = (ViewGroup) view.findViewById(R.id.carloan_content_downpayment);    // 首付比例content
        carloan_bottom_downpayment = (TextView) view.findViewById(R.id.carloan_bottom_downpayment);        // 首付比例的百分数

        carloan_content_paybackloan = (ViewGroup) view.findViewById(R.id.carloan_content_paybackloan);    // 贷款年限的content
        carloan_bottom_paybackloan = (TextView) view.findViewById(R.id.carloan_bottom_paybackloan);        // 贷款年限的TextView

        carloan_content_basicmoney = (ViewGroup) view.findViewById(R.id.carloan_content_basicmoney);    // 基本费用的content
        carloan_bottom_basicmoney = (TextView) view.findViewById(R.id.carloan_bottom_basicmoney);        // 基本费用的TextView
        carloan_bottom_basicmoney.setText(AddCommaToMoney.AddCommaToMoney("5240") + "元");

        carloan_content_business = (ViewGroup) view.findViewById(R.id.carloan_content_business);        // 商业保险的content
        carloan_bottom_business = (TextView) view.findViewById(R.id.carloan_bottom_business);            // 商业保险的TextView
        carloan_bottom_business.setText("0元");

        addClickListener(carloan_result_modlecontent);          // 融资方案的content
        addClickListener(carloan_content_downpayment);          // 首付比例的content
        addClickListener(carloan_content_paybackloan);          // 贷款年限的content
        addClickListener(carloan_content_basicmoney);           // 基本费用的content
        addClickListener(carloan_content_business);             // 商业保险的content

        String carInfo = carInfoBean.getCarName();
        nakedcarMoney = carInfoBean.getCarPrice();    // 获取到裸车价
        carloan_result_showcar.setText(carInfo);    // 获取到车的信息并显示
        carloan_nakedcar_money.setText(nakedcarMoney + "");    // 设置值到控件上

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

        String tempMoney = carloan_nakedcar_money.getText().toString();
        if(!TextUtils.isEmpty(tempMoney)) {
            carcalculatorResult(Long.valueOf(tempMoney));
        } else {
            carcalculatorResult(0);
        }
    }

    /**
     * 通过EventBus来传送车辆信息
     * @param bean
     */
//    @Subscriber (tag = FrameHttpTag.TRANSFER_CAR_PRICE_INFO)
//    public void getTransferData(FC_CalcuCarPriceBean bean) {
//        String fromWhere = bean.getFromWhere();
//        String carInfo = bean.getCarName();
//        nakedcarMoney = bean.getCarPrice();
//        // 设置车的名字 和 价格
//        carloan_result_showcar.setText(carInfo);
//        carloan_nakedcar_money.setText(AddCommaToMoney.AddCommaToMoney(String.valueOf(nakedcarMoney)) + "");    // 设置钱值到控件上
//
//        // 通过值计算
//        carcalculatorResult();
//    }

    /**
     * 获取到值
     * @param bean
     */
    public void setCarInfoAndPrice(FC_CalcuCarPriceBean bean) {
        String fromWhere = bean.getFromWhere();
        String carInfo = bean.getCarName();
        nakedcarMoney = bean.getCarPrice();
        // 设置车的名字 和 价格
        carloan_result_showcar.setText(carInfo);
        carloan_nakedcar_money.setText(nakedcarMoney+"");    // 设置钱值到控件上

        String tempMoney = carloan_nakedcar_money.getText().toString();
        if(!TextUtils.isEmpty(tempMoney)) {
            carcalculatorResult(Long.valueOf(tempMoney));
        } else {
            carcalculatorResult(0);
        }
    }
    @Override
    protected void onClick(View view) {

        Intent go = new Intent();
        // 点击事件
        if (view == carloan_result_modlecontent) {
            // 融资方案
            go.setClass(getActivity(), Find_car_FinancingPlanActivity.class);
            startActivityForResult(go, FC_CalcuLoanFrg.modle);
        }
        if (view == carloan_content_downpayment) {
            // 首付比例
            go.setClass(getActivity(), Find_car_FirstPayRatioActivity.class);
            go.putExtra("ratio", carloan_bottom_downpayment.getText().toString());    // 传送过去首付比例
            startActivityForResult(go, FC_CalcuLoanFrg.downpayment);
        }

        if (view == carloan_content_paybackloan) {
            // 贷款年限
            go.setClass(getActivity(), Find_car_LoanYearsActivity.class);
            go.putExtra("loanYear", carloan_bottom_paybackloan.getText().toString());    // 传送过去的贷款年限
            startActivityForResult(go, FC_CalcuLoanFrg.paybackloan);
        }

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
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null || "".equals(data)) {
            return;
        }

        if (requestCode == FC_CalcuLoanFrg.modle) {
            // 融资方案
            modlecontent = data.getIntExtra("modle", 1);
            if (modlecontent == 2) {
                carloan_result_modle.setText("等额本息");
            } else if (modlecontent == 1) {
                carloan_result_modle.setText("等额本金");
            }
        }
//			break;
        if (requestCode == FC_CalcuLoanFrg.downpayment) {
            String f = data.getStringExtra("firstPayRatio");
            carloan_bottom_downpayment.setText(f);
            if (f.equals("30%")) {
                firstPayRatio = 0.3;
            } else if (f.equals("40%")) {
                firstPayRatio = 0.4;
            } else if (f.equals("50%")) {
                firstPayRatio = 0.5;
            } else if (f.equals("60%")) {
                firstPayRatio = 0.6;
            } else if (f.equals("70%")) {
                firstPayRatio = 0.7;
            } else if (f.equals("80%")) {
                firstPayRatio = 0.8;
            } else if (f.equals("90%")) {
                firstPayRatio = 0.9;
            }
        }

        if (requestCode == FC_CalcuLoanFrg.paybackloan) {
            // 贷款年限
            String interestRateStr = data.getStringExtra("interestRate");
            carloan_bottom_paybackloan.setText(interestRateStr);
            if (interestRateStr.equals("3年")) {
                interestRate = 0.175;
            } else if (interestRateStr.equals("2年")) {
                interestRate = 0.125;
            } else if (interestRateStr.equals("1年")) {
                interestRate = 0.075;
            }
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
        // 计算
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
     * @Description : TODO(最后计算结果显示在界面上)
     * @params 设定文件
     */
    private void carcalculatorResult(long carPrice) {
        String totalPrice = Find_car_CarcalculatorUtils.getTotalMoney(
                carPrice,
                firstPayRatio,
                basicPrice,
                commercialInsurance,
                interestRate);
        carloan_title_money.setText(totalPrice);

        String downpaymentPrice = Find_car_CarcalculatorUtils.getDownPayment(
                carPrice
                , firstPayRatio
                , interestRate
                , modlecontent
                , basicPrice
                , commercialInsurance);
        carloan_down_payment.setText(downpaymentPrice + "元");

        String yearStr = carloan_bottom_paybackloan.getText().toString();
        int year = 3;
        if ("3年".equals(yearStr)) {
            year = 3;
        } else if ("2年".equals(yearStr)) {
            year = 2;
        } else if ("1年".equals(yearStr)) {
            year = 1;
        }
        String monthlyPrice = Find_car_CarcalculatorUtils.getMonthlyPayment(
                carPrice
                , firstPayRatio
                , interestRate
                , year
                , modlecontent);
        carloan_monthly_payment.setText(monthlyPrice + "元");
    }


}
