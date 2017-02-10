package com.etong.android.jxappfours.find_car.grand.carfinance_caclu;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.etong.android.frame.request_init.FrameHttpTag;
import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.utils.AddCommaToMoney;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;
import com.etong.android.jxappfours.find_car.grand.bean.FC_CalcuCarPriceBean;
import com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc.FC_CalcuLoanFrg;
import com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc.Find_car_BasicCostActivity;
import com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc.Find_car_CarcalculatorUtils;
import com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc.Find_car_CommercialInsuranceActivity;
import com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc.Find_car_FinancingPlanActivity;
import com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc.Find_car_FirstPayRatioActivity;
import com.etong.android.jxappfours.find_car.grand.find_car_shopcar_calc.Find_car_LoanYearsActivity;

public class FC_FinanceResultActivity extends BaseSubscriberActivity {
/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/

    private TitleBar mTitleBar;
    private TextView carloan_title_money;
    private TextView carloan_down_payment;
    private TextView carloan_monthly_payment;
    private ViewGroup carloan_result_modlecontent;
    private TextView carloan_result_modle;
    private ViewGroup carloan_content_downpayment;
    private TextView carloan_bottom_downpayment;
    private ViewGroup carloan_content_paybackloan;
    private TextView carloan_bottom_paybackloan;

    private int modlecontent = 2;               // 融资方案
    private double firstPayRatio = 0.3;         // 首付比例
    private double interestRate = 0.175;        // 根据贷款年限获取到利率
    private long loanPrice;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_car_activity_fc_finance_result_content);
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
        mTitleBar = new TitleBar(this);
        mTitleBar.setTitle("融资计算结果");
        mTitleBar.showNextButton(false);

        // 初始化组件
        carloan_title_money = (TextView) findViewById(R.id.carloan_title_money);            // 花费的总金额
        carloan_down_payment = (TextView)findViewById(R.id.carloan_down_payment);            // 首付
        carloan_monthly_payment = (TextView) findViewById(R.id.carloan_monthly_payment);    // 月供

        carloan_result_modlecontent = (ViewGroup) findViewById(R.id.fc_finance_result_bottom_way_vp);    // 融资方案的content
        carloan_result_modle = (TextView) findViewById(R.id.fc_finance_result_bottom_way_txt);           // 综合模式TextView
        carloan_result_modle.setText("等额本息");

        carloan_content_downpayment = (ViewGroup) findViewById(R.id.fc_finance_result_bottom_first_pay_vp);    // 首付比例content
        carloan_bottom_downpayment = (TextView) findViewById(R.id.fc_finance_result_bottom_first_pay_txt);        // 首付比例的百分数

        carloan_content_paybackloan = (ViewGroup) findViewById(R.id.fc_finance_result_bottom_loanyear_vp);    // 贷款年限的content
        carloan_bottom_paybackloan = (TextView) findViewById(R.id.fc_finance_result_bottom_loanyear_txt);        // 贷款年限的TextView

        addClickListener(carloan_result_modlecontent);          // 融资方案的content
        addClickListener(carloan_content_downpayment);          // 首付比例的content
        addClickListener(carloan_content_paybackloan);          // 贷款年限的content
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

        Intent i = getIntent();
        String price = i.getStringExtra(FC_FinanceActivity.LOAN_PRICE);

        if(TextUtils.isEmpty(price)) {
            return;
        }
        loanPrice = Long.parseLong(price);
        // 计算结果
        carcalculatorResult(loanPrice);
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
        Intent go = new Intent();
        // 点击事件
        if (view == carloan_result_modlecontent) {
            // 融资方案
            go.setClass(this, Find_car_FinancingPlanActivity.class);
            startActivityForResult(go, FC_CalcuLoanFrg.modle);
        }
        if (view == carloan_content_downpayment) {
            // 首付比例
            go.setClass(this, Find_car_FirstPayRatioActivity.class);
            go.putExtra("ratio", carloan_bottom_downpayment.getText().toString());    // 传送过去首付比例
            startActivityForResult(go, FC_CalcuLoanFrg.downpayment);
        }

        if (view == carloan_content_paybackloan) {
            // 贷款年限
            go.setClass(this, Find_car_LoanYearsActivity.class);
            go.putExtra("loanYear", carloan_bottom_paybackloan.getText().toString());    // 传送过去的贷款年限
            startActivityForResult(go, FC_CalcuLoanFrg.paybackloan);
        }

    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

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

        // 计算最终的结果
        carcalculatorResult(loanPrice);
    }

    /**
     * @return void    返回类型
     * @throws
     * @Title : carcalculatorResult
     * @Description : 最后计算结果显示在界面上
     * @params 设定文件
     */
    protected void carcalculatorResult(long carPrice) {
        String totalPrice = Find_car_CarcalculatorUtils.getTotalMoney(
                carPrice,
                firstPayRatio,
                0,
                0,
                interestRate);
        carloan_title_money.setText(totalPrice);

        String downpaymentPrice = Find_car_CarcalculatorUtils.getDownPayment(
                carPrice
                , firstPayRatio
                , interestRate
                , modlecontent
                , 0
                , 0);
        carloan_down_payment.setText("首付：\n"+downpaymentPrice + "元");

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
        carloan_monthly_payment.setText("月供：\n"+monthlyPrice + "元");
    }


/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/

}
