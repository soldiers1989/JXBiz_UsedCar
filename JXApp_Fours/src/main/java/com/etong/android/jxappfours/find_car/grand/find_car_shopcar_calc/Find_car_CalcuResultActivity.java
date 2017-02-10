package com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.AddCommaToMoney;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;

public class Find_car_CalcuResultActivity extends BaseSubscriberActivity {

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
    private TextView carloan_nakedcar_money;
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

    @Override
    protected void onInit(Bundle savedInstanceState) {
        setContentView(R.layout.find_car_calcu_result);

        initView();
    }

    /**
     * @return void    返回类型
     * @throws
     * @Title : initView
     * @Description : TODO(初始化控件)
     * @params 设定文件
     */
    private void initView() {


        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("购车计算");
        mTitleBar.showNextButton(false);


//	L.d("获取到的checkedPosition:", checkedPositon);
        // 初始化组件
        carloan_title_money = findViewById(R.id.carloan_title_money, TextView.class);            // 花费的总金额
        carloan_down_payment = findViewById(R.id.carloan_down_payment, TextView.class);            // 首付
        carloan_monthly_payment = findViewById(R.id.carloan_monthly_payment, TextView.class);    // 月供
        carloan_result_showcar = findViewById(R.id.carloan_result_showcar, TextView.class);        // 具体的车
        carloan_result_divider = findViewById(R.id.carloan_result_divider, View.class);            // 分割线，两种进来的方式
        carloan_nakedcar_money = findViewById(R.id.carloan_nakedcar_money, TextView.class);        // 裸车价

        carloan_result_modlecontent = findViewById(R.id.carloan_result_modlecontent, ViewGroup.class);    // 融资方案的content
        carloan_result_modle = findViewById(R.id.carloan_result_modle, TextView.class);                    // 综合模式TextView
        carloan_result_modle.setText("等额本息");

        carloan_content_downpayment = findViewById(R.id.carloan_content_downpayment, ViewGroup.class);    // 首付比例content
        carloan_bottom_downpayment = findViewById(R.id.carloan_bottom_downpayment, TextView.class);        // 首付比例的百分数

        carloan_content_paybackloan = findViewById(R.id.carloan_content_paybackloan, ViewGroup.class);    // 贷款年限的content
        carloan_bottom_paybackloan = findViewById(R.id.carloan_bottom_paybackloan, TextView.class);        // 贷款年限的TextView

        carloan_content_basicmoney = findViewById(R.id.carloan_content_basicmoney, ViewGroup.class);    // 基本费用的content
        carloan_bottom_basicmoney = findViewById(R.id.carloan_bottom_basicmoney, TextView.class);        // 基本费用的TextView
        carloan_bottom_basicmoney.setText(AddCommaToMoney.AddCommaToMoney("5240") + "元");

        carloan_content_business = findViewById(R.id.carloan_content_business, ViewGroup.class);        // 商业保险的content
        carloan_bottom_business = findViewById(R.id.carloan_bottom_business, TextView.class);            // 商业保险的TextView
        carloan_bottom_business.setText("0元");

        addClickListener(R.id.carloan_result_modlecontent);
        addClickListener(R.id.carloan_content_downpayment);
        addClickListener(R.id.carloan_content_paybackloan);
        addClickListener(R.id.carloan_content_basicmoney);
        addClickListener(R.id.carloan_content_business);

        Intent i = getIntent();
        int fromWhere = i.getIntExtra("fromWhere", -1);
        if (fromWhere == 1) {                // 选择车之后
            String carInfo = i.getStringExtra("carInfo");
            nakedcarMoney = (long) i.getDoubleExtra("carPrice", 0.0);    // 获取到裸车价
            carloan_result_showcar.setText(carInfo);    // 获取到车的信息并显示
        } else if (fromWhere == 2) {        // 直接输入的钱
            carloan_result_showcar.setVisibility(View.GONE);
            carloan_result_divider.setVisibility(View.GONE);
            nakedcarMoney = Long.valueOf(i.getStringExtra("nakedcarMoney"));
        }
        carloan_nakedcar_money.setText(AddCommaToMoney.AddCommaToMoney(String.valueOf(nakedcarMoney)) + "");    // 设置值到控件上

        carcalculatorResult();        // 计算值，并把值设置在控件上
    }

    @Override
    protected void onClick(View view) {

        Intent go = new Intent();
        // 点击事件
//        switch (view.getId()) {
//            case R.id.carloan_result_modlecontent:        // 融资方案
//                go.setClass(Find_car_CalcuResultActivity.this, Find_car_FinancingPlanActivity.class);
//                startActivityForResult(go, Find_car_CalcuResultActivity.modle);
//                break;
//            case R.id.carloan_content_downpayment:        // 首付比例
//                go.setClass(Find_car_CalcuResultActivity.this, Find_car_FirstPayRatioActivity.class);
//                go.putExtra("ratio", carloan_bottom_downpayment.getText().toString());    // 传送过去首付比例
//                startActivityForResult(go, Find_car_CalcuResultActivity.downpayment);
//                break;
//            case R.id.carloan_content_paybackloan:        // 贷款年限
//                go.setClass(Find_car_CalcuResultActivity.this, Find_car_LoanYearsActivity.class);
//                go.putExtra("loanYear", carloan_bottom_paybackloan.getText().toString());    // 传送过去的贷款年限
//                startActivityForResult(go, Find_car_CalcuResultActivity.paybackloan);
//                break;
//            case R.id.carloan_content_basicmoney:        // 基本费用
//                go.setClass(Find_car_CalcuResultActivity.this, Find_car_BasicCostActivity.class);
//                startActivityForResult(go, Find_car_CalcuResultActivity.basicmoney);
//                break;
//            case R.id.carloan_content_business:            // 商业保险
//                go.setClass(Find_car_CalcuResultActivity.this, Find_car_CommercialInsuranceActivity.class);
//                go.putExtra("nakedcarMoney", carloan_nakedcar_money.getText().toString());
//                go.putExtra("insuranceCheckedPositon", checkedPositon);
//                startActivityForResult(go, Find_car_CalcuResultActivity.business);
//                break;
//        }

        if (view.getId() == R.id.carloan_result_modlecontent) { // 融资方案
            go.setClass(Find_car_CalcuResultActivity.this, Find_car_FinancingPlanActivity.class);
            startActivityForResult(go, Find_car_CalcuResultActivity.modle);
        } else if (view.getId() == R.id.carloan_content_downpayment) { // 首付比例
            go.setClass(Find_car_CalcuResultActivity.this, Find_car_FirstPayRatioActivity.class);
            go.putExtra("ratio", carloan_bottom_downpayment.getText().toString());    // 传送过去首付比例
            startActivityForResult(go, Find_car_CalcuResultActivity.downpayment);
        } else if (view.getId() == R.id.carloan_content_paybackloan) {// 贷款年限
            go.setClass(Find_car_CalcuResultActivity.this, Find_car_LoanYearsActivity.class);
            go.putExtra("loanYear", carloan_bottom_paybackloan.getText().toString());    // 传送过去的贷款年限
            startActivityForResult(go, Find_car_CalcuResultActivity.paybackloan);
        } else if (view.getId() == R.id.carloan_content_basicmoney) {// 基本费用
            go.setClass(Find_car_CalcuResultActivity.this, Find_car_BasicCostActivity.class);
            startActivityForResult(go, Find_car_CalcuResultActivity.basicmoney);
        } else if (view.getId() == R.id.carloan_content_business) { // 商业保险
            go.setClass(Find_car_CalcuResultActivity.this, Find_car_CommercialInsuranceActivity.class);
            go.putExtra("nakedcarMoney", carloan_nakedcar_money.getText().toString());
            go.putExtra("insuranceCheckedPositon", checkedPositon);
            startActivityForResult(go, Find_car_CalcuResultActivity.business);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null || "".equals(data)) {
            return;
        }

        if (requestCode == Find_car_CalcuResultActivity.modle) {
            // 融资方案
            modlecontent = data.getIntExtra("modle", 1);
            if (modlecontent == 2) {
                carloan_result_modle.setText("等额本息");
            } else if (modlecontent == 1) {
                carloan_result_modle.setText("等额本金");
            }
        }
//			break;
        if (requestCode == Find_car_CalcuResultActivity.downpayment) {
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

        if (requestCode == Find_car_CalcuResultActivity.paybackloan) {
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
        if (requestCode == Find_car_CalcuResultActivity.basicmoney) {
            // 基本费用
        }
        if (requestCode == Find_car_CalcuResultActivity.business) {
            // 商业保险
            String commerPrice = data.getStringExtra("commercialInsurance");
            carloan_bottom_business.setText(AddCommaToMoney.AddCommaToMoney(commerPrice) + "元");
            commercialInsurance = Double.valueOf(commerPrice);

            // 获取到商业保险中选中item的状态
            checkedPositon = data.getStringExtra("checkedPosition");
        }
        carcalculatorResult();
    }

    /**
     * @return void    返回类型
     * @throws
     * @Title : carcalculatorResult
     * @Description : TODO(最后计算结果显示在界面上)
     * @params 设定文件
     */
    private void carcalculatorResult() {
        String totalPrice = Find_car_CarcalculatorUtils.getTotalMoney(
                nakedcarMoney, firstPayRatio,
                basicPrice, commercialInsurance, interestRate);
        carloan_title_money.setText(totalPrice);

        String downpaymentPrice = Find_car_CarcalculatorUtils.getDownPayment(
                nakedcarMoney
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
                nakedcarMoney
                , firstPayRatio
                , interestRate
                , year
                , modlecontent);
        carloan_monthly_payment.setText(monthlyPrice + "元");
    }


}
