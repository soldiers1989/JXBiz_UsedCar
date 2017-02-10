package com.etong.android.jxappfours.find_car.grand.carfinance_caclu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.etong.android.frame.subscriber.BaseSubscriberActivity;
import com.etong.android.frame.widget.TitleBar;
import com.etong.android.jxappfours.R;

public class FC_FinanceActivity extends BaseSubscriberActivity {
/*
  ##################################################################################################
  ##                                        类中的变量                                            ##
  ##################################################################################################
*/
    public static final String LOAN_PRICE = "loan price";
    private EditText etPrice;       // 输入的价格
    private TitleBar mTitleBar;
    private Button btCalcu;

    @Override
    protected void onInit(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.find_car_activity_fc_finance_content_calcu);

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
        mTitleBar.setTitle("融资计算");
        mTitleBar.showNextButton(false);

        etPrice = findViewById(R.id.fc_finance_content_calcu_et_price, EditText.class);     // 输入的金额
        btCalcu = findViewById(R.id.fc_finance_content_calcu_btn_calcu, Button.class);      // 点击计算的按钮
        addClickListener(R.id.fc_finance_content_calcu_btn_calcu);
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
        if (view.getId() == R.id.fc_finance_content_calcu_btn_calcu) {
            // 进入计算页面
            goCalculatorResult();
        }
    }


/*
  ##################################################################################################
  ##                              使用的逻辑方法，以及对外公开的方法                              ##
  ##                                      请求数据、获取数据                                      ##
  ##################################################################################################
*/

    /**
     * @Title        : goCalculatorResult
     * @Description  : 获取到输入的价格进入到计算的结果页面
     * @params     设定文件
     * @return
     *     void    返回类型
     * @throws
     */
    private void goCalculatorResult() {
        if (TextUtils.isEmpty(etPrice.getText().toString())) {
            toastMsg("请输入正确的金额");
            return;
        }

        String price = etPrice.getText().toString();
        if (Double.valueOf(price) < 0) {
            toastMsg("请输入正确的金额");
            return;
        }

        Intent goResult = new Intent(this, FC_FinanceResultActivity.class);
        goResult.putExtra(LOAN_PRICE, price);
        startActivity(goResult);
    }



/*
  ##################################################################################################
  ##                            类中根据成员设置的getter和setter方法                              ##
  ##################################################################################################
*/
}
